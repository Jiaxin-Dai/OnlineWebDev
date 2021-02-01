import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.io.*;

/* 
	Users class contains class variables id,name,password,usertype.
	Users class has a constructor with Arguments name, String password, String usertype.
	  
	Users  class contains getters and setters for id,name,password,usertype.
*/

public class UnavailableTime implements Serializable {
	private Date unavilableDate;
	private int unavilableTimeFrame;
	
	
	public UnavailableTime(Date unavilableDate, int unavilableTimeFrame) {
		this.unavilableDate = unavilableDate;
		this.unavilableTimeFrame = unavilableTimeFrame;
	}


	public int getUnavilableTimeFrame() {
		return unavilableTimeFrame;
	}


	public void setUnavilableTimeFrame(int unavilableTimeFrame) {
		this.unavilableTimeFrame = unavilableTimeFrame;
	}


	public Date getUnavilableDate() {
		return unavilableDate;
	}


	public void setUnavilableDate(Date unavilableDate) {
		this.unavilableDate = unavilableDate;
	}
	
	
	
}