package com.cheng.photography.Controller;

import com.alibaba.fastjson.JSONObject;
import com.cheng.photography.pojo.*;
import com.cheng.photography.service.UserService;
import com.cheng.photography.util.HttpUtils;
import com.cheng.photography.util.IpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/")
public class InterController {
    @Autowired
    private UserService userService;
    @RequestMapping("acceptPost") //发帖
    public ModelAndView acceptPost(User_post user_post, HttpSession session){
        int id = Integer.parseInt(session.getAttribute("userid").toString());
        if(userService.isUserMute(id)!=null){
            return new ModelAndView("redirect:/alert");
        }
        else{
            if(userService.userPost(user_post)>0){
                return new ModelAndView("redirect:/index_jie");
            }
        }
      return new ModelAndView("redirect:/add");
    }

    @RequestMapping("editPost") //更贴
    public ModelAndView editPost(User_post user_post, HttpSession session){
        int id = Integer.parseInt(session.getAttribute("userid").toString());
        if(userService.isUserMute(id)!=null && id==user_post.getPoster_id()){
            return new ModelAndView("redirect:/alert");
        }
        else{
            if(userService.updatePost(user_post)>0){
                userService.update_post_time(user_post.getPost_id());
                return new ModelAndView("redirect:/index_jie");
            }
        }
        return new ModelAndView("redirect:/edit");
    }

    @RequestMapping("acceptReply")  //接收回复
    public int acceptReply(Reply reply, Notices notices,User_post user_post,HttpSession session){
        int flag = 0;
        int id = Integer.parseInt(session.getAttribute("userid").toString());
        if(userService.isUserMute(id)!=null){
            flag = 1;//禁言了
        }
        else{
            if(userService.replyByPosterId(reply)>0){
                userService.update_post_time(reply.getPost_id());
                userService.notice(notices);
                System.out.println("已经通知了"+notices.getNoticee());
                flag = 2;
            }
        }
        return flag;
    }

    @RequestMapping("checkZanStatus")
    public boolean checkZanStatus(Zan_reply zan_reply, Reply_clicker reply_clicker){
        boolean flag = false;
        if(userService.checkClickerAndZan(reply_clicker)==null){
            userService.InitClicker(reply_clicker);
            System.out.println("点赞者已初始化");
        }else {
            System.out.println("点赞者已存在关联无需初始化");
        }

        if(userService.checkZanStatus(zan_reply)!=null){
            userService.ZanToFalse(zan_reply);
            userService.ZanCountSub(zan_reply);
            System.out.println("已经赞过点赞-1");
            flag =true;
        }
        else{
            userService.ZanToTrue(zan_reply);
            System.out.println("还没赞过点赞+1");
            userService.ZanCountPlus(zan_reply);
            flag = false;

        }
        return flag;
    }

    @RequestMapping("getUsers")  //获取用户列表信息
    public String getUsers(@RequestParam int limit, @RequestParam int page,User user){
        List<User> UD = userService.findAllUser(user);
        JSONObject jsonObject = new JSONObject();
        int size=UD.size();
        if(page*limit<=size){
            UD=UD.subList((page-1)*limit, page*limit);
        }else{
            UD=UD.subList((page-1)*limit, size);
        }
        jsonObject.put("code", 0);    // 成功的状态码，默认：0
        jsonObject.put("msg", "");
        jsonObject.put("count", size);//总结果数
        jsonObject.put("data", UD);
        return jsonObject.toString();
    }

    @RequestMapping("getManages")  //获取用户列表信息
    public String getManages(@RequestParam int limit, @RequestParam int page,User user){
        List<User> UD = userService.Managers(user);
        JSONObject jsonObject = new JSONObject();
        int size=UD.size();
        if(page*limit<=size){
            UD=UD.subList((page-1)*limit, page*limit);
        }else{
            UD=UD.subList((page-1)*limit, size);
        }
        jsonObject.put("code", 0);    // 成功的状态码，默认：0
        jsonObject.put("msg", "");
        jsonObject.put("count", size);//总结果数
        jsonObject.put("data", UD);
        return jsonObject.toString();
    }

    @RequestMapping("getPost")  //获取投稿列表信息
    public String getPost(@RequestParam int limit, @RequestParam int page ,User_post user_post){
        List<User_post> UD = userService.postList(user_post);
        JSONObject j = new JSONObject();
        int size=UD.size();
        if(page*limit<=size){
            UD=UD.subList((page-1)*limit, page*limit);
        }else{
            UD=UD.subList((page-1)*limit, size);
        }
        j.put("code", 0);    // 成功的状态码，默认：0
        j.put("msg", "");
        j.put("count", size);//总结果数
        j.put("data", UD);
        return j.toString();
    }

    @RequestMapping("getlockedUser")  //获取封禁用户列表信息
    public String getlockedUser(@RequestParam int limit, @RequestParam int page ,User user){
        List<User> UD = userService.lockedUser(user);
        JSONObject j = new JSONObject();
        int size=UD.size();
        if(page*limit<=size){
            UD=UD.subList((page-1)*limit, page*limit);
        }else{
            UD=UD.subList((page-1)*limit, size);
        }
        j.put("code", 0);    // 成功的状态码，默认：0
        j.put("msg", "");
        j.put("count", size);//总结果数
        j.put("data", UD);
        return j.toString();
    }

    @RequestMapping("getmutedUser")  //获取封禁用户列表信息
    public String getmutedUser(@RequestParam int limit, @RequestParam int page ,User user){
        List<User> UD = userService.mutedUser(user);
        JSONObject j = new JSONObject();
        int size=UD.size();
        if(page*limit<=size){
            UD=UD.subList((page-1)*limit, page*limit);
        }else{
            UD=UD.subList((page-1)*limit, size);
        }
        j.put("code", 0);    // 成功的状态码，默认：0
        j.put("msg", "");
        j.put("count", size);//总结果数
        j.put("data", UD);
        return j.toString();
    }

    @RequestMapping("setPostTop")   //设置置顶
    public boolean setPostTop(User_post user_post){
        boolean flag = false;
        if(userService.setPostTop(user_post)>0){
            flag = true;
        }
            return flag;
    }

    @RequestMapping("un_setPostTop")   //设置置顶
    public boolean un_setPostTop(User_post user_post){
        boolean flag = false;
        if(userService.un_setPostTop(user_post)>0){
            flag = true;
        }
        return flag;
    }

    @RequestMapping("set_nice")   //设置加精
    public boolean set_nice(User_post user_post){
        boolean flag = false;
        if(userService.set_nice(user_post)>0){
            flag = true;
        }
        return flag;
    }

    @RequestMapping("un_setnice")   //设置取消精
    public boolean un_setnice(User_post user_post){
        boolean flag = false;
        if(userService.un_setnice(user_post)>0){
            flag = true;
        }
        return flag;
    }

    @RequestMapping("deleteReply") //删除回复
    public boolean deleteReply(Reply reply){
        boolean flag = false;
        if(userService.deleteReply(reply)>0){
            flag = true;
        }
        return flag;
    }

    @RequestMapping("deletePost")  //删除帖子会顺带删除回复和赞
    public boolean deltePost(User_post user_post,Reply reply,@RequestBody String[] appCodes){
        boolean flag = false;
        FileUploadController fileUploadController = new FileUploadController();
        if(userService.deletePost(user_post)>0){
            userService.deleteReply(reply);
            for(int i= 0;i<appCodes.length;i++){
                System.out.println(appCodes[i]);
                String url = appCodes[i].replaceFirst("http://106.14.45.97:8081","/opt/uploaded_img").replace("amp;","");
                fileUploadController.deleteFile(url);
            }
            flag = true;
        }
        return flag;
    }

    @RequestMapping("do_ipost")   //投稿
    public ModelAndView do_ipost(Ipost ipost){
        if(userService.ipost(ipost)>0){
            return new ModelAndView("redirect:/index_ph");
        }
        return new ModelAndView("redirect:/ipost");
    }


    @RequestMapping("do_val") //评分
    public void do_val(Ipost_val ipost_val){
        if(userService.checkIpostValuer(ipost_val)!=null){
            System.out.println("评分者与帖子已存在关联无需初始化");
            userService.update_IpostVal(ipost_val);
        }
        else {
            System.out.println("评分者与帖子还未关联正在初始化");
            userService.InitValer(ipost_val);
            System.out.println(ipost_val);
        }
    }

    @RequestMapping("follow")
    public int follow(Follow follow){   //关注
        int flag=0;
        if(userService.checkFollowStatus(follow)==null){
            userService.followInit(follow);
            System.out.println("关注关系已初始化");
        }else {
            System.out.println("关注关系已存在关联无需初始化");
        }

        if(userService.isfollow(follow)!=null){
            userService.unfollow_him(follow);
            System.out.println("已经关注，将取关");
            flag =0;
        }
        else{
            System.out.println("还没关注，将关注");
            userService.follow_him(follow);
            flag = 1;

        }

        return flag;
    }

    @RequestMapping("searchPost") //搜贴
    public boolean searchPost(String post_title){
        boolean flag = false;
        if(userService.searchPost(post_title)!=null){
            flag = true;
        }
        return flag;
    }

    @RequestMapping("lockUser") //封号
    public boolean lockUser(int id){
        boolean flag = false;
        if(userService.lockUser(id)>0){
            flag = true;
        }
        return flag;
    }

    @RequestMapping("unlockUser") //解封
    public boolean unlockUser(int id){
        boolean flag = false;
        if(userService.unlockUser(id)>0){
            flag = true;
        }
        return flag;
    }

    @RequestMapping("muteUser") //禁言
    public boolean muteUser(int id){
        boolean flag = false;
        if(userService.muteUser(id)>0){
            flag = true;
        }
        return flag;
    }

    @RequestMapping("unmuteUser") //解禁言
    public boolean unmuteUser(int id){
        boolean flag = false;
        if(userService.unmuteUser(id)>0){
            flag = true;
        }
        return flag;
    }

    @RequestMapping("passIpost") // 通过投稿
    public boolean passIpost(int ipost_id){
        boolean flag = false;
        if(userService.passIpost(ipost_id)>0){
            flag = true;
        }
        return flag;
    }

    @RequestMapping("del_ipost") // 删除投稿
    public boolean del_ipost(int ipost_id){
        boolean flag = false;
        if(userService.del_ipost(ipost_id)>0){
            flag = true;
        }
        return flag;
    }

    @RequestMapping("setback_ipost") // 退回投稿
    public boolean setback_ipost(int ipost_id){
        boolean flag = false;
        if(userService.setback_ipost(ipost_id)>0){
            flag = true;
        }
        return flag;
    }

    @RequestMapping("repass_ipost") //  重新通过投稿
    public boolean repass_ipost(int ipost_id){
        boolean flag = false;
        if(userService.repass_ipost(ipost_id)>0){
            flag = true;
        }
        return flag;
    }

    @RequestMapping("setManage") // 设置1级管理
    public boolean setManage(int id){
        boolean flag = false;
        if(userService.setManage(id)>0){
            flag = true;
        }
        return flag;
    }

    @RequestMapping("setUser0") // 设置平民
    public boolean setUser0(int id){
        boolean flag = false;
        if(userService.setUser0(id)>0){
            flag = true;
        }
        return flag;
    }

    @RequestMapping("setNav_color") // 设置导航主色
    public boolean setNav_color(String style_val){
        boolean flag = false;
        if(userService.setNav_color(style_val)>0){
            flag = true;
        }
        return flag;
    }

    @RequestMapping("readNav_color") // 读导航主色
    public String readNav_color(){
//        JSONObject js =new JSONObject();
//        js.put("style",userService.readNav_color());
//        System.out.println(js.toJSONString());
//        return js.toString();
        System.out.println(userService.readNav_color().getStyle_val());
        return userService.readNav_color().getStyle_val();
    }


    @RequestMapping("del_msg")  //删除消息
    public String del_msg( int notices_id){
        int flag = 1;
        JSONObject j = new JSONObject();
        if(userService.del_msg(notices_id)>0){
            flag = 0;
            j.put("status",flag);
        }
        return j.toJSONString();
    }

    @RequestMapping("del_allmsg")  //清空消息
    public String del_allmsg( int noticee_id){
        int flag = 1;//失败
        JSONObject j = new JSONObject();
        if(userService.del_allmsg(noticee_id)>0){
            flag = 0; //成功
            j.put("status",flag);
        }
        return j.toJSONString();
    }

    @RequestMapping("accept_best")  //采纳最佳
    public String accept_best( int reply_id,int post_id){
        int flag = 1;//失败
        JSONObject j = new JSONObject();
        if(userService.accept_best(reply_id)>0){
            userService.close_reply_nice(post_id);
            flag = 0; //成功
            j.put("status",flag);
        }
        return j.toJSONString();
    }

    @RequestMapping("add_Tags") //新增标签
    public void add_Tags(Tags tags){
        System.out.println(tags);
        userService.addTags(tags);
    }

    @RequestMapping("remove_Tags") //删除标签
    public void remove_Tags(Tags tags){
        System.out.println(tags);
        userService.remove_Tags(tags);
    }

    HttpUtils httpUtils;
    IpUtils ipUtils;
    @RequestMapping("getip")
    public String getIp(HttpServletRequest request){
        String body="";
        String host = "https://iweather.market.alicloudapi.com";
        String path = "/ipquery";
        String method = "GET";
        String appcode = "27f30d0f65c84ebeb140a7e96e5b33a3";
        String address  ;
        String addressIp  ;
        address = ipUtils.getIpAddr(request);
//        address = "1.24.215.255";
//        System.out.println(addressIp);
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("ip",address);
        querys.put("needday", "1");
        try {
            HttpResponse response = httpUtils.doGet(host, path, method, headers, querys);
//            System.out.println(response.toString());
//            System.out.println(EntityUtils.toString(response.getEntity()));
            body = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpUtils.unicodeToUtf8(body);
    }


}
