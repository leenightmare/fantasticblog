import { reqChatList } from "@/api/chat"

const actions = {
    async getChatList({ commit }) {
        let result = await reqChatList();
        if (result.code == 200) {
            commit("GETCHATLIST", result.data)
        }
    }
}
const mutations = {
    GETCHATLIST(state, data) {
        state.chatList = data;
    }
}
const state = {
    chatList: []
}

export default {
    namespaced: true,
    actions,
    mutations,
    state
}