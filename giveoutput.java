import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class giveoutput {

	static Connection con;
	static

	private boolean hasDate, hasTime, hasArea, hasRoom;
	private static String sql;
	private static String from_clause = " FROM area a JOIN roombooking rb USING (area_id) JOIN room USING (room_id) ";
	private static Date date;
	private static Time time;
	private static String area_name;
	private static String room_name;
	private static boolean taken;

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
		extractJSONData();

		hasDate = (date == null);
		hasTime = (time == null);
		hasArea = (area_name == null);
		hasRoom = (room_name == null);
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
			sendinfo(rst.getDate(1),rst.getTime(2),rst.getString(3),rst.getString(4),rst.getBoolean(5));
		}


	}

	//Writing to JSON
	private static void sendinfo(Date date2, Time time2, String string, String string2, boolean b) {
		// TODO Auto-generated method stub
		
		//First Employee
		JSONObject infoDetails = new JSONObject();
		infoDetails.put("date", date2);
		infoDetails.put("time", time);
		infoDetails.put("area", string);
		infoDetails.put("room", string2);
		infoDetails.put("area", b);

		JSONObject infoObject = new JSONObject(); 
		infoObject.put("info", infoDetails);

		//Add employees to list
		JSONArray infoList = new JSONArray();
		infoList.add(infoObject);

		//Write JSON file
		try (FileWriter file = new FileWriter("data/results.json")) {

			file.write(infoList.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
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

static void extractJSONData() {

	//JSON parser object to parse read file
	JSONParser jsonParser = new JSONParser();

	try (FileReader reader = new FileReader("data/info.json"))
	{
		//Read JSON file
		Object obj = jsonParser.parse(reader);

		JSONArray info = (JSONArray) obj;
		System.out.println(info);

		//Iterate over employee array
		info.forEach( data -> parseData( (JSONObject) data ) );

	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
	}
}

static void parseData(JSONObject data) 
{
	//Get employee object within list
	JSONObject employeeObject = (JSONObject) data.get("data");

	//Get employee first name
	date = (Date) employeeObject.get("date");    
	System.out.println(date);

	//Get employee last name
	time = (Time) employeeObject.get("time");  
	System.out.println(time);

	//Get employee website name
	area_name = (String) employeeObject.get("area");    
	System.out.println(area_name);

	//Get employee website name
	room_name = (String) employeeObject.get("room");    
	System.out.println(room_name);

	//Get employee website name
	taken = (boolean) employeeObject.get("taken");    
	System.out.println(taken);

}
}
