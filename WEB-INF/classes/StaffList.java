import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/StaffList")

public class StaffList extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		/* Checks the Tablets type whether it is microsft or apple or samsung */
		String idString = request.getParameter("id");
		int id = -1;
		if (idString != null) id = Integer.parseInt(idString);
		HashMap<Integer, Staff> staffs = new HashMap<>();
		try {
			staffs = MySqlDataStoreUtilities.selectStaff();
		} catch (Exception e) {
			System.out.println(e);
		}
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		if (!utility.isLoggedin()) {
			HttpSession session = request.getSession(true);
			session.setAttribute("login_msg", "Please Login to add items to cart");
			response.sendRedirect("Login");
			return;
		}
		ArrayList<Staff> list = new ArrayList<>();
		String zip = utility.getUser().getZip();
		for (Staff staff: staffs.values()) {
//			if (staff.getPostcode().equals(zip) && staff.getSer_id() == id) {
//				list.add(staff);
//			}
			if (staff.getSer_id() == id) {
				list.add(staff);
			}
		}
		Service ser = MySqlDataStoreUtilities.selectService().get(id);
//		
		pw.print("<div class=\"album py-5 bg-light\">\n" + "    <div class=\"container\">\n" + "\n");
		for (int i = 0; i < list.size(); i++) {
			
			 Staff staff = list.get(i);
			 ArrayList<UnavailableTime> unavailableTimes = new ArrayList<UnavailableTime>();
			 long millis=System.currentTimeMillis();  
			 Date now = new Date(millis);
			 try {
				 unavailableTimes = utility.getUnavailableTime(staff);
			 } catch(Exception e) {
				 System.out.println(e);
			 }
			if (i % 3 == 0) {
				pw.print("<div class=\"row\">\n");
			}
				pw.print( "         <div class=\"col-md-4\">\n" + "          <div class=\"card mb-4 shadow-sm\">\n"
						+ "<img class='card-image' src="+ser.getImage() + ">"
						+ "    <div class=\"card-body\">\r\n"
						+ "      <h5 class=\"card-title\"> Staff id: "+staff.getSid()+"</h5>\r\n"
						+ "      <p class=\"card-text\"> Technician name: "+staff.getSname()+"</p>\r\n"
						+ "      <p class=\"card-text\"><small class=\"text-muted\"> Service zip code: "+staff.getPostcode()+"</small></p>\r\n"
						+"  <div class=\"form-group\">\r\n"
						+ "    <label for=\"exampleFormControlSelect2\">Technician Availability</label>\r\n"
						 + "<form method='POST' action='Cart'>"
						+ "    <select name='time' multiple class=\"form-control\" id=\"exampleFormControlSelect2\">\r\n");
				for (int j = 1; j < 10; j++) {
					for (int k = 1; k <= 5; k++) {
						String time = utility.mapIntToTime(k);
						Date date = new Date(now.getTime() + (j * 24* 60 * 60 * 1000));
						UnavailableTime ut = new UnavailableTime(date, k);
						boolean shouldrender = true;
						for (UnavailableTime u: unavailableTimes) {
							if (u.getUnavilableTimeFrame() == ut.getUnavilableTimeFrame() && utility.isSameDay(u.getUnavilableDate(),date)) {
								shouldrender = false;
								break;
							}
						}
						if (shouldrender == true){
							pw.print("<option>"+ date + " " + time +"</option>");
						}
					}
				}
				pw.print("    </select>\r\n");
				pw.print("<input type='hidden' name='serid' value=" + staff.getSer_id() + ">"
								+ "<input type='hidden' name='sid' value=" + staff.getSid() + ">"
								+ "<input type='hidden' name='productType' value='service'>" 
								+  "<input type='hidden' name='type' value=" +ser.getType() +">"
								+ "<input type='hidden' name='price' value="+ser.getPrice()+ ">"
					+ " <button value='Cart' type='submit' class=\"btn btn-sm btn-outline-secondary\">Choose this technician</button>\n</form>");
				pw.print("</div>\r\n"
						+ "  </div></div></div>");
			
		if (i % 3 == 2) {
			pw.print("</div>");
		}
		}
		pw.print("</div></div>");
		utility.printHtml("Footer.html");
	}
}
