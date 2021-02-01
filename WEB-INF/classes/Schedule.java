import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.io.*;

public class Schedule implements Serializable {
    private int sid;
    private int worktime;
    private Date date;

    public Schedule(int sid, int worktime, Date date) {
        this.sid = sid;
        this.worktime = worktime;
        this.date = date;
    }

    public int getSid() {
        return this.sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getWorktime() {
        return this.worktime;
    }

    public void setWorktime(int worktime) {
        this.worktime = worktime;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
