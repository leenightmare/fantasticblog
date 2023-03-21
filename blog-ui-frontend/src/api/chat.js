import requests from "./request";

// 获取标签列表
export const reqChatList = () => {
    return requests({
        url: "/chat/list",
        method: "GET",
    })
}