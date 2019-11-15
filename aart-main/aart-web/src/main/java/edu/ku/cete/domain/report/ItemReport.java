package edu.ku.cete.domain.report;

import java.util.List;

public class ItemReport {
    private List<InterimTestItems>  interimTestItems;
    private String testName;
    private String scheduledDate;
    private String scheduledTime;
    private String width;
    private int respNum;
    public List<InterimTestItems> getInterimTestItems() {
        return interimTestItems;
    }

    public void setInterimTestItems(List<InterimTestItems> interimTestItems) {
        this.interimTestItems = interimTestItems;
    }

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(String scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public String getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(String scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public int getRespNum() {
		return respNum;
	}

	public void setRespNum(int maxResp) {
		this.respNum = maxResp;
	}

}