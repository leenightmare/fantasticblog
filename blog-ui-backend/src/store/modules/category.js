import { reqCategoryList } from '@/api/category'

const state = {
    categoryList: []
}

const mutations = {
    GETCATEGORYLIST(state, data) {
        state.categoryList = data;

    }
}

const actions = {
    getCategoryList({ commit }, params) {
        return new Promise((resolve, reject) => {
            reqCategoryList(params).then((response) => {
                if (response.code == 200) {
                    commit("GETCATEGORYLIST", response.data)
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

