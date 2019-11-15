<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<!-- Try to use two div's to represent data in columns like one column for labels and another for textboxes/select or any other controls  -->
<!-- This page needs to be changed to incorporate the above change and need to have some cleanup whenever we have sometime. -->
<style>
.input-large{
width :250px !important;
}
.select2-container--open{
position: none !important;
}
input[type="radio"] {
  margin-top: -1px;
  vertical-align: middle;
  margin: 0 10px 0 10px;
}
</style>
<link rel="stylesheet" href="<c:url value='/css/theme/reports.css'/>" type="text/css" /> 	
<div id="createOrganization" style="margin-top:20px;" >
    <form id="createOrganizationForm" name="createOrganizationForm" class="form">
    
    
    	<div style="margin: 0px 20px 0px 33px;" id="profileHeaderDiv" >
    		<span class="viewOrgHeaderTitle" >PROFILE</span>
    	</div>    
    		<div style="margin: 0px 0px 0px 20px;" >
    				<div id="orgNameDiv" class="hidden form-fields">
		            <label for="organizationName_create" class="isrequired field-label">Organization Name:<span class="lbl-required">*</span></label>
		            <input type="text" id="organizationName_create" name="organizationName" class="input-large" maxlength="50" size ="31"/>
		        </div>
				<div id="orgDisplayIdDiv" class="hidden form-fields">
		        	<label for="organizationDisplayId" class="isrequired field-label">Organization Display Id:<span class="lbl-required">*</span></label>
		           	<input type="text" id="organizationDisplayId" name="organizationDisplayId" class="input-large" maxlength="50" size ="31"/>
		       	</div>
			   	<div id="contractingOrgFlagDiv" class="hidden form-fields">
					<label for="contractingOrgFlag" class="isrequired field-label">Contracting Organization:<span class="lbl-required">*</span></label>
					<select id="contractingOrgFlag" title="Contracting Organization" class="bcg_select required" name="contractingOrgFlag">
						<option value="">Select</option>
				 		<option value="yes">Yes</option>
						<option value="no">No</option>
					</select>			
				</div>
				
				<div id="orgTypeDiv" class="hidden form-fields">
				<label for="orgType" class="isrequired field-label">Organization Type:<span class="lbl-required">*</span></label>				
				<select id="orgType"  title="Organization Type" name="orgType" class="bcg_select">
						<option value="">Select</option>
				 			<c:if test="${fn:length(organizationTypes) > 0 }">						
								<c:forEach items="${organizationTypes}" var="organizationType" varStatus="index">
									<option value="${organizationType.typeCode}" data-level="${organizationType.typeLevel}">${organizationType.typeName}</option>
								</c:forEach>					
							</c:if>	  
					</select>
				</div>
				
				<div id="orgStructureDiv" class="hidden form-fields">
					<label for="orgStructure" class="isrequired field-label">Organization Structures:<span class="lbl-required">*</span></label>
					<select id="orgStructureSelect" title="Organization Structures" name="orgStructure" class="bcg_select" multiple="multiple">
			 			<c:if test="${fn:length(organizationTypes) > 0 }">						
							<c:forEach items="${organizationTypes }" var="organizationType" varStatus="index">	
								<option value="${organizationType.typeCode}" data-level="${organizationType.typeLevel}">${organizationType.typeName}</option>
							</c:forEach>					
						</c:if>	 
					</select>
			  	</div>
			  	
			  	<div id="buldinguniqueDiv" class="hidden form-fields">
		   			<label for="buldingunique" class="isrequired field-label">Building Uniqueness:<span class="lbl-required">*</span></label>   			
					<select id="buldingunique" title="Building Uniqueness" name="buldingunique" class="bcg_select">
						<option value="">Select</option>
					</select>
		       	</div>
		       	
		       	<div id="parentOrgDiv" class="hidden form-fields">
		       		<label for="parentOrg" class="isrequired field-label">Parent Organization:<span class="lbl-required">*</span></label>      		
					<select id="parentOrg" title="Parent Organization" name="parentOrg" class="bcg_select">
						<option value="">Select</option>
				 		<c:if test="${fn:length(organizationsConsortia) > 0 }">						
							<c:forEach items="${organizationsConsortia}" var="org" varStatus="index">
								<option value="${org.id}">${org.organizationName}</option>
							</c:forEach>					
						</c:if>	  
					</select>			
		       	</div>
       	
       			<div id="orgHierarchyDiv" class="hidden">
					<div id="createOrgStateFilter"></div>
				</div>
				
       		</div>
       	
       	<div id="programHeaderDiv" class="hidden" style="height:30px;margin: 35px 20px 0px 33px;border-bottom: 1px solid #aaa;" >
       		<span class="viewOrgHeaderTitle" >PROGRAMS</span>
       	</div>
       		<div style="margin: 0px 0px 0px 20px;" >
			  	
			  	<div id="assessmentProgramsDiv"  class="hidden form-fields">
					<label for="assessmentPrograms" class="isrequired field-label">Select Assessment Program:<span class="lbl-required">*</span></label>
					<select id="assessmentProgramSelect" name="assessmentPrograms" title="Select Assessment Program" class="bcg_select" multiple="multiple">			 			
			 			<c:if test="${fn:length(assessmentPrograms) > 0 }">						
							<c:forEach items="${assessmentPrograms }" var="assessmentProgram" varStatus="index">
								<option data-abbName="${assessmentProgram.abbreviatedname}" value="${assessmentProgram.id }">${assessmentProgram.programName}</option>
							</c:forEach>					
						</c:if>	 
					</select>
				</div>
				
				<div id="testingModelDiv"  class="hidden form-fields">
					<label for="testingModel" class="isrequired field-label">Dlm State Model:<span class="lbl-required">*</span></label>
					<select id="testingModelSelect" title="Dlm State Model" name="testingModel" class="bcg_select" >
			 			<option value="">Select</option>
				 			<c:if test="${fn:length(testingModels) > 0 }">						
								<c:forEach items="${testingModels}" var="testingModel" varStatus="index">
									<option value="${testingModel.id}">${testingModel.categoryName}</option>
								</c:forEach>					
							</c:if>	  		
					</select>
				</div>
			</div>
			
		<div id="statusSchoolYearHeaderDiv" class="hidden" style="height:30px;margin: 35px 20px 0px 33px;border-bottom: 1px solid #aaa;" >
       		<span class="viewOrgHeaderTitle" >STATUS AND SCHOOL YEAR</span>
       	</div>
       					
		<div id="statusDateHeaderDiv" class="hidden" style="height:30px;margin: 35px 20px 0px 33px;border-bottom: 1px solid #aaa;" >
       		<span class="viewOrgHeaderTitle" >STATUS AND DATES</span>
       	</div>
       			
       		<div style="margin: 0px 0px 0px 20px;" >
       			
				<div class="hidden form-fields dateDiv"> 
					<label for="startDate" class="isrequired field-label">Start Date:<span class="lbl-required">*</span></label>
		           	<input id="startDate" name="startDate" class="input-large" placeholder="MM/DD/YYYY"  />
				</div>
				<div class="hidden form-fields dateDiv"> 
		            <label for="endDate" class="isrequired field-label">End Date:<span class="lbl-required">*</span></label>
		           	<input id="endDate" name="endDate" class="input-large" placeholder="MM/DD/YYYY" /><br />
				</div>
				
				<div id="reportYearDiv" class="hidden form-fields" style="display:none" > 
		            <label for="reportYear" class="isrequired field-label">Report Year:<span class="lbl-required">*</span></label>
		           	<input type="text" id="reportYear" name="reportYear" class="input-large " placeholder="YYYY" oninput="if(value.length>4)value=value.slice(0,4)" /><br />
				</div>
       			
				<div class="hidden form-fields timeDiv" id="beginTimeDiv"> 
					<label for="testBeginTime" class="isrequired field-label">Testing Begin Time:</label>
		           	<input id="createTestBeginTime" name="testBeginTime" class="input-large" placeholder="HH:MM AM/PM" />
				</div>
				<div class="hidden form-fields timeDiv" id="endTimeDiv"> 
		            <label for="testEndTime" class="isrequired field-label">Testing End Time:</label>
		           	<input id="createTestEndTime" name="testEndTime" class="input-large" placeholder="HH:MM AM/PM" /><br />
				</div>
				
				<div class="hidden form-fields timeDiv" id="testDaysDiv"> 
		            <label for="testDays" class="isrequired field-label">Testing Days:</label>
		           	<INPUT TYPE="radio" name="testDays" value="Mon-Fri" checked="checked" title="TestDays"/>Mon-Fri 
		           	<INPUT TYPE="radio" name="testDays" value="Mon-Sun" title="TestDays" />Mon-Sun
				</div>
				
		        <div id="createOrgSaveBtnDiv" class="hidden btn-bar"><a class="panel_btn" href="#" id="createOrgSave">Save</a><button type="reset" class="panel_btn" id="createOrgReset">Reset</button></div>
		      
		      </div>
	</form>
</div>
<script type="text/javascript">
	var gCreateOrganizationLoadOnce = false;
</script>		