<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %> --%>

<style>

.addStudentErrorMessagesDiv{
	  position: relative;
}

.errorMessagesAddStudent{
    display: inline;
    position: absolute;
    bottom: -110px;
    background-color: #ffa040;
    width: 35%;
    margin-top: 25%;
    left: 43%;
    padding: 9px;
    line-height: 15px;
}
</style>

   <div id="checkStateStudentIdentifierFormDiv">	
   
   
<div class="sMessages studentARTSmessages addStudentMessages">
	<span style="margin-left: 25px;" class="error_message ui-state-error permissionDeniedMessage hidden" id="permissionDeniedMessageAddStudent" ><fmt:message key="error.permissionDenied"/></span>
	<span style="margin-left: 25px;" class="info_message ui-state-highlight successMessage hidden" id="successMessage" >This student record has successfully saved.</span>
    <span style="margin-left: 25px;" class="error_message ui-state-error selectAllLabels hidden validate" id="failMessage">Failed to add student.</span>
    <span style="margin-left: 25px;" class="error_message ui-state-error selectAllLabels hidden validate" id="requiredMessageAddStudent">Choose all required fields.</span>
	<span style="margin-left: 25px;" class="error_message ui-state-error selectAllLabels hidden duplicate" id="duplicateMessage" >Student already exists with same state student identifier.</span>
	<span style="margin-left: 25px;" class="error_message ui-state-error selectAllLabels hidden duplicate" id="invalidParentOrgMessage"></span>
	<span style="margin-left: 25px;" class="error_message ui-state-error selectAllLabels hidden duplicate" id="ksPermissionDeniedMessage" >Students cannot be added manually for Kansas state.</span>
</div>
   
   
   
    <form id="checkStateStudentIdentifierForm" name="checkStateStudentIdentifierForm" class="form">
	
	    <input type="hidden" id="stateStudentIdentifierToActivate" value="" />
		<input type="hidden" id="enrollmentId" value="" /> 
		<input type="hidden" id="studentIdToActivate" value="" /> 
		<input type="hidden" id="districtId" value="" /> 
		<input type="hidden" id="schoolId" value="" /> 
		<input type="hidden" id="gradeId" value="" /> 

			<div style="float: left;" id="stateStudentIdDiv" class="form-fields">
           		<label for="stateStudentId" style="margin: 30px 0 20px;" class=" field-label isrequired">State Student Identifier:<span class="lbl-required">*</span></label>
            	<input type="text" id="stateStudentId" name="stateStudentId" class="input-large" style="color:#444;" />
           </div>
           
            <div style="float: left;margin-top: 6%;" class="btn-bar"><a class="panel_btn" href="#" id="stateStudentIdentifierAdd">ADD</a></div>
			
			<div class="activateMessages addStudentErrorMessagesDiv">
				<span class="errorMessagesAddStudent hidden" id="addStudentErrorMessages" ></span>				
			</div>
			
			<div style="float: right;margin-top: 6%;display:none;" class="btn-bar" id="stateStudentIdentifierActivateBtnDiv" >
				<a class="panel_btn" href="#" id="stateStudentIdentifierActivate" >Activate</a>
			</div>
     </form> 
    </div>
    <div id ="addStudentsPage" style="float: left;" class="hidden">
		  <jsp:include page="addStudents.jsp"></jsp:include>  
	</div> 
