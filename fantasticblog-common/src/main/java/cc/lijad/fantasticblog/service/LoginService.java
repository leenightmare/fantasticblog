package cc.lijad.fantasticblog.service;

import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.entity.User;

/**
 * @author ljd
 * @create 2023/2/17 20:02
 */
public interface LoginService {
    R login(User user);

    R logout();
}
