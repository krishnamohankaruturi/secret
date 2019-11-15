-- US15293 Update Highstakes to true for KAP 
update testingprogram set highstake = true where programname = 'Formative' and assessmentprogramid = (select id from assessmentprogram where programname = 'KAP');
