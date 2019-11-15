package edu.ku.cete.batch.kids.email;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.ksde.kids.result.KSDERecord;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.EmailService;
import edu.ku.cete.service.KSDERecordStagingService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.util.DateUtil;

public class SendKIDSEmails implements Tasklet {

	private static final Log LOGGER = LogFactory.getLog(SendKIDSEmails.class);

    @Value("${tascRecordType}")
    private String tascRecordType;
    @Value("${exitRecordType}")
    private String exitRecordType;
    @Value("${testRecordType}")
    private String testRecordType;
	
    @Autowired
	private KSDERecordStagingService ksdeRecordStagingService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserService userService;
	
	@Value("${webServiceRecordTypeCode}")
	private String webServiceRecordTypeCode;
	
	@Value("${kidsEmailDateCode}")
	private String kidsEmailDateCode;
	
	@Value("${kidsEmailDateFormat}")
	private String kidsEmailDateFormat;
	
	@Autowired
	private CategoryService categoryService;
	
	@Value("${wsAdminUserName}")
	private String wsAdminUserName;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		//Send KIDS Email Process
		LOGGER.info("Started executing SendKIDSEmails (" + new Date().toString() + ")");
	
		User user = userService.getByUserName(wsAdminUserName);
		UserDetailImpl userDetailImpl = new UserDetailImpl(user);
		// Create a token and set the security context
		PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(userDetailImpl, userDetailImpl.getPassword(), userDetailImpl.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);
		
		Category kidsEmailDateCategory = categoryService.selectByCategoryCodeAndType(kidsEmailDateCode, webServiceRecordTypeCode);
		String kidsEmailDateStr = kidsEmailDateCategory.getCategoryName();
		Date kidsEmailDate = DateUtil.parseAndFail(kidsEmailDateStr, kidsEmailDateFormat);
		
		List<KSDERecord> kidsRecords = ksdeRecordStagingService.getKidsStagingRecords(kidsEmailDate);
		List<KSDERecord> tascRecords = ksdeRecordStagingService.getTASCStagingRecords(kidsEmailDate);
		
		LOGGER.info("KIDS EmailDate: "+kidsEmailDate+"; kidsRecords: "+kidsRecords.size()+"; tascRecords: "+tascRecords.size());
		
		if(kidsRecords.size() > 0 || tascRecords.size() > 0){
			Map<Long, List<KSDERecord>> summarizedEmailList = new HashMap<Long, List<KSDERecord>>();
			List<KSDERecord> temp = new ArrayList<KSDERecord>();
			
			if(kidsRecords.size() > 0){
				for(KSDERecord kidsRecord : kidsRecords){
					List<Long> btc_dtc_List = new ArrayList<Long>();
					btc_dtc_List = userService.getKIDSEmailUsersPerSchoolIds(kidsRecord.getAypSchoolIdentifier(), kidsRecord.getAttendanceSchoolProgramIdentifier());
					kidsRecord.setEmailSentTo(StringUtils.join(btc_dtc_List, ','));
					for(Long userId : btc_dtc_List){
		        		temp = new ArrayList<KSDERecord>();
						if(summarizedEmailList.containsKey(userId)){
							temp = summarizedEmailList.get(userId);
							temp.add(kidsRecord);
						}
						else{
							temp.add(kidsRecord);
						}
						summarizedEmailList.put(userId, temp);
					}
					ksdeRecordStagingService.updateKIDSEmailStatus(kidsRecord);
				}
			}
			LOGGER.info("Updated DTC/BTC userids for "+kidsRecords.size()+" kidsRecords");
			
			if(tascRecords.size() > 0){
				for(KSDERecord tascRecord : tascRecords){
					List<Long> btc_dtc_List = new ArrayList<Long>();
					btc_dtc_List = userService.getKIDSEmailUsersPerSchoolIds(tascRecord.getAypSchoolIdentifier(), tascRecord.getAttendanceSchoolProgramIdentifier());
					tascRecord.setEmailSentTo(StringUtils.join(btc_dtc_List, ','));
					
					for(Long userId : btc_dtc_List){
						temp = new ArrayList<KSDERecord>();
						if(summarizedEmailList.containsKey(userId)){
							temp = summarizedEmailList.get(userId);
							temp.add(tascRecord);
						}
						else{
							temp.add(tascRecord);
						}
						summarizedEmailList.put(userId, temp);
					}
					ksdeRecordStagingService.updateTASCEmailStatus(tascRecord);
				}
			}
			LOGGER.info("Updated DTC/BTC userids for "+tascRecords.size()+" tascRecords");
			
			int i=0;
			
			List<Long> userIdKeys = new ArrayList<Long>(summarizedEmailList.keySet());
			while(i<userIdKeys.size()){
				LOGGER.info("Sending email to userid: "+userIdKeys.get(i));
				if(userIdKeys.get(i)!=null && summarizedEmailList.get(userIdKeys.get(i))!=null)
					emailService.sendKIDSEmail(userIdKeys.get(i), summarizedEmailList.get(userIdKeys.get(i)));
				i++;
			}
			LOGGER.info("Email sent to "+userIdKeys.size()+" users.");
		}
		
		LOGGER.info("Completed executing SendKIDSEmails (" + new Date().toString() + ")");
		return RepeatStatus.FINISHED;
	}
	
}
