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
	        <fo:region-body column-count="2" column-gap="0.0in" margin-top="0.30in"  margin-bottom="0.30in" />
	
	        <!-- Header -->
	        <fo:region-before extent="0.25in"/>

	        <!-- Footer -->
	        <fo:region-after extent="0.25in"/>
	        
        </fo:simple-page-master>
      </fo:layout-master-set>
      <fo:page-sequence master-reference="simpleA4">
	      
	      <!-- Define the contents of the header. -->
	      <fo:static-content flow-name="xsl-region-before">
	        <fo:block font-size="8.0pt" font-family="serif" padding-after="2.0pt"
	                  space-before="4.0pt" text-align="right"
	                  border-bottom-style="solid" border-bottom-width="1.0pt">
	          	
	          	<xsl:value-of select="java:format(java:java.text.SimpleDateFormat.new('dd MMMMMMMMMMMM yyyy hh:mm:ss a'), java:java.util.Date.new())" /> 				 
				<xsl:text>&#xA0;&#xA0;&#xA0;</xsl:text>
	        </fo:block>
	      </fo:static-content>
	
	      <!-- Define the contents of the footer. -->
	      <fo:static-content flow-name="xsl-region-after">
	        <fo:block font-size="8.0pt" font-family="sans-serif" padding-after="2.0pt"
	                  space-before="4.0pt" text-align="center"
	                  border-top-style="solid" border-bottom-width="1.0pt">
	                  <xsl:text>Page </xsl:text>
	                  <fo:page-number/>
	        </fo:block> 
	      </fo:static-content>
	      
        <fo:flow flow-name="xsl-region-body">
          <fo:block font-size="9.0pt" font-family="serif" border-right-style="dashed" border-right-width="1.5pt">  
	        	<xsl:for-each select="/tsPdfDTO[position()=1]"> 
   					<xsl:variable name="stateStudentIdentifierTest" select="stateStudentIdentifier"/>
   					<xsl:choose>
	   					<xsl:when test="$stateStudentIdentifierTest"> 
	   					 	<xsl:call-template name="printStudentLoop"/> 
						</xsl:when>
	   					<xsl:otherwise> 
	   					 	<fo:block wrap-option="wrap" start-indent="1.0in">
				              All student tests are completed or not available.
				          </fo:block>
						 </xsl:otherwise>
					</xsl:choose>
	   			</xsl:for-each>		 
          </fo:block>
        </fo:flow>
        
      </fo:page-sequence>
    </fo:root>
  </xsl:template>
  <xsl:template name="printStudentLoop">  
      <xsl:for-each select="/tsPdfDTO"> 
	       	<xsl:variable name="assessmentProgramName" select="substring(programName,1,40)"/>
		    <xsl:variable name="rosterName" select="substring(rosterName,1,25)"/> 
	      	<xsl:variable name="stateStudentIdentifier" select="substring(stateStudentIdentifier,1,50)"/>
	      	<xsl:variable name="studentFirstName" select="substring(legalFirstName,1,30)"/>
	      	<xsl:variable name="studentLastName" select="substring(legalLastName,1,35)"/> 
	      	<xsl:variable name="studentUserName" select="substring(username,1,100)"/>
	      	<xsl:variable name="studentPassword" select="substring(password,1,35)"/> 
	      	<xsl:variable name="groupingindicator1" select="substring(groupingIndicator1,1,50)"/>
	      	<xsl:variable name="groupingindicator2" select="substring(groupingIndicator2,1,50)"/>
	        <xsl:variable name="subjectName" select="substring(subjectName,1,50)"/>
	        <xsl:variable name="assessmentProgramCode" select="substring(abbreviatedName,1,50)"/>
 				<fo:block-container height="2.93in" space-before="32.0pt" overflow="hidden"> 
			      <fo:block padding-bottom="80.0pt" border-bottom-style="dashed" border-bottom-width="2.0pt">
 			       	  <fo:block font-weight="bold" wrap-option="wrap" end-indent="0.25in" text-align="right">
        				<xsl:value-of select="$assessmentProgramName"/>
			          </fo:block>
			          	<fo:block-container height="10pt">
			      	  <fo:block font-weight="bold" wrap-option="wrap" end-indent="0.25in" text-align="right" margin-top="5pt" >  			          
			              <xsl:choose>
			              <xsl:when test="$rosterName != ''">
							 <xsl:value-of select="$rosterName"/>
						  </xsl:when>
					    <xsl:otherwise>				      
					      <xsl:text>&#xa0;</xsl:text>				    
					    </xsl:otherwise>
					   </xsl:choose>	 
			          </fo:block>
			         	      	 </fo:block-container>
			          <fo:block padding-bottom="18.0pt"> 
			          </fo:block>
			          <fo:block wrap-option="wrap" start-indent="0.20in" margin-top="10pt">
			              State ID: <xsl:value-of select="$stateStudentIdentifier"/>
			          </fo:block>
			          
			          <xsl:if test="not($assessmentProgramCode = 'PLTW' or $assessmentProgramCode = 'DLM' or $assessmentProgramCode = 'I-SMART' or $assessmentProgramCode = 'I-SMART2')">
					  <fo:block-container height="11.5pt">
					  <fo:block wrap-option="wrap" start-indent="0.20in">
			             Grouping 1: <xsl:value-of select="$groupingindicator1"/>
			          </fo:block>
			          </fo:block-container>
			          <fo:block-container height="11.5pt">
			          <fo:block wrap-option="wrap" start-indent="0.20in">
			              Grouping 2: <xsl:value-of select="$groupingindicator2"/>
			          </fo:block>
			          </fo:block-container>
      				  </xsl:if>
			          
			          
			          <fo:block wrap-option="wrap" start-indent="0.20in">
			              Name: <xsl:value-of select="$studentLastName"/>, <xsl:value-of select="$studentFirstName"/>
			          </fo:block>
			         <fo:block-container height="12pt">
			          <fo:block start-indent="0.20in">
			              UserName: 
			              <xsl:choose>
			              <xsl:when test="$studentUserName != ''">
							 <fo:inline font-family="Verdana" font-weight="bold"><xsl:value-of select="$studentUserName"/></fo:inline>
						  </xsl:when>
					    <xsl:otherwise>				      
					      <fo:inline font-family="Verdana" font-weight="bold">
					      <xsl:text>&#xa0;</xsl:text>				    
					      </fo:inline>
					    </xsl:otherwise>
					   </xsl:choose>   
			            </fo:block>
			            </fo:block-container>
			            <fo:block-container height="12pt">
			          <fo:block start-indent="0.20in">
			              Password:
			              <xsl:choose>
			              <xsl:when test="$studentPassword != ''">
							 <fo:inline font-family="Verdana" font-weight="bold"><xsl:value-of select="$studentPassword"/></fo:inline>
						  </xsl:when>
					    <xsl:otherwise>				      
					      <fo:inline font-family="Verdana" font-weight="bold">
					      <xsl:text>&#xa0;</xsl:text>				    
					      </fo:inline>
					    </xsl:otherwise>
					   </xsl:choose>    
			          </fo:block>
			          </fo:block-container>
			          <fo:block-container height="12pt">
			          <fo:block start-indent="0.20in">
			          <xsl:if test="$assessmentProgramCode = 'PLTW'">
			          Course:</xsl:if>
			          <xsl:if test="not($assessmentProgramCode = 'PLTW')">
			          Subject:</xsl:if>
			              <xsl:choose>
			              <xsl:when test="$subjectName != ''">
							 <fo:inline font-family="Verdana" font-weight="bold">
			              <xsl:value-of select="$subjectName"/></fo:inline>
						  </xsl:when>
					    <xsl:otherwise>				      
					      <fo:inline font-family="Verdana" font-weight="bold">
					      <xsl:text>&#xa0;</xsl:text>				    
					      </fo:inline>
					    </xsl:otherwise>
					   </xsl:choose>
			              
			          </fo:block>
			          </fo:block-container>
			           <xsl:if test="($assessmentProgramCode = 'PLTW' or $assessmentProgramCode = 'DLM' or $assessmentProgramCode = 'I-SMART' or $assessmentProgramCode = 'I-SMART2')">
			              <fo:block-container height="23pt">
				          <fo:block start-indent="0.20in">				           
				           <xsl:text>&#xa0;</xsl:text>    
				           </fo:block>    
				         </fo:block-container>     
         				 </xsl:if>
         				 
			      </fo:block>  
			</fo:block-container> 
		</xsl:for-each> 
  </xsl:template>
</xsl:stylesheet>
