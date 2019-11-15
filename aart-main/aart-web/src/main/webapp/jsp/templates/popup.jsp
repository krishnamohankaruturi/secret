<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- TODO change to jsp/include.jsp -->
<%@ include file="/jsp/include.jsp" %>
<!DOCTYPE html>

<html lang='en'>
    <head>
        <meta charset="UTF-8" />
        <title><fmt:message key="label.common.title"/></title>
        <link rel="stylesheet" href="<c:url value='/css/external/jquery-ui-1.8.19.custom.min.css'/>" type="text/css" />        
        <!-- <link rel="stylesheet" href="<c:url value='/css/screen.css'/>" type="text/css" /> --><%-- 
        <link rel="stylesheet" href="<c:url value='/css/external/ui.jqgrid.css'/>" type="text/css"/>  --%>       
        <link rel="stylesheet" href="<c:url value='/css/theme/recordBrowser.css'/>" type="text/css" />     
        <!-- <link rel="stylesheet" href="<c:url value='/css/bcg-jqGrid.css'/>" type="text/css" />  -->
        <link rel="stylesheet" href="<c:url value='/css/external/ui.multiselect.css'/>" type="text/css"/>         
        <link rel="stylesheet" href="<c:url value='/css/external/multi-select.css'/>" type="text/css"/>     
        <link rel="stylesheet" href="<c:url value='/css/custom.css'/>" type="text/css" />        
        <link rel="stylesheet" href="<c:url value='/css/external/jstree.css'/>" type="text/css" />                
        <link rel="stylesheet" href="<c:url value='/css/screen.css'/>" type="text/css" />
        <link rel="stylesheet" href="<c:url value='/css/bcg-jqGrid.css'/>" type="text/css" />
         <link rel="stylesheet" href="<c:url value='/css/bcg-jqGrid.css'/>" type="text/css" />      
        
        <script type="text/javascript" src="<c:url value='/js/external/jquery-1.7.2.min.js'/>"> </script>
        <script type="text/javascript" src="<c:url value='/js/external/jquery-ui-1.8.19.custom.min.js'/>"> </script>
        <!-- <script type="text/javascript" src="<c:url value='/js/external/combobox.min.js'/>"> </script> -->
        <script type="text/javascript" src="<c:url value='/js/external/i18n/grid.locale-en.js'/>"> </script>
        <script type="text/javascript" src="<c:url value='/js/external/jquery.jqGrid.min.js'/>"> </script>
        <script type="text/javascript" src="<c:url value='/js/external/jquery.quicksearch.min.js'/>"> </script>
        <script type="text/javascript" src="<c:url value='/js/external/jquery.multi-select.min.js'/>"> </script>
        <script type="text/javascript" src="<c:url value='/js/jquery.multiselect.js'/>"> </script>
        <script type="text/javascript" src="<c:url value='/js/external/application.min.js'/>"> </script>
        <script type="text/javascript" src="<c:url value='/js/external/jquery.jstree.js'/>"> </script>
        <script type="text/javascript" src="<c:url value='/js/aart.js'/>"> </script>
        <script type="text/javascript" src="<c:url value='/js/ui.multiselect.js'/>"> </script>
        <script type="text/javascript" src="<c:url value='/js/recordbrowser.js'/>"> </script>
        <script type="text/javascript" src="<c:url value='/js/external/jquery.validate.js'/>"> </script>  
         <script type="text/javascript" src="<c:url value='/js/saveRecordBrowserState.js'/>"> </script> 
         <script type="text/javascript" src="<c:url value='/js/custom.orgFilter.js'/>"> </script>  
    </head>
    <body> 
	        <div>
	             <tiles:insertAttribute name="content"/>
	        </div>  
    </body>
</html>