package edu.ku.cete.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import edu.ku.cete.ksde.kids.KidsSettings;

@Component
public class ImmediateWebServiceInputConfiguration implements WebServiceInputConfiguration{
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
    @Value("${kansasImmediateWebServiceStartTime}")
    private String kansasImmediateWebServiceStartTime;
    /**
     * kansasImmediateWebServiceEndTime.
     */
    @Value("${kansasImmediateWebServiceEndTime}")
    private String kansasImmediateWebServiceEndTime;
    /**
     * kansasImmediateWebServiceEndTime.
     */
    @Value("${kansasImmediateWebServiceSchoolYear}")
    private String kansasImmediateWebServiceSchoolYear;
    /**
     * kansasImmediateWebServiceEndTime.
     */
    @Value("${kidsByDateInputParameterImmediateUploadCode}")
    private String kidsByDateInputParameterImmediateUploadCode;
    
    /**
     * rosterImmediateWebServiceStartTime.
     */
    @Value("${rosterImmediateWebServiceStartTime}")
    private String rosterImmediateWebServiceStartTime;
    /**
     * rosterImmediateWebServiceEndTime.
     */
    @Value("${rosterImmediateWebServiceEndTime}")
    private String rosterImmediateWebServiceEndTime;    
    /**
     * rosterByDateInputParameterImmediateUploadCode
     */
    @Value("${rosterByDateInputParameterImmediateUploadCode}")
    private String rosterByDateInputParameterImmediateUploadCode;
    
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
	 * @return the kansasImmediateWebServiceStartTime
	 */
	public String getKansasImmediateWebServiceStartTime() {
		return kansasImmediateWebServiceStartTime;
	}
	/**
	 * @return the kansasImmediateWebServiceEndTime
	 */
	public String getKansasImmediateWebServiceEndTime() {
		return kansasImmediateWebServiceEndTime;
	}
	/**
	 * @return the kansasImmediateWebServiceSchoolYear
	 */
	public String getKansasImmediateWebServiceSchoolYear() {
		return kansasImmediateWebServiceSchoolYear;
	}

	/**
	 * @return the kidsByDateInputParameterCode
	 */
	public String getKidsByDateInputParameterImmediateUploadCode() {
		return kidsByDateInputParameterImmediateUploadCode;
	}
	/**
	 * @return rosterImmediateWebServiceStartTime
	 */
	public String getRosterImmediateWebServiceStartTime() {
		return rosterImmediateWebServiceStartTime;
	}	
	/**
	 * @return rosterImmediateWebServiceEndTime
	 */
	public String getRosterImmediateWebServiceEndTime() {
		return rosterImmediateWebServiceEndTime;
	}	
	/**
	 * @return the rosterByDateInputParameterCode
	 */
	public String getRosterByDateInputParameterImmediateUploadCode() {
		return rosterByDateInputParameterImmediateUploadCode;
	}
	@Override
	public String getKansasWebServiceStartTime() {
		return getKansasImmediateWebServiceStartTime();
	}
	@Override
	public String getKansasWebServiceEndTime() {
		return getKansasImmediateWebServiceEndTime();
	}
	@Override
	public String getKansasWebServiceSchoolYear() {
		return getKansasImmediateWebServiceSchoolYear();
	}
	@Override
	public String getKidsByDateInputParameterCode() {
		return getKidsByDateInputParameterImmediateUploadCode();
	}
	@Override
	public String getRosterWebServiceStartTime() {
		return getRosterImmediateWebServiceStartTime();
	}
	@Override
	public String getRosterWebServiceEndTime() {
		return getRosterImmediateWebServiceEndTime();
	}
	@Override
	public String getRosterByDateInputParameterCode() {
		return getRosterByDateInputParameterImmediateUploadCode();
	}

}
