<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo" extension-element-prefixes="saxon">
	 <xsl:include href="cpassStudentReportUtility.xsl" />  
	 <xsl:include href="cpassStudentReportBarChart.xsl" />	
	 <xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format"
			font-family="Verdana" font-size="10pt">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="firstPage"					
					page-height="27.94cm" page-width="21.59cm" margin="1.27cm">
					<fo:region-body margin-top="25mm" />
					<fo:region-before region-name="xsl-region-before-firstPage"
						extent="30mm" />
				</fo:simple-page-master>
				
				<fo:page-sequence-master master-name="allPages">
					<fo:repeatable-page-master-alternatives>
						<fo:conditional-page-master-reference
							master-reference="firstPage" page-position="first" />
						
					</fo:repeatable-page-master-alternatives>
				</fo:page-sequence-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="allPages">
				<fo:static-content flow-name="xsl-region-before-firstPage">
					<xsl:call-template name="report-page1-header" />
				</fo:static-content>				
				<fo:flow flow-name="xsl-region-body" >								
 						<fo:block margin-top="150mm" margin-left="25pt">
							<xsl:call-template name="summaryLevelScoreGraph">
								<xsl:with-param name="reportType" select="'school'"/>																
							</xsl:call-template>						
				</fo:block>				
				
				<fo:block>
			<fo:table table-layout="fixed">
				<fo:table-column column-width="proportional-column-width(100)" />
				<fo:table-body>
					<fo:table-row>
						<!-- <fo:table-cell width="18.2cm" text-align="center">	 -->
						<fo:table-cell width="17.5cm" text-align="center">							
							<fo:block  margin-top="124pt" font-size="9pt" margin-left="35.5pt" text-align="left"  line-height="15px">
						<fo:inline color="#383535">Your score (and the margin of error) is also comparable with the average score for your district, state, and all students who took the test in all participating states within the Career Pathways Collaborative. More information on how to interpret your score, margin of error &amp; achievement levels can be found on the Career Pathways website</fo:inline> (<fo:basic-link external-destination="https://careerpathways.us/scorereports" show-destination="new" color="#160be8" text-decoration="underline" >https://careerpathways.us/scorereports</fo:basic-link>).
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
					
								
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	
	<xsl:template name="report-page1-header">
	<fo:block text-align="center" margin-left="80pt">
	<!-- <xsl:call-template name="headerLogo" />  -->
	<fo:table >
		<fo:table-body>
		<fo:table-row>
		<fo:table-cell >
		<fo:block-container height="6.75cm" width="7.2cm">
			<fo:block margin-left="-65pt" margin-top="-9.8mm">
				<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
					inline-progression-dimension.maximum="100%" block-progression-dimension.maximum="100%"
					 height="6.75cm" width="7.2cm">
					 <xsl:attribute name="src"><xsl:value-of select="reportDetails/logoPath"/></xsl:attribute>
				</fo:external-graphic>
			</fo:block>
		</fo:block-container>
		</fo:table-cell>
		</fo:table-row>
		</fo:table-body>
		</fo:table>
	
	</fo:block>
	<fo:block font-style="italic" margin-top="-152.5pt" text-align="center"  font-size="13pt"  font-weight="bold" color="#3E3B3A" margin-left="-2.6pt" space-after="10pt">
	<fo:inline> Student Computer-Based Assessment Results</fo:inline>
	</fo:block>
	<fo:block >
		<fo:table table-layout="fixed" width="100%" border-separation="3pt">
			 <fo:table-column column-width="proportional-column-width(100)" />
			<fo:table-column column-width="proportional-column-width(30)" />
			<fo:table-column column-width="proportional-column-width(48)" /> 
			
			<fo:table-body >
				<fo:table-row >				
				<fo:table-cell>				
						<fo:block  font-size="9pt"  text-align="left"  margin-top="2pt" margin-left="52pt" ><fo:inline color="#984806" font-weight="bold">Student Name</fo:inline><fo:inline color="#984806">:&#160;</fo:inline>
						 <xsl:call-template name="firstName-Header"/>&#160;&#160;<xsl:call-template name="lastName-Header"/></fo:block>
				</fo:table-cell>	
				<fo:table-cell text-align="center">
						<fo:block white-space-treatment="preserve" margin-top="1pt">
						</fo:block>
					</fo:table-cell>					
			<fo:table-cell text-align="left" width="6cm">				
			<fo:block  font-size="9pt" margin-right="0pt" margin-left="-65pt" margin-top="2pt"> 
			<fo:inline color="#984806" font-weight="bold">Report Date</fo:inline><fo:inline color="#984806">:&#160;</fo:inline><xsl:value-of select="reportDetails/data/reportDate"/>
			</fo:block>				
				</fo:table-cell>
				</fo:table-row>
				<fo:table-row >				
				<fo:table-cell>			
						<fo:block  font-size="9pt" text-align="left"  margin-top="2pt" margin-left="52pt">						
					<fo:inline color="#984806" font-weight="bold">Grade</fo:inline><fo:inline color="#984806">:&#160;</fo:inline><xsl:value-of select="reportDetails/data/gradeLevel"/></fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" >
						<fo:block white-space-treatment="preserve" margin-top="2pt">
						</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="left" width="9cm" >				
			       <fo:block  font-size="9pt" margin-right="0pt" margin-left="-65pt" margin-top="2pt"><fo:inline color="#984806" font-weight="bold" line-height="1pt">Assessment</fo:inline><fo:inline color="#984806">:&#160;</fo:inline><xsl:value-of select="reportDetails/data/assessmentName"/></fo:block>					
					</fo:table-cell>					
				</fo:table-row>		
				<fo:table-row >				
				<fo:table-cell>
						<fo:block  text-align="left"  margin-top="2pt" font-size="9pt" margin-left="52pt">
						<!-- <xsl:call-template name="headerGradeSubjectName"/> -->
						<fo:inline color="#984806" font-weight="bold">State</fo:inline><fo:inline color="#984806">:&#160;</fo:inline><xsl:value-of select="reportDetails/data/stateName"/>
						</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center">
						<fo:block white-space-treatment="preserve" margin-top="2pt">
						</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="left" width="6cm">	
								
			       <fo:block  font-size="9pt" margin-right="0pt" margin-left="-65pt" margin-top="2pt"><fo:inline color="#984806" font-weight="bold">Complete</fo:inline><fo:inline color="#984806">:&#160;</fo:inline>
			       <xsl:choose>
			<xsl:when test="reportDetails/data/completed = 'true'">
			<xsl:text>Yes</xsl:text>
			</xsl:when>
			<xsl:otherwise>	
			<xsl:text>No</xsl:text>
			</xsl:otherwise>
			</xsl:choose>
			       </fo:block>					
					</fo:table-cell>					
				</fo:table-row>	
				
				<fo:table-row >				
				<fo:table-cell>
						<fo:block  text-align="left"  margin-top="2pt" font-size="9pt" margin-left="52pt">
						<!-- <xsl:call-template name="headerDistrictName"/> -->
						<fo:inline color="#984806" font-weight="bold">District</fo:inline><fo:inline color="#984806">:&#160;</fo:inline><xsl:value-of select="reportDetails/data/districtName"/>
						</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center">
						<fo:block white-space-treatment="preserve" margin-top="2pt">
						</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="left" width="6cm" >				
			       <fo:block  font-size="9pt" margin-right="0pt" margin-left="-65pt" margin-top="2pt"><fo:inline color="#984806" font-weight="bold">Overall Score</fo:inline><fo:inline color="#984806">:&#160;</fo:inline><xsl:value-of select="reportDetails/data/studentScore"/></fo:block>					
					</fo:table-cell>					
				</fo:table-row>	
				
				
				<fo:table-row >				
				<fo:table-cell width="6cm">
						<fo:block  text-align="left"   margin-top="2pt" margin-left="52pt" font-size="9pt">
						<!-- <xsl:call-template name="headerSchoolName"/> -->
						<fo:inline color="#984806" font-weight="bold">School</fo:inline><fo:inline color="#984806">:&#160;</fo:inline><xsl:value-of select="reportDetails/data/schoolName"/>
						</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center">
						<fo:block white-space-treatment="preserve" margin-top="2pt">
						</fo:block>
					</fo:table-cell>
					<fo:table-cell  width="6cm" >				
			       <fo:block  font-size="9pt" margin-right="0pt" margin-left="-65pt" margin-top="2pt"><fo:inline color="#984806" font-weight="bold">Achievement Level</fo:inline><fo:inline color="#984806">:&#160;</fo:inline><xsl:value-of select="reportDetails/data/achievementLevel"/></fo:block>					
					</fo:table-cell>					
				</fo:table-row>				
			</fo:table-body>
		</fo:table>
		</fo:block>
		<fo:block font-size="9pt" >
				<fo:table table-layout="fixed" >
				<fo:table-column column-width="proportional-column-width(100)"/>
				<fo:table-column column-width="proportional-column-width(100)"></fo:table-column>
				<fo:table-body>
				<fo:table-row>
				<fo:table-cell>
				  <fo:block-container margin-top="13pt">
				<fo:block  margin-left="35.5pt"    font-size="9pt"  ><fo:inline color="#984806" font-weight="bold">Student Score Profile</fo:inline></fo:block>
			</fo:block-container>
				</fo:table-cell>
				</fo:table-row>
				</fo:table-body>
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell width="17.5cm">							
							<fo:block  margin-top="13pt" font-size="9pt" margin-left="36pt" text-align="left"   color="#383535" padding-right="-2.0px" line-height="15px">
								This report describes your performance on the Career Pathways																
								<xsl:value-of select="reportDetails/data/assessmentName"/>.						 	
							 	Your state is one of several states participating in the Career Pathways Collaborative in &#xa; order to better provide a measurement student achievement compared to national standards in career and technical education. Your results for this assessment are:
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
				
			</fo:table>
		</fo:block>
		
		<fo:table>
		<fo:table-body>
		<fo:table-row>
		<fo:table-cell text-align="center">
		<fo:block margin-top="7pt"  margin-right="-1pt" >				
		<fo:instream-foreign-object>
		<svg xmlns="http://www.w3.org/2000/svg" width="160" height="42" version="1.1">
		<rect  x="0" y="0" rx="9" ry="10" width="160" stroke="#984806" height="45" fill="#FDEADD" stroke-width="1.8px" stroke-opacity="1.8"/>
		<text  x="50%" y="45%"  font-size="9" fill="#A04000" text-anchor="middle">Student Score:
		<xsl:choose>
				<xsl:when test="reportDetails/data/studentScore = 0" >
					<xsl:text>N/A*</xsl:text>					
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="reportDetails/data/studentScore"/> 
					<xsl:choose>
				<xsl:when test="reportDetails/data/standardError = 0" >
				<xsl:text>&#160;(N/A*)</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					(<xsl:value-of select="format-number(reportDetails/data/standardError, '0')"/>)
				</xsl:otherwise>
				</xsl:choose>				
				</xsl:otherwise>
			</xsl:choose></text>
		<text x="50%" y="80%"  font-size="9" fill="#A04000" text-anchor="middle">Achievement Level: <xsl:value-of select="reportDetails/data/achievementLevel"/></text>
			</svg>
		</fo:instream-foreign-object>					
		</fo:block>
		</fo:table-cell>
		</fo:table-row>
		</fo:table-body>
		</fo:table>	
		
		<fo:table table-layout="fixed" >
			<fo:table-column column-width="proportional-column-width(20)" />
			<fo:table-column column-width="proportional-column-width(10)" />
			<fo:table-column column-width="proportional-column-width(10)" /> 
				<fo:table-body>			
				<fo:table-row >				
				<fo:table-cell>				
						<fo:block  font-size="9pt" margin-top="15.8pt"   margin-left="35.7pt" color="#383535" space-after="1pt"><fo:inline font-weight="bold" color="#9B4D31">District Average Score:</fo:inline>
						</fo:block>
				</fo:table-cell>	
				<fo:table-cell text-align="center">
						<fo:block white-space-treatment="preserve" margin-top="1pt">
						</fo:block>
					</fo:table-cell>					
			<fo:table-cell>				
			<fo:block font-size="9pt" margin-top="15.8pt" margin-left="-215pt" color="#383535">	
			<xsl:choose>
				<xsl:when test="reportDetails/data/districtAverageScore = 0" >
					<xsl:text>N/A*</xsl:text>					
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="reportDetails/data/districtAverageScore"/> 
					<xsl:choose>
				<xsl:when test="reportDetails/data/districtStandardError = 0" >
				<xsl:text>&#160;(N/A*)</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					(<xsl:value-of select="format-number(reportDetails/data/districtStandardError, '0')"/>)	
				</xsl:otherwise>
				</xsl:choose>	
				</xsl:otherwise>
			</xsl:choose>			
			</fo:block>			
				</fo:table-cell>
				</fo:table-row>
							<fo:table-row >				
								<fo:table-cell>				
									<fo:block  font-size="9pt" margin-left="35.5pt" color="#383535" space-after="1pt" margin-top="2pt"><fo:inline color="#9B4D31" font-weight="bold">State Average Score:</fo:inline>
								</fo:block>
							</fo:table-cell>	
				<fo:table-cell text-align="center">
				<fo:block white-space-treatment="preserve" margin-top="1pt">
				</fo:block>
				</fo:table-cell>					
			<fo:table-cell>				
			<fo:block font-size="9pt" margin-left="-215pt" color="#383535" space-after="1pt" margin-top="2pt">
			
			<xsl:choose>
				<xsl:when test="reportDetails/data/stateAverageScore = 0" >
					<xsl:text>N/A*</xsl:text>
				</xsl:when>
				<xsl:otherwise>
				<xsl:value-of select="reportDetails/data/stateAverageScore"/>
					<xsl:choose>
				<xsl:when test="reportDetails/data/stateStandardError = 0" >
				<xsl:text>&#160;(N/A*)</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					 (<xsl:value-of select="format-number(reportDetails/data/stateStandardError, '0')"/>)
					 </xsl:otherwise>
					 </xsl:choose>		
				</xsl:otherwise>
			</xsl:choose>		
			</fo:block>			
				</fo:table-cell>
				</fo:table-row>
				<fo:table-row >				
				<fo:table-cell>				
						<fo:block  font-size="9pt" margin-left="35.5pt" color="#383535" space-after="1pt" margin-top="2pt"><fo:inline color="#9B4D31" font-weight="bold">Average Score for All States:</fo:inline>
						</fo:block>
				</fo:table-cell>	
				<fo:table-cell text-align="center">
						<fo:block white-space-treatment="preserve" margin-top="1pt">
						</fo:block>
					</fo:table-cell>					
			<fo:table-cell>				
			<fo:block font-size="9pt" margin-left="-215pt" color="#383535" space-after="1pt" margin-top="2pt">	
			<xsl:choose>
					<xsl:when test="reportDetails/data/allStatesAvgScore = 0" >
						<xsl:text>N/A*</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="reportDetails/data/allStatesAvgScore"/>
						<xsl:choose>
				<xsl:when test="reportDetails/data/allStateStandardError = 0" >
				<xsl:text>&#160;(N/A*)</xsl:text>
				</xsl:when>
				<xsl:otherwise>
						(<xsl:value-of select="format-number(reportDetails/data/allStateStandardError, '0')"/>)
						</xsl:otherwise>
						</xsl:choose>
					</xsl:otherwise>
			</xsl:choose>		
			</fo:block>			
				</fo:table-cell>
				</fo:table-row>							
				</fo:table-body>
				</fo:table>			
			<fo:block  font-size="9pt" margin-left="35.5pt" color="#383535" margin-top="14pt">
			<xsl:choose>
			 <xsl:when test="reportDetails/data/districtAverageScore = '0' or reportDetails/data/stateAverageScore = '0' or reportDetails/data/allStatesAvgScore = '0' or reportDetails/data/studentScore = '0'"> 
						<xsl:text>* Not enough data to calculate this information</xsl:text>
					 </xsl:when>
					<xsl:otherwise>
					<xsl:choose>
					<xsl:when test="reportDetails/data/districtStandardError = '0' or reportDetails/data/stateStandardError = '0' or reportDetails/data/allStateStandardError = '0' or reportDetails/data/standardError = '0'" >
			<xsl:text>* Not enough data to calculate this information</xsl:text>
			</xsl:when>
			</xsl:choose>
			</xsl:otherwise> 
			</xsl:choose>
			</fo:block>
	</xsl:template>
	
 	<xsl:template name="standerd_error_for_grade">
		<fo:table table-layout="fixed">
			<fo:table-column column-width="proportional-column-width(48)" />
			<fo:table-column column-width="proportional-column-width(4)" />
			<fo:table-column column-width="proportional-column-width(48)" />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell>
						<fo:block font-weight="bold" font-family="Verdana"
							font-size="9pt" text-align="left" margin-top="10pt">Standard error of
							measurement for this report:
						</fo:block>
						<fo:block font-family="Verdana"
							font-size="8pt" text-align="left" margin-top="10pt">
							<xsl:for-each select="reportDetails/standardErrorList/edu.ku.cete.report.ReportStandardError">
								<fo:block font-size="8pt">									
									<xsl:value-of select="gradeName" />: <xsl:text>School&#8212;</xsl:text><xsl:value-of select="schoolStandardError" />  |  <xsl:text>District&#8212;</xsl:text><xsl:value-of select="districtStandardError" />  |  <xsl:text>State&#8212;</xsl:text><xsl:value-of select="stateStandardError" />
								</fo:block>
							</xsl:for-each>
						</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="center">
						<fo:block white-space-treatment="preserve">
						</fo:block>
					</fo:table-cell>
					
				</fo:table-row>
			</fo:table-body>
		</fo:table>
		
		
	</xsl:template>
	

</xsl:stylesheet>