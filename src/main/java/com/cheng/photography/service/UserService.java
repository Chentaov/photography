package com.cheng.photography.service;

import com.cheng.photography.dao.mapper.UserMapper;
import com.cheng.photography.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public int register(User user) { //注册
        return userMapper.register(user);
    }

    public User check_email(User user) {  //查重
        return userMapper.check_email(user);
    }

    public User login(User user) {
        return userMapper.login(user); //登录
    }

    public void actEmail(int id) {
        userMapper.actEmail(id);
    }//发送邮件激活

    public int check_time(String check_time, int id) {
        return userMapper.check_time(check_time, id);
    } // 存入绑定邮箱的时间

    public int check_timeByEmail(String check_time, String email) {
        return userMapper.check_timeByEmail(check_time, email);
    }

    public User findUserById(int id) {
        return userMapper.findUserById(id);
    } //根据用户id查找用户所有信息

    public User findUserByEmail(String email) {
        return userMapper.findUserByEmail(email);
    }// 根据email找用户所有信息

    public int updateUserInfo(User user) {
        return userMapper.updateUserInfo(user);
    } //更新用户信息

    public int updateEmail(User user) {
        return userMapper.updateEmail(user);
    } //更新邮箱

    public void updateEmailStatus(int id) {
        userMapper.updateEmailStatus(id);
    } //根据id更新用户激活状态

    public int updateHeadIcon(User user) {
        return userMapper.updateHeadIcon(user);
    } //更新头像

    public int updateIcon(int id, String head_icon) {
        return userMapper.updateIcon(id, head_icon);
    } //文件上传方式更新头像

    public int updatePassword(User user) {
        return userMapper.updatePassword(user);
    } //更新用户密码

    public int resetPasswordByEmail(String email) {
        return userMapper.resetPasswordByEmail(email);
    }

    public int userPost(User_post user_post) {
        return userMapper.userPost(user_post);
    } //用户发贴

    public List<User_post> findAllPostTop(User_post user_post) {
        return userMapper.findAllPostTop(user_post);
    } //查找所有置顶的帖子

    public List<User_post> findAllPost(User_post user_post) {
        return userMapper.findAllPost(user_post);
    } //查找所有没置顶的帖子

    public List<User_post> postList(User_post user_post){
        return userMapper.postList(user_post); //无条件查找post
    }

    public User_post post_detail(int post_id) {
        return userMapper.post_detail(post_id);
    }//根据帖子id查找帖子信息

    public int replyByPosterId(Reply reply) {
        return userMapper.replyByPosterId(reply);
    } //根据发贴人id回复

    public List<Reply> findReplyByPostId(int PostId) {
        return userMapper.findReplyByPostId(PostId);
    } // 根据PostId找回复表

    public Zan_reply checkZanStatus(Zan_reply zan_reply) {
        return userMapper.checkZanStatus(zan_reply);
    } //根据点赞id，点赞者id，点赞状态返回。

    public int ZanToTrue(Zan_reply zan_reply) {
        return userMapper.ZanToTrue(zan_reply);
    } //赞置true

    public int ZanToFalse(Zan_reply zan_reply) {
        return userMapper.ZanToFalse(zan_reply);
    } //赞置flase

    public int ZanCountPlus(Zan_reply zan_reply) {
        return userMapper.ZanCountPlus(zan_reply);
    }//赞++

    public int ZanCountSub(Zan_reply zan_reply) {
        return userMapper.ZanCountSub(zan_reply);
    }//赞--

    public int InitClicker(Reply_clicker reply_clicker) {
        return userMapper.InitClicker(reply_clicker);
    }//初始化点赞者

    public Reply_clicker checkClickerAndZan(Reply_clicker reply_clicker){
        return userMapper.checkClickerAndZan(reply_clicker); //检查赞和点赞者的关联
    }

    public List<User_post> findUserPostByUid(int poster_id){
        return userMapper.findUserPostByUid(poster_id); //根据用户id找他最近发表的贴
    }

    public int notice(Notices notices){
        return userMapper.notice(notices); //根据被提醒者id在某个帖子附上帖子id回复他内容在xxx时间
    }

    public List<Notices> findMyNoticesById(int noticee_id){
        return userMapper.findMyNoticesById(noticee_id); ////根据被回贴人id搜罗消息
    }

    public List<User_post> weekHotPost(User_post user_post){
        return userMapper.weekHotPost(user_post);//每周热贴
    }

    public List<User> weekReplyRank(){
        return userMapper.weekReplyRank();////根据id列出周榜
    }
    public List<User> findAllUser(User user){
        return userMapper.findAllUser(user);//列出所有用户
    }
    public int setPostTop(User_post user_post){
        return userMapper.setPostTop(user_post); //根据帖子id置顶
    }
    public int un_setPostTop(User_post user_post){return userMapper.un_setPostTop(user_post);} //取消置顶
    public int set_nice(User_post user_post){return userMapper.set_nice(user_post);}//设置加精
    public int un_setnice(User_post user_post){return userMapper.un_setnice(user_post);}//取消加精
    public int deleteReply(Reply reply){return userMapper.deleteReply(reply);} //删除回复
    public int deletePost(User_post user_post){return userMapper.deletePost(user_post);}//删除帖子顺带删除回复接力删除赞
    public int ipost(Ipost ipost){return userMapper.ipost(ipost);} //投专栏
    public List<Ipost> findAllIpost(){return userMapper.findAllIpost();}//找出所有的专栏投稿;
    public Ipost ipost_detail(int ipost_id){return userMapper.ipost_detail(ipost_id);} //根据ipost_id找投稿内容
    public int addHabit(int uid,String p_class){return userMapper.addHabit(uid,p_class);}; //养成习惯数据
    public List<Ipost> recommendIpost(int uid){return userMapper.recommendIpost(uid);} //根据用户id推荐
    public int update_IpostTime(int ipost_id){return userMapper.update_IpostTime(ipost_id);}//更新点击时间
    public Ipost_val checkIpostValuer(Ipost_val ipost_val){return userMapper.checkIpostValuer(ipost_val);} //检查评分者与帖子是否关联
    public int InitValer(Ipost_val ipost_val){return userMapper.InitValer(ipost_val);} //初始化评分者信息
    public int update_IpostVal(Ipost_val ipost_val){return userMapper.update_IpostVal(ipost_val);}//用户给ipost打分
    public int ipost_counter(int ipost_id){return userMapper.ipost_counter(ipost_id);} //点击计数器
    public int userpost_counter(int post_id){return userMapper.userpost_counter(post_id);} //帖子点击计数器
    public int update_post_time(int post_id){return userMapper.update_post_time(post_id);} //更新post时间上浮
    public List<Integer> findAllUid(){return userMapper.findAllUid();} //uid集合
    public List<Integer> findUserLikeIpost(int valer_id){return userMapper.findUserLikeIpost(valer_id);} //根据用户id找到他们喜欢的ipos
    public Ipost recommendIpost2(int ipost_id){return userMapper.recommendIpost2(ipost_id);} //协同过滤推荐
    public int followInit(Follow follow){return userMapper.followInit(follow);} //第一次关注初始化
    public Follow checkFollowStatus(Follow follow) {return userMapper.checkFollowStatus(follow);} ///
    public int follow_him(Follow follow){return  userMapper.follow_him(follow);} //关注
    public int unfollow_him(Follow follow){return  userMapper.unfollow_him(follow);} //取关
    public Follow isfollow(Follow follow){return userMapper.isfollow(follow);} //是否关注
    public Integer follow_status(int follower_id,int obj_id){return userMapper.follow_status(follower_id,obj_id);}//关注状态
    public List<Follow> ifollowed(int id){return userMapper.ifollowed(id);} //我关注的
    public List<User_post> searchPost(String title){return userMapper.searchPost(title);} //根据标题搜帖子
    public int updatePosterName(int poster_id,String poster_name){return userMapper.updatePosterName(poster_id,poster_name);}//更新posterName
    public int updateReplyerName(int reply_owner_id,String reply_name){return userMapper.updateReplyerName(reply_owner_id,reply_name);}//更新回复者Name
    public List<Ipost_val> iLiked(int valer_id){return userMapper.iLiked(valer_id);} //我喜欢的列表
    public int lockUser(int id){return userMapper.lockUser(id);} //封号
    public List<User> lockedUser(User user){return userMapper.lockedUser(user);}//封禁的用户
    public int unlockUser(int id){return userMapper.unlockUser(id);}//解封用户
    public int muteUser(int id){return userMapper.muteUser(id);} // 禁言用户
    public List<User> mutedUser(User user){return userMapper.mutedUser(user);} //禁言的用户
    public int unmuteUser(int id){return userMapper.unmuteUser(id);} //解禁言用户
    public User findHeadicon(int id){return userMapper.findHeadicon(id);}//根据id查头像路径
    public User isUserMute(int id){return userMapper.isUserMute(id);}//是否禁言
    public List<Ipost> checkAllIpost(){return userMapper.checkAllIpost();} //审查投稿
    public List<Ipost> setbackedIpost(){return userMapper.setbackedIpost();} //退回的稿子
    public int passIpost(int ipost_id){return userMapper.passIpost(ipost_id);}//通过投稿并通知用户
    public int del_ipost(int ipost_id){return userMapper.del_ipost(ipost_id);} //删除投稿
    public int setback_ipost(int ipost_id){return userMapper.setback_ipost(ipost_id);} //退回投稿
    public int repass_ipost(int ipost_id){return userMapper.repass_ipost(ipost_id);} //重新通过稿子
    public int setManage(int id){return userMapper.setManage(id);} //设为1级管理
    public List<User> Managers(User user){return userMapper.Managers(user);}//管理层
    public int setUser0(int id){return userMapper.setUser0(id);} //降为平民
    public int setNav_color(String style_val){return userMapper.setNav_color(style_val);}//设置导航主色
    public  Web_style readNav_color(){return userMapper.readNav_color();} //读导航主色
    public int setERcode(String style){return userMapper.setERcode(style);} //设置ERcode
    public Web_style readERcode(){return userMapper.readERcode();} //读二维码
    public int setPhotoimg(String style_val){return userMapper.setPhotoimg(style_val);} //写主图
    public Web_style readPhotoimg(){return userMapper.readPhotoimg();} //
    public int del_msg(int notices_id){return userMapper.del_msg(notices_id);}//删除消息
    public int del_allmsg(int noticee_id){return userMapper.del_allmsg(noticee_id);}//清空消息
    public int accept_best(int reply_id){return userMapper.accept_best(reply_id);} //采纳最佳
        public int close_reply_nice(int post_id){return userMapper.close_reply_nice(post_id);} //关闭神评
    public int addTags(Tags tags){return userMapper.addTags(tags);} //新增标签
    public int remove_Tags(Tags tags){return userMapper.remove_Tags(tags);} //删除标签
    public List<Tags> tagList_pic(){return userMapper.tagList_pic();}//图标签list
    public List<Tags> tagList_vdo(){return userMapper.tagList_vdo();}//视频标签list
    public int updatePost(User_post user_post){return userMapper.updatePost(user_post);}//更新帖子
}
