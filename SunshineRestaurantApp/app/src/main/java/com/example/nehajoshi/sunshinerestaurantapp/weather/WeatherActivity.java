package com.example.nehajoshi.sunshinerestaurantapp.weather;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.example.nehajoshi.sunshinerestaurantapp.R;
import com.example.nehajoshi.sunshinerestaurantapp.model.Function;

public class WeatherActivity extends AppCompatActivity {
    TextView city, details, currentTemperature, humidity, pressure, weatherIcon, updated;
    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_weather);

        Bundle bundle = getIntent().getExtras();
        latitude = bundle.getDouble("latitude");
        longitude = bundle.getDouble("longitude");

        city = (TextView)findViewById(R.id.city);
        updated = (TextView)findViewById(R.id.updated);
        details = (TextView)findViewById(R.id.details);
        currentTemperature = (TextView)findViewById(R.id.current_temperature);
        humidity = (TextView)findViewById(R.id.humidity);
        pressure = (TextView)findViewById(R.id.pressure);
        weatherIcon = (TextView)findViewById(R.id.weather_icon);


        Function.placeIdTask asyncTask =new Function.placeIdTask(new Function.AsyncResponse() {
            public void processFinish(String weather_city, String weather_description, String weather_temperature, String weather_humidity, String weather_pressure, String weather_updatedOn, String weather_iconText, String sun_rise) {

                city.setText(weather_city);
                updated.setText(weather_updatedOn);
                details.setText(weather_description);
                currentTemperature.setText(weather_temperature);
                humidity.setText("Humidity: "+weather_humidity);
                pressure.setText("Pressure: "+weather_pressure);
                weatherIcon.setText(Html.fromHtml(weather_iconText));

            }
        });
        asyncTask.execute(String.valueOf(latitude), String.valueOf(longitude));
    }
}