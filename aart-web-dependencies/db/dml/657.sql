--DE15726
update fieldspecification set rejectifinvalid=true where fieldname='teacherID';

--- DE15696
update fieldspecification set allowablevalues = '' where mappedname = 'Subject' and fieldname='subject';
update fieldspecification set rejectifempty = false where mappedname = 'Subject' and fieldname='subject';

---DE15697
update category set categoryname = 'State Aggregate'  where categorycode = 'ALT_SS';
update category set categoryname = 'District Aggregate'  where categorycode = 'ALT_DS';
update category set categoryname = 'School Aggregate'  where categorycode = 'ALT_SCHOOL';
update category set categoryname = 'School Aggregate &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(Bundled)'  where categorycode = 'ALT_SCH_SUM_ALL';
update category set categoryname = 'Class Aggregate'  where categorycode = 'ALT_CLASS_ROOM';