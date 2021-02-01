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

@WebServlet("/GoogleMap")

public class GoogleMap extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayContent(request, response);
	}


	protected void displayContent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		try {
			response.setContentType("text/html");
			
			
			
			HttpSession session = request.getSession();
			utility.printHtml("Header.html");
			
			// pw.print("<h3>Store Locations</h3><div id='map'></div>");

            utility.printHtml("map.html");
            utility.printHtml("Footer.html");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
