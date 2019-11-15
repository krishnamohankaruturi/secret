<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo" extension-element-prefixes="saxon">
	<xsl:template name="subScoreGraph"> 
		<xsl:param name="reportType"/>
		<xsl:variable name="axisImageHeight" select="13"/>
		<xsl:variable name="headerRowHeight" select="30"/>
		<xsl:variable name="tableHeight">
		   <xsl:choose>
		     <xsl:when test="$reportType = 'student'"><xsl:value-of select="(reportDetails/subScoreChartHeight * 4) + $headerRowHeight"/></xsl:when>
		     <xsl:when test="$reportType = 'school'"><xsl:value-of select="(reportDetails/subScoreChartHeight * 3) + $headerRowHeight"/></xsl:when>
		     <xsl:otherwise><xsl:value-of select="(reportDetails/subScoreChartHeight * 2) + $headerRowHeight"/></xsl:otherwise>
		   </xsl:choose>
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
																<xsl:attribute name="width"><xsl:value-of select="reportDetails/subScoreChartWidth"/></xsl:attribute>
																<xsl:attribute name="height"><xsl:value-of select="$axisImageHeight"/></xsl:attribute>
																<xsl:attribute name="src"><xsl:value-of select="reportDetails/axisSubScoreChart"/></xsl:attribute>
															</fo:external-graphic>
														</fo:block>
													</fo:block-container>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
										<xsl:if test="$reportType = 'student'">
											<fo:table-row>
												<fo:table-cell text-align="start" display-align="center" padding="2px">
													<fo:block></fo:block>
												</fo:table-cell>
												<fo:table-cell padding-left=".2mm" number-columns-spanned="2" display-align="center">
													<fo:block>
														<fo:block-container>
															<xsl:attribute name="height"><xsl:value-of select="reportDetails/subScoreChartHeight"/></xsl:attribute>
															<fo:block>
																<fo:external-graphic content-width="scale-down-to-fit">
																	<xsl:attribute name="width"><xsl:value-of select="reportDetails/subScoreChartWidth"/></xsl:attribute>
																	<xsl:attribute name="height"><xsl:value-of select="reportDetails/subScoreChartHeight"/></xsl:attribute>
																	<xsl:attribute name="src"><xsl:value-of select="reportDetails/studentSubScoreChart"/></xsl:attribute>
																</fo:external-graphic>
															</fo:block>
														</fo:block-container>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="$reportType != 'district'">
											<fo:table-row>
												<fo:table-cell text-align="start" display-align="center" padding="2px">
													<fo:block></fo:block>
												</fo:table-cell>
												<fo:table-cell padding-left=".2mm" number-columns-spanned="2" display-align="center">
													<fo:block>
														<fo:block-container>
															<xsl:attribute name="height"><xsl:value-of select="reportDetails/subScoreChartHeight"/></xsl:attribute>
															<fo:block>
																<fo:external-graphic content-width="scale-down-to-fit">
																	<xsl:attribute name="width"><xsl:value-of select="reportDetails/subScoreChartWidth"/></xsl:attribute>
																	<xsl:attribute name="height"><xsl:value-of select="reportDetails/subScoreChartHeight"/></xsl:attribute>
																	<xsl:attribute name="src"><xsl:value-of select="reportDetails/schoolSubScoreChart"/></xsl:attribute>
																</fo:external-graphic>
															</fo:block>
														</fo:block-container>
														<xsl:if test="reportDetails/schoolPrivacyStmtFlag = 'true'">
															<fo:block-container position="absolute" left="2.5cm" background-color="#ffffff">
																<xsl:attribute name="height"><xsl:value-of select="reportDetails/subScoreChartHeight"/></xsl:attribute>
																<fo:block margin-left="2mm">
																	 <xsl:call-template name="privacyStatementForNoScore"/>
																</fo:block>
															</fo:block-container>
														</xsl:if>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<fo:table-row>
											<fo:table-cell  text-align="start" display-align="center" padding="2px">
												<fo:block></fo:block>
											</fo:table-cell>
											<fo:table-cell  padding-left=".2mm" number-columns-spanned="2" display-align="center">
												<fo:block>
													<fo:block-container>
														<xsl:attribute name="height"><xsl:value-of select="reportDetails/subScoreChartHeight"/></xsl:attribute>
														<fo:block>
															<fo:external-graphic content-width="scale-down-to-fit">
																<xsl:attribute name="width"><xsl:value-of select="reportDetails/subScoreChartWidth"/></xsl:attribute>
																<xsl:attribute name="height"><xsl:value-of select="reportDetails/subScoreChartHeight"/></xsl:attribute>
																<xsl:attribute name="src"><xsl:value-of select="reportDetails/districtSubScoreChart"/></xsl:attribute>
															</fo:external-graphic>
														</fo:block>
													</fo:block-container>
													<xsl:if test="reportDetails/districtPrivacyStmtFlag = 'true'">
															<fo:block-container position="absolute" left="2.5cm" background-color="#ffffff">
																<xsl:attribute name="height"><xsl:value-of select="reportDetails/subScoreChartHeight"/></xsl:attribute>
																<fo:block margin-left="2mm">
																	 <xsl:call-template name="privacyStatementForNoScore"/>
																</fo:block>
															</fo:block-container>
														</xsl:if>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row>
											<fo:table-cell  text-align="start" display-align="center" padding="2px">
												<fo:block></fo:block>
											</fo:table-cell>
											<fo:table-cell  padding-left=".2mm" number-columns-spanned="2" display-align="center">
												<fo:block>
												<fo:block-container>
														<xsl:attribute name="height"><xsl:value-of select="reportDetails/subScoreChartHeight"/></xsl:attribute>
														<fo:block>
															<fo:external-graphic content-width="scale-down-to-fit">
																<xsl:attribute name="width"><xsl:value-of select="reportDetails/subScoreChartWidth"/></xsl:attribute>
																<xsl:attribute name="height"><xsl:value-of select="reportDetails/subScoreChartHeight"/></xsl:attribute>
																<xsl:attribute name="src"><xsl:value-of select="reportDetails/stateSubScoreChart"/></xsl:attribute>
															</fo:external-graphic>
														</fo:block>
												</fo:block-container>
												<xsl:if test="reportDetails/statePrivacyStmtFlag = 'true'">
															<fo:block-container position="absolute" left="2.5cm" background-color="#ffffff">
																<xsl:attribute name="height"><xsl:value-of select="reportDetails/subScoreChartHeight"/></xsl:attribute>
																<fo:block margin-left="2mm">
																	 <xsl:call-template name="privacyStatementForNoScore"/>
																</fo:block>
															</fo:block-container>
												</xsl:if>
											</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
						</fo:block>
					</fo:block-container>
					<fo:block-container position="absolute" top="0cm" left="0cm">
						<xsl:attribute name="height"><xsl:value-of select="$tableHeight"/></xsl:attribute>
						<fo:block>
								<fo:table table-layout="fixed" border="solid #000 .2mm" keep-together.within-column="always">
									<fo:table-column column-width="proportional-column-width(10)" />
									<fo:table-column column-width="proportional-column-width(14)" />
									<fo:table-column column-width="proportional-column-width(76)" />
									<fo:table-body>
										<fo:table-row>
											<fo:table-cell border="solid #000 .2mm" text-align="center" display-align="center">
												<fo:block>
													<fo:block-container>
														<xsl:attribute name="height"><xsl:value-of select="$headerRowHeight"/></xsl:attribute>
														<fo:block>
															<xsl:value-of select="reportDetails/data/gradeName"/>
														</fo:block>
													</fo:block-container>
												</fo:block>
											</fo:table-cell>
											<fo:table-cell border="solid #000 .2mm" text-align="center" display-align="center">
												<fo:block>Claims</fo:block>
											</fo:table-cell>
											<fo:table-cell border="solid #000 .2mm" text-align="center" padding-top="1mm">
												<fo:block>
													<fo:block-container>
														<xsl:attribute name="height"><xsl:value-of select="$headerRowHeight"/></xsl:attribute>
														<fo:block font-size="8pt"><xsl:if test="$reportType != 'student'">Median </xsl:if>Score</fo:block>
													</fo:block-container>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
										<xsl:if test="$reportType = 'student'">
											<fo:table-row>
												<fo:table-cell border="solid #000 .2mm" text-align="start" display-align="center" padding="2px">
													<fo:block font-weight="bold">Student</fo:block>
												</fo:table-cell>
												<fo:table-cell border="solid #000 .2mm" text-align="start" display-align="center" padding="2px">
													<fo:block></fo:block>
												</fo:table-cell>
												<fo:table-cell border="solid #000 .2mm" padding-left=".2mm">
													<fo:block>
														<fo:block-container>
															<xsl:attribute name="height"><xsl:value-of select="reportDetails/subScoreChartHeight"/></xsl:attribute>
															<fo:block></fo:block>
														</fo:block-container>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="$reportType != 'district'">
											<fo:table-row>
												<fo:table-cell border="solid #000 .2mm" text-align="start" display-align="center" padding="2px">
													<xsl:if test="$reportType = 'school'">
													<fo:block font-weight="bold">School</fo:block>
													</xsl:if>
													<xsl:if test="$reportType != 'school'">
													<fo:block>School</fo:block>
													</xsl:if>
												</fo:table-cell>
												<fo:table-cell border="solid #000 .2mm" text-align="start" display-align="center" padding="2px">
													<fo:block></fo:block>
												</fo:table-cell>
												<fo:table-cell border="solid #000 .2mm" padding-left=".2mm">
													<fo:block>
														<fo:block-container>
															<xsl:attribute name="height"><xsl:value-of select="reportDetails/subScoreChartHeight"/></xsl:attribute>
															<fo:block></fo:block>
														</fo:block-container>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<fo:table-row>
											<fo:table-cell border="solid #000 .2mm" text-align="start" display-align="center" padding="2px">
												<xsl:if test="$reportType = 'district'">
													<fo:block font-weight="bold">District</fo:block>
												</xsl:if>
												<xsl:if test="$reportType != 'district'">
													<fo:block>District</fo:block>
												</xsl:if>
											</fo:table-cell>
												<fo:table-cell border="solid #000 .2mm" text-align="start" display-align="center" padding="2px">
													<fo:block></fo:block>
												</fo:table-cell>
												<fo:table-cell border="solid #000 .2mm" padding-left=".2mm">
												<fo:block>
													<fo:block-container>
														<xsl:attribute name="height"><xsl:value-of select="reportDetails/subScoreChartHeight"/></xsl:attribute>
														<fo:block></fo:block>
													</fo:block-container>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row>
											<fo:table-cell border="solid #000 .2mm" text-align="start" display-align="center" padding="2px">
												<fo:block>State</fo:block>
											</fo:table-cell>
												<fo:table-cell border="solid #000 .2mm" text-align="start" display-align="center" padding="2px">
													<fo:block></fo:block>
												</fo:table-cell>
												<fo:table-cell border="solid #000 .2mm" padding-left=".2mm">
												<fo:block>
													<fo:block-container>
														<xsl:attribute name="height"><xsl:value-of select="reportDetails/subScoreChartHeight"/></xsl:attribute>
														<fo:block></fo:block>
													</fo:block-container>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
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