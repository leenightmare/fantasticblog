package cc.lijad.fantasticblog.service.impl;

import cc.lijad.fantasticblog.annotation.FlushCache;
import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.domain.vo.PageVo;
import cc.lijad.fantasticblog.utils.PageVoUtil;
import cc.lijad.fantasticblog.utils.RedisCache;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cc.lijad.fantasticblog.domain.entity.Tag;
import cc.lijad.fantasticblog.service.TagService;
import cc.lijad.fantasticblog.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static cc.lijad.fantasticblog.constant.RedisKey.DEFAULT_EXPIRE;
import static cc.lijad.fantasticblog.constant.RedisKey.FRONTEND_TAG_LIST;

/**
 *
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
        implements TagService {

    @Autowired
    private RedisCache redisCache;


    @Override
    public R tagList() {
        List<Tag> cacheList = redisCache.getCacheObject(FRONTEND_TAG_LIST);
        if (cacheList == null) {
            //TODO 热门标签
            cacheList = list();
            redisCache.setCacheObject(FRONTEND_TAG_LIST, cacheList, DEFAULT_EXPIRE, TimeUnit.MINUTES);
        }
        return R.success(cacheList);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Override
    @FlushCache(dimKey = "tag")
    public R deleteTag(Long[] ids) {
        //TODO 其他文章引用时，不能删除
        removeBatchByIds(Arrays.asList(ids));
        return R.success();
    }

    /**
     * 分页查询
     *
     * @param pageDTO
     * @return
     */
    @Override
    public R getPageTag(PageDTO pageDTO) {
        Page<Tag> pageInfo = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
        page(pageInfo);
        PageVo pageVo = PageVoUtil.toPageVo(pageInfo, pageInfo.getRecords());
        return R.success(pageVo);
    }

    @Override
    @FlushCache(dimKey = "tag")
    public boolean saveTag(Tag tag) {
        return save(tag);
    }

    @Override
    @FlushCache(dimKey = "tag")
    public boolean updateTag(Tag tag) {
        return updateById(tag);
    }

}




