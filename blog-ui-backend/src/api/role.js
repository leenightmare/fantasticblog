import request from '@/utils/request'

export const getRolesList = () => {
    return request({
        url: "/system/role/list",
        method: "GET"
    })
}

/**
 * 分配角色
 * @param {*} roleIds 
 * @param {*} userId 
 * @returns 
 */
export const getSaveRoleForUser = (roleIds, userId) => {
    return request({
        url: `/system/role/auth/${userId}`,
        method: "POST",
        data: [...roleIds]
    })
}

/**
 * 
 * @param {*} params 
 * @returns 
 */
export const reqRolePage = (params) => {
    return request({
        url: `/system/role/page`,
        method: "GET",
        params
    })
}
/**
 * 
 * @param {*} roleId 
 * @returns 
 */
export const reqRoleInfo = (roleId) => {
    return request({
        url: `/system/role/${roleId}`,
        method: "GET",
    })
}
/**
 * 
 * @param {*} data 
 * @returns 
 */
export const reqSaveRole = (data) => {
    return request({
        url: `/system/role`,
        method: "POST",
        data
    })
}

export const reqUpdateRole = (data) => {
    return request({
        url: `/system/role`,
        method: "PUT",
        data
    })
}