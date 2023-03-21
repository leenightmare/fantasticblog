package cc.lijad.fantasticblog.utils;

import cc.lijad.fantasticblog.domain.vo.PageVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author ljd
 * @create 2023/2/15 22:42
 */
public class PageVoUtil {

    public static PageVo toPageVo(Page source, List pageList) {
        PageVo pageVo = new PageVo();
        pageVo.setList(pageList);
        pageVo.setPageNum(source.getCurrent());
        pageVo.setPageSize(source.getSize());
        pageVo.setTotal(source.getTotal());
        return pageVo;

    }
}
