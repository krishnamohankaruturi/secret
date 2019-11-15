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
    				<xsl:if test="not(standardError)"><xsl:text>N/A</xsl:text></xsl:if>
    				<xsl:if test="standardError != ''">
    					<xsl:value-of select="format-number(standardError, '0.0')"/>
    				</xsl:if>
    			</xsl:if>
    		</xsl:if>
    		<xsl:if test="$paramType = 'SC'">		
    			<xsl:if test="organizationId = $paramOrgId">
    				<xsl:if test="not(studentCount)"><xsl:text>N/A</xsl:text></xsl:if>
    				<xsl:if test="studentCount != ''">
    					<xsl:value-of select="format-number(studentCount, '###,###')" />
    			     </xsl:if>
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
    <xsl:template name="lookup-level-description-by-descriptiontype">
    	<xsl:param name="descriptionType"/>
    	<xsl:param name="dataLevel"/>
	    <xsl:for-each select="reportDetails/data/allLevelDescriptions/edu.ku.cete.domain.report.LevelDescription">
	   		<xsl:if test="(($descriptionType = descriptionType) and ($dataLevel = level))">
	   			
	   			<xsl:variable name="firstLevelWordDescription" select="substring-before( concat(levelDescription, '~' ) , '~')"/>
				
				<xsl:choose>
					<xsl:when test="contains(levelDescription, '~')">
						<fo:block font-weight="bold" >
	   						<xsl:value-of select="substring-before( concat(levelDescription, '~' ) , '~' )" />
	   					</fo:block>
					</xsl:when>
					<xsl:otherwise>
						<fo:block>
	   						<xsl:value-of select="substring-before( concat(levelDescription, '~' ) , '~' )" />
	   					</fo:block>
					</xsl:otherwise>
				</xsl:choose>	
								
				<!-- <xsl:choose>
					<xsl:when test="($firstLevelWordDescription = 'Students who score at this level can typically ' ) or ($firstLevelWordDescription = 'Students who score at this level can typically' ) ">
						<fo:block font-weight="bold" >
	   						<xsl:value-of select="substring-before( concat(levelDescription, '~' ) , '~' )" />
	   					</fo:block>
					</xsl:when>
					<xsl:otherwise>
						<fo:block>
	   						<xsl:value-of select="substring-before( concat(levelDescription, '~' ) , '~' )" />
	   					</fo:block>
					</xsl:otherwise>
				</xsl:choose> -->
				
	   			<xsl:variable name="tempLevelDescription" select="substring-after( concat(levelDescription, '~' ), '~')"/>
	       					<fo:block>
							     <xsl:call-template name="splitStringToItems">
							       <xsl:with-param name="delimiter" />
							       <xsl:with-param name="list" select="$tempLevelDescription" />
							     </xsl:call-template>
							     </fo:block>
			
	  		</xsl:if> 		
	    </xsl:for-each>
	</xsl:template>
	<xsl:template name="lookup-level-description-by-descriptiontype-for-MDPT">
    	<xsl:param name="descriptionType"/>
    	<xsl:param name="dataLevel"/>
	    <xsl:for-each select="reportDetails/data/allLevelDescriptions/edu.ku.cete.domain.report.LevelDescription">
	   		<xsl:if test="(($descriptionType = descriptionType) and ($dataLevel = level))">
				<fo:block>
	   				<xsl:value-of select="substring-before( concat(levelDescription, '~' ) , '~' )" />
	   			</fo:block>
	   			<xsl:variable name="tempLevelDescription" select="substring-after( concat(levelDescription, '~' ), '~')"/>
	       		<fo:block>
      				<xsl:call-template name="splitStringToItems">
						<xsl:with-param name="delimiter" />
						<xsl:with-param name="list" select="$tempLevelDescription" />
					</xsl:call-template>
				</fo:block>
	  		</xsl:if> 		
	    </xsl:for-each>
	</xsl:template>
	
	<xsl:template name="splitStringToItems">
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
						<fo:table-cell height="0.27cm" width="0.4cm">
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
	     <xsl:call-template name="splitStringToItems">
		       <xsl:with-param name="list" select="$remaining" />
		       <xsl:with-param name="delimiter" select="$delim" />
	     </xsl:call-template>
	   </xsl:if>
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
	
	<xsl:template name="lastName-Header">
    	<xsl:variable name="lastNameString" select="reportDetails/data/studentLastName"></xsl:variable>
    	<xsl:variable name="firstNameString" select="reportDetails/data/studentFirstName"></xsl:variable>
    	<xsl:if test="string-length($firstNameString) &gt;= '15'">
    		<fo:inline><xsl:value-of select="substring($lastNameString,1,15)" /></fo:inline>
    	</xsl:if>
    	<xsl:if test="string-length($firstNameString) &lt; '15'">
    		<xsl:variable name="countFirstName" select="((15 - string-length($firstNameString)) + 15)" />
    		<fo:inline><xsl:value-of select="substring($lastNameString,1,$countFirstName)" /></fo:inline>
    	</xsl:if>
    </xsl:template>
	<xsl:template name="firstName-Header">
    	<xsl:variable name="firstNameString" select="reportDetails/data/studentFirstName"></xsl:variable>
    	<xsl:variable name="lastNameString" select="reportDetails/data/studentLastName"></xsl:variable>
    	<xsl:if test="string-length($lastNameString) &gt;= '15'">
    		<fo:inline><xsl:value-of select="substring($firstNameString,1,15)" /></fo:inline>
    	</xsl:if>
    	<xsl:if test="string-length($lastNameString) &lt; '15'">
    		<xsl:variable name="countLastName" select="((15 - string-length($lastNameString)) + 15)" />
    		<fo:inline><xsl:value-of select="substring($firstNameString,1,$countLastName)" /></fo:inline>
    	</xsl:if>
	</xsl:template>
	
<!-- 	<xsl:template name="lastName-page2">
    	<xsl:variable name="lastNameString" select="reportDetails/data/studentLastName"></xsl:variable>
    	<xsl:variable name="firstNameString" select="reportDetails/data/studentFirstName"></xsl:variable>
    	<xsl:if test="string-length($firstNameString) &gt;= '20'">
    		<fo:inline><xsl:value-of select="substring($lastNameString,1,20)" /></fo:inline>
    	</xsl:if>
    	<xsl:if test="string-length($firstNameString) &lt; '20'">
    		<xsl:variable name="countFirstName" select="((20 - string-length($firstNameString)) + 20)" />
    		<fo:inline><xsl:value-of select="substring($lastNameString,1,$countFirstName)" /></fo:inline>
    	</xsl:if>
    </xsl:template>
	<xsl:template name="firstName-page2">
    	<xsl:variable name="firstNameString" select="reportDetails/data/studentFirstName"></xsl:variable>
    	<xsl:variable name="lastNameString" select="reportDetails/data/studentLastName"></xsl:variable>
    	<xsl:if test="string-length($lastNameString) &gt;= '20'">
    		<fo:inline><xsl:value-of select="substring($firstNameString,1,20)" /></fo:inline>
    	</xsl:if>
    	<xsl:if test="string-length($lastNameString) &lt; '20'">
    		<xsl:variable name="countLastName" select="((20 - string-length($lastNameString)) + 20)" />
    		<fo:inline><xsl:value-of select="substring($firstNameString,1,$countLastName)" /></fo:inline>
    	</xsl:if>
	</xsl:template> -->
	
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
		<fo:table>
		<fo:table-body>
		<fo:table-row>
		<fo:table-cell>
		<fo:block-container height="2cm" width="3cm">
			<fo:block margin-left="14mm" margin-top="-10mm">
				<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
					inline-progression-dimension.maximum="100%" block-progression-dimension.maximum="100%"
					 height="2.88cm" width="3.88cm">
					 <xsl:attribute name="src"><xsl:value-of select="reportDetails/logoPath"/></xsl:attribute>
				</fo:external-graphic>
			</fo:block>
		</fo:block-container>
		</fo:table-cell>
		</fo:table-row>
		</fo:table-body>
		</fo:table>
	</xsl:template>
	<xsl:template name="scoreStarPerformanceLevel">
		<fo:block-container height="90%" width="90%">
			<fo:block>
				<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
					inline-progression-dimension.maximum="100%" block-progression-dimension.maximum="100%">
					 <xsl:attribute name="src"><xsl:value-of select="reportDetails/scoreStarPerformanceLevelPath"/></xsl:attribute>
				</fo:external-graphic>
			</fo:block>
		</fo:block-container>
	</xsl:template>
	
	<!-- US18542 add transferred student path -->	
	<xsl:template name="scoreTransferredStudentPath">
		<fo:block-container height="100%" width="100%">
			<fo:block>
				<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
					inline-progression-dimension.maximum="100%" block-progression-dimension.maximum="100%">
					 <xsl:attribute name="src"><xsl:value-of select="reportDetails/transferredStudentPath"/></xsl:attribute>
				</fo:external-graphic>
			</fo:block>
		</fo:block-container>
	</xsl:template>
	<xsl:template name="scoreIncompleteScorePath">
		<fo:block-container height="100%" width="100%">
			<fo:block>
				<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
					inline-progression-dimension.maximum="100%" block-progression-dimension.maximum="100%">
					 <xsl:attribute name="src"><xsl:value-of select="reportDetails/incompleteScorePath"/></xsl:attribute>
				</fo:external-graphic>
			</fo:block>
		</fo:block-container>
	</xsl:template>
	<xsl:template name="responseNotScoredPath">
		<fo:block-container height="100%" width="100%">
			<fo:block>
				<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
					inline-progression-dimension.maximum="100%" block-progression-dimension.maximum="100%">
					 <xsl:attribute name="src"><xsl:value-of select="reportDetails/responseNotScoredPath"/></xsl:attribute>
				</fo:external-graphic>
			</fo:block>
		</fo:block-container>
	</xsl:template>
	
	
	
	
	<xsl:template name="footerNextPageArrow">
		<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
			inline-progression-dimension.maximum="100%" block-progression-dimension.maximum="100%">
			 <xsl:attribute name="src"><xsl:value-of select="reportDetails/footerNextPageArrowPath"/></xsl:attribute>
		</fo:external-graphic>
	</xsl:template>
	<xsl:template name="rightTriangleBulletGeneration">
		<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
			inline-progression-dimension.maximum="100%" block-progression-dimension.maximum="100%">
			 <xsl:attribute name="src"><xsl:value-of select="reportDetails/blackRightPointingTriangleBullet"/></xsl:attribute>
		</fo:external-graphic>
	</xsl:template>
	<xsl:template name="onDemandWritingTaskScoreLevel">
		<fo:block-container height="100%" width="95%">
			<fo:block margin-left="10pt">
				<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
					inline-progression-dimension.maximum="100%" block-progression-dimension.maximum="100%" height="100%" width="100%">
					 <xsl:attribute name="src"><xsl:value-of select="reportDetails/onDemandWritingTaskScorePath"/></xsl:attribute>
				</fo:external-graphic>
			</fo:block>
		</fo:block-container>
	</xsl:template>
	<xsl:template name="reportFooter">
		<fo:block>
	     	<fo:table table-layout="fixed">
				<fo:table-column/>
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block space-after="5pt"><fo:leader leader-pattern="rule"  leader-length="100%" rule-style="solid"/></fo:block>
							<fo:block  font-size="8pt" text-align="left">&#169; <xsl:value-of select="java:format(java:java.text.SimpleDateFormat.new('yyyy'), java:java.util.Date.new())"/> The University of Kansas</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
	     </fo:block>
	</xsl:template>
	<xsl:template name="headerSchoolYear">
		<fo:block font-family="Verdana" font-weight="bold" font-size="9pt"  margin-left="25mm" padding-top="2pt"><xsl:value-of select="reportDetails/data/currentSchoolYear - 1"/><xsl:text >&#8213;</xsl:text><xsl:value-of select="reportDetails/data/currentSchoolYear"/></fo:block>
	</xsl:template>
	<xsl:template name="headerGradeSubjectName"> 
    	<xsl:variable name="gradeSubject"><xsl:text>GRADE&#58;&#160;</xsl:text>
    	<xsl:value-of select="reportDetails/data/gradeCode"/>
    	<xsl:text>&#160;</xsl:text>
    		<xsl:choose>
				<xsl:when test="reportDetails/data/contentAreaCode = 'SS'">
					<xsl:text>History, Government, &amp; Social Studies</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="reportDetails/data/contentAreaName"/>
				</xsl:otherwise>
			</xsl:choose>
    	</xsl:variable>
    	<fo:block wrap-option="no-wrap">
	    	<xsl:value-of select="$gradeSubject"/> &#47; STATE ID: <xsl:call-template name="stateStudentIdentifier"/>
		</fo:block>
	</xsl:template>	
									
	<xsl:template name="headerSchoolName"> 
		<xsl:variable name="schName">SCHOOL: <xsl:value-of select="reportDetails/data/attendanceSchoolName"/></xsl:variable>
    	<fo:block>
			<xsl:value-of select="$schName"/>
    	</fo:block>
	</xsl:template>
	<xsl:template name="headerDistrictName"> 
		<xsl:variable name="districtName">DISTRICT: <xsl:value-of select="reportDetails/data/districtName"/> &#47; &#35;<xsl:value-of select="reportDetails/data/districtDisplayIdentifier"/></xsl:variable>
    	<fo:block>
			<xsl:value-of select="$districtName"/>
    	</fo:block>
	</xsl:template>
	<xsl:template name="headerContentAreaName">	
		<xsl:variable name="contentAreaName">SUBJECT: <xsl:value-of select="reportDetails/data/contentAreaName"/></xsl:variable>
    	<fo:block>
    	<xsl:if test="reportDetails/data/contentAreaCode = 'SS'">
		<xsl:text>SUBJECT: History, Government, and Social Studies</xsl:text>
		</xsl:if>
		<xsl:if test="reportDetails/data/contentAreaCode != 'SS'">
			<xsl:value-of select="$contentAreaName"/>
			</xsl:if>
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
  			<fo:inline font-size="8pt" text-align="left">Fewer than 10 students. Data not reported.</fo:inline>
     	</fo:block>
    </xsl:template>
    <xsl:template name="privacyStatementForNoScoreWordsForSchoolSummary">
    	 <fo:block font-size="10pt">
  			<fo:inline font-size="8pt" background-color="white" >Fewer than 10 students. Data not reported.</fo:inline>
     	</fo:block>
    </xsl:template>
    <xsl:template name="inSufficientDataMessage">
    	 <fo:block font-size="10pt">
  			<fo:inline font-size="8pt">Due to the small number of students, data are not shown to protect student privacy.</fo:inline>
     	</fo:block>
    </xsl:template>
    <xsl:template name="iTerateSubScoreDescriptions">
    	<xsl:param name = "subScoreDataLevel"/>
    	<xsl:param name="subScoreIconExceedsPath"/>
		<xsl:param name="subScoreIconMeetsPath"/>
		<xsl:param name="subScoreIconBelowPath"/>
		<xsl:param name="subScoreIconInsufficientPath"/>
															
    	<xsl:for-each select="reportDetails/data/testLevelSubscoreBuckets/edu.ku.cete.domain.report.ReportSubscores">
    		<fo:table>
      			<fo:table-column column-width="3%"></fo:table-column>
      			<fo:table-column column-width="97%"></fo:table-column>
      				<fo:table-body>
      					<fo:table-row >
						<fo:table-cell number-columns-spanned="2">
							<fo:block font-family="Verdana" font-size="8pt" text-align="left" margin-top="9pt">
								<xsl:value-of select="subScoreReportDisplayName"/>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell>
							<fo:block margin-top="1pt" text-align="left" margin-right="3pt">
								<xsl:call-template name="studentPermformanceWithSign">
									<xsl:with-param name="Param" select="rating"></xsl:with-param>
									<xsl:with-param name="subScoreIconExceedsPath" select="$subScoreIconExceedsPath"></xsl:with-param>
									<xsl:with-param name="subScoreIconMeetsPath" select="$subScoreIconMeetsPath"></xsl:with-param>
									<xsl:with-param name="subScoreIconBelowPath" select="$subScoreIconBelowPath"></xsl:with-param>
									<xsl:with-param name="subScoreIconInsufficientPath" select="$subScoreIconInsufficientPath"></xsl:with-param>
								</xsl:call-template>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block margin-left="2pt" margin-top="2pt" font-family="Verdana" font-size="8pt" text-align="left">
								<fo:inline>
									<xsl:call-template name="studentPermformanceWithDescription">
										<xsl:with-param name="subScoreDataLevel" select="$subScoreDataLevel"></xsl:with-param>
										<xsl:with-param name="subScoreRating" select="rating"></xsl:with-param>
									</xsl:call-template>
									<xsl:value-of select="subScoreReportDescription"/>
								</fo:inline>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell number-columns-spanned="2">
							<fo:block>
								<xsl:if test="sectionLineBelow='true'">
									<fo:block>
										<fo:leader leader-pattern="rule"  leader-length="100%" rule-style="solid"/>	
									</fo:block>
								</xsl:if>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
      			</fo:table-body>
	       	</fo:table>
		</xsl:for-each>
    </xsl:template>

    <xsl:template name="studentPermformanceWithSign">
 		<xsl:param name="Param"/>
		<xsl:param name="subScoreIconExceedsPath"/>
		<xsl:param name="subScoreIconMeetsPath"/>
		<xsl:param name="subScoreIconBelowPath"/>
		<xsl:param name="subScoreIconInsufficientPath"/>
		
		<xsl:if test="$Param = 1">
			<fo:inline>
				<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
					inline-progression-dimension.maximum="5mm" block-progression-dimension.maximum="5mm"  height="5mm" width="5mm">
					<xsl:attribute name="src"><xsl:value-of select="$subScoreIconBelowPath"/></xsl:attribute>
				</fo:external-graphic>
			</fo:inline>
		</xsl:if>
		<xsl:if test="$Param = 2">
			 <fo:inline>
				<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
					inline-progression-dimension.maximum="5mm" block-progression-dimension.maximum="5mm" height="5mm" width="5mm">
					<xsl:attribute name="src"><xsl:value-of select="$subScoreIconMeetsPath "/></xsl:attribute>
				</fo:external-graphic>
			</fo:inline>
		</xsl:if>
		<xsl:if test="$Param = 3">
			<fo:inline>
				<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
					inline-progression-dimension.maximum="5mm" block-progression-dimension.maximum="5mm" height="5mm" width="5mm">
					<xsl:attribute name="src"><xsl:value-of select="$subScoreIconExceedsPath"/></xsl:attribute>
				</fo:external-graphic>
			</fo:inline>
		</xsl:if>
		<xsl:if test="$Param = -1">
			<fo:inline>
				<fo:external-graphic content-width="scale-down-to-fit" content-height="scale-down-to-fit"
					inline-progression-dimension.maximum="5mm" block-progression-dimension.maximum="5mm" height="5mm" width="5mm">
					<xsl:attribute name="src"><xsl:value-of select="$subScoreIconInsufficientPath"/></xsl:attribute>
				</fo:external-graphic>
			</fo:inline>
		</xsl:if>
	</xsl:template>
	<xsl:template name="studentPermformanceWithDescription">
		<xsl:param name="subScoreRating"/>
		<xsl:param name="subScoreDataLevel"/>
		
		<xsl:if test="$subScoreRating = 1">
			<fo:inline font-family="Verdana" font-size="8pt" text-align="left" font-weight="bold">
				<xsl:text>&#x9;In this area, your student performed below students who received the minimum Level 3 score.&#x9;</xsl:text>
			</fo:inline>
		</xsl:if>
		<xsl:if test="$subScoreRating = 2">
			<fo:inline font-family="Verdana" font-size="8pt" text-align="left" font-weight="bold">
				<xsl:text>&#x9;In this area, your student performed as well as students who received the minimum Level 3 score.&#x9;</xsl:text>
			</fo:inline>
		</xsl:if>
		<xsl:if test="$subScoreRating = 3">
			<fo:inline font-family="Verdana" font-size="8pt" text-align="left" font-weight="bold">
				<xsl:text>&#x9;In this area, your student performed better than students who received the minimum Level 3 score.&#x9;</xsl:text>
			</fo:inline>
		</xsl:if>
		<xsl:if test="$subScoreRating = -1">
			<fo:inline font-family="Verdana" font-size="8pt" text-align="left" font-weight="bold">
				<xsl:text>&#x9;In this area, your student did not answer enough questions for accurate reporting.&#x9;</xsl:text>
			</fo:inline>
		</xsl:if>
	</xsl:template>
	<xsl:template name="schoolreportFooter">
		<fo:block>
	     	<fo:table table-layout="fixed">
				<fo:table-column/>
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block space-after="5pt"><fo:leader leader-pattern="rule"  leader-length="100%" rule-style="solid"/></fo:block>
							<fo:block  font-size="8pt" text-align="left">&#169; <xsl:value-of select="java:format(java:java.text.SimpleDateFormat.new('yyyy'), java:java.util.Date.new())"/> The University of Kansas</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
	     </fo:block>
	</xsl:template>
	<xsl:template name="summaryheaderSchoolName">
		<xsl:variable name="schName">
			SCHOOL REPORT:
			<xsl:value-of select="reportDetails/data/summaryHeaderSchoolName" />
			&#47; &#35;<xsl:value-of select="reportDetails/data/attSchDisplayIdentifier" />
		</xsl:variable>
		<fo:block>
			<xsl:value-of select="$schName" />
		</fo:block>
	</xsl:template>
	<xsl:template name="summaryheaderDistrictName">
		<xsl:variable name="districtName">
			DISTRICT REPORT:
			<xsl:value-of select="reportDetails/data/summaryHeaderDistrictName" />
			&#47; &#35;<xsl:value-of select="reportDetails/data/districtDisplayIdentifier" />
		</xsl:variable>
		<fo:block>
			<xsl:value-of select="$districtName" />
		</fo:block>
	</xsl:template>
	<xsl:template name="rating_icons_text">
		<xsl:param name="imageone" />
		<xsl:param name="imagetwo" />
		<xsl:param name="textone" />
		<xsl:param name="texttwo" />
		<xsl:param name="cantentone" />
		<xsl:param name="cantenttwo" />
		<fo:table table-layout="fixed">
			<fo:table-column column-width="3%" />
			<fo:table-column column-width="45%" />
			<fo:table-column column-width="4%" />
			<fo:table-column column-width="3%" />
			<fo:table-column column-width="45%" />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell height="1.27mm" display-align="center">
					<fo:block height="5mm" font-size="8pt" text-align="center"
						display-align="center">
						<xsl:call-template name="rating_icon">
							<xsl:with-param name="path" select="$imageone"></xsl:with-param>
						</xsl:call-template>
					</fo:block>
					</fo:table-cell>
					<fo:table-cell height="1.27mm" display-align="center">
						<fo:block font-size="8pt" text-align="left"
							display-align="center" font-weight="bold" margin-left="3pt">
							<xsl:value-of select="$textone" />
						</fo:block>

					</fo:table-cell>
					<fo:table-cell height="1.27mm" display-align="center">
						<fo:block font-size="8pt" text-align="center"
							display-align="center">
						</fo:block>
					</fo:table-cell>
					<fo:table-cell height="1.27mm" display-align="center">
					<fo:block height="5mm" font-size="8pt" text-align="center"
						display-align="center">
						<xsl:call-template name="rating_icon">
							<xsl:with-param name="path" select="$imagetwo"></xsl:with-param>
						</xsl:call-template>
					</fo:block>
					</fo:table-cell>
					<fo:table-cell height="1.27mm" display-align="center">
						<fo:block font-size="8pt" text-align="left"
							display-align="center" font-weight="bold" margin-left="3pt">
							<xsl:value-of select="$texttwo" />
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
		<fo:table table-layout="fixed">
			<fo:table-column column-width="48%" />
			<fo:table-column column-width="4%" />
			<fo:table-column column-width="48%" />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell display-align="center">
						<fo:block font-size="8pt" text-align="left"
							display-align="center" margin-left="2pt">
							<xsl:value-of select="$cantentone" />
						</fo:block>

					</fo:table-cell>
					<fo:table-cell height="1.27mm" display-align="center">
						<fo:block font-size="8pt" text-align="center"
							display-align="center">
						</fo:block>
					</fo:table-cell>
					<fo:table-cell display-align="center">
						<fo:block font-size="8pt" text-align="left"
							display-align="center" margin-left="2pt">
							<xsl:value-of select="$cantenttwo" />
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>
	<xsl:template name="rating_icon">
		<xsl:param name="path" />
		
		<fo:external-graphic content-width="scale-down-to-fit"
					content-height="scale-down-to-fit"
					inline-progression-dimension.maximum="5mm"
					block-progression-dimension.maximum="5mm" height="5mm" width="5mm">
				<xsl:attribute name="src"><xsl:value-of select="$path" /></xsl:attribute>
			</fo:external-graphic>
		
	</xsl:template>
	<xsl:template name="rating_icon_table">
		<xsl:param name="stringvalue" />
		<xsl:param name="subScoreIconExceedsPath" />
		<xsl:param name="subScoreIconMeetsPath" />
		<xsl:param name="subScoreIconBelowPath" />
		<xsl:param name="subScoreIconInsufficientPath" />
		
		
			<xsl:if test="$stringvalue = 3">
			<xsl:call-template name="rating_icon_legand_image">
							<xsl:with-param name="path" select="$subScoreIconExceedsPath"></xsl:with-param>
					
						</xsl:call-template>				
			</xsl:if>
			<xsl:if test="$stringvalue = 2 ">
			<xsl:call-template name="rating_icon_legand_image">
							<xsl:with-param name="path" select="$subScoreIconMeetsPath"></xsl:with-param>
						
						</xsl:call-template>				
			</xsl:if>
			<xsl:if test="$stringvalue = 1">
			<xsl:call-template name="rating_icon_legand_image">
							<xsl:with-param name="path" select="$subScoreIconBelowPath"></xsl:with-param>
			
			</xsl:call-template>				
			</xsl:if>
			<xsl:if test="$stringvalue = -1 or  $stringvalue = 10">
				<xsl:call-template name="rating_icon_legand_image">
							<xsl:with-param name="path" select="$subScoreIconInsufficientPath"></xsl:with-param>
							
			</xsl:call-template>
			</xsl:if>
		
	</xsl:template>
	<xsl:template name="rating_icon_legand_image">
	<xsl:param name="path" />
		<fo:block height="5mm" font-size="8pt" text-align="center"
			display-align="center" >
		<fo:external-graphic content-width="scale-down-to-fit"
					content-height="scale-down-to-fit"
					inline-progression-dimension.maximum="5mm"
					block-progression-dimension.maximum="5mm" height="5mm" width="5mm">
					<xsl:attribute name="src"><xsl:value-of
						select="$path" /></xsl:attribute>
		</fo:external-graphic>
		</fo:block>
		
	</xsl:template>
	<xsl:template name="rating_icon_legand">
		<xsl:param name="organization" />
		<fo:block font-weight="bold" margin-top="10pt"  text-align="right">
			<fo:table table-layout="fixed">
				<fo:table-column column-width="47%" />
				<fo:table-column column-width="3%" />
				<fo:table-column column-width="9%" />
				<fo:table-column column-width="3%" />
				<fo:table-column column-width="7%" />
				<fo:table-column column-width="3%" />
				<fo:table-column column-width="7%" />
				<fo:table-column column-width="3%" />
				<fo:table-column column-width="18%" />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell text-align="left">
							<fo:block font-size="12pt" text-align="left" margin-top="-6pt">
								<xsl:text>Your </xsl:text><xsl:value-of select="$organization" /><xsl:text>’s Performance</xsl:text>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell height="1.27mm" display-align="center" >
						<fo:block height="5mm" font-size="8pt" text-align="center"
								display-align="center" margin-top="-8pt" >
								<fo:external-graphic content-width="scale-down-to-fit"
								content-height="scale-down-to-fit"
							inline-progression-dimension.maximum="5mm"
								block-progression-dimension.maximum="5mm" height="5mm" width="5mm">
									<xsl:attribute name="src"><xsl:value-of
										select="reportDetails/iconExceedsPath" /></xsl:attribute>
								</fo:external-graphic>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell height="1.27mm" display-align="center">
							<fo:block font-size="8pt" text-align="left"
								display-align="center" margin-top="-8pt" margin-left="3pt">
								<xsl:text>Exceeds</xsl:text>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell height="1.27mm" display-align="center">
							<fo:block height="5mm" font-size="8pt" text-align="center"
								display-align="center" margin-top="-8pt">
								<fo:external-graphic content-width="scale-down-to-fit"
								content-height="scale-down-to-fit"
							inline-progression-dimension.maximum="5mm"
								block-progression-dimension.maximum="5mm" height="5mm" width="5mm">
									<xsl:attribute name="src"><xsl:value-of
										select="reportDetails/iconMeetsPath" /></xsl:attribute>
								</fo:external-graphic>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell height="1.27mm" display-align="center">
							<fo:block font-size="8pt" text-align="left"
								display-align="center" margin-top="-8pt" margin-left="3pt">
								<xsl:text>Meets</xsl:text>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell height="1.27mm" display-align="center">
							<fo:block height="5mm" font-size="8pt" text-align="center"
								display-align="center" margin-top="-8pt" >
								<fo:external-graphic content-width="scale-down-to-fit"
								content-height="scale-down-to-fit"
							inline-progression-dimension.maximum="5mm"
								block-progression-dimension.maximum="5mm" height="5mm" width="5mm">
									<xsl:attribute name="src"><xsl:value-of
										select="reportDetails/iconBelowPath" /></xsl:attribute>
								</fo:external-graphic>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell height="1.27mm" display-align="center">
							<fo:block font-size="8pt" text-align="left"
								display-align="center" margin-top="-8pt" margin-left="3pt">
								<xsl:text>Below</xsl:text>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell height="1.27mm" display-align="center">
							<fo:block height="5mm" font-size="8pt" text-align="right"
								display-align="center" margin-top="-8pt">
								<fo:external-graphic content-width="scale-down-to-fit"
								content-height="scale-down-to-fit"
							inline-progression-dimension.maximum="5mm"
								block-progression-dimension.maximum="5mm" height="5mm" width="5mm" >
									<xsl:attribute name="src"><xsl:value-of
										select="reportDetails/iconInsufficientPath" /></xsl:attribute>
								</fo:external-graphic>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell height="1.27mm" display-align="center">
							<fo:block font-size="8pt" text-align="left"
								 margin-top="-8pt" margin-left="3pt" >
								<xsl:text>Insufficient Data</xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
		<fo:block font-weight="bold" margin-top="12pt">
			<fo:table table-layout="fixed">
				<fo:table-column column-width="33%" />
				<fo:table-column column-width="67%" />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell display-align="center">
							<fo:block font-size="9pt" text-align="left" font-weight="bold"
								margin-top="6pt">
								<xsl:text>Grade</xsl:text>
							</fo:block>

						</fo:table-cell>
						<fo:table-cell display-align="center">
							<fo:table table-layout="fixed">
								<xsl:for-each select="reportDetails/allGrades/string">
									<fo:table-column  column-width="14.285%"/>
								</xsl:for-each>
								<fo:table-body>
									<fo:table-row>
										<xsl:for-each select="reportDetails/allGrades/string">
											<fo:table-cell height="1.27mm" display-align="center">
												<fo:block font-size="8pt" text-align="center"
													margin-right="2pt">
													<xsl:value-of select="substring-after(., 'Grade ')" />
												</fo:block>
											</fo:table-cell>
										</xsl:for-each>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
	</xsl:template>
	<xsl:template name="rating_icons_text_looper">
		<xsl:param name="organization" />
		<xsl:param name="subScoreIconExceedsPath" />
		<xsl:param name="subScoreIconMeetsPath" />
		<xsl:param name="subScoreIconBelowPath" />
		<xsl:param name="subScoreIconInsufficientPath" />
		<xsl:for-each
			select="reportDetails/data/testLevelSubscoreBuckets/edu.ku.cete.domain.report.ReportSubscores">
			
			<fo:block>
				<fo:table table-layout="fixed">
					<fo:table-column column-width="33%" />
					<fo:table-column column-width="67%" />
					<fo:table-body>
					<xsl:if test="$organization = 'District'">
					<fo:table-row>	
					<fo:table-cell display-align="center" number-columns-spanned="2">				
				<fo:block margin-top="-7pt">
					<fo:leader leader-pattern="rule" rule-thickness="0.5pt"
						background-color="#939597" leader-length="100%" space-after="-5pt"
						margin-top="-4pt" rule-style="solid" />
				</fo:block>	
			</fo:table-cell>
					</fo:table-row>
					</xsl:if>
						<fo:table-row >
							<xsl:choose>
								<xsl:when test="$organization = 'District'">
									<fo:table-cell display-align="center"
										wrap-option="wrap" >
										<xsl:call-template name="rating_icons_text_looper_text" />
									</fo:table-cell>
								</xsl:when>
								<xsl:otherwise>
									<fo:table-cell display-align="center"
										wrap-option="wrap" margin-top="12pt">
										<xsl:call-template name="rating_icons_text_looper_text" />
									</fo:table-cell>
								</xsl:otherwise>
							</xsl:choose>
							<fo:table-cell display-align="center">
								<fo:table table-layout="fixed">
									<xsl:for-each select="gradeRatings/edu.ku.cete.domain.report.Rating">
										<fo:table-column column-width="14.285%"/>
									</xsl:for-each>
									<fo:table-body>
										<fo:table-row>
											<xsl:for-each select="gradeRatings/edu.ku.cete.domain.report.Rating">
												<xsl:choose>
													<xsl:when test="$organization = 'District'">
														<fo:table-cell display-align="center" >
															<fo:block font-size="9pt" text-align="center"
																font-weight="bold" >
																<xsl:call-template name="rating_icon_table">
																	<xsl:with-param name="stringvalue"
																		select="rating"></xsl:with-param>
																	<xsl:with-param name="subScoreIconExceedsPath"
																		select="$subScoreIconExceedsPath"></xsl:with-param>
																	<xsl:with-param name="subScoreIconMeetsPath"
																		select="$subScoreIconMeetsPath"></xsl:with-param>
																	<xsl:with-param name="subScoreIconBelowPath"
																		select="$subScoreIconBelowPath"></xsl:with-param>
																	<xsl:with-param name="subScoreIconInsufficientPath"
																		select="$subScoreIconInsufficientPath">
																	</xsl:with-param>
																</xsl:call-template>
															</fo:block>
														</fo:table-cell>
													</xsl:when>
													<xsl:otherwise>
														<fo:table-cell display-align="center"
															margin-top="12pt">
															<fo:block font-size="9pt" text-align="left"
																font-weight="bold" margin-top="5pt">
																<xsl:call-template name="rating_icon_table">
																	<xsl:with-param name="stringvalue"
																		select="rating"></xsl:with-param>
																	<xsl:with-param name="subScoreIconExceedsPath"
																		select="$subScoreIconExceedsPath"></xsl:with-param>
																	<xsl:with-param name="subScoreIconMeetsPath"
																		select="$subScoreIconMeetsPath"></xsl:with-param>
																	<xsl:with-param name="subScoreIconBelowPath"
																		select="$subScoreIconBelowPath"></xsl:with-param>
																	<xsl:with-param name="subScoreIconInsufficientPath"
																		select="$subScoreIconInsufficientPath">
																	</xsl:with-param>
																</xsl:call-template>
															</fo:block>
														</fo:table-cell>
													</xsl:otherwise>
												</xsl:choose>
											</xsl:for-each>
										</fo:table-row>
									</fo:table-body>
								</fo:table>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="rating_icons_text_looper_text">
		<xsl:choose>
			<xsl:when test="childsubscore = 'true'">
				<fo:block font-size="8pt" text-align="left" font-family="Verdana"
					margin-left="5pt">
					<xsl:value-of select="subScoreReportDisplayName" />
				</fo:block>
			</xsl:when>
			<xsl:otherwise>
				<fo:block font-size="8pt" text-align="left" font-family="Verdana">
					<xsl:value-of select="subScoreReportDisplayName" />
				</fo:block>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
		    <xsl:template name="subsore_disription">	    			
    	<xsl:for-each select="reportDetails/data/testLevelSubscoreBuckets/edu.ku.cete.domain.report.ReportSubscores">
    		<fo:block-container keep-together.within-page="always">
    		<fo:table>      			
      			<fo:table-column />
      				<fo:table-body>
      					<fo:table-row >
						<fo:table-cell >
							<xsl:choose>
									<xsl:when test="childsubscore = 'true'">
										<fo:block font-size="8pt" text-align="left"
											font-family="Verdana" margin-top="8pt" margin-left="7pt">
											<xsl:value-of select="subScoreReportDisplayName" />
										</fo:block>
									</xsl:when>
									<xsl:otherwise>
										<fo:block font-size="8pt" text-align="left"
											font-family="Verdana" margin-top="7pt">
											<xsl:value-of select="subScoreReportDisplayName" />
										</fo:block>
									</xsl:otherwise>
								</xsl:choose>							
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>					
						<fo:table-cell>
						<xsl:choose>
									<xsl:when test="childsubscore = 'true'">
										<fo:block font-size="8pt" text-align="left"
											font-family="Verdana"  margin-left="7pt">
											<xsl:value-of select="subScoreReportDescription" />
										</fo:block>
									</xsl:when>
									<xsl:otherwise>
										<fo:block font-size="8pt" text-align="left"
											font-family="Verdana" >
											<xsl:value-of select="subScoreReportDescription" />
										</fo:block>
									</xsl:otherwise>
								</xsl:choose>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell >
							<fo:block>
								<xsl:if test="sectionLineBelow='true'">
									<fo:block>
										<fo:leader leader-pattern="rule"  rule-thickness="2pt" leader-length="100%" rule-style="solid"/>	
									</fo:block>
								</xsl:if>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
      			</fo:table-body>
	       	</fo:table>
	       	</fo:block-container>
		</xsl:for-each>
    </xsl:template>
	<xsl:template name="header_intro_paragraph_text">
		<fo:block  font-size="9pt">
				<fo:table table-layout="fixed">
								<fo:table-column column-width="proportional-column-width(100)" />
								<fo:table-body>
									<fo:table-row>
										<fo:table-cell>
											<fo:block margin-top="5pt" space-after="5pt">
												<fo:leader leader-pattern="rule"  leader-length="100%" rule-style="solid"  />
											</fo:block>
											<fo:block>
												<!-- <xsl:if test="reportDetails/data/performanceRawscoreIncludeFlag = 'true'"> -->
													<xsl:if test="reportDetails/data/contentAreaCode = 'ELA'">
														<xsl:if test="reportDetails/data/gradeCode = '10'">
															<!-- <xsl:text>The KAP assessments measure students’ understanding of the Kansas College and Career Ready Standards at each grade. The English language arts questions ask students to select the right answer and organize information.</xsl:text> -->
																 <xsl:text>The KAP assessments measure students’ understanding of the Kansas College and Career Ready Standards at each grade. The English language arts assessment asks students to read and answer questions about literary passages, informational texts, and writing samples. Students demonstrate their knowledge and skills related to reading and writing by selecting the right answer and sorting, matching, labeling, and ordering information.</xsl:text>
														</xsl:if>
													</xsl:if>
													<xsl:if test="reportDetails/data/contentAreaCode = 'M'">
														<xsl:if test="reportDetails/data/gradeCode != '10'">
															<xsl:text>The KAP assessments measure students’ understanding of the Kansas College and Career Ready Standards at each grade. The math assessment asks students to answer computation questions and questions about data presented in word problems, equations, graphs, tables, and diagrams. Students may show what they know about mathematics by
															selecting or providing the right answer, sorting or ordering items, creating graphs, and labeling pictures.</xsl:text>														
															<!-- <xsl:text>The KAP assessments measure students’ understanding of the Kansas College and Career Ready Standards at each grade. The math questions may ask students to select the right answer, sort items, create graphs, or label pictures. Your student’s score includes a performance task.</xsl:text> -->
														</xsl:if>
													</xsl:if>
												<!-- </xsl:if> 
												<xsl:if test="reportDetails/data/performanceRawscoreIncludeFlag != 'true'"> -->
													<xsl:if test="reportDetails/data/contentAreaCode = 'ELA'">
														<xsl:if test="reportDetails/data/gradeCode != '10'">
															<!-- <xsl:text>The KAP assessments measure students’ understanding of the Kansas College and Career Ready Standards at each grade. The English language arts questions ask students to select the right answer, organize information, and respond to a writing prompt. The overall score combines a score from the reading, writing, and listening section and a score from the on-demand writing task section.</xsl:text> -->
																 <xsl:text>The KAP assessments measure students’ understanding of the Kansas College and Career Ready Standards at each grade. The English language arts assessment asks students to read and answer questions about literary passages, informational texts, and writing samples. Students demonstrate their knowledge and skills related to reading and writing by selecting the right answer and sorting, matching, labeling, and ordering information.</xsl:text>
														</xsl:if>
													</xsl:if>
												<!-- </xsl:if> -->
												<xsl:if test="reportDetails/data/contentAreaCode = 'M'">
														<xsl:if test="reportDetails/data/gradeCode = '10'">
															<xsl:text>The KAP assessments measure students’ understanding of the Kansas College and Career Ready Standards at each grade. The math assessment asks students to answer computation questions and questions about data presented in word problems, equations, graphs, tables, and diagrams. Students may show what they know about mathematics by
															selecting or providing the right answer, sorting or ordering items, creating graphs, and labeling pictures.</xsl:text>
															<!-- <xsl:text>The KAP assessments measure students’ understanding of the Kansas College and Career Ready Standards at each grade. The math questions may ask students to select the right answer, sort items, create graphs, or label pictures.</xsl:text> -->
														</xsl:if>
												</xsl:if>
												<xsl:if test="reportDetails/data/contentAreaCode = 'SS'">
													<xsl:text>The KAP assessments measure students’ understanding of the Kansas College and Career Ready Standards at each grade. The history, government, and social studies questions may ask students to select the right answer, sort items, or label maps. Your student’s score includes a writing task.</xsl:text>
<!-- 													<xsl:text>The KAP assessments measure students’ understanding of the Kansas College and Career Ready Standards. Students take the history, government, and social studies assessment in grades 6, 8, and 11. These questions may ask students to select the right answer, organize information, and respond to a writing prompt.</xsl:text> -->
												</xsl:if>
												<xsl:if test="reportDetails/data/contentAreaCode = 'Sci'">
													<xsl:text>The KAP assessments measure students’ understanding of the Kansas College and Career Ready Standards at each grade. The science assessment asks students to answer questions about data presented in narratives, equations, graphs, tables, and diagrams. Students show what they know about science by selecting or providing the right answer; sorting, ordering, or matching items; and labeling pictures.</xsl:text>
												</xsl:if>
											</fo:block>
											<fo:block>
												<fo:leader leader-pattern="rule"  leader-length="100%" rule-style="solid"  />
											</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
				</fo:block>
	</xsl:template>
	
	<xsl:template name="noScores"> 				
			<fo:block margin-top="3mm" text-align="left" font-family="Verdana" font-size="11pt"  margin="0mm" padding-left="13mm" padding-bottom="13mm"  padding-top="7mm" padding-right="13mm">
					Your student did not receive a score for this assessment. This can happen for a 
					variety of reasons, including your student not taking the test, your student not 
					completing enough of the test, your student transferring schools during testing, 
					or a special circumstance preventing your student’s test from being scored. Please
					contact your district test coordinator, principal, or student’s teacher for specific 
					information.
			</fo:block>		
	</xsl:template>	
			
	<xsl:template name="upper-case">
		<xsl:param name="x" />
		<xsl:value-of select="translate($x,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')" />
	</xsl:template>
	
</xsl:stylesheet>