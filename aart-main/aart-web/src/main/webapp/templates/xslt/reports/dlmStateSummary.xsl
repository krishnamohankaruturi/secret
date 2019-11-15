<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:fo="http://www.w3.org/1999/XSL/Format" 
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java fo" extension-element-prefixes="saxon">
	
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" font-family="Arial" font-size="10pt">
			
			<fo:layout-master-set>
				<fo:simple-page-master master-name="allPages" page-height="22cm" page-width="27.92cm" margin-top="1.4cm" margin-bottom="1.8cm" margin-left="2.5cm" margin-right="2.5cm">
					
					<!-- Central part of page -->
					<fo:region-body margin-top="34mm" margin-right="5pt" margin-bottom="10mm" />

					<!-- Header -->
					<fo:region-before />

					<!-- Footer -->
					<fo:region-after display-align="after" />

				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="allPages">

				<!-- Define the contents of the header. -->
				<fo:static-content flow-name="xsl-region-before" >
					<xsl:call-template name="dlmStateSummmaryHeaderContent"/>
				</fo:static-content>

				<!-- Define the contents of the footer. -->
				<fo:static-content flow-name="xsl-region-after">				
					<xsl:call-template name="dlmStateSummmaryFooterContent"/>
				</fo:static-content>
				
				<!-- Define the contents of the body. -->
				<fo:flow flow-name="xsl-region-body">						
				 	<xsl:call-template name="dlmStateSummmaryBodyContent"/>		
					<fo:block id="veryStateLastPage"> </fo:block>
				</fo:flow>
				
			</fo:page-sequence>			
		</fo:root>
	</xsl:template>	
	
	<xsl:template name="dlmStateSummmaryBodyContent" >
	
		<fo:block >
		
			<fo:table border-before-width.conditionality="retain" border-after-width.conditionality="retain"  border-top-style="solid" border-bottom-style="solid"
													border-left-style="solid" border-right-style="solid"
													display-align="center"  >
													<fo:table-column column-width="24.5mm" />
													<fo:table-column column-width="50.5mm" />
													<fo:table-column column-width="24.5mm" />
													<fo:table-column column-width="24.5mm" />
													<fo:table-column column-width="24.5mm" />
													<fo:table-column column-width="24.5mm" />
													<fo:table-column column-width="24.5mm" />
													<fo:table-column column-width="24.5mm" />
													 <fo:table-header>
														<fo:table-cell padding-left="1pt" padding-right="1pt"
															padding-top="0.5pt" display-align="before" padding-bottom="0.3pt" border-top-style="solid"
															border-bottom-style="solid" >
															<fo:block font-size="11.0pt" font-weight="bold" text-align="center">
																<xsl:text>Grade</xsl:text>
															</fo:block>
														</fo:table-cell>
						
														<fo:table-cell padding-left="1pt" padding-right="1pt"
															padding-top="0.5pt" display-align="before" padding-bottom="0.3pt" border-top-style="solid"
															border-bottom-style="solid" >
															<fo:block font-size="11.0pt" font-weight="bold"  text-align="center">
																<xsl:text>Subject</xsl:text>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell padding-left="1pt" padding-right="1pt"
															padding-top="0.5pt" display-align="before" padding-bottom="0.3pt" border-top-style="solid"
															border-bottom-style="solid" >
															<fo:block font-size="11.0pt" font-weight="bold"  text-align="center">						
																<xsl:text>Number of Students Tested</xsl:text>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell padding-left="1pt" padding-right="1pt"
															padding-top="0.5pt" display-align="before" padding-bottom="0.3pt" border-top-style="solid"
															border-bottom-style="solid" >
															<fo:block font-size="11.0pt" font-weight="bold" text-align="center">
																<xsl:text>Emerging</xsl:text>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell padding-left="1pt" padding-right="1pt"
															padding-top="0.5pt" display-align="before" padding-bottom="0.3pt" border-top-style="solid"
															border-bottom-style="solid" wrap-option="wrap">
															<fo:block font-size="11.0pt" font-weight="bold" text-align="center">
																<xsl:text>Approaching Target</xsl:text>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell padding-left="1pt" padding-right="1pt"
															padding-top="0.5pt" display-align="before" padding-bottom="0.3pt" border-top-style="solid"
															border-bottom-style="solid" >
															<fo:block font-size="11.0pt" font-weight="bold" text-align="center">
																<xsl:text>At Target</xsl:text>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell padding-left="1pt" padding-right="1pt"
															padding-top="0.5pt" display-align="before" padding-bottom="0.3pt" border-top-style="solid"
															border-bottom-style="solid" >
															<fo:block font-size="11.0pt" font-weight="bold" text-align="center">
																<xsl:text>Advanced</xsl:text>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell padding-left="1pt" padding-right="1pt"
															padding-top="0.5pt" display-align="before" padding-bottom="0.3pt" border-top-style="solid"
															border-bottom-style="solid">
															<fo:block font-size="11.0pt" font-weight="bold" text-align="center">
																<xsl:text>At Target or Advanced</xsl:text>
															</fo:block>
														</fo:table-cell>														
													 </fo:table-header>
													 
													 <fo:table-body>
													 
													 	<xsl:for-each select="dlmSummaryReport/orgGrfCalculation/orgSummaryGradeLists/edu.ku.cete.domain.report.DLMOrganizationSummaryGrade">
													 	  
													 	   <fo:table-row  border-top-style="solid"  keep-together.within-page="always" >
																<fo:table-cell padding-left="1pt" padding-right="1pt" padding-top="0.3pt" display-align="before" 
																	padding-bottom="0.3pt" text-align="center" >
																	<fo:block font-size="11.0pt" >
																		<xsl:value-of select="gradeLevel" />
																	</fo:block>
																 </fo:table-cell>
																 
																 <fo:table-cell>														
																 <fo:table display-align="center"  >
																	<fo:table-column column-width="50.5mm" />
																	<fo:table-column column-width="24.5mm" />
																	<fo:table-column column-width="24.5mm" />
																	<fo:table-column column-width="24.5mm" />
																	<fo:table-column column-width="24.5mm" />
																	<fo:table-column column-width="24.5mm" />
																	<fo:table-column column-width="24.5mm" />																																																	
																	<fo:table-body>	
																		<xsl:for-each select="orgSummarySubjectLists/edu.ku.cete.domain.report.DLMOrganizationSummarySubject">
																			
																			 <fo:table-row  keep-together.within-page="always" >
																				<fo:table-cell padding-left="1pt" padding-right="1pt" padding-top="0.3pt" display-align="before" 
																					padding-bottom="0.3pt" text-align="center" >
																					<fo:block font-size="11.0pt" >
																						<xsl:value-of select="subjectName" />
																					</fo:block>
																				</fo:table-cell>
																				<fo:table-cell padding-left="1pt" padding-right="1pt" padding-top="0.3pt" display-align="before" 
																					padding-bottom="0.3pt" text-align="center" >
																					<fo:block font-size="11.0pt" >
																						<xsl:value-of select="noOfStudentsTested" />
																					</fo:block>
																				</fo:table-cell>
																				<fo:table-cell padding-left="1pt" padding-right="1pt" padding-top="0.3pt" display-align="before" 
																					padding-bottom="0.3pt" text-align="center" >
																					<fo:block font-size="11.0pt" >
																						 <xsl:value-of select="numberOfEmerging" />
																					</fo:block>
																				</fo:table-cell>
																				<fo:table-cell padding-left="1pt" padding-right="1pt" padding-top="0.3pt" display-align="before" 
																					padding-bottom="0.3pt" text-align="center" >
																					<fo:block font-size="11.0pt" >
																						<xsl:value-of select="numberOfApproachingTarget" />
																					</fo:block>
																				</fo:table-cell>
																				<fo:table-cell padding-left="1pt" padding-right="1pt" padding-top="0.3pt" display-align="before" 
																					padding-bottom="0.3pt" text-align="center" >
																					<fo:block font-size="11.0pt" >
																						<xsl:value-of select="numberOfAtTarget" />
																					</fo:block>
																				</fo:table-cell>
																				<fo:table-cell padding-left="1pt" padding-right="1pt" padding-top="0.3pt" display-align="before" 
																					padding-bottom="0.3pt" text-align="center" >
																					<fo:block font-size="11.0pt" >
																						<xsl:value-of select="numberOfAdvanced" />
																					</fo:block>
																				</fo:table-cell>
																				<fo:table-cell padding-left="1pt" padding-right="1pt" padding-top="0.3pt" display-align="before" 
																					padding-bottom="0.3pt" text-align="center" >
																					<fo:block font-size="11.0pt" >
																						<xsl:value-of select="percentageAtTargetAdvanced" />%
																					</fo:block>
																				  </fo:table-cell>
																				</fo:table-row>		
																																																								
																			  </xsl:for-each>	
																       </fo:table-body>
																   </fo:table>
															  </fo:table-cell>
															</fo:table-row>
													 	  
													 	 </xsl:for-each> 
														
													 </fo:table-body>
										</fo:table>	
							</fo:block>	
						
							<fo:block-container margin-left="2.2pt"  margin-right="2.2pt" margin-top="50pt" page-break-inside="avoid"  >
								
								<fo:block  font-weight="bold" font-size="11pt" text-align="left"  >
									Achievement Levels
								</fo:block>
								
								<fo:block  font-size="11.5pt" text-align="left" margin-top="10pt" >
									<xsl:text>The student demonstrates </xsl:text>
									<fo:inline font-weight="bold" font-style="italic" ><xsl:text>emerging </xsl:text></fo:inline>
									<xsl:text>understanding of and ability to apply content knowledge and skills represented by the Essential Elements.</xsl:text>
								</fo:block>
																						
								<fo:block  font-size="11.5pt" text-align="left" margin-top="10pt">
									<xsl:text>The student’s understanding of and ability to apply targeted content knowledge and skills represented by the Essential Elements is </xsl:text>
									<fo:inline font-weight="bold" font-style="italic" ><xsl:text>approaching the target</xsl:text></fo:inline><xsl:text>.</xsl:text>
								</fo:block>
								
								<fo:block  font-size="11.5pt" text-align="left" margin-top="10pt">
									<xsl:text>The student’s understanding of and ability to apply content knowledge and skills represented by the Essential Elements is </xsl:text>
									<fo:inline font-weight="bold" font-style="italic" ><xsl:text>at target</xsl:text></fo:inline><xsl:text>.</xsl:text>
								</fo:block>
								
								<fo:block  font-size="11.5pt" text-align="left" margin-top="10pt">
									<xsl:text>The student demonstrates </xsl:text>
									<fo:inline font-weight="bold" font-style="italic" ><xsl:text>advanced </xsl:text></fo:inline>
									<xsl:text>understanding of and ability to apply targeted content knowledge and skills represented by the Essential Elements.</xsl:text>
								</fo:block>
									
							</fo:block-container>					
							
							
	</xsl:template>
	
	
	<xsl:template name="dlmStateSummmaryHeaderContent" >
		<fo:table table-layout="fixed">
			<fo:table-column column-width="proportional-column-width(41)" />
			<fo:table-column column-width="proportional-column-width(22)" />
			<fo:table-column column-width="proportional-column-width(37)" />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell>
						<fo:block-container>
								<fo:block text-align="left" font-size="9pt"  >
										<fo:inline font-weight="bold" >
											<xsl:text>REPORT DATE</xsl:text>
										</fo:inline>
										<xsl:text>: </xsl:text>
										<xsl:value-of select="dlmSummaryReport/reportDate"/>
								</fo:block>											
						</fo:block-container>
					</fo:table-cell>
					<fo:table-cell >					
							<fo:block text-align="center" font-weight="bold" font-size="12pt"  >
										<xsl:text>End of Year Report State Results </xsl:text>
										<xsl:value-of select="dlmSummaryReport/orgGrfCalculation/reportYear - 1"/>
										<xsl:text >-</xsl:text><xsl:value-of select="substring(dlmSummaryReport/orgGrfCalculation/reportYear,3,4)"/>
							</fo:block>
					</fo:table-cell>
					<fo:table-cell >
						<fo:block-container >
							<xsl:call-template name="dlmStateSummaryHeaderLogo"/>		
						</fo:block-container>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
		
		<fo:table table-layout="fixed"  margin-top="-5.0mm" >
			<fo:table-column column-width="proportional-column-width(35)" />
			<fo:table-column column-width="proportional-column-width(35)" />
			<fo:table-column column-width="proportional-column-width(30)" />
			<fo:table-body>
				<fo:table-row>				
					<fo:table-cell text-align="center">
						<fo:block white-space-treatment="preserve">
						</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center">
						<fo:block white-space-treatment="preserve">
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
						<fo:block margin-left="88pt"  >
							<xsl:call-template name="dlmStateSummmaryHeaderState"/>
						</fo:block>
					</fo:table-cell>					
				</fo:table-row>
			</fo:table-body>
		</fo:table>
		
		
		
	</xsl:template>	
	
	<xsl:template name="dlmStateSummmaryFooterContent" >
	 	<fo:block text-align="end" font-size="10pt" >
			Page <fo:page-number/> of <fo:page-number-citation ref-id="veryStateLastPage"/>
		</fo:block>
	</xsl:template>	
	
	<xsl:template name="dlmStateSummmaryHeaderState">
		<fo:block font-size="11pt"  >
			<fo:inline font-weight="bold">
				<xsl:text>STATE</xsl:text>
			</fo:inline>
			<xsl:text>: </xsl:text>	
			<xsl:value-of select="dlmSummaryReport/orgGrfCalculation/stateName"/>
		</fo:block>
	</xsl:template>
	
	<xsl:template name="dlmStateSummaryHeaderLogo">
		<fo:table>
		<fo:table-body>
		<fo:table-row>
		<fo:table-cell>
		<fo:block-container height="2cm" >
			<fo:block margin-left="48mm"  margin-top="-8.4mm" >
				<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
					inline-progression-dimension.maximum="100%" block-progression-dimension.maximum="100%"
					 height="2.88cm" width="4.4cm"  >
					 <xsl:attribute name="src"><xsl:value-of select="dlmSummaryReport/dlmLogo"/></xsl:attribute>
				</fo:external-graphic>
			</fo:block>
		</fo:block-container>
		</fo:table-cell>
		</fo:table-row>
		</fo:table-body>
		</fo:table>		
	</xsl:template>
	
</xsl:stylesheet>