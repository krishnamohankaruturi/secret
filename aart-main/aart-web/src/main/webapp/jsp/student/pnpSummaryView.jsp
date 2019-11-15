<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='security'
	uri='http://www.springframework.org/security/tags'%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style>

.header {
	float: left;
	margin-right: 100px;
	width: 100%;
}

</style>

<div id="summayDiv" style="width: 90%; float: left;" class="none">

	<div class ='header'> <br/> PNP Summary View </div>
	<c:choose>
	
		<c:when test="${! empty studentProfileItemAttributeValues}" >	
			
			<c:set var="attributeContainerName" scope="page" value="${studentProfileItemAttributeValues[0].attributeContainerName}"/>	
			<div id="containerNameDiv" style="width: 90%; float: left;">
				<br/><br/>
				<b>${attributeContainerName}</b><br/>
			</div>
			
			<c:forEach var="studentProfileItemAttribute" items="${studentProfileItemAttributeValues}">				
				<c:if test="${attributeContainerName != studentProfileItemAttribute.attributeContainerName}">			
					<div id="containerNameDiv" style="width: 90%; float: left;">
						<hr style="width: 100%"/>
						<br/>
						<b>${studentProfileItemAttribute.attributeContainerName}</b> <br/>
					</div>
					<c:set var="attributeContainerName" scope="page" value="${studentProfileItemAttribute.attributeContainerName}"/>
				</c:if>				
				<div id="attributeDiv" style="width: 90%; float: left; margin-left: 6%;">	
					${studentProfileItemAttribute.attributeName} (${studentProfileItemAttribute.attributeNickname}) : ${studentProfileItemAttribute.selectedValue}
				</div>			
			</c:forEach>
			
		</c:when>
		
		<c:otherwise>
			<br/><br/><br/><br/>
			<b>No data to display.</b>
		</c:otherwise>
		
	</c:choose>
	
</div>
