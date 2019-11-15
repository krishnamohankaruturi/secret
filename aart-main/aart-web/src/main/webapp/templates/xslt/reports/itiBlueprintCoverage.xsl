<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java"
	xmlns:javadate="java:java.util.Date"
	extension-element-prefixes="">
	
	<xsl:param name="byTeacher"/>
	<xsl:param name="metCriterionImage"/>
	<xsl:param name="partiallyMetCriterionImage"/>
	<xsl:param name="completedTestletImage"/>
	<xsl:param name="notTestedImage"/>
	<xsl:param name="dateString"/>
	<xsl:param name="orgName"/>
	
	<xsl:attribute-set name="root">
		<xsl:attribute name="font-family">Verdana</xsl:attribute>
		<xsl:attribute name="font-size">7.5pt</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="legend">
		<xsl:attribute name="font-size">7.5pt</xsl:attribute>
		<xsl:attribute name="text-align">right</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="legendImage">
		<xsl:attribute name="content-width">scale-down-to-fit</xsl:attribute>
		<xsl:attribute name="content-height">scale-down-to-fit</xsl:attribute>
		<xsl:attribute name="inline-progression-dimension.maximum">100%</xsl:attribute>
		<xsl:attribute name="block-progression-dimension.maximum">100%</xsl:attribute>
		<xsl:attribute name="width">0.15in</xsl:attribute>
		<xsl:attribute name="height">0.15in</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="completionImage">
		<xsl:attribute name="content-width">scale-down-to-fit</xsl:attribute>
		<xsl:attribute name="content-height">scale-down-to-fit</xsl:attribute>
		
		<xsl:attribute name="inline-progression-dimension.maximum">100%</xsl:attribute>
		<xsl:attribute name="block-progression-dimension.maximum">100%</xsl:attribute>
		<xsl:attribute name="width">0.3in</xsl:attribute>
		<xsl:attribute name="height">0.3in</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="testedImage">
		<xsl:attribute name="content-width">scale-down-to-fit</xsl:attribute>
		<xsl:attribute name="content-height">scale-down-to-fit</xsl:attribute>
		<xsl:attribute name="inline-progression-dimension.maximum">100%</xsl:attribute>
		<xsl:attribute name="block-progression-dimension.maximum">100%</xsl:attribute>
		<xsl:attribute name="width">0.15in</xsl:attribute>
		<xsl:attribute name="height">0.15in</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="teacherSubjectGradeHeader">
		<xsl:attribute name="font-size">10pt</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>
		<xsl:attribute name="color">#2c80dc</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="table">
		<xsl:attribute name="display-align">center</xsl:attribute>
		<xsl:attribute name="border">solid 0.3mm black</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="tableHeader" use-attribute-sets="table">
		<xsl:attribute name="font-weight">bold</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="tableCell">
		<xsl:attribute name="border">solid 0.1mm black</xsl:attribute>
		<xsl:attribute name="display-align">center</xsl:attribute>
		<xsl:attribute name="text-align">center</xsl:attribute>
		<xsl:attribute name="padding">.02in</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="descriptionTableCell" use-attribute-sets="tableCell">
		<xsl:attribute name="display-align">center</xsl:attribute>
		<xsl:attribute name="text-align">left</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="criterionDescriptionTableCell" use-attribute-sets="descriptionTableCell">
		<xsl:attribute name="color">#94b54d</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="eeDescriptionTableCell" use-attribute-sets="descriptionTableCell">
	</xsl:attribute-set>
	<xsl:attribute-set name="studentTableCell" use-attribute-sets="tableCell">
		<xsl:attribute name="font-size">6pt</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="tableCellBlock">
		<xsl:attribute name="page-break-inside">avoid</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:variable name="page-width-num">11</xsl:variable>
	<xsl:variable name="page-height-num">8.5</xsl:variable>
	<xsl:variable name="page-margin-num">0.25</xsl:variable>
	<xsl:variable name="page-width"><xsl:value-of select="$page-width-num"/>in</xsl:variable>
	<xsl:variable name="page-height"><xsl:value-of select="$page-height-num"/>in</xsl:variable>
	<xsl:variable name="page-margin"><xsl:value-of select="$page-margin-num"/>in</xsl:variable>
	
	<xsl:variable name="conceptualAreaColumnWidth">0.8</xsl:variable>
	<xsl:variable name="eeColumnWidth">1.35</xsl:variable>
	<xsl:variable name="eeDescriptionColumnWidth">1.9</xsl:variable>
	<xsl:variable name="studentColumnWidth">0.8</xsl:variable>
	<xsl:variable name="numberOfStudentsPerTable" select="floor(
		($page-width-num - (2 * $page-margin-num) - $conceptualAreaColumnWidth - $eeColumnWidth - $eeDescriptionColumnWidth) div $studentColumnWidth
	)"/>
	
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="root">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="page-master">
					<xsl:attribute name="page-width"><xsl:value-of select="$page-width"/></xsl:attribute>
					<xsl:attribute name="page-height"><xsl:value-of select="$page-height"/></xsl:attribute>
					<xsl:attribute name="margin"><xsl:value-of select="$page-margin"/></xsl:attribute>
					<!-- region-body margins should be equal to or greater than the "extent" 
						values in region-before and region-after, otherwise content could overlap and look terrible. -->
					<fo:region-body margin-top="1.55in" margin-bottom="0.25in" />
					<fo:region-before region-name="xsl-region-page-header" extent="1.55in" display-align="before" />
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
					<xsl:if test="//reportContext/dataSize > 0">
						<xsl:for-each select="//reportContext/data/edu.ku.cete.web.DLMBlueprintCoverageReportDTO">
							<xsl:variable name="report" select="."/>
							<xsl:call-template name="blueprintCoverageBlock">
								<xsl:with-param name="report" select="."/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:if>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	
	<xsl:template name="page-header">
		<fo:table table-layout="fixed">
			<fo:table-column column-width="proportional-column-width(35)" />
			<fo:table-column column-width="proportional-column-width(30)" />
			<fo:table-column column-width="proportional-column-width(35)" />
			<fo:table-body>
				<fo:table-row keep-together="always">
					<fo:table-cell>
						<fo:block-container font-family="Verdana" color="#94b54d" font-weight="bold">
							<fo:block>
								<xsl:value-of select="$dateString"/>
							</fo:block>
							<fo:block>
								<xsl:value-of select="$orgName"/>
							</fo:block>
						</fo:block-container>
					</fo:table-cell>
					<fo:table-cell text-align="center">
						<fo:block-container font-family="Verdana" color="#94b54d" font-weight="bold" display-align="center">
							<fo:block>Blueprint Coverage Report</fo:block>
						</fo:block-container>
					</fo:table-cell>
					<fo:table-cell text-align="end">
						<!-- width:height ratio of the DLM logo as of implementation is 2.44:1 -->
						<fo:block-container text-align="end">
							<fo:block>
								<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
									inline-progression-dimension.maximum="100%" block-progression-dimension.maximum="100%"
									width="2.44in" height="1in">
									<xsl:attribute name="src"><xsl:value-of select="//reportContext/logoPath" /></xsl:attribute>
								</fo:external-graphic>
							</fo:block>
						</fo:block-container>
						<fo:block-container xsl:use-attribute-sets="legend">
							<fo:block>
								<fo:block>
									<xsl:call-template name="legend-image" select="$metCriterionImage">
										<xsl:with-param name="src" select="$metCriterionImage"/>
									</xsl:call-template>
									met criterion
									
									<xsl:call-template name="legend-image">
										<xsl:with-param name="src" select="$completedTestletImage"/>
									</xsl:call-template>
									student has completed a testlet
								</fo:block>
								<fo:block>
									<xsl:call-template name="legend-image">
										<xsl:with-param name="src" select="$partiallyMetCriterionImage"/>
									</xsl:call-template>
									partially met
									
									<xsl:call-template name="legend-image">
										<xsl:with-param name="src" select="$notTestedImage"/>
									</xsl:call-template>
									plan created, student not tested
								</fo:block>
							</fo:block>
						</fo:block-container>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>
	
	<xsl:template name="legend-image">
		<xsl:param name="src"/>
		<fo:external-graphic xsl:use-attribute-sets="legendImage">
			<xsl:attribute name="src"><xsl:value-of select="$src"/></xsl:attribute>
		</fo:external-graphic>
	</xsl:template>
	
	<xsl:template name="completion-image">
		<xsl:param name="src"/>
		<fo:external-graphic xsl:use-attribute-sets="completionImage">
			<xsl:attribute name="src"><xsl:value-of select="$src"/></xsl:attribute>
		</fo:external-graphic>
	</xsl:template>
	
	<xsl:template name="tested-image">
		<xsl:param name="src"/>
		<fo:external-graphic xsl:use-attribute-sets="testedImage">
			<xsl:attribute name="src"><xsl:value-of select="$src"/></xsl:attribute>
		</fo:external-graphic>
	</xsl:template>
	
	<xsl:template name="blueprintCoverageBlock">
		<xsl:param name="report"/>
		<fo:block page-break-before="always">
			<fo:block xsl:use-attribute-sets="teacherSubjectGradeHeader">
				<xsl:if test="$byTeacher">
					<xsl:value-of select="teacherLastName"/><xsl:text>, </xsl:text><xsl:value-of select="teacherFirstName"/>
					<xsl:text> - </xsl:text>
				</xsl:if>
				<xsl:value-of select="contentAreaName"/><xsl:text> </xsl:text><xsl:value-of select="gradeCourseName"/>
			</fo:block>
			<xsl:for-each select="$report/criteriaAndEEs/edu.ku.cete.web.BlueprintCriteriaAndEEDTO">
				<xsl:variable name="criteria" select="."/>
				<xsl:variable name="numberOfStudents" select="count(
					$report/studentTestCriteria/edu.ku.cete.web.DLMBlueprintCoverageReportStudentTestsCriteriaDTO[criteria = $criteria/criteria]
				)"/>
				
				<xsl:call-template name="blueprintCoverageCriteriaTables">
					<xsl:with-param name="report" select="$report"/>
					<xsl:with-param name="criteria" select="$criteria"/>
					<xsl:with-param name="i" select="1"/>
					<xsl:with-param name="numberOfStudents" select="$numberOfStudents"/>
				</xsl:call-template>
			</xsl:for-each>
		</fo:block>
	</xsl:template>
	
	<xsl:template name="blueprintCoverageCriteriaTables">
		<xsl:param name="report"/>
		<xsl:param name="criteria"/>
		<xsl:param name="i"/>
		<xsl:param name="numberOfStudents"/>
		
		<xsl:choose>
			<xsl:when test="$numberOfStudents > ($i * $numberOfStudentsPerTable)">
				<xsl:call-template name="blueprintCoverageCriteriaTable">
					<xsl:with-param name="report" select="$report"/>
					<xsl:with-param name="criteria" select="$criteria"/>
					<xsl:with-param name="studentBeginIndex" select="($numberOfStudentsPerTable * ($i - 1)) + 1"/>
					<xsl:with-param name="studentEndIndex" select="$numberOfStudentsPerTable * $i"/>
				</xsl:call-template>
				
				<xsl:call-template name="blueprintCoverageCriteriaTables">
					<xsl:with-param name="report" select="$report"/>
					<xsl:with-param name="criteria" select="$criteria"/>
					<xsl:with-param name="i" select="$i + 1"/>
					<xsl:with-param name="numberOfStudents" select="$numberOfStudents"/>
				</xsl:call-template> 
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="blueprintCoverageCriteriaTable">
					<xsl:with-param name="report" select="$report"/>
					<xsl:with-param name="criteria" select="$criteria"/>
					<xsl:with-param name="studentBeginIndex" select="($numberOfStudentsPerTable * ($i - 1)) + 1"/>
					<xsl:with-param name="studentEndIndex" select="$numberOfStudents"/>
				</xsl:call-template>
				<fo:block page-break-after="always"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="blueprintCoverageCriteriaTable">
		<xsl:param name="report"/>
		<xsl:param name="criteria"/>
		<!-- indexes are inclusive -->
		<xsl:param name="studentBeginIndex"/>
		<xsl:param name="studentEndIndex"/>
		
		<fo:block linefeed-treatment="preserve">&#x2028;</fo:block>
		<fo:block>
			<fo:table xsl:use-attribute-sets="table" table-layout="fixed" width="10.5in" text-align="center">
				<fo:table-column>
					<xsl:attribute name="column-width"><xsl:value-of select="$conceptualAreaColumnWidth"/>in</xsl:attribute>
				</fo:table-column>
				<fo:table-column>
					<xsl:attribute name="column-width"><xsl:value-of select="$eeColumnWidth"/>in</xsl:attribute>
				</fo:table-column>
				<fo:table-column>
					<xsl:attribute name="column-width"><xsl:value-of select="$eeDescriptionColumnWidth"/>in</xsl:attribute>
				</fo:table-column>
				
				<xsl:for-each select="$report/studentTestCriteria/edu.ku.cete.web.DLMBlueprintCoverageReportStudentTestsCriteriaDTO[criteria = $criteria/criteria]">
					<xsl:if test="not(position() &lt; $studentBeginIndex) and not(position() &gt; $studentEndIndex)">
						<fo:table-column>
							<xsl:attribute name="column-width"><xsl:value-of select="$studentColumnWidth"/>in</xsl:attribute>
						</fo:table-column>
					</xsl:if>
				</xsl:for-each>
				
				<fo:table-header>
					<fo:table-row keep-with-next="always">
						<fo:table-cell xsl:use-attribute-sets="tableHeader tableCell">
							<fo:block xsl:use-attribute-sets="tableCellBlock">Conceptual Area</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="tableHeader tableCell">
							<fo:block xsl:use-attribute-sets="tableCellBlock">EE</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="tableHeader tableCell">
							<fo:block xsl:use-attribute-sets="tableCellBlock">EE Description</fo:block>
						</fo:table-cell>
						<xsl:for-each select="$report/studentTestCriteria/edu.ku.cete.web.DLMBlueprintCoverageReportStudentTestsCriteriaDTO[criteria = $criteria/criteria]">
							<xsl:if test="not(position() &lt; $studentBeginIndex) and not(position() &gt; $studentEndIndex)">
								<fo:table-cell xsl:use-attribute-sets="tableHeader studentTableCell">
									<fo:block xsl:use-attribute-sets="tableCellBlock">
										<xsl:value-of select="studentLastName"/><xsl:text>, </xsl:text><xsl:value-of select="studentFirstName"/>
									</fo:block>
								</fo:table-cell>
							</xsl:if>
						</xsl:for-each>
					</fo:table-row>
				</fo:table-header>
				
				<fo:table-body>
					<fo:table-row keep-with-next="always">
						<fo:table-cell xsl:use-attribute-sets="tableHeader criterionDescriptionTableCell" number-columns-spanned="3">
							<fo:block xsl:use-attribute-sets="tableCellBlock"><xsl:value-of select="criteriaText"/></fo:block>
						</fo:table-cell>
						<xsl:for-each select="$report/studentTestCriteria/edu.ku.cete.web.DLMBlueprintCoverageReportStudentTestsCriteriaDTO[criteria = $criteria/criteria]">
							<xsl:if test="not(position() &lt; $studentBeginIndex) and not(position() &gt; $studentEndIndex)">
								<fo:table-cell xsl:use-attribute-sets="tableHeader studentTableCell">
									<fo:block xsl:use-attribute-sets="tableCellBlock">
										<xsl:choose>
											<xsl:when test="completedCriteriaStatus = 'complete'">
												<xsl:call-template name="completion-image">
													<xsl:with-param name="src" select="$metCriterionImage"/>
												</xsl:call-template>
											</xsl:when>
											<xsl:when test="completedCriteriaStatus = 'partial'">
												<xsl:call-template name="completion-image">
													<xsl:with-param name="src" select="$partiallyMetCriterionImage"/>
												</xsl:call-template>
											</xsl:when>
											<xsl:otherwise></xsl:otherwise>
										</xsl:choose>
									</fo:block>
								</fo:table-cell>
							</xsl:if>
						</xsl:for-each>
					</fo:table-row>
					<xsl:for-each select="ees/edu.ku.cete.domain.test.EEConceptualAndClaimDetailsDTO">
						<xsl:variable name="ee" select="."/>
						<fo:table-row keep-together="always">
							<fo:table-cell xsl:use-attribute-sets="tableCell">
								<fo:block xsl:use-attribute-sets="tableCellBlock"><xsl:value-of select="conceptualCodeForLM"/></fo:block>
							</fo:table-cell>
							<fo:table-cell xsl:use-attribute-sets="tableCell">
								<fo:block xsl:use-attribute-sets="tableCellBlock"><xsl:value-of select="eeConetnetCode"/></fo:block>
							</fo:table-cell>
							<fo:table-cell xsl:use-attribute-sets="eeDescriptionTableCell">
								<fo:block xsl:use-attribute-sets="tableCellBlock"><xsl:value-of select="eeDescription"/></fo:block>
							</fo:table-cell>
							<xsl:for-each select="$report/studentTestCriteria/edu.ku.cete.web.DLMBlueprintCoverageReportStudentTestsCriteriaDTO[criteria = $criteria/criteria]">
								<xsl:if test="not(position() &lt; $studentBeginIndex) and not(position() &gt; $studentEndIndex)">
									<xsl:variable name="student" select="."/>
									<fo:table-cell xsl:use-attribute-sets="tableCell studentTableCell">
										<fo:block xsl:use-attribute-sets="tableCellBlock">
											<xsl:call-template name="studentTestCell">
												<xsl:with-param name="eeCode" select="$ee/eeConetnetCode"/>
												<xsl:with-param name="eesToTests" select="$student/eesToTests"/>
											</xsl:call-template>
										</fo:block>
									</fo:table-cell>
								</xsl:if>
							</xsl:for-each>
						</fo:table-row>
					</xsl:for-each>
				</fo:table-body>
			</fo:table>
		</fo:block>
	</xsl:template>
	
	<xsl:template name="studentTestCell">
		<xsl:param name="eeCode"/>
		<xsl:param name="eesToTests"/>
		<xsl:for-each select="$eesToTests/entry">
			<xsl:variable name="ee" select="string"/>
			<xsl:variable name="tests" select="list"/>
			<xsl:if test="$eeCode = $ee">
				<xsl:for-each select="$tests/edu.ku.cete.domain.ItiTestSessionHistory">
					<fo:block>
						<xsl:choose>
							<xsl:when test="testStatus = 'complete'">
								<xsl:call-template name="tested-image">
									<xsl:with-param name="src" select="$completedTestletImage"/>
								</xsl:call-template>
								<xsl:value-of select="blueprintCoverageCompletedDateStr"/>
							</xsl:when>
							<xsl:when test="confirmDate">
								<xsl:call-template name="tested-image">
									<xsl:with-param name="src" select="$notTestedImage"/>
								</xsl:call-template>
								<xsl:value-of select="blueprintCoverageConfirmDateStr"/>
							</xsl:when>
							<xsl:when test="savedDate">
								<xsl:call-template name="tested-image">
									<xsl:with-param name="src" select="$notTestedImage"/>
								</xsl:call-template>
								<xsl:value-of select="blueprintCoverageSavedDateStr"/><xsl:text> Saved</xsl:text>
							</xsl:when>
							<xsl:otherwise></xsl:otherwise>
						</xsl:choose>
					</fo:block>
				</xsl:for-each>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>