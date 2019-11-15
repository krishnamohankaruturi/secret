<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<style>
#ViewGrid_testsTable input[name=selectedTestOrTc]{
	display:none !important;
}
#stsaSearchBtn{
margin-top: 38px;
}
</style>
	

<div >
	<div class="ui-state-error noTestsPermission hidden"><fmt:message key='error.common.permission.test.view' /></div>
	<div class="setupAssesmentsDiv">	
	<input type="button" id="assessmentsNextButtonTop" value="<fmt:message key='label.common.next'/>" class="nextButton assessmentsNextButton" style="float: right;"/>
	<span class="error_message ui-state-error hidden selectAssessmentsError" id="selectAssessmentError"><fmt:message key="error.testsession.noAssessments"/></span> <br />
		<div id="stsaSearchFilterContainer">
				<form id="stsaSearchFilterForm" name="searchFilterForm" class="form">					
						<div class="btn-bar">
							<div id="stsaSearchFilterErrors" class="error"></div>
							<div id="stsaSearchFilterMessage" style="padding:20px" class="hidden"></div> 
						</div>
						<div class="form-fields">
								<label for="stsaAssessmentPrograms" class="field-label">Assessment Program:<span class="lbl-required">*</span></label>			
								<select id="stsaAssessmentPrograms" title="Assessment Program" class="bcg_select required" name="assessmentPrograms">
									<option value="">Select</option>
								</select>
						</div>
						<div class="form-fields">
							<label for="stsaTestingPrograms" class="field-label">Testing Program:</span></label>			
							<select id="stsaTestingPrograms" title="Testing Program" class="bcg_select" name="testingPrograms">
								<option value="">Select</option>
							</select>
						</div>					
				        <div class="form-fields" style="vertical-align: top;">
				            <button class="btn_blue" id="stsaSearchBtn">Search</button> 			
				        </div>
				</form>
			</div>
		<div class ="table_wrap table_wrap_overloaded">
			<div id="resultsSection" class="kite-table">
				<table class="responsive" id="testsTable"></table>
				<div id="naMessage" class="hidden">
					<fmt:message key='label.tests.search.notApplicableMessage' />
				</div>
				<div id="testsPager"></div>
			</div>
		</div> <!-- /table_wrap -->
	</div>
	<input type="button" id="assessmentsNextButton" value="<fmt:message key='label.common.next'/>" class="nextButton assessmentsNextButton floatRightBottomButton" />
</div>
	
<div id="previewTestDiv"></div>
		 


<script type="text/javascript">
var hasMultiSelectAccess =false;
<c:if test="${hasQCPermission == true}">
    hasMultiSelectAccess=true;
</c:if>
$('.setupAssesmentsDiv').hide();
<security:authorize access="!hasRole('PERM_TEST_VIEW')">
$('.noTestsPermission').show();
</security:authorize>	 
<security:authorize access="hasRole('PERM_TEST_VIEW')">
$('.setupAssesmentsDiv').show();
</security:authorize>
	//Need to store the radio button value so that we can set it back, because JQGrid does not support automatic selection in memory.
	var selectedRadio = null;
	var selectedCheckbox = null;
	var selectedTestCollectionId = null;
	var arraySelectedTestId;
	var selectedTestId = [];
	var testcollectionAray = {};
  	var testcollectionArayKey = {};
	
	var assessmentProgramValues;
	var testingProgramValues;
	var assessmentValues;
	var gradeCourseValues;
	var contentAreaValues;	
	
	$(function() {
		getValues();
		
		$('#stsaTestingPrograms, #stsaAssessmentPrograms').select2({
    		placeholder:'Select',
    		multiple: false,
    		allowClear : true
    	});
    	
    	$('#stsaSearchFilterForm').validate({
    		ignore: "",
    		rules: {
    			assessmentPrograms: {required: true},
    			testingPrograms: {required: false},
    		}
    	});
    	
    	
	});
	
	
	
	/**
	* Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15083 : Test Management Add Test Session page - misc UI changes
	* - Get assessment program names
	* - Bind event to get testing prgram names upon selection of assessment program name
	* - Bind event to load grid with data based on user selection.
	*/	
	function populateSearchFiltersData() {
		
		var apSelect = $('#stsaAssessmentPrograms'), optionText='';
		$('.messages').html('').hide();
		apSelect.find('option').filter(function(){return $(this).val() > 0}).remove().end();
		
		/**
		* Get assessment program names
		*/
		$.ajax({
			url: 'getAssessmentProgramsByUserSelected.htm',
			dataType: 'json',
			type: "POST",
			success: function(assessmentPrograms) {				
				if (assessmentPrograms !== undefined && assessmentPrograms !== null && assessmentPrograms.length > 0) {
					$.each(assessmentPrograms, function(i, assessmentProgram) {
						optionText = assessmentPrograms[i].programName;
						if(assessmentProgram.id == $('#hiddenCurrentAssessmentProgramId').val()){
							apSelect.append($('<option selected=\''+'active'+'\' ></option>').val(assessmentProgram.id).html(optionText));
						} else {
							apSelect.append($('<option></option>').val(assessmentProgram.id).html(optionText));	
						}
					});
					apSelect.trigger('change');
					
					var filterSelectedValue = getFromSessionStorage("stsaAssessmentProgramId");				
					if(typeof filterSelectedValue  != 'undefined' && filterSelectedValue != null) {							
						apSelect.val(filterSelectedValue);
						apSelect.trigger('change');
					} else if (assessmentPrograms.length == 1) {
						apSelect.find('option:first').removeAttr('active').next('option').attr('active', 'active');
						apSelect.trigger('change');
    				}
					
				} else {
					$('body, html').animate({scrollTop:0}, 'slow');
					$('#stsaSearchFilterErrors').html("<fmt:message key='newreport.no.assessmentprogram'/>").show();
				}
				apSelect.trigger('change.select2');
			}
		});
		
		/**
		* Bind event to get testing prgram names upon selection of assessment program name
		*/
		$('#stsaAssessmentPrograms').on("change",function() {

			$('#stsaTestingPrograms').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
			$('#stsaTestingPrograms').trigger('change.select2');
			var assessmentProgramId = $('#stsaAssessmentPrograms').val();

			if (assessmentProgramId != 0) {
				$.ajax({
			        url: 'getTestingPrograms.htm',
			        data: {
			        	assessmentProgramId: assessmentProgramId
			        	},
			        dataType: 'json',
			        type: "POST",
			        success: function(testingPrograms) {
			        	
			        	$('#stsaTestingPrograms').html("");		
		        		$('#stsaTestingPrograms').append($('<option></option>').val('').html('Select'));
						$.each(testingPrograms, function(i, testingProgram) {
							$('#stsaTestingPrograms').append($('<option></option>').attr("value", testingProgram.id).text(testingProgram.programName));
						});
						
						var filterSelectedValue = getFromSessionStorage("stsaTestingProgramId");				
						if(typeof filterSelectedValue  != 'undefined' && filterSelectedValue != null) {							
							$("#stsaTestingPrograms").val(filterSelectedValue);
							$("#stsaTestingPrograms").trigger('change');
						} else if (testingPrograms.length == 1) {
							$("#stsaTestingPrograms option").removeAttr('active').next('option').attr('active', 'active');
							$("#stsaTestingPrograms").trigger('change');
						}
						$('#stsaTestingPrograms').trigger('change.select2');
			        }
				});
				
			}
		});
		
		/**
		* Validate form to make sure user has selected assessment program which is mandatory.
		*/
		$('#stsaSearchFilterForm').validate({
    		ignore: "",
    		rules: {
    			assessmentPrograms: {required: true},
    			testingPrograms: {required: false}
    		}
    	});

		/**
		* Bind event to load grid with data based on user selection.
		*/
		$('#stsaSearchBtn').on("click",function(e) {
			selectedTestId = [];
			testcollectionArayKey = {};
			
			e.preventDefault();
			$('#stsaSearchFilterErrors').html("");
			$('#stsaSearchFilterMessage').html("").hide();
			isNotBack = true;
			if($('#stsaSearchFilterForm').valid()){
				setSearchFilterValuesToSession();
				var testingProgramName = $('#stsaTestingPrograms option:selected').text();
				if(testingProgramName!='Select'){
					$('#gs_testingProgramName').val(testingProgramName)
				}else{
					$('#testsTable')[0].clearToolbar();
				}
				loadTestAndTestCollectionsGrid();
			}
		});
		
	} 
	
	/**
	* Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15083 : Test Management Add Test Session page - misc UI changes
	* When user selects the assessment program (mandatory) [and testing program (optional)], the grid is loaded with 
	* data (if exists) by this method.
	* This method is called in click event of search filters button.
	*/
	function loadTestAndTestCollectionsGrid() {

		var assessmentProgramName = "";
		var testingProgramName = "";
		
		assessmentProgramName = $('#stsaAssessmentPrograms option:selected').text();		
		testingProgramName = $('#stsaTestingPrograms option:selected').text();
			
		if($('#stsaAssessmentPrograms').val()==''){
			assessmentProgramName = ''
		}
		
		if($('#stsaTestingPrograms').val()==''){
			testingProgramName = ''
		}
		
		//Clear the previous error messages
		setTimeout("aart.clearMessages()", 0);
		
		var pdata = grid.getGridParam("postData");
		pdata = {
				filters: ""
			};
		
		var myfilter = { groupOp: "AND", rules: [] };
		if(typeof assessmentProgramName  != 'undefined' && assessmentProgramName != '') {
			myfilter.rules.push({ field: "assessmentProgramName", op: "eq", data: assessmentProgramName });
		}		
		if(typeof testingProgramName  != 'undefined' && testingProgramName != '') {
			myfilter.rules.push({ field: "testingProgramName", op: "eq", data: testingProgramName });
		}
		pdata = (myfilter) ? { filters: JSON.stringify(myfilter)} : {};
		
		$grid = $('#testsTable'); 
		$grid.jqGrid('setGridParam',{
			datatype:"json", 
			url : 'findTestsAndTestCollections.htm?q=1', 					
			search: false,
			postData: pdata,
			gridComplete: function() {
				//very hacky, but fixes the issue of column headers not aligning with columns if user scrolled
				//columns before clicking search button.  I could not find the actual root cause, so treated
				//the symptom for now.
				$grid.closest(".ui-jqgrid-bdiv").scrollLeft(1);
				$grid.closest(".ui-jqgrid-bdiv").scrollLeft(0);				
				selectedCheckbox = null;
				selectedRadio = null;
			}
		});	
		$grid.trigger("reloadGrid",[{page:1}]);

	}
	
	/**
	* Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15083 : Test Management Add Test Session page - misc UI changes
	* Initially the test collection should not be loaded. When user selects the assessment program (mandatory) [and testing program (optional)],
	* the grid is loaded with data (if exists) by loadTestAndTestCollectionsGrid method.
	* Remove display of assement program name column
	* Rename Subject Category to Subject
	* The below method is being called from setupTestSession jsp upon load and upon select.
	*/
	function loadTestAndTestCollectionsData() {		
	    
	    $('#resultsSection').show();
	    $('#hideShowDLMPreviewId').show();
	    
	    var $grid = $("#testsTable");

        var autoCompleteUrl =  "getTestsAutoCompleteData.htm";
	    
	    var cmForAssessments = [
	        					{ name : 'id', index : 'id', formatter: idFormatter, unformat: idUnFormatter, width : 30, key: false,sortable : false, search : false, hidden: false, hidedlg : true },
	        					
	        					{ name : 'testCollectionId', index : 'testCollectionId', width : colWidth, sorttype : 'int', search : true, hidden: true, hidedlg : true },
	        					
	        					{ name : 'externalId', index : 'externalId', width : colWidth, sorttype : 'int', search : true, hidden: false, hidedlg : true },
	        					<security:authorize access="hasRole('QUALITY_CONTROL_COMPLETE')">
	        						{ name : 'testId', index : 'testId', width : colWidth, sorttype : 'int', search : true, hidden: false, hidedlg : true },
	        					</security:authorize>
		        				<security:authorize access="!hasRole('QUALITY_CONTROL_COMPLETE')">
	        						{ name : 'testId', index : 'testId', width : colWidth, sorttype : 'int', search : true, hidden: true, hidedlg : true },
	        					</security:authorize>
	        						
	        					{ name : 'name', index : 'name', formatter: nameFormatter, unformat: nameUnFormatter, width : colWidth, editable : true, editrules:{edithidden:true}, viewable: true, 
	        						searchoptions:{ sopt:['cn'], dataInit: function(elem) {
	        						   /*  $(elem).autocomplete({
	        						        source: autoCompleteUrl + '?fileterAttribute=name'
	        						    }); */
	        						    $(elem).autocomplete({
	        						        source: function(request, response) {
	        						            $.ajax({
	        						                url: autoCompleteUrl,
	        						                dataType: "json",
	        						                data: {
	        						                	fileterAttribute : 'name',
	        						                	term : request.term,
	        						                    testingProgramName : $('#stsaTestingPrograms option:selected').text()
	        						                },
	        						                success: function(data) {
	        						                    response(data);
	        						                }
	        						            });
	        						        }
	        						    });
	        						} 							
	        					}, sortable : true, search : true, hidden : false },
	        					<security:authorize access="hasRole('QUALITY_CONTROL_COMPLETE')">
	        						{ name : 'testInternalName', index : 'testInternalName', width : colWidth, sortable : true, search : true, hidden : false },
	        					</security:authorize>
	        						
	        					{ name : 'collectionName', index : 'collectionName', formatter: nameFormatter, unformat: nameUnFormatter, width : colWidth, sortable : true, search : true, hidden: true, hidedlg : true },
	        					
	        					{ name : 'assessmentProgramName', index : 'assessmentProgramName', width : colWidth,								
	        						 sortable : true, search : true, stype : 'select', searchoptions: { value : buildAssessmentProgramSearchSelect(), sopt:['eq'] }, hidden : true }, 
	        						 
	        					{ name : 'testingProgramName', index : 'testingProgramName', width : colWidth, 
	        						sortable : true, search : true, stype : 'select', searchoptions: { value : buildTestingProgramSearchSelect(), sopt:['eq'] }, hidden : false },	

	        					{ name : 'assessmentName', index : 'assessmentName', width : colWidth, 
	        						sortable : true, search : true, stype : 'select', searchoptions: { value : buildAssessmentSearchSelect(), sopt:['eq'] }, hidden : true },
	        					
	        					{ name : 'contentAreaName', index : 'contentAreaName', width : colWidth, 
	        						sortable : true, search : true, stype : 'select', searchoptions: { value : buildContentAreaSearchSelect(), sopt:['eq'] }, hidden : false },
	        					
	        					{ name : 'gradeCourseName', index : 'gradeCourseName', width : colWidth, 
	        						sortable : true, search : true, stype : 'select', searchoptions: { value : buildGradeCourseSearchSelect(), sopt:['eq'] }, hidden : false },
	        					<security:authorize access="hasRole('QUALITY_CONTROL_COMPLETE')">	
	        						{ name : 'gradeBandName', index : 'gradeBandName', width : colWidth,sortable : false, search : false, hidden : false },
	        					</security:authorize>
		        				<security:authorize access="!hasRole('QUALITY_CONTROL_COMPLETE')">	
	        						{ name : 'gradeBandName', index : 'gradeBandName', width : colWidth,sortable : false, search : false, hidden : true },
	        					</security:authorize>	        						
	        					<security:authorize access="hasRole('QUALITY_CONTROL_COMPLETE')">
	        						{ name : 'qcComplete', index : 'qcComplete', formatter: qcCompleteFormatter, unformat: qcCompleteUnFormatter, width : colWidth, 
	        							sortable : true, search : true, stype : 'select', searchoptions: { value : ':All;false:No;true:Yes', sopt:['eq'] }, hidden : false },
	        					</security:authorize>
	        					<security:authorize access="hasRole('HIGH_STAKES')">
	        						{ name : 'highStake', index : 'highStake', formatter:highStakeFormatter, unformat: highStakeUnFormatter, width : colWidth, 
	        								sortable : true, search : true, stype : 'select', searchoptions: { value : ':All;false:No;true:Yes', sopt:['eq'] }, hidden : false },
	        					</security:authorize>

	        					{ name : 'manualScoring', index : 'manualScoring', formatter: manualScoringFormatter, unformat: manualScoringUnFormatter, width : colWidth, sortable : false, search : false, hidden : true },							
	        					
	        					<security:authorize access="hasRole('QUALITY_CONTROL_COMPLETE')">
	        						{ name : 'previewNode', index : 'previewNode',formatter: nodeButtonFmatter, unformat: nodeButtonUnFmt,  width : colWidth, sortable : false, search : false, hidden : false },
	        					</security:authorize>
		        				<security:authorize access="!hasRole('QUALITY_CONTROL_COMPLETE')">
	        						{ name : 'previewNode', index : 'previewNode',formatter: nodeButtonFmatter, unformat: nodeButtonUnFmt,  width : colWidth, sortable : false, search : false, hidden : true },
	        					</security:authorize>	        						
	        					{ name : 'dlmAssociation', index : 'dlmAssociation', width : colWidth, sortable : false, search : false, hidden : true, hidedlg : true }
	        					
	        		];
	    
	    var myDefaultSearch = 'cn',        
        myColumnStateName = 'ColumnChooserAndLocalStorage6.colState6';        
        var myColumnsState;
        var isColState;
        firstLoad = true;
    	myColumnsState = restoreColumnState(cmForAssessments, myColumnStateName);
    	isColState = typeof (myColumnsState) !== 'undefined' && myColumnsState !== null;
	    if($("#testsTable").getGridParam('reccount') == undefined || $("#testsTable").getGridParam('reccount') < 1) {
	        	        
	        var grid_width = $('.kite-table').width();
			grid_width = 1031;
	        var colWidth = grid_width/4;
	       
	        
			//create the grid.
	        $('#testsTable').scb({
	        	mtype: "POST",
	        	width: grid_width,
	        	postData:  {									
					filters : isColState ? myColumnsState.filters : null,
				},
	            datatype: "local",	            
	            colNames: [
							'',
							'<fmt:message key="label.tests.search.testCollectionId"/>',
							'CB Test Id',
							'EP Test Id',							
							'<fmt:message key="label.setuptestsession.assessment.assessmentname"/>',
							<security:authorize access="hasRole('QUALITY_CONTROL_COMPLETE')">
							'Test Internal Name',
							</security:authorize>
							'CollectionName',
							'Assessment Program',
							'Testing Program',
							'Assessment',
							'<fmt:message key="label.tests.search.subject"/>',
							'<fmt:message key="label.tests.search.grade"/>',
							'Grade Band',
							<security:authorize access="hasRole('QUALITY_CONTROL_COMPLETE')">
								'QC Complete',
							</security:authorize>
							<security:authorize access="hasRole('HIGH_STAKES')">
								'High Stakes',
							</security:authorize>
							//'<div> <div class="questionMarkDiv">' + 'Manual Scoring </div>' + 
									'Manual Scoring <img class="questionMarkImg" title = "One or more questions might need to be scored manually" src="images/questionMark.png" />',									
							'<fmt:message key="label.tests.search.nodesPreview"/>',
							''
	            ],	                  	                     
	                       
	            colModel: cmForAssessments,	            	            
	            shrinkToFit: false,	          	
	          	height : 'auto',
	            width: grid_width,
	            pager : '#testsPager',
	            loadFilterDefaults: true,
	            rowNum : 10,
	            rowList : [ 10, 20, 30 ],
	            page: isColState ? myColumnsState.page : 1,
	    		search: isColState ? myColumnsState.search : false,
	    		sortname: isColState ? myColumnsState.sortname : 'name',
	 		    sortorder: isColState ? myColumnsState.sortorder : 'asc',
	 		    		sortable: {
	 			        	update: function() {
	 			        		saveColumnState.call($grid, $grid[0].p.remapColumns,myColumnStateName);
	 			        	}
	 			        },
	            viewrecords : true,
	            multiPageSelection : true,
	            emptyrecords : "<fmt:message key='label.common.norecordsfound'/>",	            
	            //changes for F477
	            //Changes for facelift
				multiselect : hasMultiSelectAccess,
	            toppager: false,
	            caption: '',
	            onSortCol : function(){
	            	saveColumnState.call($grid, $grid[0].p.remapColumns,myColumnStateName);
					//saveBrowserOptions();
				},
	            beforeSelectRow: function(rowid, e) {            	            	
	            	
	            	<c:if test="${hasQCPermission == false}">					
						var radio = $(e.target).closest('tr').find('input[type="radio"]');
		            	radio.attr('checked', 'checked');
		           	            		            	
		    	    	//Need to store the radio button value so that we can set it back, 
		    	    		//because JQGrid does not support automatic selection in memory.
		            	if(radio[0] != null && radio[0] != undefined){
		            		selectedTestId=[];			            		
			            	selectedRadio = radio[0].id;
			            	var rowData = jQuery("#testsTable").jqGrid('getRowData',$(e.target).closest('tr').attr('id'));      	
			            	selectedTestCollectionId = Number(rowData.testCollectionId);
			            	selectedTestId.push(Number(rowData.testId));
			            	arraySelectedTestId = JSON.stringify(selectedTestId);
		            	}			            	
		    	    	
		            	</c:if>	             	
	            	
					//In some cases the disabled attribute is coming as undefined which is causing problem for next button.					  
	            	if($('.assessmentsNextButton').attr('disabled') != undefined){
		            	$('.assessmentsNextButton').removeAttr('disabled');	            		
	            	}
					
	            	return true; //allow row selection
	            },
	            onSelectRow: function(id, status) {	            		        
	            		            	
	            	<c:if test="${hasQCPermission == true}">
	              
	             	testcollectionAray = {};
            		var testIdsArray = {};
            		var testIdCollectionObjectArray = new Array();
            		
		            var rowData = jQuery("#testsTable").jqGrid('getRowData',id);			            
		            testcollectionAray['testCollectionId']= Number(rowData.testCollectionId);
		            testcollectionAray['testName']=$(rowData.name).html();;         
		            selectedTestCollectionId =  Number(rowData.testCollectionId); 
	            		            
	            	//Add/remove items to/from selectedDAC array based on checkbox selection/deselection. 
	        		if(status) {	
	        			
	        			selectedTestId = $.grep(selectedTestId, function(value) {
	    					return value != id;
	    				});	    				       			
    		    			
	        			delete testcollectionArayKey[Number(rowData.testId)];	        			
		            	selectedTestId.push(id.replace('tr',''));
		            	testcollectionArayKey[Number(rowData.testId)]=testcollectionAray;	
		            	
	    			} else {
	    				
	    				//User unchecks.							
	    				selectedTestId = $.grep(selectedTestId, function(value) {
	    					return value != id;
	    				});
	  		            
	    				delete testcollectionArayKey[Number(rowData.testId)];	    			
	    			}

	        		//Check SelectAllCheckbox header on rowNum change.
	        		var recordCount = $("#testsTable").getGridParam('reccount');
	        		var checkboxChecckedCount = 0;
	        		$("input[type='checkbox']").each(function() {
	        			if(this.name != "" && 
	        					this.name.indexOf("jqg_testsTable_") != -1 && 
	        					this.checked) {
	        				checkboxChecckedCount++;
	        			}
	        	    });
	            	if(recordCount == checkboxChecckedCount) {
	            		$("#cb_testsTable").attr("checked", "checked");
	            	}  	
	            	
	               	testIdCollectionObjectArray.push(testcollectionArayKey);
					testIdsArray['testIds']=testIdCollectionObjectArray; 
		            arraySelectedTestId = JSON.stringify(testIdsArray);
		            if(selectedTestId.length>1) selectedTestCollectionId = selectedTestId[0];
		            selectedCheckbox = selectedTestId;		      
		            </c:if> 	         
	            	
	            },
	            onSelectAll: function(id, status) {
	            	
	            	<c:if test="${hasQCPermission == true}">
	            	  
	             	var testIdsArray = {};
            		var testIdCollectionObjectArray = new Array();
            		
	            	if(status) {
	    				//User checks the dac.
	    				for(var i=0; i<id.length; i++) {
	    					testcollectionAray = {};	                		
	    					selectedTestId.push(id[i].replace('tr',''));	    					
	    					var rowData = jQuery("#testsTable").jqGrid('getRowData',id[i]);			            
	    			        testcollectionAray['testCollectionId']= Number(rowData.testCollectionId);
	    			        testcollectionAray['testName']=$(rowData.name).html();;
	    			        testcollectionArayKey[Number(rowData.testId)]=testcollectionAray;   					
	    				}
	    			} else {
	    				//User unchecks.
	    				for(var i=0; i<id.length; i++) {
	    					var rowData = jQuery("#testsTable").jqGrid('getRowData',id[i]);
	    					selectedTestId = $.grep(selectedTestId, function(value) {
	    						return value != id[i];
	    					});   
	    					delete testcollectionArayKey[Number(rowData.testId)];
	    				}
	    			}  
	            	
	               	testIdCollectionObjectArray.push(testcollectionArayKey);
					testIdsArray['testIds']=testIdCollectionObjectArray; 
		            arraySelectedTestId = JSON.stringify(testIdsArray);
		            if(selectedTestId.length>1) selectedTestCollectionId = selectedTestId[0];
		            selectedCheckbox = selectedTestId;		            
		            </c:if> 
	            	
	            },
	            loadComplete: function () {
	            	
	            	var $this = $(this);
                    if (firstLoad) {
                        firstLoad = false;
                        if (isColState) {
                            $this.jqGrid("remapColumns", myColumnsState.permutation, true);
                        }
                        if (typeof (this.ftoolbar) !== "boolean" || !this.ftoolbar) {
                            // create toolbar if needed
                            $this.jqGrid('filterToolbar',
                                {stringResult: true, searchOnEnter: true, defaultSearch: myDefaultSearch});
                        }
                    }
                    saveColumnState.call($this, this.p.remapColumns,myColumnStateName);
                    var ids = $(this).jqGrid('getDataIDs');         
   		         var tableid=$(this).attr('id');      
   		            for(var i=0;i<ids.length;i++)
   		            {        
   		                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'testCollectionId')+ ' Check Box');
   		             	$('#jqg_'+tableid+'_'+ids[i]).removeAttr('aria-checked');
   		            }
   		            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
   		             $('#cb_'+tableid).attr('title','Assessment Grid Select All Check Box');
   		          	 $('#cb_'+tableid).removeAttr('aria-checked');
   		             $.each(objs, function(index, value) {         
   		              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
   		                    $(value).attr('title',$(nm).text()+' filter');
   		                 if ( $(value).is('select')) {
		                	   $(value).removeAttr("role");
		                    };
   		                    });
   		          $('td[id^="view_"]').on("click",function(){
		       		  if($(".EditTable.ViewTable tbody").find('th').length==0){
		     			$(".EditTable.ViewTable tbody").prepend("<tr style='border-bottom: 2pt solid #e9e9e9;'><th style='font-weight:bold'>Column Name</th><th style='font-weight:bold'>Column Value</th></tr>");	
		       		  }
		       		  });
		          $("input[name=selectedTestOrTc]").each(function(){
	   		         	$(this).attr('title', $(this).val()+ ' Radio button');
	   		     		});
		          $(".ui-jqgrid-bdiv tr").each(function(){
	   		         	$(this).attr('id', $(this).attr('id')+ 'tr');
	   		     		});
	            },
                resizeStop: function () {
                    saveColumnState.call($grid, $grid[0].p.remapColumns,myColumnStateName);
                },
	            gridComplete: function() {	            		            	
	    	    	//Need to store the radio button value so that we can set it back,
	    	    	//because JQGrid does not support automatic selection in memory.
	            	if(selectedRadio != null && selectedRadio != undefined){
	            		$('input[id='+selectedRadio + ']').attr('checked', 'checked');
	            	}

	            	$('.previewform').button();

	                <security:authorize access="!hasRole('PERM_VIEW_NODES')">
	                	jQuery("#testsTable").jqGrid('hideCol',["previewNode"]);
	                </security:authorize>
	                
	                var recs = parseInt($("#testsTable").getGridParam("records"));	                
	                
	                if (isNaN(recs) || recs == 0) {
					     $("#gbox_testsTable").hide();
					     $("#noReport").html('No records found.');
					 } else {
					     $("#gbox_testsTable").show();
					     $("#noReport").html('');
					 }
	                
	                <c:if test="${hasQCPermission == true}">
                		jQuery("#testsTable").jqGrid('hideCol',["id"]);
                	</c:if>
                	
                	<c:if test="${hasQCPermission == false}">
            			jQuery("#testsTable").jqGrid('showCol',["id"]);
            		</c:if>        
	                
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
	            
	        }); //end of jqgrid
	        
	        $('#testsTable')[0].clearToolbar();	
	        
	    } //end of jqgrid recordcount if condition
	    $.extend($.jgrid.search, {
		    multipleSearch: true,
		    multipleGroup: true,
		    recreateFilter: true,
		    closeOnEscape: true,
		    closeAfterSearch: true,
		    overlay: 0
		});
		$grid.jqGrid('navGrid', '#testsPager', {edit: false, add: false, del: false});
	} //end of loadTestAndTestCollectionsGrid() method
	//Deb 6/26 changing the data format: 
		/*
		assessmentProgramValues = ":" + data.assessmentPrograms[0].programName 
       	 			+ ":" + data.assessmentPrograms[0].programName;
		New Format:
			assessmentProgramValues = ":" + data.assessmentPrograms[0].programName;
		*/
	function getValues() {
		return $.ajax({
            url: 'getSelectAssessmentsDropdownData.htm',            
            dataType: 'json',
            type: "GET",
            async: true,
            success : function(data) {
            	if(data.assessmentPrograms.length === 1) {
            		assessmentProgramValues = ":" + data.assessmentPrograms[0].programName;
            	} else {
            		assessmentProgramValues = ":All";
            		for (i=0; i<data.assessmentPrograms.length; i++) {
               	 		assessmentProgramValues += ";" + data.assessmentPrograms[i].programName;
               	 	}
            	}
           	 	
           	 	testingProgramValues = ":All";
        	 	for (i=0; i<data.testingPrograms.length; i++) {
        	 		testingProgramValues += ";" + data.testingPrograms[i].programName 
        	 			+ ":" + data.testingPrograms[i].programName;
        	 	}
        	 	
        	 	assessmentValues = ":All";
           	 	for (i=0; i<data.assessments.length; i++) {
           	 		assessmentValues += ";" + data.assessments[i].assessmentName
           	 			+ ":" + data.assessments[i].assessmentName;
           	 	}
           	 	
           	 	gradeCourseValues = ":All";
        	 	for (i=0; i<data.gradeCourses.length; i++) {
        	 		gradeCourseValues += ";" + data.gradeCourses[i].name 
        	 			+ ":" + data.gradeCourses[i].name;
        	 	}
        	 	
        	 	contentAreaValues = ":All";
           	 	for (i=0; i<data.contentAreas.length; i++) {
           	 		contentAreaValues += ";" + data.contentAreas[i].name 
           	 			+ ":" + data.contentAreas[i].name;
           	 	}
            }
        });
	}
	
	 var buildAssessmentProgramSearchSelect = function() {        
	        return assessmentProgramValues;
	    };        
	    
	    var buildTestingProgramSearchSelect = function() {        
	        return testingProgramValues;
	    };
	    
	    var buildAssessmentSearchSelect = function() {        
	        return assessmentValues;
	    };
	    
	    var buildGradeCourseSearchSelect = function() {        
	        return gradeCourseValues;
	    };
	    
	    var buildContentAreaSearchSelect = function() {        
	        return contentAreaValues;
	    };

	$('[name=selectedTestOrTc]').on('click', function (e){
		var target = e.target;
		var rid = $(target).closest('input').prop('id');
		$('#testsTable').jqGrid('setSelection', rid);
	});
	
	//Custom formatter for id column. 
	function idFormatter(cellvalue, options, rowObject) {
		
		var htmlString = "";
		//For F477 Multiple Test selection changes
		<c:if test="${hasQCPermission == false}">
		
			//For QC admin users.
			<security:authorize access="hasRole('QUALITY_CONTROL_COMPLETE')">
				htmlString = '<input type="radio" id=' + options.rowId + ' name="selectedTestOrTc" value="' + rowObject[2] + '"/>';				
			</security:authorize>
	
	        //For non-QC admin users.
			<security:authorize access="!hasRole('QUALITY_CONTROL_COMPLETE')">
	        
		    	var canPreviewIndex = 12;
				<security:authorize access="hasRole('HIGH_STAKES')">
					canPreviewIndex = 13;
				</security:authorize>
		    
				//Check CanPreview value for true
				if(rowObject[canPreviewIndex] == "true") {
					htmlString = '<input type="radio" id=' + options.rowId + ' name="selectedTestOrTc" value="' + rowObject[2] + '"/>';
				} else {
					htmlString = '<input type="radio" id=' + options.rowId + ' name="selectedTestOrTc" value="' + rowObject[1] + '"/>';
				}
			</security:authorize>
			</c:if>
		
	    return htmlString;
	}
	
	
	//Custom unformatter for id column. 
	function idUnFormatter(cellvalue, options, rowObject) {
		 return;
	}
	
	
	//Custom formatter for qcComplete column. 
	function qcCompleteFormatter(cellvalue, options, rowObject) {
		//Check qc complete value for true
		if(rowObject[13] == "true")
			return "Yes";
		else 
			return "No";
	}
	
	
	//Custom unformatter for qcComplete column.
	function qcCompleteUnFormatter(cellvalue, options, rowObject) {
	    return;
	}
	
	
	//Custom formatter for highStake column. 
	function highStakeFormatter(cellvalue, options, rowObject) {
		
		var highStakesIndex = 12;
		
		//For QC admin users.
		<security:authorize access="hasRole('QUALITY_CONTROL_COMPLETE')">		
			highStakesIndex = highStakesIndex + 2;
		</security:authorize>
		
		//Check high Stakes value for true
		if(rowObject[highStakesIndex] == "true")
			return "Yes";
		else 
			return "No";
	}
	
	
	//Custom unformatter for highStake column.
	function highStakeUnFormatter(cellvalue, options, rowObject) {
	    return;
	}
	
	
	//Custom formatter for name column link. 
	function nameFormatter(cellvalue, options, rowObject) {
		var htmlString = "";
		//For QC admin users.
		<security:authorize access="hasRole('QUALITY_CONTROL_COMPLETE')">		
				htmlString = "<a href='javascript:openTestDialog(" + rowObject[1] + "," + rowObject[3] + ");'>" + rowObject[4] + "</a>";
		</security:authorize>

        //For non-QC admin users.
		<security:authorize access="!hasRole('QUALITY_CONTROL_COMPLETE')">       	
        	var canPreviewIndex = 12;
			<security:authorize access="hasRole('HIGH_STAKES')">
				canPreviewIndex = 13;
			</security:authorize>
			//Check CanPreview value for true
			if(rowObject[canPreviewIndex] == "true") {
				htmlString = "<a href='javascript:openTestDialog(" + rowObject[1] + "," + rowObject[3] + ");'>" + rowObject[4] + "</a>";
			} else {
				htmlString = rowObject[5];
			}
		</security:authorize>
		
	    return htmlString;
	}
	
	
	//Custom unformatter for name column link. 
	function nameUnFormatter(cellvalue, options, rowObject) {
	    return;
	}

	
	//Custom formatter for manualScoring column. 
	function manualScoringFormatter(cellvalue, options, rowObject) {
		
		var manuScoringIndex = 11;
		
		//For QC admin users.
		<security:authorize access="hasRole('QUALITY_CONTROL_COMPLETE')">		
			manuScoringIndex = manuScoringIndex + 2;
		</security:authorize>

        //For high stakes users.		
		<security:authorize access="hasRole('HIGH_STAKES')">
			manuScoringIndex = manuScoringIndex + 1;
		</security:authorize>		
		
		//Check ManualScoring value for true
		if(rowObject[manuScoringIndex] == "true")
			return "Yes";
		else 
			return "No";
	}
	
	
	//Custom unformatter for manualScoring column.
	function manualScoringUnFormatter(cellvalue, options, rowObject) {
	    return;
	}


	//Custom formatter for previewnode column link.
	function nodeButtonFmatter(cellvalue, options, rowObject) {
		var htmlString = "";
		
		var previewNodeIndex = 12;
		//For QC admin users.
		<security:authorize access="hasRole('QUALITY_CONTROL_COMPLETE')">		
			previewNodeIndex = previewNodeIndex + 2;
		</security:authorize>

        //For high stakes users.		
		<security:authorize access="hasRole('HIGH_STAKES')">
			previewNodeIndex = previewNodeIndex + 1;
		</security:authorize>
		
		previewNodeIndex++; // Expiredflag
		
		if(rowObject[previewNodeIndex] == "true"){
			htmlString = "<a href='javascript:openNodeDialog(" +
			rowObject[1] + ")'>Preview Nodes</a>";			
		} else {
			//do nothing
		}
	    return htmlString;
	}

	
	//Custom unformatter for previewnode column link.
	function nodeButtonUnFmt(cellvalue, options, rowObject) {
	    return;
	}
	
	function openTestDialog(testCollectionId, testId) {
		
		//Reverted the selected index back to 0, Here it was making the selected index as a object instead of 
			//previous numeric digit 0, which was casuing problem later in next button click.

		$('#setupTestSessionTabs li:eq(0) a').tab('show');
		$('#previewTestDiv').dialog({
			autoOpen: false,
			modal: true,
			//resizable: false,
			width: 1080,
			height: 655,
			title:'  ',
			open : function(ev, ui) {
				$(".ui-dialog").removeAttr("role");
			},
			close: function(ev, ui) {
				//Reverted the selected index back to 0, Here it was making the selected index as a object instead of 
					//previous numeric digit 0, which was casuing problem later in next button click.				
			    $('#setupTestSessionTabs li:eq(0) a').tab('show');
			    $("#testsTable").jqGrid('setSelection',testId);
			    $(this).html('');
			}
		}).load('previewTest.htm?&selectedTestCollectionId='+testCollectionId + '&selectedTestId='+testId).dialog('open');	
		$('#previewTestDiv').dialog({autoOpen: false}).dialog('widget').find('.ui-dialog-title').html('<span>&nbsp;</span>');
	}
	
	function openNodeDialog(testId) {
		
		//Reverted the selected index back to 0, Here it was making the selected index as a object instead of 
			//previous numeric digit 0, which was casuing problem later in next button click.
		$('#setupTestSessionTabs li:eq(0) a').tab('show');
		$('#previewTestDiv').dialog({
			autoOpen: false,
			modal: true,
			//resizable: false,
			width: 1080,
			height: 655,
			 _title: function(title) {title.html("&#160;");},
			open : function(ev, ui) {
					$(".ui-dialog").removeAttr("role");
				},
			close: function(ev, ui) {				
				//Reverted the selected index back to 0, Here it was making the selected index as a object instead of 
					//previous numeric digit 0, which was casuing problem later in next button click.				
			    $('#setupTestSessionTabs li:eq(0) a').tab('show');
			    $("#testsTable").jqGrid('setSelection',testId);
			    $(this).html('');
			}
		}).load('previewNodes.htm?&selectedTestCollectionId='+testId).dialog('open');
		
	    $('#previewTestDiv').dialog({autoOpen: false}).dialog('widget').find('.ui-dialog-title').html('<span>&nbsp;</span>');
	}


</script>

