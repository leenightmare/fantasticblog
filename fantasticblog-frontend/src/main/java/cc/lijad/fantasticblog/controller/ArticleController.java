package cc.lijad.fantasticblog.controller;

import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.domain.entity.Article;
import cc.lijad.fantasticblog.service.ArticleService;
import cc.lijad.fantasticblog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author ljd
 * @create 2023/2/14 13:12
 */
@RequestMapping("/article")
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @Autowired
    private RedisCache redisCache;

    //    @BlogLog(description = "获取文章列表")
    @GetMapping("/list")
    public R list(@Valid PageDTO pageDTO, @RequestBody(required = false) Article article) {
        return articleService.getArticleList(pageDTO, article);
    }

    //    @BlogLog(description = "获取文章详情")
    @GetMapping("/detail/{id}")
    public R detail(@PathVariable Long id) {
        R r = articleService.getArticleDetail(id);
        // 浏览量+1
        articleService.incrVisitCount(id);
        return r;
    }


    /**
     * 归档查询
     *
     * @return
     */
//    @BlogLog(description = "归档查询")
    @GetMapping("/timeline")
    public R timeline() {
        return articleService.getTimeLineArticle();
    }

    /**
     * 分类对应的文章数量
     */
//    @BlogLog(description = "分类对应的文章数量")
    @GetMapping("/category/list")
    public R getCategoryArticleCount() {
        return articleService.getCategoryArticleList();
    }

}
