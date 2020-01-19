import java.sql.*;

public class giveoutput {
	
	static Connection con;
	static
	
	private boolean hasDate;
	private static boolean hasTime;
	private static boolean hasArea;
	private static boolean hasRoom;
	private static String sql;
	private static String from_clause = " FROM area a JOIN roombooking rb USING (area_id) JOIN room USING (room_id) ";
	private static Date date;
	private static Time time;
	private static int area_name;
	private static int room_name;
	
	public static void main(String[] args) throws Exception
	{
		connect();			//connect to db
		getoutput();		//get the output
		disconnect();		//disconnect from db
	}

	private static void getoutput() throws SQLException {
		// TODO Auto-generated method stub
		checkParameters();		//prepare the parameters needed
		gettimeslots();			//get the empty time slots
		setoutput();			//return results to app
		
	}

	private static void checkParameters() {
		// TODO Auto-generated method stub
		
	}

	private static void gettimeslots() {
		// TODO Auto-generated method stub
		if(hasDate) {
			sql = "Select room_name, area_name" + from_clause + "where taken = false and date = " + date;
			if(hasTime)
				sql = sql + " time = " + time;
			if(hasArea)
				sql = sql + " area_name = " + area_name;
			if(hasRoom)
				sql = sql + " room_name = " + room_name;
		}
		
	}

	private static void setoutput() throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rst = stmt.executeQuery(sql);
		
		while(rst.next()) {
			//sends the info from the query to the application
			sendinfo(rst.getDate(1),rst.getTime(2),rst.getString(3),rst.getString(4),rst.getString(5));
		}
		
		
	}

	private static void sendinfo(Date date2, Time time2, String string, String string2, String string3) {
		// TODO Auto-generated method stub
		
	}

	private static void disconnect() throws SQLException {
		// TODO Auto-generated method stub
		con.close();
	}

	private static void connect() throws Exception {
		// TODO Auto-generated method stub
		String url = "jdbc:sqlserver://sql04.ok.ubc.ca:1433;DatabaseName=db_asabry;";
		String uid = "asabry";
		String pw = "99025207";
		System.out.println("Connecting to database.");

		con = DriverManager.getConnection(url, uid, pw);
	}
}
