package edu.ku.cete.batch.kids.email;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.domain.common.Category;
import edu.ku.cete.model.UserDao;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.util.AartParseException;
import edu.ku.cete.util.DateUtil;

public class SendKIDSEmailsListener implements JobExecutionListener {
	final static Log logger = LogFactory.getLog(SendKIDSEmailsListener.class);
	
	@Value("${kidsEmailDateCode}")
	private String kidsEmailDateCode;
	
	@Value("${kidsEmailDateFormat}")
	private String kidsEmailDateFormat;
	
	@Value("${webServiceRecordTypeCode}")
	private String webServiceRecordTypeCode;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public void beforeJob(JobExecution jobExecution) { 
		logger.debug("--> beforeJob");

		logger.debug("<-- beforeJob");
	}

	@Override
	public void afterJob(JobExecution jobExecution) { 
		logger.debug("--> afterJob");
		Category kidsEmailDateCategory = categoryService.selectByCategoryCodeAndType(kidsEmailDateCode, webServiceRecordTypeCode);
		String kidsEmailDateStr = kidsEmailDateCategory.getCategoryName();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(kidsEmailDateFormat);
		try {
			Date kidsEmailDate = DateUtil.parseAndFail(kidsEmailDateStr, kidsEmailDateFormat);
			Calendar newKidsEmailDateCalendarObject = Calendar.getInstance(); 
			newKidsEmailDateCalendarObject.setTime(kidsEmailDate); 
			newKidsEmailDateCalendarObject.add(Calendar.DATE, 1);
		
			String newKidsEmailDate = simpleDateFormat.format(newKidsEmailDateCalendarObject.getTime());
			kidsEmailDateCategory.setCategoryName(newKidsEmailDate);
			categoryService.updateByPrimaryKey(kidsEmailDateCategory);
			
			logger.info("KIDS Email Scheduler date updated from "+kidsEmailDateStr+" to "+newKidsEmailDate);
		} catch (AartParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	 	logger.debug("<-- afterJob");
	}

	
}
