import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DiscountItem")

/*
 * Tablet class contains class variables
 * name,price,image,retailer,condition,discount.
 * 
 * Tablet class has a constructor with Arguments
 * name,price,image,retailer,condition,discount.
 * 
 * Tablet class contains getters and setters for
 * name,price,image,retailer,condition,discount.
 */

public class DiscountItem extends HttpServlet {
    private String productname;
    private double price;
    private int count;

    public DiscountItem(String productname, double price, int count) {
        this.productname = productname;
        this.price = price;
        this.count = count;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return this.productname;
    }

}
