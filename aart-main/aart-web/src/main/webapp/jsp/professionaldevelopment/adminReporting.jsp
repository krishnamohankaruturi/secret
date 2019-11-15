<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/jsp/include.jsp" %>

<div>
	<h4 align="left"> Standard reports requested for your organization </h4>		
	<div id="reportsSection" class="kite-table">
		<table class="responsive" id="reportsTable"></table>
		<div id="reportsPager"></div>
	</div>

</div>
<br>
<div>
	<img src="<c:url value='/images/icons/noteicon.png'/>"/>
	<br/>
	
	<div id="pdExtractQueueMessage" style="font-weight:bold">
	</div>
</div>

<div class="confirmNewFileDialog"></div>

<script>
var pdQueuedStartTime = parseInt(${applicationScope['queued.extracts.starttime']});
var pdQueuedEndTime = ${applicationScope['queued.extracts.endtime']};
var pdQueuedMsg = "PD Extract generation requests will be placed in queue and extract will be available between " + (pdQueuedStartTime - 12) + " p.m. and " + pdQueuedEndTime + " a.m. central time, thank you for your patience.";

$('#pdExtractQueueMessage').html(pdQueuedMsg).show();

	$("#reporting").click(function() {
		$('#pdbreadCrumMessage').text("Professional Development: <fmt:message key='label.pd.admin.heading'/> - Reporting");
		
		$('#reportsTable').jqGrid("GridUnload");
		getAdminReports();
		$('#reportsTable').trigger("reloadGrid");
		
		$("#reporting").addClass("active");
		$("#existingModules").removeClass("active").addClass("hidden");
		$("#newModule").removeClass("active").addClass("hidden");
		$("#tabs_reporting").removeClass("hidden");
		$("#tabs_newmodule").addClass("hidden");
		$("#tabs_existingmodules").addClass("hidden");
		$("#resultsSection").addClass("hidden");		
	});
	
	function getAdminReports(){
		var url = "getAdminReports.htm";
		
		$("#reportsTable").jqGrid({
			url:url, 
			mtype: "POST",
			datatype: "json", 
			postData :  {
				filters: "",
				stateAdminFlag : isPDStateAdmin,
				districtAdminFlag: isPDDistrictAdmin
			},
	        colNames: [
	                   'id',
	                   'Report Name',
	                   'Description',
	                   'Requested',
	                   'File Name',
	                   'File',
	                   'Action'
	                   ],
	                   
	        colModel: [
	   				   {name: 'id', index: 'id',  align: 'center', hidden: true},
	                   {name: 'reportname', index: 'reportname', align: 'center', sortable:false},
	                   {name: 'description', index: 'description', align: 'center', sortable:false},
	                   {name: 'requested', index: 'requested', align: 'center', sortable:false,
	                	   formatter: function (cellvalue, options, rowObject) {
	               				if(cellvalue != "Not Available" && cellvalue != "") { 
	               					var date = new Date(cellvalue);  
	               					return ($.datepicker.formatDate("mm/dd/yy", date)) +" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
	               				} else { return cellvalue; } },
	                	   
	                	   },
	                   {name: 'fileName', index: 'fileName', align: 'center', hidden: true, sortable:false},
	                   {name: 'status', index: 'status', align: 'center', formatter: reportLinkFormatter,sortable:false},                  		
	                   {name: 'actions', index: 'actions', align: 'center',sortable:false, formatter: actionLinkFormatter}
	                    ], 
	    	           height : 'auto',
	    	           shrinkToFit: true,
			           rowNum:10, 
			           rowList:[10,20,30], 
			           pager: '#reportsPager',  
			           viewrecords: true, 
			           sortorder: "asc",
			           sortname : 'id',
					   altclass: 'altrow',
					   emptyrecords : "<fmt:message key='label.common.norecordsfound'/>",
					   altRows : true,
					   hoverrows : true,
					   multiselect : false,
					   toppager: false	               
		}); 
		$("#reportsTable").jqGrid('navGrid','#reportsPager',{edit:false,add:false,del:false,search:false});
	}
	
	function reportLinkFormatter(cellvalue, options, rowObject) {
           var status = rowObject[5].toUpperCase();
           if (status == "COMPLETED") {
        	   return '<a href="getAdminReportCsv.htm?reportName=' +rowObject[4] + '"><img src="images/icon-csv.png" title="Click to edit module details"/> </a>';
           }
		return cellvalue;
	}
	
	function actionLinkFormatter(cellvalue, options, rowObject) {
       	var buttonId = 'newFileStatus';
       	if(rowObject[1] == 'Training Details') {
       		buttonId = 'newFileDetails';
       	}
       	if(isPDStateAdmin){
       		buttonId = buttonId + 'PDStateAdmin';
       	}
       	else if(isPDDistrictAdmin){
       		buttonId = buttonId + 'PDDistrictAdmin';
       	}
       	else {
       		buttonId = buttonId + 'PDAdmin';
       	}
       	
		if(rowObject[1] == 'Training Details') {
	        return '<button id="' + buttonId + '" onclick="generateTrainingStatusReportDialog('+ rowObject[0] +', &quot;'+ rowObject[1] +'&quot;)" title="Click to generate Training Details Report">New File</button>';			
		} else {
			return '<button id="' + buttonId + '" onclick="generateTrainingStatusReportDialog('+ rowObject[0] +', &quot;'+ rowObject[1] +'&quot;)" title="Click to generate Training Status Report">New File</button>';
		}
	}
	
	function generateTrainingStatusReportDialog(moduleReportId, moduleReportType) {
		
		if(moduleReportId > 0) {
			$('.confirmNewFileDialog').dialog({
				resizable: false,
				height: 150,
				width: 500,
				modal: true,
				autoOpen:false,			
				title: "Create Report confirmation",
				buttons: {
				  Yes: function() {
					  	generateTrainingStatusReport(moduleReportId, moduleReportType);
				    	$(this).dialog("close");		
				    },
				  No: function() {
				    	 $(this).dialog("close");
				    }
				}
			});
			
			$('.confirmNewFileDialog').html("The existing report request and file will be deleted.  Do you want to proceed?");
			$('.confirmNewFileDialog').dialog("open");
		} else {
			generateTrainingStatusReport(moduleReportId, moduleReportType);
		}
	}
	
	function generateTrainingStatusReport(moduleReportId, moduleReportType) {
		
		var ajaxurl = 'generateTrainingStatusReport.htm';		
		if(moduleReportType == 'Training Details') {
			ajaxurl = 'generateTrainingDetailsReport.htm';
		}
		if(moduleReportType == 'DLM PD Training List') {
			ajaxurl = 'dlmPDTrainingList.htm';
		}
				
		$.ajax({
            url: ajaxurl,            
            dataType: 'json',
            data: {
            	moduleReportId : moduleReportId,
            	stateAdminFlag : isPDStateAdmin,
            	districtAdminFlag: isPDDistrictAdmin
            },
            type: "POST",
            success : function(data) {
            	moduleReportId = data;
            	$('#reportsTable').jqGrid("GridUnload");
        		getAdminReports();
        		$('#reportsTable').trigger("reloadGrid");
        		
        		// monitor the status of when report generation is complete
        		monitorReportGenerationStatus(moduleReportId);
            }
        });
	}
	
	function monitorReportGenerationStatus(moduleReportId){

		$.post(
				'monitorReportGenerationStatus.htm',
				{moduleReportId : moduleReportId},
				function(responseText){
					if(responseText === 'In Progress'){
						setTimeout(function(){monitorReportGenerationStatus(moduleReportId);}, 10000);
					} else if(responseText === 'Completed'  || responseText === 'Failed'){		
						$('#reportsTable').jqGrid("GridUnload");
		        		getAdminReports();
		        		$('#reportsTable').trigger("reloadGrid");
					}
				},
				"html"
			);	
	}

</script>