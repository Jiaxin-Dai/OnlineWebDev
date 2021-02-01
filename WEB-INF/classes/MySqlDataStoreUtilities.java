
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class MySqlDataStoreUtilities {
	static Connection conn = null;

	public static void getConnection() {

		String pwd = "root";
//		String pwd = "12345678";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/homehub?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", pwd);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// --------------------------------------Product--------------------------------------------------

	public static HashMap<Integer, Product> selectAllProducts() throws SQLException {

		HashMap<Integer, Product> productlist = new HashMap<Integer, Product>();
		int id;
		String productName;
		double price;
		int stock;
		String desc;
		String image;
		String brand;
		String type;
		try {

			getConnection();
			// select the table
			String selectProduct = "select * from product";
			PreparedStatement pst = conn.prepareStatement(selectProduct);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				id = rs.getInt(1);
				productName = rs.getString(2);
				type = rs.getString(3);
				price = rs.getDouble(4);
				stock = rs.getInt(5);
				desc = rs.getString(6);
				image = rs.getString(7);
				brand = rs.getString(8);

				Product prod = new Product(id, productName, type, price, stock, desc, image, brand);
				productlist.put(id, prod);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return productlist;
	}

	public static void insertproduct(Product product) {

		try {

			getConnection();
			String insertproduct = "INSERT INTO product(pname,type, price,stock, description, img,brand) "
					+ "VALUES (?,?,?,?,?,?,?);";

			PreparedStatement pst = conn.prepareStatement(insertproduct);
			// set the parameter for each column and execute the prepared statement
			// pst.setInt(1, product.getId());
			pst.setString(1, product.getProductName());
			pst.setString(2, product.getType());
			pst.setDouble(3, product.getPrice());
			pst.setInt(4, product.getStock());
			pst.setString(5, product.getDesc());
			pst.setString(6, product.getImage());
			pst.setString(7, product.getBrand());

			pst.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static HashMap<Integer, Product> selectProductbyType(String type) throws SQLException {

		HashMap<Integer, Product> productlist = new HashMap<Integer, Product>();
		int id;
		String productName;
		double price;
		int stock;
		String desc;
		String image;
		String brand;
		try {

			getConnection();
			// select the table
			String selectProductbyType = "select * from product where type like ?";
			PreparedStatement pst = conn.prepareStatement(selectProductbyType);
			pst.setString(1, type + "%");
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				id = rs.getInt(1);
				productName = rs.getString(2);
				type = rs.getString(3);
				price = rs.getDouble(4);
				stock = rs.getInt(5);
				desc = rs.getString(6);
				image = rs.getString(7);
				brand = rs.getString(8);

				Product prod = new Product(id, productName, type, price, stock, desc, image, brand);
				productlist.put(id, prod);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return productlist;
	}

	public static void updateProduct(Product product) {

		String updateProductQurey = "UPDATE product SET pname=?,type=?,price=?,stock=?,description=?,img=?,brand=? where pid =?;";
		try {
			getConnection();
			PreparedStatement pst = conn.prepareStatement(updateProductQurey);

			pst.setString(1, product.getProductName());
			pst.setString(2, product.getType());
			pst.setDouble(3, product.getPrice());
			pst.setInt(4, product.getStock());
			pst.setString(5, product.getDesc());
			pst.setString(6, product.getImage());
			pst.setString(7, product.getBrand());
			pst.setInt(8, product.getId());

			pst.execute();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void deleteProduct(Integer productID) {
		try {

			getConnection();
			String deleteProduct = "Delete from product where pid='" + productID + "'";
			PreparedStatement pst = conn.prepareStatement(deleteProduct);
			// pst.setString(1,productID);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------Order--------------------------------------------------

	public static HashMap<Integer, Order> selectOrder() {
		HashMap<Integer, Order> orderlist = new HashMap<Integer, Order>();
		int oid;
		Date otime;
		Double totalprice;
		String creditcard;
		String address;
		String postcode;
		int uid;

		try {

			getConnection();
			String selectOrder = "select * from `order`";

			PreparedStatement pst = conn.prepareStatement(selectOrder);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				oid = rs.getInt(1);
				otime = rs.getDate(2);
				totalprice = rs.getDouble(3);
				creditcard = rs.getString(4);
				address = rs.getString(5);
				postcode = rs.getString(6);
				uid = rs.getInt(7);
				Order order = new Order(oid, otime, totalprice, creditcard, address, postcode, uid);

				orderlist.put(oid, order);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderlist;
	}

	public static HashMap<Integer, Order> selectOrderbyCustomer(int uid) {
		HashMap<Integer, Order> orderlist = new HashMap<Integer, Order>();
		int oid;
		Date otime;
		Double totalprice;
		String creditcard;
		String address;
		String postcode;

		try {

			getConnection();
			String selectOrderbyCustomer = "select * from `order` where uid=\"" + uid + "\"";

			PreparedStatement pst = conn.prepareStatement(selectOrderbyCustomer);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				oid = rs.getInt(1);
				otime = rs.getDate(2);
				totalprice = rs.getDouble(3);
				creditcard = rs.getString(4);
				address = rs.getString(5);
				postcode = rs.getString(6);
				uid = rs.getInt(7);
				Order order = new Order(oid, otime, totalprice, creditcard, address, postcode, uid);

				orderlist.put(oid, order);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderlist;
	}

	public static void updateOrder(Order order) {
		String updateOrder = "UPDATE `order` SET otime=?,totalprice=?,creditcard=?,address=?,postcode=?,uid=? where oid =?;";
		try {

			getConnection();
			PreparedStatement pst = conn.prepareStatement(updateOrder);

			pst.setDate(1, order.getOtime());
			pst.setDouble(2, order.getTotalprice());
			pst.setString(3, order.getCreditcard());
			pst.setString(4, order.getAddress());
			pst.setString(5, order.getPostcode());
			pst.setInt(6, order.getUid());
			pst.setInt(7, order.getOid());

			pst.execute();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void deleteOrder(int orderId) {
		try {

			getConnection();
			String deleteOrderQuery = "Delete from `order` where oid=?";
			PreparedStatement pst = conn.prepareStatement(deleteOrderQuery);
			pst.setInt(1, orderId);

			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int insertOrder(Order order) {
		String insertQuery = "INSERT INTO `order`(otime,totalprice,creditcard,address,postcode,uid) VALUES (?,?,?,?,?,?);";
		try {

			getConnection();
			PreparedStatement pst = conn.prepareStatement(insertQuery,Statement.RETURN_GENERATED_KEYS);

			pst.setDate(1, order.getOtime());
			pst.setDouble(2, order.getTotalprice());
			pst.setString(3, order.getCreditcard());
			pst.setString(4,  order.getAddress());
			pst.setString(5,  order.getPostcode());
			pst.setInt(6, order.getUid());
			pst.execute();
			ResultSet tableKeys = pst.getGeneratedKeys();
			tableKeys.next();
			return tableKeys.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}

	// --------------------------------------OrderItem--------------------------------------------------
	public static HashMap<Integer, OrderItem> selectOrderItembyOrderID(Integer orderID) {

		HashMap<Integer, OrderItem> orderitemlist = new HashMap<Integer, OrderItem>();

		try {
			getConnection();
			String selectOrderItembyOrderID = "select * from orderitem where oid=\"" + orderID + "\"";

			PreparedStatement pst = conn.prepareStatement(selectOrderItembyOrderID);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				OrderItem item = new OrderItem(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getString(4),
						rs.getInt(5), rs.getInt(6), rs.getInt(7));
				orderitemlist.put(item.getOid(), item);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderitemlist;

	}

	public static void updateOrderedItem(OrderItem item) {
		try {
			String updateProductQurey = "UPDATE orderitem SET item_price=?,category=?,quantity=?,sid=?,scheduletime=? where oid =? and item_id=?;";
			getConnection();
			PreparedStatement pst = conn.prepareStatement(updateProductQurey);

			pst.setDouble(1, item.getItem_price());
			pst.setString(2, item.getCategory());
			pst.setInt(3, item.getQuantity());
			pst.setInt(4, item.getSid());
			pst.setInt(5, item.getScheduletime());

			pst.setInt(6, item.getOid());
			pst.setInt(7, item.getItem_id());
			pst.execute();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteOrderItem(Integer orderID, Integer item_id) {
		try {
			getConnection();
			String deleteOrderItem = "Delete from orderitem where oid=? and item_id = ?";
			PreparedStatement pst = conn.prepareStatement(deleteOrderItem);
			pst.setInt(1, orderID);
			pst.setInt(2, item_id);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void deleteOrderItem(Integer orderID) {
		try {
			getConnection();
			String deleteOrderItem = "Delete from orderitem where oid=? ";
			PreparedStatement pst = conn.prepareStatement(deleteOrderItem);
			pst.setInt(1, orderID);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void insertOrderItem(OrderItem item) {
		try {			
			getConnection();
			String insertQuery = "INSERT INTO orderitem(item_price,category,quantity,sid,scheduletime, oid, item_id) "
					+ "VALUES (?,?,?,?,?,?,?);";
			PreparedStatement pst = conn.prepareStatement(insertQuery);
			pst.setDouble(1, item.getItem_price());
			pst.setString(2, item.getCategory());
			pst.setInt(3, item.getQuantity());
			pst.setInt(4, item.getSid());
			pst.setInt(5, item.getScheduletime());

			pst.setInt(6, item.getOid());
			pst.setInt(7, item.getItem_id());
			pst.execute();
		} catch (Exception e) {
			System.out.print(e);
		}
		
	}

	// --------------------------------------User--------------------------------------------------
	public static void insertUserRegister(String username, String password, String usertype, String firstName,
			String lastName,String address,String city, String state, String zip) {
		try {

			getConnection();
			String insertIntoCustomerRegisterQuery = "INSERT INTO user(uname,pwd,type,firstName,lastName,address,city,state,postcode) "
					+ "VALUES (?,?,?,?,?,?,?,?,?);";

			PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			pst.setString(1, username);
			pst.setString(2, password);
			pst.setString(3, usertype);
			pst.setString(4, firstName);
			pst.setString(5, lastName);
			pst.setString(6, address);
			pst.setString(7, city);
			pst.setString(8, state);
			pst.setString(9, zip);
			pst.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void insertUser(User user) {
		try {

			getConnection();
			String insertIntoCustomerRegisterQuery = "INSERT INTO user(uid,uname,pwd,type,firstName,lastName,address,city,state,postcode) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?);";

			PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			pst.setInt(1, user.getId());
			pst.setString(2, user.getName());
			pst.setString(3, user.getPassword());
			pst.setString(4, user.getUsertype());
			pst.setString(5, user.getFirstname());
			pst.setString(6, user.getLastname());
			pst.setString(7, user.getAddress());
			pst.setString(8, user.getCity());
			pst.setString(9, user.getState());
			pst.setString(10, user.getZip());
			pst.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void updateUser(User user) {
		try {
			getConnection();
			String updateUser = "UPDATE user SET uname=?,pwd=?,type=?,firstName=?,lastName=?,address=?,city=?,state=?,postcode=? where uid =? ;";

			PreparedStatement pst = conn.prepareStatement(updateUser);
			pst.setString(1, user.getName());
			pst.setString(2, user.getPassword());
			pst.setString(3, user.getUsertype());
			pst.setString(4, user.getFirstname());
			pst.setString(5, user.getLastname());
			pst.setString(6, user.getAddress());
			pst.setString(7, user.getCity());
			pst.setString(8, user.getState());
			pst.setString(9, user.getZip());
			pst.setInt(10, user.getId());
			pst.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void updatePartofUser(Integer uid, String uname, String type, String firstname, String lastname) {
		try {
			getConnection();
			String updatePart = "UPDATE user SET uname=?,type=?,firstName=?,lastName=? where uid =? ;";

			PreparedStatement pst = conn.prepareStatement(updatePart);
			pst.setString(1, uname);
			pst.setString(2, type);
			pst.setString(3, firstname);
			pst.setString(4, lastname);
			pst.setInt(5, uid);
			
			pst.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static HashMap<String, User> selectUser() {
		HashMap<String, User> hm = new HashMap<String, User>();
		try {
			getConnection();
			Statement stmt = conn.createStatement();
			String selectCustomerQuery = "select * from  user";
			ResultSet rs = stmt.executeQuery(selectCustomerQuery);
			while (rs.next()) {
				User user = new User(rs.getInt("uid"), rs.getString("uname"), rs.getString("pwd"), rs.getString("type"),
						rs.getString("firstName"), rs.getString("lastName"), rs.getString("address"),
						rs.getString("city"), rs.getString("state"), rs.getString("postcode"));
				hm.put(rs.getString("uname"), user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hm;
	}
	
	public static void deleteUser(Integer uid) {
		try {

			getConnection();
			String deleteOrderQuery = "Delete from user where uid=?";
			PreparedStatement pst = conn.prepareStatement(deleteOrderQuery);
			pst.setInt(1, uid);

			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// --------------------------------------Schedule--------------------------------------------------
	public static void insertSchedule(Schedule schedule) {
		try {

			getConnection();
			String insertSchedule = "INSERT INTO schedule(sid,worktime,date) " + "VALUES (?,?,?);";

			PreparedStatement pst = conn.prepareStatement(insertSchedule);
			pst.setInt(1, schedule.getSid());
			pst.setInt(2, schedule.getWorktime());
			pst.setDate(3, schedule.getDate());
			pst.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static HashMap<Integer, ArrayList<Schedule>> selectSchedule() {
		HashMap<Integer, ArrayList<Schedule>> hm = new HashMap<>();
		try {
			getConnection();
			Statement stmt = conn.createStatement();
			String selectSchedule = "select * from  schedule";
			ResultSet rs = stmt.executeQuery(selectSchedule);
			ArrayList<Schedule> scheduleList = new ArrayList<>();
			while (rs.next()) {
				Schedule schedule = new Schedule(rs.getInt(1), rs.getInt(2), rs.getDate(3));
				if (hm.containsKey(schedule.getSid())) {
					scheduleList = hm.get(schedule.getSid());
				} else {
					scheduleList = new ArrayList<>();
				}
				scheduleList.add(schedule);
				hm.put(schedule.getSid(), scheduleList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hm;
	}

	// --------------------------------------Service---------------------------------------------------
	public static void insertService(Service Service) {
		try {

			getConnection();
			String insertService = "INSERT INTO services(serviceName,type,price,description,image) "
					+ "VALUES (?,?,?,?,?);";

			PreparedStatement pst = conn.prepareStatement(insertService);
			// pst.setInt(1, Service.getId());
			pst.setString(1, Service.getServiceName());
			pst.setString(2, Service.getType());
			pst.setDouble(3, Service.getPrice());
			pst.setString(4, Service.getDescription());
			pst.setString(5, Service.getImage());

			pst.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static HashMap<Integer, Service> selectService() {
		HashMap<Integer, Service> hm = new HashMap<Integer, Service>();
		try {
			getConnection();
			Statement stmt = conn.createStatement();
			String selectService = "select * from  services";
			ResultSet rs = stmt.executeQuery(selectService);
			while (rs.next()) {
				Service service = new Service(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4),
						rs.getString(5), rs.getString(6));
				hm.put(service.getId(), service);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hm;
	}

	// --------------------------------------Staff-----------------------------------------------------
	public static void insertStaff(Staff staff) {
		try {

			getConnection();
			String insertStaff = "INSERT INTO staff(sname,ser_id,postcode) " + "VALUES (?,?,?);";

			PreparedStatement pst = conn.prepareStatement(insertStaff);
			// pst.setInt(1, staff.getSid());
			pst.setString(1, staff.getSname());
			pst.setInt(2, staff.getSer_id());
			pst.setString(3, staff.getPostcode());

			pst.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static HashMap<Integer, Staff> selectStaff() {
		HashMap<Integer, Staff> hm = new HashMap<Integer, Staff>();
		try {
			getConnection();
			Statement stmt = conn.createStatement();
			String selectStaff = "select * from  staff";
			ResultSet rs = stmt.executeQuery(selectStaff);
			while (rs.next()) {
				Staff staff = new Staff(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
				hm.put(rs.getInt("sid"), staff);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hm;
	}

	public static ArrayList<Mostsold> mostsoldProducts() {
        ArrayList<Mostsold> mostsold = new ArrayList<>();
        try {
            getConnection();
            String mostSoldProductString = "Select item_id, count(item_id) as count from `orderitem` where item_id != -1 group by item_id order by count DESC limit 5;";

            PreparedStatement pst = conn.prepareStatement(mostSoldProductString);
			ResultSet result = pst.executeQuery();
			HashMap<Integer, Product> products = selectAllProducts();
			HashMap<Integer, Service> services = selectService();
            while (result.next()) {
				int item_id = result.getInt("item_id");
				String name = "Undefined";
				if (item_id > 50) {
					name = services.get(item_id).getServiceName();
				} else {
					name = products.get(item_id).getProductName();
				}
                Mostsold item = new Mostsold(name, result.getString("count"));
                mostsold.add(item);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return mostsold;
    }

    public static ArrayList<Mostsoldzip> mostsoldZip() {
        ArrayList<Mostsoldzip> mostsoldzip = new ArrayList<>();
        try {
            getConnection();
            String mostSoldZipProductString = "select postcode as zip, count(oid) as count from `order` group by zip order by count DESC limit 5;";
            PreparedStatement pst = conn.prepareStatement(mostSoldZipProductString);
			ResultSet result = pst.executeQuery();
            while (result.next()) {
                Mostsoldzip item = new Mostsoldzip(result.getString("zip"), result.getString("count"));
                mostsoldzip.add(item);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return mostsoldzip;
	}
	
	public static HashMap<Integer, SalesItem> getOnSaleProducts() {
		HashMap<Integer, SalesItem> result = new HashMap<>();
		try {

			getConnection();
			// select the table
			String selectProduct = "select * from product natural join discountinformation";
			PreparedStatement pst = conn.prepareStatement(selectProduct);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				int id = rs.getInt(1);
				String productName = rs.getString(2);
				String type = rs.getString(3);
				Double price = rs.getDouble(4);
				int stock = rs.getInt(5);
				String desc = rs.getString(6);
				String image = rs.getString(7);
				String brand = rs.getString(8);
				Double discount =  rs.getDouble(9);
				Double rebate =  rs.getDouble(10);

				SalesItem prod = new SalesItem(productName, price, stock, discount, rebate);
				result.put(id, prod);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static HashSet<DiscountItem> getDiscountItem() {
		HashSet<DiscountItem> hs = new HashSet<>();
		try {
			String selectProduct = "select pname, item_price, count(item_id) as count from orderitem inner join product on orderitem.item_id = product.pid group by item_id;";
			PreparedStatement pst = conn.prepareStatement(selectProduct);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				String productName = rs.getString(1);
				Double price = rs.getDouble(2);
				int stock = rs.getInt(3);
				DiscountItem prod = new DiscountItem(productName, price, stock);
				hs.add(prod);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return hs;
	}

	public static  HashSet<DailySale> getDailySale() {
		HashSet<DailySale> hs = new HashSet<>();
		try {
			String selectProduct = "select otime, sum(item_price * quantity) from `orderitem` natural join `order` group by date(otime)";
			PreparedStatement pst = conn.prepareStatement(selectProduct);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				Date date = rs.getDate(1);
				Double total = rs.getDouble(2);;
				DailySale prod = new DailySale(date, total);
				hs.add(prod);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return hs;
	}
}
