<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo" extension-element-prefixes="saxon">
	<xsl:template name="explanation">
		<xsl:param name="reportType"/>
		<xsl:choose>
			<xsl:when test="reportDetails/data/assessmentProgramCode = 'AMP'">
				<xsl:choose>
					<xsl:when test="$reportType = 'school'">
						<fo:table table-layout="fixed" keep-with-previous.within-page="always">
							<fo:table-column column-width="proportional-column-width(4)" />
							<fo:table-column column-width="proportional-column-width(92)" />
							<fo:table-column column-width="proportional-column-width(4)" />
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell text-align="center" column-number="2" >
										<fo:block>
											<fo:block-container height="123px">
												<fo:block-container position="absolute" width="454px" left="15px">
													<fo:block><fo:instream-foreign-object>
														<svg width="464" height="122" xmlns="http://www.w3.org/2000/svg" version="1.1">
															<g>
																<rect x="5" y="0" rx="15" ry="15" width="454" height="121" style="fill:#fff;stroke:#3784C6;stroke-width:1pt;opacity:1" />
															</g>
														</svg>
													</fo:instream-foreign-object></fo:block>
												</fo:block-container>
												<fo:block-container position="absolute" width="448px" top="2mm" left="19px">
													<fo:block>
														<fo:block>Explanation of Median and Standard Error</fo:block>
														<fo:block-container text-align="start">
															<fo:block font-size="8pt"  margin-top="2mm">
																School, district, and state scores on this report are represented by the median score. A median
																		is the middle number in an ordered list of numbers. For example, in the ordered list of scores
																		{200, 210, 220, 230, 240, 250, 260}, the score of 230 is the
																		median. The graphs show how the school’s median scores compare to the median score for all students in the same grade who took the test in the district and state.
															</fo:block>
															<fo:block font-size="8pt"  margin-top="2mm">
																Each score is also associated with a standard error of measurement (SE). The standard error around a student's score indicates how much a student's score might vary if the student took many equivalent versions of the test (a test with different items but covering the same content). The SE around the school, district, and state scores can be interpreted in a similar way. Standard error generally becomes smaller with larger comparison groups.
															</fo:block>
														</fo:block-container>
													</fo:block>
												</fo:block-container>
											</fo:block-container>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
					</xsl:when>
					<xsl:when test="$reportType = 'district'">
						<fo:table table-layout="fixed" keep-with-previous.within-page="always">
							<fo:table-column column-width="proportional-column-width(4)" />
							<fo:table-column column-width="proportional-column-width(92)" />
							<fo:table-column column-width="proportional-column-width(4)" />
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell text-align="center" column-number="2" >
										<fo:block>
											<fo:block-container height="123px">
												<fo:block-container position="absolute" width="454px" left="15px">
													<fo:block><fo:instream-foreign-object>
														<svg width="464" height="122" xmlns="http://www.w3.org/2000/svg" version="1.1">
															<g>
																<rect x="5" y="0" rx="15" ry="15" width="454" height="121" style="fill:#fff;stroke:#3784C6;stroke-width:1pt;opacity:1" />
															</g>
														</svg>
													</fo:instream-foreign-object></fo:block>
												</fo:block-container>
												<fo:block-container position="absolute" width="448px" top="2mm" left="19px">
													<fo:block>
														<fo:block>Explanation of Median and Standard Error</fo:block>
														<fo:block-container text-align="start">
															<fo:block font-size="8pt"  margin-top="2mm">
																District and state scores on this report are represented by the median score. A median
																		is the middle number in an ordered list of numbers. For example, in the ordered list of scores
																		{200, 210, 220, 230, 240, 250, 260}, the score of 230 is the
																		median. The graphs show how the district’s median scores compare to the median score for all students in the same grade who took the test in the state. 
															</fo:block>
															<fo:block font-size="8pt"  margin-top="2mm">
																Each score is also associated with a standard error of measurement (SE). The standard error around a student's score indicates how much a student's score might vary if the student took many equivalent versions of the test (a test with different items but covering the same content). The SE around the school, district, and state scores can be interpreted in a similar way. Standard error generally becomes smaller with larger comparison groups.
															</fo:block>
														</fo:block-container>
													</fo:block>
												</fo:block-container>
											</fo:block-container>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
					</xsl:when>
					<xsl:otherwise>
						<fo:table table-layout="fixed" keep-with-previous.within-page="always">
							<fo:table-column column-width="proportional-column-width(4)" />
							<fo:table-column column-width="proportional-column-width(92)" />
							<fo:table-column column-width="proportional-column-width(4)" />
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell text-align="center" column-number="2" >
										<fo:block>
											<fo:block-container height="123px">
												<fo:block-container position="absolute" width="454px" left="15px">
													<fo:block><fo:instream-foreign-object>
														<svg width="464" height="122" xmlns="http://www.w3.org/2000/svg" version="1.1">
															<g>
																<rect x="5" y="0" rx="15" ry="15" width="454" height="121" style="fill:#fff;stroke:#3784C6;stroke-width:1pt;opacity:1" />
															</g>
														</svg>
													</fo:instream-foreign-object></fo:block>
												</fo:block-container>
												<fo:block-container position="absolute" width="448px" top="2mm" left="19px">
													<fo:block>
														<fo:block>Explanation of Median and Standard Error</fo:block>
														<fo:block-container text-align="start">
															<fo:block font-size="8pt"  margin-top="2mm">
																School, district, and state scores on this report are represented by the median score. A median
																		is the middle number in an ordered list of numbers. For example, in the ordered list of scores
																		{200, 210, 220, 230, 240, 250, 260}, the score of 230 is the
																		median. The graphs show how the student's score compares
																		to the median score for all students in the same
																		grade who took the test in the school, district, and
																		state.
															</fo:block>
															<fo:block font-size="8pt"  margin-top="2mm">
																Each score is also associated with a standard error of measurement (SE). The standard error around a student's score indicates how much a student's score might vary if the student took many equivalent versions of the test (a test with different items but covering the same content). The SE around the school, district, and state scores can be interpreted in a similar way. Standard error generally becomes smaller with larger comparison groups.
															</fo:block>
														</fo:block-container>
													</fo:block>
												</fo:block-container>
											</fo:block-container>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<fo:table table-layout="fixed" keep-with-previous.within-page="always">
					<fo:table-column column-width="proportional-column-width(4)" />
					<fo:table-column column-width="proportional-column-width(92)" />
					<fo:table-column column-width="proportional-column-width(4)" />
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell text-align="center" column-number="2" >
								<fo:block>
									<fo:block-container height="123px">
										<fo:block-container position="absolute" width="454px" left="15px">
											<fo:block><fo:instream-foreign-object>
												<svg width="464" height="122" xmlns="http://www.w3.org/2000/svg" version="1.1">
													<g>
														<rect x="5" y="0" rx="15" ry="15" width="454" height="121" style="fill:#fff;stroke:#3784C6;stroke-width:1pt;opacity:1" />
													</g>
												</svg>
											</fo:instream-foreign-object></fo:block>
										</fo:block-container>
										<fo:block-container position="absolute" width="448px" top="2mm" left="19px">
											<fo:block>
												<fo:block>Explanation of Median and Standard Error</fo:block>
												<fo:block-container text-align="start">
													<fo:block font-size="8pt"  margin-top="2mm">
														School, district, and state scores on this report are represented by the median score. A median
																is the middle number in an ordered list of numbers. For example, in the ordered list of scores
																{200, 210, 220, 230, 240, 250, 260}, the score of 230 is the
																median. The graphs show how the student's score compares
																to the median score for all students in the same
																grade who took the test in the school, district, and
																state.
													</fo:block>
													<fo:block font-size="8pt"  margin-top="2mm">
														Each score is also associated with a standard error of measurement (SE). The standard error around a student's score indicates how much a student's score might vary if the student took many equivalent versions of the test (a test with different items but covering the same content). The SE around the school, district, and state scores can be interpreted in a similar way. Standard error generally becomes smaller with larger comparison groups.
													</fo:block>
												</fo:block-container>
											</fo:block>
										</fo:block-container>
									</fo:block-container>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>