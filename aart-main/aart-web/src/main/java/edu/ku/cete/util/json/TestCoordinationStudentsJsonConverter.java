package edu.ku.cete.util.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JQGridRow;
import edu.ku.cete.domain.enrollment.AutoRegisteredStudentsDTO;
import edu.ku.cete.util.NumericUtil;

/**
 * @author vittaly
 *
 */
public class TestCoordinationStudentsJsonConverter {

	/**
	 * @param autoRegisteredStudents
	 * @param totalCount
	 * @param page
	 * @param limitCount
	 * @return
	 */
	public static JQGridJSONModel convertToStudentsJson(
			Collection<AutoRegisteredStudentsDTO> autoRegisteredStudents, int totalCount, int page, int limitCount) {
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		
		if (autoRegisteredStudents != null
				&& CollectionUtils.isNotEmpty(autoRegisteredStudents)) {
			for (AutoRegisteredStudentsDTO autoRegisteredStudent : autoRegisteredStudents) {
				row = new JQGridRow();
				row.setId(autoRegisteredStudent.getStudent().getId());
				row.setCell(autoRegisteredStudent.buildJSONRow());				
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
}
