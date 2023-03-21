import request from '@/utils/request'

export const reqCommentList = (params) => {
    return request({
        url: "/comment/list",
        method: "GET",
        params
    })
}

export const reqDeleteComment = (ids) => {
    return request({
        url: `/comment/${ids}`,
        method: "DELETE",
    })
}


export const reqCommentMessage = (params) => {
    return request({
        url: `/comment/message`,
        method: "GET",
        params
    })
}