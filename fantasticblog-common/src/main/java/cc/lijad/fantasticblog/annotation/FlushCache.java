package cc.lijad.fantasticblog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ljd
 * @create 2023/3/19 15:54
 */
// 删除缓存
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface FlushCache {
    String dimKey();
}
