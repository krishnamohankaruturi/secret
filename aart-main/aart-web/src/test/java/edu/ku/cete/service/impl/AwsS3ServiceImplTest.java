package edu.ku.cete.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.ku.cete.service.AwsS3Service;


@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes = AwsS3ServiceTestConfig.class)
public class AwsS3ServiceImplTest {

	@Autowired
	private AwsS3Service s3;
	
	@Test
	public void testSynchMultipartUploadCSV() throws IOException {

		File emptyCSV = File.createTempFile("test/tmp/empty", ".csv");
		String key = "test/tmp/empty.csv";
		s3.synchMultipartUpload(key, emptyCSV);
		FileUtils.deleteQuietly(emptyCSV);
		assertFalse(emptyCSV.exists());
		assertTrue(s3.doesObjectExist(key));
		s3.deleteObject(key);
		assertFalse(s3.doesObjectExist(key));
	}
	
	@Test
	public void testSynchMultipartUploadPDF() throws IOException {

		File emptyPDF = File.createTempFile("test/tmp/empty", ".pdf");
		String key = "test/tmp/empty.pdf";
		s3.synchMultipartUpload(key, emptyPDF);
		FileUtils.deleteQuietly(emptyPDF);
		assertFalse(emptyPDF.exists());
		assertTrue(s3.doesObjectExist(key));
		s3.deleteObject(key);
		assertFalse(s3.doesObjectExist(key));
	}
	
	@Test
	public void testSynchMultipartUploadExcel() throws IOException {

		File emptyXLSX = File.createTempFile("test/tmp/empty", ".xlsx");
		String key = "test/tmp/empty.xlsx";
		s3.synchMultipartUpload(key, emptyXLSX);
		FileUtils.deleteQuietly(emptyXLSX);
		assertFalse(emptyXLSX.exists());
		assertTrue(s3.doesObjectExist(key));
		s3.deleteObject(key);
		assertFalse(s3.doesObjectExist(key));
	}
	
	@Test
	public void testSynchMultipartUploadCsvInputStream() throws IOException {

		File emptyCSV = File.createTempFile("test/tmp/empty", ".csv");
		String key = "test/tmp/empty.csv";
		InputStream stream = FileUtils.openInputStream(emptyCSV);
		s3.synchMultipartUpload(key, stream, "text/csv");
		stream.close();
		FileUtils.deleteQuietly(emptyCSV);
		assertFalse(emptyCSV.exists());
		assertTrue(s3.doesObjectExist(key));
		s3.deleteObject(key);
		assertFalse(s3.doesObjectExist(key));
	}
}
