package com.kemk.sql;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlakaVeritabani {
	SQLConnection conn ;
	public PlakaVeritabani() throws SQLException {
		// TODO Auto-generated constructor stub
		conn = new SQLConnection();
		
	}
	public void GetDatas() {
		try {
			conn.Connect();
			try {
				String sorgu = "SELECT * FROM plakaListesi ";
				ResultSet result = conn.durum.executeQuery(sorgu);
		         int i = 1 ;
		         while (result.next()) {  
		              System.out.println("------------------"+i+".Plaka--------------------------");
		             System.out.println("Id: "+result.getInt(1) +
		                     "\nPlaka : " + result.getString(2)+
		                     "\nKayýt Tarihi : "+ result.getDate(3));
		            i++;
		         }
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println(e);
			}
			try {
				conn.Disconnect();
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println(e);
			}
		} catch (SQLException e) {
			CantConnectToDBCallback(e);
		}
		
		
	}
	public void PlakaEkle(String plakaName,String date) throws SQLException {
		conn.Connect();
		System.out.println(date.toString());
		String sorgu = "INSERT INTO `plakalistesi`(`plakaName`,`date`) VALUES ('"+plakaName+"' , '"+date+"')";
		 int result = conn.durum.executeUpdate(sorgu);
         System.out.println("------------------Inserting--------------------------");
         System.out.println("result :" + result);
          
         conn.Disconnect();
	}
	public RegisteredUserInfo PlakaSorgula(String plakaName) throws SQLException {
		conn.Connect();
		
		String sorgu = "SELECT users.id as 'userId', plakaListesi.plakaName as 'plateName', users.Name as 'username' FROM plakaListesi,users where plakaName='"+plakaName+"' and users.plakaID = plakaListesi.id";
		ResultSet result = conn.durum.executeQuery(sorgu);
		RegisteredUserInfo user = null;
		if(!result.wasNull()) {
         if(result.next()) { 
        	 user = new RegisteredUserInfo(result.getInt("userId") , result.getString("plateName"),result.getString("username"));
        	 System.out.println("mysql : Plaka Bulundu: plaka :"+result.getString("plateName")  + ", User : " + result.getString("username"));
         }else {
        	 System.out.println("mysql : Plaka Bulunamadý");
         }
		}else {
			System.out.println("mysql : Plaka Bulunamadý");
		}
        conn.Disconnect();
        return user;
	} 
	public void CantConnectToDBCallback(SQLException e ) {}
}
