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
				<fo:simple-page-master master-name="simpleA4"
					page-height="8.27in" page-width="11.69in" margin-top="0.30in"
					margin-bottom="0.30in">

					<!-- Central part of page -->
					<fo:region-body  column-count="0" column-gap="0.0in"
						margin-top="0.30in" margin-bottom="0.30in" margin-left="0.20in" />

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
               	
                       <xsl:text font-weight="bold">Student Report For </xsl:text>
                       
                	 <xsl:variable name="testName" select="InterimReportsByStudentList/testName"></xsl:variable>
                	 <fo:inline font-style="italic" font-family="sans-serif" font-size="12.0pt">
                	<xsl:value-of  select="$testName"/> 
                   </fo:inline>
                </fo:block>
				
				<xsl:for-each select="InterimReportsByStudentList/studentList/InterimReportsByStudent">
				<fo:block margin-top="10px">
							<fo:table   margin-left="0.50in" border-top-style="none" border-bottom-style="none" border-left-style="solid" border-right-style="solid" display-align="center">
							<fo:table-column column-width="20mm" />
					       	<fo:table-column column-width="20mm" />
					       	<fo:table-column column-width="20mm" />
					       	<fo:table-column column-width="20mm" />
					        <fo:table-column column-width="20mm" />
					       	<fo:table-column column-width="20mm" />
					     	<fo:table-column column-width="20mm" />
					        <fo:table-column column-width="20mm" />
					        <fo:table-column column-width="20mm" />
					       	<fo:table-column column-width="20mm" />
					       	<fo:table-column column-width="20mm" />
					       	<fo:table-column column-width="20mm" />
					        <fo:table-column column-width="20mm" />
					       	<fo:table-column column-width="20mm" />
					     	<fo:table-column column-width="20mm" />
					        <fo:table-column column-width="20mm" />
					        
					       <fo:table-header>
					         <xsl:variable name="firstName" select="firstName"></xsl:variable>
					         <xsl:variable name="lastName" select="lastName"></xsl:variable>
					         <xsl:variable name="stateId" select="stateId"></xsl:variable>					         
							<fo:table-cell  background-color="#A9A9A9" number-columns-spanned="2" padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" 
								border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid" 
								border-left="2px solid #000000" border-right="2px solid #000000" border-top="2px solid #000000" 
								border-bottom="2px solid #000000">
								<fo:block font-size="10.0pt" font-family="sans-serif" font-weight="bold" text-align="center" >
									<xsl:text>Name:</xsl:text>
								</fo:block>
								</fo:table-cell>
								<fo:table-cell  background-color="#A9A9A9" number-columns-spanned="2" padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" border-top-style="solid" border-bottom-style="solid"
								 border-left-style="solid" border-right-style="solid" border-left="2px solid #000000" 
								 border-right="2px solid #000000" border-top="2px solid #000000" border-bottom="2px solid #000000">
								
								<fo:block font-size="10.0pt" font-family="sans-serif" font-weight="bold" text-align="center">
									 <xsl:value-of select="$firstName"/>
								</fo:block>
								</fo:table-cell>
								<fo:table-cell  background-color="#A9A9A9" number-columns-spanned="2" padding-left="2pt" padding-right="2pt" 
								padding-top="2pt" padding-bottom="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid"
								 border-right-style="solid" border-left="2px solid #000000" 
								 border-right="2px solid #000000" border-top="2px solid #000000" border-bottom="2px solid #000000">
								<fo:block font-size="10.0pt" font-family="sans-serif" font-weight="bold" text-align="center" >
								 <xsl:value-of select="$lastName"/>
                                </fo:block>
								</fo:table-cell>
								<fo:table-cell  background-color="#A9A9A9" number-columns-spanned="2" padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" 
								border-right-style="solid" border-left="2px solid #000000" 
								 border-right="2px solid #000000" border-top="2px solid #000000" border-bottom="2px solid #000000">
								<fo:block font-size="10.0pt" font-family="sans-serif" font-weight="bold" text-align="center" >
									<xsl:text>State Id :</xsl:text>
								</fo:block>
								</fo:table-cell>
								<fo:table-cell  background-color="#A9A9A9" number-columns-spanned="2" padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" border-top-style="solid" border-bottom-style="solid" 
								border-left-style="solid" border-right-style="solid" border-left="2px solid #000000" 
								 border-right="2px solid #000000" border-top="2px solid #000000" border-bottom="2px solid #000000">
								<fo:block font-size="10.0pt" font-family="sans-serif" font-weight="bold" text-align="center" >
								 <xsl:value-of select="$stateId"/>	
								</fo:block>
								</fo:table-cell>
								</fo:table-header>
                               <fo:table-body>				              
					       <fo:table-row>
								<xsl:variable name="testSessionName" select="testSessionName"></xsl:variable>
								<xsl:variable name="totalpointsOrpercentage" select="totalpointsOrpercentage"></xsl:variable>
							   
								<fo:table-cell number-columns-spanned="4" padding-left="-4pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" 
								border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid" 
								border-left="2px solid #000000" border-right="2px solid #000000" border-top="2px solid #000000" border-bottom="2px solid #000000">
								<fo:block font-size="10.0pt" font-family="sans-serif" font-weight="bold" text-align="left">							
									<xsl:text>Test Session: </xsl:text>
									 <xsl:value-of select="$testSessionName"/>
								</fo:block>
								</fo:table-cell>
								
								<fo:table-cell number-columns-spanned="6"  padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" border-top-style="solid" border-bottom-style="solid"
								 border-left-style="none" border-right-style="solid" border-left="2px solid #000000" 
								 border-right="2px solid #000000" border-top="2px solid #000000" border-bottom="2px solid #000000">
										<fo:block font-size="10.0pt" font-family="sans-serif">
										      <xsl:text>Total Points / Percentage: </xsl:text>
										       <xsl:value-of select="$totalpointsOrpercentage"/>
										</fo:block>
									</fo:table-cell>
									</fo:table-row>
									
								<fo:table-row>
								<xsl:variable name="testStartDate" select="testStartDate"></xsl:variable>
								<fo:table-cell number-columns-spanned="2" padding-left="-4pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" border-top-style="solid" border-bottom-style="solid" 
								border-left-style="solid" border-right-style="solid" border-left="2px solid #000000" 
								 border-right="2px solid #000000" border-top="2px solid #000000" border-bottom="2px solid #000000">
								<fo:block font-size="10.0pt" font-family="sans-serif" font-weight="bold" text-align="left">							
									<xsl:text>Test Start Date</xsl:text>
								</fo:block>
								</fo:table-cell>
								
								<fo:table-cell number-columns-spanned="8"  padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" 
								border-right-style="solid" border-left="2px solid #000000" border-right="2px solid #000000" border-top="2px solid #000000" border-bottom="2px solid #000000">
										<fo:block font-size="10.0pt" font-family="sans-serif">
											 <xsl:value-of select="$testStartDate"/>
										</fo:block>
									</fo:table-cell>
									
									</fo:table-row>
									
									<fo:table-row>
								<xsl:variable name="testEndDate" select="testEndDate"></xsl:variable>
								
								<fo:table-cell number-columns-spanned="2"  padding-left="-4pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid"
								 border-right-style="solid" border-left="2px solid #000000" 
								 border-right="2px solid #000000" border-top="2px solid #000000" border-bottom="2px solid #000000">
								<fo:block font-size="10.0pt" font-family="sans-serif" font-weight="bold" text-align="left">							
									<xsl:text>Test End Date</xsl:text>
								</fo:block>
								</fo:table-cell>
								
								<fo:table-cell number-columns-spanned="8"  padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid"
								 border-right-style="solid" border-left="2px solid #000000" 
								 border-right="2px solid #000000" border-top="2px solid #000000" border-bottom="2px solid #000000">
										<fo:block font-size="10.0pt" font-family="sans-serif">
											 <xsl:value-of select="$testEndDate"/>
										</fo:block>
									</fo:table-cell>
									</fo:table-row>
									
								</fo:table-body>
							</fo:table>
						</fo:block>
				 		<xsl:call-template name="interimStudentResponse"/> 
					</xsl:for-each>
					</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	<xsl:template name="interimStudentResponse">
	<fo:block text-align="center">
						<fo:table border-top-style="none" border-bottom-style="none"
							border-left-style="solid" border-right-style="none"
							display-align="center" margin-top="0.30in" break-after="page">	
							
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
							<xsl:for-each select="InterimReportsByStudent/interimStudentResponse/InterimStudentResponse">
								<fo:table-column column-width="15mm" />
							</xsl:for-each>

							<fo:table-header>
								<fo:table-cell background-color="#1a77bb"
									number-columns-spanned="2" padding-left="2pt" padding-right="2pt"
									padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
									border-bottom-style="solid" border-left-style="solid"
									border-right-style="solid" border-left="2px solid #000000"
									border-right="2px solid #000000" border-top="2px solid #000000" 
									border-bottom="2px solid #000000">
									<fo:block font-size="10.0pt" font-family="sans-serif"
										font-weight="bold" text-align="center">
										<xsl:text>Test Questions</xsl:text>
									</fo:block>
								</fo:table-cell>
								
								<fo:table-cell background-color="#f4be49"
									number-columns-spanned="2" padding-left="2pt" padding-right="2pt"
									padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
									border-bottom-style="solid" border-left-style="solid"
									border-right-style="solid" border-left="2px solid #000000"
									border-right="2px solid #000000" border-top="2px solid #000000" border-bottom="2px solid #000000">
									<fo:block font-size="10.0pt" font-family="sans-serif"
										font-weight="bold">
										<xsl:text>Student Answers</xsl:text>
									</fo:block>
									</fo:table-cell>
									<fo:table-cell background-color="#f4be49"
									number-columns-spanned="2" padding-left="2pt" padding-right="2pt"
									padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
									border-bottom-style="solid" border-left-style="solid"
									border-right-style="solid" border-left="2px solid #000000" 
									border-right="2px solid #000000" border-top="2px solid #000000" border-bottom="2px solid #000000">
									<fo:block font-size="10.0pt" font-family="sans-serif"
										font-weight="bold" text-align="center">
										<xsl:text>Correct Answers</xsl:text>
									</fo:block>
								</fo:table-cell>
								
								<fo:table-cell background-color="#f4be49"
									number-columns-spanned="2" padding-left="2pt" padding-right="2pt"
									padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
									border-bottom-style="solid" border-left-style="solid"
									border-right-style="solid" border-left="2px solid #000000"
									border-right="2px solid #000000" border-top="2px solid #000000" 
									border-bottom="2px solid #000000" >
									<fo:block font-size="10.0pt" font-family="sans-serif"
										font-weight="bold" text-align="center">
										<xsl:text>Score</xsl:text>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell background-color="#f4be49"
									number-columns-spanned="2" padding-left="2pt" padding-right="2pt"
									padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
									border-bottom-style="solid" border-left-style="solid"
									border-right-style="solid" border-left="2px solid #000000"
									 border-right="2px solid #000000" border-top="2px solid #000000" 
									 border-bottom="2px solid #000000">
									<fo:block font-size="10.0pt" font-family="sans-serif"
										font-weight="bold" text-align="center">
										<xsl:text>Percentage</xsl:text>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell background-color="#f4be49"
									number-columns-spanned="2" padding-left="2pt" padding-right="2pt"
									padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
									border-bottom-style="solid" border-left-style="solid"
									border-right-style="solid" border-left="2px solid #000000" 
									border-right="2px solid #000000" border-top="2px solid #000000" 
									border-bottom="2px solid #000000">
									<fo:block font-size="10.0pt" font-family="sans-serif"
										font-weight="bold" text-align="center">
										<xsl:text>Item Types</xsl:text>
									</fo:block>
								</fo:table-cell>
								
								<fo:table-cell background-color="#f4be49"
									number-columns-spanned="2" padding-left="2pt" padding-right="2pt"
									padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
									border-bottom-style="solid" border-left-style="solid"
									border-right-style="solid" border-left="2px solid #000000" 
									border-right="2px solid #000000" border-top="2px solid #000000"
									border-bottom="2px solid #000000">
									<fo:block font-size="10.0pt" font-family="sans-serif"
										font-weight="bold" text-align="center">
										<xsl:text>Scoring Type</xsl:text>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell background-color="#f4be49"
									number-columns-spanned="2" padding-left="2pt" padding-right="2pt"
									padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
									border-bottom-style="solid" border-left-style="solid"
									border-right-style="solid" border-left="2px solid #000000" 
									border-right="2px solid #000000" border-top="2px solid #000000"
									 border-bottom="2px solid #000000">
									<fo:block font-size="10.0pt" font-family="sans-serif"
										font-weight="bold" text-align="center">
										<xsl:text>Alignment</xsl:text>
									</fo:block>
								</fo:table-cell>
								
								<fo:table-cell background-color="#f4be49"
									number-columns-spanned="2" padding-left="2pt" padding-right="2pt"
									padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
									border-bottom-style="solid" border-left-style="solid"
									border-right-style="solid" border-left="2px solid #000000" 
									border-right="2px solid #000000" border-top="2px solid #000000" 
									border-bottom="2px solid #000000" >
									<fo:block font-size="10.0pt" font-family="sans-serif"
										font-weight="bold" text-align="center">
										<xsl:text>Max Score</xsl:text>
									</fo:block>
								</fo:table-cell>
								
							</fo:table-header>
							<fo:table-body>
								<xsl:for-each select="interimStudentResponse/InterimStudentResponse">
									<fo:table-row>
										<fo:table-cell number-columns-spanned="2"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid" border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif"
												font-weight="bold">
												<xsl:value-of select="questionName" />
											</fo:block>
										</fo:table-cell>
										
										
										<fo:table-cell number-columns-spanned="2"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid" border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif">
												<xsl:value-of select="studentAnswer" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell number-columns-spanned="2"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid" border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif">
												<xsl:value-of select="correctAnswer" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell number-columns-spanned="2"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid" border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif">
												<xsl:value-of select="score" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell number-columns-spanned="2"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid" border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif">
												<xsl:value-of select="percentage" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell number-columns-spanned="2"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid" border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif">
												<xsl:value-of select="ItemType" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell number-columns-spanned="2"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid" border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
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
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif">
												<xsl:value-of select="alignment" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell number-columns-spanned="2"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid" border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif">
												<xsl:value-of select="pointValue" />
											</fo:block>
										</fo:table-cell>
									</fo:table-row>
									
									
								</xsl:for-each>
								
						<fo:table-row>
								<xsl:variable name="totalPercentage" select="totalPercentage"></xsl:variable>
								
								<xsl:variable name="totalPoint" select="totalPoint"></xsl:variable>
								
								<xsl:variable name="maxTotalScore" select="maxTotalScore"></xsl:variable>
								
								<fo:table-cell number-columns-spanned="6"  padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" border-top-style="solid"
								 border-bottom-style="solid" border-left-style="solid" border-right-style="solid" 
								 			border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
										<fo:block font-size="10.0pt" font-family="sans-serif">
										      <xsl:text>Total</xsl:text>
										      
											 
										</fo:block>
									</fo:table-cell>
									
									<fo:table-cell number-columns-spanned="2"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid" border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif">
											<xsl:value-of select="$totalPoint"/>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell number-columns-spanned="2"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid" border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif">
											<xsl:value-of select="$totalPercentage"/>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell number-columns-spanned="6"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid" border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif">
											</fo:block>
										</fo:table-cell>
										<fo:table-cell number-columns-spanned="2"
											padding-left="2pt" padding-right="2pt" padding-top="2pt"
											padding-bottom="2pt" border-top-style="solid"
											border-bottom-style="solid" border-left-style="solid"
											border-right-style="solid" border-left="2px solid #000000" 
											border-right="2px solid #000000" border-top="2px solid #000000" 
											border-bottom="2px solid #000000">
											<fo:block font-size="10.0pt" font-family="sans-serif">
											<xsl:value-of select="$maxTotalScore"/>
											
											</fo:block>
										</fo:table-cell>
										
										
							</fo:table-row>
						</fo:table-body>
					</fo:table>
				</fo:block>
		</xsl:template>
</xsl:stylesheet>