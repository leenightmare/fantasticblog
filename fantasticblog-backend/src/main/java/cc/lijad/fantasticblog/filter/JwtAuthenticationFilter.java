package cc.lijad.fantasticblog.filter;

import cc.lijad.fantasticblog.constant.RedisKey;
import cc.lijad.fantasticblog.domain.model.LoginUser;
import cc.lijad.fantasticblog.exception.AuthenticationFailedException;
import cc.lijad.fantasticblog.utils.JwtUtil;
import cc.lijad.fantasticblog.utils.RedisCache;
import cn.hutool.core.util.ObjectUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author ljd
 * @create 2023/2/17 20:33
 */


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if (ObjectUtil.isNull(token)) {
            //放行交给SpringSecurity来处理
            filterChain.doFilter(request, response);
            return;
        }
        String userID;

        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            //相应前端重新登录
            resolver.resolveException(request, response, null, new AuthenticationFailedException());
            return;
        }
        userID = claims.getSubject();
        LoginUser loginUser = redisCache.getCacheObject(RedisKey.LOGIN_USER_INFO + userID);
        if (ObjectUtil.isNull(loginUser)) {
            //相应前端重新登录
            resolver.resolveException(request, response, null, new AuthenticationFailedException());
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
