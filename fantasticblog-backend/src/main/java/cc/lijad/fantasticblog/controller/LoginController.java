package cc.lijad.fantasticblog.controller;

import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.entity.User;
import cc.lijad.fantasticblog.domain.model.LoginUser;
import cc.lijad.fantasticblog.domain.vo.MenuVo;
import cc.lijad.fantasticblog.domain.vo.RouterVo;
import cc.lijad.fantasticblog.domain.vo.UserInfoAdminVo;
import cc.lijad.fantasticblog.domain.vo.UserInfoVo;
import cc.lijad.fantasticblog.service.LoginService;
import cc.lijad.fantasticblog.service.MenuService;
import cc.lijad.fantasticblog.service.RoleService;
import cc.lijad.fantasticblog.utils.BeanCopyUtil;
import cc.lijad.fantasticblog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author ljd
 * @create 2023/2/17 20:01
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    public R login(@RequestBody User user) {
        return loginService.login(user);
    }

    @GetMapping("/user/info")
    public R info() {
        //获取当前登录用户信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //封装用户信息
        UserInfoVo userInfoVo = BeanCopyUtil.copyObj(loginUser.getUser(), UserInfoVo.class);
        //1.查询角色信息
        List<String> roles = roleService.getRoleByUserId(SecurityUtils.getUserId());
        //2.查询权限列表
        Set<String> perms = menuService.getPermissionsByUserId(SecurityUtils.getUserId());

        UserInfoAdminVo userInfoAdminVo = new UserInfoAdminVo(userInfoVo, roles, perms);
        return R.success(userInfoAdminVo);
    }


    /**
     * 注销登录
     *
     * @return
     */
    @PostMapping("/user/logout")
    public R logout() {
        return loginService.logout();
    }

    /**
     * @return
     */
    @GetMapping("/getRouters")
    public R getRouters() {
        Long userId = SecurityUtils.getUserId();
        //构建树形菜单
        List<MenuVo> menuVos = menuService.selectMenuTreeByUserId(userId);
        //构建成前端所需的路由菜单
        List<RouterVo> routerVos = menuService.buildMenus(menuVos);
        return R.success(routerVos);
    }


}
