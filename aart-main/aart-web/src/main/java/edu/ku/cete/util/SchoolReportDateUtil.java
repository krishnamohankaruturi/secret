package edu.ku.cete.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import edu.ku.cete.model.OrganizationDao;

@Component
public class SchoolReportDateUtil {
	
	@Autowired
	private OrganizationDao organizationDao;
		
	public String getDate(Long stateId) {
	    TimeZone tz = getTimeZoneForOrganization(stateId);
		Date createDate = new Date();
		if (tz == null) {
			// default to central, if necessary
			tz = TimeZone.getTimeZone("US/Central");
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		dateFormat.setTimeZone(tz);
		// Server is in GMT and need to convert to US/Central time else showing future date.
		return dateFormat.format(createDate);		
	}
	
	public TimeZone getTimeZoneForOrganization(Long organizationId) {
		String tzCode = organizationDao.getTimeZoneForOrg(organizationId);
		if (tzCode != null) {
			return TimeZone.getTimeZone(tzCode);
		}
		return null;
	}
}
