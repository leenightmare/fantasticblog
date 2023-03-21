package cc.lijad.fantasticblog.controller;

import cc.lijad.fantasticblog.domain.R;
import cc.lijad.fantasticblog.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ljd
 * @create 2023/3/18 0:42
 */
@RestController
@RequestMapping("file")
public class FileController {
    @Autowired
    private FileService fileService;


    @PostMapping("/upload")
    @PreAuthorize("@ss.hasPermi('blog:file:upload')")
    public R fileUpload(@RequestParam(name = "file", required = false) MultipartFile file) {
        return fileService.upload(file);
    }

    @GetMapping("/preUpload")
    @PreAuthorize("@ss.hasPermi('blog:file:upload')")
    public R getPreUploadUrl(String fileName) {
        return fileService.getPreUploadUrl(fileName);
    }
}
