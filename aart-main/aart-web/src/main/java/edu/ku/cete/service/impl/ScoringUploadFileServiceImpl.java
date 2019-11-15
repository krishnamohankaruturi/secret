package edu.ku.cete.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.domain.ScoringUploadFile;
import edu.ku.cete.model.ScoringUploadMapper;
import edu.ku.cete.service.ScoringUploadFileService;

/**
 * Added By Sudhansu.b
 * Feature: f430
 * Scoring Upload
 */
@Service
public class ScoringUploadFileServiceImpl implements ScoringUploadFileService{

	@Autowired
	private ScoringUploadMapper scoringUploadMapper;
	
	@Override
	public ScoringUploadFile getUplodFileDetails(Long uploadFileId) {		
		return scoringUploadMapper.getUplodFileDetails(uploadFileId);
	}

}
