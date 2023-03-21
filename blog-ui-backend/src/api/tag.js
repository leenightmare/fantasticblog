import request from '@/utils/request'
export const reqTagList = () => {
    return request({
        url: "/tag/list",
        method: "GET",
    })
}

export const reqTagPage = (params) => {
    return request({
        url: "/tag/page",
        method: "GET",
        params
    })
}


// 添加
export const reqSaveTag = (data) => {
    return request({
        url: "/tag",
        method: "POST",
        data
    })
}
// 修改
export const reqUpdateTag = (data) => {
    return request({
        url: "/tag",
        method: "PUT",
        data
    })
}
// 删除
export const reqDeleteTag = (ids) => {
    return request({
        url: `/tag/${ids}`,
        method: "DELETE",

    })
}