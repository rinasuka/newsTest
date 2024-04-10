package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.utils.common.UserIdThreadLocalUtil;
import com.heima.wemedia.mapper.WmNewsMapper;
import com.heima.wemedia.service.WmNewsService;
import org.springframework.stereotype.Service;

@Service
public class WmNewsServiceImpl extends ServiceImpl<WmNewsMapper,WmNews> implements WmNewsService {
    /**
     * 文章列表查询
     * @return
     */
    @Override
    public ResponseResult listNews(WmNewsPageReqDto wmNewsPageReqDto) {
        wmNewsPageReqDto.checkParam();
        IPage<WmNews> page = new Page<>(wmNewsPageReqDto.getPage(),wmNewsPageReqDto.getSize());
        //各类查询条件
        LambdaQueryWrapper<WmNews> queryWrapper = new LambdaQueryWrapper();
        //当前用户的文章管理
        queryWrapper.eq(WmNews::getUserId, UserIdThreadLocalUtil.getUserLocal());
        //频道id
        queryWrapper.eq(wmNewsPageReqDto.getChannelId() != null, WmNews::getChannelId, wmNewsPageReqDto.getChannelId());
        //发布状态
        queryWrapper.eq(wmNewsPageReqDto.getStatus() != null, WmNews::getStatus, wmNewsPageReqDto.getStatus());
        //关键字查询
        queryWrapper.like(wmNewsPageReqDto.getKeyword() != null,WmNews::getTitle,wmNewsPageReqDto.getKeyword());
        //发布日期
        if (wmNewsPageReqDto.getBeginPubDate() != null || wmNewsPageReqDto.getEndPubDate() != null) {
            queryWrapper.between(WmNews::getPublishTime,wmNewsPageReqDto.getBeginPubDate(),wmNewsPageReqDto.getEndPubDate());
        }
        queryWrapper.orderByDesc(WmNews::getCreatedTime);
        this.page(page,queryWrapper);
        ResponseResult result = new PageResponseResult(wmNewsPageReqDto.getPage(),wmNewsPageReqDto.getSize(),(int)page.getTotal());
        result.setData(page.getRecords());
        return result;
    }
}
