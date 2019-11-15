// global configuration variable that can be used by the rest of the UI
var reportsConfig = new ReportsConfig();

function ReportsConfig(){
	this.reportYearFilterTypes = {
		'general_student' : true, // student(individual)
		'general_student_all' : true, // student(bundled)
		'general_school_summary' : true, // school summary
		'general_district_summary' : true, // district summary
		'alternate_yearend_state_summary' : true, // state aggregate
		'alternate_yearend_district_summary' : true, // district aggregate
		'alternate_school_summary' : true, // school aggregate
		'alternate_classroom' : true, // class aggregate ??classroom
		'alternate_student_individual' : true, // student(individual)
		'alternate_student_all' : true, // students(bundled)
		'kelpa_student_all' : true, // Kelpa Student(bundled)
		'alternate_student_summary_all' : true, // student summmary(bundled)
		'general_school_detail' : true, // school detail
		'alternate_student_dcps' : true,
		'alternate_student_summary' : true,
		'alternate_school_summary_all' : true,
		'kelpa_student_individual' : true,
		'alternate_student_individual_teacher' : true
	};

	this.summaryLevelFilterTypes = {
		'alternate_monitoring_summary' : true
	};

	this.stateFilterTypes = {
		'alternate_yearend_state_summary' : true
	};

	this.districtFilterTypes = {
		'general_student' : true,
		/* 'general_roster': true, */
		'general_student_all' : true,
		'general_school_detail' : true,
		'general_school_summary' : true,
		'general_district_detail' : true,
		'general_district_summary' : true,
		'general_student_writing' : true,
		'alternate_student' : true,
		'alternate_roster' : true,
		'alternate_student_individual' : true,
		'alternate_student_all' : true,
		'kelpa_student_all' : true,
		'alternate_monitoring_summary' : true,
		'kelpa_elp_student_score' : true,
		'alternate_blueprint_coverage' : true,
		'alternate_yearend_district_summary' : true,
		'alternate_student_summary_all' : true,
		'alternate_school_summary_all' : true,
		'alternate_student_summary' : true,
		'alternate_school_summary' : true,
		'alternate_classroom' : true,
		'alternate_student_dcps' : true,
		'kelpa_student_individual' : true
	};

	this.schoolFilterTypes = {
		'general_student' : true,
		/* 'general_roster': true, */
		'general_student_all' : true,
		'general_school_detail' : true,
		'general_school_summary' : true,
		'general_student_writing' : true,
		'alternate_student' : true,
		'alternate_roster' : true,
		'alternate_student_individual' : true,
		'alternate_student_all' : true,
		'kelpa_student_all' : true,
		'alternate_monitoring_summary' : true,
		'alternate_blueprint_coverage' : true,
		'alternate_student_summary_all' : true,
		'alternate_student_summary' : true,
		'alternate_school_summary' : true,
		'alternate_classroom' : true,
		'alternate_student_dcps' : true,
		'kelpa_student_individual' : true
	};

	this.subjectFilterTypes = {
		'general_student' : true,
		/* 'general_roster': true, */
		/* 'general_school_detail': true, */
		/* 'general_district_detail': true, */
		'alternate_student' : true,
		'alternate_roster' : true,
		'alternate_student_individual' : true,
		'alternate_blueprint_coverage' : true
	};

	this.gradeFilterTypes = {
		'general_student' : true,
		'general_student_writing' : true,
		'alternate_student_individual' : true,
		'alternate_blueprint_coverage' : true,
		'alternate_student_summary' : true,
		'alternate_student_dcps' : true,
		'kelpa_student_individual' : true
	/* ,'general_roster': true, */
	/* 'general_school_detail': true, */
	/* 'general_district_detail': true, */
	};

	this.rosterFilterTypes = {
		/* 'general_roster': true, */
		'alternate_roster' : true
	};

	this.teacherRosterFilterTypes = {
		'alternate_student_individual_teacher' : true,
		'alternate_student_summary_teacher' : true
	};

	this.teacherFilterTypes = {
		'alternate_classroom' : true
	};
	
	this.windowCycleFilterTypes = {
			'alternate_student' : true,
			'alternate_blueprint_coverage' : true
		};
	
	this.countURLs = {
		'general_student': 'countStudentReports.htm',
		'alternate_student_individual': 'countExternalStudentReports.htm',
		'kelpa_student_individual': 'countStudentReports.htm',
		'alternate_student_individual_teacher': 'countExternalStudentReportsForTeacherRoster.htm',
		'general_school_detail': 'countSchoolDetailReports.htm',
		'general_district_detail': 'countDistrictDetailReports.htm',
		'alternate_student_summary': 'countExternalStudentSummaryReports.htm',
		'alternate_student_dcps': 'countExternalStudentSummaryReports.htm',
		'alternate_student_summary_teacher': 'countOfStudentSummaryReportsForTeacherRoster.htm'
	};
	
	this.pageURLs = {
		'general_student': 'getStudentReports.htm',
		'general_student_writing': 'getStudentsWithWritingResponses.htm',
		'alternate_student_individual': 'getExternalStudentReports.htm',
		'kelpa_student_individual': 'getStudentReports.htm',
		'alternate_student_individual_teacher': 'getExternalStudentReportsForTeacherRoster.htm',
		'general_student_all': 'getAllStudentsReports.htm',
		'alternate_student_all': 'getAllStudentsReports.htm',
		'kelpa_student_all': 'getAllStudentsReports.htm',
		'general_school_detail': 'getSchoolDetailReports.htm',
		'general_school_summary': 'getSchoolSummaryReports.htm',
		'general_district_detail': 'getDistrictDetailReports.htm',
		'alternate_student': 'getDLMStudentsForStudentReports.htm',
		'alternate_roster': 'getDLMStudentsForRosterReports.htm',
		'general_district_summary': 'getDistrictSummaryReports.htm',
		'alternate_monitoring_summary': 'getAlternateMonitoringSummary.htm',
		'alternate_blueprint_coverage': 'getAlternateBlueprintCoverageTeachers.htm',
		'kelpa_elp_student_score': 'getELPAStudnetsScoreFileReports.htm',
		'alternate_yearend_district_summary': 'getAlternateYearEndDistrictSummary.htm',
		'alternate_yearend_state_summary': 'getAlternateYearEndStateSummary.htm',
		'alternate_student_summary_all': 'getAllStudentSummaryBundledReports.htm',
		'alternate_school_summary_all': 'getAllSchoolSummaryBundledReports.htm',
		'alternate_student_summary': 'getExternalStudentSummaryReports.htm',
		'alternate_student_summary_teacher': 'getStudentSummaryReportsForTeacherRoster.htm',
		'alternate_school_summary': 'getAllSchoolSummaryReports.htm',
		'alternate_classroom': 'getAllClassroomReports.htm',
		'alternate_student_dcps': 'getExternalStudentSummaryReports.htm'
	};
}

ReportsConfig.prototype.getReportType = function(){
	return this.reportType;
}

ReportsConfig.prototype.setReportType = function(reportType){
	this.reportType = reportType;
}

ReportsConfig.prototype.getReportCode = function(){
	return this.reportCode;
}

ReportsConfig.prototype.setReportCode = function(reportCode){
	this.reportCode = reportCode;
}

ReportsConfig.prototype.getInactiveOrgsReq = function(){
	return this.inactiveOrgsReq;
}

ReportsConfig.prototype.setInactiveOrgsReq = function(inactiveOrgsReq){
	this.inactiveOrgsReq = inactiveOrgsReq;
}

ReportsConfig.prototype.getContentElementId = function(){
	return this.contentElementId;
}

ReportsConfig.prototype.setContentElementId = function(elementId){
	this.contentElementId = elementId;
}
ReportsConfig.prototype.getPagination = function(){
	return this.pagination;
}

ReportsConfig.prototype.setPagination = function(paginationData){
	this.pagination = paginationData;
}

ReportsConfig.prototype.getMaxPages = function(){
	return this.maxPages;
}
ReportsConfig.prototype.setMaxPages = function(maxPagesData){
	this.maxPages = maxPagesData;
}
ReportsConfig.prototype.getPaginationElement = function(){
	return this.paginationElement;
}
ReportsConfig.prototype.setPaginationElement = function(paginationElementData){
	this.paginationElement = paginationElementData;
}
ReportsConfig.prototype.getAssessmentProgramCode = function(){
	return this.assessmentProgramCode;
}

ReportsConfig.prototype.setAssessmentProgramCode = function(assessProgramCode){
	this.assessmentProgramCode = assessProgramCode;
}
ReportsConfig.prototype.getFiltersAvailable = function(){
	var thiss = this;
	return {
		reportYear : thiss.isReportYearFilterNeeded(),
		summaryLevel : thiss.isSummaryLevelFilterNeeded(),
		district : thiss.isDistrictFilterNeeded(),
		school : thiss.isSchoolFilterNeeded(),
		subject : thiss.isSubjectFilterNeeded(),
		grade : thiss.isGradeFilterNeeded(),
		roster : thiss.isRosterFilterNeeded(),
		teacherRoster : thiss.isTeacherRosterFilterNeeded(),
		state : thiss.isStateFilterNeeded(),
		teacher : thiss.isTeacherFilterNeeded(),
		windowCycle : thiss.isWindowCycleFilterNeeded()
	};
};

ReportsConfig.prototype.get = function(){
	var obj = this;
	obj.filters = this.getFiltersAvailable();
	return obj;
};


ReportsConfig.prototype.isReportYearFilterNeeded = function(){
	return this.reportYearFilterTypes[this.reportType] === true;
};

ReportsConfig.prototype.isSummaryLevelFilterNeeded = function(reportType){
	return this.summaryLevelFilterTypes[this.reportType] === true;
};

ReportsConfig.prototype.isDistrictFilterNeeded = function(){
	return this.districtFilterTypes[this.reportType] === true;
};

ReportsConfig.prototype.isStateFilterNeeded = function(){
	return this.stateFilterTypes[this.reportType] === true;
};

ReportsConfig.prototype.isSchoolFilterNeeded = function(){
	return this.schoolFilterTypes[this.reportType] === true;
};

ReportsConfig.prototype.isSubjectFilterNeeded = function(){
	return this.subjectFilterTypes[this.reportType] === true;
};

ReportsConfig.prototype.isGradeFilterNeeded = function(){
	return this.gradeFilterTypes[this.reportType] === true;
};

ReportsConfig.prototype.isRosterFilterNeeded = function(){
	return this.rosterFilterTypes[this.reportType] === true;
};

ReportsConfig.prototype.isTeacherRosterFilterNeeded = function(){
	return this.teacherRosterFilterTypes[this.reportType] === true;
};

ReportsConfig.prototype.isTeacherFilterNeeded = function(){
	return this.teacherFilterTypes[this.reportType] === true;
};

ReportsConfig.prototype.getCountURL = function(){
	return this.countURLs.hasOwnProperty(this.reportType) ? this.countURLs[this.reportType] : '';
};

ReportsConfig.prototype.getPageURL = function(){
	return this.pageURLs.hasOwnProperty(this.reportType) ? this.pageURLs[this.reportType] : '';
};
ReportsConfig.prototype.getFiltersCount= function(){
	return this.filterCount;
};

ReportsConfig.prototype.setFiltersCount = function(filtersCount){
	this.filterCount = filtersCount;
};

ReportsConfig.prototype.isWindowCycleFilterNeeded = function(){
	if($('#isIEModelStateNewReports').val()=='true'){
		return this.windowCycleFilterTypes[this.reportType] === true;
	}
	return false;
};