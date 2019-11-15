package edu.ku.cete.controller.sif;

public enum SifUploadType {
	ENRL_XML_RECORD_TYPE("/xStudents/xStudent"), 
	ROSTER_XML_RECORD_TYPE("/xRosters/xRoster"), 
	UNENRL_XML_RECORD_TYPE("/xStudents/xStudent"),
	LEA_XML_RECORD_TYPE("/xLeas/xLea"), 
	SCHOOL_XML_RECORD_TYPE("/xSchools/xSchool"),
	DELETE_LEA_XML_RECORD_TYPE("/xLeas/xLea"), 
	DELETE_SCHOOL_XML_RECORD_TYPE("/xSchools/xSchool"),
	TEC_XML_RECORD_TYPE("/studentProgramAssociations/studentProgramAssociation");

	SifUploadType(String xpathRoot) {
		this.xpathRoot = xpathRoot;
	}

	private final String xpathRoot;

	public String getXpathRoot() {
		return xpathRoot;
	}

}
