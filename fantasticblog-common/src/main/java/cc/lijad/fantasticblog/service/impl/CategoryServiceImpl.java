package cc.lijad.fantasticblog.service.impl;

import cc.lijad.fantasticblog.annotation.FlushCache;
import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.domain.vo.PageVo;
import cc.lijad.fantasticblog.utils.PageVoUtil;
import cc.lijad.fantasticblog.utils.RedisCache;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cc.lijad.fantasticblog.domain.entity.Category;
import cc.lijad.fantasticblog.service.CategoryService;
import cc.lijad.fantasticblog.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static cc.lijad.fantasticblog.constant.RedisKey.*;

/**
 *
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {

    @Autowired
    private RedisCache redisCache;

    /**
     * 分页获取分类
     *
     * @param pageDTO
     * @return
     */
    @Override
    public R getPageCategory(PageDTO pageDTO) {
        String cacheKey = BACKEND_CATEGORY_PAGE + pageDTO.getPageNum() + ":" + pageDTO.getPageSize();
        PageVo cacheObject = redisCache.getCacheObject(cacheKey);
        if (cacheObject == null) {
            Page<Category> pageInfo = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
            page(pageInfo);
            cacheObject = PageVoUtil.toPageVo(pageInfo, pageInfo.getRecords());
            redisCache.setCacheObject(cacheKey, cacheObject, DEFAULT_EXPIRE, TimeUnit.MINUTES);
        }
        return R.success(cacheObject);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Override
    @FlushCache(dimKey = "category")
    public R deleteCategory(Long[] ids) {
        //TODO 其他文章引用时，不能删除
        removeBatchByIds(Arrays.asList(ids));
        return R.success();
    }

    @Override
    public List<Category> getList() {
        List<Category> cacheObject = redisCache.getCacheObject(BACKEND_CATEGORY_LIST);
        if (cacheObject == null) {
            cacheObject = list();
            redisCache.setCacheObject(BACKEND_CATEGORY_LIST, cacheObject, DEFAULT_EXPIRE, TimeUnit.MINUTES);
        }
        return cacheObject;
    }

    /**
     * 添加分类
     *
     * @param category
     */
    @Override
    @FlushCache(dimKey = "category")
    public boolean saveCategory(Category category) {
        return save(category);
    }

    @Override
    @FlushCache(dimKey = "category")
    public boolean updateCategory(Category category) {
        return updateById(category);
    }
}





