package cc.lijad.fantasticblog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ljd
 * @create 2023/2/14 20:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVo<T> {

    /**
     * 分页数据
     */
    private List<T> list;

    /**
     * 当前页
     */
    private Long pageNum;

    /**
     * 每页几条
     */
    private Long pageSize;

    /**
     * 总记录数
     */
    private Long total;
}
