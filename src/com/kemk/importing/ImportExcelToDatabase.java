package com.kemk.importing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.flexdock.util.ResourceManager;

public class ImportExcelToDatabase {
	public ImportExcelToDatabase() {
		
	}
	public void LoadToXLS(String src) {
		try {
			FileInputStream file = new FileInputStream(new File(System.getProperty("user.dir") + "/plakaListesi.xls"));   
			
			HSSFWorkbook workbook;
			try {
				workbook = new HSSFWorkbook(file);
				HSSFSheet sheet = workbook.getSheetAt(0);
				Iterator<Row> rowIterator = sheet.iterator();
				for (Row row : sheet) {
					Cell c = row.getCell(0);
					System.out.println(c.getStringCellValue());
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
