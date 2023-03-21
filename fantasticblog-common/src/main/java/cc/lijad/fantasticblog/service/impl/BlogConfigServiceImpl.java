package cc.lijad.fantasticblog.service.impl;

import cc.lijad.fantasticblog.annotation.FlushCache;
import cc.lijad.fantasticblog.utils.RedisCache;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cc.lijad.fantasticblog.domain.entity.BlogConfig;
import cc.lijad.fantasticblog.service.BlogConfigService;
import cc.lijad.fantasticblog.mapper.BlogConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static cc.lijad.fantasticblog.constant.RedisKey.BLOG_CONFIG_INFO;

/**
 *
 */
@Service
public class BlogConfigServiceImpl extends ServiceImpl<BlogConfigMapper, BlogConfig>
        implements BlogConfigService {

    @Autowired
    private RedisCache redisCache;

    /**
     * 获取
     *
     * @return
     */
    @Override
    public BlogConfig getInfo() {
        BlogConfig cacheObject = redisCache.getCacheObject(BLOG_CONFIG_INFO + 1);
        if (cacheObject == null) {
            cacheObject = getById(1L);
            redisCache.setCacheObject(BLOG_CONFIG_INFO + 1, cacheObject);
        }
        return cacheObject;
    }

    /**
     * 更新
     *
     * @param blogConfig
     * @return
     */
    @Override
    @FlushCache(dimKey = "config")
    public boolean updateBlogConfig(BlogConfig blogConfig) {
        boolean flag = updateById(blogConfig);
        return flag;
    }
}




