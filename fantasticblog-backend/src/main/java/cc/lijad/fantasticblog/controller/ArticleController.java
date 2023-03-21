package cc.lijad.fantasticblog.controller;

import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.dto.ArticleDTO;
import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.domain.entity.Article;
import cc.lijad.fantasticblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author ljd
 * @create 2023/2/20 13:58
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 分页查询
     *
     * @param pageDTO
     * @param keyword
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermi('blog:article:list')")
    public R list(@Valid PageDTO pageDTO, String keyword) {
        Article article = new Article();
        article.setTitle(keyword);
        return articleService.getArticleList(pageDTO, article);
    }

    /**
     * 获取单条用于修改
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPermi('blog:article:list')")
    public R detail(@PathVariable Long id) {
        return articleService.getArticleForEdit(id);
    }


    /**
     * 添加
     *
     * @param articleDTO
     * @return
     */
    @PostMapping
    @PreAuthorize("@ss.hasPermi('blog:article:add')")
    public R save(@RequestBody ArticleDTO articleDTO) {
        return articleService.saveArticle(articleDTO);
    }

    /**
     * 修改
     *
     * @param articleDTO
     * @return
     */
    @PutMapping
    @PreAuthorize("@ss.hasPermi('blog:article:edit')")
    public R update(@RequestBody ArticleDTO articleDTO) {
        return articleService.updateArticle(articleDTO);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPermi('blog:article:remove')")
    public R delete(@PathVariable Long[] ids) {
        return articleService.deleteArticle(ids);
    }


    /**
     * 查询
     *
     * @param title
     * @return
     */
    @GetMapping("/query")
    @PreAuthorize("@ss.hasPermi('blog:article:query')")
    public R queryTitle(String title) {
        return articleService.queryTitle(title);
    }
}
