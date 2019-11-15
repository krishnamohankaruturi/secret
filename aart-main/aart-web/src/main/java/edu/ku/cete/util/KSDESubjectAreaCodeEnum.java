package edu.ku.cete.util;

import org.apache.commons.lang3.StringUtils;

public enum KSDESubjectAreaCodeEnum {

	ELA_M(DataReportTypeEnum.KSDE_ELA_AND_MATH_RETURN_FILE, "SELAA,D74"), 
	SOCIAL_STUDIES(DataReportTypeEnum.KSDE_SOCIAL_STUDIES_RETURN_FILE, "SHISGOVA"), 
	SCIENCE(DataReportTypeEnum.KSDE_SCIENCE_RETURN_FILE, "SSCIA"),
	ELP(DataReportTypeEnum.KSDE_KELPA_STATE_RETURN_FILE,"ELP");

	private DataReportTypeEnum dataReportType;
	private String subjectAreaCode;

	private KSDESubjectAreaCodeEnum(DataReportTypeEnum dataReportType,
			String subjectAreaCode) {
		this.dataReportType = dataReportType;
		this.subjectAreaCode = subjectAreaCode;
	}

	/**
	 * @return the dataReportType
	 */
	public DataReportTypeEnum getDataReportType() {
		return dataReportType;
	}

	/**
	 * @return the subjectAreaCode
	 */
	public String getSubjectAreaCode() {
		return subjectAreaCode;
	}

	public static KSDESubjectAreaCodeEnum getKSDESubjectAreaCodeBySubjectCode(
			String subjectCode) {
		for (KSDESubjectAreaCodeEnum ksdeSubjectAreaCode : values()) {
			if (StringUtils.contains(ksdeSubjectAreaCode.getSubjectAreaCode(),
					subjectCode)) {
				return ksdeSubjectAreaCode;
			}
		}
		return null;
	}
}
