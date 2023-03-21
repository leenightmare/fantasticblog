package cc.lijad.fantasticblog.controller;

import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.entity.Notice;
import cc.lijad.fantasticblog.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ljd
 * @create 2023/3/3 23:20
 */

@RequestMapping("/notice")
@RestController
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/list")
    public R getBlogNotice() {
        List<Notice> notices = noticeService.getList();
        return R.success(notices);
    }
}
