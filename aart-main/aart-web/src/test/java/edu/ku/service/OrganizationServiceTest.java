/**
 * 
 */
package edu.ku.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
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

import edu.ku.cete.domain.common.OrganizationDetail;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.impl.OrganizationServiceImpl;

@RunWith(PowerMockRunner.class)
public class OrganizationServiceTest {
	
	@Mock
	private OrganizationDao organizationDao;
	
    @Before
    public void setUp() {
    	MockitoAnnotations.initMocks(this);
    }
    
    @Test
	public void getOrganizationDetailsTest(){
		
		OrganizationService organizationService = new OrganizationServiceImpl();
		Whitebox.setInternalState(organizationService, "organizationDao", organizationDao);
				
		OrganizationDetail organizationDetail1 = new OrganizationDetail();
		organizationDetail1.setId(185L);
		organizationDetail1.setOrganizationId(51L);
		organizationDetail1.setCreatedUser(89033);
		
		OrganizationDetail organizationDetail2 = new OrganizationDetail();
		organizationDetail2.setId(108L);
		organizationDetail2.setOrganizationId(77540L);
		organizationDetail2.setCreatedUser(89033);
		
		List<OrganizationDetail> list = Arrays.asList(organizationDetail1, organizationDetail2);
		
		when(organizationDao.getOrganizationDetails(anyLong())).thenReturn(list);

		Long id = 185L;
				
		List<OrganizationDetail> reports = organizationService.getOrganizationDetails(id);
		
        verify(organizationDao, times(1)).getOrganizationDetails(anyLong());
        verifyNoMoreInteractions(organizationDao);
        
        OrganizationDetail dto1 = reports.get(0);
        OrganizationDetail dto2 = reports.get(1);
        
        assertTrue(dto1.getOrganizationId().equals(51L));
        assertTrue(dto1.getCreatedUser().equals(89033));
        assertTrue(dto2.getOrganizationId().equals(77540L));
        assertTrue(dto2.getCreatedUser().equals(89033));
	}
    
}