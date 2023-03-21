package cc.lijad.fantasticblog.service;

import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface CategoryService extends IService<Category> {

    R getPageCategory(PageDTO pageDTO);

    R deleteCategory(Long[] ids);

    List<Category> getList();

    boolean saveCategory(Category category);

    boolean updateCategory(Category category);
}
