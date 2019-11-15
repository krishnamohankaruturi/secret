<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:fo="http://www.w3.org/1999/XSL/Format" extension-element-prefixes="saxon">
	<xsl:include href="utility.xsl"/>
	<xsl:include href="levels-table.xsl"/>
	<xsl:include href="claims-definition.xsl"/>
	<xsl:include href="resourcesISR.xsl"/>
	<xsl:include href="subscorecharts.xsl"/>
	<xsl:include href="explanation.xsl"/>
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" font-family="Verdana" font-size="10pt">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="firstPage" page-height="27.94cm" page-width="21.59cm" margin="1.27cm">
					<fo:region-body margin-top="25mm" />
					<fo:region-before region-name="xsl-region-before-firstPage" extent="30mm" />
				<fo:region-after region-name="xsl-region-after-firstPage" extent="60mm" display-align="after"/>
				</fo:simple-page-master>
				<fo:simple-page-master master-name="nonFirstPage" page-height="27.94cm" page-width="21.59cm" margin="1.27cm">
					<fo:region-body margin-top="7mm" margin-bottom="15mm" />
					<fo:region-before region-name="xsl-region-before-nonFirstPage" extent="7mm" />
         			<fo:region-after region-name="xsl-region-after-nonFirstPage" extent="7mm" display-align="after"/>
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
					<fo:block margin-bottom="0px" font-family="Verdana" font-size="9pt">
						<fo:table table-layout="fixed" background-color="white">
							<fo:table-column column-width="32%" />
							<fo:table-column column-width="34%" />
							<fo:table-column column-width="34%" />
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell text-align="start" wrap-option="no-wrap">
										<fo:block text-align="left">STUDENT REPORT</fo:block>
									</fo:table-cell>
									<fo:table-cell text-align="center" display-align="center">
										<fo:block-container text-align="center" display-align="center">
											<fo:block wrap-option="no-wrap">STUDENT: <xsl:call-template name="lastName-Header"/>, <xsl:call-template name="firstName-Header" /></fo:block>
											<fo:block>STATE ID: <xsl:call-template name="stateStudentIdentifier"/></fo:block>
										</fo:block-container>
									</fo:table-cell>
									<fo:table-cell text-align="end">
										<fo:block text-align="right"><xsl:text>GRADE: </xsl:text>
										<xsl:value-of select="reportDetails/data/gradeCode"/><xsl:text>&#32;</xsl:text>
										<xsl:choose>
											<xsl:when test="reportDetails/data/contentAreaCode = 'SS'">
												<xsl:text>History, Government, and Social Studies</xsl:text>
											</xsl:when>
											<xsl:otherwise>
												<xsl:value-of select="reportDetails/data/contentAreaName"/>
											</xsl:otherwise>
										</xsl:choose>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
					</fo:block>
				</fo:static-content>
				
			<xsl:if test="reportDetails/data/status != 'false'">
							
				<fo:static-content flow-name="xsl-region-after-firstPage">
				<fo:block text-align="center" padding-top="10pt" font-family="Verdana" font-size="12pt" >
							<fo:inline font-weight="bold"><xsl:text>Performance Level Descriptions</xsl:text></fo:inline>
							<xsl:if test="reportDetails/data/contentAreaCode != 'ELA' and reportDetails/data/contentAreaCode != 'M' and reportDetails/data/contentAreaCode != 'Sci'and reportDetails/data/contentAreaCode != 'SS'">
								<fo:inline font-style="italic" font-size="8pt"><xsl:text> &#32;(applies to all scores) </xsl:text></fo:inline>
							</xsl:if>
						</fo:block>
						<fo:block text-align="left" font-family="Verdana" font-size="9pt">
							<fo:table table-layout="fixed">
								<fo:table-column column-width="proportional-column-width(25)" />
				        		<fo:table-column column-width="proportional-column-width(25)" />
				        		<fo:table-column column-width="proportional-column-width(25)" />
				        		<fo:table-column column-width="proportional-column-width(25)" />
								<fo:table-body>
									<fo:table-row>
										<fo:table-cell padding-right="15pt">
											<fo:block-container>
											<fo:block>
												<fo:leader space-after="5pt" leader-pattern="rule" color="#EC6B38" leader-length="97%" rule-style="solid" rule-thickness="4pt" />
											</fo:block>
											<fo:block>
												<xsl:for-each select="reportDetails/data/levels/edu.ku.cete.domain.report.LevelDescription">
													<xsl:if test="level = '1'">
														<fo:inline font-weight="bold"><xsl:text>Level 1: </xsl:text></fo:inline> 
														<xsl:value-of select="descriptionParagraphPageBottom"/>
													</xsl:if>
												</xsl:for-each>
											</fo:block>
											</fo:block-container>
										</fo:table-cell>
										<fo:table-cell padding-right="15pt">
											<fo:block-container>
												<fo:block>
													<fo:leader space-after="5pt" leader-pattern="rule" color="#EA8F06" leader-length="97%" rule-style="solid" rule-thickness="4pt"/>
												</fo:block>
												<fo:block>
													<xsl:for-each select="reportDetails/data/levels/edu.ku.cete.domain.report.LevelDescription">
														<xsl:if test="level = '2'">
															<fo:inline font-weight="bold"><xsl:text>Level 2: </xsl:text></fo:inline>
															<xsl:value-of select="descriptionParagraphPageBottom"/>
														</xsl:if>
													</xsl:for-each>
													<!-- <xsl:text>A student at Level 2 shows a basic ability to understand and use the mathematics skills and knowledge needed for college and career readiness.</xsl:text> --> 
												</fo:block>
											</fo:block-container>
										</fo:table-cell>
										<fo:table-cell padding-right="15pt">
											<fo:block-container>
												<fo:block>
													<fo:leader space-after="5pt" leader-pattern="rule" color="#FBC668" leader-length="97%" rule-style="solid" rule-thickness="4pt" />
												</fo:block>
												<fo:block>
													<xsl:for-each select="reportDetails/data/levels/edu.ku.cete.domain.report.LevelDescription">
														<xsl:if test="level = '3'">
															<fo:inline font-weight="bold"><xsl:text>Level 3: </xsl:text></fo:inline>
															<xsl:value-of select="descriptionParagraphPageBottom"/>
														</xsl:if>
													</xsl:for-each>
													<!-- <xsl:text>A student at Level 3 shows an effective ability to understand and use the mathematics skills and knowledge needed for college and career readiness.</xsl:text> --> 
												</fo:block>
											</fo:block-container>
										</fo:table-cell>
										<fo:table-cell padding-right="13pt">
											<fo:block-container>
												<fo:block>
													<fo:leader space-after="5pt" leader-pattern="rule" color="#F8EEA4" leader-length="96%" rule-style="solid" rule-thickness="4pt" />
												</fo:block>
												<fo:block>
													<xsl:for-each select="reportDetails/data/levels/edu.ku.cete.domain.report.LevelDescription">
														<xsl:if test="level = '4'">
															<fo:inline font-weight="bold"><xsl:text>Level 4: </xsl:text></fo:inline>
															<xsl:value-of select="descriptionParagraphPageBottom"/>
														</xsl:if>
													</xsl:for-each>
												 <!-- <xsl:text>A student at Level 4 shows an excellent ability to understand and use the mathematics skills and knowledge needed for college and career readiness.</xsl:text> --> 
												</fo:block>
											</fo:block-container>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
						</fo:block>
				<fo:block>
							<fo:leader leader-pattern="rule"  leader-length="100%" rule-style="solid" space-after="5pt" />
						</fo:block>
						<fo:block font-size="9pt" font-family="Verdana" text-align="left" >
							<fo:inline>
								<xsl:text>For more details about how your student performed on specific types of test questions, see the back of this report. </xsl:text>
								<fo:inline padding-left="4pt" text-align="right"><xsl:call-template name="footerNextPageArrow"/></fo:inline>
							</fo:inline>
						</fo:block>
				</fo:static-content>
				
				<fo:static-content flow-name="xsl-region-after-nonFirstPage">
         			<xsl:call-template name="reportFooter"/>
      			</fo:static-content>
      		</xsl:if>
      		
      	<xsl:if test="reportDetails/data/status = 'false'">		     			
      			<fo:static-content flow-name="xsl-region-after-firstPage">
      				<fo:block margin-bottom="27mm" >
						<fo:table table-layout="fixed" font-family="Verdana" font-size="8pt">
								<fo:table-column column-width="proportional-column-width(80)" />
								<fo:table-column column-width="proportional-column-width(20)" />
								<fo:table-body>
								<fo:table-row>
									<fo:table-cell text-align="left" >
										<fo:block font-size="8pt" >
											<fo:block></fo:block>
										</fo:block>
									</fo:table-cell>
								
									<fo:table-cell text-align="right" margin-bottom="4mm" >								 	  
											<fo:block-container text-align="right" absolute-position="absolute" >
												<fo:block width="100%" height="100%">
												 	<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
													inline-progression-dimension.maximum="100%" block-progression-dimension.maximum="100%" height="2.88cm"
													width="3.88cm">
													 <xsl:attribute name="src"><xsl:value-of select="reportDetails/footerLogoPath"/></xsl:attribute>
													</fo:external-graphic> 
												</fo:block>
											</fo:block-container>										 
										</fo:table-cell>								
								</fo:table-row>
								</fo:table-body>
							</fo:table>
					</fo:block>	
      			
         			<xsl:call-template name="reportFooter"/>
      			</fo:static-content>
        </xsl:if>
		<xsl:choose>
			<xsl:when test="reportDetails/data/status = 'false'">
				<fo:flow flow-name="xsl-region-body">
					<fo:block margin-top="-25pt">
						<xsl:call-template name="header_intro_paragraph_text"/>
					</fo:block>
					<fo:block>
						<xsl:call-template name="noScores"/>	
					</fo:block>									
				</fo:flow>				
      		</xsl:when>
			<xsl:otherwise>			
				<fo:flow flow-name="xsl-region-body">
					<!-- 1st page -->
					<fo:block margin-top="-25pt">						
						<fo:block  font-size="9pt">
							<xsl:call-template name="header_intro_paragraph_text"/>
						</fo:block>						
											
						<fo:block >
							<fo:table table-layout="fixed">
								<fo:table-column column-width="proportional-column-width(100)" />
								<fo:table-body>
									<fo:table-row>
										<fo:table-cell>
											<fo:block-container>
												<fo:block font-weight="bold" font-family="Verdana" font-size="12pt" text-align="center" margin-top="4pt" margin-left="12pt">
													<xsl:if test="reportDetails/data/contentAreaCode != 'ELA' and reportDetails/data/contentAreaCode != 'M' and reportDetails/data/contentAreaCode != 'Sci' and reportDetails/data/contentAreaCode != 'SS'">
														<xsl:text>Overall&#32;</xsl:text>
													</xsl:if>
													<xsl:choose>
														<xsl:when test="reportDetails/data/contentAreaCode = 'SS'">
															<xsl:text>History, Government, and Social Studies</xsl:text>
														</xsl:when>
														<xsl:otherwise>
															<xsl:value-of select="reportDetails/data/contentAreaName"/>
														</xsl:otherwise>
													</xsl:choose>
													
													<xsl:text>&#32;Score&#58;&#32;</xsl:text>
													<xsl:choose>
														<xsl:when test="reportDetails/data/suppressMdptScore = 'true' or reportDetails/data/suppressMainScalescorePrfrmLevel = 'true'">
															<xsl:choose>
																<xsl:when test="not(reportDetails/data/mdptScorableFlag = 'false')" >
																	<xsl:text>N/A</xsl:text>		
																</xsl:when>
																<xsl:otherwise>
																	<xsl:choose>
																		<xsl:when test="reportDetails/data/scCodeExist = 'true'">
																			<xsl:text>N/A</xsl:text>
																		</xsl:when>
																		<xsl:otherwise>
																			<xsl:for-each select="reportDetails/data/allLevelDescriptions/edu.ku.cete.domain.report.LevelDescription">
																				<xsl:if test="descriptionType = 'Combined'">
																					<xsl:value-of select="levelName"/>
																				</xsl:if>
																			</xsl:for-each>
																		</xsl:otherwise>
																	</xsl:choose>
																</xsl:otherwise>
															</xsl:choose>
														</xsl:when>
														<xsl:otherwise>
															<xsl:choose>	
																<xsl:when test="reportDetails/data/performanceRawscoreIncludeFlag = 'false' and reportDetails/data/contentAreaCode = 'ELA' and reportDetails/data/gradeCode != '10' ">
																	<xsl:for-each select="reportDetails/data/allLevelDescriptions/edu.ku.cete.domain.report.LevelDescription">
																		<xsl:if test="descriptionType = 'Combined'">
																			<xsl:value-of select="levelName"/>
																		</xsl:if>
																	</xsl:for-each>
																</xsl:when>
																<xsl:otherwise>
																	<!-- <xsl:value-of select="reportDetails/data/level"></xsl:value-of> -->
																	<xsl:for-each select="reportDetails/data/allLevelDescriptions/edu.ku.cete.domain.report.LevelDescription">
																		<xsl:if test="descriptionType = 'Main'">
																			<xsl:value-of select="levelName"/>
																		</xsl:if>
																	</xsl:for-each>
																</xsl:otherwise>
															</xsl:choose>
														</xsl:otherwise>
													</xsl:choose>
												</fo:block>												
												<fo:block>
													<fo:table table-layout="fixed">
														<fo:table-column column-width="proportional-column-width(13)" />
														<fo:table-column column-width="proportional-column-width(26)" />
														<fo:table-column column-width="proportional-column-width(40)" />
														<fo:table-column column-width="proportional-column-width(35)" />
														<fo:table-body>
															<fo:table-row>
																<!-- <fo:table-cell><fo:block><xsl:text>&#32;</xsl:text></fo:block></fo:table-cell>  scoreIncompleteScorePath-->
																<fo:table-cell>
																<fo:block>
																	<xsl:if test="reportDetails/data/transferred = 'true'">
																		<!-- <fo:block margin-top="-4mm">
																			<xsl:call-template name="scoreTransferredStudentPath"/>
																		</fo:block> -->
																	</xsl:if>
																	
																	<xsl:if test="reportDetails/data/incompleteStatus ='true' or reportDetails/data/suppressMdptScore='true' or reportDetails/data/suppressMainScalescorePrfrmLevel = 'true'">	
																		
																		<xsl:choose>
																			<xsl:when test="reportDetails/data/incompleteStatus ='true' or reportDetails/data/suppressMainScalescorePrfrmLevel = 'true'">
																				<!-- <fo:block margin-top="-3mm">
																					<xsl:call-template name="scoreIncompleteScorePath"/>
																				</fo:block> -->
																			</xsl:when>
																			<xsl:otherwise>
																				<xsl:choose>
																					<xsl:when test="reportDetails/data/suppressMdptScore = 'true' and reportDetails/data/scCodeExist ='true'">
																						<!-- <fo:block margin-top="-3mm">
																							<xsl:call-template name="scoreIncompleteScorePath"/>
																						</fo:block>	 -->
																					</xsl:when>
																					<xsl:otherwise>
																						<xsl:if test="reportDetails/data/suppressMdptScore = 'true' and not(reportDetails/data/mdptScorableFlag = 'false')">
																							<!-- <fo:block margin-top="-3mm">
																								<xsl:call-template name="scoreIncompleteScorePath"/>
																							</fo:block> -->
																						</xsl:if>
																					</xsl:otherwise>
																				</xsl:choose>
																			</xsl:otherwise>
																		</xsl:choose>
																	</xsl:if>
																</fo:block>
																</fo:table-cell>
																<fo:table-cell><fo:block><xsl:text>&#32;</xsl:text></fo:block></fo:table-cell>
																<fo:table-cell>
																	<fo:block ><xsl:call-template name="scoreStarPerformanceLevel"/></fo:block>
																</fo:table-cell>
																<fo:table-cell><fo:block><xsl:text>&#32;</xsl:text></fo:block></fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:block>
												<fo:block>
												<xsl:choose>
													<xsl:when test="reportDetails/data/prevLevelString = 'N/A'  and reportDetails/data/contentAreaCode != 'Sci' ">												
														<fo:block font-family="Verdana" font-size="9pt" text-align="center" margin-top="-1mm" >
																<xsl:text>Your student’s performance level from last year is not available.</xsl:text>														
														</fo:block>
													</xsl:when>												
												<xsl:otherwise>
													<xsl:choose>
														<xsl:when test="reportDetails/data/prevLevelString != 'Suppressed' and reportDetails/data/contentAreaCode != 'Sci' and reportDetails/data/contentAreaCode != 'SS'">
															<fo:block font-family="Verdana" font-size="9pt" text-align="center" margin-top="-1mm"  >
																	<xsl:text>Last year your student performed at Level&#32;</xsl:text>
																	<xsl:value-of select="reportDetails/data/prevLevelString"/><xsl:text>&#46;</xsl:text>
															</fo:block>
														</xsl:when>
														<xsl:otherwise>
															<fo:block></fo:block>
														</xsl:otherwise>
													</xsl:choose>
												</xsl:otherwise>
												</xsl:choose>
												</fo:block>
											</fo:block-container>											
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
						</fo:block>
						<fo:block space-after="4pt">
							<fo:leader leader-pattern="rule"  leader-length="100%" rule-style="solid"/>
						</fo:block>
						
						
						<fo:block font-weight="bold" font-family="Verdana" font-size="12pt" text-align="center" space-after="8pt">
							<xsl:choose>	
								<xsl:when test="reportDetails/data/performanceRawscoreIncludeFlag = 'false'">
									<xsl:choose>
										<xsl:when test="reportDetails/data/contentAreaCode = 'ELA'">
											<xsl:choose>
												<xsl:when test="reportDetails/data/gradeCode != '10'">
													<!-- <xsl:text>Reading, Writing, and Listening Score</xsl:text> -->
												</xsl:when>
												<xsl:otherwise>
													<!-- <xsl:value-of select="reportDetails/data/contentAreaName"/><xsl:text>&#32;Score</xsl:text> -->
												</xsl:otherwise>
											</xsl:choose>
										</xsl:when>
										<xsl:otherwise>
												<xsl:if test="reportDetails/data/contentAreaCode != 'M' and reportDetails/data/contentAreaCode != 'Sci' and reportDetails/data/contentAreaCode != 'SS'">
													<xsl:choose>
														<xsl:when test="reportDetails/data/contentAreaCode = 'SS'">
															<!-- <xsl:text>History, Government, and Social Studies</xsl:text> -->
														</xsl:when>
														<xsl:otherwise>
															<xsl:value-of select="reportDetails/data/contentAreaName"/>
														</xsl:otherwise>
													</xsl:choose>
													<xsl:text>&#32;Score</xsl:text>
												</xsl:if>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:when>
								<xsl:otherwise>
	               			      <xsl:if test="reportDetails/data/contentAreaCode != 'ELA' and reportDetails/data/contentAreaCode != 'M' and reportDetails/data/contentAreaCode != 'Sci' and reportDetails/data/contentAreaCode != 'SS'">
									<xsl:choose>
										<xsl:when test="reportDetails/data/contentAreaCode = 'SS'">
											<!-- <xsl:text>History, Government, and Social Studies</xsl:text> -->
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="reportDetails/data/contentAreaName"/>
										</xsl:otherwise>
									</xsl:choose>
									<xsl:text>&#32;Score</xsl:text>
								   </xsl:if>
								</xsl:otherwise>
							</xsl:choose>
						</fo:block>
						
						
						<fo:block>
							<fo:table table-layout="fixed">
								<fo:table-column column-width="proportional-column-width(12)" />
								<fo:table-column column-width="proportional-column-width(40)" />
								<fo:table-column column-width="proportional-column-width(48)" />
								<fo:table-body>
									<fo:table-row>
									
									<fo:table-cell>
										 <fo:block>
											<xsl:if test="reportDetails/data/incompleteStatus ='true' and reportDetails/data/contentAreaCode = 'ELA'">	
											    <!-- <fo:block margin-top="-5mm"><xsl:call-template name="scoreIncompleteScorePath"/></fo:block> -->
											</xsl:if>
										</fo:block>	 
									</fo:table-cell>									
										<fo:table-cell>
											<fo:block margin-top="0pt" margin-left="-70pt">
												<fo:external-graphic>
													<xsl:attribute name="width"><xsl:value-of select="reportDetails/studentMeterWidth"/></xsl:attribute>
													<xsl:attribute name="height"><xsl:value-of select="reportDetails/studentMeterHeight"/></xsl:attribute>
													<xsl:attribute name="src"><xsl:value-of select="reportDetails/studentMeter"/></xsl:attribute>
												</fo:external-graphic>
											</fo:block>
											<fo:block margin-top="-15pt">
												<fo:table table-layout="fixed">
													<fo:table-column column-width="proportional-column-width(33)" />
													<fo:table-column column-width="proportional-column-width(33)" />
													<fo:table-column column-width="proportional-column-width(34)" />
													<fo:table-body>
														<fo:table-row>
															
															
															<!-- School Median  -->
															<fo:table-cell text-align="center" >
															<fo:block margin-left="-51.5pt">
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
																
																<fo:block margin-left="-78.2pt">																
																<fo:block font-size="7pt" >School Median</fo:block>
																<xsl:variable name="schoolMedianScore">
  																	<xsl:call-template name="lookup-median-se-sc">
																		<xsl:with-param name="paramOrgId" select="reportDetails/data/attendanceSchoolId"/>
																		<xsl:with-param name="paramType" select="'SCORE'"/>
																	</xsl:call-template>
																</xsl:variable>
																<xsl:choose>
 																	 <xsl:when test="$schoolMedianScore > 0">
 																	 	<fo:block font-size="7pt" font-weight="bold">
																			<fo:inline><xsl:copy-of select="$schoolMedianScore" /></fo:inline>
																		</fo:block>
																		<fo:block font-size="6pt">Number of Students: 
																			<xsl:call-template name="lookup-median-se-sc">
																				<xsl:with-param name="paramOrgId" select="reportDetails/data/attendanceSchoolId"/>
																				<xsl:with-param name="paramType" select="'SC'"/>
																			</xsl:call-template>
																		</fo:block>
																	 </xsl:when>
  																	 <xsl:otherwise>  																	 
  																	 	 	<fo:table table-layout="fixed" background-color="white" margin-left="86pt"  >
																			<fo:table-column column-width="60%" />						
																			<fo:table-body>
																				<fo:table-row>
																					<fo:table-cell text-align="start" wrap-option="wrap">
																						<fo:block font-size="6pt" text-align="center" >
																						<fo:inline font-size="6pt" >Data not shown to protect student privacy</fo:inline>
																						</fo:block>
																					</fo:table-cell>																					
																				</fo:table-row>
																			</fo:table-body>
																		</fo:table> 
																 </xsl:otherwise>
																</xsl:choose>
																</fo:block>
															</fo:block>
															</fo:table-cell>
															
															<!-- District Median -->
															<fo:table-cell text-align="center"  margin-left="-34.5pt">
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
																
																<fo:block margin-left="-44pt">															
																<fo:block font-size="7pt">District Median</fo:block>
																<xsl:variable name="districtMedianScore">
  																	<xsl:call-template name="lookup-median-se-sc">
																		<xsl:with-param name="paramOrgId" select="reportDetails/data/districtId"/>
																		<xsl:with-param name="paramType" select="'SCORE'"/>
																	</xsl:call-template>
																</xsl:variable>
																<xsl:choose>
 																	<xsl:when test="$districtMedianScore > 0">
 																		<fo:block font-size="7pt" font-weight="bold">
																			<fo:inline><xsl:copy-of select="$districtMedianScore" /></fo:inline>
																		</fo:block>
																		<fo:block font-size="6pt">Number of Students: <xsl:call-template name="lookup-median-se-sc">
																			<xsl:with-param name="paramOrgId" select="reportDetails/data/districtId"/>
																			<xsl:with-param name="paramType" select="'SC'"/>
																			</xsl:call-template>
																		</fo:block>
																    </xsl:when>
  																	<xsl:otherwise>
  																		<!--  <xsl:call-template name="privacyStatementForNoScore"> </xsl:call-template> -->
  																		<fo:table table-layout="fixed" background-color="white" margin-left="49pt"  >
																			<fo:table-column column-width="80%" />						
																			<fo:table-body>
																				<fo:table-row>
																					<fo:table-cell text-align="start" wrap-option="wrap">
																						<fo:block font-size="6pt" text-align="center" >
																						<fo:inline font-size="6pt" >Data not shown to protect student privacy</fo:inline>
																						</fo:block>
																					</fo:table-cell>																					
																				</fo:table-row>
																			</fo:table-body>
																		</fo:table> 
  																		
																	</xsl:otherwise>
																</xsl:choose>
																</fo:block>
															</fo:table-cell>
															
															
															
															<!--State Median  -->
															<fo:table-cell text-align="center" margin-left="-21.5pt">
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
																
																<fo:block margin-left="-20.5pt">
																<fo:block font-size="7pt">State Median</fo:block>
																<xsl:variable name="stateMedianScore">
  																	<xsl:call-template name="lookup-median-se-sc">
																		<xsl:with-param name="paramOrgId" select="reportDetails/data/stateId"/>
																		<xsl:with-param name="paramType" select="'SCORE'"/>
																	</xsl:call-template>
																</xsl:variable>
																<xsl:choose>
 																	<xsl:when test="$stateMedianScore > 0">
 																		 <fo:block font-size="7pt" font-weight="bold">
																	   		 <fo:inline><xsl:copy-of select="$stateMedianScore" /></fo:inline>
																		</fo:block>
																		<fo:block font-size="6pt">Number of Students: <xsl:call-template name="lookup-median-se-sc">
																			<xsl:with-param name="paramOrgId" select="reportDetails/data/stateId"/>
																			<xsl:with-param name="paramType" select="'SC'"/>
																			</xsl:call-template>
																		</fo:block>
																    </xsl:when>
  																	<xsl:otherwise>
  																		<!--  <xsl:call-template name="privacyStatementForNoScore"> </xsl:call-template> -->
  																		<fo:table table-layout="fixed" background-color="white" margin-left="28pt"  >
																			<fo:table-column column-width="80%" />						
																			<fo:table-body>
																				<fo:table-row>
																					<fo:table-cell text-align="start" wrap-option="wrap">
																						<fo:block font-size="6pt" text-align="center" >
																						<fo:inline font-size="6pt" >Data not shown to protect student privacy</fo:inline>
																						</fo:block>
																					</fo:table-cell>																					
																				</fo:table-row>
																			</fo:table-body>
																		</fo:table> 
  																		
  																		
																	</xsl:otherwise>
																</xsl:choose>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
														<fo:table-row >
														
														
															<fo:table-cell number-columns-spanned="3" wrap-option="wrap"  margin-left="-65pt"  >
																<fo:block margin-top="6pt" font-family="Verdana" font-size="9pt" line-height="10.5pt" text-align="left" wrap-option="wrap" >
																	<xsl:choose>	
																		<xsl:when test="reportDetails/data/performanceRawscoreIncludeFlag = 'false'">
																			<xsl:choose>
																				<xsl:when test="reportDetails/data/contentAreaCode = 'ELA' and reportDetails/data/gradeCode != '10'" >
																					
																					<!-- <xsl:choose>
																						<xsl:when test="reportDetails/data/suppressMainScalescorePrfrmLevel = 'true'">
																							<fo:inline font-weight="bold">
																								<xsl:text></xsl:text>
																								<xsl:text>Your student did not complete the reading, writing and listening section</xsl:text>
																								
																							</fo:inline>
																						</xsl:when>
																						<xsl:otherwise>
																							 <fo:inline font-weight="bold">
																								<xsl:text>In the reading, writing, and listening section, </xsl:text>
																								<xsl:text>your student is performing at Level </xsl:text>
																								<xsl:value-of select="reportDetails/data/level"></xsl:value-of>
																								<xsl:text>&#46;</xsl:text>
																							</fo:inline> 
																						</xsl:otherwise>
																					</xsl:choose>
																					
																				<xsl:text> Questions in this section ask students to select the right answer and to sort, match, label, and order items.</xsl:text>
																				 --></xsl:when>
																				<xsl:otherwise>
																					<xsl:if test="reportDetails/data/contentAreaCode != 'ELA' and reportDetails/data/contentAreaCode != 'M' and reportDetails/data/contentAreaCode != 'Sci' and reportDetails/data/contentAreaCode != 'SS'">
																					   <fo:inline font-weight="bold">
																						<xsl:text>Your student is performing at Level </xsl:text>
																						<xsl:value-of select="reportDetails/data/level"></xsl:value-of>
																						<xsl:text>&#46;</xsl:text>
																					   </fo:inline>
																					</xsl:if> 
																				</xsl:otherwise>
																			</xsl:choose>
																		</xsl:when>
																		<xsl:otherwise>
																		 	<xsl:if test="reportDetails/data/contentAreaCode != 'ELA' and reportDetails/data/contentAreaCode != 'M' and reportDetails/data/contentAreaCode != 'Sci' and reportDetails/data/contentAreaCode != 'SS'">
																				<fo:inline font-weight="bold">
																					<xsl:text  font-family="Verdana">Your student is performing at Level </xsl:text>
																					<xsl:value-of select="reportDetails/data/level"></xsl:value-of>
																						<xsl:text>&#46;</xsl:text>
																					<!-- <xsl:text> Questions in this section ask students to select the right answer and to sort, match, label, and order items.</xsl:text> -->
																					<!-- <xsl:value-of select="reportDetails/data/level"></xsl:value-of>
																					<xsl:text>&#46;</xsl:text> -->
																				</fo:inline>
																			</xsl:if>
																			<!-- <fo:inline>
																					<xsl:text>Questions in this section ask students to select the right answer and to sort, match, label, and order items.</xsl:text>
																			</fo:inline> -->
																		</xsl:otherwise>
																	</xsl:choose>
																	
																	<fo:block margin-top="150pt">
																		<xsl:call-template name="standard_error"/>
																	</fo:block>																	
																	
																			<!-- <xsl:choose>
																				<xsl:when test="(reportDetails/data/contentAreaCode = 'ELA' or reportDetails/data/contentAreaCode = 'M') and (reportDetails/data/gradeCode = '10')">
																					 <fo:block margin-top="150pt" font-family="Verdana" font-size="9pt" line-height="10.5pt" text-align="left" wrap-option="wrap" >
																							<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">
																								<fo:block font-weight="bold" ><xsl:text>Standard error of measurement for this report:</xsl:text></fo:block>
																									<fo:block margin-top="2pt">							      
																											<xsl:text>Student — </xsl:text><fo:inline><xsl:if test="not(reportDetails/data/standardError)"><xsl:text>N/A</xsl:text></xsl:if>
																												<xsl:if test="reportDetails/data/standardError != ''"><xsl:value-of select="format-number(reportDetails/data/standardError, '0.0')"/></xsl:if>			      		
																														</fo:inline><xsl:text> | School — </xsl:text><fo:inline><xsl:call-template name="lookup-median-se-sc"><xsl:with-param name="paramOrgId" select="reportDetails/data/attendanceSchoolId"/><xsl:with-param name="paramType" select="'SE'"/></xsl:call-template></fo:inline><xsl:text> | District — </xsl:text><fo:inline><xsl:call-template name="lookup-median-se-sc"><xsl:with-param name="paramOrgId" select="reportDetails/data/districtId"/><xsl:with-param name="paramType" select="'SE'"/></xsl:call-template></fo:inline><xsl:text> | State — </xsl:text><fo:inline><xsl:call-template name="lookup-median-se-sc"><xsl:with-param name="paramOrgId" select="reportDetails/data/stateId"/><xsl:with-param name="paramType" select="'SE'"/></xsl:call-template></fo:inline>
																									</fo:block>
																									<fo:block>The standard error indicates how much a student’s score might vary if the student took many equivalent versions of the test (tests with different items but covering the same knowledge and skills).</fo:block>
																							</xsl:if>
																					</fo:block>
																		<		</xsl:when>																		
																			</xsl:choose> -->														
																</fo:block>
																
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell padding="0" text-align="left">
											<fo:block-container>
												<fo:block font-size="9pt"  >
												<xsl:choose>
													<xsl:when test="reportDetails/data/incompleteStatus ='true' and reportDetails/data/suppressMainScalescorePrfrmLevel = 'true'">
														  <!-- <fo:inline font-weight="bold" font-family="Verdana" >
															<xsl:text>Your student did not take this portion of the test or did not complete enough of the test to receive a score.</xsl:text>
														  </fo:inline> -->	
												    </xsl:when>
												   	<xsl:otherwise>
												   		<fo:block text-align="left" font-size="9pt">
												   		<xsl:call-template  name="lookup-level-description-by-descriptiontype">
															<xsl:with-param name="descriptionType" select="'Main'"></xsl:with-param>
															<xsl:with-param name="dataLevel" select="reportDetails/data/level"></xsl:with-param>				
														</xsl:call-template>
														</fo:block>
												   	</xsl:otherwise>
												</xsl:choose>
												</fo:block>
											</fo:block-container>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
						</fo:block>
						
						<!-- On Demand Writing Task Score -->
						<fo:block>
							<fo:table table-layout="fixed">
								<fo:table-column column-width="proportional-column-width(12)" />
				        		<fo:table-column column-width="proportional-column-width(88)" />
				        	<fo:table-body>	
					        	<fo:table-row>	
									<fo:table-cell>									
									   <fo:block-container>
									   <fo:block margin-top="10pt">
											 <fo:inline>	
											 	<xsl:if test="reportDetails/data/suppressMdptScore ='true' and reportDetails/data/mdptScorableFlag = 'false' and reportDetails/data/scCodeExist ='false'">
													<xsl:call-template name="responseNotScoredPath"/>
												</xsl:if>			    
											</fo:inline>
										</fo:block>
										</fo:block-container>	
									</fo:table-cell>
									<!-- MDPT Section will not be there for ELA 2017 -->
									<xsl:if test="reportDetails/data/contentAreaCode != 'ELA'">
										<fo:table-cell>		
										     <fo:block-container>
										       <fo:block text-align="center" margin-top="10pt" margin-left="-64pt">
												<fo:inline    font-weight="bold" font-family="Verdana" font-size="12pt">
												<xsl:if test="(reportDetails/data/contentAreaCode = 'ELA') and (reportDetails/data/gradeCode != '10')">
										       		<xsl:text>On-Demand Writing Task Score</xsl:text>
										       	</xsl:if>
										       	</fo:inline>
										       	</fo:block>
										       </fo:block-container>
   										</fo:table-cell> 
									</xsl:if>     	
								</fo:table-row>       	
							</fo:table-body>       	
					       	</fo:table>
					    </fo:block>
					    
					    <!-- MDPT Section will not be there for ELA 2017 -->
					    <xsl:if test="reportDetails/data/contentAreaCode != 'ELA'">
					    	<fo:block margin-bottom="10mm">
					       		<fo:table table-layout="fixed">
					        		<fo:table-column column-width="proportional-column-width(52)" />
					        		<fo:table-column column-width="proportional-column-width(48)" />
			        				<fo:table-body>
			         					<fo:table-row>
			          						<fo:table-cell>
			           							<fo:block-container height="27mm">
													<xsl:choose>
													   <xsl:when test="(reportDetails/data/contentAreaCode = 'ELA') and (reportDetails/data/gradeCode != '10') and (reportDetails/data/performanceRawscoreIncludeFlag = 'false')">
													   		<fo:block  margin-left="3.2mm">
													   			<xsl:call-template name="onDemandWritingTaskScoreLevel"/>
													   		</fo:block>                   
												            <fo:block font-family="Verdana" font-size="9pt" line-height="10.5pt" text-align="left"  margin-right="3mm">
													            <xsl:choose>
													                <xsl:when test="reportDetails/data/suppressMdptScore = 'true'">
													                	<xsl:choose>
													                		<xsl:when test="reportDetails/data/suppressMdptScore ='true' and reportDetails/data/mdptScorableFlag = 'false' and reportDetails/data/scCodeExist ='false'">
																				<fo:inline font-family="Verdana" font-size="9pt" text-align="left" font-weight="bold">
																	            		<xsl:text>Your student's writing task response did not meet the requirements and could not be scored.</xsl:text>
																	            </fo:inline>			
																			</xsl:when>
																			<xsl:otherwise>
																				<fo:inline font-family="Verdana" font-size="9pt" text-align="left" font-weight="bold">
																        			<xsl:text>Your student did not take this portion of the test.</xsl:text>
																        		</fo:inline>
																			</xsl:otherwise>
													                	</xsl:choose>
															        	
																        	<xsl:text> On-demand writing tasks ask students to engage with texts and other resources and then compose a related writing sample.</xsl:text>
													              	</xsl:when>
													              	<xsl:otherwise>
															              <fo:block>
																            	<fo:inline font-family="Verdana" font-size="9pt" text-align="left" font-weight="bold">
																            		<xsl:text>Your student’s on-demand writing task score is a </xsl:text>
																            		<xsl:value-of select="reportDetails/data/mdptLevel"/>	
																            		<xsl:text>&#46;&#32;</xsl:text>														            		
																            	</fo:inline>															            	
																            	<xsl:text> On-demand writing tasks ask students to engage with texts and other resources and then compose a related writing sample.</xsl:text>
															              </fo:block>	
																            <!-- </xsl:otherwise>
															             </xsl:choose> -->	
													              	</xsl:otherwise>
													           	</xsl:choose>
												           	</fo:block>
													   </xsl:when>												   
													   <xsl:otherwise>
													   		<fo:block white-space-treatment="preserve"> </fo:block>
												       </xsl:otherwise>
													</xsl:choose>
										       	</fo:block-container>
			          						</fo:table-cell>
			          						<fo:table-cell>
			           							<fo:block-container height="27mm">
													<xsl:choose>
													   <xsl:when test="(reportDetails/data/contentAreaCode = 'ELA') and (reportDetails/data/gradeCode != '10') and (reportDetails/data/performanceRawscoreIncludeFlag = 'false') ">
													   		<fo:block-container>
																<fo:block margin-top="5pt" font-size="9pt">
																	<xsl:if test=" reportDetails/data/suppressMdptScore != 'true'">
																		<xsl:call-template name="lookup-level-description-by-descriptiontype-for-MDPT">
																			<xsl:with-param name="descriptionType" select="'MDPT'"></xsl:with-param>
																			<xsl:with-param name="dataLevel" select="reportDetails/data/mdptLevel"></xsl:with-param>
																		</xsl:call-template>
																	</xsl:if>
																</fo:block>
															</fo:block-container>
													   </xsl:when>												   
													   <xsl:otherwise>
													   		<fo:block white-space-treatment="preserve"> </fo:block>
													   </xsl:otherwise>
													</xsl:choose>
										       	</fo:block-container>
			          						</fo:table-cell>
			         					</fo:table-row>
					        		</fo:table-body>
					       		</fo:table>
					    	</fo:block>	
				    	</xsl:if>					
					</fo:block>
					<!-- 2nd page -->
					<fo:block break-before="page" font-family="Verdana" >
						<fo:block space-before="5pt" space-after="5pt">
							<fo:leader leader-pattern="rule"  leader-length="100%" rule-style="solid"/>
						</fo:block>
						<xsl:choose>
							<xsl:when test="reportDetails/data/contentAreaCode != 'SS'">
								<xsl:choose>
									<xsl:when test="count(reportDetails/data/testLevelSubscoreBuckets) &gt; 0  ">
										<fo:block-container height="185mm">
											<fo:block font-weight="bold">
												<fo:table table-layout="fixed">
													<fo:table-column column-width="47%" />
													<fo:table-column column-width="2%" />
													<fo:table-column column-width="10%" />
													<fo:table-column column-width="2%" />
													<fo:table-column column-width="10%" />
													<fo:table-column column-width="2%" />
													<fo:table-column column-width="10%" />
													<fo:table-column column-width="2%" />
													<fo:table-column column-width="15%" />
													<fo:table-body>
														<fo:table-row>
															<fo:table-cell text-align="left">
																<fo:block font-size="11pt" text-align="left" margin-top="-3pt"><xsl:text>Your Student’s Performance</xsl:text></fo:block>
															</fo:table-cell>
															<fo:table-cell height="5mm" display-align="center">
																<fo:block font-size="8pt" text-align="right" display-align="center" margin-top="-8pt">
																	<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
																		inline-progression-dimension.maximum="5mm" block-progression-dimension.maximum="5mm">
					 													<xsl:attribute name="src"><xsl:value-of select="reportDetails/iconExceedsPath"/></xsl:attribute>	
																	</fo:external-graphic>
																</fo:block>
															</fo:table-cell>
															<fo:table-cell height="5mm" display-align="center">
																<fo:block font-size="8pt" text-align="left" display-align="center" margin-top="-8pt" margin-left="3pt">
																	<xsl:text>Exceeds</xsl:text>
																</fo:block>
															</fo:table-cell>
															<fo:table-cell height="5mm" display-align="center">
																<fo:block font-size="8pt" text-align="right" display-align="center" margin-top="-8pt">
																	<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
																		inline-progression-dimension.maximum="5mm" block-progression-dimension.maximum="5mm">
					 													<xsl:attribute name="src"><xsl:value-of select="reportDetails/iconMeetsPath"/></xsl:attribute>	
																	</fo:external-graphic>
																</fo:block>
															</fo:table-cell>
															<fo:table-cell height="5mm" display-align="center">
																<fo:block font-size="8pt" text-align="left" display-align="center" margin-top="-8pt" margin-left="3pt">
																	<xsl:text>Meets</xsl:text>
																</fo:block>
															</fo:table-cell>
															<fo:table-cell height="5mm" display-align="center">
																<fo:block font-size="8pt" text-align="right" display-align="center" margin-top="-8pt">
																	<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
																		inline-progression-dimension.maximum="5mm" block-progression-dimension.maximum="5mm">
					 													<xsl:attribute name="src"><xsl:value-of select="reportDetails/iconBelowPath"/></xsl:attribute>	
																	</fo:external-graphic>
																</fo:block>
															</fo:table-cell>
															<fo:table-cell height="5mm" display-align="center">
																<fo:block font-size="8pt" text-align="left" display-align="center" margin-top="-8pt" margin-left="3pt">
																	<xsl:text>Below</xsl:text>
																</fo:block>
															</fo:table-cell>
															<fo:table-cell height="5mm" display-align="center">
																<fo:block font-size="8pt" text-align="right" display-align="center" margin-top="-8pt">
																	<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
																		inline-progression-dimension.maximum="5mm" block-progression-dimension.maximum="5mm">
					 													<xsl:attribute name="src"><xsl:value-of select="reportDetails/iconInsufficientPath"/></xsl:attribute>
																	</fo:external-graphic>
																</fo:block>
															</fo:table-cell>
															<fo:table-cell height="5mm" display-align="center">
																<fo:block font-size="8pt" text-align="left" display-align="center" margin-top="-8pt" margin-left="3pt">
																	<xsl:text>Insufficient Data</xsl:text>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:block>
											<fo:block margin-top="3pt">
												<fo:table>
													<fo:table-body>
														<fo:table-row>
															<fo:table-cell>
																<fo:block>
																	<xsl:call-template name="iTerateSubScoreDescriptions">
																		<xsl:with-param name="subScoreDataLevel" select="reportDetails/data/level"></xsl:with-param>
																		<xsl:with-param name="subScoreIconExceedsPath" select="reportDetails/iconExceedsPath"></xsl:with-param>
																		<xsl:with-param name="subScoreIconMeetsPath" select="reportDetails/iconMeetsPath"></xsl:with-param>
																		<xsl:with-param name="subScoreIconBelowPath" select="reportDetails/iconBelowPath"></xsl:with-param>
																		<xsl:with-param name="subScoreIconInsufficientPath" select="reportDetails/iconInsufficientPath"></xsl:with-param>
																	</xsl:call-template>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:block>
										</fo:block-container>
									</xsl:when>
									<xsl:otherwise>
										<fo:block-container height="185mm">
											<fo:block font-size="10pt" font-family="Verdana" text-align="left" line-height="10pt" margin-top="15pt">
												 <xsl:text>Section is not applicable for this subject.</xsl:text>
											</fo:block>
										</fo:block-container>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:when>
							<xsl:otherwise>
								<fo:block-container height="185mm">
									<fo:block font-size="10pt" font-family="Verdana" text-align="left" line-height="10pt" margin-top="15pt">
										 <xsl:text>Section is not applicable for this subject.</xsl:text> 
									</fo:block>
								</fo:block-container>
							</xsl:otherwise>
						</xsl:choose>
												
						<fo:block margin-top="25pt">
						<!-- 	<xsl:choose>
								<xsl:when test="(reportDetails/data/contentAreaCode = 'ELA' or reportDetails/data/contentAreaCode = 'M') and (reportDetails/data/gradeCode = '10')">
									 -->
									<xsl:call-template name="additionalResources" >
										<xsl:with-param name="reportType" select="'student'"/>
									</xsl:call-template>
								<!-- </xsl:when>						
								 <xsl:otherwise>
									<fo:block margin-top="48pt">
										<xsl:call-template name="standard_error"/>
									</fo:block>
										<xsl:call-template name="additionalResources" >
											<xsl:with-param name="reportType" select="'student'"/>
										</xsl:call-template>
								</xsl:otherwise> 					
						 	</xsl:choose>   -->
						</fo:block>
						
					</fo:block>
				</fo:flow>
		
		</xsl:otherwise>
	</xsl:choose>
				
				
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	
	<xsl:template name="standard_error" >
		<fo:block font-family="Verdana" font-size="8pt" space-before="5pt" wrap-option="wrap">
				<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">
				            	<fo:block font-weight="bold" ><xsl:text>Standard error of measurement for this report:</xsl:text></fo:block>
								    	<fo:block margin-top="2pt">							      
									      		<xsl:text>Student — </xsl:text><fo:inline><xsl:if test="not(reportDetails/data/standardError)"><xsl:text>N/A</xsl:text></xsl:if>
										    	<xsl:if test="reportDetails/data/standardError != ''"><xsl:value-of select="format-number(reportDetails/data/standardError, '0.0')"/></xsl:if>			      		
									      		</fo:inline><xsl:text> | School — </xsl:text><fo:inline><xsl:call-template name="lookup-median-se-sc"><xsl:with-param name="paramOrgId" select="reportDetails/data/attendanceSchoolId"/><xsl:with-param name="paramType" select="'SE'"/></xsl:call-template></fo:inline><xsl:text> | District — </xsl:text><fo:inline><xsl:call-template name="lookup-median-se-sc"><xsl:with-param name="paramOrgId" select="reportDetails/data/districtId"/><xsl:with-param name="paramType" select="'SE'"/></xsl:call-template></fo:inline><xsl:text> | State — </xsl:text><fo:inline><xsl:call-template name="lookup-median-se-sc"><xsl:with-param name="paramOrgId" select="reportDetails/data/stateId"/><xsl:with-param name="paramType" select="'SE'"/></xsl:call-template></fo:inline>
										  	</fo:block>
									      	<fo:block>The standard error indicates how much a student’s score might vary if the student took many equivalent versions of the test</fo:block>
							    <fo:block>(tests with different items but covering the same knowledge and skills).</fo:block>
				</xsl:if>
		 </fo:block>	
	</xsl:template>
	
	
	<xsl:template name="report-page1-header">
		<fo:table table-layout="fixed">
			<fo:table-column column-width="proportional-column-width(50)" />
			<fo:table-column column-width="proportional-column-width(22)" />
			<fo:table-column column-width="proportional-column-width(28)" />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell>
						<fo:block font-family="Verdana" font-weight="bold" font-size="11pt" line-height="13.5pt" text-align="left" wrap-option="no-wrap">STUDENT REPORT: <xsl:call-template name="lastName-Header"/>, <xsl:call-template name="firstName-Header"/></fo:block>
						<fo:block-container font-family="Verdana" font-size="9pt" line-height="12.5pt">
							<xsl:call-template name="headerGradeSubjectName"/>
							<xsl:call-template name="headerSchoolName"/>
							<xsl:call-template name="headerDistrictName"/>
											
						</fo:block-container>
					</fo:table-cell>
					<fo:table-cell  text-align="center">
						<fo:block white-space-treatment="preserve"> </fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="end" >
						<fo:block-container display-align="center" text-align="center" color="#25408f" font-family="Verdana" font-weight="bold" font-size="9pt" margin-left="3.7mm">
							<xsl:call-template name="headerSchoolYear"/>
							<xsl:call-template name="headerLogo"/>
						</fo:block-container>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>
</xsl:stylesheet>