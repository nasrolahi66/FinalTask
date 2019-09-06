package com.example.finaltask.ForeCastWeatherClass;

import java.util.Date;

public class NextDays {
    public Date date;
    public double temperture;
    public String icon;
    public NextDays(double _temperture, String _icon, Date _date){
        temperture = _temperture;
        icon = _icon;
        date = _date;
    }
}
