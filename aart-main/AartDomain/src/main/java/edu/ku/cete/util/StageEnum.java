package edu.ku.cete.util;

public enum StageEnum {
	STAGE1("Stg1", "Stage 1",1),
	STAGE2("Stg2", "Stage 2",2),
	STAGE3("Stg3", "Stage 3",3),
	STAGE4("Stg4", "Stage 4",4),
	PERFORMANCE("Prfrm", "Performance",1),
	SPEAKING("Spkng", "Speaking",1),
	LISTENING("Lstng", "Listening",1),
	WRITING("Wrtng", "Writing",1),
	READING("Rdng", "Reading",1);
	
	private String code;
	private String description;		
	private int sortOrder;
	
	StageEnum(String code, String description, int sortOrder) {

		this.code = code;
		this.description = description;
		this.sortOrder = sortOrder;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getSortOrder() {
		return sortOrder;
	}
	
	public static StageEnum getByCode(String code) {
		if (code != null) {
			for (StageEnum rule : StageEnum.values()) {
				if (rule.getCode().equals(code)) {
					return rule;
				}
			}
		}
		return null;
	}
}
