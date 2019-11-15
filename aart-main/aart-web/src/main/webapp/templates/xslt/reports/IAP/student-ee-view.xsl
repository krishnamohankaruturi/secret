<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="3.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:fox="http://xmlgraphics.apache.org/fop/extensions">
	
	<xsl:variable name="isScience">
		<xsl:choose>
			<xsl:when test="//reportDetails/contentArea/abbreviatedName = 'Sci'">true</xsl:when>
			<xsl:otherwise>false</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	
	<xsl:variable name="claimLabel">
		<xsl:choose>
			<xsl:when test="$isScience = 'true'">Core Idea</xsl:when>
			<xsl:otherwise>Claim</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	
	<xsl:variable name="conceptualAreaLabel">
		<xsl:choose>
			<xsl:when test="$isScience = 'true'">Topic</xsl:when>
			<xsl:otherwise>Conceptual Area</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	
	<xsl:variable name="allLinkageLevels" select="//reportDetails/allLinkageLevels" />
	
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" font-family="Verdana" font-size="10pt">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="page-master" page-height="11in" page-width="8.5in" margin-top="0.5in" margin-bottom="0.4in" margin-left="0.3in" margin-right="0.3in">
					<fo:region-body region-name="xsl-region-body"/>
					<fo:region-after region-name="xsl-region-page-footer" display-align="after" />
				</fo:simple-page-master>
				<fo:page-sequence-master master-name="allPages">
					<fo:repeatable-page-master-alternatives>
						<fo:conditional-page-master-reference master-reference="page-master" page-position="any" />
					</fo:repeatable-page-master-alternatives>
				</fo:page-sequence-master>
			</fo:layout-master-set>
			
			<fo:page-sequence id="pages" master-reference="allPages" initial-page-number="1" force-page-count="no-force">
				<fo:static-content flow-name="xsl-region-page-footer">
					<fo:table table-layout="fixed" margin="0" width="100%">
						<fo:table-column column-width="proportional-column-width(33)" />
						<fo:table-column column-width="proportional-column-width(33)" />
						<fo:table-column column-width="proportional-column-width(33)" />
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell>
									<fo:block>
										
									</fo:block>
								</fo:table-cell>
								<fo:table-cell display-align="after">
									<fo:block text-align="center" font-size="6pt">
										&#169; <xsl:value-of select="reportDetails/schoolYear"/> University of Kansas
									</fo:block>
								</fo:table-cell>
								<fo:table-cell display-align="after">
									<fo:block text-align="end">
										Page <fo:page-number /> of <fo:page-number-citation-last ref-id="pages" />
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-body>
					</fo:table>
				</fo:static-content>
				
				<fo:flow flow-name="xsl-region-body">
					<fo:block>
						<xsl:call-template name="header-printview" />
					</fo:block>
					<fo:block>
						<xsl:call-template name="legend" />
					</fo:block>
					<xsl:call-template name="line-break"/>
					<xsl:call-template name="hr" />
					<xsl:call-template name="line-break"/>
					<fo:block>
						<xsl:call-template name="ees">
							<xsl:with-param name="eeList" select="//reportDetails/criteria" />
						</xsl:call-template>
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
			
		</fo:root>
	</xsl:template>
	
	<xsl:attribute-set name="bgcolor-instruction-in-progress">
		<xsl:attribute name="background-color">#41A3D3</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="bgcolor-testlet-assigned">
		<xsl:attribute name="background-color">#24368B</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="bgcolor-testing-in-progress">
		<xsl:attribute name="background-color">#F4B12A</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="bgcolor-complete">
		<xsl:attribute name="background-color">#587D23</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="bgcolor-gray">
		<xsl:attribute name="background-color">#707070</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="legend-piece">
		<xsl:attribute name="display-align">center</xsl:attribute>
		<xsl:attribute name="text-align">center</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="legend-image">
		<xsl:attribute name="width">0.25in</xsl:attribute>
		<xsl:attribute name="height">0.25in</xsl:attribute>
		<xsl:attribute name="inline-progression-dimension.maximum">0.25in</xsl:attribute>
		<xsl:attribute name="block-progression-dimension.maximum">0.25in</xsl:attribute>
		<xsl:attribute name="content-width">scale-down-to-fit</xsl:attribute>
		<xsl:attribute name="content-height">scale-down-to-fit</xsl:attribute>
		<xsl:attribute name="display-align">center</xsl:attribute>
		<xsl:attribute name="text-align">center</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="legend-text">
		<xsl:attribute name="color">#666666</xsl:attribute>
		<xsl:attribute name="font-size">7pt</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>
		<xsl:attribute name="display-align">center</xsl:attribute>
		<xsl:attribute name="text-align">center</xsl:attribute>
		<xsl:attribute name="wrap-option">wrap</xsl:attribute>
		<xsl:attribute name="width">0.5in</xsl:attribute>
		<xsl:attribute name="height">0.5in</xsl:attribute>
		<xsl:attribute name="inline-progression-dimension.maximum">0.5in</xsl:attribute>
		<xsl:attribute name="block-progression-dimension.maximum">0.5in</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="dlm-logo">
		<xsl:attribute name="width">2.50in</xsl:attribute>
		<xsl:attribute name="height">1.10in</xsl:attribute>
		<xsl:attribute name="inline-progression-dimension.maximum">2.50in</xsl:attribute>
		<xsl:attribute name="block-progression-dimension.maximum">01.10in</xsl:attribute>
		<xsl:attribute name="content-width">scale-down-to-fit</xsl:attribute>
		<xsl:attribute name="content-height">scale-down-to-fit</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="header-celldata">
		<xsl:attribute name="margin-top">1.7mm</xsl:attribute>
		<xsl:attribute name="margin-left">1.7mm</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="header-school-districtmargin">
		<xsl:attribute name="margin-top">5mm</xsl:attribute>
		<xsl:attribute name="margin-left">12mm</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="headerStudentTable-outerborder">
  		<xsl:attribute name="border">solid 1pt black</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="headerStudentTable-font">
  		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="headerStudentTable-cellfontsize9">
  		<xsl:attribute name="font-size">9pt</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="cellLeftMargin">
  		<xsl:attribute name="margin-left">1.7mm</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="cellTopMargin">
  		<xsl:attribute name="margin-top">1.7mm</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="cellBottomMargin">
  		<xsl:attribute name="margin-bottom">1.7mm</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="headerStudentTable-headerbackground">
  		<xsl:attribute name="background-color">#5b5c5e</xsl:attribute>
	</xsl:attribute-set>
	<xsl:template name="header-printview">
    <fo:block break-before='page'>
    <fo:table >
        <fo:table-body>
          <fo:table-row >
            <fo:table-cell width="230px" number-rows-spanned="2" >
              <fo:block>
              	<fo:inline>
					<fo:external-graphic
						xsl:use-attribute-sets="dlm-logo"
						src="url('images/dlm_logo_final_registered_312x128.png')"/>
				</fo:inline>
				</fo:block>
            </fo:table-cell>
            <fo:table-cell>
              <fo:block xsl:use-attribute-sets="headerStudentTable-font" font-style = "italic" font-size="7pt">
				<xsl:text>*This report contains a student’s personally identifiable information (PII), and as such must be treated as a secure document. Protect and store securely. If not storing, securely destroy.</xsl:text>
			</fo:block>
            </fo:table-cell>
          </fo:table-row>
          <fo:table-row >
            <fo:table-cell >
              <fo:block xsl:use-attribute-sets="header-school-districtmargin headerStudentTable-font" font-size="12pt" ><xsl:value-of select="reportDetails/districtName"/> / <xsl:value-of select="reportDetails/schoolName"/> / <xsl:value-of select="reportDetails/contentArea/abbreviatedName"/></fo:block>
            </fo:table-cell>
          </fo:table-row>
        </fo:table-body>
    </fo:table>
    <fo:block xsl:use-attribute-sets="headerStudentTable-font" margin-top="1mm" margin-left="3.7mm" margin-bottom="1.5mm" font-weight="bold" font-size="14pt" display-align="center" color="#25408f">ESSENTIAL ELEMENT STATUS REPORT : <xsl:value-of select="reportDetails/windowName"/> WINDOW</fo:block>
    <fo:table table-layout="fixed" border="0" width="100%">
    		<fo:table-column column-width="proportional-column-width(55)" />
			<fo:table-column column-width="proportional-column-width(45)" />
        <fo:table-body>
          <fo:table-row >
            <fo:table-cell xsl:use-attribute-sets="headerStudentTable-outerborder" number-rows-spanned="2">
              <fo:block>
              <xsl:call-template name="headerStudentTable" />
              
			</fo:block>
            </fo:table-cell>
            <fo:table-cell height="20px">
              <fo:block xsl:use-attribute-sets="headerStudentTable-font" font-size="11pt" margin-top="1.7mm" margin-left="20mm">Report Date: <xsl:value-of select="reportDetails/reportDate"/> </fo:block>
            </fo:table-cell>
          </fo:table-row>
          <fo:table-row >
            <fo:table-cell>
              <fo:block xsl:use-attribute-sets="headerStudentTable-font" font-size="11pt" text-decoration="underline" margin-left="20mm">Credentials</fo:block>
              <fo:block xsl:use-attribute-sets="headerStudentTable-font headerStudentTable-cellfontsize9" margin-left="20mm">Username:  <xsl:value-of select="reportDetails/studentUserName"/> </fo:block>
              <fo:block xsl:use-attribute-sets="headerStudentTable-font headerStudentTable-cellfontsize9" margin-left="20mm">Password:  <xsl:value-of select="reportDetails/studentPassword"/> </fo:block>
            </fo:table-cell>
          </fo:table-row>
        </fo:table-body>
    </fo:table>
    <fo:block xsl:use-attribute-sets="cellTopMargin"></fo:block>
    <fo:block xsl:use-attribute-sets="cellTopMargin"></fo:block>
    </fo:block>
  </xsl:template>
	
	<xsl:template name="headerStudentTable">
    <fo:block>
    <fo:table table-layout="fixed" border="0" width="100%">
    		<fo:table-column column-width="proportional-column-width(70)" />
			<fo:table-column column-width="proportional-column-width(30)" />
        <fo:table-body border="inherit">
         <fo:table-row border="inherit">
            <fo:table-cell xsl:use-attribute-sets="headerStudentTable-headerbackground">
              <fo:block xsl:use-attribute-sets="header-celldata headerStudentTable-font" margin-bottom="1.7mm" color="#ffffff" font-size="11pt" ><xsl:value-of select="reportDetails/studentName"/> </fo:block>
            </fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="headerStudentTable-headerbackground">
              <fo:block>
			</fo:block>
            </fo:table-cell>
          </fo:table-row>
        <fo:table-row >
            <fo:table-cell  xsl:use-attribute-sets="headerStudentTable-headerbackground">
              <fo:block xsl:use-attribute-sets="headerStudentTable-font cellLeftMargin headerStudentTable-cellfontsize9" color="#ffffff" >State ID: <xsl:value-of select="reportDetails/studentStateIdentifier"/> </fo:block>
            </fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="headerStudentTable-headerbackground">
             <fo:block xsl:use-attribute-sets="headerStudentTable-font headerStudentTable-cellfontsize9" margin-bottom="1.7mm" color="#ffffff" text-align = "center"><xsl:value-of select="reportDetails/subjectAbbreviatedName"/> </fo:block>
            </fo:table-cell>
          </fo:table-row>
           <fo:table-row >
            <fo:table-cell background-color="#add8e6">
              <fo:block xsl:use-attribute-sets="headerStudentTable-font cellLeftMargin cellTopMargin cellBottomMargin headerStudentTable-cellfontsize9" >Essential Elements complete that count towards meeting blueprint requirements</fo:block>
            </fo:table-cell>
            <fo:table-cell background-color="#add8e6">
              <fo:block xsl:use-attribute-sets="cellTopMargin cellBottomMargin" text-align = "center"> <xsl:value-of select="reportDetails/essentialElementComplete"/>
			</fo:block>
            </fo:table-cell>
          </fo:table-row>
           <fo:table-row>
            <fo:table-cell >
              <fo:block xsl:use-attribute-sets="headerStudentTable-font cellLeftMargin cellTopMargin cellBottomMargin headerStudentTable-cellfontsize9" >Number of plans with instruction in progress</fo:block>
            </fo:table-cell>
            <fo:table-cell>
              <fo:block xsl:use-attribute-sets="cellTopMargin cellBottomMargin" text-align = "center"> <xsl:value-of select="reportDetails/planInProgress"/>
			</fo:block>
            </fo:table-cell>
          </fo:table-row>
           <fo:table-row>
            <fo:table-cell background-color="#d3d3d3">
              <fo:block xsl:use-attribute-sets="headerStudentTable-font cellLeftMargin cellTopMargin cellBottomMargin headerStudentTable-cellfontsize9">Testlets assigned and ready to test</fo:block>
            </fo:table-cell>
            <fo:table-cell background-color="#d3d3d3">
              <fo:block xsl:use-attribute-sets="cellTopMargin cellBottomMargin" text-align = "center"> <xsl:value-of select="reportDetails/assignedTeslets"/>
			</fo:block>
            </fo:table-cell>
          </fo:table-row>
           <fo:table-row >
            <fo:table-cell >
              <fo:block xsl:use-attribute-sets="headerStudentTable-font cellLeftMargin headerStudentTable-cellfontsize9 cellTopMargin cellBottomMargin" >Total number of testlets completed</fo:block>
            </fo:table-cell>
            <fo:table-cell>
              <fo:block xsl:use-attribute-sets="cellTopMargin cellBottomMargin" text-align = "center"> <xsl:value-of select="reportDetails/tesletCompleted"/>
			</fo:block>
            </fo:table-cell>
          </fo:table-row>
        </fo:table-body>
    </fo:table>
     </fo:block>
  </xsl:template>
	
	<xsl:template name="legend">
		<fo:table table-layout="fixed" border="0" width="100%">
			<fo:table-column column-width="proportional-column-width(12.5)" />
			<fo:table-column column-width="proportional-column-width(12.5)" />
			<fo:table-column column-width="proportional-column-width(12.5)" />
			<fo:table-column column-width="proportional-column-width(12.5)" />
			<fo:table-column column-width="proportional-column-width(12.5)" />
			<fo:table-column column-width="proportional-column-width(12.5)" />
			<fo:table-column column-width="proportional-column-width(12.5)" />
			<fo:table-column column-width="proportional-column-width(12.5)" />
			
			<fo:table-body>
				<fo:table-row display-align="before">
					<fo:table-cell>
						<fo:block xsl:use-attribute-sets="legend-piece">
							<fo:inline>
								<fo:external-graphic
									xsl:use-attribute-sets="legend-image bgcolor-instruction-in-progress"
									src="url('images/icons/instruction-and-assessment-planner/svg/transparentBlackArrowBlueBox.svg')"/>
							</fo:inline>
							<fo:block xsl:use-attribute-sets="legend-text">
								Instruction In Progress
							</fo:block>
						</fo:block>
					</fo:table-cell>
					
					<fo:table-cell>
						<fo:block xsl:use-attribute-sets="legend-piece">
							<fo:inline>
								<fo:external-graphic
									xsl:use-attribute-sets="legend-image bgcolor-testlet-assigned"
									src="url('images/icons/instruction-and-assessment-planner/svg/flagBox-transparent.svg')"/>
							</fo:inline>
							<fo:block xsl:use-attribute-sets="legend-text">
								Testlet Assigned
							</fo:block>
						</fo:block>
					</fo:table-cell>
					
					<fo:table-cell>
						<fo:block xsl:use-attribute-sets="legend-piece">
							<fo:inline>
								<fo:external-graphic
									xsl:use-attribute-sets="legend-image bgcolor-testing-in-progress"
									src="url('images/icons/instruction-and-assessment-planner/svg/timerBox-transparent.svg')"/>
							</fo:inline>
							<fo:block xsl:use-attribute-sets="legend-text">
								Testing In Progress
							</fo:block>
						</fo:block>
					</fo:table-cell>
					
					<fo:table-cell>
						<fo:block xsl:use-attribute-sets="legend-piece">
							<fo:inline>
								<fo:external-graphic
									xsl:use-attribute-sets="legend-image bgcolor-complete"
									src="url('images/icons/instruction-and-assessment-planner/svg/whiteCheckmark.svg')"/>
							</fo:inline>
							<fo:block xsl:use-attribute-sets="legend-text">
								Complete
							</fo:block>
						</fo:block>
					</fo:table-cell>
					
					<fo:table-cell>
						<fo:block xsl:use-attribute-sets="legend-piece">
							<fo:inline>
								<fo:external-graphic
									xsl:use-attribute-sets="legend-image bgcolor-gray"
									src="url('images/icons/instruction-and-assessment-planner/svg/WhiteRibbonTransparentBox.svg')"/>
							</fo:inline>
							<fo:block xsl:use-attribute-sets="legend-text">
								Recommended Linkage Level
							</fo:block>
						</fo:block>
					</fo:table-cell>
					
					<fo:table-cell>
						<fo:block xsl:use-attribute-sets="legend-piece">
							<fo:inline>
								<fo:external-graphic
									xsl:use-attribute-sets="legend-image bgcolor-complete"
									src="url('images/icons/instruction-and-assessment-planner/svg/WhiteStarTransparentBox.svg')"/>
							</fo:inline>
							<fo:block xsl:use-attribute-sets="legend-text">
								Mastery Demonstrated
							</fo:block>
						</fo:block>
					</fo:table-cell>
					
					<fo:table-cell>
						<fo:block xsl:use-attribute-sets="legend-piece">
							<fo:inline>
								<fo:external-graphic
									xsl:use-attribute-sets="legend-image bgcolor-complete"
									src="url('images/icons/instruction-and-assessment-planner/svg/WhiteXTransparentBox.svg')"/>
							</fo:inline>
							<fo:block xsl:use-attribute-sets="legend-text">
								Mastery Not Demonstrated
							</fo:block>
						</fo:block>
					</fo:table-cell>
					
					<fo:table-cell>
						<fo:block xsl:use-attribute-sets="legend-piece">
							<fo:inline>
								<fo:external-graphic
									xsl:use-attribute-sets="legend-image bgcolor-complete"
									src="url('images/icons/instruction-and-assessment-planner/svg/minusBoxTransparent.svg')"/>
							</fo:inline>
							<fo:block xsl:use-attribute-sets="legend-text">
								Results Not Available
							</fo:block>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>
	
	<xsl:attribute-set name="default-font">
		<xsl:attribute name="font-family">Verdana</xsl:attribute>
		<xsl:attribute name="font-size">6.5pt</xsl:attribute>
		<xsl:attribute name="color">#000000</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="criteria-box">
		<xsl:attribute name="border">solid 0.01in #000000</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="criteria-text">
		<xsl:attribute name="color">#0E76BC</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>
		<xsl:attribute name="margin">0.1in 0 0 0.1in</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="claim-text">
		<xsl:attribute name="font-weight">bold</xsl:attribute>
		<xsl:attribute name="start-indent">0.2in</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="conceptual-area-text">
		<xsl:attribute name="start-indent">0.2in</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="blueprint-completion-container">
		<xsl:attribute name="right">0</xsl:attribute>
		<xsl:attribute name="display-align">center</xsl:attribute>
		<xsl:attribute name="text-align">center</xsl:attribute>
		<xsl:attribute name="inline-progression-dimension.minimum">10%</xsl:attribute>
		<xsl:attribute name="block-progression-dimension.minimum">10%</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="blueprint-completion-image-container">
		<xsl:attribute name="fox:border-radius">50%</xsl:attribute>
		<xsl:attribute name="border">solid 0.01in #000000</xsl:attribute>
		<xsl:attribute name="width">1.6em</xsl:attribute>
		<xsl:attribute name="height">1.6em</xsl:attribute>
		<xsl:attribute name="inline-progression-dimension.minimum">1.6em</xsl:attribute>
		<xsl:attribute name="inline-progression-dimension.maximum">1.6em</xsl:attribute>
		<xsl:attribute name="block-progression-dimension.minimum">1.6em</xsl:attribute>
		<xsl:attribute name="block-progression-dimension.maximum">1.6em</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="blueprint-completion-image">
		<xsl:attribute name="width">1.6em</xsl:attribute>
		<xsl:attribute name="height">1.6em</xsl:attribute>
		<xsl:attribute name="inline-progression-dimension.maximum">1.6em</xsl:attribute>
		<xsl:attribute name="block-progression-dimension.maximum">1.6em</xsl:attribute>
		<xsl:attribute name="content-width">scale-down-to-fit</xsl:attribute>
		<xsl:attribute name="content-height">scale-down-to-fit</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="blueprint-completion-text-container">
		<xsl:attribute name="font-weight">bold</xsl:attribute>
		<xsl:attribute name="font-size">7pt</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="headerRow">
		<xsl:attribute name="border">solid 0.01in #FFFFFF</xsl:attribute>
		<xsl:attribute name="keep-together">always</xsl:attribute>
		<xsl:attribute name="page-break-after">avoid</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="headerRowCell" use-attribute-sets="bgcolor-gray">
		<xsl:attribute name="border">solid 0.001in #FFFFFF</xsl:attribute>
		<xsl:attribute name="font-family">Verdana</xsl:attribute>
		<xsl:attribute name="font-size">7.5pt</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>
		<xsl:attribute name="color">#FFFFFF</xsl:attribute>
		<xsl:attribute name="text-align">center</xsl:attribute>
		<xsl:attribute name="display-align">center</xsl:attribute>
		<xsl:attribute name="height">1.4em</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="recommendedLinkageLevelImage">
		<xsl:attribute name="display-align">center</xsl:attribute>
		<xsl:attribute name="content-width">scale-down-to-fit</xsl:attribute>
		<xsl:attribute name="content-height">scale-down-to-fit</xsl:attribute>
		<xsl:attribute name="inline-progression-dimension.maximum">0.9em</xsl:attribute>
		<xsl:attribute name="block-progression-dimension.maximum">0.9em</xsl:attribute>
		<xsl:attribute name="width">0.9em</xsl:attribute>
		<xsl:attribute name="height">0.9em</xsl:attribute>
		<xsl:attribute name="margin">0</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="row">
		<xsl:attribute name="block-progression-dimension.minimum">0.8in</xsl:attribute>
	</xsl:attribute-set>
	
	
	<xsl:attribute-set name="cell">
		<xsl:attribute name="font-family">Verdana</xsl:attribute>
		<xsl:attribute name="font-size">6pt</xsl:attribute>
		<xsl:attribute name="color">#000000</xsl:attribute>
		<xsl:attribute name="wrap-option">wrap</xsl:attribute>
		<xsl:attribute name="margin">0.02in</xsl:attribute>
		<xsl:attribute name="margin-top">0.04in</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="emptyCell" use-attribute-sets="cell">
		<xsl:attribute name="background-color">#EAEAEA</xsl:attribute>
		<xsl:attribute name="border">solid 0.01in #FFFFFF</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="eeCode">
		<xsl:attribute name="font-weight">bold</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="card-container">
		<xsl:attribute name="border">solid 0.005in #000000</xsl:attribute>
		<xsl:attribute name="height">0.75in</xsl:attribute>
		<xsl:attribute name="fox:border-radius">1.25pt</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="card-main-content-container">
		<xsl:attribute name="margin">0</xsl:attribute>
		<xsl:attribute name="height">66%</xsl:attribute>
		<xsl:attribute name="width">100%</xsl:attribute>
		<xsl:attribute name="border-bottom">solid 0.005in #000000</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="card-main-content-text">
		<xsl:attribute name="margin">0.02in</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="card-footer-content-container">
		<xsl:attribute name="margin">0</xsl:attribute>
		<xsl:attribute name="height">33%</xsl:attribute>
		<xsl:attribute name="width">100%</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="card-footer-content-container-blank" use-attribute-sets="card-footer-content-container">
		<xsl:attribute name="background-color">#EAEAFF</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="card-footer-content-container-instruction-in-progress" use-attribute-sets="card-footer-content-container bgcolor-instruction-in-progress">
	</xsl:attribute-set>
	<xsl:attribute-set name="card-footer-content-container-testlet-assigned" use-attribute-sets="card-footer-content-container bgcolor-testlet-assigned">
		<xsl:attribute name="color">#FFFFFF</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="card-footer-content-container-testing-in-progress" use-attribute-sets="card-footer-content-container bgcolor-testing-in-progress">
	</xsl:attribute-set>
	<xsl:attribute-set name="card-footer-content-container-complete" use-attribute-sets="card-footer-content-container bgcolor-complete">
		<xsl:attribute name="color">#FFFFFF</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="card-footer-image-container">
		<xsl:attribute name="display-align">center</xsl:attribute>
		<xsl:attribute name="height">0.2in</xsl:attribute>
		<xsl:attribute name="width">0.2in</xsl:attribute>
		<xsl:attribute name="inline-progression-dimension.maximum">0.2in</xsl:attribute>
		<xsl:attribute name="block-progression-dimension.maximum">0.2in</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="card-footer-text-container">
		<xsl:attribute name="height">100%</xsl:attribute>
		<xsl:attribute name="width">60%</xsl:attribute>
		<xsl:attribute name="display-align">center</xsl:attribute>
		<xsl:attribute name="inline-progression-dimension.maximum">60%</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="card-footer-date-container">
		<xsl:attribute name="height">100%</xsl:attribute>
		<xsl:attribute name="width">20%</xsl:attribute>
		<xsl:attribute name="display-align">center</xsl:attribute>
		<xsl:attribute name="inline-progression-dimension.maximum">20%</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="card-footer-image">
		<xsl:attribute name="margin">0</xsl:attribute>
		<xsl:attribute name="display-align">center</xsl:attribute>
		<xsl:attribute name="content-width">scale-down-to-fit</xsl:attribute>
		<xsl:attribute name="content-height">scale-down-to-fit</xsl:attribute>
		<xsl:attribute name="inline-progression-dimension.maximum">0.2in</xsl:attribute>
		<xsl:attribute name="block-progression-dimension.maximum">0.2in</xsl:attribute>
		<xsl:attribute name="width">0.2in</xsl:attribute>
		<xsl:attribute name="height">0.2in</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="card-footer-mastery-image" use-attribute-sets="card-footer-image">
		<xsl:attribute name="width">0.15in</xsl:attribute>
		<xsl:attribute name="height">0.15in</xsl:attribute>
		<xsl:attribute name="inline-progression-dimension.maximum">0.15in</xsl:attribute>
		<xsl:attribute name="block-progression-dimension.maximum">0.15in</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="card-footer-table">
		<xsl:attribute name="block-progression-dimension.maximum">100%</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="card-footer-table-cell">
		<xsl:attribute name="text-align">center</xsl:attribute>
		<xsl:attribute name="display-align">center</xsl:attribute>
		<xsl:attribute name="wrap-option">wrap</xsl:attribute>
		<xsl:attribute name="margin">0</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="card-footer-table-cell-date">
		<xsl:attribute name="text-align">center</xsl:attribute>
		<xsl:attribute name="display-align">before</xsl:attribute>
		<xsl:attribute name="wrap-option">no-wrap</xsl:attribute>
		<xsl:attribute name="margin">0</xsl:attribute>
		<xsl:attribute name="font-size">6pt</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="card-footer-table-cell-date-child">
		<xsl:attribute name="margin">0</xsl:attribute>
		<xsl:attribute name="block-progression-dimension.minimum">50%</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:template name="ees">
		<xsl:param name="eeList" />
		<xsl:for-each select="$eeList/edu.ku.cete.report.iap.CriteriaContextData">
			<xsl:variable name="criteria" select="."/>
			<xsl:choose>
				<xsl:when test="//reportDetails/isInstructionallyEmbeddedModel = 'true'">
					<fo:block xsl:use-attribute-sets="criteria-box">
						<fo:table table-layout="fixed" border="0">
							<fo:table-column column-width="proportional-column-width(88)" />
							<fo:table-column column-width="proportional-column-width(12)" />
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell>
										<fo:block xsl:use-attribute-sets="criteria-text">
											<xsl:value-of select="$criteria/criteriaText" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block xsl:use-attribute-sets="blueprint-completion-container">
											<!--
												Summer 2019 (initial implementation):
													We only want to show the blueprint criteria completion if there is actually a criteria.
													In cases like Science, there is no blueprint at all, so there would be no completion.
													For ELA/M, there are some EEs that are just not in the blueprint to be displayed at the end
													of the rest of the content, and these would not need the completion icon either.
											-->
											<xsl:if test="$criteria/criteria != ''">
												<xsl:choose>
													<xsl:when test="$criteria/metCriteria = 'true'">
														<fo:inline>
															<fo:external-graphic
																xsl:use-attribute-sets="blueprint-completion-image-container blueprint-completion-image bgcolor-complete"
																src="url('images/icons/instruction-and-assessment-planner/svg/whiteCheckmark.svg')"/>
														</fo:inline>
														<fo:block xsl:use-attribute-sets="blueprint-completion-text-container">
															Complete
														</fo:block>
													</xsl:when>
													<xsl:otherwise>
														<fo:inline>
															<!--
															Only difference between this one and the one above is the background color...
															This works because the image is white so it will just blend in, maybe inflating the PDF file size just a touch.
															But since the most criteria is 4 or 5, it's not going to be by a lot, considering the amount of other stuff in the PDF. 
															Hacky, but works to keep the same dimensions.
															-->
															<fo:external-graphic
																xsl:use-attribute-sets="blueprint-completion-image-container blueprint-completion-image"
																src="url('images/icons/instruction-and-assessment-planner/svg/whiteCheckmark.svg')"/>
														</fo:inline>
														<fo:block xsl:use-attribute-sets="blueprint-completion-text-container">
															Incomplete
														</fo:block>
													</xsl:otherwise>
												</xsl:choose>
											</xsl:if>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
						
						<xsl:call-template name="line-break"/>
						
						<xsl:call-template name="claimsForCriteria">
							<xsl:with-param name="criteria" select="$criteria"/>
						</xsl:call-template>
						
					</fo:block>
				</xsl:when> <!-- isInstructionallyEmbeddedModel -->
				<xsl:otherwise>
					<xsl:call-template name="claimsForCriteria">
						<xsl:with-param name="criteria" select="$criteria"/>
					</xsl:call-template>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:call-template name="line-break"/>
		</xsl:for-each>
	</xsl:template>
	
	<xsl:template name="claimsForCriteria">
		<xsl:param name="criteria"/>
		<xsl:for-each select="$criteria/claims/edu.ku.cete.report.iap.ClaimContextData">
			<xsl:variable name="claim" select="."/>
			<fo:block xsl:use-attribute-sets="claim-text">
				<xsl:value-of select="$claimLabel" />:&#160;<xsl:value-of select="$claim/claimContentCode" />&#160;<xsl:value-of select="$claim/claimDescription" />
			</fo:block>
			
			<xsl:for-each select="$claim/conceptualAreas/edu.ku.cete.report.iap.ConceptualAreaContextData">
				<xsl:variable name="conceptualArea" select="."/>
				<fo:block xsl:use-attribute-sets="conceptual-area-text">
					<xsl:value-of select="$conceptualAreaLabel" />:&#160;<xsl:value-of select="$conceptualArea/conceptualAreaContentCode" />&#160;<xsl:value-of select="$conceptualArea/conceptualAreaDescription" />
				</fo:block>
				<xsl:call-template name="line-break"/>
				<xsl:for-each select="$conceptualArea/ees/edu.ku.cete.web.IAPContentFramework">
					<xsl:variable name="currentEE" select="."/>
					<fo:block-container margin="0.02in">
						<fo:block-container margin="0">
							<fo:table table-layout="fixed" border="0">
								<xsl:choose>
									<xsl:when test="$isScience = 'true'">
										<fo:table-column column-width="proportional-column-width(25)" />
										<fo:table-column column-width="proportional-column-width(25)" />
										<fo:table-column column-width="proportional-column-width(25)" />
										<fo:table-column column-width="proportional-column-width(25)" />
									</xsl:when>
									<xsl:otherwise>
										<fo:table-column column-width="proportional-column-width(16)" />
										<fo:table-column column-width="proportional-column-width(16)" />
										<fo:table-column column-width="proportional-column-width(16)" />
										<fo:table-column column-width="proportional-column-width(16)" />
										<fo:table-column column-width="proportional-column-width(16)" />
										<fo:table-column column-width="proportional-column-width(16)" />
									</xsl:otherwise>
								</xsl:choose>
								<fo:table-body>
									<xsl:call-template name="eeHeaderAndRow">
										<xsl:with-param name="ee" select="$currentEE"/>
									</xsl:call-template>
								</fo:table-body>
							</fo:table>
						</fo:block-container>
					</fo:block-container>
				</xsl:for-each>
				<xsl:call-template name="line-break"/>	
			</xsl:for-each>
		</xsl:for-each>
	</xsl:template>
	
	<xsl:template name="eeHeaderAndRow">
		<xsl:param name="ee" />
		<fo:table-row xsl:use-attribute-sets="headerRow">
			
			<fo:table-cell xsl:use-attribute-sets="headerRow">
				<fo:block-container xsl:use-attribute-sets="headerRowCell">
					<fo:block>
						Essential Element
					</fo:block>
				</fo:block-container>
			</fo:table-cell>
			
			<xsl:for-each select="$allLinkageLevels/edu.ku.cete.domain.LinkageLevelSortOrder">
				<xsl:variable name="ll" select="."/>
				<fo:table-cell xsl:use-attribute-sets="headerRow">
					<fo:block-container xsl:use-attribute-sets="headerRowCell">
						<fo:block>
							<xsl:value-of select="$ll/displayName"/>
							<xsl:if test="$ee/ee/recommendedLinkageLevelName = $ll/levelName">
								<fo:external-graphic xsl:use-attribute-sets="recommendedLinkageLevelImage" src="url('images/icons/instruction-and-assessment-planner/svg/WhiteRibbonTransparentBox.svg')"/>
							</xsl:if>
						</fo:block>
					</fo:block-container>
				</fo:table-cell>
			</xsl:for-each>
			
		</fo:table-row>
				
		<fo:table-row xsl:use-attribute-sets="row">
			<fo:table-cell xsl:use-attribute-sets="cell">
				<fo:block-container xsl:use-attribute-sets="default-font">
					<fo:block xsl:use-attribute-sets="eeCode">
						<xsl:value-of select="$ee/ee/contentCode"/>
					</fo:block>
					<fo:block>
						<xsl:value-of select="$ee/ee/description"/>
					</fo:block>
				</fo:block-container>
			</fo:table-cell>
			
			<xsl:for-each select="$allLinkageLevels/edu.ku.cete.domain.LinkageLevelSortOrder">
				<xsl:variable name="ll" select="."/>
				<xsl:call-template name="eeCardForLL">
					<xsl:with-param name="ee" select="$ee"/>
					<xsl:with-param name="ll" select="$ll"/>
				</xsl:call-template>
			</xsl:for-each>
		</fo:table-row>
	</xsl:template>
	
	<xsl:template name="eeCardForLL">
		<xsl:param name="ee" />
		<xsl:param name="ll" />
		
		<!-- this variable will basically serve to tell us if there is any content -->
		<xsl:variable name="cellResult">
			<xsl:for-each select="$ee/linkageLevels/edu.ku.cete.tde.webservice.domain.MicroMapResponseDTO">
				<xsl:variable name="eeLL" select="." />
				
				<xsl:if test="$eeLL/linkagelabel = $ll/levelName">
					<fo:table-cell xsl:use-attribute-sets="cell">
						<fo:block-container xsl:use-attribute-sets="card-container default-font">
							<fo:block-container xsl:use-attribute-sets="card-main-content-container">
								<fo:block xsl:use-attribute-sets="card-main-content-text">
									<xsl:value-of select="$eeLL/linkagelevelshortdesc"/>
								</fo:block>
							</fo:block-container>
							
							<xsl:variable name="latestItiEntry" select="$ee/itiEntries/edu.ku.cete.domain.ItiTestSessionHistory[linkageLevel = $ll/levelName][last()]"/>
							
							<xsl:choose>
								<xsl:when test="$latestItiEntry/statusCode = 'STARTED'">
									<fo:block-container xsl:use-attribute-sets="card-footer-content-container-instruction-in-progress">
										<fo:block>
											<fo:table table-layout="fixed" xsl:use-attribute-sets="card-footer-table">
												<fo:table-column column-width="proportional-column-width(18)" />
												<fo:table-column column-width="proportional-column-width(58)" />
												<fo:table-column column-width="proportional-column-width(18)" />
												<fo:table-body xsl:use-attribute-sets="card-footer-table">
													<fo:table-row>
														<fo:table-cell xsl:use-attribute-sets="card-footer-table-cell">
															<fo:block xsl:use-attribute-sets="card-footer-image-container">
																<fo:external-graphic xsl:use-attribute-sets="card-footer-image" src="url('images/icons/instruction-and-assessment-planner/svg/transparentBlackArrowBlueBox.svg')"/>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="card-footer-table-cell">
															<fo:block xsl:use-attribute-sets="card-footer-table-cell">
																<xsl:text>Instruction In Progress</xsl:text>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="card-footer-table-cell-date">
															<fo:block xsl:use-attribute-sets="card-footer-table-cell-date-child">
																<xsl:value-of select="$latestItiEntry/createdDateStr"/>
															</fo:block>
															<fo:block xsl:use-attribute-sets="card-footer-table-cell-date-child">
																
															</fo:block>
														</fo:table-cell>
													</fo:table-row>
												</fo:table-body>
											</fo:table>
										</fo:block>
									</fo:block-container>
								</xsl:when>
								<xsl:when test="$latestItiEntry/statusCode = 'COMPLETED_WITH_TESTLET'">
									<xsl:choose>
										<xsl:when test="$latestItiEntry/studentsTestsStatus = 'unused'">
											<fo:block-container xsl:use-attribute-sets="card-footer-content-container-testlet-assigned">
												<fo:block>
													<fo:table table-layout="fixed" xsl:use-attribute-sets="card-footer-table">
														<fo:table-column column-width="proportional-column-width(18)" />
														<fo:table-column column-width="proportional-column-width(58)" />
														<fo:table-column column-width="proportional-column-width(18)" />
														<fo:table-body xsl:use-attribute-sets="card-footer-table">
															<fo:table-row>
																<fo:table-cell xsl:use-attribute-sets="card-footer-table-cell">
																	<fo:block xsl:use-attribute-sets="card-footer-image">
																		<fo:external-graphic xsl:use-attribute-sets="card-footer-image" src="url('images/icons/instruction-and-assessment-planner/svg/flagBox-transparent.svg')"/>
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell xsl:use-attribute-sets="card-footer-table-cell">
																	<fo:block xsl:use-attribute-sets="card-footer-table-cell">
																		<xsl:text>Testlet Assigned</xsl:text>
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell xsl:use-attribute-sets="card-footer-table-cell-date">
																	<fo:block xsl:use-attribute-sets="card-footer-table-cell-date-child">
																		<xsl:value-of select="$latestItiEntry/confirmDateStr"/>
																	</fo:block>
																	<fo:block xsl:use-attribute-sets="card-footer-table-cell-date-child"/>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:block>
											</fo:block-container>
										</xsl:when>
										<xsl:when test="$latestItiEntry/studentsTestsStatus = 'inprogress'">
											<fo:block-container xsl:use-attribute-sets="card-footer-content-container-testing-in-progress">
												<fo:block>
													<fo:table table-layout="fixed" xsl:use-attribute-sets="card-footer-table">
														<fo:table-column column-width="proportional-column-width(18)" />
														<fo:table-column column-width="proportional-column-width(58)" />
														<fo:table-column column-width="proportional-column-width(18)" />
														<fo:table-body xsl:use-attribute-sets="card-footer-table">
															<fo:table-row>
																<fo:table-cell xsl:use-attribute-sets="card-footer-table-cell">
																	<fo:block xsl:use-attribute-sets="card-footer-image">
																		<fo:external-graphic xsl:use-attribute-sets="card-footer-image" src="url('images/icons/instruction-and-assessment-planner/svg/timerBox-transparent.svg')"/>
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell xsl:use-attribute-sets="card-footer-table-cell">
																	<fo:block xsl:use-attribute-sets="card-footer-table-cell">
																		<xsl:text>Testing In Progress</xsl:text>
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell xsl:use-attribute-sets="card-footer-table-cell-date">
																	<fo:block xsl:use-attribute-sets="card-footer-table-cell-date-child">
																		<xsl:value-of select="$latestItiEntry/studentsTestsStartTimeStr"/>
																	</fo:block>
																	<fo:block xsl:use-attribute-sets="card-footer-table-cell-date-child"/>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:block>
											</fo:block-container>
										</xsl:when>
										<xsl:when test="$latestItiEntry/studentsTestsStatus = 'complete'">
											<fo:block-container xsl:use-attribute-sets="card-footer-content-container-complete">
												<fo:block>
													<fo:table table-layout="fixed" xsl:use-attribute-sets="card-footer-table">
														<fo:table-column column-width="proportional-column-width(18)" />
														<fo:table-column column-width="proportional-column-width(58)" />
														<fo:table-column column-width="proportional-column-width(18)" />
														<fo:table-body xsl:use-attribute-sets="card-footer-table">
															<fo:table-row>
																<fo:table-cell xsl:use-attribute-sets="card-footer-table-cell">
																	<fo:block xsl:use-attribute-sets="card-footer-image">
																		<fo:external-graphic xsl:use-attribute-sets="card-footer-image" src="url('images/icons/instruction-and-assessment-planner/svg/whiteCheckmark.svg')"/>
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell xsl:use-attribute-sets="card-footer-table-cell">
																	<fo:block xsl:use-attribute-sets="card-footer-table-cell">
																		<xsl:text>Testlet Complete</xsl:text>
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell xsl:use-attribute-sets="card-footer-table-cell-date">
																	<fo:block xsl:use-attribute-sets="card-footer-table-cell-date-child">
																		<xsl:value-of select="$latestItiEntry/studentsTestsEndTimeStr"/>
																	</fo:block>
																	<fo:block xsl:use-attribute-sets="card-footer-table-cell-date-child">
																		<xsl:choose>
																			<xsl:when test="$ee/isWritingTestlet = 'true'">
																				<fo:external-graphic xsl:use-attribute-sets="card-footer-mastery-image" src="url('images/icons/instruction-and-assessment-planner/svg/minusBoxTransparent.svg')"/>
																			</xsl:when>
																			<xsl:otherwise>
																				<xsl:choose>
																					<xsl:when test="$latestItiEntry/mastered = 'true'">
																						<fo:external-graphic xsl:use-attribute-sets="card-footer-mastery-image" src="url('images/icons/instruction-and-assessment-planner/svg/WhiteStarTransparentBox.svg')"/>
																					</xsl:when>
																					<xsl:when test="$latestItiEntry/mastered = 'false'">
																						<fo:external-graphic xsl:use-attribute-sets="card-footer-mastery-image" src="url('images/icons/instruction-and-assessment-planner/svg/WhiteXTransparentBox.svg')"/>
																					</xsl:when>
																					<xsl:otherwise>
																						<fo:external-graphic xsl:use-attribute-sets="card-footer-mastery-image" src="url('images/icons/instruction-and-assessment-planner/svg/minusBoxTransparent.svg')"/>
																					</xsl:otherwise>
																				</xsl:choose>
																			</xsl:otherwise>
																		</xsl:choose>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:block>
											</fo:block-container>
										</xsl:when>
									</xsl:choose>
								</xsl:when>
								<xsl:otherwise>
									<fo:block-container xsl:use-attribute-sets="card-footer-content-container-blank">
										<fo:block></fo:block>
									</fo:block-container>
								</xsl:otherwise>
							</xsl:choose>
						</fo:block-container>
					</fo:table-cell>
				</xsl:if>
			</xsl:for-each>
		</xsl:variable>
		
		<xsl:choose>
			<xsl:when test="normalize-space($cellResult) != ''">
				<xsl:copy-of select="$cellResult"/>
			</xsl:when>
			<xsl:otherwise>
				<fo:table-cell xsl:use-attribute-sets="emptyCell">
					<fo:block/>
				</fo:table-cell>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="line-break">
		<fo:block linefeed-treatment="preserve">&#x2028;</fo:block>
	</xsl:template>
	
	<xsl:template name="page-break">
		<fo:block page-break-after="always" />
	</xsl:template>
	
	<xsl:template name="hr">
		<fo:block border-bottom="solid 0.01in #999999"/>
	</xsl:template>
</xsl:stylesheet>