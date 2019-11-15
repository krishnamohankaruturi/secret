/**
 * 
 */
package edu.ku.cete.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ghost4j.converter.PSConverter;
import org.ghost4j.document.Document;
import org.ghost4j.document.DocumentException;
import org.ghost4j.document.PDFDocument;
import org.ghost4j.document.PSDocument;
import org.ghost4j.modifier.SafeAppenderModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.ku.cete.service.AwsS3Service;

@Component
public class PdfToPostScript {

	/**
	 * logger.
	 */
	private static final Log LOGGER = LogFactory.getLog(PdfToPostScript.class);
	
	@Autowired
	private AwsS3Service s3;

	public void convertMultiplePdfToPS(List<String> pdfs, String outFileName) throws Exception, DocumentException, IOException {
		if(CollectionUtils.isNotEmpty(pdfs)){
			LOGGER.info("Started PdfToPostScript :: convertMultiplePdfToPS");
			LOGGER.info("Total No of Reports =" + pdfs.size() );
			PSConverter converter = new PSConverter();
			converter.setDevice(PSConverter.OPTION_DEVICE_PS2WRITE);
			PDFDocument pdfDocument = null;
			FileOutputStream fos = null;
			File psFile = null;
			if (pdfs.size() > 0) {
				try {
					File pdf = new File(pdfs.get(0));
					pdfDocument = new PDFDocument();
					pdfDocument.load(pdf);
					psFile = new File(outFileName);
					fos = new FileOutputStream(psFile);
					converter.convert(pdfDocument, fos);
					pdfs.remove(pdf.getPath());
				} catch (Exception e) {
					LOGGER.error("PdfToPostScript::convertMultiplePdfToPS : ",e);
					throw e;
				} finally {
					try {
						if (fos != null)
							fos.close();
					} catch (IOException e) {
						e.printStackTrace();
						throw e;
					}
				}
				if(CollectionUtils.isNotEmpty(pdfs)){
					for (String file : pdfs) {
						addpendToPSFile(psFile, new File(file));
					}
				}
			}
		}else{
			LOGGER.info("PdfToPostScript :: convertMultiplePdfToPS - No files found to process");
		}
	}

	public boolean convertPdfToPS(File pdfFile, String fileName) throws Exception, DocumentException, IOException {
		boolean successFlag = false;
		if(pdfFile != null && pdfFile.length() > 0){
			LOGGER.info("Started PdfToPostScript :: convertMultiplePdfToPS - FileName :- " + fileName);
			PSConverter converter = new PSConverter();
			converter.setDevice(PSConverter.OPTION_DEVICE_PS2WRITE);
			PDFDocument pdfDocument = null;
			FileOutputStream fos = null;
			File psFile = null;
			try {
					pdfDocument = new PDFDocument();
					pdfDocument.load(pdfFile);
					psFile = new File(fileName);
					fos = new FileOutputStream(psFile);
					converter.convert(pdfDocument, fos);
					//send PS file to S3
					s3.synchMultipartUpload(fileName, psFile);
					//delete PS File
					FileUtils.deleteQuietly(psFile);
					successFlag = true;
			} catch (Exception e) {
					LOGGER.error("PdfToPostScript::convertPdfToPS : ",e);
					throw e;
			} finally {
					try {
						if (fos != null)
							fos.close();
					} catch (IOException e) {
						e.printStackTrace();
						throw e;
					}
			}
		}else{
			LOGGER.info("PdfToPostScript :: convertMultiplePdfToPS - No files found to process");
		}
		return successFlag;
	}
	
	private void addpendToPSFile(File psFile, File pdfFile) throws Exception {
		LOGGER.info("Appending file = " + pdfFile.getPath() + " to " + psFile.getPath());
		PDFDocument pdfDocument = null;
		PSDocument psDocument = null;
		FileOutputStream fos = null;
		try {
			psDocument = new PSDocument();
			psDocument.load(psFile);
			pdfDocument = new PDFDocument();
			pdfDocument.load(pdfFile);
			SafeAppenderModifier modifier = new SafeAppenderModifier();
			Map<String, Serializable> parameters = new HashMap<String, Serializable>();
			parameters.put(SafeAppenderModifier.PARAMETER_APPEND_DOCUMENT, pdfDocument);
			Document result = modifier.modify(psDocument, parameters);
			fos = new FileOutputStream(psFile);
			result.write(fos);
		} catch (Exception e) {
			LOGGER.error("PdfToPostScript::addpendToPSFile : ",e);
			throw e;
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				LOGGER.error("PdfToPostScript::addpendToPSFile : ",e);
				throw e;
			}
		}
	}
}
