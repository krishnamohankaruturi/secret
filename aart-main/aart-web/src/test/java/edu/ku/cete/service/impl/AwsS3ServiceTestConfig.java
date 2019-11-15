package edu.ku.cete.service.impl;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import edu.ku.cete.service.AwsS3Service;

public class AwsS3ServiceTestConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() throws Exception {
        final PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        Properties properties = new Properties();

        properties.setProperty("s3.datastore.bucket", "kite-ep-data-store-qa");
        properties.setProperty("s3.default.region", "us-east-1");
        properties.setProperty("s3.chunk.mb", "5");
        properties.setProperty("accessKey", "AKIAI4ICKASZOM4MAXNA");
        properties.setProperty("secretKey", "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");
        
        pspc.setProperties(properties);
        return pspc;
    }
    
    @Bean
    public AwsS3Service getAwsS3Service(){
    	return new AwsS3ServiceImpl();
    }
}
