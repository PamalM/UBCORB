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
	private static String from_clause = " FROM roombooking rb ";
	private static String date;
	private static String time;
	private static String area_name;
	private static String room_name;
	
	private static JSONObject employeeObject;
	private static JSONArray resultarray= new JSONArray();

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

		hasDate = (date != null);
		hasTime = (time != null);
		hasArea = (area_name != null);
		hasRoom = (room_name != null);
	}

	private static void gettimeslots() {
		// TODO Auto-generated method stub
		if(hasDate) {
			sql = "Select rb.area_name, rb.room_name, rb.booking_time" + from_clause + "where taken = 'false' and booking_date = ?";
			
			if(hasTime)
				sql = sql + " and booking_time = ?";
			/*
			if(hasArea)
				sql = sql + " and area_name = ?";
			
			if(hasRoom)
				sql = sql + " and room_name = ?";
				*/
			
			System.out.println(sql);
		}

	}

	private static void setoutput() throws SQLException {
		System.out.println("hi");
		// TODO Auto-generated method stub
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, date);
		if(hasTime)
		stmt.setString(2, time);
		/*if(hasArea)
			stmt.setString(3, area_name);
		if(hasRoom)
			stmt.setString(4, room_name);
			*/
		
		ResultSet rst = stmt.executeQuery();
		

		System.out.println("hi");
		while(rst.next()) {
			System.out.println(rst.getString(1));
			//sends the info from the query to the application
			System.out.println(rst.getString(1) + " " + rst.getString(2) + " " + rst.getString(3));

			sendinfo(rst.getString(1),rst.getString(2), rst.getString(3));
			}
		System.out.println("hi");


	}

	//Writing to JSON
	private static void sendinfo(String area, String room, String time) {
		System.out.println("hi");
		// TODO Auto-generated method stub
		
		//First Employee
		JSONObject infoDetails = new JSONObject();
		infoDetails.put("area", area);
		infoDetails.put("room", room);
		infoDetails.put("time", time);
		
		System.out.println(area + " " + room + " " + time);

		//Add employees to list
		resultarray.add(infoDetails);
		
		System.out.println(resultarray.toString());

		//Write JSON file
		try (FileWriter file = new FileWriter("data/results.json")) {

			file.write(resultarray.toJSONString());
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
         
        try (FileReader reader = new FileReader("data/info_needed.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            employeeObject = (JSONObject) obj;
            //System.out.println(info);
             
            //Iterate over employee array
            //info.forEach(data -> parseData() );
            
            parseData();

            
            //Foreign Key (area_name) references area(area_name)
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
	}

static void parseData() 
{
		
     
    //Get employee first name
    date = (String) employeeObject.get("date");    
    System.out.println(date);
     
    //Get employee last name
    time = (String) employeeObject.get("time");  
    System.out.println(time);
     
    //Get employee website name
    area_name = (String) employeeObject.get("area");    
    System.out.println(area_name);
    
    //Get employee website name
    room_name = (String) employeeObject.get("room");    
    System.out.println(room_name);
    
}

}
