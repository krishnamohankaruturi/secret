<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<script type="text/javascript" src="<c:url value='/js/external/jquery.print-preview.js'/>"> </script>
<link rel="stylesheet" media="all" href="<c:url value='/css/itiprint.css'/>" type="text/css" />

<div>	
	<br><br><br>
	<div>
		<div style="float:left;color: #0E76BC;"> 
			<span id="studentDetailsInfo"></span><br>
		</div>
		<div style="float:right;" align="right" class="printAllBtn">
				<input id="historyPrintAll" class="panel_btn" type="button" value="Print Selected Plans" /><br>
				<span id="historyPrintAllErrorMessage" class="hidden error">Please select at least one plan to print.</span>
		</div>
	</div>
	<div id="cancelITIDialog" style="display: none">
		<div>Are you sure you want to cancel this assignment?</div>
		<div>This will remove the student's tests and permanently cancel the plan.</div>
	</div>
	<hr style="width: 100%; height:1px;"/>
	
	<div>
				<div>
					<input type='checkbox' class="checkAll" name="checkAll" value='all' title='Select All Plans'/>Select All Plans <br>
					<span id="cancelledITIPlan" class="error"></span>
					<div id="accordion">
						  <c:forEach items="${historyInfo}" var="hInfo"  varStatus="iter">
				 			 <h3><input type='checkbox' class="printAllCheck" name="printAll" value='plan-${iter.index}' title='Instructional Plan: ${hInfo.name}'/> Instructional Plan: ${hInfo.name} </h3> 
						 	<div id="plan-${iter.index}">
						 		<%--US15519 ITI history overlay - add PDF's plus UI for print plans -- Commented the button code. In feature if we need to enable these buttons just need to remove
						 		comments. --%>
						 	    <%-- <div style="float:right;"><input data-planid="plan-${iter.index}"  class="printIndPlan panel_btn" type="button" value="Print"></div> --%>
							 	<ul>
							 		<li>		
							 			<c:forEach items="${instructionsPDFs}" var="instPDFLoc" varStatus="insIter">
							 				<c:if test="${insIter.index eq iter.index}">
							 					<c:if test='${not empty instPDFLoc}'>							 								 					
							 						<div id="instrcutionsPDF-${iter.index}" style="float:right;"> 
													<span  id="instrcutionsPDFLabel-${iter.index}"><fmt:message key="label.iti.instrcutionsPDF"/></span>
													<a id="instrcutionsPDFLink-${iter.index}" href="getItiInstructionsPdf.htm?instructionsloc=${instPDFLoc}"><img src='images/pdf.png' style='border:0px solid;' /> </a>
												</div> 
										</c:if>		
							 				</c:if>
							 			</c:forEach>						 						 								 											
							 		</li>
							 		<li class="hidden planName"><b> Instructional Plan: </b>${hInfo.name}</li>
							 		<c:choose>
	      								<c:when test="${hInfo.statusCategory.categoryName=='Pending'}">
	      									<li><b>Status: </b><a href="javascript:confirmIti('${hInfo.testCollectionName}', '${hInfo.id}');">Pending</a></li>
								    	</c:when>
	      								<c:otherwise>
	      									<li>
	      										<p>
	      									  		<b>Status:</b> Confirmed &nbsp;&nbsp;&nbsp;&nbsp;
	      									  		<security:authorize access="hasRole('CANCEL_ITI_ASSIGNMENT')">
	      									  			<c:if test="${hInfo.testStatus=='unused'}">
	      									  				<c:set var="hInfoNameStr" value="${hInfo.name}"/>
	      									  				<c:set var="searchSingleQuote" value="\'" />
															<c:set var="replaceSingleQuote" value="\\\'" />
															<c:set var="hInfoNameStr" value="${fn:replace(hInfoNameStr, searchSingleQuote, replaceSingleQuote)}"/>
	      									  				
	      									  				<a onClick="cancelIti('${hInfo.testSessionId}', '${hInfo.id}', '${hInfo.studentId}', '${fn:escapeXml(hInfoNameStr)}', this)" href="javascript:void(0)">cancel assignment</a>
	      									   			</c:if>	      									
	      									  		</security:authorize>
	      										</p>	      									  
	      									</li>
	     								</c:otherwise>
									</c:choose>
							 		<li><b>Date Assigned:</b> <fmt:formatDate pattern="MM/dd/yyyy" value="${hInfo.savedDate}" /></li>
							 		<li>		
							 			<c:forEach items="${resourcePDFs}" var="testLetPDFLoc" varStatus="testIter">
							 				<c:if test="${testIter.index eq iter.index}">
							 					<c:if test="${not empty testLetPDFLoc && hInfo.statusCategory.categoryName !='Pending' && hInfo.testStatus !='Completed' && empty hInfo.administeredDate}">
							 						<div id="testLetPDF-${iter.index}" style="float:right;"> 
													<span  id="testLetPDFLabel-${iter.index}"><fmt:message key="label.iti.testLetPDF"/></span>
													<a id="testLetPDFLink-${iter.index}" href="${testLetPDFLoc}" download target="_blank"><img src='images/pdf.png' style='border:0px solid;' /> </a>
												</div>
												</c:if>
							 				</c:if>
							 			</c:forEach>			 									 			
							 		</li>
							 		<li><b>Date Confirmed:</b> <fmt:formatDate pattern="MM/dd/yyyy" value="${hInfo.confirmDate}" /></li>
							 		<li><b>Date Administered:</b> <fmt:formatDate pattern="MM/dd/yyyy" value="${hInfo.administeredDate}"/></li>
							 		<li><b>Essential Element:</b> ${hInfo.essentialElement}</li>
							 		<li><b>Level:</b> ${hInfo.linkageLevel} - ${hInfo.levelDescription}</li>
						 		</ul>
						 	</div>
						  </c:forEach>
					</div>    
					 
				</div>
					 
	</div>
</div>
<div id="printPreview" class="hidden">
	<div class='page'>
		<div class='printable'>
			<header class="page-header" id="pheader">
				<div class="printHeading"></div>
				<div class="printDatePage"><label style="float:left;"class="printeddate"></label><label style="float:right;" class="pageno"></label><br/> </div>
				<div class="studentDetailsPrint"></div>
			</header>
			<div id="content" class="page-content">
				<div id="content-main">
					<div class="planInfoPrint"></div>
				</div>
			</div>
		</div>
	</div>
</div>	

<script type="text/javascript">
$(function() {
	studentinfoDisplay();
	$("#cancelledITIPlan").html("");
	$("#setupITISavedCancel").click(function() {
		$('#cancelledITIPlan').html('');
		$('#confirmDialog').dialog("destroy");
	});
	
	$("#historyPrintAll").click(function() {
		$('#cancelledITIPlan').html('');
		var checkedPlans = $('.printAllCheck:checked').map(function() {
		    return this.value;
		}).get();
		if(checkedPlans.length > 0){
			$("#historyPrintAllErrorMessage").hide();
			printPlan(checkedPlans);
		}
		else{
			$("#historyPrintAllErrorMessage").show();
		}
 	});
	
	$(".printIndPlan").click(function() {
		$('#cancelledITIPlan').html('');
		 var planIds = [];
		 planIds.push($(this).data("planid"));
		 printPlan(planIds);
	     return false;
     });
	
	$(".checkAll").click(function() {
		$("#historyPrintAllErrorMessage").hide();
		$('#cancelledITIPlan').html('');
		  if(this.checked) { 
	            $('.printAllCheck').each(function() { 
	                this.checked = true;                
	            });
 	        }else{
	            $('.printAllCheck').each(function() { 
	                this.checked = false;                     
	            });  
 	     }
	});
	
	$('#accordion input[type="checkbox"]').click(function(e) {
		$('#cancelledITIPlan').html('');
		$("#historyPrintAllErrorMessage").hide();
		var checkedPlans = $('.printAllCheck:checked').map(function() {
		    return this.value;
		}).get();
		if(checkedPlans.length == 0){
			$('.checkAll').prop('checked', false);
		}
	    e.stopPropagation();
	});
	
	$('#accordion h3').click(function(e){
		$('#cancelledITIPlan').html('');
		$("#historyPrintAllErrorMessage").hide();
	});
	 
});

function confirmIti(tcName, itiTestSessionHistoryId){
	$('#instructionalToolsSupportDiv').dialog("destroy");
	window.sessionStorage.setItem('itiTestSessionHistoryId', itiTestSessionHistoryId);
	window.sessionStorage.setItem('testCollectionName', tcName);
	window.sessionStorage.setItem('itiEditMode', false);
	window.location.href = 'instructionalToolsSupport.htm#tabs_confirmation';
};

function cancelIti(testSessionId, itiTestSessionHistoryId, studentId, itiPlanName, linkPannel){
	$('#cancelledITIPlan').html('');
	var dialog = $('#cancelITIDialog').dialog({
		resizable: false,
		height: 200,
		width: 650,
		modal: true,
		autoOpen: true,
		title: 'Cancel',
		create: function(event, ui){
			var widget = $(this).dialog("widget");
		},
		buttons: {
			No: function(){
				$(this).dialog('close');
			},
			Yes: function() {
				var $dialog = $(this);
				$.ajax({
					url: 'cancelItiTestHistoryAndStudentTest.htm',
					dataType: 'json',
					data: {						
						itiTestSessionHistoryId: itiTestSessionHistoryId,
						testSessionId: testSessionId,
						studentId : studentId
					},
					type: "POST",
					success: function(updatedCount) {
						if(updatedCount < 1) {
							$dialog.dialog('close');
						} else {
							$("#cancelledITIPlan").html(" Instructional Plan "+ itiPlanName + " cancelled").show();
							var parent = $(linkPannel).closest('div');
							var head = parent.prev('h3');
							parent.add(head).fadeOut('slow',function(){$(this).remove();})
							$dialog.dialog('close');
							clearMessages();							
						}
					},
					error: function(jqXHR, textStatus, errorThrown) {
						$(this).dialog('close');
					}
				});
			}
		}
	});
	dialog.dialog("open");
}

function clearMessages() {	
	setTimeout("$('#cancelledITIPlan').html('')", 60000);	
}

function printPlan(planIds){
	var counter = 1;
	var planInfo="";
	var pageContent = "";
	var pageNo = 1;
	$.each(planIds, function( index, value ) {
		 if(counter ==  1){
			 $(".printHeading").html("<h3>All Instructional Plan Assignments</h3>");
			 $(".studentDetailsPrint").html($("#studentDetailsInfo").html());
			 $(".printeddate").html($.datepicker.formatDate('mm/dd/yy', new Date()));
			 $(".pageno").html("Page: " + pageNo);
			 $(".printFooter").html($('.studentDetailsPrint li').first().html());
		 }
		  var myDivElement = $("#" + value);
		  planInfo = planInfo + myDivElement.html();
		  if(counter == 4 || index ==  (planIds.length - 1)){
				 counter = 0;
				 $(".planInfoPrint").html(planInfo);
				 $(".planInfoPrint .printIndPlan").hide();
				 $(".planInfoPrint .planName").show();
				 pageContent = pageContent + $("#printPreview").html();
				 planInfo ="";
				 pageNo++;
		 }
		 counter++;
	});
	$.printPreview.loadPrintPreview({content: pageContent});
};

function studentinfoDisplay(){
	var studentInfoData = 	window.sessionStorage.getItem('selectedStudentITI');
	var studentInfo = $.parseJSON(studentInfoData); 
	studentInfo.studentFirstName =  decodeURIComponent(studentInfo.studentFirstName);
	studentInfo.studentLastName =  decodeURIComponent(studentInfo.studentLastName); 
	studentInfo.studentMiddleName =  decodeURIComponent(studentInfo.studentMiddleName); 
	var appendStudentInfo = "<ul><li>Student Name: "+ studentInfo.studentLastName + ", " +  studentInfo.studentFirstName + "</li>" +
							"<li>Student State ID: "+ studentInfo.stateStudentIdentifier +"</li>" +
							"<li>Instructor Name: "+ studentInfo.selectedEduLName + ", " + studentInfo.selectedEduFName + "</li>" +
							"<li>Roster: "+studentInfo.selectedRoster +"</li>" +
							"<li>Student Login: ${studentUId}</li>" +
							"<li>Password: ${studentPw}</li></ul>";						;
	$("#studentDetailsInfo").html('').append(appendStudentInfo);
	$( "#accordion" ).accordion({
		heightStyle: "content"
	});
}


</script>

