package cc.lijad.fantasticblog.service;

import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface UserService extends IService<User> {


    R pageUser(PageDTO pageDTO);

    R detailForEdit(Long id);

    R deleteUser(Long[] ids);

    R saveUser(User user);

    R authRole(Long id);

    boolean updateUserById(User user);
}
