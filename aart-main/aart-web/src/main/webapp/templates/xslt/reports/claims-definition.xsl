<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo" extension-element-prefixes="saxon">
<xsl:template name="claims-definition">
	<xsl:param name="reportType"/>
	<xsl:if test="reportDetails/data/assessmentProgramCode = 'AMP'">
		<xsl:if test="reportDetails/data/contentAreaCode = 'M'">
			<fo:block margin-top="4mm">
				<xsl:choose>
					<xsl:when test="$reportType = 'student'">
						Mathematics test questions cover four main areas (also called claims) of the Alaska Mathematics Standards. There are fewer questions on the test for Problem Solving, Communicating and Reasoning, and Modeling and Data Analysis. Therefore, these have been grouped together on the graph.
					</xsl:when>
					<xsl:otherwise>
						Mathematics test questions cover four main areas (also called claims) of the Alaska Mathematics Standards. 
					</xsl:otherwise>
				</xsl:choose>
			</fo:block>
			<fo:block margin-top="3mm" margin-left="3mm">
				<fo:table table-layout="fixed">
					<fo:table-column column-width="proportional-column-width(1.5)" />
					<fo:table-column column-width="proportional-column-width(98.5)" />
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell padding="3.5px"><fo:block font-family="Symbol">&#x2022;</fo:block></fo:table-cell>
							<fo:table-cell padding="5pt"><fo:block>Claim 1: Concepts and Procedures. These questions require students to explain and apply mathematical concepts and interpret and carry out mathematical procedures with precision and fluency.</fo:block></fo:table-cell>
						</fo:table-row>
						<fo:table-row>
							<fo:table-cell padding="3.5px"><fo:block font-family="Symbol">&#x2022;</fo:block></fo:table-cell>
							<fo:table-cell padding="5pt"><fo:block>Claim 2: Problem Solving. These questions require students to solve a range of complex problems using knowledge, problem solving strategies, and mathematical tools.</fo:block></fo:table-cell>
						</fo:table-row>
						<fo:table-row>
							<fo:table-cell padding="3.5px"><fo:block font-family="Symbol">&#x2022;</fo:block></fo:table-cell>
							<fo:table-cell padding="5pt"><fo:block>Claim 3: Communicating and Reasoning. These questions require students to explain their reasoning, defend their answers, critique the reasoning of others and ask clarifying questions.</fo:block></fo:table-cell>
						</fo:table-row>
						<fo:table-row>
							<fo:table-cell padding="3.5px"><fo:block font-family="Symbol">&#x2022;</fo:block></fo:table-cell>
							<fo:table-cell padding="5pt"><fo:block>Claim 4: Modeling and Data Analysis. These questions require students to analyze complex, real-world situations and construct and use mathematical models to solve problems, as well as interpret their result in the context of a situation.</fo:block></fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>
		</xsl:if>
		<xsl:if test="reportDetails/data/contentAreaCode = 'ELA'">
			<fo:block margin-top="4mm">
				<xsl:choose>
					<xsl:when test="$reportType = 'student'">
						English Language Arts test questions cover four main areas (also called claims) of the Alaska English Language Arts Standards. Claim 1 (Reading) is shown as a whole subscore and as two component subscores for literature and informational texts.
					</xsl:when>
					<xsl:otherwise>
						English Language Arts test questions cover four main areas (also called claims) of the Alaska English Language Arts Standards.  
					</xsl:otherwise>
				</xsl:choose>
			</fo:block>
			<fo:block margin-top="3mm" margin-left="3mm">
				<fo:table table-layout="fixed">
					<fo:table-column column-width="proportional-column-width(1.5)" />
					<fo:table-column column-width="proportional-column-width(98.5)" />
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell padding="3.5px"><fo:block font-family="Symbol">&#x2022;</fo:block></fo:table-cell>
							<fo:table-cell padding="5pt"><fo:block>Claim 1: Reading. These questions require students to read and comprehend literary (a story or poem, for example) and information texts (such as a science-related article or historical speech). Claim 1 measures reading skills such as identifying central ideas, determining word meanings, and using evidence to support inferences. This claim is further divided into Reading Literature (RL) and Reading Informational (RI) texts.</fo:block></fo:table-cell>
						</fo:table-row>
						<fo:table-row>
							<fo:table-cell padding="3.5px"><fo:block font-family="Symbol">&#x2022;</fo:block></fo:table-cell>
							<fo:table-cell padding="5pt"><fo:block>Claim 2: Writing. These questions require students to apply writing and language skills to edit and revise composed text. These questions may require the student to read a few sentences or brief paragraphs to provide the student enough context for determining audience. Claim 2 measures writing skills such as revising text into a logical order; identifying words or phrases to replace incorrect text given a purpose, audience, or task; or identifying and correcting errors in grammar, spelling, and mechanics.</fo:block></fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>
		</xsl:if>
	</xsl:if>
	<xsl:if test="reportDetails/data/assessmentProgramCode = 'KAP'">
		<xsl:if test="reportDetails/data/contentAreaCode = 'M'">
			<fo:block margin-top="4mm">
				<xsl:choose>
					<xsl:when test="$reportType = 'student'">
						Mathematics test questions cover four main areas (also called claims) of the Kansas Mathematics Standards. There are fewer questions on the test for Problem Solving, Communicating and Reasoning, and Modeling and Data Analysis. Therefore, these have been grouped together on the graph.
					</xsl:when>
					<xsl:otherwise>
						Mathematics test questions cover four main areas (also called claims) of the Kansas Mathematics Standards. 
					</xsl:otherwise>
				</xsl:choose>
			</fo:block>
			<fo:block margin-top="3mm" margin-left="3mm">
				<fo:table table-layout="fixed">
					<fo:table-column column-width="proportional-column-width(1.5)" />
					<fo:table-column column-width="proportional-column-width(98.5)" />
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell padding="3.5px"><fo:block font-family="Symbol">&#x2022;</fo:block></fo:table-cell>
							<fo:table-cell padding="5pt"><fo:block>Claim 1: Concepts and Procedures. These questions require students to explain and apply mathematical concepts and interpret and carry out mathematical procedures with precision and fluency.</fo:block></fo:table-cell>
						</fo:table-row>
						<fo:table-row>
							<fo:table-cell padding="3.5px"><fo:block font-family="Symbol">&#x2022;</fo:block></fo:table-cell>
							<fo:table-cell padding="5pt"><fo:block>Claim 2: Problem Solving. These questions require students to solve a range of complex problems using knowledge, problem solving strategies, and mathematical tools.</fo:block></fo:table-cell>
						</fo:table-row>
						<fo:table-row>
							<fo:table-cell padding="3.5px"><fo:block font-family="Symbol">&#x2022;</fo:block></fo:table-cell>
							<fo:table-cell padding="5pt"><fo:block>Claim 3: Communicating and Reasoning. These questions require students to explain their reasoning, defend their answers, critique the reasoning of others and ask clarifying questions.</fo:block></fo:table-cell>
						</fo:table-row>
						<fo:table-row>
							<fo:table-cell padding="3.5px"><fo:block font-family="Symbol">&#x2022;</fo:block></fo:table-cell>
							<fo:table-cell padding="5pt"><fo:block>Claim 4: Modeling and Data Analysis. These questions require students to analyze complex, real-world situations and construct and use mathematical models to solve problems, as well as interpret their result in the context of a situation.</fo:block></fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>
		</xsl:if>
		<xsl:if test="reportDetails/data/contentAreaCode = 'ELA'">
			<fo:block margin-top="4mm">
				<xsl:choose>
					<xsl:when test="$reportType = 'student'">
						English Language Arts test questions cover four main areas (also called claims) of the Kansas English Language Arts Standards. Claim 1 (Reading) is shown as a whole subscore and as two component subscores for literature and informational texts.
					</xsl:when>
					<xsl:otherwise>
						English Language Arts test questions cover four main areas (also called claims) of the Kansas English Language Arts Standards.  
					</xsl:otherwise>
				</xsl:choose>
			</fo:block>
			<fo:block margin-top="3mm" margin-left="3mm">
				<fo:table table-layout="fixed">
					<fo:table-column column-width="proportional-column-width(1.5)" />
					<fo:table-column column-width="proportional-column-width(98.5)" />
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell padding="3.5px"><fo:block font-family="Symbol">&#x2022;</fo:block></fo:table-cell>
							<fo:table-cell padding="5pt"><fo:block>Claim 1: Reading. These questions require students to read and comprehend literary (a story or poem, for example) and information texts (such as a science-related article or historical speech). Claim 1 measures reading skills such as identifying central ideas, determining word meanings, and using evidence to support inferences. This claim is further divided into Reading Literature (RL) and Reading Informational (RI) texts.</fo:block></fo:table-cell>
						</fo:table-row>
						<fo:table-row>
							<fo:table-cell padding="3.5px"><fo:block font-family="Symbol">&#x2022;</fo:block></fo:table-cell>
							<fo:table-cell padding="5pt"><fo:block>Claim 2: Writing. These questions require students to apply writing and language skills to edit and revise composed text. These questions may require the student to read a few sentences or brief paragraphs to provide the student enough context for determining audience. Claim 2 measures writing skills such as revising text into a logical order; identifying words or phrases to replace incorrect text given a purpose, audience, or task; or identifying and correcting errors in grammar, spelling, and mechanics.</fo:block></fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>
		</xsl:if>
	</xsl:if>
</xsl:template>
</xsl:stylesheet>