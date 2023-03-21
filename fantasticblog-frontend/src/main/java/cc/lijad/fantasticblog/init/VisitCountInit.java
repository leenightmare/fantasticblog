package cc.lijad.fantasticblog.init;

import cc.lijad.fantasticblog.utils.InitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author ljd
 * @create 2023/2/16 20:25
 */
@Component
public class VisitCountInit implements CommandLineRunner {
    @Autowired
    private InitUtil initUtil;

    @Override
    public void run(String... args) throws Exception {
        initUtil.cacheArticleVisitCount();
    }
}
