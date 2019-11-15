<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/jsp/include.jsp" %>

<div id="pdTranscriptsTab">
	
	<div id="transcriptsResults" class="transcriptsTable" style="padding-left:10px; padding-top: 80px;"><br>
		<div id="transcriptsSection" class="kite-table">
			<table class="responsive" id="transcriptsTable"></table>
			<div id="transcriptsPager"></div>
		</div>
	</div>
</div>

<script>
/*
	var isPDTAdmin = false;
	var isPDTStateAdmin = false;
	<security:authorize access="hasAnyRole('REL_MODULE', 'UNREL_MODULE')">
		isPDTStateAdmin = true;
	</security:authorize>
	<security:authorize access="hasAnyRole('PUB_MODULE', 'UNPUB_MODULE')">
		isPDTAdmin = true;
	</security:authorize>*/
	
	//alert('isPDTAdmin:'+isPDTAdmin);
	//alert('isPDTStateAdmin:'+isPDTStateAdmin);
$(function() {
	getTranscripts();
	
	$("#transcript").click(function() {
		var $gridAuto = $("#transcriptsTable");

		$gridAuto.jqGrid('setGridParam',{
			datatype:"json", 
			url : 'getTranscripts.htm', 
			search: false,
			postData: { "filters": ""}
		}).trigger("reloadGrid",[{page:1}]);
	});	
});

	
	function getTranscripts(){

		var $gridAuto = $("#transcriptsTable");
		//Unload the grid before each request.
		$("#transcriptsTable").jqGrid('clearGridData');
		$("#transcriptsTable").jqGrid("GridUnload");
		
		var gridWidthForTranscripts = 775;
		/*$gridAuto.parent().width()-60;		
		if(gridWidthForTranscripts < 750) {
			gridWidthForTranscripts = 750;				
		}*/
		var cell_width = 200;
		$gridAuto.scb({
			datatype: "local", 
			width: gridWidthForTranscripts,
	        colNames: [
						'<fmt:message key="label.pd.transcripts.id"/>',
						'<fmt:message key="label.pd.transcripts.name"/>',
						'<fmt:message key="label.pd.transcripts.ceu"/>',
						'<fmt:message key="label.pd.transcripts.completionDate"/>',
						'<fmt:message key="label.pd.transcripts.testResult"/>',
						'<fmt:message key="label.pd.transcripts.required"/>',
						'<fmt:message key="label.pd.transcripts.user"/>',
						'<fmt:message key="label.pd.transcripts.organizationName"/>',
						'<fmt:message key="label.pd.transcripts.assessmentProgram"/>'
	                   ],                     
	                   
	        colModel: [
	   				   {name: 'mid', index: 'mid',  align: 'center', width : 100, align: 'center', sortable:true},
	                   {name: 'modulename', index: 'modulename', align: 'center', width : cell_width, sortable:true},
	                   {name: 'earnedceu', index: 'earnedceu', align: 'center', width : 100, sortable:true,
	                	   search : true,  stype : 'select', searchoptions: {value: 'All:All;NA:Not Available;0:0;1:1;2:2;3:3;4:4;5:5', sopt:['eq'] }},
	                   {name: 'testcompletiondate', index: 'testcompletiondate', width : cell_width, align: 'center', sortable:true,
	                	   formatter: function (cellvalue, options, rowObject) {
	               				if(cellvalue != "Not Available") { var date = new Date(cellvalue);  return $.datepicker.formatDate("mm/dd/yy", date); } else { return cellvalue; } },
	               		   search : true, searchoptions: {      dataInit: function(el) {
                               $(el).datepicker({
                                   changeYear: true,
                                   changeMonth: true,                                   
                                   dateFormat: 'mm/dd/yy',
                                   onSelect: function (dateText, inst) {
                                       setTimeout(function () {
                                    	   $gridAuto[0].triggerToolbar();
                                       }, 100);
                                   }                                   
                               });
                           }} 
	               			},
	                   {name: 'testresult', index: 'testresult', width : 150, align: 'center', sortable:true,
	                	   stype : 'select', searchoptions: {value: 'All:All;true:Passed;false:Attempted', sopt:['eq'] }},
	                   {name: 'requiredflag', index: 'requiredflag', width : 100, align: 'center', sortable:true, 
	                		   search : true,  stype : 'select', searchoptions: {value: 'All:All;true:Yes;false:No', sopt:['eq'] }, hidden : true},
	                   {name: 'displayname', index: 'displayname', width : cell_width, align: 'center', sortable:true, search : true, hidden : true},
	                   {name: 'organizationname', index: 'organizationname', width : cell_width, align: 'center', sortable:true, search : true, hidden : true},
	                   {name: 'assessmentprogramname', index: 'assessmentprogramname', width : cell_width, align: 'center', sortable:true, search : true, hidden : true}
	                    ], 
	    	           height : 'auto',
	    	           shrinkToFit: false,
			           rowNum:10, 
			           rowList:[10,20,30], 
			           pager: '#transcriptsPager',  
			           viewrecords: true, 
			           sortorder: "asc",
			           sortname : 'm.id',
					   altclass: 'altrow',
					   viewable: false,
					   emptyrecords : "<fmt:message key='label.common.norecordsfound'/>",
					   altRows : true,
					   hoverrows : true,
					   multiselect : false,
					   toppager: false,
					   filterToolbar: true
		}); 
		
	}	
</script>	