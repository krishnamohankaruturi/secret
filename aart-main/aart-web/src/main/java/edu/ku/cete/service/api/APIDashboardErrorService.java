package edu.ku.cete.service.api;

import java.util.List;

import edu.ku.cete.domain.api.APIDashboardError;
import edu.ku.cete.domain.common.Organization;


public interface APIDashboardErrorService{

	APIDashboardError buildDashboardError(ApiRequestTypeEnum request, ApiRecordTypeEnum record, String externalId, String name,
			Organization org, Long classroomId, Long userId, String message);
	
	boolean insertErrors(List<APIDashboardError> errors);

}