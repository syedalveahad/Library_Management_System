package Library;

import java.sql.*;



public class ConnectionClass {
    
    Connection con;
    Statement stm;
    
    ConnectionClass(){
        try
        {
    
            Class.forName("com.mysql.cj.jdbc.Driver");  
            con=DriverManager.getConnection(  
                "jdbc:mysql://127.0.0.1:3306/library","root","alve"); 
            stm=con.createStatement(); 
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args)
    {
        new ConnectionClass();
    }
    
    
}
