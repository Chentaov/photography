package com.cheng.photography.pojo;

import lombok.*;

@NoArgsConstructor

@AllArgsConstructor
@ToString
@Getter
@Setter
public class Habit {
    private int uid;
    private int p_class;
    private String visit_time;
}
