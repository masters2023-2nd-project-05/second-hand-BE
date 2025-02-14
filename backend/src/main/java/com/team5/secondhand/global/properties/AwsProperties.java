package com.team5.secondhand.global.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "aws.s3")
public class AwsProperties {

    private String bucket;
    private long maxSize;
    private int minFileCount;
    private int maxFileCount;

}
