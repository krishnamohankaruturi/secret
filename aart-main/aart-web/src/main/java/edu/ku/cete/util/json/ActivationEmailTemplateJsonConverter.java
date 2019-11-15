package edu.ku.cete.util.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JQGridRow;
import edu.ku.cete.domain.common.ActivationEmailTemplate;
import edu.ku.cete.util.NumericUtil;

public class ActivationEmailTemplateJsonConverter {

	/**
	 * @param modules
	 * @param totalCount
	 * @param page
	 * @param limitCount
	 * @return
	 */
	public static JQGridJSONModel convertToEmailActivationJson(
			Collection<ActivationEmailTemplate> emailActivations, int totalCount, Integer page, Integer limitCount) {
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		
		if (emailActivations != null
				&& CollectionUtils.isNotEmpty(emailActivations)) {
			for (ActivationEmailTemplate emailActivation : emailActivations) {
				row = new JQGridRow();
				row.setId(emailActivation.getId());
				row.setCell(emailActivation.buildJSONRow());				
				rows.add(row);
			}
		}

		jqGridJSONModel.setRows(rows);
		jqGridJSONModel.setRecords(totalCount);
		jqGridJSONModel.setTotal(NumericUtil.getPageCount(totalCount, limitCount));
		jqGridJSONModel.setPage(page);
		
		return jqGridJSONModel;
	}
}
