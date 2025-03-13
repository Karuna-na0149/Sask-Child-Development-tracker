package com.sask.tracker.service;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;

@Service
public class S3Service {

	@Autowired
	private AmazonS3 s3client;
	
	public void uploadFile(String bucketName, String keyName, File file) {
		s3client.putObject(bucketName, keyName, file);
	}
}
