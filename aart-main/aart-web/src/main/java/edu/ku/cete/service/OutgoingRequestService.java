package edu.ku.cete.service;

import edu.ku.cete.report.domain.OutgoingRequest;

public interface OutgoingRequestService {
	int insert(OutgoingRequest request);
	int insertSelective(OutgoingRequest request);
	int updateByPrimaryKey(OutgoingRequest request);
	int updateByPrimaryKeySelective(OutgoingRequest request);
}
