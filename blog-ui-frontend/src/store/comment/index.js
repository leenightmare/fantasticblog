// import { reqMiniArticle } from "@/api/article";
import { reqSaveComment } from "@/api/comment";
const actions = {
    async toSaveComment(context, data) {
        let result = await reqSaveComment(data);
        console.log(result);
        if (result.code == 200) {
            context.commit("TOSAVECOMMENT", result.data)
        }
    }
}


const mutations = {
    TOSAVECOMMENT(state, data) {
        
    }
}


const state = {
    
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