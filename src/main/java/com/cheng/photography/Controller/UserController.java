package com.cheng.photography.Controller;

import com.cheng.photography.pojo.User;
import com.cheng.photography.service.UserService;
import com.cheng.photography.service.mailtest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    private mailtest m;
    @Autowired
    private UserService userService;
    @RequestMapping("doregister") //注册新用户
    public int register(User user) {
       return userService.register(user);
    }

    @RequestMapping("dologin") //登录
    public boolean login(User user, HttpSession session){
        boolean flag = false;
        if(userService.login(user)!=null){
            flag = true;
            session.setAttribute("user",userService.login(user));
            session.setAttribute("userid",userService.login(user).getId());
            session.setAttribute("useremail",userService.login(user).getEmail());
            //System.out.println(session.getAttribute("user"));
        }
        return flag;
    }

    @RequestMapping("dologout")
    public void dologout(HttpServletResponse response,HttpSession session)throws Exception{
        session.removeAttribute("user");
        session.removeAttribute("userid");
        response.sendRedirect("login");
    }

    @RequestMapping("check_email")  //email查重
    public boolean check_email(User user){
        boolean flag = false;
        if(userService.check_email(user)!=null){
            flag = true;
        }
        return flag;
    }

    @RequestMapping("mail") //发送邮件
    public String mail(HttpSession session)  {
        Calendar nowTime = Calendar.getInstance();
        Date nowDate = (Date) nowTime.getTime();
        System.out.println(nowDate); //现在时间
        Calendar afterTime = Calendar.getInstance();
        afterTime.add(Calendar.MINUTE, 5); //设置链接有效时间
        Date afterDate = (Date) afterTime.getTime(); //预设时间
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        String after =f.format(afterDate);
        userService.check_time(after,(int)session.getAttribute("userid")); //根据id刷新预设时间
        m.sendMail(userService.findUserById((int)(session.getAttribute("userid"))).getEmail(),"绑定您的邮箱","5分钟内点击激活",(int)session.getAttribute("userid"));
        return "发送成功";
    }

    @RequestMapping("actEmail") // 邮箱激活用户
    public String actEmail(int id){
        Calendar nowTime = Calendar.getInstance();
        Date nowDate = (Date) nowTime.getTime();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        String now = f.format(nowDate);
        String after = (userService.findUserById(id).getCheck_time());
        if(now.compareTo((after))<0){ //如果预设时间大于现行时间则实行激活。
            userService.actEmail(id);
            return "激活成功";
        }
       return "时间已经过期了";
    }

    @RequestMapping("updateUserInfo")  //更新
    public boolean updateUserInfo(User user,HttpSession session){
        boolean flag=false;
        if(userService.updateUserInfo(user)>0){
            userService.updatePosterName(user.getId(),user.getName());
            userService.updateReplyerName(user.getId(),user.getName());
            flag = true;
        }
        return flag;
    }

    @RequestMapping("updateHeadIcon")  //更新用户头像
    public boolean updateHeadIcon(User user){
        boolean flag = false;
        System.out.println("user"+user);
        if(userService.updateHeadIcon(user)>0){
            flag = true;
        }
        return flag;
    }


    @RequestMapping("updateEmail")   //更新用户email
    public boolean updateEmail(User user,HttpSession session){
        boolean flag = false;
        if(userService.updateEmail(user)>0){
            userService.updateEmailStatus((int)(session.getAttribute(("userid"))));
            flag = true;
        }
        return flag;
    }

    @RequestMapping("updatePassword")  //更新用户密码
    public boolean updatePassword(User user){
        boolean flag = false;
        if(userService.updatePassword(user)>0){
            flag = true;
        }
        return flag;
    }

    @RequestMapping("sendEmailReset") //发送重置密码邮件
    public String sendEmailReset(String email,HttpSession session)  {
        Calendar nowTime = Calendar.getInstance();
        Date nowDate = (Date) nowTime.getTime();
        System.out.println(nowDate); //现在时间
        Calendar afterTime = Calendar.getInstance();
        afterTime.add(Calendar.MINUTE, 5); //设置链接有效时间
        Date afterDate = (Date) afterTime.getTime(); //预设时间
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        String after =f.format(afterDate);
        userService.check_timeByEmail(after,email); //根据id刷新预设时间
        m.sendMail(email,"OK","5分钟内点击可以重置密码为666666",email);
        return "发送成功";
    }

    @RequestMapping("resetPasswordByEmail") // 点击重置密码
    public String resetEmail(String email){
        Calendar nowTime = Calendar.getInstance();
        Date nowDate = (Date) nowTime.getTime();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        String now = f.format(nowDate);
        String after = (userService.findUserByEmail(email).getCheck_time());
        if(now.compareTo((after))<0){ //如果预设时间大于现行时间则实行激活。
            userService.resetPasswordByEmail(email);
            return "密码重置成功";
        }
        return "时间已经过期了";
    }

    @RequestMapping("imgURL")
    public String imgURL(int poster_id){   //根据id查找用户头像并返回一个头像URL
        return userService.findUserById(poster_id).getHead_icon();
    }
}
