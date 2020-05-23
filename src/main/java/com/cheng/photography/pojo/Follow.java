package com.cheng.photography.pojo;

import lombok.*;

@NoArgsConstructor

@AllArgsConstructor
@ToString
@Getter
@Setter
public class Follow {
    private int follower_id;
    private String follower_name;
    private int obj_id;
    private String obj_name;
    private int status;
    private String follow_time;
}
