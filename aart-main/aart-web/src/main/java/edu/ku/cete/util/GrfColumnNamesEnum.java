package edu.ku.cete.util;


public enum GrfColumnNamesEnum {	

		unique_row_identifier("unique_row_identifier", "uniquerowidentifier"),
		kite_student_identifier("kite_student_identifier", "studentid"),
		state_student_identifier("state_student_identifier", "statestudentidentifier"),
		accountability_school_identifier("accountability_school_identifier", "aypschoolidentifier"),
		accountability_district_identifier("accountability_district_identifier", "accountabilitydistrictidentifier"),
		current_grade_level("current_grade_level", "currentgradelevel"),
		course("course", "course"),	
		student_legal_first_name("student_legal_first_name", "studentlegalfirstname"),
		student_legal_middle_name("student_legal_middle_name", "studentlegalmiddlename"),
		student_legal_last_name("student_legal_last_name", "studentlegallastname"),
		generation_code("generation_code", "generationcode"),
		username("username", "username"),
		first_language("first_language", "firstlanguage"),
		date_of_birth("date_of_birth", "dateofbirth"),
		stateuse("stateuse", "stateuse"),
		gender("gender", "gender"),
		comprehensive_race("comprehensive_race", "comprehensiverace"),
		hispanic_ethnicity("hispanic_ethnicity", "hispanicethnicity"),
		primary_disability_code("primary_disability_code", "primarydisabilitycode"),
		esol_participation_code("esol_participation_code", "esolparticipationcode"),	
		school_entry_date("school_entry_date", "schoolentrydate"),
		district_entry_date("district_entry_date", "districtentrydate"),
		state_entry_date("state_entry_date", "stateentrydate"),	
		attendance_school_program_identifier("attendance_school_program_identifier", "attendanceschoolprogramidentifier"),
		state("state", "state"),
		district_code("district_code", "districtcode"),
		district("district", "district"),
		school_code("school_code", "schoolcode"),
		school("school", "school"),
		educator_first_name("educator_first_name", "educatorfirstname"),
		educator_last_name("educator_last_name", "educatorlastname"),
		educator_username("educator_username", "educatorusername"),	
		educator_identifier("state_educator_identifier", "educatoridentifier"),
		kite_educator_identifier("kite_educator_identifier", "kiteeducatoridentifier"),
		exit_withdrawal_date("exit_withdrawal_date", "exitwithdrawaldate"),
		exit_withdrawal_code("exit_withdrawal_code", "exitwithdrawalcode"),	
		subject("subject", "subject"),	
		final_band("final_band", "finalband"),
		tbd_growth("tbd_growth", "sgp"),	
		performance_level("performance_level", "performancelevel"),
		invalidation_code("invalidation_code", "invalidationcode"),
		total_linkage_levels_mastered("total_linkage_levels_mastered", "totallinkagelevelsmastered"),	
		iowa_linkage_levels_mastered("iowa_linkage_levels_mastered", "iowalinkagelevelsmastered"),		
		ny_performance_level("ny_performance_level", "nyperformancelevel"),
		local_student_identifier("local_student_identifier", "localstudentidentifier"),		
		ee_1("ee_1", "ee1"),
		ee_2("ee_2", "ee2"),	
		ee_3("ee_3", "ee3"),
		ee_4("ee_4", "ee4"),
		ee_5("ee_5", "ee5"),
		ee_6("ee_6", "ee6"),
		ee_7("ee_7", "ee7"),
		ee_8("ee_8", "ee8"),
		ee_9("ee_9", "ee9"),
		ee_10("ee_10", "ee10"),
		ee_11("ee_11", "ee11"),
		ee_12("ee_12", "ee12"),
		ee_13("ee_13", "ee13"),
		ee_14("ee_14", "ee14"),
		ee_15("ee_15", "ee15"),
		ee_16("ee_16", "ee16"),
		ee_17("ee_17", "ee17"),
		ee_18("ee_18", "ee18"),
		ee_19("ee_19", "ee19"),
		ee_20("ee_20", "ee20"),
		ee_21("ee_21", "ee21"),
		ee_22("ee_22", "ee22"),
		ee_23("ee_23", "ee23"),
		ee_24("ee_24", "ee24"),
		ee_25("ee_25", "ee25"),
		ee_26("ee_26", "ee26"),
		firstname("firstname", "studentlegalfirstname"),
		middlename("middlename", "studentlegalmiddlename"),
		lastname("lastname", "studentlegallastname"),
		dob("dob", "dateofbirth");
	
	  
	   private final String key;
	   private final String value;

	   GrfColumnNamesEnum(String key, String value) {
	        this.key = key;
	        this.value = value;
	   }
	   
	public static GrfColumnNamesEnum getByName(String name) {
		if (name != null) {
			for (GrfColumnNamesEnum rule : GrfColumnNamesEnum.values()) {
				if (rule.getKey().equalsIgnoreCase(name)) {
					return rule;
				}
			}
		}
		return null;
	}
	   
	   public String getKey() {
	        return key;
	   }
	   public String getValue() {
	        return value;
	   }
}