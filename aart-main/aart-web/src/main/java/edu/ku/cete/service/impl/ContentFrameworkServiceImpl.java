package edu.ku.cete.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.test.ContentFrameworkDetail;
import edu.ku.cete.model.test.ContentFrameworkDetailDao;
import edu.ku.cete.service.ContentFrameworkService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class ContentFrameworkServiceImpl implements ContentFrameworkService {
	
	@Autowired
	private ContentFrameworkDetailDao cfdDao;
	
	@Override
	public ContentFrameworkDetail getContentFrameworkDetailById(Long contentFrameworkDetailId) {
		return cfdDao.selectByPrimaryKey(contentFrameworkDetailId);
	}
}
