package edu.ku.cete.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.domain.UploadedScoringRecord;
import edu.ku.cete.model.CcqScoreItemMapper;
import edu.ku.cete.model.CcqScoreMapper;
import edu.ku.cete.model.RubricCategoryDao;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.CcqScoreService;
import edu.ku.cete.service.ScoringAssignmentServices;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.impl.report.EnrollmentUploadWriterProcessServiceImpl;

/**
 * Added By Sudhansu.b
 * Feature: f430
 * Scoring Upload
 */
@Service
public class ScoringUploadWriterProcessServiceImpl implements BatchUploadWriterService{
	
	final static Log logger = LogFactory.getLog(EnrollmentUploadWriterProcessServiceImpl.class);
	
	@Autowired
	private ScoringAssignmentServices scoringAssignmentServices;
	
	@Autowired
	private RubricCategoryDao rubricCategoryDao;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CcqScoreService ccqScoreTestService;
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private CcqScoreMapper ccqScoreMapper;
	
	@Autowired
	private CcqScoreItemMapper ccqScoreItemMapper;
	
	
	@Override
	public void writerProcess(List<? extends Object> objects) {
		logger.debug(" Enrollment writter ");
		for( Object object : objects){
			UploadedScoringRecord uploadedScoringRecord= (UploadedScoringRecord) object;
			//addorUpdateScoringRecord(uploadedScoringRecord);
			scoringAssignmentServices.soreTest(uploadedScoringRecord.getStudentsScores());
			scoringAssignmentServices.removeStudentsTestScore(uploadedScoringRecord.getRemovedStudentsScores());
		}  
		
	}
}
