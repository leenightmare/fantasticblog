package cc.lijad.fantasticblog.controller.system;

import cc.lijad.fantasticblog.constant.UserConstants;
import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.domain.dto.RoleDTO;
import cc.lijad.fantasticblog.domain.entity.Role;
import cc.lijad.fantasticblog.domain.model.LoginUser;
import cc.lijad.fantasticblog.domain.vo.RoleMenuVo;
import cc.lijad.fantasticblog.service.MenuService;
import cc.lijad.fantasticblog.service.RoleService;
import cc.lijad.fantasticblog.service.UserRoleService;
import cc.lijad.fantasticblog.utils.SecurityUtils;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ljd
 * @create 2023/3/1 17:17
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {


    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private MenuService menuService;

    /**
     * 获取角色列表。分配角色表单用
     *
     * @return
     */
    @GetMapping("/list")
    public R list() {
        List<Role> roles = roleService.getRoleList();
        List<Role> roleList = roles.stream().filter(role -> !role.isAdmin()).collect(Collectors.toList());
        return R.success(roleList);
    }

    /**
     * 根据用户id分配角色
     *
     * @param roleIds
     * @param userId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:role:edit')")
    @PostMapping("/auth/{userId}")
    public R saveRoleForUser(@RequestBody Long[] roleIds, @PathVariable Long userId) {
        return roleService.saveRoleForUser(roleIds, userId);
    }


    /**
     * 分页获取角色
     *
     * @param pageDTO
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:role:list')")
    @GetMapping("/page")
    public R page(@Valid PageDTO pageDTO) {
        return roleService.selectRolePage(pageDTO);
    }


    /**
     * 根据角色编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:role:query')")
    @GetMapping(value = "/{roleId}")
    public R getInfo(@PathVariable Long roleId) {
//        roleService.checkRoleDataScope(roleId);
        RoleMenuVo roleMenuVo = roleService.selectRoleMenuById(roleId);
        return R.success(roleMenuVo);
    }


    /**
     * 新增角色
     */
    @PreAuthorize("@ss.hasPermi('system:role:add')")
    @PostMapping
    public R add(@RequestBody RoleDTO role) {
        if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            return R.error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            return R.error("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setCreateBy(SecurityUtils.getUsername());
        return roleService.insertRole(role) ? R.success() : R.error();

    }

    /**
     * 修改保存角色
     */
    @PreAuthorize("@ss.hasPermi('system:role:edit')")
    @PutMapping
    public R edit(@RequestBody RoleDTO role) {
//        roleService.checkRoleAllowed(role);
//        roleService.checkRoleDataScope(role.getRoleId());
        if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            return R.error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            return R.error("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setUpdateBy(SecurityUtils.getUsername());

        if (roleService.updateRole(role)) {

            // 更新缓存用户权限
            LoginUser loginUser = SecurityUtils.getLoginUser();
            if (ObjectUtil.isNotNull(loginUser.getUser()) && !SecurityUtils.isAdmin(loginUser.getUser().getId())) {
                Set<String> permissions = menuService.getPermissionsByUserId(loginUser.getUser().getId());
                loginUser.setPermissions(permissions);
            }
            return R.success();
        }
        return R.error("修改角色'" + role.getRoleName() + "'失败，请联系管理员");
    }


}
