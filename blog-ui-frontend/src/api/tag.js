import requests from "./request";

// 获取标签列表
export const reqTagList = () => { 
    return requests({
        url: "/tag/list",
        method: "GET",
        
    })
}