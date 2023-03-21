import requests from "./request";

//查询管理员
export const reqAdminUser = () => {
    return requests({
        url: "/user/admin",
        method: "GET"
    })
}