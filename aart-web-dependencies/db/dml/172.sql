UPDATE ksdexmlaudit SET id=nextval('ksdexmlaudit_id_seq'), processedcode='COMPLETED', processeddate= current_timestamp where id is null;

update fieldspecification set allowablevalues='{'''',1, 2, 6, 7, 8, D, A, X, R, F, S, P, T, G, U, H, L, 3, C, N}'
where fieldname='hsStateLifeScienceAssess';
UPDATE fieldspecification
 SET rejectifempty=TRUE, 
	rejectifinvalid=TRUE, 
	formatregex='^(01|51|81|02|52|82|80|03|53|83|04|54|84).*'
 WHERE fieldname = 'stateSubjectCourseIdentifier';