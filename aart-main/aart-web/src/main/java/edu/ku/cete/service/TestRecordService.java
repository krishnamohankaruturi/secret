package edu.ku.cete.service;

import java.io.Serializable;

import edu.ku.cete.web.ViewStudentDTO;

/**
 * @author sudhansu.b
 */
public interface TestRecordService extends Serializable {	
	void createTestRecord(ViewStudentDTO student, Long testTypeId,Long testSubjectId);
}
