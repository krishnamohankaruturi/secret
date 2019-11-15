package edu.ku.cete.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.user.UserContentArea;
import edu.ku.cete.model.UserContentAreasDao;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.UserContentAreasService;

@Service
public class UserContentAreasServiceImpl implements UserContentAreasService {
	
	private Logger logger = LoggerFactory.getLogger(UserContentAreasServiceImpl.class);

	@Autowired
	private UserContentAreasDao userContentAreasDao;
	
	@Autowired
	private ContentAreaService contentAreaService;

	@Override
	public List<UserContentArea> getUserContentAreas(Long userId) {
		return userContentAreasDao.getUserContentAreas(userId);
	}
	
	@Override
	public void insertUserContentAreas(List<UserContentArea> userContentAreas) {
		for(UserContentArea userContentArea : userContentAreas) {
			userContentAreasDao.insertUserContentArea(userContentArea);
		}
		
	}
	
	@Override
	public void activateUserContentAreas(List<Long> userContentAreaIds) {
		userContentAreasDao.activateUserContentAreas(userContentAreaIds);
	}
	
	@Override
	public void deactivateUserContentAreas(List<Long> userContentAreaIds) {
		userContentAreasDao.deactivateUserContentAreas(userContentAreaIds);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Boolean setUserContentAreas(Long userId, List<String> contentAreaCodes, Long modifiedUserId) {
		List<UserContentArea> existingContentAreas = getUserContentAreas(userId);
		List<Long> userCAsToActivate = new ArrayList<Long>();
		List<Long> userCAsToDeactivate = new ArrayList<Long>();
		List<String> existingCACodes = new ArrayList<String>();
		List<UserContentArea> userCAsToInsert = new ArrayList<UserContentArea>();
		
		// for logging
		List<String> userCAsToActivateCodes = new ArrayList<String>();
		List<String> userCAsToDeactivateCodes = new ArrayList<String>();
		List<String> userCAsToInsertCodes = new ArrayList<String>();
		
		// iterate through content areas already associated with that user (active or not) 
		// if it is in the set list, add to activate array, else add to inactivate array (inactivate ommited)
		for(UserContentArea uca : existingContentAreas) {
			if(contentAreaCodes != null && contentAreaCodes.contains(uca.getContentAreaAbbreviation())){
				userCAsToActivate.add(uca.getId());
				// store abbreviations for existing CAs
				existingCACodes.add(uca.getContentAreaAbbreviation());
				userCAsToActivateCodes.add((uca.getContentAreaAbbreviation()));
			}else {
				userCAsToDeactivate.add(uca.getId());
				userCAsToDeactivateCodes.add((uca.getContentAreaAbbreviation()));
			}
		}
		
		// remove any content areas already in database so they aren't double-added
		if(contentAreaCodes != null) {
			contentAreaCodes.removeAll(existingCACodes);
		
			// build new user content areas for insert any that didn't exist
			for(String caAbvr : contentAreaCodes) {
				UserContentArea newUCA = new UserContentArea();
				newUCA.setUserId(userId);
				ContentArea ca = contentAreaService.findByAbbreviatedName(caAbvr);
				if(ca == null) {
					return false;
				}
				Long caId = ca.getId();
				newUCA.setContentAreaId(caId);
				newUCA.setContentAreaAbbreviation(caAbvr);
				newUCA.setActive(true);
				newUCA.setCreatedUser(modifiedUserId);
				newUCA.setModifiedUser(modifiedUserId);
				userCAsToInsert.add(newUCA);
			}
		}
		
		for(UserContentArea uca : userCAsToInsert) {
			userCAsToInsertCodes.add(uca.getContentAreaAbbreviation());
		}
		
		logger.debug("UserContentAreas to activate: " + userCAsToActivateCodes);
		logger.debug("UserContentAreas to deactivate: " + userCAsToDeactivateCodes);
		logger.debug("UserContentAreas to insert: " + userCAsToInsertCodes);
		
		if(userCAsToActivate.size() > 0) { activateUserContentAreas(userCAsToActivate); };
		if(userCAsToDeactivate.size() > 0) { deactivateUserContentAreas(userCAsToDeactivate); };
		if(userCAsToInsert.size() > 0) { insertUserContentAreas(userCAsToInsert); };
		
		return true;
	}
	
}