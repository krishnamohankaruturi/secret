<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo" extension-element-prefixes="saxon">

	<xsl:template name="interimReportCycleScoreGraph">
		<fo:table table-layout="fixed" keep-together.within-column="always">
			<fo:table-column column-width="proportional-column-width(1)" />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell>
						<fo:block-container>
							<fo:block margin-left="-1pt">
								<fo:table table-layout="fixed">
									<fo:table-column column-width="proportional-column-width(13)" />
									<fo:table-column column-width="proportional-column-width(87)" />
									<fo:table-body>
										<fo:table-row>
											<fo:table-cell text-align="start"
												display-align="after">
												<fo:block-container>
													<fo:block></fo:block>
												</fo:block-container>
											</fo:table-cell>
											<fo:table-cell text-align="start"
												display-align="after">
												<fo:block>
													<fo:block-container>
														<fo:block>
															<fo:external-graphic content-width="scale-down-to-fit">
																<xsl:attribute name="width">450</xsl:attribute>
																<xsl:attribute name="height">15</xsl:attribute>
																<xsl:attribute name="src"><xsl:value-of
																	select="interimReportDetails/reportCycleScoreRangeCharts" /></xsl:attribute>
															</fo:external-graphic>
														</fo:block>
													</fo:block-container>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
										<xsl:for-each select="interimReportDetails/reportCycleCharts/string">
											<fo:table-row>
												<fo:table-cell text-align="start"
													display-align="before">
													<fo:block></fo:block>
												</fo:table-cell>
												<fo:table-cell
													display-align="before">
													<fo:block>
														<xsl:variable name="chartPath">
															<xsl:value-of select="." />
														</xsl:variable>
														<fo:block-container>
															<fo:block line-height="0pt"  margin-top="0.6pt" >
																<fo:external-graphic content-height="scale-down-to-fit" > 
																	<xsl:attribute name="width">450</xsl:attribute>
																	<xsl:attribute name="src"><xsl:value-of
																		select="$chartPath" /></xsl:attribute>
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
					 	<fo:block-container position="absolute" top="20pt">
							<fo:block>
								<fo:table table-layout="fixed"
									keep-together.within-column="always" text-align="center">
									<fo:table-column column-width="proportional-column-width(13)" />
									<fo:table-column column-width="proportional-column-width(87)" />
									<fo:table-body>
										<xsl:for-each select="interimReportDetails/reportCycles/string">
											<xsl:variable name="position" select="position()" />
											<fo:table-row border-top="solid 0.5pt #929597"
		 											height="53.6pt">
												<fo:table-cell text-align="start"
													display-align="center">
													<fo:block-container width="72pt"
														text-align="center">
														<fo:block font-weight="bold" font-size="9pt" >
															<xsl:value-of select="." />
														</fo:block>
													</fo:block-container>
												</fo:table-cell>
												<fo:table-cell text-align="start"
													display-align="center" padding-left=".3mm" >
													<fo:block-container>
														<fo:block>
															<xsl:choose>
																<xsl:when
																	test="contains(//interimReportDetails/reportCycleCharts/string[$position], 'noScaleScore')">
																	<fo:block-container text-align="start"  margin-left="5pt" >
																		<fo:block>																<xsl:value-of select="//interimReportDetails/reasonCodes/string[$position]" />
																		</fo:block>
																	</fo:block-container>
																</xsl:when>															
															</xsl:choose>
														</fo:block>
													</fo:block-container>
												</fo:table-cell>
											</fo:table-row>
										</xsl:for-each>
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