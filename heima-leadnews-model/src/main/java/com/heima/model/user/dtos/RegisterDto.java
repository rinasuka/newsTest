package com.heima.model.user.dtos;

import lombok.Data;

@Data
public class RegisterDto {
    /**
     * 手机号
     */
    private String phone;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String name;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 性别 男:1 女:0
     */
    private Short sex;

}
