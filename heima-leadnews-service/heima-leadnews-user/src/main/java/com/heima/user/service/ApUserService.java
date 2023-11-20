package com.heima.user.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.LoginDto;
import org.springframework.stereotype.Service;

@Service
public interface ApUserService {
    /**
     * 登录
     * 返回码 2:密码错误 1002:用户不存在 200:操作成功
     * @param loginDto
     * @return
     */
    public ResponseResult login(LoginDto loginDto);
}
