package cc.lijad.fantasticblog.handler;

import cc.lijad.fantasticblog.domain.R;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * @author ljd
 * @create 2023/2/17 20:58
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("text/json;charset=UTF-8");
        R r = R.error(401, "无效的会话，或者会话已过期，请重新登录");
        String str = JSON.toJSONString(r);
        PrintWriter writer = response.getWriter();
        writer.write(str);
    }
}
