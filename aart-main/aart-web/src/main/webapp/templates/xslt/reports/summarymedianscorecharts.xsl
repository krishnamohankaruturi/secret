<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo" extension-element-prefixes="saxon">

	<xsl:template name="summaryMedianScoreGraph"> 
		<xsl:param name="reportType"/>
		<xsl:variable name="axisImageHeight" select="13"/>
		<xsl:variable name="headerRowHeight" select="28.5"/>
		<xsl:variable name="tableHeight">
			<xsl:value-of select="(//reportDetails/subScoreChartHeight * count(reportDetails/allGrades/string)) + $headerRowHeight"/>
		</xsl:variable>
		 <fo:table table-layout="fixed" keep-together.within-column="always">
			<fo:table-column column-width="proportional-column-width(1)" />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block-container>
								<xsl:attribute name="height"><xsl:value-of select="$tableHeight"/></xsl:attribute>
								<fo:block>
									<fo:table  table-layout="fixed" >
										<fo:table-column column-width="proportional-column-width(10)" />
										<fo:table-column column-width="proportional-column-width(14)" />
										<fo:table-column column-width="proportional-column-width(76)" />
										<fo:table-body>
											<fo:table-row>
												<fo:table-cell text-align="center" display-align="center">
													<fo:block-container>
														<xsl:attribute name="height"><xsl:value-of select="$headerRowHeight"/></xsl:attribute>
														<fo:block></fo:block>
													</fo:block-container>
												</fo:table-cell>
												<fo:table-cell padding-left=".2mm" number-columns-spanned="2" padding-top="20px">
													<fo:block>
														<fo:block-container>
															<xsl:attribute name="height"><xsl:value-of select="$axisImageHeight"/></xsl:attribute>
															<fo:block>
																<fo:external-graphic content-width="scale-down-to-fit">
																	<xsl:attribute name="width"><xsl:value-of select="//reportDetails/subScoreChartWidth"/></xsl:attribute>
																	<xsl:attribute name="height"><xsl:value-of select="$axisImageHeight"/></xsl:attribute>
																	<xsl:attribute name="src"><xsl:value-of select="reportDetails/axisSubScoreChart"/></xsl:attribute>
																</fo:external-graphic>
															</fo:block>
														</fo:block-container>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
											<xsl:for-each select="reportDetails/gradeMedianCharts/string">
												<fo:table-row>
													<fo:table-cell text-align="start" display-align="center" padding="2px">
														<fo:block></fo:block>
													</fo:table-cell>
													<fo:table-cell padding-left=".2mm" display-align="center">
														<fo:block>
															<xsl:variable name="chartPath">
																<xsl:value-of select="." />
															</xsl:variable>
															<fo:block-container>
																<xsl:attribute name="height"><xsl:value-of select="//reportDetails/subScoreChartHeight + .2"/></xsl:attribute>
																<fo:block>
																	<fo:external-graphic content-width="scale-down-to-fit">
																		<xsl:attribute name="width"><xsl:value-of select="//reportDetails/subScoreChartWidth"/></xsl:attribute>
																		<xsl:attribute name="height"><xsl:value-of select="//reportDetails/subScoreChartHeight"/></xsl:attribute>
																		<xsl:attribute name="src"><xsl:value-of select="$chartPath"/></xsl:attribute>
																	</fo:external-graphic>
																</fo:block>
															</fo:block-container>
														</fo:block>
													</fo:table-cell>
												</fo:table-row> 
											</xsl:for-each>
									</fo:table-body>
								</fo:table>
							</fo:block>
						</fo:block-container>
						<fo:block-container position="absolute" top="0cm" left="0cm">
							<xsl:attribute name="height"><xsl:value-of select="$tableHeight"/></xsl:attribute>
							<fo:block>
								<fo:table table-layout="fixed"  keep-together.within-column="always" >
									<fo:table-column column-width="proportional-column-width(10)" />
									<fo:table-column column-width="proportional-column-width(14)" />
									<fo:table-column column-width="proportional-column-width(76)" />
									<fo:table-body>
										<fo:table-row >
											<fo:table-cell  text-align="center" display-align="center" >
												<fo:block>													
												</fo:block>
											</fo:table-cell>											
											<fo:table-cell number-columns-spanned="2" padding-top="1mm">
												<fo:block>
													<fo:block-container  >
														<xsl:attribute name="height"><xsl:value-of select="$headerRowHeight"/></xsl:attribute>
														<fo:block font-size="8pt" text-align="start" margin-top="9pt">LOWEST SCORE</fo:block>
														<fo:block font-size="8pt" text-align="end"  margin-top="-9pt" >HIGHEST SCORE&#xA0;&#xA0;&#xA0;&#xA0;&#xA0;&#xA0;&#xA0;&#xA0;&#xA0;</fo:block>
													</fo:block-container>
													<fo:block-container position="absolute" left="-5px">
														<xsl:attribute name="top"><xsl:value-of select="$headerRowHeight - 11"/></xsl:attribute>
														<fo:block>
															<!-- <fo:external-graphic content-width="scale-down-to-fit">
																<xsl:attribute name="width"><xsl:value-of select="26"/></xsl:attribute>
																<xsl:attribute name="height"><xsl:value-of select="24"/></xsl:attribute>
																<xsl:attribute name="src">
																	<xsl:value-of select="reportDetails/unusedAreaIconPath"/>
																</xsl:attribute>
															</fo:external-graphic> -->
														</fo:block>
													</fo:block-container>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
						 				<xsl:for-each select="reportDetails/allGrades/string">
											<xsl:variable name="position" select="position()" />
											<fo:table-row  border-top= "solid 0.5pt #929597" >												
												<fo:table-cell text-align="start" display-align="center"  >													
													<fo:block font-weight="bold" font-size="9pt">
														<xsl:value-of select="." />
													</fo:block>
												</fo:table-cell>
												<fo:table-cell  text-align="start" display-align="center" >
													<fo:block><fo:leader/></fo:block>
												</fo:table-cell>
												<fo:table-cell  padding-left=".3mm" display-align="center">
													<fo:block>
														<fo:block-container>
															<xsl:attribute name="height"><xsl:value-of select="//reportDetails/subScoreChartHeight"/></xsl:attribute>
															<fo:block>
																	<xsl:choose>
																	<xsl:when test="contains(//reportDetails/gradeMedianCharts/string[$position], 'privacy3')">
																		<xsl:choose>
																				<xsl:when test="$reportType = 'school'">
																					<fo:block-container position="absolute">
																						<xsl:attribute name="top"><xsl:value-of select="2"/></xsl:attribute>
																						<fo:block margin-top="3pt" margin-left="-71.7pt" ><xsl:call-template name="privacyStatementForNoScoreWordsForSchoolSummary"/></fo:block>
																					</fo:block-container>
																					<fo:block-container position="absolute">
																						<xsl:attribute name="top"><xsl:value-of select="21"/></xsl:attribute>
																						<fo:block margin-top="-2pt" margin-left="-71.7pt" ><xsl:call-template name="privacyStatementForNoScoreWordsForSchoolSummary"/></fo:block>
																					</fo:block-container>
																				</xsl:when>
																				<xsl:otherwise>
																					<fo:block-container position="absolute">
																						<xsl:attribute name="top"><xsl:value-of select="2"/></xsl:attribute>
																						<fo:block margin-top="3pt" margin-left="-71.7pt" ><xsl:call-template name="privacyStatementForNoScoreWordsForSchoolSummary"/></fo:block>
																					</fo:block-container>
																					<fo:block-container position="absolute">
																						<xsl:attribute name="top"><xsl:value-of select="21"/></xsl:attribute>
																						<fo:block margin-top="-2pt" margin-left="-71.7pt" ><xsl:call-template name="privacyStatementForNoScoreWordsForSchoolSummary"/></fo:block>
																					</fo:block-container>																				
																				</xsl:otherwise>
																		</xsl:choose>
																		
																		
																		
																	</xsl:when>
																	<xsl:when test="contains(//reportDetails/gradeMedianCharts/string[$position], 'privacy2')">
																		<fo:block-container position="absolute">
																			<xsl:choose>
																				<xsl:when test="$reportType = 'school'">
																					<xsl:attribute name="top"><xsl:value-of select="21"/></xsl:attribute>
																					<fo:block margin-top="-2pt" margin-left="-71.7pt" ><xsl:call-template name="privacyStatementForNoScoreWordsForSchoolSummary"/></fo:block>
																				</xsl:when>
																				<xsl:otherwise>
																					<xsl:attribute name="top"><xsl:value-of select="2"/></xsl:attribute>
																					<fo:block margin-top="3pt" margin-left="-71.7pt" ><xsl:call-template name="privacyStatementForNoScoreWordsForSchoolSummary"/></fo:block>
																				</xsl:otherwise>
																			</xsl:choose> 																		
																		</fo:block-container>
																	</xsl:when>
																	<xsl:when test="contains(//reportDetails/gradeMedianCharts/string[$position], 'privacy1')">
																		<fo:block-container position="absolute">
																			<xsl:choose>
																				<xsl:when test="$reportType = 'school'">
																					<xsl:attribute name="top"><xsl:value-of select="1"/></xsl:attribute>
																					<fo:block margin-top="3pt" margin-left="-71.7pt" ><xsl:call-template name="privacyStatementForNoScoreWordsForSchoolSummary"/></fo:block>
																				</xsl:when>
																				<xsl:otherwise>
																					<xsl:attribute name="top"><xsl:value-of select="2"/></xsl:attribute>
																					<fo:block margin-top="3pt" margin-left="-71.7pt" ><xsl:call-template name="privacyStatementForNoScoreWordsForSchoolSummary"/></fo:block>
																				</xsl:otherwise>
																			</xsl:choose>
																		</fo:block-container>
																	</xsl:when>
																</xsl:choose>
															</fo:block>
														</fo:block-container>
													</fo:block>
												</fo:table-cell>
											</fo:table-row> 
										</xsl:for-each> -->
									</fo:table-body>
								</fo:table>
							</fo:block>
						</fo:block-container>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>
</xsl:stylesheet>