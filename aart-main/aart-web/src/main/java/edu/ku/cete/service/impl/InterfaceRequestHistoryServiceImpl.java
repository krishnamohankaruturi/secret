package edu.ku.cete.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.InterfaceRequestHistory;
import edu.ku.cete.model.InterfaceRequestHistoryMapper;
import edu.ku.cete.service.InterfaceRequestHistoryService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class InterfaceRequestHistoryServiceImpl implements
		InterfaceRequestHistoryService {

	private Logger logger = LoggerFactory.getLogger(OperationalTestWindowServiceImpl.class);
    /**
     * operationalTestWindowDao
     */
    @Autowired
    private InterfaceRequestHistoryMapper interfaceRequestHistoryMapper;
    
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insert(InterfaceRequestHistory record) {
		return interfaceRequestHistoryMapper.insert(record);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertSelective(InterfaceRequestHistory record) {
		return interfaceRequestHistoryMapper.insertSelective(record);
	}

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public InterfaceRequestHistory selectByPrimaryKey(Long id) {
		return interfaceRequestHistoryMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateByPrimaryKeySelective(InterfaceRequestHistory record) {
		return interfaceRequestHistoryMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateByPrimaryKey(InterfaceRequestHistory record) {
		return interfaceRequestHistoryMapper.updateByPrimaryKey(record);
	}

}
