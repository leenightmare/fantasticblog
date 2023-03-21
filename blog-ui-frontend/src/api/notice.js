import requests from "./request";

// 获取标签列表
export const reqNoticeList = () => {
    return requests({
        url: "/notice/list",
        method: "GET",
    })
}