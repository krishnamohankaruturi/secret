<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<style>
#uploadUserForm {
    float: left;
    margin: 10px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
}
#uploadUserForm input {
    margin: 10px;
}

#uploadUserForm input[type="submit"] {
    float: left;
}

#uploadUserForm input[type="file"] {
    border-radius: 5px;
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
}

#uploadUserForm h4 {
    margin: 5px;
}
#invalidUsersDiv {
    float:left;
}
#inValidUser {
    border: 1px solid #a6c9e2;
    max-height: 250px;
    overflow-y: scroll;
}

#inValidUser th, #inValidUser td {
    border: 1px solid #a6c9e2;
}

#inValidUser th {
    color: #2E6E9E;
}
</style>
<div>
    <div>
	    <div class="messages">
	        <c:if test='${errorMessage != null }'>
	            <span class="ui-state-error">${errorMessage }</span>
	        </c:if>
	        <c:if test="${successMsg != null}">
	            <span class="ui-state-highlight">${successMsg }</span>
	        </c:if>
	        <c:if test='${invalidUploadedFile != null }'>
	           <span class="ui-state-error"><fmt:message key='error.upload.user.invalidfile'/></span>
	        </c:if>
	    </div>
	    <security:authorize access="hasRole('PERM_USER_UPLOAD')">
	    <form:form id="uploadUserForm" class="ui-widget ui-widget-content"  modelAttribute="uploadFile" enctype="multipart/form-data" action="sendUsers.htm" method="POST">
	        <h4><fmt:message key='label.upload.user'/></h4>
	        <form:input path="fileData" type="file" class="ui-widget-content"/>
	        <input type="submit" class="ui-widget-content" value="<fmt:message key='upload.button'/>"/>
	    </form:form>
	    </security:authorize>
    </div>
    <c:if test='${invalidUsers != null}'>
    <div id="invalidUsersDiv">
        <span>
            <fmt:message key="label.upload.user.invalidUsers">
                <fmt:param value="${fn:length(invalidUsers)}"/>
            </fmt:message>
        </span>
        <c:if test='${fn:length(invalidUsers) > 0 }'>
	        <display:table name="invalidUsers" id="inValidUser">
	            <display:column property="email" title="E-mail" />
	            <display:column property="organizationId" title="Organization"/>
	            <display:column property="organizationTypeCode" title="Organization Level"/>
	            <display:column title="Reason">
		            <c:forEach items="${inValidUser.inValidDetails }" var="invalidDetail" varStatus="index">
		                <fmt:message key="label.upload.user.invalidmsg">
		                   <fmt:param value="${invalidDetail.fieldName}"/>
		                   <c:choose>
			                   <c:when test='${invalidDetail.fieldValue != ""}'>
		                           <fmt:param value="${invalidDetail.fieldValue}"/>
		                       </c:when>
		                       <c:otherwise>
		                          <fmt:param value="''"/>
		                       </c:otherwise>
	                       </c:choose>
		                </fmt:message>
		            </c:forEach>
	            </display:column>
	        </display:table>
        </c:if>
    </div>
    </c:if>
</div>
<script>
$(document).ready(function() {
	$('#uploadUserForm input[type="submit"]').button();
});
</script>