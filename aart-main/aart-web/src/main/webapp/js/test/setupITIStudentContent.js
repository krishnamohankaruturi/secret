var studentInfo = null;
var claim;
var conceptualArea;
var eElement = "";
function enableEEDropdown(enable) {
	if (enable == false) {
		$('#EEDropdown').prop('disabled', true).attr('disabled', 'disabled');
	} else {
		$('#EEDropdown').prop('disabled', false).removeAttr('disabled');
	}
}
function loadStudentInfoMessage() {
	gridParam = window.sessionStorage.getItem("selectedStudentITI");  
	studentInfo = $.parseJSON(gridParam);
	$("#noTestLetsFound").hide();
	$("#noRecTestLetsFound").hide();
	$("#bluePrintCoverageMsg").hide();
	if(studentInfo !=null) {
		if(studentInfo.statePoolType === 'SINGLEEE' && (studentInfo.subjectAreaCode === undefined || studentInfo.subjectAreaCode.toUpperCase() !== 'SCI')) {
			$("#bluePrintCoverageMsg").show();
		}
		$(".studentInfo").text("");
		var htmlstring = "<table><tr><th width='30%'>Selected Student:</th><th>|</th><td></td><th width='17%'>Grade:</th><th>|</th><td></td><th width='25%'>  Roster:</th><th>|</th><td></td><th width='28%'>  Subject:</th></tr><tr><td>" +
		studentInfo.studentFirstName + " "+ studentInfo.studentLastName +"</td><td>|</td><td></td><td>"+studentInfo.gradeCourseName +"</td><td>|</td><td></td><td>"+
           studentInfo.rosterName+"</td><td>|</td><td></td><td>" + studentInfo.subjectareaName + "</td></tr></table>";
		$(".studentInfo").html(htmlstring);
		if($('#EEDropdown option:selected').val() == "") {
			getEE(studentInfo.gradeCourseCode,studentInfo.stateSubjectAreaId, studentInfo.studentId, studentInfo.rosterId, studentInfo.statePoolType, studentInfo.subjectAreaCode);
			loadLinkageLevels(function(){
				enableEEDropdown(true);
			});
			$("#EEDropdown").show();
			$("#eeSelectedDiv").hide();
			$("#selectedEEMsg").hide();
			$("#descriptions").hide();
			$("#selectLevelMsg").show();
		} else {
			$("#linkageLevelsITITableId").removeAttr("disabled").on("click");
		}
	}
}

$("#selectedEEMsg").click(function() {
	$(this).hide();
	$("#eeSelectedDiv").hide();
	$("#EEDropdown").show();
});
function changeSelectEEDropDownWidth() {
	$("#width_tmp").html($("#EEDropdown option:selected").text());
	$("#EEDropdown").width($("#width_tmp").width()+30);
	$("#eeSelectedDiv").width($("#width_tmp").width()+30);
	$("#selectedEEMsg").width($("#width_tmp").width()+30);
}

$('#EEDropdown').change(function() {
	$(".itiContentSelectionNext").addClass("ui-state-disabled").prop("disabled", true);	
	changeSelectEEDropDownWidth();
	if(($(this).val()) != "") {
		enableEEDropdown(false);
		$("#descriptions").removeClass("hidden").show();
		var selectedEEText = $('#EEDropdown option:selected').html();
		$("#eeSelectedDiv").html(selectedEEText);
		getClaimConAreaDescriptions($(this).val());
		$("#selectLevelMsg").hide();
		$("#eeSelectedDiv").show();
		$("#selectedEEMsg").show();
		$("#linkageLevelsITITableId").removeAttr("disabled").on("click");
	} else {
		$("#descriptions").hide();
		$("#selectLevelMsg").show();
		$("#eeSelectedDiv").hide();
		$("#selectedEEMsg").hide();
		$(".recommendedLevel").text("");
		$("#linkageLevelsITITableId").attr("disabled", "disabled").off("click");
		loadLinkageLevels(function(){
			enableEEDropdown(true);
		});
	}
});

 function getClaimConAreaDescriptions(selectedEEId) {
	$.ajax({
		url: 'getClaimConceptualAreasForSelectedEE.htm',
		data: {
			contentCodeId: selectedEEId
		},
		type: "GET",
		success: function(response) {
			eElement = response.eeConetnetCode + " - " + response.eeDescription;
			$("#eeDescription").html(" " + response.eeConetnetCode + "  " + response.eeDescription);
			$("#claimDescription").html(" " + response.claimCodeForLM + "  " + response.claimDescriptionForLM);
			$("#conceptualAreaDesc").html(" " + response.conceptualCodeForLM + "  " + response.conceptualDescriptionForLM);
			loadLinkageLevels(function(){
				enableEEDropdown(true);
			});
	  }
	});
}

function getEE(gradeCourseCode, contentAreaId, studentId, rosterId, statePoolType, subjectAreaCode){
	$.ajax({
		url: 'getContentCodeUsedInITIElgibleTestCollections.htm',					
		dataType: 'json',
		data:{
			gradeCourseCode : gradeCourseCode,
			contentAreaId : contentAreaId,
			studentId : studentId,
			rosterId : rosterId,
			contentAreaAbbrName: subjectAreaCode,
			statePoolType : statePoolType,
		},
		type: "POST",
		success: function(response) {
			//clear options to re-load data
			 $('#EEDropdown').empty('');
			if(response.length == 1 && response[0].multipleTestOverviewsExists != null && (response[0].multipleTestOverviewsExists == true || 
					response[0].contentCode == null || response[0].description == null)) {				
				$('body, html').animate({scrollTop:0}, 'slow');
				if(!response[0].multipleTestOverviewsExists) {
					$(".noDataError").removeClass("hidden").show();	
				} else {
					$(".multipleOverviewsError").removeClass("hidden").show();
				}
				$('#EEDropdown').append(new Option("No Essential Elements Available",""));
				setTimeout("aart.clearMessages()", 9000);
			} else if(response.length > 0){				
				$('#EEDropdown').append(new Option("Select Essential Element",""));
				for(var i=0;i<response.length;i++){
					if(response[i].enableEEFlag) {
						if(response[i].criteriaNumber == null || !response[i].eeInBluePrint ||  response[i].studentTestFlagExists 
								|| response[i].eeMetCriteria) {
							$('#EEDropdown').append("<option value='" + response[i].id + "'>" + "&nbsp;" + response[i].contentCode + "  " + response[i].description.substring(0,24) + "</option>");
						} else {
							$('#EEDropdown').append("<option value='" + response[i].id + "'>" + "*" + response[i].contentCode + "  " + response[i].description.substring(0,24) + "</option>");
						}
					} else {
						$('#EEDropdown').append("<option value='" + response[i].id + "' " +
								"style='opacity:0.7' disabled='disabled'>" + response[i].contentCode + "  " + response[i].description.substring(0,24) + "</option>");
					}
				}
			} else{
					$('body, html').animate({scrollTop:0}, 'slow');
					$(".noDataError").removeClass("hidden").show();
					$('#EEDropdown').append(new Option("No Essential Elements Available",""));
					setTimeout("aart.clearMessages()", 9000);
			}
			$("#EEDropdown > option:contains('*')").each(function(){
				$(this).html($(this).html().replace("*", "<span style='color:#0E76BC !important'>*</span>"));
			});
		}
	});	
}
function validateContent(){
	var check = false;
	if(!$('#EEDropdown').val())
		check = true;
	if(!check){
		claim = $('#claimDescription').text();
		conceptualArea = $('#conceptualAreaDesc').text();
	}
 	return check;
}

var linkageLevelCodes = new Array();
var linkageLevel;
var actualLinkageLevel;
var linkageLevelid;
var moretext = "more";
var lesstext = "less";
var ellipsestext = "";
var selectedLinkageRadio;
var i=0;
var index =0;
var contentCode;
var levelLongDesc="";
var levelShortDesc="";
var isRecomLevelAvailable = "false";
var linkageLevelsAvailable = "true";

$('#longDesc').dialog({
	resizable: false,
	height: 300,
	width: 600,
	modal: true,
	autoOpen:false,
	title:"LEVEL DESCRIPTION",
	buttons: {
	    Close: function() {
	    	 $(this).dialog('close');
	    }			    
	}
});

function loadLinkageLevels(callback) {
	linkageLevel="";
	actualLinkageLevel="";
	isRecomLevelAvailable = "false";
	linkageLevelCodes = [], linkageLevelids = [];
	$(".recommendedLevel").text("");
	if($('#EEDropdown option:selected').val() != ""){
		var htmlstring = '<font size="5" color="red">*</font>Recommended level for ' + studentInfo.studentFirstName +', '+ studentInfo.studentLastName+'.';
		$(".recommendedLevel").append(htmlstring);
	}
	contentCode = $('#EEDropdown option:selected').text();
	if(contentCode != null && contentCode != "" && contentCode != undefined) {
		contentCode = contentCode.substring(0,contentCode.indexOf('  '));
		linkageLevelCodes.push(contentCode.substring(1));
		linkageLevelid = ($('#EEDropdown').val());
	}	
	var gridWidth = $('#linkageLevelsITITableId').parent().width();		
	if(gridWidth == 100 || gridWidth == 0) {
		gridWidth = 960;				
	}
    var cellWidth = gridWidth/2;
    cellWidthRadio = 60;
	var gridParam;	
	$gridAuto = $("#linkageLevelsITITableId");
	$("#linkageLevelsITITableId").jqGrid('clearGridData');
	$("#linkageLevelsITITableId").jqGrid("GridUnload");
	var cmforAutoRegistration = [
			{ name : 'id', index : 'id', formatter: levelIdFormatter,  width : 30, sortable : false, search : false, hidden: false },
			{name : 'testavailabile', index: 'testavailable',  width : 100, search : false, hidden: false,  sortable : false, hidedlg: false},
			{ name : 'linkagelabel', index : 'linkagelabel', width : cellWidth, search : false, hidden: false, sortable : true,
				formatter : function(cellvalue, options, rowObject){
					if((cellvalue.indexOf(":recommended") >= 0)){ 
						return cellvalue.substr(0, cellvalue.indexOf(":")) +'<font size="5" color="red">*</font>';
					}else{
						return cellvalue;
					}
				}, hidedlg: false },
			{ name : 'linkagelevelshortdesc', index : 'linkagelevelshortdesc', width : cellWidth, formatter: linkageDescFormatter, unformat: linkageDescUnFormatter, search : false, hidden : false, hidedlg : false},
			
			{ name : 'linkagelevellongdesc', index : 'linkagelevellongdesc', width : cellWidth, search : false, hidden : true, hidedlg : true},
			{ name : 'actualLinkageLevel', index : 'actualLinkageLevel', width : cellWidth, search : false, hidden: false, sortable : true, hidden: true, hidedlg:true},
		];
		
    firstLoad = true;
	//JQGRID
    if(linkageLevelid!=''){
	jQuery("#linkageLevelsITITableId").scb({
		url : 'getLinkageLevels.htm?q=1',
		postData : {
			orgChildrenIds : function() {
				return $('#orgChildrenIdsDLM').val();
			},
			studentId: studentInfo.studentId,
			rosterId:studentInfo.rosterId,
			essentialelementid:linkageLevelid,
			gradeCourseCode: studentInfo.gradeCourseCode,
			contentAreaId: studentInfo.stateSubjectAreaId,
			contentAreaAbbrName: studentInfo.subjectAreaCode,
			eElementCode: eElement.substring(0,eElement.indexOf(" - "))
		},
		mtype: "POST",
		datatype : "json",
		width: gridWidth,
		colNames : [
   						'','Available','Level', 'Level Description','', ''
   		           ],
   		colModel :cmforAutoRegistration,	
		rowNum : 10,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		height : 'auto',
			sortname : 'linkagelabel',
		search: false,
        sortname: 'linkagelabel',
       	sortorder: 'desc',
	    beforeRequest: function() {
	    	$("#loadingmessage").show();
	    	//Set the page param to lastpage before sending the request when 
			  //the user entered current page number is greater than lastpage number.
			var currentPage = $(this).getGridParam('page');
            var lastPage = $(this).getGridParam('lastpage');
            
             if (lastPage!= 0 && currentPage > lastPage) {
            	 $(this).setGridParam('page', lastPage);
            	$(this).setGridParam({postData: {page : lastPage}});
            } else {
            	$(this).setGridParam({postData: {contentCode : JSON.stringify(linkageLevelCodes), contentId : linkageLevelid, studentId: studentId}});
            }
	    },
	    loadComplete: function(){
	    	var eeVal = $('#EEDropdown').val();
	    	if(!eeVal && isRecomLevelAvailable == "false" && linkageLevelid != "" && linkageLevelsAvailable == "true") {	    		
				$("#noRecTestLetsFound").show();				
			} else if(eeVal && isRecomLevelAvailable == "false") {
				var rowIDs = jQuery("#linkageLevelsITITableId").jqGrid('getDataIDs');
				var numOfAvailableLinkageLevels = 0;
				var rowIdNeedToCheck = 0;
				var rowIndex = 0;
				for(var rowNum = 0; rowNum < rowIDs.length; rowNum++) {
					var rowId = rowIDs[rowNum];
					var row = jQuery("#linkageLevelsITITableId").jqGrid('getRowData', rowId);
					if(row.testavailabile == "Yes") {
						numOfAvailableLinkageLevels++;
						rowIndex = rowNum;
						rowIdNeedToCheck = rowId;
			    	}
				}
				if(numOfAvailableLinkageLevels == 1) {
					$("#" + rowIdNeedToCheck + " .micromaplevel").prop("checked", true);
					linkageLevel = $("#" + rowIdNeedToCheck + " .micromaplevel").val();
					actualLinkageLevel = $("#" + rowIdNeedToCheck + " .micromaplevel").attr('data-actualLinkageLevel');
					levelLongDesc =  $("#" + rowIdNeedToCheck + " .micromaplevel").attr('data-longleveldesc');
					levelShortDesc = $("#" + rowIdNeedToCheck + " .micromaplevel").attr('data-shortleveldesc');					
					enableNextButton();
					getInstructionalPlan();
				} else if(numOfAvailableLinkageLevels == 0){
					if(linkageLevelid != "" && linkageLevelsAvailable == "true"){
						$("#noTestLetsFound").show();
					}
				}			
			}
	    	if (typeof (callback) === 'function'){
				callback();
			}
	    },
	    gridComplete: function() {
			$("#loadingmessage").hide();
			$("#noLinkageLevelFound").hide();
		    var recs = parseInt($("#linkageLevelsITITableId").getGridParam("records"));
			if (isNaN(recs) || recs == 0) {
			     //Set min height of 1px on no records found
			     $('.jqgfirstrow').css("height", "4px");
			     linkageLevelsAvailable = "false";
			     $("#noLinkageLevelFound").show();			     
			 }	
			$('.micromaplevel').click(function() {
            	if($(this).is(':enabled')){
            			$(this).attr('checked', 'checked');
	            		selectedLinkageLevel = $(this).val();
	            		levelLongDesc = $(this).attr('data-longleveldesc');
	            		levelShortDesc = $(this).attr('data-shortleveldesc');
	            		actualLinkageLevel = $(this).attr('data-actualLinkageLevel');
		            	if(linkageLevel != selectedLinkageLevel ){
		            		linkageLevel = selectedLinkageLevel;
		            		enableNextButton();
		            		getInstructionalPlan();		            		
		            	}
            	}  
			});
	    }
	});	
    }
	//Clear the previous error messages
	setTimeout("aart.clearMessages()", 0);
	//Custom formatter for id column. 
}

function enableNextButton() {	
	$(".itiContentSelectionNext").removeClass("ui-state-disabled").prop("disabled", false);	
}

function levelIdFormatter(cellvalue, options, rowObject) {	
	var htmlString = "";
	if((rowObject[2].indexOf(":recommended") >= 0) && rowObject[1] == "Yes"){
		linkageLevel = rowObject[2].substr(0, rowObject[2].indexOf(":"));		
		htmlString = '<input type="radio" data-longleveldesc="' + rowObject[4] +'" data-shortleveldesc="' + rowObject[3] + '" data-actualLinkageLevel="' + rowObject[5] 
			+'" class="micromaplevel" id=' + rowObject[0] + ' name="microMapId" value="' + linkageLevel + '" checked="checked"/>';		
		levelLongDesc = rowObject[4];
		levelShortDesc = rowObject[3];
		actualLinkageLevel = rowObject[5];
		isRecomLevelAvailable = "true";
		enableNextButton();
		getInstructionalPlan();
	}else{
		if(check == "false"){			
			htmlString = '<input type="radio" class="micromaplevel" id=' + rowObject[0] + ' name="microMapId" value="' + rowObject[2] + '" data-actualLinkageLevel="' + rowObject[5] 
				+ '"disabled/>';
		}else{
			if(rowObject[1] == "Yes"){
				htmlString = '<input type="radio" data-longleveldesc="' + rowObject[4] + '" data-shortleveldesc="' + rowObject[3] + '" data-actualLinkageLevel="' + rowObject[5] 
					+ '" class="micromaplevel" id=' + rowObject[0] + ' name="microMapId" value="' + rowObject[2] + '"/>';
			} else{
				htmlString = '<input type="radio" class="micromaplevel" id=' + rowObject[0] + ' name="microMapId" value="' + rowObject[2] + '" data-actualLinkageLevel="' + rowObject[5] 
				+ '"disabled/>';
			}
		}
	}	
	return htmlString;
}

//Custom unformatter for id column. 
function levelIdUnFormatter(cellvalue, options, rowObject) {
    return;
}

function linkageDescFormatter(cellvalue, options, rowObject) {
	if($('#EEDropdown').val() == "") {
		return rowObject[3];
	}
    return rowObject[3] + '&nbsp;&nbsp;<a onClick="detailedDesc('+ options.rowId + ');">' + moretext + '</a>';
}

//Custom unformatter for id column. 
function linkageDescUnFormatter(cellvalue, options, rowObject) {
    return;
}

function detailedDesc(rowid) {
 	var gridRow = $("#linkageLevelsITITableId").jqGrid('getRowData',rowid);
 	$('#longDescDetails').html(gridRow['linkagelevellongdesc']);
 	$('#longDesc').dialog('open');
}

function validateLinkageLevel(){
	if(linkageLevel == ""){
		return true;
	}
	return false;
}
		
