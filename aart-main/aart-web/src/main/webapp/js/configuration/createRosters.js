function createRostersInit(){
	$('#createCourseSelect').select2();
	$('#createContentAreaSelect').select2();
	gCreateRosterLoadOnce = true;
	$('.createRosterInput').hide();
	$('#createRosterOrgFilter').orgFilter({
		'containerClass': '',
		requiredLevels: [70]
	});
	$('#createRosterOrgFilter').orgFilter('option','requiredLevels',[70]);
	$("#createCourseSelect").attr("disabled", "disabled");
	$.validator.setDefaults({
		submitHandler: function() {		
		},
		errorPlacement: function(error, element) {
			if(element.hasClass('required') || element.attr('type') == 'file') {
				error.insertAfter(element.next());
			}
			else {
	    		error.insertAfter(element);
			}
	    }
	});
	
	$('#createRosterSearchForm').validate({
		ignore: ""
	});
	$('#createContentAreaSelect').on("change",function(e){
		$("#createCourseSelect").attr("disabled", "disabled");
		$("#createCourseSelect").val("").trigger('change.select2');
		var subjectId = $('#createContentAreaSelect option:selected').val();		
		var subjectCode = $('#createContentAreaSelect option:selected').attr("data-contentAreaCode");
		
		if(contractingOrgDisplayIdentifier!=null && contractingOrgDisplayIdentifier!=undefined && contractingOrgDisplayIdentifier.length> 0 
				&& subjectCode!=null && subjectCode!=undefined && subjectCode.length>0 								
				&& (contractingOrgDisplayIdentifier == 'DE' || contractingOrgDisplayIdentifier == 'DC')  
				&& subjectCode == 'Sci')
		{
			$("#createCourseSelect").removeAttr("disabled");
		    getCourses(subjectId);
		}else{
			$("#createCourseSelect").attr("disabled", "disabled");
			$("#createCourseSelect").val("").trigger('change.select2');
			$("#createCourseSelect").removeClass('validateCourse');	
			$("#createCourseSelect").removeClass('error');	
			//$("label[for=createCourseSelect]").attr("style","display:none;");
		}
	});
	$('#createRoster,#searchRosters').on("click",function(e) {
		e.preventDefault();
 		if($('#createRosterSearchForm').valid()) {
 			//TO DISABLE COURSES
 			//$('#createContentAreaSelect , #createCourseSelect')
 			$('#createContentAreaSelect').empty().append('<option selected="selected" value="">Select</option>');			
			var orgIds = [$('#createRosterOrgFilter').orgFilter('value')];
			$("#createEducatorGrid").jqGrid('clearGridData');
			$("#createEducatorGrid").jqGrid("GridUnload");;			
			initCreateEducatorGrid(orgIds);
			$("#createEducatorGrid").trigger("reloadGrid");
						
			$("#createStudentGrid").jqGrid("GridUnload");;			
			initCreateStudentGrid(orgIds);
			
			$("#createStudentGrid").trigger("reloadGrid");
			
			initCreateRosterSelectors();
			$('.createRosterInput').show();
			
		}
	});
	
	$('#createRoster,#createRosterBtn').on("click",function(e) {
		e.preventDefault();
		createRoster();
	});
	
	$('#createRosterForm').validate({
		ignore: "",
		rules: {
			rosterName: {required: true},
			createContentAreaSelect: {required: true},
			
		}
	});	 
	
	filteringOrganizationSet($('#createRosterSearchForm'));
	
}

function initCreateRosterSelectors() {
	var assessmentProgramId = $('#hiddenCurrentAssessmentProgramId').val();
	$('#createContentAreaSelect').select2();
	$.ajax({
		url: 'getContentAreasByAssessmentProgram.htm',
		data: { 
			assessmentProgramId: assessmentProgramId
		},
		dataType: 'json',
		type: "POST"
	}).done(function(contentAreas) {
    	
		$.each(contentAreas, function(i, contentArea) {
			$('#createContentAreaSelect').append($('<option data-contentAreaCode = "'+contentArea.abbreviatedName+'" ></option>').attr("value", contentArea.id).text(contentArea.name));
			$("#createContentAreaSelect").trigger('change');
		});
		
		if (contentAreas.length == 1) {
			$("#createContentAreaSelect option").removeAttr('selected').next('option').attr('selected', 'selected');
			$("#createContentAreaSelect").trigger('change');
		}
		$('#createContentAreaSelect').trigger('change.select2');
    });

}

/**
 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15421 : Roster - refactor Add Roster manually page
 * Load course list on selection of content area.
 * @param contentAreaId
 */
function getCourses(contentAreaId){
	$('#createCourseSelect').select2();
	if (typeof(contentAreaId) != 'undefined'){
		if (contentAreaId == ''){
			return;
		}
		var select = $('#createCourseSelect');
		select.find('option:not(:first)').remove();
		var url = 'getCoursesByContentArea.htm';
		var data = {};
		data.contentAreaId = contentAreaId;
		$.ajax({
			url : url,
			data: data,
			dataType: 'json',
			type: 'GET',
		}).done(function(data) {				
			if (data !== undefined && data !== null && data.courses != undefined && data.courses.length > 0){
        		for (var i = 0, length = data.courses.length; i < length; i++){
        			if(data.courses[i].abbreviatedName == 'BIO') {
        				select.append($('<option data-Course="'+data.courses[i].abbreviatedName+'" ></option>').attr("value", data.courses[i].id).text(data.courses[i].name));
        				/*select.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
	        			select.trigger('change');
	        			$('#createCourseSelect').trigger('change.select2');*/
        			}
        			}
        		
        		if (data.courses.length == 1){
        			select.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
        			select.trigger('change');
        			$('#createCourseSelect').trigger('change.select2');
        		
				} else {
					select.prop('disabled', false);
				}
        	} else {
        		// error
        	}
			$('#createCourseSelect').trigger('change.select2');
		});
	}
	
}

function initCreateEducatorGrid(orgIds) {  
	var $gridAuto = $("#createEducatorGrid");
	
	var gridWidthForVR = $('#createEducatorGrid').parent().width();		
	if(gridWidthForVR < 1045) {
		gridWidthForVR = 1045;				
	}
	var cellWidthForVR = gridWidthForVR/5;
	
	var cmforViewRosters = [ 
	             			{ name : 'firstName', index : 'firstName',label:'createRosterFirstName', width : cellWidthForVR, editable : true, editrules:{edithidden:true}, viewable: true, 
	               				searchoptions:{ sopt:['cn'], dataInit: function(elem) {
	               				    
	               				} 							
	               			}
	               			},
	               			{ name : 'surName', index : 'surName',label:'createRosterSurName', width : cellWidthForVR, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, hidden : false, hidedlg : true,
	               				searchoptions:{ sopt:['cn'], dataInit: function(elem) {
	               				    
	               				} 							
	               			}	
	               			},
	               			{ name : 'uniqueCommonIdentifier', index : 'uniqueCommonIdentifier',label:'createRosterUniqueCommonIdentifier', width : cellWidthForVR, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : false, hidedlg : true, 
	               				searchoptions:{ sopt:['cn'], dataInit: function(elem) {
	               				    
	               								} 							
	               							  }	
	               			
	               			},
	               			{name: 'statusCode', index: 'statusCode',label:'createRosterStatusCode', hidden:false},
	               			{name: 'id', index: 'id',label:'educatorId',label:'createRosterEducatorId', hidden:true},
	               			{name: 'email', index: 'email',label:'createRosterEemail', width : cellWidthForVR, hidden:false}
	               		];
	//JQGRID
	$gridAuto.scb({
		url : "getEducatorsForRosters.htm?q=1",
		mtype: "POST",
		datatype : "json",
		width: gridWidthForVR,
	  	colModel :cmforViewRosters,
	  	colNames : [
					"First Name",
					"Last Name",
		   			"Educator Identifier",
		            "Status",
	  	            "id",
					"Email"
		           ],     
		rowNum : 10,
		shrinkToFit: true,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		pager : '#createEducatorGridPager',
		// F-820 Grids default sort order
		sortname : 'surName,firstName',
		loadonce: false,
		singleselect: true,
		columnChooser: false,
		beforeSelectRow: function(rowid, e)
		{
			$("#createEducatorGrid").data('selectedIds', [rowid]);
			$("#createRosterEducatorSelectError").hide();
			$("#createRosterEducatorIdError").hide();
		    return(true);
		}, 
		loadComplete: function(data) {
			$("#createEducatorGrid").jqGrid('resetSelection');
			var rowIds = $(this).jqGrid("getDataIDs");
            if(null != rowIds && $(this).data('selectedIds') != null) {
    			 var selectedIds = $(this).data('selectedIds');
    			 for (var i = 0; i < rowIds.length; i++) {
	                if ($.inArray( rowIds[i], selectedIds ) > -1) {
	                	$(this).jqGrid('setSelection', rowIds[i], true);
	                	break;
	                }
	             }
            }
            var ids = $(this).jqGrid('getDataIDs');         
	         var tableid=$(this).attr('id');      
	            for(var i=0;i<ids.length;i++)
	            {         
	                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'firstName') +' '+$(this).getCell(ids[i], 'surName')+ ' Check Box');
	                $('#jqg_'+tableid+'_'+ids[i]).removeAttr("aria-checked",false);  
	            }                   
            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');;
            $.each(objs, function(index, value) {
              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));
                   $(value).attr('title',$(nm).text()+' filter');
                            
                   });
            $('td[id^="view_"]').on("click",function(){
					$(".EditTable.ViewTable tbody").prepend("<tr style='border-bottom: 2pt solid #e9e9e9;'><th style='font-weight:bold'>Column Name</th><th style='font-weight:bold'>Column Value</th></tr>");	
				});
		},
	    beforeRequest: function() {
	    	if(!$('#createRosterSearchForm').valid() && $(this).getGridParam('datatype') == 'json'){
	    		return false;
	    	} 
	    	//Set the page param to lastpage before sending the request when 
			//the user entered current page number is greater than lastpage number.
			var currentPage = $(this).getGridParam('page');
	        var lastPage = $(this).getGridParam('lastpage');
	              
	        if (lastPage!= 0 && currentPage > lastPage) {
	        	$(this).setGridParam('page', lastPage);
	            $(this).setGridParam({postData: {page : lastPage}});
	        }	        
	        $(this).setGridParam({postData:{
							orgChildrenIds: orgIds.join(','), rosterId : 0
							}
						});	  
	    }
	});	
	if($("#createEducatorGrid")[0].grid && $("#createEducatorGrid")[0]['clearToolbar']){
		$("#createEducatorGrid")[0].clearToolbar();
	} 
	$gridAuto.data('selectedIds',null);
}

var selectedStudentsArray = [];
function initCreateStudentGrid(orgIds) { 
	
	var $gridAuto = $("#createStudentGrid");
	
	var gridWidthForVR = $('#createStudentGrid').parent().width();		
	if(gridWidthForVR < 1045) {
		gridWidthForVR = 1045;				
	}
	var cellWidthForVR = gridWidthForVR/5;
	selectedStudentsArray = [];
	var cmforViewRosters = [ 
                			{name: 'id', index: 'id', label:'createRosterStudentId', hidden:true},
                   			{ name : 'stateStudentIdentifier',label:'createRosterStateStudentIdentifier', index : 'stateStudentIdentifier', width : cellWidthForVR, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : false, hidedlg : true, 
                   				searchoptions:{ sopt:['cn'], dataInit: function(elem) {
                   				    
                   								} 							
                   							  }	
                   			
                   			},
                 			{ name : 'legalFirstName', index : 'legalFirstName',label:'createRosterLegalFirstName', width : cellWidthForVR, viewable: true, 
                   				searchoptions:{ sopt:['cn'], dataInit: function(elem) {
                   				    
                   				} 							
                   			}
                   			},
                   			{ name : 'legalMiddleName', index : 'legalMiddleName',label:'createRosterLegalMiddleName', width : cellWidthForVR, viewable: true, 
                   				searchoptions:{ sopt:['cn'], dataInit: function(elem) {}}},
                   			{ name : 'legalLastName', index : 'legalLastName',label:'createRosterLegalLastName', width : cellWidthForVR,  viewable: true,  hidden : false, hidedlg : true,
                   				searchoptions:{ sopt:['cn'], dataInit: function(elem) {
                   				    
                   				} 							
                   			}	
                   			},
                   			{ name : 'genderStr', index : 'genderStr',label:'createRosterGenderStr', width : cellWidthForVR,  viewable: true,  hidden : false, hidedlg : true},
                   			{ name: 'gradeLevel', index: 'gradeLevel',label:'createRostergradeLevel', hidden:true},
                   			{ name: 'name', index: 'name',label:'createRosterGradeName', width : cellWidthForVR}
                   		];
                        
	//JQGRID
	$gridAuto.scb({
		url : "getStudentsForRosters.htm?q=1",
		mtype: "POST",
		datatype : "json",
		width: gridWidthForVR,
	  	colModel :cmforViewRosters,
        colNames : ["id",
   		   			"Student Identifier",
   					"First Name",
   					"Middle Name",   					
   					"Last Name",
   					"Gender",
   					"Grade Level",
   					"Grade Course"
   		           ],      
		rowNum : 10,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		pager : '#createStudentGridPager',
		// Adding multiple column string flag for default sort F820
		//sortname : 'id',
		sortname : 'legalLastName,legalFirstName,legalMiddleName',
		loadonce: false,
		multiselect: true,
		columnChooser: false,
	    beforeRequest: function() {
	    	if(!$('#createRosterSearchForm').valid() && $(this).getGridParam('datatype') == 'json'){
	    		return false;
	    	}
	    	//Set the page param to lastpage before sending the request when 
			//the user entered current page number is greater than lastpage number.
			var currentPage = $(this).getGridParam('page');
	        var lastPage = $(this).getGridParam('lastpage');
	              
	        if (lastPage!= 0 && currentPage > lastPage) {
	        	$(this).setGridParam('page', lastPage);
	            $(this).setGridParam({postData: {page : lastPage}});
	        }	        
	        $(this).setGridParam({postData:{
							orgChildrenIds: orgIds.join(',') }
						});	  
	    },
	    onSelectRow: function(rowid, status, e){ 
	    
	    	//Added for F694
	     	var rData = $('#createStudentGrid').jqGrid('getRowData', rowid);
	    	var studentObj = {
	    			id : rData.id,
	    			gradeLevel : rData.gradeLevel,
	    			legalFirstName : rData.legalFirstName, 
	    			legalLastName : rData.legalLastName,
	    			stateStudentIdentifier : rData.stateStudentIdentifier  
				};
	    	
	    	   if(status){
	       		 var found = false; 
	       		 
	       		 $.each(selectedStudentsArray, function(i){
	     			    if(selectedStudentsArray[i].id === rData.id) {
	     			    	found = true;
	     			    }
	     			 }); 
	       		 if( ! found )
	       			selectedStudentsArray.push(studentObj);
	       		   
	       		
	       	   } else{
	       		   $.each(selectedStudentsArray, function(i){
	       			    if(selectedStudentsArray[i].id === rData.id) {
	       			    	selectedStudentsArray.splice(i,1);
	       			        return false;
	       			    }
	       			});
	       		  
	       		   //while deselecting it checkbox will automatically be unchecked, no need to do anything.
	       		   //do nothing.
	       	   } 
	    	    	
	    },
	    onSelectAll: function(aRowids,status) {
	    	//Added for F694
	    	var grid = $(this);
	        if (status) {
	            // uncheck "protected" rows	      
	            var allRowsIds = grid.jqGrid('getDataIDs');
	            for(var i=0;i<allRowsIds.length;i++){	
	            	
			        var result = $.grep(selectedStudentsArray, function(selectedObj){
			        	return selectedObj.id == allRowsIds[i];
			        });
			        
			        if(result.length == 0){
			        	 var rData = $('#createStudentGrid').jqGrid('getRowData',allRowsIds[i]);				    	
					     var studentObj =	{ 
					    		 id : rData.id,
					    		 gradeLevel : rData.gradeLevel,
					    		 legalFirstName : rData.legalFirstName, 
					    		 legalLastName : rData.legalLastName,
					    		 stateStudentIdentifier : rData.stateStudentIdentifier  
					    };
			        	
					     selectedStudentsArray.push(studentObj);
			        }	
			    }	
	        }
	        else{
	        	var allRowsIds = grid.jqGrid('getDataIDs');
	            for(var i=0;i<allRowsIds.length;i++){
	            	var result = $.grep(selectedStudentsArray, function(selectedObj){ 
	            		return selectedObj.id == allRowsIds[i]; 
	            	});
	            	if(result.length == 1 ){
	            		var indx = selectedStudentsArray.indexOf(result[0]);
	            		selectedStudentsArray.splice(indx,1);
	            	}	
	            }
	        }	   
	    },
		beforeSelectRow: function(rowid, e)
		{	$('#createRosterStudentSelectError').hide();		
		},
	    loadComplete: function(data) {
	    	var ids = $(this).jqGrid('getDataIDs');         
	         var tableid=$(this).attr('id');      
	            for(var i=0;i<ids.length;i++)
	            {         
	                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'legalFirstName') +' '+$(this).getCell(ids[i], 'legalLastName')+ ' Check Box');
	                $('#jqg_'+tableid+'_'+ids[i]).removeAttr("aria-checked",false); 
	            }                   
           var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
           $('#cb_'+tableid).attr('title','Student Grid All Check Box');
           $('#cb_'+tableid).removeAttr("aria-checked",false);
           $.each(objs, function(index, value) {
             var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));
                  $(value).attr('title',$(nm).text()+' filter');
                           
                  });	
	    }
	    
	});	
	if($("#createStudentGrid")[0].grid && $("#createStudentGrid")[0]['clearToolbar']){
		$("#createStudentGrid")[0].clearToolbar();
	} 
}


function validateCreateRoster() {
	$('#createRosterSearchForm').valid();
	var validationSuccess = true;
	if(!$('#createRosterForm').valid()){
		validationSuccess = false;
	}
	var selectedEducatorId = $('#createRoster,#createEducatorGrid').data('selectedIds');
    var selectedEducatorIdentifier = $('#createRoster,#createEducatorGrid').jqGrid ('getCell', selectedEducatorId, 'uniqueCommonIdentifier');
	if(selectedEducatorIdentifier=="" || selectedEducatorIdentifier== null || selectedEducatorIdentifier == undefined){
		$("#createRosterEducatorIdError").hide();
		$("#createRosterEducatorIdError").html("The selected educator does not have an Educator Identifier. Please add an Educator Identifier to the educator before creating the roster.").show();
		validationSuccess = false;
	}
	if(selectedEducatorId == null || selectedEducatorId.length==0) {
		$("#createRosterEducatorIdError").hide();
		$("#createRosterEducatorSelectError").html("Educator Selection Required").show();
		validationSuccess = false;
	}
	if($('#createRoster,#createStudentGrid')[0].grid){
		var selectedStudentIds = $('#createRoster,#createStudentGrid').jqGrid('getGridParam', 'allselectedrows').map(Number);
		if(selectedStudentIds.length==0) {
			$("#createRosterStudentSelectError").html("Student Selection Required").show();
			validationSuccess = false;
		}
	}else{
		$("#createRosterStudentSelectError").html("Student Selection Required").show();
		validationSuccess = false;
	}
	return validationSuccess;
}

jQuery.validator.addMethod('validateCourse', function(value, element){
	return value != '';
},'Please select course');

var rosterData; 
var arrayTest;
var confirmIndex;
var multipleRostersSize;

function createRoster() {
	var subjectCode = $('#createContentAreaSelect option:selected').attr("data-contentAreaCode");	
	var createCourseSelect = $('#createRoster,#createCourseSelect');
	var checkGreaterThanEqualToLowerBound= false;
			if(contractingOrgDisplayIdentifier!=null && contractingOrgDisplayIdentifier!=undefined 
			&& contractingOrgDisplayIdentifier.length> 0 
			&& subjectCode!=null && subjectCode!=undefined && subjectCode.length>0 					
			&& (contractingOrgDisplayIdentifier == 'DE' || contractingOrgDisplayIdentifier == 'DC') 
			&& subjectCode == 'Sci'){
				
				var lowerGradeBound = 10;
				if (contractingOrgDisplayIdentifier == 'DE') {
					lowerGradeBound = 10;
				} else if (contractingOrgDisplayIdentifier == 'DC'){
					lowerGradeBound = 9;
				}
				
				if(selectedStudentsArray.length>0){
					for(var i =0;i<selectedStudentsArray.length;i++){
							var gradeLevel = selectedStudentsArray[i].gradeLevel;					
				 			if(parseInt(gradeLevel) >= lowerGradeBound){
				 				checkGreaterThanEqualToLowerBound = true;
			     		}	
					}	
				}
					
				if(checkGreaterThanEqualToLowerBound && createCourseSelect.val() == ''){
					createCourseSelect.addClass('validateCourse');		
					$('#forceBiologyCourseError').html('Biology course is a required selection when students listed in grades '+lowerGradeBound+'-12');
					$('#forceBiologyCourseError').show();
					$('html, body').animate({
						scrollTop: $("#forceBiologyCourseError").offset().top
					}, 0);
		    		setTimeout(function(){ $("#forceBiologyCourseError").hide(); },3000);
				}else{
					createCourseSelect.removeClass('validateCourse');	
					createCourseSelect.removeClass('error');
					$('#forceBiologyCourseError').html('');
					$('#forceBiologyCourseError').hide();	
				}	
			}else{
				createCourseSelect.removeClass('validateCourse');	
				createCourseSelect.removeClass('error');	
				$('#forceBiologyCourseError').html('');
				$('#forceBiologyCourseError').hide();	
			}	
	if(!validateCreateRoster()){
		return false;
	}
	$("#createRosterStudentGradeError").hide();
	$('#forceBiologyCourseError').html('');
	$('#forceBiologyCourseError').hide();	
	$("#createRosterError").html("");
	$("#createRosterError").hide();
	$('#duplicateRosterError').html('');
	$('#duplicateRosterError').hide();
	$('#createRosterSuccess').html("");
	$('#createRosterSuccess').show();
	$("#createRosterEducatorIdError").hide();
	$('#createRosterEducatorSelectError').hide();
	$('#createRosterStudentSelectError').hide();
	var orgIds = [$('#createRosterOrgFilter_school').val()];
	var contentAreaId = $('#createRoster,#createContentAreaSelect').val();
	var name = $('#createRoster,#createRosterName').val();	
	var selectedEducatorId = $('#createRoster,#createEducatorGrid').jqGrid('getGridParam', 'selarrrow');
	var selectedStudentIds = $('#createRoster,#createStudentGrid').jqGrid('getGridParam', 'allselectedrows').map(Number);
	var courseId = $('#createRoster,#createCourseSelect').val();
	
	/** US19195: Enhance rostering so that unused testlets move with the student - UI ROSTER CHANGES ONLY ***for DLM only*** 
	 	We already have the studentids and contentareaid/courseid. 
	 	Need a check for each of the student if they are rostered already for the same subject.
	**/
	arrayTest = selectedStudentIds;
	rosterData = {
		orgIds: orgIds.join(','),
		name: name,
		contentAreaId: contentAreaId,
		educatorId: selectedEducatorId[0],
		addStudentIds: selectedStudentIds.join(','),
		courseId: courseId
	};
	
	/*
	 * if(non DLM)
	 * 	callCreateRoster(rosterData);
	 * else
	 *  checkForMultipleRostersToCreateRoster;
	 */

	if(isDLMUser){
		
		$.ajax({
			url: 'checkForMultipleRostersToCreateRoster.htm',
			data: rosterData,
			dataType: 'json',
			type: "GET",
			async: false
		}).done(function(data) {
			confirmIndex = 0;
			multipleRostersSize = data.length;
			
			for(var index=0;index<data.length;index++){
				
				var studentEnrlId = data[index].studentEnrlId;
				var enrollmentid = data[index].enrollmentId;
				var stateStudentIdentifier = data[index].student.stateStudentIdentifier;
				var legalFirstName = data[index].student.legalFirstName;
				var legalLastName = data[index].student.legalLastName;
				
				arrayTest = confirmDialogToCreateRoster(studentEnrlId, legalFirstName, legalLastName, stateStudentIdentifier, arrayTest);
				
			}	
				
			if(multipleRostersSize == 0){
			 	 if(rosterData.addStudentIds.length > 0){
			 		confirmCreateRoster(rosterData);
			     }
				 else{
				 	$("#createRosterStudentSelectError").html("Student Selection Required").show();
					$('#createRosterStudentSelectError').show();
				 }
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
		});
	}
	else
	{
		confirmCreateRoster(rosterData);
	}
	
}


function confirmCreateRoster(rosterData){
	var subjectCode = $('#createContentAreaSelect option:selected').attr("data-contentAreaCode");	
	var courseCode = $('#createCourseSelect option:selected').attr("data-Course");
	if(contractingOrgDisplayIdentifier!=null && contractingOrgDisplayIdentifier!=undefined && contractingOrgDisplayIdentifier.length> 0 
			&& subjectCode!=null && subjectCode!=undefined && subjectCode.length>0 
			&& courseCode!=null && courseCode!=undefined && courseCode.length> 0				
			&& (contractingOrgDisplayIdentifier == 'DE' || contractingOrgDisplayIdentifier == 'DC')  
			&& subjectCode == 'Sci' && courseCode == 'BIO'){
		
		var checkLessThanTenthGrade = false;
		var selectedStudents = [];
		
		var addStud = rosterData.addStudentIds.split(',');
		if(addStud.length>0){
			for(var k=0;k<addStud.length;k++){
				if(addStud[k]!='') selectedStudents.push(addStud[k]);
			}
		}
		
		var warningTextMsg = "The following student(s) are listed in a grade where the selected subject </br>"+
		"and course do not apply:</br></br>[ ";
		var warningStudentInfo="";
		if(selectedStudentsArray.length>0){
			var lowerGradeBound = 10;
			if (contractingOrgDisplayIdentifier == 'DE') {
				lowerGradeBound = 10;
			} else if (contractingOrgDisplayIdentifier == 'DC'){
				lowerGradeBound = 9;
			} 
			
			for(var i =0;i<selectedStudentsArray.length;i++){
				var id = selectedStudentsArray[i].id;				
					var gradeLevel = selectedStudentsArray[i].gradeLevel;					
					var legalFirstName = selectedStudentsArray[i].legalFirstName;
					var legalLastName = selectedStudentsArray[i].legalLastName;
					var stateStudentIdentifier = selectedStudentsArray[i].stateStudentIdentifier;
		 			if(parseInt(gradeLevel) < lowerGradeBound){		 				
		 				selectedStudents = jQuery.grep(selectedStudents, function(value) {
						  return value != id;
						});		 				
		 				checkLessThanTenthGrade = true;
		 				if(warningStudentInfo=='') warningStudentInfo = warningStudentInfo+legalFirstName+", "+legalLastName+" - "+stateStudentIdentifier;
		 				else warningStudentInfo = warningStudentInfo+",</br>"+legalFirstName+", "+legalLastName+" - "+stateStudentIdentifier;
					}			
						 
			}	
		}
		
		warningTextMsg = warningTextMsg+warningStudentInfo+" ]";		
		rosterData.addStudentIds = selectedStudents.join(',');
		
		if(checkLessThanTenthGrade){
			$('<div style="line-height: 20px;text-align:center;" ></div>').html(warningTextMsg).dialog({
		      height: 300,
		      width: 'auto',
		      modal: true,
		      closeOnEscape: false,
		      buttons: {
		        "Ok": function() {
		        	$(this).dialog("close");
		        	if(selectedStudents.length>0){
		        	callCreateRoster(rosterData);	
		        	}
		        },
		      	"Cancel": function() {		      		
		          	$(this).dialog( "close" );
		       	}
		      }
			});			
		}
		else{
			 callCreateRoster(rosterData);
		}
	}else{
		 callCreateRoster(rosterData);
	}
}

function callCreateRoster(rosterData){
	var name = $('#createRoster,#createRosterName').val();
	
	$.ajax({
		url: 'createRoster.htm',
		data: rosterData,
		dataType: 'json',
		type: "POST",
		async: false
	}).done(function(data) {
		
		if(data.success) {
			$('#forceBiologyCourseError').html('');
			$('#forceBiologyCourseError').hide();
			$('#createRosterError').html('');
			$('#createRosterError').hide();
			$('#duplicateRosterError').html('');
			$('#duplicateRosterError').hide();
			$('#createRosterSuccess').html("Successfully created: " + escapeHtml(name));
			$('#createRosterSuccess').show();
			$('#createRosterSuccess').focus();
			_clearInputs(rosterData.orgIds);
			$('html, body').animate({
				scrollTop: $("#createRosterSuccess").offset().top
			}, 1500);
		
		} else if(data.duplicate) {
			$('#duplicateRosterError').html('Roster name already exists for the selected Educator, Subject and Course');
			$('#duplicateRosterError').show();
			$('html, body').animate({
				scrollTop: $("#duplicateRosterError").offset().top
			}, 1500);
			setTimeout(function(){ $("#duplicateRosterError").hide(); },3000);
		}
		else if(data.nopermit){
			$("#rostermessages_createroster").show();
    		$('#ksPermissionDeniedMessage_createRoster').show();
    		$('html, body').animate({
				scrollTop: $("#ksPermissionDeniedMessage_createRoster").offset().top
			}, 0);
    		setTimeout(function(){ $("#ksPermissionDeniedMessage_createRoster").hide(); },3000);
    		setTimeout(function(){ $("#rostermessages_createroster").hide(); },3000);
		}
		else{
			$('#createRosterError').html('Error:' + textStatus);
			$('#createRosterError').show();	
		}
			
	}).fail(function(jqXHR, textStatus, errorThrown) {
		$('#createRosterError').html('Error:' + textStatus);
		$('#createRosterError').show();			
	});
}


function _clearInputs(orgIds) {	
	$('#createRoster,#createRosterName').val('');
	$('#createRoster,#createRosterOrgFilter_district').val("").trigger('change.select2');
	$('#createRoster,#select2-createRosterOrgFilter_school-container').html("");
	$('#createRoster,#select2-createRosterOrgFilter_school-container').append($('<option></option>').val("").html("Select")).trigger('change.select2');
	//$('#createContentAreaSelect').val("").trigger('change.select2');
	$('#createRoster,#createContentAreaSelect').html("");
	$('#createRoster,#createContentAreaSelect').append($('<option></option>').val("").html("Select")).trigger('change.select2');
	//$('#createRoster,#createContentAreaSelect').val('');
	$('#createRoster,#createCourseSelect').val('');
	$("#createEducatorGrid").jqGrid('clearGridData');
	$("#createEducatorGrid").jqGrid("GridUnload");	
	$("#createStudentGrid").jqGrid('clearGridData');
	$("#createStudentGrid").jqGrid("GridUnload");
}

function confirmDialogToCreateRoster(enrollmentid, legalFirstName, legalLastName, stateStudentIdentifier, arrayTest) {
    var okButtonText = "Yes";
    var cancelButtonText = "No";
    var title = "Confirm";

    var message = "Student ["+legalFirstName+" "+legalLastName+" - "+stateStudentIdentifier+"] is already assigned to a roster for the subject you selected. Do you want to remove the student from the other roster and add them to this roster?";
    
    var deferred = $.Deferred();
    $('<div title="' + title + '">' + message + '</div>').dialog({
        modal: true,
        resizable: false,
		height: 250,
		width: 500,
		zIndex: 10000,
        autoOpen: true,
        closeOnEscape: false,
        create: function(event, ui) { 
            var widget = $(this).dialog("widget");      
        }, 
        buttons: [{
            // The OK button
            text: okButtonText,
            click: function () {
                // Resolve the promise as true indicating the user clicked "OK"
                deferred.resolve(true);
                $(this).dialog("close");
                confirmIndex++;
	            if(confirmIndex == multipleRostersSize){
					 if(rosterData.addStudentIds.length > 0){
						 confirmCreateRoster(rosterData);
				     }
					 else{
					 	$("#createRosterStudentSelectError").html("Student Selection Required").show();
						$('#createRosterStudentSelectError').show();
					 }
			 	}
            }
        }, {
            // The Cancel button
            text: cancelButtonText,
            click: function () {
            	deferred.resolve(false);
                $(this).dialog("close");
                if(arrayTest.indexOf(enrollmentid )>=0)
    				arrayTest.splice(arrayTest.indexOf(enrollmentid ), 1);
    			rosterData.addStudentIds = arrayTest.join(',');
    		 	confirmIndex++;
	            
			 	if(confirmIndex == multipleRostersSize){
					 if(rosterData.addStudentIds.length > 0){
						 confirmCreateRoster(rosterData);
				     }
					 else{
					 	$("#createRosterStudentSelectError").html("Student Selection Required").show();
						$('#createRosterStudentSelectError').show();
					 }
			 	}

            }
        }],
        close: function (event, ui) {
            // Destroy the jQuery UI dialog and remove it from the DOM
            $(this).dialog("destroy").remove();
        }
    });
   
    return arrayTest;
}
