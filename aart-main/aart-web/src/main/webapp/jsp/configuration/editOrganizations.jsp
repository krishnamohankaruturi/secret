<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
	#editOrganizationContent input.text {
		width: 96%;
	}
	
	#editOrganizationContent .form-fields {
		vertical-align:top;
		width:320px;
	}
	
	#editOrganizationContent .label_height{
    line-height: 25px;
    }
    .continueButton {
		margin-left: 65px !important;
	}
</style>

<div id="editOrganizationContent" class="_bcg config">
	<label class="hidden error" id="messageEditOrganization"></label>
	<br/>
	<form:form id="editOrganizationForm" commandName="organization" class="form">
		<input id="organizationIdForEdit" type="hidden" value="${organization.id}" name="id"/>
		<input id="orgTypeCode" type="hidden" value="${organization.organizationType.typeCode}" name="orgTypeCode" />
			    
		     <div>			
					<div class="form-fields">
						<label for="orgName" class="field-label">ORGANIZATION NAME:&nbsp;&nbsp;<span class="lbl-required">*</span></label>
						<c:if test="${not organization.contractingOrganization}">
							<form:input type="text" title="Organization Name" class="text required" path="organizationName" maxlength="50" style="border:1px solid #434343 !important;color: #434343 !important;" />
							<form:input type="hidden" id="orgName" name="orgName" path="organizationName"/>
						</c:if>
						<c:if test="${organization.contractingOrganization}">
						    <label class="label_height" >${organization.organizationName}</label>
						</c:if>									
					</div>
					<div class="form-fields">
						<label class="field-label">ORGANIZATION ID: </label><label class="label_height orgLabel wordWrapText" >${organization.displayIdentifier}</label>
					</div>
					<%-- <div class="form-fields">
						<label class="field-label">CONTRACTING ORGANIZATION  </label>						
						 <c:if test="${not organization.contractingOrganization}"><label class="label_height" >No</label></c:if>
						 <c:if test="${organization.contractingOrganization}"><label class="label_height" >Yes</label></c:if>
					</div>	 --%>		
					<div class="form-fields">
					   <label class="field-label">ORGANIZATION TYPE: </label><label class="label_height orgLabel" >${organization.organizationType.typeName}</label>					
				    </div>		
		      </div>
		      
		      	  
		   <div>			
			   <c:if test="${organization.contractingOrganization}">
			   
					<div class="form-fields">
						<label class="field-label">ASSESSMENT PROGRAM(S)</label>						
						<c:if test="${not empty orgAssesprogLists}">
							<div class="label_height" >
								<c:forEach var="s" items="${orgAssesprogLists}">
									<label>${s.assessmentProgram.programName}</label><br>					
								</c:forEach>
							</div>
						</c:if>										
					</div>
					
					<div class="form-fields">
						<label class="field-label">START DATE </label>
						<label class="label_height" ><fmt:formatDate pattern="MM/dd/yyyy" value="${organization.schoolStartDate}" /></label>					
					</div>
					
					<div class="form-fields">
						<label class="field-label">END DATE </label>	
						<label class="label_height" ><fmt:formatDate pattern="MM/dd/yyyy" value="${organization.schoolEndDate}" /></label>						
					</div>	
					
<%--                    <div class="form-fields">
						<label class="field-label">EXPIRE PASSWORDS </label>
						<c:if test="${not organization.expirePasswords}"><label class="label_height" >No</label></c:if>
						<c:if test="${organization.expirePasswords}"><label class="label_height" >Yes</label></c:if>						
					</div> --%>
										
						<%-- <div class="form-fields">					
							<label class="field-label">EXPIRATION DATE </label>
							<c:if test="${organization.expirePasswords}">
								<label class="label_height" >${organization.expirationDateTypeString}</label>
							</c:if>
						</div> --%>
				</c:if>					
		   </div>
		   
		  <%--  <div>			
				<div class="form-fields">
					<label class="field-label">ORGANIZATION TYPE </label><label class="label_height" >${organization.organizationType.typeName}</label>					
				</div>				
		   </div> --%>
		   
		   		   
		   <div>		   
		   <c:if test="${organization.organizationType.typeCode == 'ST'}">		
				<div class="form-fields">
					<label class="field-label">PARENT ORGANIZATION </label><label class="label_height orgLabel" >${organization.parentOrganizationName}</label>					
				</div>	
				
			</c:if>
			<c:if test="${organization.organizationType.typeCode == 'SCH' || organization.organizationType.typeCode == 'RG'
			|| organization.organizationType.typeCode == 'AR' || organization.organizationType.typeCode == 'DT'	|| organization.organizationType.typeCode == 'BLDG' }">					
				<div class="form-fields">
					<label class="field-label">STATE: </label><label class="label_height orgLabel" >${stateName}</label>				
				</div>
				
			  	<c:if test="${organization.organizationType.typeCode == 'SCH' || organization.organizationType.typeCode == 'BLDG' }">
					<div class="form-fields">
						<label class="field-label">DISTRICT: </label><label class="label_height orgLabel" >${districtName}</label>				
					</div>
				</c:if>
				
				<c:if test="${not empty regionName && (organization.organizationType.typeCode == 'DT' || organization.organizationType.typeCode == 'SCH' || organization.organizationType.typeCode == 'BLDG' || organization.organizationType.typeCode == 'AR')}">
			
					<div class="form-fields">
					   <label class="field-label">REGION: </label><label class="label_height orgLabel" >${regionName}</label>				
				    </div>
				</c:if>	
			</c:if>	   				
		   </div>
		   <c:if test="${not organization.contractingOrganization && organization.organizationType.typeCode == 'SCH'}">
			<div id="testTimeSpan">
				<div class="form-fields" > 
					<label class="field-label">TESTING BEGIN TIME: </label> 
					<input type="text" name="editTestBeginTime" id="editTestBeginTime" placeholder="HH:MM AM/PM" value="${testBeginTimeStr}"  title="Testing Begin Time" /> 
				</div>
				<div class="form-fields"> 
					<label class="field-label">TESTING END TIME: </label>
					<input type="text" name="editTestEndTime" id="editTestEndTime" placeholder="HH:MM AM/PM" value="${testEndTimeStr}" title="Testing End Time" /> 
				</div>
   				<div class="form-fields"> 
					<label class="field-label">TESTING DAYS: </label>
					<INPUT TYPE="radio" name="editTestDays" value="Mon-Fri" class="editTestDays" title="TestDays"
						<c:if test="${testDaysStr=='Mon-Fri'}">checked</c:if>/>Mon-Fri 
					<INPUT TYPE="radio" name="editTestDays" value="Mon-Sun" class="editTestDays" title="TestDays"
						<c:if test="${testDaysStr=='Mon-Sun'}">checked</c:if>/>Mon-Sun 
				</div>
			</div>
			</c:if>
		   	<br/>
		   	<div>
				<p id="errorMessage" style="margin-left: 24px; font-size:14px; color:red"></p>
			</div>
			<div>
				<button type="button" id="editOrgSave" class="btn_blue save" style="width: 90px; margin-left: 24px;float:left;"> <fmt:message key="button.save"/> </button>
				<button type="button" class="btn_blue" id="EditOrgReset" style="width: 90px; margin-left: 24px;" >Reset</button>		
			</div>
		   
		</form:form>
</div>


<div id="confirmDialogEditOrg" style="width : 510px !important;" title="">
       <div style="margin-left:27px;"><p>Are you sure you want to make these changes?</p></div>
</div>

<script type="text/javascript" src="<c:url value='/js/configuration/editOrganization.js'/>"> </script>
