<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:fo="http://www.w3.org/1999/XSL/Format" extension-element-prefixes="saxon">
  <xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes"/>
  <!-- ========================= -->
  <!-- root element: projectteam -->
  <!-- ========================= -->
  <xsl:template match="/">
    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
      <fo:layout-master-set>
<!-- <fo:simple-page-master master-name="simpleA4"  page-height="11.69in" page-width="8.27in" margin-left="0.30in" margin-right="0.30in" margin-top="0.30in" margin-bottom="0.30in">  -->        
        <fo:simple-page-master master-name="simpleA4"  page-width="11.69in" page-height="8.27in" margin-left="0.30in" margin-right="0.30in" margin-top="0.30in" margin-bottom="0.30in">
           
           <!-- Central part of page -->
	        <fo:region-body  margin-top="1.30in"  margin-bottom="0.4in" />
	        
	        <!-- Header -->
	        <fo:region-before extent="0.25in"/>
	
	       

	        <!-- Footer -->
	        <fo:region-after extent="0.40in"/>
	        
        </fo:simple-page-master>
      </fo:layout-master-set>
      
      <fo:page-sequence master-reference="simpleA4">
	      <!-- Define the contents of the header. -->
	      <fo:static-content flow-name="xsl-region-before">
	      	  <fo:table>
	      	  	<fo:table-column column-width="70mm" />
				<fo:table-column column-width="130mm" />
				<fo:table-column column-width="70mm" />
				<fo:table-body>
					<fo:table-cell display-align="center" text-align="center">
						<fo:block font-size="8.0pt" font-weight="bold" font-family="sans-serif" text-align="center"></fo:block>
	        		</fo:table-cell>
					<fo:table-cell display-align="center" text-align="center">
						<fo:block font-size="14.0pt" font-weight="bold" font-family="sans-serif" text-align="center">
							<xsl:text>Individual Student Progress Report</xsl:text>
	        			</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="right">
						<fo:block-container height="15mm">
						<fo:block>
						 	<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit" height="100px" width="100px" vertical-align="middle">
 								<xsl:attribute name="src">
 									<xsl:value-of select="studentReportData/dlmLogo"/>
 								</xsl:attribute>
							</fo:external-graphic>
						</fo:block>
						</fo:block-container>
					</fo:table-cell>
				</fo:table-body>
	      	  </fo:table>

			<fo:table>
				<fo:table-column column-width="110mm" />
				<fo:table-column column-width="130mm" />
				<fo:table-column column-width="40mm" />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block font-size="12.0pt" font-family="sans-serif"><fo:inline font-weight="bold">Name:<xsl:text>&#160;</xsl:text></fo:inline> <xsl:value-of select="studentReportData/studentFirstName"/>
							<xsl:text>&#160;</xsl:text>
							<xsl:value-of select="studentReportData/studentLastName"/> </fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block font-size="12.0pt" font-family="sans-serif"><fo:inline font-weight="bold">School:<xsl:text>&#160;</xsl:text></fo:inline> <xsl:value-of select="studentReportData/schoolName"/></fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block font-size="12.0pt" font-family="sans-serif"><fo:inline font-weight="bold">Year:<xsl:text>&#160;</xsl:text></fo:inline> <xsl:value-of select="studentReportData/schoolYear"/></fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell>
							<fo:block font-size="12.0pt" font-family="sans-serif"><fo:inline font-weight="bold">Subject:<xsl:text>&#160;</xsl:text></fo:inline> <xsl:value-of select="studentReportData/contentAreaName"/></fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block font-size="12.0pt" font-family="sans-serif"><fo:inline font-weight="bold">District:<xsl:text>&#160;</xsl:text></fo:inline> <xsl:value-of select="studentReportData/districtName"/></fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block font-size="12.0pt" font-family="sans-serif"><fo:inline font-weight="bold">Grade:<xsl:text>&#160;</xsl:text></fo:inline> <xsl:value-of select="studentReportData/gradeName"/></fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell>
							<fo:block font-size="12.0pt" font-family="sans-serif" space-before="4.0pt"><fo:inline font-weight="bold">Report Date:<xsl:text>&#160;</xsl:text></fo:inline> <xsl:value-of select="studentReportData/reportDate"/></fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block font-size="12.0pt" font-family="sans-serif" space-before="4.0pt"><fo:inline font-weight="bold">State:<xsl:text>&#160;</xsl:text></fo:inline> <xsl:value-of select="studentReportData/stateName"/></fo:block>
						</fo:table-cell>
						
					</fo:table-row>
				</fo:table-body>
			</fo:table>
			 
	      </fo:static-content>
	
	      <!-- Define the contents of the footer. -->
	      <fo:static-content flow-name="xsl-region-after">
	<fo:block>
		<fo:table font-size="9.0pt" font-family="sans-serif">
			<fo:table-column column-width="10mm" />
			<fo:table-column column-width="20mm" />
			<fo:table-column column-width="0mm" />
			
			<fo:table-column column-width="10mm" />
			<fo:table-column column-width="25mm" />
			<fo:table-column column-width="0mm" />
			
			<fo:table-column column-width="10mm" />
			<fo:table-column column-width="25mm" />
			<fo:table-column column-width="0mm" />
			
			<fo:table-column column-width="10mm" />
			<fo:table-column column-width="50mm" />
			<fo:table-column column-width="0mm" />
			
			<fo:table-column column-width="10mm" />
			<fo:table-column column-width="20mm" />
			<fo:table-column column-width="0mm" />
			
			<fo:table-column column-width="90mm" />
			<fo:table-body>
				
				<fo:table-cell text-align="right" border-left-style="solid"
					border-bottom-style="solid" border-width="thin" border-top-style="solid">
					<fo:block>
						<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit" height="20px" width="20px" vertical-align="middle">
								<xsl:attribute name="src">
									<xsl:value-of select="studentReportData/targetLogo"/>
								</xsl:attribute>
						</fo:external-graphic>
					</fo:block>
				</fo:table-cell>
				<fo:table-cell display-align="center" border-width="thin"
					border-bottom-style="solid" border-top-style="solid">
					<fo:block>
						<xsl:text>&#160; = Target</xsl:text>
					</fo:block>
				</fo:table-cell>
				<fo:table-cell border-bottom-style="solid" border-width="thin"
					border-top-style="solid">
					<fo:block>
						 
					</fo:block>
				</fo:table-cell>
				
				<fo:table-cell background-color="#253494" border-width="thin"
					border-bottom-style="solid" border-top-style="solid">
					<fo:block>
					</fo:block>
				</fo:table-cell>
				<fo:table-cell display-align="center" border-width="thin"
					border-bottom-style="solid" border-top-style="solid">
					<fo:block>
						<xsl:text>&#160; = Mastered</xsl:text>
					</fo:block>
				</fo:table-cell>
				<fo:table-cell border-bottom-style="solid" border-width="thin"
					border-top-style="solid">
					<fo:block>
						 
					</fo:block>
				</fo:table-cell>
				
				<fo:table-cell background-color="#2c7fb8" border-width="thin"
					border-bottom-style="solid" border-top-style="solid">
					<fo:block>
					</fo:block>
				</fo:table-cell>
				<fo:table-cell display-align="center" border-width="thin"
					border-bottom-style="solid" border-top-style="solid">
					<fo:block>
						<xsl:text>&#160; = Attempted</xsl:text>
					</fo:block>
				</fo:table-cell>
				<fo:table-cell border-bottom-style="solid" border-width="thin"
					border-top-style="solid">
					<fo:block>
						 
					</fo:block>
				</fo:table-cell>
				
				
				<fo:table-cell background-color="#41b6c4" border-width="thin"
					border-bottom-style="solid" border-top-style="solid">
					<fo:block>
					</fo:block>
				</fo:table-cell>
				<fo:table-cell display-align="center" border-width="thin"
					border-bottom-style="solid" border-top-style="solid">
					<fo:block>
						<xsl:text>&#160; = Assessed, results not available</xsl:text>
					</fo:block>
				</fo:table-cell>
				<fo:table-cell border-bottom-style="solid" border-width="thin"
					border-top-style="solid">
					<fo:block>
						 
					</fo:block>
				</fo:table-cell>
				
				
				<fo:table-cell background-color="#a1dab4" border-width="thin"
					border-bottom-style="solid" border-top-style="solid">
					<fo:block>
					</fo:block>
				</fo:table-cell>
				<fo:table-cell display-align="center" border-width="thin"
					border-bottom-style="solid" border-top-style="solid">
					<fo:block>
						<xsl:text>&#160; = Planned</xsl:text>
					</fo:block>
				</fo:table-cell>
				<fo:table-cell border-right-style="solid" border-bottom-style="solid" border-top-style="solid" border-width="thin">
					<fo:block>
						 
					</fo:block>
				</fo:table-cell>
				
				<fo:table-cell display-align="center" text-align="right">
					<fo:block font-size="12.0pt" font-family="sans-serif"
						padding-after="2.0pt" space-before="4.0pt" text-align="right">
						<xsl:text>Page </xsl:text>
						<fo:page-number />
						of
						<fo:page-number-citation ref-id="end" />
					</fo:block>
				</fo:table-cell>
				
			</fo:table-body>
		</fo:table>
		
	</fo:block>

</fo:static-content>
	      
        <fo:flow flow-name="xsl-region-body">
              <fo:block font-size="12.0pt" font-family="sans-serif" padding-after="2.0pt" space-before="4.0pt" text-align="left">
	                   <xsl:value-of select="studentReportData/studentFirstName"/>'s current performance in <xsl:value-of select="studentReportData/gradeName"/><xsl:text>&#160;</xsl:text> <xsl:value-of select="studentReportData/contentAreaName"/> Essential Elements is summarized below.
	                   This information is based on all of the Dynamic Learning Maps tests taken between the beginning of the school year and <xsl:value-of select="studentReportData/reportDate"/>. 
	                   The target level is the grade level expectation for students to have proficient understanding of and ability to apply the Essential Element.  
	          </fo:block>  
			
			  <fo:block font-size="12.0pt" font-family="sans-serif" padding-after="2.0pt" space-before="4.0pt" text-align="left">
	                   This report provides student results so far for this school year. These results do not guarantee the student’s overall performance at the end of the year. This report does not show progress on all of <xsl:value-of select="studentReportData/studentFirstName"/>'s instructional goals. <xsl:value-of select="studentReportData/studentFirstName"/> may be taught other academic concepts that have not yet been tested. 
	                   This report does not show progress on IEP goals.                     
	          </fo:block> 
	          <fo:block padding-before="2.0pt">
	                  <xsl:text></xsl:text>
	        		</fo:block> 
	          
              <xsl:choose>
					<xsl:when test="studentReportData/claimsConceptualData/edu.ku.cete.domain.report.student.StudentRptCCArea">
					   <xsl:for-each select="studentReportData/claimsConceptualData/edu.ku.cete.domain.report.student.StudentRptCCArea">
			         		<xsl:call-template name="claimConceptualAreaLoop"/> 
			         		 <fo:block padding-before="10.0pt">
			                  <xsl:text></xsl:text>
			        		</fo:block> 
			          </xsl:for-each> 
		            </xsl:when>
		          	<xsl:otherwise>
		          		<fo:block font-size="12.0pt" font-family="sans-serif" padding-after="1.0pt" space-before="40.0pt" text-align="center">
		                  <xsl:text>
		                  	No instructional plans exist in Educator Portal for this student and subject
		                  </xsl:text>
		        		</fo:block>
		          	</xsl:otherwise>
				</xsl:choose>
              
              
              <fo:block id="end"/>
        </fo:flow>
        
      </fo:page-sequence>
    </fo:root>
  </xsl:template>
  <xsl:variable name="contentArea" select = "studentReportData/contentAreaName" />
  <xsl:template name="claimConceptualAreaLoop">
  	<fo:block font-size="14.0pt" background-color="#ffffcc" font-weight="bold" font-family="sans-serif" padding-after="2.0pt" space-before="4.0pt" text-align="center" 
  	 	border-right-style="solid" border-left-style="solid" border-bottom-style="solid" border-top-style="solid">
				<fo:block font-size="12.0pt" font-family="sans-serif" padding-after="2.0pt" space-before="4.0pt">
					<fo:block text-align="left">Claim: <xsl:value-of select="claim"/></fo:block>
					<fo:block text-align="left">Conceptual Area: <xsl:value-of select="conceptualArea"/></fo:block>					
				</fo:block>
	 </fo:block>	 
  	<fo:table border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid">										
		<xsl:choose>
			<xsl:when test="$contentArea = 'Science'">
				<fo:table-column column-width="78mm" />
				<fo:table-column column-width="68mm" />
				<fo:table-column column-width="68mm" />
				<fo:table-column column-width="68mm" />
			</xsl:when>
			<xsl:otherwise>
				<fo:table-column column-width="51mm" />
				<fo:table-column column-width="46mm" />
				<fo:table-column column-width="46mm" />
				<fo:table-column column-width="46mm" />
				<fo:table-column column-width="46mm" />
				<fo:table-column column-width="46mm" />
			</xsl:otherwise>
		</xsl:choose>
																	
		<fo:table-header > 
			<fo:table-cell  padding-left="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid" display-align="center">
	   			<fo:block font-size="10.0pt" font-weight="bold" font-family="sans-serif">Grade Level Expectation</fo:block>
	  		</fo:table-cell>
	  		<fo:table-cell padding-left="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid" display-align="center">
	   		 	<fo:block font-size="10.0pt" font-weight="bold" font-family="sans-serif">Level 1</fo:block>
	  		</fo:table-cell>
	  		<fo:table-cell padding-left="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid" display-align="center">
	   		 	<fo:block font-size="10.0pt" font-weight="bold" font-family="sans-serif">Level 2</fo:block>
	  		</fo:table-cell>
	  		<xsl:choose>
				<xsl:when test="$contentArea = 'Science'">
					<fo:table-cell padding-left="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid" display-align="center">
						<fo:block font-size="10.0pt" font-weight="bold" font-family="sans-serif">Level 3
							<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit" height="20px" width="20px" vertical-align="middle">
								<xsl:attribute name="src">
									<xsl:value-of select="targetLogo"/>
								</xsl:attribute>
							</fo:external-graphic>
						</fo:block>
					</fo:table-cell>
	  			</xsl:when>
	  			<xsl:otherwise>
	  				<fo:table-cell padding-left="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid" display-align="center">
	   		 			<fo:block font-size="10.0pt" font-weight="bold" font-family="sans-serif">Level 3</fo:block>
	  				</fo:table-cell>
	  			</xsl:otherwise>
	  		</xsl:choose>
	  		<xsl:choose>
	  			<xsl:when test="$contentArea != 'Science'">
	  				<fo:table-cell padding-left="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid" display-align="center">
	   		 			<fo:block font-size="10.0pt" font-weight="bold" font-family="sans-serif">Level 4
	  						<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit" height="20px" width="20px" vertical-align="middle">
	 							<xsl:attribute name="src">
	 								<xsl:value-of select="targetLogo"/>
	 							</xsl:attribute>
							</fo:external-graphic>
						</fo:block>
					</fo:table-cell>
	  				<fo:table-cell padding-left="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid" display-align="center">
	   					<fo:block font-size="10.0pt" font-weight="bold" font-family="sans-serif">Level 5</fo:block>
					</fo:table-cell>
				</xsl:when>
			</xsl:choose>
		</fo:table-header>				
		<fo:table-body>
			<xsl:for-each select="eEList/edu.ku.cete.domain.report.student.StudentRptEssentialElement">
	        	<xsl:call-template name="eeLoop"/>  
	          		</xsl:for-each> 
			</fo:table-body>
	</fo:table>
    
  </xsl:template>
  <xsl:template name="eeLoop">
  	<fo:table-row border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid">
						<fo:table-cell padding-left="2pt">
							<fo:block font-size="9.0pt" font-family="sans-serif">
									<xsl:value-of select="eeCode"/> 
							</fo:block>
							<fo:block font-size="9.0pt" font-family="sans-serif">
									<xsl:value-of select="eeDesc"/> 
							</fo:block>
						</fo:table-cell>
						<xsl:for-each select="levelDetails/edu.ku.cete.domain.report.student.StudentRptLinkageLevel">
							<xsl:call-template name="linkageLevelLoop"/> 
	          			</xsl:for-each> 
					</fo:table-row>
  </xsl:template>
  <xsl:template name="linkageLevelLoop">
  	<xsl:choose>
					<xsl:when test="contains(testStatus, 'Complete')">
					<xsl:choose>
					
        				<xsl:when test="writingType = 'true'">
	        				<fo:table-cell padding-left="2pt" background-color="#41b6c4" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid">
								<fo:block padding-left="1.0pt" font-size="9.0pt" font-family="sans-serif" >
										<xsl:value-of select="desc"/>
								</fo:block>
								<xsl:if test="string(administered)"> 
									<fo:block font-size="9.0pt" font-family="sans-serif" padding-before="2.0pt">
										Assessed: <xsl:value-of select="administered"/>
									</fo:block>
								</xsl:if> 
							</fo:table-cell>
				        </xsl:when>
				        <xsl:when test="percentCorrectScoreOfScoreableItems &gt;= 80">
		        				<fo:table-cell padding-left="2pt" background-color="#253494" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid">
									<fo:block padding-left="1.0pt" font-size="9.0pt" font-family="sans-serif" color="#ffffff">
											<xsl:value-of select="desc"/>
									</fo:block>
									<xsl:if test="string(administered)"> 
										<fo:block font-size="9.0pt" font-family="sans-serif" padding-before="2.0pt" color="#ffffff">
											Mastered: <xsl:value-of select="administered"/>
										</fo:block>
									</xsl:if> 
								</fo:table-cell>
					    </xsl:when>
					    <xsl:when test="percentCorrectScoreOfScoreableItems &lt; 80">
	        				<fo:table-cell padding-left="2pt" background-color="#2c7fb8" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid">
								<fo:block padding-left="1.0pt" font-size="9.0pt" font-family="sans-serif"  color="#ffffff">
										<xsl:value-of select="desc"/>
								</fo:block>
								<xsl:if test="string(administered)"> 
									<fo:block font-size="9.0pt" font-family="sans-serif" padding-before="2.0pt" color="#ffffff">
										Attempted: <xsl:value-of select="administered"/>
									</fo:block>
								</xsl:if> 
							</fo:table-cell>
				        </xsl:when>	
				        </xsl:choose>			        
				    </xsl:when>
				    <xsl:when test="not(string(administered)) and (sessionLevel = 'yes')">
			        	<fo:table-cell padding-left="2pt" background-color="#a1dab4" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid">
							<fo:block font-size="9.0pt" font-family="sans-serif" >
									<xsl:value-of select="desc"/>
							</fo:block>
							<fo:block font-size="9.0pt" font-family="sans-serif" padding-before="2.0pt">
									Planned
							</fo:block>
							 
						</fo:table-cell>
			        </xsl:when>
        			<xsl:otherwise>
        				<fo:table-cell padding-left="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid">
							<fo:block font-size="9.0pt" font-family="sans-serif" >
									<xsl:value-of select="desc"/>
							</fo:block>							 
						</fo:table-cell>
			        </xsl:otherwise>
	</xsl:choose>
  </xsl:template>
</xsl:stylesheet>
