package cc.lijad.fantasticblog.controller;

import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.domain.entity.Category;
import cc.lijad.fantasticblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ljd
 * @create 2023/2/17 22:11
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermi('blog:category:list')")
    public R list() {
        List<Category> categoryList = categoryService.getList();
        return R.success(categoryList);
    }

    @GetMapping("/page")
    @PreAuthorize("@ss.hasPermi('blog:category:list')")
    public R page(@Valid PageDTO pageDTO) {
        return categoryService.getPageCategory(pageDTO);
    }

    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPermi('blog:category:remove')")
    public R delete(@PathVariable Long[] ids) {
        return categoryService.deleteCategory(ids);
    }

    /**
     * 添加
     *
     * @param category
     * @return
     */
    @PostMapping
    @PreAuthorize("@ss.hasPermi('blog:category:add')")
    public R save(@RequestBody Category category) {
//        categoryService.save(category);
        categoryService.saveCategory(category);
        return R.success();
    }


    /**
     * 修改
     *
     * @param category
     * @return
     */
    @PutMapping
    @PreAuthorize("@ss.hasPermi('blog:category:edit')")
    public R update(@RequestBody Category category) {
//        categoryService.updateById(category);
        categoryService.updateCategory(category);
        return R.success();
    }
}
