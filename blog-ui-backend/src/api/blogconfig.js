import request from '@/utils/request'
export const reqBlogConfig = () => {
    return request({
        url: "/blog/config",
        method: "GET",
    })
}
export const reqUpdateBlogConfig = (data) => {
    return request({
        url: "/blog/config",
        method: "PUT",
        data
    })
}

