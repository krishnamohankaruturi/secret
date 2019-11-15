<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/jsp/include.jsp" %>


		<h4 align="center"><fmt:message key="label.pd.home.myinfo" /></h4>
		<div> <fmt:message key="label.pd.home.Name" />: ${name} </div>  
		<div> <fmt:message key="label.pd.home.State" />: ${state} </div>
		<div> <fmt:message key="label.pd.home.District" />: ${district} </div>
		<div> <fmt:message key="label.pd.home.School" />: ${school} </div>
		<div> <fmt:message key="label.pd.home.id" />: ${studentid} </div> 