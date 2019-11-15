/**
 * 
 */
package edu.ku.cete.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author m802r921
 * the specification that has field limits, allowable values and attribute mappings.
 * This class dictates what is valid, what files will be rejected and so on.
 */
@Component
public class UploadSpecification {
	/**
     * Delimiter like , for list type porperty.
     */
    private static String OUTER_DELIM = ",";
    /**
     * Delimiter for hash map like property.
     */
    private static String INNER_DELIM = "-";
    /**
     * header column to attribute type mapping.
     */
    private Map<String, String> enrollmentColumnAttributeMap = new HashMap<String, String>();
    /**
     * header column for scrs upload.
     */
    private Map<String, String> scrsColumnAttributeMap = new HashMap<String, String>();

    @Value("${uploadedFile.path}")
    private String path;

    @Value("${userupload.maxRecords}")
    private int maxRecords;

    private Map<String, String> userColumnMap = new HashMap<String, String>();

    @Value("${userupload.userRecordType}")
    private String userRecordType;
    
    private Map<String, String> pdTrainingResultsMap = new HashMap<String, String>();
    @Value("${pdTrainingUpload.userRecordType}")
    private String pdTrainingUploadType;

    @Value("${includeExcludeRecordType}")
    private String includeExcludeRecordType;

    /**
     * the category code of the kansas web service url.
     */
    @Value("${kansasWebServiceUrlCode}")
    private String kansasWebServiceUrlCode;
    /**
     * the category code of the kansas web service url.
     */
    @Value("${kansasWebServiceScheduleFrequencyCode}")
    private String kansasWebServiceScheduleFrequencyCode;
    /**
     * the category code of the kansas web service url.
     */
    @Value("${kansasWebServiceScheduleFrequencyDelta}")
    private String kansasWebServiceScheduleFrequencyDelta;
    /**
     * tomcatStartTimeCode.
     */
    @Value("${tomcatStartTimeCode}")
    private String tomcatStartTimeCode;
    /**
     * tomcatStartTimeCode.
     */
    @Value("${retryTimeCode}")
    private String retryTimeCode;
    
    private String[] errorMessageFileHeaders;

    /**
     * record types for csv uploads.
     */
    private String csvRecordTypeCode;
    /**
     * webServiceRecordTypeCode.
     */
    private String webServiceRecordTypeCode;
    private String enrollmentRecordType;
    private String xmlEnrollmentRecordType;
    private String xmlRosterRecordType;
    private String scrsRecordType;
    private String xmlUnEnrollmentRecordType;
    private String xmlLeaRecordType;
    private String xmlSchoolRecordType;
    private String xmlDeleteLeaRecordType;
    private String xmlDeleteSchoolRecordType;

    /**
     * record type kid.
     */
    private String kidRecordType;
    private String courseEnrollmentStatusCode;
    private Map<String, String> testColumnAttributeMap = new HashMap<String, String>();
    private String testRecordType;
    private String kansasAssessmentTags;
    private String kansasWebServiceConfigTypeCode;
    private String orgRecordType;
    
    /**
     * Roster record type.
     */
    @Value("${rosterRecordType}")
	private String rosterRecordType;
    /**
     * personal needs profile record type.
     */
    @Value("${personalNeedsProfileRecordType}")
	private String personalNeedsProfileRecordType;

    @Value("${firstContactRecordType}")
    private String firstContactRecordType;
    private Map<String, String>
    personalNeedsProfileColumnAttributeMap = new HashMap<String, String>();
    /**
     * firstContactColumnAttributeMap retains specified order.
     */
    private Map<String, String>
    firstContactColumnAttributeMap = new LinkedHashMap<String, String>();
	
	/**
	 * ON/OFF switch for webservice.
	 */
	@Value("${kansas.scheduled.webservice.switch}")
	private String kansasScheduledWebServiceSwitch;
	
	
    private Map<String, String> testCutScoresColumnAttributeMap = new HashMap<String, String>();
    
    @Value("${testCutScoresRecordType}")
    private String testCutScoresRecordType;
    
    private Map<String, String> rawToScaleScoresColumnAttributeMap = new HashMap<String, String>();

    @Value("${rawToScaleScoresRecordType}")
    private String rawToScaleScoresRecordType;
    
    /**
     * header column to attribute type mapping for test, exit and clear (tec) upload.
     */
    private Map<String, String> tecColumnAttributeMap = new HashMap<String, String>();
    
    @Value("${tecRecordType}")
    private String tecRecordType;
    
    @Value("${tecXMLRecordType}")
    private String tecXMLRecordType;
    
    
    
    public String getTecXMLRecordType() {
		return tecXMLRecordType;
	}

	public void setTecXMLRecordType(String tecXMLRecordType) {
		this.tecXMLRecordType = tecXMLRecordType;
	}

	private Map<String, String> includeExcludeStudentColumnAttributeMap = new HashMap<String, String>();
    
    /**
     * @return the enrollmentColumnAttributeMap
     */
    public Map<String,String> getEnrollmentColumnAttributeMap() {
        return enrollmentColumnAttributeMap;
    }

    /**
     * @param enrollmentColumnAttributeMap the enrollmentColumnAttributeMap to set
     */
    public void setEnrollmentColumnAttributeMap(Map<String,String> columnAttributeMap) {
        this.enrollmentColumnAttributeMap = columnAttributeMap;
    }

    /**
     * @return the columnAttributeMapStr
     */
    public String getEnrollmentColumnAttributeMapStr() {
        return (enrollmentColumnAttributeMap != null && enrollmentColumnAttributeMap.size() > 0
                ? enrollmentColumnAttributeMap.toString() : null);
    }

    /**
     * @param columnAttributeMapStr the columnAttributeMapStr to set
     */
    public void setEnrollmentColumnAttributeMapStr(String columnAttributeMapStr) {
        if (!StringUtils.hasText(columnAttributeMapStr)) {
            return;
        }
        String[] attributeMapStrings = columnAttributeMapStr.split(OUTER_DELIM);
        for (String attributeMapString : attributeMapStrings) {
            if (StringUtils.hasText(attributeMapString) && attributeMapString.contains(INNER_DELIM)) {
                String[] keyAndValue = attributeMapString.split(INNER_DELIM);
                enrollmentColumnAttributeMap.put(keyAndValue[0], keyAndValue[1]);
            }
        }
    }

    /**
     * Returns the column mapping for a user upload.
     * @return Map<String, String>
     */
    public final Map<String, String> getUserColumnMap() {
        return userColumnMap;
    }

    /**
     * @param columnAttributeMap the userColumnMap to set
     */
    public final void setUserColumnMap(final Map<String, String> columnAttributeMap) {
        this.userColumnMap = columnAttributeMap;
    }

    /**
     * @return the columnAttributeMapStr
     */
    public final String getUserColumnMapStr() {
        return (userColumnMap != null && userColumnMap.size() > 0 ? userColumnMap.toString() : null);
    }

    /**
     * @param userColumnMapStr {@link String}
     */
    public final void setUserColumnMapStr(String userColumnMapStr) {
        if (!StringUtils.hasText(userColumnMapStr)) {
            return;
        }
        String[] attributeMapStrings = userColumnMapStr.split(OUTER_DELIM);
        for (String attributeMapString : attributeMapStrings) {
            if (StringUtils.hasText(attributeMapString) && attributeMapString.contains(INNER_DELIM)) {
                String[] keyAndValue = attributeMapString.split(INNER_DELIM);
                userColumnMap.put(keyAndValue[0], keyAndValue[1]);
            }
        }
    }

    /**
     * @return the scrsColumnAttributeMap
     */
    public Map<String,String> getScrsColumnAttributeMap() {
        return scrsColumnAttributeMap;
    }

    /**
     * @param scrsColumnAttributeMap the scrsColumnAttributeMap to set
     */
    public void setScrsColumnAttributeMap(Map<String,String> columnAttributeMap) {
        this.scrsColumnAttributeMap = columnAttributeMap;
    }

    /**
     * @return the columnAttributeMapStr
     */
    public String getScrsColumnAttributeMapStr() {
        return (scrsColumnAttributeMap != null && scrsColumnAttributeMap.size() > 0
                ? scrsColumnAttributeMap.toString() : null);
    }
    /**
     * @param columnAttributeMapStr the columnAttributeMapStr to set
     */
    public void setScrsColumnAttributeMapStr(String columnAttributeMapStr) {
        if (!StringUtils.hasText(columnAttributeMapStr)) {
            return;
        }
        String[] attributeMapStrings = columnAttributeMapStr.split(OUTER_DELIM);
        for (String attributeMapString : attributeMapStrings) {
            if (StringUtils.hasText(attributeMapString) && attributeMapString.contains(INNER_DELIM)) {
                String[] keyAndValue = attributeMapString.split(INNER_DELIM);
                scrsColumnAttributeMap.put(keyAndValue[0], keyAndValue[1]);
            }
        }
    }
    /**
     * @return the scrsColumnAttributeMap
     */
    public Map<String,String> getPersonalNeedsProfileColumnAttributeMap() {
        return personalNeedsProfileColumnAttributeMap;
    }

    /**
     * @param scrsColumnAttributeMap the scrsColumnAttributeMap to set
     */
    public void setPersonalNeedsProfileColumnAttributeMap(Map<String,String> columnAttributeMap) {
        this.personalNeedsProfileColumnAttributeMap = columnAttributeMap;
    }

    /**
	 * @return the firstContactColumnAttributeMap
	 */
	public Map<String, String> getFirstContactColumnAttributeMap() {
		return firstContactColumnAttributeMap;
	}

	/**
	 * @param firstContactColumnAttributeMap the firstContactColumnAttributeMap to set
	 */
	public void setFirstContactColumnAttributeMap(
			Map<String, String> firstContactColumnAttributeMap) {
		this.firstContactColumnAttributeMap = firstContactColumnAttributeMap;
	}

	/**
     * @return the columnAttributeMapStr
     */
    public String getPersonalNeedsProfileColumnAttributeMapStr() {
        return (personalNeedsProfileColumnAttributeMap != null && personalNeedsProfileColumnAttributeMap.size() > 0
                ? personalNeedsProfileColumnAttributeMap.toString() : null);
    }
    /**
     * @param columnAttributeMapStr the columnAttributeMapStr to set
     */
    public void setPersonalNeedsProfileColumnAttributeMapStr(String columnAttributeMapStr) {
        if (!StringUtils.hasText(columnAttributeMapStr)) {
            return;
        }
        String[] attributeMapStrings = columnAttributeMapStr.split(OUTER_DELIM);
        for (String attributeMapString : attributeMapStrings) {
            if (StringUtils.hasText(attributeMapString) && attributeMapString.contains(INNER_DELIM)) {
                String[] keyAndValue = attributeMapString.split(INNER_DELIM);
                personalNeedsProfileColumnAttributeMap.put(keyAndValue[0], keyAndValue[1]);
            }
        }
    }
    

	/**
     * @return the columnAttributeMapStr
     */
    public String getFirstContactColumnAttributeMapStr() {
        return (firstContactColumnAttributeMap != null && firstContactColumnAttributeMap.size() > 0
                ? firstContactColumnAttributeMap.toString() : null);
    }
    /**
     * @param columnAttributeMapStr the columnAttributeMapStr to set
     */
    public void setFirstContactColumnAttributeMapStr(String columnAttributeMapStr) {
        if (!StringUtils.hasText(columnAttributeMapStr)) {
            return;
        }
        String[] attributeMapStrings = columnAttributeMapStr.split(OUTER_DELIM);
        for (String attributeMapString : attributeMapStrings) {
            if (StringUtils.hasText(attributeMapString) && attributeMapString.contains(INNER_DELIM)) {
                String[] keyAndValue = attributeMapString.split(INNER_DELIM);
                firstContactColumnAttributeMap.put(keyAndValue[0], keyAndValue[1]);
            }
        }
    }    
    /**
     * @return the testColumnAttributeMap
     */
    public Map<String,String> getTestColumnAttributeMap() {
        return testColumnAttributeMap;
    }

    /**
     * @param testColumnAttributeMap the testColumnAttributeMap to set
     */
    public void setTestColumnAttributeMap(Map<String,String> columnAttributeMap) {
        this.testColumnAttributeMap = columnAttributeMap;
    }

    /**
     * @return the columnAttributeMapStr
     */
    public String getTestColumnAttributeMapStr() {
        return (testColumnAttributeMap != null && testColumnAttributeMap.size() > 0
                ? testColumnAttributeMap.toString() : null);
    }

    /**
     * @param columnAttributeMapStr the columnAttributeMapStr to set
     */
    public void setTestColumnAttributeMapStr(String columnAttributeMapStr) {
        if (!StringUtils.hasText(columnAttributeMapStr)) {
            return;
        }
        String[] attributeMapStrings = columnAttributeMapStr.split(OUTER_DELIM);
        for (String attributeMapString : attributeMapStrings) {
            if (StringUtils.hasText(attributeMapString) && attributeMapString.contains(INNER_DELIM)) {
                String[] keyAndValue = attributeMapString.split(INNER_DELIM);
                testColumnAttributeMap.put(keyAndValue[0], keyAndValue[1]);
            }
        }
    }

    /**
     * @return the csvRecordTypeCode
     */
    public final String getCsvRecordTypeCode() {
        return csvRecordTypeCode;
    }

    /**
     * @param csvRecordTypCode {@link String} the csvRecordTypeCode to set
     */
    public final void setCsvRecordTypeCode(String csvRecordTypCode) {
        this.csvRecordTypeCode = csvRecordTypCode;
    }

    /**
     * @return the webServiceRecordTypeCode
     */
    public final String getWebServiceRecordTypeCode() {
        return webServiceRecordTypeCode;
    }

    /**
     * @param webServiceRecordTypCode the webServiceRecordTypeCode to set
     */
    public final void setWebServiceRecordTypeCode(String webServiceRecordTypCode) {
        this.webServiceRecordTypeCode = webServiceRecordTypCode;
    }

    /**
     * @return the enrollmentRecordType
     */
    public final String getEnrollmentRecordType() {
        return enrollmentRecordType;
    }

    /**
     * @param enrollmentRecType the enrollmentRecordType to set
     */
    public final void setEnrollmentRecordType(String enrollmentRecType) {
        this.enrollmentRecordType = enrollmentRecType;
    }

    /**
     * @return the scrsRecordType
     */
    public String getScrsRecordType() {
        return scrsRecordType;
    }

    public String getXmlEnrollmentRecordType() {
		return xmlEnrollmentRecordType;
	}

	public void setXmlEnrollmentRecordType(String xmlEnrollmentRecordType) {
		this.xmlEnrollmentRecordType = xmlEnrollmentRecordType;
	}

	public String getXmlRosterRecordType() {
		return xmlRosterRecordType;
	}

	public void setXmlRosterRecordType(String xmlRosterRecordType) {
		this.xmlRosterRecordType = xmlRosterRecordType;
	}

	/**
     * @param scrsRecordType the scrsRecordType to set
     */
    public void setScrsRecordType(String scrsRecordType) {
        this.scrsRecordType = scrsRecordType;
    }
    /**
     * @return the kidRecordType
     */
    public String getKidRecordType() {
        return kidRecordType;
    }

    /**
     * @param kidRecordType the kidRecordType to set
     */
    public void setKidRecordType(String kidRecordType) {
        this.kidRecordType = kidRecordType;
    }

    /**
     * @return the userRecordType
     */
    public String getUserRecordType() {
        return userRecordType;
    }

    /**
     * @param userRecordType the userRecordType to set
     */
    public final void setUserRecordType(final String userRecordType) {
        this.userRecordType = userRecordType;
    }
    
    /**
     * @return the pdTrainingUploadType
     */
    public String getPdTrainingUploadType() {
        return pdTrainingUploadType;
    }

    /**
     * @param pdTrainingUploadType the pdTrainingUploadType to set
     */
    public final void setPdTrainingUploadType(final String pdTrainingUploadType) {
        this.pdTrainingUploadType = pdTrainingUploadType;
    }

    public String getIncludeExcludeRecordType() {
		return includeExcludeRecordType;
	}

	public void setIncludeExcludeRecordType(String includeExcludeRecordType) {
		this.includeExcludeRecordType = includeExcludeRecordType;
	}

    /**
     * @return the testRecordType
     */
    public String getTestRecordType() {
        return testRecordType;
    }

    /**
     * @param testRecordType the testRecordType to set
     */
    public void setTestRecordType(String testRecordType) {
        this.testRecordType = testRecordType;
    }
    /**
     * @return the courseEnrollmentStatusCode
     */
    public String getCourseEnrollmentStatusCode() {
        return courseEnrollmentStatusCode;
    }

    /**
     * @param courseEnrollmentStatusCode the courseEnrollmentStatusCode to set
     */
    public void setCourseEnrollmentStatusCode(String courseEnrollmentStatusCode) {
        this.courseEnrollmentStatusCode = courseEnrollmentStatusCode;
    }
    /**
     * @return {@link String}
     */
    public final String getKansasAssessmentTags() {
        return this.kansasAssessmentTags;
    }

    /**
     * @param kanAssessmentTags {@link String}
     */
    public final void setKansasAssessmentTags(String kanAssessmentTags) {
        this.kansasAssessmentTags = kanAssessmentTags;
    }

    /**
     * @return {@link String}
     */
    public final String getKansasWebServiceConfigTypeCode() {
        return kansasWebServiceConfigTypeCode;
    }

    /**
     * @param kansasWebServiceConfigTypCode {@link String}
     */
    public final void setKansasWebServiceConfigTypeCode(String kansasWebServiceConfigTypCode) {
        this.kansasWebServiceConfigTypeCode = kansasWebServiceConfigTypCode;
    }

    /**
     * @return the kansasWebServiceUrlCode
     */
    public final String getKansasWebServiceUrlCode() {
        return kansasWebServiceUrlCode;
    }

    /**
     * @param kansasWebServUrlCode the kansasWebServiceUrlCode to set
     */
    public final void setKansasWebServiceUrlCode(String kansasWebServUrlCode) {
        this.kansasWebServiceUrlCode = kansasWebServUrlCode;
    }

    /**
     * @return the kansasWebServiceScheduleFrequencyCode
     */
    public String getKansasWebServiceScheduleFrequencyCode() {
        return kansasWebServiceScheduleFrequencyCode;
    }
    
    /**
     * @return the kansasWebServiceScheduleFrequencyDelta
     */
    public String getKansasWebServiceScheduleFrequencyDelta() {
        return kansasWebServiceScheduleFrequencyDelta;
    }
    
    /**
     * @return the tomcatStartTimeCode
     */
    public String getTomcatStartTimeCode() {
        return tomcatStartTimeCode;
    }

    /**
     * @param tomcatStartTimeCode the tomcatStartTimeCode to set
     */
    public void setTomcatStartTimeCode(String tomcatStartTimeCode) {
        this.tomcatStartTimeCode = tomcatStartTimeCode;
    }

    /**
     * @return the retryTimeCode
     */
    public String getRetryTimeCode() {
        return retryTimeCode;
    }

    /**
     * @param retryTimeCode the retryTimeCode to set
     */
    public void setRetryTimeCode(String retryTimeCode) {
        this.retryTimeCode = retryTimeCode;
    }

    /**
     * @param kansasWebServiceScheduleFrequencyCode the kansasWebServiceScheduleFrequencyCode to set
     */
    public void setKansasWebServiceScheduleFrequencyCode(
            String kansasWebServiceScheduleFrequencyCode) {
        this.kansasWebServiceScheduleFrequencyCode = kansasWebServiceScheduleFrequencyCode;
    }
    
    /**
     * @param kansasWebServiceScheduleFrequencyDelta the kansasWebServiceScheduleFrequencyDelta to set
     */
    public void setKansasWebServiceScheduleFrequencyDelta(
            String kansasWebServiceScheduleFrequencyDelta) {
        this.kansasWebServiceScheduleFrequencyDelta = kansasWebServiceScheduleFrequencyDelta;
    }
    
    /**
     * @return the orgRecordType
     */
    public final String getOrgRecordType() {
        return orgRecordType;
    }

    /**
     * @param orgRecordType the orgRecordType to set
     */
    public final void setOrgRecordType(String orgRecordType) {
        this.orgRecordType = orgRecordType;
    }
    /**
     * @return the errorMessageFileHeaders
     */
    public final String getErrorMessageFileHeaderStr() {
        return errorMessageFileHeaders + ParsingConstants.BLANK;
    }
    /**
     * @return the errorMessageFileHeaders
     */
    public final String[] getErrorMessageFileHeaders() {
        return errorMessageFileHeaders;
    }
    /**
     * @param errorMessgFileHeaders the errorMessageFileHeaders to set
     */
    public final void setErrorMessageFileHeaderStr(String errorMessgFileHeaders) {
        if (!StringUtils.hasText(errorMessgFileHeaders)) {
            return;
        }
        errorMessageFileHeaders = errorMessgFileHeaders.split(OUTER_DELIM);
    }

    /**
     * @return the path
     */
    public final String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public final void setPath(final String path) {
        this.path = path;
    }

	public String getRosterRecordType() {
		return rosterRecordType;
	}

    public void setRosterRecordType(String rosterRecordType) {
		this.rosterRecordType = rosterRecordType;
	}

	public String getPersonalNeedsProfileRecordType() {
		return this.personalNeedsProfileRecordType;
	}

	/**
	 * @param personalNeedsProfileRecordType the personalNeedsProfileRecordType to set
	 */
	public void setPersonalNeedsProfileRecordType(
			String personalNeedsProfileRecordType) {
		this.personalNeedsProfileRecordType = personalNeedsProfileRecordType;
	}
	
	public String getFirstContactRecordType() {
		return this.firstContactRecordType;
	}
	/**
	 * @param firstContactRecordType the firstContactRecordType to set
	 */
	public void setFirstContactRecordType(
			String firstContactRecordType) {
		this.firstContactRecordType = firstContactRecordType;
	}
	
	/**
	 * @return kidsScheduledWebServiceSwitch
	 */
	public String  getKansasScheduledWebServiceSwitch() {
		return kansasScheduledWebServiceSwitch;
	}

    /**
     * @param kidsScheduledWebServiceSwitch
     */
    public void setKansasScheduledWebServiceSwitch(String kansasScheduledWebServiceSwitch) {
		this.kansasScheduledWebServiceSwitch = kansasScheduledWebServiceSwitch;
	}

	public Map<String, String> getTestCutScoresColumnAttributeMap() {
		return testCutScoresColumnAttributeMap;
	}

	public Map<String, String> getRawToScaleScoresColumnAttributeMap() {
		return rawToScaleScoresColumnAttributeMap;
	}
	/**
	 * @return the columnAttributeMapStr
	 */
	public String getTestCutScoresColumnAttributeMapStr() {
        return (testCutScoresColumnAttributeMap != null && testCutScoresColumnAttributeMap.size() > 0
                ? testCutScoresColumnAttributeMap.toString() : null);
    }

    /**
     * @param columnAttributeMapStr the columnAttributeMapStr to set
     */
    public void setTestCutScoresColumnAttributeMapStr(String columnAttributeMapStr) {
        if (!StringUtils.hasText(columnAttributeMapStr)) {
            return;
        }
        String[] attributeMapStrings = columnAttributeMapStr.split(OUTER_DELIM);
        for (String attributeMapString : attributeMapStrings) {
            if (StringUtils.hasText(attributeMapString) && attributeMapString.contains(INNER_DELIM)) {
                String[] keyAndValue = attributeMapString.split(INNER_DELIM);
                testCutScoresColumnAttributeMap.put(keyAndValue[0], keyAndValue[1]);
            }
        }
    }
    
	public void setTestCutScoresColumnAttributeMap(
			Map<String, String> testCutScoresColumnAttributeMap) {
		this.testCutScoresColumnAttributeMap = testCutScoresColumnAttributeMap;
	}

	public String getTestCutScoresRecordType() {
		return testCutScoresRecordType;
	}

	public void setTestCutScoresRecordType(String testCutScoresRecordType) {
		this.testCutScoresRecordType = testCutScoresRecordType;
	}
	
	/**
	 * @return the columnAttributeMapStr
	 */
	public String getRawToScaleScoresColumnAttributeMapStr() {
        return (rawToScaleScoresColumnAttributeMap != null && rawToScaleScoresColumnAttributeMap.size() > 0
                ? rawToScaleScoresColumnAttributeMap.toString() : null);
    }

    /**
     * @param columnAttributeMapStr the columnAttributeMapStr to set
     */
    public void setRawToScaleScoresColumnAttributeMapStr(String columnAttributeMapStr) {
        if (!StringUtils.hasText(columnAttributeMapStr)) {
            return;
        }
        String[] attributeMapStrings = columnAttributeMapStr.split(OUTER_DELIM);
        for (String attributeMapString : attributeMapStrings) {
            if (StringUtils.hasText(attributeMapString) && attributeMapString.contains(INNER_DELIM)) {
                String[] keyAndValue = attributeMapString.split(INNER_DELIM);
                rawToScaleScoresColumnAttributeMap.put(keyAndValue[0], keyAndValue[1]);
            }
        }
    }
    
	public void setRawToScaleScoresColumnAttributeMap(
			Map<String, String> rawToScaleScoresColumnAttributeMap) {
		this.rawToScaleScoresColumnAttributeMap = rawToScaleScoresColumnAttributeMap;
	}
	public String getRawToScaleScoresRecordType() {
		return rawToScaleScoresRecordType;
	}

	public void setRawToScaleScoresRecordType(String rawToScaleScoresRecordType) {
		this.rawToScaleScoresRecordType = rawToScaleScoresRecordType;
	}

	public String getTecRecordType() {
		return tecRecordType;
	}

	public void setTecRecordType(String tecRecordType) {
		this.tecRecordType = tecRecordType;
	}

	public Map<String, String> getTecColumnAttributeMap() {
		return tecColumnAttributeMap;
	}

	public void setTecColumnAttributeMap(Map<String, String> tecColumnAttributeMap) {
		this.tecColumnAttributeMap = tecColumnAttributeMap;
	}

    public String getTecColumnAttributeMapStr() {
        return (tecColumnAttributeMap != null && tecColumnAttributeMap.size() > 0
                ? tecColumnAttributeMap.toString() : null);
    }

    public void setTecColumnAttributeMapStr(String columnAttributeMapStr) {
        if (!StringUtils.hasText(columnAttributeMapStr)) {
            return;
        }
        String[] attributeMapStrings = columnAttributeMapStr.split(OUTER_DELIM);
        for (String attributeMapString : attributeMapStrings) {
            if (StringUtils.hasText(attributeMapString) && attributeMapString.contains(INNER_DELIM)) {
                String[] keyAndValue = attributeMapString.split(INNER_DELIM);
                tecColumnAttributeMap.put(keyAndValue[0], keyAndValue[1]);
            }
        }
    }    
    
	/*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

	/**
	 * @return the pdTrainingResultsMap
	 */
	public Map<String, String> getPdTrainingResultsMap() {
		return pdTrainingResultsMap;
	}

	/**
	 * @param pdTrainingResultsMap the pdTrainingResultsMap to set
	 */
	public void setPdTrainingResultsMap(Map<String, String> pdTrainingResultsMap) {
		this.pdTrainingResultsMap = pdTrainingResultsMap;
	}
	
	/**
     * @return the columnAttributeMapStr
     */
    public final String getPDTrainingResultsMapStr() {
        return (pdTrainingResultsMap != null && pdTrainingResultsMap.size() > 0 ? pdTrainingResultsMap.toString() : null);
    }

    /**
     * @param userColumnMapStr {@link String}
     */
    public final void setPDTrainingResultsMapStr(String pDTrainingResultsMapStr) {
        if (!StringUtils.hasText(pDTrainingResultsMapStr)) {
            return;
        }
        String[] attributeMapStrings = pDTrainingResultsMapStr.split(OUTER_DELIM);
        for (String attributeMapString : attributeMapStrings) {
            if (StringUtils.hasText(attributeMapString) && attributeMapString.contains(INNER_DELIM)) {
                String[] keyAndValue = attributeMapString.split(INNER_DELIM);
                pdTrainingResultsMap.put(keyAndValue[0], keyAndValue[1]);
            }
        }
    }

	public Map<String, String> getIncludeExcludeStudentColumnAttributeMap() {
		return includeExcludeStudentColumnAttributeMap;
	}

	public void setIncludeExcludeStudentColumnAttributeMap(Map<String, String> includeExcludeStudentColumnAttributeMap) {
		this.includeExcludeStudentColumnAttributeMap = includeExcludeStudentColumnAttributeMap;
	}

	public final String getIncludeExcludeRecordMapStr() {
        return (includeExcludeStudentColumnAttributeMap != null && includeExcludeStudentColumnAttributeMap.size() > 0 ? includeExcludeStudentColumnAttributeMap.toString() : null);
    }

    /**
     * @param userColumnMapStr {@link String}
     */
    public final void setIncludeExcludeRecordMapStr(String includeExcludeRecordMapStr) {
        if (!StringUtils.hasText(includeExcludeRecordMapStr)) {
            return;
        }
        String[] attributeMapStrings = includeExcludeRecordMapStr.split(OUTER_DELIM);
        for (String attributeMapString : attributeMapStrings) {
            if (StringUtils.hasText(attributeMapString) && attributeMapString.contains(INNER_DELIM)) {
                String[] keyAndValue = attributeMapString.split(INNER_DELIM);
                includeExcludeStudentColumnAttributeMap.put(keyAndValue[0], keyAndValue[1]);
            }
        }
    }
    
    public String getXmlUnEnrollmentRecordType() {
		return xmlUnEnrollmentRecordType;
	}

	public void setXmlUnEnrollmentRecordType(String xmlUnEnrollmentRecordType) {
		this.xmlUnEnrollmentRecordType = xmlUnEnrollmentRecordType;
	}

	public String getXmlLeaRecordType() {
		return xmlLeaRecordType;
	}

	public void setXmlLeaRecordType(String xmlLeaRecordType) {
		this.xmlLeaRecordType = xmlLeaRecordType;
	}

	public String getXmlSchoolRecordType() {
		return xmlSchoolRecordType;
	}

	public void setXmlSchoolRecordType(String xmlSchoolRecordType) {
		this.xmlSchoolRecordType = xmlSchoolRecordType;
	}

	public String getXmlDeleteLeaRecordType() {
		return xmlDeleteLeaRecordType;
	}

	public void setXmlDeleteLeaRecordType(String xmlDeleteLeaRecordType) {
		this.xmlDeleteLeaRecordType = xmlDeleteLeaRecordType;
	}

	public String getXmlDeleteSchoolRecordType() {
		return xmlDeleteSchoolRecordType;
	}

	public void setXmlDeleteSchoolRecordType(String xmlDeleteSchoolRecordType) {
		this.xmlDeleteSchoolRecordType = xmlDeleteSchoolRecordType;
	}

	
}
