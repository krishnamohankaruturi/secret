
package edu.ku.cete.util.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JQGridRow;
import edu.ku.cete.domain.JsonResultSet;
import edu.ku.cete.domain.Row;
import edu.ku.cete.domain.JsonSummaryData;
import edu.ku.cete.domain.common.StudentRosterSummaryData;
import edu.ku.cete.domain.test.OperationalTestWindowDTO;
import edu.ku.cete.domain.testsession.AutoRegisteredTestSessionDTO;
import edu.ku.cete.domain.testsession.TestSessionRoster;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.web.AssessmentProgramTCDTO;

/**
 * @author mahesh
 *
 */
public class OperationalTestWindowJSONConverter {
	
	public static JQGridJSONModel convertToTestWindowJson(
			Collection<OperationalTestWindowDTO> testWindowTestSessions, int totalCount, int page, int limitCount) {
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		
		if (testWindowTestSessions != null
				&& CollectionUtils.isNotEmpty(testWindowTestSessions)) {
			for (OperationalTestWindowDTO operationalTestWindowDTO : testWindowTestSessions) {
				row = new JQGridRow();
				row.setId(operationalTestWindowDTO.getOtwId());
				row.setCell(operationalTestWindowDTO.buildJSONRow());				
				rows.add(row);
			}
		}

		jqGridJSONModel.setRows(rows);
		jqGridJSONModel.setRecords(totalCount);
		jqGridJSONModel.setTotal(
				NumericUtil.getPageCount(
						totalCount, limitCount));
		jqGridJSONModel.setPage(page);
		
		return jqGridJSONModel;
	}

	public static JQGridJSONModel convertToTestCollectionGridJson(List<AssessmentProgramTCDTO> assessmentProgramTCDTO,
			int totalCount, Integer currentPage, Integer limitCount) {
		// TODO Auto-generated method stub
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		
		if (assessmentProgramTCDTO != null
				&& CollectionUtils.isNotEmpty(assessmentProgramTCDTO)) {
			for (AssessmentProgramTCDTO assessmentProgramTCDTOJSON : assessmentProgramTCDTO) {
				row = new JQGridRow();
				row.setId(assessmentProgramTCDTOJSON.getId());
				row.setCell(assessmentProgramTCDTOJSON.buildJSONRow());				
				rows.add(row);
			}
		}

		jqGridJSONModel.setRows(rows);
		jqGridJSONModel.setRecords(totalCount);
		jqGridJSONModel.setTotal(
				NumericUtil.getPageCount(
						totalCount, limitCount));
		jqGridJSONModel.setPage(currentPage);
		
		return jqGridJSONModel;
		
		
	}
	
	
	
	
}
