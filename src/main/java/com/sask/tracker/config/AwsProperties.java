package com.sask.tracker.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aws")
public class AwsProperties {private String accessKeyId;
private String secretAccessKey;
private String region;
private String bucketName;
private String sender; 

// Getters and Setters
public String getAccessKeyId() {
    return accessKeyId;
}
public void setAccessKeyId(String accessKeyId) {
    this.accessKeyId = accessKeyId;
}
public String getSecretAccessKey() {
    return secretAccessKey;
}
public void setSecretAccessKey(String secretAccessKey) {
    this.secretAccessKey = secretAccessKey;
}
public String getRegion() {
    return region;
}
public void setRegion(String region) {
    this.region = region;
}
public String getBucketName() {
	return bucketName;
}
public void setBucketName(String bucketName) {
	this.bucketName = bucketName;
}
public String getSender() {
	return sender;
}
public void setSender(String sender) {
	this.sender = sender;
}



}
