import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.io.*;

/* 
	Users class contains class variables id,name,password,usertype.
	Users class has a constructor with Arguments name, String password, String usertype.
	  
	Users  class contains getters and setters for id,name,password,usertype.
*/

public class Order implements Serializable {
    private int oid;
    private Date otime;
    private Double totalprice;
    private String creditcard;
    private String address;
    private String postcode;
    private int uid;

    public Order(int oid, Date otime, Double totalprice, String creditcard, String address, String postcode, int uid) {
        this.oid = oid;
        this.otime = otime;
        this.totalprice = totalprice;
        this.creditcard = creditcard;
        this.address = address;
        this.postcode = postcode;
        this.uid = uid;
    }

    public int getOid() {
        return this.oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public Date getOtime() {
        return this.otime;
    }

    public void setOtime(Date otime) {
        this.otime = otime;
    }

    public Double getTotalprice() {
        return this.totalprice;
    }

    public void setTotalprice(Double totalprice) {
        this.totalprice = totalprice;
    }

    public String getCreditcard() {
        return this.creditcard;
    }

    public void setCreditcard(String creditcard) {
        this.creditcard = creditcard;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return this.postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

}