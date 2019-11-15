package edu.ku.cete.service.report;

import edu.ku.cete.report.domain.APIRequest;

public interface APIRequestService {
	public int insert(APIRequest apiRequest);
	
	public int updateByPrimaryKey(APIRequest apiRequest);
}
