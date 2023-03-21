package cc.lijad.fantasticblog.controller.system;

import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.domain.entity.Notice;
import cc.lijad.fantasticblog.domain.vo.PageVo;
import cc.lijad.fantasticblog.service.NoticeService;
import cc.lijad.fantasticblog.utils.PageVoUtil;
import cc.lijad.fantasticblog.utils.SecurityUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;

/**
 * @author ljd
 * @create 2023/3/3 23:11
 */
@RequestMapping("/system/notice")
@RestController
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 分页获取
     *
     * @param pageDTO
     * @return
     */
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPermi('system:notice:list')")
    public R page(@Valid PageDTO pageDTO) {
        PageVo pageVo = noticeService.getPage(pageDTO);
        return R.success(pageVo);
    }

    /**
     * ID获取
     *
     * @param noticeId
     * @return
     */
    @GetMapping("/{noticeId}")
    @PreAuthorize("@ss.hasPermi('system:notice:query')")
    public R info(@PathVariable("noticeId") Long noticeId) {
        Notice notice = noticeService.getNoticeById(noticeId);
        return R.success(notice);
    }

    /**
     * 添加
     *
     * @param notice
     * @return
     */
    @PostMapping
    @PreAuthorize("@ss.hasPermi('system:notice:add')")
    public R add(@RequestBody Notice notice) {
        noticeService.saveNotice(notice);
        return R.success();
    }


    /**
     * 修改
     *
     * @param notice
     * @return
     */
    @PutMapping
    @PreAuthorize("@ss.hasPermi('system:notice:edit')")
    public R update(@RequestBody Notice notice) {
        noticeService.updateNoticeById(notice);
        return R.success();
    }

    /**
     * 删除
     *
     * @param noticeIds
     * @return
     */
    @DeleteMapping("/{noticeIds}")
    @PreAuthorize("@ss.hasPermi('system:notice:remove')")
    public R delete(@PathVariable Long[] noticeIds) {
        noticeService.deleteNoticeBatchByIds(noticeIds);
        return R.success();
    }
}
