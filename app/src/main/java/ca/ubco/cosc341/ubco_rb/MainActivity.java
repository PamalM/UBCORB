package ca.ubco.cosc341.ubco_rb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    EditText dateEntry, hhEntry, mmEntry, timeOfDayEntry, buildingEntry, roomEntry;
    Button sumbitButton;

    String room, hours, minutes, building, am_Identifier;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateEntry = (EditText) findViewById(R.id.dateEntry);
        hhEntry = (EditText) findViewById(R.id.hhEntry);
        mmEntry = (EditText) findViewById(R.id.mmEntry);
        timeOfDayEntry = (EditText) findViewById(R.id.timeOfDayEntry);
        buildingEntry = (EditText) findViewById(R.id.buildingEntry);
        roomEntry = (EditText) findViewById(R.id.roomEntry);

        sumbitButton = (Button) findViewById(R.id.submitButton);

        sumbitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                room = roomEntry.getText().toString();
                

                System.out.println("Name: " + room);

            }
        });

    }

}
