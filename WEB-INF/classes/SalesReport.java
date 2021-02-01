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
import com.google.gson.JsonObject;

import java.util.*;
import java.sql.*;

@WebServlet("/SalesReport")

public class SalesReport extends HttpServlet {
    private String error_msg;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        displaySalesReport(request, response);
    }

    /* Display Account Details of the Customer (Name and Usertype) */

    protected void displaySalesReport(HttpServletRequest request, HttpServletResponse response)
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
            HashSet<DiscountItem> hs = new HashSet<>();
            // HashMap<String, Double> data = new HashMap<>();
            hs = MySqlDataStoreUtilities.getDiscountItem();
            utility.printHtml("Header.html");
            pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
            pw.print("<a style='font-size: 24px;'>Sales Report</a></h2>");
            pw.print("<div id='chart_div'></div>");
            pw.print("<div class='entry' >");
            pw.print(
                    "<table class='gridtable'><tr/><td>Product Sell Details</td><tr/><tr><td>Product Name</td><td>price</td><td>count</td><td>Total Sales</td></tr>");
            for (DiscountItem product : hs) {
                pw.print("<tr>");
                pw.print("<td>");
                pw.print(product.getName());
                pw.print("</td>");
                pw.print("<td>");
                pw.print(product.getPrice());
                pw.print("</td>");
                pw.print("<td>");
                pw.print(product.getCount());
                pw.print("</td>");
                pw.print("<td>");
                pw.print(product.getCount() * product.getPrice());
                pw.print("</td>");
                pw.print("</tr>");
                // data.put(product.getName(), product.getCount() * product.getPrice());
            }
            Gson gson = new Gson();
            String output = gson.toJson(hs);
            pw.print("</table></div>");
            HashSet<DailySale> sales = new HashSet<>();
            sales = MySqlDataStoreUtilities.getDailySale();
            pw.print("<div class='entry'>");
            pw.print(
                    "<table class='gridtable'><tr/><td>Product Sell by Date</td><tr/><tr><td>Purchase Date</td><td>Total Sale</td></tr>");
            for (DailySale sale : sales) {
                pw.print("<tr>");
                pw.print("<td>");
                pw.print(sale.getPurchaseDate());
                pw.print("</td>");
                pw.print("<td>");
                pw.print(sale.getSale());
                pw.print("</td>");
                pw.print("</tr>");
            }
            pw.print("</table>");
            pw.print("</div></div></div>");
            System.out.println(hs.size());
            pw.println("<script type='text/javascript' src='DataVisualizationSale.js' data='" + output + "'></script>");
            utility.printHtml("Footer.html");
        } catch (Exception e) {
        }
    }
}
