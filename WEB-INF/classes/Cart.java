import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.sql.Date;
import java.text.SimpleDateFormat;


@WebServlet("/Cart")

public class Cart extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		/*
		 * From the HttpServletRequest variable name,type,maker and acessories
		 * information are obtained.
		 */

		Utilities utility = new Utilities(request, pw);
		String productType = null;
		if (request.getParameter("productType") != null) {
			productType = request.getParameter("productType");
			if (productType.equals("service")) {
				String seridString = request.getParameter("serid");
				String sidString = request.getParameter("sid");
				String type = request.getParameter("type");
				int sid = Integer.parseInt(sidString);
				int serid = Integer.parseInt(seridString);
				String comboString = request.getParameter("time");
				String[] parsedString = comboString.split("\\s+");
				String dateString = parsedString[0];
				String timeString = parsedString[1];
				int time = utility.mapTimeToInt(timeString);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				java.sql.Date date = null;
				try {
					java.util.Date d = format.parse(dateString);
					date = new java.sql.Date(d.getTime());
				} catch (Exception e) {
					//TODO: handle exception
					System.out.println(e);
				}
				Schedule sch = new Schedule(sid, time, date);
				ArrayList<Schedule> list = new ArrayList<>();
				if (SchedulesHashMap.schedules.containsKey(utility.username())) {
					list = SchedulesHashMap.schedules.get(utility.username());
				}
				list.add(sch);
				SchedulesHashMap.schedules.put(utility.username(), list);
				utility.storeProduct(sidString,serid, productType, time);
			}
		} else {
			String name = request.getParameter("name");
			String idString = request.getParameter("id");
			String type = request.getParameter("type");
			int id = Integer.parseInt(idString);
			/* StoreProduct Function stores the Purchased product in Orders HashMap. */
			utility.storeProduct(name, id, "product", -1);
		}
		displayCart(request, response);
	}

	/*
	 * displayCart Function shows the products that users has bought, these products
	 * will be displayed with Total Amount.
	 */

	protected void displayCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		Recommendation recomm = new Recommendation();
		
		if (!utility.isLoggedin()) {
			HttpSession session = request.getSession(true);
			session.setAttribute("login_msg", "Please Login to add items to cart");
			response.sendRedirect("Login");
			return;
		}

		utility.printHtml("Header.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Cart(" + utility.CartCount() + ")</a>");
		pw.print("</h2><div class='entry'>");
		HashMap<Integer, Product> products = new HashMap<>();
		try {
			products = MySqlDataStoreUtilities.selectAllProducts();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO: change hashmap based on id
		HashMap<Integer, Service> services = new HashMap<>();
		try {
			services = MySqlDataStoreUtilities.selectService();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (utility.CartCount() > 0) {
			pw.print("<form name ='Cart' action='CheckOut' method='get'>");
			pw.print("<table  class='table'>");
			int i = 1;
			int quant = 0;
			double total = 0;
			for (OrderItem oi : utility.getCustomerOrders()) {
				int index = utility.getCustomerOrders().indexOf(oi);
				System.out.println(utility.getCustomerOrders().size());
				pw.print("<tr>");
				int id = oi.getItem_id();
				System.out.println(oi.getItem_id());
				if (id > 50) {
					pw.print("<td scope='row'>"+i+"</td><td>" + services.get(oi.getItem_id()).getServiceName() + "</td><td>: " + oi.getItem_price()
					+ "</td><td>"+oi.getQuantity()+"<td><td><A href='Cart?id=" + index + "' class=\\\"btn btn-sm btn-outline-secondary\\\">remove</A></td>");			
				} else {
					pw.print("<td scope='row'>"+i+"</td><td>" + products.get(oi.getItem_id()).getProductName() + "</td><td>: " + oi.getItem_price()
					+ "</td><td>"+oi.getQuantity()+"<td><td><A href='Cart?id=" + index + "' class=\\\"btn btn-sm btn-outline-secondary\\\">remove</A></td>");			
				}
				pw.print("<input type='hidden' name='orderName' value='" + oi.getOid() + "'>");
				pw.print("<input type='hidden' name='orderPrice' value='" + oi.getItem_price() * oi.getQuantity() + "'>");
				pw.print("</tr>");
				total = total + oi.getItem_price() * oi.getQuantity();
				i++;
				quant += oi.getQuantity();
			}
			pw.print("<input type='hidden' name='orderTotal' value='" + total + "'>");
			pw.print("<tr><th></th><th>Total</th><th>" + total + "</th>");
			pw.print(
					"<tr><td></td><td></td><td><input type='submit' name='CheckOut' value='CheckOut' class='btnbuy' /></td>");
			pw.print("</table></form>");
			/* This code is calling Carousel.java code to implement carousel feature */
		} else {
			pw.print("<h4 style='color:red'>Your Cart is empty</h4>");
		}
		
		pw.print(recomm.Recommendation(utility));
		
		pw.print("</div></div></div>");
		utility.printHtml("Footer.html");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);

		String idString = request.getParameter("id");
		boolean valid = (idString != null);
		int id = -1;
		if (valid) {
			id = Integer.parseInt(idString);
		}
		if (id != -1) {
			utility.removeProduct(id);
		}
		displayCart(request, response);
	}
}
