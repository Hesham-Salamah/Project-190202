package com.example.se328_hesham190202;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity4 extends AppCompatActivity {

    private double temp;
    private String weather;//weather--> main
    private String city;
    public  ImageView img;

    public static int imgResource;

    String weatherWebserviceURL = "http://api.openweathermap.org/data/2.5/weather?q=calgary&appid=64d30c10c2c7f7bfa647f48ecca7dee8&units=metric";


    // Textview to show temperature and description
    TextView temperature, h;

    EditText inputCity;

    Button submit;

    JSONObject jsonObj;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        //link graphical items to variables
        temperature = (TextView) findViewById(R.id.temp);
        h = (TextView) findViewById(R.id.hum);
        inputCity=(EditText)findViewById(R.id.entercity);

        submit=(Button)findViewById(R.id.submit);
        Button backto1 = (Button) findViewById(R.id.backtoact);



        h.setVisibility(View.INVISIBLE);
        temperature.setVisibility(View.INVISIBLE);


        img = (ImageView) findViewById(R.id.changeweather);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h.setVisibility(View.VISIBLE);
                temperature.setVisibility(View.VISIBLE);

                String city=inputCity.getText().toString();
                String par="http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=64d30c10c2c7f7bfa647f48ecca7dee8&units=metric";

                weather(par);


            }
        });

        backto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity4.this, MainActivity.class));
            }
        });
    }

    public void weather(String url) {
        // we will send a json formatted request and we will recieve a formatted json formatted reply
        JsonObjectRequest jsonObj =
                new JsonObjectRequest(Request.Method.GET,
                        url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // result of the request

                        Log.d("Hesham",response.toString());

                        try {
                            //please use the json validator and formatter to see what u r dealing with

                            JSONObject jsonMain = response.getJSONObject("main");//for all jsons to be saved they need a try and catch
                            Log.d("Hesham","subObject"+jsonMain.toString());

                            double temp=jsonMain.getDouble("temp");//getting the temp in the main json object
                            Log.d("Hesham","temp is: "+temp);
                            temperature.setText("Temprature: " +String.valueOf(temp));

                            double humidity=jsonMain.getDouble("humidity");//getting the humidity in the main json object
                            Log.d("Hesham","Humidity is: "+humidity);
                            h.setText("Humidity: "+String.valueOf(humidity));

                            whetherPic(response.getJSONArray("weather"));//calling the weatherPic and passing it the weather element);
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //log errors here
                        Log.d("Hesham","Error in url");

                    }

                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObj);
    }


    public void whetherPic(JSONArray jArray) {
        //accept an array as an input and go through the array and do work on each object in the array
        // we accept it as an array since the wether element is an array
        // anything that starts with a [] is an array

        Log.d("Hesham","Inside the whetherPic method");

        for (int i = 0; i < jArray.length(); i++) {
            try {
                JSONObject oneObject = jArray.getJSONObject(i);
                Log.d("Hesham", "jArray(i): " + oneObject.toString());

                String weatherCondition=oneObject.getString("main");
                Log.d("Hesham","Weather condition: "+weatherCondition);


                img.setImageResource(R.drawable.weather);

                if(weatherCondition.equals("Clouds")){

                    img.setImageResource(R.drawable.cloud);
                    img.setTag(R.drawable.cloud);
                    imgResource=(Integer)img.getTag();
                }

                else if(weatherCondition.equals("Clear")){
                    img.setImageResource(R.drawable.clear);
                    img.setTag(R.drawable.clear);
                    imgResource=(Integer)img.getTag();
                }
                else if(weatherCondition.equals("Rain")){
                    img.setImageResource(R.drawable.rain);
                    img.setTag(R.drawable.rain);
                    imgResource=(Integer)img.getTag();
                }
                else if(weatherCondition.equals("Snow")){
                    img.setImageResource(R.drawable.snow);
                    img.setTag(R.drawable.snow);
                    imgResource=(Integer)img.getTag();
                }




            } catch (Exception e) {
                Log.d("Hesham","Error JSONarray "+ jArray.toString());

            }
        }
    }

    public int getImgResource(){
        return imgResource;
    }
}