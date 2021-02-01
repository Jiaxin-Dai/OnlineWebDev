import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



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

public class Service implements Serializable {
    private int id;
    private String serviceName;
    private String type;
    private double price;
    private String description;
    private String image;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Service(int id, String serviceName, String type, double price, String description, String image) {
        this.id = id;
        this.serviceName = serviceName;
        this.type = type;
        this.price = price;
        this.description = description;
        this.image = image;
    }

   
}
