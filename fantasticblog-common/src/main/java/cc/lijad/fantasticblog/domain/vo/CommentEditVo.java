package cc.lijad.fantasticblog.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ljd
 * @create 2023/2/28 11:36
 */
@Data
public class CommentEditVo implements Serializable {
    /**
     *
     */
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
     * 文章标题
     */
    private String articleTitle;

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

}
