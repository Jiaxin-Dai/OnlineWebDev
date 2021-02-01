import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.io.*;

@WebServlet("/CustomerViewOrderDetail")

public class CustomerViewOrderDetail extends HttpServlet {

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		Utilities utility = new Utilities(request, pw);

		HttpSession session = request.getSession(true);
		
		String orderIDs = request.getParameter("oid");
		Integer orderID = Integer.parseInt(orderIDs);
		
		utility.printHtml("Header.html");
		displayForm(orderID,utility,pw);
		utility.printHtml("Footer.html");
	
	}
	public void displayForm(Integer orderID,Utilities utility,PrintWriter pw){
		//orders = (HashMap<Integer, Order>) session.getAttribute("orderlist");
				HashMap<Integer, OrderItem> itemsinOrder = new HashMap<Integer, OrderItem>();

				try {
					itemsinOrder = MySqlDataStoreUtilities.selectOrderItembyOrderID(orderID);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			
				pw.print("<div class=\"table-responsive\">\n"
						+ "        <table class=\"table table-striped table-sm\">\n"
						+ "          <thead>\n"
						+ "            <tr>\n"
						+ "              <th>OrderNumber</th>\n"
						+ "              <th>Product ID</th>\n"
						+ "              <th>Price</th>\n"
						+ "              <th>Category</th>\n"
						+ "			     <th>Quantity</th>\n"
						+ "              <th>Service</th>\n"
						+ "              <th>Schedule Time</th>\n"
						+ "            </tr>\n"
						+ "          </thead>\n"
						+ "          <tbody>\n");
					
				for(Map.Entry<Integer,OrderItem> items:itemsinOrder.entrySet())
				{
				    int oid = items.getKey();
				    OrderItem item = items.getValue();
				    
				    pw.print( " <form class=\"needs-validation\" method=\"post\" action=\"SalesmanViewOrderDetail\">\n"
				    		+ "            	<tr>\n"
				    		+ "              	<td>" + oid+ "</td>\n"
				    		+ "              	<td>" + item.getItem_id() + "</td>\n"
				    		+ "              	<td>" + item.getItem_price() + "</td>\n"
				    		+ "              	<td>" + item.getCategory() + "</td>\n"
				    		+ "              	<td>" + item.getQuantity() + "</td>\n"
				    		+ "			  		<td>" + item.getSid() + "</td>\n"
				    		+ "            		<td>" + utility.mapIntToTime(item.getScheduletime()) + "</td>\n"
				    		+ "				</tr>\n"
				    		+ "	</form>");
				    		
				}
				pw.print("          </tbody>\n"
						+ "        </table>\n"
						+"		   <a class='btn btn-outline-primary btn-outline-success' href='SalesmanViewOrderList'>Return to check other orders</a>"
						+ "      </div>\n");
		
	}
}


