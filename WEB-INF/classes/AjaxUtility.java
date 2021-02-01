import java.io.*;

import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.util.*;
import java.text.*;

import java.sql.*;

import java.io.IOException;
import java.io.*;

public class AjaxUtility {
	StringBuffer sb = new StringBuffer();
	boolean namesAdded = false;
	static Connection conn = null;
	static String message;

	public static void getConnection() {

		String pwd = "root";
//		String pwd = "12345678";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/homehub?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", pwd);
		} catch (Exception e) {
			System.out.println(e);
		}
	}


	public StringBuffer readdata(String searchId) {
		HashMap<Integer, Product> data;
		data = getData();
		Iterator it = data.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pi = (Map.Entry) it.next();
			if (pi != null) {
				Product p = (Product) pi.getValue();
				if (p.getProductName().toLowerCase().startsWith(searchId)) {
					sb.append("<product>");
					sb.append("<id>" + p.getId() + "</id>");
					sb.append("<productName>" + p.getProductName() + "</productName>");
					sb.append("</product>");
				}
			}
		}
		return sb;
	}

	public static HashMap<Integer, Product> getData() {
        HashMap<Integer, Product> hm = new HashMap<Integer, Product>();
        try {
            hm = MySqlDataStoreUtilities.selectAllProducts();
        } catch(Exception e) {
            System.out.println(e);
        }
		return hm;
	}

	// public static void storeData(HashMap<String, Product> productdata) {
	// 	try {

	// 		getConnection();

	// 		String insertIntoProductQuery = "INSERT INTO product(productId,productName,image,retailer,productCondition,productPrice,productType,discount) "
	// 				+ "VALUES (?,?,?,?,?,?,?,?);";
	// 		for (Map.Entry<String, Product> entry : productdata.entrySet()) {

	// 			PreparedStatement pst = conn.prepareStatement(insertIntoProductQuery);
	// 			// set the parameter for each column and execute the prepared statement
	// 			pst.setString(1, entry.getValue().getId());
	// 			pst.setString(2, entry.getValue().getName());
	// 			pst.setString(3, entry.getValue().getImage());
	// 			pst.setString(4, entry.getValue().getRetailer());
	// 			pst.setString(5, entry.getValue().getCondition());
	// 			pst.setDouble(6, entry.getValue().getPrice());
	// 			pst.setString(7, entry.getValue().getType());
	// 			pst.setDouble(8, entry.getValue().getDiscount());
	// 			pst.execute();
	// 		}
	// 	} catch (Exception e) {

	// 	}
	// }

}
