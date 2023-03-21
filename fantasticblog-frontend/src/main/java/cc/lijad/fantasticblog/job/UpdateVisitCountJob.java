package cc.lijad.fantasticblog.job;

import cc.lijad.fantasticblog.constant.RedisKey;
import cc.lijad.fantasticblog.domain.entity.Article;
import cc.lijad.fantasticblog.service.ArticleService;
import cc.lijad.fantasticblog.utils.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ljd
 * @create 2023/2/16 21:20
 */
@Component
public class UpdateVisitCountJob {

    private static final Logger logger = LoggerFactory.getLogger(UpdateVisitCountJob.class);
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    /**
     * 每隔30分钟执行一次
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void updateCount() {
        Map<String, Integer> VisitCountMap = redisCache.getCacheMap(RedisKey.Article_VISIT_COUNT);
        List<Article> collect = VisitCountMap.entrySet().stream().map(e -> {
            Article article = new Article();
            article.setId(Long.valueOf(e.getKey()));
            article.setVisitCount(e.getValue());
            System.out.println(article);
            return article;
        }).collect(Collectors.toList());
        try {
            articleService.updateBatchById(collect);
            logger.info("文章访问数量，成功同步到数据库:{}", VisitCountMap);
        } catch (Exception e) {
            logger.error("文章访问数量失败，出现未知异常：{}", e.getCause());
        }
    }
}
