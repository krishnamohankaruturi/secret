var activeStudentName = '';
var orgaName = '';
var searchFlag = false;
var editPermission = false;
if (typeof(editStudentPermission) !== 'undefined'){
	editPermission = true;
	}

$("#findStudentStateId").on("keypress",function(event){
    if(event.keyCode == 13){
    	event.preventDefault();
        $("#findStudentsButton").click();
    }
});

$('#findStudentsFilterForm').validate({
		ignore: "",
		errorPlacement : function(error, element) {
	        $('.errorplacediv').append(error);
	    },
		rules: {
			findStudentStateId: {required: true}
		}
});

$('#findStudentsButton').on("click",function(event) {
	if($('#findStudentsFilterForm').valid()) {
		searchFlag = true;
		var stateStudentId = $('#findStudentStateId').val();
		if(!isEmpty(stateStudentId.trim())){
			$.ajax({
				url: 'findStudentEnrollment.htm',					
				dataType: 'json',
				data:{
					stateStudentId : stateStudentId.trim(),					
				},
				type: "GET"
			}).done(function(response) {
				if(response.Action =='Edit'){
					$('.errorNoStudent').hide();
					var editLink = editPermission;					
					openViewStudentPopupfromFindstudent(response.Student.studentId,response.Student.stateStudentIdentifier,response.Student.legalFirstName,response.Student.legalLastName,response.Student.middleName,editLink,response.Student);
					//$('#findStudentsFilterForm')[0].reset();
				}
				else if(response.Action =='AcessDecline'){
					$('.errorNoStudent').hide();
					declineStudentAcess();						
					//$('#findStudentsFilterForm')[0].reset();
				}else if(response.Action =='Activate'){
					$('.errorNoStudent').hide();
					//console.log(response.Student.stateStudentIdentifier,response.Student.studentId,response.Student.districtId,response.Student.schoolId,response.Student.gradeCode,response.Student.id);
					activeStudentName = response.Student.legalFirstName+" "+response.Student.legalLastName;
					orgaName=response.Student.stateName;						 						
					 var schoolId = ' ';
					 var gradeId = ' ';
					 var enrollmentId = ' ';
					 var districtId = ' ';
					 if(!isEmpty(response.Student.districtId)){
						  districtId =response.Student.districtId; 
					 }
					 if(!isEmpty(response.Student.schoolId)){
						  schoolId = response.Student.schoolId; 
					 }
					 if(!isEmpty(response.Student.id)){
						  enrollmentId = response.Student.id; 
					 }
					 if(!isEmpty(response.Student.gradeId)){
						  gradeId =response.Student.gradeId; 
					 }
					 
					editInActiveStudent(response.Student.stateStudentIdentifier,response.Student.studentId,districtId,schoolId,gradeId,enrollmentId);
					//$('#findStudentsFilterForm')[0].reset();
				}else if(response.Action=='NoRecord'){
					$('.errorNoStudent').html(response.errorMessage).show();
				}else if(response.Action=='Unauthorized'){
					$('.errorNoStudent').html(response.errorMessage).show();
				}
				else if(response.Action=='NotAssociatedAsp'){
					$('.errorNoStudent').html(response.errorMessage).show();
				}else if(response.Action=='NotAssociatedOrg'){
					$('.errorNoStudent').html(response.errorMessage).show();
				}
			});	
		}
	}
});

function isEmpty(data){
	if(data == undefined || data == null || data.length == 0 ){
		return true;
	}
}

function declineStudentAcess(){
	$("#acessDeclineDialog").dialog({
		width: 520,	
		create: function(event, ui){
		    var widget = $(this).dialog("widget");
		},
		      buttons : {
		     "ok" :{
		    	 class:'decilneOkButton',
		    	 text:'ok' ,
		    	
		    	 click:function(){
		       $(this).dialog("close");
		    	 }
		      }
		     }			
		 });
}
function editInActiveStudent(stateStudentId,studentId,districtId,schoolId,gradeId,enrollmentId){
	$("#confirmDialogSelectInActiveStudent").dialog({
		width: 520,
		
		create: function(event, ui){
		    var widget = $(this).dialog("widget");
		},
		      buttons : {
		      	  
		     "Yes" :  {
		    	 class: 'rightYesButton',
		    	 text:'Yes',
		    	 click: function(){
		    	 openActivationStudent(stateStudentId,studentId,districtId,schoolId,gradeId,enrollmentId);
		    	 
		    	
					//$gridAuto[0].clearToolbar();
					
		    	 
		    	 $(this).dialog("close");
		    	 },
		     },
		     "No" :{
		    	 class: 'rightNoButton',
		    	 text:'No',
		    	 click:function(){
		       $(this).dialog("close");
		    	 },
		      }
		     }			
		 });
		$("#confirmDialogSelectInActiveStudent").dialog("open");
		return ;
		
}

function openActivationStudent(stateStudentId,studentId,districtId,schoolId,gradeId,enrollmentId){
	dialogTitle = "Activate Student for Current School Year";
	
	$('#activeStudentDiv').dialog({
		autoOpen: false,
		modal: true,
		width: 1150,
		position: { my: 'top', at: 'top+10' },
		title: dialogTitle,
		create: function(event, ui) { 
		    var widget = $(this).dialog("widget");
		},		
		beforeClose: function() {
			resetAddStudentIdentifierSearch();			
		}
}).load('activeStudent.htm',{"stateStudentId":stateStudentId,
    "enrollmentId":enrollmentId,
    "studentId":studentId,
    "districtId":districtId,"schoolId":schoolId,
    "gradeId":gradeId,
    "orgaName":orgaName}).dialog('open');	
	
	
}
function activateStudentConfirmation(){
	$("#ui-dialog-title-activateStudentConfirmation").addClass('confirmationTitle');
  //  $(".ui-dialog-titlebar-close span").html('X');
 //   $(".ui-dialog-titlebar-close span").removeClass("ui-icon-closethick").removeClass('ui-icon').addClass('btn_close');

	$("#activateStudentConfirmation").dialog({
		width: 580,		
	      buttons : {
	    	  
	     "OK" : {
	    	 class: 'rightYesButton',
	    	 text:'OK',
	    	 click: function() {
	    	 $(this).dialog("close");
	    	 
	    	 $("#activateStudentConfirmation").dialog({
	    		    autoOpen: false,
	    		    modal: true
	    		});
	    	
	     },
	     }
	    /* "Edit Student" : function(){
	    	 var studentId = $("#activateStudentId").val();
	    	 var studentName = $("#ui-dialog-title-activeStudentDiv").html();
	    	 var studentSplit = studentName.split('-',2);
	    	 var editLink = editPermission;
	    	callEditStudent('Edit Student Record - '+studentSplit[1],studentId);
	    	// openViewStudentPopup(studentId,studentSplit[1], editLink);
	    	
	       $(this).dialog("close");
	      }*/
	     }
	 });
	$("#activateStudentConfirmation").dialog("open");
	return ;	
}
function openViewStudentPopupfromFindstudent(studentId,stateStudentIdentifier,legalFirstName,legalLastName,middleName, editLink,student) {
	gridParam = window.localStorage.getItem(studentId);  
	//var studentInfo = JSON.stringify(student);
	var studentInfo = {"studentFirstName":student.legalFirstName,"studentMiddleName":student.middleName,"studentLastName":student.legalLastName,"id":student.id,"stateStudentIdentifier":student.stateStudentIdentifier,"gradeCourseName":student.gradeName,"dateOfBirth":student.dateOfBirthStr,"gender":student.gender};
	if(studentInfo.studentMiddleName!="")
		studentInfo.studentMiddleName=studentInfo.studentMiddleName;
	else
		studentInfo.studentMiddleName = "-";
	window.localStorage.setItem('FC_'+studentId,JSON.stringify(studentInfo));
	window.localStorage.setItem(studentId, JSON.stringify(studentInfo));
	var dialogTitle = "View Student Record - " + legalFirstName + " ";
	if (middleName != null && middleName.length > 0 && middleName != '-'){
		dialogTitle += middleName + " ";
	}
	dialogTitle +=  legalLastName;
	var action = 'view';
	var selectedOrg = $('#userDefaultOrganization').val();
	
	$('#viewStudentDetailsDiv').dialog({
		autoOpen: false,
		modal: true,
		width: 1087,
		height: 700,			
		title: dialogTitle,
		create: function(event, ui) { 
		    var widget = $(this).dialog("widget");
		}
		
	}).load('viewStudentDetails.htm',{"studentId":studentId,
		  "editLink":editLink,
		  "action":action,
		  "selectedOrg[]":selectedOrg}).dialog('open');	
}
	