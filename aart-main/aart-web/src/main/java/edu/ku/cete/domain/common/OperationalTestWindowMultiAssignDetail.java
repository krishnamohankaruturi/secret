package edu.ku.cete.domain.common;

public class OperationalTestWindowMultiAssignDetail {
	

	private long id;
	
	private long operationaltestwindowid;
	
	private long contentareaid;
	
	private String name;
	
	private int numberoftests;
	
	public long getOperationaltestwindowid() {
		return operationaltestwindowid;
	}
	
	public void setOperationaltestwindowid(long operationaltestwindowid) {
		this.operationaltestwindowid = operationaltestwindowid;
	}
	
	public long getContentareaid() {
		return contentareaid;
	}
	
	public void setContentareaid(long contentareaid) {
		this.contentareaid = contentareaid;
	}
	
	public int getNumberoftests() {
		return numberoftests;
	}
	
	public void setNumberoftests(int numberoftests) {
		this.numberoftests = numberoftests;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
 
	
}
