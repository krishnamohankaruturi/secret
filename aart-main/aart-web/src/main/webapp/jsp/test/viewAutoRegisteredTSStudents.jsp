<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>


<div class="panel_full noBorder">
	
	<input id="detailViewCaption" value ="View Test Session Detail" type="text" class="hidden" />
	<input id="saveGrid" value ="${saveGrid}" type="text" class="hidden" />		
	<input id="hasSpecialCircumstanceApprovalPermission" value = "${hasSpecialCircumstanceApprovalPermission}" type="hidden" />
	<input id="specialCircumstanceApprovalVisible" value = "${specialCircumstanceApprovalVisible}" type="hidden" />
	<input id="stateHasRestrictedCodes" value = "${stateHasRestrictedCodes}" type="hidden" />
	<input id="isExcludeAdmins" value="${isExcludeAdmins}" type="hidden" />
	<!-- In below select component attribute "class="hidden" used to fix the IE8 browser issue. -->
	<select id="orgChildrenIds" multiple="multiple" class="hidden">
		<c:forEach var="orgChildrenId" items="${orgChildrenIds}">
		    <option value="${orgChildrenId}" selected="selected"></option>
		</c:forEach>
	</select>
		
	<!-- In below input component attribute "class="hidden" used to fix the IE8 browser issue. -->	
	<input id="loggedInUserId" value ="${user.id}" type="text" class="hidden" />	
	<input id="refreshGridManageTestMonitor" value ="false" type="hidden" class="hidden" />
    <input id="viewRecordGridManageTestMonitor" value ="false" type="hidden" class="hidden" />
    <input id="columnChooserGrid1" value ="false" type="hidden" class="hidden" />	

	<div id="ARSTmessages" class="messages">
    	<span id="select_student_error" class="info_message ui-state-error hidden"><fmt:message key='error.select.students.tickets' /></span>
    	<span id="save_specialcircumstances" class="save_specialcircumstances info_message ui-state-highlight successMessage hidden">Student's special circumstance reason has been saved for this assessment</span>
    	<span id="remove_specialcircumstances" class="save_specialcircumstances info_message ui-state-highlight successMessage hidden">Student's special circumstance reason has been removed for this assessment.</span>
    	<span id="save_specialcircumstances_required" class="save_specialcircumstances info_message ui-state-highlight successMessage hidden">Student's special circumstance reason is required.</span>
 	</div>
         	
	<div id="noAutoStuReportuReport" class="none" style="width:100%"></div>	

	<div class="top_info">
 		<div id="viewTicketsDiv" class ="viewTicketsDiv hidden" style="text-align:right;">                 
			<input type="button" id="viewTicketsTopButton" value="<fmt:message key='label.button.viewtickets'/>" class="panel_btn nextButton"/>
		</div>
	</div>
	<div id="dialog-confirm"></div>	
	<div class="confirmSCStateConfirmationDialog"></div>		 
	<div class ="table_wrap">
		<div class="kite-table">
			<table id="autoRegisteredTSStudentsTableId"  class="responsive"></table>
			<div id="pautoRegisteredTSStudentsTableId" class="responsive"></div>
			<br/>
			<img alt="View Braille Test ID" style="border:0px solid;" height="20" width="26" align="left" src="images/test/braille-icon.png"/>View Braille Test ID
		</div>
	</div>

	<br />	
</div>


<script>

	//Global variable for storing studentId's
	var selectedStudents = [];
	var index = 0;
	var specialCircumstanceConfirmationList = [];
	
	function setApprovalAction(element, isApprovalAction) {
		$(element).data('isApprovalAction', isApprovalAction);
	}
	function setRequestAction(studentId) {
		$("#specialCircumstanceApprovalListSelect"+studentId).data('isApprovalAction', 'false');
	}
	//This method will be called on test session name click.
	function loadAutoRegisteredTSStudents() {		
		
		var testSessionId = ${testSessionId};
		var assessmentProgramName= '${assessmentProgramName}';
		var ticketsDisplay= '${ticketsDisplay}';
		var isHidden = (ticketsDisplay == 'true') ? false : true;
		if(!isHidden)
			$('#viewTicketsDiv').removeClass("hidden");
		else
			$('#viewTicketsDiv').addClass("hidden");
		var testSessionName= '${testSessionName}';
		var count=0;
		specialCircumstanceConfirmationList = [
			<c:forEach items="${specialCircumstanceList}" var="specialCircumstance">
				{id: "${specialCircumstance.id}", requireConfirmation: "${specialCircumstance.requireConfirmation}"},
			</c:forEach>
        ];
		
		var specialCircumstancesHeader = 'Special Circumstance';
		
		$("#ARSTmessages").hide();
		$('#autoRegisteredTestSessionsDiv').hide();
		$('#autoRegisteredTSStudentsDiv').show();
		//$('#breadCrumMessage').text("Test Coordination - " + testSessionName);
		$('#testCoordinationBackDiv').removeClass("hidden");
		selectedStudents = [];
		
		var grid_width = $('.kite-table').width();
		if(grid_width == 100 || grid_width == 0) {
			grid_width = 980;				
		}
		else if(specialCircumstancesHeader == '')
			grid_width=788;
		
        var cell_width = grid_width/5;
		var gridParam;
	    getColumnIndexByName = function (grid, columnName) {
            var cm = grid.jqGrid('getGridParam', 'colModel'), i, l = cm.length;
            for (i = 0; i < l; i++) {
                if (cm[i].name === columnName) {
                    return i; // return the index
                }
            }
            return -1;
        };
		//Save the grid parameters when coming to this page from test session name link click.
		//saveARTSGridParameters($("#autoRegisteredTestSessionsTableId"));
		
		//Need to set this in the session so that loggedInUserId would be used as a key to store the orgs.
		window.sessionStorage.setItem("loggedInUserId", $('#loggedInUserId').val() );
		var isWebkit = 'WebkitAppearance' in document.documentElement.style && !/Edge/.test(navigator.userAgent);
		var autoCompleteUrl = "getARStudentsAutoCompleteData.htm?testSessionId=" + testSessionId;
		var cmForAutoRegisteredStudents = [

				{ name : 'id', index : 'id', width : cell_width, search : true, hidden: true, },
				
				{ name : 'stateStudentIdentifier', index : 'stateStudentIdentifier', width : cell_width + 40, formatter: pnpBraillelinkFormatter, unformat: pnpBraillelinkUnFormatter, search : true, hidden: false,
					searchoptions: { sopt:['cn'], dataInit: function(elem) {
				    	$(elem).autocomplete({
				        	source: autoCompleteUrl + '&filterAttribute=stateStudentIdentifier'
				    	});
					} }
				},
				
				{ name : 'legalFirstName', index : 'legalFirstName', width : cell_width, sortable : true, search : true, hidden : false,
					searchoptions: { sopt:['cn'], dataInit: function(elem) {
				    	$(elem).autocomplete({
				        	source: autoCompleteUrl + '&filterAttribute=legalFirstName'
				    	});
					} }
				},		   								   					
				
				{ name : 'legalLastName', index : 'legalLastName', width : cell_width, sortable : true, search : true, hidden : false,
					searchoptions: { sopt:['cn'], dataInit: function(elem) {
				    	$(elem).autocomplete({
				        	source: autoCompleteUrl + '&filterAttribute=legalLastName'
				    	});
					} }
				},
				{ name : 'printTicket', index : 'printTicket',  width : cell_width - 80, formatter: printARStudentTicketLinkFormatter, unformat: printARStudentTicketLinkUnFormatter,
				 	sortable : false, search : false, hidden : isHidden, hidedlg : true },
				
			<security:authorize access="hasRole('VIEW_INTERIM_THETA_VALUES')">
				{ name : 'studentTestId', index : 'studentTestId', width : cell_width, sortable : true, search : true, hidden : false,
						searchoptions: { sopt:['cn'], dataInit: function(elem) {
					    	$(elem).autocomplete({
					        	source: autoCompleteUrl + '&filterAttribute=studentTestId'
					    	});
						} }
				},
				
				{ name : 'testStatus', index : 'testStatus', width : cell_width, sortable : true, search : true, hidden : false,
					searchoptions: { sopt:['cn'], dataInit: function(elem) {
				    	$(elem).autocomplete({
				        	source: autoCompleteUrl + '&filterAttribute=testStatus'
				    	});
					} }
				},
			
				{ name : 'interimTheta', index : 'interimTheta', width : cell_width, sortable : true, search : true, hidden : false,
					searchoptions: { sopt:['cn'], dataInit: function(elem) {
				    	$(elem).autocomplete({
				        	source: autoCompleteUrl + '&filterAttribute=interimTheta'
				    	});
					} }
				},
				
				{ name : 'pnpBrailleSelected', index : 'pnpBrailleSelected', width : cell_width, sortable : false, search : true, hidden: true },
				
			</security:authorize>
			<c:if test="${fn:length(specialCircumstanceList) > 0 }">
				<security:authorize access="hasRole('HIGH_STAKES_SPL_CIRCUM_CODE') || hasRole('HIGH_STAKES_SPL_CIRCUM_CODE_SEL')">
					{ name : 'specialCircumstance', hideglg: true, search: false, index : 'specialCircumstance', width : cell_width + 50,
						formatter: specialCircumstanceFormatter, unformat: specialCircumstanceUnFormatter,
				 		sortable : false, hidden : false, hidedlg : true },
					{ name: 'act', hideglg: true, search: false, index: 'act', align: 'center', width: (isWebkit ? 70 : 75), align: 'center', sortable: false, formatter: 'actions'},
					{ name : 'specialCircumstanceStatus', hideglg: true, search: false, index : 'specialCircumstanceStatus', width : cell_width + 50, 
						formatter: specialCircumstanceStatusFormatter, unformat: specialCircumstanceStatusUnFormatter,
				 		sortable : false, hidden : false, hidedlg : true }
				</security:authorize>
				<c:if test="${specialCircumstanceApprovalVisible}">
					<security:authorize access="hasRole('HIGH_STAKES_SPL_CIRCUM_CODE_SEL')">
						,{ name : 'specialCircumstanceApproval', hideglg: true, search: false, index : 'specialCircumstanceApproval', width : cell_width + 50, 
						formatter: specialCircumstanceApprovalFormatter, unformat: specialCircumstanceApprovalUnFormatter,
					 	sortable : false, hidden : false, hidedlg : true }
					</security:authorize>
				</c:if>
			</c:if>
			];
		
		var $grid = $("#autoRegisteredTSStudentsTableId");
		var myDefaultSearchStu = 'cn',
	    myColumnStateNameStu = 'ColumnChooserAndLocalStorage10.colState';
	    
	    var myColumnsStateStu;
	    var isColStateStu;
	    firstLoadStu = true;

		myColumnsStateStu = restoreColumnState(cmForAutoRegisteredStudents,myColumnStateNameStu);
		isColStateStu = typeof (myColumnsStateStu) !== 'undefined' && myColumnsStateStu !== null;
		
		//Capture the mouse position for Loading message display.
		$(document).mousemove(function(e) {
			mousePosition = e.pageY;				
		}); 
				

		//Unload the grid before each request.
		$('#autoRegisteredTSStudentsTableId').jqGrid("GridUnload");

		//JQGRID
		jQuery("#autoRegisteredTSStudentsTableId").scb({
			url : 'getAutoRegisteredTSStudents.htm?q=1',
			postData :  {
				orgChildrenIds : function() {
					return $('#orgChildrenIds').val();
				},
				filters : isColStateStu ? myColumnsStateStu.filters : null,
			},
			mtype: "POST",
			datatype : "json",
			width: grid_width,
			colNames : [
	   					'Id', 'State Student Identifier', 'First Name', 'Last Name', 'Tickets'
	   					<security:authorize access="hasRole('VIEW_INTERIM_THETA_VALUES')">
	   						, 'Test ID', 'Status', 'Interim Theta', 'PNP Braille'
	   				  	</security:authorize>
	   					<c:if test="${fn:length(specialCircumstanceList) > 0 }">	
	   						<security:authorize access="hasRole('HIGH_STAKES_SPL_CIRCUM_CODE') || hasRole('HIGH_STAKES_SPL_CIRCUM_CODE_SEL')">
		   						, 'Special Circumstance', 'Save', 'Special Circumstance Status'
	   						</security:authorize>
		   					<c:if test="${specialCircumstanceApprovalVisible}">
			   					<security:authorize access="hasRole('HIGH_STAKES_SPL_CIRCUM_CODE_SEL')">
			   					, 'Special Circumstance Approval'
			   					</security:authorize>
		   					</c:if>
	   					</c:if>
	   		           ],
	   		colModel : cmForAutoRegisteredStudents,
			
			rowNum : 10,
			rowList : [ 5,10, 20, 30, 40, 60, 90 ],
			height : 'auto',
			pager : '#pautoRegisteredTSStudentsTableId',
			viewrecords : true,
			viewable : false,
			multiselect: true,
			page: isColStateStu ? myColumnsStateStu.page : 1,
			search: isColStateStu ? myColumnsStateStu.search : false,
	        sortname: isColStateStu ? myColumnsStateStu.sortname : 'legallastname,legalfirstname',
	        sortorder: isColStateStu ? myColumnsStateStu.sortorder : 'asc',
    		sortable: {
	        	update: function() {
	        		saveColumnState.call($grid, $grid[0].p.remapColumns,myColumnStateNameStu);
	        	}
	        },
			caption : "",
			grouping : false,
			shrinkToFit : false,
			groupingView : {
				groupField : [ '' ],
				groupColumnShow : [ false ],
				groupText : [ '<b>{0}</b>' ],
				groupCollapse : false,
				groupOrder : [ 'asc' ],
				groupSummary : [ true ],
				groupDataSorted : true
			},
			userDataOnFooter : true,
			loadBeforeSend: function () {
				//Code to position "Loading...." message.
		    	var gridIdAsSelector = $.jgrid.jqID(this.id),
		        $loadingDiv = $("#load_" + gridIdAsSelector),
		        $gbox = $("#gbox_" + gridIdAsSelector);
		    },
		    beforeRequest: function() {
		    	if(ticketsDisplay == 'false')
		    		$("#autoRegisteredTSStudentsTableId").jqGrid('hideCol','printTicket');
				else
					$("#autoRegisteredTSStudentsTableId").jqGrid('showCol','printTicket');
				
		    	$(this).setGridParam({postData: {testSessionId : function() { return testSessionId} } });
		    	
		    	//Set the page param to lastpage before sending the request when 
				  //the user entered current page number is greater than lastpage number.
				var currentPage = $(this).getGridParam('page');
                var lastPage = $(this).getGridParam('lastpage');
                
                 if (lastPage!= 0 && currentPage > lastPage) {
                	 $(this).setGridParam('page', lastPage);
                	$(this).setGridParam({postData: {page : lastPage}});
                }
		    },  
            resizeStop: function () {
                saveColumnState.call($grid, $grid[0].p.remapColumns,myColumnStateNameStu);
            },
		    gridComplete: function() {
		    	 var $this = $(this);
	                if (firstLoadStu) {
	                    firstLoadStu = false;
	                    if (isColStateStu) {
	                        $this.jqGrid("remapColumns", myColumnsStateStu.permutation, true);
	                    }
	                    if (typeof (this.ftoolbar) !== "boolean" || !this.ftoolbar) {
	                        // create toolbar if needed
	                        $this.jqGrid('filterToolbar',
	                            {stringResult: true, searchOnEnter: true, defaultSearch: myDefaultSearchStu});
	                    }
	                }
	                refreshSerchingToolbar($this, myDefaultSearchStu);
	                saveColumnState.call($this, $this[0].p.remapColumns,myColumnStateNameStu);
		    	//saveGridParameters($("#autoRegisteredTSStudentsTableId"));
		    	
		    	//Retrieve any previously stored rows for this page and re-select them.
	        	if(selectedStudents) {
	        		$.each(selectedStudents, function (index, value) {
	        			$("#autoRegisteredTSStudentsTableId").setSelection(value, false);
	        		});
	        	}
		    	
			    var recs = parseInt($("#autoRegisteredTSStudentsTableId").getGridParam("records"));
				if (isNaN(recs) || recs == 0) {
				     $("#gbox_autoRegisteredTSStudentsTableId").hide();
				     $("#noAutoStuReport").html('No student records found.');
				     
				     //Set min height of 1px on no records found
				     $('.jqgfirstrow').css("height", "1px");
				 } else {
				     $("#gbox_autoRegisteredTSStudentsTableId").show();
				     $("#noAutoStuReport").html('');
				 }
		    },
		    onSelectRow: function(id, status) {
        		//Add/remove items to/from selectedStudents array based on checkbox selection/deselection. 
        		if(status) {
					//User checks the student.
					selectedStudents.push(id);
				} else {
					//User unchecks the student.
					selectedStudents = $.grep(selectedStudents, function(value) {
						  return value != id;
					});	
				}
        		
        		
        		//Check SelectAllCheckbox header on rowNum change.
        		var recordCount = $("#autoRegisteredTestSessionsTableId").getGridParam('reccount');
        		var checkboxChecckedCount = 0;
        		$("input[type='checkbox']").each(function() {
        			if(this.name != "" && 
        					this.name.indexOf("jqg_autoRegisteredTSStudentsTableId_") != -1 && 
        					this.checked) {
        				checkboxChecckedCount++;
        			}
	    	    });
	        	if(recordCount == checkboxChecckedCount) {
	        		$("#cb_autoRegisteredTSStudentsTableId").attr("checked", "checked");
	        	}
			},
			onSelectAll: function(id, status) {
				if(status) {
					//User checks the students.
					for(var i=0; i<id.length; i++) {
						selectedStudents.push(id[i]);
					}
				} else {
					//User unchecks the students.
					for(var i=0; i<id.length; i++) {
						selectedStudents = $.grep(selectedStudents, function(value) {
							  return value != id[i];
						});	
					}
				}
			},
			loadComplete: function (data) {
				var grid = $("#autoRegisteredTSStudentsTableId");
	            var iCol = getColumnIndexByName(grid, 'act');
	            var testStatusColumn = getColumnIndexByName(grid, 'testStatus');
	            $(this).find("div.ui-inline-del>span")
	            .removeClass("ui-icon-trash")
	            .addClass("hidden");
	            $(this).find("div.ui-inline-edit>span")
	            .removeClass("ui-icon-pencil")
	            .addClass("hidden");
	            var grid_ids = grid.jqGrid('getDataIDs');
	            var row =0;
	            $(this).find(">tbody>tr.jqgrow>td:nth-child(" + (iCol + 1) + ")").each(function() {
	            	var studentId = $(this).parent().attr('id');
	                var ssscValue = $(this).parent().find('#sscImg'+studentId).attr('alt');
	                var testStatus = $(this).parent().find(':nth-child('+(testStatusColumn+1)+')').text();
	                var hasSpecialCircumstanceApprovalPermission = $('#hasSpecialCircumstanceApprovalPermission').val();
	                // attach save event only when necessary
	                var isExcludeAdmins = $('#isExcludeAdmins').val();
	                	$("<div>", {
	                        title: "Save",
	                        id: "Save"+studentId,
	                        mouseover: function() {
	                            $(this).addClass('ui-state-hover');
	                        },
	                        mouseout: function() {
	                            $(this).removeClass('ui-state-hover');
	                        },
	                        click: function(e) {
	                        	e.preventDefault();
	                        	e.stopPropagation();
	                        	var studentId = $(e.target).closest("tr.jqgrow").attr("id");
	                        	var specialCircumstanceValue = $("#specialCircumstanceListSelect"+studentId).val();	
	                        	var specialCircumstanceApprovalValue = '';
	                        	var isApprovalAction = 'false';
	                        	if($("#specialCircumstanceApprovalListSelect"+studentId).length > 0){
	                        		isApprovalAction = ($("#specialCircumstanceApprovalListSelect"+studentId).data('isApprovalAction'));
	                        		if(isApprovalAction == undefined){
	                        			isApprovalAction = 'false';
	                        		}
	                        		specialCircumstanceApprovalValue = $("#specialCircumstanceApprovalListSelect"+studentId).val();
	                        	}
	                        	
	                        	var myClass = $(this).attr("class");
	                        	var rowNum = myClass.split("save_")[1].split(" ")[0];

	                        	var checkStateConfirmation = false;
	                        	
	                        	var gridData= grid.jqGrid('getRowData');
          					    var gridRow=gridData[rowNum];
          					    checkStateConfirmation = isApprovalRequired(specialCircumstanceValue, gridRow);
          					    if(null != specialCircumstanceValue) {
	                        		if(checkStateConfirmation==true && isApprovalAction === 'false')
	                        		{
	                        			$('.confirmSCStateConfirmationDialog').dialog({
	                        				resizable: false,
	                        				width: 700,
	                        				modal: true,
	                        				autoOpen:false,			
	                        				title: "Special Circumstance Code Selection Approval Required",
	                        				buttons: {
                        					  "OK": function() {
	                        					  saveSpecialCircumstance(testSessionId,studentId,specialCircumstanceValue, rowNum, checkStateConfirmation,
                        							  specialCircumstanceApprovalValue, isApprovalAction);
	                        					  $("#specialCircumstanceListSelect"+gridRow.id).data('cellvalue', specialCircumstanceValue);
	                        					  $(this).dialog("close");		
	                        				    },
	                        				  Cancel: function() {
	                        					  $("#specialCircumstanceListSelect"+gridRow.id).val($("#specialCircumstanceListSelect"+gridRow.id).data('cellvalue'));
	                        					  $(this).dialog("close");	
	                        				    }
	                        				}
	                        			});
	                        			$('.confirmSCStateConfirmationDialog');
	                        			$('.confirmSCStateConfirmationDialog').html("The Special Circumstance code selection requires approval by a state-level administrator. "
                        					+"Application of the code selections for the  student will be held in Pending status while the request is reviewed."
                        					+"<p>Click the OK button to continue, or Cancel to return to the Test Coordination page.</p>");
	                        			$('.confirmSCStateConfirmationDialog').dialog("open");
	                        		}
	                        		else 
	                        		{
	                        			saveSpecialCircumstance(testSessionId,studentId,specialCircumstanceValue, rowNum, checkStateConfirmation, 
	                        				specialCircumstanceApprovalValue, isApprovalAction);
	                        			$("#specialCircumstanceListSelect"+gridRow.id).data('cellvalue', specialCircumstanceValue);
	                        		}	
	                        	}
	                        }
	                    }
	                  ).css({"margin": "5px", "float": "center", "cursor": "pointer"})
	                   .addClass("ui-pg-div ui-inline-custom save_"+row)
	                   .append('<img src="images/save_btn.png" alt="save" class="img_'+row+'" height="20" width="20"></img>')
	                   .prependTo($(this).children("div"));
	                	// Per 13412, Initialize the action to be taken if sc code is approved or not approved.
		            	var scApprovalValue = data.rows[row].cell[11];
		            	if(scApprovalValue !=='' && (scApprovalValue === 'Approved' || scApprovalValue === 'Not Approved'
		            		|| scApprovalValue === 'Pending Further Review')){
		            		$("#specialCircumstanceApprovalListSelect"+studentId).data('isApprovalAction', true);
		            	}
	                	row++;
	                });
		            var rows= grid.jqGrid('getRowData');
		            for(var i=0;i<rows.length;i++){
		                var row=rows[i];
		                $("#specialCircumstanceListSelect"+row.id).val($("#specialCircumstanceListSelect"+row.id).data('cellvalue'));
		            }
					var ids = $(this).jqGrid('getDataIDs');         
			        var tableid=$(this).attr('id');      
		            for(var i=0;i<ids.length;i++)
		            {         
		                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'legalFirstName') +' '+$(this).getCell(ids[i], 'legalLastName')+ ' Check Box');
		                $('#jqg_'+tableid+'_'+ids[i]).removeAttr('aria-checked');
		            }
		            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
		            $('#cb_'+tableid).attr('title','Student Grid All Check Box');
		            $('#cb_'+tableid).removeAttr('aria-checked');
		            $.each(objs, function(index, value) {         
		             	var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
		                $(value).attr('title',$(nm).text()+' filter');                          
		            });
				}
		});

		jQuery("#autoRegisteredTSStudentsTableId").jqGrid(
				'groupingRemove', false);
		
		
		function pnpBraillelinkFormatter(cellvalue, options, rowObject) {
			var htmlString = "";
			
			if(rowObject[8] !== undefined && rowObject[8] == 'true') {				
				var studentNameForPopUp = '';
				studentNameForPopUp += rowObject[2];
				if(studentNameForPopUp !=='' && studentNameForPopUp.length >0){
					studentNameForPopUp += ', '+rowObject[3];
				}else{
					studentNameForPopUp += rowObject[3];
				}				
				
				htmlString = '<a href="javascript:void(0)" onclick="javascript:showDialogTest('+rowObject[0]+', '+rowObject[5]+', \''+studentNameForPopUp+'\');">' + '<img alt="View Braille Test ID" style="border:0px solid;" height="20" width="20" align="left" src="images/test/braille-icon.png"/>' + rowObject[1] + '</a>';
			
			} else {				
				htmlString = '<span style="padding-left:20px"></span>' + rowObject[1];
			}
			
		    return htmlString;
		};

		function pnpBraillelinkUnFormatter(cellvalue, options, rowObject) {
		    return;
		};		
		
		
		//Custom formatter for pdf link. 
		function printARStudentTicketLinkFormatter(cellvalue, options, rowObject) {
			var htmlString = "N.A";
			htmlString = '<a class="pdfLink" href="getPDFTickets.htm?'+
					'assessmentProgramName=' + assessmentProgramName + 
					'&testSessionId='+ testSessionId +
					'&isAutoRegistered=true' +
					'&selectedStudents=' + rowObject[0] + 
					'">' + '<img alt="Test Session PDF" style="border:0px solid;" src="images/pdf.png">' + '</a>';
		    return htmlString;
		}
		
		//Custom unformatter for pdf link.
		function printARStudentTicketLinkUnFormatter(cellvalue, options, rowObject) {
		    return;
		}
		
		
		function specialCircumstanceFormatter(cellvalue, options, rowObject) {
			var htmlString = "N.A";
			var disabledAttrib = '';
			var statusName = rowObject[11];
			if(statusName === 'Approved' || statusName === 'Not Approved'){
				disabledAttrib = ' disabled="disabled" style="background-color:#e5e5e5;" ';
			}
			if(cellvalue != null && cellvalue !== undefined && cellvalue !== '' && (cellvalue !== 'Not Available' || cellvalue !== 'N.A')){
				if(isApprovalRequired(cellvalue, rowObject)){
					disabledAttrib = ' disabled="disabled" style="background-color:#e5e5e5;" ';
				}
			}
			htmlString = '<div id="assessmentProgramsDiv'+rowObject[0]+'">	<select onChange="setRequestAction('+rowObject[0]+');" data-cellvalue="'+rowObject[9]+'" title="Special Circumstance List '+
			rowObject[0]+'" id="specialCircumstanceListSelect'+rowObject[0]+'" name="specialCircumstanceList'+rowObject[0]+'" class="bcg_select" '+disabledAttrib +'>'+
			'<option value="" title="">Select</option><c:if test="${fn:length(specialCircumstanceList) > 0 }">'			
				+'<c:forEach items="${specialCircumstanceList}" var="specialCircumstance" varStatus="index">'
				+'<option value="${specialCircumstance.id}"'
				+' title="${specialCircumstance.description}" >${specialCircumstance.description}</option>'
				+'</c:forEach></c:if></select></div>';
			index++;
		    return htmlString;
		}
		
		//Custom unformatter for special circumstance link.
		function specialCircumstanceUnFormatter(cellvalue, options, rowObject) {
		    return;
		}
		
		function specialCircumstanceStatusFormatter(cellvalue, options, rowObject) {
			var htmlString = 'N.A' + '<img alt="'+rowObject[11]+'" id="sscImg'+rowObject[0]+'" />';
			if(rowObject[11] === 'Saved') {
				htmlString = '<img id="sscImg'+ rowObject[0] +'" alt="'+rowObject[11]+'" class="specialCircumstanceStatus" src="images/saved.png"/>';
			} else if(rowObject[11] === 'Approved') {
				htmlString = '<img id="sscImg'+ rowObject[0] +'" alt="'+rowObject[11]+'" class="specialCircumstanceStatus" src="images/approved.png"/>';
			} else if(rowObject[11] === 'Not Approved') {
				htmlString = '<img id="sscImg'+ rowObject[0] +'" alt="'+rowObject[11]+'" class="specialCircumstanceStatus" src="images/notApproved.png"/>';
			} else if(rowObject[11] === 'Pending' || cellvalue === 'Pending Further Review') {
				htmlString = '<img id="sscImg'+ rowObject[0] +'" alt="'+rowObject[11]+'" class="specialCircumstanceStatus" src="images/pending.png"/>';
			}
		    return htmlString;
		}
		
		//Custom unformatter for special circumstance link.
		function specialCircumstanceStatusUnFormatter(cellvalue, options, rowObject) {
		    return;
		}
		
		function specialCircumstanceApprovalFormatter(cellvalue, options, rowObject) {
			var htmlString = "N.A";
			var statusName = rowObject[11];
			var disabledAttrib = '';
			if(statusName === 'Not Available' || statusName === 'Saved'){
				disabledAttrib = ' disabled="disabled" style="background-color:#e5e5e5;" ';
			}
			htmlString = '<div id="specialCircumstanceApprovalDiv'+ rowObject[0]+'">'+
			'<select onChange="setApprovalAction(this, true);" data-cellvalue="'+rowObject[12]+'" id="specialCircumstanceApprovalListSelect'+ rowObject[0]+'" '+
			'name="specialCircumstanceApprovalList'+rowObject[0]+'" class="bcg_select" title="Special Circumstance Approval'+ rowObject[0]+'" '+disabledAttrib +'>'+
			'<option value="" title="">Select</option><c:if test="${fn:length(specialCircumstanceApprovalList) > 0 }">'			
				+'<c:forEach items="${specialCircumstanceApprovalList}" var="specialCircumstanceApproval" varStatus="index">'
				+'<option value="${specialCircumstanceApproval.id}"'
				+' title="${specialCircumstanceApproval.categoryName}">${specialCircumstanceApproval.categoryName}</option>'
				+'</c:forEach></c:if></select></div>';
			if(cellvalue!=''){
				if(htmlString.indexOf('<option value="'+rowObject[12]+'"')!=-1){
					htmlString = htmlString.replace('<option value="'+rowObject[12]+'"','<option value="'+rowObject[12]+'" selected ');
				}
			}
			index++;
		    return htmlString;
		}
		
		//Custom unformatter for special circumstance link.
		function specialCircumstanceApprovalUnFormatter(cellvalue, options, rowObject) {
		    return;
		}
		
		//Execute below method on view tickets button click.
		$('#viewTicketsTopButton').click(function() {
			if (selectedStudents !== undefined &&  selectedStudents.length > 0) {
				window.location.href = 'getPDFTickets.htm?'+ 
						'assessmentProgramName=' + assessmentProgramName + 
						'&testSessionId='+ testSessionId + 
						'&isAutoRegistered=true' +
						'&selectedStudents=' + selectedStudents;
			} else {
				$("#ARSTmessages").show();				
				$('#select_student_error').show();
				setTimeout("aart.clearMessages()", 3000);
				setTimeout(function(){ $("#ARSTmessages").hide(); },3000);
			}
		});
		
		function saveSpecialCircumstance(testSessionId,studentId,specialCircumstanceValue, rowNum, requireConfirmation, 
			specialCircumstanceApprovalValue, isApprovalAction){
			if($('#stateHasRestrictedCodes').val() === 'true'){
				$(".img_"+rowNum).replaceWith('<img src="images/saved_text.png" height="15" width="26" class="img_'+rowNum+'"></img>');
			}
			var jqxhr = $.ajax({
				type: "POST",
				url: 'saveSpecialCircumstance.htm',
				dataType: 'text',
	            data: {
	            	testSessionId : testSessionId,
	            	studentId: studentId,
	            	specialCircumstanceValue: specialCircumstanceValue,
	            	requireConfirmation: requireConfirmation,
	            	specialCircumstanceApprovalValue: specialCircumstanceApprovalValue,
	            	isApprovalAction: isApprovalAction
	            },
	            error : function(jqXhr, textStatus, errorThrown) {
	            	//alert("Error: "+errorThrown+" ********* "+textStatus);
	            },
	            success : function(data, textStatus, jQxhr) {
	    			if(requireConfirmation){
	    				$('#sscImg'+studentId).parent().html('<img id="sscImg'+ studentId +'" alt="'+specialCircumstanceValue+'" class="specialCircumstanceStatus" src="images/pending.png"/>');
	    			} else {
	    				$('#sscImg'+studentId).parent().html('<img id="sscImg'+ studentId +'" alt="'+specialCircumstanceValue+'" class="specialCircumstanceStatus" src="images/saved.png"/>');
	    			}
	    			// DE12794 - refresh if saved code is restricted.
    				$("#autoRegisteredTSStudentsTableId").trigger('reloadGrid');
	            }
	          
	        });  
		}
		
	}	
	
	function showDialogTest(studentIdValue, testId, studentName) {
		$("#dialog-confirm").dialog({
			resizable: false,
			height: 220,
			width: 500,
			modal: true,
			autoOpen:false,
			title:'Test Assignment',
			buttons: {			   
			    Ok: function() {
			    	 $(this).dialog("close");
			    }
			}
		});	 
		
		var htmlString='';
		htmlString += '&nbsp;&nbsp;<b>Student Name: </b>'+studentName+'<br/><br/>';
		htmlString += '&nbsp;&nbsp;<b>Student ID: </b>'+studentIdValue+'<br/><br/>';	
		htmlString += '&nbsp;&nbsp;<b>Test ID: </b>'+testId+'<br/><br/>';
		
		$("#dialog-confirm").html(htmlString);
		$("#dialog-confirm").dialog('open');
	}	
	function isApprovalRequired(specialCircumstanceValue, rowObject){
		var statusName = rowObject[11];
		if(statusName !== null && statusName !== undefined){
			return false;
		}
		for(var i = 0; i < specialCircumstanceConfirmationList.length; i++) {
			if(specialCircumstanceConfirmationList[i].id==specialCircumstanceValue && specialCircumstanceConfirmationList[i].requireConfirmation=="true")
			{
				return true;
			}
		}
		return false;
	}
</script>