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

@WebServlet("/CustomerViewOrderList")

public class CustomerViewOrderList extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		Utilities utility = new Utilities(request, pw);
		
		HttpSession session = session = request.getSession(true);
		
		
		utility.printHtml("Header.html");
		
	
	
			HashMap<Integer, Order> orders = new HashMap<Integer, Order>();
			
			try {
				Integer uid = utility.getUser().getId();
				orders = MySqlDataStoreUtilities.selectOrderbyCustomer(uid);
	
			} catch (Exception e) {
				e.printStackTrace();
			}
	
			if (orders.isEmpty()) {// no orders
				pw.print("<p>You have No order!</p>");
			} else {// have orders
	
	
				pw.print("<div class=\"table-responsive\">\n"
						+ "        <table class=\"table table-striped table-sm\">\n"
						+ "          <thead>\n"
						+ "            <tr>\n"
						+ "              <th>OrderNumber</th>\n"
						+ "              <th>UserID</th>\n"
						+ "              <th>Total Price</th>\n"
						+ "              <th>Address</th>\n"
						+ "			     <th>Postcode</th>\n"
						+ "              <th>Order Time</th>\n"
						+ "              <th>    </th>\n"
						+ "              <th>    </th>\n"
						+ "            </tr>\n"
						+ "          </thead>\n"
						+ "          <tbody>\n");
				
				for(Map.Entry<Integer,Order> orderE:orders.entrySet())
				{
				    int orderID=orderE.getKey();
				    Order order=orderE.getValue();
				    pw.print(
						  "            <tr>\n"
						+ "              <td>" + orderID + "</td>\n"
						+ "              <td>" + order.getUid() + "</td>\n"
						+ "              <td>" + order.getTotalprice() + "</td>\n"
						+ "              <td>" + order.getAddress() + "</td>\n"
						+ "              <td>" + order.getPostcode() + "</td>\n"
						+ "			     <td>" + order.getOtime() + "</td>\n"
						+ "     		 <td> <form class='needs-validation' action='CustomerViewOrderDetail' method='post'>"
						+ "						<input type='submit' class='btn btn-primary btn-lg btn-block' name='action' value=\"View\">\n"
	      				+ "						<input name='oid' type='hidden' value='"+orderID+"'>"
	      				+ "					  </form> "
	      				+ "				 </td>\n"
	      				+ "     		 <td> <form class='needs-validation' action='CustomerViewOrderList' method='post'>"
						+ "						<input type='submit' class='btn btn-primary btn-lg btn-block' name='action' value=\"Delete\">\n"
	      				+ "						<input name='oid' type='hidden' value='"+orderID+"'>"
	      				+ "					  </form> "
	      				+ "				 </td>\n"
						+ "            </tr>\n");
				}
				pw.print("          </tbody>\n"
						+ "        </table>\n"
						+ "      </div>\n");
				
			
			}
		
		utility.printHtml("Footer.html");

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		Utilities utility = new Utilities(request, pw);
		
		HttpSession session = session = request.getSession(true);
		String action = request.getParameter("action");
		
		utility.printHtml("Header.html");
		
		if(action!= null && action.equals("Delete")) {
			String orderIDs = request.getParameter("oid");
			Integer oid = Integer.parseInt(orderIDs);

			MySqlDataStoreUtilities.deleteOrder(oid);
			MySqlDataStoreUtilities.deleteOrderItem(oid);
			pw.print("<h4>This order has been deleted!</h4>"
					+"<a class='btn btn-outline-primary btn-outline-success' href='CustomerViewOrderList'>Return to check other orders</a>");
		}
		
		utility.printHtml("Footer.html");
		
	}
}
