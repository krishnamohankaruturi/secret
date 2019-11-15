<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java fo"
	extension-element-prefixes="saxon">
	<xsl:include href="utility.xsl" />

	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format"
			font-family="Verdana" font-size="10pt">
			<fo:layout-master-set>

				<fo:simple-page-master master-name="allPages"
					page-height="27.94cm" page-width="21.59cm" margin="1.27cm"
					margin-top="1.3cm" margin-bottom="4.5cm">
					<!-- Central part of page -->
					<fo:region-body margin-top="25mm" margin-bottom="10mm"
						margin-right="5pt" />
					<!-- Header -->
					<fo:region-before margin-top="1.3cm" extent="7mm" />
					<!-- Footer -->

					<fo:region-after margin-top="0mm" margin-bottom="40mm"
						extent="7mm" display-align="after" />
				</fo:simple-page-master>

			</fo:layout-master-set>

			<fo:page-sequence master-reference="allPages">
				<!-- Define the contents of the header. -->
				<fo:static-content flow-name="xsl-region-before">
					<xsl:call-template name="report-page-header" />
				</fo:static-content>
				<!-- Define the contents of the footer. -->
				<fo:static-content flow-name="xsl-region-after">
					<xsl:call-template name="page_footer" />
				</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
					1st page
					<xsl:call-template name="page_body_content" />
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>




	<xsl:template name="page_body_content">

		<fo:block-container>
			<fo:block margin-top="-25pt">
				<fo:block font-size="9pt">
					<fo:table table-layout="fixed">
						<fo:table-column column-width="proportional-column-width(100)" />
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell>
									<fo:block margin-top="5pt" space-after="5pt">
										<fo:leader leader-pattern="rule" leader-length="100%"
											rule-thickness="0.5px" />
									</fo:block>
									<fo:block>
										<xsl:value-of select="reportDetails/kelpaReportIntroParagraph" />
									</fo:block>
									<fo:block>
										<fo:leader leader-pattern="rule" leader-length="100%"
											rule-thickness="0.5px" />
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-body>
					</fo:table>
				</fo:block>
			</fo:block>


			<fo:block>
				<fo:table table-layout="fixed">
					<fo:table-column column-width="proportional-column-width(100)" />
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell>
								<fo:block-container>
									<fo:block font-weight="bold" 
										font-size="14pt" text-align="center" margin-top="4pt"
										margin-left="12pt">
										<xsl:text>Overall Proficiency&#58;</xsl:text>
										<xsl:choose>
											<xsl:when test="reportDetails/data/prevLevelString != ''">
												<xsl:text>&#32;</xsl:text><xsl:value-of select="reportDetails/data/prevLevelString" />
											</xsl:when>
											<xsl:otherwise>
												<xsl:text>&#32;Level&#32;</xsl:text><xsl:value-of select="reportDetails/data/level" />
											</xsl:otherwise>
										</xsl:choose>	
									</fo:block>
									<fo:block>
										<fo:table table-layout="fixed">
											<fo:table-column column-width="proportional-column-width(13)" />
											<fo:table-column column-width="proportional-column-width(26)" />
											<fo:table-body>
												<fo:table-row>
													<fo:table-cell>
														<fo:block>
															<xsl:text>&#32;</xsl:text>
														</fo:block>
													</fo:table-cell>
													<fo:table-cell>
														<fo:block>
															<xsl:call-template name="scoreStarPerformanceLevel" />
														</fo:block>
													</fo:table-cell>
												</fo:table-row>
											</fo:table-body>
										</fo:table>
									</fo:block>
									<fo:block  font-size="6pt"
										text-align="center" margin-top="2pt">
										
										<fo:table table-layout="fixed">
											<fo:table-column column-width="proportional-column-width(33)" />
											<fo:table-column column-width="proportional-column-width(34)" />	
											<fo:table-column column-width="proportional-column-width(33)" />	
											<fo:table-body>
												<fo:table-row>
													<fo:table-cell>
														<fo:block>
															<xsl:text>&#32;</xsl:text>
														</fo:block>
													</fo:table-cell>
													<fo:table-cell>
														<fo:block>
														<fo:table table-layout="fixed" text-align="center" >
															<fo:table-column column-width="proportional-column-width(36)" />
															<fo:table-column column-width="proportional-column-width(34)" />
															<fo:table-column column-width="proportional-column-width(30)" />
															<fo:table-body>
																<fo:table-row>
															
																<xsl:for-each select="reportDetails/data/levels/edu.ku.cete.domain.report.LevelDescription">
																	<fo:table-cell>	
																		<xsl:if test="level = 1">
																			<fo:block text-align="right" ><xsl:call-template name="upper-case"><xsl:with-param name="x" select="levelName"/></xsl:call-template></fo:block>
																		</xsl:if>
																		<xsl:if test="level = 2">
																			<fo:block><xsl:call-template name="upper-case"><xsl:with-param name="x" select="levelName"/></xsl:call-template></fo:block>
																		</xsl:if>												
																		<xsl:if test="level = 3">
																			<fo:block  margin-left="10pt" text-align="left" ><xsl:call-template name="upper-case"><xsl:with-param name="x" select="levelName"/></xsl:call-template></fo:block>
																		</xsl:if>
																	</fo:table-cell>																																	
																</xsl:for-each>
																
																</fo:table-row>
															</fo:table-body>											
															</fo:table>		
														</fo:block>
													</fo:table-cell>
													<fo:table-cell>
														<fo:block>
															<xsl:text>&#32;</xsl:text>
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
			</fo:block>

			<fo:block margin-top="10pt">

				<fo:table table-layout="fixed" 
					font-size="8pt">
					<fo:table-column column-width="proportional-column-width(31)" />
					<fo:table-column column-width="proportional-column-width(4)" />
					<fo:table-column column-width="proportional-column-width(30)" />
					<fo:table-column column-width="proportional-column-width(5)" />
					<fo:table-column column-width="proportional-column-width(30)" />
					<fo:table-body>
					
					<fo:table-row>
							
							<xsl:for-each select="reportDetails/data/levels/edu.ku.cete.domain.report.LevelDescription">
								<fo:table-cell text-align="left" padding-left="1pt"
									padding-right="1pt">
									<fo:block>
											<xsl:if test="level = 1">
												<fo:leader space-after="5pt" leader-pattern="rule"
												leader-length="97%" rule-style="solid"
												rule-thickness="4pt"  xsl:use-attribute-sets="level1Color" />
											</xsl:if>
											<xsl:if test="level = 2">
												<fo:leader space-after="5pt" leader-pattern="rule"
												leader-length="97%" rule-style="solid"
												rule-thickness="4pt"  xsl:use-attribute-sets="level2Color" />
											</xsl:if>
											<xsl:if test="level = 3">
												<fo:leader space-after="5pt" leader-pattern="rule"
												leader-length="97%" rule-style="solid"
												rule-thickness="4pt"  xsl:use-attribute-sets="level3Color" />
											</xsl:if>
									</fo:block>
									<fo:block line-height="11px">
										<fo:inline font-weight="bold"><xsl:value-of select="level" />–<xsl:value-of select="levelName" />:
										</fo:inline>
										<xsl:value-of select="levelDescription" />
									</fo:block>
								</fo:table-cell>
								<xsl:if test="position() != last()" >
									<fo:table-cell text-align="center">
										<fo:block white-space-treatment="preserve">
										</fo:block>
									</fo:table-cell>			
								</xsl:if>						
							</xsl:for-each>	
													
						</fo:table-row>
					
					</fo:table-body>
				</fo:table>
			</fo:block>
			<fo:block margin-top="10pt">
				<fo:leader leader-pattern="rule" leader-length="100%"
					rule-thickness="0.5px" />
			</fo:block>
			<fo:block text-align="center" font-weight="bold" font-size="14pt"
				margin-top="8pt">
				<xsl:text>Domain Performance Levels</xsl:text>
			</fo:block>
			
			<xsl:variable name="domainNotTestedLabel" select="reportDetails/domainNotTestedLabel"></xsl:variable>
			<xsl:variable name="progressStatus" select="reportDetails/progressStatus"></xsl:variable>
			<xsl:variable name="domainLevelCount" select="count(reportDetails/domainScoreList/edu.ku.cete.domain.report.StudentReport)"></xsl:variable>
			
			<fo:block margin-top="8pt">
				<fo:table table-layout="fixed">
					<fo:table-column column-width="proportional-column-width(5)" />
					<fo:table-column column-width="proportional-column-width(90)" />
					<fo:table-column column-width="proportional-column-width(5)" />
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell>
								<fo:block>
									<xsl:text>&#32;</xsl:text>
								</fo:block>
							</fo:table-cell>
							<fo:table-cell>
								<fo:table text-align="center" table-layout="fixed" border="1pt solid #3098D0" border-collapse="separate"  border-separation="-1pt" >
									<fo:table-column column-width="proportional-column-width(12)" />
									<fo:table-column column-width="proportional-column-width(12)" />
									<fo:table-column column-width="proportional-column-width(12)" />
									<fo:table-column column-width="proportional-column-width(12)" />
									<fo:table-column column-width="proportional-column-width(12)" />
									<fo:table-column column-width="proportional-column-width(40)" />
									<fo:table-header border="inherit" > 
										<fo:table-row border="inherit" >
											<fo:table-cell border="inherit" text-align="center" padding-left="2pt"
												padding-right="2pt" padding-top="2pt" padding-bottom="0pt"
												number-columns-spanned="1" background-color="#F0F8FC">
												<fo:block></fo:block>
											</fo:table-cell>
											<fo:table-cell border="inherit" padding-left="2pt"
												padding-right="2pt" padding-top="2pt" padding-bottom="0pt"												
												text-align="center"
												number-columns-spanned="4" background-color="#F0F8FC">
												<fo:block text-align="center" font-weight="bold"
													font-size="9pt">DOMAIN SCORE</fo:block>
											</fo:table-cell>
											<fo:table-cell border="inherit" padding-left="2pt"  number-rows-spanned="2"
												padding-right="2pt" padding-top="2pt" padding-bottom="0pt"	display-align = "center"										
												text-align="center" background-color="#F0F8FC">
												<fo:block text-align="center" font-weight="bold" 
													font-size="9pt">Progress Toward Proficiency</fo:block>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="inherit" height="6mm">
											<fo:table-cell border="inherit" padding-left="2pt"
												padding-right="2pt" padding-top="2pt" padding-bottom="0pt"												
												text-align="center" background-color="#F0F8FC">
												<fo:block text-align="left" font-weight="bold"
													font-size="8pt">Year</fo:block>
											</fo:table-cell>
											<fo:table-cell border="inherit" padding-top="2pt" padding-bottom="0pt"												
												text-align="center" background-color="#F0F8FC">
												<fo:block text-align="center" font-weight="bold"
													font-size="8pt">Speaking</fo:block>
											</fo:table-cell>
											<fo:table-cell border="inherit" padding-top="2pt" padding-bottom="0pt"											
												text-align="center" background-color="#F0F8FC">
												<fo:block text-align="center" font-weight="bold"
													font-size="8pt">Writing</fo:block>
											</fo:table-cell>
											<fo:table-cell border="inherit" padding-top="2pt" padding-bottom="0pt"										
												text-align="center" background-color="#F0F8FC">
												<fo:block text-align="center" font-weight="bold"
													font-size="8pt">Listening</fo:block>
											</fo:table-cell>
											<fo:table-cell border="inherit" padding-top="2pt" padding-bottom="0pt"
												text-align="center" background-color="#F0F8FC">
												<fo:block text-align="center" font-weight="bold"
													font-size="8pt">Reading</fo:block>
											</fo:table-cell>									
										</fo:table-row>
									</fo:table-header>

									<fo:table-body border="inherit">
										<xsl:for-each
											select="reportDetails/domainScoreList/edu.ku.cete.domain.report.StudentReport">
											<fo:table-row border="inherit" padding-left="5pt" padding-right="5pt" height="4mm">
												<fo:table-cell border="inherit"
													padding-left="2pt" padding-right="2pt" padding-top="2pt" 
													padding-bottom="0pt" 
													text-align="left">
													<fo:block text-align="left" font-size="8pt">
														<xsl:value-of select="schoolYear" />
													</fo:block>
												</fo:table-cell>
												<fo:table-cell border="inherit"
												    padding-top="2pt"
													padding-bottom="0pt" 
													text-align="center">
													<fo:block text-align="center" font-size="8pt">
														<xsl:choose>
															<xsl:when test="speakingLevel=-1">
																<xsl:value-of select="$domainNotTestedLabel"/>																					
															</xsl:when>
															<xsl:otherwise>
																<xsl:value-of select="speakingLevel" />	
															</xsl:otherwise>
														</xsl:choose>														
													</fo:block>
												</fo:table-cell>
												<fo:table-cell border="inherit"
													padding-top="2pt"
													padding-bottom="0pt" 
													text-align="center">
													<fo:block text-align="center" font-size="8pt">
														<xsl:choose>
															<xsl:when test="writingLevel=-1">
																<xsl:value-of select="$domainNotTestedLabel"/>																					
															</xsl:when>
															<xsl:otherwise>
																<xsl:value-of select="writingLevel" />	
															</xsl:otherwise>
														</xsl:choose>
													</fo:block>
												</fo:table-cell>
												<fo:table-cell border="inherit"
													padding-top="2pt"
													padding-bottom="0pt" 
													text-align="center">
													<fo:block text-align="center" font-size="8pt">
														<xsl:choose>
															<xsl:when test="listeningLevel=-1">
																<xsl:value-of select="$domainNotTestedLabel"/>																					
															</xsl:when>
															<xsl:otherwise>
																<xsl:value-of select="listeningLevel" />	
															</xsl:otherwise>
														</xsl:choose>													
													</fo:block>
												</fo:table-cell>
												<fo:table-cell border="inherit"
													padding-top="2pt"
													padding-bottom="0pt" 
													text-align="center">
													<fo:block text-align="center" font-size="8pt">
														<xsl:choose>
															<xsl:when test="readingLevel=-1">
																<xsl:value-of select="$domainNotTestedLabel"/>																					
															</xsl:when>
															<xsl:otherwise>
																<xsl:value-of select="readingLevel" />	
															</xsl:otherwise>
														</xsl:choose>
													</fo:block>
												</fo:table-cell>
												<fo:table-cell border="inherit"
													padding-top="2pt"
													padding-bottom="0pt" 
													text-align="left">
													<fo:block text-align="center" font-size="8pt">													
														<xsl:choose>
															<xsl:when test="$domainLevelCount=1">																
																<xsl:value-of select="$progressStatus" />
															</xsl:when>
															<xsl:otherwise>															
																<xsl:if test="position() &gt; 1">
																		<xsl:value-of select="$progressStatus" />
																</xsl:if>														
															</xsl:otherwise>
														</xsl:choose>														
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:for-each>
									</fo:table-body>
								</fo:table>
							</fo:table-cell>
							<fo:table-cell>
								<fo:block>
									<xsl:text>&#32;</xsl:text>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>


				</fo:table>
			</fo:block>
			
			<fo:block margin-top="12pt" >
				<xsl:for-each select="reportDetails/domainPerformanceLevelCategoryLists/edu.ku.cete.domain.common.Category">
					<fo:block  font-size="9pt" margin-top="3pt" >
						<xsl:attribute-set name=""></xsl:attribute-set>
							<fo:inline font-weight="bold"><xsl:value-of select="categoryCode" />–<xsl:value-of select="categoryName" /> -&#160;</fo:inline>
						<xsl:value-of select="categoryDescription" />
					</fo:block>
				</xsl:for-each>	
			</fo:block>
									
		</fo:block-container>
	</xsl:template>

	<xsl:template name="page_footer">

		<fo:block>
			<fo:table table-layout="fixed" 
				font-size="8pt">
				<fo:table-column column-width="proportional-column-width(80)" />
				<fo:table-column column-width="proportional-column-width(20)" />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell text-align="left">
							<fo:block-container text-align="left"
								line-height="10px">
								<fo:block margin-top="45pt">
									<fo:leader leader-pattern="rule" leader-length="105%"
										rule-thickness="0.5px" />
								</fo:block>
								<fo:block text-align="left" 
									font-size="8pt">
									<fo:inline font-weight="bold">Additional Resources
									</fo:inline>
								</fo:block>
								<fo:block text-align="left" 
									font-size="8pt">
									For more information about the Kansas English Language Proficiency
									Assessment, and information about
								</fo:block>
								<fo:block text-align="left" 
									font-size="8pt">
									<xsl:text>the Kansas Assessment Program, visit&#160;</xsl:text>
									<fo:inline font-weight="bold">ksassessments.org/kelpa2.
									</fo:inline>
									<xsl:text> For score report information, visit</xsl:text>
								</fo:block>
								<fo:block text-align="left" 
									font-size="8pt" font-weight="bold">
									ksassessments.org/scorereports.
								</fo:block>
								<fo:block margin-top="-4pt" >
									<fo:leader leader-pattern="rule" leader-length="105%"
										rule-thickness="0.5px" />
								</fo:block>
								<fo:block font-size="8pt" text-align="left" margin-top="3pt"  >
									&#169;
									<xsl:value-of
										select="java:format(java:java.text.SimpleDateFormat.new('yyyy'), java:java.util.Date.new())" />
									The University of Kansas
								</fo:block>
							</fo:block-container>
						</fo:table-cell>
						<fo:table-cell text-align="right">
							<fo:block-container text-align="right">
								<fo:block width="100%" height="100%">
									<fo:external-graphic content-width="scale-down-to-fit"
										content-height="scale-down-to-fit"
										inline-progression-dimension.maximum="100%"
										block-progression-dimension.maximum="100%" height="4.58cm"
										width="5.88cm">
										<xsl:attribute name="src"><xsl:value-of
											select="reportDetails/footerLogoPath" /></xsl:attribute>
									</fo:external-graphic>
								</fo:block>
							</fo:block-container>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>




	</xsl:template>


	<xsl:template name="report-page-header">
		<fo:table table-layout="fixed">
			<fo:table-column column-width="proportional-column-width(50)" />
			<fo:table-column column-width="proportional-column-width(22)" />
			<fo:table-column column-width="proportional-column-width(28)" />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell>
						<fo:block  font-weight="bold"
							font-size="11pt" line-height="13.5pt" text-align="left"
							wrap-option="no-wrap">
							STUDENT REPORT:
							<xsl:call-template name="lastName-Header" />, <xsl:call-template name="firstName-Header" />
						</fo:block>
						<fo:block-container 
							font-size="9pt" line-height="12.5pt">
							<xsl:call-template name="headerGradeName" />
							<xsl:call-template name="headerSchoolName" />
							<xsl:call-template name="headerDistrictName" />

						</fo:block-container>
					</fo:table-cell>
					<fo:table-cell text-align="center">
						<fo:block white-space-treatment="preserve">
						</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="end">
						<fo:block-container display-align="center"
							text-align="center" color="#25408f" 
							font-weight="bold" font-size="9pt" margin-left="3.7mm">
							<fo:block  font-weight="bold"
								font-size="9pt" margin-left="25mm" padding-top="2pt">
								<xsl:value-of select="reportDetails/data/schoolYear - 1" />
								<xsl:text>&#8213;</xsl:text>
								<xsl:value-of select="reportDetails/data/schoolYear" />
							</fo:block>
							<fo:block>
								<fo:table>
									<fo:table-body>
										<fo:table-row>
											<fo:table-cell>
												<fo:block-container height="2cm" width="3cm" >
													<fo:block margin-left="14mm" margin-top="-18mm" >
														<fo:external-graphic content-width="scale-down-to-fit"
															content-height="scale-down-to-fit"
															inline-progression-dimension.maximum="100%"
															block-progression-dimension.maximum="100%" height="5cm"
															width="30cm" >
															<xsl:attribute name="src"><xsl:value-of
																select="reportDetails/logoPath" /></xsl:attribute>
														</fo:external-graphic>
													</fo:block>
												</fo:block-container>
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

	<xsl:template name="headerGradeName"> 
    	<xsl:variable name="gradeSubject"><xsl:text>GRADE&#58;&#160;</xsl:text>
    	<xsl:value-of select="reportDetails/data/gradeCode"/>    		
    	</xsl:variable>
    	<fo:block wrap-option="no-wrap">
	    	<xsl:value-of select="$gradeSubject"/> &#47; STATE ID: <xsl:call-template name="stateStudentIdentifier"/>
		</fo:block>
	</xsl:template>	

	<xsl:attribute-set name="level1Color">
		<xsl:attribute name="color">#EC6B38</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="level2Color">
		<xsl:attribute name="color">#EA8F06</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="level3Color">
		<xsl:attribute name="color">#FBC668</xsl:attribute>
	</xsl:attribute-set>
	
			
</xsl:stylesheet>