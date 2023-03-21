package cc.lijad.fantasticblog.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ljd
 * @create 2023/2/14 22:12
 */
public class BeanCopyUtil {

    private BeanCopyUtil() {
    }

    public static <T> T copyObj(Object source, Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
            BeanUtils.copyProperties(source, t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public static <T> List<T> copyToList(List<?> source, Class<T> clazz) {
        return source.stream().map(item -> {
            T t = null;
            try {
                t = clazz.newInstance();
                BeanUtils.copyProperties(item, t);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return t;
        }).collect(Collectors.toList());
    }
}
