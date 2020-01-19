package ca.ubco.cosc341.ubco_rb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ListView;
import android.content.Context;

import java.util.ArrayList;

public class resultPage extends AppCompatActivity {

    Button returnButton;
    public static TextView date1;

    private static final String URL = "https://jsoneditoronline.org/?id=b60835f733e94c8b854499eb1c07db2f";
    private static String date, time, building, room;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);

        returnButton = (Button) findViewById(R.id.returnButton);
        date1 = (TextView) findViewById(R.id.date1);

        returnButton.setOnClickListener(new View.OnClickListener() {

            //Method will execute when user clicks search button.
            //Grab current values in all text boxes, and save into variables.
            @Override
            public void onClick(View v) {

                //Method redirects user back to home/search screen.
                returnHome();

            }
        });


    }

    public void returnHome() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}
