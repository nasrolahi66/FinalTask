package com.example.finaltask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView txtCityName;
    TextView txtDate;
    TextView txtCityTempreture;
    ImageView imgIcon;
    TextView txtHumidity;
    RecyclerView recycler;
    TextView txtWind;
    EditText edtSearchCity;
    Button btnSeachCity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
