package edu.ku.cete.service.impl.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.report.domain.APIRequest;
import edu.ku.cete.report.model.APIRequestMapper;
import edu.ku.cete.service.report.APIRequestService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class APIRequestServiceImpl implements APIRequestService {
	
	private Logger logger = LoggerFactory.getLogger(APIRequestServiceImpl.class);
	
	@Autowired
	private APIRequestMapper apiRequestDao;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insert(APIRequest apiRequest) {
		return apiRequestDao.insert(apiRequest);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateByPrimaryKey(APIRequest apiRequest) {
		return apiRequestDao.updateByPrimaryKey(apiRequest);
	}

}
