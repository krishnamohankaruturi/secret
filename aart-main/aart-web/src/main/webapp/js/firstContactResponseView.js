var i = 0;
var s = 0;
var currentPageLabels = [];
var answeredPageLabels = [];
var skipPageLabels = [];
var inActivePageLabels = [];
var isPreviousButtonClick = false;
var selectedRanks = [];
var existingRank = "";
var surveyLabelPrerequisites = "";
var lastPageTobeSaved = 0;
var labelNumbers = [];
var surveyResponseIds = [];
var surveyResponseTexts = [];
var pagesArray = [];
var nonMandatoryPage = true;
var answeredAll = false;
var notCompletedArray = [];
var currentTab = 0;
var currentSectionPage = 0;
var allQuestionsRequired = false;
var firstContactResponseViewOnly = false;
var disabledAttribute = '';
var answeredNon = false;

function initializeFirstContactSurvey() {
	if((typeof(editFirstContactSurvey) !== 'undefined' && editFirstContactSurvey))
	{
		firstContactResponseViewOnly = false;
		$("#editSurveyButton").val('Edit Survey');
	}
	else if((typeof(viewFirstContactSurvey) !== 'undefined' && viewFirstContactSurvey))
	{
		firstContactResponseViewOnly = true;
		disabledAttribute = "disabled='disabled'";
		$("#editSurveyButton").val('View Survey');
		$(".preSubmitSurveyMessage").hide();
		$(".postSubmitSurveyMessage").hide();
		$("#firstContactResponseTabs").find('.fcsSectionContent').css('background-color', '#e7e7e7');
	}
	
	allQuestionsRequired = $('#allQuestionsRequired').val();
	pagesArray = [];
	// Populate all the active page numbers here.
	var array = $('#globalPageNumList').val();
	array = array.replace('[', '');
	array = array.replace(']', ''); 
	array = array.split(",");

	for (var i in array){
		pagesArray.push(parseInt(array[i]));
	}

	$('.submitSurvey').hide();
	$("#firstContactPageDiv").parents().removeClass("wrap_bcg");

	// Set the pageNumber to 0 as we haven't started loading the section-wise
	// labels/questions.
	if (($('#editSurveyCheck').val()) == "true") {
		$('.page0').css('background-color', '#0e69fd');
	}

	// Setting up the tabs.
	$('#firstContactResponseTabs')
			.tabs(
					{
						beforeActivate : function(event, ui) { 
							var sectionToShow;
							if (isPreviousButtonClick) {
								sectionToShow = $('#tabs_' + ui.newTab.index()).find('.fcsSectionContainer').last();
								$(sectionToShow).show();
							} else {
								// Find first section of the page and show it
								// when button click is next
								$('#firstContactResponseTabs').find('.fcsSectionContainer').hide();
								sectionToShow = $('#tabs_' + ui.newTab.index()).find('.fcsSectionContainer').first();
								$(sectionToShow).show();
							}

							// currentSectionPage holds the first page of the
							// current tab selected
							currentSectionPage = $(sectionToShow).data('pagenumber');
							
							var editSurveyCheck = $('#editSurveyCheck').val();

							checkPageCompletion();

							if (!isPreviousButtonClick) {
													
								if (!answeredAll &&  !firstContactResponseViewOnly) {
									$('#section' + lastPageTobeSaved).find('.selectAllLabelsFCS').removeClass('hidden');
									currentSectionPage = lastPageTobeSaved;
									$('#section' + currentSectionPage).find('.fcsSectionContent').parent().show();
									return false;
								} else {
									if (currentSectionPage === $('#noOfSubSections').val() && notCompletedArray.length > 0) {
										if (!(($.inArray(parseInt(currentSectionPage), notCompletedArray) == 0) && notCompletedArray.length == 1)) {
											if (ui.newTab.index() == 8) {
												$('#section' + lastPageTobeSaved).find('.selectAllLabelsFCS').removeClass('hidden');
												return false;
											}
										}
									}
									$('#section' + lastPageTobeSaved).find('.selectAllLabelsFCS').addClass('hidden');
								}
							} else {
								$('#section' + lastPageTobeSaved).find('.selectAllLabelsFCS').addClass('hidden');
							}

							// To show the FCS top header in all pages except
							// welcome page.
							if (ui.newTab.index() == 0) {
								$('#welcomePageContent').hide();
							} else {
								$('#welcomePageContent').show();
							}

							// Find the fcsSectionContentContainer and save the
							// page navigated from.
							var firstContactContentContainer = '';
							firstContactContentContainer = $('#section' + currentSectionPage).find('.fcsSectionContent').attr(
									'id');

							saveFirstContactResponses(lastPageTobeSaved, currentSectionPage, firstContactContentContainer);

							if (ui.newTab.index() == 0) {
								currentSectionPage = 0;
								// reset global variables, to prevent being
								// locked
								initializePageLabelArrays();
							}

							// should reset here to navigate to previous page on
							// previous tab.
							isPreviousButtonClick = false;
							lastPageTobeSaved = currentSectionPage;
							return true;
						}
					});

	// Called during the survey initial loadiing.
	$.ajax({
		url : 'checkSurveyCompletion.htm',
		data : {
			surveyId : $('#surveyId').val()
		},
		dataType : 'json',
		type : "GET"
	}).done(function(response) {
		// disable all tabs initially including start & complete tabs.
		var radix = 10;
		var totalTabs = parseInt($('#noOfRootSections').val(), radix);
		for (var i = 0; i <= (totalTabs + 1); i++) {
			$('#firstContactResponseTabs').tabs('disable', i);
		}
		if (response.length < $('#noOfRootSections').val()) {
			// enable only when any page under a tab is completed.
			for (var i = 0; i < response.length; i++) {
				$('#firstContactResponseTabs').tabs('enable', response[i]);
			}
		} else {
			// enable all tabs if all pages are completed.
			for (var i = 0; i <= (totalTabs + 1); i++) {
				$('#firstContactResponseTabs').tabs('enable', i);
			}
		}
	});

	$("#editSurveyButton").hide();
	$("#startSurveyButton").hide();
	if (($('#editSurveyCheck').val()) == "true") {
		$("#editSurveyButton").show();
		updateSurveyPageStatus(0);
	} else {
		$("#startSurveyButton").show();
	}

	// Submit survey
	$('.submitSurvey').on("click",function(event) {
		var lastPage = parseInt($('#fcsLastPageId').val());
		$('.lastPage' + lastPage).css('background-color', '#0e69fd');

		$.ajax({
			url : 'createTestSessionForFirstContact.htm',
			data : {
				surveyId : $('#surveyId').val()
			},
			dataType : 'json',
			type : "POST"
		}).done(function(response) {
			$('#itifirstContactViewDiv').dialog("close");
			$("#submitSurveyButton").hide();
			$(".subsectionTitleComplete").text("Complete")
			$(".preSubmitSurveyMessage").hide();
			$(".postSubmitSurveyMessage").show();
		});
	}); // end of submitSurvey

	// Enabling the correct page/tab on clicking Next button.
	$('.fcsNextButton').on("click",function(event) {
		if('editSurveyButton'===$(this).attr('id')){
			initializePageLabelArrays();
		}
		// navigational logic for next/start survey/edit survey button's click;
		var tabid = $(this).data('tabid');
		currentTab = tabid;
		var section = $(this).data('pageindex');
		var nextSectionId = parseInt(section) + 1;
		for(var i=0;i<=pagesArray.length;i++){
			if(i == section){
				nextSectionId = pagesArray[i];
			}
		}
		var nextTabId = $('#section' + nextSectionId).data('tabid');
		$('#section' + parseInt($(this).data('pagenumber'))).hide();
		//$('#section' + nextSectionId).show();
		// Whenever Next Clicked Clearing the Value
		isPreviousButtonClick = false;
		$(this).prop('disabled', true);

		var $tabs = jQuery('#firstContactResponseTabs');
		var tabsLength = $tabs.find('.ui-tabs-panel').length;
		var selected = $tabs.tabs('option', 'active');
		var isTabChange = false;
		if (tabid != nextTabId) {
			isTabChange = true;
			$tabs.tabs('enable', selected + 1).tabs('option', 'active', selected + 1 === tabsLength ? 0 : selected + 1);
		}

		checkPageCompletion();

		$('.page0').css('background-color', '#0e69fd');

		var editSurveyCheck = $('#editSurveyCheck').val() == 'true';

		// Navigate to next page, only if the user has answered all the
		// labels/questions that doesn't have prerequisites.
		if (answeredAll || firstContactResponseViewOnly) {
			$(this).parent().parent().parent().find('.selectAllLabelsFCS').addClass('hidden');

			var firstContactContentContainer = '';
			firstContactContentContainer = $('#section' + nextSectionId).find('.fcsSectionContent').attr('id');

			if (!isTabChange && firstContactContentContainer !== '') {
				saveFirstContactResponses(parseInt($(this).data('pagenumber')), nextSectionId, firstContactContentContainer);
				lastPageTobeSaved = nextSectionId;
				currentSectionPage = nextSectionId;
			}
		}
	}); // end of fcsNextButton event

	// Enabling the correct page/tab on clicking previous button.
	$('.fcsPreviousButton').on("click",function(event) {
				// navigational logic for previous button click;
				var tabid = $(this).data('tabid');
				currentTab = tabid;
				var section = $(this).data('pageindex');
				var previousSectionId = parseInt(section) - 1;
				for(var i=0;i<=pagesArray.length;i++){
					if(pagesArray[i] == parseInt($(this).data('pagenumber'))){
						previousSectionId = pagesArray[i-1];
					}
				}
				var previousTabId = $('#section' + previousSectionId).data('tabid');
				$('#section' + parseInt($(this).data('pagenumber'))).hide();
				$('#section' + previousSectionId).show();

				isPreviousButtonClick = true;
				
				if(previousSectionId == 0) {
					if($('#editSurveyCheck').val() == "true")
					$('#editSurveyButton').prop('disabled', false);
					else 
					$('#startSurveyButton').prop('disabled', false);
				}
				
				// When the user moves back, the required questions validation
				// should fire.
				answeredAll = ($(currentPageLabels).not(answeredPageLabels).length == 0 && $(answeredPageLabels).not(
						currentPageLabels).length == 0);

				enableFCSNextButton();
				$(this).parent().parent().parent().parent().find('.selectAllLabelsFCS').addClass('hidden');

				var $tabs = jQuery('#firstContactResponseTabs');
				var tabsLength = $tabs.find('.ui-tabs-panel').length;
				var selected = $tabs.tabs('option', 'active');
				var isTabChange = false;
				if (tabid != previousTabId) {
					isTabChange = true;
					$tabs.tabs('enable', selected - 1).tabs('option', 'active', (selected - 1) === tabsLength ? 0 : selected - 1);
				}

				var editSurveyCheck = $('#editSurveyCheck').val() == 'true';

				// load events for nested questions these page numbers are
				// between the tab triggers
				var firstContactContentContainer = '';
				firstContactContentContainer = $('#section' + previousSectionId).find('.fcsSectionContent').attr('id');
				if (!isTabChange && firstContactContentContainer !== '') {
					saveFirstContactResponses(parseInt($(this).data('pagenumber')), previousSectionId, firstContactContentContainer);
					lastPageTobeSaved = previousSectionId;
					currentSectionPage = previousSectionId;
				}
			}); // end of fcsPreviousButton event
}

// ajax call to get the section-wise labels and responseoptions along with
// student responses.
function updateSurveyPageStatus(pageNumber) {
//	loadFirstContactSectionData(0, 0, "", "complete");
	pageNumber = $('#section' + pageNumber).data('pagenumber');
	var updatedPageNumber = pageNumber;
	var lastPageId = parseInt($('#fcsLastPageId').val());
	if(!firstContactResponseViewOnly || pageNumber === 0){
		$.ajax({
			url : 'updateSurveyPageStatus.htm',
			data : {
				surveyId : $('#surveyId').val(),
				pageNumber : updatedPageNumber
			},
			dataType : 'json',
			type : "GET"
		}).done(function(response) {
			// Method call to update page status when displaying
			// labels/questions for each page.
			updatePageStatus(response);
		});
	}
}

function updateSurveyPageStatusToIncomplete(pageNumber) {
	pageNumber = $('#section' + pageNumber).data('pagenumber');
	var pageStatus = answeredAll ? "complete" : "incomplete";
	$.ajax({
		url : 'updateCurrentSurveyPageStatus.htm',
		data : {
			surveyId : $('#surveyId').val(),
			pageNumber : pageNumber,
			status: pageStatus
		},
		dataType : 'json',
		type : "GET"
	}).done(function(response) {
		// Method call to update page status when displaying
		// labels/questions for each page.
		updatePageStatus(response);
		var lastTabId = parseInt($('#noOfRootSections').val(), 10) + 1;
		$('#firstContactResponseTabs').tabs('disable', lastTabId);
	});
}

// Method to update page status when displaying labels/questions for each page.
function updatePageStatus(response) {
	for (var s = 0; s < response.surveyPageStatusList.length; s++) {
		$('.page' + response.surveyPageStatusList[s]).css('background-color', '#0e69fd');
	}
	notCompletedArray = _.difference(pagesArray, response.surveyPageStatusList);
	for (var k = 0; k < notCompletedArray.length; k++) {
		$('.page' + notCompletedArray[k]).css('background-color', '#FDA20E');
	}
	answeredAll = ($(currentPageLabels).not(answeredPageLabels).length == 0 && $(answeredPageLabels).not(currentPageLabels).length == 0);
	var currentPageNumber = currentSectionPage;
	var lastTabId = parseInt($('#noOfRootSections').val(), 10) + 1;
	var lastPageId = parseInt($('#fcsLastPageId').val());
	if (notCompletedArray && notCompletedArray.length > 0) {
		$('.page' + lastPageId).css('background-color', '#FDA20E');
		$('#firstContactResponseTabs').tabs('disable', lastTabId);
	} else {
		$('#firstContactResponseTabs').tabs('enable', lastTabId);
	}

	var totalPages = parseInt($('#noOfSubSections').val(), 10);
	// "complete" and "ready to submit" state toggle logic
	if (response.surveyPageStatusList.length >= totalPages) {
		if (response.surveyStatus === 'COMPLETE') {
			$(".subsectionTitleComplete").text("Complete");
			if(!firstContactResponseViewOnly){
				$(".postSubmitSurveyMessage").show();
			}
			$(".preSubmitSurveyMessage").hide();
			$('.submitSurvey').hide();
			$('.lastPage' + lastPageId).css('background-color', '#0e69fd');
		} else {
			$(".subsectionTitleComplete").text("Ready to Submit");
			$(".postSubmitSurveyMessage").hide();
			if(!firstContactResponseViewOnly){
				$(".preSubmitSurveyMessage").show();
			}
			$('.submitSurvey').show();
			$('.lastPage' + lastPageId).css('background-color', '#FDA20E');
		}
	}else{
		$('.submitSurvey').hide();
	}
}

// ajax call to get the section-wise labels and responseoptions along with
// student responses.
function loadFirstContactSectionData(lastPageToBeSaved, pageNumber, firstContactContentContainer, pageStatus) {
	$.ajaxSetup({
		cache : false
	});
	
	$.ajax({
			url : 'getFirstContactResponses.htm',
			data : {
				studentId : $('#fcrvStudentId').val(),
				surveyId : $('#surveyId').val(),
				lastPageToBeSaved : $('#section' + lastPageToBeSaved).data('pagenumber'),
				pageNumber : $('#section' + pageNumber).data('pagenumber'),
				status : pageStatus
			},
			dataType : 'json',
			type : "GET"
		}).done(function(response) {
			$('#surveyId').val(response.surveyId);
			buildFirstContactSectionPage(response, firstContactContentContainer);
			var totalPages = parseInt($('#noOfSubSections').val(), 10);
			//DE15248 - FIX
			if(currentSectionPage === 9){
				if($(currentPageLabels).not(inActivePageLabels).length === 0 && $(inActivePageLabels).not(currentPageLabels).length === 0
					&& answeredAll && !isPreviousButtonClick){
					$('#section'+currentSectionPage).find('.fcsNextButton').click();
				} else if($(currentPageLabels).not(inActivePageLabels).length === 0 && $(inActivePageLabels).not(currentPageLabels).length === 0
					&& answeredAll && isPreviousButtonClick){
					$('#section'+currentSectionPage).find('.fcsPreviousButton').click();
				}
			}
			$('#section' + currentSectionPage).show();
		});

}

function buildFirstContactSectionPage(response, firstContactContentContainer) {
	var htmlString = "";
	var labelNumber = "";
	var textresponse = "";
	var studentSurveyResponseLabels = "";
	var surveyQuestionsDisplayTypeValues = "";
	var surveyQuestionsDisplayType = "";
	var surveyPageStatusList = "";
	var displayType = "";
	nonMandatoryPage = true;
	initializePageLabelArrays();

	studentSurveyResponseLabels = response.studentSurveyResponseLabels;
	surveyQuestionsDisplayTypeValues = response.surveyQuestionsDisplayTypeValues;
	surveyPageStatus = response.prevoiusPageStatus;
	surveyLabelPrerequisites = response.surveyLabelPrerequisites;
	surveyPageStatusList = response.surveyPageStatusList;

	// Method call to update page status when displaying labels/questions for
	// each page.
	updateResponsesArray = surveyPageStatusList;
	updatePageStatus(response);
	//DE15248 - FIX
	if(!($(currentPageLabels).not(inActivePageLabels).length === 0 && $(inActivePageLabels).not(currentPageLabels).length === 0
		&& $(answeredPageLabels).length ===0 && answeredAll && !isPreviousButtonClick)
		||
		!($(currentPageLabels).not(inActivePageLabels).length === 0 && $(inActivePageLabels).not(currentPageLabels).length === 0
				&& $(answeredPageLabels).length ===0 && answeredAll && isPreviousButtonClick)){
	for (i = 0; i < studentSurveyResponseLabels.length; i++) {
		if (!studentSurveyResponseLabels[i].optional) {
			nonMandatoryPage = false;
		}
		answeredNon = (notCompletedArray.indexOf(6) >= 0 && notCompletedArray.indexOf(7) >= 0 &&  notCompletedArray.indexOf(8) >= 0);
		notCompletedArray = _.difference(pagesArray, response.surveyPageStatusList);
		for (var k = 0; k < notCompletedArray.length; k++) {
			$('.page' + notCompletedArray[k]).css('background-color', '#FDA20E');
		}

		displayType = studentSurveyResponseLabels[i].labelType;

		// Code to display textarea if the label/question type is text.
		if (displayType != null && displayType == "text") {

			htmlString = buildTextLabels(studentSurveyResponseLabels, htmlString);

		} else if (displayType != null && displayType == "twodimentional") {

			// Code to display twodimentional labels/questions.
			htmlString = buildTwoDimentionalLabels(studentSurveyResponseLabels, labelNumber, htmlString);

		} else if (displayType != null && displayType == "radiobutton") {
			// Code to display radiobutton for labels/questions along with
			// response options.

			if (labelNumber != studentSurveyResponseLabels[i].labelNumber) {

				labelNumber = studentSurveyResponseLabels[i].labelNumber;

				// Code to display the labels for non-two dimentional
				// labels(questions).
				htmlString += "<div class='labelDiv " + studentSurveyResponseLabels[i].surveyLabelId;
				if ((studentSurveyResponseLabels[i].hasPreRequisite && studentSurveyResponseLabels[i].metPreRequisite)
						|| (!studentSurveyResponseLabels[i].hasPreRequisite && !studentSurveyResponseLabels[i].metPreRequisite)) {
					htmlString += "'>";
				} else {
					if((studentSurveyResponseLabels[i].hasPreRequisite && !studentSurveyResponseLabels[i].metPreRequisite)
						|| !firstContactResponseViewOnly){
						if (firstContactResponseViewOnly
								&& firstContactContentContainer == "SectionContent_9") {
							if ((studentSurveyResponseLabels[i].hasPreRequisite && !studentSurveyResponseLabels[i].metPreRequisite)
									&& answeredNon) {

							} else {
								htmlString += " hidden ";
							}

						} else {
							htmlString += " hidden ";
						}
					}
					htmlString += " '>";
					if(allQuestionsRequired == 'false'){
						if (answeredPageLabels.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1) {
							if(studentSurveyResponseLabels[i].studentResponseId != null && studentSurveyResponseLabels[i].studentResponseId != ""
								&& studentSurveyResponseLabels[i].activeFlag){
								answeredPageLabels.push(studentSurveyResponseLabels[i].labelNumber);
							}
						}
					}
					
					if (answeredPageLabels.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1) {
						inActivePageLabels.push(studentSurveyResponseLabels[i].labelNumber);
					}
				}
				htmlString += "<label>" + studentSurveyResponseLabels[i].label + "</label>";
				htmlString = renderMandatoryFlag(studentSurveyResponseLabels[i].optional, htmlString);
				htmlString += "</div> ";

				if(currentPageLabels.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1){
					currentPageLabels.push(studentSurveyResponseLabels[i].labelNumber);
				}
				if (studentSurveyResponseLabels[i].hasPreRequisite) {
					if (!studentSurveyResponseLabels[i].optional) {
						if(skipPageLabels.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1){
							skipPageLabels.push(studentSurveyResponseLabels[i].labelNumber);
						}
					}
				}
			}

			// Code to display response options.
			htmlString += "<div class='labelOptionsDiv " + studentSurveyResponseLabels[i].surveyLabelId;

			if ((studentSurveyResponseLabels[i].hasPreRequisite && studentSurveyResponseLabels[i].metPreRequisite)
					|| (!studentSurveyResponseLabels[i].hasPreRequisite && !studentSurveyResponseLabels[i].metPreRequisite)) {
				htmlString += "'>";
			} else {
				if((studentSurveyResponseLabels[i].hasPreRequisite && !studentSurveyResponseLabels[i].metPreRequisite)
						|| !firstContactResponseViewOnly){
					if (firstContactResponseViewOnly
							&& firstContactContentContainer == "SectionContent_9") {
						if ((studentSurveyResponseLabels[i].hasPreRequisite && !studentSurveyResponseLabels[i].metPreRequisite)
								&& answeredNon) {

						} else {
							htmlString += " hidden ";
						}
					} else {
						htmlString += " hidden ";
					}
				}
				htmlString += " '>";
			}

			htmlString += "<input type='radio' title='"+studentSurveyResponseLabels[i].responseValue+"' class = '" + studentSurveyResponseLabels[i].surveyLabelId + "' name='"
					+ studentSurveyResponseLabels[i].labelNumber + "' id ='" + studentSurveyResponseLabels[i].labelNumber +"_"+studentSurveyResponseLabels[i].surveyResponseId
					+ "' onclick ='getRadioButtonData(\"" + labelNumber + "\", \""
					+ studentSurveyResponseLabels[i].surveyResponseId + "\", \"" + studentSurveyResponseLabels[i].hasPreRequisite
					+ "\", \"" + studentSurveyResponseLabels[i].isPreRequisite + "\",\""
					+ escape(JSON.stringify(studentSurveyResponseLabels)) + "\",\"" + studentSurveyResponseLabels[i].optional
					+ "\",event)' "
					+ "onkeypress ='getRadioButtonData(\"" + labelNumber + "\", \""
					+ studentSurveyResponseLabels[i].surveyResponseId + "\", \"" + studentSurveyResponseLabels[i].hasPreRequisite
					+ "\", \"" + studentSurveyResponseLabels[i].isPreRequisite + "\",\""
					+ escape(JSON.stringify(studentSurveyResponseLabels)) + "\",\"" + studentSurveyResponseLabels[i].optional
					+ "\",event)' "
					+"value='"
					+ studentSurveyResponseLabels[i].surveyResponseId + "' style='float:left; margin-left:1px; margin-top:6px;'"
					+ disabledAttribute;

			if (studentSurveyResponseLabels[i].studentResponseId != null
					&& studentSurveyResponseLabels[i].studentResponseId != ""
					&& studentSurveyResponseLabels[i].activeFlag == true) {
				htmlString += " checked='checked' style='float:left; margin-left:1px'";
				if (answeredPageLabels.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1) {
					answeredPageLabels.push(studentSurveyResponseLabels[i].labelNumber);
				}
			}
			// Making Radio Button Questions Optional.
			if (studentSurveyResponseLabels[i].optional) {
				if (answeredPageLabels.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1) {
					answeredPageLabels.push(studentSurveyResponseLabels[i].labelNumber);
				}
			}

			htmlString += " />";
			htmlString += "<div  class='lableOptions'style='width:86%; display: inline-block; margin-top:1px'>"
					+ studentSurveyResponseLabels[i].responseValue;
			htmlString += "</div>"
			htmlString += " </div> ";

		} else if (displayType != null && displayType == "radiobuttonandtext") {
			// Code to display radiobutton and textbox for labels/questions
			// along with response options.

			if (studentSurveyResponseLabels[i].optional) {
				if (answeredPageLabels.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1) {
					answeredPageLabels.push(studentSurveyResponseLabels[i].labelNumber);
				}
			}

			if (labelNumber != studentSurveyResponseLabels[i].labelNumber
					&& (labelNumber.substring(0, labelNumber.indexOf('_')) != studentSurveyResponseLabels[i].labelNumber.substring(0, studentSurveyResponseLabels[i].labelNumber.indexOf('_')) 
						&& studentSurveyResponseLabels[i].labelNumber.substring(4) != 'TEXT')) {
				if(currentPageLabels.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1){
					currentPageLabels.push(studentSurveyResponseLabels[i].labelNumber);
				}
				labelNumber = studentSurveyResponseLabels[i].labelNumber;

				htmlString += "<div class='labelDiv'>" + "<label>" + studentSurveyResponseLabels[i].label + "</label>  </div> ";
			}

			htmlString = renderMandatoryFlag(studentSurveyResponseLabels[i].optional, htmlString);

			// Code to display response options.
			htmlString += "<div class='labelOptionsDiv'>";
			if (studentSurveyResponseLabels[i].responseLabel != "TEXT") {

				htmlString += "<input type='radio' title='"+studentSurveyResponseLabels[i].responseValue+"' class = '" + studentSurveyResponseLabels[i].surveyLabelId + "' name='"
						+ studentSurveyResponseLabels[i].labelNumber + "' id ='" + studentSurveyResponseLabels[i].labelNumber+"_"+studentSurveyResponseLabels[i].surveyResponseId
						+ "' onclick ='getRadioButtonData(\"" + studentSurveyResponseLabels[i].labelNumber + "\",\""
						+ studentSurveyResponseLabels[i].surveyResponseId + "\",\""
						+ studentSurveyResponseLabels[i].hasPreRequisite + "\",\""
						+ studentSurveyResponseLabels[i].isPreRequisite + "\",\""
						+ escape(JSON.stringify(studentSurveyResponseLabels)) + "\",\"" + studentSurveyResponseLabels[i].optional
						+ "\",event)'"
						+ "onkeypress ='getRadioButtonData(\"" + studentSurveyResponseLabels[i].labelNumber + "\",\""
						+ studentSurveyResponseLabels[i].surveyResponseId + "\",\""
						+ studentSurveyResponseLabels[i].hasPreRequisite + "\",\""
						+ studentSurveyResponseLabels[i].isPreRequisite + "\",\""
						+ escape(JSON.stringify(studentSurveyResponseLabels)) + "\",\"" + studentSurveyResponseLabels[i].optional
						+ "\",event)'"
						+" value='"
						+ studentSurveyResponseLabels[i].surveyResponseId + "' style='float:left; margin-left:1px;  margin-left:1px;'"
						+ disabledAttribute;

				if (studentSurveyResponseLabels[i].studentResponseId != null
						&& studentSurveyResponseLabels[i].studentResponseId != ""
						&& studentSurveyResponseLabels[i].activeFlag == true) {
					htmlString += " checked='checked' style='float:left; margin-left:1px'";
					if (answeredPageLabels.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1) {
						answeredPageLabels.push(studentSurveyResponseLabels[i].labelNumber);
					}
				}
				htmlString += " />" + studentSurveyResponseLabels[i].responseValue;

			} else {
				if(currentPageLabels.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1){
					currentPageLabels.push(studentSurveyResponseLabels[i].labelNumber);
				}

				// Code to avoid displaying "null" for textbox on the UI.
				if (studentSurveyResponseLabels[i].studentSurveyResponseText != null
						&& studentSurveyResponseLabels[i].studentSurveyResponseText != "") {
					textresponse = studentSurveyResponseLabels[i].studentSurveyResponseText;
				} else {
					textresponse = "";
				}
				if (answeredPageLabels.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1) {
					answeredPageLabels.push(studentSurveyResponseLabels[i].labelNumber);
				}
				htmlString += "<input type='text' value='" + textresponse + "' name='"
						+ studentSurveyResponseLabels[i].labelNumber + "' id ='" + studentSurveyResponseLabels[i].labelNumber
						+ "' onblur ='getTextData(\"" + studentSurveyResponseLabels[i].surveyResponseId + "\",\""
						+ studentSurveyResponseLabels[i].labelNumber + "\")'"
						+ disabledAttribute;

				htmlString += " />" + studentSurveyResponseLabels[i].responseValue;
			}
			htmlString += " </div> ";

		} else if (displayType != null && displayType == "checkbox") {
			// Code to display checkbox for labels/questions along with response
			// options.
			if (labelNumber != studentSurveyResponseLabels[i].labelNumber) {
				// Code to display the labels for checkbox labels/questions.
				labelNumber = studentSurveyResponseLabels[i].labelNumber;

				htmlString += "<div class='labelDiv "
						+ studentSurveyResponseLabels[i].surveyLabelId;
				if ((studentSurveyResponseLabels[i].hasPreRequisite && studentSurveyResponseLabels[i].metPreRequisite)
						|| (!studentSurveyResponseLabels[i].hasPreRequisite && !studentSurveyResponseLabels[i].metPreRequisite)) {
					htmlString += "'>";
				} else {
					if ((studentSurveyResponseLabels[i].hasPreRequisite && !studentSurveyResponseLabels[i].metPreRequisite)
							|| !firstContactResponseViewOnly) {
						if (firstContactResponseViewOnly
								&& firstContactContentContainer == "SectionContent_9") {
							if ((studentSurveyResponseLabels[i].hasPreRequisite && !studentSurveyResponseLabels[i].metPreRequisite)
									&& answeredNon) {

							} else {
								htmlString += " hidden ";
							}
						} else {
							htmlString += " hidden ";
						}
					}
					htmlString += " '>";
				}
				htmlString += "<label>"
						+ studentSurveyResponseLabels[i].label.substring(0,
								studentSurveyResponseLabels[i].label.indexOf('-'))
						+ "</label>";

				htmlString = renderMandatoryFlag(
						studentSurveyResponseLabels[i].optional, htmlString);
				htmlString += "</div> ";
			}

			// Code to display response options.
			htmlString += "<div id='"
					+ labelNumber.substring(0, labelNumber.indexOf('_'))
					+ "' class='labelOptionsDiv "
					+ studentSurveyResponseLabels[i].surveyLabelId;
			if ((studentSurveyResponseLabels[i].hasPreRequisite && studentSurveyResponseLabels[i].metPreRequisite)
					|| (!studentSurveyResponseLabels[i].hasPreRequisite && !studentSurveyResponseLabels[i].metPreRequisite)) {
				htmlString += "'>";
			} else {
				if ((studentSurveyResponseLabels[i].hasPreRequisite && !studentSurveyResponseLabels[i].metPreRequisite)
						|| !firstContactResponseViewOnly) {
					if (firstContactResponseViewOnly
							&& firstContactContentContainer == "SectionContent_9") {
						if ((studentSurveyResponseLabels[i].hasPreRequisite && !studentSurveyResponseLabels[i].metPreRequisite)
								&& answeredNon) {

						} else {
							htmlString += " hidden ";
						}
					} else {
						htmlString += " hidden ";
					}

				}
				htmlString += " '>";
				if (labelNumber.substring(0, labelNumber.indexOf('_')) == -1) {
					if (inActivePageLabels.indexOf(labelNumber) == -1) {
						inActivePageLabels.push(labelNumber);
					}
				} else {
					if (inActivePageLabels.indexOf(labelNumber.substring(0, labelNumber
							.indexOf('_'))) == -1) {
						inActivePageLabels.push(labelNumber.substring(0, labelNumber
								.indexOf('_')));
					}
				}
			}

			for (; i < studentSurveyResponseLabels.length
					&& labelNumber.substring(0, labelNumber.indexOf('_')) == studentSurveyResponseLabels[i].labelNumber
							.substring(0, studentSurveyResponseLabels[i].labelNumber
									.indexOf('_')); i++) {

				htmlString += "<div > <input type='checkbox' title='"+ studentSurveyResponseLabels[i].label
				.substring(studentSurveyResponseLabels[i].label
						.indexOf('-') + 1) +"'  class = '"
						+ studentSurveyResponseLabels[i].surveyLabelId + "' name='"
						+ studentSurveyResponseLabels[i].labelNumber + "' id ='"
						+ studentSurveyResponseLabels[i].labelNumber
						+ "' onclick ='getCheckBoxData(\""
						+ studentSurveyResponseLabels[i].surveyResponseId + "\",\""
						+ studentSurveyResponseLabels[i].labelNumber + "\", \""
						+ studentSurveyResponseLabels[i].hasPreRequisite + "\", \""
						+ studentSurveyResponseLabels[i].isPreRequisite + "\",\""
						+ studentSurveyResponseLabels[i].optional + "\",event)'"
						+ "onkeypress ='getCheckBoxData(\""
						+ studentSurveyResponseLabels[i].surveyResponseId + "\",\""
						+ studentSurveyResponseLabels[i].labelNumber + "\", \""
						+ studentSurveyResponseLabels[i].hasPreRequisite + "\", \""
						+ studentSurveyResponseLabels[i].isPreRequisite + "\",\""
						+ studentSurveyResponseLabels[i].optional + "\",event)'"
						+ disabledAttribute;

				if (studentSurveyResponseLabels[i].studentResponseId != null
						&& studentSurveyResponseLabels[i].studentResponseId != ""
						&& studentSurveyResponseLabels[i].activeFlag == true) {
					htmlString += " checked='checked' ";
					var labelId = studentSurveyResponseLabels[i].labelNumber.substring(
							0, studentSurveyResponseLabels[i].labelNumber.indexOf('_'));
					if (answeredPageLabels.indexOf(labelId) == -1) {
						answeredPageLabels.push(labelId);
					}
				}

				if (!studentSurveyResponseLabels[i].optional) {
					if (skipPageLabels
							.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1) {
						skipPageLabels.push(studentSurveyResponseLabels[i].labelNumber);
					}
					var currentPagelLabel = studentSurveyResponseLabels[i].labelNumber
							.substring(0, studentSurveyResponseLabels[i].labelNumber
									.indexOf('_'));
					if (currentPageLabels.indexOf(currentPagelLabel) == -1) {
						currentPageLabels.push(currentPagelLabel)
					}
				} else {
					var currentPagelLabel = studentSurveyResponseLabels[i].labelNumber
							.substring(0, studentSurveyResponseLabels[i].labelNumber
									.indexOf('_'));
					if (currentPageLabels.indexOf(currentPagelLabel) == -1) {
						currentPageLabels.push(currentPagelLabel)
					}
					if (answeredPageLabels.indexOf(currentPagelLabel) == -1) {
						answeredPageLabels.push(currentPagelLabel);
					}
				}

				htmlString += " />"
						+ "<label>"
						+ studentSurveyResponseLabels[i].label
								.substring(studentSurveyResponseLabels[i].label
										.indexOf('-') + 1) + "</label>" + "</div> "
						+ "";
			}
			htmlString += " </div> ";
			i--;

		} else if (displayType != null && displayType == "rankdropdown") {
			// Code to display dropdown for rank values.
			htmlString = buildRankDropdown(studentSurveyResponseLabels, labelNumber, htmlString);

		} else if (displayType != null && displayType == "dropdown") {
			// Code to display dropdown values.
			htmlString = builddropdown(studentSurveyResponseLabels, labelNumber, htmlString);
		}

	} // end of for loop
	}

	var currentPageNumber = currentSectionPage;
	$("#" + firstContactContentContainer).empty();
	$("#" + firstContactContentContainer).append(htmlString);
	$("#" + firstContactContentContainer).css('padding-top', "2%");

	checkPageCompletion();

	if (answeredAll) {
		enableFCSNextButton();
	}
	if (!answeredAll) {
		if(!firstContactResponseViewOnly){
			disableFCSNextButton();
		}
	}
}

// Code to display textarea if the label/question type is text.
function buildTextLabels(studentSurveyResponseLabels, htmlString) {
	var textresponse = "";
	if(currentPageLabels.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1){
		currentPageLabels.push(studentSurveyResponseLabels[i].labelNumber);
	}

	// Code to display the labels for non-two dimentional labels(questions).
	labelNumber = studentSurveyResponseLabels[i].labelNumber;

	htmlString += "<div class='labelDiv'>" + "<label>" + studentSurveyResponseLabels[i].label + "</label>";

	htmlString = renderMandatoryFlag(studentSurveyResponseLabels[i].optional, htmlString);
	htmlString += "</div> ";
	// Code to avoid displaying "null" for textbox on the UI.
	if (studentSurveyResponseLabels[i].studentSurveyResponseText != null
			&& studentSurveyResponseLabels[i].studentSurveyResponseText != "") {
		textresponse = studentSurveyResponseLabels[i].studentSurveyResponseText;

	} else {
		textresponse = "";
	}

	// Code to display response options.
	htmlString += "<div class='labelOptionsDiv'>";
	htmlString += "<textarea rows='4' cols='50' name='" + studentSurveyResponseLabels[i].labelNumber + "' id ='"
			+ studentSurveyResponseLabels[i].labelNumber + "' onblur ='getTextData(\""
			+ studentSurveyResponseLabels[i].surveyResponseId + "\",\"" + studentSurveyResponseLabels[i].labelNumber + "\")' >"
			+ textresponse + " </textarea>";

	htmlString += " </div>";

	return htmlString;
}

// Code to display twodimentional labels/questions if the type is
// twodimentional.
function buildTwoDimentionalLabels(studentSurveyResponseLabels, labelNumber, htmlString) {
	var twoDimentionalTable = "<table style='border-collapse:collapse' width='100%'> ";
	var firstRowFlag = true;
	var newRow = studentSurveyResponseLabels[i].labelNumber;
	twoDimentionalTable += " <tr> ";

	if (labelNumber != studentSurveyResponseLabels[i].labelNumber) {
		labelNumber = studentSurveyResponseLabels[i].labelNumber;

		htmlString += "<div class='labelDiv " + studentSurveyResponseLabels[i].surveyLabelId;
		if ((studentSurveyResponseLabels[i].hasPreRequisite && studentSurveyResponseLabels[i].metPreRequisite)
				|| (!studentSurveyResponseLabels[i].hasPreRequisite && !studentSurveyResponseLabels[i].metPreRequisite)) {
			htmlString += "'>";
		} else {
			if((studentSurveyResponseLabels[i].hasPreRequisite && !studentSurveyResponseLabels[i].metPreRequisite)
				|| !firstContactResponseViewOnly){
				htmlString += " hidden ";
			}
			htmlString += " '>";
		}
		htmlString += "<label>"
				+ studentSurveyResponseLabels[i].label.substring(0, studentSurveyResponseLabels[i].label.indexOf('-'))
				+ "</label>";
	}

	var optionalQuestion = true;
	for (var index = i; index < studentSurveyResponseLabels.length
			&& labelNumber.substring(1, 3) == studentSurveyResponseLabels[index].labelNumber.substring(1, 3); index++) {
		if (studentSurveyResponseLabels[index].optional) {
			if (answeredPageLabels.indexOf(studentSurveyResponseLabels[index].labelNumber) == -1) {
				answeredPageLabels.push(studentSurveyResponseLabels[index].labelNumber);
			}
		} else {
			optionalQuestion = false;
		}
	}
	// Close the main question element with mandatory mark if applicable.
	htmlString = renderMandatoryFlag(optionalQuestion, htmlString);
	htmlString += "</div> ";

	// Code to display response options.
	htmlString += "<div class='labelDivNoBackGround " + studentSurveyResponseLabels[i].surveyLabelId;
	if ((studentSurveyResponseLabels[i].hasPreRequisite && studentSurveyResponseLabels[i].metPreRequisite)
			|| (!studentSurveyResponseLabels[i].hasPreRequisite && !studentSurveyResponseLabels[i].metPreRequisite)) {
		htmlString += "'>";
	} else {
		if((studentSurveyResponseLabels[i].hasPreRequisite && !studentSurveyResponseLabels[i].metPreRequisite)
				|| !firstContactResponseViewOnly){
			htmlString += " hidden ";
		}
		htmlString += " '>";
	}

	for (; i < studentSurveyResponseLabels.length
			&& labelNumber.substring(1, 3) == studentSurveyResponseLabels[i].labelNumber.substring(1, 3); i++) {
		if (firstRowFlag) {
			firstRowFlag = false;
			// Code to display the header information for two dimentional
			// labels/questions.
			twoDimentionalTable += "<th width='60%' class=''>  </th>";
			twoDimentionalTable += "<th> &nbsp </th>";
			for (var j = i; labelNumber == studentSurveyResponseLabels[j].labelNumber; j++) {
				labelNumber = studentSurveyResponseLabels[j].labelNumber;
				twoDimentionalTable += " <th class='labelDivHead'> <label>" + studentSurveyResponseLabels[j].responseValue
						+ " </label> </th>";
				twoDimentionalTable += " <th> &nbsp </th>";
			}
			twoDimentionalTable += " </tr>";

			// Code to display empty row after header with border-bottom which
			// looks like a horizontal line.
			twoDimentionalTable += "<tr> <td class= 'tableEmptyTd'> &nbsp </td>";
			twoDimentionalTable += "<td class='tableEmptyTd'>&nbsp</td>" + "<td class='tableEmptyTd'>&nbsp</td>"
					+ "<td class='tableEmptyTd'>&nbsp</td>";
			twoDimentionalTable += "<td class='tableEmptyTd'>&nbsp</td>" + "<td class='tableEmptyTd'>&nbsp</td>"
					+ "<td class='tableEmptyTd'>&nbsp</td>";
			twoDimentionalTable += "<td class='tableEmptyTd'>&nbsp</td>" + "<td class='tableEmptyTd'>&nbsp</td>"
					+ "<td class='tableEmptyTd'>&nbsp</td>";
			twoDimentionalTable += "<td class='tableEmptyTd'>&nbsp</td>" + "</tr>";
			twoDimentionalTable += "<tr style='border-bottom:solid 1px lightgrey;padding-bottom:2px;'> ";

			// Code to populate the first question ( A) ....) in the two
			// dimentional labels(questions).
			twoDimentionalTable += " <td width='60%' class=''>"
					+ studentSurveyResponseLabels[i].label.substring(studentSurveyResponseLabels[i].label.indexOf('-') + 1)
					+ "</td> <td>&nbsp</td>";
			if(currentPageLabels.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1){
				currentPageLabels.push(studentSurveyResponseLabels[i].labelNumber);
			}
			if (studentSurveyResponseLabels[i].hasPreRequisite) {
				if (answeredPageLabels.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1) {
					answeredPageLabels.push(studentSurveyResponseLabels[i].labelNumber);
				}
				if (!studentSurveyResponseLabels[i].optional) {
					if(skipPageLabels.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1){
						skipPageLabels.push(studentSurveyResponseLabels[i].labelNumber);
					}
				}
			}
		}

		if (newRow != studentSurveyResponseLabels[i].labelNumber) {
			twoDimentionalTable += "</tr>";

			// Code to display empty row after each row to have some space
			// between each question in a two dimentional labels/questions.
			twoDimentionalTable += "<tr> <td class=''> &nbsp </td> </tr>";

			// Code to populate the individual labels/questions (e.g. B) .... ,
			// C) ...... ) in the dimentional labels/questions.
			twoDimentionalTable += "<tr style='border-bottom:solid 1px lightgrey;padding-bottom:2px;'>";
			twoDimentionalTable += "<td width='60%' class='' >"
					+ studentSurveyResponseLabels[i].label.substring(studentSurveyResponseLabels[i].label.indexOf('-') + 1)
					+ "</td> <td> &nbsp </td>";

			newRow = studentSurveyResponseLabels[i].labelNumber;
			if(currentPageLabels.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1){
				currentPageLabels.push(studentSurveyResponseLabels[i].labelNumber);
			}

			if (studentSurveyResponseLabels[i].hasPreRequisite) {
				if (answeredPageLabels.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1) {
					answeredPageLabels.push(studentSurveyResponseLabels[i].labelNumber);
				}
				if (!studentSurveyResponseLabels[i].optional) {
					if(skipPageLabels.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1){
						skipPageLabels.push(studentSurveyResponseLabels[i].labelNumber);
					}
				}
			}
		}

		// Code to displayradiobuttons for on each row.
		twoDimentionalTable += "<td>";
		twoDimentionalTable += "<input type='radio' title='"+studentSurveyResponseLabels[i].responseValue+"'  class = '" + studentSurveyResponseLabels[i].surveyLabelId + "' name='"
				+ studentSurveyResponseLabels[i].labelNumber + "' id ='" + studentSurveyResponseLabels[i].labelNumber+"_"+studentSurveyResponseLabels[i].surveyResponseId
				+ "' onclick ='getRadioButtonData(\"" + studentSurveyResponseLabels[i].labelNumber + "\", \""
				+ studentSurveyResponseLabels[i].surveyResponseId + "\", \"" + studentSurveyResponseLabels[i].hasPreRequisite
				+ "\",\"" + studentSurveyResponseLabels[i].isPreRequisite + "\",\""
				+ escape(JSON.stringify(studentSurveyResponseLabels)) + "\",\"" + studentSurveyResponseLabels[i].optional
				+ "\",event)'"
				+ "onkeypress ='getRadioButtonData(\"" + studentSurveyResponseLabels[i].labelNumber + "\", \""
				+ studentSurveyResponseLabels[i].surveyResponseId + "\", \"" + studentSurveyResponseLabels[i].hasPreRequisite
				+ "\",\"" + studentSurveyResponseLabels[i].isPreRequisite + "\",\""
				+ escape(JSON.stringify(studentSurveyResponseLabels)) + "\",\"" + studentSurveyResponseLabels[i].optional
				+ "\",event)'"
				+" value='"
				+ studentSurveyResponseLabels[i].surveyResponseId + "' style='float:left; margin-left:1px;  margin-left:1px;'"
				+ disabledAttribute;

		if (studentSurveyResponseLabels[i].studentResponseId != null && studentSurveyResponseLabels[i].studentResponseId != ""
				&& studentSurveyResponseLabels[i].activeFlag) {
			twoDimentionalTable += " checked='checked' style='float:left; margin-left:1px'";
			if (answeredPageLabels.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1) {
				answeredPageLabels.push(studentSurveyResponseLabels[i].labelNumber);
			}
		}
		twoDimentionalTable += " > </td>";
		twoDimentionalTable += " <td> &nbsp; </td>";
	}
	twoDimentionalTable += " </div> </tr> </table>  <br> &nbsp; <br>";

	// Add two dimentional questoins here.
	htmlString += twoDimentionalTable;
	htmlString += " </div> ";
	i--;
	return htmlString;
}


// Code to display labels/questions with textboxes.
function buildRankDropdown(studentSurveyResponseLabels, labelNumber, htmlString) {

	var responseText = "";
	var labelsWithTextTable = "<table style='border-collapse:collapse' width='100%'> ";

	if (labelNumber != studentSurveyResponseLabels[i].labelNumber) {
		// Code to display the labels for checkbox labels/questions.
		labelNumber = studentSurveyResponseLabels[i].labelNumber;
		htmlString += "<div class='labelDiv'>" + "<label>"
				+ studentSurveyResponseLabels[i].label.substring(0, studentSurveyResponseLabels[i].label.indexOf('-'))
				+ "</label>";

		htmlString = renderMandatoryFlag(studentSurveyResponseLabels[i].optional, htmlString);
		htmlString += "</div> ";
	}

	// Code to display response options.
	htmlString += "<div class='labelOptionsDiv'>";

	for (; i < studentSurveyResponseLabels.length
			&& labelNumber.substring(1, 3) == studentSurveyResponseLabels[i].labelNumber.substring(1, 3); i++) {
		if (studentSurveyResponseLabels[i].label.substring(studentSurveyResponseLabels[i].label.indexOf('-') + 1) != responseText) {

			if(currentPageLabels.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1){
				currentPageLabels.push(studentSurveyResponseLabels[i].labelNumber);
			}

			if (responseText != "") {
				labelsWithTextTable += "</select> </td> <td width ='45%'> <span style='color: #ff0000;'> <label class='error_message hidden' id ='rankError"
						+ studentSurveyResponseLabels[i - 1].labelNumber
						+ "'> Please select a different rank </label> </span></td> </tr>";
			}

			labelsWithTextTable += "<tr>";
			labelsWithTextTable += "<td>"
					+ studentSurveyResponseLabels[i].label.substring(studentSurveyResponseLabels[i].label.indexOf('-') + 1)
					+ "</td>";

			labelsWithTextTable += "<td> <select class='rank' name='" + studentSurveyResponseLabels[i].labelNumber + "' id ='"
					+ studentSurveyResponseLabels[i].labelNumber + "' onchange ='getRankDropDownData(\""
					+ studentSurveyResponseLabels[i].labelNumber + "\")' onclick='getPreSelectionText(\""
					+ studentSurveyResponseLabels[i].labelNumber + "\")'>";
			labelsWithTextTable += "<option value='0'>Pick an order number</option>";

			responseText = studentSurveyResponseLabels[i].label.substring(studentSurveyResponseLabels[i].label.indexOf('-') + 1);
		}

		labelsWithTextTable += "<option value='" + studentSurveyResponseLabels[i].surveyResponseId + "'";

		if (studentSurveyResponseLabels[i].studentResponseId != null && studentSurveyResponseLabels[i].studentResponseId != ""
				&& studentSurveyResponseLabels[i].activeFlag == true) {
			labelsWithTextTable += " selected='selected' ";
			if (answeredPageLabels.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1) {
				answeredPageLabels.push(studentSurveyResponseLabels[i].labelNumber);
			}
			selectedRanks.push(studentSurveyResponseLabels[i].responseLabel);
		}

		labelsWithTextTable += " >" + studentSurveyResponseLabels[i].responseLabel + "</option>";

	}
	labelsWithTextTable += "</select> </td> <td width ='45%'> <span style='color: #ff0000;'> <label class='error_message hidden' id ='rankError"
			+ studentSurveyResponseLabels[i - 1].labelNumber + "'> Please select a different rank </label> </span></td> </tr>";

	labelsWithTextTable += "</table>";
	htmlString += labelsWithTextTable;
	htmlString += " </div> ";
	i--;

	return htmlString;
}

function builddropdown(studentSurveyResponseLabels, labelNumber, htmlString) {
	var responseText = "";
	var labelsWithTextTable = "<table style='border-collapse:collapse'> ";

	if (labelNumber != studentSurveyResponseLabels[i].labelNumber) {
		// Code to display the labels for checkbox labels/questions.
		labelNumber = studentSurveyResponseLabels[i].labelNumber;
		htmlString += "<div class='labelDiv'>" + "<label>"
				+ studentSurveyResponseLabels[i].label.substring(0, studentSurveyResponseLabels[i].label.indexOf('-'))
				+ "</label>";

		htmlString = renderMandatoryFlag(studentSurveyResponseLabels[i].optional, htmlString);
		htmlString += "</div> ";
	}

	// Code to display response options.
	htmlString += "<div class='labelOptionsDiv'>";

	for (; i < studentSurveyResponseLabels.length
			&& labelNumber.substring(1, 3) == studentSurveyResponseLabels[i].labelNumber.substring(1, 3); i++) {
		if (studentSurveyResponseLabels[i].label.substring(studentSurveyResponseLabels[i].label.indexOf('-') + 1) != responseText) {
			if(currentPageLabels.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1){
				currentPageLabels.push(studentSurveyResponseLabels[i].labelNumber);
			}

			if (studentSurveyResponseLabels[i].optional) {
				if (answeredPageLabels.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1) {
					answeredPageLabels.push(studentSurveyResponseLabels[i].labelNumber);
				}
			}

			if (responseText != "") {
				labelsWithTextTable += "</select> </td> </tr>";
			}

			labelsWithTextTable += "<tr>";
			labelsWithTextTable += "<td>"
					+ studentSurveyResponseLabels[i].label.substring(studentSurveyResponseLabels[i].label.indexOf('-') + 1)
					+ "&nbsp; </td>";

			labelsWithTextTable += "<td> <select name='" + studentSurveyResponseLabels[i].labelNumber + "' id ='"
					+ studentSurveyResponseLabels[i].labelNumber + "' onchange ='getDropDownData(\""
					+ studentSurveyResponseLabels[i].labelNumber + "\")' "
					+ disabledAttribute + " title= 'Select Primary Disability'>";
			labelsWithTextTable += "<option value='0'>Select</option>";

			responseText = studentSurveyResponseLabels[i].label.substring(studentSurveyResponseLabels[i].label.indexOf('-') + 1);
		}

		labelsWithTextTable += "<option value='" + studentSurveyResponseLabels[i].surveyResponseId + "'";
		if (studentSurveyResponseLabels[i].studentResponseId != null && studentSurveyResponseLabels[i].studentResponseId != ""
				&& studentSurveyResponseLabels[i].activeFlag == true) {
			labelsWithTextTable += " selected='selected' ";
			if (answeredPageLabels.indexOf(studentSurveyResponseLabels[i].labelNumber) == -1) {
				answeredPageLabels.push(studentSurveyResponseLabels[i].labelNumber);
			}
		}
		labelsWithTextTable += " >" + studentSurveyResponseLabels[i].responseValue + "</option>";

	}
	labelsWithTextTable += "</select> </td> </tr>";

	labelsWithTextTable += "</table>";
	htmlString += labelsWithTextTable;
	htmlString += " </div> ";
	i--;

	return htmlString;
}

function getRadioButtonData(labelNumber, surveyResponseId, hasPreRequisite, isPreRequisite, responseLabelsJSON, optional,event) {
//	alert(event.type);
	if(event.type=='keypress'){
		   return false;
		 }	
	
	var surveyResponseText = "";
	var surveyLabelIdFound = false;
	var surveyLabelIdToEnable = [];
	var surveyLabelIdToDisable = [];
	
	var responseLabels = JSON.parse(unescape(responseLabelsJSON));
	if (hasPreRequisite == "false" && isPreRequisite == "true") {
		for (var j = 0; j < surveyLabelPrerequisites.length; j++) {
			if (surveyLabelPrerequisites[j].surveyResponseId == surveyResponseId) {
				surveyLabelIdFound = true;
				if(surveyLabelIdToEnable.indexOf(surveyLabelPrerequisites[j].surveyLabelId) == -1){
					surveyLabelIdToEnable.push(surveyLabelPrerequisites[j].surveyLabelId);
				}
			} else {
				if(surveyLabelIdToDisable.indexOf(surveyLabelPrerequisites[j].surveyLabelId) == -1){
					surveyLabelIdToDisable.push(surveyLabelPrerequisites[j].surveyLabelId);
				}
			}
		}
		if (surveyLabelIdFound) {
			// If the label is enabled then that should be answered.
			var tempSkipPageLabels = [];
			for(idx = 0; idx < skipPageLabels.length; idx++){
				if(skipPageLabels[idx].indexOf('_') == -1){
					if(tempSkipPageLabels.indexOf(skipPageLabels[idx]) == -1 ){
						tempSkipPageLabels.push(skipPageLabels[idx]);
					}
				} else {
					if(tempSkipPageLabels.indexOf(skipPageLabels[idx].substring(0, skipPageLabels[idx].indexOf('_'))) == -1 ){
						tempSkipPageLabels.push(skipPageLabels[idx].substring(0, skipPageLabels[idx].indexOf('_')));
					}
				}
			}
			var diff = $(answeredPageLabels).not(tempSkipPageLabels).get();
			answeredPageLabels = diff;

			for (var j = 0; j < surveyLabelIdToEnable.length; j++) {
				$('#SectionContent_'+currentSectionPage).find("input[class='" + surveyLabelIdToEnable[j] + "']").prop( "checked", false );
				$("." + surveyLabelIdToEnable[j]).show();
				var i = surveyLabelIdToDisable.indexOf(surveyLabelIdToEnable[j]);
				if (i != -1) {
					surveyLabelIdToDisable.splice(i, 1);
				}
				
				if(surveyLabelIdToEnable.length > 0) {
					$("." + surveyLabelIdToEnable[j]).find('input').each(function(){
						// Remove from inActivePageLabels (if visible in UI).
						var labelNumber = $(this).attr('id');
						if($(this).attr('id').indexOf('_') == -1){
							labelNumber = $(this).attr('id');
						} else {
							labelNumber = $(this).attr('id').substring(0, $(this).attr('id').indexOf('_'));
						}
						
						if(inActivePageLabels.length > 0){
							inActivePageLabels = $.grep(inActivePageLabels, function(element){
								return element != labelNumber;
							});
						}
					});
				}
			}
			// splice enabled survey lable ids before rendering. FIX for DE13994
			for (var k = 0; k < surveyLabelIdToDisable.length; k++) {
				var l = surveyLabelIdToDisable.indexOf(surveyLabelIdToEnable[k]);
				if (l != -1) {
					surveyLabelIdToDisable.splice(l, 1);
				}
			}
			for (var j = 0; j < surveyLabelIdToDisable.length; j++) {
				$("." + surveyLabelIdToDisable[j]).hide();
				var i = surveyLabelIdToDisable.indexOf(surveyLabelIdToEnable[j]);
				if (i != -1) {
					surveyLabelIdToDisable.splice(i, 1);
				}
				$("." + surveyLabelIdToDisable[j]).prop( "checked", false );
				$('#SectionContent_'+currentSectionPage).find('input:checkbox').prop( "checked", false );
				if(surveyLabelIdToDisable.length > 0) {
					if(labelNumber.indexOf('_') == -1){
						if(inActivePageLabels.indexOf(labelNumber) == -1){
							inActivePageLabels.push(labelNumber);
						}
					} else {
						if(inActivePageLabels.indexOf(labelNumber.substring(0, $(this).attr('id').indexOf('_'))) == -1){
							inActivePageLabels.push(labelNumber.substring(0, $(this).attr('id').indexOf('_')));
						}
					}
				}
				
			}
		} else {
			for (var j = 0; j < surveyLabelIdToDisable.length; j++) {
				$("." + surveyLabelIdToDisable[j]).hide();
				$("." + surveyLabelIdToDisable[j]).prop( "checked", false );
				$('#SectionContent_'+currentSectionPage).find('input:checkbox').prop( "checked", false );
				
				// Add to inActivePageLabels (if not visible in UI).
				if(surveyLabelIdToDisable.length > 0){
					$("." + surveyLabelIdToDisable[j]).find('input').each(function(){
						var labelNumber = $(this).attr('id');
						if(labelNumber.indexOf('_') == -1) {
							labelNumber = labelNumber;
						} else {
							labelNumber = labelNumber.substring(0, labelNumber.indexOf('_'));
						}
						if(inActivePageLabels.indexOf(labelNumber) == -1){
							inActivePageLabels.push(labelNumber);
							if (answeredPageLabels.indexOf(labelNumber) == -1) {
								answeredPageLabels.push(labelNumber);
							}
						}
					});
				}
			}
			
		}
	}
	if (answeredPageLabels.indexOf(labelNumber) == -1) {
		answeredPageLabels.push(labelNumber);
	}
	// For all options enabled need to handle the labels.
	if (optional === 'false') {
		if ($.inArray(labelNumber, answeredPageLabels) == -1) {
			answeredPageLabels.push(labelNumber);
		}
		inActivePageLabels = $.grep(inActivePageLabels, function(value) {
			return value != labelNumber;
		});
		
		// If the label is disbaled then skip answering that.
		for(var j =0; j< skipPageLabels.length; j++){
			// Merge skipPageLabels into answeredPageLabels.
			var skipPageLabel = (skipPageLabels[j].indexOf('_') != -1) ? skipPageLabels[j].substring(0, skipPageLabels[j].indexOf('_')) : skipPageLabels[j];
			if(skipPageLabel.indexOf('_') != -1){
				// Handle check box items specially
				var answered = false;
				var containerId = skipPageLabel.substring(0, skipPageLabel.indexOf('_'));
				$('#'+containerId +' input[type=checkbox]').each(function() {
					if (this.checked) {
						answered = true;
					}
				});
		
				if (answered) {
					if ($.inArray(skipPageLabel.substring(0, skipPageLabel.indexOf('_')), answeredPageLabels) == -1) {
						answeredPageLabels.push(skipPageLabel.substring(0, skipPageLabel.indexOf('_')));
					}
					inActivePageLabels = $.grep(inActivePageLabels, function(value) {
						return value != skipPageLabel.substring(0, skipPageLabel.indexOf('_'));
					});
				} else {
					answeredPageLabels = $.grep(answeredPageLabels, function(value) {
						return value != skipPageLabel;
					});
				}
			} else {
				if(skipPageLabel !== '' && answeredPageLabels.indexOf(skipPageLabel) == -1){
					if(labelNumber === skipPageLabel) {
						answeredPageLabels.push(skipPageLabel);
					}
				}
				// Add to inActivePageLabels (if not visible in UI).
				if(surveyLabelIdToDisable.length > 0){
					$("." + surveyLabelIdToDisable[j]).find('input').each(function(){
						var inactiveLabelNumber = ($(this).attr('id').indexOf('_') != -1) ? $(this).attr('id').substring(0, $(this).attr('id').indexOf('_')): $(this).attr('id');
						if(inActivePageLabels.indexOf(inactiveLabelNumber) == -1){
							inActivePageLabels.push(inactiveLabelNumber);
						}
					});
				}
			}
		}
	}

	var $tabs = jQuery('#firstContactResponseTabs');
	var tabsLength = $tabs.find('.ui-tabs-panel').length;
	var selected = $tabs.tabs('option', 'active');
	var currentPageNumber = currentSectionPage;

	checkPageCompletion();

	if (answeredAll) {
		enableFCSNextButton();
		
		if(currentPageNumber == 6 || currentPageNumber == 7 || currentPageNumber == 8){
			updateSurveyPageStatusToIncomplete(currentPageNumber);
		}
		updateSurveyPageStatus(currentPageNumber);
	}
	if (!answeredAll) {
		if(!firstContactResponseViewOnly){
			disableFCSNextButton();
		}
		updateSurveyPageStatusToIncomplete(currentPageNumber);
	}
	storeFirstContactResponses(labelNumber, $('input[name=' + labelNumber + ']:checked').val(), surveyResponseText);
}

function getTextData(surveyResponseId, labelNumber) {

	var surveyResponseText = $("#" + labelNumber).val();
	if (answeredPageLabels.indexOf(labelNumber) == -1) {
		answeredPageLabels.push(labelNumber);
	}
	storeFirstContactResponses(labelNumber, surveyResponseId, surveyResponseText);
}


function getCheckBoxData(surveyResponseId, labelNumber, hasPreRequisite, isPreRequisite, optional,event) {

	if(event.type=='keypress'){
			  return false;
		 }	
	console.log(event.which);
	var surveyLabelIdFound = false;
	var surveyLabelIdToEnable = [];
	var surveyLabelIdToDisable = [];

	for (var j = 0; j < surveyLabelPrerequisites.length; j++) {
		if (surveyLabelPrerequisites[j].surveyResponseId == surveyResponseId
				&& $('input[name=' + labelNumber + ']').is(":checked")) {
			surveyLabelIdFound = true;
			surveyLabelIdToEnable.push(surveyLabelPrerequisites[j].surveyLabelId);
		} else if (surveyLabelPrerequisites[j].surveyResponseId == surveyResponseId
				&& !$('input[name=' + labelNumber + ']').is(":checked")) {
			surveyLabelIdToDisable.push(surveyLabelPrerequisites[j].surveyLabelId);
		}
	}
	
	if (surveyLabelIdFound) {

		// If the label is enabled then that should be answered.
		var diff = $(answeredPageLabels).not(skipPageLabels).get();
		answeredPageLabels = diff;

		for (var j = 0; j < surveyLabelIdToEnable.length; j++) {
			$("." + surveyLabelIdToEnable[j]).show();
			$("." + surveyLabelIdToEnable[j]).find(':checked').each(function() {
				$(this).prop( "checked", false );
			});
			
			if(surveyLabelIdToEnable.length > 0) {
				$("." + surveyLabelIdToEnable[j]).find('input').each(function(){
					// Remove from inActivePageLabels (if visible in UI).
					var labelNumber = $(this).attr('id').substring(0, $(this).attr('id').indexOf('_'));
					if(inActivePageLabels.length > 0){
						inActivePageLabels = $.grep(inActivePageLabels, function(element){
							return element != labelNumber;
						});
					}
				});
			}
		}
		
	} else {
		// If the label is disbaled then skip answering that.
		for(var j =0; j< skipPageLabels.length; j++){
			// Merge skipPageLabels into answeredPageLabels.
			var skipPageLabel = skipPageLabels[j].substring(0, skipPageLabels[j].indexOf('_'));
			if(skipPageLabel !== '' && answeredPageLabels.indexOf(skipPageLabel) == -1){
				if(labelNumber.substring(0, labelNumber.indexOf('_')) === skipPageLabel) {
					answeredPageLabels.push(skipPageLabel);
				}
			}
			// Add to inActivePageLabels (if not visible in UI).
			if(surveyLabelIdToDisable.length > 0){
				$("." + surveyLabelIdToDisable[j]).find('input').each(function(){
					var labelNumber = $(this).attr('id').substring(0, $(this).attr('id').indexOf('_'));
					if(inActivePageLabels.indexOf(labelNumber) == -1){
						inActivePageLabels.push(labelNumber);
					}
				});
			}
		}
		
		
		for (var j = 0; j < surveyLabelIdToDisable.length; j++) {
			$("." + surveyLabelIdToDisable[j]).hide();
			$("." + surveyLabelIdToDisable).prop( "checked", false );
		}
	}
	
	var surveyResponseText = "";
	var answered = false;
	if (optional === 'false') {
		var containerId = labelNumber.substring(0, labelNumber.indexOf('_'));
		$('#'+containerId +' input[type=checkbox]').each(function() {
			if (this.checked) {
				answered = true;
			}
		});

		if (answered) {
			if ($.inArray(labelNumber.substring(0, labelNumber.indexOf('_')), answeredPageLabels) == -1) {
				answeredPageLabels.push(labelNumber.substring(0, labelNumber.indexOf('_')));
			}
			inActivePageLabels = $.grep(inActivePageLabels, function(value) {
				return value != labelNumber.substring(0, labelNumber.indexOf('_'));
			});
		} else {
			answeredPageLabels = $.grep(answeredPageLabels, function(value) {
				return value != labelNumber.substring(0, labelNumber.indexOf('_'));
			});
		}
	}

	if (!$('input[name=' + labelNumber + ']').is(":checked")) {
		surveyResponseId = -1;
	}
	
	var currentPageNumber = currentSectionPage;

	checkPageCompletion();

	if (answeredAll) {
		enableFCSNextButton();
		updateSurveyPageStatus(currentPageNumber);
	}
	if (!answeredAll) {
		if(!firstContactResponseViewOnly){
			disableFCSNextButton();
		}
		updateSurveyPageStatusToIncomplete(currentPageNumber);
	}
	
	storeFirstContactResponses(labelNumber, surveyResponseId, surveyResponseText);
}

function getPreSelectionText(labelNumber) {
	existingRank = $("#" + labelNumber).find(":selected").text();
}

function getRankDropDownData(labelNumber) {
	var selectedOptionText = $("#" + labelNumber).find(":selected").text();
	var surveyResponseId = $("#" + labelNumber).val();
	var surveyResponseText = "";
	var valid = false;
	var remove = true;

	if (surveyResponseId == 0) {
		valid = true;
		answeredPageLabels = $.grep(answeredPageLabels, function(value) {
			return value != labelNumber;
		});

		// Check if its already in any other dropdown.
		$(".rank option").each(function() {
			if ($(this).is(':selected') && $(this).text() == existingRank) {
				remove = false;
			}
		});

		if (remove) {
			selectedRanks = $.grep(selectedRanks, function(value) {
				return value != existingRank;
			});
		}
	} else if ($.inArray(selectedOptionText, selectedRanks) == -1) {
		valid = true;
		selectedRanks.push(selectedOptionText);

		if ($.inArray(labelNumber, answeredPageLabels) == -1) {
			answeredPageLabels.push(labelNumber);
		}
		// Check if its already in any other dropdown.
		$(".rank option").each(function() {
			if ($(this).is(':selected') && $(this).text() == existingRank) {
				remove = false;
			}
		});
		if (remove) {
			selectedRanks = $.grep(selectedRanks, function(value) {
				return value != existingRank;
			});
		}
	}

	if (valid) {
		existingRank = "";
		$('#rankError' + labelNumber).hide();

		if ($("#" + labelNumber).val() == 0) {
			surveyResponseId = -1;
		}
		storeFirstContactResponses(labelNumber, surveyResponseId, surveyResponseText);
	} else {
		$('#rankError' + labelNumber).show();
		setTimeout("aart.clearMessages()", 5000);
		remove = true;
		$("select#" + labelNumber).val("0");
		answeredPageLabels = $.grep(answeredPageLabels, function(value) {
			return value != labelNumber;
		});
		// Check if its already in any other dropdown.
		$(".rank option").each(function() {
			if ($(this).is(':selected') && $(this).text() == existingRank) {
				remove = false;
			}
		});
		if (remove) {
			selectedRanks = $.grep(selectedRanks, function(value) {
				return value != existingRank;
			});
		}
	}
}

function getDropDownData(labelNumber) {
	var surveyResponseId = $("#" + labelNumber).val();
	var surveyResponseText = "";
	var pageNumber = currentSectionPage;
	if(surveyResponseId == '0'){
		if (answeredPageLabels.indexOf(labelNumber) != -1) {
			answeredPageLabels = $.grep(answeredPageLabels, function(element){
				return element != labelNumber;
			});
		}
	} else {
		if (answeredPageLabels.indexOf(labelNumber) == -1) {
			answeredPageLabels.push(labelNumber);
		}
	}
	
	storeFirstContactResponses(labelNumber, surveyResponseId, surveyResponseText);

	checkPageCompletion();

	if (answeredAll) {
		enableFCSNextButton();
	} else {
		if(!firstContactResponseViewOnly){
			disableFCSNextButton();
		}
	}

}

function storeFirstContactResponses(labelNumber, surveyResponseId, surveyResponseText) {
	labelNumbers.push(labelNumber);
	surveyResponseIds.push(surveyResponseId);
	if (surveyResponseText == null || surveyResponseText.length == 0) {
		surveyResponseText = "null";
	}

	surveyResponseTexts.push(surveyResponseText);
}

// ajax call to save the response for labels on click/change.
function saveFirstContactResponses(lastPageToBeSaved, pageNumber, firstContactContentContainer) {
	if(lastPageToBeSaved == 0){
		initializeArrays();
	}
	var data = {
		studentId : $('#fcrvStudentId').val(),
		currentPageNumber : $('#section' + pageNumber).data('pagenumber'),
		labelNumbers : labelNumbers,
		surveyResponseIds : surveyResponseIds,
		surveyResponseTexts : surveyResponseTexts
	};
	if ($('#surveyId').val() != '') {
		data.surveyId = $('#surveyId').val();
	}
	var lastPageId = parseInt($('#fcsLastPageId').val());

	checkPageCompletion();

	var pageStatus = answeredAll ? "complete" : "incomplete";

	if (labelNumbers.length > 0) {
		$.ajax({
			url : 'saveFirstContactResponses.htm',
			data : data,
			dataType : 'json',
			type : "GET"
		}).done(function(response) {
			if (response) {
				$('#surveyId').val(response);
			}
			if (pageNumber == lastPageId) {
				// if we're on the last page now (Complete tab), then we
				// need to
				updateSurveyPageStatus(pageNumber);
			} else {
				loadFirstContactSectionData(lastPageToBeSaved, pageNumber, firstContactContentContainer, pageStatus);
			}
		});
		initializeArrays();
	} else {
		if (pageNumber == lastPageId) {
			// if we're on the last page now (Complete tab), then we need to
			updateSurveyPageStatus(pageNumber);
		} else {
			loadFirstContactSectionData(lastPageToBeSaved, pageNumber, firstContactContentContainer, pageStatus);
		}
	}
}

function saveFirstContactResponsesOnClose() {
	var surveyId = $('#surveyId').val();
	//lastPageTobeSaved = currentSectionPage;
	var updatedPageNumber = $('#section' + lastPageTobeSaved).data('pagenumber');

	checkPageCompletion();

	if (!answeredAll) {
		return false;
	} else {

		if (labelNumbers.length > 0) {
			$.ajax({
				url : 'saveFirstContactResponses.htm',
				data : {
					studentId : $('#fcrvStudentId').val(),
					surveyId : $('#surveyId').val(),
					currentPageNumber : updatedPageNumber,
					labelNumbers : labelNumbers,
					surveyResponseIds : surveyResponseIds,
					surveyResponseTexts : surveyResponseTexts
				},
				dataType : 'json',
				type : "GET"
			}).done(function(response) {
				var lastPageId = parseInt($('#fcsLastPageId').val());
				if(!firstContactResponseViewOnly || pageNumber === 0){
					$.ajax({
						url : 'updateSurveyPageStatus.htm',
						data : {
							surveyId : surveyId,
							pageNumber : updatedPageNumber
						},
						dataType : 'json',
						type : "GET"
					}).done(function(response) {
						// Method call to update page status when displaying
						// labels/questions for each page.
						updatePageStatus(response);
					});
				}
				
			});
			initializeArrays();
		}else if(surveyId != ""){
			updateSurveyPageStatus(lastPageTobeSaved);
		}else{

		}
	}
}

function renderMandatoryFlag(optional, htmlString) {
	if (optional) {
		return htmlString;
	} else {
		return htmlString + '<font size="5" color="red">*</font>';
	}
}

function enableFCSNextButton() {
	$('#section'+currentSectionPage+' .fcsNextButton').prop( "disabled", false );
	$('#section'+currentSectionPage+' .fcsNextButton').attr('disabled', false);
	$('#section'+currentSectionPage+' .fcsNextButton').removeClass("btn_disabled");
	$('#section'+currentSectionPage+' .fcsNextButton').addClass("btn_blue");
}

function disableFCSNextButton() {
	$('#section'+currentSectionPage+' .fcsNextButton').attr('disabled', 'disabled');
	$('#section'+currentSectionPage+' .fcsNextButton').removeClass("btn_blue");
	$('#section'+currentSectionPage+' .fcsNextButton').addClass("btn_disabled");
	// fix for page bubble color change on navigating to the page.
	$('#firstContactResponseTabs').find('.page'+currentSectionPage).css('background-color', '#FDA20E');
}

function checkPageCompletion() {
	var totalQuestionsInPage = $.merge($.merge([], answeredPageLabels), inActivePageLabels);
	answeredAll = ($(currentPageLabels).not(totalQuestionsInPage).length == 0 && $(totalQuestionsInPage).not(currentPageLabels).length == 0);
	if (nonMandatoryPage) {
		answeredAll = true;
	}
	
	if(allQuestionsRequired == 'true') {
		var totalQuestionsInPage = $.merge($.merge([], answeredPageLabels), inActivePageLabels);
		answeredAll = ($(totalQuestionsInPage).not(currentPageLabels).length == 0 && $(currentPageLabels).not(totalQuestionsInPage).length == 0);
	}
}

function initializeArrays(){
	labelNumbers = [];
	surveyResponseIds = [];
	surveyResponseTexts = [];
}

function initializePageLabelArrays(){
	currentPageLabels = [];
	answeredPageLabels = [];
	skipPageLabels = [];
	inActivePageLabels = [];
}