 package ca.ubco.cosc341.ubco_rb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

 public class resultPage extends AppCompatActivity {

     Button returnButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);

        //Setup listview to accommodate search results.

        ListView listView = (ListView) findViewById(R.id.listView);

        


        returnButton = (Button) findViewById(R.id.returnButton);

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
