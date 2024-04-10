package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.wemedia.service.WmChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/channel")
public class WmChannelController {
    @Autowired
    private WmChannelService wmChannelService;
    /**
     * 频道列表查询
     */
    @GetMapping("/channels")
    public ResponseResult list(){
        return  wmChannelService.listChannel();
    }
}