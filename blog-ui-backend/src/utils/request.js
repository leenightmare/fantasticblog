import axios from 'axios'
import { MessageBox, Message, Notification } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 10000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent

    if (store.getters.token) {
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation
      config.headers['token'] = getToken()
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(

  response => {
    // 未设置状态码则默认成功状态
    const code = response.data.code || 200;
    // 获取错误信息
    const msg = response.data.msg || "请求失败";

    if (response.status == 200 && response.headers.server == "MinIO") {
      return response;
    }
    const res = response.data
    if (code == 401) {
      MessageBox.confirm('会话已过期,是否重新登录？', {
        confirmButtonText: '重新登录',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        store.dispatch('user/resetToken').then(() => {
          setTimeout(location.reload(), 200)
        })
      })
      return Promise.reject('无效的会话，或者会话已过期，请重新登录。')
    } else if (code == 403) {
      Message({
        message: msg,
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject("权限不足")
    } else if (code == 500) {
      Message({
        message: msg,
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(msg)
    } else if (code != 200) {
      // Message({
      //   message: msg,
      //   type: 'error',
      //   duration: 5 * 1000
      // })
      Notification.error({
        title: msg,
        message: res
      })

      return Promise.reject(msg)
    } else {
      return res
    }
  }, error => {
    console.log('err' + error)
    let { message } = error;
    if (message == "Network Error") {
      message = "后端接口连接异常";
    }
    else if (message.includes("timeout")) {
      message = "系统接口请求超时";
    }
    else if (message.includes("Request failed with status code")) {
      message = "系统接口" + message.substr(message.length - 3) + "异常";
    }
    Message({
      message: message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
