package cc.lijad.fantasticblog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ljd
 * @create 2023/2/15 15:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryArticleVo implements Serializable {
    private Long categoryId;
    private String categoryName;
    private Long articleCount;
}
