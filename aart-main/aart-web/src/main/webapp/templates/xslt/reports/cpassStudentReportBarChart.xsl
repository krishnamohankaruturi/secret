<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo" extension-element-prefixes="saxon">
	
	<xsl:template name="summaryLevelScoreGraph">
		<xsl:param name="reportType" />
		<xsl:variable name="axisImageHeight" select="5" />
		<xsl:variable name="headerRowHeight" select="30" />
		

		<fo:block>
			<fo:table table-layout="fixed" text-align="center">
				<fo:table-column column-width="proportional-column-width(1)" />
				<fo:table-body>
					<!-- <xsl:if test="($percentLevelDescriptionType = 'Main')"> -->
						<xsl:for-each select="reportDetails/mainPercentLevelCharts/string">
							<fo:table-row>
								<fo:table-cell padding-left=".2mm" display-align="center"
									text-align="start">
									<fo:block>
										<xsl:variable name="chartPath">
											<xsl:value-of select="." />
										</xsl:variable>
										
											<fo:block-container margin-top="-6px">
												<fo:block>
													<fo:external-graphic content-width="scale-down-to-fit">
													<xsl:attribute name="width">430</xsl:attribute>
														
														<xsl:attribute name="height">1</xsl:attribute>
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
	</xsl:template>
</xsl:stylesheet>