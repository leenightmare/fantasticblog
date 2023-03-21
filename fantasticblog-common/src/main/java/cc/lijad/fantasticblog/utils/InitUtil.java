package cc.lijad.fantasticblog.utils;

import cc.lijad.fantasticblog.constant.RedisKey;
import cc.lijad.fantasticblog.domain.entity.Article;
import cc.lijad.fantasticblog.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ljd
 * @create 2023/3/19 17:33
 */
@Component
public class InitUtil {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleMapper articleMapper;

    public void cacheArticleVisitCount() {
        //从数据库中查询，并放到redis中
        List<Article> articleList = articleMapper.selectVisitCount();
        //TODO 一次性查询全部，是否可以优化？
        Map<String, Integer> visitCount = articleList.stream().collect(Collectors.toMap(i -> i.getId().toString(),
                c -> c.getVisitCount()));
        if (visitCount != null) {
            redisCache.setCacheMap(RedisKey.Article_VISIT_COUNT, visitCount);
        }
    }

}
