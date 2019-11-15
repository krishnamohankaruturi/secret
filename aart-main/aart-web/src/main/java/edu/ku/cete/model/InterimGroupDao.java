package edu.ku.cete.model;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.content.InterimGroup;
import edu.ku.cete.domain.content.InterimGroupStudent;

public interface InterimGroupDao {

	public void insertInterimGroup(InterimGroup ig);

	public void addInterimGroupStudents(InterimGroupStudent igs);

	public void deleteStudents(@Param("interimGroupId") Long interimGroupId,@Param("modifiedDate") Date modifiedDate,
			@Param("modifiedUser") Long modifiedUser);

	public void updateGroup(@Param("interimGroupId") Long interimGroupId, @Param("groupName") String groupName,
			@Param("modifiedDate") Date modifiedDate, @Param("modifiedUser") Long modifiedUser);

	public int deleteOnRosterChange(@Param("currentContextUserId") Long currentContextUserId,
			@Param("studentId") Long studentId,@Param("modifiedDate") Date modifiedDate,
			@Param("modifiedUser") Long modifiedUser);

	public int deleteOnStudent(@Param("studentId") Long studentId,@Param("modifiedDate") Date modifiedDate,
			@Param("modifiedUser") Long modifiedUser);

	public List<Long> getStudentsByGroup(Long interimGroupId);

	public List<InterimGroup> getGroups(@Param("groupName") String groupName, @Param("userName") String userName,
			@Param("userIds") List<Long> userList, @Param("organizationId") Long orgId);

	public void deleteGroup(@Param("interimGroupId") Long interimGroupId,@Param("modifiedDate") Date modifiedDate,
			@Param("modifiedUser") Long modifiedUser);

	public List<InterimGroup> getAllGroups();

	public List<InterimGroup> getGroupsByUser(@Param("userIds") List<Long> userIds,
			@Param("organizationId") Long organizationId);

	public int removeInterimGroups(@Param("modifiedUser") Long modifiedUser);
	
	public int removeInterimGroupStudent(@Param("modifiedDate") Date modifiedDate,
			@Param("modifiedUser") Long modifiedUser);
	
}
