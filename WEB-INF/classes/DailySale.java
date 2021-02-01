import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DailySale")

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

public class DailySale extends HttpServlet {
    private Date purchaseDate;
    private double totalSale;

    public DailySale(Date purchaseDate, double sale) {
        this.purchaseDate = purchaseDate;
        this.totalSale = sale;
    }


    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public double getSale() {
        return totalSale;
    }

    public void setSale(double totalSale) {
        this.totalSale = totalSale;
    }
}
