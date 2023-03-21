package cc.lijad.fantasticblog.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 文章表
 *
 * @TableName blog_article
 */
@TableName(value = "blog_article")
@Data
public class Article implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
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
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 访问量
     */
    private Integer visitCount;

    /**
     * 文章作者ID
     */
    private Long userId;

    /**
     * 文章作者名称
     */
    private String userNickname;

    /**
     * 文章作者头像
     */
    private String userAvatar;

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
     * 删除标志：0：正常 1：删除
     */
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}