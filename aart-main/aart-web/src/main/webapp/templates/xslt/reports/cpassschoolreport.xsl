<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo" extension-element-prefixes="saxon">
	
	
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" font-family="Verdana" font-size="10pt">
			<fo:layout-master-set>
			
				<fo:simple-page-master master-name="allPages" page-height="18.94cm" page-width="24.59cm" margin-top="1.4cm" margin-bottom="1.8cm" margin-left="0.5cm" margin-right="1.8cm">
				<!-- Central part of page -->
					<fo:region-body margin-top="54mm" margin-bottom="20mm" margin-right="5pt"/>
					<!-- Header -->
					<fo:region-before extent="52mm" />
					<!-- Footer -->
					 <fo:region-after  extent="15mm"  /> 
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
					<!-- 1st page -->
	 			<xsl:call-template name="page_body_content"/>		
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	
	<xsl:template name="report-logo">
	<fo:block text-align="center" margin-top="-3pt">
	<!-- <xsl:call-template name="headerLogo" />  -->
	<fo:table >
		<fo:table-body>
		<fo:table-row>
		<fo:table-cell>
		<fo:block-container height="3.7cm" width="5.4cm" >
			<fo:block  margin-top="-3mm">
				<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
					inline-progression-dimension.maximum="100%" block-progression-dimension.maximum="100%"
					 height="4.88cm" width="6.00cm">
					 <xsl:attribute name="src"><xsl:value-of select="reportDetails/logoPath"/></xsl:attribute>
				</fo:external-graphic>
			</fo:block>
		</fo:block-container>
		</fo:table-cell>
		</fo:table-row>
		</fo:table-body>
		</fo:table>
		
	</fo:block>
	</xsl:template>
	
	
	<xsl:template name="page_body_content">							
						<fo:block-container >
						 <fo:block>
						<fo:table display-align="center">
							     <fo:table-column column-width="23%" />
								<fo:table-column  column-width="20%"/>								 
								 <fo:table-column column-width="60%"/>															
												 								
						<fo:table-header>
						<fo:table-row>										
						<fo:table-cell   display-align="after" text-align="left" padding-left="1pt" padding-right="1pt">
						<fo:block font-size="9pt"  font-weight="bold" font-family="Times New Roman">
						<fo:inline >Group</fo:inline>
						</fo:block>
						</fo:table-cell>
						<fo:table-cell    display-align="after" text-align="center" padding-left="-15pt" padding-right="12pt"> 
						<fo:block font-size="9pt"  font-weight="bold" font-family="Times New Roman">
						<fo:inline>Score</fo:inline>
						</fo:block>
						</fo:table-cell>
						
						<fo:table-cell   display-align="after"  text-align="center">
					<fo:table table-layout="fixed">
       <xsl:for-each select="reportDetails/data/topicList/edu.ku.cete.report.domain.AssessmentTopic">
         <fo:table-column  column-width="auto"/>
        </xsl:for-each>
        <fo:table-body>
         <fo:table-row>
         <xsl:for-each select="reportDetails/data/topicList/edu.ku.cete.report.domain.AssessmentTopic">
           <fo:table-cell  display-align="after">
            <fo:block  font-size="9pt"  font-weight="bold" font-family="Times New Roman"
             margin-right="2pt" margin-left="40pt">
            <xsl:value-of select="topicName"/> 
            </fo:block>
            <fo:block   font-size="9pt"  font-weight="bold" font-family="Times New Roman" margin-left="40pt">(<xsl:value-of select="totalNoOfItems"></xsl:value-of>)</fo:block>
           </fo:table-cell>
          </xsl:for-each>
         </fo:table-row>
        </fo:table-body>
       </fo:table>
						</fo:table-cell>																	
						</fo:table-row>						
						<fo:table-row >
						<fo:table-cell width="100%" >
						<fo:block    margin-top="-5pt">
		                <fo:leader leader-pattern="rule"  leader-length="23.75cm" rule-thickness="0.5px"/>
						</fo:block>
						</fo:table-cell>
						</fo:table-row>
						</fo:table-header>						
					<fo:table-body>				
					<xsl:for-each select="reportDetails/data/organizationList/edu.ku.cete.report.domain.OrganizationPrctTopicReportsDTO">	
					<fo:table-row>					
					<fo:table-cell   display-align="after" padding-left="1pt" padding-right="1pt" text-align="left" >
						<fo:block font-size="9pt"  font-family="Times New Roman" >						
						<!-- <xsl:value-of select="typeCode"/> -->
						
						<xsl:if test="(typeCode = 'ALL')">
						<xsl:text>Collaborative (all students, all states)</xsl:text>											
						</xsl:if>
						
						<xsl:if test="(typeCode = 'ST')">
						<xsl:text>State (all students, this state only)</xsl:text>											
						</xsl:if>
						
						<xsl:if test="(typeCode = 'DT')">
						<xsl:text>District (all students in this district)</xsl:text>											
						</xsl:if>					
						</fo:block>
						</fo:table-cell>	
						
						<fo:table-cell    display-align="after" text-align="center" padding-left="-17pt" padding-right="12pt"> 
						<fo:block font-size="9pt"   font-family="Times New Roman">					
						<xsl:value-of select="scalescore"/>
						</fo:block>
						</fo:table-cell> 
						 <fo:table-cell   display-align="after" >
						<fo:table table-layout="fixed">
      <xsl:for-each	select="percentsCorrect/a/string">
         <fo:table-column  column-width="auto"/>
        </xsl:for-each>
        <fo:table-body>
         <fo:table-row>
         <xsl:for-each	select="percentsCorrect/a/string">
           <fo:table-cell  display-align="after">
            <fo:block font-size="9pt"  font-family="Times New Roman" text-align="center"
             margin-right="2pt" margin-left="40pt">
            <xsl:value-of select="."/>
            </fo:block>
           </fo:table-cell>
          </xsl:for-each>
         </fo:table-row>
        </fo:table-body>
       </fo:table>	
       </fo:table-cell>	 		
					</fo:table-row>
					</xsl:for-each>					
					</fo:table-body>						
				   </fo:table>
				   </fo:block> 
				     <fo:block margin-top="-5pt">
		                <fo:leader leader-pattern="rule"  leader-length="107%" rule-thickness="0.5px"/>
						</fo:block> 						
						 <fo:block>
		                <fo:leader leader-pattern="rule"  leader-length="107%" rule-thickness="0.5px"/>
						</fo:block> 
				   
				   <!-- 2nd table Start -->
				   
				      <fo:block>
						<fo:table display-align="center">	
							 <fo:table-column column-width="10%" />
							 <fo:table-column  column-width="9.5%" />
							 <fo:table-column  column-width="8.5%" />
							  <fo:table-column  column-width="7%" />
							 <fo:table-column  column-width="8%" />	 															 							 
							  <fo:table-column column-width="60%" />		
						<fo:table-header>								 
						<fo:table-row>					
						<fo:table-cell   display-align="after" text-align="left" padding-left="1pt" padding-right="1pt">
						<fo:block font-size="9pt"  font-weight="bold" font-family="Times New Roman">
						<fo:inline >Student LastName</fo:inline>
						</fo:block>
						
						</fo:table-cell>
						<fo:table-cell    display-align="after" text-align="left" padding-left="1pt" padding-right="1pt"> 
						<fo:block font-size="9pt"  font-weight="bold" font-family="Times New Roman" >
						<fo:inline >Student FirstName </fo:inline>
						</fo:block>
						
						</fo:table-cell>
				 		<fo:table-cell   display-align="after" text-align="center"	>
						<fo:block font-size="9pt"  font-weight="bold" font-family="Times New Roman"  >
						<fo:inline >State StudentID</fo:inline>
						</fo:block>
						
						</fo:table-cell>
						<fo:table-cell  display-align="after" text-align="center" padding-left="1pt" padding-right="12pt"> 
						<fo:block font-size="9pt"  font-weight="bold" font-family="Times New Roman" >
						<fo:inline>Score</fo:inline>
						</fo:block>
						</fo:table-cell>
						<fo:table-cell  display-align="after"  padding-left="1pt" padding-right="12pt">
						<fo:block font-size="9pt"  font-weight="bold" font-family="Times New Roman" text-align="center" >
						<fo:inline >Achievement Level</fo:inline>
						</fo:block>
						</fo:table-cell> 
						
						<fo:table-cell   display-align="after"  text-align="center">
						<fo:table table-layout="fixed">
       <xsl:for-each select="reportDetails/data/topicList/edu.ku.cete.report.domain.AssessmentTopic">
         <fo:table-column  column-width="auto"/>
        </xsl:for-each>
        <fo:table-body>
         <fo:table-row>
         <xsl:for-each select="reportDetails/data/topicList/edu.ku.cete.report.domain.AssessmentTopic">
           <fo:table-cell  display-align="after">
            <fo:block  font-size="9pt"  font-weight="bold" font-family="Times New Roman"
             margin-right="2pt" margin-left="40pt">
            <xsl:value-of select="topicName"/> 
            </fo:block>
            <fo:block   font-size="9pt"  font-weight="bold" font-family="Times New Roman" margin-left="40pt">(<xsl:value-of select="totalNoOfItems"></xsl:value-of>)</fo:block>
           </fo:table-cell>
          </xsl:for-each>
         </fo:table-row>
        </fo:table-body>
       </fo:table>
						</fo:table-cell>
																
						</fo:table-row>					
						 <fo:table-row >
						<fo:table-cell width="100%">
						<fo:block    margin-top="-5pt">
		                <fo:leader leader-pattern="rule"  leader-length="23.7cm" rule-thickness="0.5px"/>
						</fo:block>
						</fo:table-cell>
						</fo:table-row> 											
						</fo:table-header>									
					<fo:table-body>
					<xsl:for-each select="reportDetails/data/studentList/edu.ku.cete.report.domain.StudentPrctTopicReportsDTO">	
					<fo:table-row>					
					<fo:table-cell  text-align="left"  display-align="after"  >
						<fo:block font-size="9pt"   font-family="Times New Roman" text-align="left" >
						<xsl:value-of select="legalLastName"/>
						</fo:block>
						</fo:table-cell>	
						<fo:table-cell  padding-before="0pt" padding-after="0pt" display-align="after">
						<fo:block font-size="9pt"   font-family="Times New Roman"  text-align="left">
						<xsl:value-of select="legalFirstName"/>
						</fo:block>
						</fo:table-cell>	
							 <fo:table-cell  padding-before="0pt" padding-after="0pt" display-align="after" text-align="center">
						<fo:block font-size="9pt"   font-family="Times New Roman"  >
						<xsl:value-of select="stateStudentIdentifier"/>
						</fo:block>
						</fo:table-cell>			
						<fo:table-cell padding-left="1pt" padding-right="13pt" display-align="after" text-align="center"> 
						<fo:block font-size="9pt"   font-family="Times New Roman" >						
						<xsl:value-of select="scalescore"/>
						</fo:block>
						</fo:table-cell> 
						<fo:table-cell  padding-before="0pt" padding-after="0pt" display-align="after" text-align="center" padding-left="1pt" padding-right="5pt">
						<fo:block font-size="9pt" font-family="Times New Roman"  >
						<xsl:value-of select="achievementlevel"/>
						</fo:block>
						</fo:table-cell> 				
						 <fo:table-cell   display-align="after" >
						<fo:table table-layout="fixed">
      <xsl:for-each	select="percentsCorrect/a/string">
         <fo:table-column  column-width="auto"/>
        </xsl:for-each>
        <fo:table-body>
         <fo:table-row>
         <xsl:for-each	select="percentsCorrect/a/string">
           <fo:table-cell  display-align="after">
            <fo:block font-size="9pt"  font-family="Times New Roman" text-align="center"
             margin-right="2pt" margin-left="40pt">
            <xsl:value-of select="."/>
            </fo:block>
           </fo:table-cell>
          </xsl:for-each>
         </fo:table-row>
        </fo:table-body>
       </fo:table>	
       </fo:table-cell>	
					</fo:table-row>
					</xsl:for-each>	
					</fo:table-body>						
				   </fo:table>
				   </fo:block>  
				    <fo:block margin-top="-5pt">
		                <fo:leader leader-pattern="rule"  leader-length="107%" rule-thickness="0.5px"/>
						</fo:block> 
				    </fo:block-container>
	</xsl:template>
	
	<xsl:template name="page_footer">
	<fo:block-container>
		<fo:block margin-bottom="5px" font-family="Verdana"	font-size="9pt" page-break-inside="avoid" >
			<fo:table table-layout="fixed">
				<fo:table-column column-width="proportional-column-width(1)" />
				<fo:table-column column-width="proportional-column-width(1)" />
				<fo:table-column column-width="proportional-column-width(1)" />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell text-align="start" wrap-option="no-wrap">
							<fo:block font-size="9pt"   font-family="Times New Roman"  text-align="left">Summary data for Collaborative, State, and District are group averages.</fo:block>
						</fo:table-cell>					
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell text-align="start" wrap-option="no-wrap">
							<fo:block font-size="9pt"   font-family="Times New Roman"  text-align="left">Topics covered on the test are listed, with the number of items for each topic in parentheses.</fo:block>
						</fo:table-cell>					
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell text-align="start" wrap-option="no-wrap">
							<fo:block font-size="9pt"   font-family="Times New Roman"  text-align="left">Numbers under each topic area are the percentages of items correct within each topic.</fo:block>
						</fo:table-cell>					
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell text-align="start" wrap-option="no-wrap">
							<fo:block font-size="9pt"   font-family="Times New Roman" text-align="left">Please contact the Career Pathways Collaborative at (913) 864-6391 or cpass@ku.edu if you have questions or concerns about this report.</fo:block>
						</fo:table-cell>					
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
		</fo:block-container>
		
	</xsl:template> 
	
	<xsl:template name="report-page-header">
	<xsl:call-template name="report-logo"/>
		
		<fo:table table-layout="fixed">			 
			<fo:table-body>
				<fo:table-row >				
				<fo:table-cell >				
				<fo:block  font-size="8.5pt"  font-family="Times New Roman" margin-top="-45pt"  font-weight="bold"><fo:inline>Career Pathways Assessment System</fo:inline>
						 </fo:block>
				</fo:table-cell>
				</fo:table-row>
				<fo:table-row >				
				<fo:table-cell>
						<fo:block  font-size="8.5pt"  font-family="Times New Roman" margin-top="-35pt"  font-weight="bold">						
					 <xsl:value-of select="reportDetails/data/assessmentCode"/></fo:block>
						</fo:table-cell>
				</fo:table-row>		
				<fo:table-row >				
				<fo:table-cell>
						<fo:block  font-size="8.5pt"  font-family="Times New Roman" margin-top="-25pt"  font-weight="bold">
						<fo:inline >School Roster Report</fo:inline>
						</fo:block>
						</fo:table-cell>
				</fo:table-row>	
				
				<fo:table-row >				
				<fo:table-cell>
						<fo:block  font-size="8.5pt"  font-family="Times New Roman" margin-top="-15pt"  font-weight="bold" >						
						<xsl:value-of select="reportDetails/data/schoolYear - 1"/><xsl:text >-</xsl:text><xsl:value-of select="reportDetails/data/schoolYear"/>
						</fo:block>
						</fo:table-cell>	
				</fo:table-row>	
				<fo:table-row>
				<fo:table-cell>
				<fo:block   font-size="8.5pt"  font-family="Times New Roman" margin-top="5pt"   >	
				<fo:inline>School:</fo:inline><xsl:value-of select="reportDetails/data/schoolName"/>
				</fo:block>
				</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
				<fo:table-cell>
				<fo:block   font-size="8.5pt"  font-family="Times New Roman" margin-top="0pt"   >	
				<fo:inline>District:</fo:inline><xsl:value-of select="reportDetails/data/districtName"/>
				</fo:block>
				</fo:table-cell>
				</fo:table-row>	
				<fo:table-row>
				<fo:table-cell>
				<fo:block   font-size="8.5pt"  font-family="Times New Roman" margin-top="0pt"  >	
				<fo:inline>State:</fo:inline><xsl:value-of select="reportDetails/data/stateName"/>
				</fo:block>
				</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
		 
				<fo:block space-after="-1pt"  margin-top="1mm">
		<fo:leader leader-pattern="rule"  leader-length="107%" rule-thickness="0.5px" />
						</fo:block>
		
						
						
						
	</xsl:template>	
	
	
</xsl:stylesheet>