package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.file.service.FileStorageService;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.utils.common.UserIdThreadLocalUtil;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.service.WmMaterialService;
import javafx.scene.paint.Material;
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

    /**
     * 图片上传
     * @param multipartFile
     * @return
     */
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

    /**
     * 素材列表查询
     * @param wmMaterialDto
     * @return
     */
    @Override
    public ResponseResult pageList(WmMaterialDto wmMaterialDto) {
        //检查参数
        wmMaterialDto.checkParam();
        //确认分页数
        IPage<WmMaterial> page = new Page<>(wmMaterialDto.getPage(),wmMaterialDto.getSize());

        LambdaQueryWrapper<WmMaterial> queryWrapper = new LambdaQueryWrapper<>();
        //查询是否收藏的(即页面上点击收藏和普通素材时的显示)
        queryWrapper.eq(wmMaterialDto.getIsCollection() != null,WmMaterial::getIsCollection,wmMaterialDto.getIsCollection());
        //查询当前用户的素材
        queryWrapper.eq(WmMaterial::getUserId,UserIdThreadLocalUtil.getUserLocal());
        queryWrapper.orderByDesc(WmMaterial::getCreatedTime);
        //方法内部是return this.getBaseMapper().selectPage(page, queryWrapper);
        this.page(page,queryWrapper);

        ResponseResult result = new PageResponseResult(wmMaterialDto.getPage(), wmMaterialDto.getSize(),(int)page.getTotal());

        result.setData(page.getRecords());

        return result;
    }
}
