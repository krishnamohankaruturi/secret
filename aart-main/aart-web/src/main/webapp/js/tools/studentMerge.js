var selectedStudentArr = [];
var getStudentTestGridData1 = [];
var getStudentTestGridData2 = [];
var selectedOrg = new Array();
var selectedStudentTestIds = [];
var allStudentTestIds = [];


function students_merge_tab() {
	$('#stateForStudentMerging').select2({
		placeholder : 'Select',
		multiple : false,
		allowClear:true
	});
	$('#stateForStudentMerging').val('').trigger('change.select2');
	

	populateStatesForStudentMerge();

	buildViewDuplicateStudentsByOrgGrid();
	selectedStudentArr = [];

	$('#mergeStudentButton').on("click",function(event) {
		event.preventDefault();
		enableDisableStudentBtn(0);
		selectedStudentArr = [];
		var $gridAuto = $("#mergeStudentGridTableId");
		$gridAuto[0].clearToolbar();
		$gridAuto.jqGrid('setGridParam', {
			datatype : "json",
			url : 'getViewDuplicateStudentInformationRecords.htm?q=1',
			search : false,
			postData : {
				"filters" : ""
			}
		}).trigger("reloadGrid", [ {
			page : 1
		} ]);
	});
 
	filteringOrganization($('#stateForStudentMerging'));
}

function buildViewDuplicateStudentsByOrgGrid() {
	var $gridAuto = $("#mergeStudentGridTableId");
	// Unload the grid before each request.
	$("#mergeStudentGridTableId").jqGrid('clearGridData');
	$("#mergeStudentGridTableId").jqGrid("GridUnload");

	selectedStudentArr = [];

	var gridWidthForVS = $gridAuto.parent().width();
	if (gridWidthForVS < 800) {
		gridWidthForVS = 1042;
	}
	var cellWidthForVS = gridWidthForVS / 5;

	var cmForStudentRecords;
	if ((typeof (viewFirstContactSurvey) !== 'undefined' && !viewFirstContactSurvey)
			&& (typeof (editFirstContactSurvey) !== 'undefined' && !editFirstContactSurvey)) {
		cmForStudentRecords = [
				{
					name : 'stateStudentIdentifier',
					formatter : viewStudentLinkFormatter,
					unformat : viewStudentLinkUnFormatter,
					width : cellWidthForVS,
					sorttype : 'int',
					search : true,
					hidden : false
				},
				{
					name : 'legalFirstName',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : false,
					formatter : escapeHtml
				},
				{
					name : 'legalMiddleName',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : true,
					formatter : escapeHtml
				},
				{
					name : 'legalLastName',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : false,
					formatter : escapeHtml
				},
				{
					name : 'dateOfBirthStr',
					width : cellWidthForVS,
					sorttype : 'date',
					search : true,
					hidden : true
				},
				{
					name : 'generationCode',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : true,
					formatter : escapeHtml
				},
				{
					name : 'genderString',
					width : cellWidthForVS,
					search : true,
					hidden : true,
					stype : 'select',
					searchoptions : {
						value : ':All;1:Male;0:Female',
						sopt : [ 'eq' ]
					}
				},
				{
					name : 'firstLanguage',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : true
				},
				{
					name : 'comprehensiveRace',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : true
				},
				{
					name : 'primaryDisabilityCode',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : true
				},
				{
					name : 'currentSchoolYears',
					width : cellWidthForVS,
					sorttype : 'int',
					search : true,
					hidden : false
				},
				{
					name : 'localStudentIdentifiers',
					width : cellWidthForVS,
					sorttype : 'int',
					search : true,
					hidden : false,
					formatter : escapeHtml
				},
				{
					name : 'residenceDistrictIdentifiers',
					width : cellWidthForVS,
					sorttype : 'int',
					search : true,
					hidden : false,
					formatter : escapeHtml
				},
				{
					name : 'attendanceSchoolDisplayIdentifiers',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : false
				},
				{
					name : 'attendanceSchoolNames',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : false
				},
				{
					name : 'gradeCourseName',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : false
				},
				{
					name : 'gradeCourseId',
					width : cellWidthForVS,
					sorttype : 'text',
					search : false,
					hidden : true
				},
				{
					name : 'rosterIds',
					width : cellWidthForVS,
					sorttype : 'int',
					search : true,
					hidden : true
				},
				{
					name : 'rosterName',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : true
				},{
					name : 'educatorName',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : true
				},
				{
					name : 'programName',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : true
				},
				{
					name : 'accessProfileStatus',
					formatter : accessProfileLinkFormatter,
					unformat : accessProfileLinkUnFormatter,
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					stype : 'select',
					searchoptions : {
						value : ':All;NO SETTINGS:NO SETTINGS;CUSTOM:CUSTOM',
						sopt : [ 'eq' ]
					},
					hidden : false
				},
				{
					name : 'status',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					stype : 'select',
					searchoptions : {
						value : ':All;Not Applicable:Not Applicable;Not Started:Not Started; Completed:Completed;Ready to Submit:Ready to Submit;In Progress:In Progress',
						sopt : [ 'eq' ]
					},
					hidedlg : true,
					hidden : true
				}, {
					name : 'hispanicEthnicity',
					width : cellWidthForVS,
					sorttype : 'text',
					search : false,
					hidden : true
				}, {
					name : 'giftedStudent',
					width : cellWidthForVS,
					sorttype : 'text',
					search : false,
					hidden : true
				}, {
					name : 'esolParticipationCode',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : true
				} ];
	} else {
		cmForStudentRecords = [
				{
					name : 'stateStudentIdentifier',
					formatter : viewStudentLinkFormatter,
					unformat : viewStudentLinkUnFormatter,
					width : cellWidthForVS,
					sorttype : 'int',
					search : true,
					hidden : false
				},
				{
					name : 'legalFirstName',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : false,
					formatter : escapeHtml
				},
				{
					name : 'legalMiddleName',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : true,
					formatter : escapeHtml
				},
				{
					name : 'legalLastName',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : false,
					formatter : escapeHtml
				},
				{
					name : 'dateOfBirthStr',
					width : cellWidthForVS,
					sorttype : 'date',
					search : true,
					hidden : true
				},
				{
					name : 'generationCode',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : true,
					formatter : escapeHtml
				},
				{
					name : 'genderString',
					width : cellWidthForVS,
					search : true,
					hidden : true,
					stype : 'select',
					searchoptions : {
						value : ':All;1:Male;0:Female',
						sopt : [ 'eq' ]
					}
				},
				{
					name : 'firstLanguage',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : true
				},
				{
					name : 'comprehensiveRace',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : true
				},
				{
					name : 'primaryDisabilityCode',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : true
				},
				{
					name : 'currentSchoolYears',
					width : cellWidthForVS,
					sorttype : 'int',
					search : true,
					hidden : false
				},
				{
					name : 'localStudentIdentifiers',
					width : cellWidthForVS,
					sorttype : 'int',
					search : true,
					hidden : false,
					formatter : escapeHtml
				},
				{
					name : 'residenceDistrictIdentifiers',
					width : cellWidthForVS,
					sorttype : 'int',
					search : true,
					hidden : false,
					formatter : escapeHtml
				},
				{
					name : 'attendanceSchoolDisplayIdentifiers',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : false
				},
				{
					name : 'attendanceSchoolNames',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : false
				},
				{
					name : 'gradeCourseName',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : false
				},
				{
					name : 'gradeCourseId',
					width : cellWidthForVS,
					sorttype : 'text',
					search : false,
					hidden : true
				},
				{
					name : 'rosterIds',
					width : cellWidthForVS,
					sorttype : 'int',
					search : true,
					hidden : true
				},
				{
					name : 'rosterName',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : true
				},{
					name : 'educatorName',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : true
				},
				{
					name : 'programName',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : true
				},
				{
					name : 'accessProfileStatus',
					formatter : accessProfileLinkFormatter,
					unformat : accessProfileLinkUnFormatter,
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					stype : 'select',
					searchoptions : {
						value : ':All;NO SETTINGS:NO SETTINGS;CUSTOM:CUSTOM',
						sopt : [ 'eq' ]
					},
					hidden : false
				},
				{
					name : 'status',
					formatter : firstContactLinkFormatter,
					unformat : firstContactLinkUnFormatter,
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					stype : 'select',
					searchoptions : {
						value : ':All;Not Applicable:Not Applicable;Not Started:Not Started; Completed:Completed;Ready to Submit:Ready to Submit;In Progress:In Progress',
						sopt : [ 'eq' ]
					},
					hidden : false
				}, {
					name : 'hispanicEthnicity',
					width : cellWidthForVS,
					sorttype : 'text',
					search : false,
					hidden : true
				}, {
					name : 'giftedStudent',
					width : cellWidthForVS,
					sorttype : 'text',
					search : false,
					hidden : true
				}, {
					name : 'esolParticipationCode',
					width : cellWidthForVS,
					sorttype : 'text',
					search : true,
					hidden : true
				} ];
	}
	$gridAuto
			.scb({

				mtype : "POST",
				datatype : "local",
				width : gridWidthForVS,
				rowNum : 10,
				rowList : [ 5, 10, 20, 30, 40, 60, 90 ],
				colNames : [ 'State ID', 'First Name', 'Middle Name',
						'Last Name', 'Date of Birth', 'Generation', 'Gender',
						'First Language', 'Comprehensive Race', 'Disability',
						'Current School Year', 'Local Id', 'District Id',
						'School Id', 'School Name', 'Grade', 'Grade Id',
						'Roster Id', 'RosterNames', 'Educator Name', 'Assessment Program', 'PNP Profile',
						'First Contact', 'Hispanic Ethnicity',
						'Gifted Student', 'ESOL Participation' ],
				colModel : cmForStudentRecords,
				sortname : 'stateStudentIdentifier',
				sortorder : 'asc',
				viewrecords : true,
				multiselect : true,
				loadonce : false,
				viewable : false,
				pager : '#pmergeStudentGridTableId',
				beforeSelectRow : function(rowid, e) {
					return true;
				},
				onSelectRow : function(rowid, status, e) {
					var rData = $('#mergeStudentGridTableId').jqGrid(
							'getRowData', rowid);
					var studentObj = {
						stateStudentIdentifier : rData.stateStudentIdentifier,
						legalFirstName : rData.legalFirstName,
						legalMiddleName : rData.legalMiddleName,
						legalLastName : rData.legalLastName,
						dateOfBirthStr : rData.dateOfBirthStr,
						attendanceSchoolDisplayIdentifiers : rData.attendanceSchoolDisplayIdentifiers,
						attendanceSchoolNames : rData.attendanceSchoolNames,
						rosterName :rData.rosterName,
						accessProfileStatus :rData.accessProfileStatus,
						genderString :rData.genderString,
						gradeCourseName :rData.gradeCourseName,
						firstContactSurvey :rData.status,
						currentSchoolYears :rData.currentSchoolYears,
						assessmentProgramName :rData.programName,
						hispanicEthnicity :rData.hispanicEthnicity,
						comprehensiveRace :rData.comprehensiveRace,
						primaryDisabilityCode :rData.primaryDisabilityCode,
						rosterId : rData.rosterIds,
						educatorName :rData.educatorName,
						id : rowid
					};
					if (status) {
						var found = false;
						$.each(selectedStudentArr, function(i){							
			   			    if(selectedStudentArr[i].stateStudentIdentifier === rData.stateStudentIdentifier) {
			   			    	found = true;
			   			    }
			   			 }); 
						
			     		 if( ! found )
						selectedStudentArr.push(studentObj);
						var grid = $(this);
						var cbsdis = $("tr#" + rowid
								+ ".jqgrow > td > input.cbox", grid);
						// if selected row and checkbox is disabled, do not let
						// it get checked.
						if (cbsdis.is(":disabled")) {
							cbsdis.prop('checked', false);
						}
					} else {
						$
								.each(
										selectedStudentArr,
										function(i) {
											if (selectedStudentArr[i].stateStudentIdentifier === rData.stateStudentIdentifier) {
												selectedStudentArr.splice(i, 1);
												return false;
											}
										});

					}
					enableDisableStudentBtn(selectedStudentArr);
				},
				onSelectAll : function(aRowids, status) {
					var grid = $(this);
					if (status) {
						// uncheck "protected" rows
						var cbs = $("tr.jqgrow > td > input.cbox:disabled",
								$(this)[0]);
						cbs.removeAttr("checked");
						var allRowsIds = grid.jqGrid('getDataIDs');
						for (var i = 0; i < allRowsIds.length; i++) {
							if ($(
									"#jqg_mergeStudentGridTableId_"
											+ allRowsIds[i], grid).is(
									":disabled")) {
								grid.jqGrid('setSelection', allRowsIds[i],
										false);
							}
							var result = $.grep(selectedStudentArr, function(
									studentO) {
								return studentO.id == allRowsIds[i];
							});
							
							if (result.length == 0) {
								var rData = $('#mergeStudentGridTableId')
										.jqGrid('getRowData', allRowsIds[i]);

								var studentObj = {
									stateStudentIdentifier : rData.stateStudentIdentifier,
									legalFirstName : rData.legalFirstName,
									legalMiddleName : rData.legalMiddleName,
									legalLastName : rData.legalLastName,
									dateOfBirthStr : rData.dateOfBirthStr,
									attendanceSchoolDisplayIdentifiers : rData.attendanceSchoolDisplayIdentifiers,
									attendanceSchoolNames : rData.attendanceSchoolNames,
									rosterName :rData.rosterName,
									accessProfileStatus :rData.accessProfileStatus,
									genderString :rData.genderString,
									gradeCourseName :rData.gradeCourseName,
									assessmentProgramName :rData.programName,
									firstContactSurvey :rData.status,
									currentSchoolYears :rData.currentSchoolYears,
									hispanicEthnicity :rData.hispanicEthnicity,
									comprehensiveRace :rData.comprehensiveRace,
									primaryDisabilityCode :rData.primaryDisabilityCode,
									rosterId : rData.rosterIds,
									educatorName :rData.educatorName,
									id : rowid
								};

								selectedStudentArr.push(studentObj);
							}
						}
					} else {
						var allRowsIds = grid.jqGrid('getDataIDs');
						for (var i = 0; i < allRowsIds.length; i++) {
							var result = $.grep(selectedStudentArr, function(
									studentO) {
								return studentO.id == allRowsIds[i];
							});
							if (result.length == 1) {
								var indx = selectedStudentArr
										.indexOf(result[0]);
								selectedStudentArr.splice(indx, 1);
							}
						}
					}
					enableDisableBtn(selectedStudentArr);
				},
				beforeRequest : function() {
					var currentPage = $(this).getGridParam('page');
					var lastPage = $(this).getGridParam('lastpage');

					if (lastPage != 0 && currentPage > lastPage) {
						$(this).setGridParam('page', lastPage);
						$(this).setGridParam({
							postData : {
								page : lastPage
							}
						});
					}

					if ($('#stateForStudentMerging').val() != null) {
						var orgs = new Array();
						orgs.push($('#stateForStudentMerging').val());
						selectedOrg = orgs;
						$(this).setGridParam({
							postData : {
								'orgChildrenIds' : function() {
									return orgs;
								}
							}
						});
					} else if ($(this).getGridParam('datatype') == 'json') {
						return false;
					}
				},
				localReader : {
					page : function(obj) {
						return obj.page !== undefined ? obj.page : "0";
					}
				},
				jsonReader : {
					page : function(obj) {
						return obj.page !== undefined ? obj.page : "0";
					},
					repeatitems : false,
					root : function(obj) {
						return obj.rows;
					}
				},
				loadComplete : function() {
					this.grid.hDiv.scrollLeft = this.grid.bDiv.scrollLeft;
					 var ids = $(this).jqGrid('getDataIDs');         
			         var tableid=$(this).attr('id');      
			            for(var i=0;i<ids.length;i++)
			            {         
			                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'legalFirstName') +' '+$(this).getCell(ids[i], 'legalLastName')+ ' Check Box');
			            }
			            $('input').removeAttr("aria-checked");
			            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
			             $('#cb_'+tableid).attr('title','User Grid All Check Box');
				             $.each(objs, function(index, value) {         
				              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
				                    $(value).attr('title',$(nm).text()+' filter');                          
				                    });
				}
			});
	$gridAuto[0].clearToolbar();
	$gridAuto.jqGrid('setGridParam', {
		postData : {
			"filters" : ""
		}
	}).trigger("reloadGrid", [ {
		page : 1
	} ]);
}

$(document).on('click','#mergeStudentNextButton',function(){
	$('#viewMergeStudentDetailsDiv').attr('data-studentdetails',JSON.stringify(selectedStudentArr));
	$('#viewMergeStudentDetailsDiv').dialog(
			{
				autoOpen : false,
				modal : true,
				width : 1000,
				height : 700,
				title : 'MergeStudents',
				create : function(event, ui) {
					var studentdata=JSON.parse($('#viewMergeStudentDetailsDiv').attr('data-studentdetails'));
					var widget = $(this).dialog("widget");
					$('#stateStudentIdentifierL').html(' '+$(selectedStudentArr[0].stateStudentIdentifier).html());
					$('#stateStudentIdentifierR').html(' '+$(selectedStudentArr[1].stateStudentIdentifier).html());
					$('#firstNameL').html(' '+studentdata[0].legalFirstName);
					$('#firstNameR').html(' '+studentdata[1].legalFirstName);
					$('#lastNameL').html(' '+studentdata[0].legalLastName);
					$('#lastNameR').html(' '+studentdata[1].legalLastName);
					$('#dateOfBirthL').html(' '+studentdata[0].dateOfBirthStr);
					$('#dateOfBirthR').html(' '+studentdata[1].dateOfBirthStr);
					$('#genderL').html(' '+studentdata[0].genderString);
					$('#genderR').html(' '+studentdata[1].genderString);
					$('#assessmentProgramL').html(' '+studentdata[0].assessmentProgramName);
					$('#assessmentProgramR').html(' '+studentdata[1].assessmentProgramName);
					$('#currentSchoolYearL').html(' '+studentdata[0].currentSchoolYears);
					$('#currentSchoolYearR').html(' '+studentdata[1].currentSchoolYears);
					$('#currentGradeL').html(' '+studentdata[0].gradeCourseName);
					$('#currentGradeR').html(' '+studentdata[1].gradeCourseName);
					$('#accessProfileL').html(' '+studentdata[0].accessProfileStatus);
					$('#accessProfileR').html(' '+studentdata[1].accessProfileStatus);
					$('#firstContactSurveyL').html(' '+studentdata[0].firstContactSurvey);
					$('#firstContactSurveyR').html(' '+studentdata[1].firstContactSurvey);
					$('#rosterL').html(' '+studentdata[0].rosterName);
					$('#rosterR').html(' '+studentdata[1].rosterName);
					$('#rosterIdL').html(' '+studentdata[0].rosterId);
					$('#rosterIdR').html(' '+studentdata[1].rosterId);
					$('#educatorNameL').html(' '+studentdata[0].educatorName);
					$('#educatorNameR').html(' '+studentdata[1].educatorName);
					$('#hispanicethnicityL').html(' '+studentdata[0].hispanicEthnicity);
					$('#hispanicethnicityR').html(' '+studentdata[1].hispanicEthnicity);
					$('#primaryDisabilityCodeL').html(' '+studentdata[0].primaryDisabilityCode);
					$('#primaryDisabilityCodeR').html(' '+studentdata[1].primaryDisabilityCode);
					$('#comprehensiveraceL').html(' '+studentdata[0].comprehensiveRace);
					$('#comprehensiveraceR').html(' '+studentdata[1].comprehensiveRace);
					
				},
				open : function(ev, ui) {
					var studentdata=JSON.parse($('#viewMergeStudentDetailsDiv').attr('data-studentdetails'));
					$('#stateStudentIdentifierL').html(' '+$(selectedStudentArr[0].stateStudentIdentifier).html());
					$('#stateStudentIdentifierR').html(' '+$(selectedStudentArr[1].stateStudentIdentifier).html());
					$('#firstNameL').html(' '+studentdata[0].legalFirstName);
					$('#firstNameR').html(' '+studentdata[1].legalFirstName);
					$('#lastNameL').html(' '+studentdata[0].legalLastName);
					$('#lastNameR').html(' '+studentdata[1].legalLastName);
					$('#dateOfBirthL').html(' '+studentdata[0].dateOfBirthStr);
					$('#dateOfBirthR').html(' '+studentdata[1].dateOfBirthStr);
					$('#genderL').html(' '+studentdata[0].genderString);
					$('#genderR').html(' '+studentdata[1].genderString);
					$('#assessmentProgramL').html(' '+studentdata[0].assessmentProgramName);
					$('#assessmentProgramR').html(' '+studentdata[1].assessmentProgramName);
					$('#currentSchoolYearL').html(' '+studentdata[0].currentSchoolYears);
					$('#currentSchoolYearR').html(' '+studentdata[1].currentSchoolYears);
					$('#currentGradeL').html(' '+studentdata[0].gradeCourseName);
					$('#currentGradeR').html(' '+studentdata[1].gradeCourseName);
					$('#accessProfileL').html(' '+studentdata[0].accessProfileStatus);
					$('#accessProfileR').html(' '+studentdata[1].accessProfileStatus);
					$('#firstContactSurveyL').html(' '+studentdata[0].firstContactSurvey);
					$('#firstContactSurveyR').html(' '+studentdata[1].firstContactSurvey);
					$('#rosterL').html(' '+studentdata[0].rosterName);
					$('#rosterR').html(' '+studentdata[1].rosterName);
					$('#rosterIdL').html(' '+studentdata[0].rosterId);
					$('#rosterIdR').html(' '+studentdata[1].rosterId);
					$('#educatorNameL').html(' '+studentdata[0].educatorName);
					$('#educatorNameR').html(' '+studentdata[1].educatorName);
					$('#hispanicethnicityL').html(' '+studentdata[0].hispanicEthnicity);
					$('#hispanicethnicityR').html(' '+studentdata[1].hispanicEthnicity);
					$('#primaryDisabilityCodeL').html(' '+studentdata[0].primaryDisabilityCode);
					$('#primaryDisabilityCodeR').html(' '+studentdata[1].primaryDisabilityCode);
					$('#comprehensiveraceL').html(' '+studentdata[0].comprehensiveRace);
					$('#comprehensiveraceR').html(' '+studentdata[1].comprehensiveRace);

				},
				close : function(ev, ui) {
					$('input[name=StudentDetails]').attr('checked',false);
					$('input[name=pnpOptionCheck]').attr('checked',false);
					$('input[name=fcsOptionCheck]').attr('checked',false);
					$('input[name=rosterOptionCheck]').attr('checked',false);
					selectedStudentArr = [];
					$('#mergeStudentGridTableId').trigger('reloadGrid');
					$("#mergeStudentNextButton").prop("disabled", true);
					$('#mergeStudentNextButton').addClass('ui-state-disabled');
					$("label.error").html('');
				}

			}).dialog('open');

	 renderStudentsTestSessionsRolesGrid('studentsTestSessionGrid');
});
$(document).on('click','#mergeButton',function(){
	var studentDetaisToRetainL =$('#StudentIdL').prop('checked');
	var studentDetaisToRetainR =$('#StudentIdR').prop('checked');
	var checkPNPOptionsL = $('#checkPNPOptionL').prop('checked');
	var checkPNPOptionsR = $('#checkPNPOptionR').prop('checked');
	var checkFCSOptionL =$('#checkFcsL').prop('checked');
	var checkFCSOptionR =$('#checkFcsR').prop('checked');
	var checkRosterOptionL =$('#checkRosterL').prop('checked');
	var checkRosterOptionR =$('#checkRosterR').prop('checked');
	var studentPnpToRetain='',studentFcsToRetain='',studentRosterToRetain='';	
	var studentToRetain,studentToRemove;
	
	var selectedStudentTestIdList = {};
 	selectedStudentTestIdList.studentTestId =[];
 	var studentTestIdsToSend = [];
 	var unSelectedIdsToSend = [];
 	 
	if(studentDetaisToRetainL ===true){
		$('#StudentIdR').prop('unchecked');
		studentToRetain=selectedStudentArr[0].id;
		studentToRemove=selectedStudentArr[1].id;
	}else if(studentDetaisToRetainR ===true){
		$('#StudentIdR').prop('checked');
		studentToRetain=selectedStudentArr[1].id;
		studentToRemove=selectedStudentArr[0].id;
	}
	
	for(i=0; i < selectedStudentTestIds.length; i++){	
		studentTestIdsToSend.push(selectedStudentTestIds[i].studentTestId);
	 }
		
	for( var i = 0; i < allStudentTestIds.length; i++){ 
		for( var j = 0; j < selectedStudentTestIds.length; j++) {
			   if ( allStudentTestIds[i] == selectedStudentTestIds[j].studentTestId) {
				   allStudentTestIds.splice(i, 1); 
				   }
		}
	}
	
	for (var i = 0; i < allStudentTestIds.length; i++ ){
		unSelectedIdsToSend.push(allStudentTestIds[i]);
	}	
	
	for(var i=0; i < studentTestIdsToSend.length; i++){
 		selectedStudentTestIdList.studentTestId[i] = studentTestIdsToSend[i];
 	}
	
	if(checkPNPOptionsL === true){
		studentPnpToRetain=selectedStudentArr[0].id;
	}else if(checkPNPOptionsR === true){
		studentPnpToRetain=selectedStudentArr[1].id;
	}
	
    if(checkFCSOptionL === true){
    	studentFcsToRetain=selectedStudentArr[0].id;
    }else if(checkFCSOptionR === true){
    	studentFcsToRetain=selectedStudentArr[1].id;
    }
    if(checkRosterOptionL === true){
    	studentRosterToRetain=selectedStudentArr[0].id;
    }else if(checkRosterOptionR === true){
    	studentRosterToRetain=selectedStudentArr[1].id;
    }
    
    var valid = $('#validateMergeStudentDetails').validate(mergeStudentDetailsvaldiation()).form();
    if(valid)
    {
    	$.ajax({
	    		url : 'getStudentMergeDetails.htm',
	    		dataType: 'json',
	    		type: "POST",
	    		data:{
	    			studentToRetain:studentToRetain,
	    			studentToRemove:studentToRemove,
	    			studentPnpToRetain:studentPnpToRetain,
	    			studentFcsToRetain:studentFcsToRetain,
	    			studentRosterToRetain:studentRosterToRetain,
	    			selectedStudentTestIdList:selectedStudentTestIdList,
	    			unSelectedStudentTestIds:unSelectedIdsToSend
	    		}
    		}).done(function(data) {
    			$('#mergeStudentDetailsSucess').text(data.success);
    			$('#mergeStudentDetailsSucess').show();
    			setTimeout("$('#mergeStudentDetailsSucess').hide()", 3000);
    		 });
    }else{
		$('label.error').css({'display':'block','border':'0px','margin-left': '14%','margin-bottom': '3%'});
	}
});
	
function mergeStudentDetailsvaldiation(){
	$('#validateMergeStudentDetails').validate({
		rules:{
			StudentDetails:{
				required:true
			},
			pnpOptionCheck:{
				required:true
			},
			fcsOptionCheck:{
				required:true
			},
			rosterOptionCheck:{
				required:true
			}
		},
		messages:{
			StudentDetails:{
				required:"Please select atleast one student details",
			},
			pnpOptionCheck:{
				required:"Please select atleast one Pnp option ",
			},
			fcsOptionCheck:{
				required:"Please select atleast one fcs option",
			},
			rosterOptionCheck:{
				required:"Please select atleast one roster option",
			}
		}
	});
}

function viewStudentLinkFormatter(cellvalue, options, rowObject) {
	// Save student info in local storage for reuse
	var studentFirstName = rowObject.legalFirstName;
	var studentLastName = rowObject.legalLastName;
	var studentMiddleName = rowObject.legalMiddleName;
	var studentInfo = new Object();
	studentInfo.studentFirstName = studentFirstName;
	if (studentMiddleName)
		studentInfo.studentMiddleName = studentMiddleName;
	else
		studentInfo.studentMiddleName = "-";
	studentInfo.studentLastName = studentLastName;
	studentInfo.id = rowObject.id;
	studentInfo.stateStudentIdentifier = rowObject.stateStudentIdentifier;
	if (rowObject.gradeCourseName)
		studentInfo.gradeCourseName = rowObject.gradeCourseName;
	else
		studentInfo.gradeCourseName = "-";
	if (rowObject.dateOfBirthStr)
		studentInfo.dateOfBirth = rowObject.dateOfBirthStr;
	else
		studentInfo.dateOfBirth = "-";
	if (rowObject.genderString)
		studentInfo.gender = rowObject.genderString;
	else
		studentInfo.gender = "-";
	window.localStorage.setItem(rowObject.id, JSON.stringify(studentInfo));
	var editLink = false;
	/*
	 * if (typeof(editStudentPermission) !== 'undefined'){ editLink = true; }
	 */
	return '<a href="javascript:openViewStudentPopup(\'' + rowObject.id
			+ '\',\'' + escapeHtml(rowObject.stateStudentIdentifier) + '\','
			+ editLink + ');">' + escapeHtml(cellvalue) + '</a>';
}

function viewStudentLinkUnFormatter(cellvalue, options, rowObject) {
	return;
}

function openViewStudentPopup(studentId, stateStudentIdentifier, editLink) {
	gridParam = window.localStorage.getItem(studentId);
	var studentInfo = JSON.parse(gridParam);
	// Decode for displaying
	// studentInfo.studentFirstName =
	// decodeURIComponent(studentInfo.studentFirstName);
	// studentInfo.studentLastName =
	// decodeURIComponent(studentInfo.studentLastName);
	var dialogTitle = "View Student Record - " + studentInfo.studentFirstName
			+ " ";
	if (studentInfo.studentMiddleName != null
			&& studentInfo.studentMiddleName.length > 0
			&& studentInfo.studentMiddleName != '-') {
		dialogTitle += studentInfo.studentMiddleName + " ";
	}
	dialogTitle += studentInfo.studentLastName;
	var action = 'view';

	$('#viewStudentDetailsDiv').dialog(
			{
				autoOpen : false,
				modal : true,
				width : 1087,
				height : 700,
				title : dialogTitle,
				create : function(event, ui) {
					var widget = $(this).dialog("widget");
				},
				beforeClose : function() {
					// $(this).dialog('close');
					$(this).html('');
					var $gridAuto = $("#mergeStudentGridTableId");
					$gridAuto.jqGrid('setGridParam', {
						datatype : "json",
						url : 'getViewDuplicateStudentInformationRecords.htm?q=1',
						search : false,
					}).trigger("reloadGrid");
					selectedStudentArr = [];
					$("#mergeStudentNextButton").prop("disabled", true);
					$('#mergeStudentNextButton').addClass('ui-state-disabled');
				}

			}).load('viewInactiveStudentDetails.htm', {
		"studentId" : studentId,
		"editLink" : editLink,
		"action" : action,
		"selectedOrg[]" : selectedOrg
	}).dialog('open');

}

// Custom formatter for AccessProfile link.
function accessProfileLinkFormatter(cellvalue, options, rowObject) {
	// Save student info in local storage for reuse

	if (typeof (viewStudentPNPpermission) !== 'undefined'
			&& viewStudentPNPpermission) {
		var studentFirstName = rowObject.legalFirstName;
		var studentLastName = rowObject.legalLastName;
		var studentMiddleName = rowObject.legalMiddleName;
		var studentInfo = new Object();
		studentInfo.studentFirstName = studentFirstName;
		if (studentMiddleName)
			studentInfo.studentMiddleName = studentMiddleName;
		else
			studentInfo.studentMiddleName = "-";
		studentInfo.studentLastName = studentLastName;
		studentInfo.id = rowObject.id;
		studentInfo.stateStudentIdentifier = rowObject.stateStudentIdentifier;
		if (rowObject.gradeCourseName)
			studentInfo.gradeCourseName = rowObject.gradeCourseName;
		else
			studentInfo.gradeCourseName = "-";
		if (rowObject.dateOfBirthStr)
			studentInfo.dateOfBirth = rowObject.dateOfBirthStr;
		else
			studentInfo.dateOfBirth = "-";
		if (rowObject.genderString)
			studentInfo.gender = rowObject.genderString;
		else
			studentInfo.gender = "-";
		window.localStorage.setItem(rowObject.id, JSON.stringify(studentInfo));
		return '<a href="javascript:localRef_accessProfileDetails(\''
				+ rowObject.id + '\',\'' + rowObject.stateStudentIdentifier
				+ '\',\'' + rowObject.programName + '\');">' + cellvalue
				+ '</a>';
	} else {
		return cellvalue;
	}
}

// Custom unformatter for AccessProfile link.
function accessProfileLinkUnFormatter(cellvalue, options, rowObject) {
	return;
}

// This method takes the studentId and opens the dialog with PNP summary data
// for which the user double clicks the row.

function accessProfileDetails(studentId, stateStudentIdentifier,
		assessmentProgramCode) {
	gridParam = window.localStorage.getItem(studentId);
	var studentInfo = JSON.parse(gridParam);
	// Decode for displaying
	// studentInfo.studentFirstName =
	// decodeURIComponent(studentInfo.studentFirstName);
	// studentInfo.studentLastName =
	// decodeURIComponent(studentInfo.studentLastName);
	var dialogTile = studentInfo.studentFirstName + " ";
	dialogTile += studentInfo.studentLastName;
	var assmtPrgmCode = '';
	if (assessmentProgramCode !== undefined && assessmentProgramCode !== null) {
		assmtPrgmCode = assessmentProgramCode;
	}
	var viewAccessProfileUrl = 'viewAccessProfile.htm?&studentId=' + studentId
			+ "&assessmentProgramCode="
			+ encodeURIComponent(assmtPrgmCode.toString())
			+ "&stateStudentIdentifier="
			+ encodeURIComponent(stateStudentIdentifier.toString())
			+ "&studentInfo=" + encodeURIComponent(gridParam)
			+ "&previewAccessProfile=" + "noPreview";
	
	
	$('#accessProfileDiv').dialog(
			{	autoOpen : false,
				modal : true,
				width : 1087,
				height : 700,
				title : escapeHtml(dialogTile),
				create : function(event, ui) {					
				},
				beforeClose : function() {
					// $(this).dialog('close');
					$(this).html('');
					var $gridAuto = $("#mergeStudentGridTableId");
					if ($('#viewStudentDetailsDiv').dialog("isOpen")) {
						$('#viewStudentDetailsDiv').dialog("close");
					}
					$gridAuto.jqGrid('setGridParam', {
						datatype : "json",
						url : 'getViewDuplicateStudentInformationRecords.htm?q=1',
						search : false,
					}).trigger("reloadGrid");
				}

			}).load(viewAccessProfileUrl).dialog('open');
}
// needed to maintain reference for call made via a link in row data
var localRef_accessProfileDetails = accessProfileDetails;

// Custom formatter for FirstContact link.
function firstContactLinkFormatter(cellvalue, options, rowObject) {
	// Save student info in local storage for reuse
	var studentFirstName = rowObject.legalFirstName;
	var studentLastName = rowObject.legalLastName;
	var studentMiddleName = rowObject.legalMiddleName;
	// encode the values for ",&,? characters
	// studentFirstName = encodeURIComponent(studentFirstName);
	// studentLastName = encodeURIComponent(studentLastName);
	// studentMiddleName = encodeURIComponent(studentMiddleName);
	var studentInfo = new Object();
	studentInfo.studentFirstName = studentFirstName;
	studentInfo.studentMiddleName = studentMiddleName;
	studentInfo.studentLastName = studentLastName;

	window.localStorage.setItem('FC_' + rowObject.id, JSON
			.stringify(studentInfo));
	if ((typeof (editFirstContactSurvey) !== 'undefined' && editFirstContactSurvey)) {
		if (cellvalue == 'Not Applicable')
			return cellvalue;
		else
			return '<a href="javascript:localRef_viewFirstContactDetails(\''
					+ rowObject.id + '\');">' + cellvalue + '</a>';
	} else if ((typeof (viewFirstContactSurvey) !== 'undefined' && viewFirstContactSurvey)) {
		if (cellvalue == 'Not Applicable' || cellvalue == 'NOT STARTED')
			return cellvalue;
		else
			return '<a href="javascript:localRef_viewFirstContactDetails(\''
					+ rowObject.id + '\');">' + cellvalue + '</a>';
	}
}

// Custom unformatter for FirstContact link.
function firstContactLinkUnFormatter(cellvalue, options, rowObject) {
	return;
}

// Show popup screen on clicking the FirstContact link.
function viewFirstContactDetails(studentId) {
	// alert(studentFirstName + " " + studentLastName);
	gridParam = window.localStorage.getItem('FC_' + studentId);
	var studentInfo = $.parseJSON(gridParam);
	var dialogTile = studentInfo.studentFirstName + " "
			+ studentInfo.studentLastName;
	$('#firstContactViewDiv').dialog(
			{
				autoOpen : false,
				resizable : false,
				modal : true,
				width : 1110,
				height : 700,
				title : escapeHtml(dialogTile),
				create : function(event, ui) {
					var widget = $(this).dialog("widget");
					
				},
				beforeClose : function() {
					// $(this).dialog('close');
					saveFirstContactResponsesOnClose();
					$(this).html('');
					if ($('#viewStudentDetailsDiv').dialog("isOpen")) {
						$('#viewStudentDetailsDiv').dialog("close");
					}
					var $gridAuto = $("#mergeStudentGridTableId");
					initializePageLabelArrays();
					lastPageTobeSaved = 0;
					$gridAuto.jqGrid('setGridParam', {
						datatype : "json",
						url : 'getViewDuplicateStudentInformationRecords.htm?q=1',
						search : false,
					}).trigger("reloadGrid");
				}
			}).load('firstContactResponseView.htm?&studentId=' + studentId)
			.dialog('open');
}
var localRef_viewFirstContactDetails = viewFirstContactDetails;

function populateStatesForStudentMerge() {
	var stateOrgSelect = $('#stateForStudentMerging');
	clrStateOptionText = '';

	$('#stateForStudentMerging').html("");
	$('#stateForStudentMerging').append(
			$('<option></option>').val("").html("Select")).trigger('change.select2');

	$.ajax({
		url : 'getStatesOrgsForUser.htm',
		data : {},
		dataType : 'json',
		type : "GET" 
	}).done(function(states) {
		if (states !== undefined && states !== null && states.length > 0) {
			$.each(states, function(i, stateOrg) {
				clrStateOptionText = states[i].organizationName;
				stateOrgSelect.append($('<option></option>').val(
						stateOrg.id).html(clrStateOptionText));
			});
			if (states.length == 1) {
				stateOrgSelect.find('option:first').removeAttr('selected')
						.next('option').attr('selected', 'selected');
				$("#stateForStudentMerging").trigger('change');
			}
		}

	});
}

function enableDisableStudentBtn(selectedRowId) {	
	if (selectedRowId.length == 2) {
		$("#mergeStudentNextButton").prop("disabled", false);
		$('#mergeStudentNextButton').removeClass('ui-state-disabled');
	} else {
		$("#mergeStudentNextButton").prop("disabled", true);
		$('#mergeStudentNextButton').addClass('ui-state-disabled');
	}
}

var studentsTestsGridData = [];


function renderStudentsTestSessionsRolesGrid(studentTestGridId){
	var selectedStudnetArrayList = {};
 	
	selectedStudnetArrayList.id =[];
 	var dataArrayToSend = [];
 	
 	 dataArrayToSend.push(selectedStudentArr[0].id);
     dataArrayToSend.push(selectedStudentArr[1].id);
 	
 	for(var i=0; i < dataArrayToSend.length; i++){
 		selectedStudnetArrayList.id[i] = dataArrayToSend[i];
 	}
    

	var rowData = {};
	getStudentTestGridData1 = [];
	getStudentTestGridData2 = [];
 	
$.ajax({
     url: 'getStudentTestsBasedOnStudentIds.htm',
     data:{
		
		"studentsList":selectedStudnetArrayList,
		
		},
     dataType: "json",
     type: 'POST'
   
		}).done(function(data){
         	if(data == null){
         		alert('Failed');
         		return;
         	} else {
         		var mergeStudentDetails = data.studentsTest;
         		
         		if(mergeStudentDetails != null && mergeStudentDetails != undefined){
         			
         			
        			
        			// Iterate over all roles and push the data to 
        			$(data.studentsTest).each(function(idx, studentsTest){
        				// initialize here.
        				allStudentTestIds.push(studentsTest.studentTestId);
    				rowData = {studentTestId :0, testName:0, stateStudentIdentifier:0, gradeCourse:0, categoryCode:0, activeFlag:0, 
        					testSessionName:0, subject:0, testStatus:0, testSessionId:0, assessmentCode:0};
				
        			rowData.studentTestId = studentsTest.studentTestId;
					rowData.testName = studentsTest.testName;
					rowData.stateStudentIdentifier = studentsTest.stateStudentIdentifier;
					rowData.gradeCourse = studentsTest.tcGradeCourseName;	
					rowData.categoryCode = studentsTest.categoryCode;	
					rowData.activeFlag = studentsTest.activeFlag;	
					rowData.testSessionName = studentsTest.testSessionName;	
					rowData.subject = studentsTest.subject;	
					rowData.testStatus = studentsTest.testStatus;	
					rowData.testSessionId = studentsTest.testSessionId;	
					rowData.assessmentCode = studentsTest.assessmentCode;	
												
					if(rowData.stateStudentIdentifier == $(selectedStudentArr[0].stateStudentIdentifier).html()){
						getStudentTestGridData1.push(rowData);
				
					} else if (rowData.stateStudentIdentifier == $(selectedStudentArr[1].stateStudentIdentifier).html()){
						getStudentTestGridData2.push(rowData);
						
					}
						
				});
        			
        			renderStudentTestDetailsGridL('studentsTestSessionGridL', 'e');
        			renderStudentTestDetailsGridR('studentsTestSessionGridR', 'e');
	        			
	         		}
	         	}
	         });
       			
}


function renderStudentTestDetailsGridL(studentTestDetailsGridId, screen){
	$('#studentsTestSessionGridL').jqGrid("GridUnload");
	currentMode = screen;
	
	var dataType = "local";
	var gridData = [];//addUserRolesGridData;
	if(screen === 'e'){
			gridData = getStudentTestGridData1;	
		
	}else if(screen === 'm'){
		gridData = mergeUserRolesGridData;
	}
	myDelOptions = {
		
        processing:true
    };
	var pagerId = '#studentsTestSessionGridPagerL';
	
	$('#studentsTestSessionGridL').jqGrid({ 
		datatype: dataType, 
		data: gridData,
		editurl: 'clientArray',
		colNames:['id', 'State Student Identifier', 'Student Test Session Name' ,'Student Test Name', 'Student Test Id', 'Subject', 'Test Status', 'Active Status', 'Test Session Id', 'Assessment Program'], 
		rowNum : 10,
		rowList : [ 10, 20, 30, 40, 60, 90 ],
		width: 440,
		page: 1,
		pager : pagerId,
		viewrecords : true,
		altclass: 'altrow',
		altRows:true,
		height : 'auto',
		multiselect: true,
		autowidth:true,
		shrinkToFit:false,
		forceFit:true,
		
		colModel:[ 
			
		{name : 'id', index : 'id', search : false, hidden: true, key: true, hidedlg: false},	
	       	{name:'stateStudentIdentifier', index:'stateStudentIdentifier', hidden: true}, 
	       	{name:'testSessionName', index:'testSessionName', width:100, sortable:true}, 
		{name:'testName', index:'testName', width:100,sortable:true}, 
		{name:'studentTestId', index:'studentTestId', width:100, sortable:true}, 
	       	{name:'subject', index:'subject', width:100, sortable:true},
	       	{name:'testStatus', index:'testStatus', width:100, sortable:false},  
	       	{name:'activeFlag', index:'activeFlag', width:100, sortable:false},
	       	{name:'testSessionId', index:'testSessionId', width:100, sortable:false},
	       	{name:'assessmentCode', index:'assessmentCode', width:100, sortable:false}
	   		],
	   		beforeSelectRow : function(rowid, e) {
				return true;
			},
	   		onSelectRow : function(rowid,status, e) {
				var rData = $('#studentsTestSessionGridL').jqGrid(
						'getRowData', rowid);
				var studentTestDetailsObj = {
					stateStudentIdentifier : rData.stateStudentIdentifier,
					studentTestId : rData.studentTestId,
					id : rowid
				};
				if (status) {
					var found = false;
					$.each(selectedStudentTestIds, function(i){							
		   			    if(selectedStudentTestIds[i].studentTestId === rData.studentTestId) {
		   			    	found = true;
		   			    }
		   			 }); 
					 if( ! found )
					selectedStudentTestIds.push(studentTestDetailsObj);
					 var grid = $(this);
						var cbsdis = $("tr#" + rowid
								+ ".jqgrow > td > input.cbox", grid);
						// if selected row and checkbox is disabled, do not let
						// it get checked.
						if (cbsdis.is(":disabled")) {
							cbsdis.prop('checked', false);
						}
					
				}
				else{
					
					$.each(selectedStudentTestIds,function(i) {
								if (selectedStudentTestIds[i].studentTestId === rData.studentTestId) {
									selectedStudentTestIds.splice(i, 1);
									return false;
								}
							});					
				}					    
       	},	
	});
	
	$('#studentsTestSessionGridL').navGrid($(pagerId), {edit: true, add: true, del: true, search: true, refresh: true});
	$('#studentsTestSessionGridL').jqGrid('filterToolbar', { stringResult: false, searchOnEnter: false});
}


function renderStudentTestDetailsGridR(studentTestDetailsGridId, screen){
	$('#studentsTestSessionGridR').jqGrid("GridUnload");
	currentMode = screen;
	
	var dataType = "local";
	var gridData = [];//addUserRolesGridData;
	if(screen === 'e'){
			gridData = getStudentTestGridData2;		
		
	}else if(screen === 'm'){
		gridData = mergeUserRolesGridData;
	}
	myDelOptions = {		
        processing:true
    };
	var pagerId = '#studentsTestSessionGridPagerR';
	
	$('#studentsTestSessionGridR').jqGrid({ 
		datatype: dataType, 
		data: gridData,
		editurl: 'clientArray',
		colNames:['id', 'State Student Identifier', 'Student Test Session Name' ,'Student Test Name', 'Student Test Id', 'Subject', 'Test Status', 'Active Status', 'Test Session Id', 'Assessment Program'], 
		rowNum : 10,
		rowList : [ 10, 20, 30, 40, 60, 90 ],
		width: 440,
		page: 1,
		pager : pagerId,
		viewrecords : true,
		altclass: 'altrow',
		altRows:true,
		height : 'auto',
		multiselect: true,
		autowidth:true,
		shrinkToFit:false,
		forceFit:true,
		
		colModel:[ 
			
		{name : 'id', index : 'id', search : false, hidden: true, key: true, hidedlg: false},	
	       	{name:'stateStudentIdentifier', index:'stateStudentIdentifier', hidden: true}, 
	       	{name:'testSessionName', index:'testSessionName', width:100, sortable:true}, 
		{name:'testName', index:'testName', width:100,sortable:true}, 
		{name:'studentTestId', index:'studentTestId', width:100, sortable:true}, 
	       	{name:'subject', index:'subject', width:100, sortable:true},
	       	{name:'testStatus', index:'testStatus', width:100, sortable:false},  
	       	{name:'activeFlag', index:'activeFlag', width:100, sortable:false},
	       	{name:'testSessionId', index:'testSessionId', width:100, sortable:false},
	       	{name:'assessmentCode', index:'assessmentCode', width:100, sortable:false}
	   		],
	   		beforeSelectRow : function(rowid, e) {
				return true;
			},
	   		onSelectRow : function(rowid,status, e) {
				var rData = $('#studentsTestSessionGridR').jqGrid(
						'getRowData', rowid);
				var studentTestDetailsObj = {
					stateStudentIdentifier : rData.stateStudentIdentifier,
					studentTestId : rData.studentTestId,
					id : rowid
				};
				if (status) {
					var found = false;
					$.each(selectedStudentTestIds, function(i){							
		   			    if(selectedStudentTestIds[i].studentTestId === rData.studentTestId) {
		   			    	found = true;
		   			    }
		   			 }); 
					 if( ! found )
					selectedStudentTestIds.push(studentTestDetailsObj);
					 var grid = $(this);
						var cbsdis = $("tr#" + rowid
								+ ".jqgrow > td > input.cbox", grid);
						// if selected row and checkbox is disabled, do not let
						// it get checked.
						if (cbsdis.is(":disabled")) {
							cbsdis.prop('checked', false);
						}
					
				}
				else{
					
					$.each(selectedStudentTestIds,function(i) {
							if (selectedStudentTestIds[i].studentTestId === rData.studentTestId) {
								selectedStudentTestIds.splice(i, 1);
								return false;
							}
						});					
				}			
				    
       	},	
	});
	
	$('#studentsTestSessionGridR').navGrid($(pagerId), {edit: true, add: true, del: true, search: true, refresh: true});
	$('#studentsTestSessionGridR').jqGrid('filterToolbar', { stringResult: false, searchOnEnter: false});
}
    
  