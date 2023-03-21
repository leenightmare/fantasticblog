package cc.lijad.fantasticblog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author ljd
 * @create 2023/2/23 21:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoAdminVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private UserInfoVo userInfo;
    private List<String> roles;
    private Set<String> permissions;
}
