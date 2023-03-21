package cc.lijad.fantasticblog.controller;

import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.entity.BlogConfig;
import cc.lijad.fantasticblog.service.BlogConfigService;
import cc.lijad.fantasticblog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cc.lijad.fantasticblog.constant.RedisKey.BLOG_CONFIG_INFO;

/**
 * @author ljd
 * @create 2023/3/18 22:20
 */
@RequestMapping("/blog/config")
@RestController
public class BlogConfigController {
    @Autowired
    private BlogConfigService blogConfigService;

    @GetMapping("/info")
    public R getInfo() {
        BlogConfig blogConfig = blogConfigService.getInfo();
        return R.success(blogConfig);
    }
}
