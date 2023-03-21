import { getRouters } from "@/api/menu"
import router, { constantRoutes, dynamicRoutes } from '@/router'
import Layout from '@/layout/index'
import ParentView from '@/components/ParentView'

const state = {
    routes: [],
    addRoutes: [],
    defaultRoutes: [],
    topbarRouters: [],
    sidebarRouters: []
}

const mutations = {
    SET_ROUTES: (state, routes) => {
        state.addRoutes = routes
        state.routes = constantRoutes.concat(routes)
    },
    SET_DEFAULT_ROUTES: (state, routes) => {
        state.defaultRoutes = constantRoutes.concat(routes)
    },
    SET_TOPBAR_ROUTES: (state, routes) => {
        state.topbarRouters = routes
    },
    SET_SIDEBAR_ROUTERS: (state, routes) => {
        state.sidebarRouters = routes
    },
}

const actions = {
    GenerateRoutes({ commit }) {
        return new Promise(resolve => {
            getRouters().then(res => {
                const sdata = JSON.parse(JSON.stringify(res.data));
                const rdata = JSON.parse(JSON.stringify(res.data));
                const sidebarRoutes = filterAsyncRouter(sdata);
                const rewriteRoutes = filterAsyncRouter(rdata, false, true)
                router.addRoutes(rewriteRoutes);
                rewriteRoutes.push({ path: '*', redirect: '/404', hidden: true })
                commit('SET_ROUTES', rewriteRoutes)
                commit('SET_SIDEBAR_ROUTERS', constantRoutes.concat(sidebarRoutes))
                commit('SET_DEFAULT_ROUTES', sidebarRoutes)
                commit('SET_TOPBAR_ROUTES', sidebarRoutes)
                resolve(rewriteRoutes);
            })
        })
    }
}

// 遍历后台传来的路由字符串，转换为组件对象
function filterAsyncRouter(asyncRouterMap, lastRouter = false, type = false) {
    return asyncRouterMap.filter(route => {
        if (type && route.children) {
            route.children = filterChildren(route.children)
        }
        if (route.component) {
            // Layout  组件特殊处理
            if (route.component === 'Layout') {
                route.component = Layout
            } else if (route.component === 'ParentView') {
                route.component = ParentView
            } else {
                route.component = loadView(route.component)
            }
        }
        if (route.children != null && route.children && route.children.length) {
            route.children = filterAsyncRouter(route.children, route, type)
        } else {
            delete route['children']
            delete route['redirect']
        }
        return true
    })
}

function filterChildren(childrenMap, lastRouter = false) {
    var children = []
    childrenMap.forEach((el, index) => {
        if (el.children && el.children.length) {
            if (el.component === 'ParentView' && !lastRouter) {
                el.children.forEach(c => {
                    c.path = el.path + '/' + c.path
                    if (c.children && c.children.length) {
                        children = children.concat(filterChildren(c.children, c))
                        return
                    }
                    children.push(c)
                })
                return
            }
        }
        if (lastRouter) {
            el.path = lastRouter.path + '/' + el.path
        }
        children = children.concat(el)
    })
    return children
}

export const loadView = (view) => {
    if (process.env.NODE_ENV === 'development') {
        return (resolve) => require([`@/views/${view}`], resolve)
    } else {
        // 使用 import 实现生产环境的路由懒加载
        return () => import(`@/views/${view}`)
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}

