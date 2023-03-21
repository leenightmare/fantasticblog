package cc.lijad.fantasticblog.exception.handler;

import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.exception.AuthenticationFailedException;
import cc.lijad.fantasticblog.exception.ServiceException;
import cn.hutool.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * @author ljd
 * @create 2023/2/23 19:50
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ServiceException.class)
    public R serviceExceptionHandler(ServiceException e) {

        logger.error("服务错误:{}", e.getMessage());
//        log.error("服务错误:{}", e.getMessage());
        return R.error(e.getMessage());
    }

    /**
     * 参数非法
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R validExceptionHandler(MethodArgumentNotValidException e) {
        HashMap<String, String> map = new HashMap<>();
        e.getFieldErrors().forEach(item -> {
            map.put(item.getField(), item.getDefaultMessage());
        });
        logger.error("参数非法");
//        log.error("参数非法");
        return R.error(400, "参数非法").put("data", map);
    }

    /**
     * 参数非法
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public R validExceptionHandler(BindException e) {
        HashMap<String, String> map = new HashMap<>();
        e.getFieldErrors().forEach(item -> {
            map.put(item.getField(), item.getDefaultMessage());
        });
//        log.error("参数非法");
        logger.error("参数非法");
        return R.error(400, "参数非法").put("data", map);
    }

    /**
     * 授权失败
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public R handlerAccessDeniedException(AccessDeniedException e) {
        logger.error("权限不足");
//        log.error("权限不足");
        return R.error(403, "权限不足");
    }

    /**
     * 认证失败
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AuthenticationFailedException.class)
    public R handlerAuthenticationFailedException(AuthenticationFailedException e) {
        logger.error("无效的会话，或者会话已过期，请重新登录");
//        log.error("无效的会话，或者会话已过期，请重新登录");
        return R.error(401, "无效的会话，或者会话已过期，请重新登录");
    }

    /**
     * 登录失败
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BadCredentialsException.class)
    public R handlerBadCredentialsException(BadCredentialsException e) {
        logger.error("登录失败：{}", e.getMessage());
        return R.error("用户名或密码错误");
    }


    @ExceptionHandler(Throwable.class)
    public R commonExceptionHandler(Throwable throwable) {
        logger.error("未知错误:{}", throwable.getMessage());
//        log.error("未知错误:{}", throwable.getMessage());
        return R.error(throwable.getMessage());
    }
}
