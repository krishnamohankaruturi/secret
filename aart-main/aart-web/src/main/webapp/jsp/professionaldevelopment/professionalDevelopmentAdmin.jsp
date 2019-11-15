<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/jsp/include.jsp" %>

<div>
		<div>
			<ul class="vertTabs">
			 <security:authorize access="hasAnyRole('PUB_MODULE', 'UNPUB_MODULE','REL_MODULE', 'UNREL_MODULE', 'EDIT_MODULES', 'STATE_CEU')">
			  	<li><a href="#tabs_existingmodules" id="existingModules" class="active"> <fmt:message key="label.pd.admin.existingmodules"/> </a></li>
			 </security:authorize>
			 <security:authorize access="hasRole('EDIT_MODULES')">	
			  	<li><a href="#tabs_newmodule" id="newModule">  <fmt:message key="label.pd.admin.newmodule"/>  </a></li>			  	
			  </security:authorize>
			  <li><a href="#tabs_reporting" id="reporting"> <fmt:message key="label.pd.admin.reporting"/>  </a></li>	
			</ul>
		   	<div class="head hidden">
					<span class="info_message" id="search_failed">
						<h1>An error occurred.</h1>
					</span>					
			</div>
			<span class="info_message ui-state-highlight editSuccessMessage hidden" id="editSuccessMessage" ><fmt:message key="label.pd.editModuleSuccess"/></span>			
			<div id="tabs_existingmodules" class="modulesTable"><br>
				<div id="resultsSection" class="kite-table">
					<table class="responsive" id="modulesTable"></table>
					<div id="modulesPager"></div>
				</div>
			</div>
			
			<security:authorize access="hasRole('EDIT_MODULES')">	
				<div id="tabs_newmodule" class="modulesTable">
					<jsp:include page="professionalDevelopmentCreateModules.jsp" />
				</div>
			</security:authorize>
			
			<div id="tabs_reporting" class="modulesTable">
				<jsp:include page="adminReporting.jsp" />
			</div>
		</div>
    <div id="confirmPubDialog"></div>
    
    <div id ="editModule" hidden="hidden" class="hidden">
		<jsp:include page="editModule.jsp"></jsp:include>
	</div>
</div>

<script>

	var isPDStateAdmin = false;
	var isStateCEUEditAccess = false;
	<security:authorize access="hasAnyRole('PUB_MODULE', 'UNPUB_MODULE','REL_MODULE', 'UNREL_MODULE', 'EDIT_MODULES')">
		isPDStateAdmin = true;
	</security:authorize>
	<security:authorize access="hasRole('STATE_CEU')">
		isStateCEUEditAccess = true;
	</security:authorize>
	
	var isPDDistrictAdmin = false;
	
	<security:authorize access="!hasAnyRole('PUB_MODULE', 'UNPUB_MODULE','REL_MODULE', 'UNREL_MODULE', 'EDIT_MODULES', 'STATE_CEU')">
		<c:if test="${user.accessLevel == 50 }">
			isPDDistrictAdmin = true;
		</c:if>
	</security:authorize>

	
	//var isPDDistrictAdmin = !isPDStateAdmin && !isStateCEUEditAccess;
	
	$("#admin").click(function() {

		if(isPDDistrictAdmin || (!isPDStateAdmin && !isStateCEUEditAccess)){
			$("#reporting")[0].click();
	    } else {
			$('#pdbreadCrumMessage').text("Professional Development: <fmt:message key='label.pd.admin.heading'/>");
			getModules();
			$("#existingModules").addClass("active");
			$("#reporting").removeClass("active");
			$("#newModule").removeClass("active");
			$("#tabs_existingmodules").removeClass("hidden");
			$("#tabs_newmodule").addClass("hidden");
			$("#tabs_reporting").addClass("hidden");
			$("#resultsSection").removeClass("hidden");	    	
	    }
	});
	
	$("#newModule").click(function() {
		$("#createModuleForm").data('validator').resetForm();
		$('#pdbreadCrumMessage').text("Professional Development: <fmt:message key='label.pd.admin.heading'/> - Create Module");
	});
	
	$("#existingModules").click(function() {
		$('#pdbreadCrumMessage').text("Professional Development: <fmt:message key='label.pd.admin.heading'/>");
		$('#modulesTable').jqGrid("GridUnload");
		getModules();
		$('#modulesTable').trigger("reloadGrid");
		
		$("#existingModules").addClass("active");
		$("#reporting").removeClass("active");
		$("#newModule").removeClass("active");
		$("#tabs_existingmodules").removeClass("hidden");
		$("#tabs_newmodule").addClass("hidden");
		$("#tabs_reporting").addClass("hidden");
		$("#resultsSection").removeClass("hidden");
	
	});	
	
	$("#newModule").click(function() {
		$("#newModule").addClass("active");
		$("#existingModules").removeClass("active");
		$("#reporting").removeClass("active");
		$("#tabs_newmodule").removeClass("hidden");
		$("#tabs_reporting").addClass("hidden");
		$("#tabs_existingmodules").addClass("hidden");
		$("#resultsSection").addClass("hidden");
		$("#createModuleForm").data('validator').resetForm();
		$('#pdbreadCrumMessage').text("Professional Development: <fmt:message key='label.pd.admin.heading'/> - Create Module");		
	});
	
	var getColumnIndexByName = function (grid, columnName) {
	    var cm = grid.jqGrid('getGridParam', 'colModel'), i, l = cm.length;
	    for (i = 0; i < l; i++) {
	        if (cm[i].name === columnName) {
	            return i; // return the index
	        }
	    }
	    return -1;
	}
	
	function getModules(){
		var url = "getModules.htm";
		<security:authorize access="hasAnyRole('REL_MODULE', 'UNREL_MODULE')">
			url = "getOrgModules.htm";
		</security:authorize>
		$("#modulesTable").jqGrid({
			url:url, 
			datatype: "json", 
	        colNames: [
	                   '<fmt:message key="label.pd.existingmodules.id"/>',
	                   '<fmt:message key="label.pd.existingmodules.name"/>',
	                   '<fmt:message key="label.pd.existingmodules.status"/>',
	                   '<fmt:message key="label.pd.existingmodules.ceu"/>',
	                   '<fmt:message key="label.pd.existingmodules.actions"/>'
	                   ],                     
	                   
	        colModel: [
	   				   {name: 'id', index: 'id',  align: 'center', align: 'center', sortable:true},
	                   {name: 'name', index: 'name', align: 'center', formatter: moduleNameLinkFormatter, unformat: moduleNameLinkUnFormatter,},
	                   {name: 'status', index: 'status', align: 'center',formatter: statusFormatter},
	                   <security:authorize access="hasAnyRole('REL_MODULE', 'UNREL_MODULE')">
	                   		{name: 'ceu', index: 'ceu', align: 'center', hidden: false},
	                   </security:authorize>
	                   <security:authorize access="!(hasAnyRole('REL_MODULE', 'UNREL_MODULE'))">
	                   		{name: 'ceu', index: 'ceu', align: 'center', hidden: true},
	                   	</security:authorize>	                   		
	                   {name: 'actions', index: 'actions', align: 'center',sortable:false,formatter: 'actions',formatoptions: {
	                	   editbutton : false, 
	                	   delbutton : false
	                   }}
	                    ], 
	    	           height : 'auto',
	    	           shrinkToFit: true,
			           rowNum:10, 
			           rowList:[10,20,30], 
			           pager: '#modulesPager',  
			           viewrecords: true, 
			           sortorder: "asc",
			           sortname : 'id',
					   altclass: 'altrow',
					   emptyrecords : "<fmt:message key='label.common.norecordsfound'/>",
					   altRows : true,
					   hoverrows : true,
					   multiselect : false,
					   toppager: false,
			           //caption:'<fmt:message key="label.pd.existingmodules.results"/>' 
						loadComplete: function () {
							<security:authorize access="hasAnyRole('PUB_MODULE', 'UNPUB_MODULE','REL_MODULE', 'UNREL_MODULE', 'EDIT_MODULES', 'STATE_CEU')">	
				                var iCol = getColumnIndexByName($("#modulesTable"), 'actions');
				                var grid_ids = $("#modulesTable").jqGrid('getDataIDs');
				                $(this).find(">tbody>tr.jqgrow>td:nth-child(" + (iCol + 1) + ")")
					            .each(function(index,element) {
					               	var gridRow = $("#modulesTable").jqGrid('getRowData',grid_ids[index]);							        
					            	var status = gridRow['status'];
					            	status = status.toUpperCase();
					            	if(status == "UNPUBLISHED") {
					            		<security:authorize access="hasRole('PUB_MODULE')">
									        $("<div>", {
						                        title: "Publish",
						                        mouseover: function() {
						                            $(this).addClass('ui-state-hover');
						                        },
						                        mouseout: function() {
						                            $(this).removeClass('ui-state-hover');
						                        },
						                        click: function(e) {
						                        	publishModule($(e.target).closest("tr.jqgrow").attr("id"),
							                        			gridRow['name']
						                        			);
						                        }
						                    }
						                  ).css({"margin-right": "5px", float: "left", cursor: "pointer"})
						                   .addClass("ui-pg-div ui-inline-custom")
						                   .append('<span class="ui-icon ui-icon-document"></span>')
						                   .prependTo($(this).children("div"));
								        </security:authorize>
					               	} else if (status == "UNRELEASED" || status == "NOT RELEASED") {
					               		<security:authorize access="hasRole('REL_MODULE')">
						               	 	$("<div>", {
						                        title: "Release",
						                        mouseover: function() {
						                            $(this).addClass('ui-state-hover');
						                        },
						                        mouseout: function() {
						                            $(this).removeClass('ui-state-hover');
						                        },
						                        click: function(e) {
						                        	releaseModule($(e.target).closest("tr.jqgrow").attr("id"),
							                        			gridRow['name']
						                        			);
						                        }
						                    }
						                  ).css({"margin-right": "5px", float: "left", cursor: "pointer"})
						                   .addClass("ui-pg-div ui-inline-custom")
						                   .append('<span class="ui-icon ui-icon-key"></span>')
						                   .prependTo($(this).children("div"));
					               	 	</security:authorize>
					               	} else if (status == "RELEASED") {
					               		<security:authorize access="hasRole('UNREL_MODULE')">
					               	 	$("<div>", {
					                        title: "Unrelease",
					                        mouseover: function() {
					                            $(this).addClass('ui-state-hover');
					                        },
					                        mouseout: function() {
					                            $(this).removeClass('ui-state-hover');
					                        },
					                        click: function(e) {
					                        	unreleaseModule($(e.target).closest("tr.jqgrow").attr("id"),
						                        			gridRow['name']
					                        			);
					                        }
					                    }
					                  ).css({"margin-right": "5px", float: "left", cursor: "pointer"})
					                   .addClass("ui-pg-div ui-inline-custom")
					                   .append('<span class="ui-icon ui-icon-cancel"></span>')
					                   .prependTo($(this).children("div"));
				               	 	</security:authorize>
				               	} else if (status == "PUBLISHED") {
				               		<security:authorize access="hasRole('UNPUB_MODULE')">
				               	 	$("<div>", {
				                        title: "Unpublish",
				                        mouseover: function() {
				                            $(this).addClass('ui-state-hover');
				                        },
				                        mouseout: function() {
				                            $(this).removeClass('ui-state-hover');
				                        },
				                        click: function(e) {
				                        	unpublishModule($(e.target).closest("tr.jqgrow").attr("id"),
					                        			gridRow['name']
				                        			);
				                        }
				                    }
				                  ).css({"margin-right": "5px", float: "left", cursor: "pointer"})
				                   .addClass("ui-pg-div ui-inline-custom")
				                   .append('<span class="ui-icon ui-icon-document-b"></span>')
				                   .prependTo($(this).children("div"));				               	 	
				               	 </security:authorize>
			               		}
					            	
						         if (status == "PUBLISHED" || status == "UNPUBLISHED" 
						        		 ||status == "UNRELEASED" || status == "NOT RELEASED") {
						               	<security:authorize access="hasAnyRole('EDIT_MODULES', 'STATE_CEU')">
									        $("<div>", {
						                        title: "Edit",
						                        mouseover: function() {
						                            $(this).addClass('ui-state-hover');
						                        },
						                        mouseout: function() {
						                            $(this).removeClass('ui-state-hover');
						                        },
						                        click: function(e) {
						                        	openEditModuleDialog($(e.target).closest("tr.jqgrow").attr("id"));
						                        }
						                    }
						                  ).css({"margin-right": "5px", float: "left", cursor: "pointer"})
						                   .addClass("ui-pg-div ui-inline-custom")
						                   .append('<span class="ui-icon ui-icon-pencil"></span>')
						                   .prependTo($(this).children("div"));
							        </security:authorize>
						         }
						        
					             });
			                </security:authorize>
						}                		               
		}); 
		$("#modulesTable").jqGrid('navGrid','#modulesPager',{edit:false,add:false,del:false,search:false});
	}
	
	function statusFormatter ( cellvalue, options, rowObject ) {
		<security:authorize access="hasAnyRole('REL_MODULE', 'UNREL_MODULE')">
		if(cellvalue=="Published" || cellvalue=="UNRELEASED")
			return "Not Released";
		</security:authorize>
		return cellvalue;
	}
	
	function publishModule(id,name) {
		$.ajax({
            url: 'setModuleStatus.htm',            
            dataType: 'json',
            data: {
            	moduleId : id,
            	status: "PUBLISHED"
            },
            type: "POST",
            async: false,
            success : function(data) {
            	$('#confirmPubDialog').dialog({
        			resizable: false,
        			height: 200,
        			width: 500,
        			modal: true,
        			autoOpen:false,			
        			title: "Published",
        			buttons: {
        			  Ok: function() {
        				$(this).dialog("close");		
        			    }
        			}
        		});
            	$('#confirmPubDialog').html(name + " has been published successfully. It will be available for PD State Admins to review it and assign it to their PD participants.");
        		$('#confirmPubDialog').dialog("open");
        		$('#modulesTable').jqGrid("GridUnload");
            	getModules();
				$('#modulesTable').trigger("reloadGrid");
            }
        });	
	}
	
	function unpublishModule(id,name) {
		$.ajax({
            url: 'setModuleStatus.htm',            
            dataType: 'json',
            data: {
            	moduleId : id,
            	status: "UNPUBLISHED"
            },
            type: "POST",
            async: false,
            success : function(data) {
            	$('#confirmPubDialog').dialog({
        			resizable: false,
        			height: 200,
        			width: 500,
        			modal: true,
        			autoOpen:false,			
        			title: "UnPublished",
        			buttons: {
        			  Ok: function() {
        				$(this).dialog("close");		
        			    }
        			}
        		});
            	$('#confirmPubDialog').html(name + " has been Unpublished successfully.");
        		$('#confirmPubDialog').dialog("open");
        		$('#modulesTable').jqGrid("GridUnload");
            	getModules();
				$('#modulesTable').trigger("reloadGrid");
				
            	// refresh both the grids on Modules Tab
				$('#browseModulesTable').trigger("reloadGrid");
				$('#myModulesTable').trigger("reloadGrid");
            }
        });	
	}	
	
	function releaseModule(id,name) {
		$.ajax({
            url: 'setModuleStatus.htm',            
            dataType: 'json',
            data: {
            	moduleId : id,
            	status: "RELEASED"
            },
            type: "POST",
            async: false,
            success : function(data) {
            	$('#confirmPubDialog').dialog({
        			resizable: false,
        			height: 200,
        			width: 500,
        			modal: true,
        			autoOpen:false,			
        			title: "Released",
        			buttons: {
        			  Ok: function() {
        				$(this).dialog("close");		
        			    }
        			}
        		});
            	$('#confirmPubDialog').html(name + " has been released successfully. It will be available for PD participants.");
        		$('#confirmPubDialog').dialog("open");
        		$('#modulesTable').jqGrid("GridUnload");
            	getModules();
				$('#modulesTable').trigger("reloadGrid");
				
            	// refresh both the grids on Modules Tab
				$('#browseModulesTable').trigger("reloadGrid");
				$('#myModulesTable').trigger("reloadGrid");
            }
        });	
	}
	
	function unreleaseModule(id,name) {
		$.ajax({
            url: 'setModuleStatus.htm',            
            dataType: 'json',
            data: {
            	moduleId : id,
            	status: "UNRELEASED"
            },
            type: "POST",
            async: false,
            success : function(data) {
            	$('#confirmPubDialog').dialog({
        			resizable: false,
        			height: 200,
        			width: 500,
        			modal: true,
        			autoOpen:false,			
        			title: "UnReleased",
        			buttons: {
        			  Ok: function() {
        				$(this).dialog("close");		
        			    }
        			}
        		});
            	$('#confirmPubDialog').html(name + " has been unreleased successfully. It will be available for PD State Admins to review it and assign it to their PD participants.");
        		$('#confirmPubDialog').dialog("open");
        		$('#modulesTable').jqGrid("GridUnload");
            	getModules();
				$('#modulesTable').trigger("reloadGrid");
				
            	// refresh both the grids on Modules Tab
				$('#browseModulesTable').trigger("reloadGrid");
				$('#myModulesTable').trigger("reloadGrid");
            }
        });	
	}	
	
	//Name link formatter
	function moduleNameLinkFormatter(cellvalue, options, rowObject) {
        <security:authorize access="hasRole('EDIT_MODULES')">
           var status = rowObject[2].toUpperCase();
           if (status == "PUBLISHED" || status == "UNPUBLISHED" 
         		 || status == "UNRELEASED" || status == "NOT RELEASED") {
				return '<a href="javascript:openEditModuleDialog(\'' + options.rowId + 
			 		'\');" title="Click to edit module details">' + cellvalue + '</a>';
           }
		</security:authorize>
		return cellvalue;
	}
	//Id, Name link unformatter
	function moduleNameLinkUnFormatter(cellvalue, options, cellObject) {
		 return  $(cellObject).text(); 
	}
	
	//Show Module details on clicking the id/name value.
	function openEditModuleDialog(moduleId) {

   		$('.editValidate').hide();
   		$('.editDuplicate').hide();
   		$('.editTagInputValidate').hide();
   		
		if (moduleId)	{			
			$.ajax({
		         url: 'getAllModuleDetails.htm',
		         data: '&moduleId=' + moduleId,
		         dataType: "json",
		         type: 'POST',
		         error: function(jqXHR, textStatus, errorThrown){
		        	 
		         },
		         success: function(data){
		         	if(data == null){
		         		alert('Failed');
		         		return;
		         	} else {
		         		var module = data.module;
		         		if(module != null && module != undefined){
		        	   		
		        			$('#emoduleName').val(module.name);
		        			$('#edescription').val(module.description);
		        			$('#esuggestAudience').val(module.suggestedaudience);
						    $("#eassessmentPrograms").val(module.assessmentprogramid);
						    $("#etests").val(module.testid).change();
						    $("#epassingScoreSpinner").val(module.passingScore);
						    $("#etutorials").val(module.tutorialid);
						    $("#eceu").val(module.stateCEU);
						    if(module.requiredflag != null && module.requiredflag){
						    	$('input:radio[name=erequiredflag][value=Yes]').click();
						    } else {
						    	$('input:radio[name=erequiredflag][value=No]').click();
						    }
						    
						    $("#erolesSelect").val(module.groupIds);
						    $("#erolesSelect").trigger('change.select2');
						    $("#erolesSelect").select2();
						    
						    $('#pdEditTags').find('span').each(function() { 
								   $(this).remove();
							});
						    
						    $.each( module.tags, function( index, value ){
						    	epopulateTagList(value);
						    });
						    
						    $('#moduleId').val(module.id);
						    
						    if(isPDStateAdmin){
						    	//disable all the fields
						    	if(!isStateCEUEditAccess){
						    		$( "#eceu").prop( "disabled", true );						    		
						    		$( "#editModuleSave").hide();
						    	}

						    	$( "#emoduleName" ).prop( "disabled", true );
						    	$( "#eassessmentPrograms" ).prop( "disabled", true );
						    	$( "#etests" ).prop( "disabled", true );
						    	$( "#etutorials" ).prop( "disabled", true );
						    	$( "#erolesSelect" ).prop( "disabled", true );
						    	$("#epassingScoreSpinner").prop( "disabled", true );
						    	$('input[name=erequiredflag]').attr("disabled",true);
						    	$( "#edescription" ).prop( "disabled", true );
						    	$( "#esuggestAudience" ).prop( "disabled", true );
						    	$( "#eaddTagInput" ).prop( "disabled", true );
						    	$( "#eaddTagButton" ).hide();
						    	$( "#tagClickInfo" ).hide();						    	
						    } else {
						    	$( "#divCEU").hide();
						    }
		         		}
		         	}
		         }
		     });
		}
		
		$('#editModule').dialog({
			autoOpen: false,
			modal: true,
			//resizable: false,
			width: 900,
			height: 640,
			title: "Modify Module",
			create: function(event, ui) { 
			    var widget = $(this).dialog("widget");		
			},
			close: function(ev, ui) {				
			}
		}).dialog('open');
	}

</script>