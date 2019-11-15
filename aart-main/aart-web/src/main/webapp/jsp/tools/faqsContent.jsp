<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<style>
#faqsContentHeader {
	color: #0e76bc;
	text-align: center;
}
.helpQuestion{
	padding: 1%;
}
.helpQuestion > a:hover{
	cursor: pointer;
}
#ViewHelpTopicWindow h2, #ViewHelpTopicWindow h3{
	color: #0e76bc;
	text-align: center;
}
#ViewHelpTopicWindow h3{
	text-align: left;
}
.accordionContent a{
	font-size: 1.0em;
}
.helpQuestion{
	list-style-type: disc;
}
.helpTopic{
	height:auto; 
	padding: 1%;
	font-family: helvetica;
    font-size: 12pt;
    margin-left: 20px;
}
.helpTopic>a{
	color:#000000;
	font-size: 14pt;
}
.faqDiscription{
	margin-bottom: 4%;
    margin-top: 4%;
   	font-family: helvetica;
   	font-size: 12pt;
   	font-color:#00000;
   	padding-left: 10px;
}
#helpContentBackLink{
	font-family: helvetica;
   	font-size: 12pt;
   	color: #000000;
   	text-decoration: none !important;
}
</style>
<input id="helpContenthelpImageUrl" type="hidden" value="${pageContext.request.contextPath}/images/quickHelp.png"></input>
<input id="helpContentHostUrl" type="hidden" value="${pageContext.request.contextPath}/"></input>
 <span id="helpTopicsContainer">
		<div id="faqDiscription" class="faqDiscription">
		<p>Welcome to the help content screen. This screen can be used to find answers to frequently asked questions. Choose a topic below.</p>
		</div>
 
		<table style="width:100%" id="helpTopicsTable" role='presentation'>
			<tr>
				<c:forEach items="${helpTopics}" var="helpTopic" varStatus="status">
						<td style="min-width:50%; vertical-align: top;padding: 2%;"> 
						<div class="helpTopic">
							<img alt='<c:out value="${helpTopic.name}"/>' src="images/help-icon.png">
							<a id="helpTopic${helpTopic.id}" data-id="${helpTopic.id}" class="helpTopicLinks" href="#helpTopicsContainer"><c:out value="${helpTopic.name}"/>
							</a>
							<div id="discription${helpTopic.id}" class="helpTopic"> <c:out value="${helpTopic.description}"/> </div>
						</div>
						</td>
						<c:if test="${(status.index%2)==1}">
					</tr>
					<tr>
					</c:if>
				</c:forEach>
				</tr>
		</table>
</span>
<div class="container" id="ViewHelpTopicWindow" style="display: none;">
	<a id="helpContentBackLink" href="#">&lt; back</a>
	<div id="fagsContainer">
	
	</div>
	<h2 id="viewHelpTopicName"></h2>
	<h3 id="viewHelpContentTitle"></h3>
	<p id="viewHelpContentText"></p>
	<a id="viewHelpContentFile" style="display:none;"></a>
</div>
<script>
	$(function() {
		$('#helpContentBackLink').on("click", function(){
			$('#ViewHelpTopicWindow').hide();
			$('#helpTopicsContainer').show();
		});
	});
</script>