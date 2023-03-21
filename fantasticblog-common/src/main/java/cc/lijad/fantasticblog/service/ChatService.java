package cc.lijad.fantasticblog.service;

import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.domain.entity.Chat;
import cc.lijad.fantasticblog.domain.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface ChatService extends IService<Chat> {

    List<Chat> getList();

    PageVo getPageChat(PageDTO pageDTO);

    Chat getChatById(Long chatId);

    boolean saveChat(Chat chat);

    boolean updateChatById(Chat chat);

    boolean deleteBatchChatByIds(Long[] chatIds);
}
