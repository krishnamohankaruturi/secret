package edu.ku.cete.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.model.TestSessionDao;
import edu.ku.cete.service.KSDEReturnFileService;
import edu.ku.cete.service.StudentReportService;
import edu.ku.cete.web.KSDEStudentTestDTO;
import edu.ku.cete.web.KSDEStudentTestDetailsDTO;
import edu.ku.cete.web.KSDEStudentTestSectionsDTO;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class KSDEReturnFileServiceImpl implements KSDEReturnFileService {

	@Autowired
	private TestSessionDao testSessionDao;
	
	@Autowired
	private StudentReportService studentReportService;

	//private static String COMMA = ",";
	private final String COMPLETE ="Complete";
	private final String IN_COMPLETE = "Incomplete";
	private final int COMPUTER_TEST = 1;
	//private final int NOT_A_COMPUTER_TEST = 0;
	private final int ELA_KSDE_FILE_CODE = 0;
	private final int MATH_KSDE_FILE_CODE = 1;
	private final int SCIENCE_KSDE_FILE_CODE = 2;
	private final int SOCIAL_KSDE_FILE_CODE = 3;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<KSDEStudentTestDetailsDTO> getReturnFileDetailsExtract(
			List<String> subjects, Long currentSchoolYear, Long schoolId) {
		List<KSDEStudentTestDetailsDTO> ksdeStudentDetails = new ArrayList<KSDEStudentTestDetailsDTO>();
		
		if(subjects.contains("SELAA") || subjects.contains("D74") || subjects.contains("SSCIA")) {
			ksdeStudentDetails = testSessionDao.getKSDEStudentTestDetails(schoolId, currentSchoolYear, subjects);
		}else if(subjects.contains("SHISGOVA")){
			ksdeStudentDetails = testSessionDao.getKSDEStudentTestDetailsForHGSS(schoolId, currentSchoolYear, subjects);
		}
		
		List<KSDEStudentTestDetailsDTO> ksdeReturnFileDtos = new ArrayList<KSDEStudentTestDetailsDTO>();
		for(KSDEStudentTestDetailsDTO ksdeStudentDetail : ksdeStudentDetails) {			
			boolean isTestsIncmpltedOrinPrgsStatus = false;
			for(KSDEStudentTestDTO studentTestDetails : ksdeStudentDetail.getStudentTestDetails()) {
				if(StringUtils.equalsIgnoreCase(studentTestDetails.getStudentTeststatus(), "complete" ) 
						|| StringUtils.equalsIgnoreCase(studentTestDetails.getStudentTeststatus(), "inprogress" )
						|| StringUtils.equalsIgnoreCase(studentTestDetails.getStudentTeststatus(), "inprogresstimedout")) {
					isTestsIncmpltedOrinPrgsStatus = true;
						break;
					}
			}
			if(isTestsIncmpltedOrinPrgsStatus) {
				ksdeStudentDetail.setKsdeFileCode(getksdeFileCode(ksdeStudentDetail.getAbbrSubject()));
				ksdeStudentDetail.setTestGrade(getTestGradeOrGradBand(ksdeStudentDetail.getTestGrade()));				
				ksdeStudentDetail.setComputerTest(COMPUTER_TEST);
				ksdeStudentDetail.setScholYear(currentSchoolYear.intValue());
				
				if(ksdeStudentDetail.getMdptScorableFlag() != null) {
					if(ksdeStudentDetail.getMdptScorableFlag()) {
						ksdeStudentDetail.setElaMdptScorableFlag("Yes");
						
					} else {
						ksdeStudentDetail.setElaCombinedPrfrmLevel(BigDecimal.valueOf(1L));
						ksdeStudentDetail.setMdptScore(BigDecimal.valueOf(99L));
						ksdeStudentDetail.setElaMdptScorableFlag("No");
					}
					
				}
				
				for(KSDEStudentTestDTO studentTestDetails : ksdeStudentDetail.getStudentTestDetails()) {
					if(StringUtils.equalsIgnoreCase(studentTestDetails.getStageCode(), "Stg1")) {
						ksdeStudentDetail.setStage1_ksdeSCCode(studentTestDetails.getKsdeScCode());
						ksdeStudentDetail.setStage1_questionCount(studentTestDetails.getSectionQuestionsCount() - studentTestDetails.getNumofExludedItems());
						ksdeStudentDetail.setStage1_respondedCount(studentTestDetails.getResponseCount());
						ksdeStudentDetail.setStage1Status(getStageStatus(studentTestDetails.getStudentTeststatus(), studentTestDetails.getKsdeStudentTestSectionDtos()));
						ksdeStudentDetail.setStage1_testId(studentTestDetails.getTestid());
						ksdeStudentDetail.setStage1_testBeginTime(studentTestDetails.getTestBeginDate());
						ksdeStudentDetail.setStage1_testEndTime(studentTestDetails.getTestEndTime());
					}
					
					if(StringUtils.equalsIgnoreCase(studentTestDetails.getStageCode(), "Stg2")) {
						ksdeStudentDetail.setStage2_ksdeSCCode(studentTestDetails.getKsdeScCode());
						ksdeStudentDetail.setStage2_questionCount(studentTestDetails.getSectionQuestionsCount() - studentTestDetails.getNumofExludedItems());
						ksdeStudentDetail.setStage2_respondedCount(studentTestDetails.getResponseCount());						
						ksdeStudentDetail.setStage2Status(getStageStatus(studentTestDetails.getStudentTeststatus(), studentTestDetails.getKsdeStudentTestSectionDtos()));
						ksdeStudentDetail.setStage2_testId(studentTestDetails.getTestid());
						ksdeStudentDetail.setStage2_testBeginTime(studentTestDetails.getTestBeginDate());
						ksdeStudentDetail.setStage2_testEndTime(studentTestDetails.getTestEndTime());
					}
					
					if(StringUtils.equalsIgnoreCase(studentTestDetails.getStageCode(), "Stg3")) {
						ksdeStudentDetail.setStage3_ksdeSCCode(studentTestDetails.getKsdeScCode());
						ksdeStudentDetail.setStage3_questionCount(studentTestDetails.getSectionQuestionsCount() - studentTestDetails.getNumofExludedItems());
						ksdeStudentDetail.setStage3_respondedCount(studentTestDetails.getResponseCount());						
						ksdeStudentDetail.setStage3Status(getStageStatus(studentTestDetails.getStudentTeststatus(), studentTestDetails.getKsdeStudentTestSectionDtos()));
						ksdeStudentDetail.setStage3_testId(studentTestDetails.getTestid());
						ksdeStudentDetail.setStage3_testBeginTime(studentTestDetails.getTestBeginDate());
						ksdeStudentDetail.setStage3_testEndTime(studentTestDetails.getTestEndTime());
					}
					
					if(StringUtils.equalsIgnoreCase(studentTestDetails.getStageCode(), "Prfrm")) {
						if(StringUtils.equalsIgnoreCase(ksdeStudentDetail.getStudentGrade(), "10") 
								&& ((StringUtils.equalsIgnoreCase(ksdeStudentDetail.getAbbrSubject(), "M") || StringUtils.equalsIgnoreCase(ksdeStudentDetail.getAbbrSubject(), "ELA")))) {
							ksdeStudentDetail.setPerf_ksdeSCCode(StringUtils.EMPTY);
							ksdeStudentDetail.setPerformanceStageQuestionCount(null);
							ksdeStudentDetail.setPerf_respondedCount(null);
							ksdeStudentDetail.setPerfStatus(StringUtils.EMPTY);
							ksdeStudentDetail.setPerf_testId(null);
							ksdeStudentDetail.setPerf_testBeginTime(null);
							ksdeStudentDetail.setPerf_testEndTime(null);
						} else {
							ksdeStudentDetail.setPerf_ksdeSCCode(studentTestDetails.getKsdeScCode());							
							ksdeStudentDetail.setPerformanceStageQuestionCount(studentTestDetails.getSectionQuestionsCount() - studentTestDetails.getNumofExludedItems());							
							ksdeStudentDetail.setPerf_respondedCount(studentTestDetails.getResponseCount());
							ksdeStudentDetail.setPerfStatus(getStageStatus(studentTestDetails.getStudentTeststatus(), studentTestDetails.getKsdeStudentTestSectionDtos()));
							ksdeStudentDetail.setPerf_testId(studentTestDetails.getTestid());
							ksdeStudentDetail.setPerf_testBeginTime(studentTestDetails.getTestBeginDate());
							ksdeStudentDetail.setPerf_testEndTime(studentTestDetails.getTestEndTime());
						}
						
					}
				}
				ksdeStudentDetail.setStudentGrade(getKsdeGradeCode(ksdeStudentDetail.getStudentGrade()));
				ksdeReturnFileDtos.add(ksdeStudentDetail);
			}				
				
		}
		return ksdeReturnFileDtos;
	}

	private String getKsdeGradeCode(String studentGrade) {
		String ksdeGradeEqualent = StringUtils.EMPTY;
		if(StringUtils.equalsIgnoreCase(studentGrade, "K")) {
			ksdeGradeEqualent = "05";
		} else if(StringUtils.isNotBlank(studentGrade)){
			int grade = Integer.parseInt(studentGrade);
			grade = grade + 5;
			if(grade < 10) {
				ksdeGradeEqualent = "0" + grade;
			} else {
				ksdeGradeEqualent = "" + grade;
			}
		}
		return ksdeGradeEqualent;
	}

	private String getTestGradeOrGradBand(String testGrade) {
		if(StringUtils.isNotBlank(testGrade) && testGrade.contains(".")) {
			String gradeBands[] = StringUtils.split(testGrade, ".");
			if(gradeBands.length > 2) {
				return gradeBands[1] + "-" + gradeBands[2];
			}			
		}
		return testGrade;
	}

	private String getStageStatus(String studentTeststatus, List<KSDEStudentTestSectionsDTO> ksdeStudentTestSectionDtos) {
		if(StringUtils.equalsIgnoreCase(studentTeststatus, "complete")) {
			return COMPLETE;
		} else if(StringUtils.equalsIgnoreCase(studentTeststatus, "pending") || StringUtils.equalsIgnoreCase(studentTeststatus, "unused")) {
			return IN_COMPLETE;
		} else if(StringUtils.equals(studentTeststatus, "inprogress") || StringUtils.equals(studentTeststatus,"inprogresstimedout")) {
			for(KSDEStudentTestSectionsDTO ksdeStudentTestSectionDto : ksdeStudentTestSectionDtos) {
				if(StringUtils.equalsIgnoreCase(ksdeStudentTestSectionDto.getSectionStatus(), "reactivated")){
					return IN_COMPLETE;
				}
			}
			return COMPLETE;
		}
		return null;
	}

	private Long getRespondedItems(Long responseCount, Long exculdeItemsCount) {
		return responseCount - exculdeItemsCount;
	}

	private Long getStageQuestionCount(List<KSDEStudentTestSectionsDTO> ksdeStudentTestSectionDtos) {
		Long totalCount = 0L;
		Long exculdeItemsCount = 0L; 
		for(KSDEStudentTestSectionsDTO ksdeTestSectionDto : ksdeStudentTestSectionDtos) {
			totalCount = ksdeTestSectionDto.getSectionQuestionsCount() + totalCount;
			exculdeItemsCount = exculdeItemsCount + ksdeTestSectionDto.getNumofExludedItems();
		}		
		return totalCount - exculdeItemsCount;
	}

	private int getksdeFileCode(String abbrSubject) {
		if(StringUtils.equalsIgnoreCase(abbrSubject, "ELA")) {
			return ELA_KSDE_FILE_CODE;
		} else if(StringUtils.equalsIgnoreCase(abbrSubject, "M")) {
			return MATH_KSDE_FILE_CODE;
		} else if(StringUtils.equalsIgnoreCase(abbrSubject, "Sci")) {
			return SCIENCE_KSDE_FILE_CODE;
		} else if(StringUtils.equalsIgnoreCase(abbrSubject, "SS")) {
			return SOCIAL_KSDE_FILE_CODE;
		}
		return 0;
	}
	
	private Long getTotalExcludedItems(List<KSDEStudentTestSectionsDTO> ksdeStudentTestSectionDtos) {
		Long exculdeItemsCount = 0L; 
		for(KSDEStudentTestSectionsDTO ksdeTestSectionDto : ksdeStudentTestSectionDtos) {
			exculdeItemsCount = exculdeItemsCount + ksdeTestSectionDto.getNumofExludedItems();
		}
		return exculdeItemsCount;
	}

}
