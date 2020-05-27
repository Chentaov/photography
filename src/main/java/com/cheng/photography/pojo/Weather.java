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
        System.out.println(index);
        char weather_char [];
        weather_char = weather_code.toCharArray();
        System.out.println(weather_char.length);
        if(weather_code.contains("转")){
//            System.out.println(weather_code);
          weather_code = weather_code.replaceFirst(weather_code.substring(index,weather_char.length),"");
        }
        return "/weather/img/"+weather_code+".png";
    }
}
