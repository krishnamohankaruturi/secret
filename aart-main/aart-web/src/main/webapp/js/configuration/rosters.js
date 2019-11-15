	function resetRoster(){
		$("#rosterSelect").select2();
		var options = sortDropdownOptions($("#rosterSelect option"));
		options.appendTo("#rosterSelect");
		$('#rosterSelect').val("").trigger('change.select2');
		$("#uploadRoster").hide();
		$("#viewRoster").hide();
		$("#createRoster").hide();
	}
	
	
	function rostersInitNew(action){			
			/*$("#rosterSelect").change(function() {*/
				//$('#uploadReport').hide();
				//var action = $(this).val();
		$('td[id^="view_"]').on("click",function(){
			   	$(".EditTable.ViewTable tbody").prepend("<tr style='border-bottom: 2pt solid #e9e9e9;'><th style='font-weight:bold'>Column Name</th><th style='font-weight:bold'>Column Value</th></tr>");	
			});
				if (action == "view-roster"){
					rosterGridsReset();
					$('#viewRosterOrgFilterForm')[0].reset();	
					$("#viewRostersTableId").jqGrid('clearGridData');					
					if(!gViewRosterLoadOnce){					
						viewRostersInit();
					}else{ 
						$('#viewRosterOrgFilter').orgFilter('reset');
						$("#viewRostersTableId").jqGrid('clearGridData');
						/*if($("#viewRostersTableId")[0].grid && $("#viewRostersTableId")[0]['clearToolbar']){
							$("#viewRostersTableId")[0].clearToolbar();
						}*/
						$grid = $("#viewRostersTableId");
						var myStopReload = function () {
				            $grid.off("jqGridToolbarBeforeClear", myStopReload);
				            return "stop"; // stop reload
				        };
				        $grid.on("jqGridToolbarBeforeClear", myStopReload);
				        if ($grid[0].ftoolbar) {
				        	$grid[0].clearToolbar();
				        }
						$('#viewRosterOrgFilterForm')[0].reset();
					}
					$("label.error").html('');
					$("#viewRoster").show();
					$("#uploadRoster").hide();
					$("#createRoster").hide();
					$('#breadCrumMessage').text("Configuration: Rosters - View Roster");
				}else if(action == "upload-roster"){
					rosterGridsReset();
					if(!gUploadRostersLoadOnce){
						uploadRostersInit();
					}else{
						$('#uploadRosterOrgFilter').orgFilter('reset');
						$('#uploadRosterFilterForm')[0].reset();
						$("#rosterGrid").jqGrid('clearGridData');
						if($("#rosterGrid")[0].grid && $("#rosterGrid")[0]['clearToolbar']){
							$("#rosterGrid")[0].clearToolbar();
						}
					}
					$("#Roster_TemplatedownloadquickHelpPopup").hide();
					$("#viewRoster").hide();
					$("#createRoster").hide();
					$('#uploadRosterError').html('');
					$('#uploadRosterError').hide();			
					$("label.error").html('');
					$("#rosterUploadReport").html('');
					$("#uploadRoster").show();
					$('#breadCrumMessage').text("Configuration: Rosters - Upload Roster");
					showRosterUploadGrid();
				}else if(action == "create-roster"){
					rosterGridsReset();
					if(!gCreateRosterLoadOnce){
						createRostersInit();
					}else{
						$('#createRosterOrgFilter').orgFilter('reset');
						$('#createRosterSearchForm')[0].reset();
						$('#createRosterForm')[0].reset();
						$('#createContentAreaSelect , #createCourseSelect')
					    .empty()
					    .append('<option selected="selected" value="">Select</option>');							
						$("#createEducatorGrid").jqGrid('clearGridData');
						$("#createStudentGrid").jqGrid('clearGridData'); 
						if($("#createStudentGrid")[0].grid && $("#createStudentGrid")[0]['clearToolbar']){
							$("#createStudentGrid")[0].clearToolbar();
						}
						if($("#createEducatorGrid")[0].grid && $("#createEducatorGrid")[0]['clearToolbar']){
							$("#createEducatorGrid")[0].clearToolbar();
						}
					}
					$("#viewRoster").hide();
					$("#uploadRoster").hide();
					$("label.error").html('');
					$('#createRosterSuccess').html("");
					$("#createRoster").show();
					$('#breadCrumMessage').text("Configuration: Rosters - Create Roster Manually");
				
				}
				else{
					$("#uploadRoster").hide();
					$("#viewRoster").hide();
					$("#createRoster").hide();
					$('#breadCrumMessage').text("Configuration: Rosters");
				}
			 /* }).trigger( "change" ); */
				
	}
	
	function rosterGridsReset(){
		$("#viewRostersTableId").jqGrid('clearGridData');
		$("#editEducatorGrid").jqGrid('clearGridData');
		$("#editStudentGrid").jqGrid('clearGridData');
		$("#createEducatorGrid").jqGrid('clearGridData');
		$("#createStudentGrid").jqGrid('clearGridData');
		
			}