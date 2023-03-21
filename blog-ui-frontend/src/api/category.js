import requests from "./request";

// 获取分类列表
export const reqCategoryList = () => { 
    return requests({
        url: "/category/list",
        method: "GET",
    })

}

