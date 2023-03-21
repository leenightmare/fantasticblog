package cc.lijad.fantasticblog.controller.system;

import cc.lijad.fantasticblog.constant.UserConstants;
import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.entity.Menu;
import cc.lijad.fantasticblog.domain.vo.MenuVo;
import cc.lijad.fantasticblog.service.MenuService;
import cc.lijad.fantasticblog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ljd
 * @create 2023/3/2 15:24
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController {


    @Autowired
    private MenuService menuService;

    /**
     * 获取菜单列表
     */
    @PreAuthorize("@ss.hasPermi('system:menu:list')")
    @GetMapping("/list")
    public R list() {
        List<MenuVo> menus = menuService.selectMenuList(SecurityUtils.getUserId());
        return R.success(menus);
    }

    /**
     * 根据菜单编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:menu:query')")
    @GetMapping(value = "/{menuId}")
    public R getInfo(@PathVariable Long menuId) {
        Menu menu = menuService.selectMenuById(menuId);
        return R.success(menu);
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public R treeselect() {
        List<MenuVo> menus = menuService.selectMenuList(SecurityUtils.getUserId());
        return R.success(menus);
    }

    /**
     * 新增菜单
     */
    @PreAuthorize("@ss.hasPermi('system:menu:add')")
    @PostMapping
    public R add(@RequestBody Menu menu) {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return R.error("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }//TODO 判断外链是否为合法http格式
        menu.setCreateBy(SecurityUtils.getUsername());
        return menuService.insertMenu(menu) ? R.success() : R.error();
    }


    /**
     * 修改菜单
     */
    @PreAuthorize("@ss.hasPermi('system:menu:edit')")
    @PutMapping
    public R edit(@RequestBody Menu menu) {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return R.error("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }//TODO 判断外链是否为合法http格式
        else if (menu.getMenuId().equals(menu.getParentId())) {
            return R.error("修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        menu.setUpdateBy(SecurityUtils.getUsername());
        return menuService.updateMenu(menu) ? R.success() : R.error();
    }


    /**
     * 删除菜单
     */
    @PreAuthorize("@ss.hasPermi('system:menu:remove')")
    @DeleteMapping("/{menuId}")
    public R remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            return R.error("存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(menuId)) {
            return R.error("菜单已分配,不允许删除");
        }
        return menuService.deleteMenuById(menuId) ? R.success() : R.error();
    }
}
