<?xml version="1.0" encoding="UTF-8"?>
<!-- Licensed to the Apache Software Foundation (ASF) under one or more contributor 
	license agreements. See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership. The ASF licenses this file to 
	You under the Apache License, Version 2.0 (the "License"); you may not use 
	this file except in compliance with the License. You may obtain a copy of 
	the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required 
	by applicable law or agreed to in writing, software distributed under the 
	License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS 
	OF ANY KIND, either express or implied. See the License for the specific 
	language governing permissions and limitations under the License. -->
<!-- $Id$ -->
<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java fo">
	
	<xsl:output method="xml" version="1.0" omit-xml-declaration="no"
		indent="yes" />

	<!-- ========================= -->
	<!-- root element: projectteam -->
	<!-- ========================= -->
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
			<xsl:variable name="width" select="ItemReport/width"></xsl:variable>
				<fo:simple-page-master master-name="simpleA4"
					page-height="10in" page-width="{$width}" margin-top="0.30in"
					margin-bottom="0.30in">

					<!-- Central part of page -->
					<fo:region-body column-count="1" column-gap="0.0in"
						margin-top="0.30in" margin-bottom="0.30in" margin-left="0.30in"
						margin-right="0.03in" />

					<!-- Header -->
					<fo:region-before extent="0.25in" />

					<!-- Footer -->
					<fo:region-after extent="0.25in" />


				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="simpleA4">


				<!-- Define the contents of the header. -->
				<fo:static-content flow-name="xsl-region-before">
					<fo:block font-size="8.0pt" font-family="serif"
						padding-after="2.0pt" space-before="4.0pt" text-align="right"
						border-bottom-style="solid" border-bottom-width="1.0pt">

						<xsl:value-of
							select="java:format(java:java.text.SimpleDateFormat.new('dd MMMMMMMMMMMM yyyy hh:mm:ss a'), java:java.util.Date.new())" />
						<xsl:text>&#xA0;&#xA0;&#xA0;</xsl:text>
					</fo:block>
				</fo:static-content>


				<!-- Define the contents of the footer. -->
				<fo:static-content flow-name="xsl-region-after">
					<fo:block font-size="8.0pt" font-family="sans-serif"
						padding-after="2.0pt" space-before="4.0pt" text-align="center"
						border-top-style="solid" border-bottom-width="1.0pt">
						<xsl:text>Page </xsl:text>
						<fo:page-number />
					</fo:block>
				</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
				<fo:block  margin-top="-40px" text-align="center" >
               	
                       <xsl:text font-weight="bold">Item Report For </xsl:text>
                       
                	 <xsl:variable name="testName" select="ItemReport/interimTestItems/InterimTestItems/testName"></xsl:variable>
                	  
                	 <fo:inline font-style="italic" font-family="sans-serif" font-size="12.0pt">
                	
                    <xsl:value-of  select="$testName"/> 
                   
                	 </fo:inline>
                	 </fo:block>
                	 <fo:block  text-align="center" >
                	 <xsl:variable name="scheduledDate" select="ItemReport/scheduledDate"></xsl:variable>
                      <xsl:variable name="scheduledTime" select="ItemReport/scheduledTime"></xsl:variable>
                      
                	 <fo:inline  font-family="sans-serif" font-size="12.0pt">
                	  <xsl:value-of  select="$scheduledDate"/>
                	  </fo:inline>
                      
                	 <fo:inline  font-family="sans-serif" font-size="12.0pt">
                	
                    <xsl:value-of  select="$scheduledTime"/>
                     
                   </fo:inline>
                   
                	 </fo:block>
				
 
                        <fo:block text-align="center">
						<fo:table border-top-style="none" border-bottom-style="none"
							border-left-style="solid" border-right-style="none"
							display-align="center" margin-top="15px">
							<fo:table-column column-width="10mm" />
							<fo:table-column column-width="10mm" />
							<fo:table-column column-width="10mm" />
							<fo:table-column column-width="10mm" />
							<fo:table-column column-width="10mm" />
							<fo:table-column column-width="10mm" />
							<fo:table-column column-width="10mm" />
							<fo:table-column column-width="10mm" />
							<fo:table-column column-width="10mm" />
							<fo:table-column column-width="10mm" />
							<fo:table-column column-width="10mm" />
							<fo:table-column column-width="10mm" />
							<fo:table-column column-width="10mm" />
							<fo:table-column column-width="10mm" />
							<fo:table-column column-width="10mm" />
							<fo:table-column column-width="10mm" />
							<fo:table-column column-width="10mm" />
							<fo:table-column column-width="10mm" />
							<!-- <xsl:for-each select="ItemReport/interimTestItems/InterimTestItems[1]/responses/InterimReportTasks">
								<fo:table-column column-width="10mm" />
							</xsl:for-each> -->
							
							<xsl:for-each select="ItemReport/interimTestItems/InterimTestItems/responses/InterimReportTasks">
								<fo:table-column column-width="10mm" />
							</xsl:for-each>
					
							
							<xsl:for-each select="ItemReport/interimTestItems/InterimTestItems">
								<fo:table-column column-width="10mm" />
							</xsl:for-each>
							<xsl:for-each select="ItemReport/interimTestItems/InterimTestItems/responses/InterimReportTasks">
								<fo:table-column column-width="10mm" />
							</xsl:for-each>
							

							<fo:table-header >
								<fo:table-cell background-color="#1a77bb"
									number-columns-spanned="2.1" padding-left="2pt" padding-right="2pt"
									padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
									border-bottom-style="solid" border-left-style="solid" border-right-style="solid" 
									border-left="2px solid #000000" 
									border-right="2px solid #000000" border-top="2px solid #000000" 
									border-bottom="2px solid #000000" 
									keep-together.within-column="always">
									
									<fo:block font-size="10.0pt" font-family="sans-serif"
										font-weight="bold" text-align="center">
										<xsl:text>Item Number</xsl:text>
									</fo:block>
								</fo:table-cell>

								<fo:table-cell background-color="#f4be49"
									number-columns-spanned="2" padding-left="2pt" padding-right="2pt"
									padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
									border-bottom-style="solid" border-left-style="solid"
									border-right-style="solid" 
									border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
									<fo:block font-size="10.0pt" font-family="sans-serif"
										font-weight="bold">
										<xsl:text>Alignment</xsl:text>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell background-color="#f4be49"
									number-columns-spanned="1.5" padding-left="2pt" padding-right="2pt"
									padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
									border-bottom-style="solid" border-left-style="solid"
									border-right-style="solid" border-left="2px solid #000000" border-right="2px solid #000000"
									 border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
									<fo:block font-size="10.0pt" font-family="sans-serif"
										font-weight="bold">

										<xsl:text>Item Type</xsl:text>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell background-color="#f4be49"
									number-columns-spanned="2.1" padding-left="2pt" padding-right="2pt"
									padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
									border-bottom-style="solid" border-left-style="solid"
									border-right-style="solid" border-left="2px solid #000000"
									 border-top="2px solid #000000" 
											border-bottom="2px solid #000000"
									 border-right="2px solid #000000"  keep-together.within-column="always">
									<fo:block font-size="10.0pt" font-family="sans-serif"
										font-weight="bold">
										<xsl:text>Scoring Type</xsl:text>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell background-color="#f4be49"
									number-columns-spanned="2" padding-left="2pt" padding-right="2pt"
									padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
									border-bottom-style="solid" border-left-style="solid"
									border-right-style="solid" border-left="2px solid #000000" 
									border-right="2px solid #000000"
									 border-top="2px solid #000000" 
											border-bottom="2px solid #000000"  wrap-option="wrap">
									<fo:block font-size="10.0pt" font-family="sans-serif"
										font-weight="bold" wrap-option="wrap">
										<xsl:text>Total Correct Responses</xsl:text>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell background-color="#f4be49"
									number-columns-spanned="2" padding-left="2pt" padding-right="2pt"
									padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
									border-bottom-style="solid" border-left-style="solid"  border-right-style="solid" 
									border-left="2px solid #000000" border-right="2px solid #000000" 
									 border-top="2px solid #000000" 
											border-bottom="2px solid #000000"
									keep-together.within-column="always">
									
									<fo:block font-size="10.0pt" font-family="sans-serif"
										font-weight="bold">
										<xsl:text>Total Correct %</xsl:text>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell background-color="#f4be49"
									number-columns-spanned="2" padding-left="2pt" padding-right="2pt"
									padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
									border-bottom-style="solid" border-left-style="solid"
									border-right-style="solid" border-left="2px solid #000000"
									border-right="2px solid #000000" 
									 border-top="2px solid #000000" 
											border-bottom="2px solid #000000"
									keep-together.within-column="always">
									<fo:block font-size="10.0pt" font-family="sans-serif"
										font-weight="bold">
										<xsl:text>Total InCorrect Responses</xsl:text>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell background-color="#f4be49"
									number-columns-spanned="2" padding-left="2pt" padding-right="2pt"
									padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
									border-bottom-style="solid" border-left-style="solid"
									border-right-style="solid" border-left="2px solid #000000" 
									border-right="2px solid #000000"
									 border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
									<fo:block font-size="10.0pt" font-family="sans-serif"
										font-weight="bold">
										<xsl:text>Total InCorrect %</xsl:text>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell background-color="#f4be49"
									number-columns-spanned="1.5" padding-left="2pt" padding-right="2pt"
									padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
									border-bottom-style="solid" border-left-style="solid" border-right-style="solid"
									border-left="2px solid #000000" border-right="2px solid #000000"
									border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
									<fo:block font-size="10.0pt" font-family="sans-serif"
										font-weight="bold">
										<xsl:text>Max Score</xsl:text>
									</fo:block>
								</fo:table-cell>
 
 				 <xsl:for-each select="ItemReport/interimTestItems/InterimTestItems[1]/responses/InterimReportTasks">
 
                                  <fo:table-cell background-color="#f4be49"
										number-columns-spanned="1.5" padding-left="2pt" padding-right="2pt"
										padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
										border-bottom-style="solid" border-left-style="solid"
										border-right-style="solid" border-left="2px solid #000000" border-right="2px solid #000000"
										border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
										
										<fo:block font-size="10.0pt" font-family="sans-serif"
											font-weight="bold">
											<xsl:text>Response</xsl:text>
											
										</fo:block>
										
									</fo:table-cell>
									
									<fo:table-cell background-color="#f4be49"
										number-columns-spanned="1.5" padding-left="2pt" padding-right="2pt"
										padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
										border-bottom-style="solid" border-left-style="solid"
										border-right-style="solid" border-left="2px solid #000000" 
										border-right="2px solid #000000"
										border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
										<fo:block font-size="10.0pt" font-family="sans-serif"
											font-weight="bold">
											<xsl:text># Responses</xsl:text>

										</fo:block>
									</fo:table-cell>

									<fo:table-cell background-color="#f4be49"
										number-columns-spanned="1.6" padding-left="2pt" padding-right="2pt"
										padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
										border-bottom-style="solid" border-left-style="solid"
										border-right-style="solid" border-left="2px solid #000000" 
										border-right="2px solid #000000"
										border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
										<fo:block font-size="10.0pt" font-family="sans-serif"
											font-weight="bold">
											<xsl:text>% Responses</xsl:text>

										</fo:block>
									</fo:table-cell>
								
								
							</xsl:for-each>
							
							</fo:table-header>
 <xsl:for-each select="ItemReport/interimTestItems">
							<fo:table-body>
								<!-- <fo:table-row>
									<fo:table-cell>
										<fo:block font-size="10.0pt" font-family="sans-serif"
											font-weight="bold">

										</fo:block>
									</fo:table-cell>
								</fo:table-row> -->
								
								
								<xsl:for-each select="InterimTestItems">
									<fo:table-row>
										<fo:table-cell number-columns-spanned="2"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid" border-left="2px solid #000000" 
										border-right="2px solid #000000"
										border-top="2px solid #000000" 
											border-bottom="2px solid #000000"> 
											<fo:block font-size="10.0pt" font-family="sans-serif"
												font-weight="bold">
												<xsl:value-of select="itemName" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell number-columns-spanned="2"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid" border-left="2px solid #000000" 
										border-right="2px solid #000000"
										border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif">
												<xsl:value-of select="allignment" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell number-columns-spanned="1.5"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid"
											border-left="2px solid #000000" 
										border-right="2px solid #000000"
										border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif">
												<xsl:value-of select="itemType" />
											</fo:block>
										</fo:table-cell>

										<fo:table-cell number-columns-spanned="2.1"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid"
											border-left="2px solid #000000" 
										border-right="2px solid #000000"
										border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif">
												<xsl:value-of select="ScoringType" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell number-columns-spanned="2"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid" border-left="2px solid #000000" 
										border-right="2px solid #000000"
										border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif">
												<xsl:value-of select="totalCorrectResponses" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell number-columns-spanned="2"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid" border-left="2px solid #000000" 
										border-right="2px solid #000000"
										border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif">
												<xsl:value-of select="totalCorrect" />
											</fo:block>
										</fo:table-cell>

										<fo:table-cell number-columns-spanned="2"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid" border-left="2px solid #000000" 
										border-right="2px solid #000000"
										border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif">
												<xsl:value-of select="InterimReportTasksme" />
											</fo:block>
										</fo:table-cell>

										<fo:table-cell number-columns-spanned="2"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid" border-left="2px solid #000000" 
										border-right="2px solid #000000"
										border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif">
												<xsl:value-of select="totalInCorrect" />
											</fo:block>
										</fo:table-cell>


										<fo:table-cell number-columns-spanned="1.5"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid" border-left="2px solid #000000" 
										border-right="2px solid #000000"
										border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif">
												<xsl:value-of select="maxScore" />
											</fo:block>
										</fo:table-cell>

										<xsl:for-each select="responses/InterimReportTasks">

											<fo:table-cell number-columns-spanned="1.5"
												padding-left="2pt" padding-right="2pt" padding-top="2pt"
												padding-bottom="2pt" border-top-style="solid"
												border-bottom-style="solid" border-left-style="solid"
												border-right-style="solid" border-left="2px solid #000000" 
										border-right="2px solid #000000"
										border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
												<fo:block font-size="10.0pt" font-family="sans-serif">
													<xsl:value-of select="response" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell number-columns-spanned="1.5"
												padding-left="2pt" padding-right="2pt" padding-top="2pt"
												padding-bottom="2pt" border-top-style="solid"
												border-bottom-style="solid" border-left-style="solid"
												border-right-style="solid" border-left="2px solid #000000" 
										border-right="2px solid #000000"
										border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
												<fo:block font-size="10.0pt" font-family="sans-serif">
													<xsl:value-of select="responseNo" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell number-columns-spanned="1.6"
												padding-left="2pt" padding-right="2pt" padding-top="2pt"
												padding-bottom="2pt" border-top-style="solid"
												border-bottom-style="solid" border-left-style="solid"
												border-right-style="solid" border-left="2px solid #000000" 
										border-right="2px solid #000000"
										border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
												<fo:block font-size="10.0pt" font-family="sans-serif">
													<xsl:value-of select="responsePercent" />
												</fo:block>
											</fo:table-cell>

										</xsl:for-each>


									</fo:table-row>

								</xsl:for-each>
								
							</fo:table-body>
							</xsl:for-each>
						</fo:table>
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
</xsl:stylesheet>