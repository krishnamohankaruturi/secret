package edu.ku.cete.domain.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JQGridRow;


public class TestingOutsideHoursJsonConverter {
	public static JQGridJSONModel convertToJson(List<TestingOutsideHours> tohs) throws JsonProcessingException{	
		
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		
		if(tohs != null
				&& CollectionUtils.isNotEmpty(tohs)) {
			for(TestingOutsideHours reactivation : tohs) {
				row = new JQGridRow();				
				//row.setCell(reactivation.buildJSONRow());				
				rows.add(row);
			}
		}

		jqGridJSONModel.setRows(rows);		
		return jqGridJSONModel;
	}
		
}