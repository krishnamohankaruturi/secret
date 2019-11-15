/**
 * 
 */
package edu.ku.cete.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author mahesh
 * TODO pass the logger and print the start and end here itself.
 */
public class TimerUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(TimerUtil.class);
	private static final Log alogger = LogFactory.getLog(TimerUtil.class);
	
	private Date start = new Date();
	
	private Date end = new Date();
	
	private Long[] levels = {(long) 250 , (long) 500,(long) 1000,(long) 2000,(long) 4000, Long.MAX_VALUE};
	/**
	 * This is to force the use of getInstance. 
	 */
	private TimerUtil() {
	}
	
	public static TimerUtil getInstance() {
		return new TimerUtil();
	}

	public void start(){
		start = new Date();
	}
	
	public void stop(){
		end = new Date();
	}
	
	private boolean findLevel(Long interval,int i) {
		return interval <= levels[i];
	}
	
	public Long getInterval() {
		return end.getTime() - start.getTime();
	}
	
	public int getLevel() {
		Long interval = getInterval();
		boolean levelFound = false;
		int i = 0;
		while(!levelFound) {
			levelFound = findLevel(interval, i);
			i ++;
		}
		return i;
	}

	public void log(Logger inLogger, String message) {
		Logger loggerToUse = logger;
		if(inLogger != null) {
			loggerToUse = inLogger;
		}
//		if(getLevel() > 3) {
//			loggerToUse.error(message +" " + getInterval() + " milli seconds");
//		} else if(getLevel() > 2) {
//			loggerToUse.warn(message +" " + getInterval() + " milli seconds");
//		} else if(getLevel() > 1) {
//			loggerToUse.debug(message +" " + getInterval() + " milli seconds");
//		} else {
			loggerToUse.debug(message +" " + getInterval() + " milli seconds");
		//}		
	}
	public void resetAndLog(Logger inLogger, String message) {
		this.stop();
		this.log(inLogger, message);
		this.start();
	}
	
	public void resetAndLogInfo(Log inLogger, String message) {
		this.stop();
		this.logInfo(inLogger, message);
		this.start();
	}
	
	public void logInfo(Log inLogger, String message) {
		Log aloggerToUse = alogger;
		if(inLogger != null) {
			aloggerToUse = inLogger;
		}
			aloggerToUse.info(message +" " + getInterval() + " milli seconds");
	}
	

}
