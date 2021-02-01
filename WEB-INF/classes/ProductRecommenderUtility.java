import java.io.*;
import java.sql.*;
import java.io.IOException;
import java.util.*;

import javax.servlet.ServletContext;

public class ProductRecommenderUtility{
	
	static Connection conn = null;
    static String message;
	
	public static String getConnection()
	{

		String pwd = "root";
		// String pwd = "12345678";
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/homehub?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root",pwd);							
			message="Successfull";
			return message;
		}
		catch(SQLException e)
		{
			 message="unsuccessful";
		     return message;
		}
		catch(Exception e)
		{
			 message="unsuccessful";
		     return message;
		}
	}

	public HashMap<String,String> readOutputFile(){

		String TOMCAT_HOME = System.getProperty("catalina.home");

		
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
		HashMap<String,String> prodRecmMap = new HashMap<String,String>();
		try {

        	br = new BufferedReader(
                            new FileReader(new File(TOMCAT_HOME + "\\webapps\\CSP584-Project\\data\\output.csv")));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] prod_recm = line.split(cvsSplitBy,2);
				prodRecmMap.put(prod_recm[0],prod_recm[1]);
            }
			
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}
		
		return prodRecmMap;
	}
	
	public static Product getProduct(String pname){
		Product prodObj = new Product();
		try 
		{
			getConnection();
			String selectProd="select * from  product where pname=?";
			PreparedStatement pst = conn.prepareStatement(selectProd);
			pst.setString(1,pname);
			ResultSet rs = pst.executeQuery();
		
			while(rs.next())
			{	
				prodObj = new Product(rs.getInt("pid"),rs.getString("pname"),rs.getString("type"),
											  rs.getDouble("price"),rs.getInt("stock"),rs.getString("description"),
											  rs.getString("img"),rs.getString("brand"));
			}
			rs.close();
			pst.close();
			conn.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return prodObj;	
	}
}