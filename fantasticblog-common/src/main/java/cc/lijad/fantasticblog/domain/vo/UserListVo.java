package cc.lijad.fantasticblog.domain.vo;

import cc.lijad.fantasticblog.domain.entity.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ljd
 * @create 2023/3/1 9:28
 */
@Data
public class UserListVo implements Serializable {
    /**
     *
     */
    private Long id;


    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;


    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 用户性别：0：男，1：女，2：未知）
     */
    private Integer sex;

    /**
     * 用户状态    0：正常 1：禁用
     */
    private Integer status;

    /**
     * 删除标志：0：正常 1：删除
     */
    private Integer delFlag;

    /**
     * 用户角色列表
     */
    private List<Role> roles;

    /**
     *
     */
    private static final long serialVersionUID = 1L;
}
