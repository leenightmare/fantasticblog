package cc.lijad.fantasticblog.controller;

import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.domain.entity.Tag;
import cc.lijad.fantasticblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ljd
 * @create 2023/2/17 22:13
 */
@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 查所有
     *
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermi('blog:tag:list')")
    public R list() {
        return tagService.tagList();

    }


    @GetMapping("/page")
    @PreAuthorize("@ss.hasPermi('blog:tag:list')")
    public R page(@Valid PageDTO pageDTO) {
        return tagService.getPageTag(pageDTO);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPermi('blog:tag:remove')")
    public R delete(@PathVariable Long[] ids) {
        return tagService.deleteTag(ids);
    }

    /**
     * 添加
     *
     * @param tag
     * @return
     */
    @PostMapping
    @PreAuthorize("@ss.hasPermi('blog:tag:add')")
    public R save(@RequestBody Tag tag) {
        tagService.saveTag(tag);
        return R.success();
    }

    /**
     * 修改
     *
     * @param tag
     * @return
     */
    @PutMapping
    @PreAuthorize("@ss.hasPermi('blog:tag:edit')")
    public R update(@RequestBody Tag tag) {
        tagService.updateTag(tag);
        return R.success();
    }
}
