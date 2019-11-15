package edu.ku.cete.service.impl.report;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.model.amp.AmpDataExtractDao;
import edu.ku.cete.service.report.AmpDataExtractService;
import edu.ku.cete.web.AmpExtractStudentProfileItemAttributeDTO;
import edu.ku.cete.web.AmpExtractStudentSujectSectionItemCountDTO;
import edu.ku.cete.web.AmpStudentDataExtractDTO;
import edu.ku.cete.web.StudentGradesTestedDTO;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class AmpDataExtractServiceImpl implements AmpDataExtractService {
	
	@Autowired
    private AmpDataExtractDao ampDataExtractDao;
	
	@Override
	public List<Long> getAMPTestCollections(int currentSchoolYear, List<Long> otwIds){
		return ampDataExtractDao.getAMPTestCollections(currentSchoolYear,otwIds);
	}
	
	@Override
	public List<AmpStudentDataExtractDTO> getStudentData(List<Long> ampTestCollections, int currentSchoolYear){
		return ampDataExtractDao.getStudentData(ampTestCollections, currentSchoolYear);
	}
	
	@Override
	public List<StudentGradesTestedDTO> getStudentsGradesTested(List<Long> ampTestCollections, int currentSchoolyear){
		return ampDataExtractDao.getStudentsGradesTested(ampTestCollections, currentSchoolyear);
	}
	
	@Override
	public HashMap<Long, HashMap<Long,List<AmpExtractStudentProfileItemAttributeDTO>>> getStudentProfileItemAttributes(List<Long> ampTestCollections, int currentSchoolyear){
		return ampDataExtractDao.getStudentProfileItemAttributes(ampTestCollections, currentSchoolyear);
	}
//	@Override
//	public HashMap<Long, List<StudentsTests>> getStudentTestsStatus(List<Long> studentIds){
//		return ampDataExtractDao.getStudentTestsStatus(studentIds);
//	}
	@Override
	public HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> getStudentSubjectTotalItemsCount(List<Long> ampTestCollections, int currentSchoolyear){
		return ampDataExtractDao.getStudentSubjectItemsCount(ampTestCollections, currentSchoolyear, "total");
	}
	@Override
	public HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> getStudentSubjectViewedItemsCount(List<Long> ampTestCollections, int currentSchoolyear){
		return ampDataExtractDao.getStudentSubjectItemsCount(ampTestCollections, currentSchoolyear, "viewed");
	}
	@Override
	public HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> getStudentSubjectAnsweredItemsCount(List<Long> ampTestCollections, int currentSchoolyear){
		return ampDataExtractDao.getStudentSubjectItemsCount(ampTestCollections, currentSchoolyear, "answered");
	}
	@Override
	public HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> getStudentSubjectTotalIncludedItemsCount(List<Long> ampTestCollections, int currentSchoolyear){
		return ampDataExtractDao.getStudentSubjectIncludedItemsCount(ampTestCollections, currentSchoolyear, "total");
	}
	@Override
	public HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> getStudentSubjectViewedIncludedItemsCount(List<Long> ampTestCollections, int currentSchoolyear){
		return ampDataExtractDao.getStudentSubjectIncludedItemsCount(ampTestCollections, currentSchoolyear, "viewed");
	}
	@Override
	public HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> getStudentSubjectAnsweredIncludedItemsCount(List<Long> ampTestCollections, int currentSchoolyear){
		return ampDataExtractDao.getStudentSubjectIncludedItemsCount(ampTestCollections, currentSchoolyear, "answered");
	}
	@Override
	public HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> getStudentSubjectAnsweredIncludedItemsCorrectlyCount(List<Long> ampTestCollections, int currentSchoolyear){
		return ampDataExtractDao.getStudentSubjectIncludedItemsCount(ampTestCollections, currentSchoolyear, "answeredcorrect");
	}

//	@Override
//	public HashMap<Long, List<HashMap<String,String>>> getStudentReportDataBySubject(List<Long> studentIds, Long enrollmentId, List<Long> contentAreas){
//		return ampDataExtractDao.getStudentReportDataBySubject(studentIds,contentAreas);
//	}
//	@Override
//	public HashMap<Long, List<SubScoreDto>> getSubScoreDetails(List<Long>studentIds, Long enrollmentId, List<Long> contentAreas, Long displaySequence){
//		return ampDataExtractDao.getSubScoreDetails(studentIds,contentAreas, displaySequence);
//	}

}
