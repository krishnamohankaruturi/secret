package edu.ku.cete.util.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JQGridRow;
import edu.ku.cete.tde.webservice.domain.MicroMapResponseDTO;
import edu.ku.cete.util.NumericUtil;

public class ITIJsonConverter {

	
	public static JQGridJSONModel convertToLinkageLevelJson(
			Map<String, MicroMapResponseDTO> linkageLevels, int totalCount, Integer page, Integer limitCount) {
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		Long counter = 1L;
		
		if (linkageLevels != null) {
			for (Map.Entry<String, MicroMapResponseDTO> entry : linkageLevels.entrySet()) {
				row = new JQGridRow();
				row.setId(counter);
				row.setCell(entry.getValue().buildJSONRow());
				rows.add(row);
				counter++;
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
