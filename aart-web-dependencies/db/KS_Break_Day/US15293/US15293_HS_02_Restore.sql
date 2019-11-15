-- US15293 set Highstakes to false for KAP 
update testingprogram set highstake = false where programname = 'Formative' and assessmentprogramid = (select id from assessmentprogram where programname = 'KAP');
