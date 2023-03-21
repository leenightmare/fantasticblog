package cc.lijad.fantasticblog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ljd
 * @create 2023/2/14 22:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVo extends ArticleListVo implements Serializable {
    /**
     * 文章内容
     */
    private String content;
}
