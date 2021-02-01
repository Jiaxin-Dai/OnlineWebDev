import java.io.Serializable;

public class Staff implements Serializable{
    private int sid;
    private String sname;
    private int ser_id;
    private String postcode;

    public Staff(int sid, String sname, int ser_id, String postcode) {
        this.sid = sid;
        this.sname = sname;
        this.ser_id = ser_id;
        this.postcode = postcode;
    }

    public int getSid() {
        return this.sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return this.sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public int getSer_id() {
        return this.ser_id;
    }

    public void setSer_id(int ser_id) {
        this.ser_id = ser_id;
    }

    public String getPostcode() {
        return this.postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

}
