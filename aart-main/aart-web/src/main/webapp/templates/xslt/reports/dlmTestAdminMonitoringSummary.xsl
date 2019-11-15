<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java"
	xmlns:list="java.util.List"
	extension-element-prefixes="saxon">
	<!-- <xsl:include href="utility.xsl" /> <xsl:include href="resources.xsl" /> -->
	
	<xsl:attribute-set name="tableBorder">
		<xsl:attribute name="border">solid 0.3mm black</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="tableHeader" use-attribute-sets="tableBorder">
		<xsl:attribute name="font-weight">bold</xsl:attribute>
		<xsl:attribute name="display-align">center</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="tableHeader2" use-attribute-sets="tableBorder">
		<xsl:attribute name="font-size">8pt</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="districtHeader">
		<xsl:attribute name="font-size">11pt</xsl:attribute>
		<xsl:attribute name="margin-bottom">1.7mm</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="tableCell">
		<xsl:attribute name="border">solid 0.1mm black</xsl:attribute>
		<xsl:attribute name="display-align">center</xsl:attribute>
		<xsl:attribute name="height">0.5in</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" font-family="Verdana" font-size="9pt">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="page-master" page-height="8.5in" page-width="11in" margin=".25in">
					<!-- region-body margins MUST be equal to or greater than the "extent" 
						values in region-before and region-after, otherwise content could (and probably will) overlap and look terrible. -->
					<fo:region-body margin-top="1in" margin-bottom="0.25in" />
					<fo:region-before region-name="xsl-region-page-header" extent="1in" display-align="before" />
					<fo:region-after region-name="xsl-region-page-footer" extent="0.5in" display-align="after" />
				</fo:simple-page-master>
				<fo:page-sequence-master master-name="allPages">
					<fo:repeatable-page-master-alternatives>
						<fo:conditional-page-master-reference master-reference="page-master" page-position="any" />
					</fo:repeatable-page-master-alternatives>
				</fo:page-sequence-master>
			</fo:layout-master-set>
			
			<fo:page-sequence id="pages" master-reference="allPages" initial-page-number="1" force-page-count="no-force">
				<fo:static-content flow-name="xsl-region-page-header">
					<fo:block>
						<xsl:call-template name="page-header" />
					</fo:block>
				</fo:static-content>
				<fo:static-content flow-name="xsl-region-page-footer">
					<fo:block text-align="end">
						Page <fo:page-number /> of <fo:page-number-citation-last ref-id="pages" />
					</fo:block>
				</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
					<fo:block>
						<fo:block-container font-family="Verdana" color="#94b54d" font-weight="bold" font-size="11pt" line-height="12.5pt" text-align="center">
							<fo:block linefeed-treatment="preserve">DLM Test Administration Monitoring Summary&#xA;&#xA;</fo:block>
						</fo:block-container>
						<fo:block>
							<xsl:if test="(//reportContext/isIEState = 'false')">
								<xsl:call-template name="monitoringSummaryYEStates" />
							</xsl:if>
							<xsl:if test="(//reportContext/isIEState = 'true')">
								<xsl:call-template name="monitoringSummaryIEStates" />
							</xsl:if>		
						</fo:block>
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	<xsl:template name="page-header">
		<fo:table table-layout="fixed">
			<fo:table-column column-width="proportional-column-width(24)" />
			<fo:table-column column-width="proportional-column-width(52)" />
			<fo:table-column column-width="proportional-column-width(24)" />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell>
						<fo:block-container font-family="Verdana">
							<fo:block color="#94b54d" font-weight="bold" display-align="center">
								<xsl:value-of select="java:format(java:java.text.SimpleDateFormat.new('MM/dd/yyyy'), java:java.util.Date.new())" />
							</fo:block>
						</fo:block-container>
					</fo:table-cell>
					<fo:table-cell text-align="center">
						<fo:block />
					</fo:table-cell>
					<fo:table-cell text-align="end">
						<!-- width:height ratio of the DLM logo as of implementation is 2.44:1 -->
						<fo:block-container width="2.44in" height="1in">
							<fo:block>
								<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
									inline-progression-dimension.maximum="100%" block-progression-dimension.maximum="100%"
									width="2.44in" height="1in">
									<xsl:attribute name="src"><xsl:value-of select="//reportContext/logoPath" /></xsl:attribute>
								</fo:external-graphic>
							</fo:block>
						</fo:block-container>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>
	
	<xsl:template name="monitoringSummaryYEStates">
		<fo:table xsl:use-attribute-sets="tableBorder" table-layout="fixed" width="10.5in" text-align="center">
			<fo:table-header>
				<fo:table-row>
					<xsl:if test="(//reportContext/summaryLevel = 'district') or (//reportContext/summaryLevel = 'school')">
						<fo:table-cell xsl:use-attribute-sets="tableHeader">
							<fo:block>District ID</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="tableHeader">
							<fo:block>District Name</fo:block>
						</fo:table-cell>
					</xsl:if>
					<xsl:if test="//reportContext/summaryLevel = 'school'">
						<fo:table-cell xsl:use-attribute-sets="tableHeader">
							<fo:block>School ID</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="tableHeader">
							<fo:block>School Name</fo:block>
						</fo:table-cell>
					</xsl:if>
					<fo:table-cell xsl:use-attribute-sets="tableHeader">
						<fo:block>Grade</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader">
						<fo:block>Subject</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader" number-columns-spanned="4">
						<fo:block linefeed-treatment="preserve">Instructionally	Embedded&#xA;Number of Rostered Students</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader" number-columns-spanned="3">
						<fo:block linefeed-treatment="preserve">Year End&#xA;Number of Rostered Students</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<xsl:if test="//reportContext/summaryLevel = 'state'">
						<fo:table-cell xsl:use-attribute-sets="tableHeader" number-columns-spanned="2">
							<fo:block></fo:block>
						</fo:table-cell>
					</xsl:if>
					<xsl:if test="//reportContext/summaryLevel = 'district'">
						<fo:table-cell xsl:use-attribute-sets="tableHeader" number-columns-spanned="4">
							<fo:block></fo:block>
						</fo:table-cell>
					</xsl:if>
					<xsl:if test="//reportContext/summaryLevel = 'school'">
						<fo:table-cell xsl:use-attribute-sets="tableHeader" number-columns-spanned="6">
							<fo:block></fo:block>
						</fo:table-cell>
					</xsl:if>
					<fo:table-cell xsl:use-attribute-sets="tableHeader">
						<fo:block linefeed-treatment="preserve">No Plans Created</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader">
						<fo:block linefeed-treatment="preserve">Plans Created; No Testlets Taken</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader">
						<fo:block linefeed-treatment="preserve">Only One Testlet Completed</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader">
						<fo:block linefeed-treatment="preserve">More Than One Testlet Completed</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader">
						<fo:block linefeed-treatment="preserve">No Testlets Taken</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader">
						<fo:block linefeed-treatment="preserve">Testing In Progress</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader">
						<fo:block linefeed-treatment="preserve">All Required Testlets Completed</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-header>
			<fo:table-body>
				<xsl:choose>
					<xsl:when test="//reportContext/dataSize = 0">
						<fo:table-row>
							<xsl:if test="(//reportContext/summaryLevel = 'state')">
								<fo:table-cell xsl:use-attribute-sets="tableCell" number-columns-spanned="9">
									<fo:block>No data to show.</fo:block>
								</fo:table-cell>
							</xsl:if>
							<xsl:if test="(//reportContext/summaryLevel = 'district')">
								<fo:table-cell xsl:use-attribute-sets="tableCell" number-columns-spanned="11">
									<fo:block>No data to show.</fo:block>
								</fo:table-cell>
							</xsl:if>
							<xsl:if test="//reportContext/summaryLevel = 'school'">
								<fo:table-cell xsl:use-attribute-sets="tableCell" number-columns-spanned="13">
									<fo:block>No data to show.</fo:block>
								</fo:table-cell>
							</xsl:if>
						</fo:table-row>
					</xsl:when>
					<xsl:otherwise>
						<xsl:for-each select="//reportContext/data/edu.ku.cete.web.DLMTestAdminMonitoringSummaryDTO">
							<fo:table-row>
								<xsl:if test="(//reportContext/summaryLevel = 'district') or (//reportContext/summaryLevel = 'school')">
									<fo:table-cell xsl:use-attribute-sets="tableCell">
										<fo:block><xsl:value-of select="districtIdentifier" /></fo:block>
									</fo:table-cell>
									<fo:table-cell xsl:use-attribute-sets="tableCell">
										<fo:block><xsl:value-of select="districtName" /></fo:block>
									</fo:table-cell>
								</xsl:if>
								<xsl:if test="//reportContext/summaryLevel = 'school'">
									<fo:table-cell xsl:use-attribute-sets="tableCell">
										<fo:block><xsl:value-of select="schoolIdentifier" /></fo:block>
									</fo:table-cell>
									<fo:table-cell xsl:use-attribute-sets="tableCell">
										<fo:block><xsl:value-of select="schoolName" /></fo:block>
									</fo:table-cell>
								</xsl:if>
								<fo:table-cell xsl:use-attribute-sets="tableCell">
									<fo:block><xsl:value-of select="grade" /></fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="tableCell">
									<fo:block><xsl:value-of select="subject" /></fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="tableCell">
									<fo:block><xsl:value-of select="count(studentsWithInstructionallyEmbeddedNoPlansCreated/*)" /></fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="tableCell">
									<fo:block><xsl:value-of select="count(studentsWithInstructionallyEmbeddedPlansCreatedNoTestletsTaken/*)" /></fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="tableCell">
									<fo:block><xsl:value-of select="count(studentsWithInstructionallyEmbeddedOnlyOneTestletCompleted/*)" /></fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="tableCell">
									<fo:block><xsl:value-of select="count(studentsWithInstructionallyEmbeddedMoreThanOneTestletCompleted/*)" /></fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="tableCell">
									<fo:block><xsl:value-of select="count(studentsWithYearEndNoTestletsTaken/*)" /></fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="tableCell">
									<fo:block><xsl:value-of select="count(studentsWithYearEndTestingInProgress/*)" /></fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="tableCell">
									<fo:block><xsl:value-of select="count(studentsWithYearEndAllRequiredTestletsCompleted/*)" /></fo:block>
								</fo:table-cell>
							</fo:table-row>
						</xsl:for-each>
					</xsl:otherwise>
				</xsl:choose>
			</fo:table-body>
		</fo:table>
	</xsl:template>
	
	<xsl:template name="monitoringSummaryIEStates">
		<xsl:if test="(//reportContext/summaryLevel = 'state')">
			<xsl:call-template name="summaryLevelStateIEStates" />
		</xsl:if>
		
		<xsl:if test="(//reportContext/summaryLevel = 'district') or (//reportContext/summaryLevel = 'school')">
			<xsl:call-template name="summaryLevelSchoolDistrictIEStates" />
		</xsl:if>
		
	</xsl:template>
	
	
	<xsl:template name="summaryLevelStateIEStates">
		<fo:table xsl:use-attribute-sets="tableBorder" table-layout="fixed" width="10.5in" text-align="center">
			<fo:table-header>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="tableHeader">
						<fo:block>Grade</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader">
						<fo:block>Subject</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader" number-columns-spanned="4">
						<fo:block linefeed-treatment="preserve">Instructionally	Embedded&#xA;<xsl:value-of select="reportContext/testingCycleName1"/> Number of Rostered Students</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader" number-columns-spanned="4">
						<fo:block linefeed-treatment="preserve">Instructionally	Embedded&#xA;<xsl:value-of select="reportContext/testingCycleName2"/> Number of Rostered Students</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader" number-columns-spanned="3">
						<fo:block linefeed-treatment="preserve">Spring Science&#xA;Number of Rostered Students</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<xsl:if test="//reportContext/summaryLevel = 'state'">
						<fo:table-cell xsl:use-attribute-sets="tableHeader" number-columns-spanned="2">
							<fo:block></fo:block>
						</fo:table-cell>
					</xsl:if>
					
					<fo:table-cell xsl:use-attribute-sets="tableHeader tableHeader2">
						<fo:block linefeed-treatment="preserve">No Plans Created</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader tableHeader2">
						<fo:block linefeed-treatment="preserve">No Testlets Taken</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader tableHeader2">
						<fo:block linefeed-treatment="preserve">Only One Testlet Completed</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader tableHeader2">
						<fo:block linefeed-treatment="preserve">More Than One Testlet Completed</fo:block>
					</fo:table-cell>
					
					<fo:table-cell xsl:use-attribute-sets="tableHeader tableHeader2">
						<fo:block linefeed-treatment="preserve">No Plans Created</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader tableHeader2">
						<fo:block linefeed-treatment="preserve">No Testlets Taken</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader tableHeader2">
						<fo:block linefeed-treatment="preserve">Only One Testlet Completed</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader tableHeader2">
						<fo:block linefeed-treatment="preserve">More Than One Testlet Completed</fo:block>
					</fo:table-cell>
					
					<fo:table-cell xsl:use-attribute-sets="tableHeader tableHeader2">
						<fo:block linefeed-treatment="preserve">No Testlets Taken</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader tableHeader2">
						<fo:block linefeed-treatment="preserve">Testing In Progress</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader tableHeader2">
						<fo:block linefeed-treatment="preserve">All Required Testlets Completed</fo:block>
					</fo:table-cell>
					
				</fo:table-row>
			</fo:table-header>
			<fo:table-body>
				<xsl:choose>
					<xsl:when test="//reportContext/dataSize = 0">
						<fo:table-row>
							<xsl:if test="(//reportContext/summaryLevel = 'state')">
								<fo:table-cell xsl:use-attribute-sets="tableCell" number-columns-spanned="13">
									<fo:block>No data to show.</fo:block>
								</fo:table-cell>
							</xsl:if>
						</fo:table-row>
					</xsl:when>
					<xsl:otherwise>
						<xsl:for-each select="//reportContext/data/edu.ku.cete.web.DLMTestAdminMonitoringSummaryDTO">
						<xsl:variable name="curDMLTestData" select="."/>
						
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="tableCell">
									<fo:block><xsl:value-of select="grade" /></fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="tableCell">
									<fo:block><xsl:value-of select="subject" /> </fo:block>
								</fo:table-cell>
								
								<fo:table-cell xsl:use-attribute-sets="tableCell">
									<fo:block><xsl:value-of select="count(studentsWithInstructionallyEmbeddedNoPlansCreated/*)" /></fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="tableCell">
									<fo:block><xsl:value-of select="count(studentsWithInstructionallyEmbeddedPlansCreatedNoTestletsTaken/*)" /></fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="tableCell">
									<fo:block><xsl:value-of select="count(studentsWithInstructionallyEmbeddedOnlyOneTestletCompleted/*)" /></fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="tableCell">
									<fo:block><xsl:value-of select="count(studentsWithInstructionallyEmbeddedMoreThanOneTestletCompleted/*)" /></fo:block>
								</fo:table-cell>
								
								<xsl:choose>
									<xsl:when test="$curDMLTestData/subject = 'Science'">
										<fo:table-cell xsl:use-attribute-sets="tableCell">
										<fo:block>N/A</fo:block>
										</fo:table-cell>
										<fo:table-cell xsl:use-attribute-sets="tableCell">
											<fo:block>N/A</fo:block>
										</fo:table-cell>
										<fo:table-cell xsl:use-attribute-sets="tableCell">
											<fo:block>N/A</fo:block>
										</fo:table-cell>
										<fo:table-cell xsl:use-attribute-sets="tableCell">
											<fo:block>N/A</fo:block>
										</fo:table-cell>
									</xsl:when>
									<xsl:otherwise>
										<fo:table-cell xsl:use-attribute-sets="tableCell">
										<fo:block><xsl:value-of select="count(studentsWithInstructionallyEmbeddedNoPlansCreatedSecondWindow/*)" /></fo:block>
										</fo:table-cell>
										<fo:table-cell xsl:use-attribute-sets="tableCell">
											<fo:block><xsl:value-of select="count(studentsWithInstructionallyEmbeddedPlansCreatedNoTestletsTakenSecondWindow/*)" /></fo:block>
										</fo:table-cell>
										<fo:table-cell xsl:use-attribute-sets="tableCell">
											<fo:block><xsl:value-of select="count(studentsWithInstructionallyEmbeddedOnlyOneTestletCompletedSecondWindow/*)" /></fo:block>
										</fo:table-cell>
										<fo:table-cell xsl:use-attribute-sets="tableCell">
											<fo:block><xsl:value-of select="count(studentsWithInstructionallyEmbeddedMoreThanOneTestletCompletedSecondWindow/*)" /></fo:block>
										</fo:table-cell>
									</xsl:otherwise>
								</xsl:choose>
								
								<xsl:choose>
									<xsl:when test="$curDMLTestData/subject = 'Science'">
										<fo:table-cell xsl:use-attribute-sets="tableCell">
										<fo:block><xsl:value-of select="count(studentsWithYearEndNoTestletsTaken/*)" /></fo:block>
										</fo:table-cell>
										<fo:table-cell xsl:use-attribute-sets="tableCell">
											<fo:block><xsl:value-of select="count(studentsWithYearEndTestingInProgress/*)" /></fo:block>
										</fo:table-cell>
										<fo:table-cell xsl:use-attribute-sets="tableCell">
											<fo:block><xsl:value-of select="count(studentsWithYearEndAllRequiredTestletsCompleted/*)" /></fo:block>
										</fo:table-cell>
									</xsl:when>
									<xsl:otherwise>
										<fo:table-cell xsl:use-attribute-sets="tableCell">
										<fo:block>N/A</fo:block>
										</fo:table-cell>
										<fo:table-cell xsl:use-attribute-sets="tableCell">
											<fo:block>N/A</fo:block>
										</fo:table-cell>
										<fo:table-cell xsl:use-attribute-sets="tableCell">
											<fo:block>N/A</fo:block>
										</fo:table-cell>
									</xsl:otherwise>
								</xsl:choose>
							</fo:table-row>
						</xsl:for-each>
					</xsl:otherwise>
				</xsl:choose>
			</fo:table-body>
		</fo:table>
	</xsl:template>
	
	<xsl:template name="summaryLevelSchoolDistrictIEStates">
	<xsl:variable name="preDistrictID" select="''" />
	<xsl:for-each select="//reportContext/data/edu.ku.cete.report.DLMMonitoringSummaryDistrictSchoolData">
	<fo:block break-after='page'>
		<xsl:variable name="curDMLDistrictTestData" select="."/>
		&#xA;
		<fo:block xsl:use-attribute-sets="districtHeader">District : <xsl:value-of select="$curDMLDistrictTestData/districtName" /></fo:block>
		<fo:table xsl:use-attribute-sets="tableBorder" table-layout="fixed" width="10.5in" text-align="center">
			<fo:table-header>
				<fo:table-row>

					<fo:table-cell xsl:use-attribute-sets="tableHeader">
						<xsl:if test="//reportContext/summaryLevel = 'district'">
							<fo:block>District Name</fo:block>
						</xsl:if>
						<xsl:if test="//reportContext/summaryLevel = 'school'">
							<fo:block>School Name</fo:block>
						</xsl:if>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader">
						<fo:block>Grade</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader">
						<fo:block>Subject</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader" number-columns-spanned="4">
						<fo:block linefeed-treatment="preserve">Instructionally	Embedded&#xA;<xsl:value-of select="$curDMLDistrictTestData/testingCycleName1"/> Number of Rostered Students</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader" number-columns-spanned="4">
						<fo:block linefeed-treatment="preserve">Instructionally	Embedded&#xA;<xsl:value-of select="$curDMLDistrictTestData/testingCycleName2"/> Number of Rostered Students</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader" number-columns-spanned="3">
						<fo:block linefeed-treatment="preserve">Spring Science&#xA;Number of Rostered Students</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>

					<fo:table-cell xsl:use-attribute-sets="tableHeader" number-columns-spanned="3">
						<fo:block></fo:block>
					</fo:table-cell>

					
					<fo:table-cell xsl:use-attribute-sets="tableHeader tableHeader2">
						<fo:block linefeed-treatment="preserve">No Plans Created</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader tableHeader2">
						<fo:block linefeed-treatment="preserve">No Testlets Taken</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader tableHeader2">
						<fo:block linefeed-treatment="preserve">Only One Testlet Completed</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader tableHeader2">
						<fo:block linefeed-treatment="preserve">More Than One Testlet Completed</fo:block>
					</fo:table-cell>
					
					<fo:table-cell xsl:use-attribute-sets="tableHeader tableHeader2">
						<fo:block linefeed-treatment="preserve">No Plans Created</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader tableHeader2">
						<fo:block linefeed-treatment="preserve">No Testlets Taken</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader tableHeader2">
						<fo:block linefeed-treatment="preserve">Only One Testlet Completed</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader tableHeader2">
						<fo:block linefeed-treatment="preserve">More Than One Testlet Completed</fo:block>
					</fo:table-cell>
					
					<fo:table-cell xsl:use-attribute-sets="tableHeader tableHeader2">
						<fo:block linefeed-treatment="preserve">No Testlets Taken</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader tableHeader2">
						<fo:block linefeed-treatment="preserve">Testing In Progress</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tableHeader tableHeader2">
						<fo:block linefeed-treatment="preserve">All Required Testlets Completed</fo:block>
					</fo:table-cell>
					
				</fo:table-row>
			</fo:table-header>
			<fo:table-body>
				<xsl:choose>
					<xsl:when test="$curDMLDistrictTestData/noRecordFound = 'true'">
						<fo:table-row>
							<fo:table-cell xsl:use-attribute-sets="tableCell" number-columns-spanned="14">
								<fo:block text-align="center">No data to show.</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</xsl:when>
					<xsl:otherwise>
						<xsl:for-each select="$curDMLDistrictTestData/testAdminSummaryDtos/edu.ku.cete.web.DLMTestAdminMonitoringSummaryDTO">
						<xsl:variable name="curDMLTestData" select="."/>
						
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="tableCell">
										<xsl:if test="//reportContext/summaryLevel = 'district'">
											<fo:block><xsl:value-of select="districtName" /></fo:block>
										</xsl:if>
										<xsl:if test="//reportContext/summaryLevel = 'school'">
											<fo:block><xsl:value-of select="schoolName" /></fo:block>
										</xsl:if>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="tableCell">
									<fo:block><xsl:value-of select="grade" /></fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="tableCell">
									<fo:block><xsl:value-of select="subject" /> </fo:block>
								</fo:table-cell>
								
								<fo:table-cell xsl:use-attribute-sets="tableCell">
									<fo:block><xsl:value-of select="count(studentsWithInstructionallyEmbeddedNoPlansCreated/*)" /></fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="tableCell">
									<fo:block><xsl:value-of select="count(studentsWithInstructionallyEmbeddedPlansCreatedNoTestletsTaken/*)" /></fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="tableCell">
									<fo:block><xsl:value-of select="count(studentsWithInstructionallyEmbeddedOnlyOneTestletCompleted/*)" /></fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="tableCell">
									<fo:block><xsl:value-of select="count(studentsWithInstructionallyEmbeddedMoreThanOneTestletCompleted/*)" /></fo:block>
								</fo:table-cell>
								
								<xsl:choose>
									<xsl:when test="$curDMLTestData/subject = 'Science'">
										<fo:table-cell xsl:use-attribute-sets="tableCell">
										<fo:block>N/A</fo:block>
										</fo:table-cell>
										<fo:table-cell xsl:use-attribute-sets="tableCell">
											<fo:block>N/A</fo:block>
										</fo:table-cell>
										<fo:table-cell xsl:use-attribute-sets="tableCell">
											<fo:block>N/A</fo:block>
										</fo:table-cell>
										<fo:table-cell xsl:use-attribute-sets="tableCell">
											<fo:block>N/A</fo:block>
										</fo:table-cell>
									</xsl:when>
									<xsl:otherwise>
										<fo:table-cell xsl:use-attribute-sets="tableCell">
										<fo:block><xsl:value-of select="count(studentsWithInstructionallyEmbeddedNoPlansCreatedSecondWindow/*)" /></fo:block>
										</fo:table-cell>
										<fo:table-cell xsl:use-attribute-sets="tableCell">
											<fo:block><xsl:value-of select="count(studentsWithInstructionallyEmbeddedPlansCreatedNoTestletsTakenSecondWindow/*)" /></fo:block>
										</fo:table-cell>
										<fo:table-cell xsl:use-attribute-sets="tableCell">
											<fo:block><xsl:value-of select="count(studentsWithInstructionallyEmbeddedOnlyOneTestletCompletedSecondWindow/*)" /></fo:block>
										</fo:table-cell>
										<fo:table-cell xsl:use-attribute-sets="tableCell">
											<fo:block><xsl:value-of select="count(studentsWithInstructionallyEmbeddedMoreThanOneTestletCompletedSecondWindow/*)" /></fo:block>
										</fo:table-cell>
									</xsl:otherwise>
								</xsl:choose>
								
								<xsl:choose>
									<xsl:when test="$curDMLTestData/subject = 'Science'">
										<fo:table-cell xsl:use-attribute-sets="tableCell">
										<fo:block><xsl:value-of select="count(studentsWithYearEndNoTestletsTaken/*)" /></fo:block>
										</fo:table-cell>
										<fo:table-cell xsl:use-attribute-sets="tableCell">
											<fo:block><xsl:value-of select="count(studentsWithYearEndTestingInProgress/*)" /></fo:block>
										</fo:table-cell>
										<fo:table-cell xsl:use-attribute-sets="tableCell">
											<fo:block><xsl:value-of select="count(studentsWithYearEndAllRequiredTestletsCompleted/*)" /></fo:block>
										</fo:table-cell>
									</xsl:when>
									<xsl:otherwise>
										<fo:table-cell xsl:use-attribute-sets="tableCell">
										<fo:block>N/A</fo:block>
										</fo:table-cell>
										<fo:table-cell xsl:use-attribute-sets="tableCell">
											<fo:block>N/A</fo:block>
										</fo:table-cell>
										<fo:table-cell xsl:use-attribute-sets="tableCell">
											<fo:block>N/A</fo:block>
										</fo:table-cell>
									</xsl:otherwise>
								</xsl:choose>
							</fo:table-row>
						</xsl:for-each>
					</xsl:otherwise>
				</xsl:choose>
			</fo:table-body>
		</fo:table>
	</fo:block>	
	</xsl:for-each>
		
	</xsl:template>
	

	
</xsl:stylesheet>