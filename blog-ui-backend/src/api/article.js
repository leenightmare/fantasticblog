import request from '@/utils/request'

export const reqSaveArticle = (data) => {
    return request({
        url: "/article",
        method: "POST",
        data
    })
}

export const reqGetArticleList = (params) => {
    return request({
        url: "/article/list",
        method: "GET",
        params
    })
}

export const reqGetArticleDetail = (id) => {
    return request({
        url: `/article/${id}`,
        method: "GET",
    })
}

export const reqUpdateArticle = (data) => {
    return request({
        url: `/article`,
        method: "PUT",
        data
    })
}

export const reqdeleteArticle = (ids) => {
    return request({
        url: `/article/${ids}`,
        method: "DELETE",
    })
}


export const reqArticleTitle = (title) => {
    return request({
        url: `/article/query`,
        method: "GET",
        params: {
            title
        }
    })
}