import { reqNoticeList } from "@/api/notice"

const actions = {
    async getNoticeList({ commit }) {
        let result = await reqNoticeList();
        if (result.code == 200) {
            commit("GETNOTICELIST", result.data)
        }
    }
}
const mutations = {
    GETNOTICELIST(state, data) {
        state.noticeList = data;
    }
}
const state = {
    noticeList: []
}

export default {
    namespaced: true,
    actions,
    mutations,
    state
}