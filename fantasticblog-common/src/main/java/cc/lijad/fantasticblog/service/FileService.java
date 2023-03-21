package cc.lijad.fantasticblog.service;



import cc.lijad.fantasticblog.domain.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 李坚达
 * @create 2022/12/17 15:00
 */
public interface FileService {
    R upload(MultipartFile file);

    R getPreUploadUrl(String fileName);
}
