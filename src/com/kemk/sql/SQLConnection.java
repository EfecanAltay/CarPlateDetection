package com.kemk.sql;

import com.kemk.utils.IConnectionListener;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.Statement;

import java.awt.Event;
import java.sql.DriverAction;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.omg.PortableServer.portable.Delegate;

public class SQLConnection implements IConnectionListener {

		    Connection baglan;
		    Statement durum ;

			public SQLConnection()  throws SQLException
			{
					Driver myDriver = new com.mysql.jdbc.Driver();
					DriverManager.registerDriver( myDriver );				
			}
			public void Connect() throws SQLException {
				baglan =(Connection) DriverManager.getConnection("jdbc:mysql://localhost:8081/PlakaTakip","root","usbw");
				 String goster = "SELECT * FROM plakaListesi ";
		         durum = (Statement) baglan.createStatement();
			}
			public void Disconnect() throws SQLException {
				 durum.close();
				 baglan.close();
			}
			@Override
			public void DisConnectionEvent() {
				//Baðlantý hatasýnda fýrlayacak callback
				System.out.println("Disconnect to SQL");
				
			}


}
