package cc.lijad.fantasticblog.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @TableName blog_chat
 */
@TableName(value = "blog_chat")
@Data
public class Chat implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 对话类型 0:闲聊,1:说说
     */
    private Integer type;

    /**
     * 对话内容
     */
    private String content;

    /**
     * 看板娘图片链接
     */
    private String url;

     /**
     * 状态：0:开启,1:停用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}