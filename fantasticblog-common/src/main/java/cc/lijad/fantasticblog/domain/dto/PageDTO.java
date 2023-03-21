package cc.lijad.fantasticblog.domain.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author ljd
 * @create 2023/2/26 15:26
 */
@Data
public class PageDTO {

    @NotNull
    @Min(value = 1, message = "必须为大于0的整数")
    private Integer pageNum;


    @NotNull
    @Min(value = 1, message = "必须为大于0的整数")
    private Integer pageSize;
}
