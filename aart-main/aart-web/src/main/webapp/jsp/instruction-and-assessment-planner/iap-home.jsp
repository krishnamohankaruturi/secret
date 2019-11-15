<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<script src="<c:url value='/js/instruction-and-assessment-planner/iap-home.js'/>"></script>
<link rel="stylesheet" href="<c:url value='/css/iap_home.css'/>" type="text/css" />
<c:set var="isTeacher" value="${user.isTeacher}"/>
<c:if test="${not empty error}">
<p> ${error}
</c:if>
<c:if test="${empty error}">
<div>
	<form class="form" id = "iap-studentsearch">
		<security:authorize access="hasRole('PERM_TESTSESSION_VIEW')">
			<c:if test="${testingCycleName = 'SPRING' }">
				Note: To access spring science assessments, please go to <a id="testManagement" class="panel_btn" href="viewTestSessions.htm">Test Management</a>
			</c:if>
		</security:authorize>

		<c:if test="${user.accessLevel < 70}">
			<div class="row">
				<div class="col-md-8">
				<div class="row">
					<c:if test="${user.accessLevel < 50}">
						<div class="form-fields">
							<label class="field-label" for="districtSelect">District: <span class="lbl-required">*</span></label>
							<select id="districtSelect" name="districtSelect" class="dropdown">
							<option  disabled selected value></option>
							<c:forEach var="item" items="${DT}">
						        <option value="${item.id}" >${item.organizationName}</option>
						    </c:forEach>
							</select>
							<div class="error" id = "districtError">The field is required</div>
						</div>
					</c:if>
					<div class="form-fields">
						<label class="field-label" for="schoolSelect">School: <span class="lbl-required">*</span></label>
						<select id="schoolSelect" name="schoolSelect" class="dropdown" disabled>
						<option  disabled selected value></option>
						<c:forEach var="item" items="${SCH}">
						        <option value="${item.id}" >${item.organizationName}</option>
						    </c:forEach>
						</select>
						<div class="error" id="schoolError">The field is required</div>
					</div>
					</div>
				</div>
				<c:if test="${!isTeacher}">
				<div class= "col cell_text_middle bold_text" >OR</div>
				<div class="col">
					<div class="form-fields">
						<label class="field-label" for="ssid">State Student Id:</label>
						<input class = "input_field_height30" id="ssid" name="ssid" type="text"/>
						<div class="error" id ="ssiError">The field is required</div>
					</div>
				</div>
				</c:if>
			</div>
		</c:if>
		<div class="row">
			<div class="col-md-9 style = "padding-right: 5px;">
				<div class="form-fields">
					<label class="field-label" for="gradeSelect">Grade:</label>
					<select id="gradeSelect" name="gradeSelect" class="dropdown" disabled>
						</select>
						<div>
							<input type="checkbox" class="iap_home_search_selectAll" id="gradeSelect_selectAll" title="Select All Grades" disabled="disabled"/>Select All
						</div>
				</div>
				<div class="form-fields">
					<label class="field-label" for="studentSelect">Student Name:</label>
					<select id="studentSelect" name="studentSelect" class="dropdown" disabled></select>
					<div>
						<input type="checkbox" class="iap_home_search_selectAll" id="studentSelect_selectAll" title="Select All Students" disabled="disabled"/>Select All
					</div>
				</div>
				<c:if test="${!isTeacher}">
				<div class="form-fields">
					<label class="field-label" for="teacherSelect">Teacher Name:</label>
					<select id="teacherSelect" name="teacherSelect" class="dropdown" disabled></select>
					<div>
						<input type="checkbox" class="iap_home_search_selectAll" id="teacherSelect_selectAll" title="Select All Teachers" disabled="disabled"/>Select All
					</div>
				</div></c:if>
			</div>
			<c:if test="${!isTeacher}">
			<c:if test="${user.accessLevel > 60}">
				<div class = "cell_text_middle bold_text">OR</div> 
				<div class="col-md-2 ">
					<div class="form-fields stateStudentIdentifier">
								<label class="field-label" for="ssid">State Student Id:</label>
								<input class = "input_field_height31 stateStudentIdentifier" id="ssid" name="ssid" type="text"/>
								<div class="error" id ="ssiError">The field is required</div>
							</div>
				</div>
			</c:if>
			</c:if>
			<c:if test="${user.accessLevel < 70  || isTeacher}">
			<div class="col-md-2 cell_text_middle">
				<div class="form-fields">
					<a id="search" class="panel_btn" href="#">Search</a>
				</div>
			</div>
			</c:if>
			</div>
			<c:if test="${!isTeacher}">
			<c:if test="${user.accessLevel > 60}">
			<div class="row">
				<div class="col-md-9" style = "padding-right: 5px;"></div>
				<div class = "col"></div>
				<div class="col-md-2 cell_text_middle">
					<div class="form-fields">
						<a id="search" class="panel_btn" href="#">Search</a>
					</div>
				</div>
			</div>
			</c:if>
			</c:if>
	</form>
	
	<br>
	<div id=resultTable></div>

	<div id="viewStudentDetailsDiv" style="display:none;"></div>
	<div id="accessProfileDiv" style="display:none;"></div>
	<div id="firstContactViewDiv" style="display:none;"></div>

</div>
</c:if>
	<script type="text/javascript" src="<c:url value='/js/instruction-and-assessment-planner/iap-common.js'/>"></script>
	<script type="text/javascript" src="/AART/js/external/jquery.jqpagination.js"></script>
	<script type="text/javascript">
	
	var isTeacher = ${isTeacher};
	var studentInfo = {};
	
	function setupPagination(maxPage,currentPage){
		
		$('.iap_pagination').jqPagination({
			max_page :maxPage,
		    paged: function(page) {
		        // do something with the page variable
		        console.log(page);
		        searchStudentWithOffset(page-1);
		    }
		});
		$('.iap_pagination').jqPagination('option', 'current_page', currentPage);
	}
	
	
	
	
	function initilizePopoverFunction(){
		$('.iap_home_credentialsImage[data-toggle="popover"]').popover({
			trigger: 'manual',
			placement: 'bottom',
			html: true,
			title: 'Credentials <button type="button" class="close" aria-hidden="true">&times;</button>',
			content: function(){
				var html = '<div>Username:&nbsp;&nbsp;' + $(this).data('username') + '</div>';
				html += '<div>Password:&nbsp;&nbsp;' + $(this).data('password') + '</div>';
				return html;
			}
		}).on('click', function(){
			$(this).popover('show');
		});
		
		$('[data-toggle="popover"]').on('shown.bs.popover', function(){
			var $this = $(this);
			var $popoverElement = $($this.data('bs.popover').tip);
			$('button.close', $popoverElement).off('click').on('click', function(){
				$this.popover('hide');
			});
		});
	};
	
	
	function initilizeDataFromSession(){
		//If the page is getting loaded from the iap student page back link.
		var iapDistrict = getFromSessionStorageIAP("iapDistrict");
		var iapSchool = getFromSessionStorageIAP("iapSchool");
		var iapSSID = getFromSessionStorageIAP("iapSSID");
		var iapGrades = getFromSessionStorageIAP("iapGrades");
		var iapStudents = getFromSessionStorageIAP("iapStudents");
		var iapTeachers = getFromSessionStorageIAP("iapTeachers");
		var offset = getFromSessionStorageIAP("iapSearchOffset");
		<c:if test="${!isTeacher}">
			$('#ssid').val(iapSSID);
			</c:if>
		<c:if test="${user.accessLevel < 50}">
			$('#districtSelect').val(iapDistrict).trigger('change');
			</c:if>
		<c:if test="${user.accessLevel < 60}">
			$('#schoolSelect').val(iapSchool);
			</c:if>
		$('#schoolSelect').trigger('change');
		$('#gradeSelect').val(iapGrades.split(',')).trigger('change');
		$('#studentSelect').val(iapStudents.split(',')).trigger('change');
		$('#teacherSelect').val(iapTeachers.split(',')).trigger('change');
		//clearing the session data after updating
		clearIAPSearchFilterValuesFromSession();
		if(offset==null){
			offset=0;
		}
		searchStudentWithOffset(offset);
	}
	
	$( document ).ready(function() {
	    console.log( "ready!" );
	    var user = '${user}';
	    $('[data-toggle="popover"]').popover();
	    $('#gradeSelect').val(null).trigger('change');
	    <c:if test="${user.accessLevel < 60}">
	    	$('#schoolSelect').val(null).trigger("change");
	    	$('#schoolSelect').prop('disabled', false);
	    </c:if>
	    
	    <c:if test="${user.accessLevel > 60}">
	    	updateGradeDropDown($('#hiddenCurrentOrganizationId').val());
    		$('#gradeSelect').prop('disabled', false);
    		enableSelectAll('gradeSelect');
    		</c:if>
    		
   		<c:if test="${user.accessLevel < 50}">
   			$("#districtSelect").val(null).trigger("change"); 
		</c:if>
		if(isTeacher){
    		searchStudentWithOffset(0);
    	}
    	$('#districtError').hide();
    	$('#schoolError').hide();
    	$('#ssiError').hide();
    	if(getFromSessionStorageIAP("reloadIAPHomePage")!=null && getFromSessionStorageIAP("reloadIAPHomePage")){
    		initilizeDataFromSession();
    	}
    	
	    //console.log(user);
	});
	
	function validateSearchFilter(schoolOrgId,ssid){
		var okTogo = true;
		var accessLevel = ${user.accessLevel};
		<c:if test="${user.accessLevel < 60 }">
			//only for district or state level user
			var districtOrgId = $('#districtSelect').val();
			if(ssid === "" ){
				<c:if test="${user.accessLevel <30 }">
				if(districtOrgId == null || districtOrgId == ""){
					okTogo=false;
					$('#districtError').show();
				}</c:if>
				if(schoolOrgId == null || schoolOrgId == ""){
					okTogo=false;
					$('#schoolError').show();
				}
				if(okTogo==false){
					$('#ssiError').show();
				}
			}
		</c:if>
		if(okTogo == true){
			$('#districtError').hide();
	    	$('#schoolError').hide();
	    	$('#ssiError').hide();
		}
		return okTogo;
	}
	
	//Search student results, by default offset will be 0
	function searchStudentWithOffset(offset){
		$('#resultTable').html('');
		var ssid = $('#ssid').val();
		var schoolOrgId = $('#schoolSelect').val();
		<c:if test="${user.accessLevel > 50 }">
		schoolOrgId = $('#hiddenCurrentOrganizationId').val();
			</c:if>
		var selectedGrades = $('#gradeSelect').val();
		var selectedStudentIDs = $('#studentSelect').val();
		var selectedTeacherIDs = $('#teacherSelect').val();
		if(validateSearchFilter(schoolOrgId, ssid)){
			$.ajax({
				url: 'instruction-planner-search.htm',					
				//dataType: 'json',
				data:{
					schoolID: schoolOrgId,
					stateStudentID : ssid,
					grades : selectedGrades,
					studentIDs : selectedStudentIDs,
					teacherIDs : selectedTeacherIDs,
					offSet : offset
				},
				type: "GET"
			}).done(function(response) {
				var tableDiv = document.getElementById("resultTable");
				tableDiv.innerHTML=response;
				initilizePopoverFunction();
				//Saving offset value in the local session
				offset= $('#offSet').val();
				setInSessionStorage("iapSearchOffset", offset);
				setupPagination($('#totalCount').val(),parseInt($('#offSet').val())+1);
			});
		}
	}
	
	//Search functionality
	$( "#search" ).on( "click", function() {
		searchStudentWithOffset(0);
	});
	

	
	//District dropdown Changed/Selected
	$("#districtSelect").on('change',function(){
		var districtOrgId = $('#districtSelect').val();
		$("#schoolSelect").html("");
		var orgType = 'SCH';
		var orgLevel = 70;
		if(districtOrgId >0){
			$.ajax({
				url: 'getChildOrgsWithParentForFilter.htm',
				dataType: 'json',
				data: { 
					orgId : districtOrgId,
		        	orgType:orgType
				},
				async:false,
				type: "POST"
			}).done(function(schoolOrgs) {
				$("#schoolSelect").html("");
				$('#schoolSelect').append($('<option></option>').val('').html('Select'));	
				$.each(schoolOrgs, function(i, schoolOrg) {
					$('#schoolSelect').append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
				});
				$('#schoolSelect').trigger('change.select2');
				$('#schoolSelect').prop('disabled', false);
			});
		}else{
			$('#schoolSelect').trigger('change');
		}
	});
	
	
	function updateGradeDropDown(schoolOrgId){
		$.ajax({
			url: 'getGradesBySchoolID.htm',
			dataType: 'json',
			data: { 
				schoolID : schoolOrgId
			},
			async:false,
			type: "POST"
		}).done(function(result) {
			var gradesList = result.grades;
			$("#gradeSelect").html("");	
			$.each(gradesList, function(i, curGrade) {
				$('#gradeSelect').append($('<option></option>').attr("value", curGrade.id).text(curGrade.name));
			});
			$('#gradeSelect').trigger('change.select2');
			$('#gradeSelect').prop('disabled', false);
			enableSelectAll('gradeSelect');
		});
	};
	
	//School dropdown Changed/Selected
	$("#schoolSelect").on('change',function(){
		$('#gradeSelect').val(null).trigger('change');
		var schoolOrgId = $('#schoolSelect').val();
		<c:if test="${user.accessLevel > 50 }">
			schoolOrgId = $('#hiddenCurrentOrganizationId').val();
			</c:if>
		if(schoolOrgId>0){
			updateGradeDropDown(schoolOrgId);
		}else{
			$('#gradeSelect').prop('disabled', true);
			disableSelectAll('gradeSelect');
		}
	});
	
	//Grade dropdown Changed/Selected
	$("#gradeSelect").on('change',function(){
		var grades = $('#gradeSelect').val();
		var schoolOrgId = $('#schoolSelect').val();
		
		<c:if test="${user.accessLevel > 50 }">
		schoolOrgId = $('#hiddenCurrentOrganizationId').val();
			</c:if>
		
		$("#studentSelect").html("");
		$("#teacherSelect").html("");
		if(schoolOrgId >0 && grades !=null && grades!=undefined && grades.length>0){
			$.ajax({
				url: 'getStudentBySchoolIDandGradeIDandSchoolyear.htm',
				dataType: 'json',
				data: { 
					schoolID : schoolOrgId,
					grades: grades
				},
				async:false,
				type: "POST"
			}).done(function(result) {
				var studentList = result.studentList;
				$("#studentSelect").html("");
				//$('#studentSelect').append($('<option></option>').val('').html('Select'));	
				$.each(studentList, function(i, curStudent) {
					$('#studentSelect').append($('<option></option>').attr("value", curStudent.id).text(curStudent.legalLastName+', '+curStudent.legalFirstName ));
				});
				$('#studentSelect').trigger('change.select2');
				$('#studentSelect').prop('disabled', false);
				enableSelectAll('studentSelect');
			});
		}else{
			$('#studentSelect').prop('disabled', true);
			disableSelectAll('studentSelect');
			$('#studentSelect').trigger('change');
		}
	});
	
	//Student dropdown Changed/Selected	
	$("#studentSelect").on('change',function(){
		if(!isTeacher){
			var grades = $('#gradeSelect').val();
			var schoolOrgId = $('#schoolSelect').val();
			<c:if test="${user.accessLevel > 50 }">
				schoolOrgId = $('#hiddenCurrentOrganizationId').val();
			</c:if>
			var students = $('#studentSelect').val();
			
			$("#teacherSelect").html("");
			$('#teacherSelect').prop('disabled', true);
			disableSelectAll('teacherSelect');
			if(schoolOrgId >0 && grades !=null && grades!=undefined && students!=null && students!=undefined && students.length>0){
				$.ajax({
					url: 'getTeacherByStudentIDandSchoolIDandGradeID.htm',
					dataType: 'json',
					data: { 
						schoolID : schoolOrgId,
						grades: grades,
						studentIDs : students
					},
					async:false,
					type: "POST"
				}).done(function(result) {
					var teacherList = result.teacherList;
					$("#teacherSelect").html("");	
					$.each(teacherList, function(i, curTeacher) {
						$('#teacherSelect').append($('<option></option>').attr("value", curTeacher.educatorId).text(curTeacher.educatorLastName+', '+curTeacher.educatorFirstName ));
					});
					$('#teacherSelect').trigger('change.select2');
					$('#teacherSelect').prop('disabled', false);
					enableSelectAll('teacherSelect');
				});
			}
		}
	});
	
	function clearIAPSearchFilterValuesFromSession() {
		
		removeInSessionStorage("iapDistrict");
		removeInSessionStorage("iapSchool");
		removeInSessionStorage("iapSSID");
		removeInSessionStorage("iapGrades");
		removeInSessionStorage("iapStudents");
		removeInSessionStorage("iapTeachers");
		removeInSessionStorage("reloadIAPHomePage");
		removeInSessionStorage("iapSearchOffset");
	}
	
	function setIAPSearchFilterValuesToSession() {
			
		setInSessionStorage("iapDistrict", $('#districtSelect').val());
		setInSessionStorage("iapSchool", $('#schoolSelect').val());
		<c:if test="${user.accessLevel > 50 }">
			setInSessionStorage("iapSchool", $('#hiddenCurrentOrganizationId').val());
			</c:if>
		setInSessionStorage("iapSSID", $('#ssid').val());
		setInSessionStorage("iapGrades", $('#gradeSelect').val());
		setInSessionStorage("iapStudents", $('#studentSelect').val());
		setInSessionStorage("iapTeachers", $('#teacherSelect').val());
	}		
	
	function setInSessionStorage(storageItemName, storageItemValue) {
		window.sessionStorage.setItem(storageItemName, storageItemValue);			
	}

	function removeInSessionStorage(storageItemName) {
	    window.sessionStorage.removeItem(storageItemName);
	}
	
	function getFromSessionStorageIAP(storageItemName) {
		var itemValue = window.sessionStorage.getItem(storageItemName);
		if(typeof itemValue != 'undefined' && itemValue != null) {
			return itemValue;
		}
		
		return null;
	}
	
	function viewCreateInstruction(studentID,rosterID, enrollmentRosterID) {
		console.log("viewCreateInstruction");
		console.log(rosterID);
		setIAPSearchFilterValuesToSession();
		window.location.href = "instruction-planner-student.htm?studentID=" + studentID + "&rosterID="+rosterID + "&enrollmentRosterID="+enrollmentRosterID;
	}
	
	$('.iap_home_search_selectAll').on("click", function(ev){	
		var checkboxId= this.id;  
		checkboxId = checkboxId.replace("_selectAll","");
		
		
		if($('#'+checkboxId+'> option').length==0){
			$(this).attr("checked", false);
			$(this).trigger('change');
		}
		if($(this).is(':checked')){
			
		   $("#"+checkboxId).find('option').prop("selected",true);
	        $("#"+checkboxId).trigger('change');
			
		   }else{
		      $("#"+checkboxId).find('option').prop("selected",false);
	        $("#"+checkboxId).trigger('change');

		   }
		   
	});
	
	function disableSelectAll(prefix_id) {
		var checkboxId= prefix_id + "_selectAll";
		if(document.getElementById(checkboxId)!=null){
			document.getElementById(checkboxId).setAttribute('disabled', 'disabled');
	    	document.getElementById(checkboxId).checked = false;
		}
	}

	function enableSelectAll(prefix_id) {
		var checkboxId= prefix_id + "_selectAll";
		if(document.getElementById(checkboxId)!=null)
	   		document.getElementById(checkboxId).removeAttribute('disabled');
	}
	
	</script>
