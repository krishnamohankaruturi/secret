<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:saxon="http://saxon.sf.net/"
	extension-element-prefixes="saxon">
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="listingReport"
					page-width="8.5in" page-height="11in" margin-left=".5in"
					margin-right=".5in" margin-top=".5in" margin-bottom=".5in">
					<fo:region-body region-name="xsl-region-body" />
					<fo:region-after region-name="xsl-region-after"
						extent=".1in" />
				</fo:simple-page-master>
				<fo:simple-page-master master-name="listing"
					page-width="8.5in" page-height="11in" margin-left=".5in"
					margin-right=".5in" margin-top=".5in" margin-bottom=".5in">
					<fo:region-body region-name="xsl-region-body" />
					<fo:region-after region-name="xsl-region-after"
						extent=".1in" />
				</fo:simple-page-master>
				<fo:simple-page-master master-name="listingStudentProblem"
					page-width="11in" page-height="8.5in" margin-left=".5in"
					margin-right=".5in" margin-top=".5in" margin-bottom=".5in">
					<fo:region-body region-name="xsl-region-body" />
					<fo:region-after region-name="xsl-region-after"
						extent=".1in" />
				</fo:simple-page-master>
				<fo:simple-page-master master-name="listingGraphs"
					page-width="9.5in" page-height="13in" margin-left=".5in"
					margin-right=".5in" margin-top=".5in" margin-bottom=".5in">
					<fo:region-body region-name="xsl-region-body" />
					<fo:region-after region-name="xsl-region-after"
						extent=".1in" />
				</fo:simple-page-master>				
			</fo:layout-master-set>
			<fo:page-sequence master-reference="listingReport">
				<fo:static-content flow-name="xsl-region-after">
					<fo:block font-size="11pt" text-align="right">
						<fo:page-number format="1" />
					</fo:block>
				</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
					<fo:block font-size="11pt">
						<xsl:call-template name="reportHeader">
							<xsl:with-param name="reportType" select="'Alphabetically By Name'" />
						</xsl:call-template>
						<xsl:call-template name="studentsListingReport" />
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
			<fo:page-sequence master-reference="listing">
				<fo:static-content flow-name="xsl-region-after">
					<fo:block font-size="11pt" text-align="right">
						<fo:page-number format="1" />
					</fo:block>
				</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
					<fo:block font-size="11pt">
						<xsl:call-template name="reportHeader">
							<xsl:with-param name="reportType" select="'By Performance'" />
						</xsl:call-template>
						<xsl:call-template name="listingReport" />
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
			<fo:page-sequence master-reference="listingStudentProblem">
				<fo:static-content flow-name="xsl-region-after">
					<fo:block font-size="11pt" text-align="right">
						<fo:page-number format="1" />
					</fo:block>
				</fo:static-content>

				<fo:flow flow-name="xsl-region-body">
					<fo:block font-size="9pt">
						<xsl:for-each
							select="/rawscorereport/studentProblemReports/studentproblemreport">
							<xsl:call-template name="reportHeader1">
								<xsl:with-param name="testSectionName" select="testSectionName" />
							</xsl:call-template>
							<xsl:call-template name="studentsListingReportCheck">
								<xsl:with-param name="iterations" select="currentRowCount" />
								<xsl:with-param name="rowQuestionsCount"
									select="currentRowCount + rowQuestionsCount" />
								<xsl:with-param name="rowResponseCount" select="rowResponseCount" />
								<xsl:with-param name="sortedStudentNames" select="sortedStudentNames" />
								<xsl:with-param name="scoreRowIndex" select="scoreRowIndex" />
								<xsl:with-param name="numOfResponses" select="numOfResponses" />
								<xsl:with-param name="correctResponses" select="correctResponses" />

							</xsl:call-template>
							<xsl:text>&#xA;</xsl:text>
							<xsl:text>&#xA;</xsl:text>
							<xsl:variable name="newline">
								<xsl:text>&#xA;</xsl:text>
							</xsl:variable>
						</xsl:for-each>

					</fo:block>
				</fo:flow>
			</fo:page-sequence>
			<fo:page-sequence master-reference="listingGraphs">
				<fo:static-content flow-name="xsl-region-after">
					<fo:block font-size="11pt" text-align="right">
						<fo:page-number format="1" />
					</fo:block>
				</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
					<fo:block>
						<xsl:call-template name="reportHeaderGraph">
						</xsl:call-template>
					</fo:block>
					<fo:table table-layout="fixed" width="100%" space-after="10px">
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell padding-top="5px" padding-left="5px"
									padding-bottom="5px">

									<xsl:call-template name="itemperformanceGraphs">
										<xsl:with-param name="graphImage"
											select="/rawscorereport/graphImage" />
									</xsl:call-template>

								</fo:table-cell>
							</fo:table-row>
						</fo:table-body>
					</fo:table>

				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	<xsl:template name="reportHeader">
		<xsl:param name="reportType" />
		<xsl:variable name="testName" select="/rawscorereport/testName"></xsl:variable>
		<fo:table table-layout="fixed" width="100%" background-color="#BABABA"
			space-after="10px">
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell padding-top="5px" padding-left="5px"
						padding-bottom="5px">
						<fo:block font-weight="bold">
							<xsl:choose>
								<xsl:when test="contains($testName, ' ')">
									<xsl:value-of select="$testName" />
								</xsl:when>
								<xsl:otherwise>
									<xsl:if test="string-length($testName) &lt;= 60">
										<xsl:value-of select="/rawscorereport/testName" />
									</xsl:if>
									<xsl:if test="string-length($testName) &gt; 60">
										<xsl:value-of select="substring($testName,0,60)" />
									</xsl:if>
								</xsl:otherwise>
							</xsl:choose>
						</fo:block>
						<fo:block font-weight="bold">
							<xsl:value-of select="/rawscorereport/reportName" />
						</fo:block>
						<fo:block font-weight="bold">
							Student Score Report -
							<xsl:value-of select="$reportType" />
						</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="right" padding-right="5px"
						padding-top="5px" padding-bottom="5px">
						<fo:block font-weight="bold">
							Date:
							<xsl:value-of select="/rawscorereport/date" />
						</fo:block>
						<fo:block font-weight="bold">
							Subject:
							<xsl:value-of select="/rawscorereport/subject" />
						</fo:block>
						<fo:block font-weight="bold">
							Grade:
							<xsl:value-of select="/rawscorereport/grade" />
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>
	<xsl:template name="reportHeader1">
		<xsl:param name="testSectionName" />
		<xsl:variable name="testName" select="/rawscorereport/testName"></xsl:variable>
		<fo:table table-layout="fixed" width="100%" background-color="#BABABA"
			space-after="10px">
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell padding-top="5px" padding-left="5px"
						padding-bottom="5px">
						<fo:block font-weight="bold">
							<xsl:choose>
								<xsl:when test="contains($testName, ' ')">
									<xsl:value-of select="$testName" />
								</xsl:when>
								<xsl:otherwise>
									<xsl:if test="string-length($testName) &lt;= 60">
										<xsl:value-of select="/rawscorereport/testName" />
									</xsl:if>
									<xsl:if test="string-length($testName) &gt; 60">
										<xsl:value-of select="substring(0,60,testName)" />
									</xsl:if>
								</xsl:otherwise>
							</xsl:choose>
						</fo:block>
						<fo:block font-weight="bold">
							<xsl:value-of select="/rawscorereport/reportName" />
						</fo:block>
						<fo:block font-weight="bold">
							Student Problem (S-P) Table :
							<xsl:value-of select="$testSectionName"></xsl:value-of>
						</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="right" padding-right="5px"
						padding-top="5px" padding-bottom="5px">
						<fo:block font-weight="bold">
							Date:
							<xsl:value-of select="/rawscorereport/date" />
						</fo:block>
						<fo:block font-weight="bold">
							Subject:
							<xsl:value-of select="/rawscorereport/subject" />
						</fo:block>
						<fo:block font-weight="bold">
							Grade:
							<xsl:value-of select="/rawscorereport/grade" />
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>
	<xsl:template name="reportHeaderGraph">
		<xsl:variable name="testName" select="/rawscorereport/testName"></xsl:variable>
		<fo:table table-layout="fixed" width="100%" background-color="#BABABA"
			space-after="10px">
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell padding-top="5px" padding-left="5px"
						padding-bottom="5px">
						<fo:block font-weight="bold">
							<xsl:choose>
								<xsl:when test="contains($testName, ' ')">
									<xsl:value-of select="$testName" />
								</xsl:when>
								<xsl:otherwise>
									<xsl:if test="string-length($testName) &lt; 60">
										<xsl:value-of select="/rawscorereport/testName" />
									</xsl:if>
									<xsl:if test="string-length($testName) &gt; 60">
										<xsl:value-of select="substring(0,60,testName)" />
									</xsl:if>
								</xsl:otherwise>
							</xsl:choose>
						</fo:block>
						<fo:block font-weight="bold">
							<xsl:value-of select="/rawscorereport/reportName" />
						</fo:block>
						<fo:block font-weight="bold">
							Item Performance Graphs
						</fo:block>
					</fo:table-cell>
					<fo:table-cell text-align="right" padding-right="5px"
						padding-top="5px" padding-bottom="5px">
						<fo:block font-weight="bold">
							Date:
							<xsl:value-of select="/rawscorereport/date" />
						</fo:block>
						<fo:block font-weight="bold">
							Subject:
							<xsl:value-of select="/rawscorereport/subject" />
						</fo:block>
						<fo:block font-weight="bold">
							Grade:
							<xsl:value-of select="/rawscorereport/grade" />
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>
	<xsl:template name="studentsListingReport">
		<fo:block>
			<xsl:choose>
				<xsl:when test="count(/rawscorereport/studentScores/studentrawscore) > 0">
					<fo:table table-layout="fixed" width="100%">
						<fo:table-header>
							<xsl:call-template name="tableHeader" />
						</fo:table-header>
						<fo:table-body>
							<xsl:call-template name="printStudentsByAlpha" />
						</fo:table-body>
					</fo:table>
				</xsl:when>
				<xsl:otherwise>
					<fo:block>
						No responses were found for this test session.
					</fo:block>
				</xsl:otherwise>
			</xsl:choose>
		</fo:block>
	</xsl:template>
	<xsl:template name="studentsListingReportCheck">
		<xsl:param name="iterations" />
		<xsl:param name="rowQuestionsCount" />
		<xsl:param name="rowResponseCount" />
		<xsl:param name="sortedStudentNames" />
		<xsl:param name="correctResponses"></xsl:param>
		<xsl:param name="numOfResponses" />
		<xsl:param name="scoreRowIndex" />
		<fo:block space-after="10mm">
			<xsl:choose>
				<xsl:when
					test="count(/rawscorereport/studentProblemReports/studentproblemreport) > 0">
					<fo:table table-layout="fixed" width="100%">
						<fo:table-header>
							<xsl:call-template name="tableHeaderCheck">
								<xsl:with-param name="iterations" select="$iterations" />
								<xsl:with-param name="numOfResponses" select="$numOfResponses" />
								<xsl:with-param name="rowQuestionsCount" select="$rowQuestionsCount" />
							</xsl:call-template>
						</fo:table-header>
						<fo:table-body>
							<xsl:call-template name="printStudentsByAlphaCheck">
								<xsl:with-param name="sortedStudentNames" select="$sortedStudentNames" />
								<xsl:with-param name="iterations" select="$iterations" />
								<xsl:with-param name="correctResponses" select="$correctResponses" />
								<xsl:with-param name="rowQuestionsCount" select="$rowQuestionsCount" />
								<xsl:with-param name="rowResponseCount" select="$rowResponseCount" />
								<xsl:with-param name="scoreRowIndex" select="$scoreRowIndex" />
								<xsl:with-param name="position" select="1" />
							</xsl:call-template>
						</fo:table-body>
					</fo:table>
				</xsl:when>
				<xsl:otherwise>
					<fo:block>
						No responses were found for this test session.
					</fo:block>
				</xsl:otherwise>
			</xsl:choose>
		</fo:block>
	</xsl:template>
	<xsl:template name="listingReport">
		<fo:block>
			<xsl:choose>
				<xsl:when test="count(/rawscorereport/studentScores/studentrawscore) > 0">
					<fo:table table-layout="fixed" width="100%">
						<fo:table-header>
							<xsl:call-template name="tableHeader" />
						</fo:table-header>
						<fo:table-body>
							<xsl:call-template name="printStudentsByScore" />
						</fo:table-body>
					</fo:table>
				</xsl:when>
				<xsl:otherwise>
					<fo:block>
						No responses were found for this test session.
					</fo:block>
				</xsl:otherwise>
			</xsl:choose>
		</fo:block>
	</xsl:template>
	<xsl:template name="itemperformanceGraphs">
		<xsl:param name="graphImage" />
		<fo:block font-size="9pt">
			<xsl:if test="contains($graphImage,';')">

				<xsl:variable name="tempGraph" select="substring-before($graphImage,';')" />
				<xsl:variable name="sectionAndNumber" select="substring-after($tempGraph,'|')" />
				<xsl:variable name="testSectionName" select="substring-before($sectionAndNumber,':')" />
				<xsl:variable name="chartNumber" select="substring-after($sectionAndNumber,':')" />
				<xsl:variable name="graph" select="substring-before($tempGraph,'|')" />
				<fo:external-graphic src='"{$graph}"' />
				<xsl:call-template name="printStudentResponses">
					<xsl:with-param name="testSectionName" select="$testSectionName" />
					<xsl:with-param name="chartNumber" select="$chartNumber" />
				</xsl:call-template>
				<xsl:variable name="graphImages1" select="substring-after($graphImage,';')" /> 
				<xsl:call-template name="itemperformanceGraphs">
					<xsl:with-param name="graphImage" select="$graphImages1" />
				</xsl:call-template>



			</xsl:if>
		</fo:block>
	</xsl:template>
	<xsl:template name="tableHeader">
		<fo:table-row border-after-width="2px"
			border-before-width="2px" border-before-color="#000000"
			border-before-style="solid" border-after-color="#000000"
			border-after-style="solid">
			<fo:table-cell text-align="center">
				<fo:block font-weight="bold" font-size="12pt">Student Name
				</fo:block>
			</fo:table-cell>
			<fo:table-cell text-align="center">
				<fo:block font-weight="bold" font-size="12pt">Local Student ID
				</fo:block>
			</fo:table-cell>
			<fo:table-cell text-align="center">
				<fo:block font-weight="bold" font-size="12pt">Raw Score</fo:block>
			</fo:table-cell>
			<fo:table-cell text-align="center">
				<fo:block font-weight="bold" font-size="12pt">% Correct</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>
	<xsl:template name="tableHeaderCheck">
		<xsl:param name="iterations" />
		<xsl:param name="rowQuestionsCount" />
		<xsl:param name="numOfResponses" />
		<fo:table-row>
			<fo:table-cell>
				<fo:block>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell>
				<fo:block>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell number-columns-spanned='13'>
				<fo:block text-align-last="justify" font-weight="bold"
					font-size="9pt">
					Easiest
					<fo:leader leader-pattern="dots" />
					Hardest
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
		<fo:table-row border-after-width="2px"
			border-before-width="2px" border-before-color="#000000"
			border-before-style="solid" border-after-color="#000000"
			border-after-style="solid">
			<fo:table-cell text-align="center" display-align="center">
				<fo:block font-weight="bold" font-size="9pt">Student Name
				</fo:block>
			</fo:table-cell>

			<xsl:call-template name="numQuestionsLoop">
				<xsl:with-param name="iterations" select="$iterations" />
				<xsl:with-param name="numOfResponses" select="$numOfResponses"></xsl:with-param>
				<xsl:with-param name="rowQuestionsCount" select="$rowQuestionsCount" />
			</xsl:call-template>
		</fo:table-row>
	</xsl:template>
	<xsl:template name="printStudentsByAlpha">
		<xsl:for-each select="/rawscorereport/studentScores/studentrawscore">
			<xsl:sort select="student/legalLastName" />
			<xsl:sort select="student/legalFirstName" />
			<fo:table-row border-top-width="1px" border-left-width="1px"
				border-bottom-width="1px" border-right-width="1px" border-top-style="solid"
				border-right-style="solid" border-bottom-style="solid"
				border-left-style="solid" border-top-color="#000000">
				<fo:table-cell padding-top="5pt" padding-left="2px"
					text-align="left" width="3.75in">
					<fo:block>
						<xsl:value-of select="substring(student/legalLastName, 0, 40)" />
						,
						<xsl:text>
                           		</xsl:text>
						<xsl:value-of select="substring(student/legalFirstName, 0, 40)" />
					</fo:block>
				</fo:table-cell>
				<fo:table-cell padding-top="5pt" text-align="center"
					width="1.75in">
					<fo:block>
						<xsl:value-of select="localStudentIdentifier" />
					</fo:block>
				</fo:table-cell>
				<fo:table-cell padding-top="5pt" text-align="center"
					width="1.00in">
					<fo:block>
						<xsl:value-of select="numCorrect" />
						/
						<xsl:value-of select="numQuestions" />
					</fo:block>
				</fo:table-cell>
				<fo:table-cell padding-top="5pt" text-align="center"
					width="1.00in">
					<xsl:choose>
						<xsl:when test="numQuestions &gt; 0">
							<fo:block>
								<xsl:value-of select="round(numCorrect div numQuestions * 100)" />
								%
							</fo:block>
						</xsl:when>
						<xsl:otherwise>
							<fo:block>
								<xsl:value-of select="format-number(0, '0%')" />
							</fo:block>
						</xsl:otherwise>
					</xsl:choose>
				</fo:table-cell>
			</fo:table-row>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="printStudentsByAlphaCheck">
		<xsl:param name="sortedStudentNames" />
		<xsl:param name="iterations" />
		<xsl:param name="correctResponses"></xsl:param>
		<xsl:param name="rowQuestionsCount" />
		<xsl:param name="rowResponseCount" />
		<xsl:param name="scoreRowIndex" />
		<xsl:param name="position" />

		<xsl:if test="($position  &lt; $scoreRowIndex)">
			<fo:table-row border-top-width="1px" border-left-width="1px"
				border-bottom-width="1px" border-right-width="1px" border-top-style="solid"
				border-right-style="solid" border-bottom-style="solid"
				border-left-style="solid" border-top-color="#000000">
				<fo:table-cell padding-top="5pt" padding-left="2px"
					text-align="left" width="2.75in" display-align="center">
					<fo:block font-size="8pt" font-weight="bold">
						<xsl:value-of
							select="substring-before(substring(substring-before($sortedStudentNames,';'),6),',')" />
						,
						<xsl:text>
                        </xsl:text>
						<xsl:value-of
							select="substring-after(substring(substring-before($sortedStudentNames,';'),6),',')" />
					</fo:block>
				</fo:table-cell>
				<xsl:call-template name="printCorrectResponses">
					<xsl:with-param name="correctResponses"
						select="substring($correctResponses, ((($position * $rowResponseCount) - $rowResponseCount) * 6) + 1, (($position * $rowResponseCount) * 6) + 1)" />
					<xsl:with-param name="iterations" select="$iterations" />
					<xsl:with-param name="rowResponseCount"
						select="($position * $rowResponseCount)" />
					<xsl:with-param name="colCount"
						select="(($position * $rowResponseCount) - $rowResponseCount + 1)" />
					<xsl:with-param name="imagePath"
						select="/rawscorereport/studentProblemReports/studentproblemreport/imagePath" />
				</xsl:call-template>
			</fo:table-row>
			<xsl:call-template name="printStudentsByAlphaCheck">
				<xsl:with-param name="sortedStudentNames"
					select="substring-after($sortedStudentNames,';')" />
				<xsl:with-param name="iterations" select="$iterations" />
				<xsl:with-param name="correctResponses" select="$correctResponses" />
				<xsl:with-param name="rowQuestionsCount" select="$rowQuestionsCount" />
				<xsl:with-param name="rowResponseCount" select="$rowResponseCount" />
				<xsl:with-param name="scoreRowIndex" select="$scoreRowIndex" />
				<xsl:with-param name="position" select="$position+1" />
			</xsl:call-template>
		</xsl:if>
		<xsl:if test="($position = $scoreRowIndex)">
			<fo:table-row border-top-width="1px" border-left-width="1px"
				border-bottom-width="1px" border-right-width="1px" border-top-style="solid"
				border-right-style="solid" border-bottom-style="solid"
				border-left-style="solid" border-top-color="#000000">
				<fo:table-cell padding-top="5pt" padding-left="2px"
					text-align="left" width="2.75in" display-align="center">
					<fo:block font-size="8pt" font-weight="bold">
						% of Students with
						correct answer
					</fo:block>
				</fo:table-cell>
				<xsl:call-template name="printCorrectResponses">
					<xsl:with-param name="correctResponses"
						select="substring($correctResponses, ((($scoreRowIndex * $rowResponseCount) - $rowResponseCount) * 6) + 1)" />
					<xsl:with-param name="iterations" select="$iterations" />
					<xsl:with-param name="rowResponseCount"
						select="($scoreRowIndex * $rowResponseCount)" />
					<xsl:with-param name="colCount"
						select="(($scoreRowIndex * $rowResponseCount) - $rowResponseCount + 1)" />
					<xsl:with-param name="imagePath"
						select="/rawscorereport/studentProblemReports/studentproblemreport/imagePath" />
				</xsl:call-template>
			</fo:table-row>
			<xsl:call-template name="printStudentsByAlphaCheck">
				<xsl:with-param name="sortedStudentNames"
					select="substring-after($sortedStudentNames,';')" />
				<xsl:with-param name="iterations" select="$iterations" />
				<xsl:with-param name="correctResponses" select="$correctResponses" />
				<xsl:with-param name="rowQuestionsCount" select="$rowQuestionsCount" />
				<xsl:with-param name="rowResponseCount" select="$rowResponseCount" />
				<xsl:with-param name="scoreRowIndex" select="$scoreRowIndex" />
				<xsl:with-param name="position" select="$position+1" />
			</xsl:call-template>
		</xsl:if>

	</xsl:template>

	<xsl:template name="printStudentsByScore">
		<xsl:for-each select="/rawscorereport/studentScores/studentrawscore">
			<xsl:sort select="numCorrect" data-type="number" order="descending" />
			<xsl:sort select="student/legalLastName" />
			<xsl:sort select="student/legalFirstName" />
			<fo:table-row border-top-width="1px" border-left-width="1px"
				border-bottom-width="1px" border-right-width="1px" border-top-style="solid"
				border-right-style="solid" border-bottom-style="solid"
				border-left-style="solid" border-top-color="#000000">
				<fo:table-cell padding-top="5pt" padding-left="2px"
					text-align="left" width="3.75in">
					<fo:block>
						<xsl:value-of select="substring(student/legalLastName, 0, 40)" />
						,
						<xsl:text> 
	                           </xsl:text>
						<xsl:value-of select="substring(student/legalFirstName, 0, 40)" />
					</fo:block>
				</fo:table-cell>
				<fo:table-cell padding-top="5pt" text-align="center"
					width="1.75in">
					<fo:block>
						<xsl:value-of select="localStudentIdentifier" />
					</fo:block>
				</fo:table-cell>
				<fo:table-cell padding-top="5pt" text-align="center"
					width="1.00in">
					<fo:block>
						<xsl:value-of select="numCorrect" />
						/
						<xsl:value-of select="numQuestions" />
					</fo:block>
				</fo:table-cell>
				<fo:table-cell padding-top="5pt" text-align="center"
					width="1.00in">
					<xsl:choose>
						<xsl:when test="numQuestions &gt; 0">
							<fo:block>
								<xsl:value-of select="round(numCorrect div numQuestions * 100)" />
								%
							</fo:block>
						</xsl:when>
						<xsl:otherwise>
							<fo:block>
								<xsl:value-of select="format-number(0, '0%')" />
							</fo:block>
						</xsl:otherwise>
					</xsl:choose>
				</fo:table-cell>
			</fo:table-row>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="numQuestionsLoop">
		<xsl:param name="iterations" />
		<xsl:param name="rowQuestionsCount" />
		<xsl:param name="numOfResponses" />
		<xsl:if test="contains($numOfResponses,';')">
			<fo:table-cell display-align="center" margin-left=".25in">
				<fo:block font-size="9pt">

					<xsl:value-of select="substring-before($numOfResponses,';')" />

				</fo:block>
			</fo:table-cell>
			<xsl:variable name="numOfResponses1"
				select="substring-after($numOfResponses,';')" />
			<xsl:call-template name="numQuestionsLoop">
				<xsl:with-param name="numOfResponses" select="$numOfResponses1" />
				<xsl:with-param name="iterations" select="$iterations + 1" />
				<xsl:with-param name="rowQuestionsCount" select="$rowQuestionsCount" />
			</xsl:call-template>
		</xsl:if>
	</xsl:template>
	<xsl:template name="printCorrectResponses">
		<xsl:param name="correctResponses" />
		<xsl:param name="iterations" />
		<xsl:param name="rowResponseCount" />
		<xsl:param name="colCount" />
		<xsl:variable name="imagePath"
			select="/rawscorereport/studentProblemReports/studentproblemreport/imagePath"></xsl:variable>
		<xsl:if test="($colCount  &lt;= $rowResponseCount)">

			<fo:table-cell display-align="center" margin-left=".25in">
				<fo:block>
					<xsl:choose>
						<xsl:when test="substring-before($correctResponses,';') = '0000T'">
							<fo:external-graphic src='url(images/check.jpg)' />
						</xsl:when>
						<xsl:when test="substring-before($correctResponses,';') = '-----'">
							<fo:block font-size="9pt">
								<xsl:value-of
									select="substring(substring-before($correctResponses,';'),4)" />
							</fo:block>
						</xsl:when>
						<xsl:otherwise>
							<fo:block font-size="9pt">
								<xsl:if test="substring-before($correctResponses,';') != '     '">
									<xsl:choose>
										<xsl:when
											test="substring-before($correctResponses,';') = '0000A' or substring-before($correctResponses,';') = '0000B' or substring-before($correctResponses,';') = '0000C' or
								    substring-before($correctResponses,';') = '0000D' or substring-before($correctResponses,';') = '0000E' or substring-before($correctResponses,';') = '0000F' or substring-before($correctResponses,';') = '0000G' or
								    substring-before($correctResponses,';') = '0000H' or substring-before($correctResponses,';') = '0000I' or substring-before($correctResponses,';') = '0000J'">
											<xsl:value-of
												select="substring(substring-before($correctResponses,';'),5)" />
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of
												select="round(substring-before($correctResponses,';'))" />
										</xsl:otherwise>
									</xsl:choose>
								</xsl:if>
							</fo:block>
						</xsl:otherwise>
					</xsl:choose>
				</fo:block>
			</fo:table-cell>

			<xsl:variable name="correctResponses1"
				select="substring-after($correctResponses,';')" />
			<xsl:variable name="imagePath1" select="$imagePath" />
			<xsl:call-template name="printCorrectResponses">
				<xsl:with-param name="correctResponses" select="$correctResponses1" />
				<xsl:with-param name="iterations" select="$iterations + 1" />
				<xsl:with-param name="rowResponseCount" select="$rowResponseCount" />
				<xsl:with-param name="colCount" select="$colCount + 1" />
				<xsl:with-param name="imagePath"
					select="/rawscorereport/studentProblemReports/studentproblemreport/imagePath" />
			</xsl:call-template>
		</xsl:if>
	</xsl:template>
	<xsl:template name="printStudentResponses">
		<xsl:param name="testSectionName"/>
		<xsl:param name="chartNumber"/>
		<fo:table table-layout="fixed" width="100%" space-after="10px">
			<xsl:if test="position() != last()">
				<xsl:attribute name="break-after">page</xsl:attribute>
			</xsl:if>
			<fo:table-header>
				<fo:table-row>
					<fo:table-cell padding-top="5pt" padding-left="2px"
						text-align="left" border-top-width="1px" border-left-width="1px"
					border-bottom-width="1px" border-right-width="1px" border-top-style="solid"
					border-right-style="solid" border-bottom-style="solid"
					border-left-style="solid" width="3in">
						<fo:block>Student Name</fo:block>
					</fo:table-cell>
					<xsl:for-each select="/rawscorereport//studentResponseReports/studentresponsereport[@sectionName=$testSectionName and @chartNumber=$chartNumber][1]/qAndRs/qandr">
						<fo:table-cell padding-top="5pt" text-align="center" border-width="1px" border-style="solid"
									width="0.5in">
									<fo:block><xsl:value-of select="taskIdentifier" /></fo:block>
						</fo:table-cell>
					</xsl:for-each>
				</fo:table-row>
			</fo:table-header>
			<fo:table-body>
				<xsl:for-each select="/rawscorereport/studentResponseReports/studentresponsereport[@sectionName=$testSectionName and @chartNumber=$chartNumber]">
					<xsl:sort select="studentLastName" />
					<xsl:sort select="studentFirstName" />
						<fo:table-row>
							<fo:table-cell padding-top="5pt" padding-left="2px"
								text-align="left" border-top-width="1px" border-left-width="1px"
							border-bottom-width="1px" border-right-width="1px" border-top-style="solid"
							border-right-style="solid" border-bottom-style="solid"
							border-left-style="solid" width="3in">
								<fo:block>
									<xsl:value-of select="substring(studentLastName, 0, 40)" />
									,
									<xsl:text> 
				                           </xsl:text>
									<xsl:value-of select="substring(studentFirstName, 0, 40)" />
								</fo:block>
							</fo:table-cell>
							<xsl:for-each select="qAndRs/qandr">
								<fo:table-cell padding-top="5pt" border-top-width="1px" border-left-width="1px"
							border-bottom-width="1px" border-right-width="1px" border-top-style="solid"
							border-right-style="solid" border-bottom-style="solid"
							border-left-style="solid" border-top-color="#000000" text-align="center"
									width="0.5in">
									<fo:block>
										<xsl:choose>
											<xsl:when test="isCorrectResponse = 'true'">
												<fo:inline color="green">
													<xsl:value-of select="response" />
												</fo:inline>
											</xsl:when>
											<xsl:when test="isCorrectResponse = 'false' and response != '-'">
												<fo:inline color="red">
													<xsl:value-of select="response" />
												</fo:inline>
											</xsl:when>
											<xsl:otherwise>
												<fo:inline color="black">
													<xsl:value-of select="response" />
												</fo:inline>
											</xsl:otherwise>
										</xsl:choose>
									</fo:block>
								</fo:table-cell>
							</xsl:for-each>
						</fo:table-row>
				</xsl:for-each>
			</fo:table-body>
		</fo:table>
	</xsl:template>
</xsl:stylesheet>