package edu.ku.cete.service;

import org.springframework.stereotype.Controller;

import edu.ku.cete.domain.feedback.FeedBack;

@Controller
public interface FeedBackService {

	public FeedBack addUserFeedBack(String name,String email,String feedBack, String webPage);

	public void updateUserFeedBack(String name,String email);

	public void deleteUserFeedBack(Long id);
}
