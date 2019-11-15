package edu.ku.service;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.ku.cete.dataextracts.model.YearOverYearReportMapper;
import edu.ku.cete.model.AuthoritiesDao;
import edu.ku.cete.model.ExternalstudentreportsMapper;
import edu.ku.cete.model.OrganizationReportDetailsMapper;
import edu.ku.cete.model.RawScoreSectionWeightsMapper;
import edu.ku.cete.model.RubricScoreMapper;
import edu.ku.cete.model.StudentReportMapper;
import edu.ku.cete.model.StudentsResponsesDao;
import edu.ku.cete.model.StudentsTestsDao;
import edu.ku.cete.model.TaskVariantDao;
import edu.ku.cete.model.TaskVariantsFoilsDao;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.model.report.NodeReportDao;
import edu.ku.cete.model.studentsession.TestCollectionsSessionRulesDao;

@Configuration
public class DAOContext {
	
	@Bean
    public StudentDao studentDao(){
		 return Mockito.mock(StudentDao.class);
	}
	
	@Bean
    public NodeReportDao nodeReportDao(){
		 return Mockito.mock(NodeReportDao.class);
	}
	
	@Bean
    public StudentsResponsesDao studentsResponsesDao(){
		 return Mockito.mock(StudentsResponsesDao.class);
	}
	
	@Bean
    public RubricScoreMapper rubricScoreMapper(){
		 return Mockito.mock(RubricScoreMapper.class);
	}
	
	@Bean
    public StudentsTestsDao studentsTestsDao(){
		 return Mockito.mock(StudentsTestsDao.class);
	}
	
	@Bean
    public TaskVariantDao taskVariantDao(){
		 return Mockito.mock(TaskVariantDao.class);
	}
	
	@Bean
    public TaskVariantsFoilsDao taskVariantsFoilsDao(){
		 return Mockito.mock(TaskVariantsFoilsDao.class);
	}
	
	@Bean
    public TestCollectionsSessionRulesDao testCollectionsSessionRulesDao(){
		 return Mockito.mock(TestCollectionsSessionRulesDao.class);
	}
	
	@Bean
    public StudentReportMapper studentReportMapper(){
		 return Mockito.mock(StudentReportMapper.class);
	}
	
	
	@Bean
	ExternalstudentreportsMapper externalstudentreportsMapper(){
		 return Mockito.mock(ExternalstudentreportsMapper.class);
	}
	
	@Bean
    public YearOverYearReportMapper yearOverYearReportMapper(){
		 return Mockito.mock(YearOverYearReportMapper.class);
	}
	
	@Bean
    public OrganizationReportDetailsMapper organizationReportDetailsMapper(){
		 return Mockito.mock(OrganizationReportDetailsMapper.class);
	}
	
	@Bean
    public RawScoreSectionWeightsMapper rawScoreSectionWeightsMapper(){
		 return Mockito.mock(RawScoreSectionWeightsMapper.class);
	}
	
	@Bean
    public AuthoritiesDao authoritiesDao(){
		 return Mockito.mock(AuthoritiesDao.class);
	}
	
}
