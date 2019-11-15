<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<style>
.studentRow {
    display: table;
}
/* Overriding jqgrid css for setting width */
element.style {
    width: 100%;
}
.ui-jqgrid .ui-jqgrid-view {
    left: 0;
    padding: 0;
    position: relative;
    top: 0;
    width: 100%
}
.ui-jqdialog-content td.EditButton {
    border-left: 0 none;
    border-right: 0 none;
    border-top: 0 none;
    padding-bottom: 5px;
    padding-top: 5px;
    text-align: left;
}
#unsyncedStudents {
    height: 400px;
    overflow: auto;
    border: 1px solid black;
}
/* INFO: Overriding this style sheet here did not work. */
/* .ui-jqdialog-content .form-view-data {white-space:none} */
label
{
width: 30%;
float: left;
text-align: left;
margin-right: 0.5em;
display: block
}
a.spacedLink
 { 
 width: 30%; 
 float: left; 
 text-align: left; 
 margin-right: 0.5em; 
 display: block 
 } 
.submit input
{
/* margin-left: 4.5em; */
} 
</style>
<h>
Node Report
</h>
<br/>
<div id="search_tests">
	<label for="studentKeywordInput" id="studentKeywordLabel">
		<fmt:message
			key="label.nodeResponse.search.studentKeyword" />
	</label>
	<input type="text"
		id="studentKeywordInput" /><div id="studentKeywordInputError" class="ui-state-error error_message hidden"><fmt:message key="label.error.studentTestKeywordInput"/></div>
		<br/>
	<label for="testKeywordInput" id="testKeywordLabel">
		<fmt:message
			key="label.nodeResponse.search.testKeyword" />
	</label>
	<input type="text"
		id="testKeywordInput" /><div id="testKeywordInputError" class="ui-state-error error_message hidden"><fmt:message key="label.error.studentTestKeywordInput"/></div>
		<br/>
	 <p class="submit">
	<label for="searchButton" id="searchButtonLabel">
		&nbsp;
	</label>
	<input type="button" id="searchButton"
		value="<fmt:message key='label.common.search'/>" onclick="search()"/>
	 </p>	
	<a href="javascript:void(0)" id="hideStudentNames" class="spacedLink">
	Hide Student Names
	</a>
	<a href="javascript:void(0)" id="showStudentNames" class="spacedLink">
	Show Student Names
	</a>
	<br />
	<a href="javascript:void(0)" id="hideTestNames" class="spacedLink">
	Hide Test Names
	</a>
	<a href="javascript:void(0)" id="showTestNames" class="spacedLink">
	Show Test Names
	</a>
	<br />
	<a href="javascript:void(0)" id="hideNodeInfo" class="spacedLink">
	Hide Node Information
	</a>
	<a href="javascript:void(0)" id="showNodeInfo" class="spacedLink">
	Show Node Information
	</a>
	<br />
</div>
<label for="chngroup" id="changeGroupLabel">
Group By:
</label>
<select id="chngroup" onchange="changeGrouping()">
	<option value="studentId">Student Id</option>
	<option value="testId">Test Id</option>
	<option value="studentsTestsId">Testing Session</option>
	<option value="nodeKey">Node</option>
	<option value="contentFrameworkDetailCode">Essential Elements</option>
	<option value="clear">Remove Grouping</option>
</select>
<br />
<br />
<div id="noReport" class="none" style="width:100%"></div>

<div>
	<div class="full_main divWidthComplete">
		<div class ="table_wrap table_wrap_overloaded">
			<div id="resultsSection" class="kite-table">
				<table id="48remote4" style="width:100%"></table>
				<div id="p48remote4" class="kite-table" style="width:100%"></div>
			</div>
		</div>
	</div>
</div>	

<div id="loading_overlay">
	<div class="loadingOverlayMessge">
		<fmt:message key="label.common.loading"/>
		</div>
    <div class = "loadingOverlayImage">    	
    	<img align="middle" src="<c:url value='/images/ajax-loader-big.gif'/>"/>
    </div>
</div> 
    
<script>

//Global variable to capture the mouse position for 
//"Loading...." message display.
var mousePosition;
var strlengthStudent = document.getElementById('studentKeywordInput');
var strlengthTest = document.getElementById('testKeywordInput');
var firstLoad = true;

//TODO disable calls when no of rows < limit and it is only sorting.
	$(document)
			.ready(
					function() {
						
						 $('#loading_overlay').dialog({
						        height: 200,
						        width: 200,
						        modal: true,
						        title: ' ',
						        autoOpen: false
						    });						 

						//Capture the mouse position for Loading message display.
						$(document).mousemove(function(e) {
							mousePosition = e.pageY;				
						}); 						
						
						var grid_width = $('.kite-table').width();
						if(grid_width == 100 || grid_width == 0) {
							grid_width = 719;				
						}
				        var colWidth = grid_width/6;
				     
						
						jQuery("#48remote4").jqGrid(
						{
							url : 'searchNodeReport.htm?q=1',
							postData : {
								studentKeyword : function() {
 									//This should be done otherwise $() does not evaluate.
									return $('#studentKeywordInput').val();
								},
								testKeyword : function() {
								return $('#testKeywordInput').val();
							}
								},
							datatype : "json",
							width: grid_width,
							colNames : [ 
							             'Students Tests Id',
							             'Student Id',
							             'State Student Identifier',
							             'Student First Name',
							             'Student Middle Name',
							             'Student Last Name',
							             'comprehensiveRace',
							             'gender',
							             'primaryDisabilityCode',
							             'Test Id',
							             'Test Name',
							             'Test Collection Id',
							             'Test Collection Name',
							             'Node Id',
							             'Node Key',
							             'No Of Correct Resp.',
							             'No Of InCorrect Resp.',
							             'No Of Responses',
							             'No Of Items Presented',
							             'No Of Answered Items',
							             'Total Raw Score',
							             'Profiles',
							             'Attribute Information',
							             'Essential Elements Code',
							             'Essential Elements Description',
							             'Essential Elements (From LM)',
							             'Conceptual Area',
							             'Band',
							             'CA Standard',
							             'Initial Precursor', 
							             'Distal Precursor',
							             'Proximal Precursor', 
							             'Target',
							             'Successor', 
							             'Test Session Name'
							             ],
							colModel : [ {
								name : 'studentsTestsId',
								index : 'studentsTestsId',
								width : colWidth,
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								sortable : true,
								hidden : true
							}, {
								name : 'studentId',
								index : 'studentId',
								width : colWidth,
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								hidden : true,
								editoptions:{readonly:true,size:10},
								sorttype : 'int',
								summaryType : 'count',
								summaryTpl : '({0}) total'
							}, {
								name : 'stateStudentIdentifier',
								index : 'stateStudentIdentifier',
								width : colWidth,
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								editoptions:{readonly:true,size:10},
								sortable : false
							}, {
								name : 'studentFirstName',
								index : 'studentFirstName',
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								editoptions:{readonly:true,size:10},
								width : colWidth,
								hidden : true,
								sortable : false
							}, {
								name : 'studentMiddleName',
								index : 'studentMiddleName',
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								editoptions:{readonly:true,size:10},
								width : colWidth,
								hidden : true,
								sortable : false
							}, {
								name : 'studentLastName',
								index : 'studentLastName',
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								editoptions:{readonly:true,size:10},
								width : colWidth,
								hidden : true,
								sortable : false
							}, {
								name : 'comprehensiveRace',
								index : 'comprehensiveRace',
								width : colWidth,
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								editoptions:{readonly:true},
								sortable : false,
								hidden : true
							}, {
								name : 'gender',
								index : 'gender',
								width : colWidth,
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								editoptions:{readonly:true},
								sortable : false,
								hidden : true
							}, {
								name : 'primaryDisabilityCode',
								index : 'primaryDisabilityCode',
								width : colWidth,
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								editoptions:{readonly:true},
								sortable : false,
								hidden : true
							}, {
								name : 'testId',
								index : 'testId',
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								editoptions:{readonly:true,size:10},
								width : colWidth,
								sorttype : 'int'
							}, {
								name : 'testName',
								index : 'testName',
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								editoptions:{readonly:true,size:10},
								width : colWidth,
								hidden : true,
								sortable : false
							}, {
								name : 'testCollectionId',
								index : 'testCollectionId',
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								editoptions:{readonly:true,size:10},
								width : colWidth,
								sorttype : 'int',
								hidden : true
							}, {
								name : 'testCollectionName',
								index : 'testCollectionName',
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								editoptions:{readonly:true,size:10},
								width : colWidth,
								hidden : true,
								sortable : false
							},{
								name : 'nodeId',
								index : 'nodeId',
								width : colWidth,
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								editoptions:{readonly:true,size:10},
								hidden : true,
								sortable : false
							}, {
								name : 'nodeKey',
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								editoptions:{readonly:true,size:10},
								index : 'nodeKey',
								width : colWidth,
								hidden : false
							}, {
								name : 'noOfCorrectResponses',
								index : 'noOfCorrectResponses',
								width : colWidth,
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								editoptions:{readonly:true,size:10},
								sorttype : 'int',
								formatter : 'int',
								summaryType : 'sum',
								sortable : true
							}, {
								name : 'noOfInCorrectResponses',
								index : 'noOfInCorrectResponses',
								width : colWidth,
								sortable : true,
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								editoptions:{readonly:true,size:10},
								formatter : 'int',
								summaryType : 'sum'
							}, {
								name : 'noOfResponses',
								index : 'noOfResponses',
								width : colWidth,
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								editoptions:{readonly:true,size:10},
								sortable : true,
								formatter : 'int',
								summaryType : 'sum'
							}, {
								name : 'noOfItemsPresented',
								index : 'noOfItemsPresented',
								width : colWidth,
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								editoptions:{readonly:true},
								sortable : true,
								hidden : true,
								summaryType : 'sum'
							}, {
								name : 'noOfAnsweredItems',
								index : 'noOfAnsweredItems',
								width : colWidth,
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								editoptions:{readonly:true},
								sortable : true,
								hidden : false,
								summaryType : 'sum'
							}, {
								name : 'totalRawScore',
								index : 'totalRawScore',
								width : colWidth,
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								editoptions:{readonly:true},
								sortable : true,
								hidden : false,
								summaryType : 'sum'
							}, {
								name : 'profiles',
								index : 'profiles',
								width : colWidth,
								edittype:'textarea',
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								editoptions:{
									rows:"2",
									cols:"10",
									readonly:true},
								sortable : false,
								hidden : true
							}, {
								name : 'attributeInformation',
								index : 'attributeInformation',
								width : colWidth + 100,
								edittype:'textarea',
								editable : true,
								editrules:{edithidden:true},
								viewable: false,
								editoptions:{
									rows:"3",
									cols:"10",
									readonly:true},
								sortable : false,
								hidden : true
							}, {
								name : 'contentFrameworkDetailCode',
								index : 'contentFrameworkDetailCode',
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								editoptions:{readonly:true,size:10},
								width : colWidth,
								hidden : true,
								sortable : true,
								hidden : false
							}, {
								name : 'essentialElementsDescription',
								index : 'essentialElementsDescription',
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								editoptions:{readonly:true,size:10},
								width : colWidth,
								hidden : true,
								sortable : false,
								hidden : true
							}, {
								name : 'essentialElements',
								index : 'essentialElements',
								editable : true,
								editrules:{edithidden:true},
								viewable: false,
								editoptions:{readonly:true,size:10},
								width : colWidth,
								hidden : true,
								sortable : false,
								hidden : true
							}, {
								name : 'conceptualArea',
								index : 'conceptualArea',
								width : colWidth,
								edittype:'textarea',
								editable : true,
								editrules:{edithidden:true},
								viewable: true,
								editoptions:{
									rows:"3",
									cols:"10",
									readonly:true},
								sortable : false,
								hidden : true
							}, {
								name : 'band',
								index : 'band',
								width : colWidth,
								editrules:{edithidden:true},
								viewable: true,
								sortable : false,
								hidden : true
							}, {
								name : 'caStandard',
								index : 'caStandard',
								width : colWidth,
								editrules:{edithidden:true},
								viewable: true,
								sortable : false,
								hidden : true
							}, {
								name : 'initialPrecursor',
								index : 'initialPrecursor',
								width : colWidth,
								editrules:{edithidden:true},
								viewable: true,
								sortable : false,
								hidden : true
							}, {
								name : 'distalPrecursor',
								index : 'distalPrecursor',
								width : colWidth,
								editrules:{edithidden:true},
								viewable: true,
								sortable : false,
								hidden : true
							}, {
								name : 'proximalPrecursor',
								index : 'proximalPrecursor',
								width : colWidth,
								editrules:{edithidden:true},
								viewable: true,
								sortable : false,
								hidden : true
							}, {
								name : 'target',
								index : 'target',
								width : colWidth,
								editrules:{edithidden:true},
								viewable: true,
								sortable : false,
								hidden : true
							}  , {
								name : 'successor',
								index : 'successor',
								width : colWidth,
								editrules:{edithidden:true},
								viewable: true,
								sortable : false,
								hidden : true
							}, {
								name : 'testSessionName',
								index : 'testSessionName',
								width : colWidth,
								editrules:{edithidden:true},
								viewable: true,
								sortable : false,
								hidden : false
							}
							],
							rowNum : 5,
							rowList : [ 5,10, 20, 30, 40,60,80,120 ],
							height : 'auto',
							pager : '#p48remote4',
							sortname : 'testId',
							viewrecords : true,
							sortorder : "desc",
							altRows: true,
					        altclass: 'altrow',
							caption : "Node Report",
							grouping : true,
							shrinkToFit : false,
							groupingView : {
								groupField : [ 'studentId' ],
								groupColumnShow : [ true ],
								groupText : [ '<b>{0}</b>' ],
								groupCollapse : false,
								groupOrder : [ 'asc' ],
								groupSummary : [ true ],
								groupDataSorted : true
							},
							footerrow : true,
							userDataOnFooter : true,
							   gridComplete: function() {
							        var recs = parseInt($("#48remote4").getGridParam("records"));
							        //alert('recs'+recs);
							        if (isNaN(recs) || recs == 0) {
							            $("#p48remote4").hide();
							            $("#gbox_48remote4").hide();
							            if ($('#studentKeywordInput').val() != ''  | $('#testKeywordInput').val() != ''){
							            	$("#noReport").html('No Report For Selected Input');
							            }
							            //alert('No Records Found');
							        }
							        else {
							            $('#p48remote4').show();
							            $("#gbox_48remote4").show();
							            $("#noReport").html('');
							            //alert('records > 0');
							        }
							    },
							    loadBeforeSend: function () { 
							    	var gridIdAsSelector = $.jgrid.jqID(this.id),
							        $loadingDiv = $("#load_" + gridIdAsSelector),
							        $gbox = $("#gbox_" + gridIdAsSelector);
							    $loadingDiv.show().css({
							    	top:  (Math.min($gbox.height(), mousePosition) - 250) + 'px',
							        left: (Math.min($gbox.width(), $(window).width()) - $loadingDiv.outerWidth())/2 + 'px'
							    });							    	
							    },
					               loadComplete: function () {
					                    var sumOfNoOfResponses = jQuery("#48remote4").jqGrid(
					                    		'getCol', 'noOfResponses', false, 'sum');
					                    var sumOfNoOfCorrectResponses = jQuery("#48remote4").jqGrid(
					                    		'getCol', 'noOfCorrectResponses', false, 'sum');					                    
					                    var sumOfNoOfInCorrectResponses = jQuery("#48remote4").jqGrid(
							                    		'getCol', 'noOfInCorrectResponses', false, 'sum');					                    
					                    var sumOfNoOfAnsweredItems = jQuery("#48remote4").jqGrid(
					                    		'getCol', 'noOfAnsweredItems', false, 'sum');	
					                    var sumOfTotalRawScore = jQuery("#48remote4").jqGrid(
					                    		'getCol', 'totalRawScore', false, 'sum');	
					                    
					                    jQuery("#48remote4").jqGrid('footerData','set', {noOfResponses: 'Total:',
					                    	noOfResponses: sumOfNoOfResponses});
					                    jQuery("#48remote4").jqGrid('footerData','set', {noOfCorrectResponses: 'Total:',
					                    	noOfCorrectResponses: sumOfNoOfCorrectResponses});
					                    jQuery("#48remote4").jqGrid('footerData','set', {noOfInCorrectResponses: 'Total:',
					                    	noOfInCorrectResponses: sumOfNoOfInCorrectResponses});
					                    
					                    jQuery("#48remote4").jqGrid('footerData','set', {noOfAnsweredItems: 'Total:',
					                    	noOfAnsweredItems: sumOfNoOfAnsweredItems});
					                    jQuery("#48remote4").jqGrid('footerData','set', {totalRawScore: 'Total:',
					                    	totalRawScore: sumOfTotalRawScore});
					                    
					                    /**
					                    * Biyatpragyan Mohanty (bmohanty_sta@ku.edu) : DE4359 : Columns on Node Response Report are not aligned with the column headers
					                    * There is a problem in column span, the table is trying to colspan on all 34 columns while we are only displaying 10 columns.
					                    * the below line should fix this issue.
					                    */
					                    $('.jqgroup').children().attr('colspan','10');
					                    
					                    $('#loading_overlay').dialog("close");
					                    
					                },
					                beforeRequest: function () {
										//Set the page param to lastpage before sending the request when 
										  //the user entered current page number is greater than lastpage number.
										var currentPage = $(this).getGridParam('page');
						                var lastPage = $(this).getGridParam('lastpage');
						                
						                 if (lastPage!= 0 && currentPage > lastPage) {
						                	 $(this).setGridParam('page', lastPage);
						                	$(this).setGridParam({postData: {page : lastPage}});
						                }
					                }
						});
						//Adding this here brings up a lot of options like view edit and add record.
						//Under New In Version 3.5 explore Form Navigation.
						//INFO instead of adding view and edit, using edit in read only mode,
						// because it can be extended to edit and it can be extended to edit.
						//For editing the view options.
						//http://www.ok-soft-gmbh.com/jqGrid/ClientsideEditing2.htm
						jQuery("#48remote4").jqGrid('navGrid','#p48remote4',
								{
							edit:false,
							add:false,
							del:false,
							search:false,
							refresh:true,
							view:true,
							viewtext:'View Node Detail'},
			                      {}, // edit options
			                      {}, // add options
			                      {}, // del options
			                      {}, // search options
			                      {
			                    	  beforeShowForm: function(form) { 
											 var dlgDiv = $("#viewmod48remote4"); 				                            
 				   							//Positioning the detial view modal window. 				   											       
 				   							dlgDiv.css("position","absolute");
 				   						       dlgDiv.css("top", ( $(window).height() - dlgDiv.height() ) / 2+$(window).scrollTop() + "px");
 				   						       dlgDiv.css("left", ( $(window).width() - dlgDiv.width() ) / 2+$(window).scrollLeft() + "px");
										},
			                    	  caption:'View Node Detail',
			                    	  bClose:'Click to <i>close</i> Detail',
			                    	  width:600
			                    } //view options
								);
			});


	//Code to set the position of the AlertMod 
	//warning message (Please, select a row)
	var orgViewModal = $.jgrid.viewModal;
	$.extend($.jgrid,{
	    viewModal: function (selector,o){ 
	        if(selector == '#alertmod'){
	            var of = jQuery(o.gbox).offset();       
	            var w = jQuery(o.gbox).width();       
	            var h = jQuery(o.gbox).height(); 
	            var w1 = $(selector).width();
	            //var h1 = $(selector).height();	            
	            $(selector).css({	            	
	                'top':of.top+(h - 300),	                
	                'left':of.left+((w-w1)/2)
	            });
	        }
	        orgViewModal.call(this, selector, o);
	    }
	}); 
	
	
	function changeGrouping() {
		var vl = $("#chngroup").val();
		if (vl) {
			if (vl == "clear") {
				jQuery("#48remote4").jqGrid(
						'groupingRemove', true);
			} else {
				jQuery("#48remote4").jqGrid(
						'groupingGroupBy', vl);
			}
		}
	}
	
	function search() {
		if ((strlengthStudent.value.length < 2) && (strlengthTest.value.length < 2)){
			$("#studentKeywordInputError").show();
			$("#testKeywordInputError").show();
			setTimeout("aart.clearMessages()", 3000);
		}else if (strlengthStudent.value.length < 2){
			$("#studentKeywordInputError").show();
			setTimeout("aart.clearMessages()", 3000);
		}else if (strlengthTest.value.length < 2){
			$("#testKeywordInputError").show();
			setTimeout("aart.clearMessages()", 3000);
		}else{
		if(firstLoad) {
			$('#loading_overlay').dialog("open");
			firstLoad = false;
		}
		$('#48remote4').trigger( 'reloadGrid', [{page:1}]);
		changeGrouping();
		}
	}
	jQuery("#hideStudentNames").click(
			function() {
				jQuery("#48remote4").jqGrid('hideCol',["studentFirstName"]); 
				jQuery("#48remote4").jqGrid('hideCol',["studentMiddleName"]); 
				jQuery("#48remote4").jqGrid('hideCol',["studentLastName"]); 
				});
	jQuery("#showStudentNames").click(
			function() {
				jQuery("#48remote4").jqGrid('showCol',["studentFirstName"]); 
				jQuery("#48remote4").jqGrid('showCol',["studentMiddleName"]); 
				jQuery("#48remote4").jqGrid('showCol',["studentLastName"]); 
				});
	jQuery("#hideTestNames").click(
			function() {
				jQuery("#48remote4").jqGrid('hideCol',["testName"]); 
				jQuery("#48remote4").jqGrid('hideCol',["testCollectionName"]); 
				});
	jQuery("#showTestNames").click(
			function() {
				jQuery("#48remote4").jqGrid('showCol',["testName"]); 
				jQuery("#48remote4").jqGrid('showCol',["testCollectionName"]); 
				});
	jQuery("#hideNodeInfo").click(
			function() {
				jQuery("#48remote4").jqGrid('hideCol',["nodeId"]); 
				jQuery("#48remote4").jqGrid('hideCol',["profiles"]); 
				jQuery("#48remote4").jqGrid('hideCol',["attributeInformation"]); 
				});
	jQuery("#showNodeInfo").click(
			function() {
				jQuery("#48remote4").jqGrid('showCol',["nodeId"]); 
				jQuery("#48remote4").jqGrid('showCol',["profiles"]); 
				jQuery("#48remote4").jqGrid('showCol',["attributeInformation"]); 
				});
	</script>