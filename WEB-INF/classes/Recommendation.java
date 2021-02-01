

import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;	

			
			
public class Recommendation{
			
	public String  Recommendation(Utilities utility){
				
		ProductRecommenderUtility prodRecUtility = new ProductRecommenderUtility();
		
		HashMap<String, Console> hm = new HashMap<String, Console>();
		StringBuilder sb = new StringBuilder();
		String myCarousel = null;
			

		HashMap<String,String> prodRecmMap = new HashMap<String,String>();
		prodRecmMap = prodRecUtility.readOutputFile();
		
		
		
		int l =0;
		for(String user: prodRecmMap.keySet())
		{
			if(user.equals(utility.username()))
			{
				String products = prodRecmMap.get(user);
				products=products.replace("[","");
				products=products.replace("]","");
				products=products.replace("\"", " ");
				ArrayList<String> productsList = new ArrayList<String>(Arrays.asList(products.split(",")));

		        myCarousel = "myCarousel"+l;
					
				sb.append("<div id='content'><div class='post'><h2 class='title meta'>");
				sb.append("<a style='font-size: 24px;'>"+""+" Recommended Products</a>");
				
				sb.append("</h2>");

				sb.append("<div class=\"container\">");
				
				int k = 0;
				for(String prod : productsList){
					prod= prod.replace("'", "");
					Product prodObj = new Product();
					prodObj = ProductRecommenderUtility.getProduct(prod.trim());
					if (k == 3) {
						break;
					}
					if (k % 3 == 0) {
						sb.append("<div class=\"row\">\n");
					}
					sb.append("    "
							//+ "<div class=\"row\">\r\n"
							+ "        <div class=\"col-md-4\">\r\n"
							+ "          <div class=\"card mb-4 shadow-sm\">\r\n"
							//+ "            <svg class=\"bd-placeholder-img card-img-top\" width=\"100%\" height=\"225\" xmlns=\"http://www.w3.org/2000/svg\" preserveAspectRatio=\"xMidYMid slice\" focusable=\"false\" role=\"img\" aria-label=\"Placeholder: Thumbnail\"><title>Placeholder</title><rect width=\"100%\" height=\"100%\" fill=\"#55595c\"/><text x=\"50%\" y=\"50%\" fill=\"#eceeef\" dy=\".3em\">Thumbnail</text></svg>\r\n"
							+ "			   <img class='card-image' src=" + prodObj.getImage() + " style='height: 25rem' >"
							+ "            <div class=\"card-body\">\r\n"
							+ "				 <h4 class=\"card-text\">"+ prodObj.getProductName() + "</h4>"
							+ "              <p class=\"card-text\">"+prodObj.getDesc()+"</p>\r\n"
							+ "              <div class=\"d-flex justify-content-between align-items-center\">\r\n"
							+ "                <div class=\"btn-group\">\r\n"
							+ "				      <form method='POST' action='Cart'>\n"
							+ "							<input type='hidden' name='name' value=" + prodObj.getProductName() + ">"
							+ "							<input type='hidden' name='id' value=" + prodObj.getId() + ">"
							+ "							<input type='hidden' name='type' value='product'>"
							+ "                  		<button value='Cart' class=\"btn btn-sm btn-outline-secondary\" type='submit'>Add to Cart</button>\n  </form>"
							+ "             	 <form method='POST' action='WriteReview'>"
							+ "							<input type='hidden' name='name' value=" + prodObj.getProductName() + ">"
							+ "							<input type='hidden' name='id' value=" + prodObj.getId() + ">"
							+ "							<input type='hidden' name='price' value=" + prodObj.getPrice() + ">"
							+ "							<input type='hidden' name='type' value=" + prodObj.getType() + ">"
							+ "							<input type='hidden' name='maker' value=" + prodObj.getBrand() + ">"
							+ "							<input type='hidden' name='type' value='product'>"
							+ " 						<button value='WriteReview' type='submit' class=\"btn btn-sm btn-outline-secondary\">Write a Review</button>\n</form>"
							+ "          		<form method='POST' action='ViewReview'>" 
							+ "							<input type='hidden' name='name' value="+ prodObj.getProductName() + ">" 
							+ "							<input type='hidden' name='id' value=" + prodObj.getId()+ ">" 
							+ "							<input type='hidden' name='price' value=" + prodObj.getPrice() + ">"
							+ "							<input type='hidden' name='type' value=" + prodObj.getType() + ">"
							+ "							<input type='hidden' name='maker' value=" + prodObj.getBrand() + ">"
							+ "							<button value='ViewReview' type='submit' class=\"btn btn-sm btn-outline-secondary\">View Review</button>\n</form>"
							+ "                </div>\n" 
							+ "                <small class=\"text-muted\">$"+ prodObj.getPrice() + "</small>\n" 
							+ "            </div>\n" 
							+ "            </div>\n"
							+ "          </div>\n" 
							+ "        </div>");
							//+ " 	</div>\n");
					k++;
					if (k % 3 == 0) {
						sb.append("</div>");
					}
					
				}
				

			
				sb.append("</div></div>");
				sb.append("</div>");
				l++;
			
				}
			}
			return sb.toString();
		}
	}
	