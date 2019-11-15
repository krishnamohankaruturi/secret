<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java fo"
	extension-element-prefixes="saxon">

	<!-- Start Interim Summary Report Templates -->

	<xsl:template name="interim_summary_header_intro_paragraph_text">
		<fo:block font-size="9pt" >
			<fo:table table-layout="fixed" >
				<fo:table-column column-width="proportional-column-width(100)" />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block margin-top="3pt">
								<fo:leader leader-pattern="rule" leader-length="100%"
									rule-thickness="0.3pt" />
							</fo:block>							
							<xsl:choose>
								<xsl:when test="//interimReportDetails/data/attendanceSchoolId != ''">
								   <fo:block-container width="97%">
									<fo:block margin-top="10pt" text-align="left" >
										This chart shows how students performed on each question that
										appeared on the most recent interim assessment. The School PCT
										column reports the percentage of students who earned full
										credit on each question. For comparison, the State PCT column reflects the
										number of students out of 100 who earned full credit on each
										question during the <xsl:value-of select="interimReportDetails/data/predictiveSchoolYear" /> interim mini-tests. Higher numbers in
										this column indicate an easier question; lower numbers
										indicate a more difficult question.
									</fo:block>
								  </fo:block-container>
								</xsl:when>
								<xsl:otherwise>
								  <fo:block-container width="97%">
									<fo:block margin-top="10pt" text-align="left" >
										This chart shows how students performed on each question that
										appeared on the most recent interim assessment. The District
										PCT column reports the percentage of students who earned full
										credit on each question. For comparison, the State PCT column
										reflects the number of students out of 100 who earned full
										credit on each question during the <xsl:value-of select="interimReportDetails/data/predictiveSchoolYear" /> interim mini-tests.
										Higher numbers in this column indicate an easier question;
										lower numbers indicate a more difficult question.
									</fo:block>
								  </fo:block-container>
								</xsl:otherwise>
							</xsl:choose>
						
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
	</xsl:template>

	<xsl:template name="interim-summary-report-page1-header">

		<fo:table table-layout="fixed">
			<fo:table-column column-width="proportional-column-width(62)" />
			<fo:table-column column-width="proportional-column-width(10)" />
			<fo:table-column column-width="proportional-column-width(28)" />
			<fo:table-body>
				<fo:table-row>

					<xsl:choose>
						<xsl:when test="//interimReportDetails/data/attendanceSchoolId != ''">
							<fo:table-cell>
								<fo:block font-family="Verdana" font-weight="bold"
									font-size="11pt" line-height="13.5pt" text-align="left"
									wrap-option="no-wrap">
									<xsl:call-template name="interimSummaryheaderSchoolName" />
								</fo:block>
								<fo:block-container>
									<fo:block font-family="Verdana" font-size="9pt"
										line-height="12.5pt">
										<xsl:call-template name="interimSummaryGradeContentAreaName" />
										<xsl:call-template name="interimSummaryDistrictName" />
									</fo:block>
								</fo:block-container>
							</fo:table-cell>
						</xsl:when>
						<xsl:otherwise>
							<fo:table-cell>
								<fo:block font-family="Verdana" font-weight="bold"
									font-size="11pt" line-height="13.5pt" text-align="left"
									wrap-option="no-wrap">
									<xsl:call-template name="interimSummaryheaderDistrictName" />
								</fo:block>
								<fo:block-container>
									<fo:block font-family="Verdana" font-size="9pt"
										line-height="12.5pt">
										<xsl:call-template name="interimSummaryGradeContentAreaName" />
									</fo:block>
								</fo:block-container>
							</fo:table-cell>
						</xsl:otherwise>
					</xsl:choose>



					<fo:table-cell text-align="center">
						<fo:block white-space-treatment="preserve">
						</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="end">
						<fo:block-container display-align="center">
							<fo:block text-align="center" color="#25408f"
								font-family="Verdana" font-weight="bold" font-size="9pt"
								margin-left="3.7mm">
								<xsl:call-template name="interimSummaryHeaderSchoolYear" />
								<xsl:call-template name="headerLogo" />
							</fo:block>
						</fo:block-container>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>


	<xsl:template name="interimSummaryheaderSchoolName">
		SCHOOL REPORT:
		<xsl:value-of select="//interimReportDetails/data/interimSummaryHeaderSchoolName" /> &#47; &#35;<xsl:value-of select="//interimReportDetails/data/schoolDisplayId" />
	</xsl:template>
	<xsl:template name="interimSummaryheaderDistrictName">
		DISTRICT REPORT:
		<xsl:value-of select="//interimReportDetails/data/interimSummaryHeaderDistrictName" /> &#47; &#35;<xsl:value-of select="//interimReportDetails/data/districtDisplayId" />
	</xsl:template>

	<xsl:template name="interimSummaryGradeContentAreaName">
		<fo:block>
			SUBJECT:
			<xsl:value-of select="//interimReportDetails/data/subjectName" />
		</fo:block>
		<fo:block>
			GRADE:
			<xsl:value-of select="//interimReportDetails/data/gradeCode" />
		</fo:block>
	</xsl:template>

	<xsl:template name="interimSummaryDistrictName">
		<xsl:variable name="districtName">
			DISTRICT:
			<xsl:value-of select="//interimReportDetails/data/districtName" /> &#47; &#35;<xsl:value-of select="//interimReportDetails/data/districtDisplayId" />
		</xsl:variable>
		<fo:block>
			<xsl:value-of select="$districtName" />
		</fo:block>
	</xsl:template>

	<xsl:template name="interimSummaryHeaderSchoolYear">
		<fo:block font-family="Verdana" font-weight="bold" font-size="9pt"
			margin-left="25mm" padding-top="2pt">
			<xsl:value-of select="//interimReportDetails/data/schoolYear - 1" />
			<xsl:text>&#8213;</xsl:text>
			<xsl:value-of select="//interimReportDetails/data/schoolYear" />
		</fo:block>
	</xsl:template>

	<xsl:template name="summaryFooterLogoSection">
		<fo:block width="100%" height="100%">
			<fo:external-graphic
				content-height="scale-down-to-fit"
				inline-progression-dimension.maximum="100%"
				block-progression-dimension.maximum="100%" height="32mm" width="30cm" >
				<xsl:attribute name="src"><xsl:value-of
					select="//interimReportDetails/footerLogoPath" /></xsl:attribute>
			</fo:external-graphic>
		</fo:block>
	</xsl:template>
	
	<!-- End Interim Summary Report Templates -->


	<xsl:template name="report-page1-header">
		<fo:table table-layout="fixed">
			<fo:table-column column-width="proportional-column-width(50)" />
			<fo:table-column column-width="proportional-column-width(22)" />
			<fo:table-column column-width="proportional-column-width(28)" />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell>
						<fo:block font-family="Verdana" font-weight="bold"
							font-size="11pt" line-height="13.5pt" text-align="left"
							wrap-option="no-wrap">
							STUDENT REPORT: <xsl:call-template name="lastName-Header"/>, <xsl:call-template name="firstName-Header" /></fo:block>
						<fo:block-container font-family="Verdana"
							font-size="9pt" line-height="12.5pt">
							<xsl:call-template name="headerGradeSubjectName" />
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
							text-align="right" color="#25408f" font-family="Verdana"
							font-weight="bold" font-size="9pt" margin-left="3.7mm">
							<xsl:call-template name="headerSchoolYear" />
							<xsl:call-template name="headerLogo" />
						</fo:block-container>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>

	<xsl:template name="footerNextPageArrow">
		<fo:external-graphic content-width="scale-down-to-fit"
			content-height="scale-down-to-fit"
			inline-progression-dimension.maximum="100%"
			block-progression-dimension.maximum="100%">
			<xsl:attribute name="src"><xsl:value-of
				select="interimReportDetails/footerNextPageArrowPath" /></xsl:attribute>
		</fo:external-graphic>
	</xsl:template>

	<xsl:template name="headerSchoolYear">
		<fo:block font-family="Verdana" font-weight="bold" font-size="9pt"
			margin-left="9mm" padding-top="2pt">
			<xsl:value-of select="interimReportDetails/data/schoolYear - 1" />
			<xsl:text>&#8213;</xsl:text>
			<xsl:value-of select="interimReportDetails/data/schoolYear" />
		</fo:block>
	</xsl:template>
	<xsl:template name="headerGradeSubjectName">
		<xsl:variable name="gradeSubject">
			<xsl:text>GRADE&#58;&#160;</xsl:text>
			<xsl:value-of select="interimReportDetails/data/gradeCode" />
			<xsl:text>&#160;</xsl:text>
			<xsl:value-of select="interimReportDetails/data/subjectName" />
		</xsl:variable>
		<fo:block wrap-option="no-wrap">
			<xsl:value-of select="$gradeSubject" />
			&#160;&#47; STATE ID:
			<xsl:call-template name="stateStudentIdentifier" />
		</fo:block>
	</xsl:template>

	<xsl:template name="headerSchoolName">
		<xsl:variable name="schName">
			SCHOOL:
			<xsl:value-of select="interimReportDetails/data/schoolName" />
		</xsl:variable>
		<fo:block>
			<xsl:value-of select="$schName" />
		</fo:block>
	</xsl:template>
	<xsl:template name="headerDistrictName">
		<xsl:variable name="districtName">
			DISTRICT:
			<xsl:value-of select="interimReportDetails/data/districtName" /> &#47; &#35;<xsl:value-of select="interimReportDetails/data/districtDisplayId" />
		</xsl:variable>
		<fo:block>
			<xsl:value-of select="$districtName" />
		</fo:block>
	</xsl:template>
	<xsl:template name="headerContentAreaName">
		<xsl:variable name="contentAreaName">
			SUBJECT:
			<xsl:value-of select="interimReportDetails/data/subjectName" />
		</xsl:variable>
	</xsl:template>

	<xsl:template name="headerLogo">
		<fo:table>
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell>
						<fo:block-container height="2cm">
							<fo:block margin-top="-10.0mm">
								<fo:external-graphic content-width="scale-down-to-fit"
									content-height="scale-down-to-fit"
									inline-progression-dimension.maximum="100%"
									block-progression-dimension.maximum="100%" height="2.88cm"
									width="3.88cm">
									<xsl:attribute name="src"><xsl:value-of
										select="interimReportDetails/logoPath" /></xsl:attribute>
								</fo:external-graphic>
							</fo:block>
						</fo:block-container>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>

	<xsl:template name="reportFooter">
		<fo:block>
			<fo:table table-layout="fixed">
				<fo:table-column />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block space-after="3pt">
								<fo:leader leader-pattern="rule" leader-length="100%"
									rule-thickness="0.5pt" />
							</fo:block>
							<fo:block font-size="8pt" text-align="left">
								&#169;
								<xsl:value-of
									select="java:format(java:java.text.SimpleDateFormat.new('yyyy'), java:java.util.Date.new())" />
								The University of Kansas
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
	</xsl:template>

	<xsl:template name="stateStudentIdentifier">
		<xsl:call-template name="intersperse-with-zero-spaces">
			<xsl:with-param name="data"
				select="interimReportDetails/data/stateStudentIdentifier" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="intersperse-with-zero-spaces">
		<xsl:param name="data" />
		<xsl:variable name="spacechars">
			&#x9;&#xA;
			&#x2000;&#x2001;&#x2002;&#x2003;&#x2004;&#x2005;
			&#x2006;&#x2007;&#x2008;&#x2009;&#x200A;&#x200B;
		</xsl:variable>

		<xsl:if test="string-length($data) &gt; 0">
			<xsl:variable name="c1" select="substring($data, 1, 1)" />
			<xsl:variable name="c2" select="substring($data, 2, 1)" />

			<xsl:value-of select="$c1" />
			<xsl:if
				test="$c2 != '' and
	        not(contains($spacechars, $c1) or
	        contains($spacechars, $c2))">
				<xsl:text>&#x200B;</xsl:text>
			</xsl:if>

			<xsl:call-template name="intersperse-with-zero-spaces">
				<xsl:with-param name="data" select="substring($data, 2)" />
			</xsl:call-template>
		</xsl:if>
	</xsl:template>
	<xsl:template name="header_intro_paragraph_text">
		<fo:block font-size="9pt">
			<fo:table table-layout="fixed">
				<fo:table-column column-width="proportional-column-width(100)" />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block margin-top="3pt">
								<fo:leader leader-pattern="rule" leader-length="100%"
									rule-thickness="0.3pt" />
							</fo:block>
							<fo:block margin-top="2pt" text-align="left" margin-right="0.3px" padding-right="-2.0px" >
								The KAP assessments measure students’ understanding of the Kansas
								College and Career Ready Standards at each
								grade. Interim assessments are given by teachers during the school
								year to gauge their students’ learning progress.
								Along with summative (end-of-year) testing and formative assessment
								processes, interim assessments provide an
								integral part of a complete assessment system. Interim assessments help
								teachers not only measure students’ success
								in mastering material but also encourage student achievement.
							</fo:block>

							<fo:block margin-top="13pt" text-align="left" >
								Your student recently took the predictive interim assessment. The
								black bar represents the range of likely scores your
								student could receive on the KAP summative assessment based on your
								student’s performance from the predictive
								interim assessment.
							</fo:block>

							<fo:block>
								<fo:leader leader-pattern="rule" leader-length="100%"
									rule-thickness="0.3pt" margin-top="1pt"/>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
	</xsl:template>

	<xsl:template name="lastName-Header">
		<xsl:variable name="lastNameString"
			select="interimReportDetails/data/studentLastName"></xsl:variable>
		<xsl:variable name="firstNameString"
			select="interimReportDetails/data/studentFirstName"></xsl:variable>
		<xsl:if test="string-length($firstNameString) &gt;= '15'">
			<fo:inline>
				<xsl:value-of select="substring($lastNameString,1,15)" />
			</fo:inline>
		</xsl:if>
		<xsl:if test="string-length($firstNameString) &lt; '15'">
			<xsl:variable name="countFirstName"
				select="((15 - string-length($firstNameString)) + 15)" />
			<fo:inline>
				<xsl:value-of select="substring($lastNameString,1,$countFirstName)" />
			</fo:inline>
		</xsl:if>
	</xsl:template>
	<xsl:template name="firstName-Header">
		<xsl:variable name="firstNameString"
			select="interimReportDetails/data/studentFirstName"></xsl:variable>
		<xsl:variable name="lastNameString"
			select="interimReportDetails/data/studentLastName"></xsl:variable>
		<xsl:if test="string-length($lastNameString) &gt;= '15'">
			<fo:inline>
				<xsl:value-of select="substring($firstNameString,1,15)" />
			</fo:inline>
		</xsl:if>
		<xsl:if test="string-length($lastNameString) &lt; '15'">
			<xsl:variable name="countLastName"
				select="((15 - string-length($lastNameString)) + 15)" />
			<fo:inline>
				<xsl:value-of select="substring($firstNameString,1,$countLastName)" />
			</fo:inline>
		</xsl:if>
	</xsl:template>

	<xsl:template name="footerLogoSection">
		<fo:block width="100%" height="100%">
			<fo:external-graphic
				content-height="scale-down-to-fit"
				inline-progression-dimension.maximum="100%"
				block-progression-dimension.maximum="100%" height="32mm" width="30cm" >
				<xsl:attribute name="src"><xsl:value-of
					select="interimReportDetails/footerLogoPath" /></xsl:attribute>
			</fo:external-graphic>
		</fo:block>
	</xsl:template>

	<xsl:template name="credit_earned_icon_legand_image">
		<xsl:param name="path" />	
			<fo:external-graphic content-width="scale-down-to-fit"
				content-height="scale-down-to-fit"
				inline-progression-dimension.maximum="5mm"
				block-progression-dimension.maximum="5mm">
				<xsl:attribute name="src"><xsl:value-of
					select="$path" /></xsl:attribute>
			</fo:external-graphic>
	</xsl:template>

 	<xsl:template name="interim-level-description">
	    <xsl:for-each select="interimReportDetails/levelDescriptions/edu.ku.cete.domain.report.LevelDescription">
				<fo:block>
	   				<xsl:value-of select="substring-before( concat(levelDescription, '~' ) , '~' )" />
	   			</fo:block>
	   			<xsl:variable name="tempLevelDescription" select="substring-after( concat(levelDescription, '~' ), '~')"/>
	       		<fo:block>
							    <xsl:call-template name="interim-splitStringToItems">
							       <xsl:with-param name="delimiter" />
							       <xsl:with-param name="list" select="$tempLevelDescription" />
							     </xsl:call-template>
				</fo:block>
	    </xsl:for-each>
	</xsl:template>
	
	<xsl:template name="interim-splitStringToItems">
	   <xsl:param name="list" />
	   <xsl:param name="delimiter" select="'~'"  />
	   <xsl:variable name="delim">
	     <xsl:choose>
		       <xsl:when test="string-length($delimiter)=0"><xsl:value-of select="'~'"/></xsl:when>
		       <xsl:otherwise>
		         <xsl:value-of select="$delimiter"/>
		       </xsl:otherwise>
	     </xsl:choose>
	   </xsl:variable>
	   <xsl:variable name="newlist">
	     <xsl:choose>
		       <xsl:when test="contains($list, $delim)">
		        	 <xsl:value-of select="normalize-space($list)" />
		       </xsl:when>
		       <xsl:otherwise>
		        	 <xsl:value-of select="concat(normalize-space($list), $delim)"/>
		       </xsl:otherwise>
	     </xsl:choose>
	   </xsl:variable>
	   <xsl:variable name="first" select="substring-before($newlist, $delim)" />
	   <xsl:variable name="remaining" select="substring-after($newlist, $delim)" />
	   <xsl:if test="((string-length($first)!='0'))">
	   <fo:block>
	   <fo:table>
	   		<fo:table-column column-width="3%"/>
	       	<fo:table-column column-width="97%"/>
	      		<fo:table-body>
				   <fo:table-row>
						<fo:table-cell height="0.45cm" width="0.4cm">
							<fo:block-container>
					            <fo:block>
									<fo:instream-foreign-object content-width="0.22cm" content-height="0.23cm">
										<svg xmlns="http://www.w3.org/2000/svg" width="10" height="10" version="1.1">
										   	<polygon points="2,10 2,1 10,5" fill="#000000" fill-opacity="1"/>
										</svg>
									</fo:instream-foreign-object>
								</fo:block>
							</fo:block-container>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block-container>
								<fo:block>
									<xsl:value-of select="$first" />
								</fo:block>
							</fo:block-container>
						</fo:table-cell>
				   </fo:table-row>
	   		</fo:table-body>
	   </fo:table>
	   </fo:block>
       </xsl:if>
	   <xsl:if test="$remaining">
	     <xsl:call-template name="interim-splitStringToItems">
		       <xsl:with-param name="list" select="$remaining" />
		       <xsl:with-param name="delimiter" select="$delim" />
	     </xsl:call-template>
	   </xsl:if>
	</xsl:template>
	
</xsl:stylesheet>