--702.sql dml

-- DE16544
UPDATE testtype SET testtypename ='Animal Systems Assessment' WHERE testtypecode ='B' AND testtypename ='cPass Animal Systems Assessment';
UPDATE testtype SET testtypename ='Comprehensive Agriculture Assessment' WHERE testtypecode ='A' AND testtypename ='cPass Comprehensive Agriculture Assessment';
UPDATE testtype SET testtypename ='Power, Structural, and Technical Systems Module' WHERE testtypecode ='AM' AND testtypename ='cPass Power, Structural, and Technical Systems Module';
UPDATE testtype SET testtypename ='Plant Systems Assessment' WHERE testtypecode ='D' AND testtypename ='cPass Plant Systems Assessment';
UPDATE testtype SET testtypename ='Horticulture Module' WHERE testtypecode ='DM' AND testtypename ='cPass Horticulture Module';
UPDATE testtype SET testtypename ='Drafting Assessment' WHERE testtypecode ='F'  AND testtypename ='cPass Drafting Assessment';
UPDATE testtype SET testtypename ='Comprehensive Business Assessment' WHERE testtypecode ='G'  AND testtypename ='cpass Comprehensive Business Assessment';
UPDATE testtype SET testtypename ='Finance Assessment' WHERE testtypecode ='H'  AND testtypename ='cPass Finance Assessment';
UPDATE testtype SET testtypename ='Accounting Module' WHERE testtypecode ='HM' AND testtypename ='cPass Accounting Module';
UPDATE testtype SET testtypename ='General CETE Assessment' WHERE testtypecode ='2' AND testtypename ='cPass General CETE Assessment';
UPDATE testtype SET testtypename ='Manufacturing Production Assessment' WHERE testtypecode ='E' AND testtypename ='cPass General CETE Assessment';

insert into lcs (lcsid)
select distinct lst.lcsid from lcsstudentstests lst
 left outer join lcs l on l.lcsid=lst.lcsid
 where l.lcsid is null;
 
