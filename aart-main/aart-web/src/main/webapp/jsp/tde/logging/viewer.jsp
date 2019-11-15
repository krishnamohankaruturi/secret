<%@ include file="/jsp/include.jsp"%>
<!DOCTYPE html>
<html lang='en'>
	<head>
		<title>Local storage viewer example</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<%@ taglib uri="http://jawr.net/tags" prefix="jwr" %>

	</head>
	<body>
		<h1>Local Storage: log viewer</h1>

		<section>
			<div id="localstore-example">
				<p>
					<button onclick="demo.showAll()">
						Show All objects
					</button>
				</p>
				<div id="results-wrapper"></div>

				<div id='ourList'></div>
			</div>

		</section>
		<jwr:script src="/js/global.js" />
		<script>
			var demo = new function() {

			this.removeItem = function(key) {
			localStorage.removeItem(key);
			}

			this.showAll = function() {

			document.getElementById("ourList").innerHTML = "" ;

			var keyOrder = new Array();

			for (var i = 0, len = localStorage.length; i < len; i++) {
			key = localStorage.key(i);

			if ((/^log\./).test(key)) {
			keyOrder.push(key);
			}
			}

			keyOrder.sort(function(a, b) {return a.split(".")[2] - b.split(".")[2] });

			for (var i in keyOrder) {

			var key = keyOrder[i];

			var value = localStorage.getItem(key);

			var parts = key.split(".");

			var element = document.createElement("div");

			element.textContent =
			([new Date(parseInt(parts[2])).toLocaleString(),
			logger.levelname(parts[1]),
			value,
			""

			]).join(" ");

			document.getElementById("ourList").appendChild(element);
			}
			}
			}

			$(document).ready(function() {

				console.log("Is supported:", logger.is_supported());

				// This is to make us new entries.
				logger.trace("Some Trace Event example1");
				logger.debug("Some Debug Event");
				logger.info("Some Info Event");
				logger.warn("Some Warn Event");
				logger.error("Some Error Event");
				logger.fatal("Some Fatal Event");

			});

		</script>
	</body>
</html>