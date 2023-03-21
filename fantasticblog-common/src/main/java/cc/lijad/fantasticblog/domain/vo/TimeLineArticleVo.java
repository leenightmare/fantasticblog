package cc.lijad.fantasticblog.domain.vo;

import cc.lijad.fantasticblog.domain.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author ljd
 * @create 2023/2/15 15:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeLineArticleVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String year;

    private List<Article> article;
}
