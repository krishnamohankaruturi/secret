<?xml version="1.0" encoding="UTF-8"?>
<!--
  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
-->
<!-- $Id$ -->
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java fo">
  <xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes"/>

  <!-- ========================= -->
  <!-- root element: projectteam -->
  <!-- ========================= -->
  <xsl:template match="/">
    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
      <fo:layout-master-set>
        <fo:simple-page-master master-name="simpleA4" 
        		page-height="11.69in" page-width="8.27in" margin-top="0.30in" margin-bottom="0.30in">
           
           <!-- Central part of page -->
	        <fo:region-body margin-top="0.35in"  margin-bottom="0.40in" />
	
	        <!-- Header -->
	        <fo:region-before extent="0.30in"/>

	        <!-- Footer -->
	        <fo:region-after extent="0.35in"/>
	        
        </fo:simple-page-master>
      </fo:layout-master-set>
      <fo:page-sequence master-reference="simpleA4">
	      
	      <!-- Define the contents of the header. -->
	      <fo:static-content flow-name="xsl-region-before">
<!-- 	        <fo:block font-size="8.0pt" font-family="serif" padding-after="2.0pt" -->
<!-- 	                  space-before="4.0pt" text-align="right" -->
<!-- 	                  border-bottom-style="solid" border-bottom-width="1.0pt"> -->
<!-- 	          	<fo:block> -->
<!-- 		          	Downloaded on: <xsl:value-of select="java:format(java:java.text.SimpleDateFormat.new('dd MMMMMMMMMMMM yyyy hh:mm:ss a'), java:java.util.Date.new())" /> 				  -->
<!-- 					<xsl:text>&#xA0;&#xA0;&#xA0;</xsl:text> -->
<!-- 				</fo:block> -->
<!-- 	        </fo:block> -->
				<fo:block/>
	      </fo:static-content>
	
	      <!-- Define the contents of the footer. -->
	      <fo:static-content flow-name="xsl-region-after">
	      	<fo:block start-indent="0.20in" font-size="8.0pt" font-family="sans-serif" font-style="italic" padding-after="2.0pt">
	      		<xsl:text>SECURE DOCUMENT:  Please follow all procedures for secure handling of test materials.</xsl:text>
	      	</fo:block>
	        <fo:block font-size="8.0pt" font-family="sans-serif" padding-after="2.0pt"
	                  space-before="4.0pt"  text-align="center"
	                  border-top-style="solid" border-bottom-width="1.0pt">
	                  <fo:block><xsl:text>Page </xsl:text><fo:page-number/></fo:block>
	        </fo:block> 
	      </fo:static-content>
	      
        <fo:flow flow-name="xsl-region-body">
        	<fo:block-container margin-top="15mm">
	          <fo:block font-size="12.0pt" font-family="serif">
	  				<xsl:choose>
	   					<xsl:when test="count(//map/entry/int) > 0"> 
	   					 	<xsl:call-template name="printAccessCodeLoop"/> 
						</xsl:when>
	   					<xsl:otherwise> 
	   					 	<fo:block wrap-option="wrap" start-indent="1.0in">No access codes available.</fo:block>
						</xsl:otherwise>
					</xsl:choose> 
	          </fo:block>
          </fo:block-container>
        </fo:flow>
        
      </fo:page-sequence>
    </fo:root>
  </xsl:template>
  <xsl:template name="printAccessCodeLoop">  
    <xsl:for-each select="//map/entry"> 
    	<fo:block page-break-after="always">
        	<fo:block start-indent="0.20in" margin-top="3mm">
            	Daily Access Codes are usable on: <xsl:value-of select="list/dailyAccessCode[1]/effectiveDate" />, from <xsl:value-of select="list/dailyAccessCode[1]/beginDate" /> to <xsl:value-of select="list/dailyAccessCode[1]/endDate" /> local time
          	</fo:block>
         	<fo:block start-indent="0.20in" margin-top="3mm">
            	<xsl:choose>
	   					<xsl:when test="list/dailyAccessCode[1]/assessmentProgramCode = 'CPASS'"> 
	   					  Course: <xsl:value-of select="list/dailyAccessCode[1]/contentAreaName"/>; Grade: <xsl:value-of select="list/dailyAccessCode[1]/gradeBandCode"/>	
						</xsl:when>
						<xsl:when test="list/dailyAccessCode[1]/assessmentProgramCode = 'PLTW'"> 
	   					  Course: <xsl:value-of select="list/dailyAccessCode[1]/contentAreaName"/>; Grade Band: <xsl:value-of select="list/dailyAccessCode[1]/gradeBandCode"/>	
						</xsl:when>
	   					<xsl:otherwise> 
	   					  Subject: <xsl:value-of select="list/dailyAccessCode[1]/contentAreaName"/>; Grade: <xsl:value-of select="list/dailyAccessCode[1]/gradeCode"/>
						</xsl:otherwise>
				</xsl:choose>             	
            	
          	</fo:block>
          	<fo:block start-indent="0.20in" margin-top="3mm">Access Codes:</fo:block>
          	<fo:block-container start-indent="0.20in" margin-top="1mm" text-align="start">
	          	<fo:table table-layout="fixed" width="40%" text-align="start" start-indent="0in">
			        <fo:table-column column-width="proportional-column-width(1)"/>
			        <fo:table-column column-width="proportional-column-width(1)"/>
			        <fo:table-body>
			        	<xsl:for-each select="list/dailyAccessCode"> 
				          	<fo:table-row>
					          	<fo:table-cell text-align="start" padding-left="15mm" padding-bottom="1mm"><fo:block><xsl:value-of select="stageName"/>:</fo:block></fo:table-cell>
					            <fo:table-cell text-align="start" padding-bottom="1mm"><fo:block><xsl:value-of select="accessCode"/></fo:block></fo:table-cell>
				          	</fo:table-row>
			          	</xsl:for-each>
			        </fo:table-body>
		      	</fo:table>
	      	</fo:block-container>
    	</fo:block>
	</xsl:for-each>
  </xsl:template>
</xsl:stylesheet>
