package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.file.service.FileStorageService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.utils.common.UserIdThreadLocalUtil;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.service.WmMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
public class WmMaterialServiceImpl extends ServiceImpl<WmMaterialMapper,WmMaterial> implements WmMaterialService {
    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public ResponseResult upload(MultipartFile multipartFile) {
        //上传图片  文件名大概是:xx.jpg
        String originalFilename = multipartFile.getOriginalFilename();
        //防止上传的文件重名
        String filename = UUID.randomUUID().toString(); //这种文件就是sasd-saas-sasa这样有横杠的
        //截图到".jpg"
        String postfix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //确定上传MinIO后的路径
        String path = "";
        try {
            path = fileStorageService.uploadImgFile("",filename + postfix, multipartFile.getInputStream());
        } catch (IOException e) {
            log.error("文件上传异常",e);
            return ResponseResult.errorResult(AppHttpCodeEnum.UPLOAD_ERROR);
        }
        //将url存入数据库
        WmMaterial wmMaterial = new WmMaterial();
        wmMaterial.setCreatedTime(new Date());
        wmMaterial.setUserId(UserIdThreadLocalUtil.getUserLocal());
        wmMaterial.setUrl(path);
        wmMaterial.setIsCollection((short)0);
        wmMaterial.setType((short)0);
        save(wmMaterial);

        return ResponseResult.okResult(wmMaterial);
    }
}
