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
	<div id="browsModulesTableContainer">
		<table class="responsive kite-table" id="browseModulesTable"></table>
		<div id="browseModulesPager" style="width: auto;"></div>
	</div>

</div>
<div class="confirmDialog"></div>
<div class="dialog-confirm"></div>

<script>
	
	$(function () {
		//Build the overlay
		$('#ModuleInfoDiv').dialog({
			resizable: false,
			height: 450,
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
	function browseModules() {
		var grid = $("#browseModulesTable");
		var grid_width = $('.content-cell').width();
		if(grid_width == 100 || grid_width == 0){
			grid_width = 759;				
		}
		var colWidth = Math.round(grid_width/6);
	  	var plusFortyWidth = colWidth+40;
	    getColumnIndexByName = function (grid, columnName) {
            var cm = grid.jqGrid('getGridParam', 'colModel'), i, l = cm.length;
            for (i = 0; i < l; i++) {
                if (cm[i].name === columnName) {
                    return i; // return the index
                }
            }
            return -1;
        };
        var autoCompleteUrl =  "getPDBrowseModulesAutoCompleteData.htm";
        
      	//Get dropdowns data to load filters.
    	var dropvalues = getDropdownValues(false);
 
	    var cmForBrowseModules = [
		  	{ name: 'act', index: 'act', align: 'center', width: ($.browser.webkit ? 150 : 155), align: 'center', sortable: false, formatter: 'actions'},
		
		   	{name: 'id', index: 'id', align: 'center', width: colWidth,  formatter: browseModuleLinkIDFormatter, unformat: browseModuleLinkIDUnFormatter, 
				sortable : true, search : true, hidden: false, hidedlg : false },
      	
			{name: 'name', index: 'name', align: 'center', width: plusFortyWidth, formatter: browseModuleLinkFormatter, unformat: browseModuleLinkUnFormatter, 
				sortable : true, search : true, hidden: false, hidedlg : false,
		   			searchoptions: { sopt:['cn'], dataInit: function(elem) {
			    	$(elem).autocomplete({
			        	source: autoCompleteUrl + '?fileterAttribute=name'
			    	});
				} }
		   	},
      	
		   	{name: 'assessmentProgramName', index: 'assessmentProgramName', align: 'center', width: plusFortyWidth, sortable : true, search : true, hidden: false, hidedlg : false },
      	
      		{name: 'enrollmentStatusName', index: 'enrollmentStatusName', align: 'center' , width: colWidth, sortable : true, search : true, 
		   		stype : 'select', searchoptions: { value : dropvalues.enrollmentCategoryValues, sopt:['eq'] },  hidden : false },
      	 		
      		{name: 'description', index: 'description', align: 'center' , width: colWidth, sortable : true, search : true, hidden: true, hidedlg : true },
      	
	      	{name: 'suggestedaudience', index: 'suggestedaudience', align: 'center' , width: colWidth, sortable : true, search : true, hidden: true, hidedlg : true }
	  ];
	myDefaultSearch = 'cn',
        
    myColumnStateName = 'ColumnChooserAndLocalStorage5.colState5';
        
    var myColumnsState;
    var isColState;
    // var url = "getModulesPDuser.htm?q=1";
    var urlBrowse = "browseModules.htm";
    firstLoad = true;

    myColumnsState = restoreColumnState(cmForBrowseModules, myColumnStateName);
    isColState = typeof (myColumnsState) !== 'undefined' && myColumnsState !== null;
	    if($("#browseModulesTable").getGridParam('reccount') == undefined || 
				$("#browseModulesTable").getGridParam('reccount') < 1) {

			$("#browseModulesTable").scb({
				url:urlBrowse,
				postData:  {									
					filters : isColState ? myColumnsState.filters : null ,
				},
				mtype: "GET",
				datatype: "json", 
		        colNames: [
						   	'<fmt:message key="label.pd.browseModules.actions"/>',
		                   	'<fmt:message key="label.pd.browseModules.id"/>',
		                   	'<fmt:message key="label.pd.browseModules.modulename"/>',
		                   	'<fmt:message key="label.pd.browseModules.assessmentprogram"/>',
		                   	'<fmt:message key="label.pd.browseModules.enrollment"/>',
		                   	'<fmt:message key="label.pd.browseModules.description"/>',
		                   	'<fmt:message key="label.pd.browseModules.suggestedaudience"/>'
		                  ],                     
		                   
		        colModel: cmForBrowseModules, 
	  	       	height : 'auto',
	  	       	shrinkToFit: false,
	  	       	width: grid_width,
	           	rowNum:10, 
	           	rowList:[10,20,30], 
	           	pager: '#browseModulesPager',  
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
		                      	 if(enrollmentStatusName == "Unenrolled" || enrollmentStatusName == "Released"){
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
		                      		 
		                      	 }/*
		                      	 else if(enrollmentStatusName == "Enrolled"){
			                        $("<div>", {
			                            title: "Un-enroll from the Module",
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
		                    	
		                    	 else if(enrollmentStatusName == "In Progress"){
		                      		//User is enrolled to a module and the test is in progress
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
	                var recs = parseInt($("#browseModulesTable").getGridParam("records"));	                
	                if (isNaN(recs) || recs == 0) {
	                     //Set min height of 1px on no records found
	                     $('.jqgfirstrow').css("height", "1px");
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
		grid.jqGrid('navGrid', '#browseModulesPager', {edit: false, add: false, del: false});
	}
	

	//Get dropdowns data to load filters.
	function getDropdownValues(userModulesOnly) {
		var params = { userModulesOnly : userModulesOnly};
		var dropValues = {};
		dropValues['orgDisplayIndentifierValues'] = "";
		dropValues['enrollmentCategoryValues'] = "";
		$.ajax({
            url: 'getPDBrowseModulesDropdownData.htm',            
            data: params,
            dataType: 'json',
            type: "GET",
            async: false,
            success : function(data) {
            	var i;
            	dropValues.orgDisplayIdentifierValues = ":All";
           	 	for (i=0; i<data.organizations.length; i++) {
           	 		dropValues.orgDisplayIdentifierValues += ";" + data.organizations[i]
           	 			+ ":" + data.organizations[i];
           	 	}
 
           	 	
           		dropValues.enrollmentCategoryValues = ":All;";
           	 	for (i=0; i<data.enrollmentCategory.length; i++) {
           	 		dropValues.enrollmentCategoryValues += data.enrollmentCategory[i].id
           	 			+ ":" + data.enrollmentCategory[i].categoryName;
           	 		if(i+1<data.enrollmentCategory.length) {
           	 			dropValues.enrollmentCategoryValues += ";";
           	 		}
           	 	}
           	 	
            }
        });
		return dropValues;
	}
	
    
  //Id, Name link formatterr
	function browseModuleLinkIDFormatter(cellvalue, options, rowObject) {
		return '<a href="javascript:viewModuleDetails(\'' + options.rowId + 
				 '\', \'' +  rowObject[2] + '\', \'' +  rowObject[5] + '\', \'' +  rowObject[6] +
				 '\', \'' +  rowObject[3] + '\', \'' +  rowObject[9] + '\');" title="Click to view details">' + cellvalue + '</a>';
	}
	
	
	//Id, Name link unformatter
	function browseModuleLinkIDUnFormatter(cellvalue, options, cellObject) {
	    return  $(cellObject).text(); 
	}
	
	//Name link formatter
	function browseModuleLinkFormatter(cellvalue, options, rowObject) {
		return '<a href="javascript:viewModuleDetails(\'' + options.rowId + 
				 '\', \'' +  rowObject[2] + '\', \'' +  rowObject[5] + '\', \'' +  rowObject[6] +
				 '\', \'' +  rowObject[3] + '\', \'' +  rowObject[9] + '\');" title="Click to view details">' + cellvalue + '</a>';
	}
	//Id, Name link unformatter
	function browseModuleLinkUnFormatter(cellvalue, options, cellObject) {
		 return  $(cellObject).text(); 
	}
	
	function enrollToModule(moduleId){
		$.ajax({
            url: 'enrollToModule.htm',            
            dataType: 'json',
            data: {
            	moduleId : moduleId,
            	status: "ENROLLED"
            },
            type: "POST",
            async: false,
            success : function(data) {
            	// refresh both the grids
				$('#browseModulesTable').trigger("reloadGrid");
				$('#myModulesTable').trigger("reloadGrid");

				if(data) {
					$('.dialog-confirm').html("Successfully enrolled to module " + moduleId);
				} else {
					$('.dialog-confirm').html("Active enrollment found, User is already enrolled to module " + moduleId);
				}
				
				$('.dialog-confirm').dialog("open");
            }
        });
	}
	function unenrollToModule(moduleId){
		$.ajax({
            url: 'enrollToModule.htm',            
            dataType: 'json',
            data: {
            	moduleId : moduleId,
            	status: "UNENROLLED"
            },
            type: "POST",
            async: false,
            success : function(data) {
				// refresh both the grids
				$('#browseModulesTable').trigger("reloadGrid");
				$('#myModulesTable').trigger("reloadGrid");
				
				if(data) {
					$('.dialog-confirm').html("Successfully unenrolled from module " + moduleId);
				} else {
					$('.dialog-confirm').html("Active enrollment Not found, User is already unenrolled from module " + moduleId);
				}
				
				$('.dialog-confirm').dialog("open");
            }
        });
	}
	
	//Show Module details on clicking the id/name value.
	function viewModuleDetails(rowId, name, description, suggestedAudience, assessmentProgram, moduleRequired) {
		var ModuleInfoDiv = "<div><div class='pdModuleInfoDesc'><div class='pdHeading'>Assessment Program:</div><div class='pdModuleInfoText'>" + assessmentProgram + "</div></div>" 
		+ "<div class='pdModuleInfoDesc'><div class='pdHeading'>Description:</div><div class='pdModuleInfoText'>" + description + "</div></div>";
		
		if(suggestedAudience.trim().length && suggestedAudience) 
		{
			ModuleInfoDiv += "<div class='pdModuleInfoDesc'><div class='pdHeading'>Suggested Audience:</div><div class='pdModuleInfoText'>" + suggestedAudience + "</div></div>";
		}
		
		ModuleInfoDiv += "<div class='pdModuleInfoDesc'><div class='pdHeading'>Module Required:</div><div class='pdModuleInfoText'>" + moduleRequired + "</div></div></div>";
		
		$('#ModuleInfoDiv').html(ModuleInfoDiv);
		$('#ModuleInfoDiv').dialog('option', 'title', rowId + ' - ' + name);
		$('#ModuleInfoDiv').dialog('open');	
		
	}
	
	function unenrollToModuleConfirm(moduleId){	
		$('.confirmDialog').dialog({
			resizable: false,
			height: 200,
			width: 500,
			modal: true,
			autoOpen:false,			
			title: "Are you sure?",
			buttons: {
			  Yes: function() {
				  unenrollToModule(moduleId);
			    	$(this).dialog("close");		
			    },
			  No: function() {
			    	 $(this).dialog("close");
			    	 
			    }
			}
			
		});
		$('.confirmDialog').html("\n&nbsp;&nbsp;Are you sure you want to unenroll from this module?");
		$('.confirmDialog').dialog("open");
	}
	
	$(".dialog-confirm").dialog({
		resizable: false,
		height: 200,
		width: 300,
		modal: true,
		autoOpen:false,
		title:'&nbsp;',
		buttons: {	    
		      OK: function() {
		    	 $(this).dialog("close");
		    }
		}
	});

</script>
