package cc.lijad.fantasticblog.mapper;

import cc.lijad.fantasticblog.domain.entity.Comment;
import cc.lijad.fantasticblog.domain.model.ArticleCommentUpdateModel;
import cc.lijad.fantasticblog.domain.vo.CommentEditVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity cc.lijad.fantasticblog.domain.entity.Comment
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    List<CommentEditVo> selectCommentForEdit(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("articleId") Long articleId);

    Integer selectCommentEditCount(Long articleId);

    /**
     * 查询批量删除时有对应哪些文章和数量
     *
     * @param ids
     * @return
     */
    List<ArticleCommentUpdateModel> selectCommentDeleteToArticle(@Param("ids") List<Long> ids);
}




