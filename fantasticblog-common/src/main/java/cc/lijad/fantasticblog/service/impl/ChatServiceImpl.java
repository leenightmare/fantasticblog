package cc.lijad.fantasticblog.service.impl;

import cc.lijad.fantasticblog.annotation.FlushCache;
import cc.lijad.fantasticblog.constant.RedisKey;
import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.domain.vo.PageVo;
import cc.lijad.fantasticblog.utils.PageVoUtil;
import cc.lijad.fantasticblog.utils.RedisCache;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cc.lijad.fantasticblog.domain.entity.Chat;
import cc.lijad.fantasticblog.service.ChatService;
import cc.lijad.fantasticblog.mapper.ChatMapper;
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
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat>
        implements ChatService {
    @Autowired
    private RedisCache redisCache;

    /**
     * @return
     */
    @Override
    public List<Chat> getList() {
        List<Chat> cacheObject = redisCache.getCacheObject(BLOG_CHAT_LIST);
        if (cacheObject == null) {
            cacheObject = list();
            redisCache.setCacheObject(BLOG_CHAT_LIST, cacheObject, DEFAULT_EXPIRE, TimeUnit.MINUTES);
        }
        return cacheObject;
    }

    /**
     * 获取聊天分页数据
     *
     * @param pageDTO
     * @return
     */
    @Override
    public PageVo getPageChat(PageDTO pageDTO) {
        String cacheKey = BLOG_CHAT_PAGE + pageDTO.getPageNum() + ":" + pageDTO.getPageSize();
        PageVo cacheObject = redisCache.getCacheObject(cacheKey);
        if (cacheObject == null) {
            Page<Chat> pageInfo = new Page(pageDTO.getPageNum(), pageDTO.getPageSize());
            page(pageInfo);
            PageVo pageVo = PageVoUtil.toPageVo(pageInfo, pageInfo.getRecords());
            cacheObject = pageVo;
            redisCache.setCacheObject(cacheKey, cacheObject, DEFAULT_EXPIRE, TimeUnit.MINUTES);
        }
        return cacheObject;
    }

    /**
     * 根据ID获取
     *
     * @param chatId
     * @return
     */
    @Override
    public Chat getChatById(Long chatId) {
        return getById(chatId);
    }

    /**
     * 添加
     *
     * @param chat
     * @return
     */
    @Override
    @FlushCache(dimKey = "chat")
    public boolean saveChat(Chat chat) {
        chat.setCreateTime(new Date());
        return save(chat);
    }

    /**
     * 修改
     *
     * @param chat
     * @return
     */
    @Override
    @FlushCache(dimKey = "chat")
    public boolean updateChatById(Chat chat) {
        return updateById(chat);
    }

    /**
     * 批量删除
     *
     * @param chatIds
     * @return
     */
    @Override
    @FlushCache(dimKey = "chat")
    public boolean deleteBatchChatByIds(Long[] chatIds) {
        return removeBatchByIds(Arrays.asList(chatIds));
    }
}




