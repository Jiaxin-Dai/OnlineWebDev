import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.sql.Date;

@WebServlet("/CheckOut")

// once the user clicks buy now button page is redirected to checkout page where
// user has to give checkout information

public class CheckOut extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities Utility = new Utilities(request, pw);
		storeOrdersInSql(request, response);
	}

	protected void storeOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try
        {
        response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request,pw);
        
        User u = utility.getUser();
		if(!utility.isLoggedin())
		{
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to add items to cart");
			response.sendRedirect("Login");
			return;
		}
        HttpSession session=request.getSession(); 
	    String userName = session.getAttribute("username").toString();
        String orderTotal = request.getParameter("orderTotal");
        
        ArrayList<OrderItem> list = OrdersHashMap.orders.get(userName);
        HashMap<Integer, Product> hm = MySqlDataStoreUtilities.selectAllProducts();
        HashMap<Integer, Service> services = MySqlDataStoreUtilities.selectService();
		utility.printHtml("Header.html");
		pw.print(
			 "    <div class=\"container\">\n"
				+ "  <div class=\"py-5 text-center\">\n"
				+ "    <h2>Checkout form</h2>\n"
				+ "    <p class=\"lead\">Fill out your information and we'll make sure to make your purchase a good experience.</p>\n"
				+ "  </div>\n"
				+ "\n"
				+ "  <div class=\"row\">\n"
				+ "    <div class=\"col-md-4 order-md-2 mb-4\">\n"
				+ "      <h4 class=\"d-flex justify-content-between align-items-center mb-3\">\n"
				+ "        <span class=\"text-muted\">Your cart</span>\n"
				+ "        <span class=\"badge badge-secondary badge-pill\">"+ utility.CartCount() +"</span>\n"
				+ "      </h4>\n"
				+ "      <ul class=\"list-group mb-3\">\n");
				for (OrderItem oi: list) {
					if (oi.getItem_id() > 50) {

						Service s = services.get(oi.getItem_id());
						pw.print("      <li class=\"list-group-item d-flex justify-content-between lh-condensed\">\n"
								+ "          <div>\n"
								+ "            <h6 class=\"my-0\">"+s.getServiceName()+"</h6>\n"
								+ "            <small class=\"text-muted\">"+s.getDescription()+"</small>\n"
								+ "          </div>\n"
								+ "          <span class=\"text-muted\">"+s.getPrice()+"</span>\n"
								+ "        </li>\n");
					} else {
						Product p = hm.get(oi.getItem_id());
						pw.print("      <li class=\"list-group-item d-flex justify-content-between lh-condensed\">\n"
								+ "          <div>\n"
								+ "            <h6 class=\"my-0\">"+p.getProductName()+"</h6>\n"
								+ "            <small class=\"text-muted\">"+p.getDesc()+"</small>\n"
								+ "          </div>\n"
								+ "          <span class=\"text-muted\">"+p.getPrice()+"</span>\n"
								+ "        </li>\n");
					}
				}
//				pw.print("        <li class=\"list-group-item d-flex justify-content-between lh-condensed\">\n"
//				+ "          <div>\n"
//				+ "            <h6 class=\"my-0\">Product name</h6>\n"
//				+ "            <small class=\"text-muted\">Brief description</small>\n"
//				+ "          </div>\n"
//				+ "          <span class=\"text-muted\">$12</span>\n"
//				+ "        </li>\n"
//				+ "        <li class=\"list-group-item d-flex justify-content-between lh-condensed\">\n"
//				+ "          <div>\n"
//				+ "            <h6 class=\"my-0\">Second product</h6>\n"
//				+ "            <small class=\"text-muted\">Brief description</small>\n"
//				+ "          </div>\n"
//				+ "          <span class=\"text-muted\">$8</span>\n"
//				+ "        </li>\n"

				pw.print("        <li class=\"list-group-item d-flex justify-content-between\">\n"
				+ "          <span>Total (USD)</span>\n"
				+ "          <strong>$"+ orderTotal +"</strong>\n"
				+ "        </li>\n"
				+ "      </ul>\n"
				+ "\n"
				+ "    </div>\n"
				+ "    <div class=\"col-md-8 order-md-1\">\n"
				+ "      <h4 class=\"mb-3\">Shipping Address</h4>\n"
				+ "      <form method='POST' action='CheckOut' class=\"needs-validation\" novalidate>\n"
				+ "<input type='hidden' name='orderTotal' value="+orderTotal+">"
				
				+ "        <div class=\"mb-3\">\n"
				+ "          <label for=\"address\">Address</label>\n"
				+ "          <input type=\"text\" name='address' class=\"form-control\" id=\"address\" value=\""+ u.getAddress() +"\" required>\n"
				+ "          <div class=\"invalid-feedback\">\n"
				+ "            Please enter your shipping address.\n"
				+ "          </div>\n"
				+ "        </div>\n"
				+ "        <div class=\"row\">\n"
				
				+ "          <div class=\"col-md-4 mb-3\">\r\n"
				+ "            <label for=\"state\">State</label>\r\n"
				+ "            <input type=\"text\" class=\"form-control\" name=\"state\" value=\""+u.getState()+"\"  required>\r\n"
				+ "            <div class=\"invalid-feedback\">\r\n"
				+ "              Please provide a valid state.\r\n"
				+ "            </div>\r\n"
				+ "          </div>\r\n"
				
				+ "          <div class=\"col-md-3 mb-3\">\n"
				+ "            <label for=\"zip\">Zip</label>\n"
				+ "            <input name='postcode' type=\"text\" class=\"form-control\" id=\"zip\" value=\""+u.getZip()+"\" required>\n"
				+ "            <div class=\"invalid-feedback\">\n"
				+ "              Zip code required.\n"
				+ "            </div>\n"
				+ "          </div>\n"
				+ "        </div>\n"
				+ "        <h4 class=\"mb-3\">Payment</h4>\n"
				+ "\n"
				+ "        <div class=\"d-block my-3\">\n"
				+ "          <div class=\"custom-control custom-radio\">\n"
				+ "            <input id=\"credit\" name=\"paymentMethod\" type=\"radio\" class=\"custom-control-input\" checked required>\n"
				+ "            <label class=\"custom-control-label\" for=\"credit\">Credit card</label>\n"
				+ "          </div>\n"
				+ "          <div class=\"custom-control custom-radio\">\n"
				+ "            <input id=\"debit\" name=\"paymentMethod\" type=\"radio\" class=\"custom-control-input\" required>\n"
				+ "            <label class=\"custom-control-label\" for=\"debit\">Debit card</label>\n"
				+ "          </div>\n"
				+ "        </div>\n"
				+ "        <div class=\"row\">\n"
				+ "          <div class=\"col-md-6 mb-3\">\n"
				+ "            <label for=\"cc-name\">Name on card</label>\n"
				+ "            <input type=\"text\" class=\"form-control\" id=\"cc-name\" placeholder=\"\" required>\n"
				+ "            <small class=\"text-muted\">Full name as displayed on card</small>\n"
				+ "            <div class=\"invalid-feedback\">\n"
				+ "              Name on card is required\n"
				+ "            </div>\n"
				+ "          </div>\n"
				+ "          <div class=\"col-md-6 mb-3\">\n"
				+ "            <label for=\"cc-number\">Credit card number</label>\n"
				+ "            <input name='creditcard' type=\"text\" class=\"form-control\" id=\"cc-number\" placeholder=\"\" required>\n"
				+ "            <div class=\"invalid-feedback\">\n"
				+ "              Credit card number is required\n"
				+ "            </div>\n"
				+ "          </div>\n"
				+ "        </div>\n"
				+ "        <hr class=\"mb-4\">\n"
				+ "        <button class=\"btn btn-primary btn-lg btn-block\" type=\"submit\">Continue to checkout</button>\n"
				+ "      </form>\n"
				+ "    </div></div>\n");
		utility.printHtml("Footer.html");
	    }
        catch(Exception e)
		{
         System.out.println(e.getMessage());
		}  			
		}

	protected void storeOrdersInSql(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try
        {
	    	response.setContentType("text/html");
	    	PrintWriter pw = response.getWriter();
	    	Utilities utility = new Utilities(request,pw);
        
		if(!utility.isLoggedin())
		{
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to add items to cart");
			response.sendRedirect("Login");
			return;
		}
        HttpSession session=request.getSession(); 
	    String userName = session.getAttribute("username").toString();
        String orderTotalString = request.getParameter("orderTotal");
        String creditCard = request.getParameter("creditcard");
        String address = request.getParameter("address");
        String postcode = request.getParameter("postcode");
        Double orderTotal = 0.0;
        if (orderTotal != null) {
        	orderTotal = Double.parseDouble(orderTotalString);
        }
        
		ArrayList<OrderItem> list = OrdersHashMap.orders.get(userName);
		ArrayList<Schedule> sList = SchedulesHashMap.schedules.get(userName);
        HashMap<Integer, Product> hm = MySqlDataStoreUtilities.selectAllProducts();
        Date current = new Date(System.currentTimeMillis());
        User currentUser = MySqlDataStoreUtilities.selectUser().get(userName);
        Order order = new Order(-1, current, orderTotal, creditCard, address, postcode, currentUser.getId());
        int oid = MySqlDataStoreUtilities.insertOrder(order);
        for (OrderItem oi: list) { 
        	oi.setOid(oid);
        	MySqlDataStoreUtilities.insertOrderItem(oi);	
		}
		if (sList != null && sList.size() > 0) {
			for (Schedule schedule: sList) {
				try {
					MySqlDataStoreUtilities.insertSchedule(schedule);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			SchedulesHashMap.schedules.remove(currentUser.getName());
		}
		OrdersHashMap.orders.remove(currentUser.getName());
		utility.printHtml("Header.html");
		pw.print("<h1 color='red'>Your order has been processed.</h1>");
		utility.printHtml("Footer.html");
	    }
        catch(Exception e)
		{
         System.out.println(e.getMessage());
		}  			
		}

	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		storeOrders(request, response);
	}
}
