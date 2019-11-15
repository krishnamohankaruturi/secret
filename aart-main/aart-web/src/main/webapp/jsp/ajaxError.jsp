<%@page contentType="application/json;charset=UTF-8" isErrorPage="true"%>

<%--Do not any other code in this jsp --%>
<%
    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
%>
${exceptionMessage}
