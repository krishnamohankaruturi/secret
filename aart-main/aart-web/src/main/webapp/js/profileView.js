$(function(){
	$('#defaultUserGroup').on('change', function(){
		changeDefaultOrganization($(this).val(), 'defaultUserOrganization');
	});
	$('#defaultUserOrganization').on('change', function(){
		changeDefaultAssessmentProgram($(this).val(), 'defaultUserAssessmentProgram');
	});
	
	$('#profile_menu > ul > li > ul > li a').on('click', function(e) {
		e.preventDefault();
		var hrf = $(this).attr('href');
		if (hrf == undefined) {
			return;
		}
		$('#profile_menu li ul li a').each(function() {
			$(this).parent().prop('class', '');
			var id = $(this).attr('href');
			if (id != undefined && id != "#")
				$(id).hide();
		});
		var cId = $(this).attr('href');		
		if( cId != "#"){
			$(this).parent().prop('class', 'current');
			
			if(cId == "#ednLink" )
			{
				$(cId).show();
			}
			if(cId == "#cpLink" )
			{
				$(cId).show();
			}
			if(cId == "#changeDefaultOrgAndRoleLink" )
			{
				$(cId).show();
			}
			if(cId == "#saLink" )
			{
				$(cId).show();
			}
			if(cId == "#reLink" )
			{
				$(cId).show();
			}
			if(cId == "#ovLink" )
			{
				$(cId).show();
			}
			
		}	
		e.preventDefault();
   	});
	
	$('.profileView .bcg_select').select2({
			placeholder:'Select', 
			multiple: false,
			allowClear : false
	});	
	
	$('#profile_menu > ul > li > a').on('click', function(e) {
				e.preventDefault();
				var exist = $(this).attr('href');
				if (exist == undefined)
					return;
				if (!$(this).closest('li').hasClass("current")) {
					if($("a.active").hasClass("active")){
						$("a.active").removeClass("active");
					}
					$(this).toggleClass("active");
					$(this).closest('li').toggleClass("current")
							.find('>ul').removeClass('hide');
					$(this).closest('li').toggleClass("current")
							.find('>ul').stop(true, true).toggleClass("show").end();
					$(this).closest('li').siblings("li").removeClass("current");
					$(this).closest('li').find("li").removeClass("current");
				}
				
		});
	$('#profile_menu ul ul > li > a').on('click', function(e) {
				e.preventDefault();
				var exist = $(this).attr('href');
				if (exist == undefined)
					return;
				if(!$(this).parent().parent().siblings("a").hasClass("active")){
					$('#profile_menu > ul > li > a').toggleClass("active");
				}
		});
});