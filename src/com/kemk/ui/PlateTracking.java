package com.kemk.ui;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.kemk.importing.Camera;
import com.openalpr.jni.Alpr;
import com.openalpr.jni.AlprCoordinate;
import com.openalpr.jni.AlprException;
import com.openalpr.jni.AlprPlate;
import com.openalpr.jni.AlprPlateResult;
import com.openalpr.jni.AlprResults;

public abstract class PlateTracking {
	
	Camera camera;
	byte[] bfArray;
	BufferedImage bim;
	AlprResults results;
    String PlateName ;
    Thread trackingThread;
    
    boolean isLiveTracking = false ;
    boolean plateIsFound = false ;
    
    float plateClimpY = 0;
    
	public PlateTracking(Camera cam) {
		camera = cam ;
		
		trackingThread = new Thread(new Runnable() {
			public void run() {

			    ByteArrayOutputStream bf = new ByteArrayOutputStream();
			    Alpr alpr = null;
			    alpr = new Alpr("eu","openalpr.conf","runtime_data");
				alpr.setTopN(1);
				alpr.setDefaultRegion("tr");
			    try {
			    	
			    	while(true) {
			    		bim = camera.GetImage() ;
			    		plateIsFound = false;
			    		
						if(bim != null ) {
							
							//bim.flush();
							bf.flush();
							bf.reset();
							ImageIO.write(bim,"jpg", bf);

							results = alpr.recognize(bf.toByteArray());
							
							bim.flush();
							bf.flush();
							bf.close();
							
							
							if(results.getPlates().size() > 0 ) {
								 plateIsFound = true;
								 							
							}
							
							RefleshCallback(bim,plateIsFound,results);
							
							bfArray = null;
							bim = null;
						}
						 
					}	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (AlprException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	public void Tracking() {
		trackingThread.start();
	}
	public void MotionTracking() {
		
	}
	public abstract void RefleshCallback(BufferedImage image , boolean plateIsFound,AlprResults results );
}
