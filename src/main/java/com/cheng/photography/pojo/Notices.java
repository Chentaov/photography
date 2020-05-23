package com.cheng.photography.pojo;

import lombok.*;

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
}
