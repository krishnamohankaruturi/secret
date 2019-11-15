<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='security'
	uri='http://www.springframework.org/security/tags'%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style type="text/css">
img {
	max-width: 100%;
	height: auto;
	width: auto\9; /* ie8 */
}
</style>
<div style="width: 100%" class = "pagecontent">
	<div id="testCollectionInformationHeaderDiv"
		style="width: 100%; text-align: center">
		Test Collection Information
	</div>
	<!-- Left Margin -->
	<div id="testCollectionInformationLeftMarginDiv" style="width: 5%; float: left;">
		&nbsp;
	</div>
	
	<div id="testCollection InformationMainDiv" style="width: 90%; float: left;" class="none"> <!-- Test Collection Information Div -->
		Test Collection Name : 	${testCollection.name} <BR/>
		<c:if test="${! empty testCollection.tests}">
				Number Of Tests : ${fn:length(testCollection.tests)} <BR/>
		</c:if>
		<hr style="width: 100%; height: 2px;"/>
		<c:if test="${! empty testCollection.tests}">
		<c:forEach var="test" items="${testCollection.tests}">
				<div id="testInformationHeaderDiv"
					style="width: 90%; text-align: center">
					Test Information
				</div>
				<!-- Left Margin -->
				<div id="testInformationLeftMarginDiv" style="width: 5%; float: left;">
					&nbsp;
				</div>
				<div id="testInformationMainDiv" style="width: 90%; float: left;">
				Test Name : ${test.testName} <BR/>
				<c:if test="${! empty test.testSections}"> <!-- Test Section information -->
				Number Of Test Sections : ${fn:length(test.testSections)} 
				<BR />
				<hr style="width: 100%; height: 2px;"/>
					<c:forEach var="testSection" items="${test.testSections}">
						<!-- Left Margin -->
						<div id="sectionInformationLeftMarginDiv" style="width: 5%; float: left;">
							&nbsp;
						</div>					
						<div id="SectionInformationHeaderDiv"
							style="width: 90%; text-align: center">Section Information
						</div>			
						Test Section Name : ${testSection.testSectionName}
						<br/>
						Number Of Test Items : ${testSection.numberOfTestItems}
						<BR />
						<hr style="width: 100%; height: 2px;"/>
						<c:if test="${! empty testSection.contextStimulus}">
							<div style="width: 100%; text-align: center">
								<div id="contextStimulusDiv" style="width: 100%">
									Stimulus: ${testSection.contextStimulus.stimulusTitle}
								</div>
								<BR />
							</div>
							<BR />
						</c:if>
						<BR />
						<c:if test="${! empty test.testSections}">
							<div style="width: 100%; text-align: center">Item Information
							</div>
							<!-- Left Margin -->
							<div id="taskVariantLeftMarginDiv" style="width: 5%; float: left;">
								&nbsp;</div>
							<div id="taskVariantCenterDiv"
								style="max-width: 100%; width: 90%; float: left;">
								<c:forEach var="testSectionTaskVariant"
									items="${testSection.testSectionsTaskVariants}">
										${testSectionTaskVariant.taskVariantPosition}.${testSectionTaskVariant.taskVariant.taskName}
										<BR />
									<c:if
										test="${! empty testSectionTaskVariant.taskVariant.primaryContentFrameworkDetail}">
		 							Content Code : ${testSectionTaskVariant.taskVariant.primaryContentFrameworkDetail.contentCode} 							
										<BR />
		 							</c:if>
									<c:if
										test="${! empty testSectionTaskVariant.taskVariant.lmNodes}">
		 							Node Information : 			
		 								<c:forEach var="lmNode"
									items="${testSectionTaskVariant.taskVariant.lmNodes}">
			 							<BR/>
			 							Node System Id : ${lmNode.nodeId}
			 							<BR/>
			 							Node Id : ${lmNode.nodeKey}
			 							<BR/>
			 							Node Name : ${lmNode.nodeName}
			 							<BR/>
			 							Profiles : ${lmNode.profiles}
			 							<BR/>
			 								<c:if
											test="${! empty lmNode.attributeAndValuesForPreview}">
												Attribute And Values : 
												<c:forEach var="attributeAndValue"
													items="${lmNode.attributeAndValuesForPreview}">
												<BR/>
												${attributeAndValue}
												</c:forEach>
											</c:if>
			 							<BR/>
										</c:forEach>					
		 							</c:if>
		 									 															
									<hr  style="width: 100%; height: 2px;"/>
								</c:forEach>
							</div>
							<div style="width: 5%; float: right;">&nbsp;</div>
						</c:if>
					</c:forEach>
					<BR />
				</c:if><!-- End of test section information div. -->
				</div> <!-- Test Information main div -->
			<BR/>
		</c:forEach><!-- End of test information div. -->
		</c:if>
	</div> <!-- End of Test Collection information Div -->
	<div id="testCollectionInformationRightMarginDiv"
		style="width: 5%; float: right;">&nbsp;</div>
</div>

