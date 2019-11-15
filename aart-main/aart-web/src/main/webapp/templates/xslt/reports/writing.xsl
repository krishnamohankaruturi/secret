<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:fo="http://www.w3.org/1999/XSL/Format" extension-element-prefixes="saxon">
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" font-family="Verdana" font-size="10pt">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="writing-page-master" page-height="11in" page-width="8.5in" margin=".5in">
					<!--
						region-body margins MUST be equal to or greater than the "extent" values in region-before and region-after,
						otherwise content could (and probably will) overlap and look terrible.
					-->
					<fo:region-body margin-top="2in" margin-bottom=".5in" />
					<fo:region-before region-name="xsl-region-page-header" extent="2in" display-align="before" />
					<fo:region-after region-name="xsl-region-page-footer" extent="1in" display-align="after" />
				</fo:simple-page-master>
				<fo:page-sequence-master master-name="allPages">
					<fo:repeatable-page-master-alternatives>
						<fo:conditional-page-master-reference master-reference="writing-page-master" page-position="any" />
					</fo:repeatable-page-master-alternatives>
				</fo:page-sequence-master>
			</fo:layout-master-set>
			
			<xsl:for-each select="reportContext/data/edu.ku.cete.web.StudentReportDTO">
				<xsl:variable name="pageSequenceId"><xsl:value-of select="studentId"/></xsl:variable>
				<fo:page-sequence id="{$pageSequenceId}" master-reference="allPages" initial-page-number="1" force-page-count="no-force">
					<fo:static-content flow-name="xsl-region-page-header">
						<fo:block>
							<xsl:call-template name="page-header"/>
						</fo:block>
					</fo:static-content>
					<fo:static-content flow-name="xsl-region-page-footer">
						<fo:block text-align="end">
							Page <fo:page-number/> of <fo:page-number-citation-last ref-id="{$pageSequenceId}"/>
						</fo:block>
					</fo:static-content>
					<fo:flow flow-name="xsl-region-body">
						<fo:block-container font-weight="bold">
							<fo:block>
								Question <xsl:value-of select="positionInTest"/> &#8212;
								<xsl:choose>
									<xsl:when test="(taskScore != '') or (nonScoreReasonId != '')">
										Scored
									</xsl:when>
									<xsl:otherwise>
										Needs Scoring
									</xsl:otherwise>
								</xsl:choose>
							</fo:block>
						</fo:block-container>
						<fo:block>
							<xsl:value-of select="writingResponseFO" disable-output-escaping="yes"/>
						</fo:block>
					</fo:flow>
				</fo:page-sequence>
			</xsl:for-each>
		</fo:root>
	</xsl:template>
	<xsl:template name="page-header">
		<fo:table table-layout="fixed">
			<fo:table-column column-width="proportional-column-width(33)" />
			<fo:table-column column-width="proportional-column-width(33)" />
			<fo:table-column column-width="proportional-column-width(33)" />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell>
						<fo:block-container font-family="Verdana" font-weight="bold" font-size="11pt" line-height="12.5pt">
							<fo:block font-size="9pt" color="#25408f" margin-left=".35in" padding-top="2pt">
								<xsl:value-of select="schoolYear - 1"/><xsl:text >&#8212;</xsl:text><xsl:value-of select="schoolYear"/>
							</fo:block>
							<fo:block-container height="2cm" width="3cm">
								<fo:block>
									<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
										inline-progression-dimension.maximum="100%" block-progression-dimension.maximum="100%"
										height="2.88cm" width="3.88cm"> <!--2.88, 2.88-->
										<xsl:attribute name="src"><xsl:value-of select="//reportContext/logoPath" /></xsl:attribute>
									</fo:external-graphic>
								</fo:block>
							</fo:block-container>
						</fo:block-container>
					</fo:table-cell>
					<fo:table-cell text-align="center">
						<fo:block-container font-family="Verdana" color="#94b54d" font-weight="bold" font-size="11pt" line-height="12.5pt">
							<fo:block>Student Writing Responses:</fo:block>
							<fo:block><xsl:value-of select="legalLastName"/>, <xsl:value-of select="legalFirstName"/></fo:block>
						</fo:block-container>
					</fo:table-cell>
					<fo:table-cell text-align="end">
						<fo:block/>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
		<fo:block-container font-family="Verdana" font-weight="bold" font-size="11pt" line-height="12.5pt">
			<fo:block>State Student ID: <xsl:value-of select="stateStudentIdentifier"/></fo:block>
			<fo:block>Grade: <xsl:value-of select="gradeName"/></fo:block>
			<fo:block>School: <xsl:value-of select="schoolName"/></fo:block>
			<fo:block>School ID: <xsl:value-of select="schoolIdentifier"/></fo:block>
		</fo:block-container>
	</xsl:template>
</xsl:stylesheet>