package edu.ku.cete.util;

public enum EventNameForAudit {
	USER_PASSWORD_CHANGED("User Password Changed", "USER_PASSWORD_CHANGED","When Passworg get changed"),
	USER_ADDED("User Added", "USER_ADDED", "When user added."),
	USER_ROLE_ADDED("User Role Added", "USER_ROLE_ADDED", "When a new role added to the user."),
	USER_ROLE_REMOVED("User Role Removed", "USER_ROLE_REMOVED", "When a role removed from user."),
	USER_DEFAULT_ROLE_CHANGED("User Default Role Changed", "USER_DEFAULT_ROLE_CHANGED", "When default role changed for the user."),
	USER_ORGANIZATION_ADDED("User Organization Added", "USER_ORGANIZATION_ADDED", "When a organization is added to user."),
	USER_ORGANIZATION_REMOVED("User Organization Removed", "USER_ORGANIZATION_REMOVED", "When an organization removed from user."),
	USER_DEFAULT_ORGANIZATION_CHANGED("User Default Organization Changed","USER_DEFAULT_ORGANIZATION_CHANGED","When default organization changed for the user."),
	USER_ORGANIZATION_LEVEL_CHANGED("User Organization Level Changed","USER_ORGANIZATION_LEVEL_CHANGED","When user move from one organization to another."),
	USER_DEACTIVATED("User Deactivated","USER_DEACTIVATED","When user is de activated."),
	USER_ACTIVATED("User Activated","USER_ACTIVATED","When user is activated."),
	STUDENT_EXITED("Student Exited","STUDENT_EXITED","When student is exited from system."),
	STUDENT_ADDED("Student Added","STUDENT_ADDED","When student is added to system."),
	STUDENT_TRANSFERED("Student Transferred","STUDENT_TRANSFERED","When student is transfered from one school to another."),
	STUDENT_UPDATED("Student Changed","STUDENT_UPDATED","When student is updated in system."),
	NEW_FCS_INSERTED("NEW_FCS_INSERTED", "NEW_FCS_INSERTED", "When new FCS is inserted for student"),
	FCS_SECTION_EDITED("FCS_SECTION_EDITED", "FCS_SECTION_EDITED", "When student survey page is edited"),	
	FCS_SUBMMITTED("FCS_SUBMMITTED", "FCS_SUBMMITTED", "When student survey is submitted"),
	CLAIMUSER("CLAIMUSER", "CLAIMUSER","When User Claim event is submitted.");

	private String name;
	private String description;		
	private String code;
	
	EventNameForAudit(String code, String name, String description) {

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
