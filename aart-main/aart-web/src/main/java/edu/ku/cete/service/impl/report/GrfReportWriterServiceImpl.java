package edu.ku.cete.service.impl.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.model.UploadResultFileMapper;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.service.report.GrfReportWriterService;

@Service
public class GrfReportWriterServiceImpl implements GrfReportWriterService {

	@Autowired
	private UploadResultFileMapper uploadResultFileMapper;

    @Autowired
    private StudentDao studentDao;
	
	@Override
	@Transactional
	public void generateScCodeExtract(Long assessmentProgramId, long contractingOrgId, int reportYear, Long userId,
			String stateCode, Long batchUploadId) {
		uploadResultFileMapper.generateScCodeExtract(assessmentProgramId, contractingOrgId, reportYear, userId,
				stateCode, batchUploadId);
	}

	@Override
	@Transactional
	public void generateStudentExitDetailsExtract(Long assessmentProgramId, long contractingOrgId, int reportYear,
			Long userId, Long batchUploadId) {
		Boolean isStateHaveSpecificExitCode = false;
		isStateHaveSpecificExitCode = studentDao.checkStateHaveSpecificExitCodes(contractingOrgId, assessmentProgramId, reportYear);
		uploadResultFileMapper.generateStudentExitDetailsExtract(assessmentProgramId, contractingOrgId, reportYear,
				userId, isStateHaveSpecificExitCode);
	}

}
