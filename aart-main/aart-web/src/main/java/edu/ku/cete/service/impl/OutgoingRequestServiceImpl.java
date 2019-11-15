package edu.ku.cete.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.report.domain.OutgoingRequest;
import edu.ku.cete.report.model.OutgoingRequestMapper;
import edu.ku.cete.service.OutgoingRequestService;

@Service
public class OutgoingRequestServiceImpl implements OutgoingRequestService {
	
	@Autowired
	private OutgoingRequestMapper outgoingRequestMapper;
	
	@Override
	public int insert(OutgoingRequest request) {
		return outgoingRequestMapper.insert(request);
	}

	@Override
	public int insertSelective(OutgoingRequest request) {
		return outgoingRequestMapper.insertSelective(request);
	}

	@Override
	public int updateByPrimaryKey(OutgoingRequest request) {
		return outgoingRequestMapper.updateByPrimaryKey(request);
	}

	@Override
	public int updateByPrimaryKeySelective(OutgoingRequest request) {
		return outgoingRequestMapper.updateByPrimaryKeySelective(request);
	}

}
