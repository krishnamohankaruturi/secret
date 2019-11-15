<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:saxon="http://saxon.sf.net/"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java fo"
	extension-element-prefixes="saxon">

	<!-- Start Interim Summary Report Templates -->
	<xsl:template name="lastName-Header">
    	<xsl:variable name="lastNameString" select="reportDetails/data/legalLastName"></xsl:variable>
    	<xsl:variable name="firstNameString" select="reportDetails/data/legalFirstName"></xsl:variable>
    	<xsl:if test="string-length($firstNameString) &gt;= '15'">
    		<fo:inline><xsl:value-of select="substring($lastNameString,1,15)" /></fo:inline>
    	</xsl:if>
    	<xsl:if test="string-length($firstNameString) &lt; '15'">
    		<xsl:variable name="countFirstName" select="((15 - string-length($firstNameString)) + 15)" />
    		<fo:inline><xsl:value-of select="substring($lastNameString,1,$countFirstName)" /></fo:inline>
    	</xsl:if>
    </xsl:template>
	<xsl:template name="firstName-Header">
    	<xsl:variable name="firstNameString" select="reportDetails/data/legalFirstName"></xsl:variable>
    	<xsl:variable name="lastNameString" select="reportDetails/data/legalLastName"></xsl:variable>
    	<xsl:if test="string-length($lastNameString) &gt;= '15'">
    		<fo:inline><xsl:value-of select="substring($firstNameString,1,15)" /></fo:inline>
    	</xsl:if>
    	<xsl:if test="string-length($lastNameString) &lt; '15'">
    		<xsl:variable name="countLastName" select="((15 - string-length($lastNameString)) + 15)" />
    		<fo:inline><xsl:value-of select="substring($firstNameString,1,$countLastName)" /></fo:inline>
    	</xsl:if>
	</xsl:template>
	
	<!-- End Interim Summary Report Templates -->

	
</xsl:stylesheet>