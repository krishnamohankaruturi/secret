package edu.ku.cete.util;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 *
 * @author neil.howerton
 * TODO why is this necessary ? Use Upload Specification.
 */
@Component
public class OrgUploadSpecification {

    private final String OUTER_DELIM = ",";
    private final String INNER_DELIM = "-";

    @Value("${uploadedFile.path}")
    private String path;

    @Value("${orgupload.columnMapStr}")
    private String columnMapStr;

    @Value("${csvRecordTypeCode}")
    private String csvRecordTypeCode;

    @Value("${orgupload.recordType}")
    private String orgRecordType;

    private Map<String, String> orgColumnMap = new HashMap<String, String>();

    /**
     * @return the path
     */
    public final String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public final void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the columnMapStr
     */
    public final String getColumnMapStr() {
        return (orgColumnMap != null && orgColumnMap.size() > 0
                ? orgColumnMap.toString() : null);
    }

    /**
     * @param columnMapStr the columnMapStr to set
     */
    public final void setColumnMapStr(String columnMapStr) {
        this.columnMapStr = columnMapStr;
    }

    /**
     * @return the csvRecordTypeCode
     */
    public final String getCsvRecordTypeCode() {
        return csvRecordTypeCode;
    }

    /**
     * @param csvRecordTypeCode the csvRecordTypeCode to set
     */
    public final void setCsvRecordTypeCode(String csvRecordTypeCode) {
        this.csvRecordTypeCode = csvRecordTypeCode;
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
     * @return the orgColumnMap
     */
    public final Map<String, String> getOrgColumnMap() {
        return orgColumnMap;
    }

    /**
     * @param orgColumnMap the orgColumnMap to set
     */
    public final void setOrgColumnMap(Map<String, String> orgColumnMap) {
        this.orgColumnMap = orgColumnMap;
    }

    /**
     * Method constructs the Column Mapping from the column mapping string.
     */
    @PostConstruct
    public final void generateColumnMappings() {
        if (!StringUtils.hasText(columnMapStr)) {
            return;
        }
        String[] attributeMapStrings = columnMapStr.split(OUTER_DELIM);
        for (String attributeMapString : attributeMapStrings) {
            if (StringUtils.hasText(attributeMapString) && attributeMapString.contains(INNER_DELIM)) {
                String[] keyAndValue = attributeMapString.split(INNER_DELIM);
                orgColumnMap.put(keyAndValue[0], keyAndValue[1]);
            }
        }
    }

}
