package com.cheng.photography.pojo;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class User {
    private int id; // 用户id
    private String email; //邮箱
    private String name; //昵称
    private String password; //密码
    private int status; //激活状态
    private String check_time;//粗略计为绑定邮箱的时间
    private String city; //城市
    private String des; //签名
    private String gender; //性别
    private String head_icon; //头像
    private int identity; //权限标识
    private int ureply_count; //评论数
    private int exp; //经验
    private int islock;//封禁
    private int ismute;//禁言
}
