package cc.lijad.fantasticblog.controller.system;

import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.domain.entity.User;
import cc.lijad.fantasticblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author ljd
 * @create 2023/3/1 9:12
 */
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/page")
    public R page(@Valid PageDTO pageDTO) {
        return userService.pageUser(pageDTO);
    }

    @GetMapping("/{id}")
    public R detailForEdit(@PathVariable Long id) {
        return userService.detailForEdit(id);
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @PostMapping
    @PreAuthorize("@ss.hasPermi('system:user:add')")
    public R save(@RequestBody User user) {
//        userService.save(user);
        return userService.saveUser(user);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPermi('system:user:remove')")
    public R delete(@PathVariable Long[] ids) {
        return userService.deleteUser(ids);
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    @PutMapping
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    public R update(@RequestBody User user) {
        userService.updateUserById(user);
        return R.success();
    }


    /**
     * 根据用户ID获取授权角色
     *
     * @param id
     * @return
     */
    @GetMapping("/auth/{id}")
    public R authRole(@PathVariable Long id) {
//        List<String> role = roleService.getRoleByUserId(id);
        return userService.authRole(id);
    }


}
