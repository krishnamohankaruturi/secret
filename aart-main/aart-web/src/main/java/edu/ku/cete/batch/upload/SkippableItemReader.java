package edu.ku.cete.batch.upload;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.service.AwsS3Service;

public class SkippableItemReader<T> extends FlatFileItemReader<T> {
	private boolean headerError = false;
	/*
	 * This property is added to allow the setting of the resource
	 * on the FlatFileItemReader after getting from S3.
	 */
	private String fileName;
	
	@Autowired
	private AwsS3Service s3;

	protected void doOpen() throws Exception {
		try {
			super.doOpen();
		} catch (InvalidHeaderException e) {
			headerError = true;
		}
	}

	protected T doRead() throws Exception {
		if (headerError)
			return null;
		return super.doRead();
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
		//when setting the fileName - get the resource with that name and set as well
		setResource(s3.getObjectAsInputStreamResource(this.fileName));
	}
}