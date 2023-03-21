package cc.lijad.fantasticblog.utils;

import cc.lijad.fantasticblog.domain.PageParam;
import cn.hutool.core.convert.Convert;

/**
 * @author ljd
 * @create 2023/2/14 15:34
 */
public class PageParamUtil {

    private PageParamUtil() {
    }

    public static PageParam getPageParam() {

        //TODO 多线程考虑
        Integer pageNum = Convert.toInt(ServletUtil.getParameter(PageParam.PAGE_NUM));
        Integer pageSize = Convert.toInt(ServletUtil.getParameter(PageParam.PAGE_SIZE));
        //TODO 空指针判断
        if (pageNum < 0 || pageSize < 0) {
            pageNum = 1;
            pageSize = 10;
        }
        return new PageParam(pageNum, pageSize);
    }
}
