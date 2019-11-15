<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<jsp:text>
		<![CDATA[ <?xml version="1.0" encoding="UTF-8" ?> ]]>
	</jsp:text>
	<jsp:text>
		<![CDATA[ <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> ]]>
	</jsp:text>
	<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<div id="ifrm_section"></div>
	<script>
		/**
		 * Load the given css file dynamically.
		 * @param file
		 */
		var loadCss = function(file) {
			$('<link/>', {
				rel : 'stylesheet',
				type : 'text/css',
				href : file
			}).appendTo('head');
		}
		/**
		 * Remove the css file added for innovative items.
		 */
		var removeCss = function(url) {
			if (url) {
				$('link').each(function() {
					if ($(this).attr('href').match(url)) {
						$(this).remove();
					}
				});
			} else {
				$('link').each(function() {
					if ($(this).attr('href').match('css/itemStyles.css')) {
						$(this).remove();
					}
				});
			}
		}
		$.getScript('${MEDIA_PATH}${packagePath}/js/itemScript.js', function() {
			loadCss('${MEDIA_PATH}${packagePath}/css/itemStyles.css');
			$('#ifrm_section').load(
					'${MEDIA_PATH}${packagePath}/index.html',
					function(responseText, statusText, xhr) {
						if (statusText == "success") {
							console.log(responseText);
							console.log(statusText);
							console.log(xhr);
							operationalJS.initItem();
						} else if (statusText == "error") {
							console.log("An error occurred: " + xhr.status
									+ " - " + xhr.statusText);
							removeCss();
						}
					});
		});
		/**
		 * Load the given css file dynamically.
		 * @param file
		 */
		var loadCss = function(file) {
			$('<link/>', {
				rel : 'stylesheet',
				type : 'text/css',
				href : file
			}).appendTo('head');
		}
		/**
		 * Remove the css file added for innovative items.
		 */
		var removeCss = function(url) {
			if (url) {
				$('link').each(function() {
					if ($(this).attr('href').match(url)) {
						$(this).remove();
					}
				});
			} else {
				$('link').each(function() {
					if ($(this).attr('href').match('css/itemStyles.css')) {
						$(this).remove();
					}
				});
			}
		}
		$.getScript('${MEDIA_PATH}${packagePath}/js/itemScript.js', function() {
			loadCss('${MEDIA_PATH}${packagePath}/css/itemStyles.css');
			$('#ifrm_section').load(
					'${MEDIA_PATH}${packagePath}/index.html',
					function(responseText, statusText, xhr) {
						if (statusText == "success") {
							console.log(responseText);
							console.log(statusText);
							console.log(xhr);
							operationalJS.initItem();
						} else if (statusText == "error") {
							console.log("An error occurred: " + xhr.status
									+ " - " + xhr.statusText);
							removeCss();
						}
					});
		});
	</script>
</body>
	</html>
</jsp:root>
