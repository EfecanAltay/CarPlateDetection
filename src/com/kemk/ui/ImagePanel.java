package com.kemk.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage image;
	
	public ImagePanel() {
	
	}
	public int GetImageWidth() {
		if(image != null)
		return image.getWidth();
		return -1;
	}
	public int GetImageHeight() {
		if(image != null)
		return image.getHeight();
		return -1;
	}
	public void SetImage(String path) {
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repaint();
	}
	public void SetImage(BufferedImage image) {
		
		
		this.image = image;
		repaint();
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
		if(image != null)
			g.drawImage(image,0,0,getWidth(),getHeight(),this);
	}
	


}
