package com.example.finaltask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finaltask.CurrentWeatherClass.CurrentWeatherClass;
import com.example.finaltask.ForeCastWeatherClass.ForeCastWeatherClass;
import com.example.finaltask.ForeCastWeatherClass.NextDays;
import com.example.finaltask.ForeCastWeatherClass.Weather;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    EditText edtSearchCity;
    Button btnSeachCity;
    TextView txtCityName;
    TextView txtDate;
    TextView txtCityTempreture;
    TextView txtHumidity;
    RecyclerView recycler;
    ImageView imgIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtSearchCity = findViewById(R.id.edtSearchCity);
        btnSeachCity = findViewById(R.id.btnSeachCity);
        txtCityName = findViewById(R.id.txtCityName);
        txtDate = findViewById(R.id.txtDate);
        txtCityTempreture = findViewById(R.id.txtCityTempreture);
        txtHumidity = findViewById(R.id.txtHumidity);
        recycler = findViewById(R.id.recycler);
        imgIcon = findViewById(R.id.imgIcon);
        SelectCity("");
        btnSeachCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectCity(edtSearchCity.getText().toString());
            }
        });


    }

    public void SelectCity(String city) {
        AsyncHttpClient client = new AsyncHttpClient();
        if (city == "")
            city = "tehran";
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&APPID=19dc69cdc6b7304758697de9e2fa6e25";
        try {
            client.get(url, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    Gson gson = new Gson();
                    CurrentWeatherClass currentWeatherClass = gson.fromJson(response.toString(), CurrentWeatherClass.class);

                    txtCityName.setText(currentWeatherClass.getName() + ", " + currentWeatherClass.getSys().getCountry());
                    String temp = Math.round(currentWeatherClass.getMain().getTemp().intValue()) + "";
                    txtCityTempreture.setText(temp);
                    txtHumidity.setText(currentWeatherClass.getMain().getHumidity().toString() + " %");
                    String icon = currentWeatherClass.getWeather().get(0).getIcon();
                    String url = "http://openweathermap.org/img/wn/" + icon + "@2x.png";
                    Picasso.with(MainActivity.this).load(url).into(imgIcon);
                }

                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);

                }
            });

            String forecastUrl = "http://api.openweathermap.org/data/2.5/forecast?q="+city+"&units=metric&APPID=19dc69cdc6b7304758697de9e2fa6e25";
            AsyncHttpClient foreCastClient = new AsyncHttpClient();
            foreCastClient.get(forecastUrl, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    Gson gson = new Gson();
                    ForeCastWeatherClass forecastWeatherClass = gson.fromJson(response.toString(), ForeCastWeatherClass.class);

                    List<NextDays> adapterList = new ArrayList<>();
                    String today = "";
                    for (int i = 0; i < forecastWeatherClass.getList().size(); i += 8) {
                        com.example.finaltask.ForeCastWeatherClass.List forecastList = forecastWeatherClass.getList().get(i);
                        today = forecastList.getDtTxt().substring(0, 10);
                        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = null;
                        try {
                            date = parser.parse(today);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Weather weather = forecastList.getWeather().get(0);
                        adapterList.add(new NextDays(forecastList.getMain().getTemp(), weather.getIcon(), date));

                    }
                    NextDaysAdapter adapter = new NextDaysAdapter(adapterList, MainActivity.this);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    recycler.setLayoutManager(layoutManager);
                    recycler.setAdapter(adapter);
                }

                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        } catch (Exception exception) {
exception.printStackTrace();        }
    }

    public String GetToday() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM");
        String formattedDate = df.format(c);
        return formattedDate;

    }
}


