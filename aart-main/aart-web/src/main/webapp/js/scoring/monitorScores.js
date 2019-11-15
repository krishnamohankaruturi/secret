var assessmentProgId = null;
 var testSessionId=4094564;
function scoringMonitorCcqScoresInit(){	
	
	assessmentProgId = $('#userDefaultAssessmentProgram').val();
	$('#monitorScoresDistrict,#monitorScoresContentAreas,#monitorScoresGrades,#monitorScoresStage,#monitorScoresSchool').select2({
		placeholder:'Select',
		multiple: false,
		allowClear : true
	});	
	

	disableButtons("monitorScoresSearchBtn");
	
	scoringMonitorScoresOnload(); 
	refreshSelectBtn("monitorScoresSchool");
	refreshSelectBtn("monitorScoresContentAreas");
	refreshSelectBtn("monitorScoresGrades");
	refreshSelectBtn("monitorScoresStage");
	filteringOrganization($('#monitorScoresDistrict'));
	filteringOrganization($('#monitorScoresSchool'));
	
	var $gridAuto = $("#arts_monitorScoreTestSession");
	$("#arts_monitorScoreTestSession").jqGrid('clearGridData');
	$("#arts_monitorScoreTestSession").jqGrid("GridUnload");	
	
	
	//if user is school Level
	if($("#userCurrentOrganizationType").val() == 'SCH'){
		monitorScoresSubjectLoad();
		refreshSelectBtn("monitorScoresContentAreas");
		refreshSelectBtn("monitorScoresGrades");
		refreshSelectBtn("monitorScoresStage");
		monitorScoresSearchBtnDisable();
	}
	$('.monitorScoresGgrid').hide();
	$('.monitorScoresDownload').hide();
	
	//Tiny Text changes
	initTinytextChanges($("#monitorScoresUserAccesslevel").val());
}

function scoringMonitorScoresOnload(){
	//District onLoad
	monitorScoresDistrictLoad();

	// District OnChange
	$('#monitorScoresDistrict').on("change",function() {
		
		//Added for tiny text changes
		districtEventTinyTextChanges($(this).find('option:selected').text());
		
		monitorScoresDistrictChangeEvent();
		refreshSelectBtn("monitorScoresSchool");
		refreshSelectBtn("monitorScoresContentAreas");
		refreshSelectBtn("monitorScoresGrades");
		refreshSelectBtn("monitorScoresStage");
		monitorScoresSearchBtnDisable();
		
	});
	
	
	//School OnChange
	$('#monitorScoresSchool').on("change",function() {
		
		//Added for tiny text changes
		schoolEventTinyTextChanges($(this).find('option:selected').text());
		
		monitorScoresSubjectLoad();
		refreshSelectBtn("monitorScoresContentAreas");
		refreshSelectBtn("monitorScoresGrades");
		refreshSelectBtn("monitorScoresStage");
		monitorScoresSearchBtnDisable();		
	});
	
	// Content Area OnChange	
	$('#monitorScoresContentAreas').on("change",function() {
		
		//Added for tiny text changes
		subjectEventTinyTextChanges($(this).find('option:selected').text());
		
		monitorScorerSubjectChangeEvent();		
		refreshSelectBtn("monitorScoresGrades");
		refreshSelectBtn("monitorScoresStage");		
		monitorScoresSearchBtnDisable();		
	});	

	// Grade OnChange	
	$('#monitorScoresGrades').on("change",function() {
		
		//Added for tiny text changes
		gradeEventTinyTextChanges($(this).find('option:selected').text());
		
		refreshSelectBtn("monitorScoresStage");
		monitorScoresStageLoad();
		monitorScoresSearchBtnDisable();
	});	
	
	// Stage onChange
	$('#monitorScoresStage').on("change",function() {
		//Added for tiny text changes
		stageEventTinyTextChanges($(this).find('option:selected').text());
		monitorScoresSearchBtnDisable();
	});	
	
	$('#monitorScoresSearchBtn').off("click").on("click",function(event) {
		
		enableButtons("monitorScoresSearchBtn");
		var assessmentProgram=$('#monitorScorerUserAssessmentProgramId').val();
		var school = $('#monitorScorerUserCurrentOrgId').val();
		var contentArea=$('#monitorScoresContentAreas').val();
		var grade=$('#monitorScoresGrades').val();
		var stage=$('#monitorScoresStage').val();
		if( $("#userCurrentOrganizationType").val() !='SCH'){
			school=$('#monitorScoresSchool').val();
		}
		
		loadMonitorTestSessionData(assessmentProgram,school,contentArea,grade,stage);
		enableButtons("monitorScoresSearchBtn");
		
		
	});


	$(".downloadImg").on("click",function(){
		
				  var iCol, $grid =  $("#arts_monitorScoreTestSession"), 
				  colModel = $grid.jqGrid("getGridParam", "colModel"),
				  idPrefix = $grid.jqGrid("getGridParam", "idPrefix"),
				  tr, td, cm, cmName, item,
				  nCols = colModel.length, rows = $grid[0].rows, iRow, nRows = rows.length, data = [];
				  var clmNames = [];
				  var contentArea=$('#monitorScoresContentAreas').find(":selected").attr('code');
					var grade=$('#monitorScoresGrades').find(":selected").attr('code');
              for (iRow = 0; iRow < nRows; iRow++) {
                  tr = rows[iRow];
                  
                  if ($(tr).hasClass("jqgrow")) {
                      item = {}; // item of returned data
                      for (iCol = 0; iCol < nCols; iCol++) {
                    	  
                          cm = colModel[iCol];
                          cmName = cm.name;
                         
                          if (!cm.hidden ) {
                              td = tr.cells[iCol];
                              if(iRow==1 && cmName!=undefined){
                        		  clmNames[iCol] =cmName; 
                              }
                              try {
                                  item[cmName] = $.unformat.call($grid[0], td, { rowId: tr.id, colModel: cm }, iCol);
                              } catch (exception) {
                                  item[cmName] = $.jgrid.htmlDecode($(td).html());
                              }
                          }
                      }
                      data.push(item);
                  }
              }
              
            var response = data;
           var clmNames = clmNames.filter(function(val){ return val!==undefined; });
		   var csv = Papa.unparse({fields:clmNames,data:response});
		   var blob = new Blob( [ csv ], { type: "text/csv"} );
			var csvName='Monitor Scoring By Student Items and Scorers_'+contentArea.trim()+'_'+grade.trim();
			if ( navigator.msSaveOrOpenBlob ) {
			    // Works for Internet Explorer and Microsoft Edge
			    navigator.msSaveOrOpenBlob( blob, csvName + ".csv" );
			
			  } else {
			   $("#downloadCSV").attr('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(csv));
			   $("#downloadCSV").attr('download',csvName + ".csv");
			   $("#downloadCSV")[0].click();
			  }
		});
}

function monitorScoresDistrictLoad(){
	// onLoad District
	var districtOrgSelect = $('#monitorScoresDistrict');
	$.ajax({
		url: 'getOrgsBasedOnUserContext.htm',
		dataType: 'json',
		data: {
			orgId : $("#monitorScorerUserCurrentOrgId").val(), //${user.currentOrganizationId},
	    	orgType:'DT',
	    	orgLevel:50
	    	},
	    	type: "GET"
	}).done(function (districtOrgs) { 
		districtOrgSelect.find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		districtOrgSelect.trigger('change.select2');
		if (districtOrgs !== undefined && districtOrgs !== null && districtOrgs.length > 0) {
			$.each(districtOrgs, function(i, districtOrg) {
				optionText = districtOrgs[i].organizationName;
				districtOrgSelect.append($('<option></option>').val(districtOrg.id).html(optionText));
			});					
		   if (districtOrgs.length == 1) {
				districtOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
				monitorScoresDistrictChangeEvent();
			} 
		} 
		$('#monitorScoresDistrict').trigger('change.select2');
		$('#monitorScoresSchool').val("0").trigger('change.select2');
	}).fail(function (jqXHR, textStatus, errorThrown) {
		console.log(errorThrown);
	});
}

function monitorScoresSubjectLoad(){
	//onLoad Subject
		var schoolId = [];
		var assessmentProgId = $('#userDefaultAssessmentProgram').val();
	    if($("#userCurrentOrganizationType").val() == 'SCH' && schoolId.length == 0){
	    	schoolId[0] = $("#monitorScorerUserCurrentOrgId").val();
	    }else{
	    	schoolId[0] = $('#monitorScoresSchool').val();
	    }
		if(schoolId != 0 && schoolId != null){
			$.ajax({
				url: 'getUploadScoreSubject.htm',
			    data: {
			    	assessmentProgramId: assessmentProgId,
			    	schoolId: schoolId
			    	},
			    dataType: 'json',
			    type: "POST"
			}).done(function (contentAreas) {
				$('#monitorScoresContentAreas').find('option').filter(function(){return $(this).val() > 0;}).remove().end();				        	
				$.each(contentAreas, function(i, contentArea) {
					$('#monitorScoresContentAreas').append($('<option></option>').attr("value", contentArea.id).attr("code", contentArea.abbreviatedName).text(contentArea.name));
				});
				
				if (contentAreas.length == 1) {
					$("#monitorScoresContentAreas option").removeAttr('selected').next('option').attr('selected', 'selected');
					$("#monitorScoresContentAreas").trigger('change');
				}
				$('#monitorScoresContentAreas').trigger('change.select2');
			}).fail(function (jqXHR, textStatus, errorThrown) { 
				console.log(errorThrown);
			});
	    }

	}
function monitorScoresDistrictChangeEvent(){	
	var districtOrgId = $('#monitorScoresDistrict').val();
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
		}).done(function (schoolOrgs) { 
			$('#monitorScoresSchool').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
			$.each(schoolOrgs, function(i, schoolOrg) {
				$('#monitorScoresSchool').append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
			});		
			if (schoolOrgs.length == 1) {
				$("#monitorScoresSchool option").removeAttr('selected').next('option').attr('selected', 'selected');
				$("#monitorScoresSchool").trigger('change');
			}
			$('#monitorScoresSchool').trigger('change.select2');
		}).fail(function (jqXHR, textStatus, errorThrown) { 
			console.log(errorThrown);
		});

	}	
}

function monitorScorerSubjectChangeEvent(){
	
	$('#monitorScoresGrades').trigger('change.select2');
	var contentAreaId = $('#monitorScoresContentAreas').val();
	var schoolId  = [];
	
	 if($("#userCurrentOrganizationType").val() == 'SCH' && schoolId.length == 0){
	    	schoolId[0] = $("#monitorScorerUserCurrentOrgId").val();
	    }else{
	    	schoolId[0] = $('#monitorScoresSchool').val();
	    }
		
	if (contentAreaId != 0) {
		$.ajax({
			 url: 'getUploadScoresGradeBySubjectId.htm',
		     data: {
		        	subjectId: contentAreaId,
		        	schoolId : schoolId},
		     dataType: 'json',
		     type: "GET"
		}).done(function (grades) { 
			$('#monitorScoresGrades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();				        	
			$.each(grades, function(i, grade) {
				$('#monitorScoresGrades').append($('<option></option>').attr("value", grade.id).attr("code",grade.name).text(grade.name));
			});
								
			if (grades.length == 1) {
				$("#monitorScoresGrades option").removeAttr('selected').next('option').attr('selected', 'selected');
				$("#monitorScoresGrades").trigger('change');
			}				
			$('#monitorScoresGrades').trigger('change.select2');
		}).fail(function (jqXHR, textStatus, errorThrown) { 
			console.log(errorThrown);
		});

	}
	
}

function monitorScoresStageLoad(){	
	  var schoolId = [];
	  if($("#userCurrentOrganizationType").val() == 'SCH' && schoolId.length == 0){
	    	schoolId[0] = $("#monitorScorerUserCurrentOrgId").val();
	    }else{
	    	schoolId[0] = $('#monitorScoresSchool').val();
	    }
	  var subjectId = $('#monitorScoresContentAreas').val();	
	  var gradeId = $('#monitorScoresGrades').val();
	  $.ajax({
		  url: 'getUploadScoresStage.htm',
		    data: {
		    	schoolId: schoolId,
		    	subjectId: subjectId,
		    	gradeId: gradeId
		    	},
		    dataType: 'json',
		    type: "POST"
		}).done(function (stageScores) {
			stageScores.sort(function(a, b) {
			    var at = a.name, bt = b.name;
			    return (at > bt)?1:((at < bt)?-1:0);
			});
	    	$('#monitorScoresStage').find('option').filter(function(){return $(this).val() > 0;}).remove().end();				        	
			$.each(stageScores, function(i, stageScore) {
				$('#monitorScoresStage').append($('<option></option>').attr("value", stageScore.id).text(stageScore.name));
			});
			
			if (stageScores.length == 1) {
				$("#monitorScoresStage option").removeAttr('selected').next('option').attr('selected', 'selected');
				$("#monitorScoresStage").trigger('change');
			}
			$('#monitorScoresStage').trigger('change.select2');
		}).fail(function (jqXHR, textStatus, errorThrown) { 
			console.log(errorThrown);
		});
}


function monitorScoresSearchBtnDisable(){	
   if($("#monitorScoresDistrict").val() != null &&  $("#monitorScoresSchool").val() != null &&  $("#monitorScoresContentAreas").val() != null
		&& $("#monitorScoresGrades").val() != null && $("#monitorScoresStage").val() != null && $("#monitorScoresStage").val() != "" ){
		enableButtons("monitorScoresSearchBtn");			
	} else{
		disableButtons("monitorScoresSearchBtn");
	}
}

function uploadScoresReset(){
	refreshSelectBtn("monitorScoresSchool");
	refreshSelectBtn("monitorScoresContentAreas");
	refreshSelectBtn("monitorScoresGrades");
	refreshSelectBtn("monitorScoresStage");

}
function refreshSelectBtn(id){
	$("#"+id).find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$("#"+id).val("0").trigger('change.select2');	
}
function disableButtons(id){	
	$("#"+id).prop("disabled",true);
	$("#"+id).addClass('ui-state-disabled');
}
function enableButtons(id){
	$("#"+id).prop("disabled",false);
	$("#"+id).removeClass('ui-state-disabled');
}

function loadMonitorTestSessionData(assessmentProgram,school,contentArea,grade,stage) {
	var urlValue = 'getStudentTestMonitorScore.htm';
	$.ajax({
		url: urlValue,
		data: {
			assessmentProgramId: assessmentProgram,
			schoolId: school,
			contentAreaId:contentArea,
			gradeId:grade,
			stageId:stage
		},
		dataType: 'json',
		type: "POST"
	}).done(function (data) { 
		buildMonitoringGrid(data);
	}).fail(function (jqXHR, textStatus, errorThrown) {
		console.log(errorThrown);
	});
}

function buildMonitoringGrid(response) {
	$('.monitorScoresGgrid').show();
	var $gridAuto = $("#arts_monitorScoreTestSession");
	$("#arts_monitorScoreTestSession").jqGrid('clearGridData');
	$("#arts_monitorScoreTestSession").jqGrid("GridUnload");
    var colModel = new Array(); 
     	//create the grid.	        
    var grid_width = $('.kite-table').width();
    var grid_heigth = 40;
  	var column_width = "";
	if(grid_width == 100 || grid_width == 0){
		grid_width = 780;				
	}		

    //Populate static columnmodel.
	colModel.push( { label: '',key:true, width:0,name : 'rowIndex', index : 'rowIndex', hidden : true,hidedlg : true, sortable : false});
	colModel.push({name:'studentsTestId',index:'studentsTestId', label:" ",  align: 'center',  search : true, sortable : false, hidden : true, hidedlg : true });
	colModel.push({name:'Student_First_Name', index:'Student_First_Name',  label:"Student", width:220, align: 'center', search : true, sortable : false});
	colModel.push({name:'Student_Last_Name', index:'Student_Last_Name',  label:"Student", width:220, align: 'center', search : true, sortable : false, hidden : true}); 
	colModel.push({name:'State_Student_ID', index:'State_Student_ID',  label:"State Student ID",width:200, align: 'center',search : true, sortable : false});
	colModel.push({name:'Stage', index:'Stage',  label:"Stage",width:70, align: 'center', sortable : false});
	colModel.push({name:'Overall_Status', index:'Overall_Status',  label:"Overall Status",width:90, align: 'center', sortable : false});
	colModel.push({name:'Scorer', index:'Scorer',  label:"Scorer",width:200, align: 'center', sortable : false});
	//Populate dynamic columnnames and columnmodel.
	if(typeof response.sectionStatusColumnNames != 'undefined' && response.sectionStatusColumnNames !=null) {
		for(var i=0; i < response.sectionStatusColumnNames.length; i++) {
			
			var count = (response.sectionTaskColumnLabels[i].match(/-/g) || []).length;
			var colWidth =35;
			if(count>=1){
				colWidth=colWidth+(count*20);
			}
			
			colModel.push({name:response.sectionStatusColumnNames[i], index:response.sectionStatusColumnNames[i],label:response.sectionTaskColumnLabels[i],width:colWidth, align: 'center',  sortable : false,
				cellattr: function (rowId, val, rawObject, cm, rdata) {
		        return 'title="'+rawObject.Student+'"';
		    }});
		}
	}
	if(response.students.length<7){
		grid_heigth=grid_heigth+(22*response.students.length);
	}else{
		grid_heigth=200;
	}
  
	$('#arts_monitorScoreTestSession').scb({
        datatype: "local",
        autowidth: true,
        data: response.students,       
        colModel: colModel,   
        postData: {},
        height : grid_heigth,
        width: grid_width,
        shrinkToFit: true,
        rowNum : 60000,
    	search: true,
    	sortable:false,
        sortname: 'Student_Last_Name',
        sortorder: 'asc',
        viewrecords : true,
        viewable : false,
        emptyrecords : "<fmt:message key='label.common.norecordsfound'/>",
        altRows : true,
        altclass: 'altrow',
        hoverrows : true,
        multiselect : false,
        //filterToolbar: true,
        gridComplete: function() {
        },
        loadComplete: function () {
            var $footerRow = $(this.grid.sDiv).find("tr.footrow"),
            	$newFooterRow = $(this.grid.sDiv).find("tr.footrow");
            if ($newFooterRow.length === 1) {
	            $newFooterRow = $footerRow.clone();		            
	            $newFooterRow.insertAfter($footerRow);
            }
            var tableid=$(this).attr('id'); 
            gview_arts_monitorScoreTestSession
	        var objs= $('#gbox_'+tableid).find('[id^=gs_]');;
	        $.each(objs, function(index, value) {
	          var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));
	               $(value).attr('title',$(nm).text()+' filter');
	                        
	               });         
            
        }
    });
	$('#arts_monitorScoreTestSession').jqGrid('filterToolbar',
            { stringResult: true, searchOnEnter: false, defaultSearch: "cn" });
	  $('#arts_monitorScoreTestSession').jqGrid('navGrid', '#arts_monitorTestSessionPager', {edit: false, add: false, del: false, refresh:false, search:false});
    $('#arts_monitorScoreTestSession').triggerHandler("jqGridAfterGridComplete");
    var width = $('#gbox_arts_monitorScoreTestSession').width();
    $("#arts_monitorTestSessionPager_left").css("width", width/3);
    $("#arts_monitorTestSessionPager_right").css("width", width/3);
    $("#arts_monitorTestSessionPager_center").css("width", width/3);
    $('tr.ui-search-toolbar input').css('width', '100%');
    $('select#gs_overallStatus').css('width', '100%');
	$('#arts_monitorTestSessionPager td#input_arts_monitorTestSessionPager input').css("cssText", "height: 22px !important;")
    $("td.ui-search-clear").css("display", "none");
    $("a.clearsearchclass").css("display", "none");
    $('#arts_monitorTestSessionPager').parent('div').css('border', '1px solid #d8d8d8');
    $gridAuto.jqGrid('filterToolbar', { stringResult: true, searchOnEnter: false, defaultSearch: "cn" });
    $('.ui-pager-control').css("display", "none");
 
    $('.monitorScoresDownload').show();
}


