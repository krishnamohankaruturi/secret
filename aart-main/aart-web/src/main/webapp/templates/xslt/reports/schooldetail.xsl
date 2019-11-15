<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo" extension-element-prefixes="saxon">
	<xsl:include href="utility.xsl"/>
	<xsl:include href="levels-table.xsl"/>
	<xsl:include href="claims-definition.xsl"/>
	<xsl:include href="resources.xsl"/>
	<xsl:include href="subscorecharts.xsl"/>
	<xsl:include href="explanation.xsl"/>
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format"
			font-family="Verdana" font-size="10pt">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="firstPage" page-height="29.7cm" page-width="21.0cm" margin="1.27cm">
					<fo:region-body margin-top="30mm" />
					<fo:region-before region-name="xsl-region-before-firstPage" extent="30mm" />
				</fo:simple-page-master>
				<fo:simple-page-master master-name="nonFirstPage" page-height="29.7cm" page-width="21.0cm" margin="1.27cm">
					<fo:region-body margin-top="7mm" margin-bottom="15mm" />
					<fo:region-before region-name="xsl-region-before-nonFirstPage" extent="7mm" />
         			<fo:region-after region-name="xsl-region-after-nonFirstPage" extent="15mm" display-align="after"/>
				</fo:simple-page-master>
				<fo:page-sequence-master master-name="allPages">
					<fo:repeatable-page-master-alternatives>
						<fo:conditional-page-master-reference
							master-reference="firstPage" page-position="first" />
						<fo:conditional-page-master-reference
							master-reference="nonFirstPage" page-position="any" />
					</fo:repeatable-page-master-alternatives>
				</fo:page-sequence-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="allPages">
				<fo:static-content flow-name="xsl-region-before-firstPage">
					<xsl:call-template name="report-page1-header"/>
				</fo:static-content>
				<fo:static-content flow-name="xsl-region-before-nonFirstPage">
					<fo:block>
						<fo:table table-layout="fixed">
							<fo:table-column column-width="proportional-column-width(1)" />
							<fo:table-column column-width="proportional-column-width(1)" />
							<fo:table-column column-width="proportional-column-width(1)" />
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell text-align="start" wrap-option="no-wrap">
										<fo:block color="#A7A2A2">School Detail Report</fo:block>
									</fo:table-cell>
									<fo:table-cell text-align="center">
										<fo:block color="#A7A2A2">School: <xsl:value-of select="reportDetails/data/attendanceSchoolName"/></fo:block>
									</fo:table-cell>
									<fo:table-cell text-align="end" wrap-option="no-wrap">
										<fo:block color="#A7A2A2"><xsl:value-of select="reportDetails/data/gradeName"/><xsl:text>&#160;</xsl:text><xsl:value-of select="reportDetails/data/contentAreaName"/></fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
					</fo:block>
				</fo:static-content>
				<fo:static-content flow-name="xsl-region-after-nonFirstPage">
         			<xsl:call-template name="reportFooter"/>
      			</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
					<xsl:variable name="medianScoreCheck">
						<xsl:call-template name="lookup-median-se-sc">
								<xsl:with-param name="paramOrgId" select="reportDetails/data/attendanceSchoolId"/>
								<xsl:with-param name="paramType" select="'SCORE'"/>
						</xsl:call-template>
					</xsl:variable>
					<xsl:choose>
						<xsl:when test="$medianScoreCheck > 0">
							<!-- 1st page -->
							<fo:block>
								<fo:block>
									<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">This report has information about a school's scores from the Kansas Assessment Program. The tests measure students' understanding of Kansas College and Career Ready Standards at each grade using questions that ask students to select the right answer, sort items, create graphs, or label pictures. For sample test questions, see http://ksassessments.org/practice-tests.</xsl:if>
									<xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'">This report has information about the school's Alaska Measures of Progress (AMP) test scores. The AMP measures students' understanding of the Alaska English Language Arts and Mathematics Standards in grades 3-10  using questions that ask students to select the right answer, sort items, create graphs, and label pictures. For sample test questions, see http://amp.cete.us/alaskatpt.</xsl:if>
								</fo:block>
								<fo:block margin-top="6mm" font-weight="bold">School Median Score</fo:block>
								<fo:block margin-top="3mm">The first graph shows the school’s overall median score on the test, indicated by the arrow. The bands on the graph represent the four possible levels, with <xsl:call-template name="max-level-number"/> being the highest level. The other graphs show the performance of <xsl:call-template name="gradename-word-map"><xsl:with-param name="val" select="reportDetails/data/gradeName"/></xsl:call-template> graders in the district and state. The median, or middle number in an ordered list of numbers, is used for these comparison graphs.</fo:block>
								<fo:block margin-top="3mm">
									<fo:table table-layout="fixed" border="solid #000 .3mm">
										<fo:table-column column-width="proportional-column-width(1)" />
										<fo:table-column column-width="proportional-column-width(1)" />
										<fo:table-column column-width="proportional-column-width(1)" />
										<fo:table-body>
											<fo:table-row>
												<fo:table-cell text-align="center">
													<fo:block-container>
														<fo:block>
															<fo:external-graphic content-width="scale-down-to-fit">
																<xsl:attribute name="width"><xsl:value-of select="reportDetails/smallMeterWidth"/></xsl:attribute>
																<xsl:attribute name="height"><xsl:value-of select="reportDetails/smallMeterHeight"/></xsl:attribute>
																<xsl:attribute name="src"><xsl:value-of select="reportDetails/schoolMeter"/></xsl:attribute>
															</fo:external-graphic>
														</fo:block>
														<fo:block font-size="10pt">School Median</fo:block>
														
														<fo:block font-size="10pt">
															<xsl:call-template name="lookup-median-se-sc">
																<xsl:with-param name="paramOrgId" select="reportDetails/data/attendanceSchoolId"/>
																<xsl:with-param name="paramType" select="'SCORE'"/>
															</xsl:call-template><xsl:text>&#160;</xsl:text><fo:inline font-size="8pt">[SE<xsl:text>&#160;</xsl:text>
																<xsl:call-template name="lookup-median-se-sc">
																<xsl:with-param name="paramOrgId" select="reportDetails/data/attendanceSchoolId"/>
																<xsl:with-param name="paramType" select="'SE'"/>
															</xsl:call-template>]</fo:inline>
														</fo:block>
														<fo:block font-size="8pt">Number of Students: <xsl:call-template name="lookup-median-se-sc">
																<xsl:with-param name="paramOrgId" select="reportDetails/data/attendanceSchoolId"/>
																<xsl:with-param name="paramType" select="'SC'"/>
															</xsl:call-template>
														</fo:block>
													</fo:block-container>
												</fo:table-cell>
												<fo:table-cell text-align="center">
													<fo:block-container>
														<fo:block>
															<fo:external-graphic content-width="scale-down-to-fit">
																<xsl:attribute name="width"><xsl:value-of select="reportDetails/smallMeterWidth"/></xsl:attribute>
																<xsl:attribute name="height"><xsl:value-of select="reportDetails/smallMeterHeight"/></xsl:attribute>
																<xsl:attribute name="src"><xsl:value-of select="reportDetails/districtMeter"/></xsl:attribute>
															</fo:external-graphic>
														</fo:block>
														<fo:block font-size="10pt">District Median</fo:block>
														<fo:block font-size="10pt">
															<xsl:call-template name="lookup-median-se-sc">
																<xsl:with-param name="paramOrgId" select="reportDetails/data/districtId"/>
																<xsl:with-param name="paramType" select="'SCORE'"/>
															</xsl:call-template><xsl:text>&#160;</xsl:text><fo:inline font-size="8pt">[SE<xsl:text>&#160;</xsl:text>
																<xsl:call-template name="lookup-median-se-sc">
																<xsl:with-param name="paramOrgId" select="reportDetails/data/districtId"/>
																<xsl:with-param name="paramType" select="'SE'"/>
															</xsl:call-template>]</fo:inline>
														</fo:block>
														<fo:block font-size="8pt">Number of Students: <xsl:call-template name="lookup-median-se-sc">
																<xsl:with-param name="paramOrgId" select="reportDetails/data/districtId"/>
																<xsl:with-param name="paramType" select="'SC'"/>
															</xsl:call-template>
														</fo:block>
													</fo:block-container>
												</fo:table-cell>
												<fo:table-cell text-align="center">
													<fo:block-container>
														<fo:block>
															<fo:external-graphic content-width="scale-down-to-fit">
																<xsl:attribute name="width"><xsl:value-of select="reportDetails/smallMeterWidth"/></xsl:attribute>
																<xsl:attribute name="height"><xsl:value-of select="reportDetails/smallMeterHeight"/></xsl:attribute>
																<xsl:attribute name="src"><xsl:value-of select="reportDetails/stateMeter"/></xsl:attribute>
															</fo:external-graphic>
														</fo:block>
														<fo:block font-size="10pt">State Median</fo:block>
														<fo:block font-size="10pt">
															<xsl:call-template name="lookup-median-se-sc">
																<xsl:with-param name="paramOrgId" select="reportDetails/data/stateId"/>
																<xsl:with-param name="paramType" select="'SCORE'"/>
															</xsl:call-template><xsl:text>&#160;</xsl:text><fo:inline font-size="8pt">[SE<xsl:text>&#160;</xsl:text>
																<xsl:call-template name="lookup-median-se-sc">
																<xsl:with-param name="paramOrgId" select="reportDetails/data/stateId"/>
																<xsl:with-param name="paramType" select="'SE'"/>
															</xsl:call-template>]</fo:inline>
														</fo:block>
														<fo:block font-size="8pt">Number of Students: <xsl:call-template name="lookup-median-se-sc">
																<xsl:with-param name="paramOrgId" select="reportDetails/data/stateId"/>
																<xsl:with-param name="paramType" select="'SC'"/>
															</xsl:call-template>
														</fo:block>
													</fo:block-container>
												</fo:table-cell>
											</fo:table-row>
										</fo:table-body>
									</fo:table>
								</fo:block>
								<fo:block margin-top="6mm" font-weight="bold"><xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'"><xsl:text>Performance</xsl:text></xsl:if><xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'"><xsl:text>Achievement</xsl:text></xsl:if> Levels</fo:block>
								<fo:block margin-top="3mm">
									<fo:table table-layout="fixed">
										<fo:table-column column-width="proportional-column-width(40)" />
										<fo:table-column column-width="proportional-column-width(60)" />
										<fo:table-body>
											<fo:table-row>
												<fo:table-cell>
													<fo:block>
														<xsl:variable name="schoolScore">
															<xsl:call-template name="lookup-median-se-sc">
																<xsl:with-param name="paramOrgId" select="reportDetails/data/attendanceSchoolId"/>
																<xsl:with-param name="paramType" select="'SCORE'"/>
															</xsl:call-template>
														</xsl:variable>
														Overall scores on the <xsl:value-of select="reportDetails/data/assessmentProgramCode"/> test are divided into <xsl:call-template name="levelnumber-word-map">
																		<xsl:with-param name="val"><xsl:call-template name="max-level-number"/></xsl:with-param>
																	</xsl:call-template>
														<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'"><xsl:text> performance</xsl:text></xsl:if><xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'"><xsl:text> achievement</xsl:text></xsl:if> levels. The levels range from <xsl:call-template name="min-level-number"/> to <xsl:call-template name="max-level-number"/>, with <xsl:call-template name="max-level-number"/>
														being the highest level. The school's median score is in Level <xsl:call-template name="lookup-level-number-byscore">
															<xsl:with-param name="paramScore" select="$schoolScore"/>
														</xsl:call-template><xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">.</xsl:if><xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'">, <xsl:call-template name="lookup-level-name-byscore">
															<xsl:with-param name="paramScore" select="$schoolScore"/>
														</xsl:call-template>.</xsl:if>
													</fo:block>
												</fo:table-cell>
												<fo:table-cell padding="3px" vertical-align="top" display-align="center">
													<fo:block>
														<xsl:call-template name="levels-table" />
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</fo:table-body>
									</fo:table>
								</fo:block>
								<fo:block margin-top="3mm">The following chart compares the percentage of <xsl:call-template name="gradename-word-map"><xsl:with-param name="val" select="reportDetails/data/gradeName"/></xsl:call-template> grade <xsl:call-template name="lower-case"><xsl:with-param name="x" select="reportDetails/data/contentAreaName"/></xsl:call-template> students in each <xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'"><xsl:text>performance</xsl:text></xsl:if><xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'"><xsl:text>achievement</xsl:text></xsl:if> level for school, district and state. Complete <xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'"><xsl:text>performance</xsl:text></xsl:if><xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'"><xsl:text>achievement</xsl:text></xsl:if> level descriptors can be found at <xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">http://ksassessments.org/pld</xsl:if><xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'">http://amp.cete.us/AMPresources</xsl:if>.</fo:block>
								<fo:block margin-top="3mm" font-size="10pt">
									<fo:table table-layout="fixed" border="solid #B8B8B8 .3mm">
											<fo:table-column column-width="proportional-column-width(10)" />
											<fo:table-column column-width="proportional-column-width(90)" />
											<fo:table-body>
												<fo:table-row>
													<fo:table-cell text-align="center" padding="2px" number-columns-spanned="2">
														<fo:block font-size="10pt" font-weight="bold">
															Percentage of Students in Each <xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'"><xsl:text>Performance</xsl:text></xsl:if><xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'"><xsl:text>Achievement</xsl:text></xsl:if> Level, <xsl:value-of select="reportDetails/data/gradeName"/>
														</fo:block>
													</fo:table-cell>
												</fo:table-row>
												<fo:table-row>
													<fo:table-cell text-align="end" padding="2px" column-number="2">
														<xsl:call-template name="levels-table-legend"/>
													</fo:table-cell>
												</fo:table-row>
												<fo:table-row>
													<fo:table-cell text-align="center" padding="2px" border-top="solid #B8B8B8 .3mm">
														<fo:block-container left="0px" position="absolute">
															<xsl:attribute name="top"><xsl:value-of select="((reportDetails/percentLevelChartHeight) div 2) - 8"/></xsl:attribute>
															<fo:block><xsl:value-of select="reportDetails/data/gradeName"/></fo:block>
														</fo:block-container>
													</fo:table-cell>
													<fo:table-cell text-align="start" padding="2px" border="solid #B8B8B8 .3mm">
														<fo:block-container>
															<fo:block><fo:external-graphic content-width="scale-down-to-fit">
																<xsl:attribute name="width"><xsl:value-of select="reportDetails/percentLevelChartWidth"/></xsl:attribute>
																<xsl:attribute name="height"><xsl:value-of select="reportDetails/percentLevelChartHeight"/></xsl:attribute>
																<xsl:attribute name="src"><xsl:value-of select="reportDetails/percentLevelChart"/></xsl:attribute>
															</fo:external-graphic></fo:block>
														</fo:block-container>
													</fo:table-cell>
												</fo:table-row>
											</fo:table-body>
									</fo:table>
								</fo:block>
								<fo:block text-align="end" padding="2px" font-size="8pt" color="#939597">
									<fo:block>*Percentages may not add to 100% because of rounding.</fo:block>
									<xsl:if test="reportDetails/suppressLevels = 1">
										<fo:block>**Exact numbers are concealed to protect student privacy.</fo:block>
									</xsl:if>
								</fo:block> 
							</fo:block>
							<!-- 2nd page -->
							<xsl:if test="count(reportDetails/data/subscoreBuckets) &gt; 0">
								<fo:block break-before="page">
									<fo:block margin-top="3mm">
										<xsl:call-template name="explanation">
											<xsl:with-param name="reportType" select="'school'"/>
										</xsl:call-template>
									</fo:block>
									<fo:block margin-top="4mm" font-weight="bold" font-size="12pt" text-align="center">School Sub-Scores and Claims</fo:block>
									<fo:block margin-top="3mm">
										This chart shows the school’s performance on specific areas of the <xsl:value-of select="reportDetails/data/gradeName"/><xsl:text>&#160;</xsl:text><xsl:value-of select="reportDetails/data/contentAreaName"/> test as well as the performance of the <xsl:call-template name="lower-case"><xsl:with-param name="x" select="reportDetails/data/gradeName"/></xsl:call-template> students in the district and state. The bracket on either side of the bold score line represents the standard error, or how much a student's performance might vary if the student took many equivalent versions of the test.
									</fo:block>
									<fo:block margin-top="3mm">
										<xsl:call-template name="subScoreGraph">
											<xsl:with-param name="reportType" select="'school'"/>
										</xsl:call-template>
									</fo:block>
									<xsl:call-template name="claims-definition">
										<xsl:with-param name="reportType" select="'school'"/>
									</xsl:call-template>
									<xsl:call-template name="additionalResources">
										<xsl:with-param name="reportType" select="'school'"/>
									</xsl:call-template>
								</fo:block>
							</xsl:if>
						</xsl:when>
						<xsl:otherwise>
  								<xsl:call-template name="inSufficientDataMessage"> </xsl:call-template>
						</xsl:otherwise>
					</xsl:choose>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	<xsl:template name="report-page1-header">
		<fo:table table-layout="fixed">
			<fo:table-column column-width="proportional-column-width(35)" />
			<fo:table-column column-width="proportional-column-width(28)" />
			<fo:table-column column-width="proportional-column-width(37)" />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell padding="2px">
						<fo:block color="#3784C6" font-weight="bold" font-size="11pt">School Detail Report</fo:block>
						<xsl:call-template name="headerSchoolName"/>
						<xsl:call-template name="headerDistrictName"/>
					</fo:table-cell>
					<fo:table-cell text-align="center">
						<xsl:call-template name="headerLogo"/>
					</fo:table-cell>
					<fo:table-cell text-align="end" padding-top="2px" padding-bottom="2px">
						<fo:block-container>
							<xsl:call-template name="headerGradeSubjectName"/>
							<xsl:call-template name="headerSchoolYear"/>
						</fo:block-container>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>
</xsl:stylesheet>