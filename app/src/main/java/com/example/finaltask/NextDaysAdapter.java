package com.example.finaltask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finaltask.ForeCastWeatherClass.NextDays;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NextDaysAdapter extends RecyclerView.Adapter<NextDaysAdapter.WeatherForecastViewHolder> {

    public List<NextDays> list1;
    public Context context1;
    public NextDaysAdapter(List<NextDays> list, Context context){
        list1 = list;
        context1 = context;
    }

    @NonNull
    @Override
    public WeatherForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.city_name,parent,false);
        WeatherForecastViewHolder holder=new WeatherForecastViewHolder(view);
        return  holder;
    }
    public String GetDayName(Date date){
        return new SimpleDateFormat("EE").format(date);
    }
    @Override
    public void onBindViewHolder(@NonNull WeatherForecastViewHolder holder, int position) {
        NextDays item = list1.get(position);
        holder.txtDay.setText(GetDayName(item.date));
        holder.txtTempreture.setText(item.temperture + "");
        holder.txtDay.setText(GetDayName(item.date));
        String url="http://openweathermap.org/img/wn/" + item.icon + "@2x.png";
        Picasso.with(context1).load(url).into(holder.imgIcon);
    }

    @Override
    public int getItemCount() {
        return 5;
    }
    public  class WeatherForecastViewHolder extends RecyclerView.ViewHolder{
        TextView txtDay;
        ImageView imgIcon;
        TextView txtTempreture;

        public WeatherForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDay=itemView.findViewById(R.id.txtDay);
            imgIcon=itemView.findViewById(R.id.imgIcon);
            txtTempreture=itemView.findViewById(R.id.txtCityTempreture);


        }
    }
}
