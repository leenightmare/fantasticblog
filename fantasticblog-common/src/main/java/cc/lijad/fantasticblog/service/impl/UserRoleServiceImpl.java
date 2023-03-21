package cc.lijad.fantasticblog.service.impl;

import cc.lijad.fantasticblog.annotation.FlushCache;
import cc.lijad.fantasticblog.domain.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cc.lijad.fantasticblog.domain.entity.UserRole;
import cc.lijad.fantasticblog.service.UserRoleService;
import cc.lijad.fantasticblog.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
        implements UserRoleService {

    /**
     * 根据用户id分配角色
     *
     * @param roleIds
     * @param userId
     * @return
     */
    @Override
    @Transactional
    @FlushCache(dimKey = "user")
    public R saveRoleForUser(Long[] roleIds, Long userId) {
        List<UserRole> userRoles = Arrays.asList(roleIds)
                .stream()
                .map(r -> {
                    UserRole userRole = new UserRole();
                    userRole.setUserId(userId);
                    userRole.setRoleId(r);
                    return userRole;
                }).collect(Collectors.toList());
        //先删除
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userId);
        remove(wrapper);
        //再保存
        saveOrUpdateBatch(userRoles);
        return R.success();
    }
}




