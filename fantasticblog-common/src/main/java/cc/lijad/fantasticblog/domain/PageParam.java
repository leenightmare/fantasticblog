package cc.lijad.fantasticblog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ljd
 * @create 2023/2/14 16:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageParam {
    public static final String PAGE_NUM = "pageNum";
    public static final String PAGE_SIZE = "pageSize";

    private Integer pageNum;
    private Integer pageSize;
}
