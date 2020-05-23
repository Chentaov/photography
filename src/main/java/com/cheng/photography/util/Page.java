package com.cheng.photography.util;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Page {
    private int page;//表示第几页
    private int pageSize;//页面大小
    private int start;
}
