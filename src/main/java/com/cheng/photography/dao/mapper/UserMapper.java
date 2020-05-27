package com.cheng.photography.dao.mapper;

import com.cheng.photography.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
@Mapper
public interface UserMapper {
    int register(User user); //注册
    User check_email(User user);// email查重
    User login(User user);  //登录
    void actEmail(int id); //激活用户
    int check_time(String check_time,int id); //更新发送激活邮箱的时间默认id
    int check_timeByEmail(String check_time,String email);
    User findUserById(int id); //id找用户信息
    User findUserByEmail(String email); //id找用户信息
    int updateUserInfo(User user); //更新用户信息
    int updateEmail(User user);
    int updateEmailStatus(int id);//根据id更新用户激活状态
    int updateHeadIcon(User user);//根据id换头像
    int updateIcon(int id,String head_icon);
    int updatePassword(User user);//根据id修改密码
    int resetPasswordByEmail(String email);//根据email重置密码
    int userPost(User_post user_post);//用户发帖
    List<User_post> findAllPostTop(User_post user_post); //查找所有置顶帖子
    List<User_post> findAllPost(User_post user_post); //查找所有没置顶的帖子
    List<User_post> postList(User_post user_post);//无条件查post
    User_post post_detail(int post_id);//根据帖子id查找帖子内容
    int replyByPosterId(Reply reply);//根据发贴人id回复
    List<Reply> findReplyByPostId(int PostId);//根据PostId查找回复表
    Zan_reply checkZanStatus(Zan_reply zan_reply);//根据点赞id，点赞者id，点赞状态返回。
    int ZanToTrue(Zan_reply zan_reply);//置true
    int ZanToFalse(Zan_reply zan_reply);//置flase
    int ZanCountPlus(Zan_reply zan_reply);//赞++
    int ZanCountSub(Zan_reply zan_reply);//赞--
    int InitClicker(Reply_clicker reply_clicker);//初始化点赞者
    Reply_clicker checkClickerAndZan(Reply_clicker reply_clicker);//查询点赞者和赞是否已关联
    List<User_post> findUserPostByUid(int poster_id); //根据用户id找他最近发表的贴
    int notice(Notices notices); //根据被提醒者id在某个帖子附上帖子id回复他内容在xxx时间
    List<Notices> findMyNoticesById(int noticee_id);//根据被回贴人id搜罗消息
    List<User_post> weekHotPost(User_post user_post); //每周热贴
    List<User> weekReplyRank();//根据id列出周榜
    List<User> findAllUser(User user);//列出所有用户信息
    int setPostTop(User_post user_post);//根据帖子id置顶
    int un_setPostTop(User_post user_post);//根据帖子id置顶
    int set_nice(User_post user_post);//设置加精
    int un_setnice(User_post user_post);//取消加精
    int deleteReply(Reply reply);//删除回复
    int deletePost(User_post user_post);//删除帖子顺带删除回复接力删除赞
    int ipost(Ipost ipost);//投稿专栏
    List<Ipost> findAllIpost(); //通过的投稿
    List<Ipost> checkAllIpost(); //没通过投稿
    List<Ipost> setbackedIpost(); //退回的稿子
    Ipost ipost_detail(int ipost_id);//根据ipost_id找稿子内容
    int addHabit(int uid,String p_class); //养成习惯数据
    List<Ipost> recommendIpost(int uid); //根据用户id推荐
    Ipost recommendIpost2(int ipost_id);
    int update_IpostTime(int ipost_id);//更新点击时间
    Ipost_val checkIpostValuer(Ipost_val ipost_val);//检查评分者与帖子是否关联
    int InitValer(Ipost_val ipost_val);//初始化评分者信息
    int update_IpostVal(Ipost_val ipost_val); //用户给ipost打分
    int ipost_counter(int ipost_id); //观看计数器
    int userpost_counter(int post_id);//帖子点击计数器
    int update_post_time(int post_id);//更新post时间上浮
    List<Integer> findAllUid(); //所有user id
    List<Integer> findUserLikeIpost(int valer_id); //根据用户id找出他们喜欢的ipost
    int followInit(Follow follow); //第一次关注初始化
    Follow checkFollowStatus(Follow follow); //检查是否存在关注关系
    int follow_him(Follow follow); //关注
    int unfollow_him(Follow follow); //取关
    Follow isfollow(Follow follow); //是否关注
    Integer follow_status(int follower_id,int obj_id); //关注状态
    List<Follow> ifollowed(int id);//我关注的
    List<User_post> searchPost(String title);//根据标题搜帖子
    int updatePosterName(int poster_id,String poster_name); //更新投稿表姓名
    int updateReplyerName(int reply_owner_id,String reply_name);//更新回复姓名
    List<Ipost_val> iLiked(int valer_id); //获取喜欢列表
    int lockUser(int id);//封号
    List<User> lockedUser(User user);//封禁的用户
    int unlockUser(int id);//解封用户
    int muteUser(int id);//禁言用户
    List mutedUser(User user);//禁言的用户
    int unmuteUser(int id);//解禁言用户
    User findHeadicon(int id);//根据id查头像路径
    User isUserMute(int id);
    int passIpost(int ipost_id);//通过投稿
    int del_ipost(int ipost_id);//删除ipost
    int setback_ipost(int ipost); //退回投稿
    int repass_ipost(int ipost);//重新通过稿子
    int setManage(int id);//设为1级管理
    List<User> Managers(User user);//管理层
    int setUser0(int id);//降为平民
    int setNav_color(String style_val);//设置导航主色
    Web_style readNav_color();//读导航配色
    int setERcode(String style_val); //设置ERcode
    Web_style readERcode(); //读二维码
    int setPhotoimg(String style_val); //写主图
    Web_style readPhotoimg();//读主图
    int del_msg(int notices_id); //删除消息
    int del_allmsg(int noticee_id);//清空消息
    int accept_best(int reply_id);//采纳
    int close_reply_nice(int post_id);//关闭神评
    int addTags(Tags tags); //新增标签
    int remove_Tags(Tags tags);//删除标签
    List<Tags> tagList_pic(); //图标签list
    List<Tags> tagList_vdo();//视频标签list
    int updatePost(User_post user_post);//更贴
}
