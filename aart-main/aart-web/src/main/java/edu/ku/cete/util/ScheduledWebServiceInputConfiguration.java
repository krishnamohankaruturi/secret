package edu.ku.cete.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import edu.ku.cete.ksde.kids.KidsSettings;

@Component
public class ScheduledWebServiceInputConfiguration implements WebServiceInputConfiguration{
    /**
     * wsConnectionUsername.
     */
    @Value("${wsConnectionUsername}")
    private String wsConnectionUsername;
    /**
     * wsConnectionPassword.
     */
    @Autowired
    private KidsSettings kidsSettings;
    /**
     * kansasImmediateWebServiceStartTime.
     */
    @Value("${kansasScheduledWebServiceStartTime}")
    private String kansasScheduledWebServiceStartTime;
    /**
     * kansasScheduledWebServiceEndTime.
     */
    @Value("${kansasScheduledWebServiceEndTime}")
    private String kansasScheduledWebServiceEndTime;
    /**
     * kansasScheduledWebServiceEndTime.
     */
    @Value("${kansasScheduledWebServiceSchoolYear}")
    private String kansasScheduledWebServiceSchoolYear;
    /**
     * kansasScheduledWebServiceEndTime.
     */
    @Value("${kidsByDateInputParameterScheduledUploadCode}")
    private String kidsByDateInputParameterScheduledUploadCode;
    /**
     * rosterScheduledWebServiceStartTime.
     */
    @Value("${rosterScheduledWebServiceStartTime}")
    private String rosterScheduledWebServiceStartTime;
    /**
     * rosterScheduledWebServiceEndTime.
     */
    @Value("${rosterScheduledWebServiceEndTime}")
    private String rosterScheduledWebServiceEndTime;    
    /**
     * rosterByDateInputParameterScheduledUploadCode
     */
    @Value("${rosterByDateInputParameterScheduledUploadCode}")
    private String rosterByDateInputParameterScheduledUploadCode;
    
	/**
	 * @return the wsConnectionUsername
	 */
	public String getWsConnectionUsername() {
		return wsConnectionUsername;
	}
	/**
	 * @return the wsConnectionPassword
	 */
	public String getWsConnectionPassword() {
		return kidsSettings.getEncryptedPassword();
	}
	/**
	 * @return the kansasScheduledWebServiceStartTime
	 */
	public String getKansasScheduledWebServiceStartTime() {
		return kansasScheduledWebServiceStartTime;
	}
	/**
	 * @return the kansasScheduledWebServiceEndTime
	 */
	public String getKansasScheduledWebServiceEndTime() {
		return kansasScheduledWebServiceEndTime;
	}
	/**
	 * @return the kansasScheduledWebServiceSchoolYear
	 */
	public String getKansasScheduledWebServiceSchoolYear() {
		return kansasScheduledWebServiceSchoolYear;
	}
	/**
	 * @return the kidsByDateInputParameterCode
	 */
	public String getKidsByDateInputParameterScheduledUploadCode() {
		return kidsByDateInputParameterScheduledUploadCode;
	}	
	/**
	 * @return rosterScheduledWebServiceStartTime
	 */
	public String getRosterScheduledWebServiceStartTime() {
		return rosterScheduledWebServiceStartTime;
	}	
	/**
	 * @return rosterScheduledWebServiceEndTime
	 */
	public String getRosterScheduledWebServiceEndTime() {
		return rosterScheduledWebServiceEndTime;
	}
	/**
	 * @return the rosterByDateInputParameterCode
	 */
	public String getRosterByDateInputParameterScheduledUploadCode() {
		return rosterByDateInputParameterScheduledUploadCode;
	}
	
	@Override
	public String getKansasWebServiceStartTime() {
		return getKansasScheduledWebServiceStartTime();
	}
	@Override
	public String getKansasWebServiceEndTime() {
		return getKansasScheduledWebServiceEndTime();
	}
	@Override
	public String getKansasWebServiceSchoolYear() {
		return getKansasScheduledWebServiceSchoolYear();
	}
	@Override
	public String getKidsByDateInputParameterCode() {
		return getKidsByDateInputParameterScheduledUploadCode();
	}
	@Override
	public String getRosterWebServiceStartTime() {
		return getRosterScheduledWebServiceStartTime();
	}
	@Override
	public String getRosterWebServiceEndTime() {
		return getRosterScheduledWebServiceEndTime();
	}
	@Override
	public String getRosterByDateInputParameterCode() {
		return getRosterByDateInputParameterScheduledUploadCode();
	}

}
