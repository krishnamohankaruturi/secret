<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<style>
#saveButton{
font-size: .8em !important;
margin-top:9px !important;
	  background: #0e76bc url(../images/btn-bg.png) no-repeat center center;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	-ms-border-radius: 4px;
	-o-border-radius: 4px;
	border-radius: 4px;
	border: 0;
	display: inline-block;
	margin: 0 0 20px 0;
	padding:5px 10px;
	color: white !important;
	text-decoration: none !important;
	font-size: 1em;
	font-weight: 300;
	line-height: 20px;
	-webkit-transition: all .3s ease-in-out;
	-moz-transition: all .3s ease-in-out;
	-o-transition: all .3s ease-in-out;
	transition: all .3s ease-in-out;
	margin: 10px;
}
.ui-widget-header{
font-weight:normal !important;
}

</style>
<div id="ITITabContent" class="form iti-config">
<c:if test="${hasQCPermission}">
	<div><div class="form-fields">
		<security:authorize access="hasRole('ITI_OVERRIDE_SYS_REC_LEVEL')">
		<label for="recommendedLevelLabel" class="field-label">Override System-Recommended Level:</label>
		<div class="slider-frame">
			<c:if test="${ITIRecommendedLevel}">
				<span class="slider-button slider-button-recommendedLevel"><font color="white">ON</font></span>
			</c:if>
			<c:if test="${!ITIRecommendedLevel}">
				 <span class="slider-button on slider-button-recommendedLevel">OFF</span>
			</c:if>
		</div>
		</security:authorize>
	</div></div>
	<div><div id="itiByStateDiv" class="form-fields">
		<label for="ITIpermissionLabel" class="field-label">Enable Instruction and Assessment Planner By State:</label>
		<div id="ITIbyStatePopup">
			<form>
				<div style="float:left;padding-right:20px;">
		  			Start Date:
		  			<input type="text" title="Start Date" id="startDate">
		  		</div>
		  		
		  		<div>
		  			End Date:
		  			<input type="text" title="End Date" id="endDate">
		  		</div>
		  		
		  		<button id="saveButton" class="btn_blue">SAVE</button>
		  		<div id="popupErrorMsg"></div>
			</form>
		
		</div>
		
		
		<div>
			<table id="iti-states-table" class="gridtable">
				<thead>
					<tr>
						<td><input type="checkbox" title="Select all" id="selectAll" style="float:left;">State</td>
						<td style="width:145px;">Testing Cycle</td>
						<td style="width:125px;">Current Status</td>
						<td>Schedule Begin Date</td>
						<td>Schedule End Date</td>
					</tr>
				</thead>
				<tbody>
					
				</tbody>
			</table>
			<button id="editWindow" class="btn_blue">Edit Selected</button>
		</div>
	<div id="errorMsg"></div>
	</div></div>
	<div><div class="form-fields"><label>&nbsp;</label></div></div>
	<div><div class="form-fields"><label>&nbsp;</label></div></div>
</c:if>
</div>
<script type="text/javascript">
var ITIRecommendedLevel = '${ITIRecommendedLevel}';
var ITIStatus = '${ITIStatus}';
var itiFlag = true;
var recflag = true;
var currentStartDate = "";
$(function () {
		$('.slider-button-recommendedLevel').on("click", function(){
			var iteration = $(this).data("iteration") || 1;
			switch(iteration) {
				case 1:
					if(ITIRecommendedLevel == "true"){
						$(this).addClass('on').html('OFF');
						changeStatus("ITI_RECOMMENDED_LEVEL","OFF");
						$(this).removeClass('slider-color');
					}else{
						 $(this).removeClass('on').html('ON');
							$(this).addClass('slider-color');
							changeStatus("ITI_RECOMMENDED_LEVEL","ON");
							recflag = false;
					}
					break;
				case 2:
					if(!recflag){
						$(this).addClass('on').html('OFF');
						changeStatus("ITI_RECOMMENDED_LEVEL","OFF");
						$(this).removeClass('slider-color');
					}else{
						 $(this).removeClass('on').html('ON');
							$(this).addClass('slider-color');
							changeStatus("ITI_RECOMMENDED_LEVEL","ON");
					}
					break;
			}
			iteration++;
			if (iteration>2) iteration=1;
			$(this).data('iteration',iteration);
		});

		$('#itiByStateDiv').hide();

		loadITITable();
		
		$('#selectAll').click(function(e){
			var table= $(e.target).closest('table');
			$('td input:checkbox',table).prop('checked',this.checked);
		});

		$('#saveButton').on("click", function(e){
			e.preventDefault();

			$('#popupErrorMsg').html('');
			$('#popupErrorMsg').hide();
			var accept = true;
			if($('#endDate').val().trim() == ''){
				$('#popupErrorMsg').text($('#popupErrorMsg').text() + "End Date Can't be empty.  ");
				$('#popupErrorMsg').show();
				accept = false;
			}

			var startParts = $('#startDate').val().split("/");
			var endParts = $('#endDate').val().split("/");
			if(startParts.length == 3){
				var startMonth = ("0" + startParts[0]).slice(-2);
				var startDay = ("0" + startParts[1]).slice(-2);
				var startYear = startParts[2];
				var d = new Date(startMonth + "/"  + startDay + "/"  + startYear);
				startYear = d.getFullYear();
				$('#startDate').val(startMonth + "/"  + startDay + "/"  + startYear);
			}else if($('#startDate').val().trim() != ""){	
				$('#popupErrorMsg').text($('#popupErrorMsg').text() + "Invalid Start Date format.  ");
				$('#popupErrorMsg').show();
				$('#startDate').val("");
				accept = false;
			}
			if(endParts.length == 3){
				var endMonth = ("0" + endParts[0]).slice(-2);
				var endDay = ("0" + endParts[1]).slice(-2);
				var endYear = endParts[2];
				var d = new Date(endMonth + "/"  + endDay + "/"  + endYear);
				endYear = d.getFullYear();
				$('#endDate').val(endMonth + "/"  + endDay + "/"  + endYear);
			}else if(accept){
				$('#popupErrorMsg').text($('#popupErrorMsg').text() + "Invalid End Date format.  ");
				$('#popupErrorMsg').show();
				$('#endDate').val("");
				accept = false;
			}
			if(accept){
				if($('#startDate').val().trim() != ""){
					var startDateDate = new Date($('#startDate').val().trim());
					if(isNaN(startDateDate.getTime())){
						$('#popupErrorMsg').text($('#popupErrorMsg').text() + "Invalid Start Date format.  ");
						$('#popupErrorMsg').show();
						$('#startDate').val("");
						accept = false;
					}
				}
				var endDateDate = new Date($('#endDate').val().trim());
				if(isNaN(endDateDate.getTime())){
					$('#popupErrorMsg').text($('#popupErrorMsg').text() + "Invalid End Date format.  ");
					$('#popupErrorMsg').show();
					$('#endDate').val("");
					accept = false;
				}
				var today = new Date();
				today.setHours(0,0,0,0);
			}

			var newestDate;
			var hasNoStart = false;
			$("#iti-states-table tbody input[type=checkbox]:checked").each(function(){
					var $this = $(this);
					if($this.closest("tr").find("td:nth-child(3)").text().trim() == ""){
						hasNoStart = true;
					}
					if(newestDate == undefined && $this.closest("tr").find("td:nth-child(3)").text().trim() != ""){
						newestDate = new Date($this.closest("tr").find("td:nth-child(3)").text());
					}
					var d = new Date($this.closest("tr").find("td:nth-child(3)").text());
					if(d > newestDate){
						newestDate = d;
					}
			   });

			if(hasNoStart && endDateDate <= today && accept){
				$('#popupErrorMsg').text($('#popupErrorMsg').text() + "Start Date must be before End Date.  ");
				$('#popupErrorMsg').show();
				accept = false;
			}
			
			if($('#startDate').val().trim() != "" && startDateDate >= endDateDate && accept){
				$('#popupErrorMsg').text($('#popupErrorMsg').text() + "Start Date must be before End Date.  ");
				$('#popupErrorMsg').show();
				accept = false;
			}
			if(newestDate >= endDateDate && accept){
				$('#popupErrorMsg').text($('#popupErrorMsg').text() + "Start Date must be before End Date.  ");
				$('#popupErrorMsg').show();
				accept = false;
			}
			/*if($('#startDate').val().trim() != "" && !(startDateDate >= today) && $('#startDate').val().trim() != currentStartDate.trim() && accept){
				$('#popupErrorMsg').text($('#popupErrorMsg').text() + "Start Date can't be in the past.  ");
				$('#popupErrorMsg').show();
				accept = false;
			}*/
			if(!(endDateDate >= today) && accept){
				$('#popupErrorMsg').text($('#popupErrorMsg').text() + "End Date can't be in the past.  ");
				$('#popupErrorMsg').show();
				accept = false;
			}
			if(accept == true){

				var orgIds = new Array();
				var item = {
					startDate: $('#startDate').val().trim(),
					endDate: $('#endDate').val().trim(),
					orgIds: []
				};

				if(item.startDate == ""){
					delete item.startDate;
				}
				
					$("#iti-states-table tbody input[type=checkbox]:checked").each(function(){
						var $this = $(this);
						orgIds.push(parseInt($this.attr("id"),10));
						item.orgIds = orgIds.join(',');
				   });
				
				   $.ajax({
					   url: 'updateITIWindow.htm',
						data: item,
						dataType: 'text',
						type: "POST"
					}).done(function (data) { 
						loadITITable(); 
					});
				$('#popupErrorMsg').hide();
				$('#ITIbyStatePopup').dialog('close');
			}
		});

		
		$('#ITIbyStatePopup').dialog({
			autoOpen: false,
			modal: true,
			resizable: false,
			width: 650,
			height: 150,
			title: "ITI Testing Available",
			create: function(event, ui) { 
				var widget = $(this).dialog("widget");
				$("#startDate").datepicker();
				$("#endDate").datepicker();
				
			},
			open : function(ev, ui) {
				
			},
			close: function(ev, ui) {				
		
			}
		});
		
		$('#editWindow').on("click", function(e){
			$("#errorMsg").text("");
			if ($("#iti-states-table > tbody input:checkbox:checked").length <= 0){
				$("#errorMsg").text("You must select at least one state.");
			}else{
				if($("#iti-states-table > tbody input:checkbox:checked").length == 1){
					currentStartDate = $("#iti-states-table > tbody input:checkbox:checked").closest("tr").find("td:nth-child(4)").text();
					$("#startDate").val(currentStartDate);
					$("#endDate").val($("#iti-states-table > tbody input:checkbox:checked").closest("tr").find("td:nth-child(5)").text());
				}else{
					$("#startDate").val("");
					$("#endDate").val("");
				}
				$('#popupErrorMsg').text("");
				$("#startDate").datepicker("disable");
				$("#endDate").datepicker("disable");
				$('#ITIbyStatePopup').dialog('open');
				$("#startDate").datepicker("enable");
				$("#endDate").datepicker("enable");
			}
		});
});

function loadITITable(){
	$("#iti-states-table tbody > tr").remove();
	  $.ajax({
		 	url: 'getITIStates.htm',
			data: { },
			dataType: 'JSON',
			type: "GET"
		}).done(function (data) { 
			if(data.records != 0){
				data = data.rows;
				for(var i = 0; i < data.length; i++){
					var id, org, status, begin, end;
					id = data[i].cell[0];
					org = data[i].cell[2];
					var cycle = data[i].cell[10];
		
					if(data[i].cell[3] == "Not Available"){
						begin = "";
					}else{
						begin = dateToString(data[i].cell[3]);
					}
					if(data[i].cell[4] == "Not Available"){
						end = "";
					}else{
						end = dateToString(data[i].cell[4]);
					}
					status = "Off";

					var today = new Date();
					var windowstart = new Date(begin);
					var windowend = new Date(end);
					windowend.setDate(windowend.getDate() + 1);
					if(windowstart <= today && windowend > today){
					 status = "On";
					}
					$('#iti-states-table > tbody:last-child').append('<tr><td><input type="checkbox" title="'+org+'" style="float:left;" id = "' + id + '">' + org + '</td><td>' + cycle + '</td><td>' + status + ' </td><td>' + begin + ' </td><td>' + end + ' </td></tr>')
				}
				$("#iti-states-table td").css("text-align", "Center");
				$('#itiByStateDiv').show();
			}				
		});
}

 function dateToString(date){
	var d = new Date(date);
	var month = (1 + d.getMonth()).toString();
	month = month.length > 1 ? month : '0' + month;
	var day = d.getDate().toString();
	day = day.length > 1 ? day : '0' + day;
	return month + '/' + day + '/' +  d.getFullYear();
} 

function changeStatus(categoryCode,enableFlag){
  $.ajax({
	  	url: 'saveITIconfiguration.htm',
		data: {
			categoryCode: categoryCode,
			enableFlag: enableFlag
		},
		dataType: 'json',
		type: "POST"
	}).done(function (data) { });
}


</script>