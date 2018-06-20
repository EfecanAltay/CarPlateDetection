package com.kemk.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import org.apache.poi.ss.formula.functions.TimeFunc;

import com.kemk.importing.Camera;
import com.mysql.jdbc.util.LRUCache;
import com.openalpr.jni.Alpr;
import com.openalpr.jni.AlprCoordinate;
import com.openalpr.jni.AlprException;
import com.openalpr.jni.AlprPlate;
import com.openalpr.jni.AlprPlateResult;
import com.openalpr.jni.AlprRegionOfInterest;
import com.openalpr.jni.AlprResults;

public class CameraPanel extends ImagePanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Camera cam ;

	
	boolean isFPSInfo = false ;
	JLabel txtFPS ,txtPlate;
	
	PlateTracking p;
	BufferedImage plateImage;
	
	Rectangle pRect ;
	boolean isplateFound;
	
	boolean isLiveTracking= false;
	int MotionSetValue = 0;
	int MotionSetValueOffset = 30 ;
	int MotionSetValue2 = MotionSetValue + MotionSetValueOffset;
	
	boolean PlateIsReaded = false ; 
	
	public void SetMotionValue(int value) {
		MotionSetValue = value ;
		MotionSetValue2 = MotionSetValue  + MotionSetValueOffset;
	}
	public void SetMotionOffsetValue(int value) {
		MotionSetValueOffset = value ;
		MotionSetValue2 = MotionSetValue  + MotionSetValueOffset;
	}
	public class Line{
		int x1,x2,y1,y2;
		public Line(int x1,int y1,int x2,int y2) {
			this.x1 = x1 ;
			this.x2 = x2 ;
			this.y1 = y1 ;
			this.y2 = y2 ;
		}
	
	}
	public class Rectangle{
		public int x,y,w,h;
		Line x1,x2,y1,y2;
		
		public Rectangle(int x,int y,int w,int h) {
			this.x = x ;
			this.y = y ;
			this.w = w ;
			this.h = h ;
			
		}
		public Rectangle(List<AlprCoordinate> cList) {
			
			
			x1 = new Line(cList.get(0).getX(),cList.get(0).getY(),cList.get(1).getX(),cList.get(1).getY());
			y2 = new Line(cList.get(1).getX(),cList.get(1).getY(),cList.get(2).getX(),cList.get(2).getY());
			x2 = new Line(cList.get(2).getX(),cList.get(2).getY(),cList.get(3).getX(),cList.get(3).getY());
			y1 = new Line(cList.get(3).getX(),cList.get(3).getY(),cList.get(0).getX(),cList.get(0).getY());
			
		}
	}
	
	public CameraPanel() {
		super();
	    cam = new Camera();
	}
	public CameraPanel(JLabel txtFPS , JLabel txtPlate , ImagePanel imagePanel) {
		super();
	    cam = new Camera();
	    this.txtPlate = txtPlate;
	    isFPSInfo = true ;
	   this.txtFPS = txtFPS;
	   
	   p = new PlateTracking(cam) {
			
			@Override
			public void RefleshCallback(BufferedImage image, boolean plateIsFound, AlprResults results) {
				if(image != null) {
					SetImage(image);
					isplateFound = plateIsFound;
					if(plateIsFound) {
						AlprPlateResult r =  results.getPlates().get(0);
						AlprPlate plate = r.getBestPlate();
						
						List<AlprCoordinate> l =  r.getPlatePoints();
						
						
						//System.out.println("Plaka Bulundu h:" + results);
							
							if(l.size() == 4) {
								pRect = new Rectangle(l);
								if(isLiveTracking)
								{
									plateImage =  image.getSubimage(pRect.x1.x1,pRect.x1.y1,pRect.x1.x2-pRect.x1.x1,pRect.y1.y1-pRect.y1.y2);
									imagePanel.SetImage(plateImage);
									txtPlate.setText(plate.getCharacters());
								}
								if(!isLiveTracking && !PlateIsReaded && l.get(0).getY() > MotionSetValue && l.get(1).getY() > MotionSetValue && l.get(3).getY() > MotionSetValue && l.get(2).getY() > MotionSetValue) {
									
									int x,y,w,h;
									
									x = pRect.x1.x1;
									y = pRect.x1.y1;
									w = pRect.x1.x2-pRect.x1.x1;
									h = pRect.y1.y1-pRect.y1.y2;
							
									if(h >= image.getHeight())
										h = image.getHeight();
									if(w >= image.getWidth() )
										w = image.getWidth();
									if(x < 0) x= 0; if(y < 0) y = 0;
									
									plateImage = image.getSubimage(x,y,w,h);
									
									if(l.get(0).getY() < MotionSetValue2 && l.get(1).getY() < MotionSetValue2 && l.get(3).getY() < MotionSetValue2 && l.get(2).getY() < MotionSetValue2)
									{
										DetectingCallback(plate.getCharacters());
										imagePanel.SetImage(plateImage);
										txtPlate.setText(plate.getCharacters());
										PlateIsReaded = true ;
									}
								}else if( l.get(0).getY() < MotionSetValue && l.get(1).getY() < MotionSetValue && l.get(3).getY() < MotionSetValue && l.get(2).getY() < MotionSetValue) {
									PlateIsReaded = false ;
								}
								else if(PlateIsReaded && l.get(0).getY() > MotionSetValue2 && l.get(1).getY() > MotionSetValue2 && l.get(3).getY() > MotionSetValue2 && l.get(2).getY() > MotionSetValue2)
								{
									PlateIsReaded = false ;
								}
								
							}
						
					}
					
					repaint();	
				}
			}
		};
	}
	public void StartCamera() {
		cam.OpenCamera();
		p.Tracking();
	}
	public void DetectingCallback(String platename) {
		
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(!isLiveTracking) {
			g.setColor(Color.GREEN);
			g.drawLine(0,MotionSetValue,getWidth(),MotionSetValue);
			g.setColor(Color.RED);
			g.drawLine(0,MotionSetValue2,getWidth(),MotionSetValue2);
			}
		if(isplateFound) {
			g.setColor(Color.GREEN);
			
			//g.drawRect(pRect.x, pRect.y, pRect.w, pRect.h);

			g.drawLine(pRect.x1.x1 ,pRect.x1.y1, pRect.x1.x2, pRect.x1.y2);
			g.drawLine(pRect.x2.x1,pRect.x2.y1, pRect.x2.x2, pRect.x2.y2);
			g.drawLine(pRect.y1.x1,pRect.y1.y1, pRect.y1.x2, pRect.y1.y2);
			g.drawLine(pRect.y2.x1,pRect.y2.y1, pRect.y2.x2, pRect.y2.y2);
			
			//g.fillRect(10, 10, 100, 100);
		}
	}
}
