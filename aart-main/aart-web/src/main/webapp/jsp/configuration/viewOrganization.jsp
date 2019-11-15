<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import = "java.text.SimpleDateFormat" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	
</style>

<div id="viewOrganizationContent" class="_bcg config" style="font-size: 0.9em;text-transform: uppercase;padding-left: 1em;">
	
	<form:form id="viewOrganizationForm" commandName="organization" class="form">
		 <div class="viewOrgHeaderDiv" >
    		<span class="viewOrgHeaderTitle" >PROFILE</span>
    	</div>
		 
		   <div class="viewOrgBlockDiv" >		   				
			    <div class="viewOrgRowCol1Div" >
			    	<div class="viewOrgRowLabelCol1Div" >
			    		<label>Organization Type:</label>
			    	</div>
					<div class="viewOrgRowLabelCol12Div" >
						<label class="orgLabel" >${organization.typeName}</label>
					</div>				
				</div>
				   <div class="viewOrgRowCol2Div" >
			    	<div class="viewOrgRowLabelCol2Div" >
						<label>Organization Id:</label>
					</div>
					<div class="viewOrgRowLabelCol22Div" >
						<label class="orgLabel wordWrapText" >${organization.displayIdentifier}</label>
					</div>
				</div>				
	
			    <div class="viewOrgRowCol1Div" >
			    	<div class="viewOrgRowLabelCol1Div" >			    	
						<label>Parent Organization:</label>
					</div>
					<div class="viewOrgRowLabelCol12Div" >
						<label class="orgLabel" >${organization.parentOrganizationName}</label>
					</div>
    			</div>			
				<div class="viewOrgRowCol2Div" >
			    	<div class="viewOrgRowLabelCol2Div" >	
						<label>Parent Organization Id:</label>
					</div>	
					<div class="viewOrgRowLabelCol22Div" >
						<label class="orgLabel" >${organization.parentOrgDisplayName}</label>
					</div>
				</div>				
				
			    <div class="viewOrgRowCol1Div" >
			    	<div class="viewOrgRowLabelCol1Div" >		
					<label>Parent Org Type:</label>
					</div>
					<div class="viewOrgRowLabelCol12Div" >
						<label class="orgLabel" >${organization.parentOrgTypeCode}</label>
					</div>
				</div>
			    <div class="viewOrgRowCol2Div" >
			    	<div class="viewOrgRowLabelCol2Div" >		
						<label>Contracting Org:</label>
					</div>
					<div class="viewOrgRowLabelCol22Div" >
						<c:if test="${not organization.contractingOrganization}"><label>No</label></c:if>
						<c:if test="${organization.contractingOrganization}"><label>Yes</label></c:if>
					</div>						
				</div>	       	       		         		
       		</div>
       		
       	<c:if test="${not empty organization.assessmentProgram}">
       		<div class="viewOrgHeaderDiv" >
    			<span class="viewOrgHeaderTitle" >ASSESSMENT PROGRAMS</span>
    		</div>
    		
    		<div class="viewOrgBlockDiv" >
    			<div class="viewOrgRowCol1Div" >
			    	<div class="viewOrgRowLabelCol1Div" >		
					<label>Assessment Programs:</label>
					</div>				
					<c:if test="${not empty organization.assessmentProgram}">
							<div class="viewOrgRowLabelCol12Div" style="text-transform: none;" >								
								<c:forTokens var="programName" items="${organization.assessmentProgram}" delims="," varStatus="status" >
           							 <c:out value="${programName}"/><c:if test="${!status.last}">,<br></c:if>
        						</c:forTokens>								
							</div>
					</c:if>	
				</div>
				
				<c:if test="${not empty organization.testingModelName}">				        
							<div class="viewOrgRowCol2Div" >
						    	<div class="viewOrgRowLabelCol2Div" style="float: left;" >		
									<label>Dlm State Model:</label>
								</div>	
								<div class="viewOrgRowLabelCol22Div" >	
									<c:forTokens var="testingModel" items="${organization.testingModelName}" delims="," varStatus="status" >
           								 <c:out value="${testingModel}"/><c:if test="${!status.last}">,<br></c:if>
        							</c:forTokens>
								</div>						
							</div>					
			    </c:if>		

			</div>
		</c:if>
				
    		<div class="viewOrgHeaderDiv" >
    		<c:choose>
    			<c:when test="${organization.typeCode=='SCH'}">
    				<span class="viewOrgHeaderTitle" >STATUS AND DATES</span>
    			</c:when>    
    			<c:otherwise>
					<span class="viewOrgHeaderTitle" >STATUS</span>
    			</c:otherwise>
    		</c:choose>	
    		</div>
       		
       		<div class="viewOrgBlockDiv" >
       			<div class="viewOrgRowCol1Div" >
			    	<div class="viewOrgRowLabelCol1Div" >		
					<label>Ep Status:</label>
					</div>
					<div class="viewOrgRowLabelCol12Div" >
						<c:if test="${not organization.activeFlag}"><label>InActive</label></c:if>
						<c:if test="${organization.activeFlag}"><label>Active</label></c:if>	
					</div>
				</div>
			<c:if test="${organization.typeCode=='SCH' || organization.typeCode=='ST'}">
				<div class="viewOrgRowCol2Div" >
			    	<div class="viewOrgRowLabelCol2Div" >		
					<label>TESTING DAYS:</label>
					</div>
					<div class="viewOrgRowLabelCol22Div" >
						<label>${organization.testDays}</label>	
					</div>
				</div>
				<div class="viewOrgRowCol1Div" >
			    	<div class="viewOrgRowLabelCol1Div" >		
					<label>TESTING BEGIN TIME:</label>
					</div>
					<div class="viewOrgRowLabelCol12Div" >
							<c:choose>
    							<c:when test="${not empty organization.testBeginTime}">
        							<label><fmt:formatDate value="${organization.testBeginTime}" pattern="hh:mm a" /></label>
							    </c:when>    
    							<c:otherwise>
									<label></label>
    							</c:otherwise>
							</c:choose>	
					</div>
				</div>
				<div class="viewOrgRowCol2Div" >
			    	<div class="viewOrgRowLabelCol2Div" >		
					<label>TESTING END TIME:</label>
					</div>
					<div class="viewOrgRowLabelCol22Div" >
							<c:choose>
    							<c:when test="${not empty organization.testEndTime}">
        							<label><fmt:formatDate value="${organization.testEndTime}" pattern="hh:mm a" /></label>
							    </c:when>    
    							<c:otherwise>
									<label></label>
    							</c:otherwise>
							</c:choose>	
					</div>
				</div>
			</c:if>
       		</div>
       		
	</form:form>
</div>

