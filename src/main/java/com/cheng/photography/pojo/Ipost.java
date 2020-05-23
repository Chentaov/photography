package com.cheng.photography.pojo;

import lombok.*;

@NoArgsConstructor

@AllArgsConstructor
@ToString
@Getter
@Setter
public class Ipost { //投稿专线
    private int ipost_id;
    private String ipost_title;
    private String ipost_img;
    private String ipost_leadwords;
    private String ipost_content;
    private String ipost_type;
    private String ipost_class;
    private String ipost_keywords;
    private String ipost_mood;
    private int iposter_id;
    private String iposter_name;
    private String ipost_status;
    private String ipost_time;
    private String update_time;
    private int ipost_val;
    private int ipost_count;
    private int is_setback;
}
