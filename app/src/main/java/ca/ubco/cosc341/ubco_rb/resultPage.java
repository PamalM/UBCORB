package ca.ubco.cosc341.ubco_rb;

import androidx.appcompat.app.AppCompatActivity;
import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;

public class resultPage extends AppCompatActivity {

    //Widgets
    Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);

        //Find button with id.
        returnButton = (Button) findViewById(R.id.returnButton);

        //Set search button onClick event handler.
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { returnHome(); }
        });

    }

    //Method returns user back to MainActivity (Search Page)
    public void returnHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
