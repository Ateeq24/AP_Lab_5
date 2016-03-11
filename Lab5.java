/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
/**
 *
 * @author hrehman.bscs13seecs
 */
public class Lab5 {
    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws SQLException, SQLException, IOException 
    {
        func();
    }
    public static void func() throws SQLException, FileNotFoundException, IOException
    {
        
   String DB_URL = "jdbc:mysql://localhost/";

   //  Database credentials
   String USER = "root";
   String PASS = "root";
   
   Connection conn = null;
   Statement stmt = null;
   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to a selected database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("Connected database successfully...");
      
      //STEP 4: Execute a query
      System.out.println("Creating table in given database...");
      stmt = conn.createStatement();
      String sql="DROP SCHEMA IF EXISTS GeoLocation";
      stmt.executeUpdate(sql);
     sql="CREATE SCHEMA IF NOT EXISTS GeoLocation";
      stmt.executeUpdate(sql);
      sql="use GeoLocation";
      stmt.executeUpdate(sql);
      sql = "CREATE TABLE cities " +
                   "(id INTEGER not NULL, " +
                   " country VARCHAR(255), " + 
                   " city VARCHAR(255), " + 
                   " postal_code FLOAT, " + 
                   " latitude FLOAT, " + 
                   " longitude FLOAT, " + 
                   " metroCode VARCHAR(255), " + 
                   " PRIMARY KEY ( id ))"; 

      stmt.executeUpdate(sql);

      System.out.println("Created table in given database...");
      
         BufferedReader CSVFile = 
        new BufferedReader(new FileReader("C:\\Users\\hrehman.bscs13seecs\\Desktop\\GeoLiteCity-Location.csv"));

        CSVFile.readLine();
        CSVFile.readLine();
        String dataRow = CSVFile.readLine(); // Read first line.
             // The while checks to see if the data is null. If 
             // it is, we've hit the end of the file. If not, 
             // process the data.
        try
        {
            stmt = conn.createStatement();
        for (int i = 0; i < 1000; i++)
            {
                String statement = "INSERT INTO cities VALUES (" + dataRow + ")";
                statement = statement.replace(",,",",NULL,");
                statement = statement.replace(",)",",NULL)");
                stmt.executeUpdate(statement);
                dataRow = CSVFile.readLine(); // Read next line of data.
            }
        }
        catch(SQLException ex)
        {
            System.err.println("Error --->"+ex.toString());
        }
      
   }
   catch(SQLException | ClassNotFoundException se){
   }finally{
      //finally block used to close resources
    // do nothing
       
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
      }//end finally try
   }//end try

        
    }
}
