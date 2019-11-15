<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
#ui-dialog-title-manageOrganizationDiv{
text-align: left;
}
.manageOrgVal{
color:black;
word-wrap: break-word;
}
input[type="radio"]{
  margin: 0 5px 0 5px;
}
</style>



<form:form id="manageOrgForm"  commandName="organization" class="form">
<div style="width: 100%" class = "pagecontent">
<div style="width: 5%; float: left;"> &nbsp;  </div>

<div style="width: 90%; float: left;color: black;" class="divBlockFont">
	
	<div style="float:left;">
		PROFILE
	</div>
	<div style="float:right;">
		Internal ID: ${organization.id}
		<form:input style="display:none;" path="id"   type="text" id="manageOrgId" value= "${organization.id}"/>
		<form:input style="display:none;" path="currentSchoolYear"   type="text" id="manageSchoolYear" value= "${organization.currentSchoolYear}"/>
		<form:input style="display:none;" path="typeCode"   type="text" id="manageOrgtypeCode" value= "${organization.typeCode}"/>
	</div>
<hr style="width: 100%; height: 1px;"/>
<div style="width: 100%; float: left;">
<table style="width: 100%;color:#0e76bc;" role="presentation" >
  <tr>
  		<td style="width: 20%;">
  			<label style="vertical-align: super;">ORGANIZATION NAME:<span class="lbl-required">*</span> </label>
  		</td>
  		<td style="width: 30%;">
  			<input class="manageOrgVal" name="manageOrganizationName" id="manageOrganizationName"  title="Organization Name" type="text" value="${organization.organizationName}" /> 
  		</td>
  		<td style="width: 24%;">
  			<label style="vertical-align: super;">ORGANIZATION ID:<span class="lbl-required">*</span> </label>
  		</td>
  		<td style="width: 26%;"> 
  			<input id="manageDisplayIdentifier" class="manageOrgVal" name="manageDisplayIdentifier" title="Display Identifier" type="text" value="${organization.displayIdentifier}" />   
  		</td>
  </tr>

  <tr>
  		<td style="width: 20%">
  			<label>ORGANIZATION TYPE:</label> 
  		</td>
  		<td style="width: 30%">	
  			<span class="manageOrgVal">  ${organization.organizationType.typeName}</span> 
  		</td>
  		<td style="width: 24%">
  			<label>CONTRACTING ORG:</label>
  		</td>
  		<td style="width: 26%">	
  			<span class="manageOrgVal">
  			<c:choose>
			   <c:when test='${organization.contractingOrganization}'>
			   	   YES
			   </c:when>
			   <c:otherwise>
			      NO
			   </c:otherwise>
			</c:choose> 
			</span> 
  		</td>
  </tr>	
  <tr>&nbsp;  </tr>
   <tr>&nbsp;  </tr>
    <tr>&nbsp;  </tr>
 <tr>
  		<td style="width: 20%;vertical-align: top;">
  			<label>PARENT ORGANIZATION:</label> 
  		</td>
  		<td style="width: 30%;vertical-align: top;">	
  			<span class="manageOrgVal"> ${organization.parentOrganizationName} </span> 
  		</td>
  		<td style="width: 24%;vertical-align: top;">
  			<label>PARENT ORGANIZATION ID:</label>  
  		</td>
  		<td style="width: 26%;vertical-align: top;">	
  			<span class="manageOrgVal"> ${organization.parentOrgDisplayName} </span> 
  		</td>
  </tr>		
 <tr>&nbsp;  </tr>
  <tr>&nbsp;  </tr>
   <tr>&nbsp;  </tr>
  <tr>
  		<td style="width: 20%;vertical-align: top;">
  			<label>PARENT ORG TYPE:</label> 
  		</td>
  		<td style="width: 30%;vertical-align: top;">	
  			<span class="manageOrgVal"> ${organization.parentOrgTypeName} </span>
  		</td>
  		<td style="width: 24%;vertical-align: top;">
     		<label>ORGANIZATION STRUCTURES:</label> 
     	</td>
  		<td style="width: 26%;vertical-align: top;">	
     		<span class="manageOrgVal"> ${organization.organizaionStructure} </span>
    	</td>
  </tr>
</table>
</div>
<div style="width: 5%; float: right;">&nbsp;
</div>

<div>&nbsp;</div><div>&nbsp;</div>

<div style="margin:0px;">PROGRAMS </div>
<hr style="width: 100%; height: 1px;"/>

<div style="width: 100%; float: left;">
<table style="width: 100%;color:#0e76bc;" role="presentation" >
	<tr>
		<td style="width: 50%;"> 
			<label style="vertical-align: super;">ASSESSMENT PROGRAMS:<span class="lbl-required">*</span> </label>
			<form:select path= "assessmentPrograms" id="manageOrgAssessmentProgram" title="Assessment Programs" name="manageOrgAssessmentProgram" class="bcg_select required" multiple="multiple">
						    <c:if test="${not empty allAssessments}">
								<c:forEach items="${allAssessments}" var="assessmentProgram">								
								   <c:if test="${not empty availableAssessments[assessmentProgram.id]}">
								   	<form:option data-abbName="${assessmentProgram.abbreviatedname}" value="${assessmentProgram.id}" selected = 'selected' label="${assessmentProgram.programName}"/>
								   </c:if>
								   <c:if test="${empty availableAssessments[assessmentProgram.id]}">
								   	<form:option data-abbName="${assessmentProgram.abbreviatedname}" value="${assessmentProgram.id}" label="${assessmentProgram.programName}"/>
								   </c:if>		
								</c:forEach>
							</c:if>
			 </form:select>
		</td>
		<td style="display:none;text-align: left;" id="dlmtestModelTd"> 
			<label style="vertical-align: super;">DLM STATE MODEL:<span class="lbl-required">*</span></label>
		
			  <form:select path = "testingModel" id="manageOrgTestModels" title="DLM State Model" name="manageOrgTestModels" class = "required" >
			      <form:option value="" label="Select"/>
						    <c:if test="${not empty testModels}">	
								<c:forEach items="${testModels}" var="testModel">
								   <c:if test="${organization.testingModel == testModel.id}">
								   	<form:option value="${testModel.id}" selected = 'selected' label="${testModel.categoryName}"/>
								   </c:if>
								   <c:if test="${organization.testingModel != testModel.id}">
								   	<form:option value="${testModel.id}" label="${testModel.categoryName}"/>
								   </c:if>	
								</c:forEach>
							</c:if>
			  </form:select> 
		</td>
	</tr>
</table>
</div>

<div style="width: 5%; float: right;">&nbsp;
</div>
<div>&nbsp;</div>

<div style="margin:0px;">STATUS AND DATES</div>
<hr style="width: 100%; height: 1px;"/>

<div style="width: 100%; float: left;">
<table style="width: 100%;color:#0e76bc;" role="presentation" >
	<tr>
		<td style="width: 12%;"> 
			<label style="vertical-align: super;">START DATE: </label> 
		</td>
		<td style="width: 38%;"> 	
			<input type="text" value="${startDateStr}" readonly  title="Start Date" /> 
		</td>
		<td style="width: 14%;"> 
			<label for="searchEndDate" style="vertical-align: super;">END DATE :</label>
		</td>
		<td style="width: 36%;"> 	
			<input id="searchEndDate" type="text" value="${endDateStr}" name="searchEndDate" readonly>
		</td> 
	</tr>
	<tr>
		<td style="width: 12%;"> 
			<label style="vertical-align: super;">REPORT YEAR:<span class="lbl-required">*</span></label>
		</td>
		<td style="width: 38%;"> 	
			<input type="text" id="manageReportYear" class="manageOrgVal manageOrgReportYearNumeric manageOrgReportYearValidation" name="manageReportYear" placeholder="YYYY" oninput="if(value.length>4)value=value.slice(0,4)" size="20" value="${organization.reportYear}" title="Report Year" /> 
		</td>
		<td style="width: 14%;" id="testDaysDiv1"> 
			<label style="vertical-align: super;">TESTING DAYS: </label> 
		</td>
		<td style="width: 36%;" id="testDaysDiv2"> 	
			<INPUT TYPE="radio" name="testDays" value="Mon-Fri" class="testDays" title="TestDays"
				<c:if test="${testDaysStr=='Mon-Fri'}">checked</c:if>
			/>Mon-Fri 
			<INPUT TYPE="radio" name="testDays" value="Mon-Sun" class="testDays" title="TestDays"
				<c:if test="${testDaysStr=='Mon-Sun'}">checked</c:if>
			/>Mon-Sun 
		</td>
   
	</tr>
	<tr id="testTimeSpan">
		<td style="width: 12%;"> 
			<label style="vertical-align: super;">TESTING BEGIN TIME: </label> 
		</td>
		<td style="width: 38%;"> 	
			<input type="text" name="manageTestBeginTime" id="manageTestBeginTime" placeholder="HH:MM AM/PM" value="${testBeginTimeStr}"  title="Testing Begin Time" /> 
		</td>
		<td style="width: 14%;"> 
			<label style="vertical-align: super;">TESTING END TIME: </label> 
		</td>
		<td style="width: 36%;"> 	
			<input type="text" name="manageTestEndTime" id="manageTestEndTime" placeholder="HH:MM AM/PM" value="${testEndTimeStr}" title="Testing End Time" /> 
		</td>
   
	</tr>
	<tr>
		<td style="width: 12%;"> 
			<label>STATUS: </label>  
		</td>
		<td style="width: 38%;"> 	
			<span class="manageOrgVal">
			<c:choose>
			   <c:when test='${organization.activeFlag}'>
			      ACTIVE
			   </c:when>
			   <c:otherwise>
			      IN-ACTIVE
			   </c:otherwise>
			</c:choose>  
			</span>						
		</td>
		
	</tr>
</table>
</div>

<div>&nbsp;</div>

<div>
	<p id="errorMessage" style="font-size:13px; color:red"></p>
</div>

<div class="_bcg">
<button class="btn_blue save" style="margin-right: 33px;" id="manageOrgSave"> Save</button>
<button class="btn_blue" style="margin-right: 33px;" id="manageOrgReset"> Reset</button>
</div>

</div>
</div>

</form:form>
<script type="text/javascript" src="<c:url value='/js/configuration/manageOrganization.js'/>"> </script>