package cc.lijad.fantasticblog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author ljd
 * @create 2023/2/23 22:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVo {
    /**
     *
     */
    private Long id;

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

    private static final long serialVersionUID = 1L;

    public boolean isAdmin()
    {
        return isAdmin(this.id);
    }

    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }
}
