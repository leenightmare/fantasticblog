import { reqBlogConfig } from "@/api/blogconfig"

const actions = {
    async getBlogConfig({ commit }) {
        let result = await reqBlogConfig();
        if (result.code == 200) {
            commit("GETGLOGCONFIG", result.data)
        }
    }
}
const mutations = {
    GETGLOGCONFIG(state, data) {
        state.blogConfig = data;
    }
}
const state = {
    blogConfig: {
        id: undefined,
        poem: undefined,
        indexBackground: undefined,
        girlUrl: undefined,
        aboutDescription: undefined,
        wechat: undefined,
        github: undefined,
        gitee: undefined,
        qq: undefined,
        email: undefined,
        pendant: undefined,
        username: undefined,
        userAvatar: undefined,
    }
}

export default {
    namespaced: true,
    actions,
    mutations,
    state
}