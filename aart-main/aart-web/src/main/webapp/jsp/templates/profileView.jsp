<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='security'
	uri='http://www.springframework.org/security/tags'%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="<c:url value='/js/profileView.js?v=1'/>"></script>

<style type="text/css">
img {
	max-width: 100%;
	height: auto;
	width: auto\9; /* ie8 */
}
.form .form-fields label[for="newPass"].error {
    text-align: justify;
    width: 20%;
}
.myProfileRoleContainer {
    padding: 0 0 30px;
}
#profileViewDiv ._bcg .btn_blue{
    color: white !important;
}

#profile_menu > ul >li {
    background-color: white;
    color: DimGray;
}

#profileViewDiv ._bcg{
	max-width:100% !important;
}

 #saveDispName{
 	margin-left: 10px;
 }
 
 #savePass{
 	margin-left: 10px;
 }
 
 #profileViewDiv .btn_blue{
 	cursor: default;
 }
 
 #changeDefaultOrgAndRole .form-fields {
 	width: 440px;
 }
 
  
</style>
<link rel="stylesheet" href="<c:url value='/css/theme/profileView.css'/>" type="text/css" /> 
<link rel="stylesheet" href="<c:url value='/css/passfield.min.css'/>" type="text/css" /> 
<div style="width: 100%" class = "pagecontent profileView">
	<div id="ovMessage" class="ovSuccess m-success hidden"></div>	
	<div class="container _bcg">
		<div class="row">
			<div id="ProfileTabs">
					<div id="profile_menu">
						<ul class="nav nav-tabs sub-nav" >
							<li class="active nav-item">
							<a href="#ovLink" onClick="showOverview()" class ="overview myProfileTabs nav-link active" tabindex="0"><fmt:message key="label.myprofile.overview" /></a> 
							</li>

							<!-- <ul class="show"> -->
									<li class="nav-item" ><a class="nav-link myProfileTabs editDisplayName" href="#ednLink" onClick="showEditDisplayName()" tabindex="0"><fmt:message key="label.myprofile.editDisplayName" /></a></li>
									<!--Deb: Using the 'imageFolderName' parameter which has been setup in the env.properties file. Currently the 'imageFolderName' can be setup as 'PLTW' or 'KITE'
									Requirement for the password Tab is to hide the tab for PLTW users. ALso the password tab should be enable for all the internal users.  -->
									<c:if test="${imageFolderName ne 'PLTW' || user.internaluserindicator}">
										<li class="nav-item"><a class="nav-link myProfileTabs changePassword" href="#cpLink" onClick="showChangePassword()" tabindex="0"><fmt:message key="label.myprofile.changePassword" /></a></li>
									</c:if>
									<li class="nav-item"><a class="nav-link myProfileTabs changeDefaultOrgAndRole" href="#changeDefaultOrgAndRoleLink" onClick="showChangeDefaultOrgAndRole()" tabindex="0"><fmt:message key="label.myprofile.changeDefaultOrgAndRole" /></a></li>
							<!-- 	</ul> -->


							<li class="active nav-item" ><a class="nav-link myProfileTabs security" href="#sLink" onClick="showSecurity()" tabindex="0"><fmt:message key="label.myprofile.security" /></a>
							<!-- 	<ul class="show"> -->
									<li class="nav-item"><a class="nav-link myProfileTabs securityAgreement" href="#saLink" onClick="showSecurityAgreement()" tabindex="0"><fmt:message key="label.myprofile.securityagreement" /></a></li>
									<li class="nav-item"><a class="nav-link myProfileTabs renewexpiration" href="#reLink" onClick="showRenewalExpiration()" tabindex="0"><fmt:message key="label.myprofile.renewalexpiration" /></a></li>
								<!-- </ul> -->
							</li>
						</ul>
					</div>

			<div class ="cell" style="padding: 40px;">
				<div id="overview" class="m-item-container">
				<div class="container">
				<div class="row"> <div class="cell blueLabel"><fmt:message key='common.firstname'/>:</div><div class="cell"><c:out value="${user.firstName}"/></div></div>
				<div class="row"> <div class="cell blueLabel"><fmt:message key='common.lastname'/>:</div><div class="cell"><c:out value="${user.surName}"/></div></div>
				<div class="row"> <div class="cell blueLabel"><fmt:message key='common.displayname'/>:</div><div id= "ovDisplayName" class="cell"><c:out value="${user.displayName}"/></div></div>
				<div class="row"> <div class="cell blueLabel"><fmt:message key='common.username'/>:</div><div class="cell"><c:out value="${user.email}"/></div></div>
				<div class="row"> <div class="cell blueLabel"><fmt:message key='label.myprofile.user.organization'/>:</div>
					<div class="cell pv-default-org"> 
						<c:set var="orgOut" value="0" scope="page" />					
					<c:if test="${not empty userOrganizationsGroups}">					
						<c:set var="orgLists" value=""/>
						<c:forEach items="${userOrganizationsGroups}" var="userOrganizationsGroup" varStatus="status" >
							<c:set var="selectedOrganizationFlag" scope="page" value=""></c:set>
		  						<c:if test="${userOrganizationsGroup.group.groupId eq user.currentGroupsId}">		  				
		  							<c:if test="${not empty orgLists}">
										<c:set var="orgLists" value="${orgLists}, ${userOrganizationsGroup.organization.organizationName}"/>		  							
		  							</c:if>
		  							<c:if test="${empty orgLists}">
		  								<c:set var="orgLists" value="${userOrganizationsGroup.organization.organizationName}"/>
		  							</c:if>
		  						</c:if>
		  				</c:forEach>		  				
					    <c:out value="${orgLists}"/>
		  				</c:if>						
					</div>
				</div>
				<div class="row"> <div class="cell blueLabel"><fmt:message key='label.myprofile.user.role'/>:</div><div class="cell pv-default-role"> 
					<c:if test="${not empty userGroups}">				
						<c:forEach items="${userGroups}" var="userGroup" varStatus="status">
							<c:out value="${userGroup.groupName}"/><c:if test="${!status.last}">,</c:if>
						</c:forEach>
					</c:if>					
				</div></div>
				<div class="row"> <div class="cell blueLabel"><fmt:message key='label.myprofile.user.assessmentprogram'/>:</div>
						<div class="cell"><c:out value="${userAssessmentPrograms}"/></div>
				</div>
				</div>
				</div>
				<div id="editDisplayName" class="m-item-container hidden">
					<div id="dnErrors" class="displayNameErrors"></div>
					
					<form class="validDispName" id="displayNameForm" method="get" action="">
					<div class="container form ca-form"> 
						<div class="form-fields">
							 <label class="field-label" for="dispName"><fmt:message key="common.displayname"/>:</label>
							  <input  id="dispName" name="dispName" value="${user.displayName}" maxlength="160"/> </div><div id="dnErrorCell" class="cell"></div>
						<div class="btn-bar">
							<a id="saveDispName" class="btn_blue"><fmt:message key="label.common.save"/></a></div> </div> 
					</form>
					
				</div>
			<!--	Changed for US-14985 -->					
				<div id="changePassword" class="m-item-container hidden">
					<div id="cpErrors" class="passwordErrors"></div>
					<form class="validPass" id="changePassForm" method="get" action="">
					 <div class="container form ca-form"> 
						<div class="form-fields"><label class="field-label" for="curPass" ><fmt:message key="label.myprofile.curPass"/>:</label><input  id="curPass" name="curPass" type="password"/></div>
						<div class="form-fields"><label class="field-label" for="newPass" ><fmt:message key="label.myprofile.newPass"/>:</label><div id="mypass-wrap" class="control-group"><input  id="newPass" name="newPass" type="password"/></div></div>
						<div class="form-fields" style="margin-top: 10px"><label class="field-label" for="conPass"><fmt:message key="label.myprofile.conPass"/>:</label><input  id="conPass" name="conPass" type="password"/></div> 
						<div class="btn-bar"><a id="savePass" class="btn_blue" style="margin-top: 10px;" ><fmt:message key="label.common.save"/></a></div>
					</div> 
					</form>
				</div>
				<div id="changeDefaultOrgAndRole" class="m-item-container hidden">
					<div id="cdorErrors" class="passwordErrors"></div>
					<div class="container  form ca-form myProfileRoleContainer"> 
						<div class="form-fields">
							<div>
								<div id="userDefaultRole" class='default'>Your default role is:</div>
								<div class="roleContainer">
								<div id="userDefaultRoleSelect">Role:
							 	<select id="defaultUserGroup" title="Role" class="defaultRoleSelect bcg_select">
							 		<c:forEach items="${userGroups}" var="userGroup" varStatus="status">
							 			<c:set var="selectedGroupFlag" scope="page" value=""></c:set>
										<c:if test="${userGroup.groupId eq user.currentGroupsId}">
											<c:set var="selectedGroupFlag" scope="page" value="selected='selected'"></c:set>
										</c:if>
							 			<option ${selectedGroupFlag} value="${userGroup.groupId}"><c:out value="${userGroup.groupName}"/></option>
									</c:forEach>
								</select>
								</div>
								</div>
								
								<div class="roleContainer">
								<div id='userDefaultOrganizationSelect'>Organization:
	  							<select id="defaultUserOrganization" title="Organization" class="defaultRoleSelect bcg_select">
		  							<c:forEach items="${userOrganizationsGroups}" var="userOrganizationsGroup" varStatus="status">
		  								<c:set var="selectedOrganizationFlag" scope="page" value=""></c:set>
		  								<c:if test="${userOrganizationsGroup.group.groupId eq user.currentGroupsId}">
				 							<c:if test="${userOrganizationsGroup.organization.id eq user.currentOrganizationId}">
												<c:set var="selectedOrganizationFlag" scope="page" value="selected='selected'"></c:set>
								 			</c:if>
							     			<option ${selectedOrganizationFlag} value="${userOrganizationsGroup.organization.id}"><c:out value="${userOrganizationsGroup.organization.organizationName}"/></option>
						     			</c:if>
						     		</c:forEach>
	  							</select>
	  							</div>
	  							</div>
								<div class="roleContainer">
    			     			<div id='userDefaultAPSelect'>Assessment Program:
    			     			<select id="defaultUserAssessmentProgram" title="Assessment Program" class="defaultRoleSelect bcg_select">
    			         			<c:forEach items="${assessmentPrograms}" var="userAssessmentProgram" varStatus="status">
    			         				<c:set var="selectedAssessmentProgramFlag" scope="page" value=""></c:set>
   			         					<c:if test="${userAssessmentProgram.id eq user.currentAssessmentProgramId}">
											<c:set var="selectedAssessmentProgramFlag" scope="page" value="selected='selected'"></c:set>
										</c:if>
    			 						<option ${selectedAssessmentProgramFlag} value="${userAssessmentProgram.id}">
    			 							<c:out value="${userAssessmentProgram.abbreviatedname}"/>
    			 						</option>
    			 					</c:forEach>
    			 				</select>
    			 				</div>
    			 				</div>
    							<div id ="availbleRoles" class='default' style="padding-top: 10%;padding-bottom: 5%; clear:both;">Your available roles are:</div>
								<table style="width:100%" id="changeDefaultOrgAndRoleTable">
									 
									
									<c:forEach items="${readOnlyRoleList}" var="readOnlyRole" varStatus="status">
											<tr>
											<c:choose>
												<c:when test="${status.first}">
												 <th class="cdor-group-name"><c:out value="${readOnlyRole}"/> </th>
												</c:when>
												<c:otherwise>
												<td class="cdor-group-name"><c:out value="${readOnlyRole}"/></td>
												</c:otherwise>
											</c:choose>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div> 
						<div class="btn-bar"><a id="saveChangeDefaultOrgAndRole" class="btn_blue"><fmt:message key="label.common.save"/></a></div>
					</div> 
					
				</div>
				<div id="securityAgreement"  class="m-item-container hidden">
					<form class="validSecurityAgreement" id="securityAgreementForm" method="get" action="">
						<div class="container"> 
							<div class="row"> 
								<div class="cell">
									<c:if test="${not empty securityText}">
									<table>												
									<c:forEach items="${securityText}" var="txt" varStatus="status">
										<tr><td><c:out value="${txt.attributeValue}"/></td></tr>
										<tr><td><c:out value=""/></td></tr>

									</c:forEach>								
									</table>	
									</c:if>
									<input type="radio" id="agreementElection1" name="agreementElection" title="<fmt:message key="label.myprofile.sa8"/>" value="true" checked/> <fmt:message key="label.myprofile.sa8"/><br/>
									<input type="radio" id="agreementElection2" name="agreementElection" title="<fmt:message key="label.myprofile.sa9"/>" value="false"/> <fmt:message key="label.myprofile.sa9"/><br/><br/>
									<font style="color:gray;"><fmt:message key="label.myprofile.sa10"/></font><br/>
									<input  id="signerName" title="<fmt:message key="label.myprofile.sa10"/>" name="signerName" value=""/>
									<a id="saveSecurityAgreement" style="margin-left: 220px;" class="btn_blue"><fmt:message key="label.common.save"/></a><br/>
									<div id="signerNameMessage" class="hidden" ></div>
									<div id="saMessage"  class="ovSuccess m-success hidden"></div>
								</div>
							</div> 	
						</div> 
					</form>
				</div>
				<div id="security"  class="m-item-container hidden">
					<form class="validSecurity" id="securityForm" method="get" action="">
						<div class="container">
							<div class="row"> 
								<div class="cell">
									<div style="color: #0E76BC;"><fmt:message key="label.myprofile.securityAwareness"/><br/><br/></div>
									<fmt:message key="label.myprofile.security1"/><br/><br/>
									<fmt:message key="label.myprofile.security2"/><br/><br/>
								</div>
							</div> 	
						</div> 
					</form>
				</div>
				<div id="renewexpiration" class="m-item-container hidden">
					<div class="container">
						<div class="row"> <div class="cell blueLabel"><fmt:message key='label.myprofile.renewexp.schoolyear'/>:</div><div class="cell" id="schoolyear"> ${securityAgreement.schoolYear}</div></div>
						<div class="row"> <div class="cell blueLabel"><fmt:message key='label.myprofile.renewexp.agreementelection'/>:</div><div class="cell" id="dagreementelection">${securityAgreement.agreementElection}</div></div>
						<div class="row"> <div class="cell blueLabel"><fmt:message key='label.myprofile.renewexp.agreementsigned'/>:</div><div class="cell" id="agreementSignedDate">${securityAgreement.agreementSigned}</div></div>
						<div class="row"> <div class="cell blueLabel"><fmt:message key='label.myprofile.renewexp.expiredate'/>:</div><div class="cell" id="expiredate">${securityAgreement.expireDate}</div></div>
					</div>
				</div>				
			</div>
		</div>
	</div>
	<div id="warnSignOutDiv" class="hidden" >
		<div style="font-size: .75em; color: green;"><fmt:message key="success.myprofile.passwordChange"/></div>
		<br><a href="j_spring_security_logout" onClick="window.localStorage.clear();"><fmt:message key="label.userHome.sigout" /></a>
	</div>
</div>
