<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/jsp/include.jsp" %>
<style>
table.ui-jqgrid-btable tr.jqgfirstrow td { border: none; }
table.ui-jqgrid-btable { height: 1px; }
</style>
<div>
		
	<div class="top_info"></div> 
		<div class="messages">
        <span class="info_message ui-state-highlight successMessage hidden successMessageEnroll" id="successMessageEnroll" ></span>
        <span class="info_message ui-state-highlight successMessage hidden successMessageUnenroll" id="successMessageUnenroll" ></span>
         </div>
	<div id="myModulesTableContainer">
			<table class="responsive kite-table" id="myModulesTable"></table>
			<div id="myModulesPager"></div>
	</div>

</div>
<div id="myModuleDialog"></div>

<script>
	
	$(function () {
		//Build the overlay
		$('#myModuleDialog').dialog({
			resizable: false,
			height: 500,
			width: 800,
			modal: true,
			autoOpen:false,
			buttons: {
			    Close: function() {
			    	 $(this).dialog('close');
			    }			    
			}
		});
	});
	
	//function that makes DB call for list of modules and show those in the jqGrid.
	function myModules() {
		var grid = $("#myModulesTable");
		var grid_width = $('.content-cell').width();
    	var colWidth = Math.round(grid_width/6);
    	if(grid_width == 100 || grid_width == 0){
			grid_width = 759;				
		}
	  	var plusFortyWidth = colWidth+40;
	    getColumnIndexByName = function (grid, columnName) {
            var cm = grid.jqGrid('getGridParam', 'colModel'), i, l = cm.length;
            for (i = 0; i < l; i++) {
                if (cm[i].name === columnName) {
                    return i; // return the index
                }
            }
            return -1;
        }
        var autoCompleteUrl =  "getPDBrowseModulesAutoCompleteData.htm";
        
      	//Get dropdowns data to load filters.
    	var dropvalues = getDropdownValues(true);
      
	    var cmFormyModules = [
		  	{ name: 'act', index: 'act', align: 'center', width: ($.browser.webkit ? 150 : 155), align: 'center', sortable: false, formatter: 'actions'},
		
		   	{name: 'id', index: 'id', align: 'center', width: colWidth,  formatter: myModuleLinkIDFormatter, unformat: myModuleLinkIDUnFormatter, 
				sortable : true, search : true, hidden: false, hidedlg : false },
      	
			{name: 'name', index: 'name', align: 'center', width: colWidth + 40, formatter: myModuleLinkFormatter, unformat: myModuleLinkUnFormatter, 
				sortable : true, search : true, hidden: false, hidedlg : false,
		   			searchoptions: { sopt:['cn'], dataInit: function(elem) {
			    	$(elem).autocomplete({
			        	source: autoCompleteUrl + '?fileterAttribute=name&userModulesOnly=true'
			    	});
				} }
		   	},
      	
		   	{name: 'assessmentProgramName', index: 'assessmentProgramName', align: 'center', width: colWidth + 40, sortable : true, search : true, hidden: false, hidedlg : false },
      	
      		{name: 'enrollmentStatusName', index: 'enrollmentStatusName', align: 'center' , width: colWidth, sortable : true, search : true,
      	 		stype : 'select', searchoptions: { value : dropvalues.enrollmentCategoryValues, sopt:['eq'] }, hidden : false },
      	 
      		{name: 'description', index: 'description', align: 'center' , width: colWidth, sortable : true, search : true, hidden: true, hidedlg : true },
      	
	      	{name: 'suggestedaudience', index: 'suggestedaudience', align: 'center' , width: colWidth, sortable : true, search : true, hidden: true, hidedlg : true }
	  	];
	
		var myDefaultSearch = 'cn',
            myColumnStateName = 'ColumnChooserAndLocalStorage11.colState11';
	        
	    var myColumnsState;
	    var isColState;
	 
	    firstLoad = true;
	   // var url = "getModulesPDuser.htm";
	    var url = "browseModules.htm?q=1";
	    myColumnsState = restoreColumnState(cmFormyModules, myColumnStateName);
	    isColState = typeof (myColumnsState) !== 'undefined' && myColumnsState !== null;
		    if($("#myModulesTable").getGridParam('reccount') == undefined || 
				$("#myModulesTable").getGridParam('reccount') < 1) {
				$("#myModulesTable").scb({
					url: url,
					postData:  {									
						filters : isColState ? myColumnsState.filters : null , 
						userModulesOnly : true
					},
					mtype: "GET",
					datatype: "json", 
			        colNames: [
							   	'<fmt:message key="label.pd.myModules.actions"/>',
			                   	'<fmt:message key="label.pd.myModules.id"/>',
			                   	'<fmt:message key="label.pd.myModules.modulename"/>',
			                   	'<fmt:message key="label.pd.myModules.assessmentprogram"/>',
			                   	'<fmt:message key="label.pd.myModules.enrollment"/>',
			                   	'<fmt:message key="label.pd.myModules.description"/>',
			                   	'<fmt:message key="label.pd.myModules.suggestedaudience"/>'
			                  ],                     
			                   
			        colModel: cmFormyModules, 
		  	       	height : 'auto',
		  	       	shrinkToFit: false,
			  	  	width: grid_width,
		           	rowNum:10, 
		           	rowList:[10,20,30], 
		           	pager: '#myModulesPager',  
		           	viewrecords: true, 
		           	page: isColState ? myColumnsState.page : 1,
					search: isColState ? myColumnsState.search : false,
					sortname: isColState ? myColumnsState.sortname : 'id',
					//sortorder: isColState ? myColumnsState.sortorder : 'asc',
					sortorder: isColState ? myColumnsState.sortorder : 'asc',
				 	sortable: {
				 		update: function() {
							saveColumnState.call(grid, grid[0].p.remapColumns,myColumnStateName);
				 		}
				 	},
				   	altclass: 'altrow',
				   	emptyrecords : "<fmt:message key='label.common.norecordsfound'/>",
				   	altRows : true,
				   	hoverrows : true,
				   	multiselect : false,
				   	toppager: false,
				   	loadComplete: function () {
		                var iCol = getColumnIndexByName(grid, 'act');
		                $(this).find("div.ui-inline-del>span")
		                .removeClass("ui-icon-trash")
		                .addClass("hidden");
		                $(this).find("div.ui-inline-edit>span")
		                .removeClass("ui-icon-pencil")
		                .addClass("hidden");
		                <security:authorize access="hasRole('ENROLL_MODULE')">
			                var grid_ids = grid.jqGrid('getDataIDs');
			                var i=0;
			                $(this).find(">tbody>tr.jqgrow>td:nth-child(" + (iCol + 1) + ")")
		                    .each(function() {
		                    	var gridRow = grid.jqGrid('getRowData',grid_ids[i]);
	                         	var enrollmentStatusName = gridRow['enrollmentStatusName'];
	                        	var moduleId = gridRow['id'];
		                    	//User is not enrolled to any module
		                      	/* if(enrollmentStatusName == "Unenrolled" || enrollmentStatusName == "Released"){
		                        $("<div>", {
		                            title: "Enroll Into the Module",
		                            mouseover: function() {
		                                $(this).addClass('ui-state-hover');
		                            },
		                            mouseout: function() {
		                                $(this).removeClass('ui-state-hover');
		                            },
		                            click: function(e) {
		                            	enrollToModule(moduleId);
		                            }
		                        }
			                      ).css({"margin-right": "5px", float: "left", cursor: "pointer"})
			                       .addClass("ui-pg-div ui-inline-custom")
			                       .append('<img src="/AART/images/enroll.png"></img>')
			                       .prependTo($(this).children("div"));
		                      		 
		                      	 }
		                      	else*/
		                      	
		                      	 if(enrollmentStatusName == "Enrolled"){
		                      		 //User enrolled to module has not taken the test
			                        $("<div>", {
			                            title: "Unenroll from the Module",
			                            mouseover: function() {
			                                $(this).addClass('ui-state-hover');
			                            },
			                            mouseout: function() {
			                                $(this).removeClass('ui-state-hover');
			                            },
			                            click: function(e) {
			                            	unenrollToModule(moduleId);
			                            }
			                        }
			                      ).css({"margin-right": "5px", float: "left", cursor: "pointer"})
			                       .addClass("ui-pg-div ui-inline-custom")
			                       .append('<img src="/AART/images/unenroll.png"></img>')
			                       .prependTo($(this).children("div"));
		                      		 
		                      	 }
		                      	else if(enrollmentStatusName == "Attempted"){
		                      		//User is enrolled to a module and the test is in progress
		                      		$("<div>", {
			                            title: "Unenroll from the Module",
			                            mouseover: function() {
			                                $(this).addClass('ui-state-hover');
			                            },
			                            mouseout: function() {
			                                $(this).removeClass('ui-state-hover');
			                            },
			                            click: function(e) {	
			                            	unenrollToModuleConfirm(moduleId);
			                            }
			                        }
				                      ).css({"margin-right": "5px", float: "left", cursor: "pointer"})
				                       .addClass("ui-pg-div ui-inline-custom")
				                       .append('<img src="/AART/images/unenroll.png"></img>')
				                       .prependTo($(this).children("div"));
			                      		 
			                      	 }
		                      	else if(enrollmentStatusName == "In Progress"){
		                      		//User is enrolled to a module and the test is in progress
		                      		$("<div>", {
			                            title: "Unenroll from the Module",
			                            mouseover: function() {
			                                $(this).addClass('ui-state-hover');
			                            },
			                            mouseout: function() {
			                                $(this).removeClass('ui-state-hover');
			                            },
			                            click: function(e) {	
			                            	unenrollToModuleConfirm(moduleId);
			                            }
			                        }
				                      ).css({"margin-right": "5px", float: "left", cursor: "pointer"})
				                       .addClass("ui-pg-div ui-inline-custom")
				                       .append('<img src="/AART/images/unenroll.png"></img>')
				                       .prependTo($(this).children("div"));
			                      		 
			                      	 }
			                      
			                      /*
			                      .css({"margin-right": "5px", float: "left", cursor: "pointer"})
			                       .addClass("ui-pg-div ui-inline-custom")
			                       .append('<span class="ui-icon ui-icon-triangle-1-e black"></span>')
			                       .prependTo($(this).children("div"));
		                        
		                      	 }	else if(enrollmentStatusName == "Enrolled"){
		                      		 //User enrolled to module has not taken the test
			                        $("<div>", {
			                            title: "Unenroll from the Module",
			                            mouseover: function() {
			                                $(this).addClass('ui-state-hover');
			                            },
			                            mouseout: function() {
			                                $(this).removeClass('ui-state-hover');
			                            },
			                            click: function(e) {
			                            	unenrollToModule(moduleId);
			                            }
			                        }
				                      ).css({"margin-right": "5px", float: "left", cursor: "pointer"})
				                       .addClass("ui-pg-div ui-inline-custom")
				                       .append('<span class="ui-icon ui-icon-circle-minus red"></span>')
				                       .prependTo($(this).children("div"));
		                      		 
		                      	 }else if(enrollmentStatusName == "In Progress"){
		                      		//User is enrolled to a module and the test is in progress
		                      		$("<div>", {
			                            title: "Unenroll from the Module",
			                            mouseover: function() {
			                                $(this).addClass('ui-state-hover');
			                            },
			                            mouseout: function() {
			                                $(this).removeClass('ui-state-hover');
			                            },
			                            click: function(e) {	
			                            	unenrollToModuleConfirm(moduleId);
			                            }
			                        }
				                      ).css({"margin-right": "5px", float: "left", cursor: "pointer"})
				                       .addClass("ui-pg-div ui-inline-custom")
				                       .append('<span class="ui-icon ui-icon-circle-minus red"></span>')
				                       .prependTo($(this).children("div"));
		                      		$("<div>", {
			                            title: "In Progress",
			                            mouseover: function() {
			                                $(this).addClass('ui-state-hover');
			                            },
			                            mouseout: function() {
			                                $(this).removeClass('ui-state-hover');
			                            },
			                    
			                        }
				                      ).css({"margin-right": "5px", float: "left", cursor: "pointer"})
				                       .addClass("ui-pg-div ui-inline-custom")
				                       .append('<span class="ui-icon ui-icon-pencil"></span>')
				                       .prependTo($(this).children("div"));
		                      	 } else if(enrollmentStatusName == "Completed"){
		                      		 //User enrolled to a module has completed the test
			                        $("<div>", {
			                            title: "Completed",
			                            mouseover: function() {
			                                $(this).addClass('ui-state-hover');
			                            },
			                            mouseout: function() {
			                                $(this).removeClass('ui-state-hover');
			                            }
			                        }
				                      ).css({"margin-right": "5px", float: "left", cursor: "pointer"})
				                       .addClass("ui-pg-div ui-inline-custom")
				                       .append('<span class="ui-icon ui-icon-check green"></span>')
				                       .prependTo($(this).children("div"));
		                      	 }*/
		                        i++;
		                	});
		                </security:authorize>
		            },
		            resizeStop: function () {
	                    saveColumnState.call(grid, grid[0].p.remapColumns,myColumnStateName);
	                },
		            gridComplete: function() {
		                var recs = parseInt($("#myModulesTable").getGridParam("records"));	                
		                if (isNaN(recs) || recs == 0) {
		                     //Set min height of 1px on no records found
		                     $('.jqgfirstrow').css("height", "5px");
		                 }
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
		                 refreshSerchingToolbar($this, myDefaultSearch);
		                 saveColumnState.call($this, $this[0].p.remapColumns,myColumnStateName);
		                 $("#gbox_myModulesTable").show();
		            }
				});
			}
		    $.extend($.jgrid.search, {
			    multipleSearch: true,
			    multipleGroup: true,
			    recreateFilter: true,
			    closeOnEscape: true,
			    closeAfterSearch: true,
			    overlay: 0
			});
			grid.jqGrid('navGrid', '#myModulesPager', {edit: false, add: false, del: false});
			$("#gbox_myModulesTable").show();
			$("#myModulesTable").show();
		}
		//Id, Name link formatterr
		function myModuleLinkIDFormatter(cellvalue, options, rowObject) {
			return '<a href="javascript:viewMyModuleDetails(\'' + options.rowId + 
					 '\', \'' +  rowObject[2] + '\', \'' +  rowObject[5] + '\', \'' +  rowObject[6] + '\', \'' +  rowObject[7] + '\', \'' +  rowObject[8] +
					 '\', \'' + rowObject[3]  + '\', \'' +  rowObject[9] + '\', \'' +  rowObject[0] + '\', \'' +  rowObject[10] + '\', \'' +  rowObject[11] + '\');" title="Click to view details">' + cellvalue + '</a>';
		}
		//Id, Name link formatterr
		function myModuleLinkFormatter(cellvalue, options, rowObject) {
			return '<a href="javascript:viewMyModuleDetails(\'' + options.rowId + 
					 '\', \'' +  rowObject[2] + '\', \'' +  rowObject[5] + '\', \'' +  rowObject[6] + '\', \'' +  rowObject[7] + '\', \'' +  rowObject[8] +
					 '\', \'' + rowObject[3]  + '\', \'' +  rowObject[9] + '\', \''+  rowObject[0] + '\', \'' +  rowObject[10] + '\', \'' +  rowObject[11] + '\');" title="Click to view details">' + cellvalue + '</a>';
		}
		//Id, Name link unformatter
		function myModuleLinkIDUnFormatter(cellvalue, options, cellObject) {
		    return $(cellObject).text();
		}
		//Id, Name link unformatter
		function myModuleLinkUnFormatter(cellvalue, options, cellObject) {
		    return $(cellObject).text();
		}
		//Show Module details on clicking the id/name value.
		function viewMyModuleDetails(rowId, name, description, suggestedAudience, testId, tutorialId, assessmentProgram, moduleRequired, enrollmentStatus, testCompletionDate, testResult) {
			var myModuleInfoDiv = "<div>"
			+ "<div class='pdModuleInfoDesc'><div class='pdHeading'>Assessment Program:</div>"
			+ "<div class='pdModuleInfoText'>" + assessmentProgram + "</div></div>";
			+ "<div class='pdModuleInfoDesc'><div class='pdHeading'>Description:</div>"
			+ "<div class='pdModuleInfoText'>" + description + "</div></div>";

			if(tutorialId != null && tutorialId != 'Not Available') {
				myModuleInfoDiv += "<div class='pdModuleInfoDesc'><div class=pdHeading>Tutorial</div><div class='pdModuleInfoText'><button type='button' class='viewModule' onClick='closeDialog(" + rowId + ", \"" + name + "\", " + true + ")' >View Tutorial</button></div></div>";
			}
			
			if(testResult != null && testResult == 'Yes') {
				myModuleInfoDiv = myModuleInfoDiv + "<div class='pdModuleInfoDesc'><div class=pdHeading>Test</div><div class='pdModuleInfoText'><button type='button' disabled='disabled'>Take Test</button></div></div>";
			} else {
				myModuleInfoDiv = myModuleInfoDiv + "<div class='pdModuleInfoDesc'><div class=pdHeading>Test</div><div class='pdModuleInfoText'><button type='button' onClick='closeDialog(" + rowId + ", \"" + name + "\", " + false + ")' >Take Test</button></div></div>";
			}
			
			if(suggestedAudience.trim().length && suggestedAudience) 
			{
				myModuleInfoDiv = myModuleInfoDiv + "<div class='pdModuleInfoDesc'><div class='pdHeading'>Suggested Audience :</div><div class='pdModuleInfoText'>" 
				+ suggestedAudience + "</div></div>";
			}
			
			
			myModuleInfoDiv = myModuleInfoDiv + "<div class='pdModuleInfoDesc'><div class='pdHeading'>Module Required:</div><div class='pdModuleInfoText'>" 
			+ moduleRequired + "</div></div>";
			
			if(enrollmentStatus == 'Completed'){
				myModuleInfoDiv = myModuleInfoDiv + "<div class='pdModuleInfoDesc'><div class='pdHeading'>Completed:</div><div class='pdModuleInfoText'>" 
				+ testCompletionDate + "</div></div>";
			}
			
			myModuleInfoDiv = myModuleInfoDiv + "</div>";
			
			$('#myModuleDialog').html(myModuleInfoDiv);
			$('#myModuleDialog').dialog('option', 'title', rowId + ' - ' + name);
			$('#myModuleDialog').dialog('open');	
		}
		
		
		function closeDialog(rowId, name, tutorialFlag) {
			$(".ui-dialog-titlebar-close:visible").click();

			$('<div id="viewModuleDialog"><iframe id="viewModuleIframe" src="" style="border:1px solid #000;"></iframe></div>').dialog({
				resizable: false,
				height: $(window).height(),//700,
				width: $(window).width(),//1200,
				modal: true,
				autoOpen:false,
		        close: function (e) {
		            $(this).empty();
		            $(this).dialog('destroy');
		            $('#viewModuleDialog').remove();	
	            	
					$('#browseModulesTable').trigger("reloadGrid");					
					$('#myModulesTable').trigger("reloadGrid");
		        },
		        open: function() {
		        	$("#viewModuleIframe").attr( "width", '100%' ).attr( "height", '100%' );	
					//$('#viewModuleIframe').contents().find('html').html("<h1>This is your module -" + name +" </h1>");
					//$("#viewModuleIframe").attr( "src", "${pageContext.request.contextPath}/pdStudentHome.htm?testId=653&studentTestId=33064");
		        	$("#viewModuleIframe").attr( "src", "${pageContext.request.contextPath}/pdStudentHome.htm?moduleId=" +rowId+"&tutorialFlag=" + tutorialFlag);
		        }
			});
			
			$(document).bind('pd.endtest.event', function(e) {
			     $('#viewModuleDialog').dialog('close');
			});
			
			$('#viewModuleDialog').dialog('option', 'title', rowId + ' - ' + name).dialog('open');
		}
		
		$('.viewModule').click(function(event) {
			
			alert("hi");
		});
		
		
</script>