package cc.lijad.fantasticblog.domain.vo;

import cc.lijad.fantasticblog.domain.entity.Tag;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ljd
 * @create 2023/2/27 14:52
 */
@Data
public class ArticleEditVo implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章描述
     */
    private String description;


    /**
     * 文章版图
     */
    private String headImage;

    /**
     * 文章内容
     */
    private String content;


    /**
     * 分类ID
     */
    private Long categoryId;


    /**
     * 是否开启评论 0：开启 1：关闭
     */
    private Integer isComment;

    /**
     * 文章是否置顶  0：是  1：否
     */
    private Integer isTop;

    /**
     * 是否开启附加 0：是 1：否
     */
    private Integer isExtra;

    /**
     * 文章状态：0：发布 1：草稿
     */
    private Integer status;

    /**
     * 标签列表
     */
    private List<Long> tagList;
}
