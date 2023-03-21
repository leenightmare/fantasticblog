import request from '@/utils/request'

export const reqCategoryList = () => {
    return request({
        url: "/category/list",
        method: "GET",
    })
}

export const reqCategoryPage = (params) => {
    return request({
        url: "/category/page",
        method: "GET",
        params
    })
}

// 添加
export const reqSaveCategory = (data) => {
    return request({
        url: "/category",
        method: "POST",
        data
    })
}

// 修改
export const reqUpdateCategory = (data) => {
    return request({
        url: "/category",
        method: "PUT",
        data
    })
}

// 删除
export const reqDeleteCategory = (ids) => {
    return request({
        url: `/category/${ids}`,
        method: "DELETE",
    })
}