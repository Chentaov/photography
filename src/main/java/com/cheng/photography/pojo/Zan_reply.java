package com.cheng.photography.pojo;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Zan_reply {
    private int zan_id;
    private int zan_reply_id;
    private int count;
    private int clicker_id;
    private int obj_id;
    private boolean ok;
}
