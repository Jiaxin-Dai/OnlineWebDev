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

@WebServlet("/SalesmanViewOrderList")

public class SalesmanViewOrderList extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		Utilities utility = new Utilities(request, pw);
		
		HttpSession session =session = request.getSession(true);
		
		String username=utility.username();
		
		HashMap<Integer, Order> orders=new HashMap<Integer, Order>();
		
		try {
			orders=MySqlDataStoreUtilities.selectOrder();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		utility.printHtml("Header.html");
		
		
		if(orders.isEmpty()) {// no orders
			pw.print("<p>There is No order!</p>");
		}else 
		{// have orders
			
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
					+ "     		 <td> <form class='needs-validation' action='SalesmanViewOrderDetail' method='post'>"
					+ "						<input type='submit' class='btn btn-primary btn-lg btn-block' name='action' value=\"View\">\n"
					//+ "					<button class='btn btn-primary btn-lg btn-block' type='submit' name='action'>View</button>"
      				+ "						<input name='oid' type='hidden' value='"+orderID+"'>"
      				+ "					  </form> "
      				+ "				 </td>\n"
					+ "            </tr>\n");
			}
			pw.print("          </tbody>\n"
					+ "        </table>\n"
					+ "      </div>\n");
			
		
//			if(orders!=null) {
//				session.setAttribute("orderlist",orders );
//			}
		
		}
		
	
		utility.printHtml("Footer.html");
	
	}
}


