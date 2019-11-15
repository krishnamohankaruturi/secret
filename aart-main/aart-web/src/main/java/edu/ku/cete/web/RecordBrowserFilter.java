package edu.ku.cete.web;

import java.util.List;

public class RecordBrowserFilter {
	
	private String groupOp;
	private List<RecordBrowserFilterRules> rules;
	
	public String getGroupOp() {
		return groupOp;
	}
	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}
	public List<RecordBrowserFilterRules> getRules() {
		return rules;
	}
	public void setRules(List<RecordBrowserFilterRules> rules) {
		this.rules = rules;
	}	
}
