<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java fo" extension-element-prefixes="saxon">
  	<xsl:template name="lookup-median-se-sc">
    	<xsl:param name="paramOrgId"/>
    	<xsl:param name="paramType"/>
    	<xsl:for-each select="reportDetails/data/medianScores/edu.ku.cete.domain.report.ReportsMedianScore">
    		<xsl:if test="$paramType = 'SE'">		
    			<xsl:if test="organizationId = $paramOrgId">
    				<xsl:value-of select="format-number(standardError, '0.0')"/>
    			</xsl:if>
    		</xsl:if>
    		<xsl:if test="$paramType = 'SC'">		
    			<xsl:if test="organizationId = $paramOrgId">
    				<xsl:value-of select="format-number(studentCount, '###,###')" />
    			</xsl:if>
    		</xsl:if>
    		<xsl:if test="$paramType = 'SCORE'">		
    			<xsl:if test="organizationId = $paramOrgId">
    				<xsl:value-of select="score"/>
    			</xsl:if>
    		</xsl:if>
    	</xsl:for-each>
    </xsl:template>
    <xsl:template name="lookup-level-number-byscore">
    	<xsl:param name="paramScore"/>
    	<xsl:variable name="levelValue">
	    	<xsl:for-each select="reportDetails/data/levels/edu.ku.cete.domain.report.LevelDescription">
	    		<xsl:if test="(($paramScore &gt;= levelLowCutScore) and ($paramScore &lt; levelHighCutScore))">
	   				<xsl:value-of select="level"/>
	   			</xsl:if>
	    	</xsl:for-each>
    	</xsl:variable>
    	<xsl:choose>
	    	<xsl:when test="$levelValue = ''">
	    		<xsl:for-each select="reportDetails/data/levels/edu.ku.cete.domain.report.LevelDescription">
		    		<xsl:if test="($paramScore = levelHighCutScore)">
		   				<xsl:value-of select="level"/>
		   			</xsl:if>
		    	</xsl:for-each>
	    	</xsl:when>
	    	<xsl:otherwise>
	    		<xsl:value-of select="$levelValue"/>
	    	</xsl:otherwise>
    	</xsl:choose>
    </xsl:template>
    <xsl:template name="lookup-level-name-byscore">
    	<xsl:param name="paramScore"/>
    	<xsl:variable name="levelValue">
	    	<xsl:for-each select="reportDetails/data/levels/edu.ku.cete.domain.report.LevelDescription">
	    		<xsl:if test="(($paramScore &gt;= levelLowCutScore) and ($paramScore &lt; levelHighCutScore))">
	   				<xsl:value-of select="levelName"/>
	   			</xsl:if>
	    	</xsl:for-each>
    	</xsl:variable>
    	<xsl:choose>
	    	<xsl:when test="$levelValue = ''">
	    		<xsl:for-each select="reportDetails/data/levels/edu.ku.cete.domain.report.LevelDescription">
		    		<xsl:if test="($paramScore = levelHighCutScore)">
		   				<xsl:value-of select="levelName"/>
		   			</xsl:if>
		    	</xsl:for-each>
	    	</xsl:when>
	    	<xsl:otherwise>
	    		<xsl:value-of select="$levelValue"/>
	    	</xsl:otherwise>
    	</xsl:choose>
    </xsl:template>
    <xsl:template name="lookup-level-description-byscore">
    	<xsl:param name="paramScore"/>
    	<xsl:variable name="levelValue">
	    	<xsl:for-each select="reportDetails/data/levels/edu.ku.cete.domain.report.LevelDescription">
	    		<xsl:if test="(($paramScore &gt;= levelLowCutScore) and ($paramScore &lt; levelHighCutScore))">
	   				<xsl:value-of select="levelDescription"/>
	   			</xsl:if>
	    	</xsl:for-each>
    	</xsl:variable>
    	<xsl:choose>
	    	<xsl:when test="$levelValue = ''">
	    		<xsl:for-each select="reportDetails/data/levels/edu.ku.cete.domain.report.LevelDescription">
		    		<xsl:if test="($paramScore = levelHighCutScore)">
		   				<xsl:value-of select="levelDescription"/>
		   			</xsl:if>
		    	</xsl:for-each>
	    	</xsl:when>
	    	<xsl:otherwise>
	    		<xsl:value-of select="$levelValue"/>
	    	</xsl:otherwise>
    	</xsl:choose>
    </xsl:template>
    <xsl:template name="lookup-level-number">
    	<xsl:param name="paramLevelId"/>
    	<xsl:for-each select="reportDetails/data/levels/edu.ku.cete.domain.report.LevelDescription">
    		<xsl:if test="id = $paramLevelId">
   				<xsl:value-of select="level"/>
   			</xsl:if>
    	</xsl:for-each>
    </xsl:template>
    <xsl:template name="lookup-level-name">
    	<xsl:param name="paramLevelId"/>
    	<xsl:for-each select="reportDetails/data/levels/edu.ku.cete.domain.report.LevelDescription">
    		<xsl:if test="id = $paramLevelId">
   				<xsl:value-of select="levelName"/>
   			</xsl:if>
    	</xsl:for-each>
    </xsl:template>
    <xsl:template name="max-level-number">
    	<xsl:for-each select="reportDetails/data/levels/edu.ku.cete.domain.report.LevelDescription">
		  <xsl:sort select="level" order="descending"/>
		  <xsl:if test="position()=1">
		    <xsl:value-of select="level"/>
		  </xsl:if>
		</xsl:for-each>
    </xsl:template>
    
    <xsl:template name="min-level-number">
    	<xsl:for-each select="reportDetails/data/levels/edu.ku.cete.domain.report.LevelDescription">
		  <xsl:sort select="level"/>
		  <xsl:if test="position()=1">
		    <xsl:value-of select="level"/>
		  </xsl:if>
		</xsl:for-each>
    </xsl:template>
			
	<xsl:template name="lastName">
    	<xsl:call-template name="initCap">
    			<xsl:with-param name="str" select="reportDetails/data/studentLastName"/>
    		</xsl:call-template>
    </xsl:template>
	<xsl:template name="firstName">
    	<xsl:call-template name="initCap">
    			<xsl:with-param name="str" select="reportDetails/data/studentFirstName"/>
    		</xsl:call-template>
	</xsl:template>
	<xsl:template name="firstNameMeterSection">
		<xsl:variable name="nameText">
    		<xsl:call-template name="firstName"/>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="not(contains($nameText, ' ')) and string-length($nameText) &gt; 23">
				<xsl:text>your student</xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$nameText"/>
    		</xsl:otherwise>
    	</xsl:choose>
	</xsl:template>
	<xsl:template name="firstNameLevelsSection">
		<xsl:variable name="nameText">
    		<xsl:call-template name="firstName"/>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="not(contains($nameText, ' ')) and string-length($nameText) &gt; 23">
				<xsl:text>Your student</xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$nameText"/>
    		</xsl:otherwise>
    	</xsl:choose>
	</xsl:template>
	<xsl:template name="headerLogo">
		<fo:block-container height="1.69cm" width="5.26cm">
			<fo:block>
				<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
					inline-progression-dimension.maximum="100%" block-progression-dimension.maximum="100%"
					 height="1.69cm" width="5.26cm">
					 <xsl:attribute name="src"><xsl:value-of select="reportDetails/logoPath"/></xsl:attribute>
				</fo:external-graphic>
			</fo:block>
		</fo:block-container>
	</xsl:template>
	<xsl:template name="reportFooter">
		<fo:block>
	     	<fo:table table-layout="fixed">
				<fo:table-column column-width="proportional-column-width(8)" />
				<fo:table-column column-width="proportional-column-width(92)" />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell display-align="center" >
							<fo:block-container height="52px" width="52px" position="absolute" left="35">
								<fo:block>
									<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
										inline-progression-dimension.maximum="100%" block-progression-dimension.maximum="100%"
										 height="52px" width="52px">
										 <xsl:attribute name="src"><xsl:value-of select="reportDetails/footerLogoPath"/></xsl:attribute>
									</fo:external-graphic>
								</fo:block>
							</fo:block-container>
						</fo:table-cell>
						<fo:table-cell text-align="start" display-align="center" color="#939597" font-size="8pt" padding="3px">
							<fo:block-container text-align="center"  height="52px">
							<fo:block>Copyright &#xA9; <xsl:value-of select="java:format(java:java.text.SimpleDateFormat.new('yyyy'), java:java.util.Date.new())"/> by The Achievement and Assessment Institute, The University of Kansas.</fo:block>
							<fo:block>Report generated: <xsl:value-of select="java:format(java:java.text.SimpleDateFormat.new('MM-dd-yyyy'), java:java.util.Date.new())"/></fo:block>
							</fo:block-container>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
	     </fo:block>
	</xsl:template>
	<xsl:template name="headerSchoolYear">
		<fo:block>School Year: <xsl:value-of select="reportDetails/data/currentSchoolYear - 1"/>&#8211;<xsl:value-of select="reportDetails/data/currentSchoolYear"/></fo:block>
	</xsl:template>
	<xsl:template name="headerGradeSubjectName"> 
    	<xsl:variable name="gradeSubject"><xsl:value-of select="reportDetails/data/gradeName"/><xsl:text>&#160;</xsl:text><xsl:value-of select="reportDetails/data/contentAreaName"/></xsl:variable>
    	<fo:block color="#3784C6" font-weight="bold" font-size="11pt" wrap-option="no-wrap">
	    	<xsl:value-of select="$gradeSubject"/>
		</fo:block>
	</xsl:template>								
	<xsl:template name="headerSchoolName"> 
		<xsl:variable name="schName">School: <xsl:value-of select="reportDetails/data/attendanceSchoolName"/></xsl:variable>
    	<fo:block>
			<xsl:value-of select="$schName"/>
    	</fo:block>
	</xsl:template>
	<xsl:template name="headerDistrictName"> 
		<xsl:variable name="districtName">District: <xsl:value-of select="reportDetails/data/districtName"/> / #<xsl:value-of select="reportDetails/data/districtDisplayIdentifier"/></xsl:variable>
    	<fo:block>
			<xsl:value-of select="$districtName"/>
    	</fo:block>
	</xsl:template>
	<xsl:template name="districtName"> 
    	<xsl:call-template name="intersperse-with-zero-spaces">
    	<xsl:with-param name="data" select="reportDetails/data/districtName"/></xsl:call-template>
	</xsl:template> 
	<xsl:template name="gradeName"> 
    	<xsl:call-template name="intersperse-with-zero-spaces">
    	<xsl:with-param name="data" select="reportDetails/data/gradeName"/></xsl:call-template>
	</xsl:template>
	<xsl:template name="contentAreaName"> 
    	<xsl:call-template name="intersperse-with-zero-spaces">
    	<xsl:with-param name="data" select="reportDetails/data/contentAreaName"/></xsl:call-template>
	</xsl:template>
	<xsl:template name="districtDisplayIdentifier"> 
    	<xsl:call-template name="intersperse-with-zero-spaces">
    	<xsl:with-param name="data" select="reportDetails/data/districtDisplayIdentifier"/></xsl:call-template>
	</xsl:template>
	<xsl:template name="stateStudentIdentifier"> 
    	<xsl:call-template name="intersperse-with-zero-spaces">
    	<xsl:with-param name="data" select="reportDetails/data/stateStudentIdentifier"/></xsl:call-template>
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
	<xsl:template name="prepend-pad">
		<!-- recursive template to right justify and prepend -->
		<!-- the value with whatever padChar is passed in -->
		<xsl:param name="padChar" />
		<xsl:param name="padVar" />
		<xsl:param name="length" />
		<xsl:choose>
			<xsl:when test="string-length($padVar) &lt; $length">
				<xsl:call-template name="prepend-pad">
					<xsl:with-param name="padChar" select="$padChar" />
					<xsl:with-param name="padVar" select="concat($padChar,$padVar)" />
					<xsl:with-param name="length" select="$length" />
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of
					select="substring($padVar,string-length($padVar) - $length + 1)" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="append-pad">
		<!-- recursive template to left justify and append -->
		<!-- the value with whatever padChar is passed in -->
		<xsl:param name="padChar" />
		<xsl:param name="padVar" />
		<xsl:param name="length" />
		<xsl:choose>
			<xsl:when test="string-length($padVar) &lt; $length">
				<xsl:call-template name="append-pad">
					<xsl:with-param name="padChar" select="$padChar" />
					<xsl:with-param name="padVar" select="concat($padVar,$padChar)" />
					<xsl:with-param name="length" select="$length" />
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="substring($padVar,1,$length)" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="initCap">
	  <xsl:param name="str"/>
	  <xsl:value-of select="translate(substring($str,1,1) ,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>
	  <xsl:variable name="part2"><xsl:value-of select="substring($str,2)"/></xsl:variable>
	  <xsl:choose>
	    <xsl:when test="contains(part2, '-')">
	        <xsl:value-of select="concat(substring-before(part2,'-'),
	            '-', translate(substring(substring-after(part2,'-'),1,1),'abcdefghijklmnopqrstuvwxyz'
	                 ,'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), substring(substring-after(part2,'-'),2))"/>
	    </xsl:when>
	    <xsl:otherwise>
	        <xsl:value-of select="$part2"/>
	    </xsl:otherwise>
	  </xsl:choose>
	</xsl:template>

<!-- 	<xsl:template name="initCap">
		<xsl:param name="str" />
		<xsl:param name="pat" select="' '" />
		<xsl:choose>
			<xsl:when test="contains($str,$pat)">
				<xsl:call-template name="initcaps">
					<xsl:with-param name="x" select="substring-before($str,$pat)" />
				</xsl:call-template>
				<xsl:call-template name="initCap">
					<xsl:with-param name="str" select="substring-after($str,$pat)" />
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="initcaps">
					<xsl:with-param name="x" select="$str" />
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template> -->
	<xsl:template name="initcaps">
		<xsl:param name="x" />
		<xsl:value-of select="translate(substring($x,1,1) ,'abcdefghijklmnopqrstuvwxyz' ,'ABCDEFGHIJKLMNOPQRSTUVWXYZ')" />
		<xsl:value-of select="translate(substring($x, 2),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')" />
	</xsl:template>
	
	<xsl:template name="lower-case">
		<xsl:param name="x" />
		<xsl:value-of select="translate($x,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')" />
	</xsl:template>
	
	<xsl:template name="gradename-word-map">
    	<xsl:param name="val"/>
	    <xsl:choose>
	      <xsl:when test="contains($val, '3')">third</xsl:when>
	      <xsl:when test="contains($val, '4')">fourth</xsl:when>
	      <xsl:when test="contains($val, '5')">fifth</xsl:when>
	      <xsl:when test="contains($val, '6')">sixth</xsl:when>
	      <xsl:when test="contains($val, '7')">seventh</xsl:when>
	      <xsl:when test="contains($val, '8')">eighth</xsl:when>
	      <xsl:when test="contains($val, '9')">ninth</xsl:when>
	      <xsl:when test="contains($val, '10')">tenth</xsl:when>
	      <xsl:when test="contains($val, '11')">eleventh</xsl:when>
	      <xsl:otherwise><xsl:value-of select="$val"/></xsl:otherwise>
	    </xsl:choose>
  	</xsl:template>
  	<xsl:template name="levelnumber-word-map">
    	<xsl:param name="val"/>
	    <xsl:choose>
	      <xsl:when test="contains($val, '1')">one</xsl:when>
	      <xsl:when test="contains($val, '2')">two</xsl:when>
	      <xsl:when test="contains($val, '3')">three</xsl:when>
	      <xsl:when test="contains($val, '4')">four</xsl:when>
	      <xsl:when test="contains($val, '5')">five</xsl:when>
	      <xsl:when test="contains($val, '6')">six</xsl:when>
	      <xsl:otherwise><xsl:value-of select="$val"/></xsl:otherwise>
	    </xsl:choose>
  	</xsl:template>
  	<xsl:template name="privacyStatementForNoScore">
    	 <fo:block font-size="10pt">
  			<fo:inline font-size="8pt">Data not shown to protect student privacy</fo:inline>
     	</fo:block>
    </xsl:template>
    <xsl:template name="inSufficientDataMessage">
    	 <fo:block font-size="10pt">
  			<fo:inline font-size="8pt">Due to the small number of students, data are not shown to protect student privacy.</fo:inline>
     	</fo:block>
    </xsl:template>
</xsl:stylesheet>