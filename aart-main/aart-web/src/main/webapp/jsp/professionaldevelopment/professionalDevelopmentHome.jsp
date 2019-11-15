<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/jsp/include.jsp" %>

<div>
	
	<div class="pdLeftCol" style= "overflow-y:scroll;">
		<h4 align="left"> <fmt:message key="label.pd.home.recentactivity" /> </h4>
		<c:forEach var="activity" items="${activities}">
		 <fmt:formatDate pattern="MM/dd/yyyy" value="${activity.modifiedDate}" /> - ${activity.description}
		 <br />
		</c:forEach>
	</div>

	<div class = "pdRightCol">
		<jsp:include page="professionalDevelopmentMyInfo.jsp" />
	</div>

</div>