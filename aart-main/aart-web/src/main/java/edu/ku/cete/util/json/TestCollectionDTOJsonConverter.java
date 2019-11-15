package edu.ku.cete.util.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JQGridRow;
import edu.ku.cete.domain.TestCollectionDTO;
import edu.ku.cete.util.NumericUtil;

/**
 * @author vittaly
 *
 */
public class TestCollectionDTOJsonConverter {

	/**
	 * @param modules
	 * @param totalCount
	 * @param page
	 * @param limitCount
	 * @return
	 */
	public static JQGridJSONModel convertToTestCollectionDTOJson(
			Collection<TestCollectionDTO> testCollections, int totalCount, int page, int limitCount, 
			boolean hasHighStakesPermission, boolean hasQCCompletePermission) {
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		
		if (testCollections != null
				&& CollectionUtils.isNotEmpty(testCollections)) {
			for (TestCollectionDTO testCollectionDTO : testCollections) {
				row = new JQGridRow();
				row.setId(testCollectionDTO.getId());
				row.setCell(testCollectionDTO.buildJSONRow(hasHighStakesPermission, hasQCCompletePermission));				
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
