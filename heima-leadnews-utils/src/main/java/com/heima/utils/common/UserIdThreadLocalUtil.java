package com.heima.utils.common;

public class UserIdThreadLocalUtil {
    private final static ThreadLocal<Integer> USER_LOCAL = new ThreadLocal<>();
    //傻逼问题:为什么set没有return而get要return
    //答:因为set是赋值,赋值完就结束了,不需要返回任何东西,而get是取值,取值就需要取到一个具体的值,所以需要return一个具体的值
    public static void setUserLocal(Integer userId){
      USER_LOCAL.set(userId);
    }

    public static Integer getUserLocal(){
        return USER_LOCAL.get();
    }

    public static void remove(){
        USER_LOCAL.remove();
    }
}
