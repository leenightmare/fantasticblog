package cc.lijad.fantasticblog.service.impl;

import cc.lijad.fantasticblog.annotation.FlushCache;
import cc.lijad.fantasticblog.constant.BlogSys;
import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.domain.entity.Comment;
import cc.lijad.fantasticblog.domain.model.ArticleCommentUpdateModel;
import cc.lijad.fantasticblog.domain.vo.CommentEditVo;
import cc.lijad.fantasticblog.domain.vo.PageVo;
import cc.lijad.fantasticblog.mapper.ArticleMapper;
import cc.lijad.fantasticblog.mapper.CommentMapper;
import cc.lijad.fantasticblog.service.ArticleService;
import cc.lijad.fantasticblog.service.CommentService;
import cc.lijad.fantasticblog.utils.PageVoUtil;
import cc.lijad.fantasticblog.utils.RedisCache;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static cc.lijad.fantasticblog.constant.RedisKey.*;

/**
 *
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
        implements CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 查询文章评论
     *
     * @param articleId
     * @return
     */
    @Override
    public R getCommentByArticleId(Long articleId) {
        List<Comment> cacheComment = redisCache.getCacheObject(BLOG_ARTICLE_COMMENT + articleId);
        if (cacheComment == null) {
            if (ObjectUtil.isNotNull(articleId) && articleId >= 0) {
                //先查询一级评论
                LambdaQueryWrapper<Comment> parentNodeWrapper = new LambdaQueryWrapper<>();
                parentNodeWrapper.eq(Comment::getParentId, 0)
                        .eq(Comment::getDelFlag, 0)
                        .eq(Comment::getArticleId, articleId);
                List<Comment> parentComment = list(parentNodeWrapper);

                //再查二级评论
                LambdaQueryWrapper<Comment> childNodeWrapper = new LambdaQueryWrapper<>();
                for (Comment comment : parentComment) {
                    Long pid = comment.getId();
                    childNodeWrapper.eq(Comment::getParentId, pid);
                    List<Comment> children = list(childNodeWrapper);
                    comment.setChildren(children);
                }
                cacheComment = parentComment;
                redisCache.setCacheObject(BLOG_ARTICLE_COMMENT + articleId, cacheComment, DEFAULT_EXPIRE, TimeUnit.MINUTES);
            } else {
                return R.error("评论不存在");
            }
        }
        return R.success(cacheComment);
    }

    /**
     * 添加评论
     *
     * @param comment
     * @return
     */
    @Override
    @FlushCache(dimKey = "comment")
    public R addComment(Comment comment) {
        if (StrUtil.isEmpty(comment.getUserNickname())) {
            comment.setUserNickname("Anonymous");
        }
        comment.setCreateTime(DateUtil.date());
        //添加评论
        int incrCount = baseMapper.insert(comment);
        // 添加评论之后，文章的评论数添加
        Long articleId = comment.getArticleId();
        if (!BlogSys.COMMENT_MESSAGE_ARTICLE_ID.equals(articleId.toString())) {
            articleMapper.incrCommentCount(articleId, incrCount);
            // 清除文章详情缓存
            redisCache.deleteObject(ARTICLE_DETAIL + articleId);
        }
        return R.success();
    }

    /**
     * 获取留言
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public R getCommentMessage(Integer pageNum, Integer pageSize) {
        //根据页码获取留言
        PageVo cacheObject = redisCache.getCacheObject(BLOG_ARTICLE_COMMENT_MESSAGE + pageNum);
        if (cacheObject == null) {
            Page<Comment> page = new Page<>(pageNum, pageSize);
            LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Comment::getArticleId, BlogSys.COMMENT_MESSAGE_ARTICLE_ID)
                    .eq(Comment::getDelFlag, 0);
            page(page, wrapper);
            List<Comment> sourceMessage = page.getRecords();
            cacheObject = PageVoUtil.toPageVo(page, sourceMessage);
            redisCache.setCacheObject(BLOG_ARTICLE_COMMENT_MESSAGE + pageNum, cacheObject, DEFAULT_EXPIRE, TimeUnit.MINUTES);
        }
        return R.success(cacheObject);
    }

    /**
     * 后台获取评论
     *
     * @param pageDTO
     * @param articleId
     * @return
     */
    @Override
    public R getCommentTableData(PageDTO pageDTO, Long articleId) {
        String cachekey = BLOG_ARTICLE_COMMENT_EDIT + pageDTO.getPageNum() + ":" + pageDTO.getPageSize() + ":" + articleId;
        PageVo<CommentEditVo> cacheObject = redisCache.getCacheObject(cachekey);
        if (cacheObject == null) {
            Integer pageNum = (pageDTO.getPageNum() - 1) * pageDTO.getPageSize();
            Integer pageSize = pageDTO.getPageSize();
            Integer total = baseMapper.selectCommentEditCount(articleId);
            List<CommentEditVo> editVoList = baseMapper.selectCommentForEdit(pageNum, pageSize, articleId);
            PageVo<CommentEditVo> editVoPageVo = new PageVo<>();
            editVoPageVo.setTotal(Long.valueOf(total));
            editVoPageVo.setPageNum(Long.valueOf(pageDTO.getPageNum()));
            editVoPageVo.setPageSize(Long.valueOf(pageDTO.getPageSize()));
            editVoPageVo.setList(editVoList);
            cacheObject = editVoPageVo;
            redisCache.setCacheObject(cachekey, cacheObject, DEFAULT_EXPIRE, TimeUnit.MINUTES);
        }
        return R.success(cacheObject);
    }

    /**
     * 删除评论
     *
     * @param ids
     * @return
     */
    @Override
    @FlushCache(dimKey = "comment")
    public R deleteComment(Long[] ids) {
        boolean flag = removeBatchByIds(Arrays.asList(ids));
        //TODO 修改文章评论数量
        List<ArticleCommentUpdateModel> articleCommentUpdateModels = baseMapper.selectCommentDeleteToArticle(Arrays.asList(ids));
        articleService.updateBatchCommentCount(articleCommentUpdateModels);
        return R.success();
    }

}




