import axios from "axios";

import nprogress from "nprogress";

import "nprogress/nprogress.css";

import { MessageBox, Message } from 'element-ui'

const requests = axios.create({
    baseURL: "/api",
    timeout: 5000
})



// 请求拦截器，请求之前做一些
requests.interceptors.request.use((config) => {

    // 开始进度条
    nprogress.start();
    // config.header['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';

    return config;
})

requests.interceptors.response.use((resp) => {
    //进度条结束
    nprogress.done();
    // if (resp.code !== 20000 && resp.code !== 200) {
    //     Message({
    //         message: resp.message || 'Error',
    //         type: 'error',
    //         duration: 1000
    //     })
    // }
    // console.log(resp);

    return resp.data;
}, (err) => {
    //进度条结束
    nprogress.done();
    console.log('err' + err) // for debug
    if (resp.code == 404) {
        Message({
            message: "资源找不到404",
            type: 'error',
            duration: 1000
        })
    }

    return Promise.reject(new Error("请求失败"));
})

export default requests;

