import request from '@/utils/request'

export const getRouters = () => {
    return request({
        url: "/getRouters",
        method: "GET"
    })
}

export const reqListMenu = () => {
    return request({
        url: "/system/menu/list",
        method: "GET"
    })
}

export const reqMenuInfo = (menuId) => {
    return request({
        url: `/system/menu/${menuId}`,
        method: "GET"
    })
}
export const reqTreeselect = () => {
    return request({
        url: "/system/menu/treeselect",
        method: "GET"
    })
}


export const reqSaveMenu = (data) => {
    return request({
        url: `/system/menu`,
        method: "POST",
        data
    })
}

export const reqUpdateMenu = (data) => {
    return request({
        url: `/system/menu`,
        method: "PUT",
        data
    })
}

export const reqDeleteMenu = (menuId) => {
    return request({
        url: `/system/menu/${menuId}`,
        method: "DELETE",
    })
}