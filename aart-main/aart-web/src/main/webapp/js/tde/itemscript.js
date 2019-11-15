var isAlternate = navigator.userAgent.match(/iPad/i) != null, lagTime = 1000;
$.fn.equalHeights = function(a) {
	$(this).each(function() {
		var b = 0;
		$(this).children().each(function(c) {
			if ($(this).height() > b) {
				b = $(this).height()
			}
		});
		if (!a || !Number.prototype.pxToEm) {
			b = b.pxToEm()
		}
		$(this).children().css({
			"min-height" : b
		});
		$(this).children().css({
			height : b
		})
	});
	return this
};
$.fn.equalWidths = function(a) {
	$(this).each(function() {
		var b = 0;
		$(this).children().each(function(c) {
			if ($(this).width() > b) {
				b = $(this).width()
			}
		});
		if (!a || !Number.prototype.pxToEm) {
			b = b.pxToEm()
		}
		if ($.browser.msie && $.browser.version == 6) {
			$(this).children().css({
				width : b
			})
		}
		$(this).children().css({
			"min-width" : b
		})
	});
	return this
};
Number.prototype.pxToEm = String.prototype.pxToEm = function(b) {
	b = jQuery.extend({
		scope : "body",
		reverse : false
	}, b);
	var e = (this == "") ? 0 : parseFloat(this);
	var d;
	var c = function() {
		var g = document.documentElement;
		return self.innerWidth || (g && g.clientWidth)
				|| document.body.clientWidth
	};
	if (b.scope == "body" && $.browser.msie
			&& (parseFloat($("body").css("font-size")) / c()).toFixed(1) > 0) {
		var f = function() {
			return (parseFloat($("body").css("font-size")) / c()).toFixed(3) * 16
		};
		d = f()
	} else {
		d = parseFloat(jQuery(b.scope).css("font-size"))
	}
	var a = (b.reverse == true) ? (e * d).toFixed(2) + "px" : (e / d)
			.toFixed(2)
			+ "em";
	return a
};
var operationalJS = (function() {
	var h = JSON
			.parse('{"stemContent":"<div><div contenteditable=\\"false\\"><p>\\n\\t<img src=\\"http://cb.cete.us/delegate/cbattachments/15504/stimulusAttachment/261/CA.IIA.3_199_TECH-04.png\\" alt=\\"CA.IIA.3_199_TECH\\" id=\\"img_292\\"></p>\\n</div><br></div>","rowList":["<div>One<br></div>","<div>Two<br></div>","<div>Three<br></div>"],"imagePanel":"<img class=\\"center\\" src=\\"http://cb.cete.us/delegate/cbattachments/15504/stimulusAttachment/302/CA.IIIB.2_244.gif\\" alt=\\"CA.IIIB.2_244\\" id=\\"img_333\\"><div id=\\"planetmap\\"><div style=\\"left: 170px; top: 35px; display: block; z-index: 1000; width: 170px; height: 39px;\\" class=\\"jq-droppable-zone minstyle\\"></div><div style=\\"left: 176px; top: 111px; display: block; z-index: 1000; width: 161px; height: 41px;\\" class=\\"jq-droppable-zone minstyle\\"></div><div style=\\"left: 573px; top: 109px; display: block; z-index: 1000; width: 199px; height: 45px;\\" class=\\"jq-droppable-zone minstyle\\"></div><div style=\\"left: 592px; top: 24px; display: block; z-index: 1000; width: 176px; height: 44px;\\" class=\\"jq-droppable-zone minstyle\\"></div></div>","id":"i1524966775"}'), a = jQuery("#i1524966775"), f = false, n = function(
			r) {
		var s = [];
		r.find(".labeling-left").each(function(v, x) {
			var y = $(x).attr("id");
			var u = $(x).attr("id").charAt($(x).attr("id").length - 1);
			var w = {
				id : y,
				index : u
			};
			s.push(w)
		});
		s.sort(function(w, u) {
			var v = w.index, x = u.index;
			if (v == x) {
				return 0
			}
			return v > x ? 1 : -1
		});
		var t = [];
		$.each(s, function(u, v) {
			t.push($("#" + v.id).clone())
		});
		r.html("");
		$.each(t, function(u, v) {
			r.append($(v))
		})
	}, e = {
		revert : "invalid",
		opacity : 0.8,
		containment : "document",
		cursor : "move",
		snap : true,
		addClasses : false,
		scroll : true,
		zIndex : 999999
	};
	function b() {
		if (isAlternate) {
			q();
			$(".minstyle", a).droppable(
					{
						accept : ".labeling-left",
						greedy : true,
						drop : function(r, s) {
							if (!($(this).html().trim() == "")) {
								$(".jq-labeling-zone .row-fluid", a).append(
										$(this).find(".relative").clone().css({
											left : "",
											opacity : "",
											top : ""
										}).removeClass("relative").addClass(
												"span3"));
								$(this).find(".relative").remove();
								$(this).append(
										s.draggable.remove().clone().css({
											left : "",
											opacity : "",
											top : ""
										}).removeClass("span3").addClass(
												"relative").draggable(e));
								n($(".jq-labeling-zone .row-fluid", a));
								q()
							} else {
								$(this).append(
										s.draggable.remove().clone().css({
											left : "",
											opacity : "",
											top : ""
										}).removeClass("span3").addClass(
												"relative").draggable(e))
							}
						}
					});
			$(".jq-labeling-zone .row-fluid", a).droppable({
				accept : ".labeling-left",
				greedy : true,
				drop : function(r, s) {
					$(this).append(s.draggable.remove().clone().css({
						left : "",
						opacity : "",
						top : ""
					}).addClass("span3").removeClass("relative").draggable(e));
					n($(this));
					q()
				}
			})
		} else {
			c();
			$(".jq-labeling-zone .row-fluid", a).bind("dragover", function(r) {
				k(r.originalEvent)
			}).bind("drop", function(r) {
				m(r.originalEvent)
			});
			$(".minstyle", a).bind("dragover", function(r) {
				k(r.originalEvent)
			}).bind("drop", function(r) {
				d(r.originalEvent)
			})
		}
	}
	function q() {
		$(".labeling-left", a).draggable(e)
	}
	function c() {
		$(".labeling-left", a).bind("dragstart", function(r) {
			j(r.originalEvent)
		}).bind("dragleave", function(r) {
			if ($(this).parent().hasClass("minstyle")) {
				f = true
			}
		}).bind("dragend", function(r) {
			if (f) {
				m(r.originalEvent, "dragend")
			}
			return false
		})
	}
	function k(r) {
		r.preventDefault()
	}
	function j(s) {
		var r = s.target.id;
		if ($("#" + r).is("IMG")) {
			r = $("#" + r).closest(".labeling-left").attr("id")
		}
		s.dataTransfer.setData("Text", r)
	}
	function d(s) {
		s.preventDefault();
		var u = s.dataTransfer.getData("Text");
		if (s.target.innerHTML == "") {
			$(s.target).append(
					$("#" + u).clone().removeClass("span3")
							.addClass("relative"));
			$("#" + u).remove();
			c()
		} else {
			var r = $(s.target).closest(".minstyle");
			var t = r.find(".relative").attr("id");
			m(s, "dragend", t);
			r.append($("#" + u).clone().removeClass("span3").addClass(
					"relative"));
			$("#" + u).remove();
			c()
		}
	}
	function m(s, v, u) {
		s.preventDefault();
		var t = s.dataTransfer.getData("Text");
		if (u) {
			t = u
		}
		if (!$("#" + t).hasClass("relative")) {
			return
		}
		var r = $(s.target);
		if (!r.hasClass("row-fluid")) {
			r = r.parent();
			if (r.parent().hasClass("row-fluid")) {
				r = r.parent()
			}
		}
		if (v && "dragend" === v) {
			r = $(".jq-labeling-zone .row-fluid", a)
		}
		r.append($("#" + t).clone().addClass("span3").removeClass("relative"));
		n(r);
		$("#" + t, ".minstyle").remove();
		f = false;
		c()
	}
	function p(r) {
		if (null != r && undefined != r) {
			$("#imagePanel", a).html(r[0]);
			$.each($("#imagePanel", a).find(".labeling-left"), function(s, t) {
				$("#" + $(t).attr("id"), ".jq-labeling-zone .row-fluid")
						.remove()
			})
		}
	}
	function l() {
		var s = [];
		s.push($("#imagePanel", a).html());
		var r = {
			operationalItem : {
				id : "",
				answer : s
			}
		};
		return JSON.stringify(r)
	}
	function i() {
		setTimeout(
				function() {
					$(".jq-labeling-zone .row-fluid", a).equalHeights();
					$("#imagePanel #planetmap", a).equalHeights();
					var s = [
							$(".jq-labeling-zone .row-fluid", a).children(
									":first").height(),
							$("#imagePanel #planetmap", a).children(":first")
									.height() ];
					var r = Math.max.apply(Math, s);
					$(
							".jq-labeling-zone .row-fluid .labeling-left,#imagePanel #planetmap .minstyle")
							.css({
								height : r
							})
				}, 10)
	}
	function g(r) {
		a = jQuery("#i1524966775");
		jQuery(".jq-stem", a).html(h.stemContent);
		$(".jq-labeling-zone .row-fluid", a).html("");
		$(h.rowList).each(
				function(s, t) {
					$(".jq-labeling-zone .row-fluid", a).append(
							'<div class="labeling-left span3" draggable="true" id="i1524966775'
									+ s + '">' + t + "</div>")
				});
		$("#imagePanel", a).html(h.imagePanel);
		i();
		if (r) {
			p(r)
		}
		b()
	}
	function o() {
		g()
	}
	return {
		resetItem : o,
		scoreItem : l,
		initItem : g
	}
})();