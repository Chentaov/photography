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
public class User_post {
    private int post_id;
    private int poster_id;
    private String poster_name;
    private String post_type;
    private String post_title;
    private String post_content;
    private int post_status;
    private int post_top;
    private int post_nice;
    private String post_time;
    private String time;
    private String head_icon;
    private int page;//表示第几页
    private int pageSize;//页面大小
    private int start; //数据库limit的起步
    private int reply_count; //回复数
    private int click_count; //点击数
    private int identity; //身份
    private int exp;
    private int nice_replyed;
    Date date;
    TimeFormart timeFormart;
    public String getPost_time()  {
        try {
            date = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(post_time);
        }
        catch (ParseException e){

        }
        return timeFormart.format(date);
    }

}
