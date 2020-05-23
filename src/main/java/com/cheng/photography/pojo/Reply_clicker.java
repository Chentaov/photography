package com.cheng.photography.pojo;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Reply_clicker {
    private int c_id;
    private int clicker_id;
    private int obj_id;
    private boolean ok;
}
