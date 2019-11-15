Select * from assessmentprogram where originationcode <> 'CB';

update assessmentprogram set programname = 'CPASS2' where programname='CPASS' AND originationcode <> 'CB';

update assessmentprogram set programname = 'Dynamic Learning Maps2' where programname='Dynamic Learning Maps' AND originationcode <> 'CB';

update assessmentprogram set programname = 'Utah Alternate Assessment2' where programname='Utah Alternate Assessment' and originationcode <> 'CB';

update assessmentprogram set programname = 'Example2' where programname='Example' AND originationcode <> 'CB';
	

	
