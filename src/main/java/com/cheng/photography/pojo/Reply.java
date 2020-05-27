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
public class Reply {
    private int reply_id; //回复帖子id
    private int reply_owner_id;//回贴者id
    private String reply_name;//回帖者昵称
    private String reply_content;//回复内容
    private int accepter_id; //回复帖归属帖子id
    private int post_id;//帖子id
    private int reply_nice;//精回复状态
    private String reply_time;//回复时间
    private String head_icon;//头像
    private int zan_id;
    private int zan_reply_id;
    private int clicker_id;
    private boolean ok;
    private int count;
    private int identity;
    private int exp;

    Date date;
    TimeFormart timeFormart;
    public String getReply_time()  {
        try {
            date = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(reply_time);
        }
        catch (ParseException e){

        }
        return timeFormart.format(date);
    }
}
