package com.heima.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.dtos.LoginDto;
import com.heima.model.user.pojos.ApUser;
import com.heima.user.mapper.ApUserMapper;
import com.heima.user.service.ApUserService;
import com.heima.utils.common.AppJwtUtil;
import com.heima.utils.common.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Service
@Slf4j
public class ApUserServiceImpl implements ApUserService {
    @Autowired
    private ApUserMapper apUserMapper;
    @Override
    public ResponseResult login(LoginDto loginDto) {
        log.info("登录请求:{}",loginDto);
        HashMap<String, Object> resultMap = new HashMap<>();
        //判断是否游客登录
        if (!StringUtils.isEmpty(loginDto.getPhone()) && !StringUtils.isEmpty(loginDto.getPassword())){
            //都有
            LambdaQueryWrapper<ApUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ApUser::getPhone,loginDto.getPhone());
            //获取用户
            ApUser user = apUserMapper.selectOne(queryWrapper);
            if (user == null){
                return ResponseResult.errorResult(AppHttpCodeEnum.USER_OR_PASSWORD_ERROR);
            }
            //输入密码
            String password = loginDto.getPassword();
            String salt = user.getSalt();
            String MD5Password = DigestUtils.md5DigestAsHex((password +salt).getBytes());
            log.info("登录后加密数据:{}",MD5Password);
            if (!MD5Password.equals(user.getPassword())){
               return ResponseResult.errorResult(AppHttpCodeEnum.USER_OR_PASSWORD_ERROR);
            }
            //封装数据(返回的数据要求了user这个对象返回,然后为了保密把盐和密码消除掉,设置为空)
            user.setPassword("");
            user.setSalt("");
            resultMap.put("token",AppJwtUtil.getToken(user.getId().longValue()));
            resultMap.put("user",user);
        }else{
            resultMap.put("token",AppJwtUtil.getToken(0L));
        }
        return ResponseResult.okResult(resultMap);
    }
}
