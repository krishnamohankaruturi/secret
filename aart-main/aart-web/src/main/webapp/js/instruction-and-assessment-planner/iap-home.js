$(function(){
	var dropdownCustomization = {
		'districtSelect': {
			multiple: false,
			width: '260px'
		},
		'schoolSelect': {
			multiple: false,
			width: '260px'
		},
		'gradeSelect': {
			multiple: true,
			width: '175px'
		},
		'studentSelect': {
			multiple: true,
			width: '200px'
		},
		'teacherSelect': {
			multiple: true,
			width: '185px'
		}
	};
	$('.form select.dropdown').each(function(){
		$(this).select2({
			placeholder: 'Select',
			allowClear: true,
			multiple: dropdownCustomization[$(this).attr('id')].multiple,
			width: dropdownCustomization[$(this).attr('id')].width
		});
	});
});