package cc.lijad.fantasticblog.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 评论表
 *
 * @TableName blog_comment
 */
@TableName(value = "blog_comment")
@Data
public class Comment implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 父级ID
     */
    private Long parentId;

    /**
     * 评论所在文章ID
     */
    private Long articleId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论用户ID
     */
    private Long userId;

    /**
     * 评论用户头像
     */
    private String userAvatar;

    /**
     * 评论用户昵称
     */
    private String userNickname;

    /**
     * 评论用户邮箱
     */
    private String userEmail;

    /**
     * 回复对象ID
     */
    private Long replyUserId;

    /**
     * 回复对象头像
     */
    private String replyUserAvatar;

    /**
     * 回复对象昵称
     */
    private String replyUserNickname;

    /**
     * 评论创建时间
     */
    private Date createTime;

    /**
     * 评论状态 0：正常 1：审核
     */
    private Integer status;

    /**
     * 删除标志 0：正常 1：删除
     */
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<Comment> children;
}