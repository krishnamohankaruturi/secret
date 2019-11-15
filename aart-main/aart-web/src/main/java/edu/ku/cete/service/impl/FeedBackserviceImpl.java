package edu.ku.cete.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.feedback.FeedBack;
import edu.ku.cete.model.feedback.FeedBackDao;
import edu.ku.cete.service.FeedBackService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class FeedBackserviceImpl implements FeedBackService {

	@Autowired
	private FeedBackDao feedBackDao;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public FeedBack addUserFeedBack(String name, String email, String feedBack, String webPage) {
		FeedBack userFeedBack = new FeedBack();
		userFeedBack.setUserName(name);
		userFeedBack.setEmail(email);
		userFeedBack.setFeedBack(feedBack);
		userFeedBack.setAuditColumnProperties();
		userFeedBack.setWebPage(webPage);
		feedBackDao.insertUserFeedBack(userFeedBack);
		return userFeedBack;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateUserFeedBack(String name, String email) {
		feedBackDao.updateUserFeedBack(name, email);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteUserFeedBack(Long id) {
		feedBackDao.deleteUserFeedBack(id);
	}
}
