import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import java.util.*;
import java.sql.*;

@WebServlet("/InventoryReport")

public class InventoryReport extends HttpServlet {
    private String error_msg;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        displayInventoryReport(request, response);
    }

    /* Display Account Details of the Customer (Name and Usertype) */

    protected void displayInventoryReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);
        try {
            response.setContentType("text/html");
            if (!utility.isLoggedin()) {
                HttpSession session = request.getSession(true);
                session.setAttribute("login_msg", "Please Login to view the report");
                response.sendRedirect("Login");
                return;
            }
            HttpSession session = request.getSession();
            HashMap<Integer, Product> hm = new HashMap<Integer, Product>();
            hm = MySqlDataStoreUtilities.selectAllProducts();
            HashMap<Integer, SalesItem> onsale = new HashMap<Integer, SalesItem>();
            onsale = MySqlDataStoreUtilities.getOnSaleProducts();
            utility.printHtml("Header.html");
            pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
            pw.print("<a style='font-size: 24px;'>Sales Report</a></h2>");
            pw.print("<div id='chart_div'></div>");
            pw.print("<div class='entry' >");
            pw.print(
                    "<table class='gridtable'><tr/><td>Product Inventory Details</td><tr/><tr><td>Product Name</td><td>price</td><td>Stock</td></tr>");
            for (Product product : hm.values()) {
                pw.print("<tr>");
                pw.print("<td>");
                pw.print(product.getProductName());
                pw.print("</td>");
                pw.print("<td>");
                pw.print(product.getPrice());
                pw.print("</td>");
                pw.print("<td>");
                pw.print(product.getStock());
                pw.print("</td>");
                pw.print("</tr>");
            }
            Gson gson = new Gson();
            String output = gson.toJson(hm);
            pw.print("</table></div><br></br><br></br>");

            pw.print(
                    "<div class='entry' ><table class='gridtable'><tr/><td>Product on Sale</td><tr/><tr><td>Product Name</td><td>price</td><td>Discount</td></tr>");
            for (SalesItem item : onsale.values()) {
                if (item.getDiscount() <= 0) continue;
                pw.print("<tr>");
                pw.print("<td>");
                pw.print(item.getName());
                pw.print("</td>");
                pw.print("<td>");
                pw.print(item.getPrice());
                pw.print("</td>");
                pw.print("<td>");
                pw.print(item.getDiscount());
                pw.print("</td>");
                pw.print("</tr>");
            }
            pw.print("</table></div><br></br><br></br>");

            pw.print(
                    "<div class='entry' ><table class='gridtable'><tr/><td>Product Rebate Details</td><tr/><tr><td>Product Name</td><td>price</td><td>Rebate</td></tr>");
            for (SalesItem item : onsale.values()) {
                if (item.getRebate() != 0) {

                    pw.print("<tr>");
                    pw.print("<td>");
                    pw.print(item.getName());
                    pw.print("</td>");
                    pw.print("<td>");
                    pw.print(item.getPrice());
                    pw.print("</td>");
                    pw.print("<td>");
                    pw.print(item.getRebate());
                    pw.print("</td>");
                    pw.print("</tr>");
                }
            }
            pw.print("</table>");

            pw.print("</div></div></div>");
            utility.printHtml("Footer.html");
            pw.println("<script type='text/javascript' src='DataVisualizationInventory.js' data-1='" + output + "'></script>");
        } catch (Exception e) {
        }
    }
}
