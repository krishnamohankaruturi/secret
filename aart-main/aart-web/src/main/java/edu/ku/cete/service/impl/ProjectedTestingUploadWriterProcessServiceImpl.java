package edu.ku.cete.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.domain.ProjectedTesting;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.ProjectedTestingService;
/**
 * Added By Sudhansu 
 * Feature: f183
 * Projected Testing
 */
@Service
public class ProjectedTestingUploadWriterProcessServiceImpl implements BatchUploadWriterService{
    
	final static Log logger = LogFactory.getLog(ProjectedTestingUploadWriterProcessServiceImpl.class);
	
	@Autowired
	ProjectedTestingService projectedTestingService;
	
	@Override
	public void writerProcess(List<? extends Object> objects) {
		logger.debug(" Enrollment writter ");
		for( Object object : objects){
			ProjectedTesting projectedTesting = (ProjectedTesting) object;
			projectedTestingService.addorUpdateProjectedTesting(projectedTesting);
		}  
		
	}

}
