package edu.ku.cete.util;

public enum SourceTypeEnum {
	ITI("ITI", "ITI","Instructional Tool Interface."),
	UPLOAD("UPLOAD", "UPLOAD", "The data came from an upload file."),
	MANUAL("MANUAL", "MANUAL", "The data came from the application creation page."),
	BATCHAUTO("BATCHAUTO","BATCHAUTO","The data came from Batch Auto Registration process."),
	QUESTARPROCESS("QUESTARPROCESS","QUESTARPROCESS","The data imported from Alaska Questar Paper/Pencil process."),
	STCOSWEBSERVICE("STCO","STCO","The data came from the STCO webservice."),
	TESTWEBSERVICE("TEST", "TEST", "The data came from the KIDS webservice."),
	EXITWEBSERVICE("EXIT", "EXIT", "EXIT records data came from the KIDS webservice."),
	TRACKER("TRACKER", "TRACKER", "Student Tracker"),
	RESEARCHSURVEY("RESEARCHSURVEY", "RESEARCHSURVEY", "DLM Research survey"),
	MABATCH("MABATCH","MABATCH","Batch Auto Registration process with multi assignments."),	
	FIXBATCH("FIXBATCH", "FIXBATCH", "Batch Auto Registration process with fixed assignments."),
	MASTBATCH("MASTBATCH","MASTBATCH","Batch Auto Registration process with multi assignments student tracker field test."),
	QUESTAR("QUESTAR","QUESTAR","Questar Import process."),
	STUDENT_TRANSFER("STUDENT_TRANSFER","STUDENT_TRANSFER", "Transfer students from UI"),
	STUDENT_EXIT_MANUAL("STUDENT_EXIT_MANUAL", "STUDENT_EXIT_MANUAL", "Exit student from UI"),
	SIFINTERFACE("SIFINTERFACE", "SIFINTERFACE", "SIF interface"),
	AUTOSCORING("AUTOSCORING","AUTOSCORING","The data came from Auto Scoring Batch Process."),
	TANSFER_ROSTER("TANSFER_ROSTER","TANSFER_ROSTER","The data came due to roster change."),
	TASCWEBSERVICE("TASC","TASC","TASC records data came from the KIDS webservice."),
	INTERIMTEST("INTERIMTEST", "INTERIMTEST", "Records data came from Interim test creation."),
	API("API", "API", "The data came from an API endpoint"),
	PLTWSCORINGNIGHTLY("PLTWSCORINGNIGHTLY", "PLTWSCORINGNIGHTLY", "The data came from the nightly PLTW Score process."),
	PLTWSCORINGDAYTIME("PLTWSCORINGDAYTIME", "PLTWSCORINGDAYTIME", "The data came from the daytime PLTW Score process.");

	private String name;
	private String description;		
	private String code;
	
	SourceTypeEnum(String code, String name, String description) {

		this.name = name;
		this.description = description;
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getCode() {
		return code;
	}
	
	public static SourceTypeEnum getByCode(String code) {
		if (code != null) {
			for (SourceTypeEnum rule : SourceTypeEnum.values()) {
				if (rule.getCode().equals(code)) {
					return rule;
				}
			}
		}
		return null;
	}
}
