<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java fo"
	extension-element-prefixes="saxon">

	<xsl:include href="interimReportUtility.xsl" />
	<xsl:include href="interimStudentReportScoreCharts.xsl" />

	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format"
			font-family="Verdana" font-size="9pt">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="firstPage"
					page-height="27.94cm" page-width="21.59cm" margin="1.27cm">
					<fo:region-body margin-top="25mm" />
					<fo:region-before region-name="xsl-region-before-firstPage"
						extent="30mm" />
					<fo:region-after region-name="xsl-region-after-firstPage"
						extent="60mm" display-align="after" />
				</fo:simple-page-master>
				<fo:simple-page-master master-name="nonFirstPage"
					page-height="27.94cm" page-width="21.59cm" margin="1.27cm">
					<fo:region-body margin-top="7mm" margin-bottom="15mm" />
					<fo:region-before region-name="xsl-region-before-nonFirstPage"
						extent="7mm" />
					<fo:region-after region-name="xsl-region-after-nonFirstPage"
						extent="8.5mm" display-align="after" />
				</fo:simple-page-master>
				<fo:page-sequence-master master-name="allPages">
					<fo:repeatable-page-master-alternatives>
						<fo:conditional-page-master-reference
							master-reference="firstPage" page-position="first" />
						<fo:conditional-page-master-reference
							master-reference="nonFirstPage" page-position="any" />
					</fo:repeatable-page-master-alternatives>
				</fo:page-sequence-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="allPages">
				<fo:static-content flow-name="xsl-region-before-firstPage">
					<xsl:call-template name="report-page1-header" />
				</fo:static-content>
				<fo:static-content flow-name="xsl-region-before-nonFirstPage">
					<fo:block margin-bottom="0px" font-family="Verdana"
						font-size="9pt">
						<fo:table table-layout="fixed" background-color="white">
							<fo:table-column column-width="32%" />
							<fo:table-column column-width="34%" />
							<fo:table-column column-width="34%" />
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell text-align="start" wrap-option="no-wrap">
										<fo:block text-align="left">STUDENT REPORT</fo:block>
									</fo:table-cell>
									<fo:table-cell text-align="center" display-align="center">
										<fo:block-container text-align="center"
											display-align="center">
											<fo:block wrap-option="no-wrap">
												STUDENT: <xsl:call-template name="lastName-Header" />, <xsl:call-template name="firstName-Header" />
											</fo:block>
											<fo:block>
												STATE ID:
												<xsl:call-template name="stateStudentIdentifier" />
											</fo:block>
										</fo:block-container>
									</fo:table-cell>
									<fo:table-cell text-align="end">
										<fo:block text-align="right">
											<xsl:text>GRADE: </xsl:text>
											<xsl:value-of select="interimReportDetails/data/gradeCode" />
											<xsl:text>&#32;</xsl:text>
											<xsl:value-of select="interimReportDetails/data/subjectName" />
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
					</fo:block>
				</fo:static-content>


				<fo:static-content flow-name="xsl-region-after-firstPage">
					<fo:block>
						<fo:leader leader-pattern="rule" leader-length="100%"
							rule-style="solid" space-after="5pt" rule-thickness="0.3pt" />
					</fo:block>
					<fo:block font-size="9pt" font-family="Verdana"
						text-align="left" margin-top="2.5pt">
						<fo:inline>
							<xsl:text>For more details about how your student performed on specific test questions, see the back of this report.</xsl:text>
							<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
							<fo:inline padding-left="4pt" text-align="right">
								<xsl:call-template name="footerNextPageArrow" />
							</fo:inline>
						</fo:inline>
					</fo:block>
				</fo:static-content>

				<fo:static-content flow-name="xsl-region-after-nonFirstPage">
					<xsl:call-template name="reportFooter" />
				</fo:static-content>


				<fo:flow flow-name="xsl-region-body">
					<!-- 1st page -->
					<fo:block margin-top="-25pt">
						<fo:block font-size="9pt">
							<xsl:call-template name="header_intro_paragraph_text" />
						</fo:block>
					</fo:block>

					<fo:block font-family="Verdana" font-weight="bold"
						margin-top="14pt" font-size="12pt" line-height="13.5pt"
						text-align="center" wrap-option="no-wrap">
						<xsl:value-of select="interimReportDetails/data/subjectName" /><xsl:text>&#32;</xsl:text><xsl:text>Interim Assessment Report</xsl:text>
					</fo:block>
					<fo:block font-family="Verdana" font-weight="bold"
						font-size="11pt" line-height="13.5pt" text-align="center"
						wrap-option="no-wrap">
						<xsl:text>Projected End-of-Year Score Range</xsl:text>
					</fo:block>



					<!-- Report Cycle Chart -->

					<fo:block margin-top="10pt">
						<xsl:call-template name="interimReportCycleScoreGraph">
						</xsl:call-template>
					</fo:block>


					<fo:block font-family="Verdana" font-weight="bold"
						margin-top="30pt" font-size="8.9pt" line-height="13.5pt"
						wrap-option="no-wrap">
						<xsl:text>The benchmark for college and career readiness is Level 3.</xsl:text>
					</fo:block>
					<fo:block font-family="Verdana" font-weight="bold"
						font-size="8.9pt" line-height="13.5pt" wrap-option="no-wrap">
						<xsl:text>Students who score at Level 3 can typically</xsl:text>
					</fo:block>

					<fo:block text-align="left" font-size="9pt">
						<xsl:call-template name="interim-level-description">
						</xsl:call-template>
					</fo:block>




					<!-- 2nd page -->
					<fo:block break-before="page" font-family="Verdana">
						<fo:block space-before="5pt" space-after="5pt">
							<fo:leader leader-pattern="rule" leader-length="100%"
								rule-thickness="0.3pt" />
						</fo:block>

						<fo:block font-size="9pt" space-before="5pt" space-after="5pt">
							<xsl:text>This chart shows how your student performed on each question that appeared on the most recent interim assessment.
							The Credit Earned column provides a symbol indicating whether the student received full, partial, or no credit for the question or that the question was not answered. The percent (PCT) reflects the number of students out of 100 who earned full credit on this question. Higher
							numbers indicate an easier question; lower numbers indicate a more difficult question.</xsl:text>
						</fo:block>
						<fo:block font-family="Verdana" font-weight="bold"
							font-size="12pt" space-before="10pt" space-after="6pt"
							wrap-option="no-wrap" text-align="center">
							<xsl:text>Your Student’s Results</xsl:text>
						</fo:block>

						<fo:block text-align="center" display-align="center"
							space-after="5pt">
							<fo:table text-align="center">		
								<fo:table-body>
									<fo:table-row text-align="center" display-align="center" >										
										<fo:table-cell height="5mm" text-align="right" display-align="center" font-size="10pt" width="103pt" >
											<fo:block font-weight="bold" 
												display-align="center">
												<xsl:text>Key:&#160;&#160;</xsl:text>
											</fo:block>
										</fo:table-cell>

										<fo:table-cell height="5mm" display-align="center" text-align="left" width="16pt" font-size="9pt">
											<fo:block display-align="center">
												<fo:external-graphic content-width="scale-down-to-fit"
													content-height="scale-down-to-fit"
													inline-progression-dimension.maximum="5mm"
													block-progression-dimension.maximum="5mm">
													<xsl:attribute name="src"><xsl:value-of
														select="interimReportDetails/noCreditPath" /></xsl:attribute>
												</fo:external-graphic>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell height="5mm" display-align=" center" text-align="left" font-size="9pt" width="57pt" >
											<fo:block display-align="center">
												<xsl:text>No Credit</xsl:text>
											</fo:block>
										</fo:table-cell>

										<fo:table-cell height="5mm" display-align="center" font-size="9pt" text-align="left" width="16pt" >
											<fo:block  display-align="center">
												<fo:external-graphic content-width="scale-down-to-fit"
													content-height="scale-down-to-fit"
													inline-progression-dimension.maximum="5mm"
													block-progression-dimension.maximum="5mm">
													<xsl:attribute name="src"><xsl:value-of
														select="interimReportDetails/partialCreditPath" /></xsl:attribute>
												</fo:external-graphic>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell height="5mm" display-align="center" text-align="left" width="75pt">
											<fo:block display-align="center">
												<xsl:text>Partial Credit</xsl:text>
											</fo:block>
										</fo:table-cell>

										<fo:table-cell height="5mm" display-align="center" text-align="left" width="16pt" >
											<fo:block display-align="center">
												<fo:external-graphic content-width="scale-down-to-fit"
													content-height="scale-down-to-fit"
													inline-progression-dimension.maximum="5mm"
													block-progression-dimension.maximum="5mm">
													<xsl:attribute name="src"><xsl:value-of
														select="interimReportDetails/fullCreditPath" /></xsl:attribute>
												</fo:external-graphic>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell height="5mm" display-align="center" font-size="9pt"  text-align="left" width="66pt" >
											<fo:block display-align="center">
												<xsl:text>Full Credit</xsl:text>
											</fo:block>
										</fo:table-cell>

										<fo:table-cell height="5mm" display-align="center" font-size="9pt" text-align="left" width="16pt" >
											<fo:block display-align="center">
												<fo:external-graphic content-width="scale-down-to-fit"
													content-height="scale-down-to-fit"
													inline-progression-dimension.maximum="5mm"
													block-progression-dimension.maximum="5mm">
													<xsl:attribute name="src"><xsl:value-of
														select="interimReportDetails/questionUnansweredPath" /></xsl:attribute>
												</fo:external-graphic>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell height="5mm" display-align="center" font-size="9pt" text-align="left" >
											<fo:block display-align="center">
												<xsl:text>Question Unanswered</xsl:text>
											</fo:block>
										</fo:table-cell>

									</fo:table-row>
								</fo:table-body>
							</fo:table>
						</fo:block>

						<xsl:variable name="noCreditPath">
							<xsl:value-of select="interimReportDetails/noCreditPath" />
						</xsl:variable>
						<xsl:variable name="partialCreditPath">
							<xsl:value-of select="interimReportDetails/partialCreditPath" />
						</xsl:variable>
						<xsl:variable name="fullCreditPath">
							<xsl:value-of select="interimReportDetails/fullCreditPath" />
						</xsl:variable>
						<xsl:variable name="questionUnansweredPath">
							<xsl:value-of select="interimReportDetails/questionUnansweredPath" />
						</xsl:variable>

						<fo:block text-align="center" space-before="5pt"
							space-after="5pt">
							<fo:table table-layout="fixed" text-align="center" border="solid #000 0.5pt" >
								<fo:table-column column-width="4.5%" />
								<fo:table-column column-width="80%" />
								<fo:table-column column-width="9%" />
								<fo:table-column column-width="6.5%" />

								<fo:table-header>
									<fo:table-cell height="10mm" padding="1pt"
										border="solid #000 0.5pt" display-align="center">
										<fo:block text-align="center" display-align="center"
											font-size="11pt">
											<xsl:text></xsl:text>
										</fo:block>
									</fo:table-cell>

									<fo:table-cell height="10mm" padding="1pt"
										border="solid #000 0.5pt" display-align="center">
										<fo:block text-align="center" display-align="center"
											font-size="11pt">
											<xsl:text>Question Description</xsl:text>
										</fo:block>
									</fo:table-cell>

									<fo:table-cell height="10mm" padding="1pt"
										border="solid #000 0.5pt" display-align="center">
										<fo:block text-align="center" display-align="center"
											font-size="11pt">
											<xsl:text>Credit Earned</xsl:text>
										</fo:block>
									</fo:table-cell>

									<fo:table-cell height="10mm" padding="1pt"
										border="solid #000 0.5pt" display-align="center">
										<fo:block text-align="center" display-align="center"
											font-size="11pt">
											<xsl:text>PCT</xsl:text>
										</fo:block>
									</fo:table-cell>
								</fo:table-header>

								<fo:table-body>

									<xsl:for-each
										select="interimReportDetails/reportQuestionInformation/edu.ku.cete.domain.report.StudentReportQuestionInfo">
										<fo:table-row>

											<fo:table-cell height="5mm" display-align="center"  padding="0.4pt"
												border="solid #000 0.5pt">
												<fo:block text-align="center" display-align="center">
													<xsl:value-of select="position()" />
												</fo:block>
											</fo:table-cell>

											<fo:table-cell height="5mm" display-align="center"  padding="0.4pt"
												border="solid #000 0.5pt" padding-left="4pt" >
												<fo:block text-align="left" display-align="center" >
													<xsl:value-of select="questionDescription" />
												</fo:block>
											</fo:table-cell>

											<fo:table-cell height="5mm" display-align="center"  padding="0.4pt"
												border="solid #000 0.5pt">
												<fo:block text-align="center" display-align="center">
													<xsl:if test="creditEarnedCode = 'NO_CREDIT'">
														<xsl:call-template name="credit_earned_icon_legand_image">
															<xsl:with-param name="path" select="$noCreditPath"></xsl:with-param>
														</xsl:call-template>
													</xsl:if>
													<xsl:if test="creditEarnedCode = 'PARTIAL_CREDIT'">
														<xsl:call-template name="credit_earned_icon_legand_image">
															<xsl:with-param name="path" select="$partialCreditPath"></xsl:with-param>
														</xsl:call-template>
													</xsl:if>
													<xsl:if test="creditEarnedCode = 'FULL_CREDIT'">
														<xsl:call-template name="credit_earned_icon_legand_image">
															<xsl:with-param name="path" select="$fullCreditPath"></xsl:with-param>
														</xsl:call-template>
													</xsl:if>
													<xsl:if test="creditEarnedCode = 'QUESTION_UNANSWERED'">
														<xsl:call-template name="credit_earned_icon_legand_image">
															<xsl:with-param name="path"
																select="$questionUnansweredPath"></xsl:with-param>
														</xsl:call-template>
													</xsl:if>

												</fo:block>
											</fo:table-cell>

											<fo:table-cell height="5mm" display-align="center"  padding="0.4pt"
												border="solid #000 0.5pt">
												<fo:block text-align="center" display-align="center">
													<xsl:value-of select="creditPercent" />
												</fo:block>
											</fo:table-cell>

										</fo:table-row>
									</xsl:for-each>
								</fo:table-body>
							</fo:table>
						</fo:block>

						<fo:block margin-top="25pt">

							<fo:block-container absolute-position="absolute"
								top="223mm">
								<fo:block margin-top="2pt">
									<fo:table table-layout="fixed" font-family="Verdana"
										font-size="8pt">
										<fo:table-column column-width="proportional-column-width(80)" />
										<fo:table-column column-width="proportional-column-width(20)" />
										<fo:table-body>
											<fo:table-row>
												<fo:table-cell text-align="left">
													<fo:block font-size="8pt">
														<fo:block>
															<fo:block text-align="left" font-weight="bold"
																font-family="Verdana" font-size="8pt">Additional Resources
															</fo:block>
															<fo:block margin-top="1pt">
																<fo:block margin-top="1pt">
																	<xsl:text>For sample test questions, visit </xsl:text>
																	<fo:inline font-weight="bold">
																		<xsl:text>ksassessments.org/interactive-demos</xsl:text>
																	</fo:inline>
																	<xsl:text>.</xsl:text>
																</fo:block>
																<fo:block margin-top="1pt">
																	<xsl:text>For information about the Kansas College and Career Ready Standards, visit </xsl:text>
																	<fo:inline font-weight="bold">
																		<xsl:text>ksde.org</xsl:text>
																	</fo:inline>
																	<xsl:text>.</xsl:text>
																</fo:block>
																<fo:block margin-top="1pt">
																	<xsl:text>To learn about the Kansas Assessment Program, visit </xsl:text>
																	<fo:inline font-weight="bold">
																		<xsl:text>ksassessments.org</xsl:text>
																	</fo:inline>
																	<xsl:text>.</xsl:text>
																</fo:block>
															</fo:block>

														</fo:block>
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="right"
													margin-bottom="5mm">
													<fo:block-container text-align="right"
														absolute-position="absolute" top="-16mm">
														<xsl:call-template name="footerLogoSection" />
													</fo:block-container>
												</fo:table-cell>
											</fo:table-row>
										</fo:table-body>
									</fo:table>
								</fo:block>
							</fo:block-container>

						</fo:block>

					</fo:block>




				</fo:flow>

			</fo:page-sequence>
		</fo:root>
	</xsl:template>

</xsl:stylesheet>