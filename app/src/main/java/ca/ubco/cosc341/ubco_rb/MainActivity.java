package ca.ubco.cosc341.ubco_rb;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import java.lang.reflect.Array;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import android.widget.AdapterView;
import android.widget.Toast;
import android.view.Gravity;

public class MainActivity extends AppCompatActivity {

    //Widget objects; To be initialized later on.
    Button searchButton;
    Spinner buildingSpinner;
    Spinner roomSpinner;
    Spinner timeSpinner;

    //String list containing all the bookable buildings on campus.
    String[] buildings = new String[]{
            "Library",
            "Commons - Floor 0",
            "Commons - Floor 1",
            "Commons - Floor 3",
            "Commons - (Grad Student) Floor 3",
            "EME - Tower 1",
            "EME - Tower 2"
    };

    //String list containing all the bookable rooms on campus.
    //Rooms are respective to their order in the buildings[] list.
    String[][] rooms = {{"LIB 121","LIB 122"},
            {"COM 005","COM 006","COM 007", "COM 008"},
            {"COM 108", "COM 109", "COM 110", "COM 111", "COM 112", "COM 113", "COM 114", "COM 115", "COM 116", "COM 117", "COM 118", "COM 119", "COM 120", "COM 121"},
            {"COM 301", "COM 302", "COM 303", "COM 304", "COM 305", "COM 306", "COM 307", "COM 308", "COM 309", "COM 312", "COM 314", "COM 316", "COM 318"},
            {"310A", "310C"},
            {"EME 1162", "EME 1163", "EME 1164", "EME 1165", "EME 1166", "EME 1167", "EME 1168"},
            {"EME 1252", "EME 1254", "EME 2242", "EME 2244", "EME 2246", "EME 2248", "EME 2252", "EME 2254", "EME 2257"}};

    //List containing all the possible (start) times for booking a study room.
    String[] timeSlots = new String[]{
            "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00",
            "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00"
    };

    //Text-Entry fields that will be passed to conduct search query.
    String room, hours, minutes, building, am_Identifier, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find Search Button by id.
        searchButton = (Button) findViewById(R.id.searchButton);

        //Find Building Spinner by id.
        buildingSpinner = (Spinner) findViewById(R.id.buildingSpinner);

        //Find room Spinner by id.
        roomSpinner = (Spinner) findViewById(R.id.roomSpinner);

        //Find time Spinner by id.
        timeSpinner = (Spinner) findViewById(R.id.timeSpinner);

        //Populate the timeSpinner with it's values.
        List<String> timeArray =  new ArrayList<String>(Arrays.asList(timeSlots));
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeArray){};
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(adp);

        //Populate the buildingSpinner with it's values.
        List<String> buildingArray =  new ArrayList<String>(Arrays.asList(buildings));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, buildingArray){

            @Override
            //Disable the first value of the list; It is to be used as a "hint".
            public boolean isEnabled(int position){ if(position == 0) { return false; } else { return true; } }

        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildingSpinner.setAdapter(adapter);

        buildingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // Disable the first item and display as a "hint".

                if(true){
                    // Notify (Toast) the user about their building selection.
                    Toast.makeText(getApplicationContext(), "Building: " + selectedItemText, Toast.LENGTH_LONG).show();

                    //Variable to identify which elements of the rooms[][] list to show.
                    //Library = 0
                    //Commons - Floor 0 = 1
                    //Commons - Floor 1 = 2
                    //Commons - Floor 3 = 3
                    //Commons - (Grad Student) Floor 3 = 4
                    //EME - Tower 1 = 5
                    //EME - Tower 2 = 6
                    int identify = 0;

                    //Perform a switch to update the second [rooms] list according to the building selection.
                    switch (selectedItemText){

                        case "Library":
                            identify = 0;
                            break;

                        case "Commons - Floor 0":
                            identify = 1;
                            break;

                        case "Commons - Floor 1":
                            identify = 2;
                            break;

                        case "Commons - Floor 3":
                            identify = 3;
                            break;

                        case "Commons - (Grad Student) Floor 3":
                            identify = 4;
                            break;

                        case "EME - Tower 1":
                            identify = 5;
                            break;

                        case "EME - Tower 2":
                            identify = 6;
                            break;

                    }

                    //List to be used to populate the second spinner.
                    //Populate the list to include all the rooms for length identify (building Tag).
                    List<String> populateRooms =  new ArrayList<String>();

                    //Populate the list with values from rooms list above.
                    for(int subIndex = 0; subIndex < rooms[identify].length; ++subIndex) { populateRooms.add(rooms[identify][subIndex]); }
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, populateRooms){};
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    roomSpinner.setAdapter(adapter2);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do nothing; Literally.
            }

        });

        //Set search button onClick event handler.
        searchButton.setOnClickListener(new View.OnClickListener() {

            //Method will execute when user clicks search button.
            //Grab current values in all text boxes/Spinners, etc; Save them into variables to be passed to intent.
            @Override
            public void onClick(View v) {

                //Fetch values from widgets to be passed into intent.
                building = buildingSpinner.getSelectedItem().toString();
                room = roomSpinner.getSelectedItem().toString();
                hours = timeSpinner.getSelectedItem().toString().substring(0,2);
                minutes = timeSpinner.getSelectedItem().toString().substring(3,5);

                //Output selections to terminal (For testing).
                System.out.println("Building: " + building.toString());
                System.out.println("Room: " + room.toString());
                System.out.println("Time [H]: " + hours.toString() + " Time [M]: " + minutes.toString());
            }
        });
    }

    //Collect and pass the entered values from the text views onto search query for processing.
    public void passForQuerying(String date, String hours, String minutes, String building, String room) {

        //Print the entered values on terminal.
        System.out.println("Room: " + room);
        System.out.println("Building: " + building);
        System.out.println("Hours: " + hours);
        System.out.println("Minutes: " + minutes);
        System.out.println("Date: " + date);

        //After printing results, open a new activity (page) for resultSet.
        Intent intent = new Intent(this, resultPage.class);
        startActivity(intent);

    }
}


