package cc.lijad.fantasticblog.mapper;

import cc.lijad.fantasticblog.domain.entity.Article;
import cc.lijad.fantasticblog.domain.entity.Tag;
import cc.lijad.fantasticblog.domain.model.ArticleCommentUpdateModel;
import cc.lijad.fantasticblog.domain.vo.CategoryArticleVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity cc.lijad.fantasticblog.domain.entity.Article
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    List<Article> selectArticleForTimeLine();

    List<CategoryArticleVo> selectArticleForCategory();

    List<Article> selectVisitCount();

    List<Long> selectTagListByArticleId(Long id);

    Integer incrCommentCount(@Param("articleId") Long articleId, @Param("incrCount") int count);

}




