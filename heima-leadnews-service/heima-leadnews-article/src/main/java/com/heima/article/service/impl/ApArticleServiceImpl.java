package com.heima.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.article.service.ApArticleService;
import com.heima.common.constants.ApArticleConstants;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ApArticleServiceImpl implements ApArticleService {

    @Autowired
    private ApArticleMapper apArticleMapper;

    /**
     * 加载文章列表
     * @param articleHomeDto
     * @return
     */
    @Override
    public ResponseResult loadArticle(ArticleHomeDto articleHomeDto,Integer TypeNumber) {
        Integer size = articleHomeDto.getSize();
        if (size <= 0 || size > 10 || size == null){
            size = ApArticleConstants.DEFAULT_SIZE;
        }

        List<ApArticle> apArticles = apArticleMapper.loadArticle(articleHomeDto,TypeNumber);

        return ResponseResult.okResult(apArticles);
    }
}
