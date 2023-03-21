import Vue from "vue"
import VueRouter from "vue-router"

Vue.use(VueRouter);

const constRoutes = [

    {
        path: "/article/:articleId",
        component: () => import("@/views/Detail"),
        meta: { showHeader: true, showFooter: true }
    },

    {
        path: "/message",
        component: () => import("@/views/Message"),
        meta: { showHeader: true, showFooter: false }
    },
    {
        path: "/classfiy/:type/:key",
        name: "Classfiy",
        component: () => import("@/views/Classfiy"),
        meta: { showHeader: true, showFooter: true }
    },
    {
        path: "/about",
        component: () => import("@/views/About"),
        meta: { showHeader: true, showFooter: true }
    },
    {
        path: "/timeline",
        component: () => import("@/views/TimeLine"),
        meta: { showHeader: true, showFooter: true }
    },

    {
        path: "/layout",
        component: () => import("@/layout/index.vue"),
        meta: { showHeader: true, showFooter: true }
    },
    {
        path: "/search",
        name: "Search",
        component: () => import("@/views/Search"),
        meta: { showHeader: true, showFooter: true }
    },
    {
        path: "/",
        redirect: "/layout",
        meta: { showHeader: true, showFooter: true }
    }
]

// 防止连续点击多次路由报错
let routerPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
    return routerPush.call(this, location).catch(err => err)
}

export default new VueRouter({
    // mode: 'history', // 去掉url中的#
    scrollBehavior: () => ({ y: 0 }),
    routes: constRoutes
})



