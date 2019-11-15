<%@ include file="/jsp/include.jsp" %>
<div class="clear">
<div class="reportContainer">
<div class="reportRow"><div class="reportCell"> 
<div id="performanceDiv" class="filterContainer">

<div id="assessProgLabel" class="filterRow"><div class="filterCell filterLabel"><fmt:message key="label.reports.assessmentProgram"/></div></div>
<div id="assessProgError" class="filterRow filterError"><div class="filterCell"><fmt:message key="error.required"/></div></div>
<div id="assessmentProgram" class="filterRow"><div class="filterCell"><select id="assessProgSelect"></select>   </div></div>

<div id="reportCatLabel" class="filterRow"><div class="filterCell filterLabel"><fmt:message key="label.reports.reportCategory"/></div></div>
<div id="reportCatError" class="filterRow filterError"><div class="filterCell"><fmt:message key="error.required"/></div></div>
<div id="reportCategory" class="filterRow"><div class="filterCell"><select id="reportCatSelect"></select>   </div></div>

<div id="assessTypeLabel" class="filterRow"><div class="filterCell filterLabel"><fmt:message key="label.reports.assessmentType"/></div></div>
<div id="assessType" class="filterRow"><div class="filterCell"><select id="assessTypeSelect"></select>   </div></div>

<div id="contentAreaLabel" class="filterRow"><div class="filterCell filterLabel"><fmt:message key="label.reports.contentArea"/></div></div>
<div id="contentArea" class="filterRow"><div class="filterCell"><select id="contentAreaSelect"></select>   </div></div>

<div id="stateLabel" class="filterRow"><div class="filterCell filterLabel"><fmt:message key="label.reports.state"/></div></div>
<div id="state" class="filterRow"><div class="filterCell"><select id="stateSelect"></select>   </div></div>

<div id="districtLabel" class="filterRow"><div class="filterCell filterLabel"><fmt:message key="label.reports.district"/></div></div>
<div id="district" class="filterRow"><div class="filterCell"><select id="districtSelect"></select>   </div></div>

<div id="schoolLabel" class="filterRow"><div class="filterCell filterLabel"><fmt:message key="label.reports.school"/></div></div>
<div id="school" class="filterRow"><div class="filterCell"><select id="schoolSelect"></select>   </div></div>

<div id="rosterLabel" class="filterRow"><div class="filterCell filterLabel"><fmt:message key="label.reports.roster"/></div></div>
<div id="roster" class="filterRow"><div class="filterCell"><select id="rosterSelect"></select>   </div></div>

<div id="gradeLabel" class="filterRow"><div class="filterCell filterLabel"><fmt:message key="label.reports.grade"/></div></div>
<div id="grade" class="filterRow"><div class="filterCell"><select id="gradeSelect"></select>   </div></div>

<div id="studentLabel" class="filterRow"><div class="filterCell filterLabel"><fmt:message key="label.reports.student"/></div></div>
<div id="student" class="filterRow"><div class="filterCell"><select id="studentSelect"></select>   </div></div>


<div id="reportBtn" class="filterRow"><div class="filterCell"><button id="reportBtn">View Report</button></div></div>
</div>
</div>
<div class="reportCell"> 
<div id="reportView"><h1>TODO Place Report to View Here</h1></div>
</div></div>
</div>
</div>

<script>
$().ready(function() {
	alert("gg");
	$('.filterError').hide();
	$('#assessProgSelect').append(
        $('<option></option>').val(-1).html("Select"))
	 	.append($('<option></option>').val(0).html("Dummy Value 0"));
	$('#reportCatSelect').append(
        $('<option></option>').val(-1).html("Select"));
	$('#reportCatSelect').hide();
	$('#reportCatLabel').hide();
	$('#assessTypeSelect').append(
        $('<option></option>').val(-1).html("Select"));
	$('#assessTypeSelect').hide();
	$('#assessTypeLabel').hide();
	$('#stateSelect').append(
        $('<option></option>').val(-1).html("Select"));
	$('#stateSelect').hide();
	$('#stateLabel').hide();
	$('#districtSelect').append(
        $('<option></option>').val(-1).html("Select"));
	$('#districtSelect').hide();
	$('#districtLabel').hide();
	$('#schoolSelect').append(
        $('<option></option>').val(-1).html("Select"));
	$('#schoolLabel').hide();
	$('#schoolSelect').hide();
	$('#rosterSelect').append(
        $('<option></option>').val(-1).html("Select"));
	$('#rosterSelect').hide();
	$('#rosterLabel').hide();
	$('#contentAreaSelect').append(
        $('<option></option>').val(-1).html("Select"));
	$('#contentAreaSelect').hide();
	$('#contentAreaLabel').hide();
	$('#gradeSelect').append(
        $('<option></option>').val(-1).html("Select"));
	$('#gradeSelect').hide();
	$('#gradeLabel').hide();
	
	$('#assessProgSelect').change(function() {
		$('#reportCatSelect').append(
		        $('<option></option>').val(0).html("Dummy Value 0"));
		$('#reportCatSelect').show();
 		$('#reportCatLabel').show();
		
	});
	
	$('#reportBtn').click(function() {
		if(validateSelect()) {
			$('.filterError').hide();
			$('#reportView').html("<h1>A Dummy Report</h1>");
		}
	});
	
	function validateSelect() {
		var valid = true;
		if($('#assessProgSelect').val()=='-1') {
			valid=false;
			$('#assessProgError').show();
		} else {
			$('#assessProgError').hide();
		}
		if($('#reportCatSelect').is(':visible') && $('#reportCatSelect').val()=='-1') {
			valid=false;
			$('#reportCatError').show();
		} else {
			$('#reportCatError').hide();
		}
		return valid;
	}
});
</script> 