package cc.lijad.fantasticblog.constant;

/**
 * @author ljd
 * @create 2023/2/16 20:38
 */
public class RedisKey {
    /**
     * 默认缓存时间
     */
    public static final Long DEFAULT_EXPIRE = 60L;


    public static final String Article_VISIT_COUNT = "blog:article:visitCount:";

    /**
     * 登录用户信息
     */
    public static final String LOGIN_USER_INFO = "system:login:user:";

    /**
     * 用户对应的角色
     */
    public static final String SYSTEM_LOGIN_USER_ROLE = "system:login:user:role:";

    /**
     * 获取用户分页
     */
    public static final String SYSTEM_USER_PAGE = "system:user:page:";


    /**
     * 用户对应的角色对象
     */
    public static final String SYSTEM_LOGIN_USER_ROLEOBJ = "system:login:user:roleobj:";

    /**
     * 用户对应的权限列表
     */
    public static final String SYSTEM_LOGIN_USER_PERMISSION = "system:login:user:permission:";

    /**
     * 用户对应的权限菜单。用于路由
     */
    public static final String SYSTEM_LOGIN_USER_MENU_ROUTE = "system:login:user:menu:route:";


    /**
     * 用户对应的权限菜单。由于展示修改
     */
    public static final String SYSTEM_LOGIN_USER_MENU_LIST = "system:login:user:menu:list:";


    /**
     * 用户对应的权限菜单。由于展示修改
     */
    public static final String SYSTEM_ROLE_PAGE = "system:role:page:";

    /**
     * 获取角色列表。分配角色表单用,排除admin
     */
    public static final String SYSTEM_ROLE_LIST_NOT_ADMIN = "system:role:list:notadmin";


    /**
     * 所有标签列表
     */
    public static final String FRONTEND_TAG_LIST = "blog:tag:list";
    /**
     * 某篇文章
     */
    public static final String ARTICLE_DETAIL = "blog:article:detail:";

    /**
     * 文章分页数据
     */
    public static final String BLOG_ARTICLE_PAGE = "blog:article:page:";

    /**
     * 归档
     */
    public static final String TIMELINE_ARTICLE_LIST = "blog:article:timeline:";

    /**
     * 对应的文章数量
     */
    public static final String ARTICLE_CATEGORY_COUNT = "blog:article:category:count:";

    /**
     * 博客配置
     */
    public static final String BLOG_CONFIG_INFO = "blog:config:info:";

    /**
     * 聊天列表list
     */
    public static final String BLOG_CHAT_LIST = "blog:chat:list";

    /**
     * 聊天列表list
     */
    public static final String BLOG_CHAT_PAGE = "blog:chat:page:";

    /**
     * 某篇文章的评论
     */
    public static final String BLOG_ARTICLE_COMMENT = "blog:article:comment:";
    /**
     * 后台获取编辑
     */
    public static final String BLOG_ARTICLE_COMMENT_EDIT = "blog:article:comment:edit:";

    /**
     * 留言
     */
    public static final String BLOG_ARTICLE_COMMENT_MESSAGE = "blog:article:comment:message:";

    /**
     * 前台通知列表
     */
    public static final String FRONTEND_BLOG_NOTICE_LIST = "blog:notice:list";

    /**
     * 后台通知分页
     */
    public static final String BACKEND_BLOG_NOTICE_PAGE = "blog:notice:page:";


    /**
     * 分类
     */
    public static final String BACKEND_CATEGORY_LIST = "blog:category:list";

    /**
     * 分类分页
     */
    public static final String BACKEND_CATEGORY_PAGE = "blog:category:page:";

}
