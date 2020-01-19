package ca.ubco.cosc341.ubco_rb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.content.Context;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    EditText dateEntry, hhEntry, mmEntry, timeOfDayEntry, buildingEntry, roomEntry;
    Button sumbitButton;

    String room, hours, minutes, building, am_Identifier, date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Finding the widgets by their appropriate ids.
        dateEntry = (EditText) findViewById(R.id.dateEntry);
        hhEntry = (EditText) findViewById(R.id.hhEntry);
        mmEntry = (EditText) findViewById(R.id.mmEntry);
        timeOfDayEntry = (EditText) findViewById(R.id.timeOfDayEntry);
        buildingEntry = (EditText) findViewById(R.id.buildingEntry);
        roomEntry = (EditText) findViewById(R.id.roomEntry);

        sumbitButton = (Button) findViewById(R.id.submitButton);

        sumbitButton.setOnClickListener(new View.OnClickListener() {

            //Method will execute when user clicks search button.
            //Grab current values in all text boxes, and save into variables.
            @Override
            public void onClick(View v) {

                room = roomEntry.getText().toString();
                hours = hhEntry.getText().toString();
                minutes = mmEntry.getText().toString();
                am_Identifier = timeOfDayEntry.getText().toString();
                building = buildingEntry.getText().toString();
                room = roomEntry.getText().toString();
                date = dateEntry.getText().toString();

                writeToFile(date, hours, minutes, am_Identifier, building, room);

            }
        });
    }

    //Pass the values of the user's text boxes to the method to write to a text file to be passed onto loadJava.
    public void writeToFile(String date, String hours, String minutes, String am_Identifier, String building, String room) {

        System.out.println("Writing to file.");
        System.out.println("Room: " + room);
        System.out.println("Building: " + building);
        System.out.println("Hours: " + hours);
        System.out.println("Minutes: " + minutes);
        System.out.println("AM/PM: " + am_Identifier);
        System.out.println("Date: " + date);

        //Method redirects user to new empty activity page with resultSet.
        openResultPage();

    }

    public void openResultPage() {

        Intent intent = new Intent(this, resultPage.class);
        startActivity(intent);
        //fetchData process = new fetchData();
        //process.execute();



    }

}


