<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<script type="text/javascript" src="<c:url value='/js/external/jquery-1.7.2.min.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/external/jquery-ui-1.8.19.custom.min.js'/>"> </script>
<tiles:insertAttribute name="content"/>
