package aphler.flydrop.config;

import aphler.ossClient.client.OSSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OSSConfig {

    @Value("${oss.host}")
    private String host;

    @Value("${oss.port}")
    private int port;

    @Value("${oss.access_key}")
    private String accessKey;

    @Bean
    public OSSClient ossClient() {
        return OSSClient.getOSSClient(host, port, accessKey);
    }

}
