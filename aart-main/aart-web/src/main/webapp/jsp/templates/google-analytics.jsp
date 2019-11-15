<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<c:if test="${applicationScope['enable.google.analytics']}">
	<%-- Global site tag (gtag.js) - Google Analytics --%>
	<script async src="https://www.googletagmanager.com/gtag/js?id=${applicationScope['google.analytics.account']}"></script>
	<script>
		window.dataLayer = window.dataLayer || [];
		function gtag(){dataLayer.push(arguments);}
		gtag('js', new Date());
		gtag('config', "${applicationScope['google.analytics.account']}");
	</script>
</c:if>
