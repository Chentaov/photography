package com.cheng.photography;



import com.cheng.photography.util.HttpUtils;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class view {
@Autowired
    HttpUtils httpUtils;
    @Test
    public void v () throws Exception {
        String host = "https://iweather.market.alicloudapi.com";
        String path = "/ipquery";
        String method = "GET";
        String appcode = "27f30d0f65c84ebeb140a7e96e5b33a3";
        String address  ;
        address = InetAddress.getLocalHost().getHostAddress();
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("ip",address);
        querys.put("needday", "1");
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = httpUtils.doGet(host, path, method, headers, querys);

//            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
