package com.heima.article.controller.v1;

import com.heima.article.service.ApArticleService;
import com.heima.common.constants.ApArticleConstants;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/article")
public class ApArticleController {

    @Autowired
    private ApArticleService apArticleService;

    /**
     * 加载文章列表
     * @param homeDto
     * @return
     */
    @PostMapping("/load")
    public ResponseResult load(@RequestBody ArticleHomeDto homeDto){
        return apArticleService.loadArticle(homeDto, ApArticleConstants.TYPE_NEW);
    }

    /**
     * 加载最新文章列表
     * @param homeDto
     * @return
     */
    @PostMapping("/loadnew")
    public ResponseResult loadnew(@RequestBody ArticleHomeDto homeDto){
        return apArticleService.loadArticle(homeDto, ApArticleConstants.TYPE_NEW);
    }

    /**
     * 加载更多文章列表
     * @param homeDto
     * @return
     */
    @PostMapping("/loadmore")
    public ResponseResult loadmore(@RequestBody ArticleHomeDto homeDto){
        return apArticleService.loadArticle(homeDto, ApArticleConstants.TYPE_MORE);
    }
}
