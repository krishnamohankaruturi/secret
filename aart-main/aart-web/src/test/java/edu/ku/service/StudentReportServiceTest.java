/**
 * 
 */
package edu.ku.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.modules.junit4.PowerMockRunner;

import edu.ku.cete.dataextracts.model.YearOverYearReportMapper;
import edu.ku.cete.domain.Authorities;
import edu.ku.cete.domain.Externalstudentreports;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.model.AuthoritiesDao;
import edu.ku.cete.model.ExternalstudentreportsMapper;
import edu.ku.cete.service.StudentReportService;
import edu.ku.cete.service.impl.StudentReportServiceImpl;
import edu.ku.cete.web.ExternalStudentReportDTO;
import edu.ku.cete.web.StudentReportDTO;


@RunWith(PowerMockRunner.class)
public class StudentReportServiceTest {
	@Mock
	private ExternalstudentreportsMapper externalstudentreportsMapperMock;
	
	@Mock
	private AuthoritiesDao authoritiesDao;
	
	@Mock
	private YearOverYearReportMapper yearOverYearReportMapper;
	
    @Before
    public void setUp() {
    	MockitoAnnotations.initMocks(this);
    }
 
	@Test
	public void getExternalStudentReportsForTeacherRosterTest(){
		
		StudentReportService studentReportService = new StudentReportServiceImpl();
		Whitebox.setInternalState(studentReportService, "externalstudentreportsMapper", externalstudentreportsMapperMock);
		
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
		
		when(externalstudentreportsMapperMock.selectByCriteriaForTeacherRoster(anyMapOf(String.class, Object.class))).thenReturn(list);

		
		List<Long> apIds = new ArrayList<Long>();
		apIds.add(11L);
		Long rosterId = 1L;
		Long reportYear = 2016L;
		Long page = 1L;
		Long perPage = 15L;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("assessmentProgramIds", apIds);
		params.put("rosterId", rosterId);
		params.put("reportYear", reportYear);
		params.put("offset", (page - 1) * perPage);
		params.put("limit", perPage);
		
		List<ExternalStudentReportDTO> reports = studentReportService.getExternalStudentReportsForTeacherRoster(params);
		
        verify(externalstudentreportsMapperMock, times(1)).selectByCriteriaForTeacherRoster(anyMapOf(String.class, Object.class));
        verifyNoMoreInteractions(externalstudentreportsMapperMock);
        
		assertTrue(reports.size() == 2);
		ExternalStudentReportDTO dto1 = reports.get(0);
		assertTrue(dto1.getId().equals(1L));
		ExternalStudentReportDTO dto2 = reports.get(1);
		assertTrue(dto2.getId().equals(2L));
	}
	
	@Test
	public void countExternalStudentReportsForTeacherRosterTest(){
		
		StudentReportService studentReportService = new StudentReportServiceImpl();
		Whitebox.setInternalState(studentReportService, "externalstudentreportsMapper", externalstudentreportsMapperMock);
		
		when(externalstudentreportsMapperMock.countByCriteriaForTeacherRoster(anyMapOf(String.class, Object.class))).thenReturn(2L);

		List<Long> apIds = new ArrayList<Long>();
		apIds.add(11L);
		Long rosterId = 1L;
		Long reportYear = 2016L;
		Long page = 1L;
		Long perPage = 15L;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("assessmentProgramIds", apIds);
		params.put("rosterId", rosterId);
		params.put("reportYear", reportYear);
		params.put("offset", (page - 1) * perPage);
		params.put("limit", perPage);
		
		Long count = studentReportService.countExternalStudentReportsForTeacherRoster(params);
		
        verify(externalstudentreportsMapperMock, times(1)).countByCriteriaForTeacherRoster(anyMapOf(String.class, Object.class));
        verifyNoMoreInteractions(externalstudentreportsMapperMock);
        
		assertTrue(count == 2);
	}
	
	@Test
	public void searchByStudentLastNameTest(){
		
		StudentReportService studentReportService = new StudentReportServiceImpl();
		Whitebox.setInternalState(studentReportService, "authoritiesDao", authoritiesDao);
		Whitebox.setInternalState(studentReportService, "yearOverYearReportMapper", yearOverYearReportMapper);
		
		Authorities auth = new Authorities();
		
		StudentReportDTO studentReportOne = new StudentReportDTO();
		studentReportOne.setId(1L);
		studentReportOne.setAttendanceSchoolId(1L);
		studentReportOne.setDistrictId(10L);
		studentReportOne.setStateId(1L);
		studentReportOne.setFilePath("testPath1");
		studentReportOne.setStateStudentIdentifier("123456789");
		studentReportOne.setSchoolName("Test School 1");
		studentReportOne.setStudentLastName("Smithe");
		
		StudentReportDTO studentReportTwo = new StudentReportDTO();
		studentReportTwo.setId(2L);
		studentReportTwo.setAttendanceSchoolId(1L);
		studentReportTwo.setDistrictId(10L);
		studentReportTwo.setStateId(1L);
		studentReportTwo.setFilePath("testPath2");
		studentReportTwo.setStateStudentIdentifier("987654321");
		studentReportTwo.setSchoolName("Test School 1");
		studentReportTwo.setStudentLastName("Smithers");
		
		List<StudentReportDTO> list = Arrays.asList(studentReportOne, studentReportTwo);
		
		when(authoritiesDao.getByAuthority(anyString())).thenReturn(auth);
		when(yearOverYearReportMapper.searchByStudentLastName(anyString(), anyLong(), anyString(), anyLong(), anyInt(), anyInt(), anyLong(), anyLong(), anyLong(),anyLong(), anyString())).thenReturn(list);

		Long reportYear = 2016L;
		Integer offset = 1;
		Integer limitCount = 15;
		Long orgId = 9991L;
		Long userId = 1L;
		String studentLastname = "Smith";
		String currentUserLevel = "SCH";
		Long groupId = 9861L;
		Long currentSchoolYear = 2017L;
		Groups groups = new Groups();
		groups.setGroupCode("TEA");
		
		List<StudentReportDTO> reports = studentReportService.searchByStudentLastName(studentLastname, userId, currentUserLevel, orgId, limitCount, offset, groupId, currentSchoolYear, reportYear, groups);
		
        verify(authoritiesDao, times(1)).getByAuthority(anyString());
        verifyNoMoreInteractions(authoritiesDao);
        verify(yearOverYearReportMapper, times(1)).searchByStudentLastName(anyString(), anyLong(), anyString(), anyLong(), anyInt(), anyInt(), anyLong(), anyLong(), anyLong(),anyLong(), anyString());
        verifyNoMoreInteractions(yearOverYearReportMapper);
        
        StudentReportDTO dto1 = reports.get(0);
        StudentReportDTO dto2 = reports.get(1);
        
        assertTrue(dto1.getStudentLastName().equals("Smithe"));
        assertTrue(dto1.getStateStudentIdentifier().equals("123456789"));
        assertTrue(dto2.getStudentLastName().equals("Smithers"));
        assertTrue(dto2.getStateStudentIdentifier().equals("987654321"));
	}
	
	@Test
	public void searchByStateStudentIdForKAPTest(){
		
		StudentReportService studentReportService = new StudentReportServiceImpl();
		Whitebox.setInternalState(studentReportService, "authoritiesDao", authoritiesDao);
		Whitebox.setInternalState(studentReportService, "yearOverYearReportMapper", yearOverYearReportMapper);
		
		Authorities auth = new Authorities();
		
		StudentReportDTO studentReportOne = new StudentReportDTO();
		studentReportOne.setId(1L);
		studentReportOne.setAttendanceSchoolId(1L);
		studentReportOne.setDistrictId(10L);
		studentReportOne.setStateId(1L);
		studentReportOne.setFilePath("testPath1");
		studentReportOne.setStateStudentIdentifier("123456789");
		studentReportOne.setSchoolName("Test School 1");
		studentReportOne.setStudentLastName("Smithe");
		
		
		List<StudentReportDTO> list = Arrays.asList(studentReportOne);
		
		when(authoritiesDao.getByAuthority(anyString())).thenReturn(auth);
		when(yearOverYearReportMapper.searchByStateStudentIdForKAP(anyString(), anyLong(), anyString(), anyLong(), anyBoolean(), anyLong(), anyLong(), anyLong(),anyLong())).thenReturn(list);

		Long reportYear = 2016L;
		Long orgId = 9991L;
		Long userId = 1L;
		String stateStudentIdentifier = "123456789";
		String currentUserLevel = "SCH";
		Long groupId = 9861L;
		Long currentSchoolYear = 2017L;
		
		List<StudentReportDTO> reports = studentReportService.searchByStateStudentIdForKAP(stateStudentIdentifier, userId, currentUserLevel, orgId, groupId, currentSchoolYear, reportYear);
		
		verify(yearOverYearReportMapper, times(1)).isStudentCurrentlyEnrolledInUserOrgHierarchy(anyString(), anyString(), anyLong(), anyLong());
        verify(authoritiesDao, times(1)).getByAuthority(anyString());
        verifyNoMoreInteractions(authoritiesDao);
        verify(yearOverYearReportMapper, times(1)).searchByStateStudentIdForKAP(anyString(), anyLong(), anyString(), anyLong(), anyBoolean(), anyLong(), anyLong(), anyLong(),anyLong());
        verifyNoMoreInteractions(yearOverYearReportMapper);
        
        StudentReportDTO dto1 = reports.get(0);
        
        assertTrue(dto1.getStudentLastName().equals("Smithe"));
        assertTrue(dto1.getStateStudentIdentifier().equals("123456789"));
	}
	
	@Test
	public void searchByStateStudentIdForDLMTest(){
		
		StudentReportService studentReportService = new StudentReportServiceImpl();
		Whitebox.setInternalState(studentReportService, "authoritiesDao", authoritiesDao);
		Whitebox.setInternalState(studentReportService, "yearOverYearReportMapper", yearOverYearReportMapper);
		
		Authorities auth = new Authorities();
		
		StudentReportDTO studentReportOne = new StudentReportDTO();
		studentReportOne.setId(1L);
		studentReportOne.setAttendanceSchoolId(1L);
		studentReportOne.setDistrictId(10L);
		studentReportOne.setStateId(1L);
		studentReportOne.setFilePath("testPath1");
		studentReportOne.setStateStudentIdentifier("123456789");
		studentReportOne.setSchoolName("Test School 1");
		studentReportOne.setStudentLastName("Smithe");
		
		List<StudentReportDTO> list = Arrays.asList(studentReportOne);
		
		when(authoritiesDao.getByAuthority(anyString())).thenReturn(auth);
		when(yearOverYearReportMapper.searchByStateStudentIdForDLMOrCPASS(anyString(), anyLong(), anyString(), anyLong(), anyListOf(String.class), anyBoolean(), anyLong(), anyLong(), anyLong(),anyLong(), anyString())).thenReturn(list);

		Long reportYear = 2016L;
		Long orgId = 9991L;
		Long userId = 1L;
		String stateStudentIdentifier = "123456789";
		String currentUserLevel = "SCH";
		Long groupId = 9861L;
		Long currentSchoolYear = 2017L;
		Groups groups = new Groups();
		groups.setGroupCode("TEA");
		
		List<StudentReportDTO> reports = studentReportService.searchByStateStudentIdForDLM(stateStudentIdentifier, userId, currentUserLevel, orgId, groupId, currentSchoolYear, reportYear, groups);
		
		verify(yearOverYearReportMapper, times(1)).isStudentCurrentlyEnrolledInUserOrgHierarchy(anyString(), anyString(), anyLong(), anyLong());
        verify(authoritiesDao, times(1)).getByAuthority(anyString());
        verifyNoMoreInteractions(authoritiesDao);
        verify(yearOverYearReportMapper, times(1)).searchByStateStudentIdForDLMOrCPASS(anyString(), anyLong(), anyString(), anyLong(), anyListOf(String.class), anyBoolean(), anyLong(), anyLong(), anyLong(),anyLong(), anyString());
        verifyNoMoreInteractions(yearOverYearReportMapper);
        
        StudentReportDTO dto1 = reports.get(0);
        
        assertTrue(dto1.getStudentLastName().equals("Smithe"));
        assertTrue(dto1.getStateStudentIdentifier().equals("123456789"));
	}	
	
	@Test
	public void searchByStateStudentIdForCPASSTest(){
		
		StudentReportService studentReportService = new StudentReportServiceImpl();
		Whitebox.setInternalState(studentReportService, "authoritiesDao", authoritiesDao);
		Whitebox.setInternalState(studentReportService, "yearOverYearReportMapper", yearOverYearReportMapper);
		
		Authorities auth = new Authorities();
		
		StudentReportDTO studentReportOne = new StudentReportDTO();
		studentReportOne.setId(1L);
		studentReportOne.setAttendanceSchoolId(1L);
		studentReportOne.setDistrictId(10L);
		studentReportOne.setStateId(1L);
		studentReportOne.setFilePath("testPath1");
		studentReportOne.setStateStudentIdentifier("123456789");
		studentReportOne.setSchoolName("Test School 1");
		studentReportOne.setStudentLastName("Smithe");
		
		List<StudentReportDTO> list = Arrays.asList(studentReportOne);
		
		when(authoritiesDao.getByAuthority(anyString())).thenReturn(auth);
		when(yearOverYearReportMapper.searchByStateStudentIdForDLMOrCPASS(anyString(), anyLong(), anyString(), anyLong(), anyListOf(String.class), anyBoolean(), anyLong(), anyLong(), anyLong(),anyLong(), anyString())).thenReturn(list);

		Long reportYear = 2016L;
		Long orgId = 9991L;
		Long userId = 1L;
		String stateStudentIdentifier = "123456789";
		String currentUserLevel = "SCH";
		Long groupId = 9861L;
		Long currentSchoolYear = 2017L;
		Groups groups = new Groups();
		groups.setGroupCode("TEA");
		
		List<StudentReportDTO> reports = studentReportService.searchByStateStudentIdForCPASS(stateStudentIdentifier, userId, currentUserLevel, orgId, groupId, currentSchoolYear, reportYear, groups);
		
		verify(yearOverYearReportMapper, times(1)).isStudentCurrentlyEnrolledInUserOrgHierarchy(anyString(), anyString(), anyLong(), anyLong());
        verify(authoritiesDao, times(1)).getByAuthority(anyString());
        verifyNoMoreInteractions(authoritiesDao);
        verify(yearOverYearReportMapper, times(1)).searchByStateStudentIdForDLMOrCPASS(anyString(), anyLong(), anyString(), anyLong(), anyListOf(String.class), anyBoolean(), anyLong(), anyLong(), anyLong(),anyLong(), anyString());
        verifyNoMoreInteractions(yearOverYearReportMapper);
        
        StudentReportDTO dto1 = reports.get(0);
        
        assertTrue(dto1.getStudentLastName().equals("Smithe"));
        assertTrue(dto1.getStateStudentIdentifier().equals("123456789"));
	}
	
	@Test
	public void getStudentInfoForAllStudentsReportsTest(){
		
		StudentReportService studentReportService = new StudentReportServiceImpl();
		Whitebox.setInternalState(studentReportService, "yearOverYearReportMapper", yearOverYearReportMapper);
		
		
		StudentReportDTO studentReportOne = new StudentReportDTO();
		studentReportOne.setId(1L);
		studentReportOne.setAttendanceSchoolId(1L);
		studentReportOne.setDistrictId(10L);
		studentReportOne.setStateId(1L);
		studentReportOne.setFilePath("testPath1");
		studentReportOne.setStateStudentIdentifier("123456789");
		studentReportOne.setSchoolName("Test School 1");
		studentReportOne.setStudentLastName("Smithe");

		
		when(yearOverYearReportMapper.getStudentInfoForAllStudentsReports(anyString(), anyLong(), anyString(), anyLong(), anyLong(), anyLong())).thenReturn(studentReportOne);

		Long orgId = 9991L;
		Long userId = 1L;
		String stateStudentIdentifier = "123456789";
		String currentUserLevel = "SCH";
		Long groupId = 9861L;
		Long currentSchoolYear = 2017L;
		Groups groups = new Groups();
		groups.setGroupCode("TEA");
		
		StudentReportDTO report = studentReportService.getStudentInfoForAllStudentsReports(stateStudentIdentifier, userId, currentUserLevel, orgId, currentSchoolYear, groupId);
		
        verify(yearOverYearReportMapper, times(1)).getStudentInfoForAllStudentsReports(anyString(), anyLong(), anyString(), anyLong(), anyLong(), anyLong());
        verifyNoMoreInteractions(yearOverYearReportMapper);
        
        assertTrue(report.getStudentLastName().equals("Smithe"));
        assertTrue(report.getStateStudentIdentifier().equals("123456789"));
	}
	
	@Test
	public void countReportsForStudentTest(){
		
		StudentReportService studentReportService = new StudentReportServiceImpl();
		Whitebox.setInternalState(studentReportService, "authoritiesDao", authoritiesDao);
		Whitebox.setInternalState(studentReportService, "yearOverYearReportMapper", yearOverYearReportMapper);
		
		Authorities auth = new Authorities();
		
		when(authoritiesDao.getByAuthority(anyString())).thenReturn(auth);
		when(yearOverYearReportMapper.countReportsForStudent(anyString(), anyLong(), anyString(), anyLong(), anyListOf(String.class), anyBoolean(), anyLong(), anyLong(), anyLong(), anyLong(), anyString())).thenReturn(2);
		
		Long reportYear = 2016L;
		Long orgId = 9991L;
		Long userId = 1L;
		String stateStudentIdentifier = "123456789";
		String currentUserLevel = "SCH";
		Long groupId = 9861L;
		Long currentSchoolYear = 2017L;
		Groups groups = new Groups();
		groups.setGroupCode("TEA");
		
		int count = studentReportService.countReportsForStudent(stateStudentIdentifier, userId, currentUserLevel, orgId, groupId, currentSchoolYear, reportYear, groups);
		
		verify(yearOverYearReportMapper, times(1)).isStudentCurrentlyEnrolledInUserOrgHierarchy(anyString(), anyString(), anyLong(), anyLong());
        verify(authoritiesDao, times(1)).getByAuthority(anyString());
        verifyNoMoreInteractions(authoritiesDao);
        verify(yearOverYearReportMapper, times(1)).countReportsForStudent(anyString(), anyLong(), anyString(), anyLong(), anyListOf(String.class), anyBoolean(), anyLong(), anyLong(), anyLong(), anyLong(), anyString());
        verifyNoMoreInteractions(yearOverYearReportMapper);
        
		assertTrue(count == 2);
	}
	
	@Test
	public void doesSSIDExistTest(){
		
		StudentReportService studentReportService = new StudentReportServiceImpl();
		Whitebox.setInternalState(studentReportService, "yearOverYearReportMapper", yearOverYearReportMapper);
		
		when(yearOverYearReportMapper.doesSSIDExist(anyString(), anyLong(), anyLong())).thenReturn(true);
		
		String stateStudentIdentifier = "123456789";
		Long currentSchoolYear = 2017L;
		Long stateId = 9591L;
		
		boolean exists = studentReportService.doesSSIDExist(stateStudentIdentifier, stateId, currentSchoolYear);
		
        verify(yearOverYearReportMapper, times(1)).doesSSIDExist(anyString(), anyLong(), anyLong());
        verifyNoMoreInteractions(yearOverYearReportMapper);
        
		assertTrue(exists);
	}
	
	@Test
	public void getByPrimaryKeyForAllStudentReportsTest(){
		
		StudentReportService studentReportService = new StudentReportServiceImpl();
		Whitebox.setInternalState(studentReportService, "yearOverYearReportMapper", yearOverYearReportMapper);
		
		StudentReport dto1 = new StudentReport();
		dto1.setStudentLastName("Smithe");
		dto1.setStudentFirstName("Jamie");
		dto1.setStateStudentIdentifier("1122334455");
		dto1.setGradeName("10");
		dto1.setAttendanceSchoolName("School 1");
		dto1.setContentAreaName("Math");
		dto1.setSchoolYear(2016L);
		dto1.setFilePath("filename.pdf");
		
		when(yearOverYearReportMapper.selectStudentReportByPrimaryKeyAndUserOrgForAllStudentReports(anyLong(), anyLong(), anyBoolean())).thenReturn(dto1);
		
		String stateStudentIdentifier = "123456789";
		Long currentSchoolYear = 2017L;
		Long orgId = 9591L;
		Long id = 1L;
		String currentUserLevel = "SCH";
		
		StudentReport report = studentReportService.getByPrimaryKeyForAllStudentReports( id,  orgId,  currentUserLevel,  currentSchoolYear,  stateStudentIdentifier);
		
        verify(yearOverYearReportMapper, times(1)).isStudentCurrentlyEnrolledInUserOrgHierarchy(anyString(), anyString(), anyLong(), anyLong());
        verify(yearOverYearReportMapper, times(1)).selectStudentReportByPrimaryKeyAndUserOrgForAllStudentReports(anyLong(), anyLong(), anyBoolean());
        verifyNoMoreInteractions(yearOverYearReportMapper);
        
		assertEquals(dto1.getStateStudentIdentifier(), report.getStateStudentIdentifier());
		assertEquals(dto1.getStudentLastName(), report.getStudentLastName());
	}
	
	@Test
	public void getExternalReportByPrimaryKeyForAllStudentReportsTest(){
		
		StudentReportService studentReportService = new StudentReportServiceImpl();
		Whitebox.setInternalState(studentReportService, "yearOverYearReportMapper", yearOverYearReportMapper);
		
		Externalstudentreports dto1 = new Externalstudentreports();
		dto1.setStudentId(1L);
		dto1.setFilePath("filename.pdf");
		
		when(yearOverYearReportMapper.selectExternalStudentReportByPrimaryKeyAndUserOrgForAllStudentReports(anyLong(), anyLong(), anyBoolean())).thenReturn(dto1);
		
		String stateStudentIdentifier = "123456789";
		Long currentSchoolYear = 2017L;
		Long orgId = 9591L;
		Long id = 1L;
		String currentUserLevel = "SCH";
		
		Externalstudentreports report = studentReportService.getExternalReportByPrimaryKeyForAllStudentReports( id,  orgId,  currentUserLevel,  currentSchoolYear,  stateStudentIdentifier);
		
        verify(yearOverYearReportMapper, times(1)).isStudentCurrentlyEnrolledInUserOrgHierarchy(anyString(), anyString(), anyLong(), anyLong());
        verify(yearOverYearReportMapper, times(1)).selectExternalStudentReportByPrimaryKeyAndUserOrgForAllStudentReports(anyLong(), anyLong(), anyBoolean());
        verifyNoMoreInteractions(yearOverYearReportMapper);
        
		assertEquals(dto1.getStudentId(), report.getStudentId());
		assertEquals(dto1.getFilePath(), report.getFilePath());
	}
}
