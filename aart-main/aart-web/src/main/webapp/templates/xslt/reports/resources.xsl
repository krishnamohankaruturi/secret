<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo" extension-element-prefixes="saxon">
	<xsl:template name="additionalResources"> 
		<xsl:param name="reportType"/>
		<xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'">
		<fo:block margin-top="3mm" text-align="left" font-weight="bold" font-family="Verdana" font-size="8pt">Additional Resources</fo:block>
		</xsl:if>
		
	  <xsl:choose>
		<xsl:when test="(reportDetails/data/contentAreaCode = 'ELA' or reportDetails/data/contentAreaCode = 'M') and (reportDetails/data/gradeCode = '10')">
			<fo:block-container	absolute-position="absolute" top="181mm">
	       		 <fo:block margin-top="2pt">
					<xsl:call-template name="resourceBlock">
						<xsl:with-param name="reportType" select="$reportType"/>
					</xsl:call-template>						
				 </fo:block>
			</fo:block-container>
		</xsl:when>
		<xsl:otherwise>
			<fo:block-container	absolute-position="absolute" top="210mm">
				<fo:block margin-top="2pt">
					<xsl:call-template name="resourceBlock">
						<xsl:with-param name="reportType" select="$reportType"/>
					</xsl:call-template>
				</fo:block>
			</fo:block-container>
		</xsl:otherwise>
	  </xsl:choose>
	</xsl:template>
	
	<xsl:template name ="resourceBlock" >
	   <xsl:param name="reportType"/>
		<xsl:choose>
					<xsl:when test="$reportType = 'student'">
						<xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'">
							<fo:block>For information on the Alaska Standards, visit http://education.alaska.gov/standards.</fo:block>
							<fo:block>For information on the Alaska Measures of Progress, visit http://education.alaska.gov/akassessments.</fo:block>
							<fo:block>See the 2016 Interpretive Guide at http://amp.cete.us/AMPresources.</fo:block>
						</xsl:if>
						<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">
							<xsl:choose>
								<xsl:when test="(reportDetails/data/contentAreaCode = 'ELA' or reportDetails/data/contentAreaCode = 'M')">
									<fo:block-container>	
										<fo:block  text-align="left" font-weight="bold" font-family="Verdana" font-size="8pt">Additional Resources</fo:block>
										<fo:block font-size="8pt" margin-bottom="4pt" >
											<xsl:call-template name="AdditionalResourceContent"/>
										</fo:block>
									</fo:block-container>
								</xsl:when>
							</xsl:choose>								
							<fo:table table-layout="fixed" font-family="Verdana" font-size="8pt">
								<fo:table-column column-width="proportional-column-width(80)" />
								<fo:table-column column-width="proportional-column-width(20)" />
								<fo:table-body>
								<fo:table-row>
									<fo:table-cell text-align="left" >
										<fo:block font-size="8pt" >
											<fo:block>
												<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">
												<!-- <fo:block  text-align="left" font-weight="bold" font-family="Verdana" font-size="8pt">Additional Resources</fo:block> -->
												<xsl:choose>
													<xsl:when test="(reportDetails/data/contentAreaCode = 'ELA' or reportDetails/data/contentAreaCode = 'M')">
														<!-- <xsl:call-template name="ACTSectionTable"/> 
														<fo:block>
															<xsl:call-template name="AdditionalResourceContent"/>
												  		</fo:block>-->
												  		<xsl:choose>
															<xsl:when test="reportDetails/data/gradeCode = '10' ">
												  					<xsl:call-template name="ACTSectionTable"/>
												  			</xsl:when>												  			
												  		</xsl:choose>												  		
													</xsl:when>	
													<xsl:otherwise>
														<fo:block-container margin-top = "55pt" >
															<fo:block  text-align="left" font-weight="bold" font-family="Verdana" font-size="8pt">Additional Resources</fo:block>
													  		<fo:block >
																<xsl:call-template name="AdditionalResourceContent"/>
													  		</fo:block>	
												  		</fo:block-container>												  			
												  	</xsl:otherwise>												
												</xsl:choose>													
												</xsl:if>
											</fo:block>
										</fo:block>
									</fo:table-cell>
								
									<fo:table-cell text-align="right" margin-bottom="10mm" >
										<xsl:choose>
											<xsl:when test="(reportDetails/data/contentAreaCode = 'ELA' or reportDetails/data/contentAreaCode = 'M') and (reportDetails/data/gradeCode = '10')">
												<fo:block-container text-align="right" absolute-position="absolute" top="22.5mm">
													<xsl:call-template name="footerLogoSection"/>
												</fo:block-container>
											</xsl:when>
											<xsl:otherwise>
												<xsl:choose>
													<xsl:when test="(reportDetails/data/contentAreaCode = 'ELA' or reportDetails/data/contentAreaCode = 'M')">
														<fo:block-container text-align="right" absolute-position="absolute" top="-6mm">
															<xsl:call-template name="footerLogoSection"/>
														</fo:block-container>
													</xsl:when>
													<xsl:otherwise>
														<fo:block-container text-align="right" absolute-position="absolute" top="5.5mm" >
															<xsl:call-template name="footerLogoSection"/>
														</fo:block-container>												
													</xsl:otherwise>
												</xsl:choose>
																					 
											</xsl:otherwise>
										</xsl:choose>
									 </fo:table-cell>								
								</fo:table-row>
								</fo:table-body>
							</fo:table>
							
							<fo:block margin-top="6pt" line-height="10pt" >
								<xsl:call-template name="MetametricsMeasuresSection"/>
							</fo:block>
							
						</xsl:if>
					</xsl:when>
					<xsl:otherwise>
						<xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'">
							<fo:block>See the 2016 Interpretive Guide at http://amp.cete.us/AMPresources.</fo:block>
						</xsl:if>
						<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">
							<fo:block>For the 2016 Interpretive Guide for score reports, visit http://kap.cete.us/ig.</fo:block>
						</xsl:if>
					</xsl:otherwise>
				</xsl:choose>
	</xsl:template>
	
	<xsl:template name ="AdditionalResourceContent" >
													<fo:block>
														<xsl:text>For sample test questions, information about the Kansas College and Career Ready Standards, and information 
														about the Kansas Assessment Program, visit </xsl:text> 
														<fo:inline font-weight="bold"><xsl:text>ksassessments.org</xsl:text></fo:inline>
														<xsl:text>.</xsl:text><xsl:text> For score report information, visit </xsl:text>
														<fo:inline font-weight="bold"><xsl:text>ksassessments.org/scorereports</xsl:text></fo:inline>
														<xsl:text>.</xsl:text>														
													</fo:block>		
	
													<!-- <fo:block>
													<xsl:text>For sample test questions, go to </xsl:text> 
														<fo:inline font-weight="bold"><xsl:text>ksassessments.org/interactive-demos</xsl:text></fo:inline>
														<xsl:text>.</xsl:text>
													</fo:block>
													<fo:block>
														<xsl:text>For information on the Kansas College and Career Ready Standards, visit </xsl:text>
														<fo:inline font-weight="bold"><xsl:text>ksde.org</xsl:text></fo:inline>
														<xsl:text>.</xsl:text>
													</fo:block>
													<fo:block>
														<xsl:text>To learn about the Kansas Assessment Program, go to </xsl:text>
														<fo:inline font-weight="bold"><xsl:text>ksassessments.org</xsl:text></fo:inline>
														<xsl:text>.</xsl:text>
													</fo:block>
													<fo:block>
														<xsl:choose>
															<xsl:when test="(reportDetails/data/contentAreaCode != 'ELA' and reportDetails/data/contentAreaCode != 'M' and reportDetails/data/contentAreaCode != 'Sci')">
																<xsl:text>To discover more about this score report, see the 2016 Parent Guide at </xsl:text> 
																<fo:inline font-weight="bold"><xsl:text>kap.cete.us/pg</xsl:text></fo:inline>
																<xsl:text>.</xsl:text>
															</xsl:when>
															<xsl:otherwise>
																<xsl:text>To discover more about this score report, see the 2017 Parent Guide at </xsl:text><fo:inline font-weight="bold"><xsl:text>ksassessments.org/pg</xsl:text></fo:inline><xsl:text> and in Spanish at </xsl:text><fo:inline font-weight="bold"><xsl:text>ksassessments.org/pg-esp</xsl:text></fo:inline>
																<xsl:text>.</xsl:text>
															</xsl:otherwise>
														 </xsl:choose>																	
													</fo:block>
													<xsl:if test="(reportDetails/data/contentAreaCode = 'ELA' or reportDetails/data/contentAreaCode = 'M') and (reportDetails/data/gradeCode = '10')">
																		<fo:block>
																			<xsl:text>To get an idea of how your high school student may perform on the ACT based on this KAP score, go to </xsl:text>
																			<fo:inline font-weight="bold">
																				<xsl:text>ksassessments.org/act</xsl:text>
																			</fo:inline>
																			<xsl:text>.</xsl:text>
																		</fo:block>
													</xsl:if>-->
	</xsl:template>
	
	<xsl:template name ="footerLogoSection" >
			<fo:block width="100%" height="100%">
					<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
								inline-progression-dimension.maximum="100%" block-progression-dimension.maximum="100%" height="2.88cm"
									width="3.88cm">
							<xsl:attribute name="src"><xsl:value-of select="reportDetails/footerLogoPath"/></xsl:attribute>
					</fo:external-graphic> 
			</fo:block>
	</xsl:template>
	
	<xsl:template name ="ACTSectionTable" >
			<xsl:if test="reportDetails/data/contentAreaCode = 'ELA'">
													<fo:table border-top-style="solid" border-bottom-style="solid"
													border-left-style="solid" border-right-style="solid"
													display-align="center" keep-together.within-column="always" margin-top="2pt" >
													<fo:table-column column-width="61mm" />
													<fo:table-column column-width="37mm" />
													<fo:table-column column-width="37mm" />
													<fo:table-column column-width="37mm" />	
													<fo:table-body>	
															<fo:table-row>	
																<fo:table-cell background-color="#DFE0E1" 
																 padding-left="5pt" padding-right="2pt"
																padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
																border-bottom-style="solid" border-left-style="solid"
																border-right-style="solid" keep-together.within-column="always"  number-rows-spanned="5" >
																	<fo:block font-size="8pt" font-family="Verdana" font-weight="bold">
																		<xsl:text>ACT Scoring</xsl:text>
																	</fo:block>
																	<fo:block font-size="8pt" font-family="Verdana" >																		
																		<xsl:text>To get an idea of how your high school student may perform on the ACT based on this KAP score, refer to this chart. For more information, go to </xsl:text>
																		<fo:inline font-weight="bold"><xsl:text>ksassessments.org/act</xsl:text></fo:inline>
																		<xsl:text>.</xsl:text>
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell background-color="#D8F0FC"
																 padding-left="3pt" padding-right="2pt"
																padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
																border-bottom-style="solid" border-left-style="solid"
																border-right-style="solid">
																<fo:block font-size="8pt" font-family="Verdana">
																	<xsl:text>Student’s actual KAP grade 10 ELA score</xsl:text>
																</fo:block>
																</fo:table-cell>
																<fo:table-cell background-color="#D8F0FC"
																	 padding-left="2pt" padding-right="2pt"
																	padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
																	border-bottom-style="solid" border-left-style="solid"
																	border-right-style="solid" text-align="center">
																	<fo:block font-size="8pt" font-family="Verdana">
																		<xsl:text>Student’s projected ACT Reading score</xsl:text>
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell background-color="#D8F0FC"
																	 padding-left="2pt" padding-right="2pt"
																	padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
																	border-bottom-style="solid" border-left-style="solid"
																	border-right-style="solid" text-align="center">
																	<fo:block font-size="8pt" font-family="Verdana">
																		<xsl:text>Student’s projected ACT English score</xsl:text>
																	</fo:block>
																</fo:table-cell>
															
															</fo:table-row>
																													
															<fo:table-row>																
																<fo:table-cell 
																	padding-left="3pt" padding-right="2pt" padding-top="2pt"
																	padding-bottom="2pt" border-top-style="solid"
																	border-bottom-style="solid" border-left-style="solid">
																	<fo:block font-size="8pt" font-family="Verdana">
																		Level 1: 220–268
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell 
																	padding-left="2pt" padding-right="2pt" padding-top="2pt"
																	padding-bottom="2pt" border-top-style="solid"
																	border-bottom-style="solid" border-left-style="solid" text-align="center">
																	<fo:block font-size="8pt" font-family="Verdana">
																		1–17
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell 
																	padding-left="2pt" padding-right="2pt" padding-top="2pt"
																	padding-bottom="2pt" border-top-style="solid"
																	border-bottom-style="solid" border-left-style="solid" text-align="center">
																	<fo:block font-size="8pt" font-family="Verdana">
																		1–15
																	</fo:block>
																</fo:table-cell>																					
															</fo:table-row>
															<fo:table-row>																  
																<fo:table-cell 
																	padding-left="3pt" padding-right="2pt" padding-top="2pt"
																	padding-bottom="2pt" border-top-style="solid"
																	border-bottom-style="solid" border-left-style="solid">
																	<fo:block font-size="8pt" font-family="Verdana">
																		Level 2: 269–299
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell 
																	padding-left="2pt" padding-right="2pt" padding-top="2pt"
																	padding-bottom="2pt" border-top-style="solid"
																	border-bottom-style="solid" border-left-style="solid" text-align="center">
																	<fo:block font-size="8pt" font-family="Verdana">
																		17–23
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell 
																	padding-left="2pt" padding-right="2pt" padding-top="2pt"
																	padding-bottom="2pt" border-top-style="solid"
																	border-bottom-style="solid" border-left-style="solid" text-align="center">
																	<fo:block font-size="8pt" font-family="Verdana">
																		15–22
																	</fo:block>
																</fo:table-cell>																					
															</fo:table-row>
															<fo:table-row>																  
																<fo:table-cell 
																	padding-left="3pt" padding-right="2pt" padding-top="2pt"
																	padding-bottom="2pt" border-top-style="solid"
																	border-bottom-style="solid" border-left-style="solid">
																	<fo:block font-size="8pt" font-family="Verdana">
																		Level 3: 300–333
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell 
																	padding-left="2pt" padding-right="2pt" padding-top="2pt"
																	padding-bottom="2pt" border-top-style="solid"
																	border-bottom-style="solid" border-left-style="solid" text-align="center">
																	<fo:block font-size="8pt" font-family="Verdana">
																		23–30
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell 
																	padding-left="2pt" padding-right="2pt" padding-top="2pt"
																	padding-bottom="2pt" border-top-style="solid"
																	border-bottom-style="solid" border-left-style="solid" text-align="center">
																	<fo:block font-size="8pt" font-family="Verdana">
																		22–30
																	</fo:block>
																</fo:table-cell>																					
															</fo:table-row>
															<fo:table-row>																  
																<fo:table-cell 
																	padding-left="3pt" padding-right="2pt" padding-top="2pt"
																	padding-bottom="2pt" border-top-style="solid"
																	border-bottom-style="solid" border-left-style="solid">
																	<fo:block font-size="8pt" font-family="Verdana">
																		Level 4: 334–380
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell 
																	padding-left="2pt" padding-right="2pt" padding-top="2pt"
																	padding-bottom="2pt" border-top-style="solid"
																	border-bottom-style="solid" border-left-style="solid" text-align="center">
																	<fo:block font-size="8pt" font-family="Verdana">
																		30–36
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell 
																	padding-left="2pt" padding-right="2pt" padding-top="2pt"
																	padding-bottom="2pt" border-top-style="solid"
																	border-bottom-style="solid" border-left-style="solid" text-align="center">
																	<fo:block font-size="8pt" font-family="Verdana">
																		30–36
																	</fo:block>
																</fo:table-cell>																					
															</fo:table-row>
													</fo:table-body>
												</fo:table>	
												</xsl:if>
													
													
												<xsl:if test="reportDetails/data/contentAreaCode = 'M'">
													<fo:table border-top-style="solid" border-bottom-style="solid"
													border-left-style="solid" border-right-style="solid"
													display-align="center" keep-together.within-column="always" margin-top="2pt" >
													<fo:table-column column-width="61mm" />
													<fo:table-column column-width="37mm" />
													<fo:table-column column-width="37mm" />
													<fo:table-body>	
															<fo:table-row>	
																<fo:table-cell background-color="#DFE0E1" 
																 padding-left="5pt" padding-right="2pt"
																padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
																border-bottom-style="solid" border-left-style="solid"
																border-right-style="solid" keep-together.within-column="always"  number-rows-spanned="5" >
																	<fo:block font-size="8pt" font-family="Verdana" font-weight="bold">
																		<xsl:text>ACT Scoring</xsl:text>
																	</fo:block>
																	<fo:block font-size="8pt" font-family="Verdana" >																		
																		<xsl:text>To get an idea of how your high school student may perform on the ACT based on this KAP score, refer to this chart. For more information, go to </xsl:text>
																		<fo:inline font-weight="bold"><xsl:text>ksassessments.org/act</xsl:text></fo:inline>
																		<xsl:text>.</xsl:text>
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell background-color="#D8F0FC"
																 padding-left="3pt" padding-right="2pt"
																padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
																border-bottom-style="solid" border-left-style="solid"
																border-right-style="solid">
																<fo:block font-size="8pt" font-family="Verdana">
																	<xsl:text>Student’s actual KAP grade 10 math score</xsl:text>
																</fo:block>
																</fo:table-cell>
																<fo:table-cell background-color="#D8F0FC"
																	 padding-left="2pt" padding-right="2pt"
																	padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
																	border-bottom-style="solid" border-left-style="solid"
																	border-right-style="solid" text-align="center">
																	<fo:block font-size="8pt" font-family="Verdana">
																		<xsl:text>Student’s projected ACT math score</xsl:text>
																	</fo:block>
																</fo:table-cell>														
															</fo:table-row>
																													
															<fo:table-row>																
																<fo:table-cell 
																	padding-left="3pt" padding-right="2pt" padding-top="2pt"
																	padding-bottom="2pt" border-top-style="solid"
																	border-bottom-style="solid" border-left-style="solid">
																	<fo:block font-size="8pt" font-family="Verdana">
																		Level 1: 220–274
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell 
																	padding-left="2pt" padding-right="2pt" padding-top="2pt"
																	padding-bottom="2pt" border-top-style="solid"
																	border-bottom-style="solid" border-left-style="solid" text-align="center">
																	<fo:block font-size="8pt" font-family="Verdana">
																		1–17
																	</fo:block>
																</fo:table-cell>																																					
															</fo:table-row>
															<fo:table-row>																  
																<fo:table-cell 
																	padding-left="3pt" padding-right="2pt" padding-top="2pt"
																	padding-bottom="2pt" border-top-style="solid"
																	border-bottom-style="solid" border-left-style="solid">
																	<fo:block font-size="8pt" font-family="Verdana">
																		Level 2: 275–299
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell 
																	padding-left="2pt" padding-right="2pt" padding-top="2pt"
																	padding-bottom="2pt" border-top-style="solid"
																	border-bottom-style="solid" border-left-style="solid" text-align="center">
																	<fo:block font-size="8pt" font-family="Verdana">
																		17–22
																	</fo:block>
																</fo:table-cell>																																					
															</fo:table-row>
															<fo:table-row>																  
																<fo:table-cell 
																	padding-left="3pt" padding-right="2pt" padding-top="2pt"
																	padding-bottom="2pt" border-top-style="solid"
																	border-bottom-style="solid" border-left-style="solid">
																	<fo:block font-size="8pt" font-family="Verdana">
																		Level 3: 300–332
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell 
																	padding-left="2pt" padding-right="2pt" padding-top="2pt"
																	padding-bottom="2pt" border-top-style="solid"
																	border-bottom-style="solid" border-left-style="solid" text-align="center">
																	<fo:block font-size="8pt" font-family="Verdana">
																		22–28
																	</fo:block>
																</fo:table-cell>																																					
															</fo:table-row>
															<fo:table-row>																  
																<fo:table-cell 
																	padding-left="3pt" padding-right="2pt" padding-top="2pt"
																	padding-bottom="2pt" border-top-style="solid"
																	border-bottom-style="solid" border-left-style="solid">
																	<fo:block font-size="8pt" font-family="Verdana">
																		Level 4: 333–380
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell 
																	padding-left="2pt" padding-right="2pt" padding-top="2pt"
																	padding-bottom="2pt" border-top-style="solid"
																	border-bottom-style="solid" border-left-style="solid" text-align="center">
																	<fo:block font-size="8pt" font-family="Verdana">
																		28–36
																	</fo:block>
																</fo:table-cell>																																					
															</fo:table-row>
													</fo:table-body>
												</fo:table>	
					</xsl:if>
	</xsl:template>
	
	
	<xsl:template name="summaryadditionalResources">	
	<fo:block margin-top="5pt" space-after="5pt">
		<xsl:if test="reportDetails/data/contentAreaCode != 'SS'">
							<fo:leader leader-pattern="rule" leader-length="100%"
								rule-style="solid" />
		</xsl:if>
	<fo:block text-align="left" font-weight="bold" font-family="Verdana" font-size="8pt">Additional Resources</fo:block>
	<fo:block>
		<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">
									<fo:table table-layout="fixed" font-family="Verdana"
										font-size="8pt">
										<fo:table-column column-width="proportional-column-width(80)" />
										<fo:table-column column-width="proportional-column-width(20)" />
										<fo:table-body>
											<fo:table-row>
												<fo:table-cell text-align="left">
													
													<xsl:choose>
													<xsl:when test="(reportDetails/data/contentAreaCode = 'ELA' or reportDetails/data/contentAreaCode = 'M') and (reportDetails/allGrades/string[contains(substring-after(., 'Grade '),'10')] )">
														<xsl:call-template name="ACTSectionTable"/>
														<fo:block margin-top="8pt">
															<xsl:call-template name="SummaryAdditionalResourceContent"/>
												  		</fo:block>
													</xsl:when>
													
													<xsl:otherwise>
														<fo:block>
															<xsl:call-template name="SummaryAdditionalResourceContent"/>
												  		</fo:block>
													</xsl:otherwise>
												  </xsl:choose>												
												</fo:table-cell>
												
												<fo:table-cell text-align="left">
													<fo:block font-size="8pt">
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</fo:table-body>
									</fo:table>
								</xsl:if>
								</fo:block>
								</fo:block>
		</xsl:template>
		<xsl:template name="SummaryAdditionalResourceContent">
				<fo:block font-size="8pt" >
														<fo:block>
															<xsl:if
																test="reportDetails/data/assessmentProgramCode = 'KAP'">
																<fo:block>
																	<xsl:text>For sample test questions, go to </xsl:text>
																	<fo:inline font-weight="bold">
																		<xsl:text>ksassessments.org/interactive-demos</xsl:text>
																	</fo:inline>
																	<xsl:text>.</xsl:text>
																</fo:block>
																<fo:block>
																	<xsl:text>For information on the Kansas College and Career Ready Standards, visit </xsl:text>
																	<fo:inline font-weight="bold">
																		<xsl:text>ksde.org</xsl:text>
																	</fo:inline>
																	<xsl:text>.</xsl:text>
																</fo:block>
																<fo:block>
																	<xsl:text>To learn about the Kansas Assessment Program, go to </xsl:text>
																	<fo:inline font-weight="bold">
																		<xsl:text>ksassessments.org</xsl:text>
																	</fo:inline>
																	<xsl:text>.</xsl:text>
																</fo:block>
																<fo:block>
																	<xsl:text>To discover more about this score report, see the 2018 Educator Guide at </xsl:text>
																	<fo:inline font-weight="bold">
																		<xsl:text>ksassessments.org/eg</xsl:text>
																	</fo:inline>
																	<xsl:text>.</xsl:text>
																	<!-- <xsl:text>To discover more about this score report, see the 2016 Educator Guide at </xsl:text>
																	<fo:inline font-weight="bold">
																		<xsl:text>kap.cete.us/eg</xsl:text>
																	</fo:inline> -->
																</fo:block>
																<xsl:if test="(reportDetails/data/contentAreaCode = 'ELA') or (reportDetails/data/contentAreaCode = 'M')">
																	<fo:block>
																		<xsl:text>To get an idea of how your high school students may perform on the ACT based on their KAP scores, go to </xsl:text>
																		<fo:inline font-weight="bold">
																			<xsl:text>ksassessments.org/act</xsl:text>
																		</fo:inline>
																		<xsl:text>.</xsl:text>
																	</fo:block>
																</xsl:if>
															</xsl:if>
														</fo:block>
				</fo:block>
		
		</xsl:template>		
		
		<xsl:template name ="MetametricsMeasuresSection" >
			<xsl:if test="reportDetails/data/contentAreaCode = 'ELA'">
			
													<fo:block  text-align="left" font-weight="bold" font-family="Verdana" font-size="8pt" space-after="2pt" >Lexile<fo:inline font-size="4pt" vertical-align="top">&#174;</fo:inline> Measure</fo:block>
													
													<fo:table table-layout="fixed" width="91%" >
													<fo:table-column column-width="proportional-column-width(13.5)" />
													<fo:table-column column-width="proportional-column-width(86.5)" />												
													<fo:table-body>	
															<fo:table-row>	
																<fo:table-cell text-align="center" >	
																	<fo:block background-color="#DFE0E1"
 																	border-top-style="solid" border-bottom-style="solid" 
																	border-left-style="solid" border-right-style="solid"  >
																		<fo:block font-size="8pt" font-family="Verdana" space-before="2pt" >																		
																			<xsl:text>Your student’s score:</xsl:text>																	
																		</fo:block>
																		<fo:block-container height="10pt">
																			<fo:block font-size="10pt" font-family="Verdana" font-weight="bold" space-after="2pt" >
																				<xsl:value-of select="reportDetails/data/metametricsMeasure"/>
																			</fo:block>
																		</fo:block-container>
																	</fo:block>
																	
																</fo:table-cell>
																<fo:table-cell text-align="left" padding-left="5pt" >															
																	<fo:block font-size="8pt" font-family="Verdana" >
																		<fo:block>
																			<xsl:text>The Lexile measure provides a score that describes the level at which your child can comfortably read </xsl:text>
																		</fo:block>
																		<fo:block>
																			<xsl:text>challenging text and also describes the complexity of texts, taking into account such features </xsl:text>
																		</fo:block>
																		<fo:block>
																			<xsl:text>as vocabulary and sentence complexity. This measure, along with consideration of your child’s </xsl:text>
																		</fo:block>
																		<fo:block>
																			<xsl:text>interests and experiences, is helpful in finding texts for independent reading.</xsl:text>
																		</fo:block>
																	</fo:block>																
																</fo:table-cell>																												
															</fo:table-row>												
													</fo:table-body>
												</fo:table>	
												</xsl:if>
													
													
												<xsl:if test="reportDetails/data/contentAreaCode = 'M'">
													
													<fo:block  text-align="left" font-weight="bold" font-family="Verdana" font-size="8pt" space-after="2pt">Quantile<fo:inline font-size="4pt" vertical-align="top">&#174;</fo:inline> Measure</fo:block>
													
													<fo:table table-layout="fixed" width="91%" >
													<fo:table-column column-width="proportional-column-width(13)" />
													<fo:table-column column-width="proportional-column-width(87)" />												
													<fo:table-body>	
															<fo:table-row>	
																<fo:table-cell text-align="center" >	
																	<fo:block background-color="#DFE0E1"
 																	border-top-style="solid" border-bottom-style="solid" 
																	border-left-style="solid" border-right-style="solid"  >
																		<fo:block font-size="8pt" font-family="Verdana" space-before="2pt" >																		
																			<xsl:text>Your student’s score:</xsl:text>																	
																		</fo:block>
																		<fo:block-container height="10pt">
																			<fo:block font-size="10pt" font-family="Verdana" font-weight="bold" space-after="2pt">
																			<xsl:value-of select="reportDetails/data/metametricsMeasure"/>
																			</fo:block>
																		</fo:block-container>
																	</fo:block>
																	
																</fo:table-cell>
																<fo:table-cell text-align="left" padding-left="5pt" >															
																	<fo:block font-size="8pt" font-family="Verdana" >
																		<fo:block>
																			<xsl:text>The Quantile measure provides a score that describes your child’s level of mathematical ability </xsl:text>
																		</fo:block>
																		<fo:block>
																			<xsl:text>and the difficulty of a skill or concept as it relates to other mathematical skills and concepts your </xsl:text>
																		</fo:block>
																		<fo:block>
																			<xsl:text>child is learning. The score shows your child’s readiness for instruction regarding a particular </xsl:text>
																		</fo:block>
																		<fo:block>
																			<xsl:text>mathematical skill or concept.</xsl:text>
																		</fo:block>
																	</fo:block>																
																</fo:table-cell>																												
															</fo:table-row>												
													</fo:table-body>
												</fo:table>	
					</xsl:if>
	</xsl:template>				
								
</xsl:stylesheet>