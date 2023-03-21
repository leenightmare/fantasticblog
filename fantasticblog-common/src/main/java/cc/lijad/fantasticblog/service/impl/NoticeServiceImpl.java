package cc.lijad.fantasticblog.service.impl;

import cc.lijad.fantasticblog.annotation.FlushCache;
import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.domain.vo.PageVo;
import cc.lijad.fantasticblog.utils.PageVoUtil;
import cc.lijad.fantasticblog.utils.RedisCache;
import cc.lijad.fantasticblog.utils.SecurityUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cc.lijad.fantasticblog.domain.entity.Notice;
import cc.lijad.fantasticblog.service.NoticeService;
import cc.lijad.fantasticblog.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static cc.lijad.fantasticblog.constant.RedisKey.*;

/**
 *
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice>
        implements NoticeService {

    @Autowired
    private RedisCache redisCache;

    @Override
    public List<Notice> getList() {
        List<Notice> cacheObject = redisCache.getCacheObject(FRONTEND_BLOG_NOTICE_LIST);
        if (cacheObject == null) {
            cacheObject = list();
            redisCache.setCacheObject(FRONTEND_BLOG_NOTICE_LIST, cacheObject, DEFAULT_EXPIRE, TimeUnit.MINUTES);
        }
        return cacheObject;
    }

    /**
     * 分页获取
     *
     * @param pageDTO
     * @return
     */
    @Override
    public PageVo getPage(PageDTO pageDTO) {
        String cacheKey = BACKEND_BLOG_NOTICE_PAGE + pageDTO.getPageNum() + ":" + pageDTO.getPageSize();
        PageVo cacheObject = redisCache.getCacheObject(cacheKey);
        if (cacheObject == null) {
            Page<Notice> pageInfo = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
            page(pageInfo);
            PageVo pageVo = PageVoUtil.toPageVo(pageInfo, pageInfo.getRecords());
            cacheObject = pageVo;
            redisCache.setCacheObject(cacheKey, cacheObject, DEFAULT_EXPIRE, TimeUnit.MINUTES);
        }
        return cacheObject;
    }

    /**
     * ID获取
     *
     * @param noticeId
     * @return
     */
    @Override
    public Notice getNoticeById(Long noticeId) {
        return getById(noticeId);
    }

    /**
     * 添加
     *
     * @param notice
     * @return
     */
    @Override
    @FlushCache(dimKey = "notice")
    public boolean saveNotice(Notice notice) {
        notice.setCreateTime(new Date());
        notice.setCreateBy(SecurityUtils.getUsername());
        return save(notice);
    }

    /**
     * 根据ID修改
     *
     * @param notice
     * @return
     */
    @Override
    @FlushCache(dimKey = "notice")
    public boolean updateNoticeById(Notice notice) {
        return updateById(notice);
    }

    /**
     * 批量删除
     *
     * @param noticeIds
     * @return
     */
    @Override
    @FlushCache(dimKey = "notice")
    public boolean deleteNoticeBatchByIds(Long[] noticeIds) {
        return removeBatchByIds(Arrays.asList(noticeIds));

    }
}




