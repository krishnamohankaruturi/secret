$( document ).ready(function() {
	$("#messages").hide();
	//testCoordinationInit();
	$('.nav-tabs a[href="#tabs_ticketing"]').tab('show');
	viewTestSessions();
});

function resetTestCoordination(){
	$('input[id*="gs_"]').val("");
	$('#dataTable').hide();
	
	$('#divViewDailyAccessCodes').hide();
	$('#divViewTestSessions').hide();
	$('.error').hide();
	clearTCSearchFilterValuesFromSession();
}
var selectedDAC = [];
var contentAreaGradeIdList = [];



function viewTestSessions(){
	$('#divViewTestSessions').show();
	$('#tsSearchFilterErrors').html("");
	$('#tsSearchFilterMessage').html("").hide();
	$('#divViewDailyAccessCodes').hide();
	var assessmentProgramCode = $("#userDefaultAssessmentProgram option:selected").text();
	if(getFromSessionStorage("tsAssessmentProgramId")!=null && 
			getFromSessionStorage("tsTestingProgramId")!=null && 
			getFromSessionStorage("tsDistrictOrgId")!=null && 
			getFromSessionStorage("tsSchoolOrgId")!=null){
		reLoadARTSData();
	} else {
		var path='viewAutoRegistrationTestSessions';
		$('#divViewTestSessions').load("configuration.htm?path="+path, function() {
			$('#breadCrumMessage').text("Test Coordination");
			$('#breadCrumMessageTag').text("");
			window.localStorage.removeItem("ARTSgridParamsData");
			
	    	$(".studentARTSmessages").hide();
			setTimeout("aart.clearMessages()", 5000);
			setTimeout(function(){ $(".studentARTSmessages").hide(); },5000);
			if(isTeacher && assessmentProgramCode=='PLTW'){
				$("#tsTestSessionsDiv").show();
			} else {
				loadAutoRegisteredTestSessions();
				resetTestCoordinationTab();
			}
		});
	}
}

function viewDailyAccessCodes(){
	$('#divViewTestSessions').hide();
	clearTCSearchFilterValuesFromSession();
	
	$('#divViewDailyAccessCodes').show();
	 
	$('#divGridDisclaimer1').hide();
	$('#divBundleDisclaimer').hide();
	$('#divSecurityDisclaimer').hide();
	$('#dataTable').hide();
	
	 $('#dacAssessmentPrograms, #dacTestDay').select2({
		placeholder:'Select',
		multiple: false
	});
	
	
	 $('#dacSearchFilterForm')[0].reset();
	
	var tsAPSelect = $('#dacAssessmentPrograms'), tsOptionText='';
	$('.messages').html('').hide();
	tsAPSelect.find('option').filter(function(){return $(this).val() > 0}).remove().end();			
	
	$.ajax({
		url: 'getAssessmentProgramsByUserSelected.htm',
		dataType: 'json',
		type: "POST"
	}).done(function(dacAssessmentPrograms) {				
			if (dacAssessmentPrograms !== undefined && dacAssessmentPrograms !== null && dacAssessmentPrograms.length > 0) {
				$.each(dacAssessmentPrograms, function(i, dacAssessmentProgram) {
					tsOptionText = dacAssessmentPrograms[i].programName;
					if(dacAssessmentProgram.id == $('#hiddenCurrentAssessmentProgramId').val()){
						tsAPSelect.append($('<option selected=\''+'selected'+'\'></option>').val(dacAssessmentProgram.id).html(tsOptionText));
					} else {
						tsAPSelect.append($('<option></option>').val(dacAssessmentProgram.id).html(tsOptionText));
					}
				});

				
				var filterSelectedValue = getFromSessionStorage("dacAssessmentProgramId");				
				if(typeof filterSelectedValue  != 'undefined' && filterSelectedValue != null) {							
					tsAPSelect.val(filterSelectedValue);
					$("#dacAssessmentPrograms").trigger('change');
				} else if (dacAssessmentPrograms.length == 1) {
					tsAPSelect.find('option:first').prop('selected',false).next('option').attr('selected', 'selected');
					$("#dacAssessmentPrograms").trigger('change');
				}
			} else {
				$('body, html').animate({scrollTop:0}, 'slow');
				$('#tsSearchFilterErrors').html("<fmt:message key='newreport.no.assessmentprogram'/>").show();
			}
			$('#dacAssessmentPrograms').trigger('change.select2');
	});
	
	
	
	$.ajax({
		url: 'getDailyAccessCodesTestDays.htm',
		dataType: 'json',
		type: "POST"
	}).done(function(testDays) {
			var tsDACTestDaySelect = $('#dacTestDay');
			tsDACTestDaySelect.empty();
			tsDACTestDaySelect.append($('<option></option>').val("").html("Select"));
			
			if (testDays !== undefined && testDays !== null && testDays.length > 0) {
				$.each(testDays, function(i, testDay) {
					var testDayArray = testDay.split(" ");
					tsDACTestDaySelect.append($('<option></option>').val(testDayArray[1]).html(testDay));
				});
				
				var filterSelectedValue = getFromSessionStorage("dacTestDayStr");				
				if(typeof filterSelectedValue  != 'undefined' && filterSelectedValue != null) {							
					tsDACTestDaySelect.val(filterSelectedValue);
					$("#dacTestDay").trigger('change');
				}/* else if (testDays.length == 1) {
					tsDACTestDaySelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
					$("#dacTestDay").trigger('change');
				}*/
			} else {
				$('body, html').animate({scrollTop:0}, 'slow');
				$('#tsSearchFilterErrors').html("Test Days not Found").show();
			}
			$('#dacTestDay').trigger('change.select2');
	});
	
	
	setTimeout("aart.clearMessages()", 5000);
	setTimeout(function(){ $(".studentARTSmessages").hide(); },5000);
	
	
	
	$('#dacTestDay').on("change",function(e) {
		e.preventDefault();
		$('#tsSearchFilterErrors').html("");
		$('#tsSearchFilterMessage').html("").hide();			
		$('#divGridDisclaimer1').show();			
		$('#dataTable').show();
		$('#gs_contentAreaName').val("");
		$('#gs_gradeCourseName').val("");
		
		$('#dacSearchFilterForm').validate({
    		ignore: "",
    		rules: {
    			dacAssessmentPrograms: {required: true},
    			dacTestDay: {required: true},    			
    			
    		},
		    messages:{
		    	dacAssessmentPrograms: "This field is required",
		    	dacTestDay: "This field is required"
		    }
    	});
		
		if($('#dacSearchFilterForm').valid()){					
			loadDailyAccessCodes();
		}
		
		
	});
	
	
	$('#doneBtn').on("click",function(e) {
		e.preventDefault();
		$('#tsSearchFilterErrors').html("");
		$('#tsSearchFilterMessage').html("").hide();
		
		testCoordinationInit();
	});
	
	$('#viewAccessCodesPdf').on("click",function(e) {
		e.preventDefault();
		var contentAreaIdGradeIdList = [];
		$.each(contentAreaGradeIdList, function(i, item) {
			var contentareaid = item.split('-')[0];
			var gradeid = item.split('-')[1];
			contentAreaIdGradeIdList.push({"contentAreaId":contentareaid,"gradeCourseId":gradeid});
		});
		downloadDailyAccessCodes(contentAreaIdGradeIdList, 'PDF');
		
	});
	
	$('#viewAccessCodesCsv').on("click",function(e) {
		e.preventDefault();
		
		var contentAreaIdGradeIdList = [];
		$.each(contentAreaGradeIdList, function(i, item) {
			var contentareaid = item.split('-')[0];
			var gradeid = item.split('-')[1];
			contentAreaIdGradeIdList.push({"contentAreaId":contentareaid,"gradeCourseId":gradeid});
		});
		downloadDailyAccessCodes(contentAreaIdGradeIdList, 'CSV');
	});
	
	$('#selectTestDayQuickHelp').on('mouseover',function(){
		$(".testCoordinationQuickHelpHintPopup").show();
	});
	$('#selectTestDayQuickHelp').on('mouseout',function(){
		$(".testCoordinationQuickHelpHintPopup").hide();
	});	
}

function testCoordinationInit(){
	clearTCSearchFilterValuesFromSession();
	$('#divViewTestSessions').hide();
	$('#dataTable').hide();
	$('#divViewDailyAccessCodes').hide();
}

function loadDailyAccessCodes(){
	var gradeId='';
	var gradeName='';
	var isGradeCourseName=true;
	var isGradeBandName=true;
	var sortColumnName='';
	var userDefaultAssessmentProgram = $("#userDefaultAssessmentProgram option:selected").text();
	if(userDefaultAssessmentProgram!=null && userDefaultAssessmentProgram!=undefined && userDefaultAssessmentProgram!=""){
		if(userDefaultAssessmentProgram=='PLTW') {
			gradeId='gradeBandId';
			gradeName='gradeBandName';
			isGradeBandName=false;
			sortColumnName='contentAreaName,gradeBandName';
		}
		else{
			gradeId='gradeCourseId';
			gradeName='gradeCourseName';
			isGradeCourseName=false;
			sortColumnName='contentAreaName,gradeCourseName';
		}
	}
	var dacAssessmentProgramId = "";
	var dacTesDayVal = "";
	selectedDAC = [];		
	contentAreaGradeIdList = [];
	dacAssessmentProgramId = $('#dacAssessmentPrograms').val();
	dacTesDayVal = $('#dacTestDay').val();	
	//Begin Grid
	
	var grid_width = $('.kite-table').width();
	if(grid_width == 100 || grid_width == 0) {
		grid_width = 1048;				
	}
    var cell_width = grid_width/3;

	var $gridAuto = $("#dailyAccessCodesTable");
	
	var showHideCourse = false;
	if($('#currentAssessmentProgram').val()=="PLTW"){
		showHideCourse = true;
	}

	var cmforDailyAccessCodes = [
	             				{ name : 'contentAreaId', index : 'contentAreaId',label:'contentAreaIdDailyAccess',  width : cell_width, search : false, hidden: true, hidedlg: true },
	             				
	             				{ name : 'gradeCourseId', index : 'gradeCourseId', label:'gradeCourseIdDailyAccess', width : cell_width, 
	             					search : false,hidden :true,hidedlg : true },
	             				
	             				{ name : 'gradeBandId', index : 'gradeBandId', label:'gradeBandIdDailyAccess', width : cell_width, 
		             					search : false,hidden :true,hidedlg : true },
	             				
		             			{ name : 'contentAreaName', index : 'contentAreaName',label:'contentAreaNameDailyAccess', width : cell_width, hidden : false, search : true, hidedlg : true, sortable : true},
	             					
	             				{ name : 'gradeCourseName', index : 'gradeCourseName',label:'gradeCourseNameDailyAccess', width : cell_width, search : true, hidden : (isGradeCourseName || showHideCourse), hidedlg : (isGradeCourseName || showHideCourse), sortable : true },
	             				
	             				{ name : 'gradeBandName', index : 'gradeBandName', label:'gradeBandNameDailyAccess', width : cell_width, 
	             					search : true, hidden : isGradeBandName, hidedlg : true, sortable : true},
	             					
	             				{ name : 'dailyAccessCodesExists', index : 'dailyAccessCodesExists',label:'dailyAccessCodesExistsDailyAccess', width : cell_width-20, search : false, hidden : false, sortable : false, formatter: printDacPdfLinkFormatter, unformat: printDacPdfLinkUnFormatter }
	             			];
	
	var cnforDailyAccessCodes = ['', '', '', $('#currentAssessmentProgram').val()=="PLTW" ? 'Course' : 'Subject', 'Grade', 'Grade', 'Daily Access Codes'];

	
	//Unload the grid before each request.
//	$gridAuto.jqGrid('GridUnload');	
	
	//JQGRID
	$gridAuto.scb({
		mtype: "POST",
		datatype : "local",
		width: grid_width,
		colModel: cmforDailyAccessCodes,
	  	colNames: cnforDailyAccessCodes,
		rowNum : 10,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		height : 'auto',
		pager : '#dailyAccessCodesDiv',
		// F-820 Grids default sort order
		sortname : sortColumnName,
		sortorder: 'asc',
		multiselect: true,
		pagestatesave:true,
		filterstatesave:true,
		hoverrows: false,
	    beforeRequest: function() {
		
	    	//Set the page param to lastpage before sending the request when 
			  //the user entered current page number is greater than lastpage number.
			var currentPage = $(this).getGridParam('page');
            var lastPage = $(this).getGridParam('lastpage');
            
             if (lastPage!= 0 && currentPage > lastPage) {
            	 $(this).setGridParam('page', lastPage);
            	$(this).setGridParam({postData: {page : lastPage}});
            }
	    },
	    loadComplete: function() {
	    	if($(this).getGridParam('datatype') == 'json') {
		    	//Retrieve any previously stored rows for this page and re-select them.
	        	if(selectedDAC) {
	        		$.each(selectedDAC, function (index, value) {
	        			$("#dailyAccessCodesTable").setSelection(value, false);
	        		});
	        	}

			    var recs = parseInt($("#dailyAccessCodesTable").getGridParam("records"));
			    
			    if(recs > 0){
					$('#divBundleDisclaimer').show();
					$('#divSecurityDisclaimer').show();
				}else{
					$('#divBundleDisclaimer').hide();
					$('#divSecurityDisclaimer').hide();
				}
			    
				if (isNaN(recs) || recs == 0) {						    
				     
				     //Set min height of 1px on no records found
				     $('.jqgfirstrow').css("height", "1px");
				 } else {
				     $("#gbox_dailyAccessCodesTable").show();
				 }					
				
	    	}
	    	 var ids = $(this).jqGrid('getDataIDs');         
	         var tableid=$(this).attr('id');      
	            for(var i=0;i<ids.length;i++)
	            {         
	                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'contentAreaName') +' '+$(this).getCell(ids[i], gradeName)+ ' Check Box');
	                $('#jqg_'+tableid+'_'+ids[i]).removeAttr('aria-checked');
	            }
	            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
	             $('#cb_'+tableid).attr('title','View Daily Acces Grid All Check Box');
	             $('#cb_'+tableid).removeAttr('aria-checked');
	             $.each(objs, function(index, value) {         
	              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
	                    $(value).attr('title',$(nm).text()+' filter');                          
	                    });
	    },onSelectRow: function(id, status) {
    		//Add/remove items to/from selectedDAC array based on checkbox selection/deselection. 
    		if(status) {
				//User checks the DAC record.
    			selectedDAC.push(id);						
    			var rowData = jQuery(this).getRowData(id);
    			var contentareaid = rowData['contentAreaId'];
    			var gradeid = rowData[gradeId];				
    			
    			contentAreaGradeIdList.push(rowData['contentAreaId'] + "-" + rowData[gradeId]);
			} else {
				//User unchecks.							
				selectedDAC = $.grep(selectedDAC, function(value) {
					return value != id;
				});
				
				
				var gridRow = $('#dailyAccessCodesTable').jqGrid('getRowData',id);
				
				contentAreaGradeIdList = $.grep(contentAreaGradeIdList, function(value) {
					return value != gridRow['contentAreaId'] + "-" + gridRow[gradeId];
				});
			}

    		//Check SelectAllCheckbox header on rowNum change.
    		var recordCount = $("#dailyAccessCodesTable").getGridParam('reccount');
    		var checkboxChecckedCount = 0;
    		$("input[type='checkbox']").each(function() {
    			if(this.name != "" && 
    					this.name.indexOf("jqg_dailyAccessCodesTable_") != -1 && 
    					this.checked) {
    				checkboxChecckedCount++;
    			}
    	    });
        	if(recordCount == checkboxChecckedCount) {
        		$("#cb_dailyAccessCodesTable").attr("checked", "checked");
        	}
		},
	    onSelectAll: function(id, status) {
			if(status) {
				//User checks the dac.
				for(var i=0; i<id.length; i++) {
					selectedDAC.push(id[i]);
					var gridRow = $('#dailyAccessCodesTable').jqGrid('getRowData',id[i]);
					contentAreaGradeIdList.push(gridRow['contentAreaId'] + "-" + gridRow[gradeId]);
				}
			} else {
				//User unchecks.
				for(var i=0; i<id.length; i++) {
					selectedDAC = $.grep(selectedDAC, function(value) {
						return value != id[i];
					});							
					
					var gridRow = $('#dailyAccessCodesTable').jqGrid('getRowData',id[i]);
					
					contentAreaGradeIdList = $.grep(contentAreaGradeIdList, function(value) {
						return value != gridRow['contentAreaId'] + "-" + gridRow[gradeId];
					});
				}
			}
		},
		beforeSelectRow: function (rowid, e) {
		    var $myGrid = $(this),
		        i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),
		        cm = $myGrid.jqGrid('getGridParam', 'colModel');
		    return (cm[i].name === 'cb');
		}
	});
	
	
	//End Grid
	
	//Clear the previous error messages
	setTimeout("aart.clearMessages()", 0);
	
	$gridAuto.jqGrid('setGridParam',{
		datatype:"json", 
		search: false,
		sortname : sortColumnName,
		sortorder: 'asc',
		url : 'getDailyAccessCodes.htm?q=1',
		postData : {
			filters: null,
			assessmentProgramId : dacAssessmentProgramId,
			testDayValue : dacTesDayVal
		},
	}).trigger("reloadGrid",[{page:1}]);
	
	$("#dailyAccessCodesTablebuttonColumnChooser").hide();
	$("#view_dailyAccessCodesTable").hide();
	$("#refresh_dailyAccessCodesTable").hide();
	
}	

function printDacPdfLinkFormatter(cellvalue, options, rowObject) {
	var htmlString = "N.A";	
	
	var userDefaultAssessmentProgram = $("#userDefaultAssessmentProgram option:selected").text();
		if(userDefaultAssessmentProgram=='PLTW') {
			htmlString = '<div><a class="pdfLink" href="javascript:void(0)" onclick="downloadPltwDacCaGb('+rowObject[0]+', '+rowObject[2]+', \''+"PDF"+'\')"><img alt="Daily Access Codes PDF" style="display:inline-block; border: 0px solid;" src="images/icon_pdf0210.png"></a>' 
			+'<span style="padding-left:15px;">&nbsp;</span><a class="pdfLink" href="javascript:void(0)" onclick="downloadDacCaGc('+rowObject[0]+', '+rowObject[2]+', \''+"CSV"+'\')"><img alt="Daily Access Codes CSV" style="display:inline-block; border: 0px solid;" src="images/icon-csv1209.png"></a></div>';
    return htmlString;
	}else{
	htmlString = '<div><a class="pdfLink" href="javascript:void(0)" onclick="downloadDacCaGc('+rowObject[0]+', '+rowObject[1]+', \''+"PDF"+'\')"><img alt="Daily Access Codes PDF" style="display:inline-block; border: 0px solid;" src="images/icon_pdf0210.png"></a>' 
			+'<span style="padding-left:15px;">&nbsp;</span><a class="pdfLink" href="javascript:void(0)" onclick="downloadDacCaGc('+rowObject[0]+', '+rowObject[1]+', \''+"CSV"+'\')"><img alt="Daily Access Codes CSV" style="display:inline-block; border: 0px solid;" src="images/icon-csv1209.png"></a></div>';
    return htmlString;
	}
}		

function printDacPdfLinkUnFormatter(cellvalue, options, rowObject) {
    return;
}

function setDACFilterValuesToSession() {
	
	setInSessionStorage("dacAssessmentProgramId", $('#dacAssessmentPrograms').val());
	setInSessionStorage("dacTestDayStr", $('#dacTestDay').val());
	var filterSelectedValue = getFromSessionStorage("dacAssessmentProgramId");
}

function downloadPltwDacCaGb(contentAreaId, gradeBandId, fileType) {	
	var contentAreaGradeList = [];
	contentAreaGradeList.push({"contentAreaId":contentAreaId,"gradeBandId":gradeBandId});
	
	downloadDailyAccessCodes(contentAreaGradeList, fileType);
}

function downloadDacCaGc(contentAreaId, gradeCourseId, fileType) {	
	var contentAreaGradeList = [];
	contentAreaGradeList.push({"contentAreaId":contentAreaId,"gradeCourseId":gradeCourseId});
	
	downloadDailyAccessCodes(contentAreaGradeList, fileType);
}

function downloadDailyAccessCodes(contentAreaGradeList, fileType) {
	aart.clearMessages();
	
	var downloadUrl = 'downloadDailyAccessCodesAsPDF.htm';
	
	if(fileType === 'PDF'){
		downloadUrl = 'downloadDailyAccessCodesAsPDF.htm';
	}else if(fileType === 'CSV'){
		downloadUrl = 'downloadDailyAccessCodesAsCSV.htm';
	}
	if (contentAreaGradeList !== undefined &&  contentAreaGradeList.length > 0) {
		var csrfParam = $('meta[name=_csrf_parameter]').attr("content");
		var pdata = {"assessmentProgramId": $('#dacAssessmentPrograms').val(), 
        		"testDay" : $('#dacTestDay').val(), 
        		"contentAreaGradeList": JSON.stringify(contentAreaGradeList) 
        	};
		pdata[csrfParam] = $('meta[name='+csrfParam+']').attr("content");
		$.fileDownload(downloadUrl, {
	        //preparingMessageHtml: "please wait...",
	        failMessageHtml: "There was a problem generating Daily Access Codes, please try again.",
	        httpMethod: "POST",
	        data: pdata
	    });
	} else {
		$('#select_dailyaccesscode_errors').show();
		setTimeout("aart.clearMessages()", 3000);
	}
}



function reLoadARTSData(){	
	$("#tcActions option[value='viewSessions']").prop('selected','selected');	
	$('#divViewTestSessions').show();	
	$('#tsSearchFilterErrors').html("");
	$('#tsSearchFilterMessage').html("").hide();
	$('#divViewDailyAccessCodes').hide();
	var path='viewAutoRegistrationTestSessions';
	$('#divViewTestSessions').load("configuration.htm?path="+path, function() {
		$("#autoRegisteredTestSessionsTableId").closest(".ui-jqgrid").find('.loading').show();
		$('#breadCrumMessage').text("Test Coordination");
		$('#breadCrumMessageTag').text("");
		window.localStorage.removeItem("ARTSgridParamsData");		
    	$(".studentARTSmessages").hide();
		setTimeout("aart.clearMessages()", 200);
		setTimeout(function(){ $(".studentARTSmessages").hide(); },200);
		
		loadAutoRegisteredTestSessions();	
		resetTestCoordinationTab();
		$('.error').hide();
		
		setTimeout(function(){ $("#tsSearchBtn").trigger("click");},700);
		$("#autoRegisteredTestSessionsTableId").closest(".ui-jqgrid").find('.loading').hide();
	});
}

function clearTCSearchFilterValuesFromSession() {
	
	removeInSessionStorage("tsAssessmentProgramId");
	removeInSessionStorage("tsTestingProgramId");
	removeInSessionStorage("tsContentAreaId");
	removeInSessionStorage("tsGradeCourseId");
	removeInSessionStorage("tsDistrictOrgId");
	removeInSessionStorage("tsSchoolOrgId");
	removeInSessionStorage("tsShowExpiredFlag");
}

function setInSessionStorage(storageItemName, storageItemValue) {
	window.sessionStorage.setItem(storageItemName, storageItemValue);			
}

function removeInSessionStorage(storageItemName) {
    window.sessionStorage.removeItem(storageItemName);
}

function getFromSessionStorage(storageItemName) {
	var itemValue = window.sessionStorage.getItem(storageItemName);
	if(typeof itemValue != 'undefined' && itemValue != null) {
		return itemValue;
	}
	
	return null;
}

//Custom formatter for edit test session link. 
function editTestSessionLinkFormatter(cellvalue, options, rowObject) {
	var htmlString = "N.A";
	htmlString = '<a class="link" href="setupTestSession.htm?'+
			'testSessionId='+ rowObject[1] + '&testSessionName='+ escapeHtml(rowObject[3]) + '&highStakesFlag='+ rowObject[20] +
			'&testCollectionId='+ rowObject[6] + '&source='+ rowObject[22] + '&rosterId='+ rowObject[13] + '&testingProgram='+$("#testingPrograms option:selected").text() +'">' + escapeHtml(cellvalue) + '</a>';
    return htmlString;	
}

//Custom unformatter for lastname link.
function editTestSessionLinkUnFormatter(cellvalue, options, rowObject) {
     return rowObject[3];
}

//Custom formatter for lastname link. 
function printTicketLinkFormatter(cellvalue, options, rowObject) {
	var htmlString = '<div title="You do not have permission to view tickets."><img class="ui-state-disabled" alt="Test Session PDF" style="border:0px solid;" src="images/pdf.png"></div>';
	if (hasTestTicketViewPerm) {
		htmlString = '<a class="pdfLink" href="getPDFTickets.htm?'+
				'assessmentProgramName=' + rowObject[12] + 
				'&testSessionId='+ rowObject[1] + 
				'&testCollectionId='+ rowObject[6] + 
				'">' + '<img alt="Test Session PDF" style="border:0px solid;" src="images/pdf.png">' + '</a>';
	}
    return htmlString;
}

//Custom unformatter for lastname link.
function printTicketLinkUnFormatter(cellvalue, options, rowObject) {
    return rowObject[1];
}

function testInfoLinkFormatter(cellvalue, options, rowObject) {
	var mediaPath = "${applicationScope['nfs.url']}";
	
	var lval = '';
	if(cellvalue==null || typeof cellvalue  == 'undefined' || cellvalue.length==0 ){
		lval = '';
	} else {
		var carr = cellvalue.split(',');
		$.each(carr, function(index, item) {
			var iarr = item.split('`--|-!');
			if(iarr[2] == 'pdf')
				lval = lval + '<a  target="_blank" style="display:inline-block;" href="' + mediaPath + iarr[0] + '">' + '<img alt="'+ iarr[1] +'" style="border:0px solid;" src="images/pdf.png">' + '</a>';
			else if(iarr[2] == 'UCB' || iarr[2] == 'UEB' || iarr[2] == 'EBAE')
				lval = lval + '<a  target="_blank" style="display:inline-block;" href="' + mediaPath + iarr[0] + '">' + '<img alt="'+ iarr[1] +'" style="border:0px solid;" src="images/test/braille_25_25.png">' + '</a>';
		
		});			
	} 
	return lval;	    
}


//Custom unformatter for lastname link.
function testInfoLinkUnFormatter(cellvalue, options, rowObject) {
    return;
}