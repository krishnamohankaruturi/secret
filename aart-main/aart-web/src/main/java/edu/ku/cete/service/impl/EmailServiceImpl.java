package edu.ku.cete.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import edu.ku.cete.domain.common.ActivationEmailTemplate;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.feedback.FeedBack;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.user.UserOrganizationsGroups;
import edu.ku.cete.domain.user.UserPasswordReset;
import edu.ku.cete.ksde.kids.result.KSDERecord;
import edu.ku.cete.ksde.kids.result.KidRecord;
import edu.ku.cete.model.AssessmentProgramDao;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.model.mapper.UserPasswordResetMapper;
import edu.ku.cete.service.ActivationEmailTemplateService;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.EmailService;
import edu.ku.cete.service.KSDERecordStagingService;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.UserOrganizationsGroupsService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.EncryptionUtil;
import edu.ku.cete.util.MD5Base64EncoderUtil;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.RecordSaveStatus;
import edu.ku.cete.web.ScorerTestStudentsSessionDTO;
import edu.ku.cete.web.UserDTO;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 *
 * @author neil.howerton
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class EmailServiceImpl implements EmailService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${mail.domain}")
    private String domain;

    @Value("${mail.host}")
    private String host;
    
    @Value("${mail.smtp.auth}")
    private String auth;
    
    @Value("${mail.smtp.starttls.enable}")
    private String starttlsEnable;
    
    @Value("${mail.port}")
    private String port;
    
    @Value("${mail.username}")
    private String userName;
    
    @Value("${mail.password}")
    private String password;

    
    @Value("${mail.from}")
    private String from;

    @Value("${user.activation.period}")
    private int numActivationDays;
    
    @Value("${reset.passwordlink.period}")
    private int resetPasswordLinkPeriod;
    
    @Value("${user.feedback.submission.email}")
    private String userFeedbackSubmissionEmail;
    
	@Value("images/kite_logo_2018.png")
	private Resource kiteAartLogo;
	
    @Value("${templateSubjectCode}")
    private String templateSubjectCode;
    
    @Value("${templateTypeCode}")
    private String templateTypeCode;
    
    @Value("${tascRecordType}")
    private String tascRecordType;
    @Value("${exitRecordType}")
    private String exitRecordType;
    @Value("${testRecordType}")
    private String testRecordType;
    
    @Value("${mail.kids.error.addresslist}")
    private String kidsErrorEmailToList;
    
    @Value("${mail.kids.error.env}")
    private String kidsErrorMailEnv;
    
    @Value("${ATS.management.email.address}")
    private String atsManagement;
    
    @Value("${grfDataTypeCode}")
    private String grfDataTypeCode;
    
    @Value("${grfEmailIds}")
    private String grfEmailIds;
    
    @Value("${grfEmailMsg}")
    private String grfEmailMsg;
    
    @Value("${user.internalusers.emaildomains}")
	private String internalUsersDomains;
    
    @Autowired
	private KSDERecordStagingService ksdeRecordStagingService;
	
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration freeMarkerConfig;

    @Autowired
    private UserOrganizationsGroupsService userOrganizationsGroupsService;
           
    @Autowired
    UserPasswordResetMapper userPasswordResetDao;

    @Autowired
	private CategoryService categoryService;
    
    @Autowired
	private UserService userService;
    
	@Autowired
	private ActivationEmailTemplateService activationEmailTemplateService;
	
	 @Autowired
	 private AssessmentProgramDao assessProgDao;
	 
	 @Autowired
	 private OrganizationDao organizationDao;
	 
    /**
     * @param userGroup {@link UserOrganizationsGroups}
     * @throws ServiceException ServiceException
     */
    public final void sendUserActivationMsg(UserOrganizationsGroups userGroup)
            throws ServiceException {


       
        try {

        	Long selectedAssesmentProgrmId=null;
        	List<String> assessmentPrograms=new ArrayList<String>();
        	List<Long> states=organizationDao.getAllStatesByUserId(userGroup.getUserId());
    		
    		if(states!=null && !states.isEmpty() && states.size()==1){
    			userGroup.getUser().setContractingOrgId(states.get(0));    		}
    		
        	Long selctedStateId=userGroup.getUser().getContractingOrgId();

        	for(AssessmentProgram asmntProgm:userGroup.getUser().getUserAssessmentPrograms()){
        		assessmentPrograms.add(asmntProgm.getAbbreviatedname());
        	}

        	if(assessmentPrograms.contains("KAP")){
        		for(AssessmentProgram asmntProgm:userGroup.getUser().getUserAssessmentPrograms()){
            		if(asmntProgm.getAbbreviatedname().equals("KAP")){
            			selectedAssesmentProgrmId=asmntProgm.getId();
            		}
            	}
        	}else if(assessmentPrograms.contains("DLM")){
        		for(AssessmentProgram asmntProgm:userGroup.getUser().getUserAssessmentPrograms()){
            		if(asmntProgm.getAbbreviatedname().equals("DLM")){
            			selectedAssesmentProgrmId=asmntProgm.getId();
            		}
            	}
        	}else if(assessmentPrograms.contains("KELPA2")){
        		for(AssessmentProgram asmntProgm:userGroup.getUser().getUserAssessmentPrograms()){
            		if(asmntProgm.getAbbreviatedname().equals("KELPA2")){
            			selectedAssesmentProgrmId=asmntProgm.getId();
            		}
            	}
        	}else if(assessmentPrograms.contains("CPASS")){
        		for(AssessmentProgram asmntProgm:userGroup.getUser().getUserAssessmentPrograms()){
            		if(asmntProgm.getAbbreviatedname().equals("CPASS")){
            			selectedAssesmentProgrmId=asmntProgm.getId();
            		}
            	}
        	}else{
        		for(AssessmentProgram asmntProgm:userGroup.getUser().getUserAssessmentPrograms()){
            		selectedAssesmentProgrmId=asmntProgm.getId();
            	}
        	}

        	List<ActivationEmailTemplate> activationEmailtemplates=activationEmailTemplateService.getTemplateByAssesmentAndState(selectedAssesmentProgrmId, selctedStateId);
        	ActivationEmailTemplate selectedactivationEmailtemplate=new ActivationEmailTemplate();
        	int listsize=activationEmailtemplates.size();
        	for(ActivationEmailTemplate emailtemplate:activationEmailtemplates){
        		if(listsize>1){
        			if(!emailtemplate.getIsDefault()){
        				selectedactivationEmailtemplate=emailtemplate;
        			}
        		}else{
        			selectedactivationEmailtemplate=emailtemplate;
        		}
        	}
/*            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("domain", domain);
            dataMap.put("dataObject", userGroup);*/
          /*  SimpleMailMessage mailMsg = new SimpleMailMessage();            
            mailMsg.setTo(userGroup.getUser().getEmail());
            mailMsg.setSubject(msgSubjectStr);
            mailMsg.setText(emailBody.toString());
            mailMsg.setFrom(from);*/

            //mailSender.send(mailMsg);

            send(userGroup,selectedactivationEmailtemplate);

        }  catch (Exception e) {
            logger.error("Issue sending email to user with userid: " + 
            		userGroup.getUser().getUserName());
            throw new ServiceException(e.getMessage());
        }
    }

    public void sendUserActivationMsg(User user) throws ServiceException {
        Template template = null;
        try {
            template = freeMarkerConfig.getTemplate("userActivationBody.ftl");
            String msgBodyStr = FreeMarkerTemplateUtils
                    .processTemplateIntoString(template, user);

            String msgSubjectStr = freeMarkerConfig.getTemplate(
                    "userActivationSubject.ftl").toString();

            SimpleMailMessage mailMsg = new SimpleMailMessage();

            mailMsg.setTo(user.getEmail());
            mailMsg.setSubject(msgSubjectStr);
            mailMsg.setText(msgBodyStr);
            mailMsg.setFrom(from);

            mailSender.send(mailMsg);

        } catch (IOException e) {
            logger.error("Issue sending email to user with userid: ",
                    user.getUserName());
            throw new ServiceException(e.getMessage());
        } catch (TemplateException e) {
            logger.error(
                    "Issue sending email to user with template: {} and userName: {} ",
                    new Object[] {template, user.getUserName() }, e);
            throw new ServiceException(e.getMessage());
        }
    }
    
    public void sendTestSessionCreationCompletionMsg(UserDetailImpl user,List<String> testSessionNames) throws ServiceException {
        Template template = null;
        try {
            template = freeMarkerConfig.getTemplate("userTestSessionCreationBody.ftl");
            String msgBodyStr = FreeMarkerTemplateUtils
                    .processTemplateIntoString(template, user);

            String msgSubjectStr = freeMarkerConfig.getTemplate(
                    "userTestSessionCreationSubject.ftl").toString();

            StringBuffer testSessions=new  StringBuffer();
            for(String testSession:testSessionNames){
            	testSessions.append(testSession);
            }
            msgBodyStr= msgBodyStr.replace("[]", testSessions.toString());
            SimpleMailMessage mailMsg = new SimpleMailMessage();

            mailMsg.setTo(user.getEmail());
            mailMsg.setSubject(msgSubjectStr);
            mailMsg.setText(msgBodyStr);
            mailMsg.setFrom(from);

            mailSender.send(mailMsg);

        } catch (IOException e) {
            logger.error("Issue sending email to user with userid: ",
                    user.getUsername());
            throw new ServiceException(e.getMessage());
        } catch (TemplateException e) {
            logger.error(
                    "Issue sending email to user with template: {} and userName: {} ",
                    new Object[] {template, user.getUsername() }, e);
            throw new ServiceException(e.getMessage());
        }
    }
    
    
    
    /**
     * @param user {@link UserDetailImpl}
     * @param recordSaveStatus {@link RecordSaveStatus}
     * @param messages {@link Map}
     * @param file {@link File}
     * @throws ServiceException ServiceException
     */
    public final void sendOneTimeWebServiceUploadMsg(
    		UserDetailImpl user,
    		RecordSaveStatus recordSaveStatus,
    		Map<String, Object> messages, File file) throws ServiceException {
        Template template = null;
        Template msgSubjectTemplate = null;
        String msgSubjectStr = null;
        try {
        	if (recordSaveStatus.equals(RecordSaveStatus.KIDS_NEXT_UPLOAD_SET) || recordSaveStatus.equals(RecordSaveStatus.ROSTER_NEXT_UPLOAD_SET)) {
                template = freeMarkerConfig.getTemplate("webServiceSuccessfulOneTimeUpload.ftl");
        	} else if (recordSaveStatus.equals(RecordSaveStatus.CHECKED_AUTHORITIES)) {
    			template = freeMarkerConfig.getTemplate("webServiceUnAuthorizedOneTimeUpload.ftl");
    		} else {
                template = freeMarkerConfig.getTemplate("webServiceFailedOneTimeUpload.ftl");
        	}

            msgSubjectTemplate= freeMarkerConfig.getTemplate(
                    "webServiceImmediateUploadSubject.ftl");
            messages.put("userName", user.getUsername());
            if (file == null) {
            	messages.put("fileReferenceMessage", ParsingConstants.BLANK);
            }

            msgSubjectStr = FreeMarkerTemplateUtils
                    .processTemplateIntoString(msgSubjectTemplate, messages);
            
            String msgBodyStr = FreeMarkerTemplateUtils
                    .processTemplateIntoString(template, messages);

            MimeMessage message = mailSender.createMimeMessage();

    		MimeMessageHelper helper = new MimeMessageHelper(message, true);

    		helper.setTo(user.getEmail());
    		helper.setSubject(msgSubjectStr);
    		helper.setText(msgBodyStr);
    		helper.setFrom(from);

    		if (file != null) {
				FileSystemResource fileSystemResource = new FileSystemResource(
						file);
				helper.addAttachment(fileSystemResource.getFilename(), file);
			}
			mailSender.send(message);

        } catch (IOException e) {
            logger.error("Issue sending email to user with userid: ",
                    user.getUsername());
            throw new ServiceException(e.getMessage());
        } catch (TemplateException e) {
            logger.error(
                    "Issue sending email to user with template: {} and userName: {} ",
                    new Object[] {template, user.getUsername() }, e);
            throw new ServiceException(e.getMessage());
        } catch (MessagingException e) {
        	logger.error("Issue in sending attachment", e);
            throw new ServiceException(e.getMessage());
		}
    }
    /**
     *
     * @param user {@link UserDetailImpl}
     * @param status {@link RecordSaveStatus}
     * @param messages {@link Map}
     * @param file {@link File}
     * @throws ServiceException ServiceException
     */
    public final void sendScheduledWebServiceUploadMsg(UserDetailImpl user,
    		RecordSaveStatus status,
    		Map<String, Object> messages,
    		File file) throws ServiceException {
        Template template = null;
        Template msgSubjecttemplate = null;
        String msgSubjectStr = null;
        try {
        	if (status.equals(RecordSaveStatus.KIDS_NEXT_UPLOAD_SET) || status.equals(RecordSaveStatus.ROSTER_NEXT_UPLOAD_SET)) {
        		template = freeMarkerConfig.getTemplate("webServiceSuccessfulScheduledUpload.ftl");
        		} else if (status.equals(RecordSaveStatus.CHECKED_AUTHORITIES)) {
        			template = freeMarkerConfig.getTemplate("webServiceUnAuthorizedScheduledUpload.ftl");
        		} else {
        			template = freeMarkerConfig.getTemplate("webServiceFailedScheduledUpload.ftl");
        		}
            msgSubjecttemplate = freeMarkerConfig.getTemplate(
                    "webServiceScheduledUploadSubject.ftl");
            messages.put("userName", user.getUsername());
            if (file == null) {
            	messages.put("fileReferenceMessage", ParsingConstants.BLANK);
            }

            msgSubjectStr = FreeMarkerTemplateUtils
                    .processTemplateIntoString(msgSubjecttemplate, messages);
            
            String msgBodyStr = FreeMarkerTemplateUtils
                    .processTemplateIntoString(template, messages);
            
            MimeMessage message = mailSender.createMimeMessage();

    		MimeMessageHelper helper = new MimeMessageHelper(message, true);

    		helper.setTo(user.getEmail());
    		helper.setSubject(msgSubjectStr);
    		helper.setText(msgBodyStr);
    		helper.setFrom(from);

    		if (file != null) {
				FileSystemResource fileSystemResource = new FileSystemResource(
						file);
				helper.addAttachment(fileSystemResource.getFilename(), file);
			}
            mailSender.send(message);

        } catch (IOException e) {
            logger.error("Issue sending email to user with userid: ",
                    user.getUsername());
            throw new ServiceException(e.getMessage());
        } catch (TemplateException e) {
            logger.error(
                    "Issue sending email to user with template: {} and userName: {} ",
                    new Object[] {template, user.getUsername() }, e);
            throw new ServiceException(e.getMessage());
        } catch (MessagingException e) {
        	logger.error("Issue in sending attachment", e);
            throw new ServiceException(e.getMessage());
		}
    }

    /**
     * @param users {@link List}
     * @throws ServiceException ServiceException
     */
    public final void sendUserActivationMsg(List<User> users) throws ServiceException {
        for (User user : users) {
            sendUserActivationMsg(user);
        }
    }
    
    /**
    *
    *@param userOrganizationsGroups {@link UserOrganizationsGroups}
    *@throws ServiceException ServiceException
    */
    public final void setUserOrgNotificationMsg(UserOrganizationsGroups userOrganizationsGroups) throws ServiceException {
        Template template = null;
        try {
            template = freeMarkerConfig.getTemplate("userOrgNotificationBody.ftl");

            Map<String, Object> dataMap = new HashMap<String, Object>();

            dataMap.put("domain", domain);
            dataMap.put("dataObject", userOrganizationsGroups);

            String msgBodyStr = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);

            String msgSubjectStr = freeMarkerConfig.getTemplate("userOrgNotificationSubject.ftl").toString();

            SimpleMailMessage mailMsg = new SimpleMailMessage();

            mailMsg.setTo(userOrganizationsGroups.getUser().getEmail());
            mailMsg.setSubject(msgSubjectStr);
            mailMsg.setText(msgBodyStr);
            mailMsg.setFrom(from);

            mailSender.send(mailMsg);
        } catch (IOException e) {
            logger.error("Issue sending email to user with userid: ",
               userOrganizationsGroups.getUser().getUserName());
            throw new ServiceException(e.getMessage());
        } catch (TemplateException e) {
            logger.error(
               "Issue sending email to user with template: {} and userName: {} ",
                   new Object[] {template, userOrganizationsGroups.getUser().getUserName() }, e);
           throw new ServiceException(e.getMessage());
       }
    }

    /**
     *
     * @param user {@link UserOrganizationsGroups}
     * @throws ServiceException ServiceException
     * @throws SendFailedException 
     */
    public final void sendRejectUserMsg(User user) throws ServiceException, SendFailedException {
        Template template = null;
        try {
        	Properties prop = new Properties ();
        	prop.setProperty ("mail.smtp.auth", "true"); 
            template = freeMarkerConfig.getTemplate("rejectUserAccountBody.ftl");

            Map<String, Object> dataMap = new HashMap<String, Object>();

            dataMap.put("domain", domain);
            dataMap.put("dataObject", user);

            String msgBodyStr = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);

            String msgSubjectStr = freeMarkerConfig.getTemplate("rejectUserAccountSubject.ftl").toString();

            SimpleMailMessage mailMsg = new SimpleMailMessage();

            mailMsg.setTo(user.getEmail());
            mailMsg.setSubject(msgSubjectStr);
            mailMsg.setText(msgBodyStr);
            mailMsg.setFrom(from);
            mailSender.send(mailMsg);
        } catch (IOException e) {
            logger.error("Issue sending email to user with userid: ",
               user.getUserName());
            throw new ServiceException(e.getMessage());
        } catch (TemplateException e) {
            logger.error(
               "Issue sending email to user with template: {} and userName: {} ",
                   new Object[] {template, user.getUserName() }, e);
           throw new ServiceException(e.getMessage());
       } catch (Exception ex) {
    	   logger.debug (ex.toString());
    	   throw new SendFailedException();
    	   } 
    }

    /**
     * 
     * @param userOrgGroup
     * @throws ServiceException
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)    
    public final void resendUserActivationEmail(UserOrganizationsGroups userOrgGroup) throws ServiceException {
        Calendar now = GregorianCalendar.getInstance();
        now.add(Calendar.DATE, numActivationDays);
        userOrgGroup.setActivationNoExpirationDate(now.getTime());
		List<AssessmentProgram> assessmentPrograms=new ArrayList<AssessmentProgram>();
		
        assessmentPrograms=assessProgDao.getAllAssessmentProgramByUserId(userOrgGroup.getUserId());
        userOrgGroup.getUser().setUserAssessmentPrograms(assessmentPrograms);
        userOrganizationsGroupsService.updateUserOrganizationsGroups(userOrgGroup);
        User u = userService.get(userOrgGroup.getUserId());
        String userDomainName = u.getEmail().substring(u.getEmail().indexOf('@') + 1); 
	   	
        // For PLTW user email restriction.
        Boolean isWelcomeMailRestricted = false;
	   	Boolean internalUser = false;	
	   	if(internalUsersDomains!=null && !internalUsersDomains.equals("") && !internalUsersDomains.isEmpty()){
	   		String[] domainNamesList =  internalUsersDomains.split(",");
		    if(domainNamesList!=null && domainNamesList.length>0){
		    	for(String internalDomainName : domainNamesList){					
					if(userDomainName.equals(internalDomainName)){
						internalUser = true;
					}
				}
		    } 		
	   	}
	   	if(internalUser.equals(false)) {
	   		for(AssessmentProgram ap:assessmentPrograms){
				if(CommonConstants.PLTW.equalsIgnoreCase(ap.getAbbreviatedname()))
				{
					isWelcomeMailRestricted = true;
					break;
				}
			}
	   	}
	   	// Restrict welcome emails for the PLTW external user only.
    	if(isWelcomeMailRestricted.equals(false)){
        	sendUserActivationMsg(userOrgGroup);
        }
    }
    
    public final void sendNewUserRegistration(User user) throws ServiceException {
        if (user == null) {
            logger.debug("User trying to xf null.");
            return;
        }
        Template template = null;
        
        try {
            template = freeMarkerConfig.getTemplate("newUserRegisterationBody.ftl");
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("domain", domain);
            String username = user.getUserName();
            dataMap.put("userName", user.getFirstName());
            dataMap.put("encodedUserName", URLEncoder.encode(username, "UTF-8"));           
            dataMap.put("authenticationHash",  URLEncoder.encode(EncryptionUtil.encrypt(user.getEmail()), "UTF-8"));          
            String msgBodyStr = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);
            String msgSubjectStr = freeMarkerConfig.getTemplate("newUserRegisterationSubject.ftl").toString();
            SimpleMailMessage mailMsg = new SimpleMailMessage();
            mailMsg.setTo(user.getEmail());
            mailMsg.setSubject(msgSubjectStr);
            mailMsg.setText(msgBodyStr);
            mailMsg.setFrom(from);
            mailSender.send(mailMsg);
            
        }catch (IOException e) {
            logger.error("Issue sending email to user with userid: ",
                    user.getUserName());
                  throw new ServiceException(e.getMessage());
              } catch (TemplateException e) {
                  logger.error(
                     "Issue sending email to user with template: {} and userName: {} ",
                         new Object[] {template, user.getUserName() }, e);
                 throw new ServiceException(e.getMessage());
             }
    }
    
    

    /**
     * @param user {@link User}
     * @throws ServiceException ServiceException
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void sendPasswordResetEmail(User user) throws ServiceException {
        if (user == null) {
            logger.debug("User trying to reset password was null.");
            return;
        }

        Template template = null;
        UserPasswordReset userPasswordReset = null;
        
        try {
            template = freeMarkerConfig.getTemplate("resetUserPasswordBody.ftl");

            Map<String, Object> dataMap = new HashMap<String, Object>();

            dataMap.put("domain", domain);
            String username = user.getUserName();
            dataMap.put("userName", username);
            dataMap.put("encodedUserName", URLEncoder.encode(username, "UTF-8"));
            String enc = MD5Base64EncoderUtil.encodeWithSalt(user.getEmail(), user.getId().toString());

            dataMap.put("authenticationHash", URLEncoder.encode(enc, "UTF-8"));
            
            Date dateNow = new Date ();
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
            
            Calendar c = Calendar.getInstance();
            c.setTime(dateNow);
            c.add(Calendar.DATE, resetPasswordLinkPeriod);   
            
            Date expiryDate = c.getTime();
     
            StringBuilder expiryDateString = new StringBuilder( dateFormat.format( expiryDate ) );
            StringBuilder expiryTimeString = new StringBuilder( timeFormat.format( expiryDate ) );
     
            logger.debug( "expiryDateString: '" + expiryDateString + "'");
            logger.debug( "expiryTimeString: '" + expiryTimeString + "'");
            logger.debug( "enc: '" + enc + "'");
            
            userPasswordReset = new UserPasswordReset();
            userPasswordReset.setAartUserId(user.getId());
            userPasswordReset.setPasswordExpirationDate(expiryDate); 
            userPasswordReset.setActiveFlag(true);
            userPasswordReset.setAuthToken(enc);
            userPasswordResetDao.insert(userPasswordReset);
            
            dataMap.put("expiryDate", expiryDateString);
            
            dataMap.put("expiryTime", expiryTimeString); 

            String msgBodyStr = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);

            String msgSubjectStr = freeMarkerConfig.getTemplate("resetUserPasswordSubject.ftl").toString();

            SimpleMailMessage mailMsg = new SimpleMailMessage();

            mailMsg.setTo(user.getEmail());
            mailMsg.setSubject(msgSubjectStr);
            mailMsg.setText(msgBodyStr);
            mailMsg.setFrom(from);

            mailSender.send(mailMsg);
        } catch (IOException e) {
            logger.error("Issue sending email to user with userid: ",
              user.getUserName());
            throw new ServiceException(e.getMessage());
        } catch (TemplateException e) {
            logger.error(
               "Issue sending email to user with template: {} and userName: {} ",
                   new Object[] {template, user.getUserName() }, e);
           throw new ServiceException(e.getMessage());
       }
    }
    /* 
     * Prasanth: US16352: added new function because existing one is not supporting in spring batch because of transactional   
     */
    public final void sendUserActivationMsgUpload(UserOrganizationsGroups userGroup)
            throws ServiceException {
        try {
        	List<ActivationEmailTemplate> activationEmailtemplates=activationEmailTemplateService.getTemplateByAssesmentAndState(userGroup.getUser().getAssessmentProgramId(), userGroup.getUser().getContractingOrganization().getId());
        	ActivationEmailTemplate selectedactivationEmailtemplate=new ActivationEmailTemplate();
        	int listsize=activationEmailtemplates.size();
        	for(ActivationEmailTemplate emailtemplate:activationEmailtemplates){
        		if(listsize>1){
        			if(!emailtemplate.getIsDefault()){
        				selectedactivationEmailtemplate=emailtemplate;
        			}
        		}else{
        			selectedactivationEmailtemplate=emailtemplate;
        		}
        	}
           // template = freeMarkerConfig.getTemplate("userActivationBody.ftl");

           /* Map<String, Object> dataMap = new HashMap<String, Object>();

            dataMap.put("domain", domain);
            dataMap.put("dataObject", userGroup);

            String msgBodyStr = FreeMarkerTemplateUtils
                    .processTemplateIntoString(template, dataMap);

            String msgSubjectStr = freeMarkerConfig.getTemplate(
                    "userActivationSubject.ftl").toString();

            SimpleMailMessage mailMsg = new SimpleMailMessage();

            mailMsg.setTo(userGroup.getUser().getEmail());
            mailMsg.setSubject(msgSubjectStr);
            mailMsg.setText(msgBodyStr);
            mailMsg.setFrom(from);
            mailSender.send(mailMsg);*/
        	 send(userGroup,selectedactivationEmailtemplate);


        } catch (Exception e) {
            logger.error("Issue sending email to user with userid: ",
                    userGroup.getUser().getUserName());
            logger.error( "failed!", e );
            throw new ServiceException(e.getMessage());
        }
    }
    
    /**
     *  US-14985: Method for sending mail to the users whose password is going to 
     * expire in next 30 days.
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void sendPasswordExpirationWarningResetEmail(User user) throws ServiceException {
        if (user == null) {
            logger.debug("User trying to reset password was null.");
            return;
        }
        logger.debug("User name mail to be sent :"+user.getUserName());
        logger.debug("EmailId to be sent :"+user.getEmail());
        
        Template template = null;
        UserPasswordReset userPasswordReset = null;
        
        try {
        	logger.debug("Warning Template Loading");
        	
            template = freeMarkerConfig.getTemplate("passwordExpirationWarningBody.ftl");
            logger.debug("Warning Template Loaded");

            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("domain", domain);
            String username = user.getUserName();
            dataMap.put("userName", username);
            dataMap.put("encodedUserName", URLEncoder.encode(username, "UTF-8"));
            String enc = MD5Base64EncoderUtil.encodeWithSalt(user.getEmail(), user.getId().toString());

            dataMap.put("authenticationHash", URLEncoder.encode(enc, "UTF-8"));
            
            Date dateNow = new Date ();
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
            
            Calendar c = Calendar.getInstance();
            c.setTime(dateNow);
            c.add(Calendar.DATE, resetPasswordLinkPeriod);   
            
            Date expiryDate = c.getTime();
     
            StringBuilder expiryDateString = new StringBuilder( dateFormat.format( expiryDate ) );
            StringBuilder expiryTimeString = new StringBuilder( timeFormat.format( expiryDate ) );
     
            logger.debug( "expiryDateString: '" + expiryDateString + "'");
            logger.debug( "expiryTimeString: '" + expiryTimeString + "'");
            logger.debug( "enc: '" + enc + "'");
            logger.debug( "expiryDateString: '" + expiryDateString + "'");
            logger.debug( "expiryTimeString: '" + expiryTimeString + "'");
            logger.debug( "enc: '" + enc + "'");
            
            userPasswordReset = new UserPasswordReset();
            userPasswordReset.setAartUserId(user.getId());
            userPasswordReset.setPasswordExpirationDate(expiryDate); 
            userPasswordReset.setActiveFlag(true);
            userPasswordReset.setAuthToken(enc);
            userPasswordResetDao.insert(userPasswordReset);
            
            dataMap.put("expiryDate", expiryDateString);
            
            dataMap.put("expiryTime", expiryTimeString); 

            String msgBodyStr = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);

            String msgSubjectStr = freeMarkerConfig.getTemplate("passwordExpirationWarningSubject.ftl").toString();

            SimpleMailMessage mailMsg = new SimpleMailMessage();
            logger.debug( "Sent to Email: '" + user.getEmail() + "'");
            logger.debug( "Email Subject: '" + msgSubjectStr + "'");
            logger.debug( "Message Body: '" + msgBodyStr + "'");
            logger.debug( "Email from: '" + from + "'");
            mailMsg.setTo(user.getEmail());
            mailMsg.setSubject(msgSubjectStr);
            mailMsg.setText(msgBodyStr);
            mailMsg.setFrom(from);

            mailSender.send(mailMsg);
        } catch (IOException e) {
            logger.error("Issue sending email to user with userid: " + e);
            logger.error( "failed warning IO", e );
            throw new ServiceException(e.getMessage());
        } catch (TemplateException e) {
            logger.error(
               "Issue sending email to user with template: {} and userName: {} ",
                   new Object[] {template, user.getUserName() }, e);
            logger.error( "failed Warning Template", e );
           throw new ServiceException(e.getMessage());
       }
    }

    /**
     * US-14985: Method to send the mail to the users whose password is expired and need to change before login
     */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void sendPasswordExpiringAlertEmail(User userPassExpiring)
			throws ServiceException {
	    if (userPassExpiring == null) {
            logger.debug("User trying to reset password was null.");
            return;
        }
	    logger.debug("User name mail to be sent :"+userPassExpiring.getUserName());
	    logger.debug("EmailId to be sent :"+userPassExpiring.getEmail());
        Template template = null;
        UserPasswordReset userPasswordReset = null;
        try {
        	logger.debug("Expiration template loading");
            template = freeMarkerConfig.getTemplate("passwordExpiringAlertBody.ftl");
            logger.debug("Expiration template loaded");

            Map<String, Object> dataMap = new HashMap<String, Object>();

            dataMap.put("domain", domain);
            String username = userPassExpiring.getUserName();
            dataMap.put("userName", username);
            dataMap.put("encodedUserName", URLEncoder.encode(username, "UTF-8"));
            String enc = MD5Base64EncoderUtil.encodeWithSalt(userPassExpiring.getEmail(), userPassExpiring.getId().toString());

            dataMap.put("authenticationHash", URLEncoder.encode(enc, "UTF-8"));
            
            Date dateNow = new Date ();
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
            
            Calendar c = Calendar.getInstance();
            c.setTime(dateNow);
            c.add(Calendar.DATE, resetPasswordLinkPeriod);   
            
            Date expiryDate = c.getTime();
     
            StringBuilder expiryDateString = new StringBuilder( dateFormat.format( expiryDate ) );
            StringBuilder expiryTimeString = new StringBuilder( timeFormat.format( expiryDate ) );
     
            logger.debug( "expiryDateString: '" + expiryDateString + "'");
            logger.debug( "expiryTimeString: '" + expiryTimeString + "'");
            logger.debug( "enc: '" + enc + "'");
            logger.debug( "expiryDateString: '" + expiryDateString + "'");
            logger.debug( "expiryTimeString: '" + expiryTimeString + "'");
            logger.debug( "enc: '" + enc + "'");
            
            userPasswordReset = new UserPasswordReset();
            userPasswordReset.setAartUserId(userPassExpiring.getId());
            userPasswordReset.setPasswordExpirationDate(expiryDate); 
            userPasswordReset.setActiveFlag(true);
            userPasswordReset.setAuthToken(enc);
            userPasswordReset.setRequestType("ACTIVATION");
            userPasswordResetDao.insert(userPasswordReset);
            
            dataMap.put("expiryDate", expiryDateString);
            
            dataMap.put("expiryTime", expiryTimeString); 
            
            dataMap.put("resetPasswordLinkPeriod", resetPasswordLinkPeriod);

            String msgBodyStr = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);

            Template subjectTemplate = freeMarkerConfig.getTemplate("passwordExpiringAlertSubject.ftl");
            String msgSubjectStr = FreeMarkerTemplateUtils.processTemplateIntoString(subjectTemplate, dataMap);

            SimpleMailMessage mailMsg = new SimpleMailMessage();
            
            logger.debug( "Sent to Email: '" + userPassExpiring.getEmail() + "'");
            logger.debug( "Email Subject: '" + msgSubjectStr + "'");
            logger.debug( "Message Body: '" + msgBodyStr + "'");
            logger.debug( "Email from: '" + from + "'");
           
            mailMsg.setTo(userPassExpiring.getEmail());
            mailMsg.setSubject(msgSubjectStr);
            mailMsg.setText(msgBodyStr);
            mailMsg.setFrom(from);

            mailSender.send(mailMsg);
        } catch (IOException e) {
            logger.error("Issue sending Expiration email to user with userid: "+e);
            logger.error( "failed expire IO!", e );
            throw new ServiceException(e.getMessage());
            
        } catch (TemplateException e) {
            logger.error(
               "Issue sending email to user with template: {} and userName: {} "+
                   new Object[] {template, userPassExpiring.getUserName() }, e);
            logger.error( "failed expire Template!", e );
           throw new ServiceException(e.getMessage());
       }
	}
	
	
	 public  void sendMonitorScorerMsg(UserDTO scorerDetails,
				List<ScorerTestStudentsSessionDTO> studentDetails,
				UserDetailImpl userDetails,String subject,String pathway,String CCQTestName,Long monitorassessmentProgram,String stageName)
	            throws ServiceException {
	        Template template = null;
	        try {

	            template = freeMarkerConfig.getTemplate("monitorScorerScorersBody.ftl");

	            Map<String, Object> dataMap = new HashMap<String, Object>();
	            List<String> studentList = new ArrayList<String>();
	            for (ScorerTestStudentsSessionDTO stud : studentDetails) {
	            	String stdlist = stud.getFirstName();
	            	if((stud.getMi() != null) && (stud.getMi().trim().length() > 0)){	            		
	            		stdlist = stdlist.concat(", "+stud.getMi());
	            	}
	            	stdlist = stdlist.concat(", "+stud.getLastName());
					studentList.add(stdlist);
				}
	            AssessmentProgram assessmentProgName = assessProgDao.findByAssessmentProgramId(monitorassessmentProgram);
	            dataMap.put("domain", domain);
	            dataMap.put("dataScorerObject", scorerDetails);
	            dataMap.put("dataStudentObject", studentList);
	            dataMap.put("userObject", userDetails);
	            dataMap.put("subject", subject);
	            dataMap.put("pathway", pathway);
	            if(!StringUtils.isBlank(stageName)){
	            	dataMap.put("stageName", stageName);
	            } else {
	            	dataMap.put("stageName", "");
	            }
	            if(assessmentProgName.getAbbreviatedname().equals("CPASS")){
	            	dataMap.put("pathwayLabel", "Pathway");
	            }else {
	            	dataMap.put("pathwayLabel", "Grade");
	            }
	            dataMap.put("ccqtestname", CCQTestName);
	            
	            String msgBodyStr = FreeMarkerTemplateUtils
	                    .processTemplateIntoString(template, dataMap);

	            String msgSubjectStr = freeMarkerConfig.getTemplate(
	                    "monitorScorerScorersSubject.ftl").toString();

	            MimeMessage mimeMessage = mailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8"); 
	            
	            mimeMessage.setContent(msgBodyStr , "text/html");
	            helper.setTo(scorerDetails.getEmail());
	            helper.setSubject(msgSubjectStr);
	            helper.setFrom(from);
	            if(!scorerDetails.getEmail().equalsIgnoreCase(userDetails.getEmail())){
	            	helper.setCc(userDetails.getEmail());
	            }
	            mimeMessage.saveChanges();
	            
	            mailSender.send(mimeMessage);

	        } catch (IOException e) {
	            logger.error("Issue sending email to user with userid: ",
	                    scorerDetails.getFirstName());
	            throw new ServiceException(e.getMessage());
	        } catch (TemplateException e) {
	            logger.error(
	                    "Issue sending email to user with template: {} and userName: {} ",
	                    new Object[] {template, scorerDetails.getFirstName() }, e);
	            throw new ServiceException(e.getMessage());
	        } catch (AddressException e) {
	        	throw new ServiceException(e.getMessage());
			} catch (MessagingException e) {
				throw new ServiceException(e.getMessage());
			}
	    }
	 
		public final void sendUserFeedback(FeedBack feedBack) throws ServiceException {
			Template template = null;
			try {
				template = freeMarkerConfig.getTemplate("userFeedback.ftl");
				
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("domain", domain);
				dataMap.put("feedBack", feedBack);
				
				String msgBodyStr = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);
				String msgSubjectStr = "Feedback from "+feedBack.getUserName();
				
				if (StringUtils.isNotBlank(userFeedbackSubmissionEmail)) {
					SimpleMailMessage mailMsg = new SimpleMailMessage();
					mailMsg.setTo(userFeedbackSubmissionEmail);
					mailMsg.setSubject(msgSubjectStr);
					mailMsg.setText(msgBodyStr);
					mailMsg.setFrom(from);
					mailSender.send(mailMsg);
				} else {
					logger.info("userFeedbackSubmissionEmail is blank, skipping email -- " +
						"From = \"" + feedBack.getEmail() + ", " +
						"Subject = \"" + msgSubjectStr + "\", " +
						"Body = \"" + msgBodyStr + "\"");
				}
			} catch (Exception e) {
				logger.error("Issue sending email with user feedback from : ", feedBack.getEmail());
				throw new ServiceException(e.getMessage());
			}
		}
	 
	public  void send( UserOrganizationsGroups userGroup,ActivationEmailTemplate selectedactivationEmailtemplate)
	{
		 boolean eplogorequired=false;
		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", auth);
		properties.put("mail.smtp.starttls.enable", starttlsEnable);
		properties.put("mail.user", userName);
		properties.put("mail.password", password);
		try{
			   StringBuffer replacement=new StringBuffer();           
	            replacement.append(domain);
	            replacement.append("/activate.htm?an=");
	            replacement.append(userGroup.getActivationNo());                     	
	            String newBody=selectedactivationEmailtemplate.getEmailBody();
	            eplogorequired=selectedactivationEmailtemplate.getIncludeEpLogo();
	            if(eplogorequired){
	            newBody="<img src=\"cid:"+kiteAartLogo.getFilename()+"\" height=\"177\" width=\"358\" /><br>"+newBody;
	            }
	            int startIndex = newBody.indexOf("<div");
	            int endIndex = newBody.indexOf("</div>");           
	            StringBuffer emailBody=new StringBuffer();           
	            emailBody.append(newBody);
	            emailBody.replace(startIndex, endIndex+6, replacement.toString());
	            String msgSubjectStr = selectedactivationEmailtemplate.getEmailSubject(); 
			
			// creates a new session with an authenticator
			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}
			};
			Session session = Session.getInstance(properties, auth);

			// creates a new e-mail message
			Message msg = new MimeMessage(session);	 
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] toAddresses = { new InternetAddress(userGroup.getUser().getEmail()) };
			msg.setRecipients(Message.RecipientType.TO, toAddresses);
			msg.setSubject(msgSubjectStr);
			msg.setSentDate(new Date());
			// creates message partm
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(emailBody.toString(), "text/html");
			// creates multi-part
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			// adds inline image attachments
			if(eplogorequired){					
				MimeBodyPart imagePart = new MimeBodyPart();
				imagePart.setHeader("Content-ID", "<" + kiteAartLogo.getFilename() + ">");
				imagePart.attachFile(kiteAartLogo.getFile());
				multipart.addBodyPart(imagePart);
			}
			msg.setContent(multipart);
			

            logger.info( "Email Subject: '" + msgSubjectStr + "'");
            logger.info( "Email from: '" + from + "'");
           
			
			Transport.send(msg);
		}catch(AddressException ae){
			logger.error(
					"Issue sending email AddressException "
					+ ae.getMessage());

		}catch(MessagingException ME){
			ME.printStackTrace();
		}catch(IOException IE){
			logger.error(
					"Issue while attaching the logo to email  "
					+ IE.getMessage());
		}

	}	 
	
	@Override
	public void sendKIDSErrorEmail(KidRecord failedRecord, String step, Throwable throwable) throws IOException, TemplateException {
		if (StringUtils.isBlank(kidsErrorEmailToList)) {
			logger.debug("KIDS error email recipient list is blank, skipping email");
			return;
		}
		
		List<String> recipients = buildRecipientsForErrorEmail();
		if (CollectionUtils.isEmpty(recipients)) {
			logger.debug("KIDS error email recipient list is empty, skipping email");
			return;
		}
		
		final Date now = new Date();
		final DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
		final DateFormat timeFormatter = new SimpleDateFormat("hh:mm:ss a z");
		final TimeZone tz = TimeZone.getTimeZone("America/Chicago");
		dateFormatter.setTimeZone(tz);
		timeFormatter.setTimeZone(tz);
		
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("dateStr", dateFormatter.format(now));
		paramsMap.put("timeStr", timeFormatter.format(now));
		paramsMap.put("step", step);
		paramsMap.put("env", StringUtils.isBlank(kidsErrorMailEnv) ? null : kidsErrorMailEnv);
		
		// add more parameters here, if necessary
		boolean dataIdentifiable = failedRecord != null && (failedRecord.getRecordType() != null || failedRecord.getRecordCommonId() != null);
		boolean errorIdentifiable = throwable != null;
		paramsMap.put("dataIdentifiable", dataIdentifiable);
		paramsMap.put("errorIdentifiable", errorIdentifiable);
		
		String recordType = null;
		String recordId = null;
		if (dataIdentifiable) {
			if (failedRecord.getRecordType() != null) {
				recordType = failedRecord.getRecordType();
			}
			if (failedRecord.getRecordCommonId() != null) {
				recordId = failedRecord.getRecordCommonId();
			}
		}
		paramsMap.put("recordType", recordType);
		paramsMap.put("recordId", recordId);
		
		if (errorIdentifiable) {
			paramsMap.put("errorType", throwable.getClass().getName());
			paramsMap.put("errorMessage", throwable.getMessage());
			
			StackTraceElement[] ste = throwable.getStackTrace();
			StackTraceElement locationOfError = null;
			for (int x = 0; x < ste.length; x++) {
				if (ste[x] != null && ste[x].getClassName() != null && ste[x].getClassName().startsWith("edu.ku.cete")) {
					locationOfError = ste[x];
					break;
				}
			}
			
			paramsMap.put("locationAvailable", locationOfError != null);
			if (locationOfError != null) {
				paramsMap.put("offendingClass", locationOfError.getClassName());
				paramsMap.put("offendingMethod", locationOfError.getMethodName());
				paramsMap.put("offendingLineNumber", locationOfError.getLineNumber());
			}
		}
		
		String emailSubject = (StringUtils.isBlank(kidsErrorMailEnv) ? "" : (kidsErrorMailEnv + " - ")) +
				"Educator Portal KIDS Import Error";
		Template template = freeMarkerConfig.getTemplate("kidsErrorEmail.ftl");
		String emailBody = FreeMarkerTemplateUtils.processTemplateIntoString(template, paramsMap);
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(recipients.toArray(new String[]{}));
		mail.setSubject(emailSubject);
		mail.setText(emailBody);
		mail.setFrom(from);
		mailSender.send(mail);
	}
	
	private List<String> buildRecipientsForErrorEmail() {
		List<String> recipients = new ArrayList<String>();
		if (StringUtils.isNotBlank(kidsErrorEmailToList)) {
			String[] recipientsArr = kidsErrorEmailToList.split(",");
			for (int x = 0; x < recipientsArr.length; x++) {
				if (StringUtils.isNotBlank(recipientsArr[x])) {
					recipients.add(StringUtils.trim(recipientsArr[x]));
				}
			}
		}
		return recipients;
	}

	@Override
	public final void sendEmailToDTCUsers(HashSet<Long> userIds, Long claimUserId)throws ServiceException {
		
		User user = null;
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		try{
			List<ActivationEmailTemplate> activationEmailtemplates=activationEmailTemplateService.getTemplateForClaimUsers("Claim User Email Template");
	    	ActivationEmailTemplate selectedactivationEmailtemplate=new ActivationEmailTemplate();
	    	for(ActivationEmailTemplate emailtemplate:activationEmailtemplates){
	    			selectedactivationEmailtemplate=emailtemplate;
	    	}
	    	String claimUserName = userService.get(claimUserId).getDisplayName();
	    	String claimUserFirstName = userService.get(claimUserId).getFirstName();
	    	for(Long userId:userIds){
	    		if(userId!=null && userDetails.getUserId()!=userId){
	    			user = userService.get(userId);
		    		sendEmailsToUsers(user, selectedactivationEmailtemplate, claimUserName, claimUserFirstName);
	    		}
	    	}
		}catch (Exception e) {
            logger.error("Issue sending email to user with userid: ",
                    userIds);
            throw new ServiceException(e.getMessage());
        }
	}
	
	public void sendEmailsToUsers(User user, ActivationEmailTemplate selectedactivationEmailtemplate,
			String claimUserName, String claimUserFirstName) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User loggedInUserDetails=userDetails.getUser();
		boolean eplogorequired = false;
		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", auth);
		properties.put("mail.smtp.starttls.enable", starttlsEnable);
		properties.put("mail.user", userName);
		properties.put("mail.password", password);
		try {
			StringBuffer linkContent = new StringBuffer();
			linkContent.append(domain);
			linkContent.append("/configuration.htm?view=externalLink");
			String emailBody = selectedactivationEmailtemplate.getEmailBody();
			eplogorequired = selectedactivationEmailtemplate.getIncludeEpLogo();
			if (eplogorequired) {
				emailBody = "<img src=\"cid:" + kiteAartLogo.getFilename() + "\" height=\"177\" width=\"358\" /><br>" + emailBody;
			}
			emailBody = emailBody.replace("[claimUserDisplayName]", claimUserName);
			emailBody = emailBody.replace("[userDisplayName]", loggedInUserDetails.getDisplayName());
			emailBody = emailBody.replace("[userFirstName]", claimUserFirstName);
			emailBody = emailBody.replace("[District Name]", loggedInUserDetails.getOrganizationName());
			emailBody = emailBody.replace("[link]", linkContent.toString());
			
			String msgSubjectStr = selectedactivationEmailtemplate.getEmailSubject();

			// creates a new session with an authenticator
			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}
			};
			Session session = Session.getInstance(properties, auth);

			// creates a new e-mail message
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] toAddresses = { new InternetAddress(user.getEmail()) };
			msg.setRecipients(Message.RecipientType.TO, toAddresses);
			msg.setSubject(msgSubjectStr);
			msg.setSentDate(new Date());
			// creates message partm
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(emailBody.toString(), "text/html");
			// creates multi-part
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			// adds inline image attachments
			if (eplogorequired) {
				MimeBodyPart imagePart = new MimeBodyPart();
				imagePart.setHeader("Content-ID", "<" + kiteAartLogo.getFilename() + ">");
				imagePart.attachFile(kiteAartLogo.getFile());
				multipart.addBodyPart(imagePart);
			}
			msg.setContent(multipart);

			logger.info("Email Subject: '" + msgSubjectStr + "'");
			logger.info("Email from: '" + from + "'");

			Transport.send(msg);
		} catch (AddressException ae) {
			logger.error("Issue sending email AddressException ", ae);
		} catch (MessagingException me) {
			logger.error("Issue while preparing email ", me);
		} catch (IOException ioe) {
			logger.error("Issue while attaching the logo to email  ", ioe);
		}
	}
	
	public void sendKIDSEmail(Long userId, List<KSDERecord> records) throws ServiceException, ParseException{
    	Template template = null;
    	
    	User user = null;
    	if(userId!=null)
    		user = userService.get(userId);
    	else
    		logger.error("User is null "+userId);
    	
    	if(user!=null && records!=null){
    		String emailId = user.getEmail();
	    	logger.info("Sending email:- userid: "+userId+" email: "+emailId);	    	
            
	    	boolean emailExceptions = false;
	    	try {
	    		String kidsEmailSubject = new StringBuilder(categoryService.selectByCategoryCodeAndType(templateSubjectCode, templateTypeCode).getCategoryName()).append(".ftl").toString();
	    	    
	    		Map<String, Object> dataMap = new HashMap<String, Object>();
	    	    StringBuilder msgBody = new StringBuilder();
				        
	        	template = freeMarkerConfig.getTemplate("kidsEmailMessage.ftl");
	        	StringBuffer replacement=new StringBuffer();           
	            replacement.append(domain);
	            replacement.append("/templates/dashboardAccess.htm");
	            
	        	dataMap.put("domain", replacement.toString());	            
	        	msgBody.append(FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap));	    	    
		    	
	            String msgSubjectStr = freeMarkerConfig.getTemplate(kidsEmailSubject).toString();

	            SimpleMailMessage mailMsg = new SimpleMailMessage();
	            
	            mailMsg.setTo(emailId);
	            mailMsg.setSubject(msgSubjectStr);
	            mailMsg.setText(msgBody.toString());
	            mailMsg.setFrom(from);

	            mailSender.send(mailMsg);
	            
	            Date sentDate = new Date();
	            
	            for(KSDERecord record: records){
	            	if(record.getReason()!=null && !record.getReason().trim().isEmpty()){
	            		record.setReason(record.getReason()+" ; Email sent to user: "+userId+". ");
	            	} else{
	            		record.setReason("Email sent to user: "+userId+". ");
	            	}
	            		
	            	record.setEmailSentDate(sentDate);
	            	record.setEmailSent(true);
	            	
	            	if(record.getRecordType().equals(tascRecordType)){
	            		ksdeRecordStagingService.updateTASCEmailStatus(record);
	            	} else if(record.getRecordType().equals(testRecordType) || record.getRecordType().equals(exitRecordType)){
	            		ksdeRecordStagingService.updateKIDSEmailStatus(record);
	            	}    
	            }

	        } catch (IOException e) {
	        	emailExceptions = true;
	        	logger.error("Issue sending email to user with userids: ",userId);
	            throw new ServiceException(e.getMessage());
	            
	        } catch (TemplateException e) {
	        	emailExceptions = true;
	        	logger.error("Issue sending email to user with template: {} and userids: {} ",new Object[] {template, userId }, e);
	            throw new ServiceException(e.getMessage());
	        }
	    	finally{
	    		if(emailExceptions){
	    			for(KSDERecord record: records){
	    				if(record.getReason()!=null && !record.getReason().trim().isEmpty()){
	    					record.setReason(record.getReason()+" ; Send email failed. ");
	    				}else{
	    					record.setReason("Send email failed. ");
	    				}	            		
		            	
		            	record.setEmailSent(false);
		            	
		            	if(record.getRecordType().equals(tascRecordType)){
		            		ksdeRecordStagingService.updateTASCEmailStatus(record);
		            	}else if(record.getRecordType().equals(testRecordType) || record.getRecordType().equals(exitRecordType)){
		            		ksdeRecordStagingService.updateKIDSEmailStatus(record);
		            	}	            	
		            }
	    		}
	    	}
    	} else{
    		logger.info("UserId or Email body content is null");
    	}    	
	}

	@Override
	public void sendEmailForGRFGradeChange(String organizationName) {
		SimpleMailMessage mailMsg = new SimpleMailMessage();
		String[] emails= null;
		Category category = categoryService.selectByCategoryCodeAndType(grfEmailIds, grfDataTypeCode);
		if (category != null && (category.getCategoryName() != null || !StringUtils.isEmpty(category.getCategoryName()))){
			emails = category.getCategoryName().trim().split(",");			
			    mailMsg.setTo(emails);
		        mailMsg.setSubject("DLM GRF State "+organizationName+" Student grade changed by state");
		        mailMsg.setText(grfEmailMsg);
		        mailMsg.setFrom(from);

		        mailSender.send(mailMsg);
		}else{
			logger.info("No Email Ids set for GRF");
		}
       
		
	}
}
