<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo" extension-element-prefixes="saxon">
	<xsl:template name="levels-table">
		<fo:table table-layout="fixed" border="solid .3mm black">
			<fo:table-column column-width="proportional-column-width(20)" />
			<fo:table-column column-width="proportional-column-width(30)" />
			<fo:table-column column-width="proportional-column-width(50)" />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell text-align="center"
						border="solid .3mm black" padding="2px" background-color="#c6c6c6">
						<fo:block font-weight="bold">Level</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center"
						border="solid .3mm black" padding="2px" background-color="#c6c6c6">
						<fo:block font-weight="bold">Score Range</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center"
						border="solid .3mm black" padding="2px" background-color="#c6c6c6">
						<fo:block font-weight="bold">Level Name</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
			<fo:table-body>
				<xsl:for-each select="reportDetails/data/levels/edu.ku.cete.domain.report.LevelDescription">
					<xsl:sort select="level" order="descending"/>
					<xsl:variable name="currentLvlName" select="levelName"/>
					<xsl:variable name="row-count" select="count(//reportDetails/data/levels/edu.ku.cete.domain.report.LevelDescription[levelName = $currentLvlName])"/>
					<fo:table-row border="solid .3mm black">
						<fo:table-cell text-align="center" border="solid .3mm black" padding="2px" display-align="center">
							<fo:block><xsl:value-of select="level"/></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" border="solid .3mm black" padding="2px" display-align="center">
							<fo:block><xsl:value-of select="levelLowCutScore"/> - <xsl:value-of select="levelHighCutScore"/></fo:block>
						</fo:table-cell>
						<xsl:if test="not(following-sibling::edu.ku.cete.domain.report.LevelDescription[levelName = $currentLvlName])">
							<fo:table-cell text-align="center" display-align="center" border="solid .3mm black" padding="2px">
							<xsl:attribute name="number-rows-spanned"><xsl:value-of select="$row-count"/></xsl:attribute>
							<fo:block><xsl:value-of select="$currentLvlName"/></fo:block>
						</fo:table-cell>
						</xsl:if>
					</fo:table-row>
				</xsl:for-each>
				<xsl:if test="count(reportDetails/data/levels/edu.ku.cete.domain.report.LevelDescription) = 0">
					<fo:table-row border="solid .3mm black">
						<fo:table-cell number-columns-spanned="3"  display-align="center"><fo:block>No Level Data Found.</fo:block></fo:table-cell>
					</fo:table-row>
				</xsl:if>
			</fo:table-body>
		</fo:table>
	</xsl:template>
	<xsl:template name="levels-table-ondemand-legend">
		<fo:table>
		<fo:table-body>
		<fo:table-row>
		<fo:table-cell>
		<fo:block>
			<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">
				<fo:instream-foreign-object>
							<svg xmlns="http://www.w3.org/2000/svg" width="190" height="10" version="1.1">
							 	<rect x="0" y="0" width="10" height="10" fill="#EC6B38"/>
							   	<text x="12" y="9" font-family="Verdana" font-size="10pt">1</text>
							   	<rect x="57" y="0" width="10" height="10" fill="#EA8F06"/>
							   	<text x="69" y="9" font-family="Verdana" font-size="10pt">2</text>
							   	<rect x="114" y="0" width="10" height="10" fill="#FBC668"/>
							   	<text x="126" y="9" font-family="Verdana" font-size="10pt">3</text>
							   	<rect x="171" y="0" width="10" height="10" fill="#F8EEA4"/>
							   	<text x="183" y="9" font-family="Verdana" font-size="10pt">4</text>
							</svg>
				</fo:instream-foreign-object>
			</xsl:if>			
		</fo:block>
		</fo:table-cell>
		</fo:table-row>
		</fo:table-body>
		</fo:table>	
	</xsl:template>
 	<xsl:template name="levels-table-legend">
		<fo:table>
		<fo:table-body>
		<fo:table-row>
		<fo:table-cell>
		<fo:block>
			<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">
				<fo:instream-foreign-object>
					<xsl:choose>
						<xsl:when test="count(reportDetails/data/levels/edu.ku.cete.domain.report.LevelDescription) = 5">
							<svg xmlns="http://www.w3.org/2000/svg" width="237" height="10" version="1.1">
							 	<rect x="0" y="0" width="10" height="10" fill="#EC6B38"/>
							   	<text x="12" y="9" font-family="Verdana" font-size="10pt">Level 1</text>
							   	<rect x="57" y="0" width="10" height="10" fill="#EA8F06"/>
							   	<text x="69" y="9" font-family="Verdana" font-size="10pt">Level 2</text>
							   	<rect x="114" y="0" width="10" height="10" fill="#FBC668"/>
							   	<text x="126" y="9" font-family="Verdana" font-size="10pt">Level 3</text>
							   	<rect x="171" y="0" width="10" height="10" fill="#F4D951"/>
							   	<text x="183" y="9" font-family="Verdana" font-size="10pt">Level 4</text>
							   	<rect x="228" y="0" width="10" height="10" fill="#F8EEA4"/>
							   	<text x="240" y="9" font-family="Verdana" font-size="10pt">Level 5</text>
							</svg>
						</xsl:when>
						<xsl:otherwise>
							<svg xmlns="http://www.w3.org/2000/svg" width="190" height="10" version="1.1">
							 	<rect x="0" y="0" width="10" height="10" fill="#EC6B38"/>
							   	<text x="12" y="9" font-family="Verdana" font-size="10pt">Level 1</text>
							   	<rect x="57" y="0" width="10" height="10" fill="#EA8F06"/>
							   	<text x="69" y="9" font-family="Verdana" font-size="10pt">Level 2</text>
							   	<rect x="114" y="0" width="10" height="10" fill="#FBC668"/>
							   	<text x="126" y="9" font-family="Verdana" font-size="10pt">Level 3</text>
							   	<rect x="171" y="0" width="10" height="10" fill="#F8EEA4"/>
							   	<text x="183" y="9" font-family="Verdana" font-size="10pt">Level 4</text>
							</svg>
						</xsl:otherwise>
					</xsl:choose>
				</fo:instream-foreign-object>
			</xsl:if>
			<xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'">
				<fo:instream-foreign-object>
					<svg xmlns="http://www.w3.org/2000/svg" width="237" height="10" version="1.1">
					 	<rect x="0" y="0" width="10" height="10" fill="#F6B065"/>
					   	<text x="11" y="9" font-family="Verdana" font-size="10pt">Level 1</text>
					   	<rect x="49" y="0" width="10" height="10" fill="#F3EC64"/>
					   	<text x="60" y="9" font-family="Verdana" font-size="10pt">Level 2</text>
					   	<rect x="96" y="0" width="10" height="10" fill="#A7CF52"/>
					   	<text x="107" y="9" font-family="Verdana" font-size="10pt">Level 3</text>
					   	<rect x="143" y="0" width="10" height="10" fill="#CDBFDE"/>
					   	<text x="154" y="9" font-family="Verdana" font-size="10pt">Level 4</text>
					</svg>
				</fo:instream-foreign-object>
			</xsl:if>
		</fo:block>
		</fo:table-cell>
		</fo:table-row>
		</fo:table-body>
		</fo:table>
	</xsl:template>
	 <xsl:template name="median-table-legend">
	 	<fo:table>
	 	<fo:table-body>
	 	<fo:table-row>
		<fo:table-cell>
	 	<fo:block>
			<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">				
					<fo:instream-foreign-object>
							<svg xmlns="http://www.w3.org/2000/svg" width="142" height="8" 
											version="1.1" >
											<rect x="0" y="0" width="10" height="6" fill="#40ad48" />
											<text x="12" y="6" font-family="Verdana" font-size="8pt" >SCHOOL</text> 
											<rect x="54" y="0" width="10" height="6" fill="#284496" />
											<text x="66" y="6" font-family="Verdana" font-size="8pt" >DISTRICT</text>
											<rect x="112" y="0" width="10" height="6" fill="#009ade" />
											<text x="124" y="6" font-family="Verdana" font-size="8pt">STATE</text>
							</svg>
				     </fo:instream-foreign-object>	
			</xsl:if>
		</fo:block>
		</fo:table-cell>
		</fo:table-row>
		</fo:table-body>
		</fo:table>
	</xsl:template> 
	
	 <xsl:template name="district-median-table-legend">
	 	<fo:table>
	 	<fo:table-body>
	 	<fo:table-row>
		<fo:table-cell>
	 	<fo:block>
			<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">				
					<fo:instream-foreign-object>
							<svg xmlns="http://www.w3.org/2000/svg" width="142" height="8" 
											version="1.1" >
											<rect x="20" y="0" width="10" height="6" fill="#40ad48" />
											<text x="33" y="6" font-family="Verdana" font-size="8pt" >DISTRICT</text> 
											<rect x="85" y="0" width="10" height="6" fill="#284496" />
											<text x="98" y="6" font-family="Verdana" font-size="8pt" >STATE</text>										
							</svg>
				     </fo:instream-foreign-object>	
			</xsl:if>
		</fo:block>
		</fo:table-cell>
		</fo:table-row>
		</fo:table-body>
		</fo:table>
	</xsl:template>  
	 
</xsl:stylesheet>