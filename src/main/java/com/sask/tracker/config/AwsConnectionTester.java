package com.sask.tracker.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AwsConnectionTester {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.bucketName}")
    private String bucketName;

    @PostConstruct
    public void testAwsConnection() {
        try {
            // Optionally, check if the bucket exists:
            if (amazonS3.doesBucketExistV2(bucketName)) {
                System.out.println("Bucket " + bucketName + " exists.");
            } else {
                System.out.println("Bucket " + bucketName + " does not exist.");
            }
            // List all buckets to confirm connection:
            List<Bucket> buckets = amazonS3.listBuckets();
            System.out.println("Successfully connected to AWS. Buckets found: ");
            for (Bucket bucket : buckets) {
                System.out.println("Bucket: " + bucket.getName());
            }
        } catch (Exception e) {
            System.out.println("Error connecting to AWS: " + e.getMessage());
        }
    }
}
