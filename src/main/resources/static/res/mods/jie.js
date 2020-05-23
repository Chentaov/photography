/**

 @Name: 求解板块

 */
 
layui.define('fly', function(exports){

  var $ = layui.jquery;
  var layer = layui.layer;
  var util = layui.util;
  var laytpl = layui.laytpl;
  var form = layui.form;
  var fly = layui.fly;
  
  var gather = {}, dom = {
    jieda: $('#jieda')
    ,content: $('#L_content')
    ,jiedaCount: $('#jiedaCount')
  };

  //监听专栏选择
  form.on('select(column)', function(obj){
    var value = obj.value
    ,elemQuiz = $('#LAY_quiz')
    ,tips = {
      tips: 1
      ,maxWidth: 250
      ,time: 10000
    };
    elemQuiz.addClass('layui-hide');
    if(value === '摄影专栏'){
      layer.tips('请按规范投稿，否则将会受到处罚。', obj.othis, tips);
      elemQuiz.removeClass('layui-hide');
     }// else if(value === '99'){
    //   layer.tips('系统会对【分享】类型的帖子予以飞吻奖励，但我们需要审核，通过后方可展示', obj.othis, tips);
    // }
  });

  //提交回答
  fly.form['/jie/reply/'] = function(data, required){
    var tpl = '<li>\
      <div class="detail-about detail-about-reply">\
        <a class="fly-avatar" href="/u/{{ layui.cache.user.uid }}" target="_blank">\
          <img src="{{= d.user.avatar}}" alt="{{= d.user.username}}">\
        </a>\
        <div class="fly-detail-user">\
          <a href="/u/{{ layui.cache.user.uid }}" target="_blank" class="fly-link">\
            <cite>{{d.user.username}}</cite>\
          </a>\
        </div>\
        <div class="detail-hits">\
          <span>刚刚</span>\
        </div>\
      </div>\
      <div class="detail-body jieda-body photos">\
        {{ d.content}}\
      </div>\
    </li>'
    data.content = fly.content(data.content);
    laytpl(tpl).render($.extend(data, {
      user: layui.cache.user
    }), function(html){
      required[0].value = '';
      dom.jieda.find('.fly-none').remove();
      dom.jieda.append(html);
      
      var count = dom.jiedaCount.text()|0;
      dom.jiedaCount.html(++count);
    });
  };

  //求解管理
  gather.jieAdmin = {
    //删求解
    del: function(div){
      layer.confirm('确认删除该帖子么？', function(index){
        layer.close(index);
        var post_id = div.data('id');
        $.ajax({
            async:false,
            contentType: 'application/json;charset=UTF-8',
            url:'/deletePost?post_id='+post_id,
            type:'post',
            data: JSON.stringify(deldata),

          success:function (data) {
            if(JSON.stringify(data)=='true'){
              layer.msg("已删除帖子");
              $(location).attr("href","/index_jie");
            }
          },
          error:function () {
              layer.msg("接口异常");
          }
        })
      });
    }
    
    //设置置顶、状态
    ,set_top: function(div){
      var post_id = div.data('id');
      $.ajax({
        url:'/setPostTop',
        type:'post',
        data:{
          "post_id":post_id
        },
        success:function (data) {
          if(JSON.stringify(data)=='true'){
            layer.msg("置顶成功");
          }
        }
      })
    }
    //取消置顶
    ,un_setop:function (div) {
      var post_id = div.data('id');
      $.ajax({
        url:'/un_setPostTop',
        type:'post',
        data:{
          "post_id":post_id
        },
        success:function (data) {
          if(JSON.stringify(data)=='true'){
            layer.msg("取消置顶成功");
          }
        }
      })
    }
    ,set_nice:function (div) {  //加精
      var post_id = div.data('id');
      $.ajax({
        url:'/set_nice',
        type:'post',
        data:{
          "post_id":post_id
        },
        success:function (data) {
          if(JSON.stringify(data)=='true'){
            layer.msg("已加精");
          }
        }
      })
    }
    ,un_setnice:function (div) {  //取消精
      var post_id = div.data('id');
      $.ajax({
        url:'/un_setnice',
        type:'post',
        data:{
          "post_id":post_id
        },
        success:function (data) {
          if(JSON.stringify(data)=='true'){
            layer.msg("已取消精");
          }
        }
      })
    }
    //收藏
    ,collect: function(div){
      var othis = $(this), type = othis.data('type');
      fly.json('/collection/'+ type +'/', {
        cid: div.data('id')
      }, function(res){
        if(type === 'add'){
          othis.data('type', 'remove').html('取消收藏').addClass('layui-btn-danger');
        } else if(type === 'remove'){
          othis.data('type', 'add').html('收藏').removeClass('layui-btn-danger');
        }
      });
    }
  };

  $('body').on('click', '.jie-admin', function(){
    var othis = $(this), type = othis.attr('type');
    gather.jieAdmin[type] && gather.jieAdmin[type].call(this, othis.parent());
  });

  //异步渲染
  var asyncRender = function(){
    var div = $('.fly-admin-box'), jieAdmin = $('#LAY_jieAdmin');
    //查询帖子是否收藏
    if(jieAdmin[0] && layui.cache.user.uid != -1){
      fly.json('/collection/find/', {
        cid: div.data('id')
      }, function(res){
        jieAdmin.append('<span class="layui-btn layui-btn-xs jie-admin '+ (res.data.collection ? 'layui-btn-danger' : '') +'" type="collect" data-type="'+ (res.data.collection ? 'remove' : 'add') +'">'+ (res.data.collection ? '取消收藏' : '收藏') +'</span>');
      });
    }
  }();

  //解答操作
  gather.jiedaActive = {

    zan: function(li){ //赞
      var othis = $(this), ok = othis.hasClass('zanok');
      var clicker_id = $("#reply_owner_id").html();
      var zan_id = $(this).children(li).attr("id");
      // var ok = $(this).children(li).next().attr("id");
      // alert(ok);
      // alert(zan_id+clicker_id);
      $.ajax({
        url:'/checkZanStatus?obj_id='+zan_id,
        type:'post',
        data:{
          "ok":ok,
          "zan_id":zan_id,
          "clicker_id":clicker_id
        },
        success:function (data) {
          if(JSON.stringify(data)=='true'){
            ok = true;
          }else {
            ok = false;
          }
           var zans = othis.find('em').html()|0;
           othis[ok ? 'removeClass' : 'addClass']('zanok');
           othis.find('em').html(ok ? (--zans) : (++zans));

        },
        error:function () {
        }
      })
     // alert("zanid:"+li.data('id')+"clicker:"+clicker_id);
     //  fly.json('/checkZanStatus?'+clicker_id, {
     //    ok: ok //status
     //    ,id: li.data('id') //zanID  有就取消，没用就赞
     //  }, function(res){
     //    if(res.status === 0){
     //      var zans = othis.find('em').html()|0;
     //      othis[ok ? 'removeClass' : 'addClass']('zanok');
     //      othis.find('em').html(ok ? (--zans) : (++zans));
     //    } else {
     //      layer.msg(res.msg);
     //    }
     //  });
    }
    ,reply: function(li){ //回复
      var val = dom.content.val();
      var aite = '@'+ li.find('.fly-detail-user cite').text().replace(/\s/g, '');
      dom.content.focus()
      if(val.indexOf(aite) !== -1) return;
      // dom.content.val(aite +' ' + val);
      layer.prompt({title: '向他回复', formType: 0,value:aite +' '}, function(val, index){
        var reply_name = $("#reply_name").html();
        var reply_owner_id = $("#reply_owner_id").html();
        var reply_content = val;
        var accepter_id = $("#accept_id").html();
        var post_id = $("#post_id").html();
        var noticer_id = $("#reply_owner_id").html(); //我 登录者
        var location_title = $("#post_title").html();//在这
        var noticee_id = li.find('.fly-detail-user cite').attr("id"); //@了他  //回帖者id
        var noticee = li.find('.fly-detail-user cite').text().replace(/\s/g, '');
        // alert(location_title);
      //  alert(notice_id);
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
            "noticer_id":noticer_id,
            "location_title":location_title,
            "location_id":post_id,
            "noticee_id":noticee_id,
            "noticee":noticee,
            "notice_content":reply_content
          },
          success:function (data) {
            if(JSON.stringify(data)=='2'){
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
        layer.close(index);
      });
    }
    ,accept: function(li){ //采纳
      var othis = $(this);
      layer.confirm('是否采纳该回答为最佳答案？', function(index){
        layer.close(index);
        fly.json('/api/jieda-accept/', {
          id: li.data('id')
        }, function(res){
          if(res.status === 0){
            $('.jieda-accept').remove();
            li.addClass('jieda-daan');
            li.find('.detail-about').append('<i class="iconfont icon-caina" title="最佳答案"></i>');
          } else {
            layer.msg(res.msg);
          }
        });
      });
    }
    ,edit: function(li){ //编辑
      fly.json('/jie/getDa/', {
        id: li.data('id')
      }, function(res){
        var data = res.rows;
        layer.prompt({
          formType: 2
          ,value: data.content
          ,maxlength: 100000
          ,title: '编辑回帖'
          ,area: ['728px', '300px']
          ,success: function(layero){
            fly.layEditor({
              elem: layero.find('textarea')
            });
          }
        }, function(value, index){
          fly.json('/jie/updateDa/', {
            id: li.data('id')
            ,content: value
          }, function(res){
            layer.close(index);
            li.find('.detail-body').html(fly.content(value));
          });
        });
      });
    }
    ,del: function(li){ //删除
       var reply_id= $(li).attr("id");

      layer.confirm('确认删除该回答么？', function(index){
        layer.close(index);
        $.ajax({
            url:'/deleteReply',
            type:'post',
            data:{
                "reply_id":reply_id
            },
            success:function (data) {
                if(JSON.stringify(data)=='true'){
                    layer.msg("成功删除");
                  setTimeout(function(){
                    window.location.reload();//刷新当前页面.
                  },2000)
                }
            },
            error:function () {
                layer.msg("接口异常");
            }
        })
      });    
    }
  };

  $('.jieda-reply span').on('click', function(){
    var othis = $(this), type = othis.attr('type');
    gather.jiedaActive[type].call(this, othis.parents('li'));
  });


  //定位分页
  if(/\/page\//.test(location.href) && !location.hash){
    var replyTop = $('#flyReply').offset().top - 80;
    $('html,body').scrollTop(replyTop);
  }

  exports('jie', null);
});