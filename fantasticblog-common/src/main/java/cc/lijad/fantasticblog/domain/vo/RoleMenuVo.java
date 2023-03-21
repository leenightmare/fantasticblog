package cc.lijad.fantasticblog.domain.vo;

import cc.lijad.fantasticblog.domain.entity.Role;
import lombok.Data;

import java.util.List;

/**
 * @author ljd
 * @create 2023/3/2 23:17
 */
@Data
public class RoleMenuVo extends Role {
    private List<Long> menuIds;
}
