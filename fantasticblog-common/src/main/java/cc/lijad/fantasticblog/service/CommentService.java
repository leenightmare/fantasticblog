package cc.lijad.fantasticblog.service;

import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.domain.entity.Comment;
import cc.lijad.fantasticblog.domain.model.ArticleCommentUpdateModel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface CommentService extends IService<Comment> {

    R getCommentByArticleId(Long articleId);

    R addComment(Comment comment);

    R getCommentMessage(Integer pageNum, Integer pageSize);

    R getCommentTableData(PageDTO pageDTO, Long articleId);

    R deleteComment(Long[] ids);

}
