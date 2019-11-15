<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:fo="http://www.w3.org/1999/XSL/Format" extension-element-prefixes="saxon">
  <!-- ========================= -->
  <!-- root element: projectteam -->
  <!-- ========================= -->
  <xsl:template match="/">
    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" font-family="Verdana" font-size="10pt">
      <fo:layout-master-set>
<!-- <fo:simple-page-master master-name="simpleA4"  page-height="11.69in" page-width="8.27in" margin-left="0.30in" margin-right="0.30in" margin-top="0.30in" margin-bottom="0.30in">  -->        
        <fo:simple-page-master master-name="simpleA4"  page-width="11.69in" page-height="8.27in" margin-left="0.30in" margin-right="0.30in" margin-top="0.30in" margin-bottom="0.30in">
           
           <!-- Central part of page -->
	        <fo:region-body  margin-top="1.30in"  margin-bottom="0.40in" />
	        
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
				<fo:table-column column-width="200mm" />
				<fo:table-column column-width="75mm" />
				<fo:table-body>
					<fo:table-cell display-align="center" text-align="center">
						 <fo:block font-size="14.0pt" font-weight="bold" font-family="sans-serif"  text-align="center">
							<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160; Class Roster Report for </xsl:text> <xsl:value-of select="rosterReportData/educatorName"/>
	        			</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="right">
						<fo:block-container height="15mm">
						<fo:block>
							<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit" height="100px" width="100px">
 								<xsl:attribute name="src">
 									<xsl:value-of select="rosterReportData/dlmLogo"/>
 								</xsl:attribute>
							</fo:external-graphic>		
						</fo:block>
						</fo:block-container>
					</fo:table-cell>
				</fo:table-body>
	      	  </fo:table>

			<fo:table>
				<fo:table-column column-width="90mm" />
				<fo:table-column column-width="90mm" />
				<fo:table-column column-width="90mm" />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block font-size="12.0pt" font-family="sans-serif">
								<fo:inline font-weight="bold">Report Date:<xsl:text>&#160;</xsl:text></fo:inline> <xsl:value-of select="rosterReportData/reportDate"/>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block font-size="12.0pt" font-family="sans-serif"><fo:inline font-weight="bold">School:<xsl:text>&#160;</xsl:text></fo:inline> <xsl:value-of select="rosterReportData/schoolName"/></fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block font-size="12.0pt" font-family="sans-serif"><fo:inline font-weight="bold">Subject:<xsl:text>&#160;</xsl:text></fo:inline> <xsl:value-of select="rosterReportData/contentAreaName"/></fo:block>
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
					<fo:table-column column-width="50mm" />
					<fo:table-column column-width="0mm" />
					<fo:table-column column-width="10mm" />
					<fo:table-column column-width="70mm" />
					<fo:table-column column-width="10mm" />
					<fo:table-column column-width="85mm" />
					<fo:table-body>
						<fo:table-cell>
							<fo:block>
								
							</fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block>
								
							</fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block>
								 
							</fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block>
								
							</fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block>
								 
							</fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block></fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block>
								
							</fo:block>
						</fo:table-cell>
						<fo:table-cell >
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
	    <xsl:variable name = "plusIcon"><xsl:value-of select="rosterReportData/plusLogo"/></xsl:variable>
		<xsl:variable name = "minusIcon"><xsl:value-of select="rosterReportData/minusLogo"/></xsl:variable>
        <fo:flow flow-name="xsl-region-body">
			<fo:block>
				<fo:table>
					<fo:table-column column-width="33mm" />
					<fo:table-column column-width="60mm" />
					<fo:table-column column-width="35mm" />
					<fo:table-column column-width="55mm" />
					<fo:table-column column-width="35mm" />
					<fo:table-column column-width="52mm" />
						<fo:table-header>
							<fo:table-cell number-columns-spanned="2" padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid" >
								<fo:block font-size="10.0pt" font-family="sans-serif" font-weight="bold">
									Essential Element
								</fo:block>
							</fo:table-cell>
							<fo:table-cell number-columns-spanned="2" padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid" >
								<fo:block font-size="10.0pt" font-family="sans-serif" font-weight="bold">
									Recently Assessed
								</fo:block>
							</fo:table-cell>
							<fo:table-cell number-columns-spanned="2" padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid" >
								<fo:block font-size="10.0pt" font-family="sans-serif" font-weight="bold">
									Current Instructional Goals
								</fo:block>
							</fo:table-cell>
						</fo:table-header>
						<fo:table-body>
	           			<xsl:for-each select="rosterReportData/gradeStudentDataList/edu.ku.cete.domain.report.roster.RosterReportGradeStudentData">
								<fo:table-row>
									<fo:table-cell number-columns-spanned="6" background-color="#f3df7e" padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid">
										<fo:block font-size="10.0pt" font-family="sans-serif">
											<xsl:value-of select="gradeCourseName"/>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row>	
									<fo:table-cell number-columns-spanned="6" background-color="#f3df7e" padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid">
										<fo:block font-size="10.0pt" font-family="sans-serif">
											<xsl:value-of select="studentName"/>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
																
								<xsl:for-each select="eeDataList/edu.ku.cete.domain.report.roster.ItiRosterReportEE">
									<xsl:variable name="position" select="position()-1" />
									<xsl:variable name="previousee" select="../edu.ku.cete.domain.report.roster.ItiRosterReportEE[$position]/essentialElement" />
									<xsl:variable name="eeElementVal" select="essentialElement" />
					         		<fo:table-row>
										<fo:table-cell padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid">
											<fo:block font-size="9.0pt" font-family="sans-serif">
												<xsl:choose>
													<xsl:when test="$position != 0 and $previousee = $eeElementVal"> 
														<xsl:text></xsl:text>
													</xsl:when>
													<xsl:otherwise>
														<xsl:value-of select="essentialElement"/>
													</xsl:otherwise>
												</xsl:choose>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid">
											<fo:block font-size="9.0pt" font-family="sans-serif">
												<xsl:choose>
													<xsl:when test="$position != 0 and $previousee = $eeElementVal"> 
														<xsl:text></xsl:text>
													</xsl:when>
													<xsl:otherwise>
														<xsl:value-of select="essentialElementDesc"/>
													</xsl:otherwise>
												</xsl:choose>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid">
											<fo:block font-size="9.0pt" font-family="sans-serif">
												<xsl:choose>
													<xsl:when test="$position != 0 and $previousee = $eeElementVal"> 
														<xsl:text></xsl:text>
													</xsl:when>
													<xsl:otherwise>
														<xsl:value-of select="raLinkageLevel"/>
													</xsl:otherwise>
												</xsl:choose>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid">
											<fo:block font-size="9.0pt" font-family="sans-serif">
												<xsl:choose>
													<xsl:when test="$position != 0 and $previousee = $eeElementVal"> 
														<xsl:text></xsl:text>
													</xsl:when>
													<xsl:otherwise>
														<xsl:choose>
															<xsl:when test="string-length(endDateTimeStr)!=0"> 
																<xsl:value-of select="raLinkageLevelShortDesc"/>
																<fo:block font-size="9.0pt" font-family="sans-serif">	
																Assessed: <xsl:value-of select="endDateTimeStr" />
																</fo:block>
															</xsl:when>
															<xsl:otherwise>
																<xsl:text>Not Yet Assessed</xsl:text>
															</xsl:otherwise>
														</xsl:choose>
													</xsl:otherwise>
												</xsl:choose>												
											</fo:block>
										</fo:table-cell>
										<fo:table-cell padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid">
											<fo:block font-size="9.0pt" font-family="sans-serif">
												<xsl:value-of select="cigLinkageLevel"/>
												<xsl:choose>
													<xsl:when test="icon = 'plus'">
														<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit" height="10px" width="10px" vertical-align="middle">
							 								<xsl:attribute name="src">
							 									<xsl:value-of select="$plusIcon"/>
							 								</xsl:attribute>
														</fo:external-graphic>	
													</xsl:when>
													<xsl:when test="icon = ''"> 
														<xsl:text></xsl:text>
													</xsl:when>
													<xsl:when test="icon = 'minus'">
														<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit" height="10px" width="10px" vertical-align="middle">
							 								<xsl:attribute name="src">
							 									<xsl:value-of select="$minusIcon"/>
							 								</xsl:attribute>
														</fo:external-graphic>	
													</xsl:when>
													<xsl:otherwise>
														<xsl:text></xsl:text>
													</xsl:otherwise>
												</xsl:choose>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid">
											<fo:block font-size="9.0pt" font-family="sans-serif">
												<xsl:value-of select="cigLinkageLevelShortDesc"/>
											</fo:block>
										</fo:table-cell>
									</fo:table-row> 									
					          </xsl:for-each>
			          </xsl:for-each> 
			          <xsl:for-each select="/rosterReportData/noPlanMessages/string">
						<fo:table-row>	
							<fo:table-cell number-columns-spanned="6" padding-left="2pt" padding-right="2pt" padding-top="2pt" padding-bottom="2pt" border-top-style="solid" border-bottom-style="solid" border-left-style="solid" border-right-style="solid">
								<fo:block font-size="9.0pt" font-family="sans-serif">
									<xsl:value-of select="."/>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					  </xsl:for-each>					          	          
					</fo:table-body>
				</fo:table>
			</fo:block>
			<fo:block id="end"/>
        </fo:flow>
        
      </fo:page-sequence>
    </fo:root>
  </xsl:template>
  
</xsl:stylesheet>
