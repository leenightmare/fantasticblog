package cc.lijad.fantasticblog.mapper;

import cc.lijad.fantasticblog.domain.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @Entity cc.lijad.fantasticblog.domain.entity.Menu
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    Set<String> selectPermissionsByUserId(Long userId);

    List<Menu> selectRouterByUserId(Long userId);

    List<Menu> selectMenuList(Long userId);

    Menu checkMenuNameUnique(@Param("menuName") String menuName,@Param("parentId") Long parentId);

    Integer hasChildByMenuId(Long menuId);

    List<Menu> selectMenuListByRoleId(Long roleId);
}




