<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<style>
.testsession {
    float: left;
    margin: 0px 5px 0px 5px;
    border-right: 1px solid black;
    padding: 5px;
}

#rosterReportingOrgFilter .header :not(.previewContent) {
   float: none;
}


#rosterReportingOrgFilter span.select2.select2-container.select2-container--default {
	width: 300px !important;
}

#rosterReportingOrgFilter label.field-label span.lbl-required {
	float: none;
}


#rosterReportingOrgFilter .select2-container--default .select2-selection--single .select2-selection__arrow b {
    left: 100px;
    position: absolute;
    top: 50%;
}


#rosterReportingOrgFilterForm .btn-bar {
	width: 20%;
}



</style>
<div class="pageContent _bcg">
    <div class="messages">
        <span id="no_ended_sessions" class="ui-state-error error_message hidden"><fmt:message key='report.no.ended.sessions'/></span>
        <span id="no_responses" class="ui-state-error error_message hidden"><fmt:message key='report.generate.error'/></span>
        <span id="no_ended_students" class="ui-state-error error_message hidden"><fmt:message key='report.no.ended.students'/></span>
        <span id="no_ended_enrolled_students" class="ui-state-error error_message hidden"><fmt:message key='report.no.ended.enrolled.students'/></span>
    </div>
	<div class="header">
	   <label id="title"><fmt:message key='roster.reporting'/></label>
		<div class="panel_full">
			<form id="rosterReportingOrgFilterForm" class="form">
				<div id="rosterReportingOrgFilter"></div>
				<div class="btn-bar"><a class="panel_btn" href="#" id="btnRosterReports">Get Reports</a></div>
			</form>
		</div>
	</div>
	<br/>
	<hr>
	<br/>
	<div>
	    <div id="accordionParent">
	        <div id="accordion">
	        </div>
	    </div>
	</div>
	<div id="loading_overlay" class="hidden">
	   <fmt:message key="label.common.loading"/>
	   <br/>
	   <img src="<c:url value='/images/ajax-loader-big.gif'/>" title="loader"/>
	</div>
</div>
<script>
$(function(){	
	$('#rosterReportingOrgFilter').orgFilter({
		containerClass: '',
		requiredLevels: [20,30,40,50,60,70]
	});

	$('#rosterReportingOrgFilterForm').validate({ignore: ""});

});

$("#btnRosterReports").click(function(event) {
	if($('#rosterReportingOrgFilterForm').valid()) {
		$("#accordionParent").html('<div id="accordion"></div>');
		getReportsForTeacherAndOrg();
	}
});	

function getReportsForTeacherAndOrg(){
	$('#loading_overlay').dialog({
        height: 200,
        width: 200,
        modal: true,
        autoOpen: false
    });
	var selectedOrg = null;
	if($('#rosterReportingOrgFilter').orgFilter('value') != null)  {
		selectedOrg = ($('#rosterReportingOrgFilter').orgFilter('value'));
	}
	<security:authorize access="hasRole('PERM_ROSTERRECORD_VIEW')">
    aart.buildRosterAccordion("#accordion", function() {
        var table = $(this).next("div").find("table");
        if (table.length == 0) {
            var contentDiv = $(this).next("div");
            //show a loading symbol. 
            
            if (contentDiv.html() === "") {
	            $('#loading_overlay').dialog("open");
	            //retrieve a list of the students for the roster.
	
	            var rosterId = $(this).children(".rosterId").val();
	            if (rosterId != null && !isNaN(rosterId) && Number(rosterId) > 0) {
	                $.ajax({
	                    url: 'getTestSessionsForRoster.htm',
	                    data: {
	                        rosterId: rosterId
	                    },
	                    type: "GET",
	                    success: function(data) {
	                    	<security:authorize access="hasRole('PERM_REPORT_DOWNLOAD')">
	                           buildTestSessionTable(data, contentDiv);
	                        </security:authorize>
	                        <security:authorize access="!hasRole('PERM_REPORT_DOWNLOAD')">
	                            contentDiv.html("<fmt:message key='report.permission.denied'/>");
	                        </security:authorize>
	                        $('#loading_overlay').dialog("close");
	                    },
	                    error: function() {
	                    	$('body, html').animate({scrollTop:0}, 'slow');
	                		$('#no_ended_sessions').show();
	                		setTimeout("aart.clearMessages()", 3000);
			            }
	                });
	            }
            }
        }
    }, "#noTestSessions", selectedOrg);
    </security:authorize>
};


<security:authorize access="hasRole('PERM_REPORT_DOWNLOAD')">
function buildTestSessionTable(data, container) {
	if (data !== undefined && data !== null && data.length > 0) {
		var htmlString = "";
		// DE2719 Black boxes display around PDF icons in IE8 FIX.
		for (var i = 0, length = data.length; i < length; i++) {
			//htmlString += "<div class='testsession'><span>" + data[i].name + "</span><a href='getReport.htm?testSessionId=" + data[i].id + "'><img src='images/pdf.png' style='border:0px solid;' /></a></div>"
			htmlString += "<div class='testsession'><span>" + data[i].name + "</span><a href='javascript:getReport("+ data[i].id + ")'><img src='images/pdf.png' style='border:0px solid;' /></a></div>"
		}

		container.html(htmlString);
	} else {
		$('body, html').animate({scrollTop:0}, 'slow');
		$('#no_ended_sessions').show();
		setTimeout("aart.clearMessages()", 3000);
	}
}

function getReport(id) {	
	
	 $.ajax({
         url: 'checkForCompletedStudents.htm',
         data: {
        	 testSessionId: id
         },
         type: "GET",
         dataType : 'text',
         contentType : 'application/pdf',
         success: function(response) {
		 	if(response == 1) {
        		 window.location.href = "getReport.htm?testSessionId="+id;
        	} else if (response == 0){
        		$('body, html').animate({scrollTop:0}, 'slow');
        		$('#no_ended_students').show();
        		setTimeout("aart.clearMessages()", 10000);
        	}else if (response == 2) {
        		$('body, html').animate({scrollTop:0}, 'slow');
        		$('#no_ended_enrolled_students').show();
        		setTimeout("aart.clearMessages()", 10000);
        	}
         }       
     });
}


</security:authorize>
</script>