package com.cheng.photography;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.SimpleFormatter;


public class time {
    @Test
    public void time(){
        Calendar nowTime = Calendar.getInstance();
        Date nowDate = (Date) nowTime.getTime();
        System.out.println(nowDate);
        Calendar afterTime = Calendar.getInstance();
        afterTime.add(Calendar.MINUTE, 5);
        Date afterDate = (Date) afterTime.getTime();
        System.out.println(afterDate);
        System.out.println(afterDate.compareTo(nowDate));
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        String now= f.format(nowDate);
        String after = f.format(afterDate);
        System.out.println(now.compareTo(after));
    }
}
