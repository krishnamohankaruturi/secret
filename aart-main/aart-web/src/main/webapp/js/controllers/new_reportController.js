var totalSchoolLength = 0; 
var totalGradesLength = 0; 
var totalSubjectLength = 0;
var ref = null;
var checkRef = null;
var currentReportYear = null;
var DLMRosterReport = can.Model.extend({
	findOne : function(params) {
		return $.ajax({
			url: 'getDLMRosterReport.htm',
			dataType: 'json',
			data: params,
			type: "POST"
		});
	}
},{});

var DLMStudentReport = can.Model.extend({
	findOne : function(params) {
	    return $.ajax({
			url: 'getDLMStudentReport.htm',
			dataType: 'json',
			data: params,
			type: "POST"
		});
	}
},{});

var WritingReport = can.Model.extend({
	findOne: function(params) {
	    return $.ajax({
			url: 'getWritingResponsesForStudents.htm',
			dataType: 'json',
			data: params,
			type: "POST"
		});
	}
},{});

var DLMBlueprintCoverageReport = can.Model.extend({
	findOne: function(params) {
	    return $.ajax({
			url: 'getAlternateBlueprintCoverage.htm',
			dataType: 'json',
			data: params,
			type: "POST"
		});
	}
},{});

var new_reportController = can.Control({
}, {
	
	init: function(element, options){
		this.options = options;
		this.element = element;
		this._refreshFiltersNeeded();
		
		this.element.html(
			can.view('js/views/new_reportFilter.ejs', {filtersNeeded: this.filtersNeeded, userOrgLevel: $('#orgLevel').val()})
		);
		this.filterElement = $('#reportFilter', this.element);
		$('#resetButton', this.filterElement).hide();
		$('.pagination', this.element).css('display', 'none');
		
		// we have 88% width to work with, considering the main container padding and the Report Criteria text...kinda hacky, but works
		//var width = (88 / this.filterCount); // width in percent
		//$('.section', this.filterElement).css('width', width + '%');
		clearTimeout(ref);
		ref = 'refreshed';
		var me = this;
		$('.multiselect', this.filterElement).each(function(){
			var $this = $(this);
			var dataType = $this.data('data-type');
			var div = $this.parents('.section');
			$this.select2(				
					/**{
				autoOpen: false,
				position: {
					my: 'left top',
					at: 'left bottom',
					of: '#' + div.attr('id')
				},
				open: function(event, ui){
					div.css('background-color', '#FFFFFF');
					var $this = $(this);
					if ($this.multiSelect('option', 'multiple')){
						$this.multiSelect('widget').find('a.ui-multiselect-all, a.ui-multiselect-none').show();
					}
				},
				close: function(event, ui){
					div.css('background-color', '');
					var $this = $(this);
					// this is a hack to get multiple selects to only fire one request, not one for each selection made
					if ($this.multiSelect('option', 'multiple')){
						$this.change();
					}
				},
				header: true
			}).multiselectfilter({
				placeholder: '',
				label: ''
			}**/);
			//$this.multiSelect('getButton').hide();
			
			me._determineMultipleSelect($this);
		});
		
		$('.bcg_select', this.filterElement).hide(); // avoid improper display because of the "button" for the multiselect hogging display area
		this._disableFiltersPast(0);
		this._enableNextFilterPast(-1); // enable the first filter
		
		this._loadNextFilterAfter(-1); // load first filter by default
		this._setupValidator();
		this._setupPagination();
	},
	destroy: function(){ // keep duplicate events from happening
		this.element.unbind();
		this.element.off();
		this.element.undelegate();
		this.element.children().remove();
	},
	_refreshFiltersNeeded: function(){
		/////////////////
		var reportYearNeeded = this._isReportYearFilterNeeded(this.options.reportType);
		var summaryLevelNeeded = this._isSummaryLevelFilterNeeded(this.options.reportType);
		var districtNeeded = this._isDistrictFilterNeeded(this.options.reportType);
		var schoolNeeded = this._isSchoolFilterNeeded(this.options.reportType);
		var subjectNeeded = this._isSubjectFilterNeeded(this.options.reportType);
		var gradeNeeded = this._isGradeFilterNeeded(this.options.reportType);
		var rosterNeeded = this._isRosterFilterNeeded(this.options.reportType);
		var teacherRosterNeeded = this._isTeacherRosterFilterNeeded(this.options.reportType);
		var stateNeeded = this._isStateFilterNeeded(this.options.reportType);
		var teacherFilterNeeded = this._isTeacherFilterNeeded(this.options.reportType);
		var districtMultiple = false;
		var schoolMultiple = false;
		var subjectMultiple = false;
		var gradeMultiple = false;
		
		if (this.options.reportType === 'alternate_monitoring_summary'){
			if (summaryLevelNeeded){
				var summaryLevel = $('#summaryLevelSelect').val();
				if (typeof (summaryLevel) !== 'undefined' && summaryLevel != null && summaryLevel != 0){
					districtNeeded = districtNeeded && ($.inArray(summaryLevel, ['district', 'school']) > -1);
					schoolNeeded = schoolNeeded && ($.inArray(summaryLevel, ['school']) > -1);
					
					districtMultiple = districtNeeded;
					schoolMultiple = schoolNeeded;
				}
			}
		} else if (this.options.reportType === 'alternate_blueprint_coverage'){
			subjectMultiple = true;
			gradeMultiple = true;
		}
		
		this.filtersNeeded = {
				/////////////////////
			reportYear: reportYearNeeded,
			summaryLevel: summaryLevelNeeded,
			district: districtNeeded,
			districtMultiple: districtMultiple,
			school: schoolNeeded,
			schoolMultiple: schoolMultiple,
			subject: subjectNeeded,
			subjectMultiple: subjectMultiple,
			grade: gradeNeeded,
			gradeMultiple: gradeMultiple,
			roster: rosterNeeded,
			teacherRoster: teacherRosterNeeded,
			state: stateNeeded,
			teacher:teacherFilterNeeded
		};
		
		this._setFilterCount();
		
		var me = this;
		$('.multiselect', this.element).each(function(){
			me._determineMultipleSelect($(this));
		});
	},
	/////////////////////////////////////////
	_determineMultipleSelect: function($element){
		var multiple = this.filtersNeeded[$element.data('data-type') + 'Multiple'] == true;
		var defaultOptions = $('option[value=""], option[value="0"], option[value="-1"]', $element);
		if (multiple){
			defaultOptions.attr('disabled', 'disabled');
			defaultOptions.removeAttr('selected');
		} else {
			defaultOptions.removeAttr('disabled');
		}
		$element.select2({
			multiple: multiple
		}).val('').trigger('change.select2');
	},
	_setupValidator: function(){
		var me = this;
		me.form = $('#reportFilterForm', this.filterElement);
		me.form.validate({
			errorPlacement: function(error, $element){},
 			ignore: '',
 			rules: {
 				////////////////////
 				reportYearSelect: {required: me.filtersNeeded.reportYear, minlength: 1},
 				summaryLevelSelect: {required: me.filtersNeeded.summaryLevel, minlength: 2},
 				districtSelect: {required: me.filtersNeeded.district, min: 1},
 				schoolSelect: {required: me.filtersNeeded.school, min: 1},
 				subjectSelect: {required: me.filtersNeeded.subject, min: 1},
 				gradeSelect: {required: me.filtersNeeded.grade, min: 1},
 				rosterSelect: {required: me.filtersNeeded.roster, min: 1},
 				teacherRosterSelect: {required: me.filtersNeeded.teacherRoster, min: 1},
 				teacherSelect: {required: me.filtersNeeded.teacher, min: 1}
 			}
 		});
	},
	_setFilterCount: function(){
		var filterNames = Object.getOwnPropertyNames(this.filtersNeeded);
		this.filterCount = 0;
		for (var x = 0; x < filterNames.length; x++){
			if (!/Multiple$/.test(filterNames[x]) && this.filtersNeeded[filterNames[x]]){
				this.filterCount++;
			}
		}
	},
	'.criteria click': function(element, event){
		var select = $('select.multiselect', $(element));
		if (select.length > 0){
			select.on('select2-open');
		}
	},
	//////////////////////////////////
	'#reportYearSelect change': function(element, event){
		clearTimeout(ref);		
		ref = 'refreshed';
		this._selectChange('reportYear', element);
	},
	'#summaryLevelSelect change': function(element, event){
		this._refreshFiltersNeeded();
		this._setupValidator();
		this._selectChange('summaryLevel', element);
	},
	'#districtSelect change': function(element, event){
		clearTimeout(ref);
		ref = 'refreshed';
		this._selectChange('district', element);
		if(this.options.reportType=='alternate_roster')
		{
			$("#dlmRosterReport").empty();
		}
		if(this.options.reportType=='alternate_student')
		{
			$("#dlmStudentReport").empty();
		}
	},
	'#schoolSelect change': function(element, event){
		clearTimeout(ref);
		ref = 'refreshed';
		this._selectChange('school', element);
		if(this.options.reportType=='alternate_roster')
		{
			$("#dlmRosterReport").empty();
		}
		if(this.options.reportType=='alternate_student')
		{
			$("#dlmStudentReport").empty();
		}
		
	},
	'#subjectSelect change': function(element, event){
		this._selectChange('subject', element);
		if(this.options.reportType=='alternate_roster')
		{
			$("#dlmRosterReport").empty();
		}
		if(this.options.reportType=='alternate_student')
		{
			$("#dlmStudentReport").empty();
		}
	},
	'#gradeSelect change': function(element, event){
		this._selectChange('grade', element);
	},
	'#rosterSelect change': function(element, event){
		this._selectChange('roster', element);
	},
	'#teacherRosterSelect change': function(element, event){
		this._selectChange('teacherRoster', element);
	},	
	'#stateSelect change': function(element, event){
		this._selectChange('state', element);
	},
	'#teacherSelect change': function(element, event){
		this._selectChange('teacher', element);
	},
	'#studentRoster change': function(element, event){
		$("#reportDisplayDiv").empty();
		$("#reportDisplayDiv").addClass("hidden");
		
		var form = this.element.find('form'),
		values = can.deparam(form.serialize());
		values.students = $("#studentRoster").select2("val").map(function(){return this.value;}).get();
		if(values.students != null && values.students.length > 0){
			$("#viewReportDiv").removeClass("hidden");
		}	
		else
		{
			$("#viewReportDiv").addClass("hidden");
		}
	},
	'#viewReport click': function(element, event) {
		event.preventDefault();
		var form = this.element.find('form'),
		values = can.deparam(form.serialize());
		var checkTags = $("#studentRoster").val();
		var studentNames = [];
		$(checkTags).each(function( i, value ) {
			studentNames.push($(value).attr("title"));
		 });
		values.studentNames = studentNames;
		values.schoolName = $("#schoolSelect option:selected").text();
		values.rosterName = $("#rosterSelect option:selected").text();
		values.subjectName = $("#subjectSelect option:selected").text();
		values.students = $("#studentRoster").val().map(function(){return this.value;}).get();
		if(values.students != null && values.students.length > 0){
			$("#viewReportDiv").addClass("hidden");
			DLMRosterReport.findOne(values, function(report) {
				element.trigger('selected', report );
			});					
		}
		else {
			$("#studentDiv #requiredmessage").show();
		}
		
		//this.element.find('#rosterreport').empty();
	},
	// this event fires after the 'click' in reportFilter.js, because of the reportFilter() call in init
	'#viewReport selected' : function(element, event, report){
		event.preventDefault();
		this.report = report;
		if(this.currentControlInstance) {
			this.currentControlInstance.destroy();
		}
		this.currentControlInstance = this._renderReport();
	 },
	 _renderReport: function() {
		 $("#reportDisplayDiv").removeClass("hidden");
		 return new rosterReportOnline('#reportDisplayDiv',{report: this.report});
	 },	
	 '#altStudentDropDown change': function(element, event){
		 	$("#altReportDisplayDiv").empty();
			$("#altReportDisplayDiv").addClass("hidden");
			
			var form = this.element.find('form'),
			values = can.deparam(form.serialize());
			if($("#altStudentDropDown option:selected").val()!=''){
				$("#viewAltStudentReportDiv").removeClass("hidden");
			}	
			else
			{
				$("#viewAltStudentReportDiv").addClass("hidden");
			}
		},
	'#viewAltStudentReport click': function(element, event) {
			event.preventDefault();
			var form = this.element.find('form'),
			values = can.deparam(form.serialize());
			values.districtName = $("#districtSelect option:selected").text();
			values.districtId = $("#districtSelect option:selected").val();
			values.schoolName = $("#schoolSelect option:selected").text();
			values.schoolId = $("#schoolSelect option:selected").val();
			values.subjectName = $("#subjectSelect option:selected").text();
			values.subjectId = $("#subjectSelect option:selected").val();
			values.studentName = $("#altStudentDropDown option:selected").text();
			values.studentId = $("#altStudentDropDown option:selected").val();
			values.gradeName =  $("#altStudentDropDown option:selected").data('grade-name');
			
			
			if(values.schoolId!=null && values.subjectId!=null && values.studentId != null && values.schoolId!='' && values.subjectId!='' && values.studentId!=''){
				$("#viewAltStudentReportDiv").addClass("hidden");
			
				DLMStudentReport.findOne(values, function(report) {
					element.trigger('selected', report );
				});
									
			}
			else {
				$("#viewAltStudentReportDiv #requiredmessage").show();
			}
			
			//this.element.find('#rosterreport').empty();
		},
		// this event fires after the 'click' in reportFilter.js, because of the reportFilter() call in init
	'#viewAltStudentReport selected' : function(element, event, report){
			event.preventDefault();
			this.report = report;
			if(this.currentControlInstance) {
				this.currentControlInstance.destroy();
			}
			this.currentControlInstance = this._renderAltStudentReport();
		 },
	_renderAltStudentReport: function() {
			 $("#altReportDisplayDiv").removeClass("hidden");
			 return new studentReport('#altReportDisplayDiv',{report: this.report});
		 },
	'#writingStudentDropDown change': function(element, event){
		if ($(element).val().length > 0){
			$('#viewWritingResponses').css('visibility', 'visible');
		} else {
			$('#viewWritingResponses').css('visibility', 'hidden');
		}
	},
	
	'#newBundledReport click': function(element, event){		
		clearTimeout(ref);
		ref = 'started';
		checkRef = this;
		// New Bundled Report Click		
		$('.allStudentsReportsFile').hide();
		$('.studentBundledReportStatus').show();
		$('#schoolSeparateSelect').prop('disabled', true);
	    $('#schoolSeparateSelectDiv').addClass('separateSelectDiv');
	    $('#schoolSeparateSelect').css({"cursor":"text"});
	    
		$('#districtSeparateSelect').prop('disabled', true); 
		$('#districtSeparateSelectDiv').addClass('separateSelectDiv');
	    $('#districtSeparateSelect').css({"cursor":"text"});
	    var studentBundledReportSchool = $('#studentBundledReportSchool');
		
			var schoolLevel = $('#schoolSelect option:selected').val();
			
			// student bundled school event
			var form = this.element.find('form'),
			values = can.deparam(form.serialize());
			districtOrgId  = $("#districtSelect option:selected").val();
			
			//getOrgsBasedOnUserContext.htm
			
		if(schoolLevel == 0){			
			$.ajax({
			url: 'getBundledReportOrg.htm',
			data: {
			districtId : districtOrgId,
			assessmentProgId : this.options.assessmentProg,
			assessmentProgCode : this.options.assessmentProgCode,
			reportType : this.options.reportType,
			reportCode : $('td.currentReport').attr('id')
			},
			dataType: 'json',
			type: "GET",
			success: function(schoolOrgs) {
				
				totalSchoolLength =schoolOrgs.length ;
				
						
			$.each(schoolOrgs, function(i, schoolOrg) {
				if(totalSchoolLength != 0){	
					studentBundledReportSchool.append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
				}else{
					studentBundledReportSchool.append($('<option selected="select"></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
				}
			});
			
			studentBundledReportSchool.val('').trigger('change.select2');
			if(totalSchoolLength != 1){
				studentBundledReportSchool.val('').trigger('change.select2');
				studentBundledReportSchool.select2({placeholder:'Select'}); 	
			}else {
				checkRef._studentBundledReportContentAreas();
			}
			
			}
			});
			
			$('#studentBundledDistrictBottom').show();
			$('#studentBundledContentAreaDiv').addClass('studentBundledContentAreaDivSchool');
			
			loadPdfInformation(this.options.assessmentProg,districtOrgId);
		} else {
			
			$('#studentBundledContentAreaDiv').addClass('studentBundledContentAreaDivSchoolTop');
				$('#studentBundledReportSchoolDiv').hide();
				$('#studentBundledDistrictBottom').hide();				
				$('#studentBundledSchoolBottom').show();
				this._studentBundledReportContentAreas();	
				loadPdfInformation(this.options.assessmentProg,schoolLevel);
		}			
		studentBundledReportSchool.val('').trigger('change.select2');
		
		
		//grades
		var studentBundledReportGrades = $('#studentBundledReportGrades');
		studentBundledReportGrades.select2({placeholder:'Select'});
		studentBundledReportGrades.val('').trigger('change.select2');
		
		// content area
		var studentBundledReportContentAreas = $('#studentBundledReportContentAreas');
		studentBundledReportContentAreas.select2({placeholder:'Select'});
		studentBundledReportContentAreas.val('').trigger('change.select2');
		
		
	},	
	'#studentBundledReportSchool change': function(element, event){		
		checkRef = this;
		$('#studentBundledReportContentAreas').empty();
		$('#studentBundledReportContentAreas').val('').trigger('change.select2');
		$('#studentBundledReportGrades').empty();
		$('#studentBundledReportGrades').val('').trigger('change.select2');		
		//subject 
		checkRef._studentBundledReportContentAreas();
		
		
	},	
	_studentBundledReportContentAreas: function(){
		checkRef = this;
		
		//Subject
		var studentBundledReportContentAreas = $('#studentBundledReportContentAreas');
		// subject event
		districtOrgId  = $("#districtSelect option:selected").val();	
		studentBundledReportSchool = $('#studentBundledReportSchool option:selected').val();
		
		 var selectSchooltext=[];
         $( "input[id*='studentBundledReportSchool']" ).each(function(){
        	 if($(this). prop("checked") == true){
        		 selectSchooltext.push($(this).attr("value"));
        	 }
        	});		
         
		var schoolLevel = $('#schoolSelect option:selected').val();
		var schoolId = '';
		if(schoolLevel != 0)
		{
			selectSchooltext.push($('#schoolSelect option:selected').val());
		}
		
		
		
		if(studentBundledReportSchool != 0 && studentBundledReportSchool != '' && selectSchooltext != 0){
		$.ajax({
		url: 'getContentAreaForBundledReport.htm',
		dataType: 'json',
		data: {
			reportType : this.options.reportType,
			schoolIds : selectSchooltext,			
			assessmentProgId : this.options.assessmentProg,
			assessmentProgCode : this.options.assessmentProgCode
			},
			type: 'GET',
			success: function(subjects){
				totalSubjectLength = subjects.length;
				studentBundledReportContentAreas.empty();
				//studentBundledReportContentAreas.append($('<option>Select</option>').attr("value", 0));
				$.each(subjects, function(i, subject) {
					if(totalSubjectLength != 1){
					studentBundledReportContentAreas.append($('<option></option>').attr("value", subject.id).text(subject.name));
					}else {
						studentBundledReportContentAreas.append($('<option selected="select"></option>').attr("value", subject.id).text(subject.name));
					}
				});
				
				studentBundledReportContentAreas.val('').trigger('change.select2');
				if(totalSubjectLength != 1){
				studentBundledReportContentAreas.val('').trigger('change.select2');
				studentBundledReportContentAreas.select2({placeholder:'Select'}); 	
				}else {
					$('#studentBundledReportGrades').empty();
					$('#studentBundledReportGrades').val('').trigger('change.select2');
					checkRef._studentBundledReportGrade();
				}
			},
		error: function(){}
		});
	
	}
		studentBundledReportContentAreas.val('').trigger('change.select2');	
	
	
	},
	_studentBundledReportGrade: function(){	
		// Bundled Subject change		
		districtOrgId  = $("#districtSelect option:selected").val();	
		var studentBundledReportSchool = $('#studentBundledReportSchool option:selected').val();
		var studentBundledReportContentAreas = $('#studentBundledReportContentAreas option:selected').val();
		var studentBundledReportGrades = $('#studentBundledReportGrades');
		
		// school selection
		 var selectSchooltext=[];
         $( "input[id*='studentBundledReportSchool']" ).each(function(){
        	 if($(this). prop("checked") == true){
        		 selectSchooltext.push($(this).attr("value"));
        	 }
        	});	
         var schoolLevel = $('#schoolSelect option:selected').val();
 		var schoolId = '';
 		if(schoolLevel != 0)
 		{
 			selectSchooltext.push($('#schoolSelect option:selected').val());
 		}
		
 		//subject selection
 		 var selectSubjecttext=[];
         $( "input[id*='studentBundledReportContentAreas']" ).each(function(){
        	 if($(this). prop("checked") == true){
        		 selectSubjecttext.push($(this).attr("value"));
        	 }
        	});	
		
		// Grades getGradesForReporting.htm
		if(studentBundledReportSchool != 0 && studentBundledReportContentAreas != 0 && selectSchooltext != '' && selectSubjecttext != ''){
		$.ajax({
			url: 'getGradesForBundledReporting.htm',
			dataType: 'json',
			data: {
				reportType : this.options.reportType,
				districtId : districtOrgId,
				schoolIds : selectSchooltext,
				subjectIds : selectSubjecttext,				
				assessmentProgIds : this.options.assessmentProg,
				assessmentProgCode : this.options.assessmentProgCode,
				reportCode : $('td.currentReport').attr("id")
			},
			type: 'GET',
			success: function(grades){
				totalGradesLength=grades.length;
				studentBundledReportGrades.empty();
				//studentBundledReportGrades.append($('<option>Select</option>').attr("value", 0));
				$.each(grades, function(i, grade) {
					if(totalGradesLength != 1){
						studentBundledReportGrades.append($('<option></option>').attr("value", grade.id).text(grade.name));
					}else {
						studentBundledReportGrades.append($('<option selected="select"></option>').attr("value", grade.id).text(grade.name));
					}
				});	
				studentBundledReportGrades.val('').trigger('change.select2');
				if(totalGradesLength != 1){
				studentBundledReportGrades.val('').trigger('change.select2');
				studentBundledReportGrades.select2({placeholder:'Select'}); 	
				}
			},
			error: function(){}
		});		
		
		}
				
		studentBundledReportGrades.val('').trigger('change.select2');
		
	},	
	'#studentBundledReportContentAreas change': function(element, event){
		$('#studentBundledReportGrades').empty();
		$('#studentBundledReportGrades').val('').trigger('change.select2');
		this._studentBundledReportGrade();
		
	},	
	/*  sort option  */	
	'#sortFirstBy change': function(){	
        
		$('#sortSecondBy').val('').trigger('change.select2');
		$('#sortSecondBy').prop('disabled', false);
		$('#sortLastBy').val('').trigger('change.select2');
		$('#sortLastBy').prop('disabled', false);
		
		 // refresh checkbox
			$('#districtBundledSelect').attr('checked', false); 
			$('#districtSeparateSelect').attr('checked', false); 
			$('#schoolBundledSelect').attr('checked', false); 
			$('#schoolSeparateSelect').attr('checked', false); 
			$('#separateBundledReportSelect').attr('checked', false);
			$('#schoolSeparateSelect').prop('disabled', true);
			$('#schoolSeparateSelectDiv').addClass('separateSelectDiv');
			 $('#schoolSeparateSelect').css({"cursor":"text"});
		    
			$('#districtSeparateSelect').prop('disabled', true); 
			$('#districtSeparateSelectDiv').addClass('separateSelectDiv');
			$('#districtSeparateSelect').css({"cursor":"text"});
			
        var selvalue1= $('#sortFirstBy option:selected').val();	      
        var selvalue2= $('#sortSecondBy option:selected').val();
        var selvalue3= $('#sortLastBy option:selected').val();
        var schoolLevel = $('#schoolSelect option:selected').val();	    
        
        
        	if(selvalue1!="0"){
        		$('#bundledContentAreaLastName').show();        		
        	}else {
        		
        		$('#bundledGradeLastName').hide();
        	}
     
        	// If first sort is student last name, do not display ?Sort second by? 
        	if(selvalue1=="legallastname"){	            	
        		 $("#sortSecondBy").prop('disabled', true);
        		 $("#sortLastBy").prop('disabled', true); 
        		$('#bundledContentAreaLastName').hide();
        		$('#bundledGradeLastName').hide();
        	} else if ($("#sortSecondBy").prop("disabled")=="disabled"){
        		$("#sortSecondBy").prop('disabled', false);
        		$("#sortLastBy").prop('disabled', false);
        	}	            	
         if(schoolLevel==0){
        	if(selvalue1=="0"){
        		$("#splitby1").html("Selected Option");
        		$("#splitby2").html("Selected Option");
        	}else {
        		if(selvalue1 == "legallastname"){
        			$("#splitby1").html("student last name");
        		}else{
        		  $("#splitby1").html(selvalue1);
        		}
        		
        		if(selvalue1!="school"){
        			$("#splitby2").html($("#splitby1").text());
        		} else if(selvalue2!="0"){
            			$("#splitby2").html(selvalue2);
        		} else {
            		$("#splitby2").html("Selected Option");
        		}
        	}
        }else{
        	if(selvalue1=="0"){
        		$("#splitby3").html("Selected Option");
        	}else if (selvalue1 == "legallastname"){
        		$('#separateBundledReportSelect').attr('checked', false);
        		$('#separateBundledReportSelect').prop('disabled', true);
        		$('#separateBundledReportSelectDiv').addClass('separateSelectDiv');
    		    $('#separateBundledReportSelect').css({"cursor":"text"});
    		    
        		$("#splitby3").html("student last name");
        	}else if (selvalue1 == "grouping1"){
        		$('#separateBundledReportSelect').attr('checked', false);
        		$('#separateBundledReportSelect').prop('disabled', true);
        		$('#separateBundledReportSelectDiv').addClass('separateSelectDiv');
    		    $('#separateBundledReportSelect').css({"cursor":"text"});
        	}else{
        		$("#splitby3").html(selvalue1);
        		$('#separateBundledReportSelect').prop('disabled', false);
        		$('#separateBundledReportSelectDiv').removeClass('separateSelectDiv');
    		    $('#separateBundledReportSelect').css({"cursor":"pointer"});
        	}
        }
        if($("#sortSecondBy").attr("disabled")!="disabled") {
        	$( "input[name*='sortSecondBy']" ).each(function(){
        		if($(this).val()!=selvalue3){
        			$(this).parent().parent().show();
        		}
        		if(($(this).val()==selvalue1 && selvalue1!="0") || (selvalue1!="grouping1") && ($(this).val()=="grouping2")){        			
        				$(this).parent().parent().hide();
        		} 
        	});
        }
        $( "input[name*='sortLastBy']" ).each(function(){
        	if($(this).val()!=selvalue2) {
        			$(this).parent().parent().show();
        	}
        	if(($(this).val()==selvalue1 && selvalue1!="0")|| ($(this).val()=="grouping2")){		        		
        		$(this).parent().parent().hide();
        	}
        });
     
},		
'#sortSecondBy change': function(){	
	
	$('#sortLastBy').val('').trigger('change.select2');
	$('#sortLastBy').prop('disabled', false);
	//$('#bundledGradeLastName').show();
    // refresh checkbox
	    $('#districtBundledSelect').attr('checked', false); 
	    $('#districtSeparateSelect').attr('checked', false); 
	    // second sort option disabled and add css
	    $('#districtSeparateSelect').prop('disabled', true); 
	    $('#districtSeparateSelectDiv').addClass('separateSelectDiv');
	    $('#districtSeparateSelect').css({"cursor":"text"});
	    
		$('#schoolBundledSelect').attr('checked', false); 
		$('#schoolSeparateSelect').attr('checked', false); 
		$('#schoolSeparateSelect').prop('disabled', true);
		$('#schoolSeparateSelectDiv').addClass('separateSelectDiv');
		 $('#schoolSeparateSelect').css({"cursor":"text"});
	    
		var selvalue1= $('#sortFirstBy option:selected').val();	      
        var selvalue2= $('#sortSecondBy option:selected').val();
        var selvalue3= $('#sortLastBy option:selected').val();
        var schoolLevel = $('#schoolSelect option:selected').val();	
        
        if(selvalue1!="0" && selvalue2 != "0"){    		
    		$('#bundledGradeLastName').show();
    	}else {    		
    		$('#bundledGradeLastName').hide();
    	}
         
        if(selvalue2=="legallastname"){
    		$("#sortLastBy").prop('disabled', true);
    		$('#bundledGradeLastName').hide();
    		//$("#separateBundledReportSelect").attr('disabled',true);    		
    	} else if ($("#sortLastBy").attr("disabled")!="disabled"){
    		$("#sortSecondBy").prop('disabled', false);
    		//$("#separateBundledReportSelect").removeAttr("disabled");
    	}
        if(schoolLevel==0) {
        	  	if(selvalue1=="school" || selvalue1=="0"){
        		if(selvalue2=="legallastname"){
        			 $("#splitby2").html("student last name");
        		}else{        			
    			  $("#splitby2").html(selvalue2);
        		}
        	}else {        		
        		$("#splitby2").html(selvalue1);
        	}
        	if(selvalue2=="0"){
        		if(selvalue1!="school" && selvalue1!="0"){	            		 
        			$("#splitby2").html(selvalue1);
            	} else {
            		$("#splitby2").html("Selected Option");
            	}
    		}
        	  
        	
        }
        if($("#sortLastBy").attr("disabled")!="disabled") {		        
	        $( "input[name*='sortLastBy']").each(function(){
	        	if($(this).val()!=selvalue1 ){
	        		$(this).parent().parent().show();
	        	}
	        	if(($(this).val()==selvalue2 && selvalue2!="0") || (selvalue2!="grouping1") && ($(this).val()=="grouping2") ) {	
	        	      			$(this).parent().parent().hide();
	        	}else if(selvalue2=="0" && $(this).val()!=selvalue1){
	        		$(this).parent().parent().show();
	        	}
	        	
	        	
	        });
        }
        $( "input[name*='sortFirstBy']" ).each(function(){
        	if($(this).val()!=selvalue3){
        		$(this).parent().parent().show();
        	}
        	if($(this).val()==selvalue2 && selvalue2!="0"){
        		//$(this).parent().parent().hide();
        	} 
        });
 
},
	
	'#sortLastBy change': function(){		
	     var selvalue1= $('#sortFirstBy option:selected').val();	      
         var selvalue2= $('#sortSecondBy option:selected').val();
         var selvalue3= $('#sortLastBy option:selected').val();
        $("input[name*='sortSecondBy']" ).each(function(){
        	if($(this).val()!=selvalue1 && selvalue1!="grouping1" && $(this).val()!="grouping2"){        		
        		$(this).parent().parent().show();
        	}
        	if($(this).val()==selvalue3 && selvalue3!="0"){
        		//$(this).parent().parent().hide();
        	} 
        });
        $( "input[name*='sortFirstBy']").each(function(){
        	if($(this).val()!=selvalue2){
        		$(this).parent().parent().show();
        	}
        	if($(this).val()==selvalue3 && selvalue3!="0"){		        		
        		//$(this).parent().parent().hide();
        	}
        });
     
},
'#districtBundledSelect click' : function(){
	if($('#districtBundledSelect:checked').val()){
		var selvalue1= $('#sortFirstBy option:selected').val();
		if(selvalue1!="legallastname" && selvalue1!="grouping1"){
			$('#districtSeparateSelect').prop('disabled', false);
			$('#districtSeparateSelectDiv').removeClass('separateSelectDiv');
			$('#districtSeparateSelect').css({"cursor":"pointer"});
		}
	}else {
		$('#districtSeparateSelect').prop('disabled', true); 
	    $('#districtSeparateSelectDiv').addClass('separateSelectDiv');
	    $('#districtSeparateSelect').css({"cursor":"text"});
		
		$('#districtSeparateSelect').prop('checked', false);
	}	
},
'#schoolBundledSelect click' : function(){
	if($('#schoolBundledSelect:checked').val()){
		var selvalue1= $('#sortFirstBy option:selected').val();
		var selvalue2= $('#sortSecondBy option:selected').val();
		if(selvalue1!="legallastname" && selvalue1!="grouping1" && selvalue2!="legallastname" && selvalue2!="grouping1"){
			$('#schoolSeparateSelect').prop('disabled', false);
			$('#schoolSeparateSelectDiv').removeClass('separateSelectDiv');
			 $('#schoolSeparateSelect').css({"cursor":"pointer"});
		}
	}else {
		$('#schoolSeparateSelect').prop('disabled', true);
		$('#schoolSeparateSelectDiv').addClass('separateSelectDiv');
		 $('#schoolSeparateSelect').css({"cursor":"text"});
		$('#schoolSeparateSelect').prop('checked', false);
	}
},
'#studentBundledPrev click': function(element, event){
	clearTimeout(ref);
	ref = 'refreshed';
	$('.allStudentsReportsFile').show();
	$('.studentBundledReportStatus').hide();
	// refresh select option
	$('#studentBundledReportSchool').val('').trigger('change.select2');
	$('#studentBundledReportSchool').val('').trigger('change.select2');
	
	$('#studentBundledReportContentAreas').val('').trigger('change.select2');
	$('#studentBundledReportContentAreas').val('').trigger('change.select2');
	
	$('#studentBundledReportGrades').val('').trigger('change.select2');
	$('#studentBundledReportGrades').val('').trigger('change.select2');
	
	// refresh sort
	$('#sortFirstBy').val('').trigger('change.select2');
	$('#sortSecondBy').val('').trigger('change.select2');
	$('#sortLastBy').val('').trigger('change.select2');
	 
	// refresh checkbox
	$('#districtBundledSelect').attr('checked', false); 
	$('#districtSeparateSelect').attr('checked', false); 
	$('#schoolBundledSelect').attr('checked', false); 
	$('#schoolSeparateSelect').attr('checked', false); 
	$('#separateBundledReportSelect').attr('checked', false);
	
	if($('#schoolSelect').val() == 0){
	  $('#districtSelect').trigger("change");
	}else{
	  $('#schoolSelect').trigger("change");	
	}
	
},

'#newStudentSummaryBundledReport click': function(element, event){		
	clearTimeout(ref);
	ref = 'started';
	checkRef = this;
	// New Bundled Report Click		
	$('.allStudentsReportsFile').hide();
	$('.studentSummaryBundledReportStatus').show();
	$('#schoolSummarySeparateSelect').prop('disabled', true);
    $('#schoolSummarySeparateSelectDiv').addClass('separateSelectDiv');
    $('#schoolSummarySeparateSelect').css({"cursor":"text"});
    
	$('#districtSummarySeparateSelect').prop('disabled', true); 
	$('#districtSummarySeparateSelectDiv').addClass('separateSelectDiv');
    $('#districtSummarySeparateSelect').css({"cursor":"text"});
    var studentBundledReportSchool = $('#studentSummaryBundledReportSchool');
	
		var schoolLevel = $('#schoolSelect option:selected').val();
		
		// student bundled school event
		var form = this.element.find('form'),
		values = can.deparam(form.serialize());
		districtOrgId  = $("#districtSelect option:selected").val();
		
		//getOrgsBasedOnUserContext.htm
		
	if(schoolLevel == 0){			
		$.ajax({
		url: 'getBundledReportOrg.htm',
		data: {
		districtId : districtOrgId,
		assessmentProgId : this.options.assessmentProg,
		assessmentProgCode : this.options.assessmentProgCode,
		reportType : this.options.reportType,
		reportCode : 'StudentSummary'
		},
		dataType: 'json',
		type: "GET",
		success: function(schoolOrgs) {
			
			totalSchoolLength =schoolOrgs.length ;
			
					
		$.each(schoolOrgs, function(i, schoolOrg) {
			if(totalSchoolLength != 0){	
				studentBundledReportSchool.append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
			}else{
				studentBundledReportSchool.append($('<option selected="select"></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
			}
		});
		
		studentBundledReportSchool.val('').trigger('change.select2');
		if(totalSchoolLength != 1){
			studentBundledReportSchool.val('').trigger('change.select2');
			studentBundledReportSchool.select2({placeholder:'Select'}); 	
		}else {
			checkRef._studentSummaryBundledReportGrade();
		}
		
		}
		});
		
		$('#studentSummaryBundledDistrictBottom').show();
		
		loadSummaryPdfInformation(this.options.assessmentProg,districtOrgId);
	} else {
		
			$('#studentSummaryBundledReportSchoolDiv').hide();
			$('#studentSummaryBundledDistrictBottom').hide();				
			$('#studentSummaryBundledSchoolBottom').show();
			this._studentSummaryBundledReportGrade();	
			loadSummaryPdfInformation(this.options.assessmentProg,schoolLevel);
	}			
	studentBundledReportSchool.val('').trigger('change.select2');
	
	
	//grades
	var studentBundledReportGrades = $('#studentSummaryBundledReportGrades');
	studentBundledReportGrades.select2({placeholder:'Select'});
	studentBundledReportGrades.val('').trigger('change.select2');		
	
},	
'#studentSummaryBundledReportSchool change': function(element, event){		
	checkRef = this;
	$('#studentSummaryBundledReportGrades').empty();
	$('#studentSummaryBundledReportGrades').val('').trigger('change.select2');		
	//subject 
	checkRef._studentSummaryBundledReportGrade();
	
	
},
_studentSummaryBundledReportGrade: function(){	
	// Bundled Subject change		
	districtOrgId  = $("#districtSelect option:selected").val();	
	var studentBundledReportSchool = $('#studentSummaryBundledReportSchool option:selected').val();
	var studentBundledReportGrades = $('#studentSummaryBundledReportGrades');
	
	// school selection
	 var selectSchooltext=[];
     $( "input[id*='studentSummaryBundledReportSchool']" ).each(function(){
    	 if($(this). prop("checked") == true){
    		 selectSchooltext.push($(this).attr("value"));
    	 }
    	});	
     var schoolLevel = $('#schoolSelect option:selected').val();
		var schoolId = '';
		if(schoolLevel != 0)
		{
			selectSchooltext.push($('#schoolSelect option:selected').val());
		}
	
	// Grades getGradesForReporting.htm
	if(studentBundledReportSchool != 0 && selectSchooltext != ''){
	$.ajax({
		url: 'getGradesForDynamicStudentSummaryBundledReport.htm',
		dataType: 'json',
		data: {
			reportType : this.options.reportType,
			districtId : districtOrgId,
			schoolIds : selectSchooltext,			
			assessmentProgIds : this.options.assessmentProg,
			assessmentProgCode : this.options.assessmentProgCode
		},
		type: 'GET',
		success: function(grades){
			totalGradesLength=grades.length;
			studentBundledReportGrades.empty();
			$.each(grades, function(i, grade) {
				if(totalGradesLength != 1){
					studentBundledReportGrades.append($('<option></option>').attr("value", grade.id).text(grade.name));
				}else {
					studentBundledReportGrades.append($('<option selected="select"></option>').attr("value", grade.id).text(grade.name));
				}
			});	
			studentBundledReportGrades.val('').trigger('change.select2');
			if(totalGradesLength != 1){
			studentBundledReportGrades.val('').trigger('change.select2');
			studentBundledReportGrades.select2({placeholder:'Select'}); 	
			}
		},
		error: function(){}
	});		
	
	}
			
	studentBundledReportGrades.val('').trigger('change.select2');
	
},	
/*  sort option  */	
'#summarySortFirstBy change': function(){	
    
	$('#summarySortSecondBy').val('').trigger('change.select2');
	$('#summarySortSecondBy').prop('disabled', false);
	$('#summarySortLastBy').val('').trigger('change.select2');
	$('#summarySortLastBy').prop('disabled', false);
	
	 // refresh checkbox
		$('#districtSummaryBundledSelect').attr('checked', false); 
		$('#districtSummarySeparateSelect').attr('checked', false); 
		$('#schoolSummaryBundledSelect').attr('checked', false); 
		$('#schoolSummarySeparateSelect').attr('checked', false); 
		$('#summarySeparateBundledReportSelect').attr('checked', false);
		$('#schoolSummarySeparateSelect').prop('disabled', true);
		$('#schoolSummarySeparateSelectDiv').addClass('separateSelectDiv');
		 $('#schoolSummarySeparateSelect').css({"cursor":"text"});
	    
		$('#districtSummarySeparateSelect').prop('disabled', true); 
		$('#districtSummarySeparateSelectDiv').addClass('separateSelectDiv');
		$('#districtSummarySeparateSelect').css({"cursor":"text"});
		
    var selvalue1= $('#summarySortFirstBy option:selected').val();	      
    var selvalue2= $('#summarySortSecondBy option:selected').val();
    var selvalue3= $('#summarySortLastBy option:selected').val();
    var schoolLevel = $('#schoolSelect option:selected').val();	    
    
    
    	if(selvalue1!="0"){
    		$('#bundledSummaryContentAreaLastName').show();        		
    	}else {
    		
    		$('#bundledSummaryGradeLastName').hide();
    	}
 
    	// If first sort is student last name, do not display ?Sort second by? 
    	if(selvalue1=="legallastname"){	            	
    		 $("#summarySortSecondBy").prop('disabled', true);
    		 $("#summarySortLastBy").prop('disabled', true); 
    		$('#bundledSummaryContentAreaLastName').hide();
    		$('#bundledSummaryGradeLastName').hide();
    	} else if ($("#sortSecondBy").attr("disabled")=="disabled"){
    		$("#summarySortSecondBy").prop('disabled', false);
    		$("#summarySortLastBy").prop('disabled', false);
    	}	            	
     if(schoolLevel==0){
    	if(selvalue1=="0"){
    		$("#summarySplitby1").html("Selected Option");
    		$("#summarySplitby2").html("Selected Option");
    	}else {
    		if(selvalue1 == "legallastname"){
    			$("#summarySplitby1").html("student last name");
    		}else{
    		  $("#summarySplitby1").html(selvalue1);
    		}
    		
    		if(selvalue1!="school"){
    			$("#summarySplitby2").html($("#summarySplitby1").text());
    		} else if(selvalue2!="0"){
        		$("#summarySplitby2").html(selvalue2);
    		} else {
        		$("#summarySplitby2").html("Selected Option");
    		}
    	}
    }else{
    	if(selvalue1=="0"){
    		$("#summarySplitby3").html("Selected Option");
    	}else if (selvalue1 == "legallastname"){
    		$('#summarySeparateBundledReportSelect').attr('checked', false);
    		$('#summarySeparateBundledReportSelect').prop('disabled', true);
    		$('#summarySeparateBundledReportSelectDiv').addClass('separateSelectDiv');
		    $('#summarySeparateBundledReportSelect').css({"cursor":"text"});
		    
    		$("#summarySplitby3").html("student last name");
    	}else if (selvalue1 == "grouping1"){
    		$('#summarySeparateBundledReportSelect').attr('checked', false);
    		$('#summarySeparateBundledReportSelect').prop('disabled', true);
    		$('#summarySeparateBundledReportSelectDiv').addClass('separateSelectDiv');
		    $('#summarySeparateBundledReportSelect').css({"cursor":"text"});
    	}else{
    		$("#summarySplitby3").html(selvalue1);
    		$('#summarySeparateBundledReportSelect').prop('disabled', false);
    		$('#summarySeparateBundledReportSelectDiv').removeClass('separateSelectDiv');
		    $('#summarySeparateBundledReportSelect').css({"cursor":"pointer"});
    	}
    }
    if($("#summarySortSecondBy").attr("disabled")!="disabled") {
    	$( "input[name*='summarySortSecondBy']" ).each(function(){
    		if($(this).val()!=selvalue3){
    			$(this).parent().parent().show();
    		}
    		if(($(this).val()==selvalue1 && selvalue1!="0") || (selvalue1!="grouping1") && ($(this).val()=="grouping2")){        			
    				$(this).parent().parent().hide();
    		} 
    	});
    }
    $( "input[name*='summarySortLastBy']" ).each(function(){
    	if($(this).val()!=selvalue2) {
    			$(this).parent().parent().show();
    	}
    	if(($(this).val()==selvalue1 && selvalue1!="0")|| ($(this).val()=="grouping2")){		        		
    		$(this).parent().parent().hide();
    	}
    });
 
},		
'#summarySortSecondBy change': function(){	

$('#summarySortLastBy').val('').trigger('change.select2');
$('#summarySortLastBy').prop('disabled', false);
//$('#bundledGradeLastName').show();
// refresh checkbox
    $('#districtSummaryBundledSelect').attr('checked', false); 
    $('#districtSummarySeparateSelect').attr('checked', false); 
    // second sort option disabled and add css
    $('#districtSummarySeparateSelect').prop('disabled', true); 
    $('#districtSummarySeparateSelectDiv').addClass('separateSelectDiv');
    $('#districtSummarySeparateSelect').css({"cursor":"text"});
    
	$('#schoolSummaryBundledSelect').attr('checked', false); 
	$('#schoolSummarySeparateSelect').attr('checked', false); 
	$('#schoolSummarySeparateSelect').prop('disabled', true);
	$('#schoolSummarySeparateSelectDiv').addClass('separateSelectDiv');
	 $('#schoolSummarySeparateSelect').css({"cursor":"text"});
    
	var selvalue1= $('#summarySortFirstBy option:selected').val();	      
    var selvalue2= $('#summarySortSecondBy option:selected').val();
    var selvalue3= $('#summarySortLastBy option:selected').val();
    var schoolLevel = $('#schoolSelect option:selected').val();	
    
    if(selvalue1!="0" && selvalue2 != "0"){    		
		$('#bundledSummaryGradeLastName').show();
	}else {    		
		$('#bundledSummaryGradeLastName').hide();
	}
     
    if(selvalue2=="legallastname"){
		$("#summarySortLastBy").prop('disabled', true);
		$('#bundledSummaryGradeLastName').hide();
		//$("#separateBundledReportSelect").attr('disabled',true);    		
	} else if ($("#summarySortLastBy").attr("disabled")!="disabled"){
		$("#summarySortSecondBy").prop('disabled', false);
		//$("#separateBundledReportSelect").removeAttr("disabled");
	}
    if(schoolLevel==0) {
    	    	if(selvalue1=="school" || selvalue1=="0"){
    		if(selvalue2=="legallastname"){
    			 $("#summarySplitby2").html("student last name");
    		}else{        			
			  $("#summarySplitby2").html(selvalue2);
    		}
    	}else {        		
    		$("#summarySplitby2").html(selvalue1);
    	}
    	if(selvalue2=="0"){
    		if(selvalue1!="school" && selvalue1!="0"){	            		 
    			$("#summarySplitby2").html(selvalue1);
        	} else {
        		$("#summarySplitby2").html("Selected Option");
        	}
		}
    	  
    	
    }
    if($("#summarySortLastBy").attr("disabled")!="disabled") {		        
        $( "input[name*='summarySortLastBy']").each(function(){
        	if($(this).val()!=selvalue1 ){
        		$(this).parent().parent().show();
        	}
        	if(($(this).val()==selvalue2 && selvalue2!="0") || (selvalue2!="grouping1") && ($(this).val()=="grouping2") ) {	
        	      			$(this).parent().parent().hide();
        	}else if(selvalue2=="0" && $(this).val()!=selvalue1){
        		$(this).parent().parent().show();
        	}
        	
        	
        });
    }
    $( "input[name*='summarySortFirstBy']" ).each(function(){
    	if($(this).val()!=selvalue3){
    		$(this).parent().parent().show();
    	}
    	if($(this).val()==selvalue2 && selvalue2!="0"){
    		//$(this).parent().parent().hide();
    	} 
    });

},

'#summarySortLastBy change': function(){		
     var selvalue1= $('#summarySortFirstBy option:selected').val();	      
     var selvalue2= $('#summarySortSecondBy option:selected').val();
     var selvalue3= $('#summarySortLastBy option:selected').val();
    $("input[name*='summarySortSecondBy']" ).each(function(){
    	if($(this).val()!=selvalue1 && selvalue1!="grouping1" && $(this).val()!="grouping2"){        		
    		$(this).parent().parent().show();
    	}
    	if($(this).val()==selvalue3 && selvalue3!="0"){
    		//$(this).parent().parent().hide();
    	} 
    });
    $( "input[name*='summarySortFirstBy']").each(function(){
    	if($(this).val()!=selvalue2){
    		$(this).parent().parent().show();
    	}
    	if($(this).val()==selvalue3 && selvalue3!="0"){		        		
    		//$(this).parent().parent().hide();
    	}
    });
 
},
'#districtSummaryBundledSelect click' : function(){
if($('#districtSummaryBundledSelect:checked').val()){
	var selvalue1= $('#summarySortFirstBy option:selected').val();
	if(selvalue1!="legallastname" && selvalue1!="grouping1"){
		$('#districtSummarySeparateSelect').prop('disabled', false);
		$('#districtSummarySeparateSelectDiv').removeClass('separateSelectDiv');
		$('#districtSummarySeparateSelect').css({"cursor":"pointer"});
	}
}else {
	$('#districtSummarySeparateSelect').prop('disabled', true); 
    $('#districtSummarySeparateSelectDiv').addClass('separateSelectDiv');
    $('#districtSummarySeparateSelect').css({"cursor":"text"});
	
	$('#districtSummarySeparateSelect').prop('checked', false);
}	
},
'#schoolSummaryBundledSelect click' : function(){
if($('#schoolSummaryBundledSelect:checked').val()){
	var selvalue1= $('#summarySortFirstBy option:selected').val();
	var selvalue2= $('#summarySortSecondBy option:selected').val();
	if(selvalue1!="legallastname" && selvalue1!="grouping1" && selvalue2!="legallastname" && selvalue2!="grouping1"){
		$('#schoolSummarySeparateSelect').prop('disabled', false);
		$('#schoolSummarySeparateSelectDiv').removeClass('separateSelectDiv');
		 $('#schoolSummarySeparateSelect').css({"cursor":"pointer"});
	}
}else {
	$('#schoolSummarySeparateSelect').prop('disabled', true);
	$('#schoolSummarySeparateSelectDiv').addClass('separateSelectDiv');
	 $('#schoolSummarySeparateSelect').css({"cursor":"text"});
	$('#schoolSummarySeparateSelect').prop('checked', false);
}
},
'#studentSummaryBundledPrev click': function(element, event){
clearTimeout(ref);
ref = 'refreshed';
$('.allStudentsReportsFile').show();
$('.studentSummaryBundledReportStatus').hide();
// refresh select option
$('#studentSummaryBundledReportSchool').val('').trigger('change.select2');
$('#studentSummaryBundledReportSchool').val('').trigger('change.select2');

$('#studentSummaryBundledReportGrades').val('').trigger('change.select2');
$('#studentSummaryBundledReportGrades').val('').trigger('change.select2');

// refresh sort
$('#summarySortFirstBy').val('').trigger('change.select2');
$('#summarySortSecondBy').val('').trigger('change.select2');
$('#summarySortLastBy').val('').trigger('change.select2');
 
// refresh checkbox
$('#districtSummaryBundledSelect').attr('checked', false); 
$('#districtSummarySeparateSelect').attr('checked', false); 
$('#schoolSummaryBundledSelect').attr('checked', false); 
$('#schoolSummarySeparateSelect').attr('checked', false); 
$('#separateSummaryBundledReportSelect').attr('checked', false);

if($('#schoolSelect').val() == 0){
  $('#districtSelect').trigger("change");
}else{
  $('#schoolSelect').trigger("change");	
}

},	
	'#viewWritingResponses click': function(element, event){
		event.preventDefault();
		
		$('#saveWritingResponsePDF').css('visibility', 'hidden').off();
		
		var form = this.element.find('form'),
		values = can.deparam(form.serialize());
		values.assessmentProgramId = this.options.assessmentProg;
		
		values.ids = [];
		var checkedItems = $('#writingStudentDropDown').val();
		for (var x = 0; x < checkedItems.length; x++){
			var item = $(checkedItems[x]);
			values.ids.push(item.val());
		}
		
		values.includeScoredResponses = $('#writingIncludeScored').prop('checked');
		
		if(values.assessmentProgramId != null && values.assessmentProgramId != '' &&
				values.districtId != null && values.districtId != '' &&
				values.schoolId != null && values.schoolId != ''&&
				values.gradeId != null && values.gradeId != '' &&
				values.ids.length > 0){
			$('#writingReportDisplayDiv').empty();
			WritingReport.findOne(values, function(report) {
				element.trigger('selected', report);
			});
		}
	},
	'#viewWritingResponses selected': function(element, event, report){
		event.preventDefault();
		this.report = report;
		if(this.currentControlInstance) {
			this.currentControlInstance.destroy();
		}
		this.currentControlInstance = this._renderWritingStudentReport();
	},
	_renderWritingStudentReport: function(){
		var canSavePDF = false;
		var ids = [];
		
		if (this.report != null){
			for (var x in this.report){
				if (!isNaN(x)){
					var found = false;
					for (var i = 0; i < ids.length; i++){
						if (ids[i] === this.report[x].studentId){
							found = true;
						}
					}
					if (!found){
						ids.push(this.report[x].studentId);
						canSavePDF = true;
					}
				}
			}
		}
		
		var reportIdsParam = '';
		for (var x = 0; x < ids.length; x++){
			reportIdsParam += '&ids%5B%5D=' + encodeURIComponent(ids[x]);
		}
		
		var tmp = new writingReport('#writingReportDisplayDiv', {report: this.report});
		$('#writingReportDisplayDiv').show();
		$('#saveWritingResponsePDF').css('visibility', 'visible');
		
		if (canSavePDF){
			var districtId = $('#districtSelect').val();
			var schoolId = $('#schoolSelect').val();
			var gradeId = $('#gradeSelect').val();
			$('#saveWritingResponsePDF').on('click', function(){
				var pdfUrlParams = '&districtId=' + encodeURIComponent(districtId) +
				'&schoolId=' + encodeURIComponent(schoolId) +
				'&gradeId=' + encodeURIComponent(gradeId) +
				'&includeScoredResponses=' + encodeURIComponent($('#writingIncludeScored').prop('checked')) +
				reportIdsParam;
				
				var url = 'getWritingResponsesPDF.htm?' + pdfUrlParams;
				window.location = url;
			});
		}
		
		return tmp;
	},
	_renderBlueprintCoverageReport: function(params){
		$('#blueprintCoverageReportDisplayDiv').show();
		return new blueprintCoverageReport('#blueprintCoverageReportDisplayDiv', {report: this.report, params: params});
	},
	'#resetButton click': function(element, event){
		$('#resetButton', this.filterElement).hide();
		$('.pagination', this.element).css('display', 'none');
		$('.textLine .text', this.filterElement).text('');
		
		this._resetFiltersPast(-1);
		this._disableFiltersPast(-1);
		this._loadNextFilterAfter(-1);
		this._enableNextFilterPast(-1);

		
		clearTimeout(ref);
		ref ='refreshed';
		$('#reportContent').empty();
	},
	_selectChange: function(type, element){
		var $element = $(element);		
		if(element.val() === '0'){			
			var index = $element.data('filter-index');
			this._resetFiltersPast(index);
			this._disableFiltersPast(index);
			$('#' + type + 'Text').text('');
			if(type == 'reportYear'){
				$('#textLine').css({"border-bottom": "none"});
			}
			this._enableNextFilterPast(index-1);
			$('#reportContent').empty();
			this.filterElement = $('#reportFilter', this.element);
			$('#resetButton', this.filterElement).hide();
			
			}
		// this "if" is part of the hack above mentioned in the "close" event of the multiselect
		else
		  {
			if (!$element.data('open')){
			$('#reportContent').empty();
			if (this.filtersNeeded[type]){ // check if that filter type is present, just as a safety measure
				var id = $element.val();
				var index = $element.data('filter-index');
				this._resetFiltersPast(index);
				this._disableFiltersPast(index);
				if (typeof (id) === 'object'){ // we're dealing with an array, so let's remove the silly default options
					if (id != null){
						var defaultOptions = ['', '0'];
						for (var x = 0; x < defaultOptions.length; x++){
							var indexOfDefaultOption = $.inArray(defaultOptions[x], id);
							if (indexOfDefaultOption > -1){
								id.splice(indexOfDefaultOption, 1);
							}
						}
						if (id.length === 0){
							id = '';
						}
					} else {
						id = '';
					}
				}
				if (id !== '' && id != '0'){
					var text = typeof (id) == 'object' && id.length > 1 ? id.length + ' Selected' : $('option:selected[value!="0"]', $element).text();
					$('#' + type + 'Text').text(text);
					this._completeFilterAt(index);
					this._loadNextFilterAfter(index);
					this._enableNextFilterPast(index);
				}
				else if(id==-1 && (this.options.reportType=='general_student_all' || this.options.reportType=='alternate_student_all') || this.options.reportType== 'kelpa_student_all' || this.options.reportType=='alternate_student_summary_all' || this.options.reportType=='alternate_school_summary_all')
				{
					$('#' + type + 'Text').text($('option:selected', $element).text());
					this._completeFilterAt(index);
					this._loadNextFilterAfter(index);
					this._enableNextFilterPast(index);
				}
				else {
					$('#' + type + 'Text').text('');
					this._enableNextFilterPast(index - 1); // "re-enable" this filter, mostly make sure it's the right visual state (enabled versus complete)
				}
				
				// WTF is this for?
				// It's messing up order in which filters are enabled on certain reports (writing report, specifically tested)
				if($.inArray(this.options.reportType, ['alternate_monitoring_summary', 'alternate_blueprint_coverage', 'alternate_school_summary','alternate_yearend_district_summary','general_district_summary','alternate_school_summary_all']) === -1 &&
						type == 'district' && (this.options.assessmentProgCode == 'DLM' || this.options.assessmentProgCode == 'KAP' || this.options.assessmentProgCode == 'CPASS' || this.options.assessmentProgCode == 'KELPA2') && ($('#orgLevel').val() != 'null' && $('#orgLevel').val() < 70)){
					this._enableNextFilterPast(index+1);
				}
			}
		}
	}
	},
	_loadNextFilterAfter: function(index){
		var filter = this._getFilterAt(index + 1);
		
		if (filter.length > 0){
			var currentType = filter.data('data-type');
			
			switch (currentType){			
				case 'reportYear':
					this._loadReportYear();
					break;
				case 'district':
					this._loadDistricts();
					break;
				case 'school':
					this._loadSchools();
					break;
				case 'subject':
					this._loadSubjects();
					break;
				case 'grade':
					this._loadGrades();
					break;
				case 'roster':
					this._loadRosters();
					break;
				case 'teacherRoster':
					this._loadTeacherRosters();
					break;	
				case 'state':
					this._loadStates();
					break;
				case 'teacher':
					this._loadTeachers();
					break;	
				default:
					break;
			}
		}
	},
	_loadReportYear: function(){
		var me = this;		
		 var  readyToView = $('td.currentReport').attr('readyToView');
		 var access = $('td.currentReport').attr('access');
		
		 $.ajax({
			url: 'getReportyearByUserForReports.htm',
			dataType: 'json',
			data: {
				reportType:this.options.reportType,
				reportCode : $('td.currentReport').attr('id'),
				assessmentCode : this.options.assessmentProgCode,
				assessmentProgId : this.options.assessmentProg,
				
			},
			type: 'POST',
			success: function(data){
				var select = $('#reportYearSelect', me.filterElement);
				if (data !== undefined && data !== null && data.length > 0){				
					currentReportYear = data[0];
					var i = 1;	
					//var status= false;
					if(readyToView == 'true' && access == 'true')						
						i=0;
					for ( i  ; i < data.length; i++){	
							select.append($('<option>', { value: data[i], text: data[i] }));
					}
					var j = 1;						
					if(readyToView == 'true' && access == 'true')						
						j=0;
					select.val(data[j]).change();
					if($(reportYearSelect).find("option").length === 1)
					       select.append($('<option>', { value: '',text: 'No Report Year Available' }));
					select.val('').trigger('change.select2');					
				} else {
					select.append($('<option>', { value: '',text: 'No Report Year Available' }));
				}
				
			},
			error: function(){}
		});
	
		
	},
	_loadDistricts: function(){
		var me = this;
		var url = 'getDistrictsByUserForReports.htm';			
    	 if (me.filtersNeeded.district){
			$.ajax({
				url: url,
				dataType: 'json',
				data: { 
					isInactiveOrgReq : this.options.isInactiveOrgReq,
					reportType:this.options.reportType,
					assessmentProgId : this.options.assessmentProg,},
				
				type: 'POST',
				success: function(data){
					if (data !== undefined && data !== null && data.length > 0){
						var select = $('#districtSelect', me.filterElement);
						for (var i = 0; i < data.length; i++){
							select.append($('<option>', { value: data[i].id, text: data[i].organizationName }));
						}
						if (data.length === 1){
							select.val(data[0].id).change();
						}
						select.val('').trigger('change.select2');
					} else {
						//TODO no orgs found
						/*if(me.options.reportType == 'alternate_yearend_district_summary' && me.options.assessmentProgCode == 'DLM'){
							$("#dlmGRFReportErrorMsg").show();
						}*/
					}
				},
				error: function(){}
			});
		}
	},
	_loadSchools: function(){
		var me = this;
//		var reportType = this.options.reportType;
		var url = '';
	
			 url = 'getSchoolsByUserForReports.htm';
		
		var districtIds = $('#districtSelect').val();
		
		if (typeof (districtIds) !== 'object'){ // meaning it was a single select
			districtIds = [districtIds];
		}
		if (me.filtersNeeded.school){
			$.ajax({
				url: url,
				dataType: 'json',
				data: {
					districtIds: districtIds,
					reportYear : $('#reportYearSelect').val(),
					reportType:this.options.reportType,
					isInactiveOrgReq : this.options.isInactiveOrgReq,
					assessmentProgId : this.options.assessmentProg,
				},
				type: 'POST',
				success: function(data){
					if (data !== undefined && data !== null && data.length > 0){
						var select = $('#schoolSelect', me.filterElement);
						/*if((reportType=='general_student_all' || reportType=='alternate_student_all') && data.length > 1)
							select.append($('<option>', { value: -1, text: 'All' }));*/
						for (var i = 0; i < data.length; i++){
							select.append($('<option>', { value: data[i].id, text: data[i].organizationName }));
						}
						setTimeout(function(){
							if (data.length === 1){
								select.val(data[0].id).change();
								
							}
						}, 50);	
						select.val('').trigger('change.select2');
					} else {
						//TODO no orgs found
						
						
					}
					//$('#textLine').css({"height":"auto" ,"float":"left", "border-bottom": "1px solid #bbbbbb"});
				},
				error: function(){}
			});
		}
	},
	
	_loadSubjects: function(){
		var me = this;
		var url = '';
			 url = 'getContentAreasForReporting.htm';		
		if (me.filtersNeeded.subject){
			$.ajax({
				url: url,
				dataType: 'json',
				data: {
					districtId: $('#districtSelect').val(),
					schoolId: $('#schoolSelect').val(),
					reportType: this.options.reportType,
					assessmentProg: this.options.assessmentProg,
					assessmentProgCode: this.options.assessmentProgCode,
					reportCode: $('td.currentReport').attr('id'),
					reportYear : $('#reportYearSelect').val()
				},
				type: 'GET',
				success: function(data){
					if (data !== undefined && data !== null && data.length > 0){
						var select = $('#subjectSelect', me.filterElement);
						for (var i = 0; i < data.length; i++){
							select.append($('<option>', { value: data[i].id, text: data[i].name }));
						}
						select.val('').trigger('change.select2');
					} else {
						//TODO no content areas found
					}
				},
				error: function(){}
			});
		}
	},
	_loadGrades: function(){		
		var me = this;
		var url = '';
			 url = 'getGradesForReporting.htm';		
		if (me.filtersNeeded.grade){
			var subjectIds = $('#subjectSelect').val();			
			if (typeof (subjectIds) !== 'object'){
				subjectIds = [subjectIds];
			}
			$.ajax({
				url: url,
				dataType: 'json',
				data: {
					districtId: $('#districtSelect').val(),
					schoolId: $('#schoolSelect').val(),
					subjectIds: subjectIds,
					reportType: this.options.reportType,
					assessmentProg: this.options.assessmentProg,
					reportCode: $('td.currentReport').attr('id'),
					reportYear : $('#reportYearSelect').val(),
					assessmentProgCode: this.options.assessmentProgCode
				},
				type: 'GET',
				success: function(data){
					if (data !== undefined && data !== null && data.length > 0){
						var select = $('#gradeSelect', me.filterElement);
						for (var i = 0; i < data.length; i++){
							select.append($('<option>', { value: data[i].id, text: data[i].name }));
						}
						select.val('').trigger('change.select2');
					} else {
						//TODO no grades found
						
					}
				},
				error: function(){}
			});
		}
	},
	_loadRosters: function(){
		var me = this;
		if (me.filtersNeeded.roster){
			$.ajax({
				url: 'getRostersForReporting.htm',
				dataType: 'json',
				data: {
					districtId: $('#districtSelect').val(),
					schoolId: $('#schoolSelect').val(),
					subjectId: $('#subjectSelect').val(),
					reportType: this.options.reportType
				},
				type: 'GET',
				success: function(data){
					if (data !== undefined && data !== null && data.length > 0){
						var select = $('#rosterSelect', me.filterElement);
						for (var i = 0; i < data.length; i++){
							select.append($('<option>', { value: data[i].id, text: data[i].courseSectionName }));
						}
						select.val('').trigger('change.select2');
					} else {
						//TODO no grades found						
						
					}
				},
				error: function(){}
			});
		}
	},
	_loadTeacherRosters: function(){
		var me = this;
		if (me.filtersNeeded.teacherRoster){
			$.ajax({
				url: 'getRosterForTeacherReports.htm',
				dataType: 'json',
				data: {
					reportType: this.options.reportType
				},
				type: 'GET',
				success: function(data){
					var select = $('#teacherRosterSelect', me.filterElement);
					if (data !== undefined && data !== null && data.length > 0){
						for (var i = 0; i < data.length; i++){
							select.append($('<option>', { value: data[i].id, text: data[i].courseSectionName + " (" +  data[i].schoolName + ")" }));
						}
					} else {
						select.append($('<option>', { value: '',text: 'No Rosters Available' }));
					}
					select.val('').trigger('change.select2');
				},
				error: function(){}
			});
		}
	},
	_loadStates: function(){
		var me = this;
		var url = '';
		
			url = 'getStatesByUserForAlternateReports.htm';
		
		if (me.filtersNeeded.state){
			$.ajax({
				url: url,
				dataType: 'json',
				data: {
					reportYear : $('#reportYearSelect').val(),
					reportType:this.options.reportType,
					assessmentProgId : this.options.assessmentProg,
					},
				type: 'GET',
				success: function(data){
					if (data !== undefined && data !== null && data.length > 0){
						var select = $('#stateSelect', me.filterElement);
						for (var i = 0; i < data.length; i++){
							select.append($('<option>', { value: data[i].id, text: data[i].organizationName }));
						}
						if (data.length === 1){
							select.val(data[0].id).change();
						}
						select.val('').trigger('change.select2');
					} else {
						//TODO no orgs found
						
					}
				},
				error: function(){}
			});
		}
	},
	_loadTeachers: function(){
		var me = this;
		var url = '';
		if(this.options.reportType == 'alternate_classroom'){
		  url = 'getTeachersForClassroomReportsByReportYear.htm';
		}else{
			 url = 'getTeachersForClassroomReports.htm';
		}
		var params = this.form.serializeArray();
		params.push({name: 'reportType', value: this.options.reportType});
		params.push({name: 'assessmentProgram', value: this.options.assessmentProg});
		params.push({name: 'reportYear', value: $('#reportYearSelect').val()});
		
		
		$('#reportContent', this.element).empty();
		if (me.filtersNeeded.teacher){
			$.ajax({
				url: url,
				dataType: 'json',
				data: params,
				type: 'GET',
				success: function(data){
					var select = $('#teacherSelect', me.filterElement);
					if (data !== undefined && data !== null && data.length > 0){
						for (var i = 0; i < data.length; i++){
							
							select.append($('<option>', { value: data[i].teacherId, text: data[i].teacherName}));
						}
					} else {
						select.append($('<option>', { value: '',text: 'No Teachers Available' }));
					}
					select.val('').trigger('change.select2');
				},
				error: function(){}
			});
		}
	},
	_getFilterAt: function(index){
		return this._getFiltersPast(index - 1, 1); // get the first filter before our index, nice little helper
	},
	_getFiltersPast: function(index, firstAmount){
		var filters = $('select', this.filterElement).filter(function(i, element){
			var elementIndex = $(element).data('filter-index');
			return (!isNaN(elementIndex)) && elementIndex > index;
		});
		if (firstAmount && !isNaN(firstAmount) && firstAmount > -1){
			filters = filters.slice(0, firstAmount);
		}
		return filters;
	},
	_resetFiltersPast: function(index){
		var me = this;
		var filtersToReset = this._getFiltersPast(index);
		filtersToReset.each(function(i, element){
			var $element = $(element);
			$('option', $element).slice(1).remove();
			var type = $element.data('data-type');
			$('#' + type + 'Text', me.filterElement).text('');
			$element.val('').trigger('change.select2');
		});
	},
	_completeFilterAt: function(index){
		var filter = this._getFilterAt(index);
		filter.closest('.criteria').removeClass('enabled').addClass('complete');
		$('#textLine').css({"height":"auto" ,"float":"left", "border-bottom": "1px solid #bbbbbb"});
	},
	_disableFiltersPast: function(index){
		var filtersToDisable = this._getFiltersPast(index);
		filtersToDisable.each(function(i, element){
			var $element = $(element);
			$element.closest('.criteria').removeClass('enabled complete');
			$element.prop('disabled', true);
		});
	},
	_enableNextFilterPast: function(index){
		var one = this.filterCount;
		if (index == (this.filterCount - 1)){ // last filter
			$('#resetButton', this.filterElement).show();
			if (this.form && this.form.valid()){
				
				var timeout = 0;
				this.pagination.element.jqPagination('option', 'current_page', 1);
				this._countReports();
				this._getReports(1);
			}
		} else {
			
			var nextFilter = this._getFilterAt(index + 1);
			if (nextFilter.length > 0){
				nextFilter.closest('.criteria').removeClass('complete').addClass('enabled');
				nextFilter.prop('disabled', false);
			}
		}
	},
	_countReports: function(){
		var me = this;
		var params = this.form.serializeArray();
		params.push({name: 'reportType', value: this.options.reportType});
		params.push({name: 'assessmentProgram', value: this.options.assessmentProg});
		params.push({name: 'assessmentProgCode', value: this.options.assessmentProgCode});
		params.push({name: 'reportCode', value : $('td.currentReport').attr('id')});
		params.push({name: 'reportYear', value : $('#reportYearSelect').val()});
		var url = this._getCountURL();
		
		if (url !== ''){
			$.ajax({
				url: url,
				dataType: 'json',	
				data: params,
				type: 'GET',
				success: function(count){
					me._refreshMaxPages(count);
					$('.pagination', this.element).css('display', '');
				},
				error: function(){}
			});
		}
	},
	_getReports: function(page){
		var me = this;
		var params = this.form.serializeArray();
		var reportType = this.options.reportType;
		params.push({name: 'reportType', value: this.options.reportType});
		params.push({name: 'page', value: page});
		params.push({name: 'perPage', value: me.pagination.itemsPerPage});
		params.push({name: 'assessmentProgram', value: this.options.assessmentProg});
		params.push({name: 'assessmentProgCode', value: this.options.assessmentProgCode});
		params.push({name: 'reportCode', value : $('td.currentReport').attr('id')});
		params.push({name: 'reportYear', value : $('#reportYearSelect').val()});
		/*params.push({name: 'assessmentProgCode', value: this.options.assessmentProgCode});*/
		$('#reportContent', this.element).empty();
		
		var url = this._getPageURL();
		var assessmentProgCode = this.options.assessmentProgCode;
		if (url !== ''){
			var me = this;
			$('.multiselect', me.filterElement).prop('disabled', true);
			$('#loadingImage', me.element).show();
			$.ajax({
				url: url,
				dataType: 'json',
				data: params,
				type: 'GET',
				complete: function(){
					$('.multiselect', me.filterElement).prop('disabled', false);
					$('#loadingImage', me.element).hide();
				},
				success: function(data){
					var reportsToPrepareRegardless = [
						'alternate_roster',
						'alternate_student',
						'general_student_writing',
						'alternate_monitoring_summary',
						'alternate_blueprint_coverage',
						'alternate_student_individual_teacher',
						'alternate_student_summary_teacher'
					];
					if ((data != null && data.length > 0) || $.inArray(reportType, reportsToPrepareRegardless) > -1){
						var paramsToPass = {};
						$.each(params, function(i, v){
							
							if (paramsToPass[v.name] != null){
								// see if we're dealing with an array
								if (typeof paramsToPass[v.name] === 'object'){
									var newValue = [];
									for (var x = 0; x < paramsToPass[v.name].length; x++){
										newValue.push(paramsToPass[v.name][x]);
									}
									newValue.push(v.value);
									paramsToPass[v.name] = newValue;
								} else {
									paramsToPass[v.name] = [paramsToPass[v.name], v.value];
								}
							} else {
								paramsToPass[v.name] = v.value;
							}
						});
						var assName="assName";
						data[assName]={};
						data[assName]=assessmentProgCode;
						me._prepareContent(data, paramsToPass);
					} else {
						me._prepareContent(data, paramsToPass);
						
					}
					//$('#textLine').css({"border-bottom": "1px solid #bbbbbb"});
					//$('#textLine').css({"height":"auto" ,"float":"left", "border-bottom": "1px solid #bbbbbb"});
				},
				error: function(){}
			});
		}
	},
	// If no counts are needed, just return '',
	// that will prevent the pagination controls from being displayed as well, since there is no count.
	// Student reports may be the only ones that need this. We'll see.
	_getCountURL: function(){
		var url = '';
		switch (this.options.reportType){
			case 'general_student':
				return 'countStudentReports.htm';
			case 'alternate_student_individual':
				return 'countExternalStudentReports.htm';
			case 'kelpa_student_individual':
				return 'countStudentReports.htm';			
			case 'alternate_student_individual_teacher':
				return 'countExternalStudentReportsForTeacherRoster.htm';				
			case 'general_school_detail':
				return 'countSchoolDetailReports.htm';
			case 'general_district_detail':
				return 'countDistrictDetailReports.htm';
			case 'alternate_student_summary':
				return 'countExternalStudentSummaryReports.htm';
			case 'alternate_student_dcps':
				return 'countExternalStudentSummaryReports.htm';
			case 'alternate_student_summary_teacher':
				return 'countOfStudentSummaryReportsForTeacherRoster.htm';	
				
			default:
				return '';
		}
	},
	_getPageURL: function(){
		var url = '';
		switch (this.options.reportType){
			case 'general_student':
				return 'getStudentReports.htm';
			case 'general_student_writing':
				return 'getStudentsWithWritingResponses.htm';
			case 'alternate_student_individual':
				return 'getExternalStudentReports.htm';
			case 'kelpa_student_individual':
				return 'getStudentReports.htm';
			case 'alternate_student_individual_teacher':
				return 'getExternalStudentReportsForTeacherRoster.htm';				
			case 'general_student_all':
				return 'getAllStudentsReports.htm';
			case 'alternate_student_all':
				return 'getAllStudentsReports.htm';
			case 'kelpa_student_all':
				return 'getAllStudentsReports.htm';				
			case 'general_school_detail':
				return 'getSchoolDetailReports.htm';
			case 'general_school_summary':
				return 'getSchoolSummaryReports.htm';
			case 'general_district_detail':
				return 'getDistrictDetailReports.htm';
			case 'alternate_student':
				return 'getDLMStudentsForStudentReports.htm';
			case 'alternate_roster':
				return 'getDLMStudentsForRosterReports.htm';
			case 'general_district_summary':
				return 'getDistrictSummaryReports.htm';
			case 'alternate_monitoring_summary':
				return 'getAlternateMonitoringSummary.htm';
			case 'alternate_blueprint_coverage':
				return 'getAlternateBlueprintCoverageTeachers.htm';
			case 'kelpa_elp_student_score':
				return 'getELPAStudnetsScoreFileReports.htm';
			case 'alternate_yearend_district_summary':
				return 'getAlternateYearEndDistrictSummary.htm';
			case 'alternate_yearend_state_summary':
				return 'getAlternateYearEndStateSummary.htm';
			case 'alternate_student_summary_all':
				return 'getAllStudentSummaryBundledReports.htm';
			case 'alternate_school_summary_all':
				return 'getAllSchoolSummaryBundledReports.htm';
			case 'alternate_student_summary':
				return 'getExternalStudentSummaryReports.htm';
			case 'alternate_student_summary_teacher':
				return 'getStudentSummaryReportsForTeacherRoster.htm';
			case 'alternate_school_summary':
				return 'getAllSchoolSummaryReports.htm';
			case 'alternate_classroom':
				return 'getAllClassroomReports.htm';
			case 'alternate_student_dcps':
			return 'getExternalStudentSummaryReports.htm';
			default:
				return '';
		}
	},
	_prepareContent: function(data, paramsFromRequest){
		var reportContent = $('#reportContent', this.element);
		if (this.options.reportType === 'general_student' ||this.options.reportType === 'kelpa_student_individual'){
			reportContent.html(can.view('js/views/new_reports/generalStudentReport.ejs', {data: data}));
		} else if (this.options.reportType === 'alternate_student_individual' || this.options.reportType === 'alternate_student_individual_teacher'){
			reportContent.html(can.view('js/views/new_reports/alternateExternalStudentReport.ejs', {data: data, reportType: this.options.reportType,programCode: this.options.assessmentProgCode,assessmentProgid : this.options.assessmentProg}));
		} else if (this.options.reportType === 'general_student_all' || this.options.reportType === 'alternate_student_all' ||this.options.reportType === 'kelpa_student_all'){      			
			// alterante  budled report
			reportContent.html(can.view('js/views/new_reports/generalAllStudentReports.ejs', {data: data, programCode: this.options.assessmentProgCode,assessmentProgid : this.options.assessmentProg}));
			
			if(ref != 'refreshed'){
				  ref = window.setTimeout(function(){$('#districtSelect').trigger("change");}, 5000);
			}
			
			$('#districtSeparateSelect').prop('disabled', true);  
			$('#districtSeparateSelectDiv').addClass('separateSelectDiv');
			$('#districtSeparateSelect').css({"cursor":"text"});
		    
			$('#schoolSeparateSelect').prop('disabled', true);
			$('#schoolSeparateSelectDiv').addClass('separateSelectDiv');
			 $('#schoolSeparateSelect').css({"cursor":"text"});
			var organizationId = null;
			var schoolLevel = $('#schoolSelect option:selected').val();
			if(schoolLevel == 0){
				organizationId = $('#districtSelect option:selected').val();
				$('#studentReportSchool').show();
			}else {
				organizationId = schoolLevel;
				$('#studentReportSchool').hide();
			}
			//Get information about pdf reports
			var assessmentProgId = this.options.assessmentProg;
			clearTimeout(ref);
			ref ='refreshed';
			if (data != null && data.length > 0) {
				loadPdfInformation(data[0].assessmentProgramId, organizationId);
			
			 $('.allStudentsLinks').click(function () {				
				 var currentSelectRecord = $(this).attr('pdfRecoreId');	  
				 var reportyear = $('#reportYearSelect').val();
				 var programCode = $(this).attr('programCode');	  
				 var assessmentProgramId = $(this).attr('assessmentProgramId');					
					$.ajax({
			    		url: 'isFilePresent.htm',
			    		dataType: 'json',
			    		data: {
			    			id : currentSelectRecord,
			    			organizationId : organizationId,
			    			assessmentProgramId : assessmentProgId,
		        			reportCode : $('td.currentReport').attr('id')
			    			},
			    			type: 'GET',
			    			success: function(data){
			    				
			    				if(data){
			    					//get presigned url for downloading report
			    					$.ajax({
									url: 'getAllStudentsReportFile.htm',
									data:{
										id: currentSelectRecord,
										reportYear: reportyear,
										assessmentProgCode: programCode,
										assessmentProgramId: assessmentProgramId
									},
									type: 'GET',
									success: function (s3response) {
										var downloadurl = s3response.downloadurl;
										var link=document.createElement('a');
										document.body.appendChild(link);
										link.href=downloadurl;
										link.click();
									}
			    					});
			    				}else {					
			    					
			    					var dialog = $('#studentBundledErrorLinksErrDialog').dialog({
			        					resizable: false,
			        					width: 480,
			        					modal: true,
			        					autoOpen: true,
			        					title: 'Bundled Report',
			        					create: function(event, ui){
			        						var widget = $(this).dialog("widget");
			        					},
			        				buttons: {			        					
			        					OK: function(){				        						
			        						if($('#schoolSelect').val() == 0){
			        							  $('#districtSelect').trigger("change");
			        							}else{
			        							  $('#schoolSelect').trigger("change");	
			        							}
			        						// close 
			        					$(this).dialog('close');
			        					
			        					}
			        				},
			    					});
			    				}			    				
			    			}
			    	});
					 
					
				});
			
			}
			// bcg 
			$('#sortFirstBy, #sortSecondBy, #sortLastBy').select2({
				placeholder:'Select',
				multiple: false
		}).find('option').filter(function(){return $(this).val() > 0;}).remove().end()
		.val('').trigger('change.select2');
		
			$( "input[name*='sortSecondBy']" ).each(function(){
				if($(this).val()=="grouping2"){
					$(this).parent().parent().hide();
				}
			});
			$( "input[name*='sortLastBy']" ).each(function(){
				if($(this).val()=="grouping2"){
					$(this).parent().parent().hide();
				}
			});
			
		$('#studentBundledReportSchool').val('').trigger('change.select2');	
		$('#studentBundledReportContentAreas').val('').trigger('change.select2');	
			
			
		} else if (this.options.reportType === 'general_school_detail'){
			reportContent.html(can.view('js/views/new_reports/generalSchoolDetails.ejs', {data: data,programCode: this.options.assessmentProgCode,assessmentProgid : this.options.assessmentProg}));
		} else if (this.options.reportType === 'general_school_summary'){
			reportContent.html(can.view('js/views/new_reports/generalSchoolSummary.ejs', {data: data}));
		} else if (this.options.reportType === 'general_district_detail'){
			reportContent.html(can.view('js/views/new_reports/generalDistrictDetails.ejs', {data: data}));
		} else if (this.options.reportType === 'general_district_summary'){
			reportContent.html(can.view('js/views/new_reports/generalDistrictSummary.ejs', {data: data}));
		} else if (this.options.reportType === 'general_student_writing'){
			reportContent.html(can.view('js/views/new_reports/writingReport.ejs', {data: data}));
			var writingStudents = $('#writingStudentDropDown');
			writingStudents.empty();
        	if (data !== undefined && data !== null && data.length > 0){
        		for (var i = 0; i < data.length; i++){
        			var optionText = data[i].legalFirstName + ' ' +  data[i].legalLastName + ' (ID:' + data[i].stateStudentIdentifier + ')';
        			writingStudents.append('<option value="' + data[i].studentId + '">' + data[i].legalFirstName + ' ' +  data[i].legalLastName + ' (ID:' + data[i].stateStudentIdentifier + ')</option>');
				}
				writingStudents.val('').trigger('change.select2').select2({placeholder: 'Select Multiple Students'});
				if (data.length === 1){
					writingStudents.val(data[0].id).val('').trigger('change.select2');
				}
        	} else {
				writingStudents.select2({placeholder: 'No students available'});
			}
		} else if (this.options.reportType === 'alternate_student'){
			reportContent.html(can.view('js/views/new_reports/alternateStudentReport.ejs', {data: data}));
			var altStudents = $('#altStudentDropDown');
			altStudents.empty();
        	if (data !== undefined && data !== null && data.length > 0) {
        		altStudents.append($('<option></option>').val('').html('Select student'));
        		for (var i = 0; i < data.length; i++){
        			altStudents.append("<option value='" + data[i].student.id + "' data-grade-name='"+data[i].gradeCourse.name+"'>" + data[i].student.legalFirstName + " " +  data[i].student.legalLastName + " (ID:" + data[i].student.stateStudentIdentifier + ")  " + "</option>");
				}
				if (data.length === 1){
					altStudents.val(data[0].id).change();
				}
        	}
        	
			else {
				altStudents.append($('<option></option>').val('').html('No students available'));
			}
		} else if (this.options.reportType === 'alternate_roster'){
			reportContent.html(can.view('js/views/new_reports/alternateClassRoster.ejs', {data: data}));
			var student = $('#studentRoster');
			student.empty();
        	if (data !== undefined && data !== null && data.length > 0) {
        		for (var i = 0; i < data.length; i++){
        			student.append("<option value='" + data[i].id + "'>" + data[i].legalFirstName + " " +  data[i].legalLastName + " (ID:" + data[i].stateStudentIdentifier + ")  " + "</option>");
				}
        		if (data.length != 1) {
        			student.prop('disabled', false);
				}
        		student.val('').trigger('change.select2');
        		student.select2({placeholder:'Select Multiple Students'});
        		
        	} 
			else {
				student.select2({placeholder:'No students to display'});
			}
		}else if (this.options.reportType === 'kelpa_elp_student_score'){
			$('#reportContent', this.element).html(can.view('js/views/new_reports/elpaStudentsScoreFileReport.ejs', {data: data,programCode: this.options.assessmentProgCode,assessmentProgid : this.options.assessmentProg}));
		}else if (this.options.reportType === 'alternate_monitoring_summary'){
			var params = {
				summaryLevel: $('#summaryLevelSelect').val(),
				data: data
			};
			reportContent.html(can.view('js/views/new_reports/alternateMonitoringSummary.ejs', params));
			$('#saveTestAdminMonitoringSummaryPDF', this.element).off('click').unbind('click').click(function(){
				var location = 'getAlternateMonitoringSummaryPDF.htm?summaryLevel=' + encodeURIComponent(params.summaryLevel);
				
				var districtIds = $('#districtSelect').val();
				if (districtIds){
					for (var x = 0; x < districtIds.length; x++){
						location += '&districtId=' + encodeURIComponent(districtIds[x]);
					}
				}
				
				var schoolIds = $('#schoolSelect').val();
				if (schoolIds){
					for (var x = 0; x < schoolIds.length; x++){
						location += '&schoolId=' + encodeURIComponent(schoolIds[x]);
					}
				}
				
				window.location = location;
			});
		} else if (this.options.reportType === 'alternate_blueprint_coverage'){
			reportContent.html(can.view('js/views/new_reports/blueprintCoverage.ejs', {data: data, paramsFromRequest: paramsFromRequest}));
			var teacherSelect = $('#blueprintCoverageTeacherDropdown');
        	if (data !== undefined && data !== null && data.length > 0) {
				var me = this;
				var isTeacher = $('#isNewReportsTeacher').val() === 'true';
				if (isTeacher){
					$('#blueprintCoverageTeacherDropdownDiv, #viewBlueprintCoverageReportButtonsDiv').hide();
					var params = paramsFromRequest;
					params['edIds'] = [data[0].id];
					params['groupByTeacher'] = $('#blueprintCoverageGroupByTeacher').prop('checked');
					DLMBlueprintCoverageReport.findOne(params, function(report){
						me.report = report;
						if(me.currentControlInstance) {
							me.currentControlInstance.destroy();
						}
						me.currentControlInstance = me._renderBlueprintCoverageReport(params);
					});
				} else {
					$('#blueprintCoverageTeacherDropdownDiv, #viewBlueprintCoverageReportButtonsDiv').show();
					teacherSelect.val('').trigger('change.select2').select2({placeholder:'Select Teachers'});
					$('#viewBlueprintCoverage').off('click').click(function(){
						var checked = teacherSelect.select2("val");
						var objects = checked.length !== 0 ? checked : teacherSelect.select2("val");
						var val = objects.map(function(){ return this.value; }).get();
						
						var params = paramsFromRequest;
						params['edIds'] = val;
						params['groupByTeacher'] = $('#blueprintCoverageGroupByTeacher').prop('checked');
						DLMBlueprintCoverageReport.findOne(params, function(report){
							me.report = report;
							if(me.currentControlInstance) {
								me.currentControlInstance.destroy();
							}
							me.currentControlInstance = me._renderBlueprintCoverageReport(params);
						});
					});
				}
        	} else {
				teacherSelect.select2({placeholder:'No teachers available'});
			}
		}
		else if (this.options.reportType === 'alternate_yearend_district_summary'){
				reportContent.html(can.view('js/views/new_reports/alternateDistrictSummary.ejs', {data: data}));
		}
		else if (this.options.reportType === 'alternate_yearend_state_summary'){
				reportContent.html(can.view('js/views/new_reports/alternateStateSummary.ejs', {data: data}));
		}else if (this.options.reportType=='alternate_student_summary_all'){
			reportContent.html(can.view('js/views/new_reports/alternateStudentSummaryBundledReports.ejs', {data: data}));
			
			//alt student summary
			if(ref != 'refreshed'){
				  ref = window.setTimeout(function(){$('#districtSelect').trigger("change");}, 5000);
			}
			
			$('#districtSummarySeparateSelect').prop('disabled', true);  
			$('#districtSummarySeparateSelectDiv').addClass('separateSelectDiv');
			$('#districtSummarySeparateSelect').css({"cursor":"text"});
		    
			$('#schoolSummarySeparateSelect').prop('disabled', true);
			$('#schoolSummarySeparateSelectDiv').addClass('separateSelectDiv');
			 $('#schoolSummarySeparateSelect').css({"cursor":"text"});
			var organizationId = null;
			var schoolLevel = $('#schoolSelect option:selected').val();
			if(schoolLevel == 0){
				organizationId = $('#districtSelect option:selected').val();
				$('#studentReportSchool').show();
			}else {
				organizationId = schoolLevel;
				$('#studentReportSchool').hide();
			}
			//Get information about pdf reports
			var assessmentProgId = this.options.assessmentProg;
			clearTimeout(ref);
			ref ='refreshed';
			if (data != null && data.length > 0) {
				loadSummaryPdfInformation(data[0].assessmentProgramId, organizationId);
			
			 $('.allStudentsLinks').click(function () {						
				 var currentSelectRecord = $(this).attr('pdfRecoreId');	  
				 var reportyear = $('#reportYearSelect').val();
				 var programCode = $(this).attr('programCode');	  
				 var assessmentProgramId = $(this).attr('assessmentProgramId');		
				 
					$.ajax({
			    		url: 'isFilePresent.htm',
			    		dataType: 'json',
			    		data: {
			    			id : currentSelectRecord,
			    			organizationId : organizationId,
			    			assessmentProgramId : assessmentProgId,
		        			reportCode : $('td.currentReport').attr('id')
			    			},
			    			type: 'GET',
			    			success: function(data){
			    				
			    				if(data){
			    					//get presigned url for downloading report
			    					$.ajax({
									url: 'getAllStudentsReportFile.htm',
									data:{
										id: currentSelectRecord,
										reportYear: reportyear,
										assessmentProgCode: programCode,
										assessmentProgramId: assessmentProgramId
									},
									type: 'GET',
									success: function (s3response) {
										var downloadurl = s3response.downloadurl;
										var link = document.createElement('a');
										document.body.appendChild(link);
										link.href=downloadurl;
										link.click();
									}
			    					});
			    				}else {					
			    					
			    					var dialog = $('#studentBundledErrorLinksErrDialog').dialog({
			        					resizable: false,
			        					width: 480,
			        					modal: true,
			        					autoOpen: true,
			        					title: 'Bundled Report',
			        					create: function(event, ui){
			        						var widget = $(this).dialog("widget");
			        					},
			        				buttons: {			        					
			        					OK: function(){				        						
			        						if($('#schoolSelect').val() == 0){
			        							  $('#districtSelect').trigger("change");
			        							}else{
			        							  $('#schoolSelect').trigger("change");	
			        							}
			        						// close 
			        					$(this).dialog('close');
			        					
			        					}
			        				},
			    					});
			    				}			    				
			    			}
			    	});
					 
					
				});
			
			}
			// bcg 
			$('#summarySortFirstBy, #summarySortSecondBy, #summarySortLastBy').select2({
				placeholder:'Select',
				multiple: false
		}).find('option').filter(function(){return $(this).val() > 0;}).remove().end()
		.val('').trigger('change.select2');
		
			$( "input[name*='summarySortSecondBy']" ).each(function(){
				if($(this).val()=="grouping2"){
					$(this).parent().parent().hide();
				}
			});
			$( "input[name*='summarySortLastBy']" ).each(function(){
				if($(this).val()=="grouping2"){
					$(this).parent().parent().hide();
				}
			});
			
		$('#studentSummaryBundledReportSchool').val('').trigger('change.select2');	
			
		}else if (this.options.reportType==='alternate_school_summary_all'){			
			reportContent.html(can.view('js/views/new_reports/alternateSchoolSummaryBundledReports.ejs', {data: data}));			
			if(data!=null && data!=undefined && data.length > 0){
				var date = new Date(data[0].createdDate);
				var opts = $.extend({}, $.jgrid.formatter.date, opts);
				$('#submittedDate').empty().append($.fmatter.util.DateFormat("", date, 'm/d/Y h:i:s A', opts));
			}
			
		}else if (this.options.reportType === 'alternate_student_summary' || this.options.reportType === 'alternate_student_summary_teacher' || this.options.reportType === 'alternate_student_dcps'){
			reportContent.html(can.view('js/views/new_reports/alternateExternalStudentSummaryReport.ejs', {data: data, reportType: this.options.reportType,programCode: this.options.assessmentProgCode,assessmentProgid : this.options.assessmentProg}));
		}else if (this.options.reportType=='alternate_school_summary'){
			reportContent.html(can.view('js/views/new_reports/alternateSchoolSummaryReports.ejs', {data: data}));
		}else if (this.options.reportType=='alternate_classroom'){
			reportContent.html(can.view('js/views/new_reports/alternateClassroomReports.ejs', {data: data}));
		}
	},
	_setupPagination: function(){
		this.pagination = {itemsPerPage: 15};
		var me = this;
		this.pagination.element = $('.pagination', this.element);
		this.pagination.element.jqPagination({
			paged: function(page){
				me._getReports(page);
			}
		});
	},
	_refreshMaxPages: function(recordCount){
		var maxPages = Math.floor(recordCount / this.pagination.itemsPerPage);
		if (recordCount % this.pagination.itemsPerPage !== 0){
			maxPages++;
		}
		this.pagination.maxPages = maxPages;
		if(maxPages > 0)
			this.pagination.element.jqPagination('option', 'max_page', maxPages);
	},
	_isSummaryLevelFilterNeeded: function(reportType){
		var types = [
			'alternate_monitoring_summary'
		];
		return $.inArray(reportType, types) > -1;
	},
	////////////////////////////////////
	_isReportYearFilterNeeded: function(reportType){
		var types = [
		     'general_student',						//student(individual)
		     'general_student_all',					//student(bundled)
		     'general_school_summary',				//school summary
		     'general_district_summary',			//district summary
		     'alternate_yearend_state_summary',		//state aggregate
		     'alternate_yearend_district_summary',	//district aggregate
		     'alternate_school_summary',			//school aggregate
		     'alternate_classroom',					//class aggregate ??classroom
		     'alternate_student_individual',		//student(individual)
		     'alternate_student_all',				//students(bundled)
		     'kelpa_student_all',					//Kelpa Student(bundled)
		     'alternate_student_summary_all',		//student summmary(bundled)
		     'general_school_detail',				//school detail
		     'alternate_student_dcps',
		     'alternate_student_summary',
		     'alternate_school_summary_all',
		     'kelpa_student_individual'
		];
		return $.inArray(reportType, types) > -1;
	},
	_isDistrictFilterNeeded: function(reportType){
		var types = [
			'general_student',
			/*'general_roster',*/
			'general_student_all',
			'general_school_detail',
			'general_school_summary',
			'general_district_detail',
			'general_district_summary',
			'general_student_writing',
			'alternate_student',
			'alternate_roster',
			'alternate_student_individual', 
			'alternate_student_all',
			'kelpa_student_all',
			'alternate_monitoring_summary',
			'kelpa_elp_student_score',
			'alternate_blueprint_coverage',
			'alternate_yearend_district_summary',
			'alternate_student_summary_all',
			'alternate_school_summary_all',
			'alternate_student_summary',
			'alternate_school_summary',
			'alternate_classroom',
			 'alternate_student_dcps',
			 'kelpa_student_individual'
		];
		return $.inArray(reportType, types) > -1;
	},
	_isStateFilterNeeded: function(reportType){
		var types = [			
			'alternate_yearend_state_summary'
		];
		return $.inArray(reportType, types) > -1;
	},
	_isSchoolFilterNeeded: function(reportType){
		var types = [
 			'general_student',
 			/*'general_roster',*/
 			'general_student_all',
 			'general_school_detail',
 			'general_school_summary',
 			'general_student_writing',
 			'alternate_student',
 			'alternate_roster',
 			'alternate_student_individual', 
 			'alternate_student_all',
 			'kelpa_student_all',
 			'alternate_monitoring_summary',
 			'alternate_blueprint_coverage',
 			'alternate_student_summary_all',
 			'alternate_student_summary',
 			'alternate_school_summary',
 			'alternate_classroom',
 			 'alternate_student_dcps',
 			 'kelpa_student_individual'
 		];
		return $.inArray(reportType, types) > -1;
	},
	_isSubjectFilterNeeded: function(reportType){
		var types = [
  			'general_student',
  			/*'general_roster',*/
  			/*'general_school_detail',*/
  			/*'general_district_detail',*/
  			'alternate_student',
  			'alternate_roster',
  			'alternate_student_individual',
  			'alternate_blueprint_coverage'
  			
  		];
		return $.inArray(reportType, types) > -1;
	},
	_isGradeFilterNeeded: function(reportType){
		var types = [
			'general_student',
			'general_student_writing',
			'alternate_student_individual',
			'alternate_blueprint_coverage',
			'alternate_student_summary',
			 'alternate_student_dcps',
			 'kelpa_student_individual'
			/*,'general_roster',*/
			/*'general_school_detail',*/
			/*'general_district_detail',*/
		];
		return $.inArray(reportType, types) > -1;
	},
	_isRosterFilterNeeded: function(reportType){
		var types = [
			/*'general_roster',*/
			'alternate_roster'
		];
		return $.inArray(reportType, types) > -1;
	},
	_isTeacherRosterFilterNeeded: function(reportType){
		var types = [
		    'alternate_student_individual_teacher',
		    'alternate_student_summary_teacher'
		];
		return $.inArray(reportType, types) > -1;
	},
	_isTeacherFilterNeeded: function(reportType){
		var types = [
		    'alternate_classroom'
		];
		return $.inArray(reportType, types) > -1;
	},
	
	'#submitBundledReport click': function(element, event){
		
		var selectSchooltext=[];
		var selectSchoolNames=[];
		var schoolId = $('#schoolSelect option:selected').val();
		
        $( "input[id*='studentBundledReportSchool']" ).each(function(){
       	 if($(this). prop("checked") == true){
       		 selectSchooltext.push($(this).attr("value"));
       		selectSchoolNames.push($(this).attr("title"));
       	 }
       	});
        
      	if(selectSchoolNames != '' && totalSchoolLength == selectSchooltext.length)
      		{
      			selectSchoolNames = [];
      			selectSchoolNames.push("All");
      		}
      	
      	var selectSubjecttext=[];
        var selectSubjectNames=[];
        $( "input[id*='studentBundledReportContentAreas']" ).each(function(){
       	 if($(this). prop("checked") == true){
       		selectSubjecttext.push($(this).attr("value"));
       		selectSubjectNames.push($(this).attr("title"));
       	 }
           });
      
        if(totalSubjectLength == selectSubjecttext.length){
        	selectSubjectNames=[];
        	selectSubjectNames.push("All");
      	}
        
        var selectGradetext=[];
        var selectGradeNames=[];
        $( "input[id*='studentBundledReportGrades']" ).each(function(){
       	 if($(this). prop("checked") == true){
       		selectGradetext.push($(this).attr("value"));
       		selectGradeNames.push($(this).attr("title"));
       	 }
        });
      if(totalGradesLength == selectGradetext.length){
    	  selectGradeNames=[];
    	  selectGradeNames.push("All");
      }
      
    if(schoolId == '0' && selectSchooltext == '')
 	   {
 	   $('#emptyStudentbundlereportform').html("<span>Select School</span>");
			setTimeout(function(){ $("#emptyStudentbundlereportform").html("&nbsp;"); },4000);
			return;
 	   }
    if(selectSubjecttext =='')
 	   {
 	   $('#emptyStudentBundledReportContentAreas').html("<span>Select Subject</span>");
 	   setTimeout(function(){ $("#emptyStudentBundledReportContentAreas").html("&nbsp;"); },4000);
 	   return;
 	   }
    if(selectGradetext =='')
 	   {
    	
 	   $('#emptyStudentBundledReportGrades').html("<span>Select Grade</span>");
 	   setTimeout(function(){ $("#emptyStudentBundledReportGrades").html("&nbsp;"); },4000);
 	   return;
 	   }
        
    //sort option
    	var selectFirstSort= $('#sortFirstBy option:selected').val(); 
    	var selectSecondSort= $('#sortSecondBy option:selected').val();
    	var selectLastSort= $('#sortLastBy option:selected').val();
    
  
    	if(selectFirstSort == "0")
    		{
			   $('#emptySortFirstBy').html("<span>Select First Sort</span>");
			   setTimeout(function(){ $("#emptySortFirstBy").html("&nbsp;"); },4000);
			   return;
    		}
    	if(selectSecondSort =="0" && selectFirstSort!="legallastname")
    		{
			  $('#emptySortSecondBy').html("<span>Select Second Sort</span>");
			   setTimeout(function(){ $("#emptySortSecondBy").html("&nbsp;"); },4000);
			   return;
    		}
    	if(selectLastSort =="0" && (selectSecondSort!="legallastname" && selectFirstSort!="legallastname"))
    		{
			  $('#emptySortLastBy').html("<span>Select Last Sort</span>");
			  setTimeout(function(){ $("#emptySortLastBy").html("&nbsp;"); },4000);
			  return;
			 }  
        
       
   //district combobox option select
    	var  districtBundledSelect =  $('#districtBundledSelect').prop('checked');
    	var districtSeparateSelect = $('#districtSeparateSelect').prop('checked');
    	var schoolBundledSelect = $('#schoolBundledSelect').prop('checked');
    	var schoolSeparateSelect = $('#schoolSeparateSelect').prop('checked');
    	var separateBundledReportSelect = $('#separateBundledReportSelect').prop('checked');
    	
    	var organizationId  = '';
    	var districtId = $('#districtSelect option:selected').val();
    	
    	var assessmentProgId = this.options.assessmentProg;
    	 
    	if(schoolId == 0){
    		 organizationId = districtId;
    	} else {
    		 organizationId = schoolId;
    		 
    	}   
    	
    	if(schoolId == 0 && !districtBundledSelect && !schoolBundledSelect){
    		
    		 $('#studentBundledSelectError').html("<span>Select options for District/School bundled.</span>");
			  setTimeout(function(){ $("#studentBundledSelectError").html("&nbsp;"); },4000);
			  return;
    	 } 	
    	 var separateFile = false;
    	 if(districtSeparateSelect || separateBundledReportSelect || schoolSeparateSelect){
    		 separateFile = true;
    	 }
    	 if(schoolBundledSelect && selectSchooltext.length > 1){
    		 separateFile = true; 
    	 }
    	 if(schoolBundledSelect && districtBundledSelect){
    		 separateFile = true;
    	 }
    	 $('#submitBundledReport').prop('disabled', true);
    	//showing popup        
        	$.ajax({
        		url: 'getEstimatedFileSizeForSelectedFilters.htm',
        		dataType: 'json',
        		data: {
        			organizationId : organizationId,
        			assessmentProgramId : assessmentProgId,
        			schoolIds : selectSchooltext,
        			subjectIds : selectSubjecttext,			
        			gradeIds : selectGradetext,
        			separateFile : separateFile,
        			reportCode : $('td.currentReport').attr('id')
        			},
        			type: 'GET',
        			success: function(data){
        				data.totalSize = data.totalSize/1024;//Size will be converted from kb to mb
        				if(data.totalSize > 100){             
        		             $('#bundledReportSize').empty().append('<p>Note - this file will be very large. A rough estimate is '+ Math.round(parseFloat(data.totalSize)) +' MB</p><p>');
        		            }else{
        		             $('#bundledReportSize').empty();
        		            }
        		            if(data.inProgress){
        		             $('#bundledReportStatus').empty().append('<p>Already one more request is in progress for same organization.</p>');
        		            }else{
        		             $('#bundledReportStatus').empty();
        		            }
        		            
        				var byOrganization = false;
        				if(schoolId != 0 || districtBundledSelect){
        					byOrganization = true;
        				}
        				
        				var separateFile = false;
        				if(districtSeparateSelect || separateBundledReportSelect){
        					separateFile = true;
        				}
        				
        				if(byOrganization && schoolBundledSelect){
        					$('#popupText').empty().append('district and school(s)');
        				}else if(schoolId != 0){
        					$('#popupText').empty().append('school');
        				}else if(schoolId == 0){
        					$('#popupText').empty().append('district');
        				}        				
        				
        				$('#submitBundledReport').prop('disabled', false);
        				// popup         				
        				var dialog = $('#submitBundledReportDialog').dialog({
        					resizable: false,
        					width: 585,
        					modal: true,
        					autoOpen: true,
        					title: 'Create new bundled report(s)',
        					create: function(event, ui){
        						$('#submitBundledReport').prop('disabled', false);
        						var widget = $(this).dialog("widget");
        					},
        				buttons: {
        					Cancel: function(){
        						$('#submitBundledReport').prop('disabled', false);
        						$(this).dialog('close');
        					
        					},
        					Continue: function(){
        						$.ajax({
        			        		url: 'requestForDynamicBundle.htm',
        			        		dataType: 'json',
        			        		data: {
        			        			organizationId : organizationId,
        			        			assessmentProgramId : assessmentProgId,
        			        			schoolIds : selectSchooltext,
        			        			subjectIds : selectSubjecttext,			
        			        			gradeIds : selectGradetext,
        			        			schoolNames : selectSchoolNames,
        			        			subjectNames : selectSubjectNames,
        			        			gradeNames : selectGradeNames,
        			        			sort1 : selectFirstSort,
        			        			sort2 : selectSecondSort,
        			        			sort3 : selectLastSort,
        			        			byOrganization : byOrganization,
        			        			separateFile : separateFile,
        			        			bySchool : schoolBundledSelect,
        			        			separateFileForSchool : schoolSeparateSelect,
        			        			reportCode : $('td.currentReport').attr('id')
        			        			},
        			        			type: 'POST',
        			        			success: function(data){
        			        				$('#submitBundledReport').prop('disabled', false);
        			        				clearTimeout(ref);
        			        				loadPdfInformation(assessmentProgId, organizationId);
        			        				// scrollTop       			        					        				
        			        				$(".allStudents").animate({ scrollTop: 0}, "fast");
        			        				
        			        			}
        			        			});
        						
        						$('#bundledContentAreaLastName').hide();
        						$('#bundledGradeLastName').hide();
        						
        						// close 
        					$(this).dialog('close');
        					// refresh select option
	        					$('#studentBundledReportSchool').val('').trigger('change.select2');
	        					$('#studentBundledReportContentAreas').val('').trigger('change.select2');
	        					$('#studentBundledReportGrades').val('').trigger('change.select2');
	        					
	        					if(totalSchoolLength !=1){
	        			             $('#studentBundledReportSchool').val('').trigger('change.select2');
	        			          } 
	        			        if(totalSubjectLength !=1){
	        			             $('#studentBundledReportContentAreas').val('').trigger('change.select2');
	        			          } 
	        			        if(totalGradesLength !=1 )
	        			          {
	        			             $('#studentBundledReportGrades').val('').trigger('change.select2');
	        			          }
	        					
	        					
	        					// refresh sort
	        					$('#sortFirstBy').val('').trigger('change.select2');
	        					$('#sortFirstBy').prop('disabled', false);
	        					$('#sortSecondBy').val('').trigger('change.select2');
	        					$('#sortSecondBy').prop('disabled', false);
	        					$('#sortLastBy').val('').trigger('change.select2');
	        					$('#sortLastBy').prop('disabled', false);
	        					 
	        					// refresh checkbox
	        					$('#districtBundledSelect').attr('checked', false); 
	        					$('#districtSeparateSelect').attr('checked', false); 
	        					$('#schoolBundledSelect').attr('checked', false); 
	        					$('#schoolSeparateSelect').attr('checked', false); 
	        					$('#separateBundledReportSelect').attr('checked', false);
	        					$('#submitBundledReport').prop('disabled', false);
        					}
        				},
        				});
        				},
        		error: function(){}
        		});
        
	},
	
	'#submitSummaryBundledReport click': function(element, event){
		
		var selectSchooltext=[];
		var selectSchoolNames=[];
		var schoolId = $('#schoolSelect option:selected').val();
		
        $( "input[id*='studentSummaryBundledReportSchool']" ).each(function(){
       	 if($(this). prop("checked") == true){
       		 selectSchooltext.push($(this).attr("value"));
       		selectSchoolNames.push($(this).attr("title"));
       	 }
       	});
        
      	if(selectSchoolNames != '' && totalSchoolLength == selectSchooltext.length)
      		{
      			selectSchoolNames = [];
      			selectSchoolNames.push("All");
      		}
        
        var selectGradetext=[];
        var selectGradeNames=[];
        $( "input[id*='studentSummaryBundledReportGrades']" ).each(function(){
       	 if($(this). prop("checked") == true){
       		selectGradetext.push($(this).attr("value"));
       		selectGradeNames.push($(this).attr("title"));
       	 }
        });
      if(totalGradesLength == selectGradetext.length){
    	  selectGradeNames=[];
    	  selectGradeNames.push("All");
      }
      
    if(schoolId == '0' && selectSchooltext == '')
 	   {
 	   $('#emptyStudentSummarybundlereportform').html("<span>Select School</span>");
			setTimeout(function(){ $("#emptyStudentSummarybundlereportform").html("&nbsp;"); },4000);
			return;
 	   }
    if(selectGradetext =='')
 	   {
 	   $('#emptyStudentSummaryBundledReportGrades').html("<span>Select Grade</span>");
 	   setTimeout(function(){ $("#emptyStudentSummaryBundledReportGrades").html("&nbsp;"); },4000);
 	   return;
 	   }
        
    //sort option
    	var selectFirstSort= $('#summarySortFirstBy option:selected').val(); 
    	var selectSecondSort= $('#summarySortSecondBy option:selected').val();
    	var selectLastSort= $('#summarySortLastBy option:selected').val();
    
  
    	if(selectFirstSort == "0")
    		{
			   $('#summaryEmptySortFirstBy').html("<span>Select First Sort</span>");
			   setTimeout(function(){ $("#summaryEmptySortFirstBy").html("&nbsp;"); },4000);
			   return;
    		}
    	if(selectSecondSort =="0" && selectFirstSort!="legallastname")
    		{
			  $('#summaryEmptySortSecondBy').html("<span>Select Second Sort</span>");
			   setTimeout(function(){ $("#summaryEmptySortSecondBy").html("&nbsp;"); },4000);
			   return;
    		}
    	if(selectLastSort =="0" && (selectSecondSort!="legallastname" && selectFirstSort!="legallastname"))
    		{
			  $('#summaryEmptySortLastBy').html("<span>Select Last Sort</span>");
			  setTimeout(function(){ $("#summaryEmptySortLastBy").html("&nbsp;"); },4000);
			  return;
			 }  
        
       
   //district combobox option select
    	var  districtBundledSelect =  $('#districtSummaryBundledSelect').prop('checked');
    	var districtSeparateSelect = $('#districtSummarySeparateSelect').prop('checked');
    	var schoolBundledSelect = $('#schoolSummaryBundledSelect').prop('checked');
    	var schoolSeparateSelect = $('#schoolSummarySeparateSelect').prop('checked');
    	var separateBundledReportSelect = $('#summarySeparateBundledReportSelect').prop('checked');
    	
    	var organizationId  = '';
    	var districtId = $('#districtSelect option:selected').val();
    	
    	var assessmentProgId = this.options.assessmentProg;
    	 
    	if(schoolId == 0){
    		 organizationId = districtId;
    	} else {
    		 organizationId = schoolId;
    		 
    	}   
    	
    	if(schoolId == 0 && !districtBundledSelect && !schoolBundledSelect){
    		
    		 $('#studentSummaryBundledSelectError').html("<span>Select options for District/School bundled.</span>");
			  setTimeout(function(){ $("#studentSummaryBundledSelectError").html("&nbsp;"); },4000);
			  return;
    	 } 	
    	 var separateFile = false;
    	 if(districtSeparateSelect || separateBundledReportSelect || schoolSeparateSelect){
    		 separateFile = true;
    	 }
    	 if(schoolBundledSelect && selectSchooltext.length > 1){
    		 separateFile = true; 
    	 }
    	 if(schoolBundledSelect && districtBundledSelect){
    		 separateFile = true;
    	 }
    	 $('#submitBundledReport').prop('disabled', true);
    	//showing popup        
        	$.ajax({
        		url: 'getEstimatedSummaryFileSizeForSelectedFilters.htm',
        		dataType: 'json',
        		data: {
        			organizationId : organizationId,
        			assessmentProgramId : assessmentProgId,
        			schoolIds : selectSchooltext,		
        			gradeIds : selectGradetext,
        			separateFile : separateFile
        			},
        			type: 'GET',
        			success: function(data){
        				data.totalSize = data.totalSize/1024;//Size will be converted from kb to mb
        				if(data.totalSize > 100){             
        		             $('#summaryBundledReportSize').empty().append('<p>Note - this file will be very large. A rough estimate is '+ Math.round(parseFloat(data.totalSize)) +' MB</p><p>');
        		            }else{
        		             $('#summaryBundledReportSize').empty();
        		            }
        		            if(data.inProgress){
        		             $('#summaryBundledReportStatus').empty().append('<p>Already one more request is in progress for same organization.</p>');
        		            }else{
        		             $('#summaryBundledReportStatus').empty();
        		            }
        		            
        				var byOrganization = false;
        				if(schoolId != 0 || districtBundledSelect){
        					byOrganization = true;
        				}
        				
        				var separateFile = false;
        				if(districtSeparateSelect || separateBundledReportSelect){
        					separateFile = true;
        				}
        				
        				if(byOrganization && schoolBundledSelect){
        					$('#summaryPopupText').empty().append('district and school(s)');
        				}else if(schoolId != 0){
        					$('#summaryPopupText').empty().append('school');
        				}else if(schoolId == 0){
        					$('#summaryPopupText').empty().append('district');
        				}        				
        				
        				$('#submitSummaryBundledReport').prop('disabled', false);
        				// popup         				
        				var dialog = $('#submitSummaryBundledReportDialog').dialog({
        					resizable: false,
        					width: 585,
        					modal: true,
        					autoOpen: true,
        					title: 'Create new bundled report(s)',
        					create: function(event, ui){
        						$('#submitSummaryBundledReport').prop('disabled', false);
        						var widget = $(this).dialog("widget");
        					},
        				buttons: {
        					Cancel: function(){
        						$('#submitSummaryBundledReport').prop('disabled', false);
        						$(this).dialog('close');
        					
        					},
        					Continue: function(){
        						$.ajax({
        			        		url: 'requestForDynamicStudentSummaryBundle.htm',
        			        		dataType: 'json',
        			        		data: {
        			        			organizationId : organizationId,
        			        			assessmentProgramId : assessmentProgId,
        			        			schoolIds : selectSchooltext,		
        			        			gradeIds : selectGradetext,
        			        			schoolNames : selectSchoolNames,
        			        			gradeNames : selectGradeNames,
        			        			sort1 : selectFirstSort,
        			        			sort2 : selectSecondSort,
        			        			sort3 : selectLastSort,
        			        			byOrganization : byOrganization,
        			        			separateFile : separateFile,
        			        			bySchool : schoolBundledSelect,
        			        			separateFileForSchool : schoolSeparateSelect        			        			
        			        			},
        			        			type: 'POST',
        			        			success: function(data){
        			        				$('#submitSummaryBundledReport').prop('disabled', false);
        			        				clearTimeout(ref);
        			        				loadSummaryPdfInformation(assessmentProgId, organizationId);
        			        				// scrollTop       			        					        				
        			        				$(".allStudents").animate({ scrollTop: 0}, "fast");
        			        				
        			        			}
        			        			});
        						
        						$('#bundledSummaryContentAreaLastName').hide();
        						$('#bundledSummaryGradeLastName').hide();
        						
        						// close 
        					$(this).dialog('close');
        					// refresh select option
	        					$('#studentSummaryBundledReportSchool').val('').trigger('change.select2');

	        					$('#studentSummaryBundledReportGrades').val('').trigger('change.select2');
	        					
	        					if(totalSchoolLength !=1){
	        			             $('#studentSummaryBundledReportSchool').val('').trigger('change.select2');
	        			          } 
	        			        if(totalGradesLength !=1 )
	        			          {
	        			             $('#studentSummaryBundledReportGrades').val('').trigger('change.select2');
	        			          }
	        					
	        					
	        					// refresh sort
	        					$('#summarySortFirstBy').val('').trigger('change.select2');
	        					$('#summarySortFirstBy').prop('disabled', false);
	        					$('#summarySortSecondBy').val('').trigger('change.select2');
	        					$('#summarySortSecondBy').prop('disabled', false);
	        					$('#summarySortLastBy').val('').trigger('change.select2');
	        					$('#summarySortLastBy').prop('disabled', false);
	        					 
	        					// refresh checkbox
	        					$('#districtSummaryBundledSelect').attr('checked', false); 
	        					$('#districtSummarySeparateSelect').attr('checked', false); 
	        					$('#schoolSummaryBundledSelect').attr('checked', false); 
	        					$('#schoolSummarySeparateSelect').attr('checked', false); 
	        					$('#summarySeparateBundledReportSelect').attr('checked', false);
	        					$('#submitSummaryBundledReport').prop('disabled', false);
        					}
        				},
        				});
        				},
        		error: function(){}
        		});
        
	}
});


function loadPdfInformation(assessmentProgramId, organizationId){
	$('#studentReportInclude').hide();
	$('#studentBundledCreatedDate').hide();
	$('#studentBundledSortedBy').hide();
	var schoolSelect = $('#schoolSelect').val();
	if(schoolSelect != 0){
		$('#gridSchoolName').hide();
	}
	$.ajax({
		url: 'getDynmaicBundleRequestForOrganization.htm',
		dataType: 'json',
		type: 'GET',
		data: {
			organizationId : organizationId,
			assessmentProgramId : assessmentProgramId,
			reportCode : $('td.currentReport').attr('id'),
			reportYear : $('#reportYearSelect').val()
			},
			success: function(data){
				if(data.length > 0){
					$('#studentReportInclude').show();
					$('#studentBundledCreatedDate').show();
					$('#studentBundledSortedBy').show();
					
				   $('#studentReportSchool').empty().append('Schools: ').append(data[0].schoolNames);
				   $('#studentReportSubject').empty().append('Subjects: ').append(data[0].subjectNames);
				   $('#studentReportGrades').empty().append('Grades: ').append(data[0].gradeNames);
				
				
				   $('#submittedUser').empty().append(data[0].submittedUser);
				
				   var date = new Date(data[0].createdDate);
			       var opts = $.extend({}, $.jgrid.formatter.date, opts);
			        
			       $('#submittedDate').empty().append($.fmatter.util.DateFormat("", date, 'm/d/Y h:i:s A', opts));
			       $('#studentReportSort').empty().append(data[0].sortString);
					
				 
                var studentBundledReportStatusLists = $('#studentBundledReportStatusLists');
				studentBundledReportStatusLists.empty();
				
				$.each(data, function(i, organizationBundleReport) {
					
					 date = new Date(organizationBundleReport.createdDate);
   			         opts = $.extend({}, $.jgrid.formatter.date, opts);
					var datas="<tr>";
					datas=datas+"<td style='text-align: center;'>"+organizationBundleReport.submittedUser+"</td>";
					datas=datas+"<td style='text-align: center;'>"+ $.fmatter.util.DateFormat("", date, 'm/d/Y h:i:s A', opts)+"</td>";
					datas=datas+"<td style='text-align: center;'>"+organizationBundleReport.statusString+"</td>";
					if(schoolSelect == 0){
					  datas=datas+"<td style='text-align: center;'>"+(organizationBundleReport.schoolNames == null ? '' :organizationBundleReport.schoolNames)+"</td>";
					}
					datas=datas+"<td style='text-align: center;'>"+(organizationBundleReport.subjectNames == null ? '' :organizationBundleReport.subjectNames)+"</td>";
					datas=datas+"<td style='text-align: center;'>"+(organizationBundleReport.gradeNames == null ? '' :organizationBundleReport.gradeNames)+"</td>";
					datas=datas+"<td style='text-align: center;'>"+organizationBundleReport.sortString+"</td>";
					datas=datas+"</tr>";							
					studentBundledReportStatusLists.append(datas);
				});                    
				} else{
					$('#studentReportInclude').hide();
					$('#studentBundledCreatedDate').hide();
					$('#studentBundledSortedBy').hide();
				}
				if(ref != 'refreshed'){
				  ref = window.setTimeout(function(){loadPdfInformation(assessmentProgramId,organizationId);}, 10000);
				}
		    }
	});
	

}

function loadSummaryPdfInformation(assessmentProgramId, organizationId){
	$('#studentReportInclude').hide();
	$('#studentBundledCreatedDate').hide();
	$('#studentBundledSortedBy').hide();
	var schoolSelect = $('#schoolSelect').val();
	if(schoolSelect != 0){
		$('#gridSchoolName').hide();
	}
	$.ajax({
		url: 'getDynmaicBundleRequestForOrganization.htm',
		dataType: 'json',
		type: 'GET',
		data: {
			organizationId : organizationId,
			assessmentProgramId : assessmentProgramId,
			reportYear : $('#reportYearSelect').val(),
			reportCode : 'STUDENT_SUMMARY_BUNDLED'    			        			
			},
			success: function(data){
				if(data.length > 0){
					$('#studentReportInclude').show();
					$('#studentBundledCreatedDate').show();
					$('#studentBundledSortedBy').show();
					
				   $('#studentReportSchool').empty().append('Schools: ').append(data[0].schoolNames);
				   $('#studentReportSubject').empty().append('Subjects: ').append(data[0].subjectNames);
				   $('#studentReportGrades').empty().append('Grades: ').append(data[0].gradeNames);
				
				
				   $('#submittedUser').empty().append(data[0].submittedUser);
				
				   var date = new Date(data[0].createdDate);
			       var opts = $.extend({}, $.jgrid.formatter.date, opts);
			        
			       $('#submittedDate').empty().append($.fmatter.util.DateFormat("", date, 'm/d/Y h:i:s A', opts));
			       $('#studentReportSort').empty().append(data[0].sortString);
					
				 
                var studentBundledReportStatusLists = $('#studentSummaryBundledReportStatusLists');
				studentBundledReportStatusLists.empty();
				
				$.each(data, function(i, organizationBundleReport) {
					
					 date = new Date(organizationBundleReport.createdDate);
   			         opts = $.extend({}, $.jgrid.formatter.date, opts);
					var datas="<tr>";
					datas=datas+"<td style='text-align: center;'>"+organizationBundleReport.submittedUser+"</td>";
					datas=datas+"<td style='text-align: center;'>"+ $.fmatter.util.DateFormat("", date, 'm/d/Y h:i:s A', opts)+"</td>";
					datas=datas+"<td style='text-align: center;'>"+organizationBundleReport.statusString+"</td>";
					if(schoolSelect == 0){
					  datas=datas+"<td style='text-align: center;'>"+(organizationBundleReport.schoolNames == null ? '' :organizationBundleReport.schoolNames)+"</td>";
					}
					//datas=datas+"<td style='text-align: center;'>"+(organizationBundleReport.subjectNames == null ? '' :organizationBundleReport.subjectNames)+"</td>";
					datas=datas+"<td style='text-align: center;'>"+(organizationBundleReport.gradeNames == null ? '' :organizationBundleReport.gradeNames)+"</td>";
					datas=datas+"<td style='text-align: center;'>"+organizationBundleReport.sortString+"</td>";
					datas=datas+"</tr>";							
					studentBundledReportStatusLists.append(datas);
				});                    
				} else{
					$('#studentReportInclude').hide();
					$('#studentBundledCreatedDate').hide();
					$('#studentBundledSortedBy').hide();
				}
				if(ref != 'refreshed'){
				  ref = window.setTimeout(function(){loadSummaryPdfInformation(assessmentProgramId,organizationId);}, 10000);
				}
		    }
	});	
}