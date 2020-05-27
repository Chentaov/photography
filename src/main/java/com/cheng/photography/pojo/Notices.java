package com.cheng.photography.pojo;

import com.cheng.photography.util.TimeFormart;
import lombok.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor

@AllArgsConstructor
@ToString
@Getter
@Setter
public class Notices {
    private int notices_id;
    private int noticer_id;
    private String noticer;
    private int noticee_id;
    private String noticee;
    private int location_id;
    private String location_title;
    private String notice_content;
    private String notice_time;
    Date date;
    TimeFormart timeFormart;
    public String getNotice_time()   {
        try {
            date = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(notice_time);
        }
        catch (ParseException e){

        }
        return timeFormart.format(date);
    }
}
