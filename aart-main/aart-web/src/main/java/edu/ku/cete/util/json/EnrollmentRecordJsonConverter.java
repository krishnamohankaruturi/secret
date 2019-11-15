package edu.ku.cete.util.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JQGridRow;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.FindEnrollments;
import edu.ku.cete.util.NumericUtil;

public class EnrollmentRecordJsonConverter {
	/**
	 * @param users
	 * @param totalCount
	 * @param page
	 * @param limitCount
	 * @return
	 */
	public static JQGridJSONModel convertEnrollmentJson(
			Collection<FindEnrollments> enrollments, int totalCount, Integer page, Integer limitCount) {
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		
		if (enrollments != null
				&& CollectionUtils.isNotEmpty(enrollments)) {
			for (FindEnrollments enrollment : enrollments) {
				row = new JQGridRow();
				row.setId(enrollment.getId());
				row.setCell(enrollment.buildfindStudentJSONRow());				
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
