package cc.lijad.fantasticblog.domain.vo;

import cc.lijad.fantasticblog.domain.entity.Menu;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ljd
 * @create 2023/2/24 10:11
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuVo extends Menu implements Serializable {

    /**
     * 子组件
     */
    private List<MenuVo> children;

    private static final long serialVersionUID = 1L;
}
