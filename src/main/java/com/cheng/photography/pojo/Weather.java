package com.cheng.photography.pojo;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter

public class Weather {
private int day_air_temperature;
private String weather;
private int night_air_temperature;
private String wind_direction;
private String wind_power;
private String weather_code;

    public String getWeather_code() {
        int index = weather_code.lastIndexOf("转");
        if(weather_code.contains("转")){
          weather_code = weather_code.replaceFirst(weather_code.substring(index,weather.length()+1),"");
        }
        return "/weather/img/"+weather_code+".png";
    }
}
