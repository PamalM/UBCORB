package ca.ubco.cosc341.ubco_rb;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;
import android.net.Uri;

public class MainActivity extends AppCompatActivity {

    //Widget objects; To be initialized later in execution.
    Button searchButton;
    Spinner buildingSpinner;
    Spinner roomSpinner;
    Spinner timeSpinner;
    Spinner durationSpinner;
    Spinner typeSpinner;
    TextView dateTag;
    TextView emailText;
    TextView titleText;

    //List containing all the bookable study buildings on campus.
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
    //Rooms are respective to their order in the buildings[] list;
    //Where Library = [0], Commons - Floor 0 = [1], ..., and EME-Tower 2 = [6].
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

    //List containing all the possible (duration) times for booking a study room.
    String[] durationSlots = new String[] {"30 Minutes", "1 Hour", "1.5 Hours", "2 Hours"};

    String[] types = new String[] {"Study Group", "Instructional/Workshop"};

    //Values that will be collected from the user, and passed into intent object to conduct study room search.
    String building, room, time, duration, type, date, email, title;

    //Widget retrieves the date from the user when they click the 'Select Date' TextView.
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find Search Button by id.
        searchButton = findViewById(R.id.searchButton);

        //Find Building Spinner by id.
        buildingSpinner = findViewById(R.id.buildingSpinner);

        //Find room Spinner by id.
        roomSpinner = findViewById(R.id.roomSpinner);

        //Find time Spinner by id.
        timeSpinner = findViewById(R.id.timeSpinner);

        //Find duration spinner by id.
        durationSpinner = findViewById(R.id.durationSpinner);

        //Find type spinner by id.
        typeSpinner = findViewById(R.id.typeSpinner);

        //Find email textview by id.
        emailText = findViewById(R.id.emailText);

        //Find Title textview by id.
        titleText = findViewById(R.id.titleText);

        //Populate duration Spinner with list.
        List<String> typeArray =  new ArrayList<String>(Arrays.asList(types));
        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeArray){};
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adp2);

        //Populate duration Spinner with list.
        List<String> durationArray =  new ArrayList<String>(Arrays.asList(durationSlots));
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, durationArray){};
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationSpinner.setAdapter(adp1);

        //Find date widget by it's id.
        dateTag = findViewById(R.id.datePicker);

        //Create date string to display in date TextView upon Load.
        String todays_Date = new SimpleDateFormat("MMM/dd/yyyy", Locale.getDefault()).format(new Date());
        dateTag.setText(todays_Date);

        //Set onClickListener for when 'Select Date' text is clicked; Transition to datePicker view.
        dateTag.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){

                //Get MM-DD-YYYY preference from user.
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                //Draw widget and collect input from user.
                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }

        });

        //Initialize dateSetListener.
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //January = 0 ... December = 11.
                month += 1;
                //System.out.println(year + "/" + month + "/" + dayOfMonth);

                //HashTable utilized to print the month according to the input.
                Hashtable<Integer, String> monthTrack = new Hashtable<Integer, String>();
                monthTrack.put(1, "Jan");
                monthTrack.put(2, "Feb");
                monthTrack.put(3, "Mar");
                monthTrack.put(4, "Apr");
                monthTrack.put(5, "May");
                monthTrack.put(6, "June");
                monthTrack.put(7, "July");
                monthTrack.put(8, "Aug");
                monthTrack.put(9, "Sept");
                monthTrack.put(10, "Oct");
                monthTrack.put(11, "Nov");
                monthTrack.put(12, "Dec");

                //Update the dateText with the user selected date.
                //Format to append zero to dayOfMonth if it's less than 9.
                String dateText;
                if (dayOfMonth <= 9){ dateText = monthTrack.get(month) + "/0" + dayOfMonth + "/" + year; }
                else{ dateText = monthTrack.get(month) + "/" + dayOfMonth + "/" + year; }

                //Update text after formatting is complete.
                dateTag.setText(dateText);
            }
        };

        //Populate the timeSpinner with it's values.
        List<String> timeArray =  new ArrayList<String>(Arrays.asList(timeSlots));
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeArray){};
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(adp);

        //Populate the buildingSpinner with it's values.
        List<String> buildingArray =  new ArrayList<String>(Arrays.asList(buildings));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, buildingArray){};
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildingSpinner.setAdapter(adapter);

        buildingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if(true){
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

                //Ensure all fields are complete before submitting book request.

                if (!(titleText.getText().length() > 0) || !(emailText.getText().length() > 0)) {
                    Toast.makeText(getApplicationContext(), "Fill all fields to complete booking!", Toast.LENGTH_LONG).show();
                }

                else {

                    //Fetch values from widgets to be passed into intent.
                    //String building, room, time, duration, type, date, email, title; (Recall this variables. Initialize them and pass them to conduct search).
                    building = buildingSpinner.getSelectedItem().toString();
                    room = roomSpinner.getSelectedItem().toString();
                    time = timeSpinner.getSelectedItem().toString();
                    duration = durationSpinner.getSelectedItem().toString();
                    type = typeSpinner.getSelectedItem().toString();
                    date = dateTag.getText().toString();
                    email = emailText.getText().toString();
                    title = titleText.getText().toString();

                    passForQuerying(building, room, time, duration, type, date, email, title);

                }
            }
        });
    }

    //Collect and pass the entered values from the text views onto search query for processing.
    public void passForQuerying(String building, String room, String time, String duration, String type, String date, String email, String title) {

        //Print the entered values on terminal.
        System.out.println("BOOKING BUILDING: " + building);
        System.out.println("BOOKING ROOM: " + room);
        System.out.println("BOOKING START TIME: " + time);
        System.out.println("BOOKING DURATION: " + duration);
        System.out.println("BOOKING TYPE: " + type);
        System.out.println("BOOKING DATE: " + date);
        System.out.println("EMAIL FOR BOOKING: " + email);
        System.out.println("BOOKING TITLE: " + title);

        Uri uri = Uri.parse("https://bookings.ok.ubc.ca/studyrooms/admin.php");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

    }
}


