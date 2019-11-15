<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/jsp/include.jsp" %>
 
 
 
<div>
	<div class ="panel_full noBorder">	
		 <div class="top_info">

		</div> <!-- /top_info -->
		
		<span class="error_message ui-state-error hidden scoreTestSessionError" id="scoreTestSessionError"></span> <br />
	    
	    <div class="table_wrap">
			<div class="kite-table scoreTestSession">
				<table id="scoreTestSession"  class="responsive"></table>
				<div id="scoreTestSessionPager" class="responsive"></div>
			</div>
		</div>
		<div id="taskStemOverlayScores">
	           <label id="taskStem"> </label>           
	    </div>
		<div id="progressScoreButtonDiv" class="floatRightDiv">
			<input id="refreshGrid" value ="false" type="hidden" class="hidden" />
	        <input id="viewRecordGrid" value ="false" type="hidden" class="hidden" />
	        <input id="columnChooserGrid" value ="false" type="hidden" class="hidden" />				
		</div>
		
	</div>
	
	<br />
	
</div>
<div id="rubricDiv"></div>
 <script>
 
	var performanceScoreStudentsTestIds = [];
	var progressScoreStudentsTestIds = [];
	var taskVar;
	
	function loadScoreTestSessionData() {

		if(($("#scoreTestSession").getGridParam('reccount') == undefined || 
				$("#scoreTestSession").getGridParam('reccount') < 1) || isRefresh) {

			
			
			 
			 var urlValue = "";
			
				 urlValue = 'scoring.htm';
				 $('#performanceScoreButtonDiv').show();
			 
			 $.ajax({
					url: urlValue,
					data: {
						testSessionId: ${testSessionId}
					},
					dataType: 'json',
					type: "POST",
					success: function(data) {
							 buildPerformanceScoringGrid(data);		 
			       }
			});
 		}
	}
		
	//Build grid for Performance monitoring
	function buildPerformanceScoringGrid(response) {
		//The gridComplete and onPaging events are so that a user can select students across multiple pages
	    var colNames = new Array();
	    var colModel = new Array();     
		var grid = $("#scoreTestSession");
	  	//create the grid.	        
        var grid_width = $('.kite-table').width();
	  	var column_width = "";
		if(grid_width == 100 || grid_width == 0){
			grid_width = 950;				
		}		
		column_width = grid_width/5;
		colNames.push("id");
		colModel.push({name:'rowId', index:'rowId',  width:column_width, align: 'center', sortable : false, key: true,hidden : true,viewable: false  });
		//Populate static columnnames.
	    colNames.push("Name");
	    //Populate static columnmodel.
		colModel.push({name:'studentName', index:'studentName',  width:column_width, align: 'center', sortable : true });
		//Populate static columnnames.
	    colNames.push("Student ID");
	    //Populate static columnmodel.
		colModel.push({name:'studentId', index:'studentId', width:column_width, align: 'center', sortable : true, editrules:{edithidden:true}, viewable: true, search : true, hidden : true });
		//Populate static columnnames.
	    colNames.push("Test ID");
	    //Populate static columnmodel.
		colModel.push({name:'testId', index:'testId', width:column_width, align: 'center', sortable : true, editrules:{edithidden:true}, viewable: true, search : true, hidden : true });
		 var f=0;
		
		//Populate dynamic columnnames and columnmodel.
		for(var i=0; i < response.sectionStatusColumnNames.length; i++) {
				var taskVariantId = response.sectionStatusColumnNames[i].substring((response.sectionStatusColumnNames[i].indexOf('-') + 1));
				var taskVariantPos = response.sectionStatusColumnNames[i].substring(response.sectionStatusColumnNames[i].indexOf(' '), response.sectionStatusColumnNames[i].indexOf('-'));
				taskVariantPos = taskVariantPos.trim();
				colNames.push('<a href="#" class="monitorQuestionHeaderColor" onClick="viewTaskStemScores(' + taskVariantId + ',' + taskVariantPos + ')" >' + taskVariantPos + '</a>');
				colModel.push({name:response.sectionStatusColumnNames[i], index:response.sectionStatusColumnNames[i], width:column_width, align: 'center', formatter: function (cellvalue, options, rowObject) {
					 var a = $("#scoreTestSession").jqGrid('getGridParam','colModel');
					 var id = $("#scoreTestSession").attr('id');
					 
				if(cellvalue=='T'){
					return '<img src="images/check.jpg" />';
				}
				if($.isNumeric(cellvalue )){
					var indexName = options.colModel.index;
					var scored = cellvalue;
					var colModel = grid[0].p.colModel;
					var variantId = colModel[i].name;
					taskVarId = indexName.substring((indexName.indexOf('-') + 1));
					 return '<a href="#" onClick="viewRubric(' + taskVarId + ',' + options.rowId + ',\'' + scored + '\')" >'+ cellvalue +'</a>';	
				} else {						
					var test = [];
					test = JSON.stringify(rowObject);
					
					if(cellvalue == 'Not Scored'){
						var taskVarId =""; 
						//alert(test.length);
					var indexName = options.colModel.index;
								var scored = cellvalue;
								var colModel = grid[0].p.colModel;
								var variantId = colModel[i].name;
								taskVarId = indexName.substring((indexName.indexOf('-') + 1));
								 return '<a href="#" onClick="viewRubric(' + taskVarId + ',' + options.rowId + ',\'' + scored + '\')" >'+ cellvalue +'</a>'; 
					}
					if(cellvalue==null || cellvalue=='-' || cellvalue.indexOf('/')>=0){
						return cellvalue;
					} else{
							return '<span style="color: #f00;">'+ cellvalue +'</span>';
					}
				}
				}, unformatter: function (cellvalue){
					return cellvalue;
					},sortable : true, /*stype : 'select', searchoptions : { value:{'':'','Not Scored':'Not Scored','Not Completed':'Not Completed'}, sopt:['eq'] },*/ search : false});			         
		}
		$grid= $('#scoreTestSession'); 
		myDefaultSearchAuto = 'cn',
		  myColumnStateNameAuto = 'ColumnChooserAndLocalStorage8.colState8';
		    
		    var myColumnsStateAuto;
		    var isColStateAuto;
		   
		    firstLoadAuto = true;
		 
		 
		myColumnsStateAuto = restoreColumnState(colModel,myColumnStateNameAuto);
		isColStateAuto = typeof (myColumnsStateAuto) !== 'undefined' && myColumnsStateAuto !== null;
	    jQuery("#scoreTestSession").scb({
	        datatype: "local",
	        data: response.students,
	        colNames: colNames,        
	        colModel: colModel,   
	        postData: isColStateAuto ? { filters: myColumnsStateAuto.filters } : {},
	        //viewsortcols : [ false, 'vertical', true ],
	        height : 'auto',
	        width: grid_width,
	        shrinkToFit: false,
	        rowNum : 10,
	        rowList : [ 10, 20, 30 ],
	        page: isColStateAuto ? myColumnsStateAuto.page : 1,
	    	search: isColStateAuto ? myColumnsStateAuto.search : false,
	        sortname: isColStateAuto ? myColumnsStateAuto.sortname : '',
	        sortorder: isColStateAuto ? myColumnsStateAuto.sortorder : 'asc',
		 		    		sortable: {
		 			        	update: function() {
		 			        		saveColumnState.call($grid, $grid[0].p.remapColumns,myColumnStateName);
		 			        	}
		 			        },
	        pager: '#scoreTestSessionPager',
	        viewrecords : true,
	        emptyrecords : "<fmt:message key='label.common.norecordsfound'/>",
	        altRows : true,
	        hoverrows : true,
	        multiselect : false,                        
	        onPaging: function() {
	            var pagerId = this.p.pager.substr(1); // get pager id like "pager" 
	            var pageValue = $('input.ui-pg-input', "#pg_" + $.jgrid.jqID(pagerId)).val(); 
	            var saveSelectedRows = $(this).getGridParam('selarrrow'); 
	            //Store any selected rows 
	            $(this).data(pageValue.toString(), saveSelectedRows);
	        },
	        loadComplete: function () {
                var $this = $(this);
                if (firstLoadAuto) {
                    firstLoadAuto = false;
                    if (isColStateAuto) {
                        $this.jqGrid("remapColumns", myColumnsStateAuto.permutation, true);
                    }
                    if (typeof (this.ftoolbar) !== "boolean" || !this.ftoolbar) {
                        // create toolbar if needed
                        $this.jqGrid('filterToolbar',
                            {stringResult: true, searchOnEnter: true, defaultSearch: myDefaultSearchAuto});
                    }
                }
                ($this, myDefaultSearchAuto);
                saveColumnState.call($this, this.p.remapColumns, myColumnStateNameAuto);
                var tableid=$(this).attr('id'); 
                var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
                $.each(objs, function(index, value) {         
		              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
		                    $(value).attr('title',$(nm).text()+' filter');  
		                    if ( $(value).is('select')) {
			                	   $(value).removeAttr("role");
			                    };
		                    });
            },
            resizeStop: function () {
                saveColumnState.call($gridAuto, $gridAuto[0].p.remapColumns,myColumnStateNameAuto);
            },	
	        gridComplete: function() {
		        var recs = parseInt($("#scoreTestSession").getGridParam("records"));
				if (isNaN(recs) || recs == 0) {
				     //Set min height of 1px on no records found
				     $('.jqgfirstrow').css("height", "1px");
				 }
	        }
	    });
	    $grid.jqGrid('navGrid', '#scoreTestSessionPager', {edit: false, add: false, del: false});
	}
	
	function viewRubric(taskVariantId, rowId, cellvalue) {
		$('#rubricDiv').dialog("open");	
		var grid = $("#scoreTestSession");
	 	var gridRow = $("#scoreTestSession").jqGrid('getRowData',rowId);
        var studentId = gridRow['studentId'];
        var studentName = gridRow['studentName'];
        var testId = gridRow['testId']; 
		var dialogTile = '<div style="float:left;">Student Name: ' +studentName + ' <br>Score: <span id="scoreVal">'+ cellvalue + '</span><br></div><div style="float:right;Display:none;" id="ScorerName">Scorer Name: ${user.firstName} ${user.surName}<br> <span id="dateVal"></span></div><div style="clear:both"></div>';
          $('#rubricDiv').dialog({
			autoOpen: false,
			modal: true,
			width: 1087,
			height: 500,			
			title: dialogTile,
			create: function(event, ui) { 
			    var widget = $(this).dialog("widget");
			},
			beforeClose: function(event, ui) {
				return close();
			},
		}).load('rubricScoringView.htm?&studentId='+studentId+'&taskVariantId='+taskVariantId+'&testId='+testId).dialog('open'); 
		
	}
	function viewTaskStemScores(taskVariantId, taskVariantPos) {
		$.ajax({	
			url: "getTaskVariantData.htm",
			data: {			
				taskVariantId: taskVariantId			
			},
			datatype: 'json',
			type: "POST",
			success: function(data) {
				$('#taskStemOverlayScores').html("Question " + taskVariantPos + ": " + data);
				$('#taskStemOverlayScores').dialog("open");
				setTimeout("closeQuestionView()", 10000);				
			}
		});
	}
	$('#taskStemOverlayScores').dialog({
		height: 500,
	    width: 600,
	    modal: true,
	    autoOpen: false
	});
	closeQuestionView = function() {
		$('#taskStemOverlayScores').dialog("close");
	};
	
 </script>