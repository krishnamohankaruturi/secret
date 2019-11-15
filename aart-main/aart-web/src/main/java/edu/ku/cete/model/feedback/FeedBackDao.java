package edu.ku.cete.model.feedback;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.feedback.FeedBack;

public interface FeedBackDao {

	public void insertUserFeedBack(FeedBack userFeedBack);

	public void updateUserFeedBack(@Param("name") String UserName, @Param("email") String email);

	public void deleteUserFeedBack(@Param("id") long id);
}
