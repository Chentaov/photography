package com.cheng.photography.Controller;

import com.alibaba.fastjson.JSONObject;
import com.cheng.photography.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;



@Controller
public class FileUploadController {
    @Autowired
    UserService userService;


    public static boolean deleteFile(String fileName){


        File file = new File(fileName);//根据指定的文件名创建File对象

        if (  file.exists() && file.isFile() ){ //要删除的文件存在且是文件

            if ( file.delete()){
                System.out.println("文件"+fileName+"删除成功！");
                return true;
            }else{
                System.out.println("文件"+fileName+"删除失败！");
                return false;
            }
        }else{

            System.out.println("文件"+fileName+"不存在，删除失败！" );
            return false;
        }

    }

    @ResponseBody
    @RequestMapping(value = "deletePostSource")
    public void deletePostSource( @RequestBody String[] appCodes){
        for(int i= 0;i<appCodes.length;i++){
            System.out.println(appCodes[i]);
            String url = appCodes[i].replaceFirst("http://106.14.45.97:8081","/opt/uploaded_img").replace("amp;","");
            deleteFile(url);
        }
    }

    @ResponseBody
    @RequestMapping(value="setPhotoimg" )
    public String setPhotoimg(@RequestParam("file") MultipartFile file ) { //换群二维码
        String pathString = null;
        String Photoimg;
        String Photoimg_before=userService.readERcode().getStyle_val();
        if(file!=null) {
            pathString = "/opt/uploaded_img/head_icon/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" +file.getOriginalFilename();
        }

        Photoimg = pathString.replaceFirst("/opt/uploaded_img/","");
        userService.setPhotoimg(Photoimg);
        deleteFile("/opt/uploaded_img/"+Photoimg_before);
        try {
            File files=new File(pathString);
            if(!files.getParentFile().exists()){
                files.getParentFile().mkdirs();
            }
            file.transferTo(files);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",0);
        jsonObject.put("msg","http://106.14.45.97/"+pathString);
        jsonObject.put("","http://106.14.45.97/"+pathString);
        return jsonObject.toString();
    }

    @ResponseBody
    @RequestMapping(value="setER" )
    public String setER(@RequestParam("file") MultipartFile file ) { //换群二维码
        String pathString = null;
        String ERcode;
        String ERcode_before=userService.readERcode().getStyle_val();
        if(file!=null) {
            pathString = "/opt/uploaded_img/head_icon/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" +file.getOriginalFilename();
        }

        ERcode = pathString.replaceFirst("/opt/uploaded_img/","");
        userService.setERcode(ERcode);
        deleteFile("/opt/uploaded_img/"+ERcode_before);
        try {
            File files=new File(pathString);
            //打印查看上传路径
            // System.out.println(pathString);
            if(!files.getParentFile().exists()){
                files.getParentFile().mkdirs();
            }
            file.transferTo(files);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObject.put("code",0);
//        jsonObject.put("msg","http://localhost:8081"+head_icon);
//        jsonObject.put("","http://localhost:8081/"+head_icon);
        jsonObject.put("msg","http://106.14.45.97/"+pathString);
        jsonObject.put("","http://106.14.45.97/"+pathString);
        return jsonObject.toString();
    }

    @ResponseBody
    @RequestMapping(value="uploadSource" )
    public String uploadSource(@RequestParam("file") MultipartFile file ,int id) { //换头像
        String pathString = null;
        String head_icon;
        String head_icon_before=userService.findHeadicon(id).getHead_icon();
//        if(file!=null) {
//            pathString = "/home/cct/IdeaProjects/photography/src/main/resources/static/uploaded_img/head_icon/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" +file.getOriginalFilename();
//        }
//        head_icon = pathString.replaceFirst("/home/cct/IdeaProjects/photography/src/main/resources/static","");
        if(file!=null) {
            pathString = "/opt/uploaded_img/head_icon/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" +file.getOriginalFilename();
        }
        head_icon = pathString.replaceFirst("/opt/uploaded_img/","");
            userService.updateIcon(id,head_icon);
            deleteFile("/opt/uploaded_img/"+head_icon_before);
        try {
            File files=new File(pathString);
            //打印查看上传路径
           // System.out.println(pathString);
            if(!files.getParentFile().exists()){
                files.getParentFile().mkdirs();
            }
            file.transferTo(files);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObject.put("code",0);
//        jsonObject.put("msg","http://localhost:8081"+head_icon);
//        jsonObject.put("","http://localhost:8081/"+head_icon);
        jsonObject.put("msg","http://106.14.45.97/"+pathString);
        jsonObject.put("","http://106.14.45.97/"+pathString);
        return jsonObject.toString();
//        return "{\"code\":0,\"msg\":\""+pathString+"\"}";
    }

    @ResponseBody
    @RequestMapping(value="uploadSourcePost" )
    public String uploadSourcePost(@RequestParam("file") MultipartFile file ) { //发文章带图
        String pathString = null;
        String head_icon;
        if(file!=null) {
            pathString = "/opt/uploaded_img/art_img/"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" +file.getOriginalFilename();
        }
        head_icon = "art_img/"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" +file.getOriginalFilename();

        try {
            File files=new File(pathString);
            if(!files.getParentFile().exists()){
                files.getParentFile().mkdirs();
            }
            file.transferTo(files);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObject.put("code",0);
        jsonObject.put("msg","http://106.14.45.97:8081/"+head_icon);
        jsonObject.put("","http://106.14.45.97:8081/"+head_icon);
        return jsonObject.toString();
    }

    @ResponseBody
    @RequestMapping(value="setIpostImg" )
    public String setIpostImg(@RequestParam("file") MultipartFile file ) { //发文章带图
        String pathString = null;
        String head_icon;
//        if(file!=null) {
//            pathString = "/home/cct/IdeaProjects/photography/src/main/resources/static/uploaded_img/head_icon/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" +file.getOriginalFilename();
//        }
//        head_icon = pathString.replaceFirst("/home/cct/IdeaProjects/photography/src/main/resources/static","");
        if(file!=null) {
            pathString = "/opt/uploaded_img/head_icon/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" +file.getOriginalFilename();
        }
        head_icon = pathString.replaceFirst("/opt/uploaded_img/","");
        try {
            File files=new File(pathString);
            if(!files.getParentFile().exists()){
                files.getParentFile().mkdirs();
            }
            file.transferTo(files);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObject.put("code",0);
//        jsonObject.put("msg","http://localhost:8081"+head_icon);
//        jsonObject.put("","http://localhost:8081/"+head_icon);
        jsonObject.put("msg","http://106.14.45.97:8081/"+head_icon);
        jsonObject.put("","http://106.14.45.97:8081/"+head_icon);
        return jsonObject.toString();
    }

}
