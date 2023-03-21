package cc.lijad.fantasticblog.service;

import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.dto.ArticleDTO;
import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.domain.entity.Article;
import cc.lijad.fantasticblog.domain.model.ArticleCommentUpdateModel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface ArticleService extends IService<Article> {

    R getArticleList(PageDTO pageDTO, Article article);

    R getArticleDetail(Long id);

    R getTimeLineArticle();

    R getCategoryArticleList();

    R getArticleForEdit(Long id);

    R saveArticle(ArticleDTO articleDTO);

    R updateArticle(ArticleDTO articleDTO);

    R deleteArticle(Long[] id);

    R queryTitle(String title);

    void incrVisitCount(Long id);

    /**
     * 批量更新评论数量
     *
     * @param articleCommentUpdateModels
     * @return
     */
    boolean updateBatchCommentCount(List<ArticleCommentUpdateModel> articleCommentUpdateModels);
}
