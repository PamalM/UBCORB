import java.sql.*;

public class LinkedBooking {
	
	Date date;
	Time time;
	String area_name;
	String room_name;
	
	public LinkedBooking(Date d, Time t, String aname, String rname) {
		date = d;
		time = t;
		area_name = aname;
		room_name = rname;
	}

}
