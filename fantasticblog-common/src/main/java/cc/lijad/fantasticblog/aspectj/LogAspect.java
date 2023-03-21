package cc.lijad.fantasticblog.aspectj;

import cc.lijad.fantasticblog.annotation.BlogLog;
import cc.lijad.fantasticblog.utils.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ljd
 * @create 2023/2/16 15:09
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("@annotation(cc.lijad.fantasticblog.annotation.BlogLog)")
    public void pt() {
    }

    @Around("pt()")
    public Object blogLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        try {
            handlerBefore(joinPoint);
            result = joinPoint.proceed();
            handlerAfter(result);
        } finally {
            logger.info("=====Request End====");
        }

        return result;
    }

    private void handlerBefore(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = ServletUtil.getRequest();

        logger.info("=====Request Start====");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        BlogLog blogLog = signature.getMethod().getAnnotation(BlogLog.class);
        logger.info("Http URL : {}", request.getRequestURL());
        logger.info("Http Method : {}", request.getMethod());
        logger.info("Annotation Message : {}", blogLog.description());
        logger.info("Class Method : {} , {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        logger.info("Request IP : {}", request.getRemoteHost());
        logger.info("Request Param : {}", joinPoint.getArgs());


    }

    private void handlerAfter(Object result) {
        logger.info("Return result : {}", result);
    }


}
