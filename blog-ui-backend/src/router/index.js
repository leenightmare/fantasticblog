import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/index',
    children: [{
      path: 'index',
      name: 'Index',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },
  // {
  //   path: "/add",
  //   component: Layout,
  //   redirect: 'noRedirect',
  //   children: [{
  //     path: '/addOrUpdateArticle',
  //     name: 'AddOrUpdateArticle',
  //     component: () => import('@/views/blog/article/addOrUpdateArticle'),
  //     meta: { title: '写文章', icon: 'el-icon-edit' },
  //   },
  //   ],

  // },
  // 404 page must be placed at the end !!!
  // { path: '*', redirect: '/404', hidden: true }
]

// 动态路由，基于用户权限动态去加载
export const dynamicRoutes = [
  {
    path: '/blog',
    component: Layout,
    redirect: '/blog/article',
    name: 'Blog',
    meta: { title: '博客管理', icon: 'el-icon-notebook-1' },
    children: [
      {
        path: 'article',
        name: 'Article',
        component: () => import('@/views/blog/article/index'),
        meta: { title: '文章管理', icon: 'el-icon-edit' },
      },

      {
        path: 'comment',
        name: 'Comment',
        component: () => import('@/views/blog/comment/index'),
        meta: { title: '评论管理', icon: 'el-icon-chat-line-round' }
      },
      {
        path: 'tag',
        name: 'Tag',
        component: () => import('@/views/blog/tag/index'),
        meta: { title: '标签管理', icon: 'el-icon-collection-tag' }
      },
      {
        path: 'category',
        name: 'Category',
        component: () => import('@/views/blog/category/index'),
        meta: { title: '分类管理', icon: 'el-icon-document-copy' }
      },
    ]
  },

]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
