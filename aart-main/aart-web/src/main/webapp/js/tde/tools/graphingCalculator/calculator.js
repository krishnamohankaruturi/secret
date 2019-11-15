/*
 * Calculator.
 */

function resetCalculator(curValue, elem) {
	$(elem).val(curValue);
	$(elem).siblings('.function-button').removeClass("pendingFunction");
	$(elem).data("isPendingFunction", false);
	$(elem).data("thePendingFunction", "");
	$(elem).data("valueOneLocked", false);
	$(elem).data("valueTwoLocked", false);
	$(elem).data("valueOne", curValue);
	$(elem).data("valueTwo", 0);
	$(elem).data("fromPrevious", false);
}

function graphEquation(text) {
	var ip = document.getElementById(fcs).getElementsByClassName("matheditor")[0];
	$(ip).mathquill('write',text);
	$(ip).trigger('keyup');
	//if(text!='^'){$(ip).focus();}
	//$(ip).focus();
	return;
}

$(function(){
	
	resetCalculator("0",$('.calcDisplay'));
	
	$('.num-button').click(function() {
		if(fcs!=false) {
			graphEquation($(this).text());
			return;
		}
		var calc = $(this).siblings('.calcDisplay');
		if (calc.data("fromPrevious") == true) {
			resetCalculator($(this).text(), calc);
			
		} else if ((calc.data("isPendingFunction") == true) && (calc.data("valueOneLocked") == false)) {
			calc.data("valueOne", calc.val());
			calc.data("valueOneLocked", true);
			
			calc.val($(this).text());
			calc.data("valueTwo", calc.val());
			calc.data("valueTwoLocked", true);
		
		// Clicking a number AGAIN, after first number locked and already value for second number	
		} else if ((calc.data("isPendingFunction") == true) && (calc.data("valueOneLocked") == true)) {
			var curValue = calc.val();
			if (curValue == "0") {
				curValue = "";
			}
			var toAdd = $(this).text();
			if(toAdd == '.'){
				if(curValue.match(/\./)) {
					return false;
				}
			}
		
			var newValue = curValue + toAdd;
			
			calc.val(newValue);
			
			calc.data("valueTwo", calc.val());
			calc.data("valueTwoLocked", true);
		
		// Clicking on a number fresh	
		} else {
			var curValue = calc.val();
			if (curValue == "0") {
				curValue = "";
			}
			var toAdd = $(this).text();
			if(toAdd == '.'){
				if(curValue.match(/\./)) {
					return false;
				}
			}
			
			var newValue = curValue + toAdd;
		
			calc.val(newValue);
		
		}
		
	});
	
	$('.clear-button').click(function() {
		var calc = $(this).siblings('.calcDisplay');
		resetCalculator("0", calc);
	});
	
	$('.function-button').click(function() {
		if(fcs!=false) {
			graphEquation($(this).text());
			return;
		}
		
		var calc = $(this).siblings('.calcDisplay');
		if(calc.data("isPendingFunction") && !calc.data("valueTwoLocked")) {
			return;
		}
		if (calc.data("fromPrevious") == true) {
			resetCalculator(calc.val(), calc);
			calc.data("valueOneLocked", false);
			calc.data("fromPrevious", false);
		}
		
		var currVal = calc.val();
		if (calc.data("isPendingFunction") == true) {
			if (calc.data("thePendingFunction") == "+") {
				var finalValue = parseFloat(calc.data("valueOne")) + parseFloat(calc.data("valueTwo"));
			} else if (calc.data("thePendingFunction") == "-") {
				var finalValue = parseFloat(calc.data("valueOne")) - parseFloat(calc.data("valueTwo"));
			} else if (calc.data("thePendingFunction") == "x") {
				var finalValue = parseFloat(calc.data("valueOne")) * parseFloat(calc.data("valueTwo"));
			} else if (calc.data("thePendingFunction") == "/") {
				var finalValue = parseFloat(calc.data("valueOne")) / parseFloat(calc.data("valueTwo"));
			}
			
			finalValue = parseFloat(finalValue.toFixed(10));
			
			calc.data("valueOne", finalValue);
			calc.data("valueOneLocked", false);
			calc.val(finalValue);
			calc.data("valueTwoLocked", false);
		}
		// Let it be known that a function has been selected
		var pendingFunction = $(this).text();
		calc.data("isPendingFunction", true);
		calc.data("thePendingFunction", pendingFunction);
		
		// Visually represent the current function
		$(".function-button").removeClass("pendingFunction");
		$(this).addClass("pendingFunction");
		
		
	});
	
	$('.xfunction-button').click(function() {
		if(fcs!=false) {
			graphEquation($(this).text());
			return;
		}
	});
	
	$('.plusminus').click(function() {
		var calc = $(this).siblings('.calcDisplay');
		if(calc.data("valueTwoLocked") == true) {
			calc.data("valueTwo", -calc.val());
			calc.val(-calc.val());
		} else if(calc.data("isPendingFunction") == true && calc.data("valueOneLocked") == false) {
			calc.data("valueOne", calc.val());
			calc.data("valueOneLocked", true);
			
			calc.val(-calc.val());
			calc.data("valueTwo", calc.val());
			calc.data("valueTwoLocked", true);
			console.log("khd");
		} else {
			resetCalculator(-calc.val(), calc);
		}
	});
	
	$('.calc-back').click(function() {
		var calc = $(this).siblings('.calcDisplay');
		if(calc.data("isPendingFunction") && !calc.data("valueOneLocked")) {
			return;
		}
		
		if(calc.val() == 'Infinity') {
			resetCalculator("0", calc);
			return;
		}
		if(calc.data("valueTwoLocked") == true) {
			var data = calc.val();

			if(data.length <= 1) {
				calc.data("valueTwo", "0");
				calc.val("0");
			}
			else {
				data = data.substring(0,data.length - 1);
				if(data == '-'){
					calc.data("valueTwo", "0");
					calc.val("0");
				}
				else{
					calc.data("valueTwo", data);
					calc.val(data);
				}
			}
		}
		else {
			var data = calc.val();

			if(data.length <= 1) {
				resetCalculator("0", calc);
			}
			else {
				data = data.substring(0,data.length - 1);
				if(data == '-'){resetCalculator("0", calc);}
				else{resetCalculator(data, calc);}
			}
		}
		
	});
		
	$('.equals-button').click(function() {
		if(fcs!=false) {
			graphEquation($(this).text());
			return;
		}
		var calc = $(this).siblings('.calcDisplay');
		var valTwo = calc.data("valueTwo");
		if ((calc.data("valueOneLocked") == true) && (calc.data("valueTwoLocked") == true)) {
			
			if (calc.data("thePendingFunction") == "+") {
				var finalValue = parseFloat(calc.data("valueOne")) + parseFloat(calc.data("valueTwo"));
			} else if (calc.data("thePendingFunction") == "-") {
				var finalValue = parseFloat(calc.data("valueOne")) - parseFloat(calc.data("valueTwo"));
			} else if (calc.data("thePendingFunction") == "x") {
				var finalValue = parseFloat(calc.data("valueOne")) * parseFloat(calc.data("valueTwo"));
			} else if (calc.data("thePendingFunction") == "/") {
				var finalValue = parseFloat(calc.data("valueOne")) / parseFloat(calc.data("valueTwo"));
			}
			
			finalValue = parseFloat(finalValue.toFixed(10));

			calc.val(finalValue);
			resetCalculator(finalValue, calc);
			
			calc.data("fromPrevious", true);
						
		} else {
			// both numbers are not locked, do nothing.
		}
		
	});
	
	//$('.calcDisplay').on('keypress',function(e){
	$('.calcDisplay').keypress(function(e) {
		var key = e.which || e.keyCode;
		switch(key){
		case 99:
			$(this).siblings('a.clear-button').click();
			break;	
		case 67:
			$(this).siblings('a.clear-button').click();
			break;
		case 8:
			$(this).siblings('a.calc-back').click();
			break;
		case 61:
			$(this).siblings('a.equals-button').click();
			break;
		case 13:
			$(this).siblings('a.equals-button').click();
			break;
		case 43:
			clicked($(this),'a.function-button','add');
			break;
		case 45:
			clicked($(this),'a.function-button','subtract');
			break;
		case 42:
			clicked($(this),'a.function-button','multiply');
			break;
		case 47:
			clicked($(this),'a.function-button','divide');
			break;
		case 46 :
			clicked($(this),'a.num-button','dot');
			break;
		case 48 :
			clicked($(this),'a.num-button','zero');
			break;
		case 49 :
			clicked($(this),'a.num-button','one');
			break;
		case 50 :
			clicked($(this),'a.num-button','two');
			break;
		case 51 :
			clicked($(this),'a.num-button','three');
			break;
		case 52 :
			clicked($(this),'a.num-button','four');
			break;
		case 53 :
			clicked($(this),'a.num-button','five');
			break;
		case 54 :
			clicked($(this),'a.num-button','six');
			break;
		case 55 :
			clicked($(this),'a.num-button','seven');
			break;
		case 56 :
			clicked($(this),'a.num-button','eight');
			break;
		case 57 :
			clicked($(this),'a.num-button','nine');
			break;
		}
		e.preventDefault();
	});
	
	function clicked(elem, baseclass, button) {
		elem.siblings(baseclass).each(function(){
			if($(this).hasClass(button)){
				console.log($(this));
				$(this).click();
			}
		});
	}
	
	$('#display').focus(function(){
		fcs=false;
	});
	
});
