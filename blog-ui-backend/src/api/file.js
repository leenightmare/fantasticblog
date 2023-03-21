import request from '@/utils/request'

export const reqFileUpload = () => {

}

export const reqPreFileUploadURL = (fileName) => {
    return request({
        url: "/file/preUpload",
        method: "GET",
        params: {
            fileName
        }
    })
}

export const reqFileUploadToOSS = ({ url, method, headers, data }) => {
    return request({ url, method, headers, data })
}