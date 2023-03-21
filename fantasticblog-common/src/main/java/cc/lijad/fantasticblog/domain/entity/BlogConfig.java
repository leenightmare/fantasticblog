package cc.lijad.fantasticblog.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @TableName blog_config
 */
@TableName(value = "blog_config")
@Data
public class BlogConfig implements Serializable {
    /**
     * id
     */
    @TableId
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 首页诗句
     */
    @NotBlank(message = "首页诗句不能为空")
    private String poem;

    /**
     * 首页背景
     */
    @NotBlank(message = "首页背景不能为空")
    private String indexBackground;

    /**
     * 看板娘链接
     */
    @NotBlank(message = "看板娘链接不能为空")
    private String girlUrl;

    /**
     * 关于我的描述
     */
    @NotBlank(message = "关于我的描述不能为空")
    private String aboutDescription;

    /**
     * 微信链接
     */
    @NotBlank(message = "微信链接不能为空")
    private String wechat;

    /**
     * github地址
     */
    @NotBlank(message = "github地址不能为空")
    private String github;

    /**
     * gitee地址
     */
    @NotBlank(message = "gitee地址不能为空")
    private String gitee;

    /**
     * qq地址
     */
    @NotBlank(message = "qq地址不能为空")
    private String qq;

    /**
     * 邮箱地址
     */
    @NotBlank(message = "邮箱地址不能为空")
    private String email;

    /**
     * 挂件地址
     */
    @NotBlank(message = "挂件地址不能为空")
    private String pendant;

    /**
     * 站长名称
     */
    @NotBlank(message = "站长名称不能为空")
    private String username;

    /**
     * 站长头像
     */
    @NotBlank(message = "用户头像不能为空")
    private String userAvatar;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}