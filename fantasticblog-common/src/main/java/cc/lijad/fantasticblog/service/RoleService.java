package cc.lijad.fantasticblog.service;

import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.domain.entity.Role;
import cc.lijad.fantasticblog.domain.vo.RoleMenuVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface RoleService extends IService<Role> {

    List<String> getRoleByUserId(Long userId);

    List<Role> getRoleObjByUserId(Long userId);

    R saveRoleForUser(Long[] roleIds, Long userId);

    R selectRolePage(PageDTO pageDTO);

    RoleMenuVo selectRoleMenuById(Long roleId);

    String checkRoleNameUnique(Role role);

    String checkRoleKeyUnique(Role role);

    boolean insertRole(Role role);

    boolean updateRole(Role role);

    List<Role> getRoleList();
}
