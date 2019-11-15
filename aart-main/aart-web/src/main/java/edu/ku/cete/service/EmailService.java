package edu.ku.cete.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.mail.SendFailedException;

import edu.ku.cete.domain.feedback.FeedBack;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.user.UserOrganizationsGroups;
import edu.ku.cete.ksde.kids.result.KSDERecord;
import edu.ku.cete.ksde.kids.result.KidRecord;
import edu.ku.cete.util.RecordSaveStatus;
import edu.ku.cete.web.ScorerTestStudentsSessionDTO;
import edu.ku.cete.web.UserDTO;
import freemarker.template.TemplateException;

/**
 * @author m802r921
 * Uses spring mail client for sending emails for various functionalities.
 */
public interface EmailService {

	/**
	 * @param user {@link User}
	 * @throws ServiceException ServiceException
	 */
	void sendUserActivationMsg(User user) throws ServiceException;
	/**
	 * @param userOrganizationsGroups {@link UserOrganizationsGroups}
	 * @throws ServiceException ServiceException
	 */
	void sendUserActivationMsg(UserOrganizationsGroups userOrganizationsGroups) throws ServiceException;
	/**
	 * @param mailMsg {@link SimpleMailMessage}
	 * @throws ServiceException ServiceException
	 */
	//void sendMessage(SimpleMailMessage mailMsg) throws ServiceException;
	/**
	 * @param userOrganizationsGroups {@link UserOrganizationsGroups}
	 * @throws ServiceException ServiceException
	 */
	void setUserOrgNotificationMsg(UserOrganizationsGroups userOrganizationsGroups) throws ServiceException;
	/**
	 * @param sendNewUserRegistration {@link sendNewUserRegistration}
	 * @throws ServiceException ServiceException
	 */
	 public  void sendNewUserRegistration(User user) throws ServiceException ;
	/**
	 * @param user {@link UserOrganizationsGroups}
	 * @throws ServiceException ServiceException
	 * @throws SendFailedException 
	 */
	void sendRejectUserMsg(User user) throws ServiceException, SendFailedException;
	/**
	 * @param user {@link UserDetailImpl}
	 * @param recordSaveStatus {@link RecordSaveStatus}
	 * @param messages {@link Map}
	 * @throws ServiceException ServiceException
	 */
	void sendOneTimeWebServiceUploadMsg(UserDetailImpl user,
			RecordSaveStatus recordSaveStatus,
			Map<String, Object> messages, File file) throws ServiceException;
	/**
	 * @param user {@link UserDetailImpl}
	 * @param recordSaveStatus {@link RecordSaveStatus}
	 * @param messages {@link Map}
	 * @param rejectedReasons {@link File}
	 * @throws ServiceException ServiceException
	 */
	void sendScheduledWebServiceUploadMsg(UserDetailImpl user,
			RecordSaveStatus recordSaveStatus, Map<String, Object> messages,
			File rejectedReasons) throws ServiceException;

	/**
	 * Sends an email to the user specified so that they can reset their password.
	 * @param user {@link User}
	 * @throws ServiceException 
	 */
    void sendPasswordResetEmail(User user) throws ServiceException;

    /**
     * Re-sends the user their activation email, and resets the expiration date.
     * @param userOrgGroup {@link UserOrganizationsGroups}
     * @throws ServiceException ServiceException
     */
    void resendUserActivationEmail(UserOrganizationsGroups userOrgGroup) throws ServiceException;
    /**
	 * Prasanth :  US16352 : To upload data file using batch     
	 */
    void sendUserActivationMsgUpload(UserOrganizationsGroups userGroup)
            throws ServiceException ;
    
    /**
     * Added for US-14985
     * This method is for sending password change alert to the user after 150 days.
     */
    void sendPasswordExpirationWarningResetEmail(User userPasswarning)  throws ServiceException;
	
    /**
     * Added for US-14985
     * This method is for sending and intimating user that user password is expired after 180 days.
     */
    void sendPasswordExpiringAlertEmail(User userPassExpiring) throws ServiceException;
    
   
	void sendMonitorScorerMsg(UserDTO scorerDetails,
			List<ScorerTestStudentsSessionDTO> studentDetails,
			UserDetailImpl userDetails, String subject, String pathway,
			String cCQTestName,Long monitorassessmentProgram,
			String stageName)throws ServiceException; 
	
	void sendUserFeedback(FeedBack feedBack) throws ServiceException ;
    
	void sendKIDSEmail(Long userId, List<KSDERecord> records) throws ServiceException, ParseException;
	
	void sendKIDSErrorEmail(KidRecord failedRecord, String step, Throwable throwable) throws IOException, TemplateException;
	void sendEmailToDTCUsers(HashSet<Long> userIds, Long claimUserId) throws ServiceException;
	
	void sendTestSessionCreationCompletionMsg(UserDetailImpl user,List<String> testSessionNames) throws ServiceException, SendFailedException;
	void sendEmailForGRFGradeChange(String organizationName);
}