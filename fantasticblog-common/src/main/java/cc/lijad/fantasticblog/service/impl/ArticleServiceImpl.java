package cc.lijad.fantasticblog.service.impl;

import cc.lijad.fantasticblog.annotation.FlushCache;
import cc.lijad.fantasticblog.constant.BlogStatus;
import cc.lijad.fantasticblog.constant.RedisKey;
import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.dto.ArticleDTO;
import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.domain.entity.Article;
import cc.lijad.fantasticblog.domain.entity.ArticleTag;
import cc.lijad.fantasticblog.domain.entity.Category;
import cc.lijad.fantasticblog.domain.entity.User;
import cc.lijad.fantasticblog.domain.model.ArticleCommentUpdateModel;
import cc.lijad.fantasticblog.domain.vo.*;
import cc.lijad.fantasticblog.mapper.ArticleMapper;
import cc.lijad.fantasticblog.service.ArticleService;
import cc.lijad.fantasticblog.service.ArticleTagService;
import cc.lijad.fantasticblog.service.CategoryService;
import cc.lijad.fantasticblog.utils.*;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static cc.lijad.fantasticblog.constant.RedisKey.*;

/**
 *
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private InitUtil initUtil;

    /**
     * 分页查询文章
     *
     * @param article
     * @param pageDTO
     * @return
     */
    @Override
    public R getArticleList(PageDTO pageDTO, Article article) {
//        PageVo cacheObject = redisCache.getCacheObject(BLOG_ARTICLE_PAGE + pageDTO.getPageNum());
        //缓存没找到，或者标题不为空（根据关键词搜索）时，走数据库
//        if (cacheObject == null || ObjectUtil.isNotEmpty(article.getTitle())) {
        Page<Article> sourcePage = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(article)) {
            wrapper.eq(ObjectUtil.isNotNull(article.getCategoryId()) && article.getCategoryId() > 0, Article::getCategoryId, article.getCategoryId())
                    .like(ObjectUtil.isNotNull(article.getTitle()), Article::getTitle, article.getTitle());
        }
        wrapper.eq(Article::getStatus, BlogStatus.NORMAL)
                .orderByDesc(Article::getUpdateTime);
        page(sourcePage, wrapper);
        //封装成VO
        List<Article> sourceList = sourcePage.getRecords();
        List<ArticleListVo> listVos = sourceList.stream().map(art -> {
            ArticleListVo artVo = new ArticleListVo();
            BeanUtils.copyProperties(art, artVo);
            artVo.setCategoryName(findCategoryName(art.getCategoryId()));
            //如果Redis缓存中有文章浏览量的数据，改成读取它
            Integer visitCount = redisCache.getCacheMapValue(RedisKey.Article_VISIT_COUNT, artVo.getId().toString());
            if (visitCount == null) {
                // Redis缓存中文章浏览量的数据为空，可能是删除了还是什么，重新加载进redis
                initUtil.cacheArticleVisitCount();
                visitCount = redisCache.getCacheMapValue(RedisKey.Article_VISIT_COUNT, artVo.getId().toString());
            }
            artVo.setVisitCount(visitCount);
            return artVo;
        }).collect(Collectors.toList());
        PageVo pageVo = PageVoUtil.toPageVo(sourcePage, listVos);
//        cacheObject = pageVo;
        //不是根据关键词搜索的时候，添加缓存
//        if (article == null || ObjectUtil.isEmpty(article.getTitle())) {
//            redisCache.setCacheObject(BLOG_ARTICLE_PAGE + pageDTO.getPageNum(), cacheObject, DEFAULT_EXPIRE, TimeUnit.MINUTES);
//        }
//    }
        return R.success(pageVo);
    }

    /**
     * 获取文章详情
     *
     * @param id
     * @return
     */
    @Override
    public R getArticleDetail(Long id) {
        Map<String, Object> cacheMap = redisCache.getCacheMap(ARTICLE_DETAIL + id);
        ArticleDetailVo cacheArticle = null;
        if (cacheMap != null || !ObjectUtil.isEmpty(cacheMap)) {
            cacheArticle = BeanUtil.mapToBean(cacheMap, ArticleDetailVo.class, false);
        }
        if (ObjectUtil.isEmpty(cacheMap) || cacheArticle == null) {
            Article article = getById(id);
            if (ObjectUtil.isNotNull(article)) {
                ArticleDetailVo detailVo = BeanCopyUtil.copyObj(article, ArticleDetailVo.class);
                detailVo.setCategoryName(findCategoryName(article.getCategoryId()));
                //如果Redis缓存中有文章浏览量的数据，改成读取它
                Integer visitCount = redisCache.getCacheMapValue(RedisKey.Article_VISIT_COUNT, detailVo.getId().toString());
                if (visitCount == null) {
                    // Redis缓存中文章浏览量的数据为空，可能是删除了还是什么，重新加载进redis
                    initUtil.cacheArticleVisitCount();
                    visitCount = redisCache.getCacheMapValue(RedisKey.Article_VISIT_COUNT, detailVo.getId().toString());
                }
                detailVo.setVisitCount(visitCount);
                //添加缓存
                cacheArticle = detailVo;
                Map<String, Object> map = BeanUtil.beanToMap(cacheArticle);
                redisCache.setCacheMap(ARTICLE_DETAIL + id, map);
                redisCache.expire(ARTICLE_DETAIL + id, DEFAULT_EXPIRE, TimeUnit.MINUTES);
            } else {
                return R.error("文章不存在！");
            }
        }
        return R.success(cacheArticle);
    }

    /**
     * 根据分类ID查询出名字
     *
     * @param categoryId
     * @return
     */
    private String findCategoryName(Long categoryId) {
        if (ObjectUtil.isNotNull(categoryId)) {
            Category category = categoryService.getById(categoryId);
            return category == null ? null : category.getName();
        }
        return null;
    }

    /**
     * 归档
     *
     * @return
     */
    @Override
    public R getTimeLineArticle() {
        //从redis中获取
        List<TimeLineArticleVo> cacheList = redisCache.getCacheObject(TIMELINE_ARTICLE_LIST);
        if (cacheList == null) {
            List<Article> timeLineArticles = baseMapper.selectArticleForTimeLine();
            //取出时间
            List<Date> yearTimesList = timeLineArticles.stream().map(Article::getCreateTime).collect(Collectors.toList());
            //时间转换为年份并去重
            List<Integer> years = yearTimesList.stream().map(x -> DateUtil.year(x)).distinct().collect(Collectors.toList());

            List<TimeLineArticleVo> lineVos = new ArrayList<>();
            for (Integer yearTimes : years) {
                TimeLineArticleVo timeLineVo = new TimeLineArticleVo();
                //对文章集合中的时间与年份匹配
                List<Article> articleList = timeLineArticles.stream().filter(item -> {
                    int createYear = DateUtil.year(item.getCreateTime());
                    return createYear == yearTimes;
                }).collect(Collectors.toList());
                timeLineVo.setYear(yearTimes.toString());
                timeLineVo.setArticle(articleList);
                lineVos.add(timeLineVo);
            }
            //添加缓存
            cacheList = lineVos;
            redisCache.setCacheObject(TIMELINE_ARTICLE_LIST, cacheList, DEFAULT_EXPIRE, TimeUnit.MINUTES);
        }
        return R.success(cacheList);
    }

    /**
     * 分类对应文章数量
     *
     * @return
     */
    @Override
    public R getCategoryArticleList() {
        List<CategoryArticleVo> cacheList = redisCache.getCacheObject(ARTICLE_CATEGORY_COUNT);
        if (cacheList == null) {
            cacheList = baseMapper.selectArticleForCategory();
            redisCache.setCacheObject(ARTICLE_CATEGORY_COUNT, cacheList, DEFAULT_EXPIRE, TimeUnit.MINUTES);
        }
        return R.success(cacheList);
    }

    /**
     * 后台获取文章详情
     *
     * @param id
     * @return
     */
    @Override
    public R getArticleForEdit(Long id) {
        Article article = getById(id);
        ArticleEditVo articleEditVo = BeanCopyUtil.copyObj(article, ArticleEditVo.class);
        List<Long> tagList = baseMapper.selectTagListByArticleId(id);
        articleEditVo.setTagList(tagList);
        return R.success(articleEditVo);
    }

    /**
     * 添加
     *
     * @param articleDTO
     * @return
     */
    @Transactional
    @Override
    @FlushCache(dimKey = "article")
    public R saveArticle(ArticleDTO articleDTO) {
        Article article = BeanCopyUtil.copyObj(articleDTO, Article.class);
        //获取用户信息
        User user = SecurityUtils.getLoginUser().getUser();
        article.setUserId(user.getId());
        article.setUserNickname(user.getNickname());
        article.setUserAvatar(user.getAvatar());
        article.setCreateTime(new Date());
        System.out.println(article);
        //TODO 插入数据库,自动返回主键
        save(article);
        //TODO 添加标签信息
        List<Long> tagList = articleDTO.getTagList();
        List<ArticleTag> tags = tagList.stream().map(t -> {
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleId(article.getId());
            articleTag.setTagId(t);
            return articleTag;
        }).collect(Collectors.toList());
        articleTagService.saveBatch(tags);
        return R.success();
    }

    /**
     * 更新
     *
     * @param articleDTO
     * @return
     */
    @Override
    @Transactional
    @FlushCache(dimKey = "article")
    public R updateArticle(ArticleDTO articleDTO) {
        Article article = BeanCopyUtil.copyObj(articleDTO, Article.class);
        article.setUpdateTime(new Date());
        //更新文章
        updateById(article);
        //更新标签信息前，先删除
        LambdaUpdateWrapper<ArticleTag> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ArticleTag::getArticleId, article.getId());
        articleTagService.remove(wrapper);
        //重新更新
        List<Long> tagList = articleDTO.getTagList();
        List<ArticleTag> tags = tagList.stream().map(t -> {
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleId(article.getId());
            articleTag.setTagId(t);
            return articleTag;
        }).collect(Collectors.toList());
        boolean flag = articleTagService.saveOrUpdateBatch(tags);
        return R.success();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    @FlushCache(dimKey = "article")
    public R deleteArticle(Long[] id) {
        List<Long> ids = Arrays.asList(id);
        boolean flag = removeBatchByIds(ids);
        return R.success();
    }

    /**
     * 根据标题查询
     *
     * @param title
     * @return
     */
    @Override
    public R queryTitle(String title) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        if (!"".equals(title)) {
            wrapper.like(Article::getTitle, title);
        }
        List<Article> articles = list(wrapper);
        List<HashMap<String, Object>> maps = articles.stream().map(a -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", a.getId());
            map.put("title", a.getTitle());
            return map;
        }).collect(Collectors.toList());
        return R.success(maps);
    }

    /**
     * 浏览量+1
     */
    @Override
    public void incrVisitCount(Long id) {
        //TODO 浏览量+1
        redisCache.incrementCacheMapValue(RedisKey.Article_VISIT_COUNT, id.toString());
//        redisCache.incrementCacheMapValue(ARTICLE_DETAIL + id, "visitCount",);
        redisCache.setCacheMapValue(ARTICLE_DETAIL + id, "visitCount",
                redisCache.getCacheMapValue(RedisKey.Article_VISIT_COUNT, id.toString()));
    }


    /**
     * 批量更新评论数量
     *
     * @param updateModels
     * @return
     */
    @Override
    @FlushCache(dimKey = "article:detail")
    public boolean updateBatchCommentCount(List<ArticleCommentUpdateModel> updateModels) {
        for (ArticleCommentUpdateModel model : updateModels) {
            baseMapper.incrCommentCount(model.getArticleId(), model.getInfluenceCount() * (-1));
        }
        return true;
    }
}




