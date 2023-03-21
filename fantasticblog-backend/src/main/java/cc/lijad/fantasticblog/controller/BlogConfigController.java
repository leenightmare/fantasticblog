package cc.lijad.fantasticblog.controller;

import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.entity.BlogConfig;
import cc.lijad.fantasticblog.service.BlogConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author ljd
 * @create 2023/3/18 19:36
 */
@RestController
@RequestMapping("/blog/config")
public class BlogConfigController {

    @Autowired
    private BlogConfigService blogConfigService;

    /**
     * 获取博客配置类信息
     *
     * @return
     */
    @GetMapping
    @PreAuthorize("@ss.hasPermi('blog:config:list')")
    public R getInfo() {
        BlogConfig blogConfig = blogConfigService.getInfo();
        return R.success(blogConfig);
    }

    @PutMapping
    @PreAuthorize("@ss.hasPermi('blog:config:edit')")
    public R update(@RequestBody @Valid BlogConfig blogConfig) {
        blogConfigService.updateBlogConfig(blogConfig);
//        blogConfigService.updateById(blogConfig);
        return R.success();
    }
}
