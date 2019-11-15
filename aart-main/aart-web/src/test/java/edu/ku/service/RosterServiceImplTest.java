package edu.ku.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.modules.junit4.PowerMockRunner;

import edu.ku.cete.domain.Authorities;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.model.AuthoritiesDao;
import edu.ku.cete.model.enrollment.RosterDao;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.service.impl.enrollment.RosterServiceImpl;

@RunWith(PowerMockRunner.class)
public class RosterServiceImplTest {
	
	@Mock
	private AuthoritiesDao authoritiesDaoMock;
	
	@Mock
	private RosterDao rosterDaoMock;
	
	@Mock
	private AssessmentProgramService assessmentProgramServiceMock;
	
    @Before
    public void setUp() {
    	MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void getRostersForTeacherReports_DLM_Test() {
		RosterService rosterService = new RosterServiceImpl();
		Whitebox.setInternalState(rosterService, "assessmentProgramService", assessmentProgramServiceMock);
		Whitebox.setInternalState(rosterService, "authoritiesDao", authoritiesDaoMock);
		Whitebox.setInternalState(rosterService, "rosterDao", rosterDaoMock);
		
		AssessmentProgram ap = new AssessmentProgram();
		ap.setAbbreviatedname("DLM");
		Authorities auth = new Authorities();
		
		Roster roster = new Roster();
		roster.setCourseSectionName("Ms. English ELA");
		List<Roster> roasters =  Arrays.asList(roster);
		
		Long userId = 1L;
		Long currentSchoolYear = 2017L;
		Long apId = 3L;
		Long groupId = 9681L;
		Long organizationId = 2201L;
		
		when(assessmentProgramServiceMock.findByAssessmentProgramId(anyLong())).thenReturn(ap);
		when(authoritiesDaoMock.getByAuthority(anyString())).thenReturn(auth);
		when(rosterDaoMock.getRosterForTeacherReports(anyLong(), anyLong(), anyLong(), anyLong(), anyLong(), anyLong())).thenReturn(roasters);
		
		List<Roster> rosters = rosterService.getRostersForTeacherReports(userId, currentSchoolYear, apId, groupId, organizationId);
		Roster roster1 = rosters.get(0);
		
        verify(assessmentProgramServiceMock, times(1)).findByAssessmentProgramId(anyLong());
        verifyNoMoreInteractions(assessmentProgramServiceMock);
        verify(authoritiesDaoMock, times(1)).getByAuthority(anyString());
        verifyNoMoreInteractions(authoritiesDaoMock);
        verify(rosterDaoMock, times(1)).getRosterForTeacherReports(anyLong(), anyLong(), anyLong(), anyLong(), anyLong(), anyLong());
        verifyNoMoreInteractions(rosterDaoMock);
		
		assertTrue(roster1.getCourseSectionName().equals("Ms. English ELA"));
    }
    
    @Test
    public void getRostersForTeacherReports_CPASS_Test() {
		RosterService rosterService = new RosterServiceImpl();
		Whitebox.setInternalState(rosterService, "assessmentProgramService", assessmentProgramServiceMock);
		Whitebox.setInternalState(rosterService, "authoritiesDao", authoritiesDaoMock);
		Whitebox.setInternalState(rosterService, "rosterDao", rosterDaoMock);
		
		AssessmentProgram ap = new AssessmentProgram();
		ap.setAbbreviatedname("CPASS");
		Authorities auth = new Authorities();
		
		Roster roster = new Roster();
		roster.setCourseSectionName("Ms. English ELA");
		List<Roster> roasters =  Arrays.asList(roster);
		
		Long userId = 1L;
		Long currentSchoolYear = 2017L;
		Long apId = 3L;
		Long groupId = 9681L;
		Long organizationId = 2201L;
		
		when(assessmentProgramServiceMock.findByAssessmentProgramId(anyLong())).thenReturn(ap);
		when(authoritiesDaoMock.getByAuthority(anyString())).thenReturn(auth);
		when(rosterDaoMock.getRosterForTeacherReports(anyLong(), anyLong(), anyLong(), anyLong(), anyLong(), anyLong())).thenReturn(roasters);
		
		List<Roster> rosters = rosterService.getRostersForTeacherReports(userId, currentSchoolYear, apId, groupId, organizationId);
		Roster roster1 = rosters.get(0);
		
        verify(assessmentProgramServiceMock, times(1)).findByAssessmentProgramId(anyLong());
        verifyNoMoreInteractions(assessmentProgramServiceMock);
        verify(authoritiesDaoMock, times(1)).getByAuthority(anyString());
        verifyNoMoreInteractions(authoritiesDaoMock);
        verify(rosterDaoMock, times(1)).getRosterForTeacherReports(anyLong(), anyLong(), anyLong(), anyLong(), anyLong(), anyLong());
        verifyNoMoreInteractions(rosterDaoMock);
		
		assertTrue(roster1.getCourseSectionName().equals("Ms. English ELA"));
    }

    @Test
    public void getRostersForTeacherReports_OTHER_Test() {
		RosterService rosterService = new RosterServiceImpl();
		Whitebox.setInternalState(rosterService, "assessmentProgramService", assessmentProgramServiceMock);
		Whitebox.setInternalState(rosterService, "authoritiesDao", authoritiesDaoMock);
		Whitebox.setInternalState(rosterService, "rosterDao", rosterDaoMock);
		
		AssessmentProgram ap = new AssessmentProgram();
		ap.setAbbreviatedname("KAP");
		
		Long userId = 1L;
		Long currentSchoolYear = 2017L;
		Long apId = 3L;
		Long groupId = 9681L;
		Long organizationId = 2201L;
		when(assessmentProgramServiceMock.findByAssessmentProgramId(anyLong())).thenReturn(ap);
		when(authoritiesDaoMock.getByAuthority(anyString())).thenReturn(null);

		List<Roster> rosters = rosterService.getRostersForTeacherReports(userId, currentSchoolYear, apId, groupId, organizationId);
		
        verify(assessmentProgramServiceMock, times(1)).findByAssessmentProgramId(anyLong());
        verifyNoMoreInteractions(assessmentProgramServiceMock);
        verify(authoritiesDaoMock, times(1)).getByAuthority(anyString());
        verifyNoMoreInteractions(authoritiesDaoMock);
        verifyNoMoreInteractions(rosterDaoMock);
		
    }
}
