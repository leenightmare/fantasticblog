//引入Vue核心库
import Vue from 'vue'
//引入Vuex
import Vuex from 'vuex'
//应用Vuex插件
Vue.use(Vuex)

import article from './article'
import comment from './comment'
import tag from './tag'
import category from './category'
import user from "./user"

import blogconfig from './blogconfig'
import notice from './notice'
import chat from './chat'


export default new Vuex.Store({
    modules: {
        article,
        comment,
        tag,
        category,
        user,
        blogconfig,
        notice,
        chat
    }
})