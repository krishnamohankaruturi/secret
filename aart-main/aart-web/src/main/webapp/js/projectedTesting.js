/**
 * Added By Sudhansu 
 * Feature: f183
 * Projected Testing
 */



/****
 * For Multi - Datepicker coding
 * ***/

var  projectedTestingFile = $('#projectedTestingFileData');

function multicalenCancelbtn(){
	$('.multidatepicker').pickmeup('hide');
	$('.addNewMonthDatePicker').pickmeup('hide');
}
$('#addNewProjTesting').validate({
	ignore: "",	
	rules: {
		projectedTestingDistrict: {required: true},
		projectedTestingSchool: {required: true},
		projectedTestingMonth: {required: true}
	}
});

function multicalenSavebtn(){
	
	var addnewRowSave =  $('.gridAddNewMultiDatepicker .hiddenSavetype').val();
	var gridSave =  $('.gridMultiDatepicker .hiddenSavetype').val();
	
	//GridRowSaving
	
	if(gridSave=='active'){		
		var rowId = $('.gridMultiDatepicker .hiddenValuesDatePicker').attr("id");
		var month = $('.gridMultiDatepicker .hiddenValuesDatePicker').attr("data-month");
		var dates = $('#monthDatePickerdiv_'+rowId).pickmeup('get_date',true);
		var data = $("#viewMyCalendarSessionGridTableId").jqGrid('getRowData', rowId);	
		$.ajax({
			url: 'savingCalendarValues.htm',
			dataType: 'json',
			data: {
				assessmentProgramId : data.assessmentProgramId,
				stateId				: data.stateId,
				districtId			: data.districtId,
				schoolId	        : data.schoolId,
				month               : month,
				dates				: dates,
				grade               : data.gradeId,
				projectionType      : data.projectionType
		    	},				
			type: "POST"
		}).done(function() {
			$('.multidatepicker').pickmeup('hide');
			$("#viewMyCalendarSessionGridTableId").jqGrid('setGridParam',{
				postData: { "filters": ""}
			}).trigger("reloadGrid",[{page:1}]);
		});
	}
	
	//AddNewRowSaving
	
	else if(addnewRowSave=='active'){	
		if($('#addNewProjTesting').valid()){
			var assessmentProgramId = $('#projectedTestingUserAssessmentProgId').val();
			var dates = $('.addNewMonthDatePicker').pickmeup('get_date',true);
			var districtId = $('#projectedTestingDistrict').val();
			var month = "";			
			var montYear = $("#projectedTestingMonth").val();
			var grade = $("#projectedTestingGrade").val();
			var projectionType = $("#projectedTestingType").val();
			if(montYear!=null && montYear!=undefined && montYear!=''){
				var monthYearArray = montYear.split('-');
				month=monthYearArray[0];				
			}			
			var schoolId = $('#projectedTestingSchool').val();
			var stateId = $('#projectedTestingUserCurrentOrgId').val();			
			$.ajax({
				url: 'savingCalendarValues.htm',
				dataType: 'json',
				data: {
					assessmentProgramId : assessmentProgramId,
					stateId				: stateId,
					districtId			: districtId,
					schoolId	        : schoolId,
					month               : month,
					dates				: dates,
					grade               : grade,
					projectionType      : projectionType
			    	},				
				type: "POST"
			}).done(function() {
				$('.addNewMonthDatePicker').pickmeup('hide');
				
				$("#viewMyCalendarSessionGridTableId").jqGrid('setGridParam',{
					postData: { "filters": ""}
				}).trigger("reloadGrid",[{page:1}]);
				
				projectedTestingAddNewRow();
				$(".gridAddNewMultiDatepicker #divLabelwidth").html("");
				$(".gridAddNewMultiDatepicker #gradeLabelwidth").html("");
				$(".gridAddNewMultiDatepicker #projectionTypeLabelwidth").html("");
				
				$('#projectedTestingSchool').val("0").trigger('change.select2');
				$('#projectedTestingMonth').val("0").trigger('change.select2');
				$(".addNewMonthDatePicker").pickmeup("clear");
				$('#addNewMonthDatePickerdiv').pickmeup('set_date',new Date());					
			});
		}
	  }
}	

var projectedTestinguploadCategoryCode = 'PROJ_TEST_RECORD_TYPE';
$(function() {
	
	getquerystring();
	viewMyCalendarPrjTesting();	
	$("#projTest_TemplatedownloadquickHelpPopupClose").on('click keypress',function(event){
		if(event.type=='keypress'){
			if(event.which !=13){
				return true;
			}
		}
		$("#projTest_TemplatedownloadquickHelpPopup").hide();
	});
	$("#projTest_Templatedownloadquick").on('click keypress',function(event){
		if(event.type=='keypress'){
			if(event.which !=13){
				return true;
			}
		}
		$("#projTest_TemplatedownloadquickHelpPopup").show();
	});
	
	$('#projTest_Templatedownloadquick').on('mouseover focus',function(){
		$(".QuickHelpProjectedTestingHint").show();
	});
	$('#projTest_Templatedownloadquick').on('mouseout blur',function(){
		$(".QuickHelpProjectedTestingHint").hide();
	});
	$('.QuickHelpProjectedTestingHint').on('mouseover focus',function(){
		$(".QuickHelpProjectedTestingHint").show();
	});
	$('.QuickHelpProjectedTestingHint').on('mouseout blur',function(){
		$(".QuickHelpProjectedTestingHint").hide();
	});
	$('#projTest_Templatelink').on('click',function(){
		$("#projTest_TemplatedownloadquickHelpPopup").hide();
	});
	
	$(document).find("table").each(function() {
		$(this).attr('role', 'presentation')
	});
});

function addingMonthstoDropDown(){

	var output = [];
	$.ajax({
		url: 'getValidCalendarMonthNames.htm',
		dataType: 'json',
		type: "POST"
	}).done(function(monthValues) {	
		$.each(monthValues, function(key, value)
		{
		  output.push('<option value="'+ key +'">'+ value +'</option>');
		});
		$('#projectedTestingMonth').html(output.join('')).trigger('change.select2');
	});
};

function projectedTestingAddNewRow(){	
	
	$('#projectedTestingDistrict, #projectedTestingSchool, #projectedTestingMonth, #projectedTestingGrade, #projectedTestingType').select2({
		placeholder:'Select',
		multiple: false
	}).find('option').filter(function(){return $(this).val() > 0;}).remove().end()
	.trigger('change.select2');
	
	filteringOrganization($('#projectedTestingDistrict'));
	filteringOrganization($('#projectedTestingSchool'));
	filteringOrganization($('#projectedTestingGrade'));
	filteringOrganization($('#projectedTestingType'));
	
	// projectedTestingDistrict
	var districtOrgSelect = $('#projectedTestingDistrict');
	$.ajax({
		url: 'getOrgsBasedOnUserContext.htm',
		dataType: 'json',
		data: {
			orgId : $("#projectedTestingUserCurrentOrgId").val(), //${user.currentOrganizationId},
	    	orgType:'DT',
	    	orgLevel:50
	    	},				
		type: "GET"
	}).done(function(districtOrgs) {	
		districtOrgSelect.find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		districtOrgSelect.trigger('change.select2');
		if (districtOrgs !== undefined && districtOrgs !== null && districtOrgs.length > 0) {
			$.each(districtOrgs, function(i, districtOrg) {
				optionText = districtOrgs[i].organizationName;
				districtOrgSelect.append($('<option></option>').val(districtOrg.id).html(optionText));
			});					
	   if (districtOrgs.length == 1) {
			districtOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
			projectedTestingDistrictChangeEvent();
		} 
		} else {
			$('body, html').animate({scrollTop:0}, 'slow');
			$('#searchFilterErrors').html("No District Organizations Found for the current user").show();
		}
		$('#projectedTestingSchool, #projectedTestingDistrict').trigger('change.select2');
	});
	
	$('.addNewMonthDatePicker').pickmeup({
		position		: 'right',		
		mode	        : 'multiple',
		class_name 		: 'gridAddNewMultiDatepicker',
		select_month	: false,
		select_year		: false,
		select_date		: false,
		prev 			: '',
		next			: '',
		first_day		: 0,		
		min         : new Date(),
		change : function(data){
			var dates = $(this).pickmeup('get_date',true);
	    	if(dates==''){
	    		$('.gridAddNewMultiDatepicker .multicalenSavebtn').hide();
	    	}	
	    	else{
	    		$('.gridAddNewMultiDatepicker .multicalenSavebtn').show();
	    	}
		},
		show: function(data) {		
			var month = $('#projectedTestingMonth').val();
			var schoolName = $("#projectedTestingSchool option:selected").text();
			var projectionType= $("#projectedTestingType option:selected").text();
			var grade= $("#projectedTestingGrade option:selected").text();
			if(month !='' && month != undefined && month!= null){
				var d = new Date();
				var selectedDates= [];
				var montYear = $("#projectedTestingMonth").val();
				if(montYear!=null && montYear!=undefined && montYear!=''){
					var monthYearArray = montYear.split('-');
					var monthNumber=months[monthYearArray[0]];				
					var year = monthYearArray[1];
					selectedDates.push("01"+"-"+monthNumber+"-"+year);					
					$('#addNewMonthDatePickerdiv').pickmeup('set_date',selectedDates);
					$('#addNewMonthDatePickerdiv').pickmeup('clear');				
				  }				
				}			
			if(schoolName!=null && schoolName!=undefined && schoolName!='School') $(".gridAddNewMultiDatepicker #divLabelwidth").html(schoolName);
			if(grade!=null && grade!=undefined && grade!='Grade') $(".gridAddNewMultiDatepicker #gradeLabelwidth").html(grade);
			if(projectionType!=null && projectionType!=undefined && projectionType!='Projection Type') $(".gridAddNewMultiDatepicker #projectionTypeLabelwidth").html(projectionType);
	    },
	    before_show: function(){	
	    	$('.gridAddNewMultiDatepicker .hiddenSavetype').attr("value" ,"active");
	    	$('.gridMultiDatepicker .hiddenSavetype').attr("value" ,"inactive");
	    	$('.gridAddNewMultiDatepicker .multicalenSavebtn').hide();	    	
	    }
    });
	
	$( "#setupTestSessionManagementNavigationTabs" ).tabs({
		beforeActivate: function(event, ui) {			  
				$('.multidatepicker').pickmeup('hide');
				$('.addNewMonthDatePicker').pickmeup('hide');
		   }
	});
	
	$.ajax({
	    url: 'getGradesForProjectedScoring.htm',
	    data: {},
	    dataType: 'json',
	    type: "GET"
	}).done(function(grades) {
    	$('#projectedTestingGrade').html("");		
    	$('#projectedTestingGrade').append($('<option></option>').val('').html('Grade'));				        	
		$.each(grades, function(i, grades) {
			$('#projectedTestingGrade').append($('<option></option>').attr("value", grades.id).text(grades.name));
		});

		$('#projectedTestingGrade').trigger('change.select2');
	});
	$('#projectedTestingType').val("0").trigger('change.select2');
}

$('#projectedTestingDistrict').on('change',function() {
	projectedTestingDistrictChangeEvent();
	if($(this).val()==''){
		 $('#addNewMonthDatePickerdiv').pickmeup('clear');
		 $(".gridAddNewMultiDatepicker #divLabelwidth").html('');
		 $('#addNewMonthDatePickerdiv').pickmeup('set_date',new Date());
	}	
});	

$('#projectedTestingSchool').on('change',function() {
	if($(this).val()==''){
		 $('#addNewMonthDatePickerdiv').pickmeup('clear');
		 $(".gridAddNewMultiDatepicker #divLabelwidth").html('');
		 $('#addNewMonthDatePickerdiv').pickmeup('set_date',new Date());
	}
});

$('#projectedTestingGrade').on('change',function() {
	if($(this).val()==''){
		 $('#addNewMonthDatePickerdiv').pickmeup('clear');
		 $(".gridAddNewMultiDatepicker #gradeLabelwidth").html('');
		 $('#addNewMonthDatePickerdiv').pickmeup('set_date',new Date());
	}
});

$('#projectedTestingType').on('change',function() {
	if($(this).val()==''){
		 $('#addNewMonthDatePickerdiv').pickmeup('clear');
		 $(".gridAddNewMultiDatepicker #projectionTypeLabelwidth").html('');
		 $('#addNewMonthDatePickerdiv').pickmeup('set_date',new Date());
	}
});

$('#projectedTestingMonth').on('change',function() {	
 if($(this).val()==''){
	 $('#addNewMonthDatePickerdiv').pickmeup('clear');	
	 $(".gridAddNewMultiDatepicker #divLabelwidth").html('');
	 $('#addNewMonthDatePickerdiv').pickmeup('set_date',new Date());
 }
 else{
	 $('#addNewMonthDatePickerdiv').pickmeup('clear');	
	 $(".gridAddNewMultiDatepicker #divLabelwidth").html('');
 }
});	

function projectedTestingDistrictChangeEvent(){
		
	$('#projectedTestingSchool').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#projectedTestingSchool').trigger('change.select2');
	var districtOrgId = $('#projectedTestingDistrict').val();
	if (districtOrgId != 0) {
		$.ajax({
	        url: 'getOrgsBasedOnUserContext.htm',
	        data: {
	        	orgId : districtOrgId,
	        	orgType:'SCH',
	        	orgLevel:70
	        	},
	        dataType: 'json',
	        type: "GET"
		}).done(function(schoolOrgs) {
			$.each(schoolOrgs, function(i, schoolOrg) {
				$('#projectedTestingSchool').append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
			});		
			if (schoolOrgs.length == 1) {
				$("#projectedTestingSchool option").removeAttr('selected').next('option').attr('selected', 'selected');
				$("#projectedTestingSchool").trigger('change');
			}
			$('#projectedTestingSchool').trigger('change.select2');
		});
	}	
}

function getquerystring() {
	
    drawStackChart();
    
    displayLegends();
}

function loadUploadProjectedTestingScr(){
	$('#projectedTestingGraph').hide();
	$('#uploadProjectedTestingScr').show();
	$('#errorplaceTestingdiv').html('');
	$('#projectedTestingFileDataInput').val('');
	
	$("#uploadProjectedTestingGridTableId").jqGrid("clearGridData", true);
	
	
	$('#projectedTestingUploadForm').validate({
		ignore: "",
		rules: {
			projectedTestingFileDataInput: {
	      		required: true,
	      		extension: "csv"
	    	}
		},
		errorPlacement : function(error, element) {
	        $('#errorplaceTestingdiv').append(error);
	    }
	});
	
	$('input[id=projectedTestingFileData]').on("change",function() {
		$('#projectedTestingFileDataInput').val($('#projectedTestingFileData')[0].files[0].name);
	});
	
	$('#uploadProjectedTesting').on("click",function(event) {
			if($('#uploadProjectedTesting').attr("disabled")=="disabled") {
				event.preventDefault();
	    	} else {
	    		uploadProjectedTesting(null);
	    	}
	});
	
	showProjectedTestingUploadGrid();

	$(document).find("table").each(function() {
		$(this).attr('role', 'presentation')
	});
}

function showProjectedTestingUploadGrid(){ 

	  var uploadProjectedTestingGrid = $('#uploadProjectedTestingGridTableId');
	  $("#uploadProjectedTestingDiv div").show();
	  

	  var colModel = [
	                  {label: 'id', name: 'id', hidden: true, hidedlg: true, sorttype: 'int'},
	                  {label: 'Date', name: 'date', index: 'date', width:'100px', hidedlg: true, formatoptions: {newformat: 'm/d/Y'},
	                   formatter: function(cellValue, options, rowObject, action){
	                    return $.fn.fmatter.call(this, 'date', new Date(cellValue), $.extend({}, $.jgrid.formatter.date, options), rowObject, action);
	                   }
	                  },
	                  {label: 'Time', name: 'time', index: 'time',width:'100px',  hidedlg: true, formatoptions: {newformat: 'h:i:s A'},
	                      formatter: function(cellValue, options, rowObject, action){
	                       return $.fn.fmatter.call(this, 'date', new Date(cellValue), $.extend({}, $.jgrid.formatter.date, options), rowObject, action);
	                      }
	                     },
	                  {label: 'Status', name: 'status', index: 'status',width:'435px',  hidedlg: true,formatter: projectedTestingUploadSatusFormatter},
	                  {label: 'File', name: 'filePath', index: 'filePath', hidedlg: true,width:'100px',  formatter:extractProjectedTestingUploadLinkFormatter},
	                  {label: 'Organization Name', name: 'organizationName', index: 'organizationName', hidedlg: true,hidden:true},
	                  {label: 'Failed Count', name: 'failedCount', index: 'failedCount', hidedlg: true,hidden:true},
	                  {label: 'statusCheck', name: 'statusCheck', index: 'statusCheck', hidedlg: true,hidden:true}
	                 ];

	 $(uploadProjectedTestingGrid).jqGrid({
	  datatype: 'local',
	  width: '740px',
	  height: 'auto',
	  filterstatesave: true,
	  pagestatesave: true,
	  colModel: colModel,
	  filterToolbar: false,
	  rowNum: 10,
	  rowList: [5, 10, 20, 30, 40, 60, 90],
	  columnChooser: false, 
	  multiselect: false,
	  footerrow : true,
	  pager: '#uploadProjectedTestingGridPager',
	  sortname: 'id',
	  sortorder: 'DESC',
	  altclass: 'altrow',
	  altRows: true,
	  jsonReader: {
	         page: function (obj) {
	             return obj.page !== undefined ? obj.page : "0";
	         },
	         repeatitems:false,
	      root: function(obj) { 
	       return obj.rows;
	      } 
	     }
	  });
	 loadProjectedTestingUploadData(projectedTestinguploadCategoryCode);
	}


function loadProjectedTestingUploadData(){
	  $.ajax({
		  url: "getabUploadProgressStatus.htm?&categoryCode="+projectedTestinguploadCategoryCode,
	       type: "POST"
	  }).done(function(data) {
	            $('#uploadProjectedTestingGridTableId').jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
	  });
}

function projectedTestingUploadSatusFormatter(cellValue, options, rowObject){
	if( cellValue == "COMPLETED"){
		cellValue = 'Completed: Records Created/Updated: ' + rowObject.successCount + " Rejected: "+rowObject.failedCount  + " Alerts: "+rowObject.alertCount;
	}
	return cellValue;
}

function extractProjectedTestingUploadLinkFormatter(cellValue, options, rowObject){
	var status = rowObject.statusCheck;
	if ((status == "COMPLETED" && rowObject.failedCount > 0 || rowObject.alertCount > 0) || (status == "FAILED" ) ) {
		var dataOrganizationName=rowObject.organizationName;
		var currDate = new Date();
		var twoDigitMonth = ((currDate.getMonth().length+1) === 1)? (currDate.getMonth()+1) : '0' + (currDate.getMonth()+1);
		var dateStringForFile = twoDigitMonth+""+currDate.getDate().toString()+""+currDate.getFullYear().toString()
		+"_"+currDate.getHours().toString()+""+currDate.getMinutes().toString()+""+currDate.getSeconds().toString();
		var file = rowObject.fileName.split(".",1);
		var fileName = file +"_ProjectedTestingUpload_Errors_"+dateStringForFile+".csv";
		return '<a href="getUploadErrorFile.htm?uploadedId='+rowObject.id + '&fileName='+fileName+'"><img src="images/icon-csv-green.png" title="Click to download extract."/> </a>';
	}
	else
		return '<a> </a>';
}


function uploadProjectedTesting(continueOnWarning){
	
	var fd = new FormData();
	var date = new Date();
	var milliSec =date.getMilliseconds();
	var filedata = $('#projectedTestingFileData');
	var filelist = filedata[0].files;
	var file = filelist[0];
	if($('#projectedTestingUploadForm').valid()) {
		$('#uploadProjectedTesting').attr("disabled","disabled");
		$('body, html').animate({scrollTop:0}, 'slow');
        
		//fd.append('assessmentProgramId',$('#userDefaultOrganization').val());
		fd.append('selectedOrgId',$('#userDefaultOrganization').val());	
		fd.append('categoryCode',projectedTestinguploadCategoryCode);
		fd.append('reportUpload',"false");
		fd.append('date', date.getTime());
		fd.append('milliSec',milliSec);
		fd.append('uploadFile',file);
		if(typeof continueOnWarning != 'undefined' && continueOnWarning != null && continueOnWarning != '') {
		  	fd.append('continueOnWarning',continueOnWarning);
	  	}
		$('#projTestUploadReport').html('');

		$.ajax({
			url: 'uploadFileData.htm',//US16352: Modified
			data: fd,
			dataType: 'json',
			processData: false,
			contentType: false,
			cache: false,
			type: 'POST'
		}).done(function(data){
			loadProjectedTestingUploadData(projectedTestinguploadCategoryCode);
			if(data.showWarning) {
				$('#uploadProjectedTesting').removeAttr('disabled');
	    	} else if(data.errorFound) {
				$('#uploadProjectedTesting').removeAttr('disabled');	    		
            } 
	    	else if(data.nopermit){
	    		$('#uploadProjectedTesting').removeAttr('disabled');		    		
	    	}
	    	else {
	    		monitorProjectedTestingFile(data.uploadId);
	    	}
		}).fail(function(jqXHR,textStatus,errorThrown) {
			var e=errorThrown;
		});
	}  else {
		$(".studentARTSmessages").show();
		$('.requiredMessage').show();
		setTimeout("aart.clearMessages()", 3000);
		setTimeout(function(){ $(".studentARTSmessages").hide(); },3000);	
	}
}

function monitorProjectedTestingFile(uploadFileRecordId){
	$.ajax({
		url: 'monitorUploadFileStatus.htm',
        type: 'GET',
        cache: false,
        data: {uploadFileRecordId: uploadFileRecordId}
	}).done(function(data) {
    	if(data.uploadFileStatus === 'FAILED' || data.errorFound){
    		  clearProjectedTestingUploadFile();
    		  loadProjectedTestingUploadData(projectedTestinguploadCategoryCode);
    		  $('#uploadProjectedTesting').removeAttr('disabled');
      	} else if(data.uploadFileStatus === 'COMPLETED'){
      		  clearProjectedTestingUploadFile();
      		loadProjectedTestingUploadData(projectedTestinguploadCategoryCode);
    		  $('#uploadProjectedTesting').removeAttr('disabled');
    		
		} else {
			if(data.uploadFileStatus === "IN_PROGRESS"){
				$('#uploadProjectedTestingGridTableId').jqGrid("setCell", uploadFileRecordId, "status", data.status.charAt(0).toUpperCase() + data.status.toLowerCase().slice(1));
			}
			window.setTimeout(function(){monitorProjectedTestingFile(uploadFileRecordId);}, 90000);
		}  
	});	
}

function loadProjectedTestingUploadData(){
	$.ajax({
		  url: "getabUploadProgressStatus.htm?&categoryCode="+projectedTestinguploadCategoryCode,
		  type: "POST"
	}).done(function(data) {
	        $('#uploadProjectedTestingGridTableId').jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
	});
}

function clearProjectedTestingUploadFile(){
	$('#projectedTestingFileData').val("");
	projectedTestingFile.replaceWith( projectedTestingFile = projectedTestingFile.clone( true ) );
	$('#projectedTestingFileDataInput').val("");
}
function viewMyCalendarProjTesting(){
	
	projectedTestingAddNewRow();
	addingMonthstoDropDown();
	$('#projectedTestingGraph').hide();
	$('#viewMyCalendarPage').show();	
	var $gridAuto = $("#viewMyCalendarSessionGridTableId");
	$gridAuto.jqGrid('setGridParam',{
		datatype:"json", 
		url : 'getProjectedTestingViewMyCalendar.htm', 
		search: false, 
		postData: {
				   "filters":""
				   },
					gridComplete: function() {
						 var tableid=$(this).attr('id');      
				         var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
				          $.each(objs, function(index, value) {         
				          var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
				          $(value).attr('title',$(nm).text()+' filter');                          
				          });
					}
	}) .trigger("reloadGrid",[{page:1}]);
}
var months = {
	    January  	: "01",
	    February 	: "02",
	    March    	: "03",
	    April    	: "04",
	    May      	: "05",
	    June     	: "06",
	    July     	: "07",
	    August    	: "08",
	    September	: "09",
	    October 	: "10",
	    November 	: "11",
	    December	: "12"	    
};


var days = {
		 one		: "01",
		 two		: "02",
		 three		: "03",
		 four		: "04",
		 five		: "05",
		 six		: "06",
		 seven		: "07",
		 eight		: "08",
		 nine		: "09",
		 ten		: "10",
		 eleven		: "11",
		 twelve		: "12",
		 thirteen	: "13",
		 fourteen	: "14",
		 fifteen	: "15",
		 sixteen	: "16",
		 seventeen	: "17",
		 eighteen	: "18",
		 nineteen	: "19",
		 twenty		: "20",
		 twentyOne	: "21",
		 twentyTwo	: "22",
		 twentyThree: "23",
		 twentyFour	: "24",
		 twentyFive	: "25",
		 twentySix	: "26",
		 twentySeven: "27",
		 twentyEight: "28",
		 twentyNine	: "29",
		 thirty		: "30",
		 thirtyOne	: "31"  
};
function viewMyCalendarPrjTesting(){
	
	var $gridAuto = $("#viewMyCalendarSessionGridTableId");
	//Unload the grid before each request.
	$("#viewMyCalendarSessionGridTableId").jqGrid('clearGridData');
	$("#viewMyCalendarSessionGridTableId").jqGrid("GridUnload");
	var gridWidthForVO = $('#viewMyCalendarSessionGridTableId').parent().width();		
	//if(gridWidthForVO < 700) {
		gridWidthForVO = 1029;				
	//}
	var cellWidthForVO = gridWidthForVO/3;
	var cmforViewScorerGrid = null;
	
	if(editProjectedTesting){
	 cmforViewScorerGrid = [
		{ name : 'id', index : 'id', hidden : true, hidedlg : true},
		{ name : 'stateId', index : 'stateId', hidden : true, hidedlg : true},
		{ name : 'assessmentProgramId', index : 'assessmentProgramId', hidden : true, hidedlg : true},
		{ name : 'districtId', index : 'districtId', hidden : true, hidedlg : true},
		{ name : 'schoolId', index : 'schoolId', hidden : true, hidedlg : true},
        { name : 'districtName', index : 'districtName', width : 150, search : true,sorttype : 'text', hidden : false},
        { name : 'schoolName', index : 'schoolName', width : 150, sorttype : 'text', search : true, hidden : false},       
		{ name : 'month', index : 'month',sorttype : 'text', width : 100, search : true, hidden: false, formatter:imageFormat},
		{ name : 'gradeId', index : 'gradeId', hidden : true, hidedlg : true},
		{ name : 'gradeName', index : 'gradeName', width : 75, sorttype : 'text',search : true, hidden : false},
		{ name : 'projectionType', index : 'projectionType', width : 100, sorttype : 'text', search : true, hidden : false, formatter:projectionTypeFormatter},
		{ name : 'schoolYear', index : 'schoolYear', hidden : true, hidedlg : true},
		{ name : 'one', index : 'one',sortable:false, width : 20, search : false},
		{ name: 'two',index : 'two',sortable:false, width : 20, search : false},
		{ name: 'three', index : 'three',sortable:false, width : 25, search : false},
		{ name: 'four', index : 'four',sortable:false, width : 25, search : false},
		{ name: 'five',index : 'five',sortable:false, width : 25, search : false},
		{ name: 'six',index : 'six',sortable:false, width : 25, search : false},
		{ name: 'seven',index : 'seven',sortable:false, width : 25, search : false},
		{ name: 'eight',index : 'eight',sortable:false, width : 25, search : false},
		{ name: 'nine',index : 'nine',sortable:false, width : 25, search : false},
		{ name: 'ten',index : 'ten',sortable:false, width : 25, search : false},
		{ name: 'eleven',index : 'eleven',sortable:false, width : 25, search : false},
		{ name: 'twelve',index : 'twelve',sortable:false, width : 25, search : false},
		{ name: 'thirteen',index : 'thirteen',sortable:false, width : 25, search : false},
		{ name: 'fourteen',index : 'fourteen',sortable:false, width : 25, search : false},
		{ name: 'fifteen',index : 'fifteen',sortable:false, width : 25, search : false},
		{ name: 'sixteen',index : 'sixteen',sortable:false, width : 25, search : false},
		{ name: 'seventeen',index : 'seventeen',sortable:false, width : 25, search : false},
		{ name: 'eighteen',index : 'eighteen',sortable:false, width : 25, search : false},
		{ name: 'nineteen',index : 'nineteen',sortable:false, width : 25, search : false},
		{ name: 'twenty',index : 'twenty',sortable:false, width : 25, search : false},
		{ name: 'twentyOne',index : 'twentyOne',sortable:false, width : 25, search : false},
		{ name: 'twentyTwo',index : 'twentyTwo',sortable:false, width : 25, search : false},
		{ name: 'twentyThree',index : 'twentyThree',sortable:false, width : 25, search : false},
		{ name: 'twentyFour',index : 'twentyFour',sortable:false, width : 25, search : false},
		{ name: 'twentyFive',index : 'twentyFive',sortable:false, width : 25, search : false},
		{ name: 'twentySix', index : 'twentySix',sortable:false, width : 25, search : false},
		{ name: 'twentySeven', index : 'twentySeven',sortable:false, width : 25, search : false},
		{ name: 'twentyEight', index : 'twentyEight',sortable:false, width : 25, search : false},
		{ name: 'twentyNine', index : 'twentyNine',sortable:false, width : 25, search : false},
		{ name: 'thirty', index : 'thirty',sortable:false, width : 25, search : false},
		{ name: 'thirtyOne',index : 'thirtyOne',sortable:false, width : 25, search : false},
		{ name : 'modifiedDate', index : 'modifiedDate',align : 'center',hidedlg : false,search : true,
			formatoptions : {
				newformat : 'm/d/Y h:i:s A'
			},
			formatter : function(cellValue, options, rowObject, action) {
				return $.fn.fmatter.call(this, 'date', new Date(new Date(cellValue).toLocaleString("en-US", {timeZone: "America/Chicago"})), $.extend({}, $.jgrid.formatter.date, options),
						rowObject, action);
			}
		},
		{ name : 'modifiedBy', index : 'modifiedBy', search : true,sorttype : 'text', hidden : false},
	];
	
	} else{
		cmforViewScorerGrid = [
		               		{ name : 'id', index : 'id', hidden : true, hidedlg : true},
		               		{ name : 'stateId', index : 'stateId', hidden : true, hidedlg : true},
		               		{ name : 'assessmentProgramId', index : 'assessmentProgramId', hidden : true, hidedlg : true},
		               		{ name : 'districtId', index : 'districtId', hidden : true, hidedlg : true},
		               		{ name : 'schoolId', index : 'schoolId', hidden : true, hidedlg : true},
		                       { name : 'districtName', index : 'districtName', width : 150, search : true,sorttype : 'text', hidden : false},
		                       { name : 'schoolName', index : 'schoolName', width : 150, sorttype : 'text', search : true, hidden : false},
		                    { name : 'month', index : 'month',sorttype : 'text', width : 100, search : true, hidden: false},
		                    { name : 'gradeId', index : 'gradeId', hidden : true, hidedlg : true},
		                    { name : 'gradeName', index : 'gradeName', width : 75,sorttype : 'text', search : true, hidden : false},
		            		{ name : 'projectionType', index : 'projectionType', width : 100, sorttype : 'text', search : true, hidden : false, formatter:projectionTypeFormatter},
		               		{ name : 'schoolYear', index : 'schoolYear', hidden : true, hidedlg : true},
		               		{ name : 'one', index : 'one',sortable:false, width : 20, search : false},
		               		{ name: 'two',index : 'two',sortable:false, width : 20, search : false},
		               		{ name: 'three', index : 'three',sortable:false, width : 25, search : false},
		               		{ name: 'four', index : 'four',sortable:false, width : 25, search : false},
		               		{ name: 'five',index : 'five',sortable:false, width : 25, search : false},
		               		{ name: 'six',index : 'six',sortable:false, width : 25, search : false},
		               		{ name: 'seven',index : 'seven',sortable:false, width : 25, search : false},
		               		{ name: 'eight',index : 'eight',sortable:false, width : 25, search : false},
		               		{ name: 'nine',index : 'nine',sortable:false, width : 25, search : false},
		               		{ name: 'ten',index : 'ten',sortable:false, width : 25, search : false},
		               		{ name: 'eleven',index : 'eleven',sortable:false, width : 25, search : false},
		               		{ name: 'twelve',index : 'twelve',sortable:false, width : 25, search : false},
		               		{ name: 'thirteen',index : 'thirteen',sortable:false, width : 25, search : false},
		               		{ name: 'fourteen',index : 'fourteen',sortable:false, width : 25, search : false},
		               		{ name: 'fifteen',index : 'fifteen',sortable:false, width : 25, search : false},
		               		{ name: 'sixteen',index : 'sixteen',sortable:false, width : 25, search : false},
		               		{ name: 'seventeen',index : 'seventeen',sortable:false, width : 25, search : false},
		               		{ name: 'eighteen',index : 'eighteen',sortable:false, width : 25, search : false},
		               		{ name: 'nineteen',index : 'nineteen',sortable:false, width : 25, search : false},
		               		{ name: 'twenty',index : 'twenty',sortable:false, width : 25, search : false},
		               		{ name: 'twentyOne',index : 'twentyOne',sortable:false, width : 25, search : false},
		               		{ name: 'twentyTwo',index : 'twentyTwo',sortable:false, width : 25, search : false},
		               		{ name: 'twentyThree',index : 'twentyThree',sortable:false, width : 25, search : false},
		               		{ name: 'twentyFour',index : 'twentyFour',sortable:false, width : 25, search : false},
		               		{ name: 'twentyFive',index : 'twentyFive',sortable:false, width : 25, search : false},
		               		{ name: 'twentySix', index : 'twentySix',sortable:false, width : 25, search : false},
		               		{ name: 'twentySeven', index : 'twentySeven',sortable:false, width : 25, search : false},
		               		{ name: 'twentyEight', index : 'twentyEight',sortable:false, width : 25, search : false},
		               		{ name: 'twentyNine', index : 'twentyNine',sortable:false, width : 25, search : false},
		               		{ name: 'thirty', index : 'thirty',sortable:false, width : 25, search : false},
		               		{ name: 'thirtyOne',index : 'thirtyOne',sortable:false, width : 25, search : false},
		               		{ name : 'modifiedDate', index : 'modifiedDate',align : 'center',hidedlg : false,search : true,
		        				formatoptions : {
		        					newformat : 'm/d/Y h:i:s A'
		        				},
		        				formatter : function(cellValue, options, rowObject, action) {
		        					return $.fn.fmatter.call(this, 'date', new Date(new Date(cellValue).toLocaleString("en-US", {timeZone: "America/Chicago"})), $.extend({}, $.jgrid.formatter.date, options),
										rowObject, action);
		        				}
		        			},
		        			{ name : 'modifiedBy', index : 'modifiedBy', search : true,sorttype : 'text', hidden : false},
		               		];
	}
	
	//JQGRID
  	$gridAuto.scb({
		mtype: "POST",
		datatype : "local",
		width: gridWidthForVO,
		/*height:"200px",*/
		colNames : [ 'id','stateId','assessmentProgramId','District ID','School ID','District Name','School Name', 'Month', 'GradeId', 'Grade', 'Type', 'year', '1', '2', '3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20',
		             '21','22','23','24','25','26','27','28','29','30','31','Last Modified','Modified By'
		           ],
	  	colModel :cmforViewScorerGrid,
		rowNum : 10,
		multiselect:false,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		pager : '#viewMyCalendarSessionGridPager',
		sortname : '',
        sortorder: 'asc',
        refresh: false,
		columnChooser : false,
		loadonce: false,
		viewable: false,
		localReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        }
	    },
  		jsonReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        },
	        repeatitems:false,
	    	root: function(obj) { 
	    		return obj.rows;
	    	} 
	    },
	    beforeSelectRow: function (rowid, e) {	
	    	return true;
	    },
	    onSelectRow: function(rowid, status, e){
	    	
	    }, 
	    onSelectAll: function(rowids, status, e){
	    	
	    },
	    beforeRequest: function() {
	    	$('.multidatepicker').pickmeup('hide');
	     
	    },
	    gridComplete: function() {	            		            	
	    	
	    },
	    loadComplete:function(){

		    var selectedDates= [];			
	    	$('.multidatepicker').pickmeup({
	    		position		: 'right',		
				mode	        : 'multiple',
				class_name 		: 'gridMultiDatepicker',
				select_month	: false,
				select_year		: false,
				prev 			: '',
				next			: '',
				first_day		: 0,
				min         : new Date(),
				before_show: function(){
					$('.gridMultiDatepicker .hiddenSavetype').attr("value" ,"active");
					$('.gridAddNewMultiDatepicker .hiddenSavetype').attr("value" ,"inactive");
					$('.gridMultiDatepicker .multicalenSavebtn').show();
			    },	
			    change: function() {	
			    	var dates = $(this).pickmeup('get_date',true);
			    	if(dates==''){
			    		$('.gridMultiDatepicker .multicalenSavebtn').hide();
			    	}	
			    	else{
			    		$('.gridMultiDatepicker .multicalenSavebtn').show();
			    	}
			    },
				show: function() {
					selectedDates =[];
					var rowId = $(this).attr("data-rowId");
					var data = $("#viewMyCalendarSessionGridTableId").jqGrid('getRowData', rowId);					
					var month = $(this).attr("data-month");
					var year = data.schoolYear;					
					var monthNumber=months[month];						
					var schoolName = data.schoolName;
					var projectionType=data.projectionType;
					var gradeName=data.gradeName;
					var modifiedDate = data.modifiedDate;
					var modifiedBy = data.modifiedBy;
					$.each( days, function( key, value ) {
						if(data[key]=='x')  selectedDates.push(value+"-"+monthNumber+"-"+year);						
					});	
					$('#monthDatePickerdiv_'+rowId).pickmeup('set_date',selectedDates);
					$('.gridMultiDatepicker .hiddenValuesDatePicker').attr("id" ,rowId);
					$('.gridMultiDatepicker .hiddenValuesDatePicker').attr("data-month" ,month);
					if(schoolName!=null && schoolName!=undefined) $(".gridMultiDatepicker #divLabelwidth").html(schoolName);
					if(gradeName!=null && gradeName!=undefined) $(".gridMultiDatepicker #gradeLabelwidth").html(gradeName);
					if(projectionType!=null && projectionType!=undefined) $(".gridMultiDatepicker #projectionTypeLabelwidth").html(projectionType);
				},
				render: function(date) {				
					if(selectedDates.length>0){
						 if ($.inArray(date.getTime(), selectedDates) > -1){
				                return {			                   
				                    class_name : 'disabledhighlight'
				                }
				          }
					}
	            } 
			    
		    }); 
		var ids = $(this).jqGrid('getDataIDs');
		var tableid = $(this).attr('id');	    
		var objs = $('#gbox_' + tableid).find('[id^=gs_]');
		$('#cb_' + tableid).attr('title','Projected Testing Grid All Check Box');
		$.each(objs, function(index, value) {
			var nm = $('#jqgh_' + tableid + '_'+ $(value).attr('name'));
			$(value).attr('title', $(nm).text() + ' filter');
		});
	    }
	});
   	$gridAuto.jqGrid('setGridParam',{
		postData: { "filters": ""}
	}).trigger("reloadGrid",[{page:1}]);
}

function imageFormat( cellvalue, options, rowObject ){
	var rowId = options.rowId;	
	var year = rowObject.schoolYear;	
	var monthNumber=months[cellvalue];	
	var date = new Date();
	var month = parseInt(date.getMonth())+1;
	try{
		if(year<date.getFullYear()) {
			return '<div class="monthLabelDiv" >'+cellvalue+'</div></div>';	
		}else{ 
			if(year==date.getFullYear() && monthNumber<month){
				return '<div class="monthLabelDiv" >'+cellvalue+'</div></div>';	
			}
			else{
				return '<div class="monthLabelDiv" >'+cellvalue+'</div><div data-rowId ="'+rowId+'"  data-month ="'+cellvalue+'" class="monthDatePickerdiv multidatepicker" id="monthDatePickerdiv_'+rowId+'" ></div>';
			}		
		}	
	}
	catch(e){
		return '<div class="monthLabelDiv" >'+cellvalue+'</div></div>';
	}		
}

function defaultFormatter(cellvalue, options, rowObject){					
    return "testing";
}

function projectionTypeFormatter(cellvalue, options, rowObject) {
	if (rowObject.projectionType === "S") {
		return "Scoring";
	} else if (rowObject.projectionType === "T") {
		return "Testing";
	}
}

function viewProjectedBackBtn(){	
	getquerystring();
	$('#projectedTestingGraph').show();
	$('#viewMyCalendarPage').hide();
	$('#projectedTestingType').val("0").trigger('change.select2');
	$(".gridAddNewMultiDatepicker #divLabelwidth").html('');
	$(".gridAddNewMultiDatepicker #gradeLabelwidth").html('');
	$(".gridAddNewMultiDatepicker #projectionTypeLabelwidth").html('');
}

function viewProjectedBackFromUpload(){	
	getquerystring();
	$('#uploadProjectedTestingScr').hide();
	$('#projectedTestingGraph').show();
	$('#projectedTestingType').val("0").trigger('change.select2');
	$(".gridAddNewMultiDatepicker #divLabelwidth").html('');
	$(".gridAddNewMultiDatepicker #gradeLabelwidth").html('');
	$(".gridAddNewMultiDatepicker #projectionTypeLabelwidth").html('');
}
function drawStackChart() {
	
	d3.json("./viewProjectedTestingChart.htm", function(barChartData) {
		var barChartWidth = 900;
		$('#verticalSVG').html('');
		$('#horizontalSVG').html('');
		$('#verticalSVGLabel').html('');
		if(barChartData.length > 20){
			barChartWidth = barChartData.length * 45;
		}
		//Draw Stack Chart
	    var marginStackChart = { top: 0, right: 20, bottom: 60, left: 0 },
	            widthStackChart = barChartWidth - marginStackChart.left - marginStackChart.right,
	            heightStackChart = 350 - marginStackChart.top - marginStackChart.bottom;

	    var xStackChart = d3.scaleBand()
	            .range([0, widthStackChart])
	            .padding(0.1);
	    var yStackChart = d3.scaleLinear()
	                .range([heightStackChart, 60]);
	    var keys = d3.keys(barChartData[0]).filter(function (key) { return key !== "testDate"; });
	    var colorMap = {"kap":"#2FA7DF", "kelpa2":"#e9841c", "cpass":"#a8a7a5", "dlm":"#f0cf0e", "scoring":"#CE39FF", "pltw":"#2FA7DF"};
	    var colorKeys = [];
	    keys.forEach(function(item, index){ 
	    	colorKeys.push(colorMap[item]);
	    });
	    var colorStackChart = d3.scaleOrdinal(colorKeys);

	    var canvasStackChart = d3.select("#horizontalSVG").append("svg")
	        .attr("width", widthStackChart + marginStackChart.left + marginStackChart.right)
	        .attr("height", heightStackChart + marginStackChart.top + marginStackChart.bottom)
	        .attr("alt","horizontal")
	        .attr("title","horizontal")
	        .append("g")
	        .attr("transform", "translate(" + marginStackChart.left + "," + marginStackChart.top + ")");

	    colorStackChart.domain(d3.keys(barChartData[0]).filter(function (key) { return key !== "testDate"; }));

	    barChartData.forEach(function (d) {
	        var y0 = 0;
	        d.assessments = colorStackChart.domain().map(function (name) { return { name: name, y0: y0, y1: y0 += +d[name] }; });
	        d.total = d.assessments[d.assessments.length - 1].y1;
	    });

	    xStackChart.domain(barChartData.map(function (d) { return d.testDate; }));
	    yStackChart.domain([0, d3.max(barChartData, function (d) { return (d.total + 5); })]);

	    canvasStackChart.append("g")
	    .attr("class", "x axis")
	    .attr("transform", "translate(0," + heightStackChart + ")")
	    .call(d3.axisBottom(xStackChart)).selectAll("text")	
        .style("text-anchor", "end")
        .style("font-size", "1.2em")
        .style("font-weight", "bold")
        .attr("dx", "-.8em")
        .attr("dy", ".15em")
        .attr("transform", function(d) {
            return "rotate(-75)" 
            });
        // Dynamically get max no of digits and expand/shrink y axis values block.
	    var yAxisWidth = (String(yStackChart.domain()[1].toLocaleString()).length * 5) + 5;
	    d3.select("#verticalSVG").append("svg")
        .attr("width", yAxisWidth + marginStackChart.right)
        .attr("height", heightStackChart + marginStackChart.top + marginStackChart.bottom)
        .attr("alt","vertical")
        .attr("title","vertical")
        .append("g")
        .attr("class", "y axis")
        .style("font-weight", "bold")
        .attr("transform", "translate("+((yAxisWidth - 5) + marginStackChart.right)+"," + marginStackChart.top + ")")
        .call(d3.axisLeft(yStackChart));
        
        d3.select("#verticalSVGLabel").append("svg")
        .attr("width", 0 + marginStackChart.right)
        .attr("height", heightStackChart + marginStackChart.top + marginStackChart.bottom)
        .attr("alt","vertical label")
        .attr("title","vertical label")
        .append("g")
        .append("text")
        .attr("transform", "rotate(-90)")
        .attr("y", 6)
        .attr("x", -175)
        .attr("dy", ".71em")
        .style("text-anchor", "middle")
        .text("Estimated # test sessions");

	    var testDates = canvasStackChart.selectAll(".testDate")
	    .data(barChartData)
	    .enter().append("g")
	    .attr("class", "g")
	    .attr("transform", function (d) { return "translate(" + xStackChart(d.testDate) + ",0)"; });

	    testDates.selectAll("rect")
	    .data(function (d) { return d.assessments; })
	    .enter().append("rect")
	    .attr("width", xStackChart.bandwidth())
	    .attr("y", function (d) { return yStackChart(d.y1); })
	    .attr("height", function (d) { return yStackChart(d.y0) - yStackChart(d.y1); })
	    .style("fill", function (d) { return colorStackChart(d.name); })
	    .on("mouseover", function() { $(this).next().show(); })
            .on("mouseout", function() { $(this).next().hide(); })
            .on("mousemove", function(d) {
            	$(this).next().show();
              })
      .insert("text")
      .attr("width", xStackChart.bandwidth())
      .attr("x", (Math.round(xStackChart.bandwidth()/3 * 100) / 100))
      .attr("y", function (d) { return yStackChart(d.y1); })
        .attr("dy", "1em")
        .style("text-anchor", "middle")
        .style("display", "none")
        .attr("font-size", "12px")
        .attr("font-weight", "bold")
        .text(function(d) {return (Math.round((d.y1-d.y0)*100)/100)});
      
      testDates.selectAll("rect").each(function(d, i){
      	var textEl = $(this).find('text');
      	$(this).after(textEl);
     	});
	 
	});
	
}

function displayLegends(){
	var count=1;
	$("#assessmentPrgrmsLegendIcons").html('');
	$.ajax({
		url : 'getBarChartAssessmentPrograms.htm',
		type : 'GET',
		cache : false,
		data : {}
	}).done(function(data) {
			$.each(data, function(key, value) {
				$("#assessmentPrgrmsLegendIcons").append("<div id='rectangle"+count+"' class='rectangle' />"+"<span>"+key+"</span>");
				$('#rectangle'+count).css('background',value);
				count++;
			});
	});
}

function uploadProjectedTestingFile(event){
	
	if(event.type=='keypress'){
		if(event.which !=13){
			return false;
		}
	}
$('input[id=projectedTestingFileData]').trigger("click");
}
