package cc.lijad.fantasticblog.controller.system;

import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.domain.entity.Chat;
import cc.lijad.fantasticblog.domain.vo.PageVo;
import cc.lijad.fantasticblog.service.ChatService;
import cc.lijad.fantasticblog.utils.PageVoUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author ljd
 * @create 2023/3/18 17:49
 */
@RestController
@RequestMapping("/system/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    /**
     * 获取列表展示
     *
     * @param pageDTO
     * @return
     */
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPermi('system:chat:list')")
    public R page(@Valid PageDTO pageDTO) {
        PageVo pageVo = chatService.getPageChat(pageDTO);
        return R.success(pageVo);
    }

    /**
     * ID获取
     *
     * @param chatId
     * @return
     */
    @GetMapping("/{chatId}")
    @PreAuthorize("@ss.hasPermi('system:chat:query')")
    public R info(@PathVariable("chatId") Long chatId) {
        Chat chat = chatService.getChatById(chatId);
//        Chat chat = chatService.getById(chatId);
        return R.success(chat);
    }

    /**
     * 添加
     *
     * @param chat
     * @return
     */
    @PostMapping
    @PreAuthorize("@ss.hasPermi('system:chat:add')")
    public R add(@RequestBody Chat chat) {
        chatService.saveChat(chat);
        return R.success();
    }


    /**
     * 修改
     *
     * @param chat
     * @return
     */
    @PutMapping
    @PreAuthorize("@ss.hasPermi('system:chat:edit')")
    public R update(@RequestBody Chat chat) {
        chatService.updateChatById(chat);
        return R.success();
    }

    /**
     * 删除
     *
     * @param chatIds
     * @return
     */
    @DeleteMapping("/{chatIds}")
    @PreAuthorize("@ss.hasPermi('system:chat:remove')")
    public R delete(@PathVariable Long[] chatIds) {
        chatService.deleteBatchChatByIds(chatIds);
//        chatService.removeBatchByIds(Arrays.asList(chatIds));
        return R.success();
    }
}
