package com.cheng.photography;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.SimpleFormatter;


public class time {
    @Test

        public void timeformart() throws Exception{
            Date date = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse("2020-05-22 10:16:11");
            System.out.println(date+"111");
        }

}
