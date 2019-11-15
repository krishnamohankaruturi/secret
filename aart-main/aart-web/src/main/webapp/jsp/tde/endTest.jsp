<!doctype html>
<%@ page session="true"%>
<%@ include file="/jsp/tde/include.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://jawr.net/tags" prefix="jwr"%>
<%@ page import="java.util.Date" %>
<body>
<script src="/js/external/underscore.js"></script>
  
<jwr:script src="/bundles/global.js" />
<script src="/js/external/jquery.idletimer.js"></script>
<script src="/js/jquery.idletimeout.js"></script>

<jwr:script src="/bundles/testbundle.js" />
<jwr:script src="/bundles/testbundleext.js" />

<script type="text/javascript">
	$(document).ready(function() {
		parent.$(parent.document).trigger("pd.endtest.event");
	});
</script>
</body>