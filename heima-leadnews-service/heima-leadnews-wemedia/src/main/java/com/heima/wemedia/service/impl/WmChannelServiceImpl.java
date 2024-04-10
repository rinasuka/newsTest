package com.heima.wemedia.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.wemedia.mapper.WmChannelMapper;
import com.heima.wemedia.service.WmChannelService;
import org.springframework.stereotype.Service;

@Service
public class WmChannelServiceImpl extends ServiceImpl<WmChannelMapper,WmChannel> implements WmChannelService {
    /**
     * 频道列表查询
     * @return
     */
    @Override
    public ResponseResult listChannel() {
        LambdaQueryWrapper<WmChannel> queryWrapper = new LambdaQueryWrapper();
        return ResponseResult.okResult(this.list(queryWrapper));
    }
}
