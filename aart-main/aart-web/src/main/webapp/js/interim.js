var sub, alignment, gradenum, subjectArray, alignArray, gradeArray, testArray, interimTestsArray, dropdownObjectArray, studentsArray, reportsArray, schoolsArray;
var gradeID, subjectID;
var testSessionIdInterim;
var interimTest = {};
var subjectsArray, orgainzationsArray, gradesArray, studentArray, districtsArray;
interimTest.testIds = [];
var attmptedTestArray = [];
var reportsArray = [];
var predictiveStudentScore;
var reportName = "";
var assignedStudents = [];

jQuery(document).ready(function() {
	
            var unparseConfig = {
                'comments': "",
                'delimiter': ',',
                'download': false,
                'dynamicTyping': false
            };

            // Alias $().dndScroll() to $().dndPageScroll()
            (function( $ ){
            	   $.fn.dndScroll = function() {
            	      $(this).dndPageScroll();
            	      return this;
            	   }; 
            	})( jQuery );
            
            $.ajax({
                url: 'getGroups.htm',
                data: {
                    groupName: '',
                    userName: ''
                },
                dataType: 'json',
                type: "GET"
            }).done(function(subject) {
                groupsArray = subject;
            });

            $.ajax({
                url: 'getTestsForReports.htm',
                data: {},
                dataType: 'json',
                type: "GET" 
            }).done(function(array) {
                reportsArray = array;
            });

            $.ajax({
                url: 'getGroups.htm',
                data: {},
                dataType: 'json',
                type: "GET"
            }).done(function(subject) {
                groupsArray = subject;
            });
            
            $.ajax({
                url: 'getSchoolNames.htm',
                data: {},
                dataType: 'json',
                type: "GET"
            }).done(function(school) {

                    schoolsArray = school;
                    schoolsArray = schoolsArray.sort();
                });

            $('#interimTestGrade').on('change',function() {
                    gradeID = $('#interimTestGrade').val();
            });

            $('#interimTestSubject').on('change',function() {
                    subjectID = $('#interimTestSubject').val();
             });
         
            $.ajax({
                url: 'getMiniTests.htm',
                data: {
                    gradeCourseId: 0,
                    contentAreaId: 0,
                    isInterim: "false"
                },
                dataType: 'json',
                type: "GET"
            }).done(function(tests) {
                    testArray = tests;
                });

            $.ajax({
                url: 'getInterimTests.htm',
                data: {},
                dataType: 'json',
                type: "GET"
            }).done(function(interim) {
                    interimTestsArray = interim;
                });
            
        });


$('#createTest').on('click',function(ev) {
        ev.preventDefault();
        createInterimTest($(this).attr('data-interimid'));
    }); 
   
$(document).on('click','.text_blue',function(ev) {
   	        ev.preventDefault();
   	        previewTest($(this));	
});
	
$(document).on('click','.text_green',function(ev) {	
	ev.preventDefault();
	addTest($(this));
});

$(document).on('click','.text_pink',function(ev){
	 ev.preventDefault();
	 removeTest($(this));
 }); //text pink


$(document).on('click','.cancel_added_test',function(ev){    	                           	    	    	         
     ev.preventDefault();
     var myClass = $(this);
     $('#ptag a[data-id="' + myClass.data('id') + '"]').remove();
     var i = interimTest.testIds.indexOf(myClass.data('id'));
     interimTest.testIds.splice(i, 1);
     var element = $('.text_pink').closest('a[data-id="' + myClass.data('id') + '"]');
     element.find('i').removeClass('text_pink').removeClass('fa-minus-circle');
     element.find('i').addClass('text_green').addClass('fa-plus-circle');
     element.closest('div').removeClass('added-test');
     $('.assemble_box .col-sm-4').removeClass('disable-class');
     $('.assemble_box .col-sm-4 i.text_green').css({'color': '#80A444','zoom': '1.3'});
     $('.assemble_box .col-sm-4 i.text_blue').css('color', '#3097D1');
     if ($('#radio-class').val() == 'true') {
         if ($('#ptag a').length == 0) {
             $('#interimTestName').val('');
             $('#description').val('');
             $('#description').removeAttr('disabled');
             $('#interimTestName').removeAttr('disabled');
         }
     }
     // $('.text_pink')
 });


var contextPath = '/AART';
$("#assembleTab").on('click',function() {
    testArray1 = [];

    $('#interimTabs').html(new EJS({
        url: contextPath + '/js/views/assemble.ejs'
    }).render({
        subject: subjectArray,
        align: alignArray,
        grade: gradeArray,
        school: schoolsArray,
        tests: testArray1,
        currentAssessmentProgramName : currentAssessmentProgramName
    }));

    $("#interimTestGrade").hide();
    $("#interimTestSubject").hide();
    $("#interimTestalignment").hide();

    $('#radio-class').on('change',function(ev) {
            ev.preventDefault();
            $("#interimTestSubject").hide();
            $.ajax({
                    url: 'interimsub.htm',
                    data: {
                        isInterim: $("#radio-class").val()
                    },
                    dataType: 'json',
                    type: "GET"
                }).done(function(subject) {
                        var html = "";
                        html += "<option>Select</option>";
                        for (var i = 0; i < subject.length; i++) {

                            html += "<option value=" + subject[i].id + ">" + subject[i].name + "</option>";
                        }
                        $("#interimTestSubject").html(html);
                        $("#interimTestSubject").show();

                    });
            $("#interimTestGrade").hide();
            $("#interimTestalignment").hide();
            $('#searchButton').prop('disabled', true);
            $('#searchButton').addClass('ui-state-disabled');
            $('.assemble_box').html('<h3 style="margin-left:10px;">Select Search filters to get available tests.</h3>');
            $("#ptag").html('');
        });
    
    $('#radio-class').on('change', function(ev) {
        ev.preventDefault();
        if ($(this).val() == 'false') {
            $('.interim-select').addClass('hide');
        } else if ($(this).val() == 'true') {
            $('.interim-select').removeClass('hide');
        }
    });
 
    // this change event is for interim subject dropdown
    
    $('#interimTestSubject').on('change',function(ev) {
            ev.preventDefault();
            $("#interimTestGrade").hide();
            $.ajax({
                    url: 'interimgrade.htm',
                    data: {
                        contentAreaId: $("#interimTestSubject").val(),
                        isInterim: $("#radio-class").val(),
                    },
                    dataType: 'json',
                    type: "GET"
                }).done(function(grade) {
                    var html = "";
                    html += "<option>Select</option>";
                    for (var i = 0; i < grade.length; i++) {

                        html += "<option value=" + grade[i].id + ">" + grade[i].name + "</option>";
                    }
                    $("#interimTestGrade").html(html);
                    $("#interimTestGrade").show();

                });
            $("#interimTestalignment").hide();
            if ($("#interimTestSubject option:selected").text() == "Select") {
                $("#interimTestGrade").hide();
            }
            if (currentAssessmentProgramName == 'PLTW'){
            	 $('#searchButton').removeAttr('disabled');
                 $('#searchButton').removeClass('ui-state-disabled');
           } /*else if ((currentAssessmentProgramName == 'PLTW') && ($("#interimTestSubject option:selected").text() == "Select")){
				$('#searchButton').prop('disabled', true);
				$('#searchButton').addClass('ui-state-disabled');
           } */else {
				$('#searchButton').prop('disabled', true);
				$('#searchButton').addClass('ui-state-disabled');
           }
            $('.assemble_box').html('<h3 style="margin-left:10px;">Select Search filters to get available tests.</h3>');
            $("#ptag").html('');
        });
    
    $('#interimTestGrade').on('change',function(ev) {
    	
    	        ev.preventDefault();
    	        if ($("#interimTestGrade option:selected").text() == "Select") {
    	        	$('#searchButton').addClass('ui-state-disabled');
    	        }else{
    	        	$('#searchButton').removeAttr('disabled');
    	            $('#searchButton').removeClass('ui-state-disabled');
    	        }
    	        $("#interimTestalignment").show();

    	    });
    
    $('#searchButton').on('click',function(ev) {
    	
    	        ev.preventDefault();
    	        if ($('#radio-class').val() == "true") {
    	            var isInterim = "TRUE";
    	            $('#ptag').prev('h4').html('Selected Test:');
    	            $('#interimTestName').prop('disabled', true);
    	            $('#description').prop('disabled', true);
    	        } else if ($('#radio-class').val() == "false") {
    	            var isInterim = "FALSE";
    	            $('#ptag').prev('h4').html('Selected Tests:');
    	            $('#interimTestName').prop('disabled', false);
    	            $('#description').prop('disabled', false);
    	            $('#interimTestName').val('');
    	            $('#description').val('');
    	        }

    	        $('#ptag').html('');
    	        interimTest.testIds = [];

    	        var gradeCourseId = $('#interimTestGrade').val();
    	        var contentAreaId = $('#interimTestSubject').val();
    	        var contentCode = $('#interimTestalignment').val();
    	        $('#createTest').attr('data-gradecourseid',
    	            $('#interimTestGrade').val());
    	        $('#createTest').attr('data-contentareaid',
    	            $('#interimTestSubject').val());

    	        $.ajax({
    	            url: 'getMiniTests.htm',
    	            data: {
    	                gradeCourseId: gradeCourseId,
    	                contentAreaId: contentAreaId,
    	                contentCode: contentCode,
    	                isInterim: $('#radio-class').val(),
    	                createdBy: $('#searchbycreater').val(),
    	                schoolName: $('#interimTestSchool').val(),
    	                testName: $('#searchbytestname').val(),
    	                alignmentId: $('#interimTestalignment').val(),

    	            },
    	            dataType: 'json',
    	            type: "GET" 
    	        }).done(function(tests) {
    	                testArray = tests;
    	                $('.assemble_box').html('');
    	                $('.assemble_box').append('<div class="row"></div>');
    	                var idText="";
    	                var testsToHideFeedback = [];
    	                for (var i = 0; i < tests.length; i++) 
    	                {

    	                	   	if(!tests[i].feedbackAllowed)
    	                	   	{              	   		
    	                	   		testsToHideFeedback.push('#'+tests[i].id);               		                   		               		   
    	                	   	}                      		
    	                	   
    	                    if (tests[i].testDescription && tests[i].testDescription.length > 30) {
    	                        tests[i].shortDec = tests[i].testDescription.substring(0, 29);                     
    	                    }
    	                }

    	                $('.assemble_box .row').html(new EJS({

    	                    url: contextPath + '/js/views/interimtestslist.ejs'

    	                }).render({
    	                    tests: tests    	                   
    	                }));
    	                for(var i=0;i<testsToHideFeedback.length;i++)
    	                {
    	                	$(testsToHideFeedback[i]).css({ "display": "none"});              	
    	                }
    	                
    	    	   	    	   	 	                
    	            });


    	    });
    
    $('#createTest').on('click',function(ev) {
            ev.preventDefault();
            createInterimTest($(this).attr('data-interimid'));
        }); 
});


function addTest(thisObj)
{
    var myClass = thisObj;
    if (myClass.hasClass("text_green")) {
        if ($('#radio-class').val() == "true") {
            $('#ptag').html('');
            $(".js-shift-sortable").sortable({zIndex: 100000});
            $(".js-shift-sortable").disableSelection();
            $('#interimTestName').val(myClass.closest('a').data('name'));
            $("#description").val(myClass.closest('a').data('description'));
            $('#interimTestName').prop('disabled', false);
            $('#description').prop('disabled', false);
            $('#ptag').append('<a data-id="' + myClass.closest('a')
                    .data('id') + '" class="btn btn-default btn-sm cancel_added_test ui-state-default">' + '<i class="fa fa-times pull-right"></i>' + myClass
                    .closest('a')
                    .data('name') + '</span>');
            $('#createTest').prop('disabled', false);
            myClass.removeClass("fa-plus-circle").removeClass('text_green');
            myClass.addClass("text_pink");
            myClass.addClass("fa-minus-circle");
            myClass.closest('div').addClass('added-test');
            $('.assemble_box .col-sm-4').addClass('disable-class');
            $('.assemble_box .col-sm-4 a i').css('color', '#bbb');
            myClass.closest('div.col-sm-4').removeClass('disable-class');
            myClass.closest('div').find('a i').removeAttr('style');
            myClass.closest('div').find('a i.text_pink');
            myClass.closest('div').find('a i.text_blue');
            interimTest.testIds = [];
            interimTest.testIds.push(myClass.closest('a').data('id'));

        } else {
            var name = myClass.closest('a').data('name');
            var description = myClass.closest('a').data('description');
            $('#interimTestName').prop('disabled', false);
            $('#description').prop('disabled', false);

            $(".js-shift-sortable").sortable({zIndex: 100000});
            $(".js-shift-sortable").disableSelection();
            $('#ptag').append('<a data-id="' + myClass.closest('a')
                    .data('id') + '" class="btn btn-default btn-sm cancel_added_test ui-state-default">' + '<i class="fa fa-times"></i>' + myClass
                    .closest('a')
                    .data('name') + '</span>');
            myClass.removeClass("fa-plus-circle").removeClass('text_green');
            myClass.closest('div').find('a i').removeAttr('style');
            myClass.addClass("text_pink");
            myClass.addClass("fa-minus-circle");
            myClass.closest('div').addClass('added-test');
            $('#createTest').prop('disabled', false);
            interimTest.testIds.push(myClass.closest('a').data('id'));
            
        }
    } else {

    }

}


function previewTest(thisObj)
{
       if (thisObj.hasClass("text_blue")) {
	            var testId = thisObj.closest('a').data('id');
	            var testCollectionId = thisObj.closest('a').data('testcollectionid');
	            if(!testCollectionId){
	            	testCollectionId = thisObj.closest('a').data('collectionid');
	            }
	            var previewLink = thisObj;
	            $('#previewQCTestDiv').dialog({
	                autoOpen: false,
	                modal: true,
	                width: 1080,
	                height: 655,
	                title: '',
	                close: function(ev, ui) {
	                	thisObj.html('');
	                }
	            }).data('testid', testId)
	            .load('previewTest.htm?&selectedTestCollectionId=' + testCollectionId + '&selectedTestId=' + testId,
	                function() {
	                	reRenderMathJax();
	                    scrollPreviewTop();
	                    if (previewLink.hasClass("feedbackAllowed")) {
	    	            	getFeedbackQuestions(testId);
	    	            }
	                    $('.ui-dialog').removeAttr("role");
	                }).dialog('open');
		           
	            $('.ui-widget-header .ui-dialog-title#ui-id-2').html(thisObj.closest('a').data('name'));

	            $('#previewQCTestDiv').parent('div').css('border','4px solid #d8d8d8');
	        } else {

	        }
}
function removeTest(thisObj)
{
          var myClass = thisObj;
          if (myClass.hasClass("text_pink")) {
         	
              $('#ptag a[data-id="' + myClass.closest('a').data('id') + '"]').remove();
              var i = interimTest.testIds.indexOf(myClass.closest('a').data('id'));
              interimTest.testIds.splice(i, 1);
              myClass.removeClass("text_pink");
              myClass.removeClass("fa-minus-circle");
              myClass.addClass("fa-plus-circle").addClass('text_green');
              myClass.closest('div').removeClass('added-test');
              $('.assemble_box .col-sm-4').removeClass('disable-class');
              $('.assemble_box .col-sm-4 i.text_green').css({'color': '#80A444','zoom': '1.3'});
              $('.assemble_box .col-sm-4 i.text_blue').css('color', '#3097D1');
              if ($('#radio-class').val() == 'true') {
                  if ($('#ptag a').length == 0) {
                      $('#interimTestName').val('');
                      $('#description').val('');
                      $('#description').removeAttr('disabled');
                      $('#interimTestName').removeAttr('disabled');
                  }
              }    	       	    	              

          } else {

          }
}

// removed purpose dropdown change event in the part of F560

$('.clearsearchclass').on('hover', function(ev) {
    $(this).css('cursor', 'pointer');
});



$("#viewResults").on('click',function() {

    /*
     * vkrishna_sta: Adding code to see if the user has permission to view
     * predictive student scores
     */

    var groupRoleMap = {};
    var groupsNameMap = {};

    $('#interimTabs').html(new EJS({
        url: contextPath + '/js/views/viewresults.ejs'
    }).render({
        tests: reportsArray,
        districtsArray: {
            organizations: []
        },
        subjectsArray: {
            subjects: []
        },
        orgainzationsArray: {
            organizations: []
        },
        gradesArray: {
            grades: []
        },
        studentArray: {
            students: []
        },
        currentAssessmentProgramName : currentAssessmentProgramName
    }));
    $('#dynamicStudentReports').append($('<option>', {
        value: '',
        text: 'Please Select a Report from Dropdown',
        disabled: 'disabled',
        selected: 'selected'
    }));
    $('#dynamicStudentReports').append($('<option>', {
        value: 'student_activity',
        text: 'Student Activity Report'
    }));
    if (hasViewPredictiveStudentScore) {

        $('#dynamicStudentReports').append($('<option>', {
            value: 'predictive_score',
            text: 'Predictive Student Score'
        }));
    }

    jqGridresultsConstruction();
});

function jqGridresultsConstruction() {
    $
        .ajax({
            url: "getTestsForReports.htm",
            data: {},
            dataType: 'json',
            type: "GET"
        }).done(function(students) {
            for (var i in students) {
                if (students[i].testDescription && students[i].testDescription.length > 30) {
                    students[i].dsc = students[i].testDescription
                        .substring(0, 20) + '...<a class="js-moredec" title="Test Description" data-dec="' + students[i].testDescription + '">More</a>'
                } else {
                    students[i].dsc = students[i].testDescription
                }
            }

            $("#resultstable")
                .jqGrid({

                    datatype: "local",
                    data: students,
                    cmTemplate: {
                        title: false
                    },
                    colNames: ['ID', 'TEST NAME',
                        'TEST DESCRIPTION',
                        'STUDENTS ASSIGNED',
                        'STUDENTS ATTEMPTED',
                        'GENERATE REPORTS', 'CREATED BY', 'DATE CREATED'
                    ],
                    colModel: [{
                        label: 'testSessionId',
                        name: 'testSessionId',
                        label : 'InterimTestSessionId',
                        key: true,
                        hidden: true,
                        title : 'Test SessionId',
                        searchoptions : {title:"ID"}
                    }, {
                        name: 'testName',
                        index: 'testName',
                        label : 'InterimTestName',
                        align: 'left',
                        classes: 'grid-col',
                        title : 'Test Name',
                        searchoptions : {title:"Test Name"}

                    }, {
                        name: 'dsc',
                        index: 'dsc',
                        label : 'InterimDsc',
                        editable: false,
                        align: 'left',
                        classes: 'grid-col',
                        title : 'Description',
                        formatter: actionFormatter,
                        searchoptions : {title:"Test Description"}
                    }, {
                        name: 'studentsAssigned',
                        index: 'studentsAssigned',
                        label : 'InterimStudentsAssigned',
                        align: 'center',
                        sorttype: 'number',
                        editable: false,
                        title : 'Students Assigned',
                        searchoptions : {title:"Students Assigned"}
                    }, {
                        name: 'studentsAttempted',
                        index: 'studentsAttempted',
                        label : 'InterimStudentsAttempted',
                        align: 'center',
                        sorttype: 'number',
                        editable: false,
                        title : 'Students Attempted',
                        searchoptions : {title:"Students Attempted"}
                    }, {
                        name: 'act',
                        index: 'act',
                        label : 'InterimAct',
                        width: 350,
                        cmTemplate: {
                            title: false
                        },
                        sortable: false,
                        align: 'center',
                        editable: false,
                        search: false,
                        formatter: actionFormatter,
                        searchoptions : {title:"General Reports"}
                    }, {
                        name: 'assembledBy',
                        index: 'assembledBy',
                        label : 'InterimAssembledBy',
                        editable: false,
                        align: 'center',
                        title : 'Assembled By',
                        searchoptions : {title:"Created By"}
                    }, {
                        name: 'createdDateString',
                        index: 'createdDateString',
                        label : 'InterimCreatedDateString',
                        editable: false,
                        align: 'center',
                        title : 'Created Date',
                        searchoptions : {title:"Date Created"}
                    }],
                    rowNum: 10,
                    cmTemplate: {
                        title: false
                    },
                    rowList: [10, 20, 30],
                    width: 1024,
                    pager: '#pager',
                    gridview: true,
                    ignoreCase: true,
                    rownumbers: false,
                    sortname: 'testName',
                    viewrecords: true,
                    sortorder: 'asc',
                    loadonce: true,
                    jsonReader: {
                        repeatitems: false,
                    },
                    height: 300,
                    rowheight: 300,
                    shrinkToFit: false,
                    scrollOffset: 0,
                    altRows: true,
                    hoverrows: true,
                    altClass: 'altrow',
                    editurl: "getTotalTests.htm",
                    gridComplete: function() {
                    	debugger;
                        var ids = jQuery("#resultstable").getDataIDs();
                        $("#gbox_resultstable").css("width", "");
                        for (var i = 0; i < ids.length; i++) {
                            var cl = ids[i];
                            var obj = _.findWhere(students, {testSessionId: parseInt(cl)});
                            var testTestId = obj.testSessionId;
                            var testName = obj.testName;
                            var te = "";
                            if (obj.predictiveAutoAssigned) { // For
                                // predictive
                                // reports
                                if (obj.generatedReportCount > 0) {
                                    te = '<a href = "getInterimPredictiveReportBytestsessionId.htm?testSessionId=' + testTestId + '&testName=' + testName + '" class="notcompleted" id="predStudentReports">Student Report</a>';
                                }
                                if(obj.windowComplete && interimQuestionCSVPermission){
                                	te = te + '  |  ';
                                	te = te + '<a href = "getInterimPredictiveQuestionCSVBytestsessionId.htm?testSessionId=' + testTestId + '" class="notcompleted" id="predQuestionCSV">Question CSV</a>';
                                }
                                if (obj.schoolReportCount > 0 && viewInterimSchoolReportPermission) {
                                	if (obj.generatedReportCount > 0) te = te + '  |  ';
                                    te = te + '<a href = "getInterimSummaryPredictiveReportBytestsessionId.htm?testSessionId=' + testTestId + '&orgTypeCode=SCH" id="predSchoolReports" >School Report</a>';
                                }
                                if (obj.districtReportCount > 0 && viewInterimDistrictReportPermission) {
                                	if (obj.generatedReportCount > 0 || obj.schoolReportCount > 0) te = te + '  |  ';
                                    te = te + '<a href = "getInterimSummaryPredictiveReportBytestsessionId.htm?testSessionId=' + testTestId + '&orgTypeCode=DT" id="predDistrictReports" >District Report</a>';
                                }
                                if (te != '') {
                                    jQuery("#resultstable").setRowData(ids[i], {act: te});
                                }
                            } else {
                                var cln = $('tr[id="' + cl + '"]').find('td:first').html();
                                var i1 = reportsArray.indexOf(cl);
                                reportsArray.splice(i1, 1);
                                if (reportsArray.isScoringComplete) {
                                    se = '<a  class="complete" style="color:#83a444" tabindex=-1 title="Test Summary">Test Summary</a>';
                                    ce = '  |  <a  class="complete" style="color:#83a444" tabindex=-1 title="Item Report">Item Report</a>';
                                    te = '  |  <a  class="complete" style="color:#83a444" tabindex=-1 title="Student Report">Student Report</a>';
                                } else {
                                    se = '<a  class="notcompleted" id="testSummary" tabindex=-1 data-testid="' + testTestId + '" data-testname="' + testName + '" title="Test Summary">Test Summary</a>';
                                    ce = '  |  <a  class="notcompleted" id="itemReports" tabindex=-1 data-testid="' + testTestId + '" data-testname="' + testName + '" title="Item Report">Item Report</a>';
                                    te = '  |  <a  class="notcompleted" id="studentReports" tabindex=-1 data-testid="' + testTestId + '" data-testname="' + testName + '" title="Student Report">Student Report</a>';
                                }
                                jQuery("#resultstable").setRowData(ids[i], {act: se + ce + te});

                            }
                            jQuery("#resultstable").setRowData(ids[i],false, {height: 50 + (i * 2)});
                        }
                    }

                });
            $("#resultstable").jqGrid('filterToolbar', {
                stringResult: true,
                searchOnEnter: false,
                defaultSearch: "cn"
            });
            $('tr.ui-search-toolbar input').css('width', '100%');
            jQuery("#resultstable").jqGrid('navGrid', '#pager', {
                edit: false,
                add: false,
                del: true,
                search: true
            });
            $('#pager td#input_pager input').css("cssText","height: 22px !important;")
                
            		$('#resultstable').on('click','#testSummary',function(ev){
                			ev.preventDefault();
                			testSummary($(this))
                	});
                         		
            		$('#resultstable').on('click','#itemReports',function(ev) {
                	        ev.preventDefault();
                	        itemReports($(this))
                	});
                	         		
            		$('#resultstable').on('click','#studentReports',function(ev) {
                	        ev.preventDefault();
                	        studentReports($(this))
                	});
                	
                	$('.dynamicStudentReports').on('change',function(ev) {
                		
                		        ev.preventDefault();
                		        $("#studentActivityReportLabel").text("");
                		        if ($('#dynamicStudentReports').val() == 'predictive_score') {
                		            predictiveStudentScore = true;
                		            reportName = "Predictive Student Score(s)";
                		        } else if ($('#dynamicStudentReports').val() == 'student_activity') {
                		            predictiveStudentScore = false;
                		            reportName = "Student Activity Report";
                		        } else {
                		            return;
                		        }
                		        $('#studentActivityRep-view').dialog({
                		            autoOpen: false,
                		            modal: true,               		            
                		            width: 1080,
                		            height: 900,
                		            title: reportName,
                		            create: function(event, ui) {
                		                var widget = $(this).dialog("widget");
                		                $(".ui-dialog-titlebar-close span", widget).prop('id', 'editUserDialogClose');
                		                $("#studentActivityReportLabel").text("");
                		                $(".ui-button-icon-space").remove();

                		            },
                		            open: function(ev, ui) {
                		                organizationDropDownData();
                		                // disable the dropdown
                		                $('#schoolDropdown').prop('disabled', true);
                		                $("#subjectDropdown").prop('disabled', true);
                		                $("#gradeDropdown").prop('disabled', true);
                		                $("#studentDropdown").prop('disabled', true);
                		                $('#searchStudents').prop('disabled', true);
                		                // clear the dropdown values
                		                if ($("#schoolDropdown").select2()) {
                		                    $("#schoolDropdown").select2("val", "");
                		                }
                		                if ($("#districtDropdown").select2()) {
                		                  //  $("#districtDropdown").select2("val", "");
                		                }
                		                if ($("#subjectDropdown").select2()) {
                		                    $("#subjectDropdown").select2("val", "");
                		                }
                		                if ($("#gradeDropdown").select2()) {
                		                    $("#gradeDropdown").select2("val", "");
                		                }
                		                if ($("#studentDropdown").select2()) {
                		                    $("#studentDropdown").select2("val", "");
                		                }
                		                if ($("#districtDropdown").select2()) {
                		                    $("#districtDropdown").select2('data', null)
                		                    $("#districtDropdown").find('.select2-selection__rendered').empty()
                		                    .attr('title', 'Select Option');
                		            
                		                }
                		                $('#districtDropdown').empty();
                		                $('#schoolDropdown').empty();
                		                $('#subjectDropdown').empty();
                		                $('#gradeDropdown').empty();
                		                $('#studentDropdown').empty();

                		                // disable and uncheck checkboxes for
                		                // selectAll
                		                $("#checkboxSubject").prop('disabled', true);
                		                $("#checkboxGrade").prop('disabled', true);
                		                $('#checkboxStudent').prop('disabled', true);
                		                $('#checkboxSchool').prop('disabled', false);

                		                $('#checkboxDistrict').prop('checked', false);
                		                $('#checkboxSchool').prop('checked', false);
                		                $('#checkboxSubject').prop('checked', false);
                		                $('#checkboxGrade').prop('checked', false);
                		                $('#checkboxStudent').prop('checked', false);
                		                if(currentAssessmentProgramName == 'PLTW'){
                		               	 $("#gradeDiv").hide();
                		               }
                		                $('.form-control').removeAttr('tabindex');
                		            },
                		            close: function(ev, ui) {
                		            	
                		                $("#studentActivityGridDiv").html('<table id="studentActivityRepGrid">' +
                		                    '<tr><td /></tr>' +
                		                    '</table>' +
                		                    '<div id="studentActivityRepPager"></div>' +
                		                    '</div>');
                		                $('#dynamicStudentReports').val("");
                		                $('#districtDropdown').html("");

                		            }
                		        }).dialog('open');

                		    });
                	
                	$('#districtDropdown').on('change', function(ev) {
                	    ev.preventDefault();
                	    var dataArray = $('#districtDropdown').val();
                	    $("#subjectDropdown").prop('disabled', true);
                	    $("#subjectDropdown").select2("val", "");
                	    $("#checkboxSubject").prop('disabled', true);

                	    $('#checkboxSchool').prop('checked', false);
                	    dropDownsDisable('organization');
                	    if (dataArray != null) {
                	        populateSchoolDropdown(dataArray);
                	    }

                	});
                	
                	$('#schoolDropdown').on('change',function(ev) {
                		
                		        ev.preventDefault();
                		        var dataArray = $('#schoolDropdown').val();
                		        if (dataArray == null) {
                		            $("#subjectDropdown").prop('disabled', true);
                		            $("#subjectDropdown").select2("val", "");
                		            $("#checkboxSubject").prop('disabled', true);
                		            $('#checkboxSchool').prop('checked', false);
                		            dropDownsDisable('organization');
                		        }
                		        if (dataArray != null) {
                		            var d = new Object();
                		            d.predictiveStudentScore = predictiveStudentScore;
                		            d.organizationIds = dataArray;
                		            $.ajax({
                		                url: "getSubjectDropDownsData.htm",
                		                data: d,
                		                dataType: 'json',
                		                type: "GET"
                		            }).done(function(data) {
                		                subjectsArray = data;

                		                new EJS({
                		                    url: contextPath + '/js/views/viewresults.ejs'
                		                }).update('subjectDropdown', {
                		                    districtsArray: {
                		                        organizations: []
                		                    },
                		                    orgainzationsArray: {
                		                        organizations: []
                		                    },
                		                    subjectsArray: subjectsArray,
                		                    gradesArray: {
                		                        grades: []
                		                    },
                		                    studentArray: {
                		                        students: []
                		                    }
                		                });
                		                $("#subjectDropdown").select2({
                		                    data: subjectsArray.subjects.name,
                		                    placeholder: function() {
                		                        $(this).data('placeholder');
                		                    },
                		                    allowClear: true
                		                });
                		                $('.select2-search select2-search--inline').css('background-color', 'white !important');
                		                $("#subjectDropdown").removeAttr('disabled');
                		                $("#checkboxSubject").removeAttr('disabled');
                		                dropDownsDisable('organization');
                		                $("#subjectDropdown").select2();
                		                $("#checkboxSubject").on('click', function() {
                		                    if ($("#checkboxSubject").is(':checked')) {
                		                        $("#subjectDropdown > option").prop("selected", "selected");
                		                        $("#subjectDropdown").trigger("change");
                		                    } else {
                		                        $("#subjectDropdown > option").prop("selected", false);
                		                        $("#subjectDropdown").trigger("change");
                		                    }
                		                });

                		            });
                		        }

                		    });//school drop down change
                	
                	$('#subjectDropdown').on('change',function(ev) {
                		
                		        ev.preventDefault();
                		        var dataArray = $('#schoolDropdown').val();
                		        var subjectIds = $('#subjectDropdown').val();
                		        var gradeIds = $('#gradeDropdown').val();
                		        if ($('#schoolDropdown').val() == null || $('#schoolDropdown').val() == '') {
                		            var dataArray = [];
                		            var currentOrganizationId = $('#hiddenCurrentOrganizationId').val();
                		            dataArray.push(currentOrganizationId);
                		        }
                		        if (dataArray == null || subjectIds == null) {
                		            $("#gradeDropdown").prop('disabled', true);
                		            $("#gradeDropdown").select2("val", "");
                		            $("#checkboxGrade").prop('disabled', true);
                		            $('#checkboxSubject').prop('checked', false);
                		            dropDownsDisable('subject');
                		        }
                		        if (dataArray != null && subjectIds != null && currentAssessmentProgramName != 'PLTW') {
                		        	var d = new Object();
                		            d.predictiveStudentScore = predictiveStudentScore;
                		            d.organizationIds = dataArray;
                		            d.subjectIds = subjectIds;
                		            $.ajax({
                		                url: "getGradeDropDownsData.htm",
                		                data: d,
                		                dataType: 'json',
                		                type: "GET" 
                		            }).done(function(data) {
                		                    gradesArray = data;
                		                    new EJS({
                		                        url: contextPath + '/js/views/viewresults.ejs'
                		                    }).update('gradeDropdown', {
                		                        districtsArray: {
                		                            organizations: []
                		                        },
                		                        orgainzationsArray: {
                		                            organizations: []
                		                        },
                		                        subjectsArray: {
                		                            subjects: []
                		                        },
                		                        gradesArray: gradesArray,
                		                        studentArray: {
                		                            students: []
                		                        }
                		                    });

                		                    $("#gradeDropdown").select2({
                		                        data: gradesArray.grades.name,
                		                        placeholder: function() {
                		                            $(this).data('placeholder');
                		                        },
                		                        allowClear: true
                		                    });
                		                    $('.select2-search select2-search--inline').css('background-color', 'white !important');
                		                    $("#gradeDropdown").removeAttr('disabled');
                		                    $("#checkboxGrade").removeAttr('disabled');
                		                    dropDownsDisable('subject');
                		                    $("#gradeDropdown").select2();
                		                    $("#checkboxGrade").on('click' ,function() {
                		                        if ($("#checkboxGrade").is(':checked')) {
                		                            $("#gradeDropdown > option").prop("selected", "selected");
                		                            $("#gradeDropdown").trigger("change");
                		                        } else {
                		                            $("#gradeDropdown > option").prop("selected", false);
                		                            $("#gradeDropdown").trigger("change");
                		                        }
                		                    });

                		                });
                		        }else if(gradeIds == null && currentAssessmentProgramName == 'PLTW'){
                		            var dataArray = $('#schoolDropdown').val();
                		            var subjectIds = $('#subjectDropdown').val();
                		            if ($('#schoolDropdown').val() == null || $('#schoolDropdown').val() == '') {
                		                var dataArray = [];
                		                var currentOrganizationId = $('#hiddenCurrentOrganizationId').val();
                		                dataArray.push(currentOrganizationId);
                		            }
                		            if (dataArray == null || subjectIds == null) {
                		                $("#studentDropdown").prop('disabled', true);
                		                $('#searchStudents').prop('disabled', true);

                		                // clear the dropdown values
                		                $("#studentDropdown").select2("val", "");
                		                /* disable and uncheck checkboxes for selectAll */

                		                $('#checkboxStudent').prop('disabled', true);
                		                $('#checkboxGrade').prop('checked', false);
                		                $('#checkboxStudent').prop('checked', false);
                		            }
                		            var requestData = {};
                		            requestData.organizationIds = dataArray;
                		            requestData.subjectIds = subjectIds;
                		            requestData.gradeIds = gradeIds;
                		            requestData.predictiveStudentScore = predictiveStudentScore;
                		            requestStack.push(requestData);
                		            if (dataArray != null && subjectIds != null && !isStudentsQueryInprogress) {
                		                isStudentsQueryInprogress = true;
                		                var dataDetails = requestStack.pop();
                		                var isStackEmpty = false;
                		                while (!isStackEmpty) {
                		                    if (requestStack.pop()) {
                		                        isStackEmpty = false;
                		                    } else {
                		                        isStackEmpty = true;
                		                    }
                		                }
                		                $.ajax({
                		                    url: "getStudentDropDownsData.htm",
                		                    data: dataDetails,
                		                    dataType: 'json',
                		                    type: "GET"
                		                }).done(function(data) {
                		                        isStudentsQueryInprogress = false;
                		                        studentArray = data;

                		                        new EJS({
                		                            url: contextPath + '/js/views/viewresults.ejs'
                		                        }).update('studentDropdown', {
                		                            districtsArray: {
                		                                organizations: []
                		                            },
                		                            orgainzationsArray: {
                		                                organizations: []
                		                            },
                		                            subjectsArray: {
                		                                subjects: []
                		                            },
                		                            gradesArray: {
                		                                grades: []
                		                            },
                		                            studentArray: studentArray
                		                        });

                		                        $("#studentDropdown").select2({
                		                            data: studentArray.students.legalFirstName,
                		                            placeholder: function() {
                		                                $(this).data('placeholder');
                		                            },
                		                            allowClear: true
                		                        });
                		                        $('.select2-search select2-search--inline').css('background-color', '#ffffff !important');
                		                        $("#studentDropdown").removeAttr('disabled');
                		                        $("#checkboxStudent").removeAttr('disabled');
                		                        $('#checkboxStudent').prop('checked', false);
                		                        $("#studentDropdown").select2();
                		                        $("#checkboxStudent").on('click' , function() {
                		                            if ($("#checkboxStudent").is(':checked')) {
                		                                $("#studentDropdown > option").prop("selected", "selected");
                		                                $("#studentDropdown").trigger("change");
                		                            } else {
                		                                $("#studentDropdown > option").prop("selected", false);
                		                                $("#studentDropdown").trigger("change");
                		                            }
                		                        });
                		                        // This is the place you need to check for latest
                		                        // entries
                		                        // if any and trigger the call again ignoring
                		                        // previous entries
                		                        if (requestStack.length > 0) {
                		                            $('#studentDropdown').trigger('change');
                		                        }
                		                    });
                		            }
                		        }

                		    }); //subject drop down change
                	
                	$('#gradeDropdown').on('change', function(ev) {
                		
                		        ev.preventDefault();
                		        var dataArray = $('#schoolDropdown').val();
                		        var subjectIds = $('#subjectDropdown').val();
                		        var gradeIds = $('#gradeDropdown').val();
                		        if ($('#schoolDropdown').val() == null || $('#schoolDropdown').val() == '') {
                		            var dataArray = [];
                		            var currentOrganizationId = $('#hiddenCurrentOrganizationId').val();
                		            dataArray.push(currentOrganizationId);
                		        }
                		        if (dataArray == null || subjectIds == null || gradeIds == null) {
                		            $("#studentDropdown").prop('disabled', true);
                		            $('#searchStudents').prop('disabled', true);

                		            // clear the dropdown values
                		            $("#studentDropdown").select2("val", "");
                		            /* disable and uncheck checkboxes for selectAll */

                		            $('#checkboxStudent').prop('disabled', true);
                		            $('#checkboxGrade').prop('checked', false);
                		            $('#checkboxStudent').prop('checked', false);
                		        }
                		        var requestData = {};
                		        requestData.organizationIds = dataArray;
                		        requestData.subjectIds = subjectIds;
                		        requestData.gradeIds = gradeIds;
                		        requestData.predictiveStudentScore = predictiveStudentScore;
                		        requestStack.push(requestData);
                		        if (dataArray != null && subjectIds != null && gradeIds != null && !isStudentsQueryInprogress) {
                		            isStudentsQueryInprogress = true;
                		            var dataDetails = requestStack.pop();
                		            var isStackEmpty = false;
                		            while (!isStackEmpty) {
                		                if (requestStack.pop()) {
                		                    isStackEmpty = false;
                		                } else {
                		                    isStackEmpty = true;
                		                }
                		            }
                		            $.ajax({
                		                url: "getStudentDropDownsData.htm",
                		                data: dataDetails,
                		                dataType: 'json',
                		                type: "GET"
                		            }).done(function(data) {
                		                isStudentsQueryInprogress = false;
                		                studentArray = data;

                		                new EJS({
                		                    url: contextPath + '/js/views/viewresults.ejs'
                		                }).update('studentDropdown', {
                		                    districtsArray: {
                		                        organizations: []
                		                    },
                		                    orgainzationsArray: {
                		                        organizations: []
                		                    },
                		                    subjectsArray: {
                		                        subjects: []
                		                    },
                		                    gradesArray: {
                		                        grades: []
                		                    },
                		                    studentArray: studentArray
                		                });

                		                $("#studentDropdown").select2({
                		                    data: studentArray.students.legalFirstName,
                		                    placeholder: function() {
                		                        $(this).data('placeholder');
                		                    },
                		                    allowClear: true
                		                });
                		                $('.select2-search select2-search--inline').css('background-color', '#ffffff !important');
                		                $("#studentDropdown").removeAttr('disabled');
                		                $("#checkboxStudent").removeAttr('disabled');
                		                $('#checkboxStudent').prop('checked', false);
                		                $("#studentDropdown").select2();
                		                $("#checkboxStudent").on('click',function() {
                		                    if ($("#checkboxStudent").is(':checked')) {
                		                        $("#studentDropdown > option").prop("selected", "selected");
                		                        $("#studentDropdown").trigger("change");
                		                    } else {
                		                        $("#studentDropdown > option").prop("selected", false);
                		                        $("#studentDropdown").trigger("change");
                		                    }
                		                });
                		                // This is the place you need to check for latest
                		                // entries
                		                // if any and trigger the call again ignoring
                		                // previous entries
                		                if (requestStack.length > 0) {
                		                    $('#gradeDropdown').trigger('change');
                		                }
                		            });
                		        }

                		    }); //grade drop down change
                	
                	$('#studentDropdown').on('change',function(ev) {
                		        ev.preventDefault();
                		        $('#searchStudents').removeAttr('disabled');
                		    });

                		$('#searchStudents').on("click", function(ev) {
                			
                		    var studentIds = $('#studentDropdown').val();
                		    var subjectIds = $('#subjectDropdown').val();
                		    var gradeIds = $('#gradeDropdown').val();
                		    if (studentIds != null) {
                		        $("#colChooser").remove();
                		        $("#CSVexportStudentActRep").css('display', 'inline-block');
                		        $("#refreshStudentActReport").css('display', 'inline-block');
                		        $("#labelSAR").css('display', 'block');
                		        if (predictiveStudentScore) {
                		            if (loadTestingCycle == false) {
                		                $.ajax({
                		                    url: "getTestingCycles.htm",
                		                    async: false,
                		                    type: "GET" 
                		                }).done(function(data) {
                		                    testingCycles = data;
                		                    loadTestingCycle = true;
                		                });
                		            }
                		            getInterimPredictiveStudentScore(studentIds, subjectIds, gradeIds);
                		        } else {
                		            getStudentActivityReportGrid(studentIds);
                		        }
                		        // studentActivityReportLabel
                		        $("#studentActivityReportLabel").text(reportName + " generated on " + new Date().toLocaleString());
                		    }
                		}); //student drop down change
                		
                		$('#CSVexportStudentActRep').on('click',function(ev) {
                			
                			        setTimeout(activateLink, 3000);
                			        if (studentActivityLinkExpire) {
                			            studentActivityLinkExpire = false;
                			            var iCol, $grid = $("#studentActivityRepGrid"),
                			                colModel = $grid.jqGrid("getGridParam", "colModel");
                			            var nonHiddenColumns = colModel.filter(function(cm) {
                			                if (!cm.hidden && cm.name !== "cb" && cm.name !== "subgrid" && cm.name !== "rn" && cm.formatter !== "actions" && cm.name != undefined)
                			                    return cm;
                			            });
                			            var response = lastSelected.map(function(obj) {
                			                var rObj = {};
                			                
                			                for (var i=0; i <nonHiddenColumns.length; i++) {
                			                    rObj[nonHiddenColumns[i].label] = obj[nonHiddenColumns[i].name];

                			                }
                			                return rObj;
                			            });

                			            var csv = Papa.unparse(response);
                			            // Determine which approach to take for the download
                			            var blob = new Blob([csv], {
                			                type: "text/csv"
                			            });

                			            if (navigator.msSaveOrOpenBlob) {
                			                // Works for Internet Explorer and Microsoft Edge
                			                navigator.msSaveOrOpenBlob(blob, reportName + ".csv");

                			            } else {
                			                $("#CSVexportStudentActRep").attr('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(csv));
                			                $("#CSVexportStudentActRep").attr('download', reportName + '.csv');
                			            }
                			        } else {
                			            ev.preventDefault();
                			        }
                			    }); //to extract csv
                		
                		$('#refreshStudentActReport').on('click',function(ev) {

                			        var studentIds = $('#studentDropdown').val();
                			        var subjectIds = $('#subjectDropdown').val();
                			        var gradeIds = $('#gradeDropdown').val();
                			        var dataArray = $('#schoolDropdown').val();

                			        if ($('#schoolDropdown').val() == null || $('#schoolDropdown').val() == '') {
                			              var dataArray = [];
                			              var currentOrganizationId = $('#hiddenCurrentOrganizationId').val();
                			              dataArray.push(currentOrganizationId);
                			          }
                			        
                			        var postData = new Object();
                			        var url = "";
                			        if (predictiveStudentScore) {
                			            postData.studentIds = studentIds;
                			            postData.gradeCourseIds = gradeIds;
                			            postData.contentAreaIds = subjectIds;
                			            postData.orgIds= dataArray;
                			            url = "getInterimPredictiveStudentScores.htm";
                			        } else {
                			            postData.studentIds = studentIds;
                			            url = "getStudentReportActivityDetails.htm";
                			        }
                			        $.ajax({
                			            url: url,
                			            data: postData,
                			            dataType: 'json',
                			            type: "POST"
                			        }).done(function(students) {
                			                var updatedList = JSON.stringify(students, function(key, value) {
                			                    return (value === null) ? "-" : value
                			                });
                			                $("#studentActivityReportLabel").text(reportName + " generated on " + new Date().toLocaleString());
                			                jQuery('#studentActivityRepGrid').jqGrid('clearGridData');
                			                jQuery('#studentActivityRepGrid').jqGrid('setGridParam', {
                			                    data: students
                			                });
                			                jQuery('#studentActivityRepGrid').trigger('reloadGrid');
                			            });


                			    }); //refreshing grid

        });
}

function studentReports(thisObj){
	
    $('#chooseColumns').remove();
    var testSessionID = thisObj.data('testid');
    var testNameIs = thisObj.data('testname');
    $('#studentReports-view').dialog({
        autoOpen: false,
        modal: true,
        width: 1000,
        height: 800,
        title: "",
        create: function(event, ui) {
            var widget = $(this).dialog("widget");
            $(".ui-dialog-titlebar-close span", widget).prop('id', 'studentReportsDialogClose');
            $(".ui-button-icon-space").remove();
            $(".ui-dialog").removeAttr("role");
        },
        open: function(ev, ui) {

            byClassGrid(testSessionID, 'student', 'studentReports', testNameIs);
        },

        close: function(ev, ui) {

        }
    }).dialog('open');
}

function itemReports(thisObj){
	
    $('#chooseColumns').remove();
    var testSessionID = thisObj.data('testid');
    var testNameIs = thisObj.data('testname');

    $('#itemReports-view').dialog({
        autoOpen: false,
        modal: true,               	            
        width: 1080,
        height: 800,
        title: "",
        create: function(event, ui) {
            var widget = $(this).dialog("widget");
            $(".ui-dialog-titlebar-close span", widget).prop('id', 'itemSummaryDialogClose');
            $(".ui-button-icon-space").remove();
            $(".ui-dialog").removeAttr("role");
        },
        open: function(ev, ui) {
            byClassGrid(testSessionID, 'item', 'itemReports', testNameIs);
        },

        close: function(ev, ui) {
            // getReportByClassCSVByGrid.htm
        }
    }).dialog('open');
}

function testSummary(thisObj){
	
    $('#chooseColumns').remove();
    testSessionID = thisObj.data('testid');
    var testNameIs = thisObj.data('testname');

    $('#testSummary-view').dialog({
        autoOpen: false,
        modal: true,               	           
        width: 1080,
        height: 800,
        title: "",
        create: function(event, ui) {
            var widget = $(this).dialog("widget");
            $(".ui-dialog-titlebar-close span", widget).prop('id', 'testSummaryDialogClose');
            $(".ui-button-icon-space").remove();
            $(".ui-dialog").removeAttr("role");
        },
        open: function(ev, ui) {
            byClassGrid(testSessionID, 'class', 'testSummary', testNameIs);
        },

        close: function(ev, ui) {
            // getReportByClassCSVByGrid.htm
        }
    }).dialog('open');
}

var firstTime = true;
var tsId = 0;

function byClassGrid(testSessionID, type, id, testNameIs) {
	debugger;
    var url;
    var csvName;
    var gridId;
    var pagerId;
    tsId = testSessionID;
    if (type == 'class') {
        url = "getReportByClassCSVByGrid.htm";
        csvName = testNameIs + "_Test Summary Report_" + testSessionID;
        gridId = "#" + id + "Grid";
        pagerId = "#" + id + "Pager";
        $("#testSummaryLabel").html(testNameIs + ', Generated ' + new Date().toLocaleString());
        $("#refreshTestSummary").attr('data-testName', testNameIs);
    } else if (type == 'student') {
        url = "getReportByStudentCSVByGrid.htm";
        csvName = testNameIs + "_Student Report_" + testSessionID;
        gridId = "#" + id + "Grid";
        pagerId = "#" + id + "Pager";
        $("#studentReportLabel").html(testNameIs + ', Generated ' + new Date().toLocaleString());
        $("#refreshStudentReport").attr('data-testName', testNameIs);
    } else if (type == 'item') {
        url = "getReportByTaskCSVByGrid.htm";
        csvName = testNameIs + "_Item Report_" + testSessionID;
        gridId = "#" + id + "Grid";
        pagerId = "#" + id + "Pager";
        $("#itemReportLabel").html(testNameIs + ', Generated ' + new Date().toLocaleString());
        $("#refreshItemReport").attr('data-testName', testNameIs);
    } else {
        return;
    }
    if (!firstTime) {
        $(gridId).jqGrid('clearGridData');
        $(gridId).jqGrid("GridUnload");
        $(gridId).html('');

    } else {
        firstTime = false;
    }
    $.ajax({
        url: url,
        data: {
            testSessionId: testSessionID
        },
        dataType: 'json',
        type: "GET"
    }).done(function(data) {
    	debugger;
            var colName = data[0];
            var colModel = [];
            data.shift();
            for (var i in colName) {
                var colObj = {};

                colObj.label = colName[i];
                colObj.name = colName[i];
                if (colName[i].length > 1 && colName[i].length < 5) {
                    colObj.width = 50;
                } else if (colName[i].length >= 5 && colName[i].length < 12) {
                    colObj.width = 9 * colName[i].length;

                } else {
                    if (colName[i] == "Scoring Type") {
                        colObj.width = 115;
                    } else {
                        colObj.width = 77;
                    }
                }
                colObj.align = 'center';
                colObj.key = false;
                colObj.hidden = false;
                colObj.title = colName[i];
                colObj.searchoptions={title:colName[i]};
                colModel.push(colObj);
            }
            var jqData = [];
            for (var i in data) {
                var jqObj = new Object();

                for (var j in colName) {
                    if (data[i][j] != null) {
                        jqObj[colName[j]] = data[i][j].toString();
                    } else {
                        jqObj[colName[j]] = "-";
                    }

                }
                jqData[i] = jqObj;
            }
            $(gridId).jqGrid('clearGridData');
            $(gridId).jqGrid('setGridParam', {
                data: jqData
            });
            $(gridId).trigger('reloadGrid');


            $(gridId)
                .jqGrid({
                    datatype: "local",
                    data: jqData,
                    cmTemplate: {
                        title: false
                    },
                    colNames: colName,
                    colModel: colModel,
                    rowNum: 20,
                    cmTemplate: {
                        title: false
                    },
                    rowList: [10, 20, 30, 'All'],
                    width: 1800,
                    pager: pagerId,
                    gridview: true,
                    ignoreCase: true,
                    rownumbers: false,
                    sortname: 'invdate',
                    viewrecords: true,
                    sortorder: 'desc',
                    loadonce: true,
                    jsonReader: {
                        repeatitems: false,
                    },
                    height: 300,
                    rowheight: 300,
                    shrinkToFit: false,
                    autowidth: true,
                    scrollOffset: 0,
                    altRows: true,
                    hoverrows: true,
                    altClass: 'altrow',
                    editurl: url,
                    loadComplete: function() {
                        $(".ui-pg-selbox option[value='All']").val(100000);
                        this.p.lastSelected = lastSelected; // set this.p.lastSelected

                    },
                    gridComplete: function() {
                        $("#gbox_" + id + "Grid").css("width", "");
                    }
                });

            $(gridId).jqGrid('filterToolbar', {
                stringResult: true,
                searchOnEnter: false,
                defaultSearch: "cn"
            });

            $('tr.ui-search-toolbar input').css('width', '100%');
            jQuery(gridId).jqGrid('navGrid', pagerId, {
                edit: false,
                add: false,
                del: false,
                search: false,
                refresh: false
            });
            $(pagerId + 'td#input_' + pagerId + ' input').css("cssText",
                "height: 22px !important;");

            $(gridId).jqGrid('navButtonAdd', pagerId, {
                caption: "Columns",
                buttonicon: "ui-icon-calculator",
                title: "Choose columns",
                id: "chooseColumns",
                onClickButton: function() {

                    $(gridId).jqGrid('columnChooser', {
                        width: 550,
                        dialog_opts: {
                            modal: true,
                            minWidth: 470,
                            minHeight: 470,
                            dividerLocation: 0.5
                        },
                    });
                }
            });
        });

    var getJSONArrayForReports = function(data) {
        var colName = data[0];
        data.shift();
        var jqData = [];
        for (var i in data) {
            var jqObj = new Object();
            for (var j in colName) {
                if (data[i][j] != null) {
                    jqObj[colName[j]] = data[i][j].toString();
                } else {
                    jqObj[colName[j]] = "-";
                }
            }
            jqData[i] = jqObj;
        }
        return jqData;
    }


    $('#refreshTestSummary').on('click',
        function(ev) {
            ev.preventDefault();
            $.ajax({
                url: "getReportByClassCSVByGrid.htm",
                data: {
                    testSessionId: testSessionID
                },
                dataType: 'json',
                type: "GET"
            }).done(function(data) {
                    var json = getJSONArrayForReports(data);
                    var gridId = "#testSummaryGrid";
                    $("#testSummaryLabel").html(testNameIs + ', Generated ' + new Date().toLocaleString());
                    $(gridId).jqGrid('clearGridData');
                    $(gridId).jqGrid('setGridParam', {
                        data: json
                    });
                    $(gridId).trigger('reloadGrid');
                });
        });
    $('#refreshItemReport').on('click',
        function(ev) {
            ev.preventDefault();
            $.ajax({
                url: "getReportByTaskCSVByGrid.htm",
                data: {
                    testSessionId: testSessionID
                },
                dataType: 'json',
                type: "GET"
            }).done(function(data) {
                    var json = getJSONArrayForReports(data);
                    var gridId = "#itemReportsGrid";
                    $("#itemReportLabel").html(testNameIs + ', Generated ' + new Date().toLocaleString());
                    $(gridId).jqGrid('clearGridData');
                    $(gridId).jqGrid('setGridParam', {
                        data: json
                    });
                    $(gridId).trigger('reloadGrid');
                });
        });

    $('#refreshStudentReport').on('click',
        function(ev) {
            ev.preventDefault();
            $.ajax({
                url: "getReportByStudentCSVByGrid.htm",
                data: {
                    testSessionId: testSessionID
                },
                dataType: 'json',
                type: "GET"
            }).done(function(data) {
                var json = getJSONArrayForReports(data);
                var gridId = "#studentReportsGrid";
                $("#studentReportLabel").html(testNameIs + ', Generated ' + new Date().toLocaleString());
                $(gridId).jqGrid('clearGridData');
                $(gridId).jqGrid('setGridParam', {
                    data: json
                });
                $(gridId).trigger('reloadGrid');
            });
        });


    $('#CSVTestSummary, #CSVItemReports, #CSVStudentReports').on('click',
        function(ev) {
            var iCol, $grid = $(gridId),
                colModel = $grid.jqGrid("getGridParam", "colModel");
            var nonHiddenColumns = colModel.filter(function(cm) {
                if (!cm.hidden && cm.name !== "cb" && cm.name !== "subgrid" && cm.name !== "rn" && cm.formatter !== "actions" && cm.name != undefined)
                    return cm;
            });
            var response = lastSelected.map(function(obj) {
                var rObj = {};
                for (var i=0; i< nonHiddenColumns.length; i++) {
                    rObj[nonHiddenColumns[i].label] = obj[nonHiddenColumns[i].name];

                }
                return rObj;
            });

            var csv = Papa.unparse(response);
            var blob = new Blob([csv], {
                type: "text/csv"
            });

            // Determine which approach to take for the download
            if (navigator.msSaveOrOpenBlob) {
                // Works for Internet Explorer and Microsoft Edge
                navigator.msSaveOrOpenBlob(blob, csvName + ".csv");

            } else {
                $("#CSVTestSummary, #CSVItemReports, #CSVStudentReports").attr('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(csv));
                $("#CSVTestSummary, #CSVItemReports, #CSVStudentReports").attr('download', csvName + ".csv");
            }
        });

}


function populateSchoolDropdown(districtId) {
    var d = new Object();
    if (districtId != null) {
        d["districtId"] = districtId;
    }
    d.predictiveStudentScore = predictiveStudentScore;
    $.ajax({
        url: "getOrganizationDropDownsData.htm",
        data: d,
        dataType: 'json',
        type: "GET"
    }).done(function(data) {
            orgainzationsArray = data;
            new EJS({
                url: contextPath + '/js/views/viewresults.ejs'
            }).update('schoolDropdown', {
                districtsArray: {
                    organizations: []
                },
                orgainzationsArray: orgainzationsArray,
                subjectsArray: {
                    subjects: []
                },
                gradesArray: {
                    grades: []
                },
                studentArray: {
                    students: []
                }
            });
            $("#schoolDropdown").select2({
                data: orgainzationsArray.organizations.organizationName,
                placeholder: function() {
                    $(this).data('placeholder');
                },
                allowClear: true
            });
            $('.select2-search select2-search--inline').css('background-color', 'white !important');
            $('#schoolDropdown').prop('disabled', false);
            $("#schoolDropdown").select2();

            $("#checkboxSchool").removeAttr('disabled');
            $("#checkboxSchool").on('click',function() {
                if ($("#checkboxSchool").is(':checked')) {
                    $("#schoolDropdown > option").prop("selected", "selected");
                    $("#schoolDropdown").trigger("change");
                } else {
                    $("#schoolDropdown > option").prop("selected", false);
                    $("#schoolDropdown").trigger("change");
                }
            });
        });
}

function populateDistrictDropdown() {
    var d = new Object();
    d.predictiveStudentScore = predictiveStudentScore;

    $.ajax({
        url: "getDistrictDropDownsData.htm",
        type: "GET",
        data: d 
    }).done(function(data) {
        districtsArray = data;
        new EJS({
            url: contextPath + '/js/views/viewresults.ejs'
        }).update('districtDropdown', {
            districtsArray: districtsArray,
            orgainzationsArray: {
                organizations: []
            },
            subjectsArray: {
                subjects: []
            },
            gradesArray: {
                grades: []
            },
            studentArray: {
                students: []
            }
        });
        $("#districtDropdown").select2({
            data: districtsArray.organizations.organizationName,
            placeholder: function() {
                $(this).data('placeholder');
            },
            allowClear: true
        });
        $('.select2-search select2-search--inline').css('background-color', 'white !important');
        $("#districtDropdown").trigger("change");
    });
}

function organizationDropDownData() {
    var groupMap = {};
    var groupRoleMap = {};
    var groupsNameMap = {};
    var d = new Object();
    d.predictiveStudentScore = predictiveStudentScore;

    $.ajax({
        url: 'getGroupsOrgTypeMap.htm',
        type: 'GET',
        dataType: 'json',
        async: false,
        data: d 
    }).done(function(data) {
            if (data != undefined && data != null) {
                groupMap = data.groupsOrgMap;
                groupRoleMap = data.groupsRoleTypeMap;
                groupsNameMap = data.groupsNameMap;
            }
        });
    var currentGroupId = $('#hiddenCurrentGroupsId').val();
    var roleOrgTypeCode = groupRoleMap[currentGroupId];
    if (roleOrgTypeCode < 5) {
        populateDistrictDropdown();
    } else if (roleOrgTypeCode == 5) {
        $("#districtDiv").hide();
        populateSchoolDropdown(null);
    } else if (roleOrgTypeCode == 6 || roleOrgTypeCode == 7) {
        $("#districtDiv").hide();
        $("#schoolDiv").hide();
        var dataArray = [];
        var currentOrganizationId = $('#hiddenCurrentOrganizationId').val();
        dataArray.push(currentOrganizationId);
        var d = new Object();
        d.predictiveStudentScore = predictiveStudentScore;
        d.organizationIds = dataArray;
        $.ajax({
            url: "getSubjectDropDownsData.htm",
            data: d,
            dataType: 'json',
            type: "GET"
        }).done(function(data) {
                subjectsArray = data;
                new EJS({
                    url: contextPath + '/js/views/viewresults.ejs'
                }).update('subjectDropdown', {
                    districtsArray: {
                        organizations: []
                    },
                    orgainzationsArray: {
                        organizations: []
                    },
                    subjectsArray: subjectsArray,
                    gradesArray: {
                        grades: []
                    },
                    studentArray: {
                        students: []
                    }
                });
                $("#subjectDropdown").select2({
                    data: subjectsArray.subjects.name,
                    placeholder: function() {
                        $(this).data('placeholder');
                    },
                    allowClear: true
                });
                $('.select2-search select2-search--inline').css('background-color', 'white !important');
                $("#subjectDropdown").removeAttr('disabled');
                $("#checkboxSubject").removeAttr('disabled');
                $("#subjectDropdown").select2();
                $("#checkboxSubject").on('click',function() {
                    if ($("#checkboxSubject").is(':checked')) {
                        $("#subjectDropdown > option").prop("selected", "selected");
                        $("#subjectDropdown").trigger("change");
                    } else {
                        $("#subjectDropdown > option").removeAttr("selected");
                        $("#subjectDropdown").trigger("change");
                    }
                });              

            });
        

    }
}

function dropDownsDisable(type) {
    if (type == 'organization') {
        // disable the dropdown
        $("#gradeDropdown").prop('disabled', true);
        $("#studentDropdown").prop('disabled', true);
        $('#searchStudents').prop('disabled', true);
        // clear the dropdown values
        $("#gradeDropdown").select2("val", "");
        $("#studentDropdown").select2("val", "");
        $('#gradeDropdown').empty();
        $('#studentDropdown').empty();

        // disable the checkbox
        $("#checkboxGrade").prop('disabled', true);
        $('#checkboxStudent').prop('disabled', true);

        // clear and uncheck the checkbox values
        $('#checkboxSubject').prop('checked', false);
        $('#checkboxGrade').prop('checked', false);
        $('#checkboxStudent').prop('checked', false);
    }
    if (type == 'subject') {
        $("#studentDropdown").prop('disabled', true);
        $('#searchStudents').prop('disabled', true);

        $("#studentDropdown").select2("val", "");
        $('#studentDropdown').empty();

        $('#checkboxStudent').prop('disabled', true);
        $('#checkboxGrade').prop('checked', false);
        $('#checkboxStudent').prop('checked', false);
    }
}


var requestStack = [];
var isStudentsQueryInprogress = false;


var loadTestingCycle = false;
var testingCycles = new Object();





function getInterimPredictiveStudentScore(studentIds, subjectIds, gradeIds) {
    var dataArray = $('#schoolDropdown').val();

	 if ($('#schoolDropdown').val() == null || $('#schoolDropdown').val() == '') {
         var dataArray = [];
         var currentOrganizationId = $('#hiddenCurrentOrganizationId').val();
         dataArray.push(currentOrganizationId);
     }
    var postData = new Object();
    postData.studentIds = studentIds;
    postData.gradeCourseIds = gradeIds;
    postData.contentAreaIds = subjectIds;
    postData.orgIds= dataArray;
    $.ajax({
        url: "getInterimPredictiveStudentScores.htm",
        data: postData,
        dataType: 'json',
        async: false,
        type: "POST"
    }).done(function(students) {
            displayActivityReportGrid(students);
        });

}

function getStudentActivityReportGrid(studentIds) {
    var postData = new Object();
    postData.studentIds = studentIds;
    $.ajax({
        url: "getStudentReportActivityDetails.htm",
        data: postData,
        dataType: 'json',
        async: false,
        type: "POST"
    }).done(function(students) {
            displayActivityReportGrid(students);
        });

}

function getColNamesAndModels() {
    var ret = new Object();
    if (predictiveStudentScore) {

        ret.colNames = ['State_Student_Identifier', 'Student_Legal_Last_Name', 'Student_Legal_First_Name'];
        ret.colModel = [{
            label: 'State_Student_Identifier',
            name: 'stateStudentIdentifier',
            index: 'stateStudentIdentifier',
            width: 110,
            align: 'center',
            sortable: true,
            hidden: false,
            classes: 'grid-col',
            frozen: true,
            title : 'State Student Identifier',
            searchoptions : {title:"State_Student_Identifier"}
        }, {
            label: 'Student_Legal_Last_Name',
            name: 'studentLegalLastName',
            index: 'studentLegalLastName',
            width: 120,
            align: 'center',
            sortable: true,
            hidden: false,
            classes: 'grid-col',
            title : 'Student Legal Last Name',
            searchoptions : {title:"Student_Legal_Last_Name"}
        }, {
            label: 'Student_Legal_First_Name',
            name: 'studentLegalFirstName',
            index: 'studentLegalFirstName',
            width: 120,
            align: 'center',
            sortable: true,
            hidden: false,
            classes: 'grid-col',
            title : 'Student Legal First Name',
            searchoptions : {title:"Student_Legal_First_Name"}
        }]
        for (var i=0; i<testingCycles.length; i++) {
            ret.colNames.push(testingCycles[i].testingCycleName + "_Predicted_Score_Range_High", testingCycles[i].testingCycleName + "_Predicted_Score_Range_Low");
            ret.colModel.push({
                label: testingCycles[i].testingCycleName + "_Predicted_Score_Range_High",
                name: testingCycles[i].testingCycleName + "_Predicted_Score_Range_High",
                index: testingCycles[i].testingCycleName + "_Predicted_Score_Range_High",
                width: 80,
                align: 'center',
                hidden: false,
                sortable: true,
                editable: false,
                title : testingCycles[i].testingCycleName + "_Predicted_Score_Range_High",
                searchoptions : {title:testingCycles[i].testingCycleName + "_Predicted_Score_Range_High"}
            }, {
                label: testingCycles[i].testingCycleName + "_Predicted_Score_Range_Low",
                name: testingCycles[i].testingCycleName + "_Predicted_Score_Range_Low",
                index: testingCycles[i].testingCycleName + "_Predicted_Score_Range_Low",
                width: 80,
                align: 'center',
                hidden: false,
                sortable: true,
                editable: false,
                title : testingCycles[i].testingCycleName + "_Predicted_Score_Range_Low",
                searchoptions : {title:testingCycles[i].testingCycleName + "_Predicted_Score_Range_Low"}
            })
        }
        ret.colNames.push('Summative_Scale_Score', 'Summative_Level', 'Subject', 'Grade',
            'District_Identifier', 'District_Name', 'School_Identifier', 'School_Name', 'School_Year');
        ret.colModel.push({
                label: "Summative_Scale_Score",
                name: "summativeScaleScore",
                index: "summativeScaleScore",
                width: 80,
                align: 'center',
                hidden: false,
                sortable: true,
                editable: false,
                title : "Summative Scale Score",
                searchoptions : {title:"Summative_Scale_Score"}
            }, {
                label: "Summative_Level",
                name: "summativeLevel",
                index: "summativeLevel",
                width: 80,
                align: 'center',
                hidden: false,
                sortable: true,
                editable: false,
                title : "Summative Level",
                searchoptions : {title:"Summative_Level"}
            }, {
                label: 'Subject',
                name: 'subject',
                index: 'subject',
                width: 120,
                sortable: true,
                align: 'center',
                hidden: false,
                editable: false,
                title : "Subject",
                searchoptions : {title:"Subject"}
            }, {
                label: 'Grade',
                name: 'grade',
                index: 'grade',
                width: 80,
                align: 'center',
                hidden: false,
                sortable: true,
                editable: false,
                search: true,
                title : "Grade",
                searchoptions : {title:"Grade"}
            },

            {
                label: 'District_Identifier',
                name: 'districtIdentifier',
                index: 'districtIdentifier',
                width: 80,
                sortable: true,
                align: 'center',
                hidden: false,
                editable: false,
                title : "District Identifier",
                searchoptions : {title:"District_Identifier"}
            }, {
                label: 'District_Name',
                name: 'districtName',
                index: 'districtName',
                width: 80,
                align: 'center',
                hidden: false,
                sortable: true,
                editable: false,
                title : "District Name",
                searchoptions : {title:"District_Name"}
            }, {
                label: 'School_Identifier',
                name: 'schoolIdentifier',
                index: 'schoolIdentifier',
                width: 80,
                align: 'center',
                hidden: false,
                sortable: true,
                editable: false,
                title : "School Identifier",
                searchoptions : {title:"School_Identifier"}
            }, {
                label: 'School_Name',
                name: 'schoolName',
                index: 'schoolName',
                width: 80,
                align: 'center',
                hidden: false,
                sortable: true,
                editable: false,
                title : "School Name",
                searchoptions : {title:"School_Name"}
            }, {
                label: 'School_Year',
                name: 'schoolYear',
                index: 'schoolYear',
                width: 80,
                sortable: true,
                hidden: false,
                align: 'center',
                title : "School Year",
                searchoptions : {title:"School_Year"}
            })

    } else {
		if (currentAssessmentProgramName == 'PLTW'){
    		var subjectColumn = 'Test Course'
    	}else{
    		var subjectColumn = 'Test Subject'
    	}
        ret.colNames = ['First Name',
            'Last Name',
            'Student ID',
            'Status',
            'Test', 'Total Points', 'Total %', 'Completion Date', 'Test Assigned Date',
            'Student Grade', subjectColumn
        ]
        ret.colModel = [{
            label: 'First Name',
            name: 'firstName',
            index: 'firstName',
            width: 150,
            sortable: true,
            hidden: false,
            align: 'center',
            title : 'First Name',
            searchoptions : {title:"First Name"}
        }, {
            label: 'Last Name',
            name: 'lastName',
            index: 'lastName',
            width: 150,
            align: 'center',
            sortable: true,
            classes: 'grid-col',
            title : 'Last Name',
            searchoptions : {title:"Last Name"}
        }, {
            label: 'Student Id',
            name: 'studentId',
            index: 'studentId',
            width: 120,
            editable: false,
            align: 'center',
            sortable: true,
            classes: 'grid-col',
            title : 'Student Id',
            searchoptions : {title:"Student Id"}
        }, {
            name: 'status',
            label: 'Status',
            index: 'status',
            width: 100,
            sortable: true,
            align: 'center',
            editable: false,
            title : 'Status',
            searchoptions : {title:"Status"}
        }, {
            name: 'test',
            label: 'Test',
            index: 'test',
            width: 150,
            sortable: true,
            align: 'center',
            editable: false,
            title : 'Test',
            searchoptions : {title:"Test"}
        }, {
            name: 'totalPoints',
            label: 'Total Points',
            index: 'totalPoints',
            width: 80,
            align: 'center',
            sortable: true,
            editable: false,
            title : 'Total Points',
            searchoptions : {title:"Total Points"}
        }, {
            name: 'totalPercentage',
            label: 'Total %',
            index: 'totalPercentage',
            width: 80,
            align: 'center',
            sortable: true,
            editable: false,
            title : 'Total Percentage',
            searchoptions : {title:"Total %"}
        }, {
            name: 'testCompletionDate',
            label: 'Completion Date',
            index: 'testCompletionDate',
            width: 220,
            align: 'center',
            sortable: true,
            editable: false,
            search: true,
            title : 'Completion Date',
            searchoptions : {title:"Completion Date"}
        }, {
            name: 'testAssignedDate',
            label: 'Test Assigned Date',
            index: 'testAssignedDate',
            width: 200,
            align: 'center',
            sortable: true,
            hidden: true,
            editable: false,
            search: true,
            title : 'Test Assigned Date',
            searchoptions : {title:"Test Assigned Date"}
        }, {
            name: 'studentGrade',
            label: 'Student Grade',
            index: 'studentGrade',
            width: 120,
            align: 'center',
            hidden: false,
            editable: false,
            title : 'Student Grade',
            searchoptions : {title:"Student Grade"}
        }, {
            name: 'testSubject',
            label: subjectColumn,
            index: 'testSubject',
            width: 120,
            align: 'center',
            hidden: false,
            sortable: true,
            editable: false,
            title : subjectColumn,
            searchoptions : {title:subjectColumn}
        }]
        if(currentAssessmentProgramName != 'PLTW' ){
        	ret.colNames.push('Test Grade');
            ret.colModel.push( 
                    {
                        name: 'testGrade',
                        label: 'Test Grade',
                        index: 'testGrade',
                        width: 120,
                        align: 'center',
                        hidden: false,
                        sortable: true,
                        editable: false,
                        title : 'Test Grade',
                        searchoptions : {title:"Test Grade"}
                    })
        }
                     
    }
    return ret;


}


function displayActivityReportGrid(students) {

    var updatedList = JSON.stringify(students, function(key, value) {
        return (value === null) ? "-" : value
    });

    // $('#studentActivityRepGrid').trigger( 'reloadGrid' );
    jQuery('#studentActivityRepGrid').jqGrid('clearGridData');
    jQuery('#studentActivityRepGrid').jqGrid('setGridParam', {
        data: students
    });
    jQuery('#studentActivityRepGrid').trigger('reloadGrid');
    var colData = getColNamesAndModels();
    $("#studentActivityRepGrid")
        .jqGrid({
            datatype: "local",
            data: students,
            cmTemplate: {
                title: false
            },
            colNames: colData.colNames,
            colModel: colData.colModel,
            rowNum: 20,
            cmTemplate: {
                title: false
            },
            rowList: [10, 20, 30, 'All'],
            width: 1060,
            gridview: true,
            ignoreCase: true,
            rownumbers: false,
            sortname: 'invdate',
            viewrecords: true,
            shrinktofit: true,
            sortorder: 'desc',
            loadonce: true,
            jsonReader: {
                repeatitems: false,
            },
            height: 305,
            rowheight: 200,
            shrinkToFit: false,
            autowidth: false,
            scrollOffset: 0,
            altRows: true,
            hoverrows: true,
            pager: '#studentActivityRepPager',
            altClass: 'altrow',
            editurl: "getStudentReportActivityDetails.htm",
            loadComplete: function() {
                $(".ui-pg-selbox option[value='All']").val(100000);
                this.p.lastSelected = lastSelected; // set this.p.lastSelected
            },
            gridComplete: function() {
            }
        });

    $("#studentActivityRepGrid").jqGrid('filterToolbar', {
        stringResult: true,
        searchOnEnter: false,
        defaultSearch: "cn"
    });
    $('tr.ui-search-toolbar input').css('width', '100%');
    jQuery("#studentActivityRepGrid").jqGrid('navGrid', '#studentActivityRepPager', {
        edit: false,
        add: false,
        del: false,
        search: false,
        refresh: false
    });
    $('#studentActivityRepPager td#input_studentActivityRepPager input').css("cssText","height: 22px !important;");

    $('#studentActivityRepGrid').jqGrid('navButtonAdd', "#studentActivityRepPager", {
        caption: "Columns",
        buttonicon: "ui-icon-calculator",
        title: "Choose columns",
        id: "colChooser",
        onClickButton: function() {

            $('#studentActivityRepGrid').jqGrid('columnChooser', {
                width: 550,
                dialog_opts: {
                    modal: true,
                    minWidth: 470,
                    minHeight: 470,
                    dividerLocation: 0.5
                },

            });

        }

    });
}

var studentActivityLinkExpire = true;
var activateLink = function() {
    studentActivityLinkExpire = true;
}



function incompleteScore(testTestId) {
    $.ajax({
        url: 'getTestCompletedStudents.htm',
        data: {
            testTestId: testTestId
        },
        dataType: 'json',
        type: "GET"
    }).done(function(studentsList) {

            $('#interimTabs').html(new EJS({
                url: contextPath + '/js/views/scoring.ejs'
            }).render({
                studentsList: studentsList

            }));

            $('#completeScoress').dialog('close');
            $("#rubric").hide();

        });

    $.ajax({
        url: 'getQuestionsForStudentByTest',
        data: {
            testTestId: testTestId
        },
        dataType: 'json',
        type: "GET"
    }).done(function(questionsForStudents) {
        });

}

$('#students').on('click', function() {
    $("#studentsOfTest").hide();
    $("#rubric").show();
});

function actionFormatter(cellvalue, options, rowObject) {
    return cellvalue !=undefined ? cellvalue : '&nbsp';
}

function jqGridConstruction() {

    $.ajax({
            url: "getTotalTests.htm",
            data: {},
            dataType: 'json',
            type: "POST"
        }).done(function(students) {
        	
            for (var i in students.rows) {
                if (students.rows[i].testDescription && students.rows[i].testDescription.length > 100) {
                    students.rows[i].dsc = students.rows[i].testDescription
                        .substring(0, 99) + '...<a class="js-moredec" title = "Test Description" data-dec="' + students.rows[i].testDescription + '">More</a>'
                } else {
                    students.rows[i].dsc = students.rows[i].testDescription
                }
            }
            $('#list').trigger('reloadGrid');
            $("#list").jqGrid({
                    datatype: 'local',
                    data: students.rows,
                    colNames: ['ID', 'ACTIONS', 'NAME',
                        'DESCRIPTION',
                        /*
                         * ' SCHEDULED', '
                         * START
                         * TIME(CT)',
                         */
                        'STATUS',
                        'STUDENTS ASSIGNED',
                        'STUDENTS ATTEMPTED',
                        'CREATED BY', 'BUILDING',
                        'DATE CREATED', 'ASSIGN TYPE'
                    ],
                    colModel: [{
                            label: 'serial',
                            name: 'serial',
                            label : 'Serial',
                            width: 30,
                            key: true,
                            hidden: true,
                            hidedlg: true,
                            title : 'Serial',
                            searchoptions : {title:"ID"}
                        }, {
                            name: 'act',
                            index: 'act',
                            label : 'InterimAct',
                            width: 200,
                            align: 'left',
                            cmTemplate: {
                                title: false
                            },
                            sortable: false,
                            search: false,
                            formatter: actionFormatter,
                            searchoptions : {title:"Actions"}
                        }, {
                            name: 'testName',
                            index: 'testName',
                            label : 'InterimTestName',
                            align: 'left',
                            classes: 'grid-col',
                            width: 170,
                            title : 'Test Name',
                            searchoptions : {title:"Test Name"}

                        }, {
                            name: 'dsc',
                            index: 'dsc',
                            label : 'InterimDsc',
                            width: 200,
                            align: 'left',
                            classes: 'grid-col',
                            editable: false,
                            title : 'Description',
                            searchoptions : {title:"Description"}

                        },
                        /*
                         * { name : 'scheduled', index :
                         * 'scheduled', width : 300, align :
                         * 'center', editable : false }, {
                         * name : 'startTimeCT', index :
                         * 'startTimeCT', width : 300, align :
                         * 'center', editable : false },
                         */
                        {
                            name: 'testStatus',
                            index: 'testStatus',
                            label : 'InterimTestStatus',
                            width: 150,
                            align: 'center',
                            editable: false,
                            title : 'Test Status',
                            searchoptions : {title:"Test Status"}
                        }, {
                            name: 'studentsAssigned',
                            index: 'studentsAssigned',
                            label : 'InterimStudentsAssigned',
                            width: 150,
                            align: 'center',
                            sorttype: 'number',
                            editable: false,
                            title : 'Students Assigned',
                            searchoptions : {title:"Students Assigned"}
                        }, {
                            name: 'studentsAttempted',
                            index: 'studentsAttempted',
                            label : 'InterimStudentsAttempted',
                            width: 150,
                            align: 'center',
                            sorttype: 'number',
                            editable: false,
                            title : 'Students Attempted',
                            searchoptions : {title:"Students Attempted"}
                        }, {
                            name: 'assembledBy',
                            index: 'assembledBy',
                            label : 'InterimAssembledBy',
                            align: 'center',
                            width: 150,
                            editable: false,
                            title : 'Created By',
                            searchoptions : {title:"Assembled By"}
                        }, {
                            name: 'schoolName',
                            index: 'schoolName',
                            label : 'InterimSchoolName',
                            align: 'center',
                            width: 150,
                            editable: false,
                            title : 'School Name',
                            searchoptions : {title:"Building"}
                        }, {
                            name: 'createdDateString',
                            index: 'createdDateString',
                            label : 'InterimCreatedDate',
                            width: 150,
                            align: 'center',
                            editable: false,
                            title : 'Created Date',
                            searchoptions : {title:"Created Date"}
                        }, {
                            name: 'assignmentType',
                            index: 'assignmentType',
                            label : 'InterimAssignmentType',
                            width: 150,
                            align: 'center',
                            editable: false,
                            title : 'Assignment Type',
                            searchoptions : {title:"Assign Type"}
                        }
                    ],
                    rowNum: 10,
                    cmTemplate: {
                        title: false
                    },
                    hidegrid: false,
                    rowList: [10, 20, 30],
                    width: 1800,
                    pager: '#pager',
                    gridview: true,
                    ignoreCase: true,
                    rownumbers: true,                        
                    viewrecords: true,
                    altRows: true,
                    hoverrows: true,
                    altClass: 'altrow',
                    caption: 'My Tests',
                    loadonce: true,
                    jsonReader: {
                        repeatitems: false,
                    },
                    height: 300,
                    rowheight: 300,
                    shrinkToFit: false,
                    autowidth: true,
                    scrollOffset: 0,
                    editurl: "getTotalTests.htm",
                    gridComplete: function() {
                        var ids = jQuery("#list").getDataIDs();
                        $("#gbox_list").css("width", "");

                        for (var i = 0; i < students.rows.length; i++) {
                            if (students.rows[i].studentsAttempted > 0) {
                                attmptedTestArray.push(students.rows[i].id);
                            }
                        }
                        var testsToHideFeedback=[]
                        for (var i = 0; i < ids.length; i++) {
                            var cl = ids[i];
                            var obj = _.findWhere(students.rows, {serial: parseInt(cl)});

                            if (obj.windowEffectiveDateString == null) {
                                az = ''
                                ax = ''
                            } else {
                                az = obj.windowEffectiveDateString
                                    .split(',')[0] + ' - ' + obj.windowExpiryDateString
                                    .split(',')[0]
                                ax = obj.windowStartTimeString + ' - ' + obj.windowEndTimeString
                            }

                            var testAccess = obj.access;
                            var TRUE = 'TRUE'
                             
                            be = '   <a tabindex="0"  OnKeyPress=assignStudent(event' + ',' + obj.id + ',' + testAccess + ',' + obj.isTestAssigned + ',' + obj.testSessionId + ',' + obj.serial + ',' + obj.autoAssignId + ',' + obj.testCollectionId + ') onclick=assignStudent(event' + ',' + obj.id + ',' + testAccess + ',' + obj.isTestAssigned + ',' + obj.testSessionId + ',' + obj.serial + ',' + obj.autoAssignId + ',' + obj.testCollectionId + ') title="Assign Test" data-testaccess="' + testAccess + '" class="studentassign" data-serial="' + obj.serial + '" data-id="' + obj.id + '" data-startdate="' + obj.windowEffectiveDateString + '" data-enddate="' + obj.windowExpiryDateString + '" data-endtime="' + obj.windowEndTimeString + '" data-starttime="' + obj.windowStartTimeString + '" data-testname="' + obj.testName + '"><i class="fa fa-user fa-lg"></i></a>';
                            
                            //Removed code for edit functionality during Gravity release. The code-base for edit still exists in case it re-appears.
                            
                            ce = '  <a tabindex="0" OnKeyPress=deleteInterim(event' + ',' + obj.id + ',' + obj.studentsAttempted + ',' + obj.testSessionId + ') onclick=deleteInterim(event' + ',' + obj.id + ',' + obj.studentsAttempted + ',' + obj.testSessionId + ') title="Delete Test" ><i class="fa fa-trash-o fa-lg"></i></a>';
                            as = '  <a tabindex="0" OnKeyPress=previrewInterim(event' + ',' + obj.id + ',' + obj.testCollectionId + ') onclick=previrewInterim(event' + ',' + obj.id + ',' + obj.testCollectionId + ') title="Preview Test" ><i class="fa fa-eye fa-lg"></i></a>';
                            ra = '  <a tabindex="0" OnKeyPress=assignStudent(event' + ',' + obj.id + ',' + testAccess + ',' + obj.isTestAssigned + ',' + obj.testSessionId + ',' + obj.serial + ',' + obj.autoAssignId + ',' + obj.testCollectionId + ') onclick=assignStudent(event' + ',' + obj.id + ',' + testAccess + ',' + false + ',' + null + ',' + obj.serial +  ',' + obj.autoAssignId + ',' + obj.testCollectionId + ') title="Reuse this test with other students." ><i class="fa fa-retweet fa-lg"></i></a>';
                            pdf = ' <a tabindex="0" href="getPDFTickets.htm?assessmentProgramName=KAP&testSessionId=' + obj.testSessionId + '&testCollectionId=' + obj.testCollectionId + '&isInterim=' + 'TRUE' + (obj.predictiveAutoAssigned ? '&isPredictive=true&isAutoRegistered=true' : '') + '" title="Student usernames and passwords."><i class="fa fa-file-pdf-o fa-lg" style="color:red" aria-hidden="true"></i></a>'
                                // added play pause in the part
                                // of F560
                            play = '    <a tabindex="0" OnKeyPress=suspendTestSession(event' + ',' + obj.testSessionId + ',' + false + ') onclick=suspendTestSession(event' + ',' + obj.testSessionId + ',' + false + ') title="Start accepting responses." ><i class="fa fa-play-circle-o fa-lg"></i></a>';
                            pause = '   <a tabindex="0" OnKeyPress=suspendTestSession(event' + ',' + obj.testSessionId + ',' + true + ') onclick=suspendTestSession(event' + ',' + obj.testSessionId + ',' + true + ') title="Stop accepting responses." ><i class="fa fa-pause-circle fa-lg"></i></a>';
                            mn = '  <a tabindex="0"  OnKeyPress="monitorInterimTest(event' + ',' + obj.testSessionId + ",'" + obj.testName + '\')" onclick="monitorInterimTest(event' + ',' + obj.testSessionId + ",'" + obj.testName + '\')" title="Monitor test session."><i class="fa monitor-icon fa-lg"></i></a>';                                                                                         	 
                            fb = '  <a tabindex="0" href=""  id='+obj.interimTestId+' data-id='+obj.id+' data-name='+obj.testName+' data-testcollectionid='+obj.testCollectionId+' title="View/Edit Feedback."><i class="fa fa-commenting-o fa-lg text_blue feedbackAllowed"></i></a>';
                            
                            if(!obj.feedbackAllowed)
                            	testsToHideFeedback.push('#'+obj.interimTestId);
                            	
                            if (obj.predictiveAutoAssigned) { // predictive
                                // tests
                                // get
                                // nothing
                                // but
                                // ability
                                // to
                                // view
                                // ticket
                                // PDF
                                // and
                                // monitor
                                $('#list').setRowData(ids[i], {act: pdf + mn});
                            } else if (obj.access) {
                                if (obj.testSessionId) {
                                    if (obj.studentsAssigned > 0) {
                                        jQuery("#list").setRowData(ids[i], {act: be + ce + as + fb + ra + pdf + mn});
                                        // added suspend
                                        // condition for play
                                        // pause functionality
                                        // in the part of F560
                                        
                                        if (obj.suspend == true) {
                                            jQuery("#list").setRowData(ids[i], {act: be + ce + as + fb + ra + pdf + play + mn});
                                        } else if (obj.suspend == false) {
                                            jQuery("#list").setRowData(ids[i], { act: be + ce + as+ fb + ra + pdf + pause + mn});
                                        }

                                    } else {
                                        jQuery("#list").setRowData(ids[i], {act: be + ce + as+ fb + ra + mn});
                                    }

                                } else {
                                    jQuery("#list").setRowData(ids[i], {act: be + ce + as+ fb + mn});
                                }
                            } else {
                                jQuery("#list").setRowData(ids[i], { act: be + as + fb + mn });
                            }
                            jQuery("#list").setRowData(ids[i], false, {height: 20 + (i * 2)});
                        }
                        for(var i=0;i<testsToHideFeedback.length;i++)
                        {
                        	$(testsToHideFeedback[i]).css({"display":"none"});
                        }
                        
                    }
                });
            
            $("#list").jqGrid('filterToolbar', {
                stringResult: true,
                searchOnEnter: false,
                defaultSearch: "cn"
            });

            
            $('tr.ui-search-toolbar input').css('width', '100%');       
            jQuery("#list").jqGrid('navGrid', '#pager', {
                edit: false,
                add: false,
                del: false,
                search: true
            });

            $('#pager td#input_pager input').css("cssText","height: 22px !important;")

            $('#list').jqGrid('navButtonAdd', "#pager", {
                caption: "Columns",
                buttonicon: "ui-icon-calculator",
                title: "Choose columns",
                onClickButton: function() {

                    $('#list').jqGrid('columnChooser', {
                        width: 550,
                        dialog_opts: {
                            modal: true,
                            minWidth: 470,
                            minHeight: 470,
                            dividerLocation: 0.5
                        },

                    });

                }

            });

            $('#list').jqGrid('navButtonAdd', "#pager", {
                caption: "Legend",
                buttonicon: "ui-icon-info",
                title: "Icon Details",
                onClickButton: function() {

                    $('#iconDetailsDailog').dialog({
                        resizable: false,
                        modal: true,
                        width: 450,
                        height: 400,
                        title: 'Icon Details',
                        buttons: {
                            Close: function(ev) {
                                $(this).dialog("close");
                                return false;
                            }
                        }
                    }).dialog('open');

                }

            });

            $('#list').jqGrid('navButtonAdd',"#pager", {
                        caption: "Manage Groups",
                        title: "Manage Groups",
                        onClickButton: function() {

                            $.ajax({
                                    url: 'assignView.htm',
                                    data: {},
                                    dataType: 'json',
                                    type: "GET"
                                }).done(function(data) {
                                        // data.rostername=_.sortBy(data.rostername,
                                        // 'name');
                                        data.gradename.sort(function(a,b) {
                                                var ax = [],
                                                    bx = [];

                                                a.name.replace( /(\d+)|(\D+)/g,function(_, $1,$2) {
                                                	
                                                            ax.push([ $1 || Infinity, $2 || ""])
                                                        });
                                                b.name.replace(/(\d+)|(\D+)/g, function( _, $1, $2) {
                                                	
                                                            bx.push([$1 || Infinity,$2 || ""])
                                                        });

                                                while (ax.length && bx.length) {
                                                    var an = ax.shift();
                                                    var bn = bx.shift();
                                                    var nn = (an[0] - bn[0]) || an[1].localeCompare(bn[1]);
                                                    if (nn)
                                                        return nn;
                                                }

                                                return ax.length - bx.length;
                                            })
                                        $('#interimTabs').html(
                                                new EJS({
                                                    url: contextPath + '/js/views/managegroups.ejs'
                                                })
                                                .render({
                                                    dropdownObject: data,
                                                    groups: groupsArray,
                                                    currentAssessmentProgramName:currentAssessmentProgramName
                                                }));
                                        $("#interimRostercreate")
                                            .select2({
                                                data: data.rostername.name,
                                                placeholder: function() {
                                                    $(this).data('placeholder');
                                                },
                                                allowClear: true,
                                                width:'element'
                                            });

                                        $("#interimRosterGradecreate")
                                            .select2({
                                                data: data.gradename.name,
                                                placeholder: function() {
                                                    $(this).data('placeholder');
                                                },
                                                allowClear: true
                                            });

                                        $("#interimRosteredit")
                                            .select2({
                                                data: data.rostername.name,
                                                placeholder: function() {
                                                    $(this).data('placeholder');
                                                },
                                                allowClear: true
                                            });

                                        $("#interimRosterGradeedit")
                                            .select2({
                                                data: data.gradename.name,
                                                placeholder: function() {
                                                    $(this).data('placeholder');
                                                },
                                                allowClear: true
                                            });
                                        
                                        // This is for preview Group                                       
                                        $(document).on('click','.viewGroup',function(ev){ 
                                                ev.preventDefault();
                                                previewGroup($(this));
                                         });
                                        

                                        // This is for create new group
                                        $('#addnewgroup').on('click', function(ev) {
                                            ev.preventDefault();
                                            $('.js-creategroupdiv').show();
                                            $('.js-editgroupdiv').hide();
                                            $('.js-grouppreviewdiv').hide();

                                        });
                                        
                                        // This is for Delete group
                                        $('#deleteGroup').on('click', function(ev) {
                                            ev.preventDefault();
                                            var groupId = $(this).data('id');
                                            $.ajax({
                                                url: 'deleteStudentGroup.htm',
                                                data: {
                                                    interimGroupId: groupId
                                                },
                                                dataType: 'json',
                                                type: "POST"
                                            }).done(function(data) {
                                                });
                                        });
                                        
                                        // This is for group search by group name and user name
                                        $('#searchgroup').on('click', function(ev) {
                                            ev.preventDefault();
                                            searchGroupByGrpAndUsrName();
                                        });
                                                                              
                                        $('#studentgroupcreateSearch').on('click',function(ev) {
                                        	ev.preventDefault();
                                        	studentSearchToManageGroups();
                                        });                                     
                                        
                                        $('#studentSearch').on('click',function(ev) {
                                        	ev.preventDefault();
                                        	studentSearch($(this));
                                        });
                                        
                                      //This is for edit group name call                                       
                                        $(document).on('click','.editGroup',function(ev){                               
                                        	ev.preventDefault();
                                        	editGroup($(this));
                                        });
                                        
                                      //this is for update group details
                                        $('#js-updategroup').on('click',function(ev) {
                                        	ev.preventDefault();
                                        	updateGroup($(this));
                                        });

                                     // This is for create group call
                                        $('#createGroup').on('click',function(ev) {
                                            ev.preventDefault();
                                            createAGroup();
                                        });
                                        
                                        $('#studentgroupeditSearch').on('click',function(ev){
                                        	ev.preventDefault();
                                        	studentGroupEditSearch();
                                        });
                                        
                                        $('#js-addstudentstoexistinggroup').on('click', function(ev) {                                       	
                                        	ev.preventDefault();
                                        	addStudentsToExistingGroup();

                                        });
                                        
                                    	$('#js-cancelgroup').on('click', function(ev) {
                                    	    $('.js-newstudentsdiv').dialog('close');
                                    	});
                                        

                                    });//assign.htm ajax call end
                            
                        }

                    });     

        });

}


function updateGroup(thisObj) {
  
            var groupObj = {};
            var grid = $("#studentGroupEditTable");
            var rowKey = grid.getGridParam("selrow");

            if (!rowKey) {
                alert("No rows are selected");
            } else {
                var selectedIDs = grid.getGridParam("selarrrow");
                var result = [];
                for (var i = 0; i < selectedIDs.length; i++) {
                    result.push(selectedIDs[i]);
                }
                groupObj.studentIds = result;

            }
            var groupId = thisObj.data('groupid');
            groupObj.InterimGroupId = groupId;
            $.ajax({
                    url: 'updateStudentGroup.htm',
                    data: {
                        studentId: groupObj.studentIds,
                        InterimGroupId: groupId
                    },
                    dataType: 'json',
                    type: "POST"
                }).done(function(data) {
                        groupObj.groupName = $('#eidt_groupname').val();
                        $.ajax({
                                url: 'editStudentGroup.htm',
                                data: {
                                    groupName: groupObj.groupName,
                                    interimGroupId: groupId
                                },
                                dataType: 'json',
                                type: "POST",
                                success: function(data) {
                                    $.ajax({
                                            url: 'getGroups.htm',
                                            data: {
                                                groupName: '',
                                                userName: ''
                                            },
                                            dataType: 'json',
                                            type: "GET"
                                        }).done(function( data) {
                                                $('.assemble_box .row').html(
                                                        new EJS({
                                                            url: contextPath + '/js/views/groupslist.ejs'
                                                        })
                                                        .render({
                                                            groups: data
                                                        }));
                                                $(".studentgrouptableinedit").html('');
                                                $(".studentgrouptableinedit").html(
                                                        '<table id="studentGroupEditTable"><tr></td></tr></table><div id="studentGroupEditTablePager"></div>');
                                                $('#eidt_groupname').val('');
                                                $("#interimRosteredit").select2("val","");
                                                $('#interimRosterGradeedit').select2("val","");
                                                $('.js-creategroupdiv').show();
                                                $('.js-editgroupdiv').addClass('hide');
                                            });

                                }
                            });
                    });
}

function editGroup(thisObj){
	
            $('.js-creategroupdiv').hide();
            $('.js-editgroupdiv').show();
            $('.js-grouppreviewdiv').hide();
            $('.js-editgroupdiv').removeClass('hide');
            $('#eidt_groupname').val(thisObj.data('groupname'));
            $('#js-updategroup').data('groupid',thisObj.data('groupid'));
            $.ajax({
                    url: 'getStudentsByGroup.htm',
                    data: {
                        interimGroupId: thisObj.data('groupid')
                    },
                    dataType: 'json',
                    type: "GET"
                }).done(function(data) {
                        $('.studentgrouptableinedit').html(
                                '<table id = "studentGroupEditTable"><tr><td /></tr></table><div id="studentGroupEditTablePager"></div>');
                        $('#groupname').val('');
                        loadeeditgroupjqgrid(data);
                        existingstudentsingroup = [];
                        existingstudentsingroup = data;
                    });
}

function createAGroup(){
	
    var groupObj = {};

    var grid = $("#studentgroupcreateTable");
    var rowKey = grid.getGridParam("selrow");

    if (!rowKey) {
        alert("No rows are selected");
    } else {
        var selectedIDs = grid.getGridParam("selarrrow");
        var result = [];
        for (var i = 0; i < selectedIDs.length; i++) {
            result.push(selectedIDs[i]);
        }
        groupObj.studentIds = result;

    }
    groupObj.groupName = $('#groupname').val();
    if (groupObj.groupName) {
        $('.groupnameerror').hide();
        $.ajax({
                url: 'createInterimGroup.htm',
                data: {
                    studentId: groupObj.studentIds,
                    groupName: groupObj.groupName
                },
                dataType: 'json',
                type: "POST"
            }).done(function(data) {
                    $('.studentgrouptable').html('<table id = "studentTable"><tr><td /></tr></table>');
                    $('#groupname').val('');
                    $("#interimRostercreate").select2("val", "");
                    $('#interimRosterGradecreate').select2("val", "");

                    $.ajax({
                            url: 'getGroups.htm',
                            data: {
                                groupName: '',
                                userName: ''
                            },
                            dataType: 'json',
                            type: "GET"
                        }).done(function(data) {
                                $('.assemble_box .row').html(
                                        new EJS({
                                            url: contextPath + '/js/views/groupslist.ejs'
                                        }).render({
                                            groups: data
                                        }));
                                $(".studentgrouptableinedit").html('');

                            });
                });
    } else {
        $('.groupnameerror').show();

    }
}

function searchGroupByGrpAndUsrName(){
	
    var object = {};
    object.groupName = $('#js-groupname').val();
    object.userName = $('#js-username').val();
    $.ajax({
        url: 'getGroups.htm',
        data: {
            groupName: object.groupName,
            userName: object.userName
        },
        dataType: 'json',
        type: "GET"
    }).done(function(data) {
            $('.assemble_box .row').html(new EJS({
                url: contextPath + '/js/views/groupslist.ejs'
            }).render({
                groups: data
            }));

        });
	
}
function previewGroup(thisObj){
	
    $('.js-creategroupdiv').hide();
    $('.js-editgroupdiv').hide();
    $('.js-grouppreviewdiv').show();
    $('.js-grouppreviewdiv').removeClass('hide');
    $('.groupnameload').html(thisObj.data('groupname'));

    $.ajax({
            url: 'getStudentsByGroup.htm',
            data: {
                interimGroupId: thisObj.data('groupid')
            },
            dataType: 'json',
            type: "GET"
        }).done(function(data) {
                $('.studentgrouptableinpreview')
                    .html(
                        '<table id = "studentGroupPreviewTable"><tr><td /></tr></table><div id="studentGroupPreviewTablePager"></div>');
                $("#studentGroupPreviewTable").jqGrid({
                    datatype: "local",
                    data: data,
                    colModel: [{
                        label: 'id',
                        name: 'id',
                        width: 30,
                        key: true,
                        hidden: true,
                        searchoptions : {title:"ID"}
                    }, {
                        label: 'First Name',
                        name: 'legalFirstName',
                        width: 80,
                        align: 'center',
                        searchoptions : {title:"First Name"}
                    }, {
                        label: 'Last Name',
                        name: 'legalLastName',
                        width: 90,
                        align: 'center',
                        searchoptions : {title:"Last Name"}
                    }],
                    loadonce: true,
                    viewrecords: true,
                    width: 430,
                    height: 200,
                    rowNum: 10,
                    rowList: [10, 20, 30, 40, 60, 90 ],
                    page: 1,
                    rownumbers: true,
                    rownumWidth: 50,
                    altRows: true,
                    hoverrows: true,
                    altClass: 'altrow',
                    scrollOffset: 0,
                    scrollerbar: false,
                    pager: "#studentGroupPreviewTablePager"
                });
                $("#studentGroupPreviewTable").closest('.ui-jqgrid-bdiv').width($("#studentGroupPreviewTable").closest('.ui-jqgrid-bdiv').width() + 2);
                $('.ui-jqgrid-hbox').css({"padding-right": "0"});
                $('.ui-jqgrid-bdiv').css({'overflow-x': 'hidden'});
                $("#studentGroupPreviewTable").jqGrid('filterToolbar', {
                        stringResult: true,
                        searchOnEnter: false,
                        defaultSearch: "cn"
                    });
                $('tr.ui-search-toolbar input').css('width', '100%');
                $('.ui-jqgrid .ui-jqgrid-btable').css('table-layout', 'auto');
                $('#cb_studentGroupEditTable').trigger('click');
            });
	
}

function studentSearchToManageGroups(){
	
    var rosterId = $('#interimRostercreate').val();
    var gradeCourseId = $('#interimRosterGradecreate').val();
    if (rosterId == 'select' && gradeCourseId == 'select') {

    } else {
        $.ajax({
                url: "getstudentGridNew.htm",
                data: {
                    rosterId: rosterId,
                    gradeCourseId: gradeCourseId
                },
                dataType: 'json',
                type: "GET"
            }).done(function(students) {
                idsOfSelectedRows = []
                var $grid = $("#studentgroupcreateTable"),
                    updateIdsOfSelectedRows = function(id, isSelected) {
                        var index = $.inArray(id,
                            idsOfSelectedRows);
                        if (!isSelected && index >= 0) {
                            idsOfSelectedRows.splice(index,
                                1); // remove id from
                            // the list
                        } else if (index < 0) {
                            idsOfSelectedRows.push(id);
                        }
                    };
                $('#studentgroupcreateTable').jqGrid('clearGridData');
                $('#studentgroupcreateTable').jqGrid('setGridParam', {
                        data: students
                    });
                $('#studentgroupcreateTable').trigger('reloadGrid');

                $("#studentgroupcreateTable").jqGrid({
                        datatype: "local",
                        data: students,
                        colModel: [{
                            label: 'id',
                            name: 'id',
                            width: 30,
                            key: true,
                            hidden: true,
                            searchoptions : {title:"ID"}
                        }, {
                            label: 'First Name',
                            name: 'legalFirstName',
                            width: 75,
                            align: 'center',
                            searchoptions : {title:"First Name"}
                        }, {
                            label: 'Last Name',
                            name: 'legalLastName',
                            width: 90,
                            align: 'center',
                            searchoptions : {title:"Last Name"}
                        }],
                        viewrecords: true,
                        width: 430,
                        height: 200,
                        rowNum: 10,
                        rowList: [10, 20, 30, 40, 60, 90 ],
                        page: 1,
                        rownumbers: true,
                        rownumWidth: 50,
                        multiselect: true,
                        scrollOffset: 0,
                        loadonce: false,
                        scrollerbar: false,

                        onSelectRow: updateIdsOfSelectedRows,
                        onSelectAll: function(
                            aRowids,
                            isSelected) {
                            var i, count, id;
                            for (i = 0,count = aRowids.length; i < count; i++) {
                                id = aRowids[i];
                                updateIdsOfSelectedRows(id,isSelected);
                            }
                        },
                        loadComplete: function() {
                            var $this = $(this),i, count;
                            for (i = 0,count = idsOfSelectedRows.length; i < count; i++) {
                                $this.jqGrid('setSelection',idsOfSelectedRows[i],false);
                            }
                            
                            var ids = $(this).jqGrid('getDataIDs');         
               	         	var tableid=$(this).attr('id');      
               	            for(var i=0;i<ids.length;i++)
               	            {         
               	                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'legalFirstName') +' '+$(this).getCell(ids[i], 'legalLastName')+ ' Check Box');
               	            }
               	             $('#cb_'+tableid).attr('title','User Grid All Check Box');
               	             $('#cb_'+tableid).removeAttr('aria-checked');
               	             $('#jqg_'+tableid+'_'+ids[i]).removeAttr('aria-checked');
                        },
                        pager: "#studentgroupcreateTablePager"
                    });
                $("#studentgroupcreateTable").closest('.ui-jqgrid-bdiv').width($("#studentgroupcreateTable").closest('.ui-jqgrid-bdiv').width() + 2);
                $('.ui-jqgrid-hbox').css({"padding-right": "0"});
                $('.ui-jqgrid-bdiv').css({'overflow-x': 'hidden'});
                $("#studentgroupcreateTable").jqGrid('filterToolbar', {
                        stringResult: true,
                        searchOnEnter: false,
                        defaultSearch: "cn"
                    });
                $('tr.ui-search-toolbar input').css('width', '100%');
                $('.ui-jqgrid .ui-jqgrid-btable').css('table-layout', 'auto');
                var grid = $("#studentgroupcreateTable");
                var rowKey = grid.find('tr.jqgrow');
            });
    }
	
}

function studentGroupEditSearch(){
	
        var rosterId = $('#interimRosteredit').val();
        var gradeCourseId = $('#interimRosterGradeedit').val();

        if (rosterId == 'select' && gradeCourseId == 'select') {
        } else {
            $.ajax({
                    url: "getstudentGridNew.htm",
                    data: {
                        rosterId: rosterId,
                        gradeCourseId: gradeCourseId
                    },
                    dataType: 'json',
                    type: "GET"
                }).done(function(students) {

                        idsOfSelectedRows = []
                        var $grid = $("#addingnewstudentsgroupTable"),
                            updateIdsOfSelectedRows = function(id, isSelected) {
                                var index = $.inArray(id,idsOfSelectedRows);
                                if (!isSelected && index >= 0) {
                                    idsOfSelectedRows.splice(index,1); // remove id from
                                    // the list
                                } else if (index < 0) {
                                    idsOfSelectedRows.push(id);
                                }
                            };
                        $('#addingnewstudentsgroupTable').jqGrid('clearGridData');
                        $('#addingnewstudentsgroupTable').jqGrid('setGridParam', {
                                data: students
                            });
                        $('#addingnewstudentsgroupTable').trigger('reloadGrid');
                        newstudentsforselection = [];
                        newstudentsforselection = students;
                        $("#addingnewstudentsgroupTable")
                            .jqGrid({
                                datatype: "local",
                                data: students,
                                colModel: [{
                                    label: 'id',
                                    name: 'id',
                                    width: 30,
                                    key: true,
                                    hidden: true,
                                    searchoptions : {title:"ID"}
                                }, {
                                    label: 'First Name',
                                    name: 'legalFirstName',
                                    width: 75,
                                    align: 'center',
                                    searchoptions : {title:"First Name"}
                                }, {
                                    label: 'Last Name',
                                    name: 'legalLastName',
                                    width: 90,
                                    align: 'center',
                                    searchoptions : {title:"Last Name"}
                                }],
                                width: 500,
                                height: 200,
                                rowNum: 10,
                                rowList: [10, 20, 30, 40, 60, 90 ],
                                page: 1,
                                rownumbers: true,
                                rownumWidth: 50,
                                multiselect: true,
                                scrollOffset: 0,
                                scrollerbar: false,
                                altRows: true,
                                hoverrows: true,
                                altClass: 'altrow',

                                onSelectRow: updateIdsOfSelectedRows,
                                onSelectAll: function(
                                    aRowids,
                                    isSelected) {
                                    var i, count, id;
                                    for (i = 0,count = aRowids.length; i < count; i++) {
                                        id = aRowids[i];
                                        updateIdsOfSelectedRows( id, isSelected);
                                    }
                                },
                                loadComplete: function() {
                                    var $this = $(this),
                                        i, count;
                                    for (i = 0,count = idsOfSelectedRows.length; i < count; i++) {
                                        $this.jqGrid('setSelection',idsOfSelectedRows[i],false);
                                    }
                                    
                                    var ids = $(this).jqGrid('getDataIDs');         
                       	         	var tableid=$(this).attr('id');      
                       	            for(var i=0;i<ids.length;i++)
                       	            {         
                       	                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'legalFirstName') +' '+$(this).getCell(ids[i], 'legalLastName')+ ' Check Box');
                       	            }
                       	             $('#jqg_'+tableid+'_'+ids[i]).removeAttr('aria-checked');	
                       	             $('#cb_'+tableid).attr('title','User Grid All Check Box');
                                },
                                pager: "#addingnewstudentsgroupTablePager"
                            });
                        $("#addingnewstudentsgroupTable").closest('.ui-jqgrid-bdiv').width($("#addingnewstudentsgroupTable").closest('.ui-jqgrid-bdiv').width() + 2);
                        $('.ui-jqgrid-hbox').css({"padding-right": "0"});
                        $('.ui-jqgrid-bdiv').css({'overflow-x': 'hidden'});
                        $("#addingnewstudentsgroupTable")
                            .jqGrid('filterToolbar', {
                                stringResult: true,
                                searchOnEnter: false,
                                defaultSearch: "cn"
                            });
                        $('tr.ui-search-toolbar input').css('width', '100%');
                        var grid = $("#addingnewstudentsgroupTable");
                        var rowKey = grid.find('tr.jqgrow');
                        $('.js-newstudentsdiv')
                            .dialog({
                                autoOpen: false,
                                modal: true,                               
                                maxWidth: 600,
                                maxHeight: 500,
                                width: 600,
                                height: 500,

                                title: "Add Students To Group",
                                create: function(
                                    event, ui) {
                                    var widget = $(this).dialog( "widget");
                                    $(".ui-dialog-titlebar-close span",widget).prop('id','editUserDialogClose');
                                },

                                close: function(
                                    ev, ui) {

                                }
                            }).dialog('open');
                        $('#addingnewstudentsgroupTable').removeAttr('style');
                    });
        }
}

$("#manageTests").on('click' , function() {
    renderMyTests();
});

function renderMyTests() {
    $('#interimTabs').html(new EJS({
        url: contextPath + '/js/views/interimtests.ejs'
    }).render({
        interimTests: interimTestsArray

    }));

    function actionCol() {
        return '<a href="#"><i class="fa fa-edit fa-lg" id="editButton"></i></a><a href="#"><i class="fa fa-trash fa-lg"></i></a>';
    }
    var getColumnIndexByName = function(grid, columnName) {
        var cm = grid.jqGrid('getGridParam', 'colModel'),
            i = 0,
            l = cm.length;
        for (; i < l; i += 1) {
            if (cm[i].name === columnName) {
                return i; // return the index
            }
        }
        return -1;
    };

    jqGridConstruction();
  }


$('.js-moredec').on('click',
    function(ev) {
        ev.preventDefault();
        $('#js-fulldescription').html($(this).data('dec'));
        $('#full-group-description').dialog({
            autoOpen: false,
            modal: true, 
            width: 500,
            height: 320,
            title: "",
            create: function(event, ui) {
                var widget = $(this).dialog("widget");
                $(".ui-dialog-titlebar-close span", widget).prop('id', 'editUserDialogClose');
            },

            close: function(ev, ui) {

            }
        }).dialog('open');
    });


function Interimgroups() {
    var groupMap = {};
    var groupRoleMap = {};
    var groupsNameMap = {};
    $.ajax({
        url: 'getGroupsOrgTypeMap.htm',
        type: 'GET',
        dataType: 'json',
        async: false
    }).done(function(data) {
            if (data != undefined && data != null) {
                groupMap = data.groupsOrgMap;
                groupRoleMap = data.groupsRoleTypeMap;
                groupsNameMap = data.groupsNameMap;
                groupsCodeMap = data.groupsCodeMap;
            }
        });
    var currentGroupId = $('#hiddenCurrentGroupsId').val();
    var orgTypeCode = groupMap[currentGroupId];
    var currentGroupCode = groupsCodeMap[currentGroupId];
    
    if (orgTypeCode != 7) {
        $('#interimRosterContainer').hide();
        $('#rosterSubmitConatiner').hide();
        $('#assignRosterConatiner').hide();
        // $('#studentGroup').hide();
        $('#assignGradeConatiner').show();
        $('#interimGrade').hide();
        $('#assignSubjectConatiner').hide();
        $('#assignConatiner').hide();
        $("#js-formsearch").hide();
        $('#autoAssignGrade').on('click', function(ev) {
            $('#interimRosterContainer').hide();
            $('#rosterSubmitConatiner').hide();
            // $('#studentGroup').hide();
            $('#js-individualselect').hide();
            $('#movingToSchedule').hide();
            $("#js-formsearch").hide();
            $('#interimGrade').show();
            $('#assignConatiner').show();
            $('#assignSubjectConatiner').show();
            $("#existinggroup").select2("val", "");
            $("#interimRoster").select2("val", "");
            $("#interimRosterGrade").select2("val", "");
            $('#js-individualselect').hide();
        });
        if(currentGroupCode == 'DTC' && currentAssessmentProgramName == 'PLTW'){
        	$('#assignGradeConatiner').hide();
        }
    } else if (orgTypeCode == 7) {
        $('#interimRosterContainer').hide();
        $('#rosterSubmitConatiner').hide();
        $('#assignGradeConatiner').hide();
        $('#interimGrade').hide();
        // $('#studentGroup').hide();
        $('#assignRosterConatiner').show();
        $('#assignConatiner').hide();
        $('#assignSubjectConatiner').hide();
        $("#js-formsearch").hide();

        $('#autoAssignRoster').on('click', function(ev) {
            $('#interimRosterContainer').show();
            $('#rosterSubmitConatiner').show();
            $('#interimGrade').hide();
            // $('#studentGroup').hide();
            $('#js-individualselect').hide();
            $('#movingToSchedule').hide();
            $('#assignSubjectConatiner').hide();
            $('#interimGrade').hide();
            $("#js-formsearch").hide();
            $("#existinggroup").select2("val", "");
            $("#interimRoster").select2("val", "");
            $("#interimRosterGrade").select2("val", "");
            $('#js-individualselect').hide();
        });
    }

}

function createInterimTest(interimTestId) {
	
    var testName = $('#interimTestName').val();
    var testDescription = $("#description").val();
    var interimtestGrade = $('#interimTestGrade').val();
    var interimTestSubject = $('#interimTestSubject').val();

    interimTest.testIds = [];
    $.each($('#ptag').find('a'), function(i, val) {
        if ($(val).data('id')) {
            interimTest.testIds.push($(val).data('id'));
        }
    });
    if (testDescription.length >= 299) {
        alert("description can not be more than 299 characters.");
    } else if (!$('#interimTestName').val()) {
        $('.testname-error').removeClass('hide');
    } else {
        $('.testname-error').addClass('hide');
        if (!interimTestId) {
            if (((interimtestGrade && interimtestGrade != 'Select') || (currentAssessmentProgramName == 'PLTW')) && (interimTestSubject && interimTestSubject != 'Select')) {
                var nameCheckFlag;
                $.ajax({
                        url: 'getTestNameUnique.htm',
                        data: {
                            'testName': $('#interimTestName').val()
                        },
                        dataType: 'json',
                        type: "GET"
                    }).done(function(data) {
                            nameCheckFlag = data.Status;
                            var isDisabled = $('#interimTestName').is(':disabled');
                            if (nameCheckFlag == "false") {
                                var copied = $("#radio-class").val();
                                if (copied == true) {
                                    alert("You cannot copy the test, You have either copied this test already or have built a test with similar name already.");
                                } else {
                                    alert("You cannot use the same test name for more than one test. Please choose a different name.");
                                }
                            } else {
                                if (interimTest.testIds && interimTest.testIds.length > 0) {
                                    $('#loadingMsgPop').dialog({
                                        resizable: false,
                                        modal: true,
                                        width: 1000,
                                        height: 200,
                                        title: 'We are transferring you to Assign Students Page for ' + $("#interimTestName").val() + ' Please Wait..',
                                        open: function(event, ui) {
                                            $(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
                                        }


                                    }).dialog('open');
                                    $.ajax({
                                            url: 'saveInterimTest.htm',
                                            data: {
                                                "testIds[]": interimTest.testIds,
                                                name: testName,
                                                description: testDescription,
                                                gradeCourseId: $('#interimTestGrade').val(),
                                                contentAreaId: $('#interimTestSubject').val(),
                                                isTestCopied: $("#radio-class").val()
                                            },
                                            dataType: 'JSON',
                                            type: 'POST'
                                        }).done(function(
                                                response) {
                                            // Need
                                            // to
                                            // Redirect
                                            // to
                                            // the
                                            // Other
                                            // Dashboard.
                                            // $btn.button('reset')
                                            if (response.id != null && response.id != '' && response.id != undefined && response.id > 0) {
                                                testAssignObject = {};
                                                $('#assignNow').attr('data-itid', response.testTestId);
                                                $('#assignNow').attr('data-testcollecionid', response.testCollectionId);
                                                $('.editInterimClass').attr('data-itid',response.id);
                                                testAssignObject.interimTestId = response.testTestId;
                                                assignToStudents(response);
                                                setTimeout("$('#loadingMsgPop').dialog('close')", 4000);
                                            } else {
                                                $('#create_interim_test_error').show();
                                                setTimeout(function() {
                                                	$('#create_interim_test_error').hide();
                                                }, 4000);
                                                setTimeout("$('#loadingMsgPop').dialog('close')", 500);
                                            }
                                        }).fail(function(jqXHR,textStatus,errorThrown) {
                                                console.log(errorThrown);
                                                // $btn.button('reset')
                                                console.log('Message Saved Error Occured');
                                                setTimeout("$('#loadingMsgPop').dialog('close')", 1000);
                                            });
                                } else {
                                    alert('Please select at least one test to create interim test.');
                                }
                            }
                        });

            } else {
                if ((!$('#interimTestGrade').val() || $(
                        '#interimTestGrade').val() == 'Select') && (!$('#interimTestSubject').val() || $(
                        '#interimTestSubject').val() == 'Select')) {
                    alert('Please Select Subject And Grade');
                } else if ((!$('#interimTestGrade').val() || $(
                        '#interimTestGrade').val() == 'Select')) {
                		 alert('Please Select Grade');
                } else if (!$('#interimTestSubject').val() && $('#interimTestSubject').val() != 'Select') {
                    alert('Please Select Subject');
                }
            }
        } else {
            var nameCheckFlag;
            $.ajax({
                    url: 'getTestNameUnique.htm',
                    data: {
                        'testName': $('#interimTestName').val()
                    },
                    dataType: 'json',
                    type: "GET"
                }).done(function(data) {
                    nameCheckFlag = data.Status;
                    var isDisabled = $('#interimTestName').is(':disabled');

                    if (nameCheckFlag == "false") {
                        var copied = $("#radio-class").val();
                        if (copied == true) {
                            alert("You cannot copy the test, You have either copied this test already or have built a test with similar name already.");
                        } else {
                            alert("You cannot use the same test name for more than one test. Please choose a different name.");
                        }
                    } else {
                        var testids = $('#ptag a');
                        interimTest.testIds = [];
                        if (testids.length > 0) {
                            for (var i = 0; i < testids.length; i++) {
                                interimTest.testIds.push($(testids[i]).data('id'));
                            }
                        } else if (testids.length == 0) {
                            interimTest.testIds = [];
                        }
                        testAssignObject = {};
                        testAssignObject.interimTestId = $("#createTest").attr('data-interimid');
                        var testSessionId = $("#createTest").attr('testSessionId');

                        $
                            .ajax({
                                url: 'updateInterimTest.htm',
                                data: {
                                    "testIds[]": interimTest.testIds,
                                    name: testName,
                                    description: testDescription,
                                    testSessionId: testSessionIdInterim,
                                    interimTestId: $("#createTest").attr('data-interimid')
                                },
                                dataType: 'JSON',
                                type: 'POST'
                            }).done(function(response) {
                                if (response.id != null && response.id != '' && response.id != undefined && response.id > 0) {
                                    // $btn.button('reset')
                                    var itId = response.id;
                                    var i = interimTestsArray
                                        .indexOf(itId);
                                    interimTestsArray.splice(i,1);

                                    testAssignObject = {};
                                    $('#assignNow').attr('data-itid',response.testTestId);
                                    $('#assignNow').attr('data-testsessionid', testSessionIdInterim);
                                    $('#assignNow').attr('data-testcollecionid', response.testCollectionId);
                                    testSessionIdInterim = null;
                                    $('.editInterimClass').attr('data-itid',response.id);
                                    testAssignObject.interimTestId = response.testTestId;
                                    assignToStudents(response);
                                    setTimeout("$('#loadingMsgPop').dialog('close')", 1000);
                                } else {
                                    $('#update_interim_test_error').show();
                                    setTimeout(function() {
                                        $('#update_interim_test_error').hide();
                                    }, 4000);
                                    setTimeout("$('#loadingMsgPop').dialog('close')", 500);
                                }
                            })
                            .fail(function(
                                    jqXHR,
                                    textStatus,
                                    errorThrown) {
                                    console.log(errorThrown);
                                    console.log('Message Saved Error Occured');
                                    setTimeout("$('#loadingMsgPop').dialog('close')", 1000);
                                });
                    }
                });

        }
    }

}

function assignToStudents(response) {
	
    if ($('.radio-class').val() == 'mini') {
        $('.editInterimClass').html('Change Selection');
    }
    $("#assembleScreen").hide();
    // called this method for removing confirm screen after saving the interim
    // test
    assignTest();
    $('#testNameConfirm').html(response.name);
    $("#nwTestId").append('<p>:  ' + response.id + '</p>');
    $("#createdTest").append('<p>:  ' + response.name + '</p>');
    $("#t").append(response.name);
    $("#descriptionConfirm").html(response.description);
    $('.editInterimClass').attr('data-id', response.id);
    $('.preview-test').attr('data-id', response.testTestId);
    $('.preview-test').attr('data-collectionid', response.testCollectionId);
    $('.preview-test').attr('data-name', response.name);
    var miniTestIdsString = '';
    for (var i in response.miniTestIds) {
        if (miniTestIdsString == '') {
            miniTestIdsString = response.miniTestIds[i];
        } else {
            miniTestIdsString = miniTestIdsString + ',' + response.miniTestIds[i];
        }
    }
    var dateNew = new Date();
    var date_str = ('0' + dateNew.getMonth()).substr(-2, 2) + '/' + ('0' + dateNew.getDate()).substr(-2, 2) + '/' + ('0' + dateNew.getFullYear()).substr(-2, 2);
    response.dateCreated = date_str;
    response.miniTestIdsString = miniTestIdsString;
    interimTestsArray.push(response);
}

$('#assignLater').on('click', function(ev) {
    ev.preventDefault();
    $('#interimTabs').html(new EJS({
        url: contextPath + '/js/views/interimtests.ejs'
    }).render({
        interimTests: interimTestsArray

    }));
    jqGridConstruction();
});

$('#myonoffswitch').on('click', function(ev) {
    ev.preventDefault();
});

function assignTest() {
	
    var dropdownObject;
    var testName = $('#interimTestName').val();
    var testDescription = $("#description").val();
    var itid = $('#assignNow').attr('data-itid');
    var testCollectionId = $('#assignNow').attr('data-testcollecionid');

    var testsessionid = $('#assignNow').attr('data-testsessionid');
    var presentdate = new Date();
    var h = presentdate.getHours(),
        m = presentdate.getMinutes();
    var timeforcondition = (h > 12) ? (h - 12 + ':' + m + ' PM') : (h + ':' + m + ' AM');
    var premonth = presentdate.getMonth() + 1;
    var dateforcondition = premonth + '/' + presentdate.getDate() + '/' + presentdate.getFullYear();

    if (!groupsArray || !groupsArray.length > 0) {
        groupsArray = [];
    }
    $.ajax({
        url: 'assignView.htm',
        data: {interimTestId:itid},
        dataType: 'json',
        type: "GET"
    }).done(function(dropdownObject) {
        dropdownObject.gradename.sort(function(a, b) {
            var ax = [],
                bx = [];

            a.name.replace(/(\d+)|(\D+)/g, function(_, $1, $2) {
                ax.push([$1 || Infinity, $2 || ""])
            });
            b.name.replace(/(\d+)|(\D+)/g, function(_, $1, $2) {
                bx.push([$1 || Infinity, $2 || ""])
            });

            while (ax.length && bx.length) {
                var an = ax.shift();
                var bn = bx.shift();
                var nn = (an[0] - bn[0]) || an[1].localeCompare(bn[1]);
                if (nn)
                    return nn;
            }

            return ax.length - bx.length;
        })
        // dropdownObject.gradename=_.sortBy(dropdownObject.gradename,
        // 'name');
    groupsArray = _.sortBy(groupsArray, 'groupName');
    dropdownObjectArray = dropdownObject;

    if (testsessionid) {
        $.ajax({
            url: 'getTotalTestSessionDetails.htm',
            data: {
                testSessionId: testsessionid
            },
            dataType: 'json',
            type: "GET"
        }).done(function(testsessiondata) {

            var startDate = new Date(testsessiondata.windowEffectiveDateString);
            var endDate = new Date(testsessiondata.windowExpiryDateString);
            var startTime = new Date(testsessiondata.windowStartTimeString);
            var endTime = new Date(testsessiondata.windowEndTimeString);
            var smonth = startDate.getMonth() + 1;
            var emonth = endDate.getMonth() + 1;
            var startdateformat = smonth + '/' + startDate.getDate() + '/' + startDate.getFullYear();
            var enddateformat = emonth + '/' + endDate.getDate() + '/' + endDate.getFullYear();
            var starttimeStringStartdate = Date.parse(startdateformat + ' ' + startTime);
            var endtimestringstartDate = Date
                .parse(startdateformat + ' ' + endTime);
            var startTimeStringEndDate = Date.parse(enddateformat + ' ' + startTime);
            var endTimeStringEndDate = Date
                .parse(enddateformat + ' ' + endTime);
            var currentTimeStringEndDate = Date.parse(dateforcondition + ' ' + timeforcondition);
            $('#interimTabs').html(new EJS({
                url: contextPath + '/js/views/manage.ejs'
            }).render({
                dropdownObject: dropdownObjectArray,
                testName: testName,
                testDescription: testDescription,
                students: studentsArray,
                groups: groupsArray,
                itid:itid,
                testCollectionId : testCollectionId,
                currentAssessmentProgramName : currentAssessmentProgramName
            }));
            $("#existinggroup").select2({
                data: groupsArray.groupName,
                allowClear: true,
                placeholder: "Select Group"
            });
            $('.select2-container input').css('height', '40px !important');

            $("#interimRoster, #interimAutoRoster").select2({
                data: dropdownObjectArray.rostername.name,
                placeholder: function() {
                    $(this).data('placeholder');
                },
                allowClear: true
            });

            $("#interimRosterGrade, #interimRosterAutoGrade").select2({
                data: dropdownObjectArray.gradename.name,
                placeholder: function() {
                    $(this).data('placeholder');
                },
                allowClear: true
            });

            $("#interimAssignSubject").select2({
                data: dropdownObjectArray.subjectname.name,
                placeholder: function() {
                    $(this).data('placeholder');
                },
                allowClear: true
            });
            $.ajax({
                    url: 'getStudentByInterimTestSession.htm',
                    data: {
                        'testSessionId': testsessionid
                    },
                    dataType: 'json',
                    type: "GET"
                }).done(function(studentsArray) {
                    assignedstudentsfortestsession = studentsArray;

                    if ((Date.parse(startdateformat) > Date
                            .parse(dateforcondition)) || (Date
                            .parse(startdateformat) == Date
                            .parse(dateforcondition) && starttimeStringStartdate > currentTimeStringEndDate)) {
                        testAssignObject.testSessionId = testsessionid;
                        jqGridConstructionForActionswithData(
                            testsessionid,
                            studentsArray, itid);
                    } else if ((Date
                            .parse(enddateformat) > Date
                            .parse(dateforcondition)) || (Date
                            .parse(enddateformat) == Date
                            .parse(dateforcondition) && endTimeStringEndDate > currentTimeStringEndDate)) {
                        var testStatus = 'started';
                        testAssignObject.testSessionId = testsessionid;
                        jqGridConstructionForNoActions(
                            testStatus,
                            testsessionid,
                            studentsArray);
                    } else {
                        var testStatus = 'ended';
                        jqGridConstructionForNoActions(
                            testStatus,
                            testsessionid,
                            studentsArray);
                    }
                    $('#js-showSchdule').on('click',function(ev) {
                        ev.preventDefault();
                        $("#manageScreen").hide();
                        $.ajax({
                                url: "getTestSessionDetailsByTestSessionId.htm",
                                data: {
                                    testSessionId: $(this).attr('data-testsessionid')
                                },
                                dataType: 'json',
                                type: "GET"
                            }).done(function(data) {
                                    // removed schedule page date and time code
                                    // in the part of F560
                                    $('#scheduleButton').hide();
                                    $('#backtomanage').hide();
                                    $('#backToInterim')[0].click();
                                    $('.backtointerimhome').show();
                                });
                    });
                    
                    $('#PreviewLink').on('click', function(ev) {
                    	var interimTestId = $(this).attr('data-itid');
                    	var testCollectionId = $(this).attr('data-testcollectionid');
                        previrewInterim(ev,interimTestId,testCollectionId);
                    });
                });
        });
    } else {
        $('#interimTabs').html(new EJS({
            url: contextPath + '/js/views/manage.ejs'
        }).render({
            dropdownObject: dropdownObjectArray,
            testName: testName,
            testDescription: testDescription,
            students: studentsArray,
            groups: groupsArray,
            itid:itid,
            testCollectionId : testCollectionId,
            currentAssessmentProgramName : currentAssessmentProgramName
        }));
        $("#existinggroup").select2({
            data: groupsArray.groupName,
            allowClear: true,
            placeholder: "Select Group"
        });
        $('.select2-container input').css('height', '40px !important');

        $("#interimRoster, #interimAutoRoster").select2({
            data: dropdownObjectArray.rostername.name,
            placeholder: function() {
                $(this).data('placeholder');
            },
            allowClear: true
        });

        $("#interimRosterGrade, #interimRosterAutoGrade").select2({
            data: dropdownObjectArray.gradename.name,
            placeholder: function() {
                $(this).data('placeholder');
            },
            allowClear: true
        });

        $("#interimAssignSubject").select2({
            data: dropdownObjectArray.subjectname.name,
            placeholder: function() {
                $(this).data('placeholder');
            },
            allowClear: true
        });
    }
    

    $('#PreviewLink').attr('data-itid', itid);
    $('#PreviewLink').attr('data-testcollectionid', testCollectionId);
    $("#scheduleScreen").hide();
    $('#scheduleConfirm').hide();
    Interimgroups();
    
    $('#autoAssignStudents').on('click', function(ev) {
    	ev.preventDefault();
    	autoAssignStudents();
    });
    
    $('#autoAssignGrade').on('click', function(ev) {
    	
        $('#interimRosterContainer').hide();
        $('#rosterSubmitConatiner').hide();
        // $('#studentGroup').hide();
        $('#js-individualselect').hide();
        $('#movingToSchedule').hide();
        $("#js-formsearch").hide();
        $('#interimGrade').show();
        $('#assignConatiner').show();
        $('#assignSubjectConatiner').show();
        $("#existinggroup").select2("val", "");
        $("#interimRoster").select2("val", "");
        $("#interimRosterGrade").select2("val", "");
        $('#js-individualselect').hide();
    });
    
    $('#PreviewLink').on('click', function(ev) {
    	var interimTestId = $(this).attr('data-itid');
    	var testCollectionId = $(this).attr('data-testcollectionid');
        previrewInterim(ev,interimTestId,testCollectionId);
    });
        
	    $('#studentSearch').on('click',function(ev) {
	    	ev.preventDefault();
	    	studentSearch($(this));
	     });
	    
	    $('#assign').on('click', function(ev) {
	    	ev.preventDefault();
	    	autoAssign();
	
	    });
	    
	    $('#rosterSubmit').on('click', function(ev) {
	    	ev.preventDefault();
	    	submitRoster();
	    });
    
	    $('#existinggroup').on('change',function(ev){
	    	ev.preventDefault();
	    	selectStudentsOnGrid();
	    });
    
   
    	$('#movingToSchedule').on('click',function(ev) {
    		ev.preventDefault();
    		assignSelectedStudents();
    	 });
    	
    	$('#js-savegroup').on('click', function(ev) {
    	    ev.preventDefault();
    	    saveInterimGroup();
    	});
    	
    	$('#js-cancelgroup').on('click', function(ev) {
    	    $('.js-newstudentsdiv').dialog('close');
    	});
    	
        $('#autoAssignRoster').on('click', function(ev) {
        	ev.preventDefault();
        	autoAssignRoster();
        });
});
}

function autoAssignRoster(){
	
    $('#interimRosterContainer').show();
    $('#rosterSubmitConatiner').show();
    $('#interimGrade').hide();
    // $('#studentGroup').hide();
    $('#js-individualselect').hide();
    $('#movingToSchedule').hide();
    $('#assignSubjectConatiner').hide();
    $('#interimGrade').hide();
    $("#js-formsearch").hide();
    $("#existinggroup").select2("val", "");
    $("#interimRoster").select2("val", "");
    $("#interimRosterGrade").select2("val", "");
    $('#js-individualselect').hide();
}

function submitRoster(){
	
    var obj = {};
    obj.rosterIds = $('#interimAutoRoster').val();
    obj.interimTestId = testAssignObject.interimTestId;
    if (obj.rosterIds != null) {
        $.ajax({
            url: 'autoAssignInterim.htm',
            type: 'POST',
            dataType: 'json',
            data: obj
        }).done(function(data) {

                $('#assignRosterSucessMsgDailog').html(
                    $('#testNameSideTab').text() + ' Testname has been auto assigned to the selected Roster.');
                $('#assignRosterSucessMsgDailog').dialog({
                    resizable: false,
                    modal: true,
                    width: 500,
                    height: 200,
                    title: '',
                    buttons: {
                        Ok: function(ev) {
                        	$(this).dialog("close");
                            return false;
                        }
                    }
                }).dialog('open');
                renderMyTests();
            });
    }
}

function saveInterimGroup(){
	
    if ($('#groupname').val()) {
        $('#groupnameerror').hide();
        $.ajax({
            url: 'createInterimGroup.htm',
            data: {
                studentId: testAssignObject.studentsIds,
                groupName: $('#groupname').val()
            },
            dataType: 'json',
            type: "POST"
        }).done(function(data) {
                savingTestSessionDetails();
                $("#manageScreen").hide();
            });
    } else {
        $('#groupnameerror').show();
    }
}

function assignSelectedStudents(){
    if ($('#existinggroup').length > 0 && $('#existinggroup').val() != '') {
        var grid = $("#groupstudenttable");
        var rowKey = grid.getGridParam("selarrrow");
        if (!rowKey)
            alert("No rows are selected");
        else {
            var result = [];

            testAssignObject.studentsIds = rowKey;

            $('#js-start-date,#js-end-date').datepicker({
                minDate: new Date(),
                maxDate: '+2y'
            });
            $('#js-start-time,#js-end-time').timepicker({
                'timeFormat': 'h:i A',
                'step': 15
            });
            $("#manageScreen").hide();
            $('#scheduleConfirm').hide();
            savingTestSessionDetails();
        }
    } else {
        var grid = $("#studentTable");
        var rowKey = grid.getGridParam("selrow");

        if (!rowKey)
            alert("No rows are selected");
        else {
            if (idsOfSelectedRows && idsOfSelectedRows.length > 0) {
                var selectedIDs = idsOfSelectedRows;
            } else {
                var selectedIDs = grid.getGridParam("selarrrow");
            }
            var result = [];
            for (var i = 0; i < selectedIDs.length; i++) {
                result.push(selectedIDs[i]);
            }
            testAssignObject.studentsIds = result;
            $('#group-create').dialog({
                autoOpen: false,
                modal: true,
                width: 500,
                height: 320,
                title: "Group Creation",
                create: function(event, ui) {
                    var widget = $(this).dialog("widget");
                    $(".ui-dialog-titlebar-close span", widget).prop('id', 'editUserDialogClose');
                },

                close: function(ev, ui) {

                }
            }).dialog('open');     
        }
    }
}

function autoAssign(){
    var obj = {};
    obj.gradeCourseIds = $('#interimRosterAutoGrade').val();
    obj.contentAreaIds = $('#interimAssignSubject').val();

    if (obj.gradeCourseIds == null || obj.contentAreaIds == null) {
        $('#validationError').dialog({
            resizable: false,
            modal: true,
            width: 500,
            height: 100,
            title: 'Required'
        }).dialog('open');
    } else {

        obj.interimTestId = testAssignObject.interimTestId;
        if (obj.gradeCourseIds != null && obj.contentAreaIds != null) {
            $.ajax({
                url: 'autoAssignInterim.htm',
                type: 'POST',
                dataType: 'json',
                data: obj
            }).done(function(data) {

                    $('#assignRosterSucessMsgDailog').html(
                        $('#testNameSideTab').text() + ' has been auto assigned to selected Students.');
                    $('#assignRosterSucessMsgDailog').dialog({
                        resizable: false,
                        modal: true,
                        width: 500,
                        height: 200,
                        title: '',
                        buttons: {
                            Ok: function(ev) {
                                $(this).dialog("close");
                                return false;
                            }
                        }
                    }).dialog('open');
                    $('.ui-dialog').removeAttr("role");
                    renderMyTests();

                });
        }
    }
}
function autoAssignStudents(){
	
    $("#js-formsearch").show();
    $("#interimRosterContainer").hide();
    $("#rosterSubmitConatiner").hide();
    $('#interimGrade').hide();
    $('#assignSubjectConatiner').hide();
    $('#assignConatiner').hide();
    $("#interimRosterAutoGrade").select2("val", "");
    $("#interimAssignSubject").select2("val", "");
    $("#interimAutoRoster").select2("val", "");
}

function studentSearch(thisObj){
	
    var rosterId = $("#interimRoster").val();
    var contentAreaId = $("#interimRosterSubject").val();
    var gradeCourseId = $("#interimRosterGrade").val();
    var dropdownObject;
    var testName = $('#interimTestName').val();
    var testDescription = $("#description").val();
    $("#scheduleScreen").hide();
    $('#scheduleConfirm').hide();                                               
    $('#interimGrade').hide();
    // $('#studentGroup').hide();
    $('#assignGrade').hide();
    $('#rosterSubmitConatiner').hide();
    $('#interimRosterContainer').hide();
    $('#assignSubjectConatiner').hide();
    $('#assignConatiner').hide();

    if (rosterId == 'select' && gradeCourseId == 'select') {
        $('.ui-jqgrid').hide();
        $('#movingToSchedule').hide();
    } else {

        $
            .ajax({
                url: "getstudentGridNew.htm",
                data: {
                    rosterId: rosterId,
                    gradeCourseId: gradeCourseId
                },
                dataType: 'json',
                type: "GET"
            }).done(function(students) {
                    studentsArraynew = students;
                    $('.ui-jqgrid').show();
                    $('#movingToSchedule').removeAttr("disabled");
                    idsOfSelectedRows = []
                    var $grid = $("#studentTable"),
                        updateIdsOfSelectedRows = function(id, isSelected) {
                            if (idsOfSelectedRows && idsOfSelectedRows.length > 0) {
                                var contains = idsOfSelectedRows.indexOf(id);
                            } else {
                                var contains = -1;
                            }

                            if (!isSelected && (contains > -1)) {
                                for (var i = 0; i < idsOfSelectedRows.length; i++) {
                                    if (idsOfSelectedRows[i] == id) {
                                        idsOfSelectedRows.splice(i, 1);
                                        break;
                                    }
                                }
                            } else if (contains == -1) {
                                idsOfSelectedRows.push(id);
                            }

                        };
                    $('#studentTable').jqGrid('clearGridData');
                    $('#studentTable').jqGrid('setGridParam', {data: students});
                    $('#studentTable').trigger('reloadGrid');

                    $("#studentTable")
                        .jqGrid({
                            datatype: "local",
                            data: students,
                            colModel: [{
                                label: 'id',
                                name: 'id',
                                width: 30,
                                key: true,
                                hidden: true,
                                searchoptions : {title:"ID"}
                            }, {
                                label: 'First Name',
                                name: 'legalFirstName',
                                width: 75,
                                align: 'center',
                                searchoptions : {title:"First Name"}
                            }, {
                                label: 'Last Name',
                                name: 'legalLastName',
                                width: 90,
                                align: 'center',
                                searchoptions : {title:"Last Name"}
                            }],
                            viewrecords: true,
                            width: 650,
                            height: 200,
                            rowNum: 10,
                            rowList: [10, 20, 30, 40, 60, 90 ],
                            page: 1,
                            rownumbers: true,
                            rownumWidth: 50,
                            multiselect: true,
                            scrollOffset: 0,
                            loadonce: false,
                            viewable: false,
                            scrollerbar: false,

                            onSelectRow: updateIdsOfSelectedRows,
                            onSelectAll: function(aRowids,isSelected) {
                                var i, count, id;
                                for (i = 0,count = aRowids.length; i < count; i++) {
                                    id = aRowids[i];
                                    updateIdsOfSelectedRows(id,isSelected);
                                }
                            },
                            loadComplete: function() {
                                var $this =$(this),i, count;
                                for (i = 0,count = idsOfSelectedRows.length; i < count; i++) {
                                    $this.jqGrid('setSelection',idsOfSelectedRows[i],false);
                                }
                                
                                var ids = $(this).jqGrid('getDataIDs');         
                   	         	var tableid=$(this).attr('id');      
                   	            for(var i=0;i<ids.length;i++)
                   	            {        
                   	            	$('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'legalFirstName') +' '+$(this).getCell(ids[i], 'legalLastName')+ ' Check Box');
                   	            } 

                   	             $('#cb_'+tableid).attr('title','User Grid All Check Box');
                            },
                            pager: "#pagerStudent"
                        });
                    $("#studentTable").closest('.ui-jqgrid-bdiv').width($("#studentTable").closest('.ui-jqgrid-bdiv').width() + 2);
                    $('.ui-jqgrid-hbox').css({ "padding-right": "0"});
                    $('.ui-jqgrid-bdiv').css({'overflow-x': 'hidden'});
                    $("#studentTable").jqGrid(
                        'filterToolbar', {
                            stringResult: true,
                            searchOnEnter: false,
                            defaultSearch: "cn"
                        });
                    $('tr.ui-search-toolbar input').css('width', '100%');
                    var grid = $("#studentTable");
                    var rowKey = grid.find('tr.jqgrow');
                    if ($('#js-edittestsesstion').is(":visible")) {
                        $('#js-individualselect')
                            .dialog({
                                autoOpen: false,
                                modal: true,
                                width: 700,
                                height: 500,
                                title: "Group Creation",
                                create: function(
                                    event,
                                    ui) {
                                    var widget = $(this).dialog( "widget");
                                    $( ".ui-dialog-titlebar-close span",widget).prop('id','editUserDialogClose');
                                },

                                close: function(
                                    ev, ui) {

                                }
                            }).dialog('open');
                        $('.assignnewstudentsdiv').show();
                    } else {
                        if (rowKey.length > 0) {
                            $('#movingToSchedule').show();

                        }
                        $('#js-individualselect').show();
                    }

                });
    }
}

function addStudentsToExistingGroup(){
    
	var grid = $("#addingnewstudentsgroupTable");
    var rowKey = grid.getGridParam("selarrrow");
    if (rowKey && rowKey.length > 0) {
        for (var i in rowKey) {
            var studentObj = _.findWhere(
                newstudentsforselection, {
                    id: parseInt(rowKey[i])
                });
            if (!(_.findWhere(existingstudentsingroup, {
                    id: parseInt(studentObj.id)
                }))) {
                existingstudentsingroup.push(studentObj);
            }

        }
        $('.js-newstudentsdiv').dialog('close');
        $('.studentgrouptableinedit').html(
                '<table id = "studentGroupEditTable"><tr><td /></tr></table><div id="studentGroupEditTablePager"></div>');
        loadeeditgroupjqgrid(existingstudentsingroup);
    } else {
        alert('Please select at least one student.')
    }
}



$('#cancelAddinggroup').on('click', function(ev) {
    $('#js-groupselect').dialog('close');
});
$('#saveNewStudTestgroup').on('click',
    function(ev) {
        var grid = $('#groupstudenttable')
        var selRows = grid.getGridParam("selarrrow");
        if (selRows && selRows.length > 0) {
            for (var i in selRows) {
                var obj = _.findWhere(studentsArraynew, {
                    id: parseInt(selRows[i])
                });
                if (!(_.findWhere(assignedstudentsfortestsession, {
                        id: parseInt(obj.id)
                    }))) {
                    assignedstudentsfortestsession.push(obj);
                }
            }
            jqGridConstructionForActionswithData(
                testAssignObject.testSessionId,
                assignedstudentsfortestsession, testAssignObject.interimTestId);
            $('#js-groupselect').dialog('close');
        } else {
            alert('Please select at least one student');
        }

    });



function createGroup() {
    $('#group-create').dialog('close');
    $('#js-groupnameform').show();
    $('#movingToSchedule').hide();
    $('#js-savegroup,#js-cancelgroup').show();
}

/*
 * $(document).delegate('#js-cancelgroup','click',function(ev){
 * ev.preventDefault(); $('#cancel-group-create').modal('show'); });
 */

function cancelGroupCreation() {
    // $('#cancel-group-create').modal('hide');
    $('#group-create').dialog('close');
    $('#group-create').dialog("destroy");
    $('#group-create').html("");
    $('#group-create').remove();
    $('#group-create').remove();
    $("#manageScreen").hide();
    $('#scheduleConfirm').hide();
    savingTestSessionDetails();
}

function cancelwhileGroupCreation() {
    $('#cancel-group-create').dialog({
        autoOpen: false,
        modal: true,
        width: 500,
        height: 320,
        title: "Group Creation",
        create: function(event, ui) {
            var widget = $(this).dialog("widget");
            $(".ui-dialog-titlebar-close span", widget).prop('id','editUserDialogClose');
        },
        close: function(ev, ui) {

        }
    }).dialog('open');
    // $('#cancel-group-create').modal('show');
}

function yescreategroup() {
    $('#cancel-group-create').dialog('close');
}



$('#backtomanage').on('click', function(ev) {
    ev.preventDefault();
    $('#scheduleScreen').hide();
    $("#manageScreen").show();
});

// previously it is in schedue page click
// on the part of F560 it is changed to assign button click
// this method is for assigning the testSession
function savingTestSessionDetails() {

    var obj = {};
    obj.studentIds = testAssignObject.studentsIds;
    obj.interimTestId = testAssignObject.interimTestId;

    $('#schedulepage-errors').html('');
    if (testAssignObject.testSessionId) {
        obj.testSessionId = testAssignObject.testSessionId;
        $.ajax({
            url: 'updateStudentsTestsTest.htm',
            data: obj,
            dataType: 'json',
            type: "POST"
        }).done(function(data) {
                $("#scheduleScreen").hide();
                $('#assignSucessMsgDailog').html('The test assigned successfully');
                $('#assignSucessMsgDailog').dialog({
                    resizable: false,
                    modal: true,
                    width: 500,
                    height: 200,
                    title: '',
                    buttons: {
                        Ok: function(ev) {
                            $(this).dialog("close");
                            return false;
                        }
                    }
                }).dialog('open');
                renderMyTests();
            });
    } else {
        $.ajax({
            url: 'assignInterimTestToStudent.htm',
            data: obj,
            dataType: 'json',
            type: "POST"
        }).done(function(data) {
                $("#scheduleScreen").hide();
                $('#assigned-students-count').html(obj.studentIds.length);

                $('#assignSucessMsgDailog').html('The test assigned successfully');
                $('#assignSucessMsgDailog').dialog({
                    resizable: false,
                    modal: true,
                    width: 500,
                    height: 200,
                    title: '',
                    buttons: {
                        Ok: function(ev) {
                            $(this).dialog("close");
                            return false;
                        }
                    }
                }).dialog('open');

                renderMyTests();
            });
    }

}

function deleteInterim(event, itId, studentsAttempted, testSessionId) {
		
	if(event.type=='keypress'){
		 if(event.which !=13){
			return false;
		 }
	}
		
    found = $.inArray(itId, attmptedTestArray);
    if (studentsAttempted > 0) {
        alert("Students have already attempted this test, It cannot be deleted now.");
    } else {
        var i = interimTestsArray.indexOf(itId);
        interimTestsArray.splice(i, 1);
        $('.conform_delete_message').show();
        $('.conform_delete_message').attr('data-id', itId);
        
        $('.conform_delete_message').attr('testSessionId', testSessionId);
    }
};
// this method is added for play pause functionality for F560
function suspendTestSession(event, testSessionId, suspend) {
		
	if(event.type=='keypress'){
	 if(event.which !=13){
		return false;
	 }
	}
		
	
    if (suspend) {
        $('#suspendMsgDailog').html('Do you want to Stop Accepting Responses for this Test Session');
    } else {
        $('#suspendMsgDailog').html('Do you want to Resume Accepting Responses for the Test Session');
    }
    $('#suspendMsgDailog').dialog({
        resizable: false,
        modal: true,
        width: 500,
        height: 200,
        title: '',
        buttons: {
            Yes: function(ev) {
                $.ajax({
                    url: 'suspendTestWindow.htm',
                    data: {
                        'suspend': suspend,
                        'testSessionId': testSessionId
                    },
                    dataType: 'json',
                    type: "POST"
                }).done(function(data) {
                        renderMyTests();
                    });
                $(this).dialog("close");
                return true;
            },
            No: function(ev) {
                $(this).dialog("close");
                return false;
            }
        }
    }).dialog('open');
    $(".ui-dialog").removeAttr("role");
};

function deleteConformMsg() {
    $('.conform_delete_message').hide();
    var itId = $('.conform_delete_message').attr('data-id');
    var testSessionId = $('.conform_delete_message').attr('testSessionId');
    $
        .ajax({
            url: 'deleteInterimTest.htm',
            data: {
                'interimTestId': itId,
                'testSessionId': testSessionId
            },
            dataType: 'json',
            type: "GET"
        }).done(function(data) {

                $('a[data-id="' + itId + '"]').closest('tr').remove();
                $('#interimTabs').html(new EJS({
                    url: contextPath + '/js/views/interimtests.ejs'
                }).render({
                    interimTests: interimTestsArray

                }));

                function actionCol() {
                    return '<a href="#"><i class="fa fa-edit fa-lg" id="editButton"></i></a><a href="#"><i class="fa fa-trash fa-lg"></i></a>';
                }
                var getColumnIndexByName = function(grid, columnName) {
                    var cm = grid.jqGrid('getGridParam', 'colModel'),
                        i = 0,
                        l = cm.length;
                    for (; i < l; i += 1) {
                        if (cm[i].name === columnName) {
                            return i; // return the index
                        }
                    }
                    return -1;
                };

                jqGridConstruction();
            });
    // $('.deleteInterim[data-id="'+itId+'"]').closest('tr').remove();
}

function closedeletepopup() {
    $('.conform_delete_message').hide();
    $.ajax({
        url: 'deleteInterimTest.htm',
        type: 'GET',
        data: {
            interimTestId: itId
        } 
    }).done(function(result) {

        });
}

function assignStudent(event, itId, access, isTestAssigned, testSessionId, serial, autoAssignId,testCollectionId) {
		
	if(event.type=='keypress'){
	 if(event.which !=13){
		return false;
	 }
	}
		
    // var itId = $(this).closest('a').data('id');
    var presentdate = new Date();
    var h = presentdate.getHours(),
        m = presentdate.getMinutes();
    var timeforcondition = (h > 12) ? (h - 12 + ':' + m + ' PM') : (h + ':' + m + ' AM');
    var presentmonth = presentdate.getMonth() + 1;
    var dateforcondition = presentmonth + '/' + presentdate.getDate() + '/' + presentdate.getFullYear();
    var presenttime = presentdate.getHours() + ":" + presentdate.getMinutes() + ":" + presentdate.getSeconds();
    var startDate = new Date($('.studentassign[data-serial="' + serial + '"]').data('startdate'));
    var endDate = new Date($('.studentassign[data-serial="' + serial + '"]').data('enddate'));
    var startTime = new Date($('.studentassign[data-serial="' + serial + '"]').data('starttime'));
    var endTime = new Date($('.studentassign[data-serial="' + serial + '"]').data('endtime'));
    var smonth = startDate.getMonth() + 1;
    var emonth = endDate.getMonth() + 1;
    var startdateformat = smonth + '/' + startDate.getDate() + '/' + startDate.getFullYear();
    var enddateformat = emonth + '/' + endDate.getDate() + '/' + endDate.getFullYear();
    var starttimeStringStartdate = Date.parse(startdateformat + ' ' + $('.studentassign[data-serial="' + serial + '"]').data('starttime'));
    var endtimestringstartDate = Date
        .parse(startdateformat + ' ' + $('.studentassign[data-serial="' + serial + '"]').data('endtime'));
    var startTimeStringEndDate = Date.parse(enddateformat + ' ' + $('.studentassign[data-serial="' + serial + '"]').data('starttime'));
    var endTimeStringEndDate = Date.parse(enddateformat + ' ' + $('.studentassign[data-serial="' + serial + '"]').data('endtime'));
    var currentTimeStringEndDate = Date.parse(dateforcondition + ' ' + timeforcondition);
    if (!groupsArray || !groupsArray.length > 0) {
        groupsArray = [];
    }
    if (access) {
        var nwInterimObject;
        var testName;
        var testDescription;
        $.ajax({
            url: 'getMiniTestsByInterimTestId.htm',
            data: {
                interimTestId: itId
            },
            dataType: 'json',
            type: "GET"
        }).done(function(data) {
                nwInterimObject = data;
                testName = nwInterimObject.interimTest.name;
                testDescription = nwInterimObject.interimTest.description;
            });

        if (isTestAssigned) {
            testAssignObject = {};
            testAssignObject.interimTestId = itId;
            $.ajax({
                    url: 'getStudentByInterimTestSession.htm',
                    data: {
                        'testSessionId': testSessionId
                    },
                    dataType: 'json',
                    type: "GET"
                }).done(function(studentsArray) {
                        assignedstudentsfortestsession = studentsArray;
                        $.ajax({
                                url: 'assignView.htm',
                                data: {
                                    interimTestId: itId
                                },
                                dataType: 'json',
                                type: "GET",
                        	}).done(function(data) {
                                    data.gradename.sort(function(a, b) {
                                            var ax = [],
                                                bx = [];

                                            a.name.replace(/(\d+)|(\D+)/g,function(_,$1,$2) {
                                                        ax.push([$1 || Infinity,$2 || ""])
                                                    });
                                            b.name.replace(/(\d+)|(\D+)/g,function(_,$1,$2) {
                                                        bx.push([$1 || Infinity,$2 || ""])
                                                    });

                                            while (ax.length && bx.length) {
                                                var an = ax.shift();
                                                var bn = bx.shift();
                                                var nn = (an[0] - bn[0]) || an[1].localeCompare(bn[1]);
                                                if (nn)
                                                    return nn;
                                            }

                                            return ax.length - bx.length;
                                        })
                                        // data.gradename=_.sortBy(data.gradename,
                                        // 'name');
                                    groupsArray = _.sortBy(groupsArray,
                                        'groupName');
                                    $('#interimTabs')
                                        .html(
                                            new EJS({
                                                url: contextPath + '/js/views/manage.ejs'
                                            })
                                            .render({
                                                dropdownObject: data,
                                                testName: testName,
                                                testDescription: testDescription,
                                                students: studentsArray,
                                                groups: groupsArray,
                                                itid:itId,
                                                testCollectionId : testCollectionId,
                                                currentAssessmentProgramName : currentAssessmentProgramName
                                            }));
                                    


                                    $("#existinggroup").select2({
                                        data: groupsArray.groupName,
                                        allowClear: true,
                                        placeholder: "Select Group"
                                    });
                                    $('.select2-container input')
                                        .css('height','40px !important');

                                    $("#interimRoster, #interimAutoRoster")
                                        .select2({
                                            data: data.rostername.name,
                                            placeholder: function() {
                                                $(this).data('placeholder');
                                            },
                                            allowClear: true
                                        });

                                    $("#interimRosterGrade, #interimRosterAutoGrade")
                                        .select2({
                                            data: data.gradename.name,
                                            placeholder: function() {
                                                $(this).data('placeholder');
                                            },
                                            allowClear: true
                                        });

                                    $("#interimAssignSubject").select2({
                                        data: data.subjectname.name,
                                        placeholder: function() {
                                            $(this).data('placeholder');
                                        },
                                        allowClear: true
                                    });

                                    $('#PreviewLink').attr('data-itid',itId);
                                    $('#PreviewLink').attr('data-testcollectionid', testCollectionId);
                                    /*
                                     * if ((Date.parse(startdateformat) >
                                     * Date .parse(dateforcondition)) ||
                                     * (Date .parse(startdateformat) ==
                                     * Date .parse(dateforcondition) &&
                                     * starttimeStringStartdate >
                                     * currentTimeStringEndDate)) {
                                     */
                                    if (autoAssignId == null) {
                                        testAssignObject.testSessionId = testSessionId;
                                        jqGridConstructionForActionswithData(
                                            testSessionId,
                                            studentsArray, itId);
                                    } else {
                                        var testStatus = 'ended';
                                        jqGridConstructionForNoActions(
                                            testStatus,
                                            testSessionId,
                                            studentsArray);
                                    }
                                    
                                    $('#js-showSchdule').on('click',function(ev) {
                                        ev.preventDefault();
                                        $("#manageScreen").hide();
                                        $.ajax({
                                                url: "getTestSessionDetailsByTestSessionId.htm",
                                                data: {
                                                    testSessionId: $(this).attr('data-testsessionid')
                                                },
                                                dataType: 'json',
                                                type: "GET"
                                            }).done(function(data) {
                                                    // removed schedule page date and time code
                                                    // in the part of F560
                                                    $('#scheduleButton').hide();
                                                    $('#backtomanage').hide();
                                                    $('#backToInterim')[0].click();
                                                    $('.backtointerimhome').show();
                                                });
                                    });
                                    
                                    $('#PreviewLink').on('click', function(ev) {
                                    	var interimTestId = $(this).attr('data-itid');
                                    	var testCollectionId = $(this).attr('data-testcollectionid');
                                        previrewInterim(ev,interimTestId,testCollectionId);
                                    });
                                    
                                    $('#studentSearch').on('click',function(ev) {
                                    	ev.preventDefault();
                                    	studentSearch($(this));
                                    });
                                    
                                    $('#saveNewStudTest').on('click',function(ev) {
                                    	
                                    	ev.preventDefault();
                                    	saveNewStudentTest()
                                    });
                                    
                                    $('#cancelAdding').on('click', function(ev) {
                                        $('#js-individualselect').dialog('close');
                                    });
                                                                       
                                    $('#js-edittestsesstion').on('click', function(ev) {
                                        ev.preventDefault();
                                        var grid = $("#testassignedTable");
                                        var rowKey = assignedStudents;
                                        if (!rowKey) {
                                            alert("No rows are selected");
                                        } else {

                                            testAssignObject.studentsIds = rowKey;
                                            if (testAssignObject.studentsIds.length == 0) {
                                                alert('Please select at least one student');
                                            } else {
                                                $.ajax({
                                                    url: "getTestSessionDetailsByTestSessionId.htm",
                                                    data: {
                                                        testSessionId: $(this).attr('data-testsessionid')
                                                    },
                                                    dataType: 'json',
                                                    type: "GET"
                                                }).done(function(data) {
                                                        // removed schedule page date and time code in the part of
                                                        // F560
                                                        $("#manageScreen").hide();
                                                        $('#scheduleConfirm').hide();

                                                        savingTestSessionDetails();
                                                    });
                                            }

                                        }
                                    });                                    
            
                                });                            
                    });
        } else {
            testAssignObject = {};
            testAssignObject.interimTestId = itId;
            var nwInterimObject;
            var testName;
            var testDescription;
            $.ajax({
                url: 'getMiniTestsByInterimTestId.htm',
                data: {
                    interimTestId: itId
                },
                dataType: 'json',
                type: "GET"
            }).done(function(data) {
                    nwInterimObject = data;
                    testName = nwInterimObject.interimTest.name;
                    testDescription = nwInterimObject.interimTest.description;
                });

            $.ajax({
                url: 'assignView.htm',
                data: {interimTestId: itId},
                dataType: 'json',
                type: "GET"
            }).done(function(data) {
                    data.gradename.sort(function(a, b) {
                        var ax = [],
                            bx = [];

                        a.name.replace(/(\d+)|(\D+)/g, function(_, $1, $2) {
                            ax.push([$1 || Infinity, $2 || ""])
                        });
                        b.name.replace(/(\d+)|(\D+)/g, function(_, $1, $2) {
                            bx.push([$1 || Infinity, $2 || ""])
                        });

                        while (ax.length && bx.length) {
                            var an = ax.shift();
                            var bn = bx.shift();
                            var nn = (an[0] - bn[0]) || an[1].localeCompare(bn[1]);
                            if (nn)
                                return nn;
                        }

                        return ax.length - bx.length;
                    })
                    groupsArray = _.sortBy(groupsArray, 'groupName');
                    $('#interimTabs').html(new EJS({
                        url: contextPath + '/js/views/manage.ejs'
                    }).render({
                        dropdownObject: data,
                        testName: testName,
                        testDescription: testDescription,
                        students: studentsArray,
                        groups: groupsArray,
                        itid : itId,
                        testCollectionId : testCollectionId,
                        currentAssessmentProgramName : currentAssessmentProgramName
                    }));

                    Interimgroups();

                    $("#existinggroup").select2({
                        data: groupsArray.groupName,
                        allowClear: true,
                        placeholder: "Select Group"
                    });
                    $('.select2-container input').css('height',
                        '40px !important');

                    $("#interimRoster, #interimAutoRoster").select2({
                        data: data.rostername.name,
                        placeholder: function() {
                            $(this).data('placeholder');
                        },
                        allowClear: true
                    });

                    $("#interimRosterGrade, #interimRosterAutoGrade").select2({
                        data: data.gradename.name,
                        placeholder: function() {
                            $(this).data('placeholder');
                        },
                        allowClear: true
                    });

                    $("#interimAssignSubject").select2({
                        data: data.subjectname.name,
                        placeholder: function() {
                            $(this).data('placeholder');
                        },
                        allowClear: true
                    });

                    $('#PreviewLink').attr('data-itid', itId);
                    $('#PreviewLink').attr('data-testcollectionid', testCollectionId);
                    
                    $('#autoAssignStudents').on('click', function(ev) {
                    	ev.preventDefault();
                    	autoAssignStudents();
                    });
                    
                    
                    $('#studentSearch').on('click',function(ev) {
                    	ev.preventDefault();
                    	studentSearch($(this));
                        });
                    

                    $('#js-showSchdule').on('click',function(ev) {
                        ev.preventDefault();
                        $("#manageScreen").hide();
                        $.ajax({
                                url: "getTestSessionDetailsByTestSessionId.htm",
                                data: {
                                    testSessionId: $(this).attr('data-testsessionid')
                                },
                                dataType: 'json',
                                type: "GET"
                            }).done(function(data) {
                                    // removed schedule page date and time code
                                    // in the part of F560
                                    $('#scheduleButton').hide();
                                    $('#backtomanage').hide();
                                    $('#backToInterim')[0].click();
                                    $('.backtointerimhome').show();
                                });
                    });
                    
                    $('#PreviewLink').on('click', function(ev) {
                    	var interimTestId = $(this).attr('data-itid');
                    	var testCollectionId = $(this).attr('data-testcollectionid');
                        previrewInterim(ev,interimTestId,testCollectionId);
                    });
                    
                    $('#assign').on('click', function(ev) {
                    	ev.preventDefault();
                    	autoAssign();

                    });
                    
                    $('#existinggroup').on('change',function(ev){
                    	ev.preventDefault();
                    	selectStudentsOnGrid();
                    });
                    
                	$('#movingToSchedule').on('click',function(ev) {
                		ev.preventDefault();
                		assignSelectedStudents();
                	 });
                	
                	$('#js-savegroup').on('click', function(ev) {
                	    ev.preventDefault();
                	    saveInterimGroup();
                	});
                	
                	$('#js-cancelgroup').on('click', function(ev) {
                	    $('.js-newstudentsdiv').dialog('close');
                	});
                	
                    $('#autoAssignRoster').on('click', function(ev) {
                    	autoAssignRoster();
                    });
                                     
                });
        }
        
    } else if (isTestAssigned) {
        $.ajax({
            url: 'getStudentByInterimTestSession.htm',
            data: {
                'testSessionId': testSessionId
            },
            dataType: 'json',
            type: "GET"
        }).done(function(studentsArray) {
                assignedstudentsfortestsession = studentsArray;
                $.ajax({
                    url: 'assignView.htm',
                    data: {interimTestId: itId},
                    dataType: 'json',
                    type: "GET",
                    success: function(data) {
                        data.gradename.sort(function(a, b) {
                                var ax = [],
                                    bx = [];
                                a.name.replace(/(\d+)|(\D+)/g, function(_, $1, $2) {
                                    ax.push([$1 || Infinity, $2 || ""])
                                });
                                b.name.replace(/(\d+)|(\D+)/g, function(_, $1, $2) {
                                    bx.push([$1 || Infinity, $2 || ""])
                                });

                                while (ax.length && bx.length) {
                                    var an = ax.shift();
                                    var bn = bx.shift();
                                    var nn = (an[0] - bn[0]) || an[1].localeCompare(bn[1]);
                                    if (nn)
                                        return nn;
                                }

                                return ax.length - bx.length;
                            })
                            // data.gradename=_.sortBy(data.gradename, 'name');
                        groupsArray = _.sortBy(groupsArray, 'groupName');
                        $('#interimTabs').html(new EJS({
                            url: contextPath + '/js/views/manage.ejs'
                        }).render({
                            dropdownObject: data,
                            testName: testName,
                            testDescription: testDescription,
                            students: studentsArray,
                            groups: groupsArray,
                            itid:itId,
                            testCollectionId : testCollectionId,
                            currentAssessmentProgramName : currentAssessmentProgramName
                        }));

                        $("#existinggroup").select2({
                            data: groupsArray.groupName,
                            allowClear: true,
                            placeholder: "Select Group"
                        });
                        $('.select2-container input').css('height','40px !important');

                        $("#interimRoster, #interimAutoRoster").select2({
                            data: data.rostername.name,
                            placeholder: function() { $(this).data('placeholder');
                            },
                            allowClear: true
                        });

                        $("#interimRosterGrade, #interimRosterAutoGrade").select2({
                            data: data.gradename.name,
                            placeholder: function() {
                                $(this).data('placeholder');
                            },
                            allowClear: true
                        });

                        $("#interimAssignSubject").select2({
                            data: data.subjectname.name,
                            placeholder: function() {
                                $(this).data('placeholder');
                            },
                            allowClear: true
                        });
                        $('#PreviewLink').attr('data-itid', itId);
                        $('#PreviewLink').attr('data-testcollectionid', testCollectionId);
                        
                        
                        $('#js-showSchdule').on('click',function(ev) {
                            ev.preventDefault();
                            $("#manageScreen").hide();
                            $.ajax({
                                    url: "getTestSessionDetailsByTestSessionId.htm",
                                    data: {
                                        testSessionId: $(this).attr('data-testsessionid')
                                    },
                                    dataType: 'json',
                                    type: "GET"
                                }).done(function(data) {
                                        // removed schedule page date and time code
                                        // in the part of F560
                                        $('#scheduleButton').hide();
                                        $('#backtomanage').hide();
                                        $('#backToInterim')[0].click();
                                        $('.backtointerimhome').show();
                                    });
                        });
                        
                        $('#PreviewLink').on('click', function(ev) {
                        	var interimTestId = $(this).attr('data-itid');
                        	var testCollectionId = $(this).attr('data-testcollectionid');
                            previrewInterim(ev,interimTestId,testCollectionId);
                        });
                                               
                        var testStatus = 'ended';
                        jqGridConstructionForNoActions(testStatus,
                            testSessionId, studentsArray);
                    }
                });

            });

    }
    

}

function saveNewStudentTest(){

    var grid = $('#studentTable')
    var selRows = grid.getGridParam("selarrrow");
    if (selRows && selRows.length > 0) {
        for (var i in selRows) {
            var obj = _.findWhere(studentsArraynew, {
                id: parseInt(selRows[i])
            });
            if (!(_.findWhere(assignedstudentsfortestsession, {
                    id: parseInt(obj.id)
                }))) {
                assignedstudentsfortestsession.push(obj);
            }
        }
        jqGridConstructionForActionswithData(testAssignObject.testSessionId,assignedstudentsfortestsession, testAssignObject.interimTestId);
        $('#js-individualselect').dialog('close');
    } else {
        alert('Please select at least one student');
    }
}

function jqGridConstructionForActionswithData(testSessionId, studentsArray, interimTestId) {
	
	//adding student ids
 	for (var i in studentsArray) {
 		var numId = parseInt(studentsArray[i].id);
 		var index = $.inArray(numId,assignedStudents);
	        if (index < 0) {
	       	 assignedStudents.push(numId);
	        }
     }
	 
    $('#js-individualselect').hide();
    $('#js-testassigned').show();
    $("#js-testassigned").html('');
    $("#js-testassigned").html(
        '<table id="testassignedTable"><tr></td></tr></table><div id="testassignedpager"></div>');
    $.ajax({
        url: 'assignView.htm',
        data: {interimTestId:interimTestId},
        dataType: 'json',
        type: "GET",
        success: function(data) {
            $("#testassignedTable").jqGrid({
                datatype: "local",
                data: studentsArray,
                colModel: [{
                    label: 'id',
                    name: 'id',
                    width: 30,
                    key: true,
                    hidden: true
                }, {
                    label: 'First Name',
                    name: 'legalFirstName',
                    width: 75,
                    align: 'center'
                }, {
                    label: 'Last Name',
                    name: 'legalLastName',
                    width: 90,
                    align: 'center'
                }],
                loadonce: true,
                viewrecords: true,
                width: 500,
                height: 200,
                rowNum: 10,
                rowList: [10, 20, 30, 40, 60, 90 ],
                rownumbers: true,
                rownumWidth: 50,
                multiselect: true,
                sortname: 'legalLastName, legalFirstName',
    		    sortorder: 'asc',
                scrollOffset: 0,
                scrollerbar: false,
                altRows: true,
                hoverrows: true,
                altClass: 'altrow',
                pager: "#testassignedpager",
                onSelectRow: function(rowId , isSelected){
					var numOfRowid = parseInt(rowId);
					var index = $.inArray(numOfRowid,assignedStudents);
						if (!isSelected && index >= 0) {
							assignedStudents.splice(index,1); 
						     // remove id from the list
						} else if (index < 0) {
							assignedStudents.push(numOfRowid);
						}
                     console.log("grid ids"+ assignedStudents);
                },
                onSelectAll: function(rowids, isSelected) {
                	for (var i in rowids) {
                		var numOfRowid = parseInt(rowids[i]);
                		var index = $.inArray(numOfRowid,assignedStudents);
	                        if (!isSelected && index >= 0) {
	                       	 assignedStudents.splice(index,1); 
	                            // remove id from the list
	                        } else if (index < 0) {
	                       	 	assignedStudents.push(numOfRowid);
	                        }
                	}
                	 
                },
                gridComplete: function() {
                    $('#testassignedTable input').trigger('click');
                    $('#testassignedTable input').prop('checked', true);
                    $('#cb_testassignedTable').prop('checked', true);
                }
            });
            $("#testassignedTable").closest('.ui-jqgrid-bdiv').width($("#testassignedTable").closest('.ui-jqgrid-bdiv').width() + 2);
            $('.ui-jqgrid-hbox').css({
                "padding-right": "0"
            });
            $('.ui-jqgrid-bdiv').css({
                'overflow-x': 'hidden'
            });
            $("#testassignedTable").jqGrid('filterToolbar', {
                stringResult: true,
                searchOnEnter: false,
                defaultSearch: "cn"
            });
            $('tr.ui-search-toolbar input').css('width', '100%');

            $('#js-edittestsesstion').show();
            $('#js-edittestsesstion').attr('data-testsessionid', testSessionId)
            $('#interimRosterContainer').hide();
            $('#autoAssignStudents').hide();
            $('#interimGrade').hide();
            $('#autoAssignRoster').hide();
            $('#rosterSubmit').hide();
            $('#assignConatiner').hide();
            $('#assignSubjectConatiner').hide();
            $('#assignGradeConatiner').hide();
        }
    });
}

function jqGridConstructionForNoActions(testStatus, testSessionId,
    studentsArray) {
    $('#js-individualselect').hide();
    $('#js-testassigned').show();
    $('#js-formsearch').hide();
    $("#js-testassigned").html('');
    $("#js-testassigned").html(
        '<table id="testassignedTable"><tr></td></tr></table>');
    $("#testassignedTable").jqGrid({
        datatype: "local",
        data: studentsArray,
        colModel: [{
            label: 'id',
            name: 'id',
            width: 30,
            key: true,
            hidden: true,
            searchoptions : {title:"ID"}
        }, {
            label: 'First Name',
            name: 'legalFirstName',
            width: 80,
            align: 'center',
            searchoptions : {title:"First Name"}
        }, {
            label: 'Last Name',
            name: 'legalLastName',
            width: 90,
            align: 'center',
            searchoptions : {title:"Last Name"}
        }],
        loadonce: true,
        viewrecords: true,
        width: 480,
        height: 200,
        rowNum: 1000,
        altRows: true,
        hoverrows: true,
        altClass: 'altrow',
        scrollOffset: 0,
        pager: "#testassignedpager"
    });
    $("#testassignedTable").jqGrid('filterToolbar', {
        stringResult: true,
        searchOnEnter: false,
        defaultSearch: "cn"
    });
    $('tr.ui-search-toolbar input').css('width', '100%');
    $('#js-showSchdule').attr('data-teststatus', testStatus);
    $('#js-showSchdule').attr('data-testsessionid', testSessionId);
    $('#js-showSchdule').show();
    $('#interimRosterContainer').hide();
    $('#autoAssignStudents').hide();
    $('#interimGrade').hide();
    $('#autoAssignRoster').hide();
    $('#rosterSubmit').hide();
    $('#assignConatiner').hide();
    $('#assignSubjectConatiner').hide();
    $('#assignGradeConatiner').hide();
}

$('.editInterimClass').on('click', function(ev) {
    ev.preventDefault();
    $("#assembleScreen").show();
    $('#createTest').text('Update Test');
    $('#createTest').attr('data-interimid', $(this).attr('data-itid'));

    // var access=true;
    // var studentsAttempted=0;
    // editInterim($(this).attr('data-id'),access,studentsAttempted);
});

function selectStudentsOnGrid() {
	
    $("#interimRoster").val([]).trigger('change');
    $("#interimRosterGrade").val([]).trigger('change');
    if ($('#existinggroup').val() != '') {
        $('#interimRoster').attr('disabled', 'disabled');
        $('#interimRosterGrade').attr('disabled', 'disabled');
        $('#studentSearch').addClass('disabled');
        $('#js-individualselect').html('<table id="studentTable"><tr><td /></tr></table>');
        $.ajax({
                url: "getStudentsByGroup.htm",
                data: {
                    interimGroupId: $('#existinggroup').val()
                },
                dataType: 'json',
                type: "GET"
            }).done(function(students) {
                    studentsArraynew = students;
                    $( "#js-groupselect #gbox_groupstudenttable").remove();
                    $("#js-groupselect").prepend('<table id="groupstudenttable"><tr></td></tr></table>');
                    $("#groupstudenttable").jqGrid({
                            datatype: "local",
                            data: students,
                            colModel: [{
                                label: 'id',
                                name: 'id',
                                width: 30,
                                key: true,
                                hidden: true,
                                searchoptions : {title:"ID"} 
                            }, {
                                label: 'First Name',
                                name: 'legalFirstName',
                                width: 75,
                                align: 'center',
                                searchoptions : {title:"First Name"} 
                            }, {
                                label: 'Last Name',
                                name: 'legalLastName',
                                width: 90,
                                align: 'center',
                                searchoptions : {title:"Last Name"} 
                            }],
                            loadonce: true,
                            viewrecords: true,
                            width: 650,
                            height: 200,
                            rowNum: 10,
                            rowList: [10, 20, 30, 40, 60, 90 ],
                            rownumbers: true,
                            rownumWidth: 50,
                            multiselect: true,
                            scrollOffset: 0,
                            scrollerbar: false,
                            altRows: true,
                            hoverrows: true,
                            altClass: 'altrow',

                            pager: "#pagerStudentgroup",
                            gridComplete: function() {
                                $('#groupstudenttable input').trigger('click')
                                $('#groupstudenttable input').prop('checked',true);
                                $('#cb_groupstudenttable').prop('checked',true);
                                
                                var ids = $(this).jqGrid('getDataIDs');         
                   	         	var tableid=$(this).attr('id');      
                   	            for(var i=0;i<ids.length;i++)
                   	            {         
                   	                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'legalFirstName') +' '+$(this).getCell(ids[i], 'legalLastName')+ ' Check Box');
                   	                $('#jqg_'+tableid+'_'+ids[i]).removeAttr("aria-checked");
                   	            }
                   	            $('#cb_'+tableid).attr('title','User Grid All Check Box');
                   	            $('#cb_'+tableid).removeAttr("aria-checked");
                            }
                        });
                    $("#groupstudenttable").closest('.ui-jqgrid-bdiv').width($("#groupstudenttable").closest('.ui-jqgrid-bdiv').width() + 2);
                    $('.ui-jqgrid-hbox').css({"padding-right": "0"});
                    $('.ui-jqgrid-bdiv').css({'overflow-x': 'hidden'});
                    $("#groupstudenttable").jqGrid(
                        'filterToolbar', {
                            stringResult: true,
                            searchOnEnter: false,
                            defaultSearch: "cn"
                        });
                    $('tr.ui-search-toolbar input').css('width', '100%');
                    if ($('#js-edittestsesstion').is(":visible")) {

                        $('#js-groupselect')
                            .dialog({
                                autoOpen: false,
                                modal: true,
                                width: 700,
                                height: 500,
                                title: "Assigning Test To New Students ",
                                create: function(
                                    event,
                                    ui) {
                                    var widget = $(this).dialog( "widget");
                                    $(".ui-dialog-titlebar-close span",widget).prop('id','editUserDialogClose');
                                },

                                close: function(
                                    ev, ui) {

                                }
                            }).dialog('open');
                        $('.assignnewstudentsdivgroup').show();
                    } else {
                        $('#movingToSchedule').show();
                        $('#js-groupselect').show();
                    }

                });
    } else {
        $('#interimRoster').removeAttr('disabled');
        $('#interimRosterGrade').removeAttr('disabled');
        $('#studentSearch').removeClass('disabled');
        $('#js-groupselect').hide();
        $('#js-individualselect').show();
        $('#movingToSchedule').hide();

    }
    
}



function editInterim(event, itId, access, studentsAttempted, testSessionId) {
	
	if(event.type=='keypress'){
		 if(event.which !=13){
			return false;
		 }
	}	
	
    testSessionIdInterim = testSessionId;
    $.ajax({
            url: 'getMiniTestsByInterimTestId.htm',
            data: {
                interimTestId: itId
            },
            dataType: 'json',
            type: "GET"
        }).done(function(interimObj) {
            	var gradeCourseId = interimObj.gradeCourseId;
            	if(gradeCourseId == null){
            		gradeCourseId = null
            	}else{
            		gradeCourseId = interimObj.gradeCourseId.id
            	}
                $
                    .ajax({
                        url: 'getMiniTests.htm',
                        data: {
                            gradeCourseId: gradeCourseId,
                            contentAreaId: interimObj.contentAreaId.id,
                            contentCode: interimObj.contentCode,
                            isInterim: false
                        },
                        dataType: 'json',
                        type: "GET",
                        success: function(testArray) {

                            if (testArray && testArray.length > 0) {
                                if (interimObj.miniTests && interimObj.miniTests.length > 0) {
                                    for (var i in interimObj.miniTests) {
                                        var objinteim = _.findWhere(testArray, {
                                                    id: interimObj.miniTests[i].id
                                                });
                                        if (!objinteim || !objinteim.id) {
                                            testArray.push(interimObj.miniTests[i]);
                                        }
                                    }
                                }
                            } else {
                                testArray = data.miniTests;
                            }

                            $('#interimTabs').html(
                                    new EJS({
                                        url: contextPath + '/js/views/assemble.ejs'
                                    }).render({
                                        subject: subjectArray,
                                        align: alignArray,
                                        grade: gradeArray,
                                        school: schoolsArray,
                                        tests: testArray,
                                        currentAssessmentProgramName : currentAssessmentProgramName
                                    }));

                            interimTest.testIds = [];
                            var minTestIds = [];
                            for (i = 0; i < interimObj.miniTests.length; i++) {
                                minTestIds.push(interimObj.miniTests[i].id);
                            }
                            interimTest.testIds = minTestIds;
                            $('#ptag').html('');
                            for (var i in minTestIds) {
                                $('a[data-id="' + minTestIds[i] + '"]').closest('div').addClass('added-test');
                                $('a[data-id="' + minTestIds[i] + '"]').find('i.fa-plus-circle').removeClass("fa-plus-circle text_green").addClass("fa-minus-circle text_pink");
                                $(".js-shift-sortable").sortable({
                                    zIndex: 100000
                                });
                                $(".js-shift-sortable").disableSelection();
                                $('#ptag').append(
                                        '<a data-id="' + minTestIds[i] + '" class="btn btn-default btn-sm cancel_added_test ui-state-default">' + '<i class="fa fa-times"></i>' + $(
                                            'a[data-id="' + minTestIds[i] + '"]')
                                        .attr('data-name') + '</span>');
                            }
                            $('.assemble_box .col-sm-4 a i.text_pink').removeAttr('style');
                            $('#interimTestName').val(interimObj.interimTest.name);
                            $('#description').val(interimObj.interimTest.description);
                            $('#createTest').attr('data-interimid',interimObj.interimTest.id);
                            $('#createTest').removeAttr('disabled');
                            $('#createTest').text('Update Test');
                            if(currentAssessmentProgramName != 'PLTW'){
                            	$('#createTest').attr('data-gradecourseid', interimObj.gradeCourseId.id);
                            	  $("#interimTestGrade") .html(
                                          "<option value=" + interimObj.gradeCourseId.id + ">" + interimObj.gradeCourseId.name + "</option>");
                            }
                            $('#createTest').attr('data-contentareaid',interimObj.contentAreaId.id);
                            $("#interimTestSubject").html(
                                    "<option value=" + interimObj.contentAreaId.id + ">" + interimObj.contentAreaId.name + "</option>");
                            $("#radio-class").val($("#radio-class option:eq(1)").val());
                            // $("#radio-class").attr("disabled",
                            // 'disabled');
                            alignments();

                            function alignments() {
                                $('#searchButton').removeAttr('disabled');
                                $('#searchButton').removeClass('ui-state-disabled');
                            }

                            if (!access || studentsAttempted > 0) {
                                $('.text_green').closest('a').hide();
                                $('.text_pink').closest('a').hide();
                                $('#ptag a').removeClass('cancel_added_test');
                                $('#ptag a i').hide();
                                $('#createTest').hide();
                                $('#interimTestName').prop('disabled',true);
                                $('#description').prop('disabled', true);
                            }
                            
                            $('#createTest').on('click',function(ev) {
                                    ev.preventDefault();
                                    createInterimTest($(this).attr('data-interimid'));
                                }); 
        	                
                        }                                            
                    });             

            });

};

function getFeedbackQuestions(testId){
	$.ajax({
        url: 'getFeedbackQuestionsAndResponses.htm',
        data: {testId: testId},
        dataType: 'json',
        type: "GET"
    }).done(function(data) {
        	var lookup = {};
        	var uniqueTestlets = [];
        	for (var item, i = 0; item = data[i++];) {
        		  var testletId = item.testletExternalId;

        		  if (!(testletId in lookup)) {
        		    lookup[testletId] = 1;
        		    uniqueTestlets.push(testletId);
        		  }
        	}
        	for(var i = 0; i < uniqueTestlets.length; i++){
        		var questionNumbers = [];
        		
        		var releventData = $.grep(data, function (element, index) {
        		    return element.testletExternalId == uniqueTestlets[i];
        		});
        		releventData.sort(function(a,b) {
	      			  if (a.questionSequence < b.questionSequence)
	      			    return -1;
	      			  if (a.questionSequence > b.questionSequence)
	      			    return 1;
	      			  return 0;
      			});
        		
    			var qBlocks = $('.questionColumn').filter(function() { 
      			  return $(this).data("testletid") == uniqueTestlets[i] 
      			});
    			
    			qBlocks.each(function() {
    			    questionNumbers.push($(this).data('taskvarientindex'));
    			});
    			
    			var feedbackPromptBox = qBlocks.last().closest('tr').next('.feedback');
    			feedbackPromptBox.html('');
    			
    			var answeredResponses = $.grep(releventData, function (element, index) {
        		    return element.responseId != null;
        		});
    			
    			if(answeredResponses.length > 0){    				
    				
    				feedbackPromptBox.append(
    					'<a href="#" id="feedback_'+uniqueTestlets[i]+'" class="feedbackQClass">' 
    					+ '<i class="fa fa-commenting-o fa-2x"></i>'
    					+ ' View/Edit feedback for questions:' + questionNumbers.join() + '</a>');
    					$('#feedback_'+uniqueTestlets[i]).data("QandAData", releventData);
    					$('#feedback_'+uniqueTestlets[i]).on("click", function(){viewEditFeedback($(this).data('QandAData'));});
    			}else{
    				feedbackPromptBox.append(
        					'<a href="#" id="feedback_'+uniqueTestlets[i]+'" class="feedbackQClass" >' 
        					+ '<i class="fa fa-commenting-o fa-2x"></i>'
        					+ ' Add feedback for questions: ' + questionNumbers.join() + '</a>');
    				$('#feedback_'+uniqueTestlets[i]).data("QandAData", releventData);
        			$('#feedback_'+uniqueTestlets[i]).on("click", function(){viewEditFeedback($(this).data('QandAData'));});
    			}
        	}
        });
    			
}


function previrewInterim(event, itId,testCollectionId) {
	if(event.type=='keypress'){
	 if(event.which !=13){
		return false;
	 }
	}
			
    var testId = itId;
    var testCollectionId = testCollectionId;
    $('#previewQCTestDiv').dialog({
        autoOpen: false,
        modal: true,
        create: function(event, ui) {
            var widget = $(this).dialog("widget");
            $(".ui-dialog-titlebar-close span", widget).prop('id', 'editUserDialogClose');
        },
        width: 1080,
        height: 655,
        title: '',
        open:function(ev, ui){
			 $('.ui-dialog').removeAttr("role");	
        },
        close: function(ev, ui) {
            // $('#setupTestSessionTabs').tabs().tabs("option", 'active', 0);
            // $("#testsQCTable").jqGrid('setSelection',testId);
            // $("#testsQCTable").resetSelection();
            $(this).html('');
        }
    }).load(
        'previewTest.htm?&selectedTestCollectionId=' + testCollectionId + '&selectedTestId=' + testId,
        function() {
        	reRenderMathJax();
            scrollPreviewTop();
        }).dialog('open');
    	$('.ui-dialog-titlebar-close').show();
    if ($('a[data-id="' + itId + '"]').length > 0) {
        $('.ui-widget-header .ui-dialog-title#ui-id-2').html(
            $('a[data-id="' + itId + '"]').data('testname'));
    } else {
        $('.ui-widget-header .ui-dialog-title#ui-id-2').html(
            $('#PreviewLink').data('name'));
    }
    $('#previewQCTestDiv').parent('div').css('border', '4px solid #d8d8d8');
}

function scrollPreviewTop() {
    $('#previewQCTestDiv').scrollTop(1);
}

$('#preview-close').on('click', function(ev) {
    $('#previewTestDiv').dialog('close');
});


$('.preview-test').on('click',
    function(ev) {
        ev.preventDefault();
        var testId = $(this).data('id');
        var testCollectionId = $(this).data('testcollectionid');
        $('#previewQCTestDiv').dialog({
            autoOpen: false,
            modal: true,
            width: 1080,
            height: 655,
            title: '',
            close: function(ev, ui) {
                $(this).html('');
            }
        }).load(
            'previewTest.htm?&selectedTestCollectionId=' + testCollectionId + '&selectedTestId=' + testId,
            function() {
            	reRenderMathJax();
                scrollPreviewTop();
            }).dialog('open');
        $('.ui-widget-header .ui-dialog-title#ui-id-2').html(
            $(this).closest('a').data('name'));
        $('#previewQCTestDiv').parent('div').css('border',
            '4px solid #d8d8d8');

    });

function monitorInterimTest(event, tsId, tsName) {
	
	if(event.type=='keypress'){
		 if(event.which !=13){
			return false;
		 }
	}	
	
    testSessionId = tsId;
    testSessionName = tsName;
    $('#monitorTestDiv').dialog({
        autoOpen: false,
        modal: true,
        width: 1080,
        height: 700,
        title: 'Monitor - ' + testSessionName,
        close: function(ev, ui) {
            $('#arts_monitorTestSession').jqGrid("GridDestroy");
            $(this).html('');
        }
    }).load('monitorInterimTestSession.htm?&testSessionId=' + encodeURIComponent(testSessionId) + '&testSessionName=' + encodeURIComponent(testSessionName) + '&testingProgram=Interim',
        function() {
            loadMonitorTestSessionData();
            $('#monitorTestDiv').dialog('open');
        });


}






function loadeeditgroupjqgrid(data) {
        $("#studentGroupEditTable").jqGrid({
            datatype: "local",
            data: data,
            colModel: [{
                label: 'id',
                name: 'id',
                width: 30,
                key: true,
                hidden: true,
                searchoptions : {title:"ID"}
            }, {
                label: 'First Name',
                name: 'legalFirstName',
                width: 75,
                align: 'center',
                searchoptions : {title:"First Name"}
            }, {
                label: 'Last Name',
                name: 'legalLastName',
                width: 90,
                align: 'center',
                searchoptions : {title:"Last Name"}
            }],
            scrollerbar: false,
            loadonce: true,
            shrinkToFit: true,
            viewrecords: true,
            width: 430,
            height: 200,
            rowNum: 10,
            rowList: [10, 20, 30, 40, 60, 90 ],
            page: 1,
            rownumbers: true,
            rownumWidth: 50,
            multiselect: true,
            scrollOffset: 0,
            altRows: true,
            hoverrows: true,
            altClass: 'altrow',

            pager: "#studentGroupEditTablePager",
            loadComplete: function (data) {
    	    	var ids = $(this).jqGrid('getDataIDs');         
    	         var tableid=$(this).attr('id');      
    	            for(var i=0;i<ids.length;i++)
    	            {         
    	                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'legalFirstName') +' '+$(this).getCell(ids[i], 'legalLastName')+ ' Check Box');
    	            }
    	            $('#jqg_'+tableid+'_'+ids[i]).removeAttr('aria-checked');	
    	            $('#cb_'+tableid).attr('title','User Grid All Check Box');
            }
        });
        $("#studentGroupEditTable").closest('.ui-jqgrid-bdiv').width($("#studentGroupEditTable").closest('.ui-jqgrid-bdiv').width());
        $("#studentGroupEditTable").jqGrid('filterToolbar', {
            stringResult: true,
            searchOnEnter: false,
            defaultSearch: "cn"
        });
        $('tr.ui-search-toolbar input').css('width', '100%');
        $('.ui-jqgrid .ui-jqgrid-btable').css('table-layout', 'auto');
        $('#studentGroupEditTable input').trigger('click')
        $('#studentGroupEditTable input').prop('checked', true);
        $('#cb_studentGroupEditTable').prop('checked', true);
        $('.ui-jqgrid-hbox').css({
            "padding-right": "0"
        });
        $('.ui-jqgrid-bdiv').css({
            'overflow-x': 'hidden'
        });

    }



// This is for test assign for individual students
$('#js-assigntostudents').on('click', function(ev) {
    ev.preventDefault();
    var object = {};
    $.ajax({
        url: 'getGroups.htm',
        data: {
            studentIds: object.groupName,
            interimTestId: object.userName,
            startDate: startDate,
            startTime: startTime,
            endDate: endDate,
            endTime: endTime
        },
        dataType: 'json',
        type: "GET"
    }).done(function(data) {
        });
})

$('#whygroups').on('click',function(ev) {
        ev.preventDefault();
        $('#why-group-creation').dialog({
            autoOpen: false,
            modal: true,          
            width: 500,
            height: 320,
            title: "",
            create: function(event, ui) {
                var widget = $(this).dialog("widget");
                $(".ui-dialog-titlebar-close span", widget).prop('id', 'editUserDialogClose');
            },
            close: function(ev, ui) {}
        }).dialog('open');
        // $('#why-group-creation').modal('show');
    });

$('.group-okay').on('click', function(ev) {
    ev.preventDefault();
    $('#why-group-creation').dialog('close');
});

$('#alignmentHelp').on('click',function(ev) {
        ev.preventDefault();
        $('#alignment-help-creation').dialog({
            autoOpen: false,
            modal: true,          
            width: 600,
            height: 400,
            title: "",
            create: function(event, ui) {
                var widget = $(this).dialog("widget");
                $(".ui-dialog-titlebar-close span", widget).prop('id', 'editUserDialogClose');
            },
            close: function(ev, ui) {}
        }).dialog('open');
        // $('#why-group-creation').modal('show');
    });

$('.alignment-okay').on('click', function(ev) {
    ev.preventDefault();
    $('#alignment-help-creation').dialog('close');
});




$('#whatSchedule').on('click',function(ev) {
        ev.preventDefault();
        $('#what-Schedule').dialog({
            autoOpen: false,
            modal: true,
            width: 500,
            height: 320,
            title: "",
            create: function(event, ui) {
                var widget = $(this).dialog("widget");
                $(".ui-dialog-titlebar-close span", widget).prop('id', 'editUserDialogClose');

            },

            close: function(ev, ui) {
            }
        }).dialog('open');
        // $('#what-Schedule').modal('show');
    });

$('.group-okay').on('click', function(ev) {
    ev.preventDefault();
    $('#what-Schedule').dialog('close');
});

$('#completeScore').on('click', function(ev) {
    ev.preventDefault();
    $('#completeScoress').dialog({
        autoOpen: false,
        modal: true,
        width: 1080,
        height: 655,
        title: '',
        close: function(ev, ui) {
            $(this).html('');
        }
    }).dialog('open');
    // $('#completeScoress').modal('show');
});

var t;
$(document).on('DOMMouseScroll mousewheel scroll', '#scheduleScreen',
    function() {
        window.clearTimeout(t);
        t = window.setTimeout(function() {
            $('#js-start-date,#js-end-date').datepicker('hide')
            $('#js-start-time,#js-end-time').timepicker('hide')
            $('#js-start-date,#js-end-date').datepicker('place')
            $('#js-start-time,#js-end-time').timepicker('place')
        }, 100);
    });

$('.group-okayScore').on( 'click', function(ev) {
    ev.preventDefault();
    $('#completeScoress').dialog('close');

});

var oldFrom = $.jgrid.from,
    lastSelected;

$.jgrid.from = function(source, initalQuery) {
    var result = oldFrom.call(this, source, initalQuery),
        old_select = result.select;
    result.select = function(f) {
        lastSelected = old_select.call(this, f);
        return lastSelected;
    };
    return result;
};

function reRenderMathJax(){
	// Find mathMl rows that contain bare text and wrap them in <mi> tags. Prevents 'unexpected text node' errors
	$('mrow').contents().filter(function(){ 
		return (this.nodeType == 3 && this.nodeValue.trim() !== ''); 
		}).wrap( "<mi></mi>" );
		MathJax.Hub.Queue(["Typeset", MathJax.Hub]);
}

function viewEditFeedback(QandAData){
	$('#feedbackdiv').html(new EJS({
		url : '/AART/js/views/feedbackQ.ejs'
	}).render({QandAData: QandAData}));
	
	// Populate previous answers
	for(var i = 0; i < QandAData.length; i++){
		var divName = "feedbackq_"+QandAData[i].questionId;
		switch(QandAData[i].taskType){
			case "CR":
				if(QandAData[i].response != null){
					$('#input_'+QandAData[i].questionId).val([QandAData[i].response]);
				}
				break;
			case "ER":
				if(QandAData[i].response != null){
					$('#input_'+QandAData[i].questionId).val([QandAData[i].response]);
				}
				break;
			case "MC-K":
				if(QandAData[i].response != null){
					$('input:radio[name="input_'+QandAData[i].questionId+'"]').val([QandAData[i].response]);
				}
				break;
			default:
				break;
		}
	}
	
	var feedbackDialog = $('#feedbackdiv').dialog({
		autoOpen: false,
		modal: true,
		resizable: true,
		width: 750,
        height: 600,
		title: "Educator Feedback",
		create: function(event, ui) { 
		    var widget = $(this).dialog("widget");
		    $(".ui-dialog-titlebar-close span", widget).attr('title','Close');
		},
		close: function(ev, ui) {				
		    $(this).html('');
		},
		open: function(ev, ui){
			$('#feedback_cancel').off('click').on('click',function(ev){
		    	feedbackDialog.dialog('close');
		    });
		}
	}).dialog('open');
	
	$('#feedback_submit').off('click').on('click', function(){
		$('#feedback_required_missing').hide();
		if(!feedback_form.checkValidity()){
			$('#feedback_required_missing').show();
		}else{
			$('#feedback_required_missing').hide();
			var rawResponses = $('#feedback_form').serializeArray();
			var formattedResponses = [];
			
			var testletFeedbackId = $.grep(rawResponses, function (element, index) {
    		    return element.name == 'testletFeedbackId';
    		});
			testletFeedbackId = (testletFeedbackId != null && testletFeedbackId.length > 0 && testletFeedbackId[0].value != 'undefined') ? testletFeedbackId[0].value : null;

			var responseIds = $.grep(rawResponses, function (element, index) {
				return element.name.startsWith("input_responseId_");
    		});
			
			var responseValues = $.grep(rawResponses, function (element, index) {
				if(element.name.startsWith("input_")){
					if(element.name.startsWith("input_responseId_")){
						return false
					}else{
						return true;
					}
				}else{
					return false;
				}
    		});
			
			for(var i = 0; i < responseValues.length; i++){
				var aFormattedResponse = {};
				
				var questionId = responseValues[i].name.substr(6);
				
				var responseIdVal = $.grep(responseIds, function (element, index) {
					return element.name == ("input_responseId_" + questionId);
	    		});
				
				aFormattedResponse.testletFeedbackId = testletFeedbackId;
				aFormattedResponse.responseId = (responseIdVal[0].value === 'null') ? null : responseIdVal[0].value;
				aFormattedResponse.feedbackQuestionId = questionId;
				aFormattedResponse.response = responseValues[i].value;
				
				formattedResponses.push(aFormattedResponse);
			}
			
			$.ajax({
		        url: 'saveInterimFeedback.htm',
		        data: JSON.stringify(formattedResponses),
		        dataType: 'json',
		        contentType: 'application/json; charset=utf-8',
		        type: 'POST'
			}).done(function(status) {
		        	if(status){
		        		getFeedbackQuestions($('#previewQCTestDiv').data('testid'));
		        		feedbackDialog.dialog('close');
	        		}
		        });
			
		}
	});
}

function reassignInterim(interimTestId, newTestIds){
	$.ajax({
        url: 'reassignInterimTest.htm',
        data: {
        	interimTestId: interimTestId,
        	newTestIds: newTestIds
        },
        contentType: 'application/x-www-form-urlencoded',
        type: 'POST'
	}).done(function(data){
		console.log(data);
	}).fail(function(jqXHR, textStatus, errorThrown){
		console.log(jqXHR.status);
	});
}
