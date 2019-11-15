<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jsp/include.jsp" %>

<c:set var="divId" value="popoverContent_${randomId}" />

<div id="${divId}" class="popover-content">
	<input id="eeId" type="hidden" value="${ee.id}"/>
	<div>
		${fn:escapeXml(linkageLevelName)}: ${fn:escapeXml(linkageLevelLongDesc)}
	</div>
	<br/><br/>
	<c:if test="${not empty instPDFLoc or not empty resourcePDFs or not empty brailleResource}">
		<div>
			<c:if test="${not empty instPDFLoc}">
				<div class="row">
					<div class="col-1"></div>
					<div class="col">
						<label>Mini-Map</label>
					</div>
					<div class="col-3">
						<a id="instrcutionsPDFLink-${divId}" target="_blank" href="getItiInstructionsPdf.htm?instructionsloc=${instPDFLoc}">
							<img style="cursor:pointer;" src="images/pdf.png">
						</a>
					</div>
				</div>
			</c:if>
			<c:if test="${not empty resourcePDFs}">
				<div class="row">
					<div class="col-1"></div>
					<div class="col">
						<label>Testlet Information Page</label>
					</div>
					<div class="col-3">
						<c:forEach items="${resourcePDFs}" var="resourcePDF">
							<a id="resourcePDFLink-${resourcePDF.testSectionId}" target="_blank" href="${resourcePDF.fileLocation}">
								<img style="cursor:pointer;" src="images/pdf.png">
							</a><br>
						</c:forEach>
					</div>
				</div>
			</c:if>
			<c:if test="${not empty brailleResource}">
				<div class="row">
					<div class="col-1"></div>
					<div class="col">
						<label>Braille Ready File</label>
					</div>
					<div class="col-3">
						<a id="brailleResource-${brailleResource.testSessionId}" target="_blank" href="${brailleResource.fileLocation}">
							<img style="cursor:pointer;" src="images/test/braille_25_25.png">
						</a>
					</div>
				</div>
			</c:if>
			<br/><br/>
		</div>
	</c:if>
	<div class="popover-buttons">
		<c:choose>
			<c:when test="${popoverType == 'beginInstruction'}">
				<c:choose>
					<c:when test="${contentAvailable}">
						<button class="instruction-button begin-instruction">
							<div class="button-image-container">
								<img src="<c:url value='images/icons/instruction-and-assessment-planner/svg/transparentBlackArrowBlueBox.svg'/>"/>
							</div>
							<div class="button-text">Begin Instruction</div>
						</button>
					</c:when>
					<c:otherwise>
						<div>
							<c:choose>
								<c:when test="${not empty completedPlans}">
									All testlets at this linkage level have been assessed.
								</c:when>
								<c:otherwise>
									Testlets are not available for this linkage level at this time.
								</c:otherwise>
							</c:choose>
						</div>
					</c:otherwise>
				</c:choose>
				<c:if test="${not empty latestPlanWithTestlet and latestPlanWithTestlet.statusCode == 'CANCELED'}">
					Canceled&nbsp;<fmt:formatDate value="${latestPlanWithTestlet.modifiedDate}" pattern="MM/dd"/>
				</c:if>
				<jsp:include page="iap-popover-content-completed-plans.jsp"/>
			</c:when>
			<c:when test="${popoverType == 'finishInstruction'}">
				<c:choose>
					<c:when test="${contentAvailable}">
						<button class="instruction-button finish-instruction-assign-testlet">
							<div class="button-image-container">
								<img src="<c:url value='images/icons/instruction-and-assessment-planner/svg/flagBox-transparent.svg'/>"/>
							</div>
							<div class="button-text">Instruction Complete<br/>Assign Testlet</div>
						</button>
					</c:when>
					<c:otherwise>
						<div>
							<c:choose>
								<c:when test="${not empty completedPlans}">
									All testlets at this linkage level have been assessed.
								</c:when>
								<c:otherwise>
									Testlets are not available for this linkage level at this time.
								</c:otherwise>
							</c:choose>
						</div>
					</c:otherwise>
				</c:choose>
				<br/>
				<button class="instruction-button finish-instruction-no-testlet">
					<div class="button-image-container">
						<img src="<c:url value='images/icons/instruction-and-assessment-planner/svg/ban.svg'/>"/>
					</div>
					<div class="button-text">Instruction Complete<br/>Do Not Assign Testlet</div>
				</button>
				<jsp:include page="iap-popover-content-completed-plans.jsp"/>
			</c:when>
			<c:when test="${popoverType == 'cancelTestlet'}">
				<security:authorize access="hasRole('CANCEL_ITI_ASSIGNMENT')">
					<c:if test="${empty lastSCCodeApplied}">
						<button class="instruction-button cancel-testlet">
							Cancel Testlet
						</button>
					</c:if>
				</security:authorize>
				<jsp:include page="iap-popover-content-completed-plans.jsp"/>
			</c:when>
		</c:choose>
		
		<c:if test="${not empty latestPlanWithTestlet}">
			<security:authorize access="hasRole('HIGH_STAKES_SPL_CIRCUM_CODE')">
				<c:if test="${not empty scCodes}">
					<br/><br/>
					<div>
						SPECIAL CIRCUMSTANCE
						<select class="sc-code">
							<option value="-1">Select</option>
							<c:forEach items="${scCodes}" var="scCode">
								<option value="${scCode.id}" data-requires-confirmation="${scCode.requireConfirmation == true}">${scCode.description}</option>
							</c:forEach>
						</select>
					</div>
					<div class="sc-button-container">
						<button class="apply-sc-code">
							Save
						</button>
					</div>
					<c:if test="${not empty lastSCCodeApplied}">
						<div>
							${lastSCCodeApplied.statusName}
							<c:set var="lowercaseStatus" value="${fn:toLowerCase(lastSCCodeApplied.statusCode)}" />
							<c:choose>
								<c:when test="${lowercaseStatus == 'saved'}">
									<img class="sc-image ${lowercaseStatus}" src="<c:url value='images/icons/instruction-and-assessment-planner/svg/save.svg'/>" />
								</c:when>
								<c:when test="${lowercaseStatus == 'pending_further_review'}">
									<img class="sc-image ${lowercaseStatus}" src="<c:url value='images/icons/instruction-and-assessment-planner/svg/timerBox-transparent.svg'/>" />
								</c:when>
								<c:when test="${lowercaseStatus == 'approved'}">
									<img class="sc-image ${lowercaseStatus}" src="<c:url value='images/icons/instruction-and-assessment-planner/svg/whiteCheckmark.svg'/>" />
								</c:when>
								<c:when test="${lowercaseStatus == 'not_approved'}">
									<img class="sc-image ${lowercaseStatus}" src="<c:url value='images/icons/instruction-and-assessment-planner/svg/ban.svg'/>" />
								</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
							<c:remove var="lowercaseStatus"/>
						</div>
						
						<%-- user can approve SC codes --%>
						<security:authorize access="hasRole('HIGH_STAKES_SPL_CIRCUM_CODE_SEL')">
							<c:if test="${not empty lastSCCodeApplied.requireConfirmation and lastSCCodeApplied.requireConfirmation == true}">
								<br/>
								<div>
									APPROVAL
									<select class="sc-code-approval">
										<option value="-1">Select</option>
										<c:forEach items="${scCodeStatuses}" var="scCodeStatus">
											<option value="${scCodeStatus.id}">${scCodeStatus.categoryName}</option>
										</c:forEach>
									</select>
									<div class="sc-button-container">
										<button class="approve-sc-code">
											Save
										</button>
									</div>
								</div>
							</c:if>
						</security:authorize>
					</c:if>
				</c:if>
			</security:authorize>
		</c:if>
		<br/><br/>
	</div>
	
	<div class="confirm-dialog" style="display:none;">
		<p>Assigning a testlet cannot be reversed.</p>
		<p>Select Continue to assign testlet or Cancel to go back and review.</p>
	</div>
	
	<div class="sc-confirm-dialog" style="display:none;">
		<p>
			One or more of the Special Circumstance code selections require approval by a state-level administrator. 
			Application of the code selections for the following students will be held in Pending status while the 
			request is reviewed.
		</p>
		
		<p>Click the OK button to continue, or Cancel to clear all of your Special Circumstance code selections.</p>
	</div>
</div>

<script type="text/javascript">
$(function(){
	var eeId = ${ee.id};
	var contentCode = '${ee.contentCode}';
	<%-- escape single quotes and trim potential line breaks off to make sure we don't cause ourselves a syntax error --%>
	var linkageLevel = '${fn:trim(fn:replace(linkageLevelName, "'", "\\'"))}';
	var itiId = '${latestPlan == null ? "" : latestPlan.id}';
	var itiIdWithTestlet = '${latestPlanWithTestlet == null ? "" : latestPlanWithTestlet.id}';
	var studentsTestsId = '${latestPlanWithTestlet == null ? "" : latestPlanWithTestlet.studentsTestsId}';
	//<c:if test="${not empty lastSCCodeApplied}">
		var sscId = '${lastSCCodeApplied.sscId}';
	//</c:if>
	var $confirmDialog = null;
	var $scConfirmDialog = null;
	
	//<c:if test="${popoverType == 'beginInstruction'}">
		$('#${divId} .popover-buttons button.begin-instruction').on('click', function(){
			beginInstruction(eeId, linkageLevel);
		});
	//</c:if>
	//<c:if test="${popoverType == 'finishInstruction'}">
		//<c:if test="${contentAvailable}">
			$('#${divId} .popover-buttons button.finish-instruction-assign-testlet').on('click', function(){
				if ($confirmDialog == null){
					$confirmDialog = $('#${divId} .confirm-dialog').dialog({
						resizable: false,
						width: 350,
						height: 'auto',
						autoOpen: false,
						modal: true,
						title: 'Assign Testlet?',
						buttons: {
							Continue: function(){
								finishInstruction(itiId, true);
								$(this).dialog('close');
							},
							Cancel: function(){
								$(this).dialog('close');
							}
						}
					})
				}
				$confirmDialog.dialog('open');
			});
		//</c:if>
		$('#${divId} .popover-buttons button.finish-instruction-no-testlet').on('click', function(){
			finishInstruction(itiId, false);
		});
	//</c:if>
	//<c:if test="${popoverType == 'cancelTestlet'}">
		//<security:authorize access="hasRole('CANCEL_ITI_ASSIGNMENT')">
			$('#${divId} .popover-buttons button.cancel-testlet').on('click', function(){
				cancelTestlet(itiId);
			});
		//</security:authorize>
	//</c:if>
	
	
	//<security:authorize access="hasRole('HIGH_STAKES_SPL_CIRCUM_CODE')">
		//<c:if test="${not empty lastSCCodeApplied}">
			$('#${divId} .sc-code').attr('disabled', 'disabled').prop('disabled', true).val('${lastSCCodeApplied.id}');
			$('#${divId} .apply-sc-code').attr('disabled', 'disabled').prop('disabled', true);
		//</c:if>
		
		//<c:if test="${empty lastSCCodeApplied}">
			$('#${divId} .sc-code').on('change', function(){
				var val = $(this).val();
				if (val != null && val != '-1'){
					$('#${divId} .apply-sc-code').removeAttr('disabled').prop('disabled', false);
				} else {
					$('#${divId} .apply-sc-code').attr('disabled', 'disabled').prop('disabled', true);
				}
			}).trigger('change');
			
			$('#${divId} .apply-sc-code').on('click', function(){
				var selectedOption = $('#${divId} .sc-code option:selected');
				var scId = $('#${divId} .sc-code').val();
				if (selectedOption.data('requires-confirmation') == true){
					if ($scConfirmDialog == null){
						$scConfirmDialog = $('#${divId} .sc-confirm-dialog').dialog({
							resizable: false,
							width: 785,
							height: 'auto',
							autoOpen: false,
							modal: true,
							title: 'Special Circumstance Code Selection Approval Required',
							buttons: {
								OK: function(){
									applySCCode(itiIdWithTestlet, studentsTestsId, scId);
									$(this).dialog('close');
								},
								Cancel: function(){
									$('#${divId} .sc-code').val('-1').trigger('change').trigger('change.select2');
									$(this).dialog('close');
								}
							}
						});
					}
					$scConfirmDialog.dialog('open');
				} else {
					applySCCode(itiIdWithTestlet, studentsTestsId, scId);
				}
			});
		//</c:if>
		
		$('#${divId} .sc-code, #${divId} .sc-code-approval').select2({
			placeholder: 'Select',
			width: '95%'
		});
		
		//<security:authorize access="hasRole('HIGH_STAKES_SPL_CIRCUM_CODE_SEL')">
			//<c:if test="${not empty lastSCCodeApplied}">
				$('#${divId} .sc-code-approval').val('${lastSCCodeApplied.statusId}');
			//</c:if>
			
			$('#${divId} .sc-code-approval').on('change', function(){
				var val = $(this).val();
				if (val != null && val != '-1'){
					$('#${divId} .approve-sc-code').removeAttr('disabled').prop('disabled', false);
				} else {
					$('#${divId} .approve-sc-code').attr('disabled', 'disabled').prop('disabled', true);
				}
			}).trigger('change');
			
			$('#${divId} .approve-sc-code').on('click', function(){
				var statusId = $('#${divId} .sc-code-approval').val();
				if (statusId != null && statusId != '-1'){
					approveSCCode(sscId, statusId);
					$('#${divId}').closest('.popover').popover('hide');
				}
			});
		//</security:authorize>
	//</security:authorize>
});
</script>