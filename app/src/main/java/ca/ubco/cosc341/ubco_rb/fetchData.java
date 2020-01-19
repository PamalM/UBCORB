package ca.ubco.cosc341.ubco_rb;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.*;
import java.net.*;

public class fetchData extends AsyncTask<Void, Void, Void> {

    String data = "";

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            URL url = new URL("https://api.myjson.com/bins/j5f6b");
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null){

                line = bufferedReader.readLine();
                data = data + line;

            }

        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        resultPage.date1.setText(this.data);

    }
}
