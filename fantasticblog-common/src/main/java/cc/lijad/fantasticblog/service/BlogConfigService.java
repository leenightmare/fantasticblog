package cc.lijad.fantasticblog.service;

import cc.lijad.fantasticblog.domain.entity.BlogConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface BlogConfigService extends IService<BlogConfig> {

    BlogConfig getInfo();

    boolean updateBlogConfig(BlogConfig blogConfig);
}
