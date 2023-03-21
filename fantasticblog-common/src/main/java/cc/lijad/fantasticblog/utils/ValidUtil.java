package cc.lijad.fantasticblog.utils;

import cc.lijad.fantasticblog.exception.ArgumentException;


/**
 * @author ljd
 * @create 2023/2/25 18:24
 * 验证参数
 */
public class ValidUtil {

    public static void validPage(Integer... args) {
        for (Integer arg : args) {
            if (null == arg || 1 > arg.intValue()) {
                throw new ArgumentException("页码必须为大于0的整数");
            }
        }
    }
}
