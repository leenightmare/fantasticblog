import requests from "./request";

// 获取标签列表
export const reqBlogConfig = () => {
    return requests({
        url: "/blog/config/info",
        method: "GET",

    })
}