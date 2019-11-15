package edu.ku.cete.service;

import java.util.List;

import edu.ku.cete.domain.content.InterimGroup;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.user.User;

public interface InterimGroupService {

	Long createInterimGroup(List<Long> studentIds, String groupName, Long userId);

    void updateGroup(Long interimGroupId, String groupName);

	void deleteStudentGroup(Long interimGroupId);

	List<Student> getStudentsByGroup(Long interimGroupId);

	List<InterimGroup> getGroupsByGroupNameAndUserName(String groupName, String userName, List<Long> userList,
			Long orgId);

	void addInterimGroupStudents(List<Long> studentIds, Long interimGroupId, User user);

	void deleteGroup(Long interimGroupId);

	List<InterimGroup> getAllGroups();

	int deleteOnRosterChange(User user, Long studentId);

	int deleteOnStudentDelete(Long studentId);

	List<InterimGroup> getGroupsByUser(List<Long> userList, Long orgId);

	int removeInterimGroups();
	
	int removeInterimGroupStudent();
}
