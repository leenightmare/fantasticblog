package cc.lijad.fantasticblog.utils;


import cc.lijad.fantasticblog.config.minio.MinioProp;
import cn.hutool.core.util.IdUtil;
import io.minio.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author 李坚达
 * @create 2022/12/17 21:00
 */

@Component
public class MinioUtil {
    private static MinioProp minioProp;

    private static MinioClient minioClient;

    private MinioUtil() {
    }

    @Autowired
    public void setMinioProp(MinioProp minioProp) {
        MinioUtil.minioProp = minioProp;
    }

    @Autowired
    public void setMinioClient(MinioClient minioClient) {
        MinioUtil.minioClient = minioClient;
    }

    private interface Constant {
        String DIR_TYPE_SINGLE = "1";
        String DIR_TYPE_ALL = "2";
    }

    /**
     * 文件上传
     */
    public static String putObject(MultipartFile file) throws Exception {
        String bucket = minioProp.getBucket();
        enableBucketExists(bucket);

        String originalFilename = file.getOriginalFilename();
        //2022/12/17/abc.png
        String uploadName = dateDir(Constant.DIR_TYPE_SINGLE) + "/" +
                IdUtil.fastSimpleUUID() + FileUtils.getFileSuffix(originalFilename);
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucket)
                .object(uploadName)
                .contentType(file.getContentType())
                .stream(file.getInputStream(), file.getSize(), PutObjectArgs.MIN_MULTIPART_SIZE)
                .build());
        String url = minioProp.getEndpoint() + "/" + minioProp.getBucket() + "/" + uploadName;
        return url;
    }


    /**
     * 获取预上传链接
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String getPresignedObjectUrl(String fileName) throws Exception {
        String uploadName = dateDir(Constant.DIR_TYPE_SINGLE) + "/" +
                IdUtil.fastSimpleUUID() + FileUtils.getFileSuffix(fileName);
        String presignedObjectUrl = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.PUT) //此处必须为PUT
                        .object(uploadName)
                        .expiry(30, TimeUnit.SECONDS)
                        .bucket(minioProp.getBucket())
                        .build());
        return presignedObjectUrl;
    }


    /**
     * 确保Bucket存在
     *
     * @param bucket
     * @throws Exception
     */
    private static void enableBucketExists(String bucket) throws Exception {
        if ("".equals(bucket) || bucket == null) bucket = "default";
        boolean isExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if (!isExists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }
    }


    /**
     * 构建一个日期目录字符
     *
     * @param type
     * @return
     */
    private static String dateDir(String type) {
        String fileDir = "";
        if (Constant.DIR_TYPE_SINGLE.equals(type)) {
            fileDir = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        } else if (Constant.DIR_TYPE_ALL.equals(type)) {
            fileDir = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
        return fileDir;
    }

}
