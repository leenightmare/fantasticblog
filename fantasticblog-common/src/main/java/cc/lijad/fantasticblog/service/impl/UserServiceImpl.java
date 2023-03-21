package cc.lijad.fantasticblog.service.impl;

import cc.lijad.fantasticblog.annotation.FlushCache;
import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.domain.entity.Role;
import cc.lijad.fantasticblog.domain.entity.User;
import cc.lijad.fantasticblog.domain.vo.PageVo;
import cc.lijad.fantasticblog.domain.vo.UserInfoVo;
import cc.lijad.fantasticblog.domain.vo.UserListVo;
import cc.lijad.fantasticblog.mapper.UserMapper;
import cc.lijad.fantasticblog.service.RoleService;
import cc.lijad.fantasticblog.service.UserService;
import cc.lijad.fantasticblog.utils.BeanCopyUtil;
import cc.lijad.fantasticblog.utils.PageVoUtil;
import cc.lijad.fantasticblog.utils.RedisCache;
import cc.lijad.fantasticblog.utils.SecurityUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static cc.lijad.fantasticblog.constant.RedisKey.*;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 获取用户分页
     *
     * @param pageDTO
     * @return
     */
    @Override
    public R pageUser(PageDTO pageDTO) {
        String cacheKey = SYSTEM_USER_PAGE + pageDTO.getPageNum() + ":" + pageDTO.getPageSize();
        PageVo cacheObject = redisCache.getCacheObject(cacheKey);
        if (cacheObject == null) {
            Page<User> pageInfo
                    = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
            page(pageInfo);
            List<User> userList = pageInfo.getRecords();
            List<UserListVo> listVos = userList.stream().map((u) -> {
                UserListVo userListVo = BeanCopyUtil.copyObj(u, UserListVo.class);
                userListVo.setRoles(roleService.getRoleObjByUserId(u.getId()));
                return userListVo;
            }).collect(Collectors.toList());

            PageVo pageVo = PageVoUtil.toPageVo(pageInfo, listVos);
            cacheObject = pageVo;
            redisCache.setCacheObject(cacheKey, cacheObject, DEFAULT_EXPIRE, TimeUnit.MINUTES);
        }
        return R.success(cacheObject);
    }

    /**
     * 获取用户详情
     *
     * @param id
     * @return
     */
    @Override
    public R detailForEdit(Long id) {
        User user = getById(id);
        UserInfoVo userInfoVo = BeanCopyUtil.copyObj(user, UserInfoVo.class);
        return R.success(userInfoVo);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Override
    @FlushCache(dimKey = "user")
    public R deleteUser(Long[] ids) {
        removeBatchByIds(Arrays.asList(ids));
        return R.success();
    }

    /**
     * 添加用户（注册）
     *
     * @param user
     * @return
     */
    @Override
    @Transactional
    @FlushCache(dimKey = "user")
    public R saveUser(User user) {
        String password = SecurityUtils.encryptPassword(user.getPassword());
        user.setCreateTime(new Date());
        user.setPassword(password);
        save(user);
        return R.success();
    }


    /**
     * 根据用户ID获取授权角色
     *
     * @param id
     * @return
     */
    @Override
    public R authRole(Long id) {
        HashMap<String, Object> cacheObject = redisCache.getCacheObject(SYSTEM_LOGIN_USER_ROLEOBJ + id);
        if (cacheObject == null) {
            User user = getById(id);
            UserInfoVo userInfoVo = BeanCopyUtil.copyObj(user, UserInfoVo.class);
            List<Role> roles = roleService.getRoleObjByUserId(id);
            HashMap<String, Object> map = new HashMap<>();
            map.put("user", userInfoVo);
//        map.put("roles", userInfoVo.isAdmin() ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
            map.put("roles", roles);
            cacheObject = map;
            redisCache.setCacheObject(SYSTEM_LOGIN_USER_ROLEOBJ + id, cacheObject, DEFAULT_EXPIRE, TimeUnit.MINUTES);
        }
        return R.success().put("data", cacheObject);

    }

    /**
     * 修改
     *
     * @param user
     * @return
     */
    @Override
    @FlushCache(dimKey = "user:page")
    public boolean updateUserById(User user) {
        return updateById(user);
    }
}




