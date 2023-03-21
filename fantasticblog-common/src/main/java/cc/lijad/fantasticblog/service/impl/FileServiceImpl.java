package cc.lijad.fantasticblog.service.impl;


import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.service.FileService;
import cc.lijad.fantasticblog.utils.MinioUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 李坚达
 * @create 2022/12/17 15:02
 */
@Service
public class FileServiceImpl implements FileService {


    @Override
    public R upload(MultipartFile file) {
        if (file == null || file.isEmpty() || file.getSize() <= 0) return R.error("上传失败，文件不能为空");
        try {
            String url = MinioUtil.putObject(file);
            return R.success().put("url", url);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

    @Override
    public R getPreUploadUrl(String fileName) {
        if ("".equals(fileName) || fileName == null) return R.error("文件名为空");
        try {
            String presignedObjectUrl = MinioUtil.getPresignedObjectUrl(fileName);
            return R.success().put("uploadUrl", presignedObjectUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }

    }

}
