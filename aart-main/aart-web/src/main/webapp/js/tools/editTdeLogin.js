
function view_EditTDEUserName_tab() {
	
	$('#studentUserNameOrgFilter').orgFilter({
		containerClass: '',
		requiredLevels: [20]
	});

	$('#studentUserNameOrgFilterForm').validate({ignore: ""});
	
	buildViewStudentsUserNameByOrgGrid();
	
	$('#studentUserNameButton').on("click",function(event) {
		if($('#studentUserNameOrgFilterForm').valid()) {
			var $gridAuto = $("#studentUserNameGridTableId");
			$gridAuto[0].clearToolbar();
			$gridAuto.jqGrid('setGridParam',{
				datatype:"json", 
				url : 'getViewStudentInformationRecords.htm?q=1', 
				search: false, 
				postData: { "filters": ""}
			}).trigger("reloadGrid",[{page:1}]);
		}
	});	 
	$('#studentUserNameOrgFilter').orgFilter('option','requiredLevels',[50]);
	filteringOrganizationSet($('#studentUserNameOrgFilterForm'));
}

function buildViewStudentsUserNameByOrgGrid() {
	var $gridAuto = $("#studentUserNameGridTableId");
	//Unload the grid before each request.
	$("#studentUserNameGridTableId").jqGrid('clearGridData');
	$("#studentUserNameGridTableId").jqGrid("GridUnload");
	
	var gridWidthForVS = $gridAuto.parent().width();		
	if(gridWidthForVS < 700) {
		gridWidthForVS = 1038;				
	}
	var cellWidthForVS = gridWidthForVS/5;
	
	cmForStudentRecords = [ {name:'stateStudentIdentifier', formatter: viewStudentLinkFormatter, unformat: viewStudentLinkUnFormatter, width:cellWidthForVS, sorttype : 'int', search : true, hidden: false},
                            {name:'legalFirstName', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false,
								formatter: escapeHtml},
                            {name:'legalMiddleName', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true,
								formatter: escapeHtml},
                            {name:'legalLastName', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false,
								formatter: escapeHtml},
	                        {name:'attendanceSchoolDisplayIdentifiers', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false},
	                        {name:'currentSchoolYears', width:cellWidthForVS, sorttype : 'int', search : true, hidden: false},
							{name:'programName', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true}
	
	
	                      ];
	
	$gridAuto.scb({
		mtype: "POST",
		datatype : "local",
		width: gridWidthForVS,
		colNames : [ 'StudentId', 'First Name', 'Middle Name', 'Last Name', 'School Id', 'Current School Year', 'Assessment Program'
		           ],
	  	colModel :cmForStudentRecords,
		rowNum : 10,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		pager : '#pstudentUserNameGridTableId',
		sortname : 'stateStudentIdentifier',
        sortorder: 'asc',
		loadonce: false,
		viewable: false,
	    beforeRequest: function() {
	    	if(!$('#studentUserNameOrgFilterForm').valid() && $(this).getGridParam('datatype') == 'json'){
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
	        
	        if($('#studentUserNameOrgFilter').orgFilter('value') != null)  {
	        	var orgs = new Array();
	        	orgs.push($('#studentUserNameOrgFilter').orgFilter('value'));
	        	selectedOrg = orgs;
	        	$(this).setGridParam({postData: {'orgChildrenIds': function() {
						return orgs;
					}}});
	        } else if($(this).getGridParam('datatype') == 'json') {
        		return false;
	        }
	    },localReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        }
	    },jsonReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        },
	        repeatitems:false,
	    	root: function(obj) { 
	    		return obj.rows;
	    	} 
	    },loadComplete: function() {	
	          this.grid.hDiv.scrollLeft = this.grid.bDiv.scrollLeft;
	          var tableid=$(this).attr('id'); 			           
	            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');			           
	             $.each(objs, function(index, value) {         
	              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
	                    $(value).attr('title',$(nm).text()+' filter');                          
	                    });
	    }
	});
	$gridAuto[0].clearToolbar();
	$gridAuto.jqGrid('setGridParam',{
		postData: { "filters": ""}
	}).trigger("reloadGrid",[{page:1}]);
	
}

function viewStudentLinkFormatter(cellvalue, options, rowObject) {
	// Save student info in local storage for reuse
	var studentFirstName =  rowObject.legalFirstName; 
	var studentLastName = rowObject.legalLastName; 
	var studentMiddleName = rowObject.legalMiddleName; 
	var studentInfo = new Object();
	studentInfo.studentFirstName = studentFirstName;
	if(studentMiddleName)
		studentInfo.studentMiddleName = studentMiddleName;
	else
		studentInfo.studentMiddleName = "-";
	studentInfo.studentLastName = studentLastName;
	studentInfo.stateStudentIdentifier = rowObject.stateStudentIdentifier;
	
	window.localStorage.setItem(rowObject.id, JSON.stringify(studentInfo));
	var editLink = false;
	if (typeof(editStudentPermission) !== 'undefined'){
		editLink = true;
	}
	return '<a href="javascript:openStudentUsernamePasswordPopup(\'' + rowObject.id  + '\',\''  + escapeHtml(rowObject.stateStudentIdentifier) + '\','  + editLink +');">' + escapeHtml(cellvalue) + '</a>';
	}

//Custom unformatter for AccessProfile link.
function viewStudentLinkUnFormatter(cellvalue, options, rowObject) {
    return;
}

function openStudentUsernamePasswordPopup(studentId,stateStudentIdentifier,editLink){
	gridParam = window.localStorage.getItem(studentId);  
	var studentInfo = JSON.parse(gridParam);
	var dialogTitle = "View Student Record - " + studentInfo.studentFirstName + " ";
	if (studentInfo.studentMiddleName != null && studentInfo.studentMiddleName.length > 0 && studentInfo.studentMiddleName != '-'){
		dialogTitle += studentInfo.studentMiddleName + " ";
	}
	dialogTitle +=  studentInfo.studentLastName;
	var action = 'view';
	
	$('#viewUsernamePwdWindow').dialog({
		autoOpen: false,
		modal: true,
		width: 600,
		height: 400,			
		title: dialogTitle,
		create: function(event, ui) { 
		    var widget = $(this).dialog("widget");
		    $(".ui-dialog-title", widget).css('font-size', '1em');
		},
		open : function(ev, ui) {
			viewStudentUsrnamePswrdDetails(studentId);
		},
		close : function(ev, ui) {
			$('.js-student_fields').val('');
			$('#validateStudentDetails label > input ').removeClass('error');
			$('#validateStudentDetails label > label ').remove();
		}

		
	}).dialog('open');
}

function studentDetailsvaldiation(){
	return {
	  rules: {
			studentUsername: {
		      required  : true,
		      minlength : 4,
		      maxlength : 100
		    },
		    studentPassword:{
		    	required  : true,
		    	minlength : 4,
		    	maxlength : 15
		    },
		    errorClass: "error",
	  },
	  messages: {
			studentUsername: {
		      required: "Username is required",
		     },
		    studentPassword:{
		    	required: "Password required",
		    }
	  }
	};
}


function viewStudentUsrnamePswrdDetails(studentId) {
	$('#updateStudentDetails').attr('data-studentid', studentId);
	$.ajax({
		url:'getStudentData.htm',
		type:'GET',
		data:{studentId:studentId}
	}).done(function(data){
		
		$('#studentUsername').val(data.username);
		$('#studentPassword').val(data.password);

	});
	$('#generatePassword').on("click", function(e){
		e.preventDefault();
		$('#generatePassword').addClass("fa-spin  fa-fw");
		

		$.ajax({
			url:'generateStudentPassword.htm',
			type:'GET'
		}).done(function(data){
			$('#generatePassword').removeClass("fa-spin fa-fw");

			$('#studentPassword').val(data.generatedPassword);
		});
	});
	$('#updateStudentDetails').unbind('click').bind(
			'click',
			function(e) {
				e.preventDefault();
				var valid = $('#validateStudentDetails').validate(
						studentDetailsvaldiation()).form();
				if (valid) {

					$.ajax({
						url : 'changeStudentUsernamePassword.htm',
						data : {
							newUsername : $('#studentUsername').val(),
							newPassword : $('#studentPassword').val(),
							studentId   : studentId 
						},
						dataType : 'json',
						type : 'POST'
					}).done(function(data) {
						if (data.success) {
							$('#editStudentDetailsSucess').text(data.success);
							$('#editStudentDetailsSucess').show();
							setTimeout("$('#editStudentDetailsSucess').hide()", 5000);
						}else {
							$('#editStudentDetailsError').text(data.error);
							$('#editStudentDetailsError').show();
							setTimeout("$('#editStudentDetailsError').hide()", 5000);
						}
					})
					.fail(function(jqXHR, textStatus, errorThrown) {
						console.log(errorThrown);
					});
				}else{
					$('label.error').css({'display':'block','border':'0px','margin-left': '14%','margin-bottom': '3%'});
				}

			});
}

