import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;

public class Product implements Serializable {
    private int id;
    private String productName;
    private String type;
    private double price;
    private int stock;
    private String desc;
    private String image;
    private String brand;

    public Product(int id, String productName, String type, double price, int stock, String desc, String image,
            String brand) {
        this.id = id;
        this.productName = productName;
        this.type = type;
        this.price = price;
        this.stock = stock;
        this.desc = desc;
        this.image = image;
        this.brand = brand;
    }

    public Product() {
    	
    }
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public int getStock() {
        return this.stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

}
