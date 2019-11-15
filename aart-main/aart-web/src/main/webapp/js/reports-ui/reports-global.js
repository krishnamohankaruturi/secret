// for some reason, any select2() calls that change whether a dropdown is disabled needs the 'placeholder' and 'allowClear' options
// to still function as intended

var ref = null;
var checkRef = null;
const select2DefaultOptions = {
	placeholder: 'Select',
	multiple: false,
	allowClear: true,
	closeOnSelect: !this.multiple
};
var currentReportYear = null;
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

var ref = null;
var isAKUser= $('#isNewReportsAKUser').val();
$(function(){	
	$('.reports-nav .dropdown').on('show.bs.dropdown', function(){
		$(this).find('.dropdown-menu').first().stop(true, true).slideDown(50);
	});
	$('.reports-nav .dropdown').on('hide.bs.dropdown', function(){
		$(this).find('.dropdown-menu').first().stop(true, true).slideUp(50);
	});
	
	// update configuration object whenever we switch tabs
	$('.reports-nav a.nav-link, .reports-nav a.dropdown-item').on('shown.bs.tab', function(){
		var $this = $(this);		
		reportsConfig.setReportType($this.data('report-type'));
		reportsConfig.setReportCode($this.data('report-code'));
		reportsConfig.setAssessmentProgramCode($('#userDefaultAssessmentProgram option[selected="selected"]').text().trim());
		reportsConfig.setInactiveOrgsReq($this.data('inactive-org-req'));
		reportsConfig.setContentElementId($this.attr('href'));
		if ($this.attr('loaded') != 'true'){
			loadFilters();			
			$this.attr('loaded', 'true');
		} else {			
			$('.pagination').css('display', 'none');
			setupPagination();
			clearReportContents();
			setFilterCount();
			resetFiltersPast(0);
			loadFilters();
			// clear any report data, maybe filters as well?
		}
	});
	
	showFirstNavItem();
});

function getSelect2DefaultOptionsCopy(){
	var obj = {};
	var properties = Object.getOwnPropertyNames(select2DefaultOptions);
	for (var x = 0; x < properties.length; x++){
		obj[properties[x]] = select2DefaultOptions[properties[x]];
	}
	return obj;
}

function getContentElement(){
	return $(reportsConfig.getContentElementId());
}

function getFilterElement(){
	return $('form.report-filter-form', getContentElement());
}

function getReportContentElement(){
	return $('.report-content', getContentElement());
}

function clearReportContents(){
	getReportContentElement().html('');
}

function getReportBlock(){
	return $('#student-individual-reports');
}

function showFirstNavItem(){
	var itemsToShow = $('.reports-nav a.dropdown-item');
	if (itemsToShow.length == 0){
		itemsToShow = $('.reports-nav a.nav-link');
	}
	itemsToShow.first().tab('show');
}

function loadFilters(){
	
	var $tabArea = getContentElement();
	can.view.render('js/views/reports-ui/reportFilter.ejs', {
		filtersNeeded: reportsConfig.get().filters,
		userOrgLevel: $('#orgLevel').val(),
		contentElementId: reportsConfig.getContentElementId().slice(1)
	}, function(fragment){
		$tabArea.html(fragment);
		initFilters();
		fireCustomFilterLoad();
		loadFilterAtIndex(0);
		enableFiltersAfterIndex(0, false);
		ref = 'refreshed';	
		$('.pagination').css('display', 'none');
		setupPagination();
		paginationControl();
	});
}

function initFilters(){
	$('select.select-plugin', getFilterElement()).select2(select2DefaultOptions);
	// using open/close checking because using 'change' with multiselect is kind of a nightmare experience-wise
	$('select.select-plugin', getFilterElement()).on('select2:open', function(){
		var $this = $(this);
		$this.data('opened-currently-selected', $this.val());
	}).on('select2:close', function(){		
		var $this = $(this);
		var openedVal = $this.data('opened-currently-selected');
		var val = $this.val();
		var valChanged = $(openedVal).not(val).length !== 0 || $(val).not(openedVal).length !== 0 || val !== openedVal;		
		if (valChanged){
			clearReportContents();
			setFilterCount();
			$('.pagination').css('display', 'none');
			var currentIndex = $this.data('filter-index');			
			if (val != null && val != '0'){
				var nextIndex = currentIndex + 1;
				var nextFilter = getFilterAtIndex(nextIndex);
				if (nextFilter.length > 0 && !filtersAreValid()){
					clearFiltersAfterIndex(currentIndex);
					loadFilterAtIndex(nextIndex);
					enableFiltersAfterIndex(nextIndex, false);
					enableFilterAtIndex(nextIndex, true);				
				} else {
					// we can assume the filters are all filled in, right?
					$('.pagination').css('display', 'none');
					loadCountReports();
					setupPagination();
					getReports(1);
					reportsConfig.getPaginationElement().jqPagination('option', 'current_page', 1);
					
				}
			} else {
				clearFiltersAfterIndex(currentIndex);
				enableFiltersAfterIndex(currentIndex, false);
				loadFilterAtIndex(nextIndex);
				enableFilterAtIndex(nextIndex, true);	
			}
		}
	
		$this.removeData('opened-currently-selected');		
	});
	$('select[data-data-type="summaryLevel"]', getFilterElement()).val('').trigger('change.select2');
}

function filtersAreValid(){
	// a function that should be on child pages, if necessary
	// DLM uses this in a couple of places, mostly involving summary level
	return typeof (customFilterValidation) === 'function' ? customFilterValidation() : false;
}

function fireCustomFilterLoad(){
	// The function should be implemented on child pages, not in the global file.
	// Only execute the function if it exists, because if there's no reason to have it,
	// why bother declaring it in the child file?
	if (typeof (customFilterLoad) === 'function'){
		customFilterLoad();
	}
}

function getAllFilters(){
	return $('select', getFilterElement());
}

function getFilterWithDataType(type){
	return $('select[data-data-type="' + type + '"]', getFilterElement());
}

function enableFilterAtIndex(index, enable){	
	var enabled = (enable == true);
	var filter = getFilterAtIndex(index);
	var options = $.extend({disabled: !enabled}, getSelect2DefaultOptionsCopy());
	filter.select2(options);
	if (enabled == true){
		fireCustomFilterLoad();
	}
}

function enableFiltersAfterIndex(index, enable){
	var enabled = (enable == true);
	var filters = getFiltersAfterIndex(index);
	var options = $.extend({disabled: !enabled}, getSelect2DefaultOptionsCopy());
	filters.select2(options);
	if (enabled == true){
		fireCustomFilterLoad();
	}
}

function clearFilterAtIndex(index){
	var filter = getFilterAtIndex(index);
	filter.val(null).trigger('select2.change');
	filter.find('option[value!="0"]').remove();
}

function clearFiltersAfterIndex(index){	
	var filtersToClear = getFiltersAfterIndex(index);
	if (filtersToClear.length > 0){
		filtersToClear.each(function(){
			$(this).val(null).trigger('select2.change');
			$(this).find('option[value!="0"]').remove();
		});
	}
}

function getFilterAtIndex(index){
	var filters = $('select', getFilterElement()).filter(function(i, element){
		var elementIndex = $(element).data('filter-index');
		return (!isNaN(elementIndex)) && elementIndex == index;
	});
	return filters.slice(0, 1);
}

function getFiltersAfterIndex(index, amount){
	var filters = $('select', getFilterElement()).filter(function(i, element){
		var elementIndex = $(element).data('filter-index');
		return (!isNaN(elementIndex)) && elementIndex > index;
	});
	if (amount && !isNaN(amount) && amount > -1){
		filters = filters.slice(0, amount);
	}
	return filters;
}

function loadFilterAtIndex(index){
	var filter = getFilterAtIndex(index);
	
	if (filter.length > 0){
		var currentType = filter.data('data-type');
	
		
		switch (currentType){
			case 'reportYear':
				loadReportYears();
				break;
			case 'district':
				loadDistricts();
				break;
			case 'school':
				loadSchools();
				break;
			case 'subject':
				loadSubjects();
				break;
			case 'grade':
				loadGrades();
				break;
			case 'roster':
				loadRosters();
				break;
			case 'teacherRoster':
				loadTeacherRosters();
				break;	
			case 'state':
				loadStates();
				break;
			case 'teacher':
				loadTeachers();
				break;
			case 'windowCycle':
				loadWindowCycle();
				break;
			default:
				break;
		}
	}
}

function loadReportYears(){
	 var reportCode=reportsConfig.getReportCode();	

	 var readyToView = $('#'+reportCode).attr('readyToView');
	 var access = $('#'+reportCode).attr('access');		 
	 $.ajax({
		url: 'getReportyearByUserForReports.htm',
		dataType: 'json',
		data: {
			reportType: reportsConfig.getReportType(),
			reportCode: reportsConfig.getReportCode(),
			assessmentCode: $('#userDefaultAssessmentProgram option[selected="selected"]').text().trim(),
			assessmentProgId: $('#userDefaultAssessmentProgram option[selected="selected"]').val()
		},
		type: 'POST'
	}).done(function(data){
			var select = $('select[data-data-type="reportYear"]', getFilterElement());
				if (data !== undefined && data !== null && data.length > 0){
				currentReportYear = data[0];
				var i = 1;
				
				if(readyToView == 'true' && access == 'true'){						
					i = 0;
				}				
				for (i; i < data.length; i++){	
					select.append($('<option>', { value: data[i], text: data[i] }));
				}
				select.val('').trigger('change.select2');
				
			/*	select.val(data[j]).change();
				if($(select).find("option").length === 1){
					select.append($('<option>', { value: '', text: 'No Report Year Available' }));
				}*/
//				select.val('').trigger('change.select2');
			} else {
				select.append($('<option>', { value: '',text: 'No Report Year Available' }));
			}
		}).fail(function(){});
}

function loadStates(){
	if (reportsConfig.getFiltersAvailable().state){
		$.ajax({
			url: 'getStatesByUserForAlternateReports.htm',
			dataType: 'json',
			data: {
				reportYear: $('select[data-data-type="reportYear"]', getFilterElement()).val(),
				reportType: reportsConfig.getReportType(),
				assessmentProgId : $('#userDefaultAssessmentProgram option[selected="selected"]').val(),
			},
			type: 'GET'
		}).done(function(data){
				if (data !== undefined && data !== null && data.length > 0){
					var select = $('select[data-data-type="state"]', getFilterElement());
					for (var i = 0; i < data.length; i++){
						select.append($('<option>', { value: data[i].id, text: data[i].organizationName }));
					}
					if (data.length === 1){
						select.val(data[0].id).change();
					}else{
					select.val('').trigger('change.select2');}
				} else {
					//TODO no orgs found
					
				}
		}).fail(function(){});
	}
}

function loadDistricts(){
	if (reportsConfig.getFiltersAvailable().district){
		$.ajax({
			url: 'getDistrictsByUserForReports.htm',
			dataType: 'json',
			data: { 
				isInactiveOrgReq: reportsConfig.getInactiveOrgsReq(),
				reportType: reportsConfig.getReportType(),
				assessmentProgId: $('#userDefaultAssessmentProgram option[selected="selected"]').val()
			},
			type: 'POST'
		}).done(function(data){
				
				if (data !== undefined && data !== null && data.length > 0){
					var select = $('select[data-data-type="district"]', getFilterElement());
					for (var i = 0; i < data.length; i++){
						select.append($('<option>', { value: data[i].id, text: data[i].organizationName }));
					}
					if (data.length === 1){
						select.val(data[0].id).change();
						enableNextFilterBasedOnIndex(select);
					}else{
					select.val('').trigger('change.select2');}
				} else {
					//TODO no orgs found
					/*if(me.options.reportType == 'alternate_yearend_district_summary' && me.options.assessmentProgCode == 'DLM'){
						$("#dlmGRFReportErrorMsg").show();
					}*/
				}
			}).fail(function(){});
	}
}

function loadSchools(){
	if (reportsConfig.getFiltersAvailable().school){
		var districtIds = $('select[data-data-type="district"]', getFilterElement()).val();
		if (typeof (districtIds) !== 'object'){ // meaning it was a single select
			districtIds = [districtIds];
		}
		$.ajax({
			url: 'getSchoolsByUserForReports.htm',
			dataType: 'json',
			data: {
				districtIds: districtIds,
				reportYear: $('select[data-data-type="reportYear"]', getFilterElement()).val(),
				isInactiveOrgReq: reportsConfig.getInactiveOrgsReq(),
				reportType: reportsConfig.getReportType(),
				assessmentProgId: $('#userDefaultAssessmentProgram option[selected="selected"]').val()
			},
			type: 'POST'
		}).done(function(data){
				if (data !== undefined && data !== null && data.length > 0){
					var select = $('select[data-data-type="school"]', getFilterElement());
					$('select.select-plugin', getFilterElement()).select2(select2DefaultOptions);
					/*if((reportType=='general_student_all' || reportType=='alternate_student_all') && data.length > 1)
						select.append($('<option>', { value: -1, text: 'All' }));*/
					for (var i = 0; i < data.length; i++){
						select.append($('<option>', { value: data[i].id, text: data[i].organizationName }));
					}
					setTimeout(function(){
						if (data.length === 1){
							select.val(data[0].id).change();
							enableNextFilterBasedOnIndex(select);
						}else{
							select.val('').trigger('change.select2');
						}
					}, 50);	
				
				} else {
					//TODO no orgs found
					
					
				}
				//$('#textLine').css({"height":"auto" ,"float":"left", "border-bottom": "1px solid #bbbbbb"});
			}).fail(function(){});
	}
}

function loadSubjects(){
	if (reportsConfig.getFiltersAvailable().subject){
		$.ajax({
			url: 'getContentAreasForReporting.htm',
			dataType: 'json',
			data: {
				districtId: $('select[data-data-type="district"]', getFilterElement()).val(),
				schoolId: $('select[data-data-type="school"]', getFilterElement()).val(),
				isInactiveOrgReq: reportsConfig.getInactiveOrgsReq(),
				reportType: reportsConfig.getReportType(),
				assessmentProg: $('#userDefaultAssessmentProgram option[selected="selected"]').val(),
				assessmentProgCode: $('#userDefaultAssessmentProgram option[selected="selected"]').text().trim(),
				reportCode: reportsConfig.getReportCode(),
				reportYear: $('select[data-data-type="reportYear"]', getFilterElement()).val()
			},
			type: 'GET'
		}).done( function(data){
				if (data !== undefined && data !== null && data.length > 0){
					var select = $('select[data-data-type="subject"]', getFilterElement());
					for (var i = 0; i < data.length; i++){
						select.append($('<option>', { value: data[i].id, text: data[i].name }));
					}
					setTimeout(function(){
						if (data.length === 1){
							select.val(data[0].id).change();
							enableNextFilterBasedOnIndex(select);
						}else{
							select.val('').trigger('change.select2');
						}
					}, 50);	
					
				} else {
					//TODO no content areas found
				}
			}).fail(function(){});
	}
}

function loadGrades(){		
	if (reportsConfig.getFiltersAvailable().grade){
		var subjectIds = $('select[data-data-type="subject"]', getFilterElement()).val();			
		if (typeof (subjectIds) !== 'object'){
			subjectIds = [subjectIds];
		}
		$.ajax({
			url: 'getGradesForReporting.htm',
			dataType: 'json',
			data: {
				districtId: $('select[data-data-type="district"]', getFilterElement()).val(),
				schoolId: $('select[data-data-type="school"]', getFilterElement()).val(),
				subjectIds: subjectIds,
				isInactiveOrgReq: reportsConfig.getInactiveOrgsReq(),
				reportType: reportsConfig.getReportType(),
				assessmentProg: $('#userDefaultAssessmentProgram option[selected="selected"]').val(),
				assessmentProgCode: $('#userDefaultAssessmentProgram option[selected="selected"]').text().trim(),
				reportCode: reportsConfig.getReportCode(),
				reportYear: $('select[data-data-type="reportYear"]', getFilterElement()).val()
			},
			type: 'GET'
		}).done(function(data){
				if (data !== undefined && data !== null && data.length > 0){
					var select = $('select[data-data-type="grade"]', getFilterElement());
					for (var i = 0; i < data.length; i++){
						select.append($('<option>', { value: data[i].id, text: data[i].name }));
					}
					setTimeout(function(){
						if (data.length === 1){
							select.val(data[0].id).change();
							enableNextFilterBasedOnIndex(select);
						}else{
							select.val('').trigger('change.select2');
						}
					}, 50);	
					//
				} else {
					//TODO no grades found
				}
			}).fail(function(){});
	}
}

function loadRosters(){
	if (reportsConfig.getFiltersAvailable().roster){
		$.ajax({
			url: 'getRostersForReporting.htm',
			dataType: 'json',
			data: {
				districtId: $('select[data-data-type="district"]', getFilterElement()).val(),
				schoolId: $('select[data-data-type="school"]', getFilterElement()).val(),
				subjectId: $('select[data-data-type="subject"]', getFilterElement()).val(),
				reportType: reportsConfig.getReportType()
			},
			type: 'GET'
		}).done(function(data){
				if (data !== undefined && data !== null && data.length > 0){
					var select = $('select[data-data-type="roster"]', getFilterElement());
					for (var i = 0; i < data.length; i++){
						select.append($('<option>', { value: data[i].id, text: data[i].courseSectionName }));
					}
					setTimeout(function(){
						if (data.length === 1){
							select.val(data[0].id).change();
							enableNextFilterBasedOnIndex(select);
						}else{
							select.val('').trigger('change.select2');
						}
					}, 50);	
					
				} else {
					//TODO no grades found						
					
				}
			});
	}
}

function loadTeacherRosters(){
	if (reportsConfig.getFiltersAvailable().teacherRoster){
		$.ajax({
			url: 'getRosterForTeacherReports.htm',
			dataType: 'json',
			data: {
				reportType: reportsConfig.getReportType(),
				reportYear: $('select[data-data-type="reportYear"]', getFilterElement()).val()
			},
			type: 'GET'
		}).done(function(data){
				var select = $('select[data-data-type="teacherRoster"]', getFilterElement());
				if (data !== undefined && data !== null && data.length > 0){
					for (var i = 0; i < data.length; i++){
						select.append($('<option>', { value: data[i].id, text: data[i].courseSectionName + " (" +  data[i].schoolName + ")" }));
					}
				} else {
					select.append($('<option>', { value: '',text: 'No Rosters Available' }));
				}
				setTimeout(function(){
					if (data.length === 1){
						select.val(data[0].id).change();
						enableNextFilterBasedOnIndex(select);
					}else{
						select.val('').trigger('change.select2');
					}
				}, 50);	
			});
	}
}

function loadStates(){
	if (reportsConfig.getFiltersAvailable().state){
		$.ajax({
			url: 'getStatesByUserForAlternateReports.htm',
			dataType: 'json',
			data: {
				reportYear: $('select[data-data-type="teacherRoster"]', getFilterElement()).val(),
				reportType: reportsConfig.getReportType(),
				assessmentProgId: $('#userDefaultAssessmentProgram option[selected="selected"]').val()
			},
			type: 'GET'
		}).done(function(data){
				if (data !== undefined && data !== null && data.length > 0){
					var select = $('select[data-data-type="state"]', getFilterElement());
					for (var i = 0; i < data.length; i++){
						select.append($('<option>', { value: data[i].id, text: data[i].organizationName }));
					}
					if (data.length === 1){
						select.val(data[0].id).change();
					}
					setTimeout(function(){
						if (data.length === 1){
							select.val(data[0].id).change();
							enableNextFilterBasedOnIndex(select);
						}else{
							select.val('').trigger('change.select2');
						}
					}, 50);	
				} else {
					//TODO no orgs found
					
				}
			});
	}
}

function loadWindowCycle(){
		$.ajax({
			url: 'getTestingWindowCycleForReports.htm',
			dataType: 'json',
			data: {
			},
			type: 'POST',
			success: function(data){
				if (data !== undefined && data !== null && data.length > 0){
					var select = $('select[data-data-type="windowCycle"]', getFilterElement());
					for (var i = 0; i < data.length; i++){
						select.append($('<option>', { value: data[i].id, text: data[i].testingCycleName }));
					}
					if (data.length === 1){
						select.val(data[0].id).change();
						enableNextFilterBasedOnIndex(select);
					}else{
						select.val('').trigger('change.select2');
					}
					
				} else {
					//TODO no content areas found
				}
			},
			error: function(){}
		});
}

function loadTeachers(){
	var url = '';
	if(reportsConfig.getReportType() == 'alternate_classroom'){
		url = 'getTeachersForClassroomReportsByReportYear.htm';
	}else{
		url = 'getTeachersForClassroomReports.htm';
	}
	//var params = this.form.serializeArray();
	var params =[];
	params.push({name: 'reportType', value: reportsConfig.getReportType()});
	params.push({name: 'assessmentProgram', value: $('#userDefaultAssessmentProgram option[selected="selected"]').val()});
	params.push({name: 'reportYear', value: $('select[data-data-type="reportYear"]', getFilterElement()).val()});
	params.push({name: 'schoolId', value: $('select[data-data-type="school"]', getFilterElement()).val()});
	params.push({name: 'assessmentProgram', value: $('#userDefaultAssessmentProgram option[selected="selected"]').val()});
	
	if (reportsConfig.getFiltersAvailable().teacher){
		$.ajax({
			url: url,
			dataType: 'json',
			data: params,
			type: 'GET'
		}).done(function(data){
				var select = $('select[data-data-type="teacher"]', getFilterElement());
				if (data !== undefined && data !== null && data.length > 0){
					for (var i = 0; i < data.length; i++){
						select.append($('<option>', { value: data[i].teacherId, text: data[i].teacherName}));
					}
				} else {
					select.append($('<option>', { value: '',text: 'No Teachers Available' }));
				}
				setTimeout(function(){
					if (data.length === 1){
						select.val(data[0].id).change();
						enableNextFilterBasedOnIndex(select);
					}else{
						select.val('').trigger('change.select2');
					}
				}, 50);	
			});
	}
}


function loadCountReports(){
	var url = reportsConfig.getCountURL();
	if (url !== ''){
		$.ajax({
			url: url,
			dataType: 'json',	
			data: {				
				reportType: reportsConfig.getReportType(),
				districtId: $('select[data-data-type="district"]', getFilterElement()).val(),
				schoolId: $('select[data-data-type="school"]', getFilterElement()).val(),
				subjectId: $('select[data-data-type="subject"]', getFilterElement()).val(),
				gradeId: $('select[data-data-type="grade"]', getFilterElement()).val(),
				assessmentProgram: $('#userDefaultAssessmentProgram option[selected="selected"]').val(),
				assessmentProgCode: $('#userDefaultAssessmentProgram option[selected="selected"]').text().trim(),
				reportCode: reportsConfig.getReportCode(),
				gradeId: $('select[data-data-type="grade"]', getFilterElement()).val(),
				teacherRosterId: $('select[data-data-type="teacherRoster"]', getFilterElement()).val(),
				reportYear: $('select[data-data-type="reportYear"]', getFilterElement()).val()	
			
			},			
			type: 'GET'
		}).done(function(count){
				$('.pagination', this.element).css('display', '');
				refreshMaxPages(count);
				
		});
	}
}


function refreshMaxPages(recordCount){	
	var pagination  = reportsConfig.getPagination();	
	var maxPages = Math.floor(recordCount / pagination.itemsPerPage);
	if (recordCount % pagination.itemsPerPage !== 0){
		maxPages++;
	}	
	reportsConfig.setMaxPages(maxPages);
	if(maxPages > 0)
		reportsConfig.getPaginationElement().jqPagination('option', 'max_page', maxPages);
}


function getReports(page){
	
	var url = reportsConfig.getPageURL();
	var schoolId= 0;
	var school = $('select[data-data-type="school"]', getFilterElement()).val();
	if(school!=null || school!=undefined){
		schoolId = school;
	}
	var params =[];
	var reportType = reportsConfig.getReportType();
	params.push({name: 'reportType', value: reportType});
	params.push({name: 'districtId', value: $('select[data-data-type="district"]', getFilterElement()).val()});
	params.push({name: 'schoolId', value: schoolId});
	params.push({name: 'subjectId', value: $('select[data-data-type="subject"]', getFilterElement()).val()});
	params.push({name: 'gradeId', value: $('select[data-data-type="grade"]', getFilterElement()).val()});		
	params.push({name: 'page', value: page});
	params.push({name: 'perPage', value: reportsConfig.getPagination().itemsPerPage});
	params.push({name: 'assessmentProgram', value: $('#userDefaultAssessmentProgram option[selected="selected"]').val()});
	params.push({name: 'assessmentProgCode', value: $('#userDefaultAssessmentProgram option[selected="selected"]').text().trim()});
	params.push({name: 'reportCode', value : reportsConfig.getReportCode()});
	params.push({name: 'reportYear', value : $('select[data-data-type="reportYear"]', getFilterElement()).val()	});
	params.push({name: 'summaryLevel', value : $('select[data-data-type="summaryLevel"]', getFilterElement()).val()});
	params.push({name: 'rosterId', value : $('select[data-data-type="roster"]', getFilterElement()).val()});
	params.push({name: 'stateId', value : $('select[data-data-type="state"]', getFilterElement()).val()});
	params.push({name: 'dlmTeacherId', value : $('select[data-data-type="teacher"]', getFilterElement()).val()});
	params.push({name: 'testCycleID', value : $('select[data-data-type="windowCycle"]', getFilterElement()).val()});
	params.push({name: 'teacherRosterId', value : $('select[data-data-type="teacherRoster"]', getFilterElement()).val()});
	
	$('#reportContentDiv', this.element).empty();	
	var assessmentProgCode = $('#userDefaultAssessmentProgram option[selected="selected"]').text().trim();
	if (url !== ''){
		$.ajax({
			url: url,
			dataType: 'json',
			data: params,
			type: 'GET'
		}).done(function(data){	
				var reportsToPrepareRegardless = [
					'alternate_roster',
					'alternate_student',
					'general_student_writing',
					'alternate_monitoring_summary',
					'alternate_blueprint_coverage',
					'alternate_student_individual_teacher',
					'alternate_student_summary_teacher'
				];
				if ((data != null && data.length > 0 ) || $.inArray(reportType, reportsToPrepareRegardless) > -1){
					
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
					
					// child function
					prepareReportContent(data, paramsToPass);
				} else {					
					// child function
					prepareReportContent(data, paramsToPass);
					
				}
			}).fail(function(){});
	}

}

function setupPagination(){
	reportsConfig.setPagination({itemsPerPage: 45});
	reportsConfig.setPaginationElement($('.pagination', getContentElement()));	
}

function paginationControl() {
	reportsConfig.getPaginationElement().jqPagination({			
		paged: function(page){
			getReports(page);
		}
	});
	
}

function disableFiltersPast(index){
	var filtersToDisable = getFiltersPast(index);
	filtersToDisable.each(function(i, element){
		var $element = $(element);
		$element.closest('.criteria').removeClass('enabled complete');
		$element.prop('disabled', true);
	});
}

function enableNextFilterPast(index){
	
	if (index == (reportsConfig.getFiltersCount() - 1)){ // last filter
		
		$('#resetButton', getFilterElement()).show();
		if ($(".report-filter-form").form && $(".report-filter-form").valid()){
			
			var timeout = 0;
			reportsConfig.getPaginationElement().jqPagination('option', 'current_page', 1);
			loadCountReports();
			getReports(1);
		}
	} else {
		
		var nextFilter = getFilterAtIndex(index + 1);
		if (nextFilter.length > 0){
			nextFilter.closest('.criteria').removeClass('complete').addClass('enabled');
			nextFilter.prop('disabled', false);
		}
	}
	
}

function resetFiltersPast(index){
	var filtersToReset = getFiltersPast(index);
	filtersToReset.each(function(i, element){
		var $element = $(element);
		$('option', $element).slice(1).remove();
		var type = $element.data('data-type');
		$element.val('').trigger('change.select2');
	});
}

function getFiltersPast(index, firstAmount){
	var filters = $('select', getFilterElement()).filter(function(i, element){
		var elementIndex = $(element).data('filter-index');
		return (!isNaN(elementIndex)) && elementIndex > index;
	});
	if (firstAmount && !isNaN(firstAmount) && firstAmount > -1){
		filters = filters.slice(0, firstAmount);
	}
	return filters;
}

function setFilterCount(){
	var filterNames = Object.getOwnPropertyNames(reportsConfig.getFiltersAvailable());
	var filterCount = 0;
	reportsConfig.setFiltersCount(filterCount);
	for (var x = 0; x < filterNames.length; x++){
		if (!/Multiple$/.test(filterNames[x]) && reportsConfig.getFiltersAvailable()[filterNames[x]]){
			reportsConfig.setFiltersCount(filterCount++);
		}
	}
}

function enableNextFilterBasedOnIndex(select){
	var currentIndex = select.data('filter-index');
	var nextIndex = currentIndex + 1;
	var nextFilter = getFilterAtIndex(nextIndex);
	if (nextFilter.length > 0 && !filtersAreValid()){
		enableFilterAtIndex(nextIndex, true);
		loadFilterAtIndex(nextIndex);
	}else{
		$('.pagination').css('display', 'none');
		loadCountReports();
		setupPagination();
		getReports(1);
		reportsConfig.getPaginationElement().jqPagination('option', 'current_page', 1);
	}
}