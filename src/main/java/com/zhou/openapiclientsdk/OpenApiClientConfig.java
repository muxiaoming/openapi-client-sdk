package com.zhou.openapiclientsdk;

import com.zhou.openapiclientsdk.client.OpenApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: code muxiaoming
 * @DateCreatedIn: 2022/12/4 15:54
 * @Description:
 */
@Configuration
//这是在yml配置文件中配置的前缀
//如:
/*
zhou:
  client:
    accessKey:xx
    secretKey:xx
    */
@ConfigurationProperties(prefix = "zhou.client")
@Data
//@ComponentScans
// 注意两个注解的区别,个人理解,一个是给类打卡需要scan的标记,而ComponentScans是得到所有被scan标记的后扫描得到的类
@ComponentScan
public class OpenApiClientConfig {

    /**
     * 属性的个数应该就是可配置的配置数
     */
    private String accessKey;

    private String secretKey;

    @Bean
    public OpenApiClient openApiClient() {
        return new OpenApiClient(accessKey, secretKey);
    }
}
