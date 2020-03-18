package ca.ubco.cosc341.ubco_rb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.View;
import android.widget.*;

import java.util.Hashtable;

public class SuccessfulBooking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_booking);

        //Bundle to receive booking information from previous activities.
        Intent intent = getIntent();
        String building = intent.getStringExtra("Building");
        String room = intent.getStringExtra("Room");
        String time = intent.getStringExtra("Time");
        String duration = intent.getStringExtra("Duration");
        String type = intent.getStringExtra("Type");
        String date = intent.getStringExtra("Date");
        String title = intent.getStringExtra("Title");
        String email = intent.getStringExtra("Email");

        //Hashmap to determine AM/PM indicator on timeLabel.
        Hashtable<String, String>  timeSlots = new Hashtable<String, String>();
        timeSlots.put("07", "7");
        timeSlots.put("08", "8");
        timeSlots.put("07", "8");
        timeSlots.put("09", "9");
        timeSlots.put("10", "10");
        timeSlots.put("11", "11");
        timeSlots.put("12", "12");
        timeSlots.put("13", "1");
        timeSlots.put("14", "2");
        timeSlots.put("15", "3");
        timeSlots.put("16", "4");
        timeSlots.put("17", "5");
        timeSlots.put("18", "6");
        timeSlots.put("19", "7");
        timeSlots.put("20", "8");
        timeSlots.put("21", "9");
        timeSlots.put("22", "10");

        //Find the labels by their id.
        TextView buildingLabel = findViewById(R.id.buildingLabel);
        TextView roomLabel = findViewById(R.id.roomLabel);
        TextView timeLabel = findViewById(R.id.timeLabel);
        TextView dateLabel = findViewById(R.id.dateLabel);
        TextView durationLabel = findViewById(R.id.durationLabel);
        TextView emailLabel = findViewById(R.id.emailLabel);
        TextView titleLabel = findViewById(R.id.titleLabel);

        buildingLabel.setText(building);
        dateLabel.setText(date);
        roomLabel.setText(room);
        durationLabel.setText(duration);
        emailLabel.setText(email);
        titleLabel.setText(title);

        //Determine the 12 Hour format display for time output.
        String hour = time.substring(0,2);
        String minutes = time.substring(3,time.length());
        String twelveHourFormat;
        if (Integer.parseInt(hour) <= 22 && Integer.parseInt(hour) >= 12){ twelveHourFormat = timeSlots.get(hour) + ":" + minutes + " PM"; }
        else{ twelveHourFormat = timeSlots.get(hour) + ":" + minutes + " AM"; }
        timeLabel.setText(twelveHourFormat);

    }

    public void backToMain(View view){

        //Return button onclick method returns the user back to the mainactivity.java view.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
