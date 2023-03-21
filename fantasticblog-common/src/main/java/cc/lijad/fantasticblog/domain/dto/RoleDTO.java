package cc.lijad.fantasticblog.domain.dto;

import cc.lijad.fantasticblog.domain.entity.Role;
import lombok.Data;

import java.util.List;

/**
 * @author ljd
 * @create 2023/3/3 18:18
 */
@Data
public class RoleDTO extends Role {
    private List<Long> menuIds;
}
