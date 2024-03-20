import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.heima.article.ArticleApplication;
import com.heima.article.mapper.ApArticleContentMapper;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.article.service.ApArticleService;
import com.heima.file.service.FileStorageService;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.article.pojos.ApArticleContent;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = ArticleApplication.class)
@RunWith(SpringRunner.class)
public class ApArticleTest {
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private Configuration configuration;
    @Autowired
    private ApArticleContentMapper apArticleContentMapper;
    @Autowired
    private ApArticleMapper apArticleMapper;

    @Test
    public void test() throws IOException, TemplateException {
        //先查询文章具体内容
        ApArticleContent apArticleContent = apArticleContentMapper.selectOne(Wrappers.
                <ApArticleContent>lambdaQuery().eq(ApArticleContent::getArticleId,
                1302862387124125698L));
        if (apArticleContent == null){
            return;
        }

        //将文章内容做成freemarker文件
        Template template = configuration.getTemplate("article.ftl");
        Object detail = JSONArray.parseArray(apArticleContent.getContent());
        Map<String,Object> map = new HashMap<>();
        map.put("content",detail);
        template.process(map,new FileWriter("D://detail3.html"));

        //存入分布式系统
        String path = fileStorageService.uploadHtmlFile("", "detail3.html", new FileInputStream("D://detail3.html"));

        ApArticle apArticle = new ApArticle();
        apArticle.setId(1302862387124125698L);
        apArticle.setStaticUrl(path);
        apArticleMapper.updateById(apArticle);

    }
}
