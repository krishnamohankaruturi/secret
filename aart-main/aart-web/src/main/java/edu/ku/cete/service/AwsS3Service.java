package edu.ku.cete.service;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.springframework.core.io.Resource;

import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.S3Object;

/**
 * Service wrapper for AWS S3 functionality.
 * 
 * @author craigshatswell_sta
 *
 */
public interface AwsS3Service {
	
	/**
	 * Get the object from S3 that is stored 
	 * at the key location.
	 * 
	 * @param key
	 * @return S3Object the object referenced by the key.
	 */
	public S3Object getObject(String key);

	/**
	 * Check S3 for the existence of an object at
	 * the key location.
	 * 
	 * @param key
	 * @return boolean indicating if the object exists in 
	 * S3 at the given key location.
	 */
	public boolean doesObjectExist(final String key);

	/**
	 * Delete the object at the key location.
	 * 
	 * @param key
	 */
	public void deleteObject(final String key);
	
	/**
	 * Delete the objects at the key locations.
	 * 
	 * @param keys
	 * @return int count of objects deleted.
	 */
	public int deleteObjects(final List<String> keys);
	
	/**
	 * Delete the objects at the key versions locations.
	 * 
	 * @param keyVersions
	 * @return int count of objects deleted.
	 */
	public int deleteObjectsUsingKeyVersions(final List<KeyVersion> keyVersions);

    /**
     * Lists all the keys matching the prefix in the given bucket.
     * @param keyPrefix
     *        String to match the pattern on keys.
     * @return keys found in s3 matching the key prefix
     */
	public List<KeyVersion> listAllKeysUsingPrefix(final String keyPrefix);

	/**
	 * Upload the given file to the location indicated by the 
	 * key parameter.  This is a synchronous upload.
	 * 
	 * @param key
	 * @param file
	 */
	public void synchMultipartUpload(final String key, final File file);

	/**
	 * Upload the file at the filePath to the location indicated by the 
	 * key parameter.  This is a synchronous upload.
	 * 
	 * @param key
	 * @param filePath
	 */
	public void synchMultipartUpload(final String key, final String filePath);
	
	/**
	 * Upload the file with the inputStream to the location indicated by the 
	 * key parameter.  This is a synchronous upload.
	 * 
	 * @param key
	 * @param inputStream
	 * @param contentType
	 */
	public void synchMultipartUpload(final String key, final InputStream inputStream, final String contentType);

	/**
	 * Get the object as a Spring InputStreamResource from S3 that is stored 
	 * at the key location.
	 * @param key
	 * @return
	 */
	public Resource getObjectAsInputStreamResource(final String key);
	
	URL generatePresignedUrlforStudentReport(String key);

}
