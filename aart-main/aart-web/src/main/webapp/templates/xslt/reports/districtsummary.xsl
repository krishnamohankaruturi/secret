<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo" extension-element-prefixes="saxon">
	<xsl:include href="utility.xsl"/>
	<xsl:include href="levels-table.xsl"/>
	<xsl:include href="claims-definition.xsl"/>
	<xsl:include href="resources.xsl"/>
	<xsl:include href="summarymedianscorecharts.xsl"/>
	<xsl:include href="summarylevelscorecharts.xsl"/>
	<xsl:include href="explanation.xsl"/>
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format"
			font-family="Verdana" font-size="10pt">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="firstPage" page-height="27.94cm" page-width="21.59cm" margin="1.27cm">
					<fo:region-body margin-top="25mm" />
					<fo:region-before region-name="xsl-region-before-firstPage" extent="25mm" />
				</fo:simple-page-master>
				<fo:simple-page-master master-name="nonFirstPage" page-height="27.94cm" page-width="21.59cm" margin="1.27cm">
					<fo:region-body margin-top="7mm" margin-bottom="15mm" />
					<fo:region-before region-name="xsl-region-before-nonFirstPage" extent="7mm" />
         			<fo:region-after region-name="xsl-region-after-nonFirstPage" extent="7mm" display-align="after"/>
				</fo:simple-page-master>
				<fo:simple-page-master master-name="lastPage"
					page-height="27.94cm" page-width="21.59cm" margin="1.27cm">
					<fo:region-body margin-top="7mm" margin-bottom="15mm" />
					<fo:region-before region-name="xsl-region-before-lastPage"
						extent="7mm" />
					<fo:region-after region-name="xsl-region-after-lastPage"
						extent="24mm"  />  <!-- display-align="lastafter" -->
				</fo:simple-page-master>
				<fo:page-sequence-master master-name="allPages">
					<fo:repeatable-page-master-alternatives>
						<fo:conditional-page-master-reference
							master-reference="firstPage" page-position="first" />
							<fo:conditional-page-master-reference
							master-reference="nonFirstPage" page-position="rest" />
						<fo:conditional-page-master-reference
							master-reference="lastPage" page-position="last" />
					</fo:repeatable-page-master-alternatives>
					
				</fo:page-sequence-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="allPages">
				<fo:static-content flow-name="xsl-region-before-firstPage">
					<xsl:call-template name="report-page1-header"/>
				</fo:static-content>
					<fo:static-content flow-name="xsl-region-before-nonFirstPage">
					<xsl:call-template name="page_hedder" />
				</fo:static-content>
				<fo:static-content flow-name="xsl-region-before-lastPage">
					<xsl:call-template name="page_hedder" />
				</fo:static-content>
				<fo:static-content flow-name="xsl-region-after-lastPage">
					<fo:block>
						<fo:table table-layout="fixed" font-family="Verdana"
							font-size="8pt">
							<fo:table-column column-width="proportional-column-width(80)" />
							<fo:table-column column-width="proportional-column-width(20)" />
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell text-align="left">
										<fo:block font-size="8pt">
										</fo:block>
									</fo:table-cell>
									<fo:table-cell text-align="right">
										<fo:block font-size="8pt" text-align="right" margin-bottom="8mm">
											<fo:block-container height="100px" width="100px" position="absolute">
												<fo:block margin-top="-55pt" margin-right="-10pt" text-align="right">
													<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
													inline-progression-dimension.maximum="100%" block-progression-dimension.maximum="100%" height="2.88cm"
													width="3.88cm">
														<xsl:attribute name="src"><xsl:value-of
															select="reportDetails/footerLogoPath" /></xsl:attribute>
													</fo:external-graphic>
												</fo:block>
											</fo:block-container>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
					</fo:block>
					<xsl:call-template name="schoolreportFooter" />
					</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
					<!-- 1st page -->					
					<fo:block>
						<fo:block margin-top="-25pt" space-after="5pt">
							<fo:leader leader-pattern="rule" leader-length="100%"
								rule-style="solid" />
						</fo:block>
						<fo:block font-size="9pt">
							<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">
								<xsl:if test="reportDetails/data/contentAreaCode = 'ELA'">
									<!-- Changed for minor text changes in 2017 -->
									<!-- <xsl:text>The KAP assessments measure students’ understanding of the Kansas College and Career Ready Standards at each grade. The English language arts questions ask students to select the right answer, organize information, and respond to a writing prompt. In grades 3–8, a student’s overall score combines a score from the reading, writing, and listening section and a score from the on-demand writing task section. In grade 10, students took an on-demand writing task field test and did not receive a writing task score.</xsl:text> -->
									<xsl:text>The KAP assessments measure students’ understanding of the Kansas College and Career Ready Standards at each grade. The English language arts assessment asks students to read and answer questions about literary passages, informational texts, and writing samples. Students demonstrate their knowledge and skills related to reading and writing by selecting the right answer and sorting, matching, labeling, and ordering information.</xsl:text>
								</xsl:if>
								<xsl:if test="reportDetails/data/contentAreaCode = 'M'">
									<!-- Changed for minor text changes in 2017 -->
									<!-- <xsl:text>The KAP assessments measure students’ understanding of the Kansas College and Career Ready Standards at each grade. The mathematics questions may ask students to select the right answer, sort items, create graphs, or label pictures. Students’ scores in grades 3–8 include a performance task. Students in grade 10 took a field-test performance task and did not receive a performance-task score.</xsl:text> -->
									<xsl:text>The KAP assessments measure students’ understanding of the Kansas College and Career Ready Standards at each grade. The math assessment asks students to answer computation questions and questions about data presented in word problems, equations, graphs, tables, and diagrams. Students may show what they know about mathematics by selecting or providing the right answer, sorting or ordering items, creating graphs, and labeling pictures.</xsl:text>
								</xsl:if>
								<xsl:if test="reportDetails/data/contentAreaCode = 'SS'">
									<xsl:text>The KAP assessments measure students’ understanding of the Kansas College and Career Ready Standards. Students take the history, government, and social studies assessment in grades 6, 8, and 11. These questions may ask students to select the right answer, organize information, and respond to a writing prompt.</xsl:text>
								</xsl:if>
								<!-- Changed for minor text changes in 2017 -->
								<xsl:if test="reportDetails/data/contentAreaCode = 'Sci'">
									<xsl:text>The KAP assessments measure students’ understanding of the Kansas College and Career Ready Standards at each grade. The science assessment asks students to answer questions about data presented in narratives, equations, graphs, tables, and diagrams. Students show what they know about science by selecting or providing the right answer; sorting, ordering, or matching items; and labeling pictures.</xsl:text>
								</xsl:if>
							</xsl:if>
							<xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'">This report has information about the district's Alaska Measures of Progress (AMP) test scores. The AMP measures students' understanding of the Alaska English Language Arts and Mathematics Standards in grades 3-10 using questions that ask students to select the right answer, sort items, create graphs, and label pictures. For sample test questions, see http://amp.cete.us/alaskatpt.</xsl:if>
						</fo:block>
						<xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'">
						<fo:block margin-top="4mm" font-weight="bold">Median District <xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'"><xsl:text>Performance</xsl:text></xsl:if><xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'"><xsl:text>Achievement</xsl:text></xsl:if></fo:block>
						<fo:block margin-top="3mm">This chart compares the district’s overall <xsl:value-of select="reportDetails/data/contentAreaName"/> test scores by grade to the median <xsl:value-of select="reportDetails/data/contentAreaName"/> test scores in the state.</fo:block>
							</xsl:if>
							<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">
							<fo:block font-weight="bold" font-family="Verdana"
								font-size="12pt" text-align="center" margin-top="4mm">
								<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">
									<xsl:if test="reportDetails/data/contentAreaCode = 'ELA'">
										<!-- Changed for minor text changes in 2017 -->
										<!-- Reading, Writing, and Listening Scores: --> 
										Median District and State Performance
									</xsl:if>
									<xsl:if test="reportDetails/data/contentAreaCode = 'M'">
										<!-- Changed for minor text changes in 2017 -->
										<!-- Mathematics Scores: --> Median District and State Performance
									</xsl:if>
									<xsl:if test="reportDetails/data/contentAreaCode = 'SS'">
										History, Government, and Social Studies: Median District and
										State Performance
									</xsl:if>
									<!-- Changed for minor text changes in 2017 -->
									<xsl:if test="reportDetails/data/contentAreaCode = 'Sci'">
										Median District and State Performance
									</xsl:if>
								</xsl:if>
							</fo:block>
							<fo:block-container text-align="center"
								margin-top="4px">
								<xsl:call-template name="district-median-table-legend" />
							</fo:block-container>
						</xsl:if>
							
						<fo:block margin-top="0mm">
							<xsl:call-template name="summaryMedianScoreGraph">
								<xsl:with-param name="reportType" select="'district'"/>
							</xsl:call-template>													
						</fo:block>
						<xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'">
						<fo:block margin-top="6mm">
							<xsl:call-template name="explanation">
								<xsl:with-param name="reportType" select="'district'"/>
							</xsl:call-template>
						</fo:block>
						</xsl:if>
							<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">
							<fo:block margin-top="2pt" >
								<xsl:call-template name="standerd_error_for_grade" />
							</fo:block>
							<fo:block font-size="8pt" text-align="left" margin-top="6pt">
							The standard error indicates how much students’ scores might vary
							if the students took many equivalent versions of the test (tests
							with different items but covering the same knowledge and skills).
						</fo:block>
						</xsl:if>	
					</fo:block>
					<!-- 2nd page -->
					<xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'">
						<fo:block break-before="page">
							<fo:block margin-top="4mm">
								Overall scores on the
								<xsl:value-of select="reportDetails/data/assessmentProgramCode" />
								test are divided into
								<xsl:call-template name="levelnumber-word-map">
									<xsl:with-param name="val">
										<xsl:call-template name="max-level-number" />
									</xsl:with-param>
								</xsl:call-template>
								<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">
									<xsl:text> performance</xsl:text>
								</xsl:if>
								<xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'">
									<xsl:text> achievement</xsl:text>
								</xsl:if>
								levels. The levels range from
								<xsl:call-template name="min-level-number" />
								to
								<xsl:call-template name="max-level-number" />
								, with
								<xsl:call-template name="max-level-number" />
								being the highest level. Cut scores for levels 2 and 4 vary by
								grade.
							</fo:block>
							<fo:block margin-top="4mm">
								The chart below compares the percentage of
								<xsl:value-of select="reportDetails/data/contentAreaName" />
								students at the school by grade in each
								<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">
									<xsl:text>performance</xsl:text>
								</xsl:if>
								<xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'">
									<xsl:text>achievement</xsl:text>
								</xsl:if>
								level to the district and state. Complete
								<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">
									<xsl:text>performance</xsl:text>
								</xsl:if>
								<xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'">
									<xsl:text>achievement</xsl:text>
								</xsl:if>
								level descriptors with the cut scores can be found at
								<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">
									<xsl:text>http://ksassessments.org/pld</xsl:text>
								</xsl:if>
								<xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'">
									<xsl:text>http://amp.cete.us/AMPresources</xsl:text>
								</xsl:if>
								.
							</fo:block>
							<fo:block margin-top="4mm">
								<xsl:call-template name="summaryLevelScoreGraph">
									<xsl:with-param name="reportType" select="'district'" />
								</xsl:call-template>
							</fo:block>
							<fo:block text-align="end" padding="2px" font-size="8pt"
								color="#939597">
								<fo:block>*Percentages may not add to 100% because of rounding.
								</fo:block>
								<xsl:if test="reportDetails/suppressLevels = 1">
									<fo:block>**Exact numbers are concealed to protect student
										privacy.
									</fo:block>
								</xsl:if>
							</fo:block>
						</fo:block>
					</xsl:if>
			<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">
			<!-- Changed for minor text changes in 2017 -->
			<xsl:if test="reportDetails/data/contentAreaCode != 'ELA' and reportDetails/data/contentAreaCode != 'M' and reportDetails/data/contentAreaCode != 'Sci'">
			<fo:block break-before="page">
			<fo:table table-layout="fixed">
			<fo:table-column column-width="proportional-column-width(100)" />
			<fo:table-body>
					<xsl:if test="reportDetails/data/contentAreaCode = 'ELA'">
							<fo:table-row>
								<fo:table-cell>
									<fo:block font-weight="bold" font-family="Verdana"
										font-size="12pt" text-align="center" margin-top="4pt">
										Reading, Writing, and Listening Scores: Percentage of Students in
									</fo:block>
								</fo:table-cell>
							</fo:table-row>	
							<fo:table-row>
								<fo:table-cell>
										<fo:block font-weight="bold" font-family="Verdana"
											font-size="12pt" text-align="center" >
											 Each Performance Level
										</fo:block>
								</fo:table-cell>
								</fo:table-row>
					</xsl:if>
					<xsl:if test="reportDetails/data/contentAreaCode = 'M'">
								<fo:table-row>
									<fo:table-cell>	
											<fo:block font-weight="bold" font-family="Verdana"
											font-size="12pt" text-align="center" margin-top="4pt">
											Mathematics Scores: Percentage of Students in Each Performance
												Level
											</fo:block>
									</fo:table-cell>
								</fo:table-row>
					</xsl:if>
					<xsl:if test="reportDetails/data/contentAreaCode = 'SS'">
			                   <fo:table-row>
							       <fo:table-cell>
									   <fo:block font-weight="bold" font-family="Verdana"
								         font-size="12pt" text-align="center" margin-top="4pt">
									    	History, Government, and Social Studies Scores: Percentage of
										   Students in Each Performance Level
							            </fo:block>
							          </fo:table-cell>
							 </fo:table-row>
					</xsl:if>
			</fo:table-body>
			</fo:table>		
							<fo:block-container text-align="center"
								margin-top="4px">
								<xsl:call-template name="levels-table-legend" />
							</fo:block-container>	
							<fo:block text-align="center" padding="2px" font-style="italic" font-size="8pt" margin-left="9pt">
								Percentages may not add to 100% because of rounding.
							</fo:block>
							
							<fo:block margin-top="4mm">
								<xsl:call-template name="summaryLevelScoreGraph">
									<xsl:with-param name="reportType" select="'district'" />
									<xsl:with-param name="percentLevelDescriptionType" select="'Main'"/>
								</xsl:call-template>
							</fo:block>

				</fo:block>
				</xsl:if>
			</xsl:if>
					<!-- 3rd page -->
					<xsl:if test="reportDetails/data/contentAreaCode = 'ELA'">
					<!-- <fo:block break-before="page">

						<fo:block font-weight="bold" font-family="Verdana"
							font-size="12pt" text-align="center" margin-top="4pt">
							<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">
								<xsl:if test="reportDetails/data/contentAreaCode = 'ELA'">
									On-Demand Writing Task Scores&#58;&#32;Percentage by Grade
								</xsl:if>
								<xsl:if test="reportDetails/data/contentAreaCode = 'M'">

								</xsl:if>
								<xsl:if test="reportDetails/data/contentAreaCode = 'SS'">

								</xsl:if>
							</xsl:if>
						</fo:block>						
						<fo:block-container text-align="center"
							margin-top="4px">
							<xsl:call-template name="levels-table-ondemand-legend" />
						</fo:block-container>
						<fo:block text-align="center" padding="2px" font-style="italic" font-size="8pt" margin-left="9pt">
							Percentages may not add to 100% because of rounding.
						</fo:block>
						<fo:block margin-top="4mm">
							<xsl:call-template name="summaryLevelScoreGraph">
									<xsl:with-param name="reportType" select="'district'" />
									<xsl:with-param name="percentLevelDescriptionType" select="'MDPT'"/>
								</xsl:call-template>
						</fo:block>
						
					</fo:block> -->
					</xsl:if>
					<!-- 4th page -->
					<xsl:if test="reportDetails/data/contentAreaCode = 'ELA' or reportDetails/data/contentAreaCode = 'M' or reportDetails/data/contentAreaCode = 'Sci'">
					 <fo:block break-before="page">
						<fo:block font-weight="bold" font-family="Verdana"
							font-size="12pt" text-align="center" margin-top="4pt">
							<!-- Overall Scores: -->
							Percentage of Students in Each Performance Level, by Grade
						</fo:block>

						
						<fo:block-container text-align="center"
							margin-top="4px">
							<xsl:call-template name="levels-table-legend" />
						</fo:block-container>
						<fo:block text-align="center" padding="2px" font-style="italic" font-size="8pt" margin-left="9pt">
							Percentages may not add to 100% because of rounding.
						</fo:block>
						<fo:block margin-top="4mm">
							<xsl:call-template name="summaryLevelScoreGraph">
									<xsl:with-param name="reportType" select="'district'" />
									<!-- <xsl:with-param name="percentLevelDescriptionType" select="'Combined'"/> -->
									<!-- commented for 2017th year we dont have combined level scores -->			
									<xsl:with-param name="percentLevelDescriptionType" select="'Main'"/>
								</xsl:call-template>
						</fo:block>
						
					</fo:block> 
					</xsl:if>
			<!-- 5th page -->
					<fo:block break-before="page">
						<xsl:choose>
							<xsl:when test="reportDetails/data/contentAreaCode != 'SS'">
								<xsl:call-template name="rating_icon_legand">
									<xsl:with-param name="organization"
										select="'District'"></xsl:with-param>
								</xsl:call-template>
								<fo:block>					
									<xsl:call-template name="rating_icons_text_looper">
										<xsl:with-param name="organization"
											select="'District'"></xsl:with-param>
										<xsl:with-param name="subScoreIconExceedsPath"
											select="reportDetails/iconExceedsPath"></xsl:with-param>
										<xsl:with-param name="subScoreIconMeetsPath"
											select="reportDetails/iconMeetsPath"></xsl:with-param>
										<xsl:with-param name="subScoreIconBelowPath"
											select="reportDetails/iconBelowPath"></xsl:with-param>
										<xsl:with-param name="subScoreIconInsufficientPath"
											select="reportDetails/iconInsufficientPath"></xsl:with-param>
									</xsl:call-template>
								</fo:block>
								<fo:block margin-top="7pt">
									<xsl:call-template name="subsore_disription"/>						
								</fo:block>
							</xsl:when>
							<xsl:otherwise>
								<fo:block-container height="212mm">
									<fo:block font-size="10pt" font-family="Verdana" text-align="left" line-height="10pt" margin-top="15pt">
										<xsl:text>Section non-applicable for this subject.</xsl:text>
									</fo:block>
								</fo:block-container>
							</xsl:otherwise>
						</xsl:choose>
					</fo:block>
			<!-- 6th page -->
					<fo:block>
						<xsl:if test="reportDetails/data/contentAreaCode != 'SS'">	
							<fo:block-container keep-together.within-page="always">
								<fo:block font-size="9pt" text-align="left" font-weight="bold"
									margin-top="6pt">
									<xsl:text>Your District’s Performance</xsl:text>
								</fo:block>
								<fo:block margin-top="6pt">
									<xsl:call-template name="rating_icons_text">
										<xsl:with-param name="imageone"
											select="reportDetails/iconExceedsPath"></xsl:with-param>
										<xsl:with-param name="imagetwo"
											select="reportDetails/iconMeetsPath"></xsl:with-param>
										<xsl:with-param name="textone" select="'Exceeds'"></xsl:with-param>
										<xsl:with-param name="texttwo" select="'Meets'"></xsl:with-param>
										<xsl:with-param name="cantentone"
											select="'In this area, your students typically performed better than students who received the minimum Level 3 score.'"></xsl:with-param>
										<xsl:with-param name="cantenttwo"
											select="'In this area, your students typically performed as well as students who received the minimum Level 3 score.'"></xsl:with-param>
									</xsl:call-template>
								</fo:block>
								<fo:block margin-top="6pt">
									<xsl:call-template name="rating_icons_text">
										<xsl:with-param name="imageone"
											select="reportDetails/iconBelowPath"></xsl:with-param>
										<xsl:with-param name="imagetwo"
											select="reportDetails/iconInsufficientPath"></xsl:with-param>
										<xsl:with-param name="textone" select="'Below'"></xsl:with-param>
										<xsl:with-param name="texttwo" select="'Insufficient Data'"></xsl:with-param>
										<xsl:with-param name="cantentone"
											select="'In this area, your students typically performed below students who received the minimum Level 3 score.'"></xsl:with-param>
										<xsl:with-param name="cantenttwo"
											select="'In this area, your students did not answer enough questions for accurate reporting.'"></xsl:with-param>
									</xsl:call-template>
								</fo:block>						
							</fo:block-container>
						</xsl:if>
						<fo:block-container>
							<xsl:call-template name="summaryadditionalResources"/>
						</fo:block-container>
					</fo:block> 
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	<xsl:template name="report-page1-header">
		<fo:table table-layout="fixed">
			<fo:table-column column-width="proportional-column-width(50)" />
			<fo:table-column column-width="proportional-column-width(22)" />
			<fo:table-column column-width="proportional-column-width(28)" />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell>
						<fo:block font-family="Verdana" font-weight="bold"
							font-size="11pt" line-height="13.5pt" text-align="left"
							wrap-option="no-wrap">
							<xsl:call-template name="summaryheaderDistrictName" />
						</fo:block>
						<fo:block-container font-family="Verdana"
							font-size="9pt" line-height="12.5pt">
							<xsl:call-template name="headerContentAreaName" />							
						</fo:block-container>
					</fo:table-cell>
					<fo:table-cell text-align="center">
						<fo:block white-space-treatment="preserve">
						</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="end">
						<fo:block-container display-align="center"
							text-align="center" color="#25408f" font-family="Verdana"
							font-weight="bold" font-size="9pt" margin-left="3.0mm">
							<xsl:call-template name="headerSchoolYear" />
							<xsl:call-template name="headerLogo" />
						</fo:block-container>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>
		<xsl:template name="page_hedder">
		<fo:block margin-bottom="0px" font-family="Verdana"
			font-size="9pt">
			<fo:table table-layout="fixed">
				<fo:table-column column-width="proportional-column-width(1)" />
				<fo:table-column column-width="proportional-column-width(1)" />
				<fo:table-column column-width="proportional-column-width(1)" />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell text-align="start" wrap-option="no-wrap">
							<fo:block>DISTRICT REPORT</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center">
							<fo:block>
								<!-- DISTRICT:
								<xsl:value-of select="reportDetails/data/districtDisplayIdentifier" /> -->

							</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="end" wrap-option="no-wrap">
							<fo:block>
								<xsl:call-template name="headerContentAreaName" />	
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
		<fo:block margin-top="-5pt" space-after="5pt">
			<fo:leader leader-pattern="rule" leader-length="100%"
				rule-style="solid" />
		</fo:block>
	</xsl:template>
		<xsl:template name="standerd_error_for_grade">
		<fo:table table-layout="fixed">
			<fo:table-column column-width="proportional-column-width(48)" />
			<fo:table-column column-width="proportional-column-width(4)" />
			<fo:table-column column-width="proportional-column-width(48)" />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell>
						<fo:block font-weight="bold" font-family="Verdana"
							font-size="9pt" text-align="left" margin-top="10pt">Standard error of
							measurement for this report:
						</fo:block>
						<fo:block-container font-family="Verdana"
							font-size="8pt" text-align="left" margin-top="10pt">
							<xsl:for-each select="reportDetails/standardErrorList/edu.ku.cete.report.ReportStandardError">
								<fo:block font-size="8pt">									
								<xsl:value-of select="gradeName" />: <xsl:text>District&#8212;</xsl:text><xsl:value-of select="districtStandardError" />  |  <xsl:text>State&#8212;</xsl:text><xsl:value-of select="stateStandardError" />
								</fo:block>
							</xsl:for-each>
						</fo:block-container>
					</fo:table-cell>
					<fo:table-cell text-align="center">
						<fo:block white-space-treatment="preserve">
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
						<fo:block font-size="8pt" text-align="left" margin-top="29pt">
							
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>
</xsl:stylesheet>