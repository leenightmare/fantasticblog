import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    // params: { token }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}



export function reqUserPage(params) {
  return request({
    url: '/system/user/page',
    method: 'GET',
    params
  })
}

export function reqUserDetailForEdit(id) {
  return request({
    url: `/system/user/${id}`,
    method: 'GET',
  })
}

/**删除 */
export function reqDeleteUser(id) {
  return request({
    url: `/system/user/${id}`,
    method: 'DELETE',
  })
}

/**修改 */
export function reqUpdateUser(data) {
  return request({
    url: `/system/user`,
    method: 'PUT',
    data
  })
}

/**添加 */
export function reqSaveUser(data) {
  return request({
    url: `/system/user`,
    method: 'POST',
    data
  })
}

/**添加 */
export function reqAutheUser(id) {
  return request({
    url: `/system/user/auth/${id}`,
    method: 'GET',
  })
}




