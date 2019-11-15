package edu.ku.cete.report.charts;

import java.util.HashMap;
import java.util.Map;

import org.jfree.data.category.DefaultCategoryDataset;

public class LevelCategoryDataset extends DefaultCategoryDataset {

	private static final long serialVersionUID = -1709092536442816649L;
	
	private Map<String, String> supressLabels = null;
	private Long firstLevel;
	private Long lastLevel;
	private Map<String, Double> overridedPercentMap = null;
	
	public LevelCategoryDataset() {
		super();
		supressLabels = new HashMap<String, String>();
		overridedPercentMap = new HashMap<String, Double>();
	}

	@SuppressWarnings("rawtypes")
	public void setSupressLabelValue(String value, Comparable rowKey, Comparable columnKey) {
		int row = getRowIndex(rowKey);
		int column = getColumnIndex(columnKey);
		supressLabels.put(getKey(row,column), value);
	}
	
	public Double getPercentMap(int rowKey, int columnKey) {
		return overridedPercentMap.get(getKey(rowKey,columnKey));
	}
	
	@SuppressWarnings("rawtypes")
	public void setpercentMap(Double value, Comparable rowKey, Comparable columnKey) {
		int row = getRowIndex(rowKey);
		int column = getColumnIndex(columnKey);
		overridedPercentMap.put(getKey(row,column), value);
	}
	
	public String getSupressLabelValue(int rowKey, int columnKey) {
		return supressLabels.get(getKey(rowKey,columnKey));
	}
	
	private String getKey(int rowKey, int columnKey) {
		return rowKey+","+columnKey;
	}

	public Long getFirstLevel() {
		return firstLevel;
	}

	public void setFirstLevel(Long firstLevel) {
		this.firstLevel = firstLevel;
	}

	public Long getLastLevel() {
		return lastLevel;
	}

	public void setLastLevel(Long lastLevel) {
		this.lastLevel = lastLevel;
	}
}
