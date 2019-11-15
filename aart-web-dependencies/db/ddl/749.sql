--749 DDL
--Changepond changes for I-Smart Features and for DE17564
Alter table testsectionstaskvariants add column responseoptionid bigint;
Alter table taskvariant add column taskdefinition varchar(250);
Alter table test add column testdefinition varchar(250); 
ALTER TABLE contentgroup ALTER COLUMN  htmlelementid TYPE text;