package cc.lijad.fantasticblog.service;

import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface UserRoleService extends IService<UserRole> {

    R saveRoleForUser(Long[] roleIds, Long userId);
}
