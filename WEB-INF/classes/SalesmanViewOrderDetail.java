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

@WebServlet("/SalesmanViewOrderDetail")

public class SalesmanViewOrderDetail extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		Utilities utility = new Utilities(request, pw);

		HttpSession session = request.getSession(true);

		String action = request.getParameter("action");
		
		String orderIDs = request.getParameter("oid");
		Integer orderID = Integer.parseInt(orderIDs);
		
		utility.printHtml("Header.html");
		
		if(action.equals("submit")) {
			OrderItem i = new OrderItem(orderID,
										Integer.parseInt(request.getParameter("item_id")),
										Double.parseDouble(request.getParameter("item_price")),
										request.getParameter("category"),
										Integer.parseInt(request.getParameter("quantity")),
										Integer.parseInt(request.getParameter("sid")),
										Integer.parseInt(request.getParameter("scheduletime"))  );
			MySqlDataStoreUtilities.updateOrderedItem(i);
			displayForm(orderID,utility,pw);
		}
		else if(action.equals("delete")) {
			
			MySqlDataStoreUtilities.deleteOrderItem(orderID, 
													Integer.parseInt(request.getParameter("item_id")));
			HashMap<Integer, OrderItem> items = MySqlDataStoreUtilities.selectOrderItembyOrderID(orderID);
			if(items.size() == 0) {
				MySqlDataStoreUtilities.deleteOrder(orderID);
				pw.print("<h4>This order has been deleted!</h4>"
						+"<a class='btn btn-outline-primary btn-outline-success' href='SalesmanViewOrderList'>Return to check other orders</a>");
			
			}
			else
				displayForm(orderID,utility,pw);
		}else if(action.equals("View")) {
			displayForm(orderID,utility,pw);
		}
		
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
						+ "              <th>Edit</th>\n"
						+ "              <th>Delete</th>\n"
						+ "            </tr>\n"
						+ "          </thead>\n"
						+ "          <tbody>\n");
					
				for(Map.Entry<Integer,OrderItem> items:itemsinOrder.entrySet())
				{
				    int oid = items.getKey();
				    OrderItem item = items.getValue();
				    
				    pw.print( " <form class=\"needs-validation\" method=\"post\" action=\"SalesmanViewOrderDetail\">\n"
				    		+ "            	<tr>\n"
				    		+ "              	<td><input type=\"text\" class=\"form-control\" name=\"oid\" value=\"" + oid+ "\" required></td>\n"
				    		+ "              	<td><input type=\"text\" class=\"form-control\" name=\"item_id\" value=\"" + item.getItem_id() + "\" required></td>\n"
				    		+ "              	<td><input type=\"text\" class=\"form-control\" name=\"item_price\" value=\"" + item.getItem_price() + "\"  required></td>\n"
				    		+ "              	<td><input type=\"text\" class=\"form-control\" name=\"category\" value=\"" + item.getCategory() + "\" required></td>\n"
				    		+ "              	<td><input type=\"text\" class=\"form-control\" name=\"quantity\" value=\"" + item.getQuantity() + "\"  required></td>\n"
				    		+ "			  		<td><input type=\"text\" class=\"form-control\" name=\"sid\" value=\"" + item.getSid() + "\"  required></td>\n"
				    		+ "            		<td><input type=\"text\" class=\"form-control\" name=\"scheduletime\" value=\"" + item.getScheduletime() + "\"  required></td>\n"
				    		+ "					<td><input type='submit' class='btn btn-primary btn-lg btn-block' name='action' value=\"submit\"></td>\n"
				    		+ "					<td><input type='submit' class='btn btn-primary btn-lg btn-block' name='action' value=\"delete\"></td>\n"
				    		+ "				</tr>\n"
				    		+ "	</form>");
				    		
				}
				pw.print("          </tbody>\n"
						+ "        </table>\n"
						+ "      </div>\n");
		
	}
}
