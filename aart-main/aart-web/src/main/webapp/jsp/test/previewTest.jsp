<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='security' uri='http://www.springframework.org/security/tags' %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%-- 
<link rel="stylesheet" href="<c:url value='/css/jqmath-0.4.3.css'/>" type="text/css" />
<script type="text/javascript" src="<c:url value='/js/external/extensions/jqmath-etc-0.4.6.min.js'/>"></script>  --%>

<script type="text/javascript" src="<c:url value='/js/test/previewTest.js'/>"></script>
<link rel="stylesheet" href="<c:url value='/css/previewTest.css'/>" type="text/css" />
<!-- TODO rename this to preview Test Collection -->
<div class = "pagecontent previewContent" align="left"  border-style="solid" border-color="blue">
	<!-- Left Margin -->
	<div id="testCollection InformationMainDiv" style="width: auto; padding:1%;" class="none"> 
		<c:if test="${! empty testCollection.tests}">
			<c:forEach var="test" items="${testCollection.tests}" varStatus="status" >  
		  		<div id="testInformationMainDiv_${status.index}" style="width:auto;padding:1%;">
		  			<span id="testname_${status.index}" class="testNameHeader" ><b>Test Name : ${test.testName}</b></span>
					<c:if test="${! empty test.testSections}">
						<span id="testnum_${status.index}" class="testSectionsNumber"><b>Number Of Test Sections : ${fn:length(test.testSections)}</b> </span>
						<h4 class="guidanceHeader">Guidance</h4>
						<c:if test="${user.currentAssessmentProgramName != 'PLTW'}">
							<div class="testGuidance"><fmt:message key='testpreview.guidence.other'/></div>
						</c:if>
						<c:if test="${user.currentAssessmentProgramName == 'PLTW'}">
							<div class="testGuidance"><fmt:message key='testpreview.guidance.pltw'/></div>
						</c:if>
						<c:forEach var="testSection" items="${test.testSections}" varStatus="testSectionCount">
							<br/>
				 			<span id="testsectionname_${testSectionCount.index}" class="testSectionName"><b>Test Section Name : ${testSection.testSectionName}</b></span>
							<span id="testitem_${testSectionCount.index}" class="testSectionsNumber"><b>Number Of Test Items : ${testSection.numberOfTestItems}</b></span>
						 	<br/><br/>
					    	${testSection.beginInstructions}
					   		${testSection.endInstructions} 						
					  		<br/>
					   		<br/>
					  		<c:if test="${! empty test.testSections}">
								<div id="taskVariantCenterDiv_${testSectionCount.index}"  style="max-width:100%;width:100%;float:left;">
									<c:if test="${! empty testSection.contextStimulus}">
										<div style="width:100%;">
											<div id="contextStimulusLeftMarginDiv_${testSectionCount.index}" style="width:5%;float: left;">&nbsp;</div>
												<div id="contextStimulusCenterDiv_${testSectionCount.index}" style="max-width:100%;width:100%; float: left ;font-size: 14pt">
													${testSection.contextStimulus.stimulusContent}
												</div>
											<div id="contextStimulusRightDiv_${testSectionCount.index}" style="width:5%;float: right"></div>
											<br/>
										</div>
									</c:if>
							  		<table class="previewTest" role="presentation">
										<c:forEach var="testSectionTaskVariant" items="${testSection.testSectionsTaskVariants}" varStatus="loop">
											<%-- Brad: commented for F807 (we may want it back later, so I didn't remove it)
											<c:if test="${loop.index == 0}">
												<tr><td style="width:85%;">&nbsp;</td><th style="width:14%;text-align:center;">Content Code</th></tr>
											</c:if>
											--%>
											<tr>
												<td>
													<div class="indexColumn">${testSectionTaskVariant.taskVariantPosition}.</div>
													<div class="questionColumn" data-tasktype="${testSectionTaskVariant.taskVariant.taskType}" 
														data-taskvariantid="${testSectionTaskVariant.taskVariant.id}"
														data-testletid="${testSectionTaskVariant.testletExternalId}"
														data-taskVarientIndex="${testSectionTaskVariant.taskVariantPosition}"
														data-scoringdata='${testSectionTaskVariant.taskVariant.scoringData}'>
														${testSectionTaskVariant.taskVariant.taskStem}
														<c:if test="${testSectionTaskVariant.taskVariant.taskType == 'ITP'}">
															<input type="hidden" id="${testSectionTaskVariant.taskVariant.id}" 
																data-task-subtype="${testSectionTaskVariant.taskVariant.taskSubType}" 
																class="itp-element"  
																value="${MEDIA_PATH}${testSectionTaskVariant.taskVariant.innovativeItemPackagePath}/index.html"/>
															<div id="itpContent_${testSectionTaskVariant.taskVariant.id}"></div>
														</c:if>
														<c:if test="${! empty testSectionTaskVariant.taskVariant.taskVariantsFoils}">
															<c:forEach var="taskVariantFoil" items="${testSectionTaskVariant.taskVariant.taskVariantsFoils}" varStatus="foilCount">			
																<div class="optionContainer">
																	<div class="optionImage questionColumn indexColumn"><c:if test="${taskVariantFoil.correctResponse}">
																		<img src="images/right-icon.png" title="Check Mark"/></c:if>
																	</div>
																	<div class="optionItem questionColumn">
																		<c:if test="${taskVariantFoil.alphabeticalResponseOrder!=''}">
																			${taskVariantFoil.alphabeticalResponseOrder} )
																		</c:if>
																		${taskVariantFoil.foil.foilText}
																	</div>
																</div>
															</c:forEach>
														</c:if>
													</div>
												</td>
												<%-- Brad: commented for F807 (we may want it back later, so I didn't remove it)
					 							<c:if test="${! empty testSectionTaskVariant.taskVariant.primaryContentFrameworkDetail}">
					 								<td style="text-align:center;padding:1%;">${testSectionTaskVariant.taskVariant.primaryContentFrameworkDetail.contentCode}</td>							
					 							</c:if>
												--%>
		 									</tr>
		 									<tr class = "feedback">
		 									</tr>
										</c:forEach>
									</table>
									<div id="feedbackdiv"></div>
									<br/>
									<br/>
							  	</div>
							</c:if>							
						</c:forEach>
						<br/>
					</c:if>	
				</div> <!-- End of test information main div -->
		  </c:forEach><!-- End of test information div. -->
		</c:if>
	</div> <!-- End of Test Collection information Div -->
</div>
  	 
       	 
 <script type="text/x-mathjax-config">
  	MathJax.Hub.Config({
    	tex2jax: {inlineMath: [["$","$"],["\\(","\\)"]]}
	});
       
<script type="text/javascript">
	$( document ).ready(function() {
		var BrowserDetect = {
        init: function () {
            this.browser = this.searchString(this.dataBrowser) || "Other";
            this.version = this.searchVersion(navigator.userAgent) || this.searchVersion(navigator.appVersion) || "Unknown";
        },
        searchString: function (data) {
            for (var i = 0; i < data.length; i++) {
                var dataString = data[i].string;
                this.versionSearchString = data[i].subString;

                if (dataString.indexOf(data[i].subString) !== -1) {
                    return data[i].identity;
                }
            }
        },
        searchVersion: function (dataString) {
            var index = dataString.indexOf(this.versionSearchString);
            if (index === -1) {
                return;
            }

            var rv = dataString.indexOf("rv:");
            if (this.versionSearchString === "Trident" && rv !== -1) {
                return parseFloat(dataString.substring(rv + 3));
            } else {
                return parseFloat(dataString.substring(index + this.versionSearchString.length + 1));
            }
        },

        dataBrowser: [
            {string: navigator.userAgent, subString: "Edge", identity: "MS Edge"},
            {string: navigator.userAgent, subString: "MSIE", identity: "Explorer"},
            {string: navigator.userAgent, subString: "Trident", identity: "Explorer"},
            {string: navigator.userAgent, subString: "Firefox", identity: "Firefox"},
            {string: navigator.userAgent, subString: "Opera", identity: "Opera"},  
            {string: navigator.userAgent, subString: "OPR", identity: "Opera"},  

            {string: navigator.userAgent, subString: "Chrome", identity: "Chrome"}, 
            {string: navigator.userAgent, subString: "Safari", identity: "Safari"}       
        ]};
    
	    BrowserDetect.init();
			    
	    if(BrowserDetect.browser === 'Explorer' && BrowserDetect.version < 11){
	        $.getScript("<c:url value='/js/test/mutationobserver.min.js'/>").done(function(){
	        	loadItems();
	        });
	    } else {
	    	loadItems();
	    }
	    
	    $('[data-tasktype=CR]').each(function (index, value){
	    	var crItem = $(this).find('.constructedResponse');
	    	var itemIndex = index;
	    	$(this).data('scoringdata').forEach(function(element, i) {
	    		var position = i;
	    		if(element.acceptableResponses && element.acceptableResponses.length > 0) {
	    			position = element.acceptableResponses[0].index;
	    		}
	    		$(crItem[position]).html('');
	    		$(crItem[position]).append('<input disabled="disabled" value="'+element.correctResponse.value+'">');
    		});
	    });
	});
</script>

