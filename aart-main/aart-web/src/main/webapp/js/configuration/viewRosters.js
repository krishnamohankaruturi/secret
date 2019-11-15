/**
 * Global variables to to keep track of added/removed student enrollments 
 */
var origStudentIds = [];
var addStudentIds = [];
var delStudentIds = [];
var saveEditRoster = false;
var savedAssignedEducatorId = 0;
//During Changed - DE10448 
/*$(document).ready(function(){
	viewRostersInit();
}); */
function viewRostersInit(){	
		gViewRosterLoadOnce = true;
		$('#viewRosterOrgFilter').orgFilter({
			containerClass: '',
			requiredLevels: [70]
		});
		if(globalUserLevel <= 50){
			$('#viewRosterOrgFilter').orgFilter('option','requiredLevels',[50]);
		}

		$('#viewRosterOrgFilterForm').validate({ignore: ""});

		initViewRosterSelectors();		
		initViewRosterGrid();		
		initEditEducatorGrid();		
		initEditStudentGrid();
		
		$('#searchviewRosters').on("click",function() {			
			if($('#viewRosterOrgFilterForm').valid() && $('#viewRosterOrgFilter').orgFilter('value') != null) {
				var $gridAuto = $("#viewRostersTableId");
				if($gridAuto[0].grid && $gridAuto[0]['clearToolbar']){
					$gridAuto[0].clearToolbar();
				}
				$gridAuto.jqGrid('setGridParam',{
					datatype:"json", 
					url : 'getRostersToView.htm?q=1', 
					search: false, 
					postData: { "filters": ""},
					gridComplete: function() {						   
				         var tableid=$(this).attr('id'); 			           
				            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');			           
				             $.each(objs, function(index, value) {         
				              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
				                    $(value).attr('title',$(nm).text()+' filter');                          
				                    });
					}
				}).trigger("reloadGrid",[{page:1}]);
			}
		});
			
		filteringOrganizationSet($('#viewRosterOrgFilterForm'));
	

		$(window).bind('resize', function(e)
				{
				    window.resizeEvt;
				    $(window).resize(function()
				    {
				        clearTimeout(window.resizeEvt);
				        window.resizeEvt = setTimeout(function()
				        {
				        	resizeGrid()
				        }, 100);
				    });
				});
		
};

function resizeGrid() {
	
	var viewRosterPopupWidth = $('#viewRosterPopup').width() - 20;
	if(viewRosterPopupWidth>994) {
		$("#editEducatorGrid").jqGrid("setGridWidth",viewRosterPopupWidth);
		$("#editStudentGrid").jqGrid("setGridWidth",viewRosterPopupWidth);				
	} 
}


function initViewRosterSelectors() {
	$.ajax({
		url: 'getRosterDropdownData.htm',
		data: { },
		dataType: 'json',
		type: "GET"
	}).done(function(data) {
		for(var i=0;i<data.contentAreas.length;i++) {
			$('#viewContentAreaSelect').append($('<option data-contentAreaCode = \"'+data.contentAreas[i].abbreviatedName+'\" value=\"'+data.contentAreas[i].id+'\"></option>').html(data.contentAreas[i].name));
		}
		/**
		 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15421 : Roster - refactor Add Roster manually page
		 * Add course filter on selection of content area, this is optional.
		 */
		$('#viewContentAreaSelect').on("change",function(c,stateCoursesId){
			$('#viewCourseSelect').find('option:not(:first)').remove();
			getCoursesForViewRoster($(this).val(), stateCoursesId);
		});
	});

}

/**
 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15421 : Roster - refactor Add Roster manually page
 * Load course list on selection of content area.
 * @param contentAreaId
 */
function getCoursesForViewRoster(contentAreaId, stateCoursesId){
	
	if (typeof(contentAreaId) != 'undefined'){
		if (contentAreaId == ''){
			return;
		}
		var select = $('#viewCourseSelect');
		select.find('option:not(:first)').remove();
		var url = 'getCoursesByContentArea.htm';
		var data = {};
		data.contentAreaId = contentAreaId;
		$.ajax({
			url : url,
			data: data,
			dataType: 'json',
			type: 'GET'
		}).done(function(data) {				
			if (data !== undefined && data !== null && data.courses != undefined && data.courses.length > 0){
        		for (var i = 0, length = data.courses.length; i < length; i++){
        			if(data.courses[i].abbreviatedName == 'BIO') {
        				select.append($('<option data-Course="'+data.courses[i].abbreviatedName+'" ></option>').attr("value", data.courses[i].id).text(data.courses[i].name));
        			}
        		}
        		if (data.courses.length == 1){
        			select.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
        			select.trigger('change');
				} else {
					select.prop('disabled', false);
				}
        		if(typeof(stateCoursesId) != 'undefined' && stateCoursesId != null && stateCoursesId != ''){
	        		$('#viewRosterPopup #viewCourseSelect').val(stateCoursesId);	        			
        		}
        	} else {
        		// error
        	}
		});
	}
	
}

function initViewRosterGrid() {
	var $gridAuto = $("#viewRostersTableId");
	//Unload the grid before each request.
	$("#viewRostersTableId").jqGrid('clearGridData');
	$("#viewRostersTableId").jqGrid("GridUnload");
	var gridWidthForVR = $gridAuto.parent().width();		
	if(gridWidthForVR < 740) {
		gridWidthForVR = 740;				
	}
	var cellWidthForVR = gridWidthForVR/5;
	var showHideCourse = false;
	if($('#currentAssessmentProgram').val()=="PLTW"){
		showHideCourse = true;
	}
	var cmforViewRosters = [	         		       
	                        { name : 'id', index : 'id',label:'viewrosterID', width : cellWidthForVR, search : false, hidden: true, hidedlg: false },
	        				{ name : 'name', index : 'name',label:'rosterName', width : cellWidthForVR, search : true, hidden: false, hidedlg: false,
	                        	formatter: escapeHtml
	                        },
	        				{ name : 'edUniqueCommonId', index : 'edUniqueCommonId', width : cellWidthForVR, search : true, hidden : false, hidedlg : false,
	                        	formatter: escapeHtml
	                        },
	        				{ name : 'firstName', index : 'firstName', label : 'view_roster_educator_firstname', width : cellWidthForVR, search : true, hidden : false, hidedlg : false,
	                        	formatter: escapeHtml
	                        },
	        				{ name : 'lastName', index : 'lastName', label : 'view_roster_educator_lastname', width : cellWidthForVR, search : true, hidden : false, hidedlg : false,
	                        	formatter: escapeHtml
	                        },
	        				{ name : 'contentArea', index : 'contentArea', width : cellWidthForVR, search : true, hidden : false, hidedlg : false},
	        				{ name : 'stateCoursesName', index : 'stateCoursesName', width : cellWidthForVR, search : true, hidden : showHideCourse, hidedlg : showHideCourse},
	        				{ name : 'edId', index : 'edId', width : 0, search : true, hidden : true, hidedlg : true,
	                        	formatter: escapeHtml
	                        },
	        				{ name : 'contentAreaId', index : 'contentAreaId', width : 0, search : true, hidden : true, hidedlg : true},
	        				{ name : 'edStatus', index : 'edStatus', width : 0, search : false, hidden : false, hidedlg : true,sort: false},
	        				{ name : 'stateCoursesId', index : 'stateCoursesId', width : 0, search : true, hidden : true, hidedlg : true},
	        				{ name : 'currentSchoolYear', index : 'currentSchoolYear', width : 0, search : false, hidden : false, hidedlg : true},
	        				{ name : 'schoolName', index : 'schoolName', width : 0, search : true, hidden : false, hidedlg : true,
	                        	formatter: escapeHtml
	                        },
	                        // US16275 Add field
	                        { name : 'schoolId', index : 'schoolId', width : cellWidthForVR, search : false, hidden: true, hidedlg: false}
	        			];
	
	var cnforViewRosters = [
						"Roster Id",    // US16275 Rename the field
						"Roster Name",
						"Educator Identifier",
						"First Name",
						"Last Name",
						$('#currentAssessmentProgram').val()=="PLTW" ? 'Course' : 'Subject',
						"Course",
						'edId',
						'contentAreaId',
						'Educator Status',
						"Course Id",
						"School Year",
						"School Name",
						"School Id"    // US16275 Add Field
					];	

	//JQGRID
	$gridAuto.scb({
		//url : 'getRostersToView.htm?q=1',
		mtype: "POST",
		datatype : "local",
		width: gridWidthForVR,
	  	colModel :cmforViewRosters,
	  	colNames: cnforViewRosters,
		rowNum : 10,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		pager : '#viewRosterGridPager',
		sortname : 'name',
		loadonce: false,
		onSelectRow: function (rowId,status,e) {	
	   		var rowData = $("#viewRostersTableId").jqGrid('getRowData', rowId);
	   		var orgs = new Array();
        	orgs.push($('#viewRosterOrgFilter').orgFilter('value'));
	   	    openViewRosterPopup(rowData,orgs);
	   	},
	    beforeRequest: function() {
	    	if(!$('#viewRosterOrgFilterForm').valid() && $(this).getGridParam('datatype') == 'json'){
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
	        
	        if($('#viewRosterOrgFilter').orgFilter('value') != null)  {
	        	var orgs = new Array();
	        	orgs.push($('#viewRosterOrgFilter').orgFilter('value'));
	        	$(this).setGridParam({postData: {'orgChildrenIds': function() {
						return orgs;
					}}});
	        } else if($(this).getGridParam('datatype') == 'json') {
        		return false;
	        }
	        
	      
	    },
	    loadComplete: function() {
       	    var tableid=$(this).attr('id'); 			           
            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');			           
             $.each(objs, function(index, value) {         
              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
                    $(value).attr('title',$(nm).text()+' filter');                          
                    });
   }
	});	

}

function initEditEducatorGrid() {
	
	var $gridAuto = $("#viewRosterPopup #editEducatorGrid");
	//Unload the grid before each request.
	$("#viewRosterPopup #editEducatorGrid").jqGrid('clearGridData');		
	var cmforViewRosters = [
	            			{ name : 'firstName', index : 'firstName', label : 'edit_roster_educator_firstname', editable : true, editrules:{edithidden:true}, viewable: true, 
	              				searchoptions:{ sopt:['cn'], dataInit: function(elem) {}},
	                        	formatter: escapeHtml
	                        },
	              			{ name : 'surName', index : 'surName', editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, hidden : false, hidedlg : true,
	              				searchoptions:{ sopt:['cn'], dataInit: function(elem) {}},
	                        	formatter: escapeHtml
	                        },
                  			{ name : 'uniqueCommonIdentifier', index : 'uniqueCommonIdentifier', editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : false, hidedlg : true, 
                  				searchoptions:{ sopt:['cn'], dataInit: function(elem) {}},
	                        	formatter: escapeHtml
	                        },
                  			{name: 'statusCode', index: 'statusCode', hidden:false},
		                    {name: 'id', key:true, index: 'id',label:'educatorId', hidden:true},
	              			{name: 'email', index: 'email', hidden:false, 
	                        	formatter: escapeHtml
	                        },
	              			{name: 'rosterAssigned', index: 'rosterAssigned', hidden:true, hidedlg : true}];
	//JQGRID
	$gridAuto.scb({
		//url : "getEducatorsForRosters.htm?q=1",
		mtype: "POST",
		datatype : "local",
	  	colModel :cmforViewRosters,
	  	colNames : [
					"First Name",
					"Last Name",
		   			"Educator Identifier",
		            "Status",
	  	            "Id",
					"Email",
					"Roster Assigned"],     
		rowNum : 10,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		pager : '#editEducatorGridPager',
		sortname: 'surName,firstName',
		sortorder: 'asc',
		loadonce: false,
		singleselect:true,
		grouping: true,
		columnChooser: false,
		autoWidth:true,
		shrinkToFit: true,
		groupingView:  {
			groupField: ['rosterAssigned'],
			groupColumnShow : [ false ],
			groupText : [ '<b>{0}</b>'],
			groupCollapse : false,
			groupDataSorted : false
		},
		beforeSelectRow: function(rowid, e)
		{
			$("#viewRosterPopup #editEducatorGrid").data('selectedIds', [rowid]);
		    return(true);
		}, 
		loadComplete: function(data) {
			$("#viewRosterPopup #editEducatorGrid").jqGrid('resetSelection');
			var rowIds = $(this).jqGrid("getDataIDs");
            if(null != rowIds && $(this).data('selectedIds') != null) {
    			 var selectedIds = $(this).data('selectedIds');
    			 for (var i = 0; i < rowIds.length; i++) {
	                if ($.inArray( rowIds[i], selectedIds ) > -1) {
	                	$(this).jqGrid('setSelection', rowIds[i], true);
	                	savedAssignedEducatorId = rowIds[i];
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
	            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');	            
	             $.each(objs, function(index, value) {         
	              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
	                    $(value).attr('title',$(nm).text()+' filter');                          
	                    });
	          var textboxs=   $('a[role="textbox"]');
	          $.each(textboxs, function(index, value) { 
	        	  $(value).attr('title','Page Number'); 
	          });
	    
		},
		 onSelectRow: function(rowid, status, e){
			  var rData = $('#editEducatorGrid').jqGrid('getRowData',rowid);
			 
			  if(rData.statusCode!='Inactive'){
				  $('#saveEditRoster').prop("disabled",false);
					$('#saveEditRoster').removeClass('ui-state-disabled');  
					$('#inactiveEdMsg').hide();
			  } else {
				  $('#saveEditRoster').prop("disabled",true);
					$('#saveEditRoster').addClass('ui-state-disabled');
					$('#inactiveEdMsg').show();
			  }
			  
		}
	});	
	$gridAuto.data('selectedIds',null);
}
var selectedViewStudentsArray = [];
function initEditStudentGrid() {
	var $gridAuto = $("#viewRosterPopup  #editStudentGrid");
	//Unload the grid before each request.
	$("#viewRosterPopup  #editStudentGrid").jqGrid('clearGridData');
	selectedViewStudentsArray = [];
	var cmforViewRosters = [ 
                			{name: 'id', index: 'id', label:'studentId', hidden:true},
                   			{ name : 'stateStudentIdentifier', index : 'stateStudentIdentifier', label : 'stateStudentIdentifierViewRoster',  editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : false, hidedlg : true, 
                   				searchoptions:{ sopt:['cn'], dataInit: function(elem) {} },
	                        	formatter: escapeHtml
	                        },
                 			{ name : 'legalFirstName', index : 'legalFirstName', label : 'legalFirstNameViewRoster',  viewable: true, 
                   				searchoptions:{ sopt:['cn'], dataInit: function(elem) {}},
	                        	formatter: escapeHtml
	                        },
                     		{ name : 'legalMiddleName', index : 'legalMiddleName', label : 'legalMiddleNameViewRoster', viewable: true, 
                       				searchoptions:{ sopt:['cn'], dataInit: function(elem) {}},
    	                        	formatter: escapeHtml
    	                    },
                   			{ name : 'legalLastName', index : 'legalLastName', label : 'legalLastNameViewRoster',  viewable: true,  hidden : false, hidedlg : true,
                   				searchoptions:{ sopt:['cn'], dataInit: function(elem) {}},
	                        	formatter: escapeHtml
	                        },
                       		{ name : 'genderStr', index : 'genderStr', label : 'genderStrViewRoster',  viewable: true,  hidden : false, hidedlg : true, stype: 'select', searchoptions:{sopt:['eq'], value: ":ALL;1:MALE;0:FEMALE"}},
                       		{ name: 'gradeLevel', index: 'gradeLevel', label : 'gradeLevelViewRoster', hidden:true},
                       		{name: 'name', index: 'name', label : 'edit_roster_student_grade_name'},
                   			{name: 'rosterassigned', index: 'rosterassigned'},
                   			// US16275 Add field
                   			{ name : 'localStudentIdentifier', index : 'localStudentIdentifier', label : 'localStudentIdentifierViewRoster',  viewable: true,  hidden : false, hidedlg : true}
                   		];
	
	//JQGRID
	$gridAuto.scb({
		//url : "getStudentsForRosters.htm?q=1",
		mtype: "POST",
		datatype : "local",
	  	colModel :cmforViewRosters,
        colNames : ["Id",
   		   			"Student Identifier",
   					"First Name",
   					"Middle Name",
   					"Last Name",
   					"Gender",
   					"Grade Level",
   					"Grade Course",
   					"rosterassigned",
   					"Local Student Id"   // US16275 Add field
   		           ],      
		rowNum : 10,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		pager : '#editStudentGridPager',
		loadonce: false,
		multiselect: true,
		grouping: true,
		columnChooser: false,
		autoWidth:true,
		shrinkToFit: true,
		scrollOffset: 0,
		groupingView:  {
			groupField: ['rosterassigned'],
			groupColumnShow : [ false ],
			groupText : [ '<b>{0}</b>'],
			groupCollapse : false,
			groupDataSorted : true
		}
		,loadComplete: function(data) {
			$("#viewRosterPopup #editStudentGrid").jqGrid('resetSelection');
			var rowIds = $(this).jqGrid("getDataIDs");
            if(null != rowIds && $(this).data('selectedIdsStr') != null) {
    			var selectedIds = $(this).data('selectedIdsStr');
	            for (var i = 0; i < rowIds.length; i++) {
	                if ($.inArray( rowIds[i], selectedIds ) > -1) {
	                	$(this).jqGrid('setSelection', rowIds[i], true);
	                }
	            }
	            
	            /**
	             * Biyatpragyan Mohanty : DE9075 : Unable to use search features in View/Edit Roster
	             * After the grid load is complete, clear previous values of the arrays
	             * Take all selected students into origStudentIds, this will never be changed
	             * Copy the origStudentIds to addStudentIds, this will always be changed based on add/remove
	             * delStudentIds will be used to keep track of removed student enrollments
	             */
        		delStudentIds = [];
        		origStudentIds = [];
        		addStudentIds = [];
        		
	        	if(typeof(selectedIds) != 'undefined' && selectedIds != null){
	        		origStudentIds = selectedIds.map(Number);
	        		//addStudentIds = selectedIds.map(Number).slice(0);
	        	}
	        	
            } 
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
	                    if ( $(value).is('select')) {
		                	   $(value).removeAttr("role");
		                	   $(value).css({"width": "100%"});
		                	   
		                    	$(value).select2({
		              	   		  placeholder:'Select',
		            	   		  multiple: false,
		            	      		  allowClear : true
		            	   		 });
		                    };
	                    
	                    });
	          
		}
		,onSortCol: function(index, iCol, sortorder){
			// store custom sort order
			$(this).data('sortinfo', index + ' ' + sortorder);
		}
		/**
		 * Biyatpragyan Mohanty : DE9075 : Unable to use search features in View/Edit Roster
		 * Track the user click i.e. checked or unchecked a row.
		 * If checked, add the id to addStudentIds if not already exists
		 * If unchecked, remove the id from addStudentIds, add to delStudentIds
		 * There should not be any id that is in both the arrays.
		 */
		,onSelectRow: function(idd, status) {
			id = Number(idd);
    		if(status) {
    			var existing = false;  
    			if($.inArray(id, origStudentIds) >= 0){
    				existing = true;
	        	}
	        	
	        	if(!existing) {
	        		/**
	        		 * Since this is a new id that does not exist in origStudentIds or addStudentIds, add it.
	        		 */
	        		addStudentIds.push(id);
	        	} else if($.inArray(id, delStudentIds)>=0){
	        		/**
	        		 * User might have checked/unchecked the same row multiple times, act accordingly.
	        		 */
	        		addStudentIds.push(id);
	        	}
	        	/**
	        	 * Since this is add operation, remove the id from delStudentIds
	        	 */
	        	delStudentIds= $.grep(delStudentIds, function(value) {
					  return value != id;
				});	        	
			} else {
				/**
				 * Remove id from addStudentIds if exists.
				 */
				addStudentIds = $.grep(addStudentIds, function(value) {
					  return value != id;
				});
				
				/**
				 * If id is in origStudentIds, that means student was previously enrolled and user wants
				 * to remove it.
				 */
				if($.inArray(id, origStudentIds) >= 0){
					delStudentIds.push(id);
	        	}				
			}	
    		
	    	//Added for F694
	     	var rData = $(this).jqGrid('getRowData', id);
	    	var studentObj = {
	    			id : rData.id,
	    			gradeLevel : rData.gradeLevel,
	    			legalFirstName : rData.legalFirstName, 
	    			legalLastName : rData.legalLastName,
	    			stateStudentIdentifier : rData.stateStudentIdentifier  
				};
	    	
	    	   if(status){
	       		 var found = false; 
	       		 
	       		 $.each(selectedViewStudentsArray, function(i){
	     			    if(selectedViewStudentsArray[i].id === rData.id) {
	     			    	found = true;
	     			    }
	     			 }); 
	       		 if( ! found )
	       			selectedViewStudentsArray.push(studentObj);
	       		   
	       		
	       	   } else{
	       		   $.each(selectedViewStudentsArray, function(i){
	       			    if(selectedViewStudentsArray[i].id === rData.id) {
	       			    	selectedViewStudentsArray.splice(i,1);
	       			        return false;
	       			    }
	       			});
	       		  
	       		   //while deselecting it checkbox will automatically be unchecked, no need to do anything.
	       		   //do nothing.
	       	   } 
	    	   
    		
		},
		onSelectAll: function(ids, status) {			
    		if(status) {
    			/**
    			 * Do the same operation for all ids.
    			 */
				for(var i=0; i<ids.length; i++) {
					var item = Number(ids[i]);
					var existing = false;  
	    			if($.inArray(item, origStudentIds) >= 0){
	    				existing = true;
		        	}
		        	
		        	if(!existing) {
		        		/**
		        		 * Since this is a new id that does not exist in origStudentIds or addStudentIds, add it.
		        		 */
		        		addStudentIds.push(item);
		        	} else if($.inArray(item, delStudentIds)>=0){
		        		/**
		        		 * User might have checked/unchecked the same row multiple times, act accordingly.
		        		 */
		        		addStudentIds.push(item);
		        	}
		        	/**
		        	 * Since this is add operation, remove the id from delStudentIds
		        	 */
		        	delStudentIds= $.grep(delStudentIds, function(value) {
						  return value != item;
					});
				}
    		} else {
    			for(var i=0; i<ids.length; i++) {
    				var item = Number(ids[i]);
    				/**
    				 * Remove id from addStudentIds if exists.
    				 */
    				addStudentIds = $.grep(addStudentIds, function(value) {
  					  return value != item;
    				});
    				/**
    				 * If id is in origStudentIds, that means student was previously enrolled and user wants
    				 * to remove it.
    				 */
    				if($.inArray(item, origStudentIds) >= 0){
    					delStudentIds.push(item);
    	        	}
    			}
    		}  
    		
    		//Added for F694
	    	var grid = $(this);
	        if (status) {
	            // uncheck "protected" rows	      
	            var allRowsIds = grid.jqGrid('getDataIDs');
	            for(var i=0;i<allRowsIds.length;i++){	
	            	
			        var result = $.grep(selectedViewStudentsArray, function(selectedObj){
			        	return selectedObj.id == allRowsIds[i];
			        });
			        
			        if(result.length == 0){
			        	 var rData = grid.jqGrid('getRowData',allRowsIds[i]);				    	
					     var studentObj =	{ 
					    		 id : rData.id,
					    		 gradeLevel : rData.gradeLevel,
					    		 legalFirstName : rData.legalFirstName, 
					    		 legalLastName : rData.legalLastName,
					    		 stateStudentIdentifier : rData.stateStudentIdentifier  
					    };
			        	
					     selectedViewStudentsArray.push(studentObj);
			        }	
			    }	
	        }
	        else{
	        	var allRowsIds = grid.jqGrid('getDataIDs');
	            for(var i=0;i<allRowsIds.length;i++){
	            	var result = $.grep(selectedViewStudentsArray, function(selectedObj){ 
	            		return selectedObj.id == allRowsIds[i]; 
	            	});
	            	if(result.length == 1 ){
	            		var indx = selectedViewStudentsArray.indexOf(result[0]);
	            		selectedViewStudentsArray.splice(indx,1);
	            	}	
	            }
	        }
		}
	});	
	$(this).data('selectedIdsStr', null);
}

function reloadEditEducatorGrid(edId,orgs, rosterId) { 
	var $gridAuto = $("#viewRosterPopup #editEducatorGrid");
	$gridAuto.jqGrid('setGridParam',{
		url : 'getEducatorsForRosters.htm?q=1', 
		search: false, datatype:"json", 
		loadonce: false,
		postData: { "filters": "",rosterId: rosterId, orgChildrenIds: orgs.join(',')},
		allselectedrows: [edId.toString()],
	});
	$gridAuto.data('selectedIds', [edId.toString()]);
	$gridAuto.trigger("reloadGrid",[{page:1}]);
	$gridAuto[0].clearToolbar();
}

// TODO Fix this...if more than 1000 students are on a roster, the grid doesn't show them all as selected.
// Ideally, the ajax call shouldn't need to be to this URL, as it contains a lot of data that the success callback doesn't need.
function getEnrolledStudentIds(rosterId,orgIds) {
	var studentIds=null;
	$.ajax({
		url: 'getStudentEnrollmentsByRosterId.htm?q=1',
		data: { rosterId: rosterId,
				rows: 1000,
				page: 1,
				sidx: '',
				sord: ''
		},
		async: false,
		dataType: 'json',
		type: "POST"
	}).done(function(data) {
		var ids = data.rows;
		studentIds = new Array();
		for (var i=0;i<ids.length;i++) {
			 studentIds.push(ids[i].id);
		}
	});
	return studentIds;
}

function reloadEditStudentGrid(rosterId,orgs) {
	selectedViewStudentsArray = [];
	var $gridAuto = $("#viewRosterPopup #editStudentGrid");
	var selectedIdsStr = new Array();
	var selectedIds = getEnrolledStudentIds(rosterId,orgs);
	var sortInfo = $gridAuto.data('sortinfo');
	var sortColumn = 'legalLastName,legalFirstName,legalMiddleName';
	var sortOrder = 'asc';
	if (typeof (sortInfo) !== 'undefined'){
		var split = sortInfo.split(' ');
		sortColumn = split[0];
		sortOrder = split[1];
	}
	for(var j=0;j<selectedIds.length;j++) {
		selectedIdsStr.push(selectedIds[j].toString());
	}
	$gridAuto.data('origStudentIds',selectedIds);
	$gridAuto.data('selectedIdsStr',selectedIdsStr);
	$gridAuto.jqGrid('setGridParam',{
		url : 'getStudentsForRosters.htm?q=1', 
		search: false, 
		datatype:"json",
		loadonce: false,
		postData: { filters: "", 
					orgChildrenIds: orgs.join(','),
					rosterId: rosterId },	
		allselectedrows: selectedIdsStr,
		page:1,
		sortname: sortColumn,
		sortorder: sortOrder
	});
	$gridAuto.trigger("reloadGrid",[{page: 1}]);
	$gridAuto[0].clearToolbar();
}


function openViewRosterPopup(rowData,orgIds) {
	$('#viewRosterPopup').data('rosterId',rowData.id);
	$('#viewRosterPopup').data('schoolId',rowData.schoolId);
	$('#viewRosterPopup').data('schoolYear',rowData.currentSchoolYear);
	$('#inactiveEdMsg').hide();
	$('#viewRosterPopup #viewRosterName').val(rowData.name);
	$('#viewRosterPopup #viewContentAreaSelect').val(rowData.contentAreaId);

	var selectc = $('#viewContentAreaSelect');
	selectc.trigger('change', rowData.stateCoursesId);
	
	var viewTitle ='';
	if(editRosterPermission)
		viewTitle = "View/Edit Roster - " + rowData.name;
	else {
		viewTitle = "View Roster - " + rowData.name;
		$('#viewContentAreaSelect').prop('disabled',true);
		$('#viewContentAreaSelect').addClass('ui-state-disabled');
		$('#viewRosterName').prop('disabled',true);
		$('#viewRosterName').addClass('ui-state-disabled');
	}
	$('#viewRosterPopup').dialog({
		autoOpen: false,
		modal: true,
		//resizable: false,
		minWidth: 1060,
		minHeight:550,
		width: 1060,
		height: 640,
		title: escapeHtml(viewTitle),
		create: function(event, ui) { 
		    var widget = $(this).dialog("widget");
		},
		close: function(ev, ui) {	
			$("#viewRostersTableId").jqGrid('resetSelection');
		},
		open: function(ev, ui) {
			$('#viewRosterSaveError').hide();
			
		    if(rowData.edStatus == 'Active' || rowData.edStatus == 'Pending') {
				$('#inactiveEdMsg').hide();
				saveEditRoster = false;
			} else {
				$('#inactiveEdMsg').show();
				//US17273
				saveEditRoster = true;
				
			}
			reloadEditEducatorGrid(rowData.edId,orgIds, rowData.id);
			reloadEditStudentGrid(rowData.id,orgIds);
		},
		resizeStop: function() {
	        $(this).closest(".ui-dialog-content").css({"height": "90%","width": "100%"});
	    }
	}).dialog('open');	
	if(editRosterPermission){
		
		$('#viewContentAreaSelect').prop('disabled',true);
		$('#viewContentAreaSelect').addClass('ui-state-disabled');
		$('#course').addClass('ui-state-disabled disableElement');
		
		if(saveEditRoster){
		$( "#viewRosterPopup" ).dialog( "option", "buttons", [ { text: "Save",id:"saveEditRoster", click: function() { saveRoster(); } } ] );
		$('#saveEditRoster').prop("disabled",true);
		$('#saveEditRoster').addClass('ui-state-disabled');
		}else {
		$( "#viewRosterPopup" ).dialog( "option", "buttons", [ { text: "Save",id:"saveEditRoster", click: function() { saveRoster(); } } ] );
		}
	}
}

function editRosterValidate() {
	var selectedEducatorId = $('#editEducatorGrid').data('selectedIds');
	var selectedEducatorIdentifier = $('#editEducatorGrid').jqGrid ('getCell', selectedEducatorId, 'uniqueCommonIdentifier');
	if(selectedEducatorIdentifier=="" || selectedEducatorIdentifier== null || selectedEducatorIdentifier == undefined){
		$("#viewRosterSaveError").html("The selected educator does not have an Educator Identifier. Please add an Educator Identifier to the educator before creating the roster.").show();
		$('#viewRosterSaveError').removeAttr('hidden');
		$('#viewRosterSaveError').show();
		return false;
	}
	$('#viewRosterSaveError').hide();
	var name = $('#viewRosterPopup #viewRosterName').val().trim();
	
	if(name.length == 0) {
		$('#viewRosterSaveError').html("Roster Name is a Mandatory Field");
		$('#viewRosterSaveError').show();
		return false;
	}
	var contentAreaId = $('#viewRosterPopup #viewContentAreaSelect').val();
	if(contentAreaId == 0) {
		$('#viewRosterSaveError').html("Subject is a Mandatory Field");
		$('#viewRosterSaveError').show();
		return false;
	}
	var selectedEducatorId = $('#viewRosterPopup #editEducatorGrid').data('selectedIds');
	var userSelectedEduId = $('#viewRosterPopup #editEducatorGrid').jqGrid('getGridParam', 'allselectedrows');
	if(selectedEducatorId.length == 0 || userSelectedEduId.length == 0) {
		$('#viewRosterSaveError').html("An active Educator must be selected to save Roster");
		$('#viewRosterSaveError').show();
		return false;
	}
	
	var subjectCode = $('#viewContentAreaSelect option:selected').attr("data-contentAreaCode");
	var courseSelect = $('#viewRosterPopup #viewCourseSelect');
	//DE19007 fix null pointer exception
	//this impacts the Delaware Biology as null will not allow the error message to display and 
	//force the user to put Biology on the roster.
	if (courseSelect.val() == null) {
		courseSelect.val(0);
	}
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
		
		if(selectedViewStudentsArray.length>0){		
			for(var i =0;i<selectedViewStudentsArray.length;i++){
					var gradeLevel = selectedViewStudentsArray[i].gradeLevel;
		 			if(parseInt(gradeLevel) >= lowerGradeBound){
		 				checkGreaterThanEqualToLowerBound = true;
	     		     }
			}	
		}
			
		if(checkGreaterThanEqualToLowerBound && courseSelect.val() == 0){
			$('#course').removeClass('ui-state-disabled disableElement');
			courseSelect.removeAttr("disabled");
			courseSelect.addClass('validateEditCourse');	
			courseSelect.val('0');
			$('#viewRosterSaveError').html('Biology course is a required selection when students listed in grades '+lowerGradeBound+'-12');
			$('#viewRosterSaveError').show();
			$('#viewRosterPopup').animate({
		        scrollTop: 0
		    }, 1500);
			setTimeout(function(){ $("#viewRosterSaveError").hide(); },5000);
			return false;
		}		
		else{
			courseSelect.removeClass('validateEditCourse');	
			courseSelect.removeClass('error');
			$('#viewRosterSaveError').html('');
			$('#viewRosterSaveError').hide();	
		}	
	}else{
		$('#course').addClass('ui-state-disabled disableElement');
		courseSelect.attr("disabled", "disabled");
		courseSelect.removeClass('validateEditCourse');	
		courseSelect.removeClass('error');
		courseSelect.val('0');
		$('#viewRosterSaveError').html('');
		$('#viewRosterSaveError').hide();	
	}
	
	
	
	return true;
}

jQuery.validator.addMethod('validateEditCourse', function(value, element){
	return value != '';
},'Please select course');

var rosterData; 
var arrayTest;
var confirmIndex;
var multipleRostersSize;

function saveRoster() {
	if(editRosterValidate() == false) {
		return;
	}
	var rosterId = $('#viewRosterPopup').data('rosterId');
	var schoolId = $('#viewRosterPopup').data('schoolId');
	var schoolYear = $('#viewRosterPopup').data('schoolYear');
	var name = $('#viewRosterPopup #viewRosterName').val();
	var contentAreaId = $('#viewRosterPopup #viewContentAreaSelect').val();
	var selectedEducatorId = $('#viewRosterPopup #editEducatorGrid').data('selectedIds');
	var selectedStudentIds = $('#viewRosterPopup #editStudentGrid').jqGrid('getGridParam', 'allselectedrows').map(Number);
	//var origStudentIds = $('#editStudentGrid').data('origStudentIds');
	//var addStudentIds = _getAddIds(origStudentIds,selectedStudentIds);
	//var delStudentIds = _getDelIds(origStudentIds,selectedStudentIds);
	
	for(var i=0; i<addStudentIds.length; i++) {
		var addStudentExists = false;  
		var item = Number(addStudentIds[i]);
		/**
		 * Remove id from addStudentIds if exists.
		 */
		
		if($.inArray(item, origStudentIds) >= 0){
			addStudentExists = true;
    	}
		if(addStudentExists){
				addStudentIds = $.grep(addStudentIds, function(value) {
			  return value != item;
			});
		}
	}
	
	arrayTest = addStudentIds;
	var courseId = $('#viewRosterPopup #viewCourseSelect').val();
	//DE19007 fix null pointer exception
	//courseId should be populated with 0 or a course id.
	//The backend will change 0 to null when saving.
	if (courseId == null) {
		courseId = 0;
	}
	rosterData = {
		rosterId: rosterId,
		schoolId: schoolId,
		schoolYear: schoolYear,
		name: name,
		contentAreaId: contentAreaId,
		educatorId: selectedEducatorId[0],
		addStudentIds: addStudentIds.join(','),
		delStudentIds: delStudentIds.join(','),
		courseId: courseId
	};
	
	if(isDLMUser){	
			if((rosterData.addStudentIds != null && rosterData.addStudentIds.length > 0) || (rosterData.delStudentIds!= null && rosterData.delStudentIds.length>0)){
			$.ajax({
				url: 'checkForMultipleRostersToEditRoster.htm',
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
					
					confirmDialogToEditRoster(studentEnrlId, legalFirstName, legalLastName, stateStudentIdentifier, arrayTest);
				
				}	
					
				if(multipleRostersSize == 0){
				 	 if((rosterData.addStudentIds != null && rosterData.addStudentIds.length > 0) || (rosterData.delStudentIds!= null && rosterData.delStudentIds.length>0)){
				 		confirmUpdateRoster(rosterData);
				     }
					 else{
					 	$("#viewRosterSaveError").html("Student Selection Required").show();
						$('#viewRosterSaveError').show();
						$('html, body').animate({
							scrollTop: $("#viewRosterSaveError").offset().top
						}, 1500);
					 }
				}
			}).fail(function(jqXHR, textStatus, errorThrown) {
				$('#viewRosterSaveError').html('Error:' + textStatus);
				$('#viewRosterSaveError').show();	
				$('html, body').animate({
					scrollTop: $("#viewRosterSaveError").offset().top
				}, 1500);
			});
		}
		else{
			confirmUpdateRoster(rosterData);
		}
	}
	else
	{
		confirmUpdateRoster(rosterData);
	}

	
	
}



function confirmUpdateRoster(rosterData){
	var subjectCode = $('#viewContentAreaSelect option:selected').attr("data-contentAreaCode");	
	var courseCode = $('#viewCourseSelect option:selected').attr("data-Course");
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
		
		var removeStudents = [];
		var removeStud = rosterData.delStudentIds.split(',');
		if(removeStud.length>0){
			for(var k=0;k<removeStud.length;k++){
				if(removeStud[k]!='') removeStudents.push(removeStud[k]);
			}
		}
		
		var warningTextMsg = "The following student(s) are listed in a grade where the selected subject </br>"+
		"and course do not apply:</br></br>[ ";
		var warningStudentInfo="";
		
		if(selectedViewStudentsArray.length>0){
			var lowerGradeBound = 10;
			if (contractingOrgDisplayIdentifier == 'DE') {
				lowerGradeBound = 10;
			} else if (contractingOrgDisplayIdentifier == 'DC'){
				lowerGradeBound = 9;
			} 
			
			for(var i =0;i<selectedViewStudentsArray.length;i++){
				var id = selectedViewStudentsArray[i].id;
					var gradeLevel = selectedViewStudentsArray[i].gradeLevel;
					var legalFirstName = selectedViewStudentsArray[i].legalFirstName;
					var legalLastName = selectedViewStudentsArray[i].legalLastName;
					var stateStudentIdentifier = selectedViewStudentsArray[i].stateStudentIdentifier;
					if(parseInt(gradeLevel) < lowerGradeBound){					

						if(selectedStudents.indexOf(id)<0){							
							removeStudents.push(id);
						}	
						
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
		rosterData.delStudentIds = removeStudents.join(',');
		
		if(checkLessThanTenthGrade){
			$('<div style="line-height: 20px;text-align:center;" ></div>').html(warningTextMsg).dialog({
		      height: 300,
		      width: 'auto',
		      modal: true,
		      closeOnEscape: false,
		      buttons: {
		        "Ok": function() {
		        	$(this).dialog("close");
		        	callUpdateRoster(rosterData);		        	
		        },
		      	"Cancel": function() {		      		
		          	$(this).dialog( "close" );
		       	}
		      }
			});			
		}
		else{
			callUpdateRoster(rosterData);
		}
	}else{
		callUpdateRoster(rosterData);
	}
}

function callUpdateRoster(rosterData){
	
	$.ajax({
		url: 'updateRoster.htm',
		data: rosterData,
		dataType: 'json',
		type: "POST"
	}).done(function(data) {
		if(typeof(data) != 'undefined' && data != null)
		{
			if(data.success){
				$('#viewRosterPopup').dialog('close');
					//$("#viewRostersTableId").jqGrid('resetSelection');
				$('#searchviewRosters').trigger('click');
				if(typeof(data.disabledRoster) != 'undefined' && data.disabledRoster != null && data.disabledRoster){
					$('#rosterMessage').html('All students removed. Roster deleted.').show();
					$('#rosterMessage').show();
					setTimeout(function(){ $("#rosterMessage").hide(); },5000);
				}					
			} 
			else if(data.duplicate) {
				$('#viewRosterSaveError').html('Roster name already exists for the selected Educator, Subject and Course');
				$('#viewRosterSaveError').show();
				$('html, body').animate({
					scrollTop: $("#viewRosterSaveError").offset().top
				}, 1500);
				setTimeout(function(){ $("#viewRosterSaveError").hide(); },5000);
			}
			else{
				$('#viewRosterSaveError').html("Error updating roster.");
				$('#viewRosterSaveError').show();
				$('html, body').animate({
					scrollTop: $("#viewRosterSaveError").offset().top
				}, 1500);
			}
		}
		else {
			$('#viewRosterSaveError').html("Error updating roster.");
			$('#viewRosterSaveError').show();
			$('html, body').animate({
				scrollTop: $("#viewRosterSaveError").offset().top
			}, 1500);
		}
	});
}


function confirmDialogToEditRoster(enrollmentid, legalFirstName, legalLastName, stateStudentIdentifier, arrayTest) {
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
                $(this).dialog("widget").remove();
                confirmIndex++;
	            if(confirmIndex == multipleRostersSize){
					 if((rosterData.addStudentIds != null && rosterData.addStudentIds.length > 0) || (rosterData.delStudentIds!= null && rosterData.delStudentIds.length>0)){
						 confirmUpdateRoster(rosterData);
				     }	
					 else{
						$("#viewRosterSaveError").html("Student Selection Required").show();
						$('#viewRosterSaveError').show();
						$('html, body').animate({
							scrollTop: $("#viewRosterSaveError").offset().top
						}, 1500);
					 }
			 	}
			 	$(".ui-dialog-titlebar-close").show();
            }
        }, {
            // The Cancel button
            text: cancelButtonText,
            click: function () {
            	deferred.resolve(false);
            	 $(this).dialog("widget").remove();
                if(arrayTest.indexOf(enrollmentid )>=0)
    				arrayTest.splice(arrayTest.indexOf(enrollmentid ), 1);
    		 	rosterData.addStudentIds = arrayTest.join(',');
    		 	confirmIndex++;
    		 	
	            if(confirmIndex == multipleRostersSize){
					 if((rosterData.addStudentIds != null && rosterData.addStudentIds.length > 0) || (rosterData.delStudentIds!= null && rosterData.delStudentIds.length>0)){
						 confirmUpdateRoster(rosterData);
				     }
					 else{
						$("#editRosterStudentSelectError").html("Student Selection Required").show();
						$('#editRosterStudentSelectError').show();
						/*$('html, body').animate({
							scrollTop: $("#editRosterStudentSelectError").offset().top
						}, 1500);*/
					 }
			 	}
            }
        }],
        close: function (event, ui) {           
        	 $(this).dialog("widget").remove();	  
        }
    });
    
    return arrayTest;
}


