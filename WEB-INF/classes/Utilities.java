import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;

@WebServlet("/Utilities")

/*
 * Utilities class contains class variables of type HttpServletRequest,
 * PrintWriter,String and HttpSession.
 * 
 * Utilities class has a constructor with HttpServletRequest, PrintWriter
 * variables.
 * 
 */

public class Utilities extends HttpServlet {
    HttpServletRequest req;
    PrintWriter pw;
    String url;
    HttpSession session;

    public Utilities(HttpServletRequest req, PrintWriter pw) {
        this.req = req;
        this.pw = pw;
        this.url = this.getFullURL();
        this.session = req.getSession(true);
    }

    /*
     * Printhtml Function gets the html file name as function Argument, If the html
     * file name is Header.html then It gets Username from session variables.
     * Account ,Cart Information ang Logout Options are Displayed
     */

    public String getFullURL() {
        String scheme = req.getScheme();
        String serverName = req.getServerName();
        int serverPort = req.getServerPort();
        String contextPath = req.getContextPath();
        StringBuffer url = new StringBuffer();
        url.append(scheme).append("://").append(serverName);

        if ((serverPort != 80) && (serverPort != 443)) {
            url.append(":").append(serverPort);
        }
        url.append(contextPath);
        url.append("/");
        return url.toString();
    }

    public String HtmlToString(String file) {
        String result = null;
        try {
            String webPage = url + file;
            URL url = new URL(webPage);
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            int numCharsRead;
            char[] charArray = new char[1024];
            StringBuffer sb = new StringBuffer();
            while ((numCharsRead = isr.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }
            result = sb.toString();
        } catch (Exception e) {
        }
        return result;
    }

    public void printHtml(String file) {
        String result = HtmlToString(file);
        // to print the right navigation in header of username cart and logout etc
        if (file == "Header.html") {
//        	result = result + "<ul class='navbar-nav mr-auto' >";
//        	+ "</ul>"
			//has logged in
			if (session.getAttribute("username") != null) {
				
				
				String usertype = (String) session.getAttribute("usertype");
				
				if (usertype != null && usertype.equals("manager")) {
					// ---------------manager-----------------
					String username = session.getAttribute("username").toString();
					username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
					result = result
							+ "<ul class='navbar-nav' >"
							+ "	<a class='nav-link' href='InventoryReport'>Inventory Report</a>"
							+ "	<a class='nav-link' href='SalesReport'>Sales Report</a>"
							// + "	<a class='nav-link' href='#'>DataAnalytics</a>"
							+ "	<a class='nav-link' href='Account'>Account</a>"
							+ "	<a class='nav-link' >Hello, " + username + "</a>"
							+ "</ul>"
							+ "	<a class='btn btn-outline-primary btn-outline-success' href='Logout'>Logout</a>";

				} else if (usertype != null && usertype.equals("salesman")) {
					// ----------------salesman------------
					String username = session.getAttribute("username").toString();
					username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
					result = result 
							+ "<ul class='navbar-nav' >"
							+ "	<a class='nav-link' href='SalesmanViewOrderList'>View Orders</a>"
							+ "	<a class='nav-link' href='SalesmanViewUsers'>View Users</a>"
							+ "	<a class='nav-link' href='ManageProductList'>Edit Product</a>"
							+ "	<a class='nav-link' href='Account'>Account</a>"
							+ "	<a class='nav-link' >Hello, " + username + "</a>"
							+ "</ul>"
							+ "	<a class='btn btn-outline-primary btn-outline-success' href='Logout'>Logout</a>";
					
				} else {
					// -----------------customer---------------
					String username = session.getAttribute("username").toString();
					username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
					result = result
							+ "<ul class='navbar-nav' >"
							+ "	<a class='nav-link' href='CustomerViewOrderList'>View Order</a>"
							+ "	<a class='nav-link' href='Account'>Account</a>"
							+ "	<a class='nav-link' >Hello, " + username + "</a>"
							+ "</ul>"
							+ "	<a class='btn btn-outline-primary btn-outline-success' href='Logout'>Logout</a>";
				}
			} 
			//not login
			else
				result = result + "<a class=\"btn btn-outline-primary btn-outline-success\" href=\"Login\">Sign in</a>";
						
			
			result = result
					+ "<a class=\"btn btn-outline-success\" href=\"Cart\">Cart</a>\n"
					
					+ "      </div>\n"
					+ "    </nav>\n"
					+ "</header>";
			pw.print(result);
		} else
			pw.print(result);
    }

    public void logout() {
        session.removeAttribute("username");
        session.removeAttribute("usertype");
    }

    /* logout Function checks whether the user is loggedIn or Not */

    public boolean isLoggedin() {
        if (session.getAttribute("username") == null)
            return false;
        return true;
    }

    /* username Function returns the username from the session variable. */

    public String username() {
        if (session.getAttribute("username") != null)
            return session.getAttribute("username").toString();
        return null;
    }

    /* usertype Function returns the usertype from the session variable. */
    public String usertype() {
        if (session.getAttribute("usertype") != null)
            return session.getAttribute("usertype").toString();
        return null;
    }

    /*
     * getUser Function checks the user is a customer or retailer or manager and
     * returns the user class variable.
     */
     public User getUser() {
         //String usertype = usertype();
         HashMap<String, User> hm = new HashMap<String, User>();
         try {
             hm = MySqlDataStoreUtilities.selectUser();
         } catch (Exception e) {
         }
         User user = hm.get(username());
         return user;
     }

    
	public int CartCount() {
		if (isLoggedin()) {
			ArrayList<OrderItem> al = getCustomerOrders();
			int sum = 0;
			for (OrderItem oi: al) {
				sum += oi.getQuantity();
			}
			return sum;
		}
		return 0;
	}
	
	public ArrayList<OrderItem> getCustomerOrders() {
		ArrayList<OrderItem> order = new ArrayList<OrderItem>();
		if (OrdersHashMap.orders.containsKey(username()))
			order = OrdersHashMap.orders.get(username());
		return order;
	}
	
	public void storeProduct(String name, int id, String category, int time) {
		if (!OrdersHashMap.orders.containsKey(username())) {
			ArrayList<OrderItem> arr = new ArrayList<OrderItem>();
			OrdersHashMap.orders.put(username(), arr);
		}
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		
		for (OrderItem item: orderItems) {
			if (item.getItem_id() == id) {
				item.setQuantity(item.getQuantity() + 1);
				return;
			}
		}
		
		if (category.equals("product")) {
			Product product;
			HashMap<Integer, Product> hm = null;
			try {
				hm = MySqlDataStoreUtilities.selectAllProducts();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Product p = hm.get(id);
			OrderItem oi = new OrderItem(-1, p.getId(), p.getPrice(), p.getType(), 1, -1, -1);
			orderItems.add(oi);
		}
		if (category.equals("service")) {
			Service service;
			HashMap<Integer, Service> hm = MySqlDataStoreUtilities.selectService();
			Service s = hm.get(id);
			OrderItem orderitem = new OrderItem(-1, s.getId(), s.getPrice(), s.getType(), 1, s.getId(), time);
			orderItems.add(orderitem);
		}
		OrdersHashMap.orders.put(username(), orderItems);
	}
	
	public void removeProduct(int id) {
		if (!OrdersHashMap.orders.containsKey(username())) {
			ArrayList<OrderItem> arr = new ArrayList<OrderItem>();
			OrdersHashMap.orders.put(username(), arr);
		}
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		orderItems.remove(id);
	}

	public String storeReview(String productname, String producttype, String productmaker, String reviewrating,
			String reviewdate, String reviewtext, String reatilerpin, String price, String city, String storeName,
			String retailerState, String onSale, String rebate, String age, String gender, String occupation) {
		String message = MongoDBDataStoreUtilities.insertReview(productname, username(), producttype, productmaker,
				reviewrating, reviewdate, reviewtext, reatilerpin, price, city, storeName, retailerState, onSale,
				rebate, age, gender, occupation);
		if (!message.equals("Successfull")) {
			return "UnSuccessfull";
		} else {
			HashMap<String, ArrayList<Review>> reviews = new HashMap<String, ArrayList<Review>>();
			try {
				reviews = MongoDBDataStoreUtilities.selectReview();
			} catch (Exception e) {

			}
			if (reviews == null) {
				reviews = new HashMap<String, ArrayList<Review>>();
			}
			// if there exist product review already add it into same list for productname
			// or create a new record with product name

			if (!reviews.containsKey(productname)) {
				ArrayList<Review> arr = new ArrayList<Review>();
				reviews.put(productname, arr);
			}
			ArrayList<Review> listReview = reviews.get(productname);
			Review review = new Review(productname, username(), producttype, productmaker, reviewrating, reviewdate,
					reviewtext, reatilerpin, price, city, storeName, retailerState, onSale, rebate, age, gender,
					occupation);
			listReview.add(review);

			// add Reviews into database

			return "Successfull";
		}
	}
	
	public ArrayList<UnavailableTime> getUnavailableTime(Staff staff) {
		HashMap<Integer, ArrayList<Schedule>> schedules = new HashMap<>();
		ArrayList<UnavailableTime> staffUnavailability = new ArrayList<>();
		try {
			schedules = MySqlDataStoreUtilities.selectSchedule();
			ArrayList<Schedule> list = schedules.get(staff.getSid());
			for (Schedule s: list) {
				UnavailableTime ut = new UnavailableTime(s.getDate(), s.getWorktime());
				staffUnavailability.add(ut);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return staffUnavailability;
	}
	
	public String mapIntToTime(int time) {
		String result = "error";
		switch(time) {
			case 1:
				result = "8-10am";
				break;
			case 2:
				result = "10-12am";
				break;
			case 3:
				result = "0-2pm";
				break;
			case 4:
				result = "2-4pm";
				break;
			default:
				result = "4-6pm";	
		}
		return result;
	}

	public int mapTimeToInt(String timeString) {
		int result = -1;
		switch(timeString) {
			case "8-10am":
				result = 1;
				break;
			case "10-12am":
				result = 2;
				break;
			case "0-2pm":
				result = 3;
				break;
			case "2-4pm":
				result = 4;
				break;
			default:
				result = 5;	
		}
		return result;
	}


	public boolean isSameDay(Date date1, Date date2) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		return fmt.format(date1).equals(fmt.format(date2));
	}
}
