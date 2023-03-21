package cc.lijad.fantasticblog.service.impl;

import cc.lijad.fantasticblog.annotation.FlushCache;
import cc.lijad.fantasticblog.constant.UserConstants;
import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.domain.dto.RoleDTO;
import cc.lijad.fantasticblog.domain.entity.Menu;
import cc.lijad.fantasticblog.domain.vo.PageVo;
import cc.lijad.fantasticblog.domain.vo.RoleMenuVo;
import cc.lijad.fantasticblog.mapper.MenuMapper;
import cc.lijad.fantasticblog.utils.BeanCopyUtil;
import cc.lijad.fantasticblog.utils.PageVoUtil;
import cc.lijad.fantasticblog.utils.RedisCache;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cc.lijad.fantasticblog.domain.entity.Role;
import cc.lijad.fantasticblog.service.RoleService;
import cc.lijad.fantasticblog.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static cc.lijad.fantasticblog.constant.RedisKey.*;

/**
 *
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 根据用户ID查询角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<String> getRoleByUserId(Long userId) {
        List<String> cacheObject = redisCache.getCacheObject(SYSTEM_LOGIN_USER_ROLE + userId);
        if (cacheObject == null) {
            cacheObject = roleMapper.selectRolesByUserId(userId);
            redisCache.setCacheObject(SYSTEM_LOGIN_USER_ROLE + userId, cacheObject);
        }
        return cacheObject;
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public List<Role> getRoleObjByUserId(Long userId) {
        List<Role> roles = roleMapper.selectRoleObjByUserId(userId);
        return roles;
    }

    /**
     * 根据用户ID保存角色信息，（分配角色）
     *
     * @param roleIds
     * @param userId
     * @return
     */
    @Transactional
    @Override
    @FlushCache(dimKey = "user")
    public R saveRoleForUser(Long[] roleIds, Long userId) {
        //先删除
        baseMapper.deleteUserRoleByUserId(userId);

        //再保存
        baseMapper.insertUserRoleBatch(roleIds, userId);
        return R.success();
    }


    /**
     * 角色管理首页
     *
     * @param pageDTO
     * @return
     */
    @Override
    public R selectRolePage(PageDTO pageDTO) {
        String cacheKey = SYSTEM_ROLE_PAGE + pageDTO.getPageNum() + ":" + pageDTO.getPageSize();
        PageVo cacheObject = redisCache.getCacheObject(cacheKey);
        if (cacheObject == null) {
            Page<Role> pageInfo = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
            page(pageInfo);
            PageVo pageVo = PageVoUtil.toPageVo(pageInfo, pageInfo.getRecords());
            cacheObject = pageVo;
            redisCache.setCacheObject(cacheKey, cacheObject);
        }
        return R.success(cacheObject);
    }

    /**
     * @param roleId
     * @return
     */
    @Override
    public RoleMenuVo selectRoleMenuById(Long roleId) {
        Role role = getById(roleId);
        List<Menu> menus = menuMapper.selectMenuListByRoleId(roleId);
        List<Long> menuIds = menus.stream().map(Menu::getMenuId).collect(Collectors.toList());
        RoleMenuVo menuVo = BeanCopyUtil.copyObj(role, RoleMenuVo.class);
        menuVo.setMenuIds(menuIds);
        return menuVo;
    }

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleNameUnique(Role role) {
        Long roleId = ObjectUtil.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        Role info = roleMapper.checkRoleNameUnique(role.getRoleName());
        if (ObjectUtil.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleKeyUnique(Role role) {
        Long roleId = ObjectUtil.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        Role info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
        if (ObjectUtil.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }


    /**
     * 新增保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    @FlushCache(dimKey = "role")
    public boolean insertRole(Role role) {
        //保存角色信息
        save(role);
        //保存角色菜单全新
        RoleDTO roleDTO = (RoleDTO) role;
        List<Long> menuIds = roleDTO.getMenuIds();
        Integer count = roleMapper.saveRoleMenu(menuIds, role.getRoleId());
        return count > 0;
    }


    /**
     * 更新Role
     *
     * @param role
     * @return
     */
    @Override
    @Transactional
    @FlushCache(dimKey = "role")
    public boolean updateRole(Role role) {
        //更新信息
        updateById(role);
        //删除角色权限信息
        roleMapper.deleteRoleMenuByRoleId(role.getRoleId());
        //添加角色权限信息
        Integer count = roleMapper.saveRoleMenu(((RoleDTO) role).getMenuIds(), role.getRoleId());

        return count > 0;
    }

    /**
     * 获取角色列表。分配角色表单用
     *
     * @return
     */
    @Override
    public List<Role> getRoleList() {
        List<Role> cacheObject = redisCache.getCacheObject(SYSTEM_ROLE_LIST_NOT_ADMIN);
        if (cacheObject == null) {
            cacheObject = list();
            redisCache.setCacheObject(SYSTEM_ROLE_LIST_NOT_ADMIN, cacheObject, DEFAULT_EXPIRE, TimeUnit.MINUTES);
        }
        return cacheObject;
    }
}




