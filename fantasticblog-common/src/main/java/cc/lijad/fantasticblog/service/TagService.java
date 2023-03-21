package cc.lijad.fantasticblog.service;

import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.domain.dto.PageDTO;
import cc.lijad.fantasticblog.domain.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface TagService extends IService<Tag> {

    R tagList();

    R deleteTag(Long[] ids);

    R getPageTag(PageDTO pageDTO);

    boolean saveTag(Tag tag);

    boolean updateTag(Tag tag);
}
