-- 475.sql DML

update fieldspecification set allowablevalues = '{'''',2,3,A,B,D,E,F,G,GN,H,P,2Q,AQ,AM,BQ,DQ,DM,EM,FQ,GQ,HQ,HM}' where fieldname = 'testType';

--for TASC
update fieldspecification set showerror = false where fieldname in ('studentLegalFirstName', 'studentLegalLastName', 'studentLegalMiddleName');


