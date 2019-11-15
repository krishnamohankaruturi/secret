<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:fo="http://www.w3.org/1999/XSL/Format" extension-element-prefixes="saxon">
	<xsl:include href="utility.xsl"/>
	<xsl:include href="levels-table.xsl"/>
	<xsl:include href="claims-definition.xsl"/>
	<xsl:include href="resources.xsl"/>
	<xsl:include href="subscorecharts.xsl"/>
	<xsl:include href="explanation.xsl"/>
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" font-family="Verdana" font-size="10pt">
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
						<fo:conditional-page-master-reference master-reference="firstPage" page-position="first" />
						<fo:conditional-page-master-reference master-reference="nonFirstPage" page-position="any" />
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
										<fo:block color="#A7A2A2">Student Report</fo:block>
									</fo:table-cell>
									<fo:table-cell text-align="center">
										<fo:block color="#A7A2A2">Student: <xsl:call-template name="lastName"/>, <xsl:call-template name="firstName"/> (State ID: <xsl:call-template name="stateStudentIdentifier"/>)</fo:block>
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
					<!-- 1st page -->
					<fo:block>
						<fo:block>
							<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">This report has information about your student's Kansas
							Assessment Program (KAP) test scores. The KAP assessments measure a
							student's understanding of the Kansas College and Career Ready
							Standards at the student's grade level. The test contains questions
							that ask students to select the right answer as well as questions
							that ask the student to sort items, create graphs, or label
							pictures.</xsl:if>
							<xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'">
							This report has information about your student's Alaska Measures of Progress (AMP) test scores. The AMP measures a student's understanding of the grade level Alaska English Language Arts (ELA) and Mathematics Standards. The test contains questions that ask students to select the right answer and to sort items, create graphs, or label pictures. You can see the types of questions on the computer-based assessment at http://amp.cete.us/alaskatpt. 
							</xsl:if>
						</fo:block>
						<fo:block margin-top="3mm">
							<fo:table table-layout="fixed">
								<fo:table-column column-width="proportional-column-width(75)" />
								<fo:table-column column-width="proportional-column-width(25)" />
								<fo:table-body>
									<fo:table-row>
										<fo:table-cell padding="5px" text-align="center" border="solid #000 .2mm" display-align="center">
											<fo:block font-weight="bold" font-size="14pt"><xsl:call-template name="firstName"/>'s Test Score
 											 <xsl:if test="reportDetails/data/incompleteStatus = 'true'">
											 	 <fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
					 								height="17px" width="14px" vertical-align="middle">
					 								<xsl:attribute name="src">
					 									<xsl:value-of select="reportDetails/incompleteStatusIconPath"/>
					 								</xsl:attribute>
												</fo:external-graphic>
												<fo:inline font-weight="normal" font-size="8pt">Incomplete</fo:inline>
											  </xsl:if>
 											  <xsl:if test="reportDetails/data/exitStatus = 'true'">
											 	 <fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
					 								height="17px" width="17px" vertical-align="middle">
					 								<xsl:attribute name="src">
					 									<xsl:value-of select="reportDetails/exitStatusIconPath"/>
					 								</xsl:attribute>
												</fo:external-graphic>
												<fo:inline font-weight="normal" font-size="8pt">Exited</fo:inline>
											  </xsl:if>
 											</fo:block>
											<fo:block><fo:inline font-weight="bold" font-size="14pt"><xsl:value-of select="reportDetails/data/scaleScore"/></fo:inline><xsl:text>&#160;</xsl:text><fo:inline font-weight="bold" font-size="10pt">[SE <xsl:value-of select="format-number(reportDetails/data/standardError, '0.0')"/>]</fo:inline></fo:block>
											<fo:block>
												<fo:external-graphic content-width="scale-down-to-fit">
													<xsl:attribute name="width"><xsl:value-of select="reportDetails/studentMeterWidth"/></xsl:attribute>
													<xsl:attribute name="height"><xsl:value-of select="reportDetails/studentMeterHeight"/></xsl:attribute>
													<xsl:attribute name="src"><xsl:value-of select="reportDetails/studentMeter"/></xsl:attribute>
												</fo:external-graphic>
											</fo:block>
											<fo:block>
												<fo:table table-layout="fixed">
													<fo:table-column column-width="proportional-column-width(1)" />
													<fo:table-column column-width="proportional-column-width(1)" />
													<fo:table-column column-width="proportional-column-width(1)" />
													<fo:table-body>
														<fo:table-row>
															<fo:table-cell text-align="center">
																<fo:block-container>
																	<xsl:attribute name="width"><xsl:value-of select="reportDetails/smallMeterWidth"/></xsl:attribute>
																	<xsl:attribute name="height"><xsl:value-of select="reportDetails/smallMeterHeight"/></xsl:attribute>
																	<fo:block>
																		<fo:external-graphic content-width="scale-down-to-fit">
																			<xsl:attribute name="width"><xsl:value-of select="reportDetails/smallMeterWidth"/></xsl:attribute>
																			<xsl:attribute name="height"><xsl:value-of select="reportDetails/smallMeterHeight"/></xsl:attribute>
																			<xsl:attribute name="src"><xsl:value-of select="reportDetails/schoolMeter"/></xsl:attribute>
																		</fo:external-graphic>
																	</fo:block>
																</fo:block-container>
																<fo:block font-size="10pt">School Median</fo:block>
																<xsl:variable name="schoolMedianScore">
  																	<xsl:call-template name="lookup-median-se-sc">
																		<xsl:with-param name="paramOrgId" select="reportDetails/data/attendanceSchoolId"/>
																		<xsl:with-param name="paramType" select="'SCORE'"/>
																	</xsl:call-template>
																</xsl:variable>
																<xsl:choose>
 																	 <xsl:when test="$schoolMedianScore > 0">
 																	 	<fo:block font-size="10pt">
																		<fo:inline><xsl:copy-of select="$schoolMedianScore" /></fo:inline>
																		<xsl:text>&#160;</xsl:text>
																		<fo:inline font-size="8pt">[SE<xsl:text>&#160;</xsl:text>
																			<xsl:call-template name="lookup-median-se-sc">
																				<xsl:with-param name="paramOrgId" select="reportDetails/data/attendanceSchoolId"/>
																				<xsl:with-param name="paramType" select="'SE'"/>
																			</xsl:call-template>]
																		</fo:inline>
																		</fo:block>
																		<fo:block font-size="8pt">Number of Students: 
																			<xsl:call-template name="lookup-median-se-sc">
																				<xsl:with-param name="paramOrgId" select="reportDetails/data/attendanceSchoolId"/>
																				<xsl:with-param name="paramType" select="'SC'"/>
																			</xsl:call-template>
																		</fo:block>
																	 </xsl:when>
  																	 <xsl:otherwise>
   																		 <xsl:call-template name="privacyStatementForNoScore"> </xsl:call-template>
 																	 </xsl:otherwise>
																</xsl:choose>
															</fo:table-cell>
															<fo:table-cell text-align="center">
																<fo:block-container>
																	<xsl:attribute name="width"><xsl:value-of select="reportDetails/smallMeterWidth"/></xsl:attribute>
																	<xsl:attribute name="height"><xsl:value-of select="reportDetails/smallMeterHeight"/></xsl:attribute>
																	<fo:block>
																		<fo:external-graphic content-width="scale-down-to-fit">
																			<xsl:attribute name="width"><xsl:value-of select="reportDetails/smallMeterWidth"/></xsl:attribute>
																			<xsl:attribute name="height"><xsl:value-of select="reportDetails/smallMeterHeight"/></xsl:attribute>
																			<xsl:attribute name="src"><xsl:value-of select="reportDetails/districtMeter"/></xsl:attribute>
																		</fo:external-graphic>
																	</fo:block>
																</fo:block-container>
																<fo:block font-size="10pt">District Median</fo:block>
																<xsl:variable name="districtMedianScore">
  																	<xsl:call-template name="lookup-median-se-sc">
																		<xsl:with-param name="paramOrgId" select="reportDetails/data/districtId"/>
																		<xsl:with-param name="paramType" select="'SCORE'"/>
																	</xsl:call-template>
																</xsl:variable>
																<xsl:choose>
 																	<xsl:when test="$districtMedianScore > 0">
 																		<fo:block font-size="10pt">
																			<fo:inline><xsl:copy-of select="$districtMedianScore" /></fo:inline>
																			<xsl:text>&#160;</xsl:text>
																			<fo:inline font-size="8pt">[SE<xsl:text>&#160;</xsl:text>
																				<xsl:call-template name="lookup-median-se-sc">
																				<xsl:with-param name="paramOrgId" select="reportDetails/data/districtId"/>
																				<xsl:with-param name="paramType" select="'SE'"/>
																				</xsl:call-template>]
																			</fo:inline>
																		</fo:block>
																		<fo:block font-size="8pt">Number of Students: <xsl:call-template name="lookup-median-se-sc">
																			<xsl:with-param name="paramOrgId" select="reportDetails/data/districtId"/>
																			<xsl:with-param name="paramType" select="'SC'"/>
																			</xsl:call-template>
																		</fo:block>
																    </xsl:when>
  																	<xsl:otherwise>
  																		 <xsl:call-template name="privacyStatementForNoScore"> </xsl:call-template>
																	</xsl:otherwise>
																</xsl:choose>
															</fo:table-cell>
															<fo:table-cell text-align="center">
																<fo:block-container>
																	<xsl:attribute name="width"><xsl:value-of select="reportDetails/smallMeterWidth"/></xsl:attribute>
																	<xsl:attribute name="height"><xsl:value-of select="reportDetails/smallMeterHeight"/></xsl:attribute>
																	<fo:block>
																		<fo:external-graphic content-width="scale-down-to-fit">
																			<xsl:attribute name="width"><xsl:value-of select="reportDetails/smallMeterWidth"/></xsl:attribute>
																			<xsl:attribute name="height"><xsl:value-of select="reportDetails/smallMeterHeight"/></xsl:attribute>
																			<xsl:attribute name="src"><xsl:value-of select="reportDetails/stateMeter"/></xsl:attribute>
																		</fo:external-graphic>
																	</fo:block>
																</fo:block-container>
																<fo:block font-size="10pt">State Median</fo:block>
																<xsl:variable name="stateMedianScore">
  																	<xsl:call-template name="lookup-median-se-sc">
																		<xsl:with-param name="paramOrgId" select="reportDetails/data/stateId"/>
																		<xsl:with-param name="paramType" select="'SCORE'"/>
																	</xsl:call-template>
																</xsl:variable>
																<xsl:choose>
 																	<xsl:when test="$stateMedianScore > 0">
 																		 <fo:block font-size="10pt">
																	   		 <fo:inline><xsl:copy-of select="$stateMedianScore" /></fo:inline>
																				<xsl:text>&#160;</xsl:text>
																			<fo:inline font-size="8pt">[SE<xsl:text>&#160;</xsl:text>
																				<xsl:call-template name="lookup-median-se-sc">
																				<xsl:with-param name="paramOrgId" select="reportDetails/data/stateId"/>
																				<xsl:with-param name="paramType" select="'SE'"/>
																				</xsl:call-template>]
																			</fo:inline>
																		</fo:block>
																		<fo:block font-size="8pt">Number of Students: <xsl:call-template name="lookup-median-se-sc">
																			<xsl:with-param name="paramOrgId" select="reportDetails/data/stateId"/>
																			<xsl:with-param name="paramType" select="'SC'"/>
																			</xsl:call-template>
																		</fo:block>
																    </xsl:when>
  																	<xsl:otherwise>
  																		 <xsl:call-template name="privacyStatementForNoScore"> </xsl:call-template>
																	</xsl:otherwise>
																</xsl:choose>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
												<fo:block text-align="right">
													<xsl:if test="reportDetails/data/incompleteStatus = 'true'">
											 	 		<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
					 													height="12px" width="12px" vertical-align="middle">
					 											<xsl:attribute name="src">
					 												<xsl:value-of select="reportDetails/incompleteStatusIconPath"/>
					 											</xsl:attribute>
													   	</fo:external-graphic>
													 	<fo:inline font-weight="normal" font-size="6pt">Incomplete: Student did not attempt all parts of the test.</fo:inline>
											  		</xsl:if>
				 									<xsl:if test="reportDetails/data/exitStatus = 'true'">
													 	<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
									 							height="10px" width="10px" vertical-align="middle">
									 							<xsl:attribute name="src">
									 								<xsl:value-of select="reportDetails/exitStatusIconPath"/>
									 							</xsl:attribute>
														</fo:external-graphic>
														<fo:inline font-weight="normal" font-size="6pt">Exited: Student not enrolled at the end of the school year.</fo:inline>
													</xsl:if>
												</fo:block>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell padding-left="5px">
											<fo:block>The first graph shows <xsl:call-template name="firstNameMeterSection"/>'s overall score on the <xsl:value-of select="reportDetails/data/contentAreaName"/>
												test. The bands on the graph represent the <xsl:call-template name="levelnumber-word-map">
																		<xsl:with-param name="val"><xsl:call-template name="max-level-number"/></xsl:with-param>
																	</xsl:call-template> possible levels, with <xsl:call-template name="max-level-number"/> being the highest level. The
												arrow shows <xsl:call-template name="firstNameMeterSection"/>'s score.
											</fo:block>
											<fo:block margin-top="3mm">The three smaller graphs show
												the performance of other <xsl:call-template name="gradename-word-map"><xsl:with-param name="val" select="reportDetails/data/gradeName"/></xsl:call-template> 
												graders in <xsl:call-template name="firstNameMeterSection"/>'s school, the school district, and the state. The median, or middle number in an
												ordered list of numbers, is used for these comparison graphs.
											</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
						</fo:block>
						<fo:block margin-top="4mm" font-weight="bold"><xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'"><xsl:text>Performance</xsl:text></xsl:if><xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'"><xsl:text>Achievement</xsl:text></xsl:if> Levels</fo:block>
						<fo:block margin-top="3mm">
							<fo:table table-layout="fixed">
								<fo:table-column column-width="proportional-column-width(51)" />
								<fo:table-column column-width="proportional-column-width(49)" />
								<fo:table-body>
									<fo:table-row>
										<fo:table-cell>
											<fo:block>
												Overall scores on the <xsl:value-of select="reportDetails/data/assessmentProgramCode"/> test are divided into <xsl:call-template name="levelnumber-word-map">
																		<xsl:with-param name="val"><xsl:call-template name="max-level-number"/></xsl:with-param>
																	</xsl:call-template>
												<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'"><xsl:text> performance</xsl:text></xsl:if><xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'"><xsl:text> achievement</xsl:text></xsl:if> levels. The levels range from <xsl:call-template name="min-level-number"/> to <xsl:call-template name="max-level-number"/>, with <xsl:call-template name="max-level-number"/>
												being the highest level. 
												<xsl:call-template name="firstNameLevelsSection"/>'s score is in Level <xsl:call-template name="lookup-level-number-byscore">
													<xsl:with-param name="paramScore" select="reportDetails/data/scaleScore"/>
												</xsl:call-template><xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">.</xsl:if><xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'">, <xsl:call-template name="lookup-level-name-byscore">
													<xsl:with-param name="paramScore" select="reportDetails/data/scaleScore"/>
												</xsl:call-template>.</xsl:if>
											</fo:block>
											<fo:block>
												<xsl:choose>
													<xsl:when test="string-length(reportDetails/data/studentFirstName) &lt; 9">
														<xsl:attribute name="margin-top">6mm</xsl:attribute>
													</xsl:when>
													<xsl:otherwise>
														<xsl:attribute name="margin-top">3mm</xsl:attribute>
													</xsl:otherwise>
												</xsl:choose>
												<xsl:variable name="levelDesc">
													<xsl:call-template name="lookup-level-description-byscore">
														<xsl:with-param name="paramScore" select="reportDetails/data/scaleScore"/>
													</xsl:call-template>
												</xsl:variable>
												<xsl:if test="$levelDesc != ''">
													<xsl:choose>
									    				<xsl:when test="contains($levelDesc,'~~row~~')">
									    					<xsl:value-of select="substring-before($levelDesc,'~~row~~')" />
														</xsl:when>
														<xsl:otherwise>
															<xsl:value-of select="$levelDesc"/>
														</xsl:otherwise>
													</xsl:choose>
												</xsl:if>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell padding="3px">
											<fo:block>
												<xsl:call-template name="levels-table" />
											</fo:block>
										</fo:table-cell>
									</fo:table-row>
									<xsl:variable name="levelDesc1">
										<xsl:call-template name="lookup-level-description-byscore">
											<xsl:with-param name="paramScore" select="reportDetails/data/scaleScore"/>
										</xsl:call-template>
									</xsl:variable>
									<xsl:if test="contains($levelDesc1,'~~row~~')">
					    				<fo:table-row>
											<fo:table-cell number-columns-spanned="2">
												<fo:block>
													<xsl:value-of select="substring-after($levelDesc1,'~~row~~')" />
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:if>
								</fo:table-body>
							</fo:table>
						</fo:block>
						<fo:block margin-top="4mm">
							<xsl:call-template name="explanation">
								<xsl:with-param name="reportType" select="'student'"/>
							</xsl:call-template>
						</fo:block>
					</fo:block>
					<!-- 2nd page -->
					<xsl:if test="count(reportDetails/data/subscoreBuckets) &gt; 0">
						<fo:block break-before="page">
							<fo:block margin-top="10mm" font-weight="bold" font-size="12pt" text-align="center">Student's Relative Areas of Strength</fo:block>
							<fo:block margin-top="4mm">
								<xsl:call-template name="subScoreGraph">
									<xsl:with-param name="reportType" select="'student'"/>
								</xsl:call-template>
							</fo:block>
							<fo:block margin-top="6mm">
							This chart shows your student’s performance relative to other students in the school, district and state on specific areas of the <xsl:value-of select="reportDetails/data/gradeName"/><xsl:text>&#160;</xsl:text><xsl:value-of select="reportDetails/data/contentAreaName"/> test. Note that the scale is different from the overall test score. This information is not intended to be used to make instructional decisions because the number of items is too small. The bracket on either side of the bold score line represents the standard error.
							</fo:block>
							<xsl:call-template name="claims-definition">
								<xsl:with-param name="reportType" select="'student'"/>
							</xsl:call-template>
							<xsl:call-template name="additionalResources">
								<xsl:with-param name="reportType" select="'student'"/>
							</xsl:call-template>
						</fo:block>
					</xsl:if>
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
						<fo:block color="#3784C6" font-weight="bold" font-size="11pt">Student Report</fo:block>
						<fo:block>Student: <xsl:call-template name="lastName"/>, <xsl:call-template name="firstName"/></fo:block>
						<fo:block>Student State ID: <xsl:call-template name="stateStudentIdentifier"/></fo:block>
						<xsl:call-template name="headerSchoolYear"/>
					</fo:table-cell>
					<fo:table-cell text-align="center">
						<xsl:call-template name="headerLogo"/>
					</fo:table-cell>
					<fo:table-cell text-align="end" padding-top="2px" padding-bottom="2px">
						<fo:block-container>
							<xsl:call-template name="headerGradeSubjectName"/>
							<xsl:call-template name="headerSchoolName"/>
							<xsl:call-template name="headerDistrictName"/>
						</fo:block-container>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>
</xsl:stylesheet>