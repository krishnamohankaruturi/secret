<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<style>

 .findstudent_panel_btn {
    background: #0e76bc  no-repeat scroll center center;
    border: 0 none;
    border-radius: 4px;
   /*  color: white; */
    display: inline-block;
    font-size: 1em;
    font-weight: 300;
    
    margin: 0;
    padding: 4px 9px;
    text-decoration: none;
    transition: all 0.3s ease-in-out 0s;
}
.rightYesButton {
margin-left: 240px !important;

}
.decilneOkButton {
float:right !important;
}
.rightNoButton{
/* margin-right: 184px !important; */
}
.errorNoStudent{
  background-color: #f78d58;
    margin: 0 0 8px;
    width: 22%;
}
</style>

<div class="full_side">	
	<h1 class="panel_head sub">FIND STUDENT RECORD</h1>
 
    <form id="findStudentsFilterForm" name="findStudentsFilterForm">
    	<div id="findStudentsFilter"></div>	
    	<div id =findStudentStateDivId class="form-fields" style="width: 1008px;">	
			<label for="findStudentStateId"  class="field-label isrequired">STATE STUDENT IDENTIFIER:<span class="lbl-required">*</span></label>
			<input type="text" id="findStudentStateId" class="input-large" style="color:#444;" maxlength="50" name="findStudentStateId" value=""/>
		 <security:authorize access="hasAnyRole('PERM_STUDENTRECORD_VIEW')">
		 	<a class="panel_btn" style="padding: 7px 20px; margin-left: 37px;" href="#" id="findStudentsButton">Search</a>
		</security:authorize>
		<label class=" error" id="messageFindStudent" style="margin:0px;"></label>
			<span class="errorNoStudent" style="display:none; margin-left: 51px;"></span>
			<div class="errorplacediv" style=" margin-top: -30px !important;width: 133px !important;" ></div>
		</div>		
		
	</form>
   
</div>
<div class="full_main">	
<div id="activateStudentDiv" ></div>
<div id="editStudentDivInFindStudent" ></div> 
</div>

<div id="confirmDialogSelectInActiveStudent" style="width : 510px !important; display:none;margin-top: 20px; " title="">    
       <div style="background: rgba(0, 0, 0, 0) url('./images/exclamatory.png') no-repeat scroll left;height: 21px;">
       	<div style="margin-left:27px;line-height: 20px;"> This student is not yet activated for the current school year. </div>
       </div>     
      <div style="margin-left:27px;margin-bottom: 20px;"><p>Do you wish to activate the student?</p></div>
</div>
<div id="acessDeclineDialog" style="width : 510px !important; display:none;margin-top: 20px;" title="" >
       <div style="background: rgba(0, 0, 0, 0) url('./images/exclamatory.png') no-repeat scroll left;">
       	<div style="margin-left:27px; line-height: 20px;margin-bottom: 20px;"> The State Student Identifier you entered is assigned to a student</div>
</div>
 <div style="margin-left:27px;margin-bottom: 20px;margin-top: -19px;"><p>who is already actively enrolled for the current year outside your organization. Please  contact your state educational agency if you need access to this student.</p></div>
       
</div>
<div id="activeStudentDiv"></div>   
<script type="text/javascript">
	var findStudentLoadOnce = false;
	var editFindStudentPermission=false;
	<security:authorize access="hasRole('PERM_STUDENTRECORD_MODIFY')">
		var editStudentPermission = true;
		editFindStudentPermission=true;
	</security:authorize>
</script>

<script type="text/javascript"
 src="<c:url value='/js/configuration/activateStudent.js'/>"> </script>