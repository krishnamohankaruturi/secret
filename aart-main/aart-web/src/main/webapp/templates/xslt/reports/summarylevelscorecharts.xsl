<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo" extension-element-prefixes="saxon">
	
	<xsl:template name="summaryLevelScoreGraph">
		<xsl:param name="reportType" />
		<xsl:param name="percentLevelDescriptionType" />
		<xsl:variable name="axisImageHeight" select="13" />
		<xsl:variable name="headerRowHeight" select="30" />
		<xsl:variable name="tableHeight">
			<xsl:value-of
				select="((//reportDetails/percentLevelChartHeight)  * count(reportDetails/allGrades/string)) + $headerRowHeight" />
		</xsl:variable>

		<fo:block>
			<fo:table table-layout="fixed" text-align="center">
				<fo:table-column column-width="proportional-column-width(1)" />
				<fo:table-body>
					<xsl:if test="($percentLevelDescriptionType = 'Main')">
						<xsl:for-each select="reportDetails/mainPercentLevelCharts/string">
							<fo:table-row>
								<fo:table-cell padding-left=".2mm" display-align="center"
									text-align="start">
									<fo:block>
										<xsl:variable name="chartPath">
											<xsl:value-of select="." />
										</xsl:variable>
										<xsl:if test="position() &gt; 1">
											<fo:block-container margin-top="-6px">
												<fo:block>
													<fo:external-graphic content-width="scale-down-to-fit">
														<xsl:attribute name="width"><xsl:value-of
															select="//reportDetails/percentLevelChartWidth" /></xsl:attribute>
														<xsl:attribute name="height"><xsl:value-of
															select="//reportDetails/percentLevelChartHeight" /></xsl:attribute>
														<xsl:attribute name="src"><xsl:value-of
															select="$chartPath" /></xsl:attribute>
													</fo:external-graphic>
												</fo:block>
												<xsl:choose>
													<xsl:when test="contains($chartPath, 'privacy3')">
														<fo:block-container position="absolute"
															left="72">
															<xsl:attribute name="top"><xsl:value-of
																select="5" /></xsl:attribute>
																<xsl:choose>
																	<xsl:when test="$reportType = 'school'">
																		<fo:block margin-left="13mm" margin-top="1mm">
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>																
																	</xsl:when>
																	<xsl:otherwise>
																		<fo:block margin-left="13mm">
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																	</xsl:otherwise>
																</xsl:choose>															
														</fo:block-container>
														<fo:block-container position="absolute"
															left="72">
															<xsl:attribute name="top"><xsl:value-of
																select="28" /></xsl:attribute>
																<xsl:choose>
																	<xsl:when test="$reportType = 'school'">
																		<fo:block margin-left="13mm" margin-top ="0.5mm" >
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																	 </xsl:when>
																	 <xsl:otherwise>
																	 	<fo:block margin-left="13mm" >
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																	 </xsl:otherwise>
																 </xsl:choose>
														</fo:block-container>
													</xsl:when>
													<xsl:when test="contains($chartPath, 'privacy2')">
														<fo:block-container position="absolute"
															left="72">
															<xsl:choose>
																<xsl:when test="$reportType = 'school'">
																	<xsl:attribute name="top"><xsl:value-of
																		select="28" /></xsl:attribute>
																		<fo:block margin-left="13mm" margin-top ="0.5mm" >
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																</xsl:when>
																<xsl:otherwise>
																	<xsl:attribute name="top"><xsl:value-of
																		select="10" /></xsl:attribute>
																		<fo:block margin-left="13mm" margin-top ="1mm" >
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																</xsl:otherwise>
															</xsl:choose>															
														</fo:block-container>
													</xsl:when>
													<xsl:when test="contains($chartPath, 'privacy1')">
														<fo:block-container position="absolute"
															left="72">
															<xsl:choose>
																<xsl:when test="$reportType = 'school'">
																	<xsl:attribute name="top"><xsl:value-of
																		select="5" /></xsl:attribute>
																	<fo:block margin-left="13mm" margin-top ="0.8mm" >
																		<xsl:call-template name="privacyStatementForNoScore" />
																	</fo:block>
																</xsl:when>
																<xsl:otherwise>
																	<xsl:attribute name="top"><xsl:value-of
																		select="10" /></xsl:attribute>
																	<fo:block margin-left="13mm" margin-top ="1mm" >
																		<xsl:call-template name="privacyStatementForNoScore" />
																	</fo:block>
																</xsl:otherwise>
															</xsl:choose>

														</fo:block-container>
													</xsl:when>
												</xsl:choose>
											</fo:block-container>
										</xsl:if>
										<xsl:if test="position() = 1">
											<fo:block-container>
												<fo:block>
													<fo:external-graphic content-width="scale-down-to-fit">
														<xsl:attribute name="width"><xsl:value-of
															select="//reportDetails/percentLevelChartWidth" /></xsl:attribute>
														<xsl:attribute name="height"><xsl:value-of
															select="//reportDetails/percentLevelChartHeight" /></xsl:attribute>
														<xsl:attribute name="src"><xsl:value-of
															select="$chartPath" /></xsl:attribute>
													</fo:external-graphic>
												</fo:block>
												<xsl:choose>
													<xsl:when test="contains($chartPath, 'privacy3')">
														<fo:block-container position="absolute"
															left="72">
															<xsl:attribute name="top"><xsl:value-of
																select="6" /></xsl:attribute>
																<xsl:choose>
																	<xsl:when test="$reportType = 'school'">
																		<fo:block margin-left="13mm" margin-top="1mm">
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																	</xsl:when>
																	<xsl:otherwise>
																		<fo:block margin-left="13mm">
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																	</xsl:otherwise>
																</xsl:choose>															
														</fo:block-container>
														<fo:block-container position="absolute"
															left="72">
															<xsl:attribute name="top"><xsl:value-of
																select="26" /></xsl:attribute>
																<xsl:choose>
																	<xsl:when test="$reportType = 'school'">
																		<fo:block margin-left="13mm" margin-top="1mm">
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																	</xsl:when>
																	<xsl:otherwise>
																		<fo:block margin-left="13mm" >
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																	</xsl:otherwise>
																</xsl:choose>															
														</fo:block-container>
													</xsl:when>
													<xsl:when test="contains($chartPath, 'privacy2')">
														<fo:block-container position="absolute"
															left="72">
															<xsl:choose>
																<xsl:when test="$reportType = 'school'">
																	<xsl:attribute name="top"><xsl:value-of
																		select="27" /></xsl:attribute>
																	<fo:block margin-left="13mm" margin-top ="0.5mm" >
																		<xsl:call-template name="privacyStatementForNoScore" />
																	</fo:block>
																</xsl:when>
																<xsl:otherwise>
																	<xsl:attribute name="top"><xsl:value-of
																		select="10" /></xsl:attribute>
																	<fo:block margin-left="13mm" margin-top ="1mm">
																		<xsl:call-template name="privacyStatementForNoScore" />
																	</fo:block>
																</xsl:otherwise>
															</xsl:choose>															
														</fo:block-container>
													</xsl:when>
													<xsl:when test="contains($chartPath, 'privacy1')">
														<fo:block-container position="absolute"
															left="72">
															<xsl:choose>
																<xsl:when test="$reportType = 'school'">
																	<xsl:attribute name="top"><xsl:value-of
																		select="6" /></xsl:attribute>
																<fo:block margin-left="13mm" margin-top ="0.5mm" >
																	<xsl:call-template name="privacyStatementForNoScore" />
																</fo:block>
																</xsl:when>
																<xsl:otherwise>
																	<xsl:attribute name="top"><xsl:value-of
																		select="10" /></xsl:attribute>
																	<fo:block margin-left="13mm" margin-top ="1mm">
																		<xsl:call-template name="privacyStatementForNoScore" />
																	</fo:block>
																</xsl:otherwise>
															</xsl:choose>															
														</fo:block-container>
													</xsl:when>
												</xsl:choose>
											</fo:block-container>
										</xsl:if>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</xsl:for-each>
					</xsl:if>
					
					<xsl:if test="($percentLevelDescriptionType = 'MDPT')">
						<xsl:for-each select="reportDetails/mdptPercentLevelCharts/string">
							<fo:table-row>
								<fo:table-cell padding-left=".2mm" display-align="center"
									text-align="start">
									<fo:block>
										<xsl:variable name="chartPath">
											<xsl:value-of select="." />
										</xsl:variable>
										<xsl:if test="position() &gt; 1">
											<fo:block-container margin-top="-6px">
												<fo:block>
													<fo:external-graphic content-width="scale-down-to-fit">
														<xsl:attribute name="width"><xsl:value-of
															select="//reportDetails/percentLevelChartWidth" /></xsl:attribute>
														<xsl:attribute name="height"><xsl:value-of
															select="//reportDetails/percentLevelChartHeight" /></xsl:attribute>
														<xsl:attribute name="src"><xsl:value-of
															select="$chartPath" /></xsl:attribute>
													</fo:external-graphic>
												</fo:block>
												<xsl:choose>
													<xsl:when test="contains($chartPath, 'privacy3')">
														<fo:block-container position="absolute"
															left="72">
															<xsl:attribute name="top"><xsl:value-of
																select="5" /></xsl:attribute>
																<xsl:choose>
																	<xsl:when test="$reportType = 'school'">
																		<fo:block margin-left="13mm"  margin-top="1mm" >
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																	</xsl:when>
																	<xsl:otherwise>
																		<fo:block margin-left="13mm" >
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																	</xsl:otherwise>
																	</xsl:choose>
														</fo:block-container>
														<fo:block-container position="absolute"
															left="72">
															<xsl:attribute name="top"><xsl:value-of
																select="28" /></xsl:attribute>
																<xsl:choose>
																	<xsl:when test="$reportType = 'school'">
																		<fo:block margin-left="13mm" margin-top="0.5mm">
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																	</xsl:when>
																	<xsl:otherwise>
																		<fo:block margin-left="13mm">
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>																	
																	</xsl:otherwise>
																</xsl:choose>
														</fo:block-container>
													</xsl:when>
													<xsl:when test="contains($chartPath, 'privacy2')">
														<fo:block-container position="absolute"
															left="72">
															<xsl:choose>
																<xsl:when test="$reportType = 'school'">
																	<xsl:attribute name="top"><xsl:value-of
																		select="28" /></xsl:attribute>
																		<fo:block margin-left="13mm" margin-top="0.5mm" >
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																	</xsl:when>
																<xsl:otherwise>
																	<xsl:attribute name="top"><xsl:value-of
																		select="10" /></xsl:attribute>
																		<fo:block margin-left="13mm" margin-top="1mm" >
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																</xsl:otherwise>
															</xsl:choose>															
														</fo:block-container>
													</xsl:when>
													<xsl:when test="contains($chartPath, 'privacy1')">
														<fo:block-container position="absolute"
															left="72">
															<xsl:choose>
																<xsl:when test="$reportType = 'school'">
																	<xsl:attribute name="top"><xsl:value-of
																		select="5" /></xsl:attribute>
																		<fo:block margin-left="13mm" margin-top="0.8mm" >
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																</xsl:when>
																<xsl:otherwise>
																	<xsl:attribute name="top"><xsl:value-of
																		select="10" /></xsl:attribute>
																		<fo:block margin-left="13mm" margin-top="1mm" >
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																</xsl:otherwise>
															</xsl:choose>															
														</fo:block-container>
													</xsl:when>
												</xsl:choose>
											</fo:block-container>
										</xsl:if>
										<xsl:if test="position() = 1">
											<fo:block-container>
												<fo:block>
													<fo:external-graphic content-width="scale-down-to-fit">
														<xsl:attribute name="width"><xsl:value-of
															select="//reportDetails/percentLevelChartWidth" /></xsl:attribute>
														<xsl:attribute name="height"><xsl:value-of
															select="//reportDetails/percentLevelChartHeight" /></xsl:attribute>
														<xsl:attribute name="src"><xsl:value-of
															select="$chartPath" /></xsl:attribute>
													</fo:external-graphic>
												</fo:block>
												<xsl:choose>
													<xsl:when test="contains($chartPath, 'privacy3')">
														<fo:block-container position="absolute"
															left="72">
															<xsl:attribute name="top"><xsl:value-of
																select="6" /></xsl:attribute>
															<fo:block margin-left="13mm">
																<xsl:call-template name="privacyStatementForNoScore" />
															</fo:block>
														</fo:block-container>
														<fo:block-container position="absolute"
															left="72">
															<xsl:attribute name="top"><xsl:value-of
																select="26" /></xsl:attribute>
															<fo:block margin-left="13mm">
																<xsl:call-template name="privacyStatementForNoScore" />
															</fo:block>
														</fo:block-container>
													</xsl:when>
													<xsl:when test="contains($chartPath, 'privacy2')">
														<fo:block-container position="absolute"
															left="72">
															<xsl:choose>
																<xsl:when test="$reportType = 'school'">
																	<xsl:attribute name="top"><xsl:value-of
																		select="27" /></xsl:attribute>
																		<fo:block margin-left="13mm" margin-top="0.5mm" >
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																</xsl:when>
																<xsl:otherwise>
																	<xsl:attribute name="top"><xsl:value-of
																		select="10" /></xsl:attribute>
																		<fo:block margin-left="13mm" margin-top="1mm" >
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																</xsl:otherwise>
															</xsl:choose>
															
														</fo:block-container>
													</xsl:when>
													<xsl:when test="contains($chartPath, 'privacy1')">
														<fo:block-container position="absolute"
															left="72">
															<xsl:choose>
																<xsl:when test="$reportType = 'school'">
																	<xsl:attribute name="top"><xsl:value-of
																		select="6" /></xsl:attribute>
																		<fo:block margin-left="13mm" margin-top="0.5mm" >
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																</xsl:when>
																<xsl:otherwise>
																	<xsl:attribute name="top"><xsl:value-of
																		select="10" /></xsl:attribute>
																		<fo:block margin-left="13mm"  margin-top="1mm" >
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																</xsl:otherwise>
															</xsl:choose>
														</fo:block-container>
													</xsl:when>
												</xsl:choose>
											</fo:block-container>
										</xsl:if>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</xsl:for-each>
					</xsl:if>
					
					<xsl:if test="($percentLevelDescriptionType = 'Combined')">
						<xsl:for-each select="reportDetails/combinedPercentLevelCharts/string">
							<fo:table-row>
								<fo:table-cell padding-left=".2mm" display-align="center"
									text-align="start">
									<fo:block>
										<xsl:variable name="chartPath">
											<xsl:value-of select="." />
										</xsl:variable>
										<xsl:if test="position() &gt; 1">
											<fo:block-container margin-top="-6px">
												<fo:block>
													<fo:external-graphic content-width="scale-down-to-fit">
														<xsl:attribute name="width"><xsl:value-of
															select="//reportDetails/percentLevelChartWidth" /></xsl:attribute>
														<xsl:attribute name="height"><xsl:value-of
															select="//reportDetails/percentLevelChartHeight" /></xsl:attribute>
														<xsl:attribute name="src"><xsl:value-of
															select="$chartPath" /></xsl:attribute>
													</fo:external-graphic>
												</fo:block>
												<xsl:choose>
													<xsl:when test="contains($chartPath, 'privacy3')">
														<fo:block-container position="absolute"
															left="72">
															<xsl:attribute name="top"><xsl:value-of
																select="5" /></xsl:attribute>
															<xsl:choose>
																<xsl:when test="$reportType = 'school'">
																	<fo:block margin-left="13mm" margin-top="1mm">
																		<xsl:call-template name="privacyStatementForNoScore" />
																	</fo:block>
																</xsl:when>
																<xsl:otherwise>
																	<fo:block margin-left="13mm">
																		<xsl:call-template name="privacyStatementForNoScore" />
																	</fo:block>																
																</xsl:otherwise>
															</xsl:choose>	
																
															
														</fo:block-container>
														<fo:block-container position="absolute"
															left="72">
															<xsl:attribute name="top"><xsl:value-of
																select="28" /></xsl:attribute>
																<xsl:choose>
																	<xsl:when test="$reportType = 'school'">
																		<fo:block margin-left="13mm" margin-top="0.5mm" >
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																	</xsl:when>
																	<xsl:otherwise>
																		<fo:block margin-left="13mm">
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																	</xsl:otherwise>
																</xsl:choose>
														</fo:block-container>
													</xsl:when>
													<xsl:when test="contains($chartPath, 'privacy2')">
														<fo:block-container position="absolute"
															left="72">
															<xsl:choose>
																<xsl:when test="$reportType = 'school'">
																	<xsl:attribute name="top"><xsl:value-of
																		select="28" /></xsl:attribute>
																		<fo:block margin-left="13mm" margin-top="0.5mm" >
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																</xsl:when>
																<xsl:otherwise>
																	<xsl:attribute name="top"><xsl:value-of
																		select="10" /></xsl:attribute>
																		<fo:block margin-left="13mm" margin-top="1mm" >
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																</xsl:otherwise>
															</xsl:choose>															
														</fo:block-container>
													</xsl:when>
													<xsl:when test="contains($chartPath, 'privacy1')">
														<fo:block-container position="absolute"
															left="72">
															<xsl:choose>
																<xsl:when test="$reportType = 'school'">
																	<xsl:attribute name="top"><xsl:value-of
																		select="5" /></xsl:attribute>
																		<fo:block margin-left="13mm" margin-top="0.8mm">
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																</xsl:when>
																<xsl:otherwise>
																	<xsl:attribute name="top"><xsl:value-of
																		select="10" /></xsl:attribute>
																		<fo:block margin-left="13mm" margin-top="1mm">
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																</xsl:otherwise>
															</xsl:choose>
														</fo:block-container>
													</xsl:when>
												</xsl:choose>
											</fo:block-container>
										</xsl:if>
										<xsl:if test="position() = 1">
											<fo:block-container>
												<fo:block>
													<fo:external-graphic content-width="scale-down-to-fit">
														<xsl:attribute name="width"><xsl:value-of
															select="//reportDetails/percentLevelChartWidth" /></xsl:attribute>
														<xsl:attribute name="height"><xsl:value-of
															select="//reportDetails/percentLevelChartHeight" /></xsl:attribute>
														<xsl:attribute name="src"><xsl:value-of
															select="$chartPath" /></xsl:attribute>
													</fo:external-graphic>
												</fo:block>
												<xsl:choose>
													<xsl:when test="contains($chartPath, 'privacy3')">
														<fo:block-container position="absolute"
															left="72">
															<xsl:attribute name="top"><xsl:value-of
																select="6" /></xsl:attribute>
															<fo:block margin-left="13mm">
																<xsl:call-template name="privacyStatementForNoScore" />
															</fo:block>
														</fo:block-container>
														<fo:block-container position="absolute"
															left="72">
															<xsl:attribute name="top"><xsl:value-of
																select="26" /></xsl:attribute>
															<fo:block margin-left="13mm">
																<xsl:call-template name="privacyStatementForNoScore" />
															</fo:block>
														</fo:block-container>
													</xsl:when>
													<xsl:when test="contains($chartPath, 'privacy2')">
														<fo:block-container position="absolute"
															left="72">
															<xsl:choose>
																<xsl:when test="$reportType = 'school'">
																	<xsl:attribute name="top"><xsl:value-of
																		select="27" /></xsl:attribute>
																		<fo:block margin-left="13mm" margin-top="0.5mm" >
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																</xsl:when>
																<xsl:otherwise>
																	<xsl:attribute name="top"><xsl:value-of
																		select="10" /></xsl:attribute>
																		<fo:block margin-left="13mm" margin-top="1mm" >
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																</xsl:otherwise>
															</xsl:choose>															
														</fo:block-container>
													</xsl:when>
													<xsl:when test="contains($chartPath, 'privacy1')">
														<fo:block-container position="absolute"
															left="72">
															<xsl:choose>
																<xsl:when test="$reportType = 'school'">
																	<xsl:attribute name="top"><xsl:value-of
																		select="6" /></xsl:attribute>
																		<fo:block margin-left="13mm" margin-top="0.5mm">
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																</xsl:when>
																<xsl:otherwise>
																	<xsl:attribute name="top"><xsl:value-of
																		select="10" /></xsl:attribute>
																		<fo:block margin-left="13mm" margin-top="1mm">
																			<xsl:call-template name="privacyStatementForNoScore" />
																		</fo:block>
																</xsl:otherwise>
															</xsl:choose>
														</fo:block-container>
													</xsl:when>
												</xsl:choose>
											</fo:block-container>
										</xsl:if>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</xsl:for-each>
					</xsl:if>
					
				</fo:table-body>
			</fo:table>

		</fo:block>
	</xsl:template>
</xsl:stylesheet>