package ca.ubco.cosc341.ubco_rb;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;

import java.lang.reflect.Array;
import java.util.*;
import android.widget.*;
import java.text.SimpleDateFormat;
import android.net.Uri;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static java.lang.System.in;

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

    Booking booking;

    //List containing all the bookable study buildings on campus.
    //Utilize this list to fill the spinner values.
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
            "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30"};

    //List containing all the possible (duration) times for booking a study room.
    String[] durationSlots = new String[] {"30 Minutes", "1 Hour", "1.5 Hours", "2 Hours"};

    //We will preset the type to 'Study Group' regardless. No one really books the other option.
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

        durationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Position [0] = 30 Minutes.
                //Position [1] = 1 Hour.
                //Position [2] = 1.5 Hours.
                //Position [3] = 2 Hours.

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do nothing; Literally.
            }

        });

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

        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Position [0] = 30 Minutes.
                //Position [1] = 1 Hour.
                //Position [2] = 1.5 Hours.
                //Position [3] = 2 Hours.

                //Update durations depending on the time-slots selected.
                //ArrayList is good because it can dynamically change.
                //Bookings are only valid from 07:00 - 22:00. So we have to ensure booking duration stays within these limits.
                List<String> updateDurations;

                //If Time = 20:30, we have to restrict certain booking durations.
                if (position == 27){
                    updateDurations = new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(durationSlots, 0, 3)));
                }

                else if (position == 28){
                    updateDurations = new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(durationSlots, 0, 2)));
                }

                else if (position == 29){
                    updateDurations = new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(durationSlots, 0, 1)));
                }

                //Otherwise include all the booking durations.
                else {
                    updateDurations = new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(durationSlots, 0, 4)));
                }

                //Update duration spinner values accordingly.
                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, updateDurations){};
                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                durationSpinner.setAdapter(adapter3);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do nothing; Literally.
            }

        });

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

                    //Determine the end time for the booking depending on the duration.
                    String endTime = "";

                    String hours = time.substring(0,2);
                    String minutes = time.substring(3,time.length());

                    int hours_Int = Integer.parseInt(hours);
                    int minutes_Int = Integer.parseInt(minutes);

                    if (duration == "2 Hours"){
                        hours_Int = hours_Int + 2;
                    }

                    else if (duration == "1 Hours"){
                        hours_Int = hours_Int + 1;
                    }

                    else if (duration == "30 Minutes"){

                        if (minutes_Int == 30){
                            minutes = "00";
                            hours_Int = hours_Int + 1;
                        }

                        else { minutes = "30"; }

                    }

                    else if (duration == "1.5 Hours"){

                        if (minutes_Int == 30){
                            minutes = "00";
                            hours_Int = hours_Int + 1;
                            hours_Int = hours_Int + 1;
                        }

                        else {

                            minutes = "30";
                            hours_Int = hours_Int + 1;

                        }

                    }

                    endTime = hours_Int + "" + minutes;
                    time = Integer.parseInt(time.substring(0, 2)) + "" +time.substring(3, time.length());

                    //Hashtable to format the startTime and endTime accordingly to pass information to database.
                    Hashtable<String, String>  timeSlots = new Hashtable<String, String>();
                    timeSlots.put("700", "7:00 AM");
                    timeSlots.put("730", "7:30 AM");
                    timeSlots.put("800", "8:00 AM");
                    timeSlots.put("830", "8:30 AM");
                    timeSlots.put("900", "9:00 AM");
                    timeSlots.put("930", "9:30 AM");
                    timeSlots.put("1000", "10:00 AM");
                    timeSlots.put("1030", "10:30 AM");
                    timeSlots.put("1100", "11:00 AM");
                    timeSlots.put("1130", "11:30 AM");
                    timeSlots.put("1200", "12:00 PM");
                    timeSlots.put("1230", "12:30 PM");
                    timeSlots.put("1300", "1:00 PM");
                    timeSlots.put("1330", "1:30 PM");
                    timeSlots.put("1400", "2:00 PM");
                    timeSlots.put("1430", "2:30 PM");
                    timeSlots.put("1500", "3:00 PM");
                    timeSlots.put("1530", "3:30 PM");
                    timeSlots.put("1600", "4:00 PM");
                    timeSlots.put("1630", "4:30 PM");
                    timeSlots.put("1700", "5:00 PM");
                    timeSlots.put("1730", "5:30 PM");
                    timeSlots.put("1800", "6:00 PM");
                    timeSlots.put("1830", "6:30 PM");
                    timeSlots.put("1900", "7:00 PM");
                    timeSlots.put("1930", "7:30 PM");
                    timeSlots.put("2000", "8:00 PM");
                    timeSlots.put("2030", "8:30 PM");
                    timeSlots.put("2100", "9:00 PM");
                    timeSlots.put("2130", "9:30 PM");
                    timeSlots.put("2200", "10:00 PM");

                    String startTime = timeSlots.get(time);
                    String eTime = timeSlots.get(endTime);

                    passForQuerying(building, room, startTime, duration, eTime, date, type, title, email);

                }
            }
        });
    }

    //Collect and pass the entered values from the text views onto search query for processing.
    public void passForQuerying(String building, String room, String startTime, String duration, String endTime, String date, String type, String tite, String email) {

        //Firebase Database initiation.
        //We will be utilizing the Firebase database platform to conduct booking/testing of study rooms.
        //This public open source database allows all android users to connect to this database and query results.
        //The Booking.java class handles the object creation and passing information to our firebase database.

        Intent intent;

        String formatDate = date.replace("/", "-");

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child(formatDate).child(building).child(room);

        //Create booking object and set the object's attributes.
        booking = new Booking();
        booking.setBuilding(building);
        booking.setRoom(room);
        booking.setStartTime(startTime);
        booking.setDuration(duration);
        booking.setEndTime(endTime);
        booking.setDate(formatDate);
        booking.setType(type);
        booking.setTitle(title);
        booking.setEmail(email);

        final String bookingName = startTime + "-" + endTime;

        if (true){

            //Submit booking to the database.
            reff.child(bookingName).setValue(booking);

            //Successful Booking.
            intent = new Intent(this, SuccessfulBooking.class);
        }

        else {
            //Error Booking.
            intent = new Intent(this, ErrorBooking.class);
        }

        intent.putExtra("Building", building);
        intent.putExtra("Room", room);
        intent.putExtra("Time", time);
        intent.putExtra("Duration", duration);
        intent.putExtra("Type", type);
        intent.putExtra("Date", date);
        intent.putExtra("Title", title);
        intent.putExtra("Email", email);
        startActivity(intent);

    }
}


