package ca.ubco.cosc341.ubco_rb;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    //Widgets.
    Button searchButton;

    //Text-Entry fields that will be passed to conduct search query.
    String room, hours, minutes, building, am_Identifier, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find button with id.
        searchButton = (Button) findViewById(R.id.searchButton);

        //Set search button onClick event handler.
        searchButton.setOnClickListener(new View.OnClickListener() {

            //Method will execute when user clicks search button.
            //Grab current values in all text boxes, and save into variables.
            @Override
            public void onClick(View v) {

                System.out.println("[Search Button Clicked]");

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


