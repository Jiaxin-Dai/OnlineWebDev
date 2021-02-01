import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ProductList")

public class ProductList extends HttpServlet {

	/* Trending Page Displays all the Tablets and their Information in Game Speed */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		/* Checks the Tablets type whether it is microsft or apple or samsung */

		String category = request.getParameter("product");
		String searchIdString = request.getParameter("searchId");
		int searchId = -1;
		if (searchIdString != null) {
			searchId = Integer.parseInt(searchIdString);
		}
		System.out.println(searchId);
		HashMap<Integer, Product> hm = new HashMap<>();
		try {
			hm = MySqlDataStoreUtilities.selectAllProducts();
		} catch (Exception e) {
			System.out.println(e);
		}

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");

		ArrayList<Product> list = new ArrayList<>();
		if (searchId != -1) {
			list.add(hm.get(searchId));
		} else if (category == null) {
			list = new ArrayList<>(hm.values());
		} else {
			for (Product product : hm.values()) {
				if (product.getType().equals(category)) {
					list.add(product);
				}
			}
		}

		pw.print("<div class=\"album py-5 bg-light\">\n" + "    <div class=\"container\">\n" + "\n");

		for (int i = 0; i < list.size(); i++) {
			if (i % 3 == 0) {
				pw.print("<div class=\"row\">\n");
			}
			pw.print("        <div class=\"col-md-4\">\n" + "          <div class=\"card mb-4 shadow-sm\">\n"
					+ "<img class='card-image' src=" + list.get(i).getImage() + " style='height: 25rem' >"
					+ "            <div class=\"card-body\">\n"
					+ "<h4 class=\"card-text\">"+ list.get(i).getProductName() + "</h4>"
					+ "<p class=\"card-text\">"
					+ list.get(i).getDesc() + "</p>\n"
					+ "              <div class=\"d-flex justify-content-between align-items-center\">\n"
					+ "                <div class=\"btn-group\">\n"
					+ "				      <form method='POST' action='Cart'>\n"
					+ "<input type='hidden' name='name' value=" + list.get(i).getProductName() + ">"
					+ "<input type='hidden' name='id' value=" + list.get(i).getId() + ">"
					+ "<input type='hidden' name='type' value='product'>"
					+ "                  <button value='Cart' class=\"btn btn-sm btn-outline-secondary\" type='submit'>Add to Cart</button>\n  </form>"
					+ "              <form method='POST' action='WriteReview'>"
					+ "<input type='hidden' name='name' value=" + list.get(i).getProductName() + ">"
					+ "<input type='hidden' name='id' value=" + list.get(i).getId() + ">"
					+ "<input type='hidden' name='price' value=" + list.get(i).getPrice() + ">"
					+ "<input type='hidden' name='type' value=" + list.get(i).getType() + ">"
					+ "<input type='hidden' name='maker' value=" + list.get(i).getBrand() + ">"

					+ "<input type='hidden' name='type' value='product'>"
					+ " <button value='WriteReview' type='submit' class=\"btn btn-sm btn-outline-secondary\">Write a Review</button>\n</form>"
					+ "          <form method='POST' action='ViewReview'>" + "<input type='hidden' name='name' value="
					+ list.get(i).getProductName() + ">" + "<input type='hidden' name='id' value=" + list.get(i).getId()
					+ ">" + "<input type='hidden' name='price' value=" + list.get(i).getPrice() + ">"
					+ "<input type='hidden' name='type' value=" + list.get(i).getType() + ">"
					+ "<input type='hidden' name='maker' value=" + list.get(i).getBrand() + ">"
					+ "<button value='ViewReview' type='submit' class=\"btn btn-sm btn-outline-secondary\">View Review</button>\n</form>"
					+ "                </div>\n" + "                <small class=\"text-muted\">$"
					+ list.get(i).getPrice() + "</small>\n" + "            </div>\n" + "            </div>\n"
					+ "          </div>\n" + "        </div>\n");
			if (i % 3 == 2) {
				pw.print("</div>");
			}
		}

		pw.print("    </div>\n" + "  </div>");
		utility.printHtml("Footer.html");
	}
}
