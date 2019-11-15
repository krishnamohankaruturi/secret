package edu.ku.cete.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;

import edu.ku.cete.service.AwsS3Service;

/**
 * Service wrapper implementation for AWS S3 functionality.
 * 
 * @author craigshatswell_sta
 *
 */
@Service
public class AwsS3ServiceImpl implements AwsS3Service {
	
	private final static Log logger = LogFactory.getLog(AwsS3ServiceImpl.class);

	@Value("${s3.datastore.bucket}")
	private String bucketName;

	@Value("${s3.default.region}")
	private String region;
	
	@Value("${s3.chunk.mb}")
	private Long chunkBase;
	
	private long chunkSize;

	private long threshold;
	
	private AmazonS3 s3Client;

	private TransferManager transferManager;
	
	/**
	 * Getter with lazy initialization.
	 * @return initialized AmazonS3 object
	 */
	private AmazonS3 getS3Client() {
		if (s3Client == null){
			logger.trace(String.format("Creating AmazonS3 Client - region: %s ", region));
			s3Client = AmazonS3ClientBuilder.standard().withRegion(region)
				.withCredentials(new DefaultAWSCredentialsProviderChain()).build();
		}
		return s3Client;
	}
	
	@Override
	public URL generatePresignedUrlforStudentReport(String key) {
		java.util.Date expiration = new java.util.Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60 * 60; //ttl for presigned url 
		expiration.setTime(expTimeMillis);

		// Generate the presigned URL.
		GeneratePresignedUrlRequest generatePresignedUrlRequest = 
			new GeneratePresignedUrlRequest(bucketName, key)
			.withMethod(HttpMethod.GET)
			.withExpiration(expiration);

		//get filename to displayed when the file is downloaded
		String[] patharray = key.split("/");
		String s3filename =  patharray[patharray.length-1];

		// override content disposition to download the file instead of displaying the pdf inline in the browser
		ResponseHeaderOverrides overrides = new ResponseHeaderOverrides().withContentDisposition("attachment; filename="+s3filename);
		generatePresignedUrlRequest.setResponseHeaders(overrides);

		URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

		return url;
	}

	/**
	 * Getter with lazy initialization.
	 * @return initialized TransferManager object
	 */
	private TransferManager getTransferManager() {
		if (transferManager == null) {
			logger.trace(String.format("Creating TransferManager - chunk size: %d, threshold: %d", getChunkSize(), getThreshold()));
			transferManager = TransferManagerBuilder.standard().withS3Client(getS3Client())
					.withMinimumUploadPartSize(getChunkSize()).withMultipartUploadThreshold(getThreshold()).build();
		}
		return transferManager;
	}
	
	/**
	 * Getter with lazy initialization.
	 * @return chunk size based on the configured chunk base.
	 */
	private Long getChunkSize() {
		if (chunkSize == 0){
			chunkSize = chunkBase * 1024 * 1024;
		}
		return chunkSize;
	}
	
	/**
	 * Getter with lazy initialization.
	 * @return threshold based on the chunk size
	 */
	private Long getThreshold() {
		if (threshold == 0){
			threshold = 3 * getChunkSize();
		}
		return threshold;
	}
	
	/*
	 * (non-Javadoc)
	 * @see edu.ku.cete.service.AwsS3Service#getObject(java.lang.String)
	 */
	@Override
	public S3Object getObject(final String key) {
		logger.trace(String.format("Entering getObject - key: %s.", key));
		S3Object object = getS3Client().getObject(bucketName, keySeparator(key));
		logger.trace("Leaving getObject.");
		return object;
	}
	/*
	 * (non-Javadoc)
	 * @see edu.ku.cete.service.AwsS3Service#getObjectAsInputStreamResource(java.lang.String)
	 */
	@Override
	public Resource getObjectAsInputStreamResource(final String key) {
		logger.trace(String.format("Entering getObjectAsInputStreamResource - key: %s.", key));
		Resource resource = new InputStreamResource(this.getObject(key).getObjectContent());
		logger.trace("Leaving getObjectAsInputStreamResource.");
		return resource;
	}
	
	/*
	 * (non-Javadoc)
	 * @see edu.ku.cete.service.AwsS3Service#doesObjectExist(java.lang.String)
	 */
	@Override
	public boolean doesObjectExist(final String key) {
		logger.trace(String.format("Entering doesObjectExist - key: %s.", key));
		boolean exists = getS3Client().doesObjectExist(bucketName, keySeparator(key));
		logger.trace("Leaving doesObjectExist.");
		return exists;
	}

	/*
	 * (non-Javadoc)
	 * @see edu.ku.cete.service.AwsS3Service#synchMultipartUpload(java.lang.String, java.io.File)
	 */
	@Override
	public void synchMultipartUpload(final String key, final File file) {
		logger.trace(String.format("Entering synchMultipartUpload - key: %s, file.name: %s.", key, file.getName()));
        PutObjectRequest request = new PutObjectRequest(bucketName, keySeparator(key), file);
        ObjectMetadata metadata = new ObjectMetadata();
        //determine the mime type from the file
        String mimeType = getMimeType(file);
        metadata.setContentType(mimeType);
        request.setMetadata(metadata);
        Upload upload = getTransferManager().upload(request);
        try {
			upload.waitForCompletion();
		} catch (InterruptedException e) {
			logger.error("Transfer to S3 was interrupted.", e);
		}
        logger.trace("Leaving synchMultipartUpload.");
	}

	/*
	 * (non-Javadoc)
	 * @see edu.ku.cete.service.AwsS3Service#synchMultipartUpload(java.lang.String, java.lang.String)
	 */
	@Override
	public void synchMultipartUpload(final String key, final String filePath) {
		logger.trace(String.format("Entering synchMultipartUpload - key: %s, filePath: %s.", key, filePath));
		File file = new File(filePath);
		synchMultipartUpload(keySeparator(key), file);
	}

	/*
	 * (non-Javadoc)
	 * @see edu.ku.cete.service.AwsS3Service#synchMultipartUpload(java.lang.String, java.io.InputStream, java.lang.String)
	 */
	@Override
	public void synchMultipartUpload(final String key, final InputStream inputStream, final String contentType) {
		logger.trace(String.format("Entering synchMultipartUpload - key: %s, contentType: %s.", key, contentType));
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        PutObjectRequest request = new PutObjectRequest(bucketName, keySeparator(key), inputStream, metadata);
        Upload upload = getTransferManager().upload(request);
        try {
			upload.waitForCompletion();
		} catch (InterruptedException e) {
			logger.error("Transfer to S3 was interrupted.", e);
		}
        logger.trace("Leaving synchMultipartUpload.");
	}
	
	/*
	 * (non-Javadoc)
	 * @see edu.ku.cete.service.AwsS3Service#deleteObject(java.lang.String)
	 */
	@Override
	public void deleteObject(final String key){
		logger.trace(String.format("Entering deleteObject - key: %s.", key));
		getS3Client().deleteObject(bucketName, keySeparator(key));
		logger.trace("Leaving deleteObject.");
	}
	
	/*
	 * (non-Javadoc)
	 * @see edu.ku.cete.service.AwsS3Service#deleteObjects(java.util.List)
	 */
	@Override
	public int deleteObjects(final List<String> keys){
		logger.trace("Entering deleteObjects.");
		//create key versions from the list of keys
		List<KeyVersion> keyVersions = new ArrayList<>();
		for (String key : keys){
			keyVersions.add(new KeyVersion(key));
		}
		int count = deleteObjectsUsingKeyVersions(keyVersions);
		logger.trace("Leaving deleteObjects.");
		return count; 
	}
	
	/*
	 * (non-Javadoc)
	 * @see edu.ku.cete.service.AwsS3Service#deleteObjectsUsingKeyVersions(java.util.List)
	 */
	@Override
	public int deleteObjectsUsingKeyVersions(final List<KeyVersion> keyVersions){
		logger.trace("Entering deleteObjectsUsingKeyVersions.");
        DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest(bucketName)
                .withKeys(keyVersions)
                .withQuiet(false);

		DeleteObjectsResult delObjRes = getS3Client().deleteObjects(multiObjectDeleteRequest);
		int count = delObjRes.getDeletedObjects().size();
		logger.trace("Leaving deleteObjectsUsingKeyVersions.");
		//return a count of objects deleted
		return count;
	}
	
	/*
	 * (non-Javadoc)
	 * @see edu.ku.cete.service.AwsS3Service#listAllKeysUsingPrefix(java.lang.String)
	 */
    @Override
    public List<KeyVersion> listAllKeysUsingPrefix(final String keyPrefix){
    	logger.trace(String.format("Entering listAllKeysUsingPrefix - keyPrefix: %s.", keyPrefix));
        List<KeyVersion> foundKeys = new ArrayList<KeyVersion>();
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(bucketName).withPrefix(keySeparator(keyPrefix));
        ObjectListing objectListing = null;
        do {
            objectListing = getS3Client().listObjects(listObjectsRequest);
            for(S3ObjectSummary objectSummary : objectListing.getObjectSummaries()){
                foundKeys.add(new KeyVersion(objectSummary.getKey()));
            }
            listObjectsRequest.setMarker(objectListing.getNextMarker());
        } while(objectListing.isTruncated());
        logger.trace("Leaving listAllKeysUsingPrefix.");
        return foundKeys;
    }
	
	/**
	 * File separator is not always the same as a key separator (/). If running
	 * on a Windows OS, then the file separator, if used in the key, will not
	 * resolve the object requested.
	 * 
	 * @param key
	 * @return key with forward slash as a separator
	 */
	private String keySeparator(final String key) {
		return key.replace("\\", "/");
	}
	
	/**
	 * Return the mime type of the file.
	 * 
	 * @param file
	 * @return mimeType
	 */
	private String getMimeType(File file) {
		String mimeType = "text/plain";
		try {
			//fix the deficiency of the File.probeContentType on finding 
			//the correct csv mime type
			if (file.getAbsolutePath().endsWith(".csv")){
				mimeType="text/csv";
			}else{
				mimeType = Files.probeContentType(file.toPath());
			}
		} catch (IOException e) {
			logger.error("Exception occurred while discovering mime type.", e);
		}
		return mimeType;
	}
}
