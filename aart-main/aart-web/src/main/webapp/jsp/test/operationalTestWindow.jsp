<!-- CSS goes in the document HEAD or added to your external stylesheet -->
<style type="text/css">
.leftmargin {
	margin-left: 20px;
}

.rightMargin {
	float: right;
	margin-right: 20px;
}

select.tcid {
	width: auto;
	height: 163px !important;
	margin-left:20px;
}

/*IE FIX */
select#tcid {
	width: 100%;
}

select:focus#tcid {
	width: auto\10;
}

.selectDiv {
	height: 255px;
	width: 100%;
}

.selectLeftCol {
	float: left;
	text-align: left;
	width: 220px;
	margin-left: 5px;
}

.selectRightCol {
	float: right;
	text-align: left;
	width: 280px;
	margin-right: 30px;
}

.bodyWrapper {
	width: 100%;
}

.bodyLeftCol {
	float: left;
	text-align: left;
	width: 200px;
	margin-left: 25px;
}

.bodyRightCol {
	float: right;
	text-align: left;
	width: 760px;
}

.note {
	font-family: "Courier";
	color: Blue;
	font-weight: bold;
}

.formSelect {
	overflow-x: scroll;
	overflow: -moz-scrollbars-horizontal;
	height: 180px;
	width: 620px;
	margin-left: 40px;
	border-right: solid 1px black;
}

.jqclock {
	float: right;
	text-align: center;
	border: 1px Black solid;
	background: #66CCFF;
	padding: 10px;
	margin: 5px;
}

.clockdate {
	color: DarkRed;
	margin-bottom: 10px;
	font-size: 16px;
	display: block;
}

.clocktime {
	border: 2px inset White;
	background: Black;
	padding: 5px;
	font-size: 20px;
	font-family: "Courier";
	color: LightGreen;
	margin: 2px;
	display: block;
}
</style>

<%@ include file="/jsp/include.jsp"%>

<h4>
	<fmt:message key="operTestWindow.page.message" />
</h4>

<div class="messages">
	<span class="error_message ui-state-error hidden" id="noTestCollection"><fmt:message
			key='error.operTestWindow.noTestCollection' /></span> <span
		class="error_message ui-state-error hidden" id="submitSuccess"><fmt:message
			key='error.operTestWindow.submit.success' /></span> <span
		class="error_message ui-state-error hidden" id="submitFailed"><fmt:message
			key='error.operTestWindow.submit.failed' /></span> <span
		class="error_message ui-state-error hidden" id="invalidDate"><fmt:message
			key='error.operTestWindow.invalidDate' /></span> <span
		class="error_message ui-state-error hidden" id="effectiveDateError"><fmt:message
			key='error.operTestWindow.effectiveDateError' /> </span> <span
		class="error_message ui-state-error hidden" id="effectiveTimeError"><fmt:message
			key='error.operTestWindow.effectiveTimeError' /> </span> <span
		class="error_message ui-state-error hidden"
		id="effectiveDatetimeGreaterThanCurrent"><fmt:message
			key='error.operTestWindow.effectiveDatetimeGreaterThanCurrent' /> </span> <br>
	<span class="error_message ui-state-error hidden"
		id="effectiveDatetimePriorToExpiry"><fmt:message
			key='error.operTestWindow.effectiveDatetimePriorToExpiry' /> </span> <span
		class="error_message ui-state-error hidden" id="expirationDateError"><fmt:message
			key='error.operTestWindow.expirationDateError' /> </span> <span
		class="error_message ui-state-error hidden" id="expirationTimeError"><fmt:message
			key='error.operTestWindow.expirationTimeError' /> </span> <span
		class="error_message ui-state-error hidden"
		id="expirationDatetimeGreaterThanEffective"><fmt:message
			key='error.operTestWindow.expirationDatetimeGreaterThanEffective' />
	</span> <span class="error_message ui-state-error hidden"
		id="expirationDatetimeGreaterThanCurrent"><fmt:message
			key='error.operTestWindow.expirationDatetimeGreaterThanCurrent' /> </span>
</div>

<div class="panel_full">

	<div class="selectDiv">
		<div class="selectLeftCol">
			<br>
			<label class="leftmargin"><fmt:message
					key="label.operTestWindow.testCollection" /> <font color="red"><fmt:message
						key="label.operTestWindow.requiredFieldIndicator" /></font> </label> <br>
			<br>
			<form:form modelAttribute="operationalTestWindow" method="post"
				enctype="multipart/form-data">
				<form:select path="testCollectionId" id="testCollectionId"
					items="${testCollectionMap}" multiple="false" size="10"
					class="tcid" />
			</form:form>
		</div>
		<div class="selectRightCol">
			<br>
			<label class="rightMargin"><fmt:message
					key="label.operTestWindow.currentDaetime" /></label><br> <label
				class="rightMargin" style="margin-right: 50px"><fmt:message
					key="label.operTestWindow.timeZone" /></label><br> <label id="clock"></label>
			<br>

		</div>
	</div>

	<div id="wrapper" class="bodyWrapper">
		<div id="bodyLeftCol" class="bodyLeftCol">
			<div id="managedByDivLabel" style="display: none">
				<label> <fmt:message key="label.operTestWindow.managedBy" /></label>
				<br>
				<br>
				<div id="randamizationDivLabel">
					<label> <fmt:message
							key="label.operTestWindow.randamization" /></label> <br>
					<br>
				</div>
			</div>
			<label> 
			<fmt:message key="label.operTestWindow.windowName" />
				<font color="red">
				<fmt:message
						key="label.operTestWindow.requiredFieldIndicator" />
				</font>
			</label> 
			<br>
			<br> 
				<label> 
					<fmt:message
						key="label.operTestWindow.ticketing" />
				</label> 
			<br>
			<br>
			
			<div id="testExitDivId">
				<br>
				<br> 
					<label> 
						<fmt:message
							key="label.operTestWindow.testExit" />
					</label> 
				<br>
				<br>
			</div>
			<br>
			<br>
			<label> 
			<fmt:message
					key="label.operTestWindow.effectiveDatetime" /> 
			<font color="red">
			<fmt:message
				key="label.operTestWindow.requiredFieldIndicator" />
			</font>
			</label> 
			<br>
			<br>
			<br>
			<br> 
			<label>
				<fmt:message
						key="label.operTestWindow.expirationDatetime" />
			</label>
			<br>
			<br>
			<br>
			<br>
			<br> 
			<label>
			<fmt:message
					key="label.operTestWindow.suspendWindow" />
			</label> 
			<br>
			<br>
			<label>
			<fmt:message
					key="label.operTestWindow.lastModifiedDatetime" />
			</label> 
			<br>
			<br> 
			<label>
			<fmt:message
					key="label.operTestWindow.windowId" />
			</label> 
			<br>
			<br>
		</div>
		<div id="bodyRightCol" class="bodyRightCol">
			<div id="managedByDiv" style="display: none">
				<span id="managedBySpan"> <input type="radio" id="managedBy"
					name="managedBy" value="Manual" disabled="disabled" />Manual <input
					type="radio" id="managedBy" name="managedBy" value="System"
					disabled="disabled" />System
				</span> <br>
				<br>

				<div id="randamizationDiv">
					<span id="randomizationSpan"> <input type="radio"
						id="randamizationLogin" name="randamization" value="Login"
						disabled="disabled" />Login <input type="radio"
						id="randamizationEnrollment" name="randamization"
						value="Enrollment" disabled="disabled" />Enrollment
					</span> <br>
					<br>
				</div>
			</div>
			<span id="windowNameSpan"> <input class="buttonStatus"
				type="text" id="windowName" size="45" maxlength="60"
				disabled="disabled" />
			</span> 
			<br>
			<br> 
			<span id="ticketingSpan"> 
				<input type="radio"
				id="ticketing1" name="ticketing" value="Off" disabled="disabled" checked="checked" onclick="showTestExit()"/>
				Off
				<input type="radio" id="ticketing2" name="ticketing" value="On" disabled="disabled" onclick="hideTestExit()"/>
				On
			</span> 
			<br>

			<div id="testExitRadioDivId">
				<br> 
				<br> 
				<span id="testExitSpan"> 
					<input type="radio"
					id="testExitId1" 
					name="testExitName" 
					value="completeTestValue" 
					disabled="disabled" 
					checked="checked" />
					Complete Test
					<input type="radio"
					 id="testExitId2"
					 name="testExitName" 
					 value="notRequiredToCompleteTestValue" 
					 disabled="disabled"/>
					 Not required to complete test
				</span> 

				<br>
				<br> 
			</div>

			<br> 
			<span id="effectiveDateSpan"> 
			<label>
				<fmt:message
						key="label.operTestWindow.Date" />
			</label>
			<input class="buttonStatus"
				type="text" id="effectiveDate" size="12" disabled="disabled" />
			</span>
			<div id="effectiveDateNote" style="visibility: hidden" class="note">
				<fmt:message key="label.operTestWindow.dateFormat" />
			</div>

			<span id="effectiveTimeSpan"> <label><fmt:message
						key="label.operTestWindow.Time" /></label><input class="buttonStatus"
				type="text" id="effectiveTime" size="10" disabled="disabled" /> <fmt:message
					key="label.operTestWindow.timeZone" />
			</span>
			<div id="effectiveTimeNote" style="visibility: hidden" class="note">
				<fmt:message key="label.operTestWindow.timeFormat" />
			</div>

			<span id="expirationDateSpan"> <label><fmt:message
						key="label.operTestWindow.Date" /></label><input type="text"
				id="expirationDate" size="12" disabled="disabled" />
			</span>
			<div id="expirationDateNote" style="visibility: hidden" class="note">
				<fmt:message key="label.operTestWindow.dateFormat" />
			</div>

			<span id="expirationTimeSpan"> <label><fmt:message
						key="label.operTestWindow.Time" /></label><input type="text"
				id="expirationTime" size="10" disabled="disabled" /> <fmt:message
					key="label.operTestWindow.timeZone" />
			</span>
			<div id="expirationTimeNote" style="visibility: hidden" class="note">
				<fmt:message key="label.operTestWindow.timeFormat" />
			</div>

			<span id="suspendWindowSpan"> <input class="buttonStatus"
				type="checkbox" id="suspendWindow" disabled="disabled" />
			</span> <br>
			<br> <label id="lastModifiedDatetime" disabled="disabled"></label>
			<br>
			<br> <label id="id"></label> <br>
			<br> <input type="hidden" id="timestmp" /> <input class = "btn_blue" type="submit"
				id="operTestWindow_submit"
				value="<fmt:message key='submit.operTestWindow.windowId' />"
				disabled="disabled">&nbsp;&nbsp;&nbsp; <input class = "btn_blue" type="submit"
				id="operTestWindow_cancel"
				value="<fmt:message key='cancel.operTestWindow.windowId'/>">
		</div>
	</div>

</div>

<script src="js/logger.localstorage.min.js" />
<script src="js/localstorage.min.js"></script>

<script type="text/javascript">
	
	$(function(){
		//$("#clock").clock();		
		$.ajax({
            url: 'getServerTime.htm',           
            dataType: 'json',
            type: "GET",
            success: function(data) {
            	if(data != null) {
            		//$("#timestmp").text(new Date(data));            		
            		$("#clock").clock({"timestamp":new Date(data)});
            	} 
            }            
		});		
	});
		
	clearMessages = function() {
		var msg = $('.error_message');
	    
	    msg.each(function(){
	        if ($(this).css("display") !== "none") {
	            $(this).fadeOut(500);
	            $(this).hide();
	        }
	    });
	};
	
	$(function() {
	    $("#effectiveDate").datepicker();
	    $("#expirationDate").datepicker();
	});
	
	$('.buttonStatus').focusout(function() {		
		changeButtonStatus();
	});
	
	$('#effectiveDate').focusin(function() {
		$("#effectiveDate").css("color","black");
		$("#effectiveDateSpan").css("color","black");
		$("#effectiveDateNote").css("visibility" , "visible");		
	});
	
	$('#effectiveTime').focusin(function() {		
		$("#effectiveTime").css("color","black");
		$("#effectiveTimeSpan").css("color","black");
		$("#effectiveTimeNote").css("visibility" , "visible");	
	});
	
	$("#effectiveDate").datepicker( {
		onClose: function () {			
			$("#effectiveDateNote").css("visibility" , "hidden");
			validateEffectiveDate(); 
		}
	});
	
	$('#effectiveTime').focusout(function() {
		$("#effectiveTimeNote").css("visibility" , "hidden");		
		validateEffectiveTime();		
	});	
			
	$('#expirationDate').focusin(function() {		
		$("#expirationDate").css("color","black");
		$("#expirationDateSpan").css("color","black");
		$("#expirationDateNote").css("visibility" , "visible");
		validateExpirationDate();
		validateExpirationTime(); 
	});	
	
	$('#expirationTime').focusin(function() {		
		$("#expirationDate").css("color","black");
		$("#expirationDateSpan").css("color","black");
		$("#expirationTime").css("color","black");
		$("#expirationTimeSpan").css("color","black");
		$("#expirationTimeNote").css("visibility" , "visible");		
	});	
	
	$("#expirationDate").datepicker( {
		onClose: function () {
			$("#expirationDateNote").css("visibility" , "hidden");			
			validateExpirationDate();			 
		}
	});
	
	$('#expirationTime').focusout(function() {
		$("#expirationTimeNote").css("visibility" , "hidden");
		validateExpirationTime();
	});
		
	
	function validateEffectiveDate() {
	
		var effectiveDatetime = $('#effectiveDate').val() + " " + $('#effectiveTime').val();
		var expirationDatetime = $('#expirationDate').val() + " " + $('#expirationTime').val();
		var id = $('#id').text();
		var invalidDate = false;
		var invalidTime = false;

		// Validate effectiveDatetime for correct values		
		if($('#effectiveDate').val() == "" && $('#effectiveTime').val() != "") {		
			invalidDate= true;			
			$("#effectiveDateError").show();								
		} if($('#effectiveDate').val() == "" && $('#effectiveTime').val() == "") {		
			invalidDate= true;			
			$("#effectiveDateError").hide();
			$("#effectiveDateError").show();										
		} else if( $('#effectiveTime').val() != "" && (new Date(effectiveDatetime)) < (new Date()) && id == "") {			
			invalidDate = true;
			invalidTime = true;			
			$("#effectiveDatetimePriorToExpiry").hide();
			$("#effectiveDatetimeGreaterThanCurrent").show();					
		} else if((new Date(effectiveDatetime) < new Date())  && id == "") {
			invalidDate= true;			
			invalidTime = true;			
			$("#effectiveDatetimePriorToExpiry").hide();
			$("#effectiveDatetimeGreaterThanCurrent").show();	
		} else if($('#expirationTime').val() != "" && (new Date(effectiveDatetime)) > (new Date(expirationDatetime))) {			
			invalidDate= true;			
			invalidTime = true;			
			$("#effectiveDatetimeGreaterThanCurrent").hide();
			$("#effectiveDatetimePriorToExpiry").show();	
		} else if(new Date(effectiveDatetime) > new Date(expirationDatetime)) {
			invalidDate= true;
			invalidTime = true;			
			$("#effectiveDatetimeGreaterThanCurrent").hide();
			$("#effectiveDatetimePriorToExpiry").show();
		}
		
		if(invalidDate) {
			if(invalidTime) {
				$("#effectiveDateError").hide();
				$("#effectiveTimeSpan").css("color","red");
				$("#effectiveTime").css("color","red");
			}
			$('body, html').animate({scrollTop:0}, 'slow');
			$("#effectiveDateSpan").css("color","red");
			$("#effectiveDate").css("color","red");	
			$('#suspendWindow').attr("disabled" , true);
			$('#operTestWindow_submit').attr("disabled" , true);			
		} else {			
			if($("#effectiveTime").val() != "") {
				$('#suspendWindow').attr("disabled" , false);
			}
			$("#effectiveDateError").hide();
			$("#effectiveTimeError").hide();
			$("#effectiveDatetimePriorToExpiry").hide();
			$("#effectiveDatetimeGreaterThanCurrent").hide();
			clearFieldFonts();
			changeButtonStatus();					
		}
		checkSuspendWindow(effectiveDatetime, expirationDatetime);
	}
	
	
	function validateEffectiveTime() {
		var effectiveDatetime = $('#effectiveDate').val() + " " + $('#effectiveTime').val();
		var expirationDatetime = $('#expirationDate').val() + " " + $('#expirationTime').val();
		var id = $('#id').text();
		var invalidTime = false;
		var invalidDate = false;
		
		// Validate effectiveDatetime for correct values
		 if($('#effectiveTime').val() == "") {
			 invalidTime= true;
			$("#effectiveDateError").hide();
			$("#effectiveTimeError").show();
		} else if (new Date(effectiveDatetime) == "Invalid Date") {
			invalidTime = true;
			invalidDate = true;			
			$("#effectiveDateError").hide();
			$("#effectiveTimeError").show();
		} else if($('#effectiveDate').val() != "" && ($('#effectiveTime').val() == "")) {		
			invalidTime= true;			
			$("#effectiveDateError").hide();
			$("#effectiveTimeError").hide();
			$("#effectiveTimeError").show();
		} else if((new Date(effectiveDatetime)) < (new Date())  && id == "") {			
			invalidTime= true;			
			invalidDate = true;
			$("#effectiveTimeError").hide();
			$("#effectiveDatetimePriorToExpiry").hide();
			$("#effectiveDatetimeGreaterThanCurrent").show();					
		} else if( (expirationDatetime != "") && (new Date(effectiveDatetime)) > (new Date(expirationDatetime))) {			
			invalidTime= true;
			invalidDate = true;
			$("#effectiveTimeError").hide();
			$("#effectiveDatetimeGreaterThanCurrent").hide();
			$("#effectiveDatetimePriorToExpiry").show();	
		}
		
		if(invalidTime) {
			if(invalidDate) {
				$("#effectiveDateSpan").css("color","red");
				$("#effectiveDate").css("color","red");
			}
			$('body, html').animate({scrollTop:0}, 'slow');
			$("#effectiveTimeSpan").css("color","red");
			$("#effectiveTime").css("color","red");
			$('#suspendWindow').attr("disabled" , true);
			$('#operTestWindow_submit').attr("disabled" , true);			
		} else {
			$('#suspendWindow').attr("disabled" , false);
			clearMessages();
			clearFieldFonts();
			changeButtonStatus();					
		}
		checkSuspendWindow(effectiveDatetime, expirationDatetime);
	}
		
	
	function validateExpirationDate() {
		var effectiveDatetime = $('#effectiveDate').val() + " " + $('#effectiveTime').val();
		var expirationDatetime = $('#expirationDate').val() + " " + $('#expirationTime').val();
		var invalidDate = false;
		var invalidTime = false;
		
		// Validate expirationDatetime for correct values 		
		if(($('#expirationDate').val() == ""  && $('#expirationTime').val() != "")) {
			invalidDate= true;
			$("#expirationTimeError").hide();
			$("#expirationDateError").show();	
		} else if($('#expirationTime').val() != "" && (new Date(expirationDatetime)) < (new Date())) { 	
			invalidDate = true;
			invalidTime = true;
			$("#expirationDateError").hide();
			$("#expirationDatetimeGreaterThanEffective").hide();
			$("#expirationDatetimeGreaterThanCurrent").show();	
		} else if((new Date($('#expirationDate').val()) < new Date())) {
			invalidDate= true;
			invalidTime = true;
			$("#expirationDateError").hide();
			$("#expirationDatetimeGreaterThanEffective").hide();
			$("#expirationDatetimeGreaterThanCurrent").show();
	    } else if($('#expirationTime').val() != "" &&  effectiveDatetime != "" && ((new Date(effectiveDatetime)) > (new Date(expirationDatetime)))) {						
			invalidDate= true;
			invalidTime = true;
			$("#expirationDateError").hide();
			$("#expirationDatetimeGreaterThanCurrent").hide();	
			$("#expirationDatetimeGreaterThanEffective").show();
		} else if(effectiveDatetime != " " && new Date(effectiveDatetime) > new Date(expirationDatetime)) {
			invalidDate= true;
			invalidTime = true;
			$("#expirationDateError").hide();
			$("#expirationDatetimeGreaterThanCurrent").hide();
			$("#expirationDatetimeGreaterThanEffective").show();
		} else if( ($('#expirationDate').val() != null && $('#expirationTime').val() == "") || (new Date(expirationDatetime) == "Invalid Date")) {
			invalidTime = true;
			$("#expirationDateError").hide();
			$("#expirationDatetimeGreaterThanCurrent").hide();
			$("#expirationDatetimeGreaterThanEffective").show();
			$('#operTestWindow_submit').attr("disabled", true);
		}
		
		if(invalidDate) {
			if(invalidTime) {
				$("#expirationTimeSpan").css("color","red");
				$("#expirationTime").css("color","red");
			}
			$('body, html').animate({scrollTop:0}, 'slow');
			$("#expirationDateSpan").css("color","red");
			$("#expirationDate").css("color","red");			
			$('#operTestWindow_submit').attr("disabled" , true);
		} else {
			if($("#effectiveTime").val() != "") {
				$('#suspendWindow').attr("disabled" , false);
			}
			$("#expirationDateSpan").css("color","black");
			$("#expirationDate").css("color","black");
			$("#expirationTimeSpan").css("color","black");
			$("#expirationTime").css("color","black");
			$("#expirationDateError").hide();
			$("#expirationDatetimeGreaterThanCurrent").hide();
			$("#expirationDatetimeGreaterThanEffective").hide();			
			changeButtonStatus();			
		}
		checkSuspendWindow(effectiveDatetime, expirationDatetime);
	}
	
	
	function validateExpirationTime() {
		var effectiveDatetime = $('#effectiveDate').val() + " " + $('#effectiveTime').val();
		var expirationDatetime = $('#expirationDate').val() + " " + $('#expirationTime').val();
		var invalidTime = false;
		var invalidDate = false;
		
		// Validate expirationDatetime for correct values		
		if((($('#expirationTime').val() == "") && $('#expirationDate').val() != "") || 
				($('#expirationTime').val() == "" && $('#expirationDate').val() != "" && (new Date(expirationDatetime) == "Invalid Date"))) {			
			invalidTime = true;
			$("#expirationDateError").hide();
			$("#expirationTimeError").show();	
		} else if((($('#expirationTime').val() != "") && $('#expirationDate').val() == "") || 
				($('#expirationTime').val() != "" && $('#expirationDate').val() == "" && (new Date(expirationDatetime) == "Invalid Date"))) {			
			invalidTime= true;
			invalidDate = true;
			$("#expirationDateError").hide();
			$("#expirationTimeError").show();	
		} else if((new Date(expirationDatetime)) < (new Date())) { 
			invalidTime = true;
			invalidDate = true;			
			$("#expirationTimeError").hide();
			$("#expirationDatetimeGreaterThanEffective").hide();
			$("#expirationDatetimeGreaterThanCurrent").show();	
		} else if((new Date(effectiveDatetime)) > (new Date(expirationDatetime))) {						
			invalidTime = true;	
			invalidDate = true;
			$("#expirationTimeError").hide();
			$("#expirationDatetimeGreaterThanCurrent").hide();	
			$("#expirationDatetimeGreaterThanEffective").show();
		}
		
		if(invalidTime) {
			if(invalidDate) {
				$("#expirationDateSpan").css("color","red");
				$("#expirationDate").css("color","red");
			}
			$('body, html').animate({scrollTop:0}, 'slow');
			$("#expirationTimeSpan").css("color","red");
			$("#expirationTime").css("color","red");
			$('#operTestWindow_submit').attr("disabled" , true);
		} else {
			if($("#effectiveTime").val() != "") {
				$('#suspendWindow').attr("disabled" , false);
			}
			$("#expirationDateError").hide();
			$("#expirationTimeError").hide();
			$("#expirationDatetimeGreaterThanCurrent").hide();
			$("#expirationDatetimeGreaterThanEffective").hide();
			clearFieldFonts();
			changeButtonStatus();			
		}
		checkSuspendWindow(effectiveDatetime, expirationDatetime);
	}
	
	
	function changeButtonStatus() {
		if($('#windowName').val() != "" && $('#effectiveDate').val() != "" && $('#effectiveTime').val() != "") {
			
			var effectiveDatetime = $('#effectiveDate').val() + " " + $('#effectiveTime').val();
			var expirationDatetime = $('#expirationDate').val() + " " + $('#expirationTime').val();
			var id = $('#id').text();
			var invalidDate = false;														
			
			if($('#effectiveDate').val() == "" && $('#effectiveTime').val() != "") {		
				invalidDate= true;			
				$("#effectiveDateError").show();								
			} if($('#effectiveDate').val() == "" && $('#effectiveTime').val() == "") {		
				invalidDate= true;			
				$("#effectiveDateError").hide();
				$("#effectiveDateError").show();										
			} else if( $('#effectiveTime').val() != "" && (new Date(effectiveDatetime)) < (new Date()) && id == "") {			
				invalidDate= true;			
				$("#effectiveTimeSpan").css("color","red");
				$("#effectiveTime").css("color","red");
				$("#effectiveDateError").hide();
				$("#effectiveDatetimePriorToExpiry").hide();
				$("#effectiveDatetimeGreaterThanCurrent").show();					
			} else if((new Date(effectiveDatetime) < new Date())  && id == "") {
				invalidDate= true;			
				$("#effectiveTimeSpan").css("color","red");
				$("#effectiveTime").css("color","red");
				$("#effectiveDateError").hide();
				$("#effectiveDatetimePriorToExpiry").hide();
				$("#effectiveDatetimeGreaterThanCurrent").show();	
			} else if($('#expirationTime').val() != "" && (new Date(effectiveDatetime)) > (new Date(expirationDatetime))) {			
				invalidDate= true;			
				$("#effectiveTimeSpan").css("color","red");
				$("#effectiveTime").css("color","red");
				$("#effectiveDateError").hide();
				$("#effectiveDatetimeGreaterThanCurrent").hide();
				$("#effectiveDatetimePriorToExpiry").show();	
			} else if(new Date(effectiveDatetime) > new Date(expirationDatetime)) {
				invalidDate= true;
				$("#effectiveTimeSpan").css("color","red");
				$("#effectiveTime").css("color","red");
				$("#effectiveDateError").hide();
				$("#effectiveDatetimeGreaterThanCurrent").hide();
				$("#effectiveDatetimePriorToExpiry").show();
			}
			
			if(invalidDate) {		
				$('body, html').animate({scrollTop:0}, 'slow');
				$("#effectiveDateSpan").css("color","red");
				$("#effectiveDate").css("color","red");			
				$('#operTestWindow_submit').attr("disabled" , true);			
			} else {	
				clearMessages();
				clearFieldFonts();
				$('#operTestWindow_submit').attr("disabled" , false);					
			}			
			
		} else {
			$('#operTestWindow_submit').attr("disabled" , true);
		}
	}
		
	
	// Resets fields to default values (empty)
	function clearPageData() {
		clearMessages();
		
		$('#testCollectionId option').removeAttr('selected');
		$('#windowName').val("");            	            	
    	$('#effectiveDate').val("");
    	$('#effectiveTime').val("");
    	$('#expirationDate').val("");   
    	$('#expirationTime').val("");
    	$("#suspendWindow").removeAttr('checked');
    	$('#lastModifiedDatetime').text("");
    	$('#id').text(""); 
    	$('#operTestWindow_submit').attr("disabled" , true);    	
    	$('#managedByDivLabel').hide();
		$('#managedByDiv').hide();
		$('#ticketing1').attr("checked", "checked");
		$('#ticketing2').removeAttr("checked");
		$('#testExitId1').attr("checked", "checked");
		$('#testExitId2').removeAttr("checked");
	}
	
	
	// Resets field fonts to default values (black from red)	
	function clearFieldFonts() { 
    	$("#effectiveDateSpan").css("color","black");
    	$("#effectiveTimeSpan").css("color","black");
    	$("#effectiveDate").css("color","black");
    	$("#effectiveTime").css("color","black");
		$("#expirationDateSpan").css("color","black");
		$("#expirationTimeSpan").css("color","black");
		$("#expirationDate").css("color","black");
		$("#expirationTime").css("color","black");
	}
	
	
	$('#testCollectionId').change(function() {	
		var testCollectionId = $('#testCollectionId').val().split("#")[1];

		// Enable the textboxes once the test collection is selected,
		//so that the user can enter data.
		controlsStateChange(false);
		
		//Hide the Randomization div for each testcollection selection.
		$('#randamizationDivLabel').hide();
		$('#randamizationDiv').hide();
		
		clearMessages();            		
		clearFieldFonts();
		
		$.ajax({
            url: 'getExistingTestCollectionData.htm',
            data: {
            	testCollectionId: testCollectionId               
            },
            dataType: 'json',
            type: "POST",
            success: function(data) {
            	if(data == undefined || data == null || data.length <= 0) {
            		clearPageData();
            	} else {            		
            		$('#windowName').val(data.windowName);
            		
            		$('#managedByDivLabel').show();
            		$('#managedByDiv').show();
            		
            		//Check the radiobutton System/Manual based on the data.manaedByFlag value
            		$('input[name=managedBy]').each(function(){
            			//alert(data.managedBySystemFlag);            			
            		      if ($(this).val() == "System" && data.managedBySystemFlag) {
            		            $(this).prop("checked", true);
              		            $('#randamizationDivLabel').show();
            		            $('#randamizationDiv').show();
            		            if(data.radomizationAtLoginFlag) {
            		            	 $('#randamizationLogin').prop("checked", true);
            		            }else {
            		            	$('#randamizationEnrollment').prop("checked", true);            		            	
            		            }            		    	  
            		      } else if ($(this).val() == "Manual" && !data.managedBySystemFlag) {
            		    	  $(this).prop("checked", true);
            		      }
            		});              		
 
            		//Check the radiobutton On/Off based on the data.ticketingFlag value
            		$('input[name=ticketing]').each(function(){
            		      if ($(this).val() == "On" && data.ticketingFlag) {
            		            $(this).prop("checked", true);
            		      } else if ($(this).val() == "Off" && !data.ticketingFlag) {
            		    	  $(this).prop("checked", true);
            		      }
            		});
            		if(data.ticketingFlag) {
            			hideTestExit();
            			$('#testExitId1').attr("checked", "checked");
            		} else {
            			showTestExit();
            			//alert('data.requiredToCompleteTest'+data.requiredToCompleteTest);            			
            			$('input[name=testExitName]').each(function(){
                		      if ($(this).val() == "completeTestValue" && data.requiredToCompleteTest) {
                		            $(this).prop("checked", true);
                		      } else if ($(this).val() == "completeTestValue"
                		    		  && data.id == null) {
                		    	  $(this).prop("checked", true);
                		      } else if ($(this).val() == "notRequiredToCompleteTestValue"
                		    		  && !data.requiredToCompleteTest && data.id != null) {
                		    	  $(this).prop("checked", true);
                		      }
                			});
                	}
            		//TODO
            		//
            		// Upon success response, populate fields with DB values         		
            		if(data.effectiveDate == null) {
            			$('#effectiveDate').val("");
	            		$('#effectiveTime').val("");	            		
            		} else {                 			
	            		$('#effectiveDate').val(formatDate(new Date(data.effectiveDate)));
	            		$('#effectiveTime').val(formatTime(new Date(data.effectiveDate)));	            	
            		}
            		
            		if(data.expiryDate == null) {
            			$('#expirationDate').val("");   
            			$('#expirationTime').val("");
            		} else {
            			$('#expirationDate').val(formatDate(new Date(data.expiryDate)));   
            			$('#expirationTime').val(formatTime(new Date(data.expiryDate)));
            		}            		
            		            		
            		var effectiveDatetime = $('#effectiveDate').val() + " " + $('#effectiveTime').val();
            		var expirationDatetime = $('#expirationDate').val() + " " + $('#expirationTime').val();            		            	
            		
            		$("#suspendWindow").attr('checked', data.suspendWindow);
            		checkSuspendWindow(effectiveDatetime, expirationDatetime); 
            		
            		if(data.modifiedDate == null) {
            			$('#lastModifiedDatetime').text("");
            		} else {
	            		$('#lastModifiedDatetime').text(formatDate(new Date(data.modifiedDate)) + " " + formatTime(new Date(data.modifiedDate)));
            		}
	            	
	            	if(data.id == null) {
	            		$('#id').text("");
	            		$('#operTestWindow_submit').attr("disabled" , true);
	            	} else {
	            		$('#id').text(data.id);
	            		$('#operTestWindow_submit').attr("disabled" , false);
	            	}
            	}
            }            
		});
	});
		
	
	// Enable/disable the UI controls.
	function controlsStateChange(status) {
		$('#windowName').attr("disabled" , status);
		$('#effectiveDate').attr("disabled" , status);
		$('#effectiveTime').attr("disabled" , status);
		$('#expirationDate').attr("disabled" , status);
		$('#expirationTime').attr("disabled" , status);		
		$('#suspendWindow').attr("disabled" , status);
		$('#ticketing1').prop("checked",status);
		$('#ticketing1').attr("disabled", status);
		$('#ticketing2').attr("disabled", status);
		$('#testExitId1').prop("checked",status);
		$('#testExitId1').attr("disabled", status);
		$('#testExitId2').attr("disabled", status);
	}
	
	function showTestExit() {
		//alert('Showing test exit flags');
		$('#testExitId1').removeAttr("disabled");
		$('#testExitId2').removeAttr("disabled");	
	}
	
	function hideTestExit() {
		//alert('Hiding test exit flags');
		$('#testExitId1').attr("checked", "checked");
		$('#testExitId1').attr("disabled", "disabled");
		$('#testExitId2').attr("disabled", "disabled");		
	}
	
	function checkSuspendWindow(effectiveDatetime, expirationDatetime) {
		if(effectiveDatetime == null) {
			$('#suspendWindow').attr("disabled" , true);
		} else if((effectiveDatetime != null && ((new Date(effectiveDatetime)) > (new Date())))) {        				
			$('#suspendWindow').attr("disabled" , false);
		} else if(effectiveDatetime != null && ((new Date(effectiveDatetime)) < (new Date())) && 
				(expirationDatetime == null ) || (expirationDatetime == " ")) {        				
			$('#suspendWindow').attr("disabled" , false);
		} else if(effectiveDatetime != null && ((new Date(effectiveDatetime)) < (new Date())) && 
				((new Date(expirationDatetime)) > (new Date()))) {        				
			$('#suspendWindow').attr("disabled" , false);
		} else if(effectiveDatetime != null) {        				
			$("#suspendWindow").attr("disabled", "disabled");
		}
	}
	
	
	$('#operTestWindow_submit').click(function() {
		
		// Get the field values to pass in json request upon submit
		var windowName = $('#windowName').val();
		var ticketingFlag = $("#ticketing2").is(":checked");
		var requiredToCompleteTest = $("#testExitId1").is(":checked");
		//alert('requiredToCompleteTest'+requiredToCompleteTest);
    	var effectiveDatetime = $('#effectiveDate').val() + " " + $('#effectiveTime').val();
    	var expirationDatetime = $('#expirationDate').val() + " " + $('#expirationTime').val();
    	var suspendWindow = $("#suspendWindow").is(":checked");
    	var lastModifiedDatetime = $('#lastModifiedDatetime').val()
    	var id = $('#id').text();
    	var error;
    	
    	// Validate for invalid date if any
    	var dateTimeYear = (new Date($('#effectiveDate').val())).getFullYear();
	   	if(dateTimeYear < 1600 || dateTimeYear > 2400) {	   		
	   		error= "error";
		}
	   	dateTimeYear = (new Date($('#expirationDate').val())).getFullYear();
	   	if(dateTimeYear < 1600 || dateTimeYear > 2400) {		
	   		error= "error";
		}    	    		   	
   			   	
    	if(error != "error") {
	    	if($('#testCollectionId').val() != null && $('#testCollectionId').val().length > 0) {
	    		var testCollectionId = $('#testCollectionId').val().split("#")[1];    			    			    	
				 $.ajax({
		            url: 'addOperTestWindowContent.htm',
		            data: {
		            	testCollectionId: testCollectionId,
		            	id: id,
		            	windowName: windowName,
		            	ticketingFlag: ticketingFlag,
		            	requiredToCompleteTest: requiredToCompleteTest,
		            	effectiveDatetime: effectiveDatetime,
		            	expirationDatetime: expirationDatetime,
		            	suspendWindow: suspendWindow, 
		            	lastModifiedDatetime: lastModifiedDatetime               
		            },
		            dataType: 'json',
		            type: "POST",
		            success: function(data) {		            			            
		            	if(data == "1") {
		            		clearPageData();
		            		controlsStateChange(true);
		            		$('body, html').animate({scrollTop:0}, 'slow');
		            		$("#submitSuccess").show();
		            		setTimeout("aart.clearMessages()", 3000);
		            	}  else {
			            		$('body, html').animate({scrollTop:0}, 'slow');
			            		$("#submitFailed").show();
			            		setTimeout("aart.clearMessages()", 3000);
			            }
		            },
		          error: function(response) {		        	  		        	  		        	 
		        	  	$("#invalidDate").text(response.responseText);
	            		$('body, html').animate({scrollTop:0}, 'slow');
	            		$("#invalidDate").show();	            		
	            		setTimeout("aart.clearMessages()", 3000);	            	
		          }  
				}); 
	    	} else {
	    		$('body, html').animate({scrollTop:0}, 'slow');
	    		$("#noTestCollection").show();
	    		setTimeout("aart.clearMessages()", 3000);
	    	}
		} else {
			$('body, html').animate({scrollTop:0}, 'slow');
	   		$("#invalidDate").show();
	   		setTimeout("aart.clearMessages()", 3000);
		}
	});
	

	$('#operTestWindow_cancel').click(function() {
		clearPageData();
		controlsStateChange(true);
	});	
	
	
	// Formats the date in required format.
	function formatDate(dateTime) {
		var month = dateTime.getMonth() + 1;	
		var date = dateTime.getDate();
		if(month < 10) {			 
			month = "0" + month;
		 }
		if(date < 10) {			 
			date = "0" + date;
		 }
		return(month + "/" +
				date + "/" + 
			dateTime.getFullYear());				
	}
	
	
	// Formats the time in required format.	
	function formatTime(dateTime) {
		 var hours = dateTime.getHours();
		 var min = dateTime.getMinutes();
		 var sec = dateTime.getSeconds();
		 var period = " AM";
		 
		 /**
		 * Biyatpragyan Mohanty (bmohanty_sta@ku.edu) : DE3605 : Operational Test Window doesn't set time 12:00:00 PM
		 * There was problem in time formatting, fixed as below.
		 */
		 if(hours >= 12) {		
			 if(hours != 12){
				 hours = hours - 12;				 
			 }
			 period = " PM";
		 }
		 if(hours < 10) {			 
			 hours = "0" + hours;
		 }
		 if(hours == 00) {
			hours ="12";	 
		 }
		 
		 if(min < 10) {			 
			 min = "0" + min;
		 }
		 if(sec < 10) {			 
			 sec = "0" + sec;
		 }		 
		 
		return(hours + ":" +
				min + ":" +
				sec + period);		
	}
	
	/* Sets time in clock label and calls itself every second */	
	(function($){
		$.clock={version:"2.0.1",locale:{}};
		t=[];
		$.fn.clock = function(d){
			var c={		
				en:{weekdays:["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"],
				months:["January","February","March","April","May","June","July","August","September","October","November","December"]}};
			return this.each(function(){$.extend(c,$.clock.locale);d=d||{};d.timestamp=d.timestamp||"z";y=new Date().getTime();d.sysdiff=0;if(d.timestamp!="z"){d.sysdiff=d.timestamp-y}d.langSet=d.langSet||"en";d.format=d.format||((d.langSet!="en")?"24":"12");d.calendar=d.calendar||"true";if(!$(this).hasClass("jqclock")){$(this).addClass("jqclock");}var e=function(g){if(g<10){g="0"+g}return g;},f=function(j,n){var r=$(j).attr("id");if(n=="destroy"){clearTimeout(t[r]);}else{m=new Date(new Date().getTime()+n.sysdiff);var p=m.getHours(),l=m.getMinutes(),v=m.getSeconds(),u=m.getDay(),i=m.getDate(),k=m.getMonth(),q=m.getFullYear(),o="",z="",w=n.langSet;if(n.format=="12"){o=" AM";if(p>11){o=" PM"}if(p>12){p=p-12}if(p===0){p=12}}p=e(p);l=e(l);v=e(v);if(n.calendar!="false"){z=((w=="en")?"<span class='clockdate'>"+c[w].weekdays[u]+", "+c[w].months[k]+" "+i+", "+q+"</span>":"<span class='clockdate'>"+c[w].weekdays[u]+", "+i+" "+c[w].months[k]+" "+q+"</span>");}$(j).html(z+"<span class='clocktime'>"+p+":"+l+":"+v+o+"</span>");t[r]=setTimeout(function(){f($(j),n)},1000);}};f($(this),d);});};return this;
	})(jQuery);

	
</script>