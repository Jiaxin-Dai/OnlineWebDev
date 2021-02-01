import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.io.*;
import java.sql.*;

@WebServlet("/Account")

public class Account extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayAccount(request, response);
	}


	protected void displayAccount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		try {
			response.setContentType("text/html");
			
			
			
			HttpSession session = request.getSession();
			utility.printHtml("Header.html");

			User user = utility.getUser();
			
			pw.print( " <div class=\"col-md-8 order-md-1\" style='margin:25px; margin-left: auto;margin-right: auto;'>\r\n"
					+ "      \r\n"
					+ "      <form class=\"needs-validation\" method='post' action='Account'>\r\n"
					+ "        <div class=\"row\">\r\n"
					+ "          <div class=\"col-md-6 mb-3\">\r\n"
					+ "            <label for=\"firstName\">First name</label>\r\n"
					+ "            <input type=\"text\" class=\"form-control\" name=\"firstName\" value=\"" + user.getFirstname() + "\" required>\r\n"
					+ "          </div>\r\n"
					+ "\r\n"
					+ "          <div class=\"col-md-6 mb-3\">\r\n"
					+ "            <label for=\"lastName\">Last name</label>\r\n"
					+ "            <input type=\"text\" class=\"form-control\" name=\"lastName\" value=\"" + user.getLastname()+"\" required>\r\n"
					+ "          </div>\r\n"
					+ "        </div>\r\n"
					+ "\r\n"
					+ "        <div class=\"mb-3\">\r\n"
					+ "			 <label for=\"usertype\">User Type</label>\r\n"
					+ "          <input type=\"text\" class=\"form-control\" name=\"usertype\" value=\"" + user.getUsertype()+"\" readonly=\"true\" required >\r\n"
					+ "        </div>\r\n"
					+ "\r\n"
					+ "        <div class=\"mb-3\">\r\n"
					+ "          <label for=\"username\">Username</label>\r\n"
					+ "          <div class=\"input-group\">\r\n"
					+ "            <input type=\"text\" class=\"form-control\" name=\"username\" value=\"" + user.getName() +"\" required>\r\n"
					+ "          </div>\r\n"
					+ "        </div>\r\n"
					+ "\r\n"
					+ "		  <div class=\"mb-3\">\r\n"
					+ "          <label for=\"password\">Password</label>\r\n"
					+ "          <div class=\"input-group\">\r\n"
					+ "            <input type=\"text\" class=\"form-control\" name=\"password\" value=\""+user.getPassword()+"\" required>\r\n"
					+ "          </div>\r\n"
					+ "        </div>\r\n"
					+ "\r\n"
					+ "        <div class=\"mb-3\">\r\n"
					+ "          <label for=\"address\">Address</label>\r\n"
					+ "          <input type=\"text\" class=\"form-control\" name=\"address\" value=\""+user.getAddress()+"\" required>\r\n"
					+ "        </div>\r\n"
					+ "\r\n"
					+ "        <div class=\"row\">\r\n"
					+ "			 <div class=\"col-md-4 mb-3\">\r\n"
					+ "            <label for=\"state\">City</label>\r\n"
					+ "            <input type=\"text\" class=\"form-control\" name=\"city\" value=\""+user.getCity()+"\"  required>\r\n"
					+ "          </div>"
					+ "          <div class=\"col-md-4 mb-3\">\r\n"
					+ "            <label for=\"state\">State</label>\r\n"
					+ "            <input type=\"text\" class=\"form-control\" name=\"state\" value=\""+user.getState()+"\"  required>\r\n"
					+ "          </div>\r\n"
					+ "          <div class=\"col-md-3 mb-3\">\r\n"
					+ "            <label for=\"zip\">Zip</label>\r\n"
					+ "            <input type=\"text\" class=\"form-control\" name=\"zip\" value=\""+user.getZip()+"\" required>\r\n"
					+ "          </div>\r\n"
					+ "        </div>\r\n"
					+ "        <hr class=\"mb-4\">\r\n"
					+ "		   <input type='submit' class='btn btn-primary btn-lg btn-block' name='action' value='Save change'>\r\n"
					+ "      </form>\r\n"
					+ "    </div>\r\n"
					+ "  </div>");

			

			pw.print("</h2></div></div></div>");
			utility.printHtml("Footer.html");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		User u = utility.getUser();
		String action = request.getParameter("action");
		if(action!=null && action.equals("Save change") ) {
			User newU = new User(u.getId(),
					request.getParameter("username"),
					request.getParameter("password"),
					request.getParameter("usertype"),
					request.getParameter("firstName"),
					request.getParameter("lastName"),
					request.getParameter("address"),
					request.getParameter("city"),
					request.getParameter("state"),
					request.getParameter("zip"));
			MySqlDataStoreUtilities.updateUser(newU);
		}
		displayAccount(request, response);
	}
}
