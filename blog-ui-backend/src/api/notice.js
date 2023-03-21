import request from '@/utils/request'
export const reqNoticeList = (params) => {
    return request({
        url: "/system/notice/page",
        method: "GET",
        params
    })
}

export const reqNoticeById = (noticeId) => {
    return request({
        url: `/system/notice/${noticeId}`,
        method: "GET",
    })
}

export const reqSaveNotice = (data) => {
    return request({
        url: "/system/notice",
        method: "POST",
        data
    })
}

export const reqUpdateNotice = (data) => {
    return request({
        url: "/system/notice",
        method: "PUT",
        data
    })
}
export const reqDeleteNotice = (noticeIds) => {
    return request({
        url: `/system/notice/${noticeIds}`,
        method: "DELETE",
    })
}


