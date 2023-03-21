import { reqTagList } from "@/api/tag"

const actions = {
    async getTagList({ commit }) {
        let result = await reqTagList();
        if (result.code == 200) {
            commit("GETTAGLIST", result.data)
        } else {
            Promise.reject(new Error("获取分类列表失败"));
        }
    }
}
const mutations = {
    GETTAGLIST(state, data) {
        state.tagList = data;
    }
}
const state = {
    tagList: []
}

export default {
    namespaced: true,
    actions,
    mutations,
    state
}