import { reqCategoryList } from "@/api/category"

const actions = {
    async getCategoryList({ commit }) {
        let result = await reqCategoryList();
        if (result.code == 200) {
            commit("GETCATEGORYLIST", result.data)
        } else {
            Promise.reject(new Error("获取分类列表失败"));
        }
    }
}
const mutations = {
    GETCATEGORYLIST(state, data) {
        state.categoryList = data;
    }
}
const state = {
    categoryList: []
}

export default {
    namespaced: true,
    actions,
    mutations,
    state
}