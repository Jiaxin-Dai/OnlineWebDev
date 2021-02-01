import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet("/Registration")

public class Registration extends HttpServlet {
	private String error_msg;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayRegistration(request, response, pw, false);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String repassword = request.getParameter("repassword");
		String usertype = request.getParameter("usertype");
		String firstname = request.getParameter("firstName");
		String lastname = request.getParameter("lastName");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String zip = request.getParameter("zip");
		

		
		// if password and repassword does not match show error message

		if (!password.equals(repassword)) {
			error_msg = "Passwords doesn't match!";
		} else {
			HashMap<String, User> hm = new HashMap<String, User>();

			try {

				hm = MySqlDataStoreUtilities.selectUser();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// if the user already exist show error that already exist

			if (hm.containsKey(username))
				error_msg = "Username already exist as " + usertype;
			else {
				User user = new User(username,password,usertype,firstname,lastname, address,city,state,zip);
				hm.put(username, user);

				MySqlDataStoreUtilities.insertUserRegister(username, password, usertype,firstname,lastname, address,city,state,zip);

				HttpSession session = request.getSession(true);
				session.setAttribute("login_msg", "Your " + usertype + " account has been created. Please login");
				if (!utility.isLoggedin()) {
					response.sendRedirect("Login");
					return;
				}
			}
		}

		displayRegistration(request, response, pw, true);

	}

	/* displayRegistration function displays the Registration page of New User */

	protected void displayRegistration(HttpServletRequest request, HttpServletResponse response, PrintWriter pw,
			boolean error) throws ServletException, IOException {
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");

		
		if (error)
			pw.print("<h5 style='color:red'>" + error_msg + "</h5>");
		/*pw.print("<form method='post' action='Registration'>" + "<table style='width:100%'><tr><td>"
				+ "<h3>Username</h3></td><td><input type='text' name='username' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>Password</h3></td><td><input type='password' name='password' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>Re-Password</h3></td><td><input type='password' name='repassword' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>User Type</h3></td><td><select name='usertype' class='input'><option value='customer' selected>Customer</option><option value='manager'>Store Manager</option><option value='salesman'>Salesman</option></select>"
				+ "</td></tr></table>"
				+ "<input type='submit' class='btnbuy' name='ByUser' value='Create User' style='float: right;height: 20px margin: 20px; margin-right: 10px;'></input>"
				+ "</form>" + "</div></div></div>");*/
		pw.print("<div class=\"col-md-8 order-md-1\" style='margin:25px; margin-left: auto;margin-right: auto;'>\r\n"
				+ "      \r\n"
				+ "      <form class=\"needs-validation\" method='post' action='Registration'>\r\n"
				+ "        <div class=\"row\">\r\n"
				+ "          <div class=\"col-md-6 mb-3\">\r\n"
				+ "            <label for=\"firstName\">First name</label>\r\n"
				+ "            <input type=\"text\" class=\"form-control\" name=\"firstName\" placeholder=\"firstName\" value=\"\" required>\r\n"
				+ "            <div class=\"invalid-feedback\">\r\n"
				+ "              Valid first name is required.\r\n"
				+ "            </div>\r\n"
				+ "          </div>\r\n"
				+ "          <div class=\"col-md-6 mb-3\">\r\n"
				+ "            <label for=\"lastName\">Last name</label>\r\n"
				+ "            <input type=\"text\" class=\"form-control\" name=\"lastName\" placeholder=\"lastName\" value=\"\" required>\r\n"
				+ "            <div class=\"invalid-feedback\">\r\n"
				+ "              Valid last name is required.\r\n"
				+ "            </div>\r\n"
				+ "          </div>\r\n"
				+ "        </div>\r\n"
				+ "\r\n"
				+ "        <div class=\"mb-3\">\r\n"
				+ "			 <label for=\"usertype\">User Type</label>\r\n"
				+ "            	<select class=\"custom-select d-block w-100\" name=\"usertype\" required>\r\n"
				+ "              <option value=\"\">Choose...</option>\r\n"
				+ "              <option>customer</option>\r\n"
				+ "              <option>manager</option>\r\n"
				+ "              <option>salesman</option>\r\n"
				+ "            	</select>\r\n"
				+ "            	<div class=\"invalid-feedback\">\r\n"
				+ "              Please provide a valid type.\r\n"
				+ "            	</div>"
				+ "        </div>\r\n"
				+ "\r\n"
				+ "        <div class=\"mb-3\">\r\n"
				+ "          <label for=\"username\">Username</label>\r\n"
				+ "          <div class=\"input-group\">\r\n"
				+ "            <input type=\"text\" class=\"form-control\" name=\"username\" placeholder=\"Username\" required>\r\n"
				+ "            <div class=\"invalid-feedback\" style=\"width: 100%;\">\r\n"
				+ "              Your username is required.\r\n"
				+ "            </div>\r\n"
				+ "          </div>\r\n"
				+ "        </div>\r\n"
				+ "\r\n"
				+ "		  <div class=\"mb-3\">\r\n"
				+ "          <label for=\"password\">Password</label>\r\n"
				+ "          <div class=\"input-group\">\r\n"
				+ "            <input type=\"password\" class=\"form-control\" name=\"password\" placeholder=\"Password\" required>\r\n"
				+ "            <div class=\"invalid-feedback\" style=\"width: 100%;\">\r\n"
				+ "              Your password is required.\r\n"
				+ "            </div>\r\n"
				+ "          </div>\r\n"
				+ "        </div>\r\n"
				+ "		  <div class=\"mb-3\">\r\n"
				+ "          <label for=\"username\">Repeat password</label>\r\n"
				+ "          <div class=\"input-group\">\r\n"
				+ "            <input type=\"password\" class=\"form-control\" name=\"repassword\" placeholder=\"Repeat password\" required>\r\n"
				+ "            <div class=\"invalid-feedback\" style=\"width: 100%;\">\r\n"
				+ "              Your Repeat password is required.\r\n"
				+ "            </div>\r\n"
				+ "          </div>\r\n"
				+ "        </div>\r\n"
				+ "        <div class=\"mb-3\">\r\n"
				+ "          <label for=\"address\">Address</label>\r\n"
				+ "          <input type=\"text\" class=\"form-control\" name=\"address\" placeholder=\"1234 Main St\" required>\r\n"
				+ "          <div class=\"invalid-feedback\">\r\n"
				+ "            Please enter your  address.\r\n"
				+ "          </div>\r\n"
				+ "        </div>\r\n"
				
				+ "        <div class=\"row\">\r\n"
				+ "			 <div class=\"col-md-4 mb-3\">\r\n"
				+ "            <label for=\"state\">City</label>\r\n"
				+ "            <input type=\"text\" class=\"form-control\" name=\"city\" placeholder=\"city\"  required>\r\n"
				+ "            <div class=\"invalid-feedback\">\r\n"
				+ "              Please provide a valid city.\r\n"
				+ "            </div>\r\n"
				+ "          </div>"
				+ "          <div class=\"col-md-4 mb-3\">\r\n"
				+ "            <label for=\"state\">State</label>\r\n"
				+ "            <input type=\"text\" class=\"form-control\" name=\"state\" placeholder=\"state\"  required>\r\n"
				+ "            <div class=\"invalid-feedback\">\r\n"
				+ "              Please provide a valid state.\r\n"
				+ "            </div>\r\n"
				+ "          </div>\r\n"
				+ "          <div class=\"col-md-3 mb-3\">\r\n"
				+ "            <label for=\"zip\">Zip</label>\r\n"
				+ "            <input type=\"text\" class=\"form-control\" name=\"zip\" placeholder=\"postcode\" required>\r\n"
				+ "            <div class=\"invalid-feedback\">\r\n"
				+ "              Zip code required.\r\n"
				+ "            </div>\r\n"
				+ "          </div>\r\n"
				+ "        </div>\r\n"
				+ "        <hr class=\"mb-4\">\r\n"
				+ "        <button class=\"btn btn-primary btn-lg btn-block\" type=\"submit\">Register</button>\r\n"
				+ "      </form>\r\n"
				+ "    </div>\r\n"
				+ "  </div>");
		
		utility.printHtml("Footer.html");
	}
}