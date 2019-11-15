The KIDS import into Educator Portal has encountered an error in the ${step} step on ${dateStr} at ${timeStr}.

<#if env?has_content>
The error occurred in the ${env} environment.

</#if>
<#if dataIdentifiable>
The problematic record can be identified with the following:

	<#if recordType?has_content>
Record_Type = ${recordType}
	</#if>
	<#if recordId?has_content>
Record_Common_ID = ${recordId}
	</#if>

<#else>
Unfortunately, no additional data was available to aid in identification of the problematic record.

</#if>
<#if errorIdentifiable>
The error is as follows:

	<#if locationAvailable>
		<#if offendingClass?has_content>
Class that threw the exception: ${offendingClass}
		</#if>
		<#if offendingMethod?has_content>
Method that threw the exception: ${offendingMethod}
		</#if>
		<#if offendingLineNumber?has_content>
Line number: ${offendingLineNumber}
		</#if>
	</#if>
	<#if errorType?has_content>
Exception type: ${errorType}
	</#if>
	<#if errorMessage?has_content>
Exception message: ${errorMessage}

A complete stack trace is available in the logs.
	</#if>
	
</#if>
-Kite Support