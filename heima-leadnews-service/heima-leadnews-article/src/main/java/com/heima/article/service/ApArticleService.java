package com.heima.article.service;

import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.stereotype.Service;

public interface ApArticleService {
    /**
     * 文章列表加载
     * @param articleHomeDto
     * @return
     */
    ResponseResult loadArticle(ArticleHomeDto articleHomeDto,Integer TypeNumber);
}
