package cc.lijad.fantasticblog.service;

import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.domain.entity.Notice;
import cc.lijad.fantasticblog.domain.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface NoticeService extends IService<Notice> {
    List<Notice> getList();

    PageVo getPage(PageDTO pageDTO);


    Notice getNoticeById(Long noticeId);

    boolean saveNotice(Notice notice);

    boolean updateNoticeById(Notice notice);

    boolean deleteNoticeBatchByIds(Long[] noticeIds);
}
