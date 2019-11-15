package edu.ku.cete.controller.test;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.powermock.api.mockito.PowerMockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.ku.cete.controller.DownloadController;
import edu.ku.cete.domain.Externalstudentreports;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationType;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.GroupsService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.StudentReportService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.util.FileUtil;
import edu.ku.cete.web.ExternalStudentReportDTO;
import edu.ku.cete.web.StudentReportDTO;


@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@PrepareForTest({FileUtil.class})
@ContextConfiguration(classes = {ServiceContext.class, WebAppContext.class})
@WebAppConfiguration
public class NewReportControllerTest {

	
	private MockMvc mockMvc;
	
	@Autowired
	private StudentReportService studentReportServiceMock;
	
	@Autowired
	private RosterService rosterServiceMock;
	
	@Autowired
	private OrganizationService orgServiceMock;
	
	@Autowired
	private GroupsService groupsServiceMock;
	
	@Autowired
    private WebApplicationContext webApplicationContext;
	
    @Before
    public void setUp() {
        Mockito.reset(studentReportServiceMock, rosterServiceMock, orgServiceMock, groupsServiceMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
	
    public void setUpUserMocks_Standard(){
        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.isAuthenticated()).thenReturn(true);
        SecurityContextHolder.setContext(mockSecurityContext);
        
        UserDetailImpl mockUserDetails = mock(UserDetailImpl.class);
        User mockUser = mock(User.class); 
        Organization mockOrg = mock(Organization.class);
        //use setInternalState instead of when/thenReturn because user is a final attribute
        Whitebox.setInternalState(mockUserDetails, "user", mockUser);
        when(mockUser.getContractingOrganization()).thenReturn(mockOrg);
        when(mockOrg.getReportYear()).thenReturn(2016);
        Whitebox.setInternalState(mockOrg, "id", 9591L);
        when(mockOrg.getCurrentSchoolYear()).thenReturn(2017L);
        when(mockAuthentication.getPrincipal()).thenReturn(mockUserDetails);
        when(mockUser.getCurrentAssessmentProgramId()).thenReturn(3L);
        //use setInternalState instead of when/thenReturn because getId is a final method
        Whitebox.setInternalState(mockUser, "id", 1L);
        when(mockUser.getCurrentGroupsId()).thenReturn(9681L);
    }
    
    public void setUpUserMocks_UnAuthorized(){
        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.isAuthenticated()).thenReturn(false);
        SecurityContextHolder.setContext(mockSecurityContext);
    }
    
	@Test
	public void getExternalStudentReportsForTeacherRosterTest() throws Exception{
		setUpUserMocks_Standard();
		ExternalStudentReportDTO studentReportOne = new ExternalStudentReportDTO();
		studentReportOne.setId(1L);
		studentReportOne.setSchoolId(1L);
		studentReportOne.setDistrictId(10L);
		studentReportOne.setStateId(1L);
		studentReportOne.setFilePath("testPath1");
		studentReportOne.setStateStudentIdentifier("123456789");
		studentReportOne.setSchoolName("Test School 1");
		
		ExternalStudentReportDTO studentReportTwo = new ExternalStudentReportDTO();
		studentReportTwo.setId(2L);
		studentReportTwo.setSchoolId(1L);
		studentReportTwo.setDistrictId(10L);
		studentReportTwo.setStateId(1L);
		studentReportTwo.setFilePath("testPath2");
		studentReportTwo.setStateStudentIdentifier("987654321");
		studentReportTwo.setSchoolName("Test School 1");

		List<ExternalStudentReportDTO> list = Arrays.asList(studentReportOne, studentReportTwo);
		
        when(studentReportServiceMock.getExternalStudentReportsForTeacherRoster(anyMapOf(String.class, Object.class))).thenReturn(list);
 
        mockMvc.perform(get("/getExternalStudentReportsForTeacherRoster.htm")
        .param("reportType", "alternate_student_individual_teacher")
        .param("rosterId","1")
        .param("assessmentProgram", "11")
        .param("page", "1")
        .param("perPage", "15"))        
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(jsonPath("$[0].schoolId", is(1)))
        .andExpect(jsonPath("$[0].districtId", is(10)))
        .andExpect(jsonPath("$[0].stateId", is(1)))
        .andExpect(jsonPath("$[0].filePath", is("testPath1")))
        .andExpect(jsonPath("$[0].schoolName", is("Test School 1")))
        .andExpect(jsonPath("$[0].stateStudentIdentifier", is("123456789")))
        .andExpect(jsonPath("$[1].id", is(2)))
        .andExpect(jsonPath("$[1].schoolId", is(1)))
        .andExpect(jsonPath("$[1].districtId", is(10)))
        .andExpect(jsonPath("$[1].stateId", is(1)))
        .andExpect(jsonPath("$[1].filePath", is("testPath2")))
        .andExpect(jsonPath("$[1].schoolName", is("Test School 1")))
        .andExpect(jsonPath("$[1].stateStudentIdentifier", is("987654321")));
 
        verify(studentReportServiceMock, times(1)).getExternalStudentReportsForTeacherRoster(anyMapOf(String.class, Object.class));
        verifyNoMoreInteractions(studentReportServiceMock);
	}

	@Test
	public void getExternalStudentReportsForTeacherRoster_UnAuthorizedTest() throws Exception{
		setUpUserMocks_UnAuthorized();
 
        mockMvc.perform(get("/getExternalStudentReportsForTeacherRoster.htm")
        .param("reportType", "alternate_student_individual_teacher")
        .param("rosterId","1")
        .param("assessmentProgram", "11")
        .param("page", "1")
        .param("perPage", "15"))        
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$", hasSize(0)));
 
        verify(studentReportServiceMock, times(0)).getExternalStudentReportsForTeacherRoster(anyMapOf(String.class, Object.class));
        verifyNoMoreInteractions(studentReportServiceMock);
	}
	
	@Test
	public void getExternalStudentReportsForTeacherRoster_InvalidReportTypeTest() throws Exception{
		setUpUserMocks_Standard();
		
        mockMvc.perform(get("/getExternalStudentReportsForTeacherRoster.htm")
        .param("reportType", "bogus")
        .param("rosterId","1")
        .param("assessmentProgram", "11")
        .param("page", "1")
        .param("perPage", "15"))        
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(""));
 
        verify(studentReportServiceMock, times(0)).getExternalStudentReportsForTeacherRoster(anyMapOf(String.class, Object.class));
        verifyNoMoreInteractions(studentReportServiceMock);
	}
	
	@Test
	public void countExternalStudentReportsForTeacherRosterTest() throws Exception{
		setUpUserMocks_Standard();
		
        when(studentReportServiceMock.countExternalStudentReportsForTeacherRoster(anyMapOf(String.class, Object.class))).thenReturn(2L);
 
        mockMvc.perform(get("/countExternalStudentReportsForTeacherRoster.htm")
        .param("reportType", "alternate_student_individual_teacher")
        .param("rosterId","1")
        .param("assessmentProgram", "11"))        
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().string("2"));
        
        verify(studentReportServiceMock, times(1)).countExternalStudentReportsForTeacherRoster(anyMapOf(String.class, Object.class));
        verifyNoMoreInteractions(studentReportServiceMock);
	}
	
	@Test
	public void countExternalStudentReportsForTeacherRoster_UnAuthorizedTest() throws Exception{
		setUpUserMocks_UnAuthorized();
 
        mockMvc.perform(get("/countExternalStudentReportsForTeacherRoster.htm")
        .param("reportType", "alternate_student_individual_teacher")
        .param("rosterId","1")
        .param("assessmentProgram", "11"))        
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(""));
        
        verify(studentReportServiceMock, times(0)).countExternalStudentReportsForTeacherRoster(anyMapOf(String.class, Object.class));
        verifyNoMoreInteractions(studentReportServiceMock);
	}	
	
	@Test
	public void countExternalStudentReportsForTeacherRoster_InvalidReportTypeTest() throws Exception{
		setUpUserMocks_Standard();
 
        mockMvc.perform(get("/countExternalStudentReportsForTeacherRoster.htm")
        .param("reportType", "bogus")
        .param("rosterId","1")
        .param("assessmentProgram", "11"))        
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().string("0"));
        
        verify(studentReportServiceMock, times(0)).countExternalStudentReportsForTeacherRoster(anyMapOf(String.class, Object.class));
        verifyNoMoreInteractions(studentReportServiceMock);
	}
	
	@Test
	public void getRostersForTeacherReportsTest() throws Exception{
		setUpUserMocks_Standard();
		
		Roster roster1 = new Roster();
		roster1.setCourseSectionName("Ms. English ELA");
		roster1.setTeacherId(123L);
		roster1.setAttendanceSchoolId(333L);
		roster1.setSchoolName("School 333");
		Roster roster2 = new Roster();
		roster2.setCourseSectionName("Ms. English Math");
		roster2.setTeacherId(123L);
		roster2.setAttendanceSchoolId(333L);
		roster2.setSchoolName("School 333");
		List<Roster> rosters =  Arrays.asList(roster1, roster2);
		
        when(rosterServiceMock.getRostersForTeacherReports(anyLong(), anyLong(), anyLong(), anyLong(), anyLong())).thenReturn(rosters);
 
        mockMvc.perform(get("/getRosterForTeacherReports.htm")
        .param("reportType", "alternate_student_individual_teacher"))       
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].courseSectionName", is("Ms. English ELA")))
        .andExpect(jsonPath("$[0].teacherId", is(123)))
        .andExpect(jsonPath("$[0].attendanceSchoolId", is(333)))
        .andExpect(jsonPath("$[0].schoolName", is("School 333")))
        .andExpect(jsonPath("$[1].courseSectionName", is("Ms. English Math")))
        .andExpect(jsonPath("$[1].teacherId", is(123)))
        .andExpect(jsonPath("$[1].attendanceSchoolId", is(333)))
        .andExpect(jsonPath("$[1].schoolName", is("School 333")));
        
        verify(rosterServiceMock, times(1)).getRostersForTeacherReports(anyLong(), anyLong(), anyLong(), anyLong(), anyLong());
        verifyNoMoreInteractions(rosterServiceMock);
	}
	
	@Test
	public void getRostersForTeacherReports_UnAuthorizedTest() throws Exception{
		setUpUserMocks_UnAuthorized();
 
        mockMvc.perform(get("/getRosterForTeacherReports.htm")
        .param("reportType", "alternate_student_individual_teacher"))       
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(""));;
        
        verify(rosterServiceMock, times(0)).getRostersForTeacherReports(anyLong(), anyLong(), anyLong(), anyLong(), anyLong());
        verifyNoMoreInteractions(rosterServiceMock);
	}
	
	@Test
	public void getRostersForTeacherReports_InvalidReportTypeTest() throws Exception{
		setUpUserMocks_Standard();
 
        mockMvc.perform(get("/getRosterForTeacherReports.htm")
        .param("reportType", "bogus"))       
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(""));;
        
        verify(rosterServiceMock, times(0)).getRostersForTeacherReports(anyLong(), anyLong(), anyLong(), anyLong(), anyLong());
        verifyNoMoreInteractions(rosterServiceMock);
	}
	
	
	@Test
	public void searchByStudentLastNameTest() throws Exception{
		setUpUserMocks_Standard();
		
		Organization orgMock = mock(Organization.class);
		OrganizationType orgTypeMock = mock(OrganizationType.class);
		Groups groupsMock = mock(Groups.class);
		
		when(orgServiceMock.get(anyLong())).thenReturn(orgMock);
		Whitebox.setInternalState(orgMock, "organizationType", orgTypeMock);
		Whitebox.setInternalState(orgMock, "id", 1L);
		Whitebox.setInternalState(orgTypeMock, "typeCode", "SCH");
		when(groupsServiceMock.getGroup(anyLong())).thenReturn(groupsMock);
		
		StudentReportDTO dto1 = new StudentReportDTO();
		dto1.setLegalLastName("Smithe");
		dto1.setLegalFirstName("Jamie");
		dto1.setLegalMiddleName("W");
		dto1.setStateStudentIdentifier("1122334455");
		dto1.setGradeName("10");
		dto1.setSchoolName("School 1");
		dto1.setTotalRecords(2);
		
		StudentReportDTO dto2 = new StudentReportDTO();
		dto2.setLegalLastName("Smithers");
		dto2.setLegalFirstName("Donald");
		dto2.setLegalMiddleName("James");
		dto2.setStateStudentIdentifier("5544332211");
		dto2.setGradeName("10");
		dto2.setSchoolName("School 2");
		dto2.setTotalRecords(2);
		
		List<StudentReportDTO> list = Arrays.asList(dto1, dto2);
		
		when(studentReportServiceMock.searchByStudentLastName(anyString(), anyLong(), anyString(), 
				anyLong(), anyInt(), anyInt(), anyLong(), anyLong(), anyLong(), any(Groups.class))).thenReturn(list);
		
        mockMvc.perform(get("/searchByStudentLastName.htm")
        .param("studentLastName", "Smithe")
        .param("rows","15")
        .param("page", "1"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$.total", is(1)))
        .andExpect(jsonPath("$.page", is(1)))
        .andExpect(jsonPath("$.records", is(2)))
        .andExpect(jsonPath("$.rows", hasSize(2)))
        .andExpect(jsonPath("$.rows[0].cell[4]", is("1122334455")))
        .andExpect(jsonPath("$.rows[0].cell[2]", is("Jamie")))
        .andExpect(jsonPath("$.rows[0].cell[1]", is("Smithe")))
        .andExpect(jsonPath("$.rows[0].cell[3]", is("W")))
        .andExpect(jsonPath("$.rows[0].cell[5]", is("10")))
        .andExpect(jsonPath("$.rows[0].cell[6]", is("School 1")))
        .andExpect(jsonPath("$.rows[1].cell[1]", is("Smithers")))
        .andExpect(jsonPath("$.rows[1].cell[2]", is("Donald")))
        .andExpect(jsonPath("$.rows[1].cell[3]", is("James")))
        .andExpect(jsonPath("$.rows[1].cell[4]", is("5544332211")))
        .andExpect(jsonPath("$.rows[1].cell[5]", is("10")))
        .andExpect(jsonPath("$.rows[1].cell[6]", is("School 2")));
        
        verify(studentReportServiceMock, times(1)).searchByStudentLastName(anyString(), anyLong(), anyString(), 
				anyLong(), anyInt(), anyInt(), anyLong(), anyLong(), anyLong(), any(Groups.class));
        verify(groupsServiceMock,times(1)).getGroup(anyLong());
        verify(orgServiceMock,times(1)).get(anyLong());
        verifyNoMoreInteractions(studentReportServiceMock);
        verifyNoMoreInteractions(groupsServiceMock);
        verifyNoMoreInteractions(orgServiceMock);
	}
	
	@Test
	public void searchByStateStudentIdForKAPTest() throws Exception{
		setUpUserMocks_Standard();
		
		Organization orgMock = mock(Organization.class);
		OrganizationType orgTypeMock = mock(OrganizationType.class);
		
		when(orgServiceMock.get(anyLong())).thenReturn(orgMock);
		Whitebox.setInternalState(orgMock, "organizationType", orgTypeMock);
		Whitebox.setInternalState(orgMock, "id", 1L);
		Whitebox.setInternalState(orgTypeMock, "typeCode", "SCH");
		
		StudentReportDTO dto1 = new StudentReportDTO();
		dto1.setLegalLastName("Smithe");
		dto1.setLegalFirstName("Jamie");
		dto1.setLegalMiddleName("W");
		dto1.setStateStudentIdentifier("1122334455");
		dto1.setGradeName("10");
		dto1.setSchoolName("School 1");
		dto1.setStatus(true);
		dto1.setScaleScore(10L);
		dto1.setLevel(1L);
		dto1.setContentAreaName("Math");
		dto1.setSchoolYear(2016L);
		dto1.setTotalRecords(1);
		
		List<StudentReportDTO> list = Arrays.asList(dto1);
		
		when(studentReportServiceMock.searchByStateStudentIdForKAP(anyString(), anyLong(), anyString(), 
				anyLong(), anyLong(), anyLong(), anyLong())).thenReturn(list);
		
        mockMvc.perform(get("/searchByStateStudentIdForKAP.htm")
        .param("stateStudentIdentifier", "1122334455"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$.page", is(1)))
        .andExpect(jsonPath("$.records", is(1)))
        .andExpect(jsonPath("$.rows", hasSize(1)))
        .andExpect(jsonPath("$.rows[0].cell[0]", is("2016")))
        .andExpect(jsonPath("$.rows[0].cell[1]", is("School 1")))
        .andExpect(jsonPath("$.rows[0].cell[2]", is("Math")))
        .andExpect(jsonPath("$.rows[0].cell[3]", is("10")))
        .andExpect(jsonPath("$.rows[0].cell[4]", is("10")))
        .andExpect(jsonPath("$.rows[0].cell[5]", is("1")))
        .andExpect(jsonPath("$.rows[0].cell[6]", is("")));
        
        verify(studentReportServiceMock, times(1)).searchByStateStudentIdForKAP(anyString(), anyLong(), anyString(), 
				anyLong(), anyLong(), anyLong(), anyLong());
        verify(orgServiceMock,times(1)).get(anyLong());
        verifyNoMoreInteractions(studentReportServiceMock);
        verifyNoMoreInteractions(orgServiceMock);
	}	
	
	@Test
	public void searchByStateStudentIdForDLMTest() throws Exception{
		setUpUserMocks_Standard();
		
		Organization orgMock = mock(Organization.class);
		OrganizationType orgTypeMock = mock(OrganizationType.class);
		Groups groupsMock = mock(Groups.class);
		
		when(orgServiceMock.get(anyLong())).thenReturn(orgMock);
		Whitebox.setInternalState(orgMock, "organizationType", orgTypeMock);
		Whitebox.setInternalState(orgMock, "id", 1L);
		Whitebox.setInternalState(orgTypeMock, "typeCode", "SCH");
		when(groupsServiceMock.getGroup(anyLong())).thenReturn(groupsMock);
		
		StudentReportDTO dto1 = new StudentReportDTO();
		dto1.setLegalLastName("Smithe");
		dto1.setLegalFirstName("Jamie");
		dto1.setLegalMiddleName("W");
		dto1.setStateStudentIdentifier("1122334455");
		dto1.setGradeName("10");
		dto1.setSchoolName("School 1");
		dto1.setStatus(true);
		dto1.setScaleScore(10L);
		dto1.setLevel(1L);
		dto1.setContentAreaName("Math");
		dto1.setSchoolYear(2016L);
		dto1.setTotalRecords(1);
		
		List<StudentReportDTO> list = Arrays.asList(dto1);
		
		when(studentReportServiceMock.searchByStateStudentIdForDLM(anyString(), anyLong(), anyString(), 
				anyLong(), anyLong(), anyLong(), anyLong(), any(Groups.class))).thenReturn(list);
		
        mockMvc.perform(get("/searchByStateStudentIdForDLM.htm")
        .param("stateStudentIdentifier", "1122334455"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$.page", is(1)))
        .andExpect(jsonPath("$.records", is(1)))
        .andExpect(jsonPath("$.rows", hasSize(1)))
        .andExpect(jsonPath("$.rows[0].cell[0]", is("2016")))
        .andExpect(jsonPath("$.rows[0].cell[1]", is("School 1")))
        .andExpect(jsonPath("$.rows[0].cell[2]", is("Math")))
        .andExpect(jsonPath("$.rows[0].cell[3]", is("10")))
        .andExpect(jsonPath("$.rows[0].cell[4]", is("")));
        
        verify(studentReportServiceMock, times(1)).searchByStateStudentIdForDLM(anyString(), anyLong(), anyString(), 
				anyLong(), anyLong(), anyLong(), anyLong(), any(Groups.class));
        verify(groupsServiceMock,times(1)).getGroup(anyLong());
        verify(orgServiceMock,times(1)).get(anyLong());
        verifyNoMoreInteractions(studentReportServiceMock);
        verifyNoMoreInteractions(groupsServiceMock);
        verifyNoMoreInteractions(orgServiceMock);
	}	
	
	@Test
	public void searchByStateStudentIdForCPASSTest() throws Exception{
		setUpUserMocks_Standard();
		
		Organization orgMock = mock(Organization.class);
		OrganizationType orgTypeMock = mock(OrganizationType.class);
		Groups groupsMock = mock(Groups.class);
		
		when(orgServiceMock.get(anyLong())).thenReturn(orgMock);
		Whitebox.setInternalState(orgMock, "organizationType", orgTypeMock);
		Whitebox.setInternalState(orgMock, "id", 1L);
		Whitebox.setInternalState(orgTypeMock, "typeCode", "SCH");
		when(groupsServiceMock.getGroup(anyLong())).thenReturn(groupsMock);
		
		StudentReportDTO dto1 = new StudentReportDTO();
		dto1.setLegalLastName("Smithe");
		dto1.setLegalFirstName("Jamie");
		dto1.setLegalMiddleName("W");
		dto1.setStateStudentIdentifier("1122334455");
		dto1.setGradeName("10");
		dto1.setSchoolName("School 1");
		dto1.setStatus(true);
		dto1.setScaleScore(10L);
		dto1.setLevel(1L);
		dto1.setContentAreaName("Math");
		dto1.setSchoolYear(2016L);
		dto1.setTotalRecords(1);
		
		List<StudentReportDTO> list = Arrays.asList(dto1);
		
		when(studentReportServiceMock.searchByStateStudentIdForCPASS(anyString(), anyLong(), anyString(), 
				anyLong(), anyLong(), anyLong(), anyLong(), any(Groups.class))).thenReturn(list);
		
        mockMvc.perform(get("/searchByStateStudentIdForCPASS.htm")
        .param("stateStudentIdentifier", "1122334455"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$.page", is(1)))
        .andExpect(jsonPath("$.records", is(1)))
        .andExpect(jsonPath("$.rows", hasSize(1)))
        .andExpect(jsonPath("$.rows[0].cell[0]", is("2016")))
        .andExpect(jsonPath("$.rows[0].cell[1]", is("School 1")))
        .andExpect(jsonPath("$.rows[0].cell[2]", is("Math")))
        .andExpect(jsonPath("$.rows[0].cell[3]", is("10")))
        .andExpect(jsonPath("$.rows[0].cell[4]", is("")));
        
        verify(studentReportServiceMock, times(1)).searchByStateStudentIdForCPASS(anyString(), anyLong(), anyString(), 
				anyLong(), anyLong(), anyLong(), anyLong(), any(Groups.class));
        verify(groupsServiceMock,times(1)).getGroup(anyLong());
        verify(orgServiceMock,times(1)).get(anyLong());
        verifyNoMoreInteractions(studentReportServiceMock);
        verifyNoMoreInteractions(groupsServiceMock);
        verifyNoMoreInteractions(orgServiceMock);
	}	
	
	@Test
	public void getStudentInfoForAllStudentsReportsTest() throws Exception{
		setUpUserMocks_Standard();
		
		Organization orgMock = mock(Organization.class);
		OrganizationType orgTypeMock = mock(OrganizationType.class);
		
		when(orgServiceMock.get(anyLong())).thenReturn(orgMock);
		Whitebox.setInternalState(orgMock, "organizationType", orgTypeMock);
		Whitebox.setInternalState(orgMock, "id", 1L);
		Whitebox.setInternalState(orgTypeMock, "typeCode", "SCH");

		
		StudentReportDTO dto1 = new StudentReportDTO();
		dto1.setLegalLastName("Smithe");
		dto1.setLegalFirstName("Jamie");
		dto1.setLegalMiddleName("W");
		dto1.setStateStudentIdentifier("1122334455");
		dto1.setGradeName("10");
		dto1.setSchoolName("School 1");
		dto1.setContentAreaName("Math");
		dto1.setSchoolYear(2016L);
		dto1.setTotalRecords(1);
		
		when(studentReportServiceMock.getStudentInfoForAllStudentsReports(anyString(), anyLong(), anyString(), 
				anyLong(), anyLong(), anyLong())).thenReturn(dto1);
		
        mockMvc.perform(get("/getStudentInfoForAllStudentsReports.htm")
        .param("stateStudentIdentifier", "1122334455"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$.schoolYear", is(2016)))
        .andExpect(jsonPath("$.gradeName", is("10")))
        .andExpect(jsonPath("$.contentAreaName", is("Math")))
        .andExpect(jsonPath("$.stateStudentIdentifier", is("1122334455")))
        .andExpect(jsonPath("$.legalLastName", is("Smithe")))
        .andExpect(jsonPath("$.legalFirstName", is("Jamie")))
        .andExpect(jsonPath("$.legalMiddleName", is("W")));
        
        verify(studentReportServiceMock, times(1)).getStudentInfoForAllStudentsReports(anyString(), anyLong(), anyString(), 
				anyLong(), anyLong(), anyLong());
        verify(orgServiceMock,times(1)).get(anyLong());
        verifyNoMoreInteractions(studentReportServiceMock);
        verifyNoMoreInteractions(orgServiceMock);
	}		
	
	@Test
	public void countReportsForStudentTest() throws Exception{
		setUpUserMocks_Standard();
		
		Organization orgMock = mock(Organization.class);
		OrganizationType orgTypeMock = mock(OrganizationType.class);
		Groups groupsMock = mock(Groups.class);
		
		when(orgServiceMock.get(anyLong())).thenReturn(orgMock);
		Whitebox.setInternalState(orgMock, "organizationType", orgTypeMock);
		Whitebox.setInternalState(orgMock, "id", 1L);
		Whitebox.setInternalState(orgTypeMock, "typeCode", "SCH");
		when(groupsServiceMock.getGroup(anyLong())).thenReturn(groupsMock);
		when(studentReportServiceMock.countReportsForStudent(anyString(), anyLong(), anyString(), 
				anyLong(), anyLong(), anyLong(), anyLong(), any(Groups.class))).thenReturn(1);
		
        mockMvc.perform(get("/countReportsForStudent.htm")
        .param("stateStudentIdentifier", "1122334455"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().string("1"));
        
        verify(studentReportServiceMock, times(1)).countReportsForStudent(anyString(), anyLong(), anyString(), 
				anyLong(), anyLong(), anyLong(), anyLong(), any(Groups.class));
        verify(groupsServiceMock,times(1)).getGroup(anyLong());
        verify(orgServiceMock,times(1)).get(anyLong());
        verifyNoMoreInteractions(studentReportServiceMock);
        verifyNoMoreInteractions(groupsServiceMock);
        verifyNoMoreInteractions(orgServiceMock);
	}		
	
	@Test
	public void doesSSIDExistTest() throws Exception{
		setUpUserMocks_Standard();
		
		when(studentReportServiceMock.doesSSIDExist(anyString(), anyLong(),	anyLong())).thenReturn(true);
		
        mockMvc.perform(get("/doesSSIDExist.htm")
        .param("stateStudentIdentifier", "1122334455"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().string("true"));
        
        verify(studentReportServiceMock, times(1)).doesSSIDExist(anyString(), anyLong(), anyLong());
        verifyNoMoreInteractions(studentReportServiceMock);
	}	
	
	@Test
	public void getStudentReportFileForAllStudentReportsTest() throws Exception{
		setUpUserMocks_Standard();
		
		Organization orgMock = mock(Organization.class);
		OrganizationType orgTypeMock = mock(OrganizationType.class);
		mockStatic(FileUtil.class);
		mockStatic(DownloadController.class);
		
		when(orgServiceMock.get(anyLong())).thenReturn(orgMock);
		Whitebox.setInternalState(orgMock, "organizationType", orgTypeMock);
		Whitebox.setInternalState(orgMock, "id", 1L);
		Whitebox.setInternalState(orgTypeMock, "typeCode", "SCH");

		
		StudentReport dto1 = new StudentReport();
		dto1.setStudentLastName("Smithe");
		dto1.setStudentFirstName("Jamie");
		dto1.setStateStudentIdentifier("1122334455");
		dto1.setGradeName("10");
		dto1.setAttendanceSchoolName("School 1");
		dto1.setContentAreaName("Math");
		dto1.setSchoolYear(2016L);
		dto1.setFilePath("filename.pdf");
		MockHttpServletRequest request = new MockHttpServletRequest();
	    MockHttpServletResponse response = new MockHttpServletResponse();
	    
		PowerMockito.when(FileUtil.buildFilePath(anyString(),anyString())).thenReturn(dto1.getFilePath());
		PowerMockito.doNothing().when(DownloadController.class, "download", request, response, dto1.getFilePath());
		
		when(studentReportServiceMock.getByPrimaryKeyForAllStudentReports(anyLong(), anyLong(), anyString(), anyLong(), anyString())).thenReturn(dto1);
		
        mockMvc.perform(get("/getStudentReportFileForAllStudentReports.htm")
        .param("id","1")
        .param("ssid", "1122334455"))
        .andDo(print())
        .andExpect(status().isOk());
        
        verify(studentReportServiceMock, times(1)).getByPrimaryKeyForAllStudentReports(anyLong(), anyLong(), anyString(), anyLong(), anyString());
        verify(orgServiceMock,times(1)).get(anyLong());
        verifyNoMoreInteractions(studentReportServiceMock);
        verifyNoMoreInteractions(orgServiceMock);
	}
	
	
	@Test
	public void getStudentReportFileForAllStudentReports_NoReportTest() throws Exception{
		setUpUserMocks_Standard();
		
		Organization orgMock = mock(Organization.class);
		OrganizationType orgTypeMock = mock(OrganizationType.class);
		mockStatic(FileUtil.class);
		mockStatic(DownloadController.class);
		
		when(orgServiceMock.get(anyLong())).thenReturn(orgMock);
		Whitebox.setInternalState(orgMock, "organizationType", orgTypeMock);
		Whitebox.setInternalState(orgMock, "id", 1L);
		Whitebox.setInternalState(orgTypeMock, "typeCode", "SCH");

		StudentReport dto1 = new StudentReport();
		dto1.setStudentLastName("Smithe");
		dto1.setStudentFirstName("Jamie");
		dto1.setStateStudentIdentifier("1122334455");
		dto1.setGradeName("10");
		dto1.setAttendanceSchoolName("School 1");
		dto1.setContentAreaName("Math");
		dto1.setSchoolYear(2016L);
		dto1.setFilePath("filename.pdf");
	   
		
		when(studentReportServiceMock.getByPrimaryKeyForAllStudentReports(anyLong(), anyLong(), anyString(), anyLong(), anyString())).thenReturn(null);
		
        mockMvc.perform(get("/getStudentReportFileForAllStudentReports.htm")
        .param("id","1")
        .param("ssid", "1122334455"))
        .andDo(print())
        .andExpect(status().is3xxRedirection());
        
        verify(studentReportServiceMock, times(1)).getByPrimaryKeyForAllStudentReports(anyLong(), anyLong(), anyString(), anyLong(), anyString());
        verify(orgServiceMock,times(1)).get(anyLong());
        verifyNoMoreInteractions(studentReportServiceMock);
        verifyNoMoreInteractions(orgServiceMock);
	}
	
	@Test
	public void getStudentReportFileForAllStudentReports_UnAuthorizedTest() throws Exception{
		setUpUserMocks_UnAuthorized();
 
        mockMvc.perform(get("/getStudentReportFileForAllStudentReports.htm")
        .param("id","1")
        .param("ssid", "1122334455"))
        .andDo(print())
        .andExpect(status().isOk());
 
        verify(studentReportServiceMock, times(0)).getExternalStudentReportsForTeacherRoster(anyMapOf(String.class, Object.class));
        verifyNoMoreInteractions(studentReportServiceMock);
	}
	
	@Test
	public void getExternalStudentReportFileForAllStudentReportsTest() throws Exception{
		setUpUserMocks_Standard();
		
		Organization orgMock = mock(Organization.class);
		OrganizationType orgTypeMock = mock(OrganizationType.class);
		mockStatic(FileUtil.class);
		mockStatic(DownloadController.class);
		
		when(orgServiceMock.get(anyLong())).thenReturn(orgMock);
		Whitebox.setInternalState(orgMock, "organizationType", orgTypeMock);
		Whitebox.setInternalState(orgMock, "id", 1L);
		Whitebox.setInternalState(orgTypeMock, "typeCode", "SCH");

		
		Externalstudentreports dto1 = new Externalstudentreports();
		dto1.setGradeId(10L);
		dto1.setSchoolId(444L);
		dto1.setSubjectId(440L);
		dto1.setSchoolYear(2016L);
		dto1.setFilePath("filename.pdf");
		MockHttpServletRequest request = new MockHttpServletRequest();
	    MockHttpServletResponse response = new MockHttpServletResponse();
	    
		PowerMockito.when(FileUtil.buildFilePath(anyString(),anyString())).thenReturn(dto1.getFilePath());
		PowerMockito.doNothing().when(DownloadController.class, "download", request, response, dto1.getFilePath());
		
		when(studentReportServiceMock.getExternalReportByPrimaryKeyForAllStudentReports(anyLong(), anyLong(), anyString(), anyLong(), anyString())).thenReturn(dto1);
		
        mockMvc.perform(get("/getExternalStudentReportFileForAllStudentReports.htm")
        .param("id","1")
        .param("ssid", "1122334455"))
        .andDo(print())
        .andExpect(status().isOk());
        
        verify(studentReportServiceMock, times(1)).getExternalReportByPrimaryKeyForAllStudentReports(anyLong(), anyLong(), anyString(), anyLong(), anyString());
        verify(orgServiceMock,times(1)).get(anyLong());
        verifyNoMoreInteractions(studentReportServiceMock);
        verifyNoMoreInteractions(orgServiceMock);
	}
	
	
	@Test
	public void getExternalStudentReportFileForAllStudentReports_NoReportTest() throws Exception{
		setUpUserMocks_Standard();
		
		Organization orgMock = mock(Organization.class);
		OrganizationType orgTypeMock = mock(OrganizationType.class);
		mockStatic(FileUtil.class);
		mockStatic(DownloadController.class);
		
		when(orgServiceMock.get(anyLong())).thenReturn(orgMock);
		Whitebox.setInternalState(orgMock, "organizationType", orgTypeMock);
		Whitebox.setInternalState(orgMock, "id", 1L);
		Whitebox.setInternalState(orgTypeMock, "typeCode", "SCH");

		Externalstudentreports dto1 = new Externalstudentreports();
		dto1.setGradeId(10L);
		dto1.setSchoolId(444L);
		dto1.setSubjectId(440L);
		dto1.setSchoolYear(2016L);
		dto1.setFilePath("filename.pdf");
	   
		
		when(studentReportServiceMock.getExternalReportByPrimaryKeyForAllStudentReports(anyLong(), anyLong(), anyString(), anyLong(), anyString())).thenReturn(null);
		
        mockMvc.perform(get("/getExternalStudentReportFileForAllStudentReports.htm")
        .param("id","1")
        .param("ssid", "1122334455"))
        .andDo(print())
        .andExpect(status().is3xxRedirection());
        
        verify(studentReportServiceMock, times(1)).getExternalReportByPrimaryKeyForAllStudentReports(anyLong(), anyLong(), anyString(), anyLong(), anyString());
        verify(orgServiceMock,times(1)).get(anyLong());
        verifyNoMoreInteractions(studentReportServiceMock);
        verifyNoMoreInteractions(orgServiceMock);
	}
	
	@Test
	public void getExternalStudentReportFileForAllStudentReports_UnAuthorizedTest() throws Exception{
		setUpUserMocks_UnAuthorized();
 
        mockMvc.perform(get("/getExternalStudentReportFileForAllStudentReports.htm")
        .param("id","1")
        .param("ssid", "1122334455"))
        .andDo(print())
        .andExpect(status().isOk());
 
        verify(studentReportServiceMock, times(0)).getExternalStudentReportsForTeacherRoster(anyMapOf(String.class, Object.class));
        verifyNoMoreInteractions(studentReportServiceMock);
	}
}