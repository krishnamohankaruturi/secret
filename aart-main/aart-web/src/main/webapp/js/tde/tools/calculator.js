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

$(function(){
	
	//resetCalculator("0",$('.calcDisplay'));
	
	$('body').delegate('#calculator .num-button, #talkingCalculator .num-button', 'click', function() {
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
		
		if($(this).parent().attr('id') == 'talkingCalculator'){talk($(this));}	
	});
	
	$('body').delegate('#calculator .clear-button, #talkingCalculator .clear-button', 'click', function() {
		var calc = $(this).siblings('.calcDisplay');
		if(calc.data("isPendingFunction") == true) {
			$(this).siblings('.calcHistory').children('div:last').remove();
		}
		resetCalculator("0", calc);
		if($(this).parent().attr('id') == 'talkingCalculator'){talk($(this));}
	});
	
	$('body').delegate('#calculator .function-button, #talkingCalculator .function-button', 'click', function() {	
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
		
		//$(this).siblings('.calcHistory').text($(this).siblings('.calcHistory').text() + currVal + ' ' + pendingFunction+'  ');
		if($(this).siblings('.calcHistory').children(':last').is('br')){
			nodeAdd = $(this).siblings('.calcHistory').html() + '<div>' + currVal + ' ' + pendingFunction+ '&nbsp;</div>';
		} else {
			var textAdd = $(this).siblings('.calcHistory').children('div:last').text();
			$(this).siblings('.calcHistory').children('div:last').remove();
			nodeAdd = $(this).siblings('.calcHistory').html() + '<div>'+ textAdd + currVal + ' ' + pendingFunction+ '&nbsp;</div>';
		}
		
		$(this).siblings('.calcHistory').html('');
		$(this).siblings('.calcHistory').append(nodeAdd);
		
		
		$(this).siblings('.calcHistory').scrollTop($(this).siblings('.calcHistory')[0].scrollHeight);
		if($(this).parent().attr('id') == 'talkingCalculator'){
			talk($(this));
		}
	});
	
	$('body').delegate('#calculator .plusminus, #talkingCalculator .plusminus', 'click', function() {
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
		if($(this).parent().attr('id') == 'talkingCalculator'){
			talk($(this));
		}
	});
	
	$('body').delegate('#calculator .calc-back, #talkingCalculator .calc-back', 'click', function() {
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
		
		if($(this).parent().attr('id') == 'talkingCalculator'){talk($(this));}
	});
	
	
	$('body').delegate('#calculator .equals-button, #talkingCalculator .equals-button', 'click', function() {	
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
			
			//$(this).siblings('.calcHistory').text($(this).siblings('.calcHistory').text()+ calc.data("valueTwo") + '\n'+'                          =\n'+formattedFinalValue+'\n\n');
			
			var lastDivText = $(this).siblings('.calcHistory').children('div:last').text();
			$(this).siblings('.calcHistory').children(':last').remove();
			$(this).siblings('.calcHistory').append('<div>'+ lastDivText + calc.data("valueTwo") +'</div><div style="text-align:right;">=  '+finalValue+'</div><br\>');
			
			
			
			$(this).siblings('.calcHistory').scrollTop($(this).siblings('.calcHistory')[0].scrollHeight);
			
			
			calc.val(finalValue);
			resetCalculator(finalValue, calc);
			
			calc.data("fromPrevious", true);
						
		} else {
			// both numbers are not locked, do nothing.
		}
		
		if($(this).parent().attr('id') == 'talkingCalculator'){
			readDisplayNumber($(this));
		}
	});
	
	$('body').delegate('#calculator .bClearButton, #talkingCalculator .bClearButton', 'click', function() {
		$(this).siblings('.calcHistory').html('');
		if($(this).parent().attr('id')=='talkingCalculator'){talk($(this));}
		resetCalculator("0", $(this).siblings('.calcDisplay'));
	});
	
	function talk(e) {
		var media = $(e).text();
		if(media == '/') {
			media = 'divide';
		} else if(media == '<--') {
			media = 'backspace';
		} else if(media == '+/-') {
			media = 'opposite';
		}
		$("#calculatorAudio").jPlayer("clearMedia");
		if($.browser.mozilla) {
			$("#calculatorAudio").jPlayer("setMedia", {
				oga: contextPath+'/audio/calculator/' + media + '.ogg'
			});
		} else {
			$("#calculatorAudio").jPlayer("setMedia", {
				mp3: contextPath+'/audio/calculator/' + media + '.mp3'
			});
		}
		
		$("#calculatorAudio").jPlayer("play");
	}
	
	function readDisplayNumber(e) {
		var readList = new jPlayerPlaylist(
			{
				jPlayer: '#calculatorAudio'
			},
			[],
			{
				supplied: "oga,mp3",
				loop: false
			}
		);
		
		addToPlayList("=",readList);
		
		var number = $(e).siblings('.calcDisplay').val();
		if(number < 0) {
			addToPlayList('negative',readList);
			number = number.substring(1);
		}
		var regex = number.split('e+');
		number = regex[0];
		timespower = regex[1];
		
		var reg = number.split(".");
		var integerPart = reg[0];
		var decimalPart = reg[1];
		
		if(integerPart=='Infinity'){
			addToPlayList('Infinity',readList);
		} else if(integerPart!="" && integerPart!=0){
			readInteger(integerPart, readList);
		} else if(decimalPart!="") {
			addToPlayList(0,readList);
		}
		if(decimalPart) {
			addToPlayList('.',readList);
			readDecimal(decimalPart, readList);
		}
		if(timespower) {
			addToPlayList('e+',readList);
			readInteger(timespower, readList);
		}
		readList.play();
	}
	
	function readDecimal(number, list) {
		for(var i=0; i<number.length; i++){
			addToPlayList(number.substr(i,1),list);
		}
	}
	
	function readInteger(number, list) {
		for(var i=1000000000000000000000000; i>10; i=i/1000) {
			var q = Math.floor(number / i);
			if(q>0) {
				readNumberPart(q, i, list);
				if(number.length%3==0){
					number = number.substring(3);
				}else {
					number = number.substring(number.length%3);
				}
			}
			//number = number % i; //causes rounding of numbers when they are very large (order of 10^18+)
		}
		readNumberPart(number,"",list);
	}
	
	function readNumberPart(num, placeValue, list) {
		for(var i=100; i>1; i=i/10) { 
			var q = Math.floor(num / i);
			if(q>0 && i==100) {
				addToPlayList(q,list);
				addToPlayList(100,list);
				//addToPlayList('and',list);
			} else if(q>1 && i==10) {
				addToPlayList(q*10,list);
			}
			else if(q==1 && i==10){
				break;
			}
			num = num % i;
		}
		if(num>0){
			addToPlayList(num,list);
		}
		if(placeValue) {
			addToPlayList(placeValue,list);
		}
	}
	
	function addToPlayList(value, list) {
		if($.browser.mozilla) {
			list.add({
				title: "number",
				oga: contextPath + '/audio/calculator/'+value+'.ogg'
			});
		} else {
			list.add({
				title: "number",
				mp3: contextPath + '/audio/calculator/'+value+'.mp3'
			});
		}
		
	}
	
	//$('.calcDisplay').on('keypress',function(e){
	$('body').delegate('#calculator .calcDisplay, #talkingCalculator .calcDisplay', 'keypress', function(e) {
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
	});
	
	function clicked(elem, baseclass, button) {
		elem.siblings(baseclass).each(function(){
			if($(this).hasClass(button)){
				$(this).click();
			}
		});
	}
	//$('#talkingCalculator').on('click',function(){
	$('body').delegate('#talkingcalc', 'click', function() {
		$('#talkingCalculator .calcDisplay').focus();
		tool.getOnTop($('#talkingcalc'));
	});
	
	$('body').delegate('#calc', 'click', function() {
		$('#calculator .calcDisplay').focus();
		tool.getOnTop($('#calc'));
	});
	
});
