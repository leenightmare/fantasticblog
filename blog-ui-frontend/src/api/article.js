import requests from "./request";


//博客列表 首页展示
export const reqMiniArticle = ({ pageNum, pageSize }) => {
    return requests({
        url: "/article/list",
        method: "GET",
        params: {
            pageNum,
            pageSize
        }
    });
}


// 根据ID获取博客详细信息
export const reqDetailArticle = (articleId) => {
    return requests({
        url: `/article/detail/${articleId}`,
        method: "GET"
    })
}


// 根据分类获取文章
// /article/tag/{tagId}
export const reqpageMiniArticleByCategoryId = ({ pageNum, pageSize, id }) => {
    return requests({
        url: `/article/category/${id}`,
        method: "GET",
        params: {
            pageNum, pageSize
        }
    })
}

// 根据关键词查询
export const pageMiniArticleByKeyWord = (params) => {
    console.log(params);
    return requests({
        url: `/article/list`,
        method: "GET",
        params: {
            ...params,
            pageNum: params.pageNum || 1,
            pageSize: params.pageSize || 10,
        },
    })
}


//归档查询
export const reqTimeLineArticle = () => {
    return requests({
        url: "/article/timeline",
        method: "GET"
    })
}

//分类列表对应文章数量
export const reqCategoryArticle = () => {
    return requests({
        url: "/article/category/list",
        method: "GET"
    })
}


// 获取所有标签
export const reqTagList = () => {
    return requests({
        url: "/article/tag/list",
        method: "GET",
    })
}