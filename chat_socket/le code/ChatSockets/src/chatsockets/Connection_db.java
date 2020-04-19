/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsockets;

import java.sql.Connection;
import java.sql.*;

/**
 *
 * @author mounir
 */
public class Connection_db {



	
	Connection conn=null;
	
 public static Connection ConnexionDB() {
	 
try {
		
		 Class.forName("org.postgresql.Driver");

	      String url = "jdbc:postgresql://localhost:5432/client_chat";
	      String user = "postgres";
	      String passwd = "mounir";

	      Connection conn= DriverManager.getConnection(url, user, passwd);
	         
	      return conn ;
				
	} catch (Exception e) {
		// TODO Auto-generated catch block
		  System.out.println("Connexion not ***************effective !"); 
	return null ;
	}            } 
	
	
public static void main(String []args){
    ConnexionDB();
}

    
}
