<!DOCTYPE HTML>
<html>
<head>
<style>
	pre{color:black}
	span.im{color:black !important}
</style>
</head>
<body>
<pre style="font-family: arial, sans-serif; color:black">Dear ${dataScorerObject.firstName} ${dataScorerObject.lastName},

This is a reminder to score ${stageName} tests for the listed students:

<b>Subject</b>: ${subject}, <b>${pathwayLabel}</b>: ${pathway}, <b>Test</b>: ${ccqtestname}

<b>Students</b>:

<#list dataStudentObject as student>${student} <br/></#list>


Regards,
${userObject.user.firstName} ${userObject.user.surName} </pre>
</body>
</html>