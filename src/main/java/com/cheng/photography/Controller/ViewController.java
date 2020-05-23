package com.cheng.photography.Controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cheng.photography.pojo.Ipost;

import com.cheng.photography.pojo.User_post;
import com.cheng.photography.pojo.Weather;
import com.cheng.photography.service.UserService;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;





@RestController
@RequestMapping("/")

public class ViewController {



    @Autowired
    Recommend recommend;
    @Autowired
    private UserService userService;

    @Autowired
    InterController interController;
    public Weather getWeather(HttpServletRequest request){
        String weather = interController.getIp(request);
        Gson g = new Gson();
        JSONObject all = JSONObject.parseObject(weather);
        JSONObject data = JSONObject.parseObject(all.get("data").toString());
        JSONObject now = JSONObject.parseObject(data.get("now").toString());
        JSONObject city = JSONObject.parseObject(now.get("city").toString());
        Weather w = g.fromJson(city.toJSONString(),Weather.class);
        return w;
    }

    @RequestMapping("activate") //激活邮箱
    public ModelAndView activate(HttpSession session){
        ModelAndView m = new ModelAndView();
        m.setViewName("fly/html/user/activate");
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
        return m;
    }

    @RequestMapping("set") //个人设置
    public ModelAndView set(HttpSession session,HttpServletRequest request){
        ModelAndView m = new ModelAndView();
        m.setViewName("fly/html/user/set");
        Weather w = getWeather(request);
        m.addObject("weather",w);
        String nav_color = userService.readNav_color().getStyle_val();
        m.addObject("nav_color",nav_color);
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
        return m;
    }

    @RequestMapping("login") //登录
    public ModelAndView login(HttpSession session)throws Exception{
        ModelAndView m = new ModelAndView();
        m.setViewName("fly/html/user/login");
        return m;
    }

    @RequestMapping("reg") // 注册
    public ModelAndView reg(){
        ModelAndView m = new ModelAndView();
        m.setViewName("fly/html/user/reg");
        return m;
    }

    @RequestMapping("home") //个人中心
    public ModelAndView home(HttpSession session,HttpServletRequest request){
        ModelAndView m = new ModelAndView();
        m.setViewName("fly/html/user/home");
        Weather w = getWeather(request);
        m.addObject("weather",w);
        String nav_color = userService.readNav_color().getStyle_val();
        m.addObject("nav_color",nav_color);
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
        m.addObject("my_post",userService.findUserPostByUid((int)(session.getAttribute("userid"))));
        return m;
    }

    @RequestMapping("forget") //忘记密码
    public ModelAndView forget(HttpSession session){
        ModelAndView m = new ModelAndView();
        m.setViewName("fly/html/user/forget");
        return m;
    }

    @RequestMapping("index_user") //个人主页
    public ModelAndView index_user(HttpSession session ,HttpServletRequest request){
        ModelAndView m = new ModelAndView();
        m.setViewName("fly/html/user/index");
        Weather w = getWeather(request);
        m.addObject("weather",w);
        String nav_color = userService.readNav_color().getStyle_val();
        m.addObject("nav_color",nav_color);
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
        m.addObject("ifollowed",userService.ifollowed((int)session.getAttribute("userid")));
        m.addObject("like",userService.iLiked((int)session.getAttribute("userid")));
        return m;
    }

    @RequestMapping("message") //消息
    public ModelAndView message(HttpSession session,HttpServletRequest request){
        ModelAndView m = new ModelAndView();
        m.setViewName("fly/html/user/message");
        Weather w = getWeather(request);
        m.addObject("weather",w);
        String nav_color = userService.readNav_color().getStyle_val();
        m.addObject("nav_color",nav_color);
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
        m.addObject("notices",userService.findMyNoticesById((int)session.getAttribute("userid")));
        return m;
    }

//    上面是user模块的页面

//    下面是jie模块
    @RequestMapping("index_jie")  //用户发帖主页，此处user对象的信息是非动态的，动态要根据不变变量id来重新获取user对象信息。
    public ModelAndView index_jie(HttpSession session, User_post user_post,HttpServletRequest request){
        if(user_post.getPage()<=0){
            user_post.setPage(1);
        }
        int pageSize;
        user_post.setPageSize(14);
        pageSize = user_post.getPageSize();
        if(userService.findAllPost(user_post).size()<user_post.getPage()*user_post.getPageSize()){
            user_post.setStart(userService.findAllPost(user_post).size()-pageSize);
        }
        int start = (user_post.getPage()-1)*pageSize;
        user_post.setStart(start);


        ModelAndView m = new  ModelAndView();
        Weather w = getWeather(request);
        System.out.println(w);
        m.addObject("weather",w);
        m.setViewName("fly/html/jie/index");
        m.addObject("total",userService.postList(user_post).size()/user_post.getPageSize());
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
//        System.out.println("post_type:"+user_post.getPost_type());
        List<User_post> post_top = userService.findAllPostTop(user_post);
        List<User_post> post = userService.findAllPost(user_post);
        String nav_color = userService.readNav_color().getStyle_val();
        String ERcode = userService.readERcode().getStyle_val();
        m.addObject("nav_color",nav_color);
        m.addObject("ercode",ERcode);
        m.addObject("user_post_top",post_top);
        m.addObject("user_post",post);
        m.addObject("page",user_post.getPage());
        m.addObject("weekHot",userService.weekHotPost(user_post));
        m.addObject("weekRank",userService.weekReplyRank());
        return m;
    }

    @RequestMapping("detail")  //jie模块，此处user对象的信息是非动态的，动态要根据不变变量id来重新获取user对象信息。
    public ModelAndView detail(User_post user_post, HttpSession session,HttpServletRequest request) throws Exception{
        Weather w = getWeather(request);
        System.out.println(w);
        userService.userpost_counter(user_post.getPost_id());
        ModelAndView m = new  ModelAndView();
        m.setViewName("fly/html/jie/detail");
        m.addObject("weather",w);
        String nav_color = userService.readNav_color().getStyle_val();
        m.addObject("nav_color",nav_color);
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
        m.addObject("detail",userService.post_detail(user_post.getPost_id()));
        m.addObject("reply",userService.findReplyByPostId(user_post.getPost_id()));
        m.addObject("weekHot",userService.weekHotPost(user_post));
        return m;
    }

    @RequestMapping("add")  //jie模块，此处user对象的信息是非动态的，动态要根据不变变量id来重新获取user对象信息。
    public ModelAndView add(HttpSession session,HttpServletRequest request){
        ModelAndView m = new  ModelAndView();
        m.setViewName("fly/html/jie/add");
        Weather w = getWeather(request);
        m.addObject("weather",w);
        String nav_color = userService.readNav_color().getStyle_val();
        m.addObject("nav_color",nav_color);
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
        return m;
    }

    @RequestMapping("ipost")  //jie模块，此处user对象的信息是非动态的，动态要根据不变变量id来重新获取user对象信息。
    public ModelAndView ipost(HttpSession session,HttpServletRequest request){
        ModelAndView m = new  ModelAndView();
        m.setViewName("fly/html/jie/ipost");
        Weather w = getWeather(request);
        m.addObject("weather",w);
        String nav_color = userService.readNav_color().getStyle_val();
        m.addObject("nav_color",nav_color);
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
        return m;
    }

    @RequestMapping("cases")  //jie模块，此处user对象的信息是非动态的，动态要根据不变变量id来重新获取user对象信息。
    public ModelAndView cases(HttpSession session){
        ModelAndView m = new  ModelAndView();
        m.setViewName("fly/html/case/case");
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
        return m;
    }

    @RequestMapping("u") //用户主页公共页
    public ModelAndView u( int poster_id,HttpSession session,HttpServletRequest request){
        ModelAndView m = new ModelAndView();
        m.setViewName("fly/html/user/home_public");
        Weather w = getWeather(request);
        m.addObject("weather",w);
        String nav_color = userService.readNav_color().getStyle_val();
        m.addObject("nav_color",nav_color);
        m.addObject("u",userService.findUserById(poster_id));
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
        m.addObject("my_post",userService.findUserPostByUid(poster_id));
        m.addObject("follow_status",userService.follow_status((int)(session.getAttribute("userid")),poster_id));
        m.addObject("obj_id",poster_id);
        return m;
    }

    @RequestMapping("searchPostView")
    public ModelAndView searchPostView(String post_title,HttpSession session,User_post user_post,HttpServletRequest request){  //搜索视图
        ModelAndView m = new ModelAndView();
        m.setViewName("fly/html/jie/search");
        Weather w = getWeather(request);
        m.addObject("weather",w);
        String nav_color = userService.readNav_color().getStyle_val();
        m.addObject("nav_color",nav_color);
        m.addObject("weekRank",userService.weekReplyRank());
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
        m.addObject("post",userService.searchPost(post_title));
        m.addObject("weekHot",userService.weekHotPost(user_post));
        return m;
    }

    @RequestMapping("index")
    public ModelAndView index(User_post user_post){   //游客首页
        ModelAndView m = new ModelAndView();
        m.setViewName("index");
        m.addObject("weekHot",userService.weekHotPost(user_post));
        m.addObject("weekRank",userService.weekReplyRank());
        m.addObject("user_post",userService.findAllPost(user_post));
        m.addObject("user_post_top",userService.findAllPostTop(user_post));
        return m;
    }

    //上面社区页面//
    //下面是摄影专区//


    @RequestMapping("index_ph")
    public ModelAndView index_ph(){
        String photoimg = userService.readPhotoimg().getStyle_val();
        ModelAndView m = new ModelAndView();
        m.setViewName("layuiSimpleBlog-v1.0.0/html/index");
        m.addObject("phptoimg",photoimg);
//        m.addObject("ipost",userService.findAllIpost());
        return m;
    }

    @RequestMapping("getIpost")   //公共页面
    public String  getIpost(Integer page){
        List<Ipost> UD = userService.findAllIpost();
        JSONObject js = new JSONObject();
        int size=UD.size(); //数据量
        int limit=4; //页大小
        int pages;
        pages=size%limit==0?size/limit:size/limit+1; //页数
        if(page*limit<=size){
            UD=UD.subList((page-1)*limit, page*limit);
        }else{
            UD=UD.subList((page-1)*limit, size);
        }
        js.put("data",UD);
        js.put("pages",pages);
//        System.out.println(js.toJSONString());
        return js.toJSONString();
    }

    @RequestMapping("getIpost_bac_unpass")   // 审核稿子列表
    public String  getIpost_bac_unpass(@RequestParam int limit, @RequestParam int page){
        List<Ipost> UD = userService.checkAllIpost();
        JSONObject js = new JSONObject();
        int size=UD.size();
        if(page*limit<=size){
            UD=UD.subList((page-1)*limit, page*limit);
        }else{
            UD=UD.subList((page-1)*limit, size);
        }
        js.put("data",UD);
        js.put("count",size);
        js.put("msg","");
        js.put("code",0);
        return js.toJSONString();
    }

    @RequestMapping("getIpost_bac_pass")   // 通过稿子列表
    public String  getIpost_bac_pass(@RequestParam int limit, @RequestParam int page){
        List<Ipost> UD = userService.findAllIpost();
        JSONObject js = new JSONObject();
        int size=UD.size();
        if(page*limit<=size){
            UD=UD.subList((page-1)*limit, page*limit);
        }else{
            UD=UD.subList((page-1)*limit, size);
        }
        js.put("data",UD);
        js.put("count",size);
        js.put("msg","");
        js.put("code",0);
        return js.toJSONString();
    }

    @RequestMapping("getIpost_bac_setback")   // 退回稿子
    public String  getIpost_bac_setback(@RequestParam int limit, @RequestParam int page){
        List<Ipost> UD = userService.setbackedIpost();
        JSONObject js = new JSONObject();
        int size=UD.size();
        if(page*limit<=size){
            UD=UD.subList((page-1)*limit, page*limit);
        }else{
            UD=UD.subList((page-1)*limit, size);
        }
        js.put("data",UD);
        js.put("count",size);
        js.put("msg","");
        js.put("code",0);
        return js.toJSONString();
    }

    @RequestMapping("detail_ph")  // 摄影文章
    public ModelAndView detail_ph(int ipost_id,HttpSession session)throws Exception{

        ModelAndView m = new ModelAndView();
        m.setViewName("layuiSimpleBlog-v1.0.0/html/details");
        m.addObject("ipost_detaill",userService.ipost_detail(ipost_id));
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
        m.addObject("rec",userService.recommendIpost((int)session.getAttribute("userid"))); //搜索度高质量推荐
//        if(recommend.usercf((int)session.getAttribute("userid"))!=null){
//            List<Ipost> ipost= recommend.usercf((int)session.getAttribute("userid"));
//            m.addObject("rec",ipost);
//        }
//        else{
//            m.addObject("rec",userService.recommendIpost((int)session.getAttribute("userid"))); //搜索度高质量推荐
//        }

//        System.out.println(ipost);

        String p_class=userService.ipost_detail(ipost_id).getIpost_class();
        userService.update_IpostTime(ipost_id);
        userService.addHabit((int)session.getAttribute("userid"),p_class);
        userService.ipost_counter(ipost_id);
//        System.out.println(userService.recommendIpost((int)session.getAttribute("userid")));
        return m;
    }



    //上面是展示区//
    //下面是后台//
    @RequestMapping("index_bac") //后台公页
    public ModelAndView index_bac(HttpSession session){
        ModelAndView m = new ModelAndView();
        m.setViewName("background/index");
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
        return m;
    }

    @RequestMapping("user_list") //用户列表
    public ModelAndView user_list(HttpSession session){
        ModelAndView m = new ModelAndView();
        m.setViewName("background/user_list");
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
        return m;
    }

    @RequestMapping("post") //用户列表
    public ModelAndView post(HttpSession session){
        ModelAndView m = new ModelAndView();
        m.setViewName("background/post");
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
        return m;
    }

    @RequestMapping("lockedUser") //封禁的用户列表
    public ModelAndView lockedUser(HttpSession session){
        ModelAndView m = new ModelAndView();
        m.setViewName("background/lockedUser");
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
        return m;
    }

    @RequestMapping("mutedUser") //禁言用户列表
    public ModelAndView mutedUser(HttpSession session){
        ModelAndView m = new ModelAndView();
        m.setViewName("background/mutedUser");
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
        return m;
    }

    @RequestMapping("ipost_bac_unpass") //未通过稿子
    public ModelAndView ipost_bac_unpass(HttpSession session){
        ModelAndView m = new ModelAndView();
        m.setViewName("background/ipost_unpass");
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
        return m;
    }

    @RequestMapping("ipost_bac_pass") //通过稿子
    public ModelAndView ipost_bac_pass(HttpSession session){
        ModelAndView m = new ModelAndView();
        m.setViewName("background/ipost_pass");
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
        return m;
    }

    @RequestMapping("ipost_bac_setback") //通过稿子
    public ModelAndView ipost_bac_setback(HttpSession session){
        ModelAndView m = new ModelAndView();
        m.setViewName("background/ipost_setback");
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
        return m;
    }

    @RequestMapping("user_0") //平民
    public ModelAndView user_0(HttpSession session){
        ModelAndView m = new ModelAndView();
        m.setViewName("background/user_0");
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
        return m;
    }

    @RequestMapping("user_1") //管理
    public ModelAndView user_1(HttpSession session){
        ModelAndView m = new ModelAndView();
        m.setViewName("background/user_1");
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
        return m;
    }

    @RequestMapping("set_main_style") //通过稿子
    public ModelAndView set_main_style(HttpSession session){
        ModelAndView m = new ModelAndView();
        m.setViewName("background/set_main_style");
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
        return m;
    }

    @RequestMapping("charts") //通过稿子
    public ModelAndView charts(HttpSession session){
        ModelAndView m = new ModelAndView();
        m.setViewName("background/charts");
        m.addObject("user",userService.findUserById((int)(session.getAttribute("userid"))));
        return m;
    }

//    下面是提示页面
    @RequestMapping("alert") //用户列表
    public ModelAndView alert(){
        ModelAndView m = new ModelAndView();
        m.setViewName("fly/html/other/notice");
        return m;
    }

    @RequestMapping("getIpostData")
    public String  getIpostData(){
        List<Ipost> ip = userService.findAllIpost(); //数据源ip
        JSONObject j = new JSONObject();  //创建json对象j
        j.put("ip",ip); //将数据源ip放进 json对象 j
        JSONArray val = new JSONArray(); //创建json数组对象val 存放数值
        JSONArray title = new JSONArray();//创建json数组对象title 存放标题
        for(int i =0;i<ip.size();i++){   //循环数据源ip
            val.add(ip.get(i).getIpost_count()); //取出ip中的值
            title.add((ip.get(i).getIpost_title())); //取出ip中的标题
        }
//        System.out.println(ip);
        j.put("val",val);  //将json数组对象val存入 json对象j
        j.put("title",title); //将json数组对象title存入 json对象j
        return j.toString(); //返回json对象j到前端
    }

}
