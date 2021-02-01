import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@WebServlet("/ManageProductList")

public class ManageProductList extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession(true);
		Utilities utility = new Utilities(request, pw);
		
		String action = request.getParameter("action");
		
		
		if(action!=null && action.equals("submit")) {
			Product p = new Product(
										Integer.parseInt(request.getParameter("pid")),
										request.getParameter("pname"),
										request.getParameter("type"),
										Double.parseDouble(request.getParameter("price")),
										Integer.parseInt(request.getParameter("stock")),
										request.getParameter("Description"),
										request.getParameter("img"),
										request.getParameter("brand"));
			MySqlDataStoreUtilities.updateProduct(p);
			
		}
		else if(action!=null && action.equals("delete")) {
			MySqlDataStoreUtilities.deleteProduct(Integer.parseInt(request.getParameter("pid")));
		}else if(action!=null && action.equals("add")) {
			Product p = new Product(
					Integer.parseInt(request.getParameter("pid")),
					request.getParameter("pname"),
					request.getParameter("type"),
					Double.parseDouble(request.getParameter("price")),
					Integer.parseInt(request.getParameter("stock")),
					request.getParameter("Description"),
					request.getParameter("img"),
					request.getParameter("brand"));
			MySqlDataStoreUtilities.insertproduct(p);
		}
		
		displayForm(utility,pw);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);

	}
	
	public void displayForm(Utilities utility,PrintWriter pw){
	

		utility.printHtml("Header.html");
		
		/* <productid is key> */
		HashMap<Integer, Product> productShowList = new HashMap<Integer, Product>();
		
		try {
			productShowList = MySqlDataStoreUtilities.selectAllProducts();
		} catch (SQLException e) {e.printStackTrace();}
		
		if(productShowList.isEmpty()) {// no orders
			pw.print("<p>There is No Product!</p>");
		}else 
		{// have products
			
			pw.print("<div class=\"table-responsive\">\n"
					+ "        <table class=\"table table-striped table-sm\">\n"
					+ "          <thead>\n"
					+ "            <tr>\n"
					+ "              <th>Product ID</th>\n"
					+ "              <th>Product Name</th>\n"
					+ "              <th>Type</th>\n"
					+ "              <th>Price</th>\n"
					+ "			     <th>Stock</th>\n"
					+ "              <th>Description</th>\n"
					+ "              <th>brand</th>\n"
					+ "              <th>img</th>\n"
					+ "              <th>    </th>\n"
					+ "            </tr>\n"
					+ "          </thead>\n"
					+ "          <tbody>\n");
			
			for(Map.Entry<Integer,Product> pp:productShowList.entrySet())
			{
			    int pid=pp.getKey();
			    Product p=pp.getValue();
			    pw.print( " <form class=\"needs-validation\" method=\"post\" action=\"ManageProductList\">\n"
			    		+ "            	<tr>\n"
			    		+ "              	<td><input type=\"text\" class=\"form-control\" name=\"pid\" value=\"" + pid+ "\" required></td>\n"
			    		+ "              	<td><input type=\"text\" class=\"form-control\" name=\"pname\" value=\"" + p.getProductName() + "\" required></td>\n"
			    		+ "              	<td><input type=\"text\" class=\"form-control\" name=\"type\" value=\"" + p.getType() + "\"  required></td>\n"
			    		+ "              	<td><input type=\"text\" class=\"form-control\" name=\"price\" value=\"" + p.getPrice() + "\" required></td>\n"
			    		+ "              	<td><input type=\"text\" class=\"form-control\" name=\"stock\" value=\"" + p.getStock() + "\"  required></td>\n"
			    		+ "			  		<td><input type=\"text\" class=\"form-control\" name=\"Description\" value=\"" + p.getDesc() + "\"  required></td>\n"
			    		+ "			  		<td><input type=\"text\" class=\"form-control\" name=\"brand\" value=\"" + p.getBrand() + "\"  required></td>\n"
			    		+ "			  		<td><input type=\"text\" class=\"form-control\" name=\"img\" value=\"" + p.getImage() + "\"  required></td>\n"
			    		+ "					<td><input type='submit' class='btn btn-primary btn-lg btn-block' name='action' value=\"submit\"></td>\n"
			    		+ "					<td><input type='submit' class='btn btn-primary btn-lg btn-block' name='action' value=\"delete\"></td>\n"
			    		+ "				</tr>\n"
			    		+ "	</form>");
			}
			pw.print(" <form class=\"needs-validation\" method=\"post\" action=\"ManageProductList\">\n"
					+ "            <tr>\n"
					+ "              <td><input type='text' class='form-control' name='pid' placeholder='Product ID' required></td>\n"
					+ "              <td><input type='text' class='form-control' name='pname' placeholder='Product Name' required</td>\n"
					+ "              <td><input type='text' class='form-control' name='type' placeholder='Type' required</td>\n"
					+ "              <td><input type='text' class='form-control' name='price' placeholder='Price' required</td>\n"
					+ "              <td><input type='text' class='form-control' name='stock' placeholder='Stock' required</td>\n"
					+ "              <td><input type='text' class='form-control' name='Description' placeholder='Description' required</td>\n"
					+ "              <td><input type='text' class='form-control' name='brand' placeholder='Brand' required</td>\n"
					+ "              <td><input type='text' class='form-control' name='img' value='.\\images\\home\\Lignting.jpg' required</td>\n"
				
					
					+ "				 <td><input type='submit' class='btn btn-primary btn-lg btn-block' name='action' value=\"add\"></td>\n"
					+ "				</tr>\n"
		    		+ "	</form>");
			pw.print("          </tbody>\n"
					+ "        </table>\n"
					+ "      </div>\n");
		}
			
		utility.printHtml("Footer.html");
		
		
	}
}
