package cc.lijad.fantasticblog.domain.model;

import lombok.Data;

/**
 * @author ljd
 * @create 2023/3/20 11:44
 */
@Data
public class ArticleCommentUpdateModel {
    private Long articleId;
    private Integer influenceCount;
}
