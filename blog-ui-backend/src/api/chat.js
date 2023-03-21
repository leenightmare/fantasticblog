import request from '@/utils/request'
export const reqChatList = (params) => {
    return request({
        url: "/system/chat/page",
        method: "GET",
        params
    })
}



export const reqChatById = (id) => {
    return request({
        url: `/system/chat/${id}`,
        method: "GET",
    })
}

export const reqSaveChat = (data) => {
    return request({
        url: "/system/chat",
        method: "POST",
        data
    })
}

export const reqUpdateChat = (data) => {
    return request({
        url: "/system/chat",
        method: "PUT",
        data
    })
}
export const reqDeleteChat = (ids) => {
    return request({
        url: `/system/chat/${ids}`,
        method: "DELETE",
    })
}
