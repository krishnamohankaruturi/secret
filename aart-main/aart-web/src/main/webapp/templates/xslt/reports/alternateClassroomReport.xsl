<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:fo="http://www.w3.org/1999/XSL/Format" 
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java fo" extension-element-prefixes="saxon">
	
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" font-family="Arial" font-size="10pt">
			
			<fo:layout-master-set>
				<fo:simple-page-master master-name="allPages" page-height="22cm" page-width="28.45cm" margin-top="1.4cm" margin-bottom="1.8cm" margin-left="1.8cm" margin-right="1.8cm">
					
					<!-- Central part of page -->
					<fo:region-body margin-top="42mm" margin-bottom="10mm" margin-right="5pt" />

					<!-- Header -->
					<fo:region-before  />

					<!-- Footer -->
					<fo:region-after display-align="after" />

				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="allPages">

				<!-- Define the contents of the header. -->
				<fo:static-content flow-name="xsl-region-before" >
					<xsl:call-template name="alternateAggregateClassRoomHeaderContent"/>
				</fo:static-content>

				<!-- Define the contents of the footer. -->
				<fo:static-content flow-name="xsl-region-after">				
					<xsl:call-template name="alternateAggregateClassRoomFooterContent"/>
				</fo:static-content>
				
				<!-- Define the contents of the body. -->
				<fo:flow flow-name="xsl-region-body">				 			
				 	<xsl:call-template name="alternateAggregateClassRoomBodyContent"/>		
					<fo:block id="veryClassRoomLastPage"> </fo:block>		
				</fo:flow>
				
			</fo:page-sequence>			
		</fo:root>
	</xsl:template>	
	
	<xsl:template name="alternateAggregateClassRoomBodyContent" >
	
		<fo:block>
			<fo:table border-before-width.conditionality="retain" border-after-width.conditionality="retain" border-top-style="solid" border-bottom-style="solid" 
													border-left-style="solid" border-right-style="solid"
													display-align="center" >
													
												    <fo:table-column column-width="30.5mm" />
													<fo:table-column column-width="22.5mm" />
													<fo:table-column column-width="48.5mm" />
													<fo:table-column column-width="26.5mm" />													
													<fo:table-column column-width="31.0mm" />
													<fo:table-column column-width="29.5mm" />
													<fo:table-column column-width="53.5mm" />	
																									
													 <fo:table-header>
														<fo:table-cell padding-left="4pt" padding-right="1pt" padding-top="0.5pt" display-align="before"
															padding-bottom="0.3pt" border-top-style="solid" 
															border-bottom-style="solid" >
															<fo:block font-size="11.0pt" font-weight="bold" text-align="left">
																<xsl:text>Student Name</xsl:text>
															</fo:block>
														</fo:table-cell>				
														
													
														
														<fo:table-cell padding-left="1pt" padding-right="1pt" padding-top="0.5pt" display-align="before"
															padding-bottom="0.3pt" border-top-style="solid" 
															border-bottom-style="solid" >
															<fo:block font-size="11.0pt" font-weight="bold" text-align="center">
																<xsl:text>Grade</xsl:text>
															</fo:block>
														</fo:table-cell> 
						
						
														<fo:table-cell padding-left="1pt" padding-right="1pt" padding-top="0.5pt" display-align="before"
															padding-bottom="0.3pt" border-top-style="solid" 
															border-bottom-style="solid" >
															<fo:block font-size="11.0pt" font-weight="bold"  text-align="center">
																<xsl:text>Subject</xsl:text>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell padding-left="1pt" padding-right="1pt" padding-top="0.5pt" display-align="before"
															padding-bottom="0.3pt" border-top-style="solid" 
															border-bottom-style="solid" >
															<fo:block font-size="11.0pt" font-weight="bold"  text-align="center">						
																<xsl:text>EEs Tested</xsl:text>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell padding-left="1pt" padding-right="1pt" padding-top="0.5pt" display-align="before"
															padding-bottom="0.3pt" border-top-style="solid" 
															border-bottom-style="solid" >
															<fo:block font-size="11.0pt" font-weight="bold" text-align="center">
																<xsl:text>EEs at or above Target</xsl:text>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell padding-left="1pt" padding-right="1pt" padding-top="0.5pt" display-align="before"
															padding-bottom="0.3pt" border-top-style="solid"  
															border-bottom-style="solid"  wrap-option="wrap">
															<fo:block font-size="11.0pt" font-weight="bold" text-align="center">
																<xsl:text>Skills Mastered</xsl:text>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell padding-left="1pt" padding-right="1pt" padding-top="0.5pt" display-align="before"
															padding-bottom="0.3pt" border-top-style="solid" 
															border-bottom-style="solid" >
															<fo:block font-size="11.0pt" font-weight="bold" text-align="center">
																<xsl:text>Achievement Level</xsl:text>
															</fo:block>
														</fo:table-cell>
																											
													 </fo:table-header>
													 
													 <fo:table-body>
													 
													 	<xsl:for-each select="alternateAggregateReport/alternateAggregateStudents/edu.ku.cete.domain.report.AlternateAggregateStudents">
													 	  
													 	   <fo:table-row  border-top-style="solid" border-bottom-style="solid" keep-together.within-page="always" >
																<fo:table-cell padding-left="4pt" padding-right="1pt" padding-top="0.5pt" display-align="before"
																	padding-bottom="0.3pt" text-align="left" >
																	<fo:block font-size="11.0pt" >
																		<xsl:value-of select="legalLastName" />, <xsl:value-of select="legalFirstName" />
																	</fo:block>
																 </fo:table-cell>
																 
																  <fo:table-cell padding-left="1pt" padding-right="1pt" padding-top="0.5pt" display-align="before"
																	padding-bottom="0.3pt" text-align="center" >
																	<fo:block font-size="11.0pt" >
																		<xsl:value-of select="currentGradelevel" />
																	</fo:block>
																 </fo:table-cell>
																 
																 <fo:table-cell display-align="before" >														
																 <fo:table display-align="center" >
													
																 <fo:table-column column-width="48.5mm" />
																 <fo:table-column column-width="26.5mm" />
																 <fo:table-column column-width="31.0mm" />
																 <fo:table-column column-width="29.5mm" />
																 <fo:table-column column-width="53.5mm" />	
																																																																			
																	<fo:table-body>	
																		<xsl:for-each select="alternateAggregateSubject/edu.ku.cete.domain.report.AlternateAggregateSubject">
																			
																			 <fo:table-row keep-together.within-page="always" >
																				<fo:table-cell padding-left="1pt" padding-right="1pt" padding-top="0.5pt" display-align="before"
																					padding-bottom="0.3pt" text-align="center" >
																					<fo:block font-size="11.0pt" >																					
																						<xsl:choose>
																         					<xsl:when test="subject != ''">
																         						<xsl:value-of select="subject" />
																         					</xsl:when>
																							<xsl:otherwise>&#8212;</xsl:otherwise>
																						</xsl:choose>																					
																					</fo:block>
																				</fo:table-cell>
																				<fo:table-cell padding-left="1pt" padding-right="1pt" padding-top="0.5pt" display-align="before"
																					padding-bottom="0.3pt" text-align="center" >
																					<fo:block font-size="11.0pt"   >
																						<xsl:choose>
																							<xsl:when test="achievementLevel='-'">&#8212;</xsl:when>
																							<xsl:otherwise>
																								<xsl:choose>
																									<xsl:when test="EESTested=0">
																										0																						
																									</xsl:when>
																									<xsl:otherwise>
																										<xsl:value-of select="EESTested" />		
																									</xsl:otherwise>
																								</xsl:choose>																				
																							</xsl:otherwise>
																						</xsl:choose>
																					</fo:block>
																				</fo:table-cell>
																				<fo:table-cell padding-left="1pt" padding-right="1pt" padding-top="0.5pt" display-align="before"
																					padding-bottom="0.3pt" text-align="center" >
																					<fo:block font-size="11.0pt"  >
																						<xsl:choose>
																							<xsl:when test="achievementLevel='-'">&#8212;</xsl:when>
																							<xsl:otherwise>
																								<xsl:choose>
																									<xsl:when test="aboveTarget=0">
																										0																						
																									</xsl:when>
																									<xsl:otherwise>
																										<xsl:value-of select="aboveTarget" />	
																									</xsl:otherwise>
																								</xsl:choose>																					
																							</xsl:otherwise>
																						</xsl:choose>
																					</fo:block>
																				</fo:table-cell>
																				<fo:table-cell padding-left="1pt" padding-right="1pt" padding-top="0.5pt" display-align="before"
																					padding-bottom="0.3pt" text-align="center"  >
																					<fo:block font-size="11.0pt" >
																						<xsl:choose>
																							<xsl:when test="achievementLevel='-'">&#8212;</xsl:when>
																							<xsl:otherwise>
																								<xsl:choose>
																									<xsl:when test="skillsMastered=0">
																										0																						
																									</xsl:when>
																									<xsl:otherwise>
																										<xsl:value-of select="skillsMastered" />	
																									</xsl:otherwise>
																								</xsl:choose>																						
																							</xsl:otherwise>
																						</xsl:choose>
																					</fo:block>
																				</fo:table-cell>
																				<fo:table-cell padding-left="1pt" padding-right="1pt" padding-top="0.5pt" display-align="before"
																					padding-bottom="0.3pt" text-align="center" >
																					<fo:block font-size="11.0pt" >
																						<xsl:choose>
																							<xsl:when test="achievementLevel='-'">&#8212;</xsl:when>
																							<xsl:otherwise>
																								<xsl:value-of select="achievementLevel" />																						
																							</xsl:otherwise>
																						</xsl:choose>
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
									
								<fo:block  font-weight="bold" font-size="11pt" text-align="left" >
									Achievement Levels
								</fo:block>
									
								<fo:block  font-size="11.5pt" text-align="left" margin-top="10pt">
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
	
	
	<xsl:template name="alternateAggregateClassRoomHeaderContent" >
		<fo:table table-layout="fixed">
			<fo:table-column column-width="proportional-column-width(36)" />
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
										<xsl:value-of select="alternateAggregateReport/reportDate"/>
								</fo:block>											
						</fo:block-container>
					</fo:table-cell>
					<fo:table-cell >					
							<fo:block text-align="center"  margin-top="-2pt" font-weight="bold" font-size="12pt"  >
										<xsl:text>End of Year Report Class Results </xsl:text>
										<xsl:value-of select="alternateAggregateReport/reportYear - 1"/>
										<xsl:text >-</xsl:text><xsl:value-of select="substring(alternateAggregateReport/reportYear,3,4)"/>
							</fo:block>
					</fo:table-cell>
					<fo:table-cell >
						<fo:block-container >
							<xsl:call-template name="alternateAggregateClassRoomHeaderLogo"/>
							<fo:block font-size="11pt"  margin-top="-4.9mm"  margin-left="47.0mm"  >
								<fo:inline font-weight="bold">
									<xsl:text>DISTRICT ID</xsl:text>
								</fo:inline>
								<xsl:text>: </xsl:text>	
								<xsl:value-of select="alternateAggregateReport/residenceDistrictIdentifier"/>
							</fo:block>
							<fo:block font-size="11pt"  margin-top="1.0mm" margin-left="47.0mm"   >
									<xsl:call-template name="alternateAggregateClassRoomHeaderState"/>
							</fo:block>	
						</fo:block-container>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>		
		 <fo:table table-layout="fixed" margin-top="-10.8mm" >
			<fo:table-column column-width="proportional-column-width(100)" />		
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell>
						<fo:block-container>
								<fo:block text-align="left" font-size="11pt"  >
										<fo:inline font-weight="bold" >
											<xsl:text>TEACHER NAME</xsl:text>
										</fo:inline>
										<xsl:text>: </xsl:text>
										<xsl:value-of select="alternateAggregateReport/educatorFirstName" />&#160;<xsl:value-of select="alternateAggregateReport/educatorLastName" />
								</fo:block>																	
						</fo:block-container>
					</fo:table-cell>					
				</fo:table-row>
				
				<fo:table-row>
					<fo:table-cell>
						<fo:block-container>
								<fo:block text-align="left" font-size="11pt"  margin-top="1.0mm">
										<fo:inline font-weight="bold" >
											<xsl:text>DISTRICT</xsl:text>
										</fo:inline>
										<xsl:text>: </xsl:text>
										<xsl:value-of select="alternateAggregateReport/districtShortName"/>
								</fo:block>																	
						</fo:block-container>
					</fo:table-cell>					
				</fo:table-row>
				
				<fo:table-row>
					<fo:table-cell>
						<fo:block-container>
								<fo:block text-align="left" font-size="11pt"  margin-top="1.0mm">
										<fo:inline font-weight="bold" >
											<xsl:text>SCHOOL</xsl:text>
										</fo:inline>
										<xsl:text>: </xsl:text>
										<xsl:value-of select="alternateAggregateReport/schoolName"/>
								</fo:block>																	
						</fo:block-container>
					</fo:table-cell>					
				</fo:table-row>
				
			</fo:table-body>
		 </fo:table>
	 
	</xsl:template>	
	
	<xsl:template name="alternateAggregateClassRoomFooterContent" >
	  <fo:block text-align="end" font-size="10pt" margin-left="10mm">
			Page <fo:page-number/> of <fo:page-number-citation ref-id="veryClassRoomLastPage"/>
		</fo:block> 
		
	</xsl:template>	
	
	<xsl:template name="alternateAggregateClassRoomHeaderState">		
			<fo:inline font-weight="bold">
				<xsl:text>STATE</xsl:text>
			</fo:inline>
			<xsl:text>: </xsl:text>	
			<xsl:value-of select="alternateAggregateReport/state"/>
	</xsl:template>
	
	<xsl:template name="alternateAggregateClassRoomHeaderLogo">
		<fo:table>
		<fo:table-body>
		<fo:table-row>
		<fo:table-cell>
		<fo:block-container height="2cm" >
			<fo:block margin-left="55mm"  margin-top="-10.2mm" >
				<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
					inline-progression-dimension.maximum="100%" block-progression-dimension.maximum="100%"
					 height="2.90cm" width="4.8cm"  >
					 <xsl:attribute name="src"><xsl:value-of select="alternateAggregateReport/dlmLogo"/></xsl:attribute>
				</fo:external-graphic>
			</fo:block>
		</fo:block-container>
		</fo:table-cell>
		</fo:table-row>
		</fo:table-body>
		</fo:table>		
	</xsl:template>
	
</xsl:stylesheet>