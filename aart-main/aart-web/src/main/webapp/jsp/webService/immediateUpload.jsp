<!DOCTYPE html>
<%@ include file="/jsp/include.jsp" %>

    <div id="container_login">
       <div id="header">
       		<img src="../images/logo.png" alt="<fmt:message key="label.common.KITE" />" />
       </div>
       <div class="viewportPortable" >
       		<fmt:message key="webservice.immediate.page.message">       		
       			<fmt:param value="<fmt:message key='recordType.enrollment'/>"/>
       		</fmt:message>
	       <div class="homeContent">
	        <form:form method="post" enctype="multipart/form-data">
	            <fieldset>            	
	                <p>
	                    <input type="submit" value="<fmt:message key='upload.button'/>" />
	                </p>	 
	            </fieldset>
	        </form:form>
	        
	        <c:if test="${uploadCompleted}">
	        
	        	<c:choose>
			        <c:when test="${uploadSuccessful}">
			        ${userDetails.username}
			        	<fmt:message key="upload.successful"/>
					    <fmt:message key="upload.webservice.summary">
						    <fmt:param value="${recordsCreatedCount}"/>
							<fmt:param value="${recordsUpdatedCount}"/>
							<fmt:param value="${recordsRejectedCount}"/>
							<fmt:param value="${recordsCount}"/>
					    </fmt:message>	        
			        </c:when>	        	        
			        <c:when test="${! uploadSuccessful}">
			        	<fmt:message key="upload.failed"/>
			        </c:when>
			        <c:when test="${! uploadAuthorized}">
			        	<fmt:message key="upload.notAuthorized"/>
			        </c:when>	        	
	        	</c:choose>
	        		        
	        	<c:choose>
			        <c:when test="${rosterUploadSuccessful}">
			        ${userDetails.username}
			        	<br><fmt:message key="roster.upload.successful"/>
					    <fmt:message key="upload.webservice.summary">
						    <fmt:param value="${rosterRecordsCreatedCount}"/>
							<fmt:param value="${rosterRecordsUpdatedCount}"/>
							<fmt:param value="${rosterRecordsRejectedCount}"/>
							<fmt:param value="${rosterRecordsCount}"/>
					    </fmt:message>	        
			        </c:when>	        	        
			        <c:when test="${! rosterUploadSuccessful}">
			        	<fmt:message key="upload.failed"/>
			        </c:when>
			        <c:when test="${! rosterUploadSuccessful}">
			        	<fmt:message key="upload.notAuthorized"/>
			        </c:when>	        	
	        	</c:choose>
	        
		     </c:if>
	        
	        </div>
       </div>
	</div>

  <script type="text/javascript">
  function verifyFileSelected() {
  }
  </script>