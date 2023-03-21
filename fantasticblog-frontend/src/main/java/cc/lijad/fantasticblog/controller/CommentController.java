package cc.lijad.fantasticblog.controller;

import cc.lijad.fantasticblog.domain.PageParam;
import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.entity.Comment;
import cc.lijad.fantasticblog.service.CommentService;
import cc.lijad.fantasticblog.utils.PageParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ljd
 * @create 2023/2/15 16:06
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    /**
     * 查询文章评论
     *
     * @param articleId
     * @return
     */
    @GetMapping("/{articleId}")
    public R getCommentByArticleId(@PathVariable Long articleId) {
        return commentService.getCommentByArticleId(articleId);
    }

    /**
     * 添加评论
     *
     * @param comment
     * @return
     */
    @PostMapping("/save")
    public R save(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    /**
     * 获取留言 article=0
     *
     * @return
     */
    @GetMapping("/message")
    public R getMessage() {
        PageParam pageParam = PageParamUtil.getPageParam();
        return commentService.getCommentMessage(pageParam.getPageNum(), pageParam.getPageSize());
    }
}
