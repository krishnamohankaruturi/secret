package edu.ku.cete.util;

public enum EventTypeEnum {
	INSERT("INSERT", "INSERT","Insert from add screen."),
	UPDATE("UPDATE", "UPDATE", "Updates from updated screen."),
	MOVE("MOVE", "MOVE", "When user move from one organization to another."),
	MERGE("MERGE", "MERGE", "When user merge with anothe user from merge screen."),
	MERGE_USER_PURGED("MERGE_USER_PURGED", "MERGE_USER_PURGED", "When user is deleted in merge operation."),
	ACTIVATED_DEACTIVATED("ACTIVATED/DE-ACTIVATED", "ACTIVATED_DE-ACTIVATED", "When User activated and de-activated."),
	REACTIVATED("RE-ACTIVATED", "RE-ACTIVATED", "When an inactive user gets re-activated."),
	PASSWORD_CHANGE("PASSWORD CHANGE", "PASSWORD_CHANGE", "USer changed the password."),
	SECURITY_AGREEMENT("SECURITY AGREEMENT","SECURITY_AGREEMENT","When User change the security agreement."),
	DISPLAY_NAME_CHANGED("DISPLAY NAME CHANGED","DISPLAY_NAME_CHANGED","When user change the Display name."),
	DEFAULT_ROLE_CHANGED("DEFAULT ROLE CHANGED","DEFAULT_ROLE_CHANGED","When user change the default organization and role."),
	EXIT("EXIT", "EXIT","When a student got Exited."),
	TRANSFER_STUDENT("TRANSFER STUDENT", "TRANSFER STUDENT","When we transfer the student."),
	FCS_INSERTED("FCS_INSERTED", "FCS_INSERTED", "When new FCS is inserted for student"),
	FCS_SECTION_EDITED("FCS_SECTION_EDITED", "FCS_SECTION_EDITED", "When student survey page is edited"),	
	FCS_SUBMMITTED("FCS_SUBMMITTED", "FCS_SUBMMITTED", "When student survey is submitted"),
	CLAIMUSER("CLAIMUSER", "CLAIMUSER","User Claim Event."),
	FCS_COMP_BANDS("FCS_COMP_BANDS", "FCS_COMP_BANDS", "When student survey is submitted");

	private String name;
	private String description;
	private String code;

	EventTypeEnum(String code, String name, String description) {
		this.code = code;
		this.name = name;
		this.description = description;
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

	public static EventTypeEnum getByCode(String code) {
		if (code != null) {
			for (EventTypeEnum rule : EventTypeEnum.values()) {
				if (rule.getCode().equals(code)) {
					return rule;
				}
			}
		}
		return null;
	}
}
