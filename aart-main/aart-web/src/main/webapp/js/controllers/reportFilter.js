var reportFilter = can.Control({
}, {
	'init' : function(element, options) {
		this.options = options;
		this.element = element;
		this.element.html(can.view('js/views/reportFilter.ejs', {}));
		this._populateAssessmentPrograms();
		jQuery.validator.setDefaults({
			submitHandler: function() {		
			},
			errorPlacement: function(error, element) {
				if(element.hasClass('required') || element.prop('type') == 'file') {
					error.insertAfter(element.next());
				}
				else {
		    		error.insertAfter(element);
				}
		    }
		});
	},
	_populateAssessmentPrograms: function() {
		var me = this;
		var apSelect = $('#assessmentProgram'), optionText='';
		$('.messages').html('').hide();
		apSelect.find('option').filter(function(){return $(this).val() > 0}).remove().end();
		
		$.ajax({
			url: 'getReportAssessmentPrograms.htm',
			dataType: 'json',
			type: "POST",
			success: function(data) {				
				if (data !== undefined && data !== null && data.length > 0) {
					for (var i = 0, length = data.length; i < length; i++) {
						optionText = data[i].programName;
						apSelect.append($('<option></option>').val(data[i].id).html(optionText));
					}
					if (data.length != 1) {
						apSelect.prop('disabled', false);
    				}
				} else {
					$('body, html').animate({scrollTop:0}, 'slow');
					$('.messages').html(me.options.newreport_no_assessmentprogram).show();
				}
			}
		});
	},
	'#assessmentProgram change' : function(el, ev) {
		ev.preventDefault();
		var me = this,assessmentProgramId = $('#assessmentProgram').val(), reportCategory = $('#reportCategory');
		this._resetSelection("assessmentProgram");
		$('.messages').html('').hide();
		reportCategory.find('option').filter(function(){return $(this).val() > 0}).remove().end();
		
		if (assessmentProgramId != null && !isNaN(assessmentProgramId) && Number(assessmentProgramId) > 0) {
            $.ajax({
                url: 'getReportCategories.htm',
                data: {
                	assessmentProgramId: assessmentProgramId
                },
                type: "GET",
                success: function(data) {
                	if (data !== undefined && data !== null && data.length > 0) {
                		for (var i = 0, length = data.length; i < length; i++) {
                			reportCategory.append($('<option>', { value: data[i].categoryCode, text : data[i].categoryName }));
    					}
                		if (data.length != 1) {
                			reportCategory.prop('disabled', false);
        				}
                	} else {
                		$('body, html').animate({scrollTop:0}, 'slow');
                		$('.messages').html(me.options.newreport_no_reportcategory).show();
                	}
                },
                error: function() {
                	$('body, html').animate({scrollTop:0}, 'slow');
            		$('.messages').html(me.options.unknown_error).show();
	            }
            });
        }
	},
	'#reportCategory change' : function(el, ev) {
		ev.preventDefault();
		var me = this,assessmentProgramId = $('#assessmentProgram').val(), testingProgram = $('#testingProgram'), reportCategory = $('#reportCategory').val();
		this._resetSelection("reportCategory");
		$('.messages').html('').hide();
		testingProgram.find('option').filter(function(){return $(this).val() > 0}).remove().end();
		 
		if(reportCategory == "ROSTER") {
			$('#rosterDiv').show();
			$('#districtDiv').show();
			$('#schoolDiv').show();
			$('#stateDiv').show();
			$('#studentDiv').show();
			$('#gradeDiv').hide();
			$('#yearDiv').hide();
		} else if(reportCategory == "STATE") {
			$('#districtDiv').hide();
			$('#schoolDiv').hide();
			$('#rosterDiv').hide();
			$('#studentDiv').hide();
		} else if(reportCategory == "DISTRICT") {
			$('#districtDiv').show();
			$('#schoolDiv').hide();
			$('#rosterDiv').hide();
			$('#studentDiv').hide();
		} else if (reportCategory == "STUDENT") {
			$('#districtDiv').show();
			$('#stateDiv').show();
			$('#schoolDiv').show();
			$('#rosterDiv').hide();
			$('#gradeDiv').hide();
			$('#yearDiv').hide();
			$('#studentDiv').show();
			//$('#studentDiv').find("div.bcg_select").remove();
		}else if (reportCategory == "SCHOOL") {
			$('#districtDiv').show();
			$('#schoolDiv').show();
			$('#rosterDiv').hide();
			$('#studentDiv').hide();
		}else if (reportCategory == "PARENT_REPORT") {
			$('#districtDiv').hide();
			$('#schoolDiv').hide();
			$('#rosterDiv').hide();
			$('#yearDiv').hide();
			$('#gradeDiv').hide();
			$('#stateDiv').hide();
			$('#studentDiv').hide();
		}
		
		if (reportCategory != null && reportCategory != "0") {
            $.ajax({
                url: 'getTestingProgramsForReporting.htm',
                data: {
                	assessmentProgramId: assessmentProgramId
                },
                type: "GET",
                success: function(data) {
                	if (data !== undefined && data !== null && data.length > 0) {
                		for (var i = 0, length = data.length; i < length; i++) {
                			testingProgram.append($('<option>', { value: data[i].id, text : data[i].programName }));
    					}
                		if (data.length != 1) {
                			testingProgram.prop('disabled', false);
        				}
                	} else {
                		$('body, html').animate({scrollTop:0}, 'slow');
                		$('.messages').html(me.options.newreport_no_testingprogram).show();
                	}
                },
                error: function() {
                	$('body, html').animate({scrollTop:0}, 'slow');
            		$('.messages').html(me.options.unknown_error).show();
	            }
            });
        }
	},
	'#testingProgram change' : function(el, ev) {
		ev.preventDefault();
		var me = this, testingProgramId = $('#testingProgram').val(), state = $('#state');
		this._resetSelection("testingProgram");
		$('.messages').html('').hide();
		state.find('option').filter(function(){return $(this).val() > 0}).remove().end();
		if (testingProgramId != null && !isNaN(testingProgramId) && Number(testingProgramId) > 0) {
			var contentArea = $('#contentArea');
			contentArea.find('option').filter(function(){return $(this).val() > 0}).remove().end();
			var assessmentProgramId = $('#assessmentProgram').val();
			if(assessmentProgramId != null && !isNaN(assessmentProgramId) && Number(assessmentProgramId) > 0){
				$.ajax({
	                url: 'getContentAreas.htm',
	                data: {
	                	assessmentProgramId: assessmentProgramId,
	                	testingProgramId: testingProgramId
	                },
	                type: "GET",
	                success: function(data) {
	                	if (data !== undefined && data !== null && data.length > 0) {
	                		for (var i = 0, length = data.length; i < length; i++) {
	                			contentArea.append($('<option>', { value: data[i].id, text : data[i].name }));
	    					}
	                		if (data.length != 1) {
	                			contentArea.prop('disabled', false);
	        				} 
	                	} else {
	                		$('body, html').animate({scrollTop:0}, 'slow');
	                		$('.messages').html(me.options.newreport_no_contentarea).show();
	                	}
	                },
	                error: function() {
	                	$('body, html').animate({scrollTop:0}, 'slow');
	            		$('.messages').html(me.options.unknown_error).show();
		            }
	            });
			}
			$.ajax({
                url: 'getStatesOrgsForUser.htm',
                data: { },
                type: "GET",
                success: function(data) {
                	if (data !== undefined && data !== null && data.length > 0) {
                		for (var i = 0, length = data.length; i < length; i++) {
                			state.append($('<option>', { value: data[i].id, text : data[i].organizationName }));
    					}
                		if (data.length != 1) {
                			state.prop('disabled', false);
        				}
                	} else {
                		$('body, html').animate({scrollTop:0}, 'slow');
                		$('.messages').html(me.options.newreport_no_state).show();
                	}
                },
                error: function() {
                	$('body, html').animate({scrollTop:0}, 'slow');
            		$('.messages').html(me.options.unknown_error).show();
	            }
            });
        }
	},
	'#state change' : function(el, ev) {
		ev.preventDefault();
		var me = this, stateId = $('#state').val(), district = $('#district'), reportCategory = $('#reportCategory').val();
		this._resetSelection("STATE");
		$('.messages').html('').hide();
		district.find('option').filter(function(){return $(this).val() > 0}).remove().end();
		$("#stateName").val($("#state option:selected").text());

		if (stateId != null && !isNaN(stateId) && Number(stateId) > 0) {
			if(reportCategory == "STUDENT" || reportCategory != "STATE") {
	            $.ajax({
	            	url: 'getOrgsBasedOnUserContext.htm',
		            data: { orgId: stateId, orgLevel: 50, orgType:"DT" },
	                type: "GET",
	                success: function(data) {
	                	if (data !== undefined && data !== null && data.length > 0) {
	                		for (var i = 0, length = data.length; i < length; i++) {
	                			district.append($('<option>', { value: data[i].id, text : data[i].organizationName }));
	    					}
	                		if (data.length != 1) {
	                			district.prop('disabled', false);
	        				}
	                	} else {
	                		$('body, html').animate({scrollTop:0}, 'slow');
	                		$('.messages').html(me.options.newreport_no_district).show();
	                	}
	                },
	                error: function() {
	                	$('body, html').animate({scrollTop:0}, 'slow');
	            		$('.messages').html(me.options.unknown_error).show();
		            }
	            });
			} else {
				$('#roster').trigger('change');
			}
		}
	},
	'#district change' : function(el, ev) {
		ev.preventDefault();
		var me = this, districtId = $('#district').val(), school = $('#school'), reportCategory = $('#reportCategory').val();
		this._resetSelection("DISTRICT");
		$('.messages').html('').hide();
		school.find('option').filter(function(){return $(this).val() > 0}).remove().end();
		$("#districtName").val($("#district option:selected").text());

		if (districtId != null && !isNaN(districtId) && Number(districtId) > 0) {
			if(reportCategory == "STUDENT" || reportCategory == "ROSTER" || reportCategory == "SCHOOL") {
	            $.ajax({
	                url: 'getOrgsBasedOnUserContext.htm',
	                data: { orgId: districtId, orgLevel: 70, orgType:"SCH" },
	                type: "GET",
	                success: function(data) {
	                	if (data !== undefined && data !== null && data.length > 0) {
	                		for (var i = 0, length = data.length; i < length; i++) {
	                			school.append($('<option>', { value: data[i].id, text : data[i].organizationName }));
	    					}
	                		if (data.length != 1) {
	                			school.prop('disabled', false);
	        				}
	                	} else {
	                		$('body, html').animate({scrollTop:0}, 'slow');
	                		$('.messages').html(me.options.newreport_no_school).show();
	                	}
	                },
	                error: function() {
	                	$('body, html').animate({scrollTop:0}, 'slow');
	            		$('.messages').html(me.options.unknown_error).show();
		            }
	            });
			} else {
				$('#roster').trigger('change');
			}
        }
	},
	'#school change' : function(el, ev) {
		ev.preventDefault();
		var me = this, schoolId = $('#school').val(), roster = $('#roster')
		, reportCategory = $('#reportCategory').val(), contentAreaId = $('#contentArea').val();
		this._resetSelection("SCHOOL");
		var student = $('#student');
		$('.messages').html('').hide();
		roster.find('option').filter(function(){return $(this).val() > 0}).remove().end();
		$("#schoolName").val($("#school option:selected").text());

		if (schoolId != null && !isNaN(schoolId) && Number(schoolId) > 0) {
			if(reportCategory == "ROSTER") {
				if (contentAreaId != null && !isNaN(contentAreaId) && Number(contentAreaId) > 0) {
					// do nothing.
				} else {
					contentAreaId = null;
				}
				$.ajax({
					url: 'getRostersByTeacher.htm',
					data: { selectedOrg: schoolId, contentAreaId: contentAreaId},
					dataType: 'json',
					type: "POST",
					success: function(data) {
						var optionText='';
						roster.empty();
						roster.append($('<option></option>').val('').html('Select'));
						if (data !== undefined && data !== null && data.length > 0) {
							for (var i = 0, length = data.length; i < length; i++) {
								optionText = data[i].courseSectionName;									
								roster.append($('<option></option>').val(data[i].id).html(optionText));
							}
							
							if (data.length != 1) {
	        					roster.prop('disabled', false);
	        				} 
						} else {
							$('body, html').animate({scrollTop:0}, 'slow');
							$('.messages').html(me.options.newreport_no_roster).show();
						}
						//roster.sortOptions(); 
						//roster.val([]);  //Hack: for some reason a value is selected, unselect all options
						/*roster.multiSelect({
							noneSelectedText:'Select',
							multiple: true,
							selectedList: 1,
							header: ''
						}).multiselectfilter({label: '', placeholder:'Enter Keywords', autoReset: true});*/
					}
				});
			} else if(reportCategory == "STUDENT") {
				$.ajax({
	                url: 'getStudentInformationRecordsForDLMStudentReport.htm',
	                data: { schoolOrgId: schoolId, contentAreaId: $('#contentArea').val()},
	                type: "POST",
	                success: function(data) {
	                	if (data !== undefined && data !== null && data.length > 0) {
	                		for (var i = 0, length = data.length; i < length; i++) {
	                			student.append("<option data-grade = '"+ data[i].gradeCourse.name +"' value='" + data[i].student.id + "'>" + data[i].student.legalFirstName + " " +  data[i].student.legalLastName + " (ID:" + data[i].student.stateStudentIdentifier + ")" + "</option>");
	    					}
	                		if (data.length != 1) {
	                			student.prop('disabled', false);
	        				}
	                	} else {
	                		$('body, html').animate({scrollTop:0}, 'slow');
	                		$('.messages').html(me.options.newreport_no_grade).show();
	                	}
	                },
	                error: function() {
	                	$('body, html').animate({scrollTop:0}, 'slow');
	            		$('.messages').html(me.options.unknown_error).show();
		            }
	            });
			} else {
				$('#roster').trigger('change');
			}
		}
	},	
	'#roster change' : function(el, ev) {
		ev.preventDefault();
		var reportCategory = $('#reportCategory').val();
		if(reportCategory == "ROSTER") {
			/**
			 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15500 : DLM Class Roster Report - online report
			 * Build Roster Report for DLM organizations.
			 */
			var rosterId = $('#roster').val(), student = $('#student');	
			student.empty();
			if (rosterId != null && !isNaN(rosterId) && Number(rosterId) > 0) {
				$.ajax({
	                url: 'getStudentsByRosterId.htm',
	                data: { rosterId: rosterId},
	                type: "POST",
	                success: function(data) {
	                	if (data !== undefined && data !== null && data.length > 0) {
	                		for (var i = 0, length = data.length; i < length; i++) {
	                			student.append("<option value='" + data[i].id + "'>" + data[i].legalFirstName + " " +  data[i].legalLastName + " (ID:" + data[i].stateStudentIdentifier + ")" + "</option>");
	    					}
	                		if (data.length != 1) {
	                			student.prop('disabled', false);
	        				}
	                	} else {
	                		$('body, html').animate({scrollTop:0}, 'slow');
	                		$('.messages').html(me.options.newreport_no_grade).show();
	                	}
	                	
	                	student.val('').trigger('change.select2');
	                	
	                	//student.multiSelect({noneSelectedText:'Select',});
	                	
	                },
	                error: function() {
	                	$('body, html').animate({scrollTop:0}, 'slow');
	            		$('.messages').html(me.options.unknown_error).show();
		            }
	            });
			} else {
				if($("#studentDiv").find("div.bcg_select").length > 0) {
					student.children('option').remove();
					student.append($('<option></option>').val('').html('Select'));
					student.select2("destroy");
					$("#studentDiv").find("div.bcg_select").remove();
				}
			}
		} else {
			var me = this, schoolId = $('#school').val(), contentArea = $('#contentArea'),
			stateId = $('#state').val(), districtId = $('#district').val(),assessmentProgramId = $('#assessmentProgram').val(), testingProgramId = $('#testingProgram').val() ;
			//this._resetSelection("ROSTER");
			$('.messages').html('').hide();
			contentArea.find('option').filter(function(){return $(this).val() > 0}).remove().end();
			
			if (((stateId != null && !isNaN(stateId) && Number(stateId) > 0) ||
					(districtId != null && !isNaN(districtId) && Number(districtId) > 0) ||
					(schoolId != null && !isNaN(schoolId) && Number(schoolId) > 0)) &&
					(assessmentProgramId != null && !isNaN(assessmentProgramId) && Number(assessmentProgramId) > 0) &&
					(testingProgramId != null && !isNaN(testingProgramId) && Number(testingProgramId) > 0)) {
	            $.ajax({
	                url: 'getContentAreas.htm',
	                data: {
	                	assessmentProgramId: assessmentProgramId,
	                	testingProgramId: testingProgramId
	                },
	                type: "GET",
	                success: function(data) {
	                	if (data !== undefined && data !== null && data.length > 0) {
	                		for (var i = 0, length = data.length; i < length; i++) {
	                			contentArea.append($('<option>', { value: data[i].id, text : data[i].name }));
	    					}
	                		if (data.length != 1) {
	        					contentArea.prop('disabled', false);
	        				} 
	                	} else {
	                		$('body, html').animate({scrollTop:0}, 'slow');
	                		$('.messages').html(me.options.newreport_no_contentarea).show();
	                	}
	                },
	                error: function() {
	                	$('body, html').animate({scrollTop:0}, 'slow');
	            		$('.messages').html(me.options.unknown_error).show();
		            }
	            });
	        }
		}
		
		
		
	},
	'#contentArea change' : function(el, ev) {
		ev.preventDefault();
		var me = this, contentAreaId = $('#contentArea').val(), grade = $('#grade'), reportCategory = $('#reportCategory').val();
		this._resetSelection("contentArea");
		$('.messages').html('').hide();
		grade.find('option').filter(function(){return $(this).val() > 0}).remove().end();
		$("#contentAreaName").val($("#contentArea option:selected").text());
		if (contentAreaId != null && !isNaN(contentAreaId) && Number(contentAreaId) > 0) {
			if(reportCategory == "ROSTER") {
				
			} else {
				$.ajax({
	                url: 'getGradeCoursesByContentArea.htm',
	                data: { contentAreaId: contentAreaId },
	                type: "GET",
	                success: function(data) {
	                	if (data !== undefined && data !== null && data.length > 0) {
	                		for (var i = 0, length = data.length; i < length; i++) {
	                			grade.append($('<option>', { value: data[i].id, text : data[i].name }));
	    					}
	                		if (data.length != 1) {
	                			grade.prop('disabled', false);
	        				}
	                	} else {
	                		$('body, html').animate({scrollTop:0}, 'slow');
	                		$('.messages').html(me.options.newreport_no_grade).show();
	                	}
	                },
	                error: function() {
	                	$('body, html').animate({scrollTop:0}, 'slow');
	            		$('.messages').html(me.options.unknown_error).show();
		            }
	            });
			}
            
        }
	},
	'#student change' : function(el, ev) {
		ev.preventDefault();
		var selectedStudent = $("#student option:selected");
		$("#studentName").val(selectedStudent.text());
		$("#gradeName").val(selectedStudent.data('grade'));
	},
	_resetSelection: function(changeType) {
		var assessmentProgramSelect = $('#assessmentProgram')
		var reportCategory = $('#reportCategory');
		var testingProgram = $('#testingProgram');
		var state = $('#state');
		var district = $('#district');
		var school = $('#school');
		var roster = $('#roster');
		var contentArea = $('#contentArea');
		var grade = $('#grade');
		var year = $('#year');
		var student = $("#student");
		grade.children('option:not(:first)').remove();
		year.children('option:first-child').prop('selected', 'selected');
		
		if(changeType == "assessmentProgram") {
			reportCategory.children('option:not(:first)').remove();
			testingProgram.children('option:not(:first)').remove();
			state.children('option:not(:first)').remove();
			district.children('option:not(:first)').remove();
			school.children('option:not(:first)').remove();
			/*if(roster.children('option').length > 0) {
				roster.children('option').remove();
				roster.select2("destroy");
			}*/
			if($("#studentDiv").find("div.bcg_select").length > 0) {
				student.children('option').remove();
				student.append($('<option></option>').val('').html('Select'));
				student.select2("destroy");
				$("#studentDiv").find("div.bcg_select").remove();
			}
			contentArea.children('option:not(:first)').remove();
			grade.children('option:not(:first)').remove();
			year.children('option:not(:first)').remove();
			student.children('option:not(:first)').remove();
			reportCategory.prop('disabled', false);
			testingProgram.prop('disabled', false);
			state.prop('disabled', false);
			district.prop('disabled', false);
			school.prop('disabled', false);
			roster.prop('disabled', false);
			student.prop('disabled', false);
		} else if(changeType == "reportCategory") {			
			testingProgram.children('option:not(:first)').remove();
			state.children('option:not(:first)').remove();
			district.children('option:not(:first)').remove();
			school.children('option:not(:first)').remove();
			/*if(roster.children('option').length > 0) {
				roster.children('option').remove();
				roster.select2("destroy");
			}*/
			if($("#studentDiv").find("div.bcg_select").length > 0) {
				student.children('option').remove();
				student.append($('<option></option>').val('').html('Select'));
				student.select2("destroy");
				$("#studentDiv").find("div.bcg_select").remove();
			}
			contentArea.children('option:not(:first)').remove();
			grade.children('option:not(:first)').remove();
			year.children('option:not(:first)').remove();
			student.children('option:not(:first)').remove();
			testingProgram.prop('disabled', false);
			state.prop('disabled', false);
			district.prop('disabled', false);
			school.prop('disabled', false);
			roster.prop('disabled', false);
			student.prop('disabled', false);
		} else if(changeType == "testingProgram") {			
			state.children('option:not(:first)').remove();
			district.children('option:not(:first)').remove();
			school.children('option:not(:first)').remove();
			/*if(roster.children('option').length > 0) {
				roster.children('option').remove();
				roster.select2("destroy");
			}*/
			if($("#studentDiv").find("div.bcg_select").length > 0) {
				student.children('option').remove();
				student.append($('<option></option>').val('').html('Select'));
				student.select2("destroy");
				$("#studentDiv").find("div.bcg_select").remove();
			}
			contentArea.children('option:not(:first)').remove();
			grade.children('option:not(:first)').remove();
			year.children('option:not(:first)').remove();
			student.children('option:not(:first)').remove();
			roster.children('option:not(:first)').remove();	
			state.prop('disabled', false);
			district.prop('disabled', false);
			school.prop('disabled', false);
			roster.prop('disabled', false);
			student.prop('disabled', false);
		} else if(changeType == "contentArea") {	
			district.children('option:not(:first)').remove();
			school.children('option:not(:first)').remove();
			roster.children('option:not(:first)').remove();		
			if($("#studentDiv").find("div.bcg_select").length > 0) {
				student.children('option').remove();
				student.append($('<option></option>').val('').html('Select'));
				student.select2("destroy");
				$("#studentDiv").find("div.bcg_select").remove();
			}
			state.children('option:first').prop('selected', 'selected');
		} else if(changeType == "STATE") {
			district.children('option:not(:first)').remove();
			school.children('option:not(:first)').remove();
			/*if(roster.children('option').length > 0) {
				roster.children('option').remove();
				roster.select2("destroy");
			}*/
			if($("#studentDiv").find("div.bcg_select").length > 0) {
				student.children('option').remove();
				student.append($('<option></option>').val('').html('Select'));
				student.select2("destroy");
				$("#studentDiv").find("div.bcg_select").remove();
			}
			year.children('option:not(:first)').remove();
			student.children('option:not(:first)').remove();
			district.prop('disabled', false);
			school.prop('disabled', false);
			roster.prop('disabled', false);
			student.prop('disabled', false);
		} else if(changeType == "DISTRICT") {
			school.children('option:not(:first)').remove();
			/*if(roster.children('option').length > 0) {
				roster.children('option').remove();
				roster.select2("destroy");
			}*/
			if($("#studentDiv").find("div.bcg_select").length > 0) {
				student.children('option').remove();
				student.append($('<option></option>').val('').html('Select'));
				student.select2("destroy");
				$("#studentDiv").find("div.bcg_select").remove();
			}
 			student.children('option:not(:first)').remove();
			school.prop('disabled', false);
			roster.prop('disabled', false);
		} else if(changeType == "SCHOOL") {
			/*if(roster.children('option').length > 0) {
				roster.children('option').remove();
				roster.select2("destroy");
			}*/
			if($("#studentDiv").find("div.bcg_select").length > 0) {
				student.children('option').remove();
				student.append($('<option></option>').val('').html('Select'));
				student.select2("destroy");
				$("#studentDiv").find("div.bcg_select").remove();
			}
			student.children('option:not(:first)').remove();

			roster.prop('disabled', false);
		} else {
			$('.messages').html('').hide();
 			reportCategory.children('option:not(:first)').remove();
			testingProgram.children('option:not(:first)').remove();
			state.children('option:not(:first)').remove();
			district.children('option:not(:first)').remove();
			school.children('option:not(:first)').remove();
			/*if(roster.children('option').length > 0) {
				roster.children('option').remove();
				roster.select2("destroy");
			}*/	
			if($("#studentDiv").find("div.bcg_select").length > 0) {
				student.children('option').remove();
				student.append($('<option></option>').val('').html('Select'));
				student.select2("destroy");
				$("#studentDiv").find("div.bcg_select").remove();
			}
			contentArea.children('option:not(:first)').remove();
			grade.children('option:not(:first)').remove();
			year.children('option:not(:first)').remove();
			student.children('option:not(:first)').remove();
			 
			reportCategory.prop('disabled', false);
			testingProgram.prop('disabled', false);
			state.prop('disabled', false);
			district.prop('disabled', false);
			school.prop('disabled', false);
			roster.prop('disabled', false);
			student.prop('disabled', false);
			
			$('#districtDiv').hide();
			$('#schoolDiv').hide();
			$('#rosterDiv').hide();
		}
	},
	'#roster_unused change' : function(el, ev) {
		ev.preventDefault();
		var me = this,rosterId = $('#roster').val(), testSessionSelect = $('#testSession');
		
		$('.messages').html('').hide();
		testSessionSelect.html('');
		
		if (rosterId != null && !isNaN(rosterId) && Number(rosterId) > 0) {
            $.ajax({
                url: 'getTestSessionsForRoster.htm',
                data: {
                    rosterId: rosterId
                },
                type: "GET",
                success: function(data) {
                	if (data !== undefined && data !== null && data.length > 0) {
                		for (var i = 0, length = data.length; i < length; i++) {
                			testSessionSelect.append($('<option>', { 
    					        value: data[i].id,
    					        text : data[i].name 
    					    }));
    					}
                		
                	} else {
                		$('body, html').animate({scrollTop:0}, 'slow');
                		$('.messages').html(me.options.no_ended_sessions).show();
                	}
                },
                error: function() {
                	$('body, html').animate({scrollTop:0}, 'slow');
            		$('.messages').html(me.options.unknown_error).show();
	            }
            });
        }
		testSessionSelect.sortOptions().prepend("<option value='0' selected='selected'>Select</option>");
	},
	'#btnReset click': function(el, ev) {
		ev.preventDefault();
		this.element.find('form')[0].reset();
		this._resetSelection("All");
		//var rosterSelect = $('#roster');
		//rosterSelect.val([]);  //Hack: for some reason a value is selected, unselect all options
		//rosterSelect.multiSelect('refresh');
		el.trigger('selected');
	},
	'#viewReport click': function(el, ev) {
		ev.preventDefault();
		$('.messages').html('').hide();
 		var form = this.element.find('form'),
			values = can.deparam(form.serialize());
 		//Validation
 		if(values.reportCategory == "PARENT_REPORT"){
 			$("#reportFilterForm").validate({
 					ignore: "",
 					rules: {
 						contentArea: {required: true},
 						assessmentProgram : {required: true},
 						reportCategory : {required: true},
 						testingProgram : {required: true}
 					}
 			});
 			if($('#reportFilterForm').valid()){  
 	 			if($('#grade').val() != 0 && $('#year').val() != 0) {
 	 				Report.findOne(values, function(report) {
 	 					el.trigger('selected', report );
 	 				});
 	 			} else {
 	 				Report.findOne(values, function(report) {
 	 					el.trigger('selected', report );
 	 				});
 	 				
 	 			}
 	 		}
 		} else if (values.reportCategory == 'STUDENT'){
 			$("#reportFilterForm").validate({
					ignore: "",
					rules: {
						contentArea: {required: true},
						assessmentProgram : {required: true},
						reportCategory : {required: true},
						testingProgram : {required: true},
						student : {required: true},
						school :  {required: true},
						district : {required: true},
						state : {required: true},
					}
			});
 			if($('#reportFilterForm').valid()){  
 				Report.findOne(values, function(report) {
	 					el.trigger('selected', report );
	 			});
 	 		}
 		} else if (values.reportCategory == 'ROSTER'){
 			$("#reportFilterForm").validate({
				ignore: "",
				rules: {
					assessmentProgram : {required: true},
					reportCategory : {required: true},
					testingProgram : {required: true},
					contentArea: {required: true},
					state : {required: true},
					district : {required: true},
					school :  {required: true},
					roster :  {required: true},
					student : {required: true},
				}
 			});
			if($('#reportFilterForm').valid()){
				var checkTags = $("#student").select2("val");
				var studentNames = [];
				$(checkTags).each(function( i, value ) {
					studentNames.push($(value).prop("title"));
				 });
				values.studentNames = studentNames;
				values.rosterName = $("#roster option:selected").text();
				values.contentAreaId = $("#contentArea option:selected").val();
				values.students = $("#student").select2("val").map(function(){return this.value;}).get();
				if(values.students != null && values.students.length > 0){
					$("#studentDiv #requiredmessage").hide();
					Report.findOne(values, function(report) {
	 					el.trigger('selected', report );
					});					
				} else {
					$("#studentDiv #requiredmessage").show();
				}
	 		}
		} else if (values.reportCategory == ''){
 			$('body, html').animate({scrollTop:0}, 'slow');
			$('.messages').html("Please select report category").show();
 		}
 		
	}	
});