package cc.lijad.fantasticblog.service.impl;

import cc.lijad.fantasticblog.annotation.FlushCache;
import cc.lijad.fantasticblog.constant.BlogSys;
import cc.lijad.fantasticblog.constant.Constants;
import cc.lijad.fantasticblog.constant.UserConstants;
import cc.lijad.fantasticblog.domain.entity.Menu;
import cc.lijad.fantasticblog.domain.vo.MenuVo;
import cc.lijad.fantasticblog.domain.vo.MetaVo;
import cc.lijad.fantasticblog.domain.vo.RouterVo;
import cc.lijad.fantasticblog.mapper.MenuMapper;
import cc.lijad.fantasticblog.mapper.RoleMapper;
import cc.lijad.fantasticblog.service.MenuService;
import cc.lijad.fantasticblog.utils.BeanCopyUtil;
import cc.lijad.fantasticblog.utils.RedisCache;
import cc.lijad.fantasticblog.utils.SecurityUtils;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static cc.lijad.fantasticblog.constant.RedisKey.*;

/**
 *
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
        implements MenuService {


    @Autowired
    private MenuMapper menuMapper;


    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 权限列表
     *
     * @param userId
     * @return
     */
    @Override
    public Set<String> getPermissionsByUserId(Long userId) {
        Set<String> cachePermissions = redisCache.getCacheObject(SYSTEM_LOGIN_USER_PERMISSION + userId);
        if (cachePermissions == null) {
            if (userId == 1L) {
                HashSet<String> perms = new HashSet<>();
                perms.add(BlogSys.ALL_PERMISSION);
                return perms;
            }
            cachePermissions = menuMapper.selectPermissionsByUserId(userId);
            redisCache.setCacheObject(SYSTEM_LOGIN_USER_PERMISSION + userId, cachePermissions);
        }
        return cachePermissions;
    }

    /**
     * 根据用户ID查询菜单。用于路由菜单
     *
     * @param userId 用户名称
     * @return 菜单列表
     */
    @Override
    public List<MenuVo> selectMenuTreeByUserId(Long userId) {
        List<MenuVo> cacheMenu = redisCache.getCacheObject(SYSTEM_LOGIN_USER_MENU_ROUTE + userId);
        if (cacheMenu == null) {
            List<Menu> menus = null;
            if (SecurityUtils.isAdmin(userId)) {
                //如果是超级管理员，直接返回所有
                LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
                wrapper.in(Menu::getMenuType, "M", "C")
                        .eq(Menu::getStatus, 0);
                wrapper.orderByAsc(Menu::getOrderNum);
                menus = baseMapper.selectList(wrapper);
            } else {
                //否则，根据当前用户的ID查询
                menus = baseMapper.selectRouterByUserId(userId);
            }
            List<MenuVo> menuVoList = BeanCopyUtil.copyToList(menus, MenuVo.class);
            //构建树形菜单
            List<MenuVo> menuTree = getChildPerms(menuVoList, 0L);
            cacheMenu = menuTree;
            redisCache.setCacheObject(SYSTEM_LOGIN_USER_MENU_ROUTE + userId, cacheMenu);
        }

        return cacheMenu;
    }


    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param menuVoList 分类表
     * @param parentId   传入的父节点ID
     * @return String
     */
    public List<MenuVo> getChildPerms(List<MenuVo> menuVoList, long parentId) {
        return menuVoList.stream()
                .filter(m -> m.getParentId().equals(parentId))
                .map(m -> {
                    m.setChildren(getChildList(m, menuVoList));
                    return m;
                }).collect(Collectors.toList());

    }

    /**
     * 得到子节点列表
     */
    public List<MenuVo> getChildList(MenuVo menuVo, List<MenuVo> menuVoList) {
        return menuVoList.stream()
                .filter(m -> menuVo.getMenuId().equals(m.getParentId()))
                .map(m -> {
                    m.setChildren(getChildList(m, menuVoList));
                    return m;
                })
                .collect(Collectors.toList());
    }

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    @Override
    public List<RouterVo> buildMenus(List<MenuVo> menus) {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for (MenuVo menu : menus) {
            RouterVo router = new RouterVo();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(getRouteName(menu)); //首字母大写
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setQuery(menu.getQuery());

            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache().toString()), menu.getPath()));
            List<MenuVo> cMenus = menu.getChildren();
            if (!cMenus.isEmpty() && cMenus.size() > 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            } else if (isMenuFrame(menu)) {
                router.setMeta(null);
                List<RouterVo> childrenList = new ArrayList<>();
                RouterVo children = new RouterVo();
                children.setPath(menu.getPath());

                children.setComponent(menu.getComponent());
                children.setName(StringUtils.capitalize(menu.getPath()));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache().toString()), menu.getPath()));
                children.setQuery(menu.getQuery());
                childrenList.add(children);
                router.setChildren(childrenList);
            } else if (menu.getParentId().intValue() == 0 && isInnerLink(menu)) {
                router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
                router.setPath("/");
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                String routerPath = innerLinkReplaceEach(menu.getPath());
                children.setPath(routerPath);
                children.setComponent(UserConstants.INNER_LINK);
                children.setName(StringUtils.capitalize(routerPath));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), menu.getPath()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 查询系统菜单列表。用于展示修改
     *
     * @param userId 登录用户ID
     * @return 菜单列表
     */
    @Override
    public List<MenuVo> selectMenuList(Long userId) {
        List<MenuVo> cacheMenus = redisCache.getCacheObject(SYSTEM_LOGIN_USER_MENU_LIST + userId);
        if (cacheMenus == null) {
            // 管理员显示所有菜单信息
            List<Menu> menus = null;
            if (SecurityUtils.isAdmin(userId)) {
                //如果是超级管理员，直接返回所有
                LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
                wrapper.orderByAsc(Menu::getOrderNum);
                menus = menuMapper.selectList(wrapper);
            } else {
                //否则，根据当前用户的ID查询
                menus = menuMapper.selectMenuList(userId);
            }

            List<MenuVo> menuVoList = BeanCopyUtil.copyToList(menus, MenuVo.class);
            //构建树形菜单
            List<MenuVo> menuTree = getChildPerms(menuVoList, 0L);

            cacheMenus = menuTree;
            redisCache.setCacheObject(SYSTEM_LOGIN_USER_MENU_LIST + userId, cacheMenus);
        }
        return cacheMenus;
    }

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    @Override
    public Menu selectMenuById(Long menuId) {
        Menu menu = getById(menuId);
        return menu;
    }


    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public String checkMenuNameUnique(Menu menu) {
        Long menuId = ObjectUtil.isNull(menu.getMenuId()) ? -1L : menu.getMenuId();
        Menu info = menuMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
        if (ObjectUtil.isNotNull(info) && info.getMenuId().longValue() != menuId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    @Transactional
    @FlushCache(dimKey = "menu")
    public boolean insertMenu(Menu menu) {
        return save(menu);
    }

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    @Transactional
    @FlushCache(dimKey = "menu")
    public boolean updateMenu(Menu menu) {
        return updateById(menu);
    }


    @Override
    public boolean hasChildByMenuId(Long menuId) {
        Integer count = menuMapper.hasChildByMenuId(menuId);
        return count > 0;
    }

    @Override
    public boolean checkMenuExistRole(Long menuId) {
        Integer count = roleMapper.checkMenuExistRole(menuId);
        return count > 0;
    }

    @Override
    @Transactional
    @FlushCache(dimKey = "menu")
    public boolean deleteMenuById(Long menuId) {
        return removeById(menuId);
    }

    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    public String getRouteName(Menu menu) {
        String routerName = StringUtils.capitalize(menu.getPath());
        // 非外链并且是一级目录（类型为目录）
        if (isMenuFrame(menu)) {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMenuFrame(Menu menu) {
        return menu.getParentId().intValue() == 0 && UserConstants.TYPE_MENU.equals(menu.getMenuType())
                && menu.getIsFrame().toString().equals(UserConstants.NO_FRAME);
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(Menu menu) {
        String routerPath = menu.getPath();
        // 内链打开外网方式
        if (menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            routerPath = innerLinkReplaceEach(routerPath);
        }
        // 非外链并且是一级目录（类型为目录）
        if (0 == menu.getParentId().intValue() && UserConstants.TYPE_DIR.equals(menu.getMenuType())
                && UserConstants.NO_FRAME.equals(menu.getIsFrame().toString())) {
            routerPath = "/" + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMenuFrame(menu)) {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(Menu menu) {
        String component = UserConstants.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
            component = menu.getComponent();
        } else if (StringUtils.isEmpty(menu.getComponent()) && menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            component = UserConstants.INNER_LINK;
        } else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)) {
            component = UserConstants.PARENT_VIEW;
        }
        return component;
    }

    /**
     * 内链域名特殊字符替换
     *
     * @return
     */
    public String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[]{Constants.HTTP, Constants.HTTPS, Constants.WWW, "."},
                new String[]{"", "", "", "/"});
    }

    /**
     * 是否为内链组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isInnerLink(Menu menu) {
        String reg = "^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$";

//        return menu.getIsFrame().equals(UserConstants.NO_FRAME) && StringUtils.ishttp(menu.getPath());
        return menu.getIsFrame().toString().equals(UserConstants.NO_FRAME) && (menu.getPath().matches(reg));
    }


    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(Menu menu) {
        return menu.getParentId().intValue() != 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType());
    }
}




