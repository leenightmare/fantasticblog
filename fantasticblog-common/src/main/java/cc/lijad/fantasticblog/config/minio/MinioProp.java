package cc.lijad.fantasticblog.config.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 李坚达
 * @create 2022/12/17 14:24
 */

@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProp {
    private String endpoint;
    private String accesskey;
    private String secretKey;
    private String bucket;
}
