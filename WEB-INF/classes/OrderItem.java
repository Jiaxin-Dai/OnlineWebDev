import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.io.*;

/* 
	Users class contains class variables id,name,password,usertype.

	Users class has a constructor with Arguments name, String password, String usertype.
	  
	Users  class contains getters and setters for id,name,password,usertype.

*/

public class OrderItem implements Serializable {
    private int oid;
    private int item_id;
    private double item_price;
    private String category;
    private int quantity;
    private int sid;
    private int scheduletime;

    public OrderItem(int oid, int item_id, double item_price, String category, int quantity, int sid,
            int scheduletime) {
        this.oid = oid;
        this.item_id = item_id;
        this.item_price = item_price;
        this.category = category;
        this.quantity = quantity;
        this.sid = sid;
        this.scheduletime = scheduletime;
    }

    public int getOid() {
        return this.oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getItem_id() {
        return this.item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public double getItem_price() {
        return this.item_price;
    }

    public void setItem_price(double item_price) {
        this.item_price = item_price;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSid() {
        return this.sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getScheduletime() {
        return this.scheduletime;
    }

    public void setScheduletime(int scheduletime) {
        this.scheduletime = scheduletime;
    }

}
