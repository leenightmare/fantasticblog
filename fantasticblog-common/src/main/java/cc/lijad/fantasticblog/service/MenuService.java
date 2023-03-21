package cc.lijad.fantasticblog.service;

import cc.lijad.fantasticblog.domain.entity.Menu;
import cc.lijad.fantasticblog.domain.vo.MenuVo;
import cc.lijad.fantasticblog.domain.vo.RouterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 *
 */
public interface MenuService extends IService<Menu> {

    Set<String> getPermissionsByUserId(Long userId);

    List<MenuVo> selectMenuTreeByUserId(Long userId);

    List<RouterVo> buildMenus(List<MenuVo> menuVos);

    List<MenuVo> selectMenuList(Long userId);

    Menu selectMenuById(Long menuId);

    String checkMenuNameUnique(Menu menu);

    boolean insertMenu(Menu menu);

    boolean updateMenu(Menu menu);

    boolean hasChildByMenuId(Long menuId);

    boolean checkMenuExistRole(Long menuId);

    boolean deleteMenuById(Long menuId);
}
