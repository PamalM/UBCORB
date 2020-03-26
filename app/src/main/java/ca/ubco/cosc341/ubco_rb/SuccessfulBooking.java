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
        String date = intent.getStringExtra("Date");
        String title = intent.getStringExtra("Title");
        String email = intent.getStringExtra("Email");
        String bookingId = intent.getStringExtra("bookingId");

        //Find the labels by their id.
        TextView buildingLabel = findViewById(R.id.buildingLabel);
        TextView roomLabel = findViewById(R.id.roomLabel);
        TextView timeLabel = findViewById(R.id.timeLabel);
        TextView dateLabel = findViewById(R.id.dateLabel);
        TextView durationLabel = findViewById(R.id.durationLabel);
        TextView emailLabel = findViewById(R.id.emailLabel);
        TextView titleLabel = findViewById(R.id.titleLabel);
        TextView bookingIdLabel = findViewById(R.id.bookingIdLabel);

        //Set the labels to their respective values from previous mainActivity.java window.
        buildingLabel.setText(building);
        dateLabel.setText(date);
        roomLabel.setText(room);
        durationLabel.setText(duration);
        emailLabel.setText(email);
        titleLabel.setText(title);
        bookingIdLabel.setText(bookingId);
        timeLabel.setText(time);

    }

    public void backToMain(View view){

        //Return button onclick method returns the user back to the mainactivity.java view.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
