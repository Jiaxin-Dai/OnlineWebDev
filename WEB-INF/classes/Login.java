import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")

public class Login extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String usertype = request.getParameter("usertype");
		HashMap<String, User> hm = new HashMap<String, User>();

		try {
			hm = MySqlDataStoreUtilities.selectUser();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		User user = hm.get(username);
		if (user != null) {
			String user_password = user.getPassword();
			if (password.equals(user_password)) {
				HttpSession session = request.getSession(true);
				session.setAttribute("username", user.getName());
				session.setAttribute("usertype", user.getUsertype());
				response.sendRedirect("Home");
				return;
			}
		}
		displayLogin(request, response, pw, true);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayLogin(request, response, pw, false);
	}

	/*
	 * Login Screen is Displayed, Registered User specifies credentials and logins
	 * into the Game Speed Application.
	 */
	protected void displayLogin(HttpServletRequest request, HttpServletResponse response, PrintWriter pw, boolean error)
			throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		
		
		
		pw.print("<div class='post' style='float: none; width: 100%'>");
		pw.print("<div class='entry'>"
				+ "<div style='width:400px; margin:25px; margin-left: auto;margin-right: auto;'>");
		if (error)
			pw.print("<h5 style='color:red'>Please check your username, password and user type!</h5>");
		HttpSession session = request.getSession(true);
		if (session.getAttribute("login_msg") != null) {
			pw.print("<h5 style='color:blue'>" + session.getAttribute("login_msg") + "</h5>");
			session.removeAttribute("login_msg");
		}
		/*
		pw.print("<form method='post' action='Login'>" + "<table style='width:100%'><tr><td>"
				+ "<h3>Username</h3></td><td><input type='text' name='username' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>Password</h3></td><td><input type='password' name='password' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"

				+ "<h3>User Type</h3></td><td><select name='usertype' class='input'><option value='customer' selected>Customer</option><option value='manager'>Store Manager</option><option value='salesman'>Salesman</option></select>"

				+ "</td></tr><tr><td></td><td>"
				+ "<input type='submit' class='btnbuy' value='Login' style='float: right;height: 20px margin: 20px; margin-right: 10px;'></input>"
				+ "</td></tr><tr><td></td><td>"
				+ "<strong><a class='' href='Registration' style='float: right;height: 20px margin: 20px;'>New User? Register here!</a></strong>"
				+ "</td></tr></table>" + "</form>" + "</div></div></div>");
		*/
		
		
		
		
		pw.print(" <form class=\"form-signin\" method='post' action='Login'>\r\n"
				+ "  <h1 class=\"h3 mb-3 font-weight-normal\">Please sign in</h1>\r\n"
				+ "  <label for=\"inputName\" class=\"sr-only\">User Name</label>\r\n"
				+ "  <input type=\"text\" name='username' id=\"inputUsername\" class=\"form-control\" placeholder=\"User Name\" required autofocus>\r\n"
				+ "  <label for=\"inputPassword\" class=\"sr-only\">Password</label>\r\n"
				+ "  <input type=\"password\" name='password' id=\"inputPassword\" class=\"form-control\" placeholder=\"Password\" required>\r\n"
				
				+ "  <label for=\"selectType\" class=\"sr-only\">User Type</label>\r\n"
				+ "  <select name='usertype' class='form-control'>"
				+ "  <option value='customer' selected>Customer</option>"
				+ "  <option value='manager'>Store Manager</option>"
				+ "  <option value='salesman'>Salesman</option></select>"

				 
				+ "  <div class=\"checkbox mb-3\">\r\n"
				+ "    <label>\r\n"
				+ "      <input type=\"checkbox\" value=\"remember-me\"> Remember me\r\n"
				+ "    </label>\r\n"
				+ "  </div>\r\n"
				
				+ "  <a class='' href='Registration' >New User? Register here!</a>"
				
				+ "  <button class=\"btn btn-lg btn-primary btn-block\" type=\"submit\">Sign in</button>"
				+ "</div></div></div>");
		utility.printHtml("Footer.html");
	}

}
