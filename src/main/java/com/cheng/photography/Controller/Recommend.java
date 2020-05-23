package com.cheng.photography.Controller;

import com.alibaba.fastjson.JSONObject;
import com.cheng.photography.pojo.Ipost;
import com.cheng.photography.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/")
public class Recommend {
    @Autowired
    private UserService userService;
    //基于用户的智能协同过滤算法推荐课程
    public List<Ipost> usercf(Integer uid) throws Exception{
        List<Ipost> ipost=new ArrayList<>() ;
//        Map<Integer,Double> recommendvalue=new HashMap<>();
        int N = userService.findAllUid().size(); //1 数据库用户总数
        int srN=userService.findAllIpost().size(); //2  数据库课程总数
        int[][] sparseMatrix = new int[N][N];//建立用户稀疏矩阵，用于用户相似度计算【相似度矩阵】
        Map<Integer, Integer> userItemLength = new HashMap<>();//存储每一个用户对应的不同物品总数  eg: A 3
        Map<Integer, Set<Integer>> itemUserCollection = new HashMap<>();//建立物品到用户的倒排表 eg: a A B
        Set<Integer> items = new HashSet<>();//辅助存储物品集合
        Map<Integer, Integer> userID = new HashMap<>();//辅助存储每一个用户的用户ID映射
        Map<Integer, Integer> idUser = new HashMap<>();//辅助存储每一个ID对应的用户映射
        List<Integer> tmpuid=userService.findAllUid();  //3 数据库所有用户id
        //3 用户-课程二维数组 ↓
        Integer [][] tempuser_item=new Integer[N][srN];
        for(int i=0;i<N;i++){
            tempuser_item[i][0]=tmpuid.get(i); //用户id
            List<Integer> userlikeList=userService.findUserLikeIpost(tempuser_item[i][0]); //用户关注的课程List
            int tmplength=userlikeList.size();
            for(int k=0;k<tmplength;k++){
                tempuser_item[i][k+1]=userlikeList.get(k); //构造数组 0下标用户id，之后put用户关注课程id
            }
        }
        //3 用户-二维数组 ↑
        for(int i = 0; i < N ; i++){
            Integer[] user_item = tempuser_item[i];
            int length = user_item.length;
            userItemLength.put(user_item[0], length-1);//eg: A 3
            userID.put(user_item[0], i);//用户ID与稀疏矩阵建立对应关系
            idUser.put(i, user_item[0]);
            //建立物品--用户倒排表
            for(int j = 1; j < length; j ++){
                if(items.contains(user_item[j])){//如果已经包含对应的物品--用户映射，直接添加对应的用户
                    itemUserCollection.get(user_item[j]).add(user_item[0]);
                }else{//否则创建对应物品--用户集合映射
                    items.add(user_item[j]);
                    itemUserCollection.put(user_item[j], new HashSet<Integer>());//创建物品--用户倒排关系
                    itemUserCollection.get(user_item[j]).add(user_item[0]);
                }
            }
        }
        /*System.out.println(itemUserCollection.toString());*/
        //计算相似度矩阵【稀疏】
        Set<Map.Entry<Integer, Set<Integer>>> entrySet = itemUserCollection.entrySet();
        Iterator<Map.Entry<Integer, Set<Integer>>> iterator = entrySet.iterator();
        while(iterator.hasNext()){
            Set<Integer> commonUsers = iterator.next().getValue();
            for (Integer user_u : commonUsers) {
                for (Integer user_v : commonUsers) {
                    if(user_u.equals(user_v)){
                        continue;
                    }
                    sparseMatrix[userID.get(user_u)][userID.get(user_v)] += 1;//计算用户u与用户v都有正反馈的物品总数
                }
            }
        }
        /*System.out.println(userItemLength.toString());*/
        Integer recommendUser = uid;
        /*System.out.println(userID.get(recommendUser));*/
        //计算用户之间的相似度【余弦相似性】
        int recommendUserId = userID.get(recommendUser);
        for (int j = 0;j < sparseMatrix.length; j++) {
            if(j != recommendUserId){
                System.out.println(idUser.get(recommendUserId)+"--"+idUser.get(j)+"相似度:"+sparseMatrix[recommendUserId][j]/Math.sqrt(userItemLength.get(idUser.get(recommendUserId))*userItemLength.get(idUser.get(j))));
            }
        }
        //计算指定用户recommendUser的物品推荐度
        for(Integer item: items){//遍历每一件物品
            Set<Integer> users = itemUserCollection.get(item);//得到购买当前物品的所有用户集合
            if(!users.contains(recommendUser)){//如果被推荐用户没有购买当前物品，则进行推荐度计算
                double itemRecommendDegree = 0.0;
                for(Integer user: users){
                    itemRecommendDegree += sparseMatrix[userID.get(recommendUser)][userID.get(user)]/Math.sqrt(userItemLength.get(recommendUser)*userItemLength.get(user));//推荐度计算
                }
//                recommendvalue.put(item,itemRecommendDegree);
                System.out.println("The item "+item+" for "+recommendUser +"'s recommended degree:"+itemRecommendDegree);
                 ipost.add(userService.recommendIpost2(item));
            }
        }
            return ipost;
    }
//    public void cleanIpost(){
//        ipost.clear();
//    }

    @RequestMapping("rec")
    public void rec()throws Exception{
//        System.out.println(ipost);
//
//        System.out.println(userService.findAllIpost());
    }
}
