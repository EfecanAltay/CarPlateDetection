package com.kemk.main;

import java.awt.EventQueue;

import com.kemk.importing.ImportExcelToDatabase;
import com.kemk.ui.MainGUI;

public class Main {
	
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImportExcelToDatabase ied = new ImportExcelToDatabase();
					
					MainGUI.main(args);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
	}

}
