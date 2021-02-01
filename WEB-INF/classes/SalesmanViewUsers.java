
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

@WebServlet("/SalesmanViewUsers")

public class SalesmanViewUsers extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		Utilities utility = new Utilities(request, pw);
		
		HttpSession session = request.getSession(true);
		
		
		displayForm(request,response);
		
	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		Utilities utility = new Utilities(request, pw);
		HttpSession session = request.getSession(true);
		String action = request.getParameter("action");
		if(action!=null && action.equals("submit")) {
			MySqlDataStoreUtilities.updatePartofUser(Integer.parseInt(request.getParameter("uid")),
													 request.getParameter("uname"),
													 request.getParameter("usertype"),
													 request.getParameter("firstname"),
													 request.getParameter("lastname"));
		}else if(action!=null && action.equals("delete")) {
			MySqlDataStoreUtilities.deleteUser(Integer.parseInt(request.getParameter("uid")));
		}else if(action!=null && action.equals("add")) {
			User user = new User( Integer.parseInt(request.getParameter("uid")),
								 request.getParameter("uname"),
								 "pwd",
								 request.getParameter("usertype"),
								 request.getParameter("firstname"),
								 request.getParameter("lastname"),
								 "unknow",
								 "unknow",
								 "unknow",
								 "unknow" );
			MySqlDataStoreUtilities.insertUser(user);
		}
		
		displayForm(request,response);
	}
	
	public  void displayForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter pw = response.getWriter();

		Utilities utility = new Utilities(request, pw);

		utility.printHtml("Header.html");
		
		HashMap<String, User> users = new HashMap<String, User>();
		
		try {
			users=MySqlDataStoreUtilities.selectUser();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		pw.print("<div class=\"table-responsive\">\n"
				+ "        <table class=\"table table-striped table-sm\">\n"
				+ "          <thead>\n"
				+ "            <tr>\n"
				+ "              <th>User ID</th>\n"
				+ "              <th>Username</th>\n"
				+ "              <th>User Type</th>\n"
				+ "              <th>First Name</th>\n"
				+ "			     <th>Last Name</th>\n"
				+ "              <th>    </th>\n"
				+ "            </tr>\n"
				+ "          </thead>\n"
				+ "          <tbody>\n");
		
		for(Map.Entry<String, User> umap:users.entrySet())
		{
			String uname = umap.getKey();
			User u = umap.getValue();
		    pw.print(" <form class=\"needs-validation\" method=\"post\" action=\"SalesmanViewUsers\">\n"
				+ "            <tr>\n"
				+ "              <td><input type='text' class='form-control' name='uid' value='" + u.getId() + "' required></td>\n"
				+ "              <td><input type='text' class='form-control' name='uname' value='" + uname + "' required</td>\n"
				+ "              <td><input type='text' class='form-control' name='usertype' value='" + u.getUsertype() + "' required</td>\n"
				+ "              <td><input type='text' class='form-control' name='firstname' value='" + u.getFirstname() + "' required</td>\n"
				+ "              <td><input type='text' class='form-control' name='lastname' value='" + u.getLastname() + "' required</td>\n"
				+ "				 <td><input type='submit' class='btn btn-primary btn-lg btn-block' name='action' value=\"submit\"></td>\n"
				+ "				 <td><input type='submit' class='btn btn-primary btn-lg btn-block' name='action' value=\"delete\"></td>\n"
	    		+ "				</tr>\n"
	    		+ "	</form>");
		}
		pw.print(" <form class=\"needs-validation\" method=\"post\" action=\"SalesmanViewUsers\">\n"
					+ "            <tr>\n"
					+ "              <td><input type='text' class='form-control' name='uid' placeholder='UserID' required></td>\n"
					+ "              <td><input type='text' class='form-control' name='uname' placeholder='username' required</td>\n"
					+ "              <td><input type='text' class='form-control' name='usertype' placeholder='usertype' required</td>\n"
					+ "              <td><input type='text' class='form-control' name='firstname' placeholder='First Name' required</td>\n"
					+ "              <td><input type='text' class='form-control' name='lastname' placeholder='Last Name' required</td>\n"
					+ "				 <td><input type='submit' class='btn btn-primary btn-lg btn-block' name='action' value=\"add\"></td>\n"
					+ "				</tr>\n"
		    		+ "	</form>");
		 
		pw.print("          </tbody>\n"
				+ "        </table>\n"
				+ "      </div>\n");
	
		utility.printHtml("Footer.html");
		
	}
}


