
--US14846 add esol fields to webservice consumer
--put on the wrong table initially
alter table enrollment drop column usaentrydate;
alter table enrollment drop column firstlanguage;
alter table enrollment drop column esolparticipationcode;
alter table enrollment drop column esolprogramendingdate;
alter table enrollment drop column esolprogramentrydate;  

alter table student add column usaentrydate timestamp with time zone;
alter table student add column esolparticipationcode character varying(1);
alter table student add column esolprogramendingdate timestamp with time zone;
alter table student add column esolprogramentrydate timestamp with time zone;  