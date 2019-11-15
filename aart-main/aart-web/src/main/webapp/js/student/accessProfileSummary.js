var studentIdforProfileSummary = $('#studentIdforProfileSummary').val();
$('.editSettings').on("click",function() {
	$('#accessProfileTabs').tabs("option", "active", 1);
});
function loadProfileSummary(){
	
	$.ajaxSetup({ cache: false });
	var orgname= $('#currentOrgName').val().toLowerCase();
	var gridParam = window.localStorage.getItem(studentIdforProfileSummary); 
	if(gridParam == null){
		gridParam = window.sessionStorage.getItem("selectedStudentITI");  
	}
	var studentInfo = JSON.parse(gridParam);

	var divInfo = document.getElementById("stu_value");
	$(divInfo).html("");
	//studentInfo.studentFirstName =  decodeURIComponent(studentInfo.studentFirstName).replace("\\","");
	//studentInfo.studentLastName =  decodeURIComponent(studentInfo.studentLastName).replace("\\","");
	//studentInfo.studentMiddleName =  decodeURIComponent(studentInfo.studentMiddleName).replace("\\","");
	var appendStudentInfo = "<li>"+escapeHtml(studentInfo.studentFirstName) +"</li>"
							+"<li>"+escapeHtml(studentInfo.studentMiddleName) +"</li>"
							+"<li>"+escapeHtml(studentInfo.studentLastName) +"</li>"
							+"<li>"+studentInfo.stateStudentIdentifier +"</li>"
							+"<li>"+studentInfo.gradeCourseName +"</li>"
							+"<li>"+studentInfo.gender +"</li>"
							+"<li>"+studentInfo.dateOfBirth +"</li>";
	
							$(divInfo).append(appendStudentInfo);
							
	$.ajax({
		url: 'loadStudentProfileItemAttributeDataSection.htm',
		data: {
			studentId: $('#studentIdPNP').val(),
		},	
		dataType: 'json',
		type: "GET",
		success: function(response) {
			var assignedSupport = new Array();
			//alert(response.length);
			
			for(var i = 0; i < response.length;i++) {
				if(response[i].attributeName.toLowerCase() == ("assignedSupport").toLowerCase()){
					if(response[i].selectedValue.toLowerCase() == "true"){
						assignedSupport[i]=response[i].attributeContainerName;
						//alert(assignedSupport[i]);
					}
				}
				if (response[i].attributeContainerName.toLowerCase() == ("setting").toLowerCase() 
						&& response[i].attributeName.toLowerCase() == ("separateQuiteSetting").toLowerCase() && response[i].selectedValue.toLowerCase() != ""){
					assignedSupport[i]=response[i].attributeContainerName;
				}
				if (response[i].attributeContainerName.toLowerCase() == ("presentation").toLowerCase() && response[i].selectedValue.toLowerCase() != ""){ 
						if(response[i].attributeName.toLowerCase() == ("someotheraccommodation").toLowerCase() ){
							assignedSupport[i]=response[i].attributeContainerName;
						}
						if(response[i].attributeName.toLowerCase() == ("readsAssessmentOutLoud").toLowerCase()){
							assignedSupport[i]=response[i].attributeContainerName;
						}
						if(response[i].attributeName.toLowerCase() == ("useTranslationsDictionary").toLowerCase()){
							assignedSupport[i]=response[i].attributeContainerName;
						}
				}
				if (response[i].attributeContainerName.toLowerCase() == ("response").toLowerCase() && response[i].selectedValue.toLowerCase() != ""){
						if (response[i].attributeName.toLowerCase() == ("dictated").toLowerCase()){
							assignedSupport[i]=response[i].attributeContainerName;
						}
						if (response[i].attributeName.toLowerCase() == ("usedCommunicationDevice").toLowerCase()){
							assignedSupport[i]=response[i].attributeContainerName;
						}
						if (response[i].attributeName.toLowerCase() == ("signedResponses").toLowerCase()){
							assignedSupport[i]=response[i].attributeContainerName;
						}
				}
				if (response[i].attributeContainerName.toLowerCase() == ("supportsProvidedByAlternateForm").toLowerCase() &&
						response[i].selectedValue.toLowerCase() == "true"){
					if (response[i].attributeName.toLowerCase() == ("visualImpairment").toLowerCase()){
						assignedSupport[i]=response[i].attributeContainerName;
					}
					if (response[i].attributeName.toLowerCase() == ("largePrintBooklet").toLowerCase()){ 
						assignedSupport[i]=response[i].attributeContainerName;
					}
					if (response[i].attributeName.toLowerCase() == ("paperAndPencil").toLowerCase()){
						assignedSupport[i]=response[i].attributeContainerName;
					}
				}
				if (response[i].attributeContainerName.toLowerCase() == ("supportsRequiringAdditionalTools").toLowerCase() &&
						response[i].selectedValue.toLowerCase() == "true"){
					if (response[i].attributeName.toLowerCase() == ("supportsTwoSwitch").toLowerCase()){
						assignedSupport[i]=response[i].attributeContainerName;
					}
					if (response[i].attributeName.toLowerCase() == ("supportsAdminIpad").toLowerCase()){
						assignedSupport[i]=response[i].attributeContainerName;
					}
					if (response[i].attributeName.toLowerCase() == ("supportsAdaptiveEquip").toLowerCase()){
						assignedSupport[i]=response[i].attributeContainerName;
					}
					if (response[i].attributeName.toLowerCase() == ("supportsIndividualizedManipulatives").toLowerCase()){
						assignedSupport[i]=response[i].attributeContainerName;
					}
					if (response[i].attributeName.toLowerCase() == ("supportsCalculator").toLowerCase()){
                        assignedSupport[i]=response[i].attributeContainerName;
                    }
				}
				if (response[i].attributeContainerName.toLowerCase() == ("supportsProvidedOutsideSystem").toLowerCase() &&
						response[i].selectedValue.toLowerCase() == "true"){
					if (response[i].attributeName.toLowerCase() == ("supportsHumanReadAloud").toLowerCase()){
						assignedSupport[i]=response[i].attributeContainerName;
					}
					if (response[i].attributeName.toLowerCase() == ("supportsSignInterpretation").toLowerCase()){
						assignedSupport[i]=response[i].attributeContainerName;
					}
					if (response[i].attributeName.toLowerCase() == ("supportsLanguageTranslation").toLowerCase()){
						assignedSupport[i]=response[i].attributeContainerName;
					}
					if (response[i].attributeName.toLowerCase() == ("supportsTestAdminEnteredResponses").toLowerCase()){
						assignedSupport[i]=response[i].attributeContainerName;
					}
					if (response[i].attributeName.toLowerCase() == ("supportsPartnerAssistedScanning").toLowerCase()){
						assignedSupport[i]=response[i].attributeContainerName;
					}
					if (response[i].attributeName.toLowerCase() == ("supportsStudentProvidedAccommodations").toLowerCase()){
						assignedSupport[i]=response[i].attributeContainerName;
					}
					
				}
			}
			for(var i = 0; i < response.length;i++) {
				if(response[i].attributeContainerName == "ColourOverlay"){
					if(response[i].attributeName == "colour"){
						var overlaycolor= response[i].selectedValue;
					}
				}
				if(response[i].attributeContainerName == "ForegroundColour"){
					if(response[i].attributeName == "colour"){
						var fgColor= response[i].selectedValue;
					}
				}
				if(response[i].attributeContainerName == "BackgroundColour"){
					if(response[i].attributeName == "colour"){
						var bgColor= response[i].selectedValue;
					}
				}
			}
			setCss(fgColor,bgColor,overlaycolor);
			if(assignedSupport.length < 1){
				$("#errorMessage").show();
			}else{
			for(var i = 0; i < response.length;i++) {
				var check= $.inArray((response[i].attributeContainerName), assignedSupport);
				if(check > -1){
					$('#accessProfileSummaryDiv div.containerDiv').each(function() {
						if( $(this).attr('class') != undefined &&
								(($(this).attr('class').toLowerCase()).indexOf(
										(response[i].attributeContainerName).toLowerCase(),0)) == 0) {
							var id=response[i].attributeContainerName;
							if(id == "Magnification"){
								var div= document.getElementById(id);
								$("."+id).show();
								if(response[i].attributeName == "assignedSupport"){
									var assignedSupport = response[i].selectedValue;
									//alert(assignedSupport);
								}
								
								if(response[i].attributeName == "magnification"){
									if(response[i].selectedValue!=''){
									var htmlString="<div class='attributeDiv'>"+"Magnification : "+response[i].selectedValue+"<br/></div>";
									}
									$(div).append(htmlString);
									return false; 
								}
							}
							if(id == "ColourOverlay"){
								var div= document.getElementById(id);
								$("."+id).show();
								return false; 
							}
							if(id == "Masking"){
								var div= document.getElementById(id);
								$("."+id).show();
								if(response[i].attributeName == "MaskingType"){
									if(response[i].selectedValue.toLowerCase() == "answermask"){
										var htmlString="<div class='attributeDiv'>"+"Masking Type : Answer Mask"+"<br/></div>";
									} else if(response[i].selectedValue.toLowerCase() == "custommask"){
										var htmlString="<div class='attributeDiv'>"+"Masking Type : Custom Mask"+"<br/></div>";
									}
									$(div).append(htmlString);
									return false; 
								}
							}
							if(id == "ForegroundColour"){
								var div= document.getElementById(id);
								$("."+id).show();
								return false; 
							}
							if(id == "InvertColourChoice"){
								var div= document.getElementById(id);
								$("."+id).show();
								return false; 
							}
							if(id == "itemTranslationDisplay"){
								var div= document.getElementById(id);
								$("."+id).show();
								if(response[i].attributeName == "Language"){
									if(response[i].selectedValue == "ger"){
									var htmlString="<div class='attributeDiv'>"+"Language : German"+"<br/></div>";
									$(div).append(htmlString);
									} else if(response[i].selectedValue == "eng"){
										var htmlString="<div class='attributeDiv'>"+"Language : English"+"<br/></div>";
										$(div).append(htmlString);
									}else if(response[i].selectedValue == "spa"){
										var htmlString="<div class='attributeDiv'>"+"Language : Spanish"+"<br/></div>";
										$(div).append(htmlString);
									}else if(response[i].selectedValue == "vie"){
										var htmlString="<div class='attributeDiv'>"+"Language : Vietnamese"+"<br/></div>";
										$(div).append(htmlString);
									}
									return false; 
								}
							}
							if(id == "keywordTranslationDisplay"){
								var div= document.getElementById(id);
								$("."+id).show();
								if(response[i].attributeName == "Language"){
									if(response[i].selectedValue == "ger"){
									var htmlString="<div class='attributeDiv'>"+"Language : German"+"<br/></div>";
									$(div).append(htmlString);
									} else if(response[i].selectedValue == "eng"){
										var htmlString="<div class='attributeDiv'>"+"Language : English"+"<br/></div>";
										$(div).append(htmlString);
									}else if(response[i].selectedValue == "spa"){
										var htmlString="<div class='attributeDiv'>"+"Language : Spanish"+"<br/></div>";
										$(div).append(htmlString);
									}else if(response[i].selectedValue == "vie"){
										var htmlString="<div class='attributeDiv'>"+"Language : Vietnamese"+"<br/></div>";
										$(div).append(htmlString);
									}
									return false; 
								}
							}
							if(id == "Signing"){
								var div= document.getElementById(id);
								$("."+id).show();
								if(response[i].attributeName == "SigningType"){
									if(response[i].selectedValue == "asl"){
									var htmlString="<div class='attributeDiv'>"+"Signing Type : American Sign Language"+"<br/></div>";
									$(div).append(htmlString);
									} else if(response[i].selectedValue == "SignedEnglish"){
										var htmlString="<div class='attributeDiv'>"+"Signing Type : Signed English"+"<br/></div>";
										$(div).append(htmlString);
									}
									return false; 
								}
							}
							if(id == "Tactile"){
								var div= document.getElementById(id);
								$("."+id).show();
								if(response[i].attributeName == "tactileFile"){
									if(response[i].selectedValue == "audioFile"){
									var htmlString="<div class='attributeDiv'>"+"Tactile File : Audio File"+"<br/></div>";
									} else if(response[i].selectedValue == "audioText"){
										var htmlString="<div class='attributeDiv'>"+"Tactile File : Audio Text"+"<br/></div>";
									} else if(response[i].selectedValue == "brailleText"){
										var htmlString="<div class='attributeDiv'>"+"Tactile File : Braille Text"+"<br/></div>";
									}
									$(div).append(htmlString);
									return false; 
								}
							}
							if(id == "Braille"){
								var div= document.getElementById(id);
								$("."+id).show();
								if(response[i].attributeName == "ebaeFileType" && response[i].selectedValue.toUpperCase() == "TRUE"){
									var htmlString="<div class='attributeDiv'>"+"Braille File Type : "+" EBAE"+"<br/></div>";
									$(div).append(htmlString);
									return false; 
								} else if(response[i].attributeName == "uebFileType" && response[i].selectedValue.toUpperCase() == "TRUE"){
									var htmlString="<div class='attributeDiv'>"+"Braille File Type : "+" UEB"+"<br/></div>";
									$(div).append(htmlString);
									return false; 
								}
								if(response[i].attributeName == "brailleGrade"){
									if(response[i].selectedValue!=''){
									var htmlString="<div class='attributeDiv'>"+"Braille Grade : "+response[i].selectedValue+"<br/></div>";
									}
									$(div).append(htmlString);
									return false; 
								}
								if(response[i].attributeName == "usage"){
									var htmlString="";
									if(response[i].selectedValue == "optionallyUse"){
										htmlString="<div class='attributeDiv'>"+"Usage : Optionally Use<br/></div>";
									} else if(response[i].selectedValue != ''){
										htmlString="<div class='attributeDiv'>"+"Usage : "+response[i].selectedValue+"<br/></div>";
									}
									$(div).append(htmlString);
									return false; 
								}
								if(response[i].attributeName == "brailleMark"){
									var brailleMarkSelectedValue = response[i].selectedValue.split(",");
									var brailleMark = new Array();
									for(var j=0;j<brailleMarkSelectedValue.length;j++){
										brailleMark[j] = " " + brailleMarkSelectedValue[j].charAt(0).toUpperCase() + brailleMarkSelectedValue[j].slice(1).toLowerCase();
									}
	 								var htmlString="<div class='attributeDiv'>"+"Braille Mark : "+brailleMark.join()+"<br/></div>";
									$(div).append(htmlString);
									return false; 
								}
								if(response[i].attributeName == "numberOfBrailleDots"){
									if(response[i].selectedValue!=''){
									var htmlString="<div class='attributeDiv'>"+"Number Of Braille Dots : "+response[i].selectedValue+"<br/></div>";
									}
									$(div).append(htmlString);
									return false; 
								}
								if(response[i].attributeName == "numberOfBrailleCells"){
									if(response[i].selectedValue!=''){
									var htmlString="<div class='attributeDiv'>"+"Number Of Braille Cells : "+response[i].selectedValue+"<br/></div>";
									}
									$(div).append(htmlString);
									return false; 
								}
								if(response[i].attributeName == "brailleDotPressure"){
									if(response[i].selectedValue!=''){
									var htmlString="<div class='attributeDiv'>"+"Braille Dot Pressure : "+response[i].selectedValue+"<br/></div>";
									}
									$(div).append(htmlString);
									return false; 
								}
								if(response[i].attributeName == "brailleStatusCell"){
									if(response[i].selectedValue!=''){
									var htmlString="<div class='attributeDiv'>"+"Braille Status Cell : "+response[i].selectedValue+"<br/></div>";
									}
									$(div).append(htmlString);
									return false; 
								}
							}
							if(id == "AuditoryBackground"){
								$("."+id).show();
								return false; 
							}
							if(id == "breaks"){
								$("."+id).show();
								return false; 
							}
							if(id == "AdditionalTestingTime"){
								var div= document.getElementById(id);
								$("."+id).show();
								if(response[i].attributeName == "TimeMultiplier"){
									if(response[i].selectedValue!=''){
									var htmlString="<div class='attributeDiv'>"+"Time Multiplier : "+response[i].selectedValue+"<br/></div>";
									}
									$(div).append(htmlString);
									return false; 
								}
							}
							if(id == "Spoken"){
								var div= document.getElementById(id);
								$("."+id).show();
								if(response[i].attributeName == "SpokenSourcePreference"){
									if(response[i].selectedValue!=''){
									var htmlString="<div class='attributeDiv'>"+"Spoken Source Preference : "+response[i].selectedValue+"<br/></div>";
									}
									$(div).append(htmlString);
									return false; 
								}
								if(response[i].attributeName == "ReadAtStartPreference"){
									if(response[i].selectedValue!=''){
									var htmlString="<div class='attributeDiv'>"+"Read At Start Preference : "+response[i].selectedValue+"<br/></div>";
									}
									$(div).append(htmlString);
									return false; 
								}
								if(response[i].attributeName == "UserSpokenPreference"){
									var htmlString = "";
									if(response[i].selectedValue == "textonly"){
										htmlString= "<div class='attributeDiv'>"+"User Spoken Preference : Text Only <br/></div>";
									}else if(response[i].selectedValue == "textandgraphics"){
										htmlString= "<div class='attributeDiv'>"+"User Spoken Preference : Text And Graphics <br/></div>";
									}else if(response[i].selectedValue == "graphicsonly"){
										htmlString= "<div class='attributeDiv'>"+"User Spoken Preference : Graphics Only <br/></div>";
									}else if(response[i].selectedValue == "nonvisual"){
										htmlString= "<div class='attributeDiv'>"+"User Spoken Preference : Non Visual <br/></div>";
									}
									$(div).append(htmlString);
									return false; 
								}
								if(response[i].attributeName == "directionsOnly"){
									if(response[i].selectedValue!=''){
									var htmlString="<div class='attributeDiv'>"+"Directions Only : "+response[i].selectedValue+"<br/></div>";
									}
									$(div).append(htmlString);
									return false; 
								}
								if(response[i].attributeName == 'preferenceSubject'){
									var value = '';
									if(orgname === 'cpass school' || orgname === 'armm school'){
										if (response[i].selectedValue == 'math_and_science'){
											value = 'Mathematics only';
										} else if (response[i].selectedValue == 'math_science_and_ELA'){
											value = 'Mathematics and English Language Arts';
										}
									} else {									
										if (response[i].selectedValue == 'math_and_science'){
											value = 'Mathematics and Science only';
										} else if (response[i].selectedValue == 'math_science_and_ELA'){
											value = 'Mathematics, Science and English Language Arts';
										}
									}
									if(value!=''){
									var htmlString="<div class='attributeDiv'>"+"Provided For : "+value+"<br/></div>";
									}
									$(div).append(htmlString);
									return false; 
								}
							}
							if(id == "onscreenKeyboard"){
								var div= document.getElementById(id);
								$("."+id).show();
								if(response[i].attributeName == "scanSpeed"){
									if(response[i].selectedValue!=''){
									var htmlString="<div class='attributeDiv'>"+"Scan Speed : "+response[i].selectedValue+"<br/></div>";
									}
									$(div).append(htmlString);
								}
								if(response[i].attributeName == "automaticScanInitialDelay"){
									if(response[i].selectedValue!=''){
									var htmlString="<div class='attributeDiv'>"+"Automatic Scan Initial Delay : "+response[i].selectedValue+"<br/></div>";
									}
									$(div).append(htmlString);
								}
								if(response[i].attributeName == "automaticScanRepeat"){
									if(response[i].selectedValue!=''){
									var htmlString="<div class='attributeDiv'>"+"Automatic Scan Repeat : "+response[i].selectedValue+"<br/></div>";
									}
									$(div).append(htmlString);
								}
								return false; 
							}
							if(id == "setting" && response[i].selectedValue.toLowerCase() == "true"){
								if(response[i].attributeName == "separateQuiteSetting"  && response[i].selectedValue.toLowerCase() == "true"){
									var div= document.getElementById(id);
									$("."+id).show();
									var htmlString="<div class='attributeDiv'>Separate, quiet, or individual setting<br/></div>";
									$(div).append(htmlString);
								}
								return false; 
							}
							if(id == "presentation" && response[i].selectedValue.toLowerCase() == "true"){
								var div= document.getElementById(id);
								if(response[i].attributeName.toLowerCase().indexOf("assessment") >= 0){
									var htmlString="<div class='attributeDiv'>Student reads the assessment aloud to self <br/></div>";
									$(div).append(htmlString);
									$("."+id).show();
								}
								if(response[i].attributeName.toLowerCase().indexOf("translations") >= 0){
									var htmlString="<div class='attributeDiv'>Student used a translation dictionary <br/></div>";
									$(div).append(htmlString);
									$("."+id).show();
								}
								if(response[i].attributeName.toLowerCase().indexOf("accommodation") >= 0){
									var htmlString="<div class='attributeDiv'>Some other accommodation was used <br/></div>";
									$(div).append(htmlString);
									$("."+id).show();
								}
								return false; 
							}
							if(id == "response" && response[i].selectedValue.toLowerCase() == "true"){
								var div= document.getElementById(id);
								if(response[i].attributeName.toLowerCase().indexOf("dictated") >= 0){
									var htmlString="<div class='attributeDiv'>Student dictated his/her answers to a scribe <br/></div>";
									$(div).append(htmlString);
									$("."+id).show();
								}
								if(response[i].attributeName.toLowerCase().indexOf("communication") >= 0){
									var htmlString="<div class='attributeDiv'>Student used a communication device <br/></div>";
									$(div).append(htmlString);
									$("."+id).show();
								}
								if(response[i].attributeName.toLowerCase().indexOf("responses") >= 0){
									var htmlString="<div class='attributeDiv'>Student signed responses  <br/></div>";
									$(div).append(htmlString);
									$("."+id).show();
								}
								return false; 
							}
							if(id == "supportsRequiringAdditionalTools" && response[i].selectedValue.toLowerCase() == "true"){
								var div= document.getElementById(id);
								$("."+id).show();
								if(response[i].attributeName == "supportsTwoSwitch" && response[i].selectedValue.toLowerCase() == "true"){
									var htmlString="<div class='attributeDiv'>Two switch system<br/></div>";
									$(div).append(htmlString);
								}
								if(response[i].attributeName == "supportsAdminIpad" && response[i].selectedValue.toLowerCase() == "true"){
									var htmlString="<div class='attributeDiv'>Administration via iPad<br/></div>";
									$(div).append(htmlString);
								}
								if(response[i].attributeName == "supportsAdaptiveEquip" && response[i].selectedValue.toLowerCase() == "true"){
									var htmlString="<div class='attributeDiv'>Adaptive equipment<br/></div>";
									$(div).append(htmlString);
								}
								if(response[i].attributeName == "supportsIndividualizedManipulatives" && response[i].selectedValue.toLowerCase() == "true"){
									var htmlString="<div class='attributeDiv'>Individualized manipulatives<br/></div>";
									$(div).append(htmlString);
								}
								if(response[i].attributeName == "supportsCalculator" && response[i].selectedValue.toLowerCase() == "true"){
                                    var htmlString="<div class='attributeDiv'>Calculator<br/></div>";
                                    $(div).append(htmlString);
                                }
								return false; 
							}
							if(id == "supportsProvidedOutsideSystem" && response[i].selectedValue.toLowerCase() == "true"){
								var div= document.getElementById(id);
								$("."+id).show();
								if(response[i].attributeName == "supportsHumanReadAloud" && response[i].selectedValue.toLowerCase() == "true"){
									var htmlString="<div class='attributeDiv'>Human read aloud<br/></div>";
									$(div).append(htmlString);
								}
								if(response[i].attributeName == "supportsSignInterpretation" && response[i].selectedValue.toLowerCase() == "true"){
									var htmlString="<div class='attributeDiv'>Sign interpretation <br/></div>";
									$(div).append(htmlString);
								}
								if(response[i].attributeName == "supportsLanguageTranslation" && response[i].selectedValue.toLowerCase() == "true"){
									var htmlString="<div class='attributeDiv'>Language translation <br/></div>";
									$(div).append(htmlString);
								}
								if(response[i].attributeName == "supportsTestAdminEnteredResponses" && response[i].selectedValue.toLowerCase() == "true"){
									var htmlString="<div class='attributeDiv'>Test admin enters responses for student  <br/></div>";
									$(div).append(htmlString);
								}
								if(response[i].attributeName == "supportsPartnerAssistedScanning" && response[i].selectedValue.toLowerCase() == "true"){
									var htmlString="<div class='attributeDiv'>Partner assisted scanning  <br/></div>";
									$(div).append(htmlString);
								}
								
								if(response[i].attributeName == "supportsStudentProvidedAccommodations" && response[i].selectedValue.toLowerCase() == "true"){
									var htmlString="<div class='attributeDiv'>Student provided non-embedded accommodations as noted in IEP   <br/></div>";
									$(div).append(htmlString);
								}
								return false; 
							}
							if(id == "supportsProvidedByAlternateForm" && response[i].selectedValue.toLowerCase() == "true"){
								var div= document.getElementById(id);
								$("."+id).show();
								if(response[i].attributeName == "visualImpairment" && response[i].selectedValue.toLowerCase() == "true"){
									var htmlString="<div class='attributeDiv'>Alternate Form - Visual Impairment<br/></div>";
									$(div).append(htmlString);
								}
								if(response[i].attributeName == "largePrintBooklet" && response[i].selectedValue.toLowerCase() == "true"){
									var htmlString="<div class='attributeDiv'>Alternate Form - Large print booklet<br/></div>";
									$(div).append(htmlString);
								}
								if(response[i].attributeName == "paperAndPencil" && response[i].selectedValue.toLowerCase() == "true"){
									var htmlString="<div class='attributeDiv'>Alternate Form - Paper and Pencil<br/></div>";
									$(div).append(htmlString);
								}
								return false; 
							}
						}
					});
				}
			}
			}
		}
	});
}
function setCss(fgColor,bgColor,overlaycolor){
	$('.colouroverlay').css('background-color',overlaycolor);
$('.contrastColorDiv1').css('background-color',bgColor);
$('.contrastColorDiv1').css('color',fgColor); 
}