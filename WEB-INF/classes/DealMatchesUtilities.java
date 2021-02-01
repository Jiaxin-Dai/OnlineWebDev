import java.io.IOException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DealMatchesUtilities")

public class DealMatchesUtilities extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        HashMap<String, Product> selectedproducts = new HashMap<String, Product>();
        try {

            // pw.print("<div id='content'>");
            // pw.print("<div class='post'>");
            // pw.print("<h2 class='title'>");
            // pw.print("<a href='#'>Welcome to BestDeal </a></h2>");

            // pw.print("<div class='entry'>");
            // pw.print(
            //         "<img src='images/site/BestDeal.jpg'style='width: 600px; display: block; margin-left: auto; margin-right: auto' />");
            // pw.print("<br> <br>");
            // pw.print("<h2>The world trusts us to deliver the best deal to our customers</h2>");
            // pw.print("<br> <br>");
            // pw.print("<h1>We beat our competitors in all aspects. Price-Match Guaranteed</h2>");

            String line = null;
            String TOMCAT_HOME = System.getProperty("catalina.home");

            HashMap<Integer, Product> productintmap = MySqlDataStoreUtilities.selectAllProducts();

            HashMap<String, Product> productmap = new HashMap<>();
            for (Product product: productintmap.values()) {
                productmap.put(product.getProductName(), product);
            }
            

            for (Map.Entry<String, Product> entry : productmap.entrySet()) {

                if (selectedproducts.size() < 2 && !selectedproducts.containsKey(entry.getKey())) {

                    BufferedReader reader = new BufferedReader(
                            new FileReader(new File(TOMCAT_HOME + "\\webapps\\CSP584-Project\\DealMatches.txt")));
                    line = reader.readLine().toLowerCase();
                    //

                    if (line == null) {
                        pw.print("<h2 align='center'>No Twitter Offers Found from the Twitter API</h2>");
                        break;
                    } else {
                        do {

                            if (line.contains(entry.getKey().toLowerCase())) {
                                selectedproducts.put(entry.getKey(), entry.getValue());
                                break;
                            }

                        } while ((line = reader.readLine()) != null);

                    }
                }
            }
        } catch (Exception e) {
            // pw.print("<h2 align='center'>No Twitter Offers Found from the Twitter API</h2>");
            pw.print("<div class='alert alert-primary' role='alert' id='twitter' style='margin-bottom: inherit !important'>No Twitter Offers Found from the Twitter API</div>");
        }
        // pw.print("<div class='container marketing'><div class='row featurette'><div class='col-md-7'><h2 class='featurette-heading'>Recommended for You From Twitter Deals");
        for (Product product: selectedproducts.values()) {
            pw.print("<div class='card marketing container' align='center' sty><div class='card-header'>Recommened for you from twitter </div>");
            pw.print("<div class='card-body'><h5 class='card-title'>Your personal recommendation</h5><p class='card-text'>We fetch the freshest deal from Twitter for you</p>");
            pw.print("<div class='col-md-7'><img src='"+ product.getImage() +"' alt=''></div>");
            pw.print("<div class='col-md-7'><a href='ProductList?searchId="+product.getId()+"' class='btn btn-primary'>Go checkout our recommendation</a></div</div>");
            pw.print("</div></div><hr class='featurette-divider'></div>");
        }
    }
}
