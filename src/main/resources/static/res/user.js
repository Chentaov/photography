$(function () {


    $("#register").click(function () {    //注册
        var email = $("#L_email").val();
        var name = $("#L_username").val();
        var password = $("#L_pass").val();
        var repass  = $("#L_repass").val();
        $.ajax({
            url:'/doregister',
            type:'post',
            data:{
                "email":email,
                "name":name,
                "password":password
            },
            success:function (data) {
                if(JSON.stringify(data)=='1'){
                    layer.msg("注册成功");
                }
            },
            error:function () {
                layer.msg("接口异常");
            }
        })
    })

    $("#L_email").blur(function () { //email查重
        $.ajax({
            url:'/check_email',
            type:'post',
            data:{"email":$("#L_email").val()},
            success:function (data) {
                if(JSON.stringify(data)=='true'){
                    layer.msg("email已存在");
                    $("#L_email").val('');
                }
                else {
                }
            },
            error:function () {
            }
        })
    })

    $("#L_email_forget").blur(function () { //email查有没有该用户
        $.ajax({
            url:'/check_email',
            type:'post',
            data:{"email":$("#L_email_forget").val()},
            success:function (data) {
                if(JSON.stringify(data)=='true'){
                    // layer.msg("email已存在");
                    // $("#L_email").val('');
                }
                else {
                    layer.msg("没有此人！");
                    $("#L_email_forget").val('');
                }
            },
            error:function () {
            }
        })
    })

    $("#login").click(function () {  //登录
        var email = $("#L_email_login").val();
        var password = $("#L_pass_login").val();
        $.ajax({
            url:'/dologin',
            type:'post',
            data:{"email":email,"password":password},
            success:function (data) {
                if(JSON.stringify(data)=='true'){
                    layer.msg("登录成功");
                    $(location).attr("href","/index_jie");
                }
                else {
                    layer.msg("登录失败");
                }
            },
            error:function () {

            }
        })
    })

    $("#updateUserInfo").click(function () {  //更新用户信息
        var name = $("#L_username").val();
        var city = $("#L_city").val();
        var des = $("#L_sign").val();
        var gender = $('input[name="sex"]:checked ').val();
        var id = $("#userid").html();
        if(name==''||city==''||gender==''||des==''){layer.msg("你没写完哦，不放你过！"); return;}
        $.ajax({
            url:'/updateUserInfo',
            type:'post',
            data:{
                "id":id,
                "name":name,
                "city":city,
                "des":des,
                "gender":gender
            },
            success:function (data) {
                if(JSON.stringify(data)=='true'){
                   // $(location).attr("href","/set");
                   layer.msg("修改成功！");
                    setTimeout(function(){
                        window.location.reload();//刷新当前页面.
                    },2000)
                }
            },
            error:function () {
                layer.msg("接口异常");
            }
        })
    })

    $("#updateEmail").click(function () {  //更新用户信息
        var email = $("#L_email").val();
        var id =$("#userid").html();
        if(email==''){layer.msg("你没写完哦，看来不用修改～！"); return;}
        $.ajax({
            url:'/updateEmail',
            type:'post',
            data:{
                "id":id,
                "email":email
            },
            success:function (data) {
                if(JSON.stringify(data)=='true'){
                    layer.msg("修改成功！");
                    setTimeout(function(){
                        window.location.reload();//刷新当前页面.
                    },2000)
                }
            },
            error:function () {
                layer.msg("接口异常");
            }
        })
    })

    $("#updatePassword").click(function () { //更新密码
        var id = $("#userid").html();
        var password = $("#L_pass").val();
        $.ajax({
            url:'/updatePassword',
            type:'post',
            data:{"id":id,"password":password},
            success:function (data) {
                if(JSON.stringify(data)=='true'){
                    layer.alert("修改成功");
                    setTimeout(function(){
                        window.location.reload();//刷新当前页面.
                    },2000)
                }
            },
            error:function () {
                layer.msg("接口异常");
            }
        })
    })

    $("#resetPassword").click(function () {
        var email = $("#L_email_forget").val();
         // if(email==''){lay.msg("填都没填查什么查～"); return;}
        $.ajax({
            url:'/sendEmailReset',
            type:'post',
            data:{"email":email},
            success:function (data) {

                    layer.msg("已发送重置邮箱");

            },
            error:function () {
                layer.msg("好像没发送成功？？");
            }
        })
    })

    $("#reply").click(function () {  //回复帖子
        var reply_name = $("#reply_name").html();
        var reply_owner_id = $("#reply_owner_id").html();
        var reply_content = $("#L_content").val();
        var accepter_id = $("#accept_id").html();
        var post_id = $("#post_id").html();
        var location_title = $("#post_title").html();//在这
        var noticer_id = $("#reply_owner_id").html();
        var noticee_id = $("#accept_id").html();
        var noticee = $("#poster_name").html();
        // alert(noticer_id);
        // alert(reply_name+reply_owner_id+reply_content+accepter_id);
        $.ajax({
            url:'/acceptReply',
            type:'post',
            data:{
                "reply_name":reply_name,
                "reply_owner_id":reply_owner_id,
                "reply_content":reply_content,
                "accepter_id":accepter_id,
                "post_id":post_id,
                "noticer":reply_name,
                "location_title":location_title,
                "location_id":post_id,
                "noticer_id":noticer_id,
                "noticee_id":noticee_id,
                "noticee":noticee,
                "notice_content":reply_content
            },
            success:function (data) {
                if(JSON.stringify(data)=='2'){
                    $("#L_content").val('').focus();
                    layer.msg("回复成功");
                    setTimeout(function(){
                        window.location.reload();//刷新当前页面.
                    },2000)
                }
                else if(JSON.stringify(data)=='1'){
                    layer.msg("你嗓子哑了，多喝水～");
                }
                else if(JSON.stringify(data)=='0'){
                    layer.msg("回复失败再试试！");
                }
            },
            error:function () {
                layer.msg("接口异常");
            }
        })
    })

    $("#follow").click(function () {
        var follower_id = $("#userid").html();
        var follower_name = $("#username").html();
        var obj_id = $("#obj_id").html();
        var obj_name = $("#obj_name").html();
        $.ajax({
            url:'/follow',
            type:'post',
            data:{
                "follower_id":follower_id,
                "follower_name":follower_name,
                "obj_id":obj_id,
                "obj_name":obj_name
            },
            success:function (data) {
                // alert(JSON.stringify(data));
                if(JSON.stringify(data)==0){
                    // layer.msg("已经取关");
                    $("#follow").html("关注一波");
                    $("#follow").attr("class","layui-btn layui-btn-normal fly-imActive")
                }
                else{
                    // layer.msg("已经关注");
                    $("#follow").attr("class","layui-btn layui-btn-primary fly-imActive")
                    $("#follow").html("取关");
                }
            },
            error:function () {
                 layer.msg("接口异常");
            }
        })
    })

})