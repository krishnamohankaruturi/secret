package edu.ku.cete.service.impl.report;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.domain.user.UploadedUser;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.UserService;

/**
 * Added By Prasanth 
 * User Story: US16352:
 * Spring batch upload for data file(User)
 */
@Service
public class UserUploadWriterProcessServiceImpl implements BatchUploadWriterService{

	final static Log logger = LogFactory.getLog(UserUploadWriterProcessServiceImpl.class);

	@Autowired
	private UserService userService;
	
	@Override
	public void writerProcess(List<? extends Object> objects) {
		
		for( Object object : objects){
			UploadedUser uploadedUser = (UploadedUser) object;
			userService.createOrAddUserOrganizationsGroupsUpload(uploadedUser,uploadedUser.getExistingUser(),uploadedUser.getCurrentContext(),uploadedUser.getContractingOrganizationTree(),uploadedUser.getLoggedinUser());
		} 
	}
}
