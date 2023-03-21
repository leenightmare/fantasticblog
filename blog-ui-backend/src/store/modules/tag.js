import { reqTagList } from '@/api/tag'

const state = {
    tagList: []
}

const mutations = {
    GETTAGLIST(state, data) {
        state.tagList = data;

    }
}

const actions = {
    getTagList({ commit }) {
        return new Promise((resolve, reject) => {
            reqTagList().then((response) => {
                if (response.code == 200) {
                    commit("GETTAGLIST", response.data)
                    resolve(response.data);
                }
            }).catch(error => {
                reject(error)
            })

        })
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}

