package edu.ku.cete.report.charts;

import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.data.category.CategoryDataset;

public class LevelItemLabelGenerator extends StandardCategoryItemLabelGenerator {

	private static final long serialVersionUID = 2525389918583951340L;
	
	protected Object[] createItemArray(CategoryDataset dataset,
            int row, int column) {
		LevelCategoryDataset data = (LevelCategoryDataset) dataset;
		Object[] result = new Object[3];
        result[0] = data.getRowKey(row).toString();
        result[1] = data.getColumnKey(column).toString();
        
        Number value = data.getValue(row, column);
        if (value != null) {
        	if(data.getSupressLabelValue(row, column) != null && data.getSupressLabelValue(row, column).trim().length() > 0) {
            	result[2] = new StringBuffer(data.getSupressLabelValue(row, column) + "").toString();
    		} else {
    			result[2] = new StringBuffer(Math.abs(value.longValue()) + "").toString();
    		}
        }
        else {
            result[2] = "-";
        }

        return result;
	}
}
