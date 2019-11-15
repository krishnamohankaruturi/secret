package edu.ku.cete.service.impl.report;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.enrollment.EnrollmentRecord;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.EnrollmentService;

@Service
public class UnEnrollmentUploadWriterProcessServiceImpl implements BatchUploadWriterService {

	final static Log logger = LogFactory.getLog(UnEnrollmentUploadWriterProcessServiceImpl.class);

	@Autowired
	StudentDao studentDao;

	@Autowired
	private EnrollmentService enrollmentService;

	@Override
	public void writerProcess(List<? extends Object> objects) {
		logger.debug(" UnEnrollment writter ");
		for (Object object : objects) {
			EnrollmentRecord enrollmentRecord = (EnrollmentRecord) object;
			exitEnrollmentUpload(enrollmentRecord);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private final void exitEnrollmentUpload(EnrollmentRecord enrollmentRecord) {
		logger.debug("UnEnrollment Upload Writer");

		/*
		String stateStudentIdent = enrollmentRecord.getStudent().getStateStudentIdentifier();
		enrollmentService.processExit(enrollmentRecord.getAttendanceSchoolProgramIdentifier(),
				contractingOrganizationTree, stateStudentIdent, enrollmentRecord.getExitWithdrawalType(),
				enrollmentRecord.getExitWithdrawalDate(), enrollmentRecord.getCurrentSchoolYear(),
				userDetails.getUser(), enrollmentRecord.getAttendanceSchoolId());
		*/
	}

}
