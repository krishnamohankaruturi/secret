package edu.ku.cete.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.content.InterimGroup;
import edu.ku.cete.domain.content.InterimGroupStudent;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.InterimGroupDao;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.service.InterimGroupService;
@Service
public class InterimGroupServiceImpl implements InterimGroupService {

	private Logger LOGGER = LoggerFactory.getLogger(InterimGroupServiceImpl.class);

	@Autowired
	private InterimGroupDao interimGroupsDao;
	
	@Autowired
	StudentDao studentDao;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Long createInterimGroup(List<Long> studentIds, String groupName, Long userId) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		User user = userDetails.getUser();
		InterimGroup ig = new InterimGroup();
		ig.setGroupName(groupName);
		ig.setCreatedDate(new Date());
		ig.setModifiedDate(new Date());
		ig.setCreatedUser(user.getCurrentContextUserId());
		ig.setModifiedUser(user.getCurrentContextUserId());
		ig.setActiveFlag(true);
		ig.setOrganizationId(user.getCurrentOrganizationId());
		interimGroupsDao.insertInterimGroup(ig);
		Long interimGroupId = ig.getId();

		return interimGroupId;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void addInterimGroupStudents(List<Long> studentIds, Long interimGroupId, User user) {
		InterimGroupStudent igs = new InterimGroupStudent();
		if (studentIds != null) {
			Long[] studentIdsdArray = new Long[studentIds.size()];
			for (int i = 0; i < studentIds.size(); i++) {
				studentIdsdArray[i] = Long.valueOf(studentIds.get(i));
				igs.setStudentId(studentIdsdArray[i]);
				igs.setInterimGroupId(interimGroupId);
				igs.setCreatedUser(user.getCurrentContextUserId());
				igs.setCreatedDate(new Date());
				igs.setActiveFlag(true);
				interimGroupsDao.addInterimGroupStudents(igs);
				LOGGER.debug(igs.toString());
			}
		}

	}
	@Override
	public void updateGroup(Long interimGroupId,String groupName){
		 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
                 .getPrincipal();
         User user = userDetails.getUser();
         Date modifiedDate=new Date();
         Long modifiedUser=user.getCurrentContextUserId();
		interimGroupsDao.updateGroup(interimGroupId,groupName,modifiedDate,modifiedUser);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int deleteOnRosterChange(User user, Long studentId){
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
		Date modifiedDate=new Date();
		return interimGroupsDao.deleteOnRosterChange(user.getCurrentContextUserId(),studentId,modifiedDate,userDetails.getUserId());
	}
	// need to work
	@Override
	public int deleteOnStudentDelete(Long studentId){
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
		Date modifiedDate=new Date();
		
		return interimGroupsDao.deleteOnStudent(studentId,modifiedDate,userDetails.getUserId());
	}

	@Override
	public void deleteStudentGroup(Long interimGroupId) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
		Date modifiedDate=new Date();
		interimGroupsDao.deleteStudents(interimGroupId,modifiedDate,userDetails.getUserId());
	}

	@Override
	public List<Student> getStudentsByGroup( Long interimGroupId) {
		List<Long> igs=interimGroupsDao.getStudentsByGroup(interimGroupId);
		List<Student> returnList = new ArrayList<Student>();
		for (Long ig : igs) {
			returnList.add(studentDao.findById(ig));
		}
		return returnList;
	}

	@Override
	public List<InterimGroup> getGroupsByGroupNameAndUserName(String groupName, String userName, List<Long> userList,
			Long orgId) {
		groupName="%"+groupName+"%";
		userName="%"+userName+"%";
		List<InterimGroup> ig=interimGroupsDao.getGroups(groupName,userName,userList,orgId);
		return ig;
	}

	@Override
	public void deleteGroup(Long interimGroupId) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
		Date modifiedDate=new Date();
		interimGroupsDao.deleteGroup(interimGroupId,modifiedDate,userDetails.getUserId());
		
	}

	@Override
	public List<InterimGroup> getAllGroups() {
		List<InterimGroup> ig=interimGroupsDao.getAllGroups();
		return ig;
	}

	@Override
	public List<InterimGroup> getGroupsByUser(List<Long> userIds,Long orgId) {
		return interimGroupsDao.getGroupsByUser(userIds,orgId);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int removeInterimGroups(){
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
		return interimGroupsDao.removeInterimGroups(userDetails.getUserId());
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int removeInterimGroupStudent(){
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
		Date modifiedDate=new Date();
		return interimGroupsDao.removeInterimGroupStudent(modifiedDate,userDetails.getUserId());
	}
}
