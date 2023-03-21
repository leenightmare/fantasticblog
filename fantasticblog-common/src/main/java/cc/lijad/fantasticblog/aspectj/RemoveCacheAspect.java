package cc.lijad.fantasticblog.aspectj;

import cc.lijad.fantasticblog.annotation.BlogLog;
import cc.lijad.fantasticblog.annotation.FlushCache;
import cc.lijad.fantasticblog.utils.RedisCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author ljd
 * @create 2023/3/19 15:52
 */
@Component
@Aspect
public class RemoveCacheAspect {

    @Autowired
    private RedisCache redisCache;

    private static final Logger logger = LoggerFactory.getLogger(RemoveCacheAspect.class);

    @Pointcut("@annotation(cc.lijad.fantasticblog.annotation.FlushCache)")
    public void pt() {
    }

    /**
     * 模糊查询并删除
     *
     * @param joinPoint
     * @param res
     */
    @AfterReturning(pointcut = "pt()", returning = "res")
    public void doOther(JoinPoint joinPoint, Object res) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        FlushCache flushCache = signature.getMethod().getAnnotation(FlushCache.class);
        String key = flushCache.dimKey();
        Set keys = redisCache.getRedisTemplate().keys("*" + key + "*");
        long count = redisCache.deleteObject(keys);
        logger.info("删除缓存：{} 条,{}", count, keys);
    }
}
