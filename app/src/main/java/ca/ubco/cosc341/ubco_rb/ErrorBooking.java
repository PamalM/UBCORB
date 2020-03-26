package ca.ubco.cosc341.ubco_rb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import android.os.Bundle;

import java.util.Hashtable;

public class ErrorBooking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_booking);

        //Retrieve booking information.
        Intent intent = getIntent();
        String building = intent.getStringExtra("Building");
        String room = intent.getStringExtra("Room");
        String time = intent.getStringExtra("Time");
        String duration = intent.getStringExtra("Duration");
        String type = intent.getStringExtra("Type");
        String date = intent.getStringExtra("Date");
        String title = intent.getStringExtra("Title");
        String email = intent.getStringExtra("Email");

        TextView buildingLabel, timeLabel, roomLabel, dateLabel, durationLabel;
        buildingLabel = findViewById(R.id.buildingLabel);
        timeLabel = findViewById(R.id.timeLabel);
        dateLabel = findViewById(R.id.dateLabel);
        roomLabel = findViewById(R.id.roomLabel);

        //Hashmap to convert to 12hr time format.
        Hashtable<String, String> timeSlots = new Hashtable<String, String>();
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

        buildingLabel.setText(building);
        roomLabel.setText(room);
        dateLabel.setText("On, " + date);
        timeLabel.setText(time);

    }

    public void backToMain(View view){

        //Return button onclick method returns the user back to the mainactivity.java view.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}
