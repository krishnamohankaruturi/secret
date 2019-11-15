package edu.ku.cete.controller.test;

import org.mockito.Mockito;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import edu.ku.cete.dataextract.service.DataExtractService;
import edu.ku.cete.report.DLMBlueprintCoverageReportGenerator;
import edu.ku.cete.report.DLMStudentReportGenerator;
import edu.ku.cete.report.DLMTestAdministrationMonitoringSummaryGenerator;
import edu.ku.cete.report.RosterReportGenerator;
import edu.ku.cete.report.StudentReportGenerator;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.DataReportService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.GroupsService;
import edu.ku.cete.service.ItiTestSessionService;
import edu.ku.cete.service.MicroMapService;
import edu.ku.cete.service.OrgAssessmentProgramService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.OrganizationTypeService;
import edu.ku.cete.service.ResourceRestrictionService;
import edu.ku.cete.service.StudentReportService;
import edu.ku.cete.service.StudentSpecialCircumstanceService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.TestingProgramService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.service.organizationbundle.OrganizationBundleReportService;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.service.report.DLMReportsService;
import edu.ku.cete.service.student.StudentProfileService;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RestrictedResourceConfiguration;

@Configuration
public class ServiceContext {
	
    @Bean
    public static PropertyPlaceholderConfigurer propertyConfigurer() {
        PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
        propertyPlaceholderConfigurer.setLocations(
        			new ClassPathResource("env.properties"),
        			new ClassPathResource("upload.properties"));
        propertyPlaceholderConfigurer.setIgnoreResourceNotFound(true);
        propertyPlaceholderConfigurer.setSearchSystemEnvironment(true);
        propertyPlaceholderConfigurer.setSystemPropertiesModeName("SYSTEM_PROPERTIES_MODE_OVERRIDE");
        return propertyPlaceholderConfigurer;
    }
	
	@Bean
    public StudentReportService studentReportService(){
        return Mockito.mock(StudentReportService.class);
    }
	@Bean
	public StudentProfileService studentProfileService(){
		return Mockito.mock(StudentProfileService.class);
	}
	
	@Bean
	public OrgAssessmentProgramService orgAssessmentProgramService(){
		return Mockito.mock(OrgAssessmentProgramService.class);
	}
	
	@Bean
	public TestingProgramService testingProgramService(){
		return Mockito.mock(TestingProgramService.class);
	}
	
	@Bean
	public ContentAreaService contentAreaService(){
		return Mockito.mock(ContentAreaService.class);
	}
	
	@Bean
	public GradeCourseService gradeCourseService(){
		return Mockito.mock(GradeCourseService.class);
	}
	
	@Bean
	public RosterService rosterService(){
		return Mockito.mock(RosterService.class);
	}
	
	@Bean
	public OrganizationService organizationService(){
		return Mockito.mock(OrganizationService.class);
	}
	
	@Bean
	public EnrollmentService enrollmentService(){
		return Mockito.mock(EnrollmentService.class);
	}
	
	@Bean
	public RestrictedResourceConfiguration restrictedResourceConfiguration(){
		return Mockito.mock(RestrictedResourceConfiguration.class);
	}
	
	@Bean
	public CategoryService categoryService(){
		return Mockito.mock(CategoryService.class);
	}
	
	@Bean
	public PermissionUtil permissionUtil(){
		return Mockito.mock(PermissionUtil.class);
	}
	@Bean
	public AssessmentProgramService assessmentProgramService(){
		return Mockito.mock(AssessmentProgramService.class);
	}
	
	@Bean
	public DataReportService dataReportService(){
		return Mockito.mock(DataReportService.class);
	}
	
	@Bean
	public OrganizationTypeService organizationTypeService(){
		return Mockito.mock(OrganizationTypeService.class);
	}
	@Bean
	public StudentSpecialCircumstanceService studentSpecialCircumstanceService(){
		return Mockito.mock(StudentSpecialCircumstanceService.class);
	}
	@Bean
	public UserService userService(){
		return Mockito.mock(UserService.class);
	}
	
	@Bean
	public StudentReportGenerator studentReportGenerator(){
		return Mockito.mock(StudentReportGenerator.class);
	}
	
	@Bean
	public RosterReportGenerator rosterReportGenerator(){
		return Mockito.mock(RosterReportGenerator.class);
	}
	
	@Bean
	public DLMStudentReportGenerator dlmStudentReportGenerator(){
		return Mockito.mock(DLMStudentReportGenerator.class);
	}
	
	@Bean
	public DLMTestAdministrationMonitoringSummaryGenerator dlmTestAdministrationMonitoringSummaryGenerator(){
		return Mockito.mock(DLMTestAdministrationMonitoringSummaryGenerator.class);
	}
	
	@Bean
	public DLMBlueprintCoverageReportGenerator dlmBlueprintCoverageReportGenerator(){
		return Mockito.mock(DLMBlueprintCoverageReportGenerator.class);
	}
	
	@Bean
	public DataExtractService dataExtractService(){
		return Mockito.mock(DataExtractService.class);
	}
	
	@Bean
	public GroupsService groupsService(){
		return Mockito.mock(GroupsService.class);
	}
	
	@Bean
	public ItiTestSessionService itiTestSessionService(){
		return Mockito.mock(ItiTestSessionService.class);
	}
	
	@Bean
	public MicroMapService microMapService(){
		return Mockito.mock(MicroMapService.class);
	}
	
	@Bean
	public DLMReportsService dlmReportsService(){
		return Mockito.mock(DLMReportsService.class);
	}
	
	@Bean
	public ResourceRestrictionService resourceRestrictionService(){
		return Mockito.mock(ResourceRestrictionService.class);
	}
	
	@Bean
	public TestService testService(){
		return Mockito.mock(TestService.class);
	}
	
    @Bean
    public OrganizationBundleReportService bundleReportService(){
    	return Mockito.mock(OrganizationBundleReportService.class);
    }
    @Bean
    public BatchReportProcessService batchReportProcessService(){
    	return Mockito.mock(BatchReportProcessService.class);
    }
}
