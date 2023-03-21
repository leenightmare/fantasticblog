import { reqMiniArticle, reqTagList } from "@/api/article";
const actions = {
    async getMiniArticle(context, data) {
        let result = await reqMiniArticle(data);
        if (result.code == 200) {
            context.commit("GETMINIARTICLE", result.data)
        }
    },
    async getTagList({ commit }) {
        let result = await reqTagList();
        if (result.code == 200) {
            commit("GETTAGLIST", result.data)
        } else {
            Promise.reject(new Error("获取标签列表失败"))
        }
    }

}


const mutations = {
    GETMINIARTICLE(state, data) {
        state.pageMiniArticle = data;
    },
    GETTAGLIST(state, data) {
        state.tagList = data;
    }
}


const state = {
    pageMiniArticle: {},
    tagList: [],
}
const getters = {
}

export default {
    namespaced: true,
    state,
    mutations,
    actions,
    getters
}