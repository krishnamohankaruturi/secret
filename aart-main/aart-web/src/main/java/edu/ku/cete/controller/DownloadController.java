package edu.ku.cete.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import edu.ku.cete.service.AwsS3Service;

@Controller
public class DownloadController {
	
	@Autowired
	private AwsS3Service s3;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DownloadController.class);
	
	public final void download(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
		download(request, response, path, null);
	}
	
	public final void download(HttpServletRequest request, HttpServletResponse response, String path, String fileName) throws IOException {
		if(fileName == null || fileName.trim().length() == 0) {
			fileName = Paths.get(path).getFileName().toString();
		}
		//TODO can we implement async with the transfermanager
		//This will need to be addressed in a later release.
		//I am not sure how this can work.  We would need to 
		//have a way to know when the file download is complete
		//and then delete the local file transferred from s3.
		/*boolean sendfileSupport = Boolean.TRUE.equals(request.getAttribute("org.apache.tomcat.sendfile.support"));
		
		if (sendfileSupport) {
			Path file = Paths.get(path);
			long fileSize = Files.size(file);
			response.setContentType("application/force-download");
			response.setContentLength((int) fileSize);
			response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			request.setAttribute("org.apache.tomcat.sendfile.filename", path);
			request.setAttribute("org.apache.tomcat.sendfile.start", 0L);
			request.setAttribute("org.apache.tomcat.sendfile.end", fileSize);
			response.flushBuffer();
		} else {
		*/		
			S3Object s3Object = s3.getObject(path);
			S3ObjectInputStream stream = s3Object.getObjectContent();
			if (stream.available() != 0){
				try {
					long fileSize = s3Object.getObjectMetadata().getContentLength();
					response.setContentType("application/force-download");
					response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
					response.addHeader("Content-Length", Long.toString(fileSize));
					IOUtils.copy(stream, response.getOutputStream());
					response.flushBuffer();
				} catch (FileNotFoundException e) {
					LOGGER.error("Download file not found. " + path, e);
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				} catch (IOException e) {
					LOGGER.error("IOException when trying to download " + path, e);
				} finally {
					if (stream != null) {
						try {
							stream.close();
						} catch (IOException e) {
							LOGGER.error("ignore IOException closing: ",e);
						}
					}
				}
			}
		//}
	}
}