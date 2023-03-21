package cc.lijad.fantasticblog.service.impl;

import cc.lijad.fantasticblog.constant.RedisKey;
import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.entity.User;
import cc.lijad.fantasticblog.domain.model.LoginUser;
import cc.lijad.fantasticblog.exception.ServiceException;
import cc.lijad.fantasticblog.service.LoginService;
import cc.lijad.fantasticblog.utils.JwtUtil;
import cc.lijad.fantasticblog.utils.RedisCache;
import cc.lijad.fantasticblog.utils.SecurityUtils;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author ljd
 * @create 2023/2/17 20:03
 */
@Service
public class LoginServiceImpl implements LoginService {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public R login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (ObjectUtil.isNull(authenticate)) {
            throw new ServiceException("用户名或密码错误");
        }
        //登录成功
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        loginUser.getUser().setPassword(null);
        String userID = loginUser.getUser().getId().toString();
        //生成token
        String token = JwtUtil.createJWT(userID);
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        redisCache.setCacheObject(RedisKey.LOGIN_USER_INFO + userID, loginUser);
        return R.success(map);
    }

    /**
     * 注销登录
     */
    @Override
    public R logout() {
        Long userId = SecurityUtils.getUserId();
        redisCache.deleteObject(RedisKey.LOGIN_USER_INFO + userId);
        return R.success();
    }
}
