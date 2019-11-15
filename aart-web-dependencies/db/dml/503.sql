--dml/503.sql
update specialcircumstance set description='Absent' where cedscode = 09999 and activeflag is true  and specialcircumstancetype='Absent';
update specialcircumstance set description='Absent' where cedscode = 3451 and activeflag is true  and specialcircumstancetype='Absent';
update specialcircumstance set description='Catastrophic Illness Or Accident' where cedscode = 13814 and activeflag is true;
update specialcircumstance set description='Fire Alarm' where cedscode = 13837 and activeflag is true;
update specialcircumstance set description='Homebound' where cedscode = 13824 and activeflag is true;
update specialcircumstance set description='Home Schooled For Assessed Subjects' where cedscode = 13815 and activeflag is true;
update specialcircumstance set description='Invalidation' where cedscode = 09999 and activeflag is true and specialcircumstancetype='Invalidation';
update specialcircumstance set description='Left Testing' where cedscode = 13832 and activeflag is true;
update specialcircumstance set description='Other - moved during testing' where cedscode = 09999 and activeflag is true and specialcircumstancetype='Other - moved during testing';
update specialcircumstance set description='Other Reason For Ineligibility' where cedscode = 13830 and activeflag is true;
update specialcircumstance set description='Other Reason For Nonparticipation' where cedscode = 13831 and activeflag is true;
update specialcircumstance set description='Refusal by parent' where cedscode = 03452 and activeflag is true;
update specialcircumstance set description='Refusal by student' where cedscode = 03452 and activeflag is true;
update specialcircumstance set description='Student Refusal' where cedscode = 13826 and activeflag is true;
update specialcircumstance set description='Teacher Cheating or Mis-admin' where cedscode = 13836 and activeflag is true;
update specialcircumstance set description='Student took this grade level assessment last year' where cedscode = 13816 and activeflag is true;

