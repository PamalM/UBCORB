package ca.ubco.cosc341.ubco_rb;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.content.Context;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    //Widgets.
    EditText dateEntry, hhEntry, mmEntry, timeOfDayEntry, buildingEntry, roomEntry;
    Button sumbitButton;

    //Text-Entry fields that will be passed to conduct search query.
    String room, hours, minutes, building, am_Identifier, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find textViews with ids.
        dateEntry = (EditText) findViewById(R.id.dateEntry);
        hhEntry = (EditText) findViewById(R.id.hhEntry);
        mmEntry = (EditText) findViewById(R.id.mmEntry);
        timeOfDayEntry = (EditText) findViewById(R.id.timeOfDayEntry);
        buildingEntry = (EditText) findViewById(R.id.buildingEntry);
        roomEntry = (EditText) findViewById(R.id.roomEntry);

        //Find button with id.
        sumbitButton = (Button) findViewById(R.id.submitButton);

        //Set search button onClick event handler.
        sumbitButton.setOnClickListener(new View.OnClickListener() {

            //Method will execute when user clicks search button.
            //Grab current values in all text boxes, and save into variables.
            @Override
            public void onClick(View v) {
                //Fetch and save the entered values into variables to be passed on later.
                room = roomEntry.getText().toString();
                hours = hhEntry.getText().toString();
                minutes = mmEntry.getText().toString();
                am_Identifier = timeOfDayEntry.getText().toString();
                building = buildingEntry.getText().toString();
                room = roomEntry.getText().toString();
                date = dateEntry.getText().toString();

                //Pass saved values onto method to be processed/printed to terminal and sent over to the search query.
                passForQuerying(date, hours, minutes, am_Identifier, building, room);
            }
        });
    }

    //Collect and pass the entered values from the text views onto search query for processing.
    public void passForQuerying(String date, String hours, String minutes, String am_Identifier, String building, String room) {
        //Print the entered values on terminal.
        System.out.println("Room: " + room);
        System.out.println("Building: " + building);
        System.out.println("Hours: " + hours);
        System.out.println("Minutes: " + minutes);
        System.out.println("AM/PM: " + am_Identifier);
        System.out.println("Date: " + date);

        //After printing results, open a new activity (page) for resultSet.
        Intent intent = new Intent(this, resultPage.class);
        startActivity(intent);

    }
}


