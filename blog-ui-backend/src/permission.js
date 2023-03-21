import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login'] // no redirect whitelist

router.beforeEach((to, from, next) => {
  // start progress bar
  NProgress.start()

  // set page title
  document.title = getPageTitle(to.meta.title)

  // determine whether the user has logged in
  const hasToken = getToken()

  //如果有token，说明已经登录
  if (hasToken) {
    if (to.path === '/login') {
      // 如果访问登录页面，跳到首页
      next({ path: '/' })
      NProgress.done()
    } else {
      //是否有信息（判断token是否已过期）
      const hasGetUserInfo = store.getters.nickname
      if (hasGetUserInfo) {
        next()
      } else {
        //等待获取用户信息
        store.dispatch('user/getInfo').then(res => {
          //获取动态路由
          store.dispatch("permission/GenerateRoutes").then(accessRoutes => {
            //添加路由
            router.addRoutes(accessRoutes);

            next({ ...to, replace: true })
          })
        }).catch(error => {
          // remove token and go to login page to re-login
          store.dispatch('user/resetToken')
          Message.error(error || 'Has Error')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
          next();
        })
      }
    }
  } else {
    /* has no token*/
    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
