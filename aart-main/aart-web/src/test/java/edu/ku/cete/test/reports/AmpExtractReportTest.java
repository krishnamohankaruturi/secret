/**
 * 
 */
package edu.ku.cete.test.reports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.service.report.AmpDataExtractService;
import edu.ku.cete.test.BaseTest;
import edu.ku.cete.util.StageEnum;
import edu.ku.cete.web.AmpExtractStudentProfileItemAttributeDTO;
import edu.ku.cete.web.AmpExtractStudentSujectSectionItemCountDTO;
import edu.ku.cete.web.AmpStudentDataExtractDTO;

/**
 * @author ktaduru_sta
 *
 */
public class AmpExtractReportTest extends BaseTest {

	@Autowired
	private AmpDataExtractService ampDataExtractService;
	
//	@Autowired
//	private DataReportService dataReportService;
		
	@Test
	public void testGetTestCollections() {
		ArrayList otwLst = new ArrayList();
		otwLst.add(2565L);
		List<Long>testCollections = ampDataExtractService.getAMPTestCollections(2016, otwLst);
		assertNotNull(testCollections);
	}
	
	@Test
	public void testGetStudentDataInTestCollection() {
		ArrayList testColls = new ArrayList();
		testColls.add(1630L);
		List<AmpStudentDataExtractDTO>studentDetails = ampDataExtractService.getStudentData(testColls, 2016);
		//assertEquals(null,pendingUploadRecord);
		assertNotEquals(null,studentDetails);
		System.out.println(studentDetails.size());
		//assertNotNull(pendingUploadRecord);
	}
	
	@Test
	public void testGetStudentProfileItemAttributes() {
		ArrayList testColls = new ArrayList();
		testColls.add(1630L);
		HashMap<Long, HashMap<Long,List<AmpExtractStudentProfileItemAttributeDTO>>>studentProfileAttDetails = 
				ampDataExtractService.getStudentProfileItemAttributes(testColls, 2016);
		//assertEquals(null,pendingUploadRecord);
		assertNotEquals(null,studentProfileAttDetails);
		System.out.println(studentProfileAttDetails.size());
		//assertNotNull(pendingUploadRecord);
	}
	
	@Test
	public void testGetStudentSubjectTotalItemCount() {
		ArrayList testColls = new ArrayList();
		testColls.add(1630L);
		 HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> studentSubjectTotalItemDetails = 
				 ampDataExtractService.getStudentSubjectTotalItemsCount(testColls, 2016);
		//assertEquals(null,pendingUploadRecord);
		assertNotEquals(null,studentSubjectTotalItemDetails);
		assertNotEquals(null,studentSubjectTotalItemDetails.keySet().contains(889522));
		System.out.println(studentSubjectTotalItemDetails.keySet());
		//assertNotNull(pendingUploadRecord);
	}
	
	@Test
	public void testGetStudentSubjectTotalIncItemCount() {
		ArrayList otwLst = new ArrayList();
		otwLst.add(2565L);
		 HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> studentSubjectTotalIncItemDetails = 
				 ampDataExtractService.getStudentSubjectTotalIncludedItemsCount(ampDataExtractService.getAMPTestCollections(2016, otwLst), 2016);
		 System.out.println(studentSubjectTotalIncItemDetails.keySet());
		//assertEquals(null,pendingUploadRecord);
		assertNotEquals(null,studentSubjectTotalIncItemDetails);
		assertNotEquals(null,studentSubjectTotalIncItemDetails.keySet().contains(892431));
		HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>> totIncItemMap = studentSubjectTotalIncItemDetails.get(892431L);
		List<AmpExtractStudentSujectSectionItemCountDTO> totIncItemCountsLst = totIncItemMap.get("studentSubjectSectionCountLst");
		for(AmpExtractStudentSujectSectionItemCountDTO totIncItemRec : totIncItemCountsLst){
			if(totIncItemRec.getStageId().equals(StageEnum.STAGE1.getCode())){
				assertEquals(totIncItemRec.getTotalCount().longValue(), 6L);
			}
		}
	}
	
	
}
