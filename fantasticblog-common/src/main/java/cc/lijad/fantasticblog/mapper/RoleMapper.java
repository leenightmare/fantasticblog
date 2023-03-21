package cc.lijad.fantasticblog.mapper;

import cc.lijad.fantasticblog.domain.entity.Menu;
import cc.lijad.fantasticblog.domain.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity cc.lijad.fantasticblog.domain.entity.Role
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRolesByUserId(Long userId);

    List<Role> selectRoleObjByUserId(Long userId);

    void insertUserRoleBatch(@Param("roleIds") Long[] roleIds, @Param("userId")Long userId);

    void deleteUserRoleByUserId(Long userId);

    Integer checkMenuExistRole(Long menuId);

    Role checkRoleNameUnique(String roleName);

    Role checkRoleKeyUnique(String roleKey);

    Integer saveRoleMenu(@Param("menuIds") List<Long> menuIds, @Param("roleId") Long roleId);

    Integer deleteRoleMenuByRoleId(Long roleId);
}




