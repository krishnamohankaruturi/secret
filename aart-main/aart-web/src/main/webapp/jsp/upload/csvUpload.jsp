<!-- CSS goes in the document HEAD or added to your external stylesheet -->
<style type="text/css">
table.gridtable {
	color:#333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
	width:100%;
	table-layout:fixed;
	word-wrap:break-word;	
}
table.gridtable th {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #dedede;
}
table.gridtable td {
	border-width: 1px;
	padding: 8px;
	/*width:100px;*/
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
}
</style>
<%@ include file="/jsp/include.jsp"%>
	        
    <div class="reportUpload csvFileUpload">
    
	    <div class="messages hidden">
			<c:if test='${errorMessage != null }'>
				<span class="ui-state-error">${errorMessage }</span>
			</c:if>
			<c:if test="${successMsg != null}">
				<span class="ui-state-highlight">${successMsg }</span>
			</c:if>
			<c:if test='${invalidUploadedFile != null }'>
				<span class="ui-state-error"><fmt:message key='error.upload.user.invalidfile'/></span>
			</c:if>
			<span class="error_message ui-state-error hidden fileExtensionError" id="fileExtensionError">
				<fmt:message key="required.fileUpload"/>
			</span>
			<span class="error_message ui-state-error hidden fileTypeError" id="fileTypeError">
				<fmt:message key="required.recordType"/>
			</span>
		</div>
	
       <div>
       		<h4><fmt:message key="csvupload.page.message"/></h4>
	       <div>
	        <form:form modelAttribute="uploadFile" id= "fileUploadForm" method="post" enctype="multipart/form-data">
	            
	            <label><fmt:message key="label.upload.type"/></label>	
				<form:select path="selectedRecordTypeId" id='recordTypeSelect'>
					<form:option value="0">
						<fmt:message key='label.common.select'/>
					</form:option>
					<%-- <form:options items="${recordTypes}" itemValue="id" itemLabel="categoryName" /> --%>
                    <c:forEach items="${recordTypes }" var="recordType" varStatus="index">
                        <c:choose>
                            <c:when test='${recordType.categoryCode eq "FIRST_CONTACT_RECORD_TYPE" }'>
                                <security:authorize access="hasRole('PERM_FIRST_CONTACT_UPLOAD')">
                                    <form:option value="${recordType.id }">${recordType.categoryName }</form:option>
                                </security:authorize>
                            </c:when>
                         </c:choose>
                    </c:forEach>
                    <form:hidden id="isRosterUpload" path="rosterUpload"/>
				</form:select>
				<form:errors path="selectedRecordTypeId" />							
				<!-- <legend> -->
				<%-- <fmt:message key="upload.message"> --%>
				<%-- <fmt:param value="<fmt:message key='recordType.enrollment'/>"/> --%>
				<%-- </fmt:message> --%>
				<!-- </legend> -->
					<div>
						<jsp:include page="../stateAndDistrictFilter.jsp"/>
					</div>
	                <p>
	                    <form:label for="fileData" path="fileData">File</form:label>: <br/>
	                    <form:input path="fileData" id="fileData" type="file" cssStyle="border: 1px solid #777;"/>
						<form:errors path="fileData" />
					</p>
					<form:hidden id="continueOnWarning" path="continueOnWarning" type="hidden" value="" />
				
			</form:form>
			
			<p>
			<security:authorize access="hasAnyRole('PERM_ENRL_UPLOAD', 'PERM_USER_UPLOAD', 'PERM_TEST_UPLOAD', 'PERM_ROSTERRECORD_UPLOAD')">
				<input type="submit" id="uploadData" class="btn_blue" value="<fmt:message key='upload.button'/>" />
			</security:authorize>
			</p>
			<c:if test="${showWarning}">
				<div id="ConfirmationDiv">
					<c:out value="${warningMessage}" />					
					<br/> <br/>
				</div>
			</c:if>
			<c:if test="${uploadCompleted}">
				<c:choose>
					<c:when test="${ ! empty uploadErrors }">
						<c:forEach items="${ uploadErrors}" var="errorMessage">  
                        <div class="error"><spring:message message="${errorMessage}"></spring:message></div>
                    	</c:forEach>  
                    	<br/><div class="error"><fmt:message key="upload.completed.witherrors" /></div>
					</c:when>
					<c:when test="${ ! empty inValidDetail }">
						<!-- For file rejection alone -->
						<fmt:message key="upload.file.rejected.prefix" />
						<!-- TODO for future display more like expected 3 but have 5 columns -->
						<fmt:message key="rejected.reason.format">
							<fmt:param value="${inValidDetail.fieldName}"/>
							<c:if test="${ not empty inValidDetail.invalidType }">
								<fmt:param>
								<fmt:message key="${inValidDetail.invalidType.invalidTypeName}"/>
								</fmt:param>
							</c:if>
						</fmt:message>
					</c:when>
					<c:when test="${ ! empty inValidRecords }">
						<fmt:message key="upload.summary">
							<fmt:param value="${recordsSkippedCount}" />
							<fmt:param value="${recordsCreatedCount}" />
							<fmt:param value="${recordsUpdatedCount}" />
							<fmt:param value="${recordsRejectedCount}" />
							<fmt:param value="${totalRecordCount }"/>
						</fmt:message>
						<br/>
						<fmt:message key="upload.completed.witherrors" />
						<display:table name="inValidRecords" id="inValidRecord" class="gridtable">
					  		<c:set var="runningCount" value="0" scope="page" />
						<!-- TODO change it to record type.identifier -->
						<display:column property="identifier" title="Identifier" />
						<display:column title="Reasons For Not Valid">
							<c:forEach items="${inValidRecord.inValidDetails}"
							var="inValidDetail" varStatus="status">${status.count})
							  		<c:choose>
								  		<c:when test="${inValidDetail.rejectRecord}">
								  			<c:choose>
								  				<c:when test="${inValidDetail.invalidType.invalidTypeName == 'ErrorOccurred' }">
								  					<fmt:message key="reject.permissiondenied.createorganization" />
								  				</c:when>
								  				<c:otherwise>
											  		<fmt:message key="property.reject.invalid">
												  		<fmt:param value="${inValidDetail.fieldName}"/>
												  		<fmt:param value="${inValidDetail.formattedFieldValue}"/>
												  		<c:if test="${ not empty inValidDetail.invalidType }">
												  			<fmt:param>
												  				<fmt:message key="${inValidDetail.invalidType.invalidTypeName}"/>
												  			</fmt:param>
												  		</c:if>
											  		</fmt:message>
									  			</c:otherwise>
									  		</c:choose>
								  		</c:when>
								  		<c:otherwise>
									  		<fmt:message key="property.invalid">
										  		<fmt:param value="${inValidDetail.fieldName}"/>
										  		<fmt:param value="${inValidDetail.formattedFieldValue}"/>
										  		<c:if test="${ not empty inValidDetail.invalidType }">
										  			<fmt:param>
										  				<fmt:message
										  					key="${inValidDetail.invalidType.invalidTypeName}"/>
										  			</fmt:param>
										  		</c:if>
									  		</fmt:message>
								  		</c:otherwise>
							  		</c:choose>
							  		<br/>
							  		<c:set var="runningCount" value="${status.count}" scope="page" />
							  	</c:forEach>
						  	</display:column>
						</display:table>
			        </c:when>
			        <c:otherwise>
						<fmt:message key="upload.summary">
							<fmt:param value="${recordsSkippedCount}" />
							<fmt:param value="${recordsCreatedCount}" />
							<fmt:param value="${recordsUpdatedCount}" />
							<fmt:param value="${recordsRejectedCount}" />
							<fmt:param value="${totalRecordCount }"/>
						</fmt:message>	
					<br/>			        
			        <fmt:message key="csv.upload.successful"/>
			        </c:otherwise>
	        </c:choose>
	        </c:if>
	        <c:if test='${invalidUsers != null}'>
                    <div id="invalidUsersDiv">
                        <c:if test='${fn:length(invalidUsers) > 0 }'>
                            <display:table name="invalidUsers" id="inValidUser" class="gridtable">
                                <display:column property="email" title="E-mail" />
                                <display:column property="displayIdentifier" title="Organization"/>
                                <display:column property="organizationTypeCode" title="Organization Level"/>
                                <display:column title="Reason">
                                    <c:forEach items="${inValidUser.inValidDetails }" var="invalidDetail" varStatus="index">
                                        <fmt:message key="label.upload.user.invalidmsg">
                                           <fmt:param value="${index.index + 1 }"/>
                                           <fmt:param value="${invalidDetail.fieldName}"/>
                                           <fmt:param value="${invalidDetail.formattedFieldValue}"/>
                                        </fmt:message>
                                        <br>
                                    </c:forEach>
                                </display:column>
                            </display:table>
                        </c:if>
                    </div>
              </c:if>
	       </div>
       </div>
	</div>

<script type="text/javascript">
	$(function() {
		
		$('#ConfirmationDiv').dialog({
			resizable: false,
			height: 200,
			width: 400,
			modal: true,
			autoOpen:true,
			title:'&nbsp;',
			buttons: {
				Yes: function() {
					$("#continueOnWarning").val("true");
					$('#fileUploadForm').submit();
			    },
			    Cancel: function() {
			    	 $(this).dialog('close');
			    }			    
			}
		});
		
		var recordTypeName = $('#recordTypeSelect').find(':selected').text();
		if (recordTypeName == "Roster" || recordTypeName == "Enrollment" || recordTypeName == "User"){
			$("#stateFilter").show();
			$("#isRosterUpload").val(1);
		}else{
			$("#stateFilter").hide();
			$("#isRosterUpload").val(0);
		}
	});

	$('#recordTypeSelect').change(function() {
		var recordTypeName = $(this).find(':selected').text();
		if (recordTypeName == "Roster" || recordTypeName == "Enrollment" || recordTypeName == "User"){
			$("#stateFilter").show();
			$("#isRosterUpload").val(1);
		}else{
			$("#stateFilter").hide();
			$("#isRosterUpload").val(0);
		}
	});

	function verifyFileSelected() {
	}
	
	$('#uploadData').click(function(event) {

		if($('#recordTypeSelect option:selected').val() == 0) {
			$(".messages").show();
			$('#fileTypeError').show();
			setTimeout("aart.clearMessages()", 3000);
			setTimeout(function(){ $("#messages").hide(); },3000);
			return false;
		}
		
		if($('#fileData').val().split('.').pop() != "csv") {
			$(".messages").show();
			$('#fileExtensionError').show();
			setTimeout("aart.clearMessages()", 3000);
			setTimeout(function(){ $("#ARTSmessages").hide(); },3000);
			return false;
		}
		
		$("#continueOnWarning").val("");
		$('#fileUploadForm').submit();
		
	});
</script>