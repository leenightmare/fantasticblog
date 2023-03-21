package cc.lijad.fantasticblog.controller;

import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.utils.MinioUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ljd
 * @create 2023/2/17 21:06
 */
@RestController
public class TestController {

    @GetMapping("/index")
    public R index() {
        return R.success();
    }

    @GetMapping("/preurl")
    public R getPreUrl() throws Exception {
        String url = MinioUtil.getPresignedObjectUrl("java.png");
        return R.success(url);
    }
}
