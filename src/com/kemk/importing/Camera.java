package com.kemk.importing;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;

public class Camera {
	Webcam webcam ;
	public Camera() {
		// TODO Auto-generated constructor stub
	}
	public void OpenCamera() {
		webcam = Webcam.getDefault();
		if(webcam != null) {
			
			System.out.println("Camera is Open");
			webcam.getViewSize().setSize(400, 300);
			
			webcam.open();
			
		}
	}
	public BufferedImage GetImage() {
		 
		BufferedImage bi = webcam.getImage();
		return bi;
	}
	public ByteBuffer GetImageByteArray(){
		return webcam.getImageBytes();
	}
	public double GetFPS() {
		
		return webcam.getFPS();
	}
}
