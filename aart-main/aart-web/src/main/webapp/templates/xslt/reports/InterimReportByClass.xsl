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
			<xsl:variable name="width" select="interimClassResults/width"></xsl:variable>
				<fo:simple-page-master master-name="simpleA4"
					page-height="9.27in"  page-width="{$width}" margin-top="0.30in"
					margin-bottom="0.30in">
					<!-- Central part of page -->
					<fo:region-body  column-count="1" column-gap="0.0in"
						margin-top="0.30in" margin-bottom="0.30in" margin-left="0.20in"/>					

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
                <fo:block  margin-top="-25px" text-align="center" >
               	 <xsl:text font-weight="bold">Test Summary For </xsl:text>
                    <xsl:variable name="testName" select="interimClassResults/testName"></xsl:variable>
                	 <fo:inline font-style="italic" font-family="sans-serif" font-size="12.0pt">
                	<xsl:value-of  select="$testName"/> 
                   </fo:inline>
                	 </fo:block>
                	 
					<fo:block text-align="center">
						<fo:table  border-top-style="solid" border-bottom-style="solid" margin-top="15px" border-left-style="solid" border-right-style="solid" display-align="center">
							<fo:table-column column-width="16mm" />
					       	<fo:table-column column-width="16mm" />
					       	<fo:table-column column-width="16mm" />
					       	<fo:table-column column-width="16mm" />
					        <fo:table-column column-width="16mm" />
					       	<fo:table-column column-width="16mm" />
					     	<fo:table-column column-width="16mm" />
					        <fo:table-column column-width="16mm" />
					        
					       <fo:table-header>
					      <fo:table-cell  background-color="#A9A9A9" number-columns-spanned="8" padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" 
					      border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid" 
					      border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
								<fo:block font-size="10.0pt" font-family="sans-serif" font-weight="bold" text-align="center">
									<xsl:text>Test Information</xsl:text>
								</fo:block>
								</fo:table-cell>
								</fo:table-header>
                               <fo:table-body>				              
							<fo:table-row>
								<xsl:variable name="startDate" select="interimClassResults/startDate"></xsl:variable>
								<fo:table-cell number-columns-spanned="4" padding-left="15pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" 
								border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid"
								border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000" >
								<fo:block font-size="10.0pt" font-family="sans-serif" font-weight="bold" text-align="left">							
									<xsl:text>Start Date</xsl:text>
								</fo:block>
								</fo:table-cell>
								
								<fo:table-cell number-columns-spanned="4"  padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt"
								 border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid"
								 border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
										<fo:block font-size="10.0pt" font-family="sans-serif">
											 <xsl:value-of select="$startDate"/>
										</fo:block>
									</fo:table-cell>
									</fo:table-row>
									
									<fo:table-row>
								<xsl:variable name="endDate" select="interimClassResults/endDate"></xsl:variable>
								
								<fo:table-cell number-columns-spanned="4" padding-left="15pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt"
								 border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid" 
								 border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
								<fo:block font-size="10.0pt" font-family="sans-serif" font-weight="bold" text-align="left">							
									<xsl:text>End Date</xsl:text>
								</fo:block>
								</fo:table-cell>
								
								<fo:table-cell number-columns-spanned="4"  padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" 
								border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid"
								border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
										<fo:block font-size="10.0pt" font-family="sans-serif">
											 <xsl:value-of select="$endDate"/>
										</fo:block>
									</fo:table-cell>
									</fo:table-row>
									
									<fo:table-row>
								   <xsl:variable name="totalparticipants" select="interimClassResults/totalparticipants"></xsl:variable>
									<fo:table-cell number-columns-spanned="4" padding-left="15pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt"
									 border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid" 
									 border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
										<fo:block font-size="10.0pt" font-family="sans-serif" font-weight="bold" text-align="left">							
											<xsl:text>Total participants</xsl:text>
										</fo:block>
									</fo:table-cell>
								
								<fo:table-cell number-columns-spanned="4"  padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" 
								border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid"
								border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
										<fo:block font-size="10.0pt" font-family="sans-serif">
											 <xsl:value-of select="$totalparticipants"/>
										</fo:block>
									</fo:table-cell>
									</fo:table-row>
									
									<fo:table-row>
									<xsl:variable name="classHighestpointsByPercentage" select="interimClassResults/classHighestpointsByPercentage"></xsl:variable>
								
									<fo:table-cell number-columns-spanned="4" padding-left="15pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt"
									 border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid"
									 border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000" >
										<fo:block font-size="10.0pt" font-family="sans-serif" font-weight="bold" text-align="left">							
										<xsl:text>Highest Points / Highest Percentage</xsl:text>
										</fo:block>
									</fo:table-cell>
								
									<fo:table-cell number-columns-spanned="4" space-after="8.0pt" padding-left="2pt" padding-right="2pt" padding-top="2pt" 
									padding-bottom="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid"
									border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
										<fo:block font-size="10.0pt" font-family="sans-serif">
											 <xsl:value-of select="$classHighestpointsByPercentage"/>
										</fo:block>
									</fo:table-cell>
									</fo:table-row>
									
								<fo:table-row>
								<xsl:variable name="classLowestPointsByPercentage" select="interimClassResults/classLowestPointsByPercentage"></xsl:variable>
								<fo:table-cell number-columns-spanned="4" padding-left="15pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" 
								border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid"
								border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000" >
								<fo:block font-size="10.0pt" font-family="sans-serif" font-weight="bold" text-align="left">							
									<xsl:text>Lowest Points / Lowest Percentage</xsl:text>
								</fo:block>
								</fo:table-cell>
								
								<fo:table-cell number-columns-spanned="4"  padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt"
								 border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid"
								 border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
										<fo:block font-size="10.0pt" font-family="sans-serif">
											 <xsl:value-of select="$classLowestPointsByPercentage"/>
										</fo:block>
									</fo:table-cell>
									</fo:table-row>
									
								</fo:table-body>
							</fo:table>
							
<!-- 						 <xsl:call-template name="percentcorrect"/> 
 -->						
                        <xsl:call-template name="classroster"/>  
						 
					 </fo:block>	
          		 </fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	
	
	
	<!-- <xsl:template name="percentcorrect">
	
	<fo:block-container height="15mm" margin-top="-120px" float="right" margin-left="100px">
	       
 <fo:block>
	<fo:external-graphic vertical-align="middle">
		<xsl:attribute name="src">
					<xsl:value-of select="interimClassResults/byPercentCorrectFilePath" />
					</xsl:attribute>
	</fo:external-graphic>
	<fo:external-graphic vertical-align="middle">
		<xsl:attribute name="src">
					<xsl:value-of select="interimClassResults/byItemsFilePath" />
		</xsl:attribute>
	</fo:external-graphic>		
    </fo:block>
         </fo:block-container>
  
         <xsl:call-template name="classroster"/>  
	
	</xsl:template> -->
	
	
	<xsl:template name="classroster">
			<fo:block text-align="center" >
						<fo:table 
							border-left-style="solid" border-right-style="solid"
							display-align="center" margin-top="20px">
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<xsl:for-each select="interimClassResults/classRosters/classRosters/questions">
								<fo:table-column column-width="15mm" />
							</xsl:for-each>
							
							<xsl:for-each select="interimClassResults/interimResultsTypes/interimResultsTypes">
								<fo:table-column column-width="15mm" />
							</xsl:for-each>
                               <!-- <xsl:for-each select="interimClassResults/classRosters/classRosters/questions/questionAnswer/entry">
								<fo:table-column column-width="15mm" />
							</xsl:for-each> -->
							<fo:table-header >
								<fo:table-cell background-color="#f4be49"
									number-columns-spanned="5.5" padding-left="2pt" padding-right="2pt"
									padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
									border-bottom-style="solid" border-left-style="solid"
									border-right-style="solid" line-height="20pt"
									border-left="2px solid #000000" 
									border-right="2px solid #000000"
									border-top="2px solid #000000" 
											border-bottom="2px solid #000000" >
									<fo:block font-size="10.0pt" font-family="sans-serif"
										font-weight="bold" text-align="center"  >
										<xsl:text>Class Rosters</xsl:text>
									</fo:block>
								</fo:table-cell>
								<xsl:for-each select="interimClassResults/questions/a/string">

									<fo:table-cell background-color="#1a77bb"
										number-columns-spanned="1" padding-left="1pt" padding-right="1pt"
										padding-top="1pt" padding-bottom="1pt" border-top-style="solid"
										border-bottom-style="solid" border-left-style="solid"
										border-right-style="solid" border-left="2px solid #000000" 
									    border-right="2px solid #000000"
									    border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
										<fo:block font-size="10.0pt" font-family="sans-serif"
											font-weight="bold">
											<xsl:value-of select="." />
										</fo:block>
									</fo:table-cell>
								</xsl:for-each>
								<fo:table-cell background-color="#1a77bb"
									number-columns-spanned="2" padding-left="2pt" padding-right="2pt"
									padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
									border-bottom-style="solid" border-left-style="solid"
									border-right-style="solid" border-left="2px solid #000000" 
									border-right="2px solid #000000"
									border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
									<fo:block font-size="10.0pt" font-family="sans-serif"
										font-weight="bold">
										<xsl:text>Total Points</xsl:text>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell background-color="#1a77bb"
									number-columns-spanned="2" padding-left="2pt" padding-right="2pt"
									padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
									border-bottom-style="solid" border-left-style="solid"
									border-right-style="solid" border-left="2px solid #000000" 
									border-right="2px solid #000000"
									border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
									<fo:block font-size="10.0pt" font-family="sans-serif"
										font-weight="bold">
										<xsl:text>Total Percentage</xsl:text>
									</fo:block>
								</fo:table-cell>
							</fo:table-header>
						<xsl:for-each select="interimClassResults/classRosters">
							
							<fo:table-body>
							
								<xsl:for-each select="classRosters">
									<fo:table-row>
										<fo:table-cell number-columns-spanned="2"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid"
											border-left="2px solid #000000" 
									border-right="2px solid #000000"
									border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif"
												font-weight="bold">
												<xsl:value-of select="firstName" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell number-columns-spanned="2"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid"
											border-left="2px solid #000000" 
									border-right="2px solid #000000"
									border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif">
												<xsl:value-of select="lastName" />
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
												<xsl:value-of select="studentId" />
											</fo:block>
										</fo:table-cell>
										<xsl:for-each
													select="questions/questionAnswer/entry">
										<fo:table-cell number-columns-spanned="1"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid"
											border-left="2px solid #000000" 
									border-right="2px solid #000000"
									border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif">
												<xsl:value-of select="string[2]" />
											</fo:block>
										</fo:table-cell>
										</xsl:for-each>
										<fo:table-cell number-columns-spanned="2"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid"
											border-left="2px solid #000000" 
									border-right="2px solid #000000"
									border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif">
												<xsl:value-of select="totalPoints" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell number-columns-spanned="2"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid"
											border-left="2px solid #000000" 
									border-right="2px solid #000000"
									border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif">
												<xsl:value-of select="totalPercentage" />
											</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</xsl:for-each>
								
							</fo:table-body>
							</xsl:for-each>
						</fo:table>
						<xsl:call-template name="interimResulttypes"/> 
						</fo:block>			
				 </xsl:template>
          
          <xsl:template name="interimResulttypes">
           <fo:block text-align="center">
           <fo:table  border-left-style="solid" border-right-style="none" display-align="center">
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							<fo:table-column column-width="15mm" />
							  <xsl:for-each select="interimClassResults/interimResultsTypes/interimResultsTypes">
								<fo:table-column column-width="15mm" />
							</xsl:for-each>
					           <fo:table-body>				              
					       <fo:table-row>
							<fo:table-cell number-columns-spanned="5.5" padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt"
							 border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid" 
							 border-left="2px solid #000000" 
									border-right="2px solid #000000"
									border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
								<fo:block font-size="10.0pt" font-family="sans-serif" font-weight="bold" text-align="right">							
									<xsl:text>Item Type</xsl:text>
								</fo:block>
								</fo:table-cell>
								<xsl:for-each select="interimClassResults/interimResultsTypes/interimResultsTypes">
								<fo:table-cell number-columns-spanned="1"  padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt"
								 border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid"
								 border-left="2px solid #000000" 
									border-right="2px solid #000000"
									border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
										<fo:block font-size="10.0pt" font-family="sans-serif">
											 <xsl:value-of select="itemType"/>
										</fo:block>
									</fo:table-cell>
									</xsl:for-each>
									</fo:table-row>
									
									<fo:table-row>
							   <fo:table-cell number-columns-spanned="5.5" padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" 
							   border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid"
							   border-left="2px solid #000000" 
									border-right="2px solid #000000"
									border-top="2px solid #000000" 
											border-bottom="2px solid #000000" >
								<fo:block font-size="10.0pt" font-family="sans-serif" font-weight="bold" text-align="right">							
									<xsl:text>Scoring Type</xsl:text>
								</fo:block>
								</fo:table-cell>
								<xsl:for-each select="interimClassResults/interimResultsTypes/interimResultsTypes">
								<fo:table-cell number-columns-spanned="1"  padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" 
								border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid"
								border-left="2px solid #000000" 
									border-right="2px solid #000000"
									border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
										<fo:block font-size="10.0pt" font-family="sans-serif">
											 <xsl:value-of select="scoringType"/>
										</fo:block>
									</fo:table-cell>
									</xsl:for-each>
									</fo:table-row>
									
									<fo:table-row>
								<fo:table-cell number-columns-spanned="5.5" padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" 
								border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid" 
								border-left="2px solid #000000" 
									border-right="2px solid #000000"
									border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
								<fo:block font-size="10.0pt" font-family="sans-serif" font-weight="bold" text-align="right">							
									<xsl:text>Max Score</xsl:text>
								</fo:block>
								</fo:table-cell>
								<xsl:for-each select="interimClassResults/interimResultsTypes/interimResultsTypes">
								<fo:table-cell number-columns-spanned="1"  padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt"
								 border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid"
								 border-left="2px solid #000000" 
									border-right="2px solid #000000"
									border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
										<fo:block font-size="10.0pt" font-family="sans-serif">
											 <xsl:value-of select="pointValue"/>
										</fo:block>
									</fo:table-cell>
									</xsl:for-each>
									</fo:table-row>
									
								<fo:table-row>
							    <fo:table-cell number-columns-spanned="5.5" padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" 
							    border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid"
							    border-left="2px solid #000000" 
									border-right="2px solid #000000"
									border-top="2px solid #000000" 
											border-bottom="2px solid #000000" >
								<fo:block font-size="10.0pt" font-family="sans-serif" font-weight="bold" text-align="right">							
									<xsl:text>Alignment</xsl:text>
								</fo:block>
								</fo:table-cell>
								<xsl:for-each select="interimClassResults/interimResultsTypes/interimResultsTypes">
								<fo:table-cell number-columns-spanned="1"  padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" 
								border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid"
								border-left="2px solid #000000" 
									border-right="2px solid #000000"
									border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
										<fo:block font-size="10.0pt" font-family="sans-serif">
											 <xsl:value-of select="alignment"/>
										</fo:block>
									</fo:table-cell>
									</xsl:for-each>
									</fo:table-row>
									
									<fo:table-row>
						        <fo:table-cell number-columns-spanned="5.5" padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt"
						         border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid" 
						         border-left="2px solid #000000" 
									border-right="2px solid #000000"
									border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
								<fo:block font-size="10.0pt" font-family="sans-serif" font-weight="bold" text-align="right">							
									<xsl:text>Correct Percentage</xsl:text>
								</fo:block>
								</fo:table-cell>
								<xsl:for-each select="interimClassResults/interimResultsTypes/interimResultsTypes">
								<fo:table-cell number-columns-spanned="1"  padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt"
								 border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid"
								 border-left="2px solid #000000" 
									border-right="2px solid #000000"
									border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
										<fo:block font-size="10.0pt" font-family="sans-serif">
											 <xsl:value-of select="correctPercentage"/>
										</fo:block>
									</fo:table-cell>
									</xsl:for-each>
									</fo:table-row>
									
								<fo:table-row>
									<fo:table-cell number-columns-spanned="5.5" padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt"
									 border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid" 
									 border-left="2px solid #000000" 
									border-right="2px solid #000000"
									border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
										<fo:block font-size="10.0pt" font-family="sans-serif" font-weight="bold" text-align="right">							
									    <xsl:text>Incorrect Percentage</xsl:text>
											</fo:block>
											</fo:table-cell>
												<xsl:for-each select="interimClassResults/interimResultsTypes/interimResultsTypes">
													<fo:table-cell number-columns-spanned="1"  padding-left="2pt" padding-right="2pt" padding-top="2pt" 
													padding-bottom="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" 
													border-right-style="solid" border-left="2px solid #000000" 
													border-right="2px solid #000000"
													border-top="2px solid #000000" 
													border-bottom="2px solid #000000">
														<fo:block font-size="10.0pt" font-family="sans-serif">
															 <xsl:value-of select="incorrectPercentage"/>
														</fo:block>
													</fo:table-cell>
														</xsl:for-each>
									</fo:table-row>
						</fo:table-body>
					</fo:table>
			</fo:block>
       	</xsl:template>
	
</xsl:stylesheet>