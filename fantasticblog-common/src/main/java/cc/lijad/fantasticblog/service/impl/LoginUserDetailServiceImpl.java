package cc.lijad.fantasticblog.service.impl;

import cc.lijad.fantasticblog.domain.entity.User;
import cc.lijad.fantasticblog.domain.model.LoginUser;
import cc.lijad.fantasticblog.service.MenuService;
import cc.lijad.fantasticblog.service.RoleService;
import cc.lijad.fantasticblog.service.UserService;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author ljd
 * @create 2023/2/17 20:20
 */
@Service
public class LoginUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User userServiceOne = userService.getOne(wrapper);
        if (ObjectUtil.isNull(userServiceOne)) {
            throw new RuntimeException("用户名或密码错误");
        }
        //查询权限列表
        Set<String> permissions = menuService.getPermissionsByUserId(userServiceOne.getId());
        //获取角色列表
        List<String> roles = roleService.getRoleByUserId(userServiceOne.getId());
        LoginUser loginUser = new LoginUser();
        loginUser.setUser(userServiceOne);
        loginUser.setPermissions(permissions);
        loginUser.setRoles(roles);
        return loginUser;
    }
}
