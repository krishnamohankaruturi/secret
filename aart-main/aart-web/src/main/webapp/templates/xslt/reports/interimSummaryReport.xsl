<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java fo"
	extension-element-prefixes="saxon">

	<xsl:include href="interimReportUtility.xsl" />
	

	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format"
			font-family="Verdana" font-size="9pt">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="allPages"
					page-height="27.94cm" page-width="21.59cm" margin="1.27cm">
					<fo:region-body margin-top="25mm" />
					<fo:region-before region-name="xsl-region-before-firstPage"
						extent="30mm" />
					<fo:region-after region-name="xsl-region-after-firstPage"
						extent="60mm" display-align="after" />
				</fo:simple-page-master>		
			</fo:layout-master-set>
			<fo:page-sequence master-reference="allPages">
				<fo:static-content flow-name="xsl-region-before-firstPage">
					<xsl:call-template name="interim-summary-report-page1-header" />
				</fo:static-content>
				
				<fo:static-content flow-name="xsl-region-after-firstPage">
					<xsl:call-template name="reportFooter" />
				</fo:static-content>


				<fo:flow flow-name="xsl-region-body">
					<!-- 1st page -->
					<fo:block margin-top="-25pt">
						<fo:block font-size="9pt" >
							<xsl:call-template name="interim_summary_header_intro_paragraph_text" />
						</fo:block>
					</fo:block>

					<fo:block font-family="Verdana" font-weight="bold"
						margin-top="14pt" font-size="11pt" line-height="13.5pt"
						text-align="center" wrap-option="no-wrap">
						<xsl:value-of select="//interimReportDetails/data/subjectName" />
						<xsl:text>&#32;</xsl:text>
						<xsl:value-of select="//interimReportDetails/data/reportCycle" />
						<xsl:text>&#32;</xsl:text>
						<xsl:text>Predictive Interim Assessment Results</xsl:text>
					</fo:block>
							
					<fo:block text-align="center" space-before="5pt"
							space-after="5pt">
							 <fo:table table-layout="fixed" text-align="center" border="solid #000 0.6pt" >
								<fo:table-column column-width="4.5%" />
								<fo:table-column column-width="80%" />
								<fo:table-column column-width="9%" />
								<fo:table-column column-width="6.5%" />

								<fo:table-header>
									<fo:table-cell height="10mm" padding="1pt"
										border="solid #000 0.6pt" display-align="center">
										<fo:block text-align="center" display-align="center"
											font-size="10pt">
											<xsl:text></xsl:text>
										</fo:block>
									</fo:table-cell>

									<fo:table-cell height="10mm" padding="1pt"
										border="solid #000 0.6pt" display-align="center">
										<fo:block text-align="center" display-align="center"
											font-size="10pt">
											<xsl:text>Question Description</xsl:text>
										</fo:block>
									</fo:table-cell>

									<fo:table-cell height="10mm" padding="1pt"
										border="solid #000 0.6pt" display-align="center">
										<fo:block text-align="center" display-align="center"
											font-size="10pt">
											<xsl:choose>
					         					<xsl:when test="//interimReportDetails/data/attendanceSchoolId != ''">
					         						<xsl:text>School PCT</xsl:text>
					         					</xsl:when>
												<xsl:otherwise>
													<xsl:text>District PCT</xsl:text>										
												</xsl:otherwise>
											</xsl:choose>
											<fo:inline font-size="8pt" vertical-align="top">&#42;</fo:inline>
										</fo:block>
									</fo:table-cell>

									<fo:table-cell height="10mm" padding="1pt"
										border="solid #000 0.6pt" display-align="center">
										<fo:block text-align="center" display-align="center"
											font-size="10pt">
											<xsl:text>State PCT</xsl:text>
										</fo:block>
									</fo:table-cell>
								</fo:table-header>

								<fo:table-body>

									<xsl:for-each
										select="//interimReportDetails/data/reportQuestionInformation/edu.ku.cete.domain.report.StudentReportQuestionInfo">
										<fo:table-row>

											<fo:table-cell height="5mm" display-align="center"  padding="0.4pt"
												border="solid #000 0.6pt">
												<fo:block text-align="center" display-align="center">
													<xsl:value-of select="position()" />
												</fo:block>
											</fo:table-cell>

											<fo:table-cell height="5mm" display-align="center"  padding="0.4pt"
												border="solid #000 0.6pt" padding-left="4pt" >
												<fo:block text-align="left" display-align="center" >
													<xsl:value-of select="questionDescription" />
												</fo:block>
											</fo:table-cell>

											<fo:table-cell height="5mm" display-align="center"  padding="0.4pt"
												border="solid #000 0.6pt">
												<fo:block text-align="center" display-align="center">
													<xsl:value-of select="fullCreditPercent" />
												</fo:block>
											</fo:table-cell>

											<fo:table-cell height="5mm" display-align="center"  padding="0.4pt"
												border="solid #000 0.6pt">
												<fo:block text-align="center" display-align="center">
													<xsl:value-of select="creditPercent" />
												</fo:block>
											</fo:table-cell>

										</fo:table-row>
									</xsl:for-each>
								</fo:table-body>
							</fo:table>
						</fo:block>

					<fo:block text-align="left" >
						* Percentage of students who received full credit.
					</fo:block>
					
					<fo:block text-align="left" space-before="8pt" >
						 Number of students who did not answer all of the questions = <xsl:value-of select="//interimReportDetails/data/unAnsweredStudentCount" />.
					</fo:block>

						<fo:block>

							<fo:block-container absolute-position="absolute" top="205mm" >
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
														<xsl:call-template name="summaryFooterLogoSection" />
													</fo:block-container>
												</fo:table-cell>
											</fo:table-row>
										</fo:table-body>
									</fo:table>
								</fo:block>
							</fo:block-container>

						</fo:block>


				</fo:flow>

			</fo:page-sequence>
		</fo:root>
	</xsl:template>

</xsl:stylesheet>