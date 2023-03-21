package cc.lijad.fantasticblog.controller;

import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author ljd
 * @create 2023/2/28 10:43
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermi('blog:comment:list')")
    public R list(@Valid PageDTO pageDTO, Long articleId) {
        return commentService.getCommentTableData(pageDTO, articleId);
    }


    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPermi('blog:comment:remove')")
    public R delete(@PathVariable Long[] ids) {
        return commentService.deleteComment(ids);
    }

    @GetMapping("/message")
    @PreAuthorize("@ss.hasPermi('blog:comment:list')")
    public R getMessage(@Valid PageDTO pageDTO) {
        return commentService.getCommentMessage(pageDTO.getPageNum(), pageDTO.getPageSize());
    }
}
