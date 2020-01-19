import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;


/**
 * Loads the order database using code.
 */
public class LoadData
{
	
	static Connection con;
	static String sql;
	
	
	//main
	public static void main(String[] args) throws Exception
	{
		connect();			//connect to db
		loadData();			//load the db
		insertData();		//insert data into db
		disconnect();		//disconnect from db
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

	private static void insertData() {
		// TODO Auto-generated method stub
		System.out.println("Inserting Data...");
		
		extractJSONData();
	}
	
	private static void extractJSONData() {
		
		//JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
         
        try (FileReader reader = new FileReader("data/info.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONArray info = (JSONArray) obj;
            System.out.println(info);
             
            //Iterate over employee array
            info.forEach( emp -> parseData( (JSONObject) emp ) );
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
	}
	
	private static void parseData(JSONObject data) 
    {
        //Get employee object within list
        JSONObject employeeObject = (JSONObject) data.get("data");
         
        //Get employee first name
        Date date = (Date) employeeObject.get("date");    
        System.out.println(date);
         
        //Get employee last name
        Time time = (Time) employeeObject.get("time");  
        System.out.println(time);
         
        //Get employee website name
        String area = (String) employeeObject.get("area");    
        System.out.println(area);
        
        //Get employee website name
        String room = (String) employeeObject.get("room");    
        System.out.println(room);
        
      //Get employee website name
        boolean taken = (boolean) employeeObject.get("taken");    
        System.out.println(taken);
        
        try {
        insertintodb(date,time,area,room,taken);
        
        }catch(SQLException e) {e.printStackTrace();}
    }

	private static void insertintodb(Date date, Time time, String area, String room, boolean taken) throws SQLException{
		// TODO Auto-generated method stub
		sql = "insert into roombooking values (?,?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setDate(1, date);
		pstmt.setTime(2, time);
		pstmt.setString(3, area);
		pstmt.setString(4, room);
		pstmt.setBoolean(5, taken);
		
		pstmt.executeUpdate();
		
	}

	public static void loadData() throws Exception
	{		
				
		String fileName = "data/orderdb_sql.ddl";
		
		//FOREIGN KEY (room_id) REFERENCES room(room_id)
		
	    try
	    {
	        // Create statement
	        Statement stmt = con.createStatement();
	        
	        Scanner scanner = new Scanner(new File(fileName));
	        // Read commands separated by ;
	        scanner.useDelimiter(";");
	        while (scanner.hasNext())
	        {
	            String command = scanner.next();
	            if (command.trim().equals(""))
	                continue;
	            System.out.println(command);        // Uncomment if want to see commands executed
	            try
	            {
	            	stmt.execute(command);
	            }
	            catch (Exception e)
	            {	// Keep running on exception.  This is mostly for DROP TABLE if table does not exist.
	            	System.out.println(e);
	            }
	        }	 
	        scanner.close();
	        
	        System.out.println("Database loaded.");
	    }
	    catch (Exception e)
	    {
	        System.out.println(e);
	    }   
	}
}
