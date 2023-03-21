import { reqAdminUser } from "@/api/user"

const actions = {
    async getAdminInfo({ commit }) {
        let result = await reqAdminUser();
        if (result.code == 200) {
            commit("GETADMININFO", result.data);
        } else {
            Promise.reject(new Error("获取用户信息失败"));
        }
    }
}
const mutations = {
    GETADMININFO(state, data) {
        state.userInfo = data;
    }
}
const state = {
    userInfo: {}
}

export default {
    namespaced: true,
    actions,
    mutations,
    state
}