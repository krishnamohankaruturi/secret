--dml/500.sql

update specialcircumstance set description='Medical Waiver' where cedscode = 03454 and activeflag is true;
update specialcircumstance set description='Long-term Suspension - Non-Special Ed' where cedscode = 13807 and activeflag is true;
update specialcircumstance set description='Short-term Suspension - Non Special Ed' where cedscode = 13808 and activeflag is true;
update specialcircumstance set description='Suspension - Special Education' where cedscode = 13809 and activeflag is true;
update specialcircumstance set description='Truancy - Paperwork Filed' where cedscode = 13810 and activeflag is true;
update specialcircumstance set description='Truancy - No Paperwork Filed' where cedscode = 13811 and activeflag is true;
update specialcircumstance set description='Earlier Truancy' where cedscode = 13812 and activeflag is true;
update specialcircumstance set description='Chronic Absences' where cedscode = 13813 and activeflag is true;
update specialcircumstance set description='Catastrophic Illness Or Accident' where cedscode = 13814 and activeflag is true;
update specialcircumstance set description='Home Schooled For Assessed Subjects' where cedscode = 13815 and activeflag is true;
update specialcircumstance set description='Student Took This Grade Level Assessment Last Year' where cedscode = 13816 and activeflag is true;
update specialcircumstance set description='Incarcerated At Adult Facility' where cedscode = 13817 and activeflag is true;
update specialcircumstance set description='Special Treatment Center' where cedscode = 13818 and activeflag is true;
update specialcircumstance set description='Special Detention Center' where cedscode = 13819 and activeflag is true;
update specialcircumstance set description='Parent Refusal' where cedscode = 13820 and activeflag is true;
update specialcircumstance set description='Cheating' where cedscode = 13821 and activeflag is true;
update specialcircumstance set description='Psychological Factors of Emotional Trauma' where cedscode = 13822 and activeflag is true;
update specialcircumstance set description='Student Not Showing Adequate Effort' where cedscode = 13823 and activeflag is true;
update specialcircumstance set description='Homebound' where cedscode = 13824 and activeflag is true;
update specialcircumstance set description='Foreign Exchange Student' where cedscode = 13825 and activeflag is true;
update specialcircumstance set description='Student Refusal' where cedscode = 13826 and activeflag is true;
update specialcircumstance set description='Reading Passage Read To Student (Non-IEP)' where cedscode = 13827 and activeflag is true;
update specialcircumstance set description='Non-Special Ed Student Used Calculator on Non-Calculator Items' where cedscode = 13828 and activeflag is true;
update specialcircumstance set description='Student Used Math Journal (Non-IEP)' where cedscode = 13829 and activeflag is true;
update specialcircumstance set description='Other Reason For Ineligibility' where cedscode = 13830 and activeflag is true;
update specialcircumstance set description='Other Reason For Nonparticipation' where cedscode = 13831 and activeflag is true;
update specialcircumstance set description='Left Testing' where cedscode = 13832 and activeflag is true;
update specialcircumstance set description='Cross-Enrolled' where cedscode = 13833 and activeflag is true;
update specialcircumstance set description='Only For Writing' where cedscode = 13834 and activeflag is true;
update specialcircumstance set description='Administration Or System Failure' where cedscode = 13835 and activeflag is true;
update specialcircumstance set description='Teacher Cheating or Mis-admin' where cedscode = 13836 and activeflag is true;
update specialcircumstance set description='Fire Alarm' where cedscode = 13837 and activeflag is true;
update specialcircumstance set description='Absent' where cedscode = 09999 and activeflag is true  and specialcircumstancetype='Absent';
update specialcircumstance set description='Invalidation' where cedscode = 09999 and activeflag is true and specialcircumstancetype='Invalidation';
update specialcircumstance set description='Other' where cedscode = 09999 and activeflag is true and specialcircumstancetype='Other';

--SIF updates from ScriptBees team
update fieldspecification set mappedname = 'giftedTalented'
where fieldname='giftedStudent' and 
mappedname='SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/giftedStudent';

update fieldspecificationsrecordtypes set mappedname =  'giftedTalented'
where mappedname='SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/giftedStudent';

update fieldspecification set mappedname = 'disability/primaryDisability/code'
where fieldname='primaryDisabilityCode' and 
mappedname='SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/primaryDisabilityCode';

update fieldspecificationsrecordtypes set mappedname =  'disability/primaryDisability/code'
where mappedname='SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/primaryDisabilityCode';