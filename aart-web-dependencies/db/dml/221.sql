--221.sql;
update assessmentprogramgrades set assessmentprogramid=(select distinct ap.id from assessmentprogram ap 
			where ap.programname='KAP')
	where assessmentprogramid=(select distinct ap.id from assessmentprogram ap 
					where ap.programname='Kansas Assessment Program');
					
update testtype set assessmentid=(select distinct a.id from assessment a inner join testingprogram tp on a.testingprogramid=tp.id
	inner join assessmentprogram ap on ap.id=tp.assessmentprogramid
	where ap.programname='KAP' and tp.programabbr='S' and assessmentcode='GL')
	where assessmentid=(select distinct a.id from assessment a inner join testingprogram tp on a.testingprogramid=tp.id
	inner join assessmentprogram ap on ap.id=tp.assessmentprogramid
	where ap.programname='Kansas Assessment Program' and tp.programabbr='S' and assessmentcode='GL');

update autoregistrationcriteria set assessmentid=(select distinct a.id from assessment a inner join testingprogram tp on a.testingprogramid=tp.id
	inner join assessmentprogram ap on ap.id=tp.assessmentprogramid
	where ap.programname='KAP' and tp.programabbr='S' and assessmentcode='GL')
	where assessmentid=(select distinct a.id from assessment a inner join testingprogram tp on a.testingprogramid=tp.id
	inner join assessmentprogram ap on ap.id=tp.assessmentprogramid
	where ap.programname='Kansas Assessment Program' and tp.programabbr='S' and assessmentcode='GL');

update autoregistrationcriteria set testingprogramid=(select distinct tp.id from testingprogram tp
	inner join assessmentprogram ap on ap.id=tp.assessmentprogramid
	where ap.programname='KAP' and tp.programabbr='S')
	where testingprogramid=(select distinct tp.id from testingprogram tp
	inner join assessmentprogram ap on ap.id=tp.assessmentprogramid
	where ap.programname='Kansas Assessment Program' and tp.programabbr='S');

update autoregistrationcriteria set assessmentprogramid=(select distinct ap.id from assessmentprogram ap 
	where ap.programname='KAP')
	where assessmentprogramid=(select distinct ap.id from assessmentprogram ap 
	where ap.programname='Kansas Assessment Program');