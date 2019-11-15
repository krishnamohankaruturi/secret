-- --DML for: F517 Implement ITI for Science
INSERT INTO itilinkagelevelmapping(actuallinkagelevel, mappinglinkagelevelname, contentareaabbreviatedname) values('Initial Precursor', 'Initial', 'Sci');
INSERT INTO itilinkagelevelmapping(actuallinkagelevel, mappinglinkagelevelname, contentareaabbreviatedname) values('Distal Precursor', 'Initial', 'Sci');
INSERT INTO itilinkagelevelmapping(actuallinkagelevel, mappinglinkagelevelname, contentareaabbreviatedname) values('Proximal Precursor', 'Precursor', 'Sci');
INSERT INTO itilinkagelevelmapping(actuallinkagelevel, mappinglinkagelevelname, contentareaabbreviatedname) values('Target', 'Target', 'Sci');
INSERT INTO itilinkagelevelmapping(actuallinkagelevel, mappinglinkagelevelname, contentareaabbreviatedname) values('Successor', 'Target', 'Sci');


INSERT INTO itilinkagelevelmapping(actuallinkagelevel, mappinglinkagelevelname, contentareaabbreviatedname) values('Initial Precursor', 'Initial Precursor', 'ELA');
INSERT INTO itilinkagelevelmapping(actuallinkagelevel, mappinglinkagelevelname, contentareaabbreviatedname) values('Distal Precursor', 'Distal Precursor', 'ELA');
INSERT INTO itilinkagelevelmapping(actuallinkagelevel, mappinglinkagelevelname, contentareaabbreviatedname) values('Proximal Precursor', 'Proximal Precursor', 'ELA');
INSERT INTO itilinkagelevelmapping(actuallinkagelevel, mappinglinkagelevelname, contentareaabbreviatedname) values('Target', 'Target', 'ELA');
INSERT INTO itilinkagelevelmapping(actuallinkagelevel, mappinglinkagelevelname, contentareaabbreviatedname) values('Successor', 'Successor', 'ELA');

INSERT INTO itilinkagelevelmapping(actuallinkagelevel, mappinglinkagelevelname, contentareaabbreviatedname) values('Initial Precursor', 'Initial Precursor', 'M');
INSERT INTO itilinkagelevelmapping(actuallinkagelevel, mappinglinkagelevelname, contentareaabbreviatedname) values('Distal Precursor', 'Distal Precursor', 'M');
INSERT INTO itilinkagelevelmapping(actuallinkagelevel, mappinglinkagelevelname, contentareaabbreviatedname) values('Proximal Precursor', 'Proximal Precursor', 'M');
INSERT INTO itilinkagelevelmapping(actuallinkagelevel, mappinglinkagelevelname, contentareaabbreviatedname) values('Target', 'Target', 'M');
INSERT INTO itilinkagelevelmapping(actuallinkagelevel, mappinglinkagelevelname, contentareaabbreviatedname) values('Successor', 'Successor', 'M');

-- populate default as EBAE for Braille assignedSupport values as true.
insert into studentprofileitemattributevalue (activeflag, selectedvalue, profileitemattributenameattributecontainerid, studentid, createduser, modifieduser) 
select true, 'true', (select id from profileItemAttributenameAttributeContainer where attributenameid=
(select id from profileitemattribute  where attributename  = 'ebaeFileType')) , spiav.studentid, 
(select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin') 
FROM profileitemattribute pia 
JOIN profileItemAttributenameAttributeContainer pianc ON pia.id = pianc.attributenameid 
JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id 
LEFT JOIN studentprofileitemattributevalue spiav ON pianc.id = spiav.profileitemattributenameattributecontainerid and spiav.activeflag = true 
LEFT OUTER JOIN profileitemattrnameattrcontainerviewoptions pianacvo ON pianacvo.pianacid = pianc.id 
WHERE piac.attributecontainer = 'Braille' and pia.attributename in ('assignedSupport') and selectedValue = 'true';

-- to remove if there are any duplicates.
delete from studentprofileitemattributevalue where profileitemattributenameattributecontainerid in 
(select id from profileItemAttributenameAttributeContainer where attributenameid in 
(select id from profileitemattribute where attributename in ('ebaeFileType', 'uebFileType')) );

-- populate default as EBAE for Braille assignedSupport values as true.
insert into studentprofileitemattributevalue (activeflag, selectedvalue, profileitemattributenameattributecontainerid, studentid, createduser, modifieduser) 
select distinct true, 'true', (select id from profileItemAttributenameAttributeContainer where attributenameid=
(select id from profileitemattribute  where attributename  = 'ebaeFileType')) , spiav.studentid, 
(select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin') 
FROM profileitemattribute pia 
JOIN profileItemAttributenameAttributeContainer pianc ON pia.id = pianc.attributenameid 
JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id 
LEFT JOIN studentprofileitemattributevalue spiav ON pianc.id = spiav.profileitemattributenameattributecontainerid and spiav.activeflag = true 
LEFT OUTER JOIN profileitemattrnameattrcontainerviewoptions pianacvo ON pianacvo.pianacid = pianc.id 
WHERE piac.attributecontainer = 'Braille' and pia.attributename in ('assignedSupport') and selectedValue = 'true';

insert into studentprofileitemattributevalue (activeflag, selectedvalue, profileitemattributenameattributecontainerid, studentid, createduser, modifieduser) 
select distinct true, 'false', (select id from profileItemAttributenameAttributeContainer where attributenameid=
(select id from profileitemattribute  where attributename  = 'uebFileType')) , spiav.studentid, 
(select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin') 
FROM profileitemattribute pia 
JOIN profileItemAttributenameAttributeContainer pianc ON pia.id = pianc.attributenameid 
JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id 
LEFT JOIN studentprofileitemattributevalue spiav ON pianc.id = spiav.profileitemattributenameattributecontainerid and spiav.activeflag = true 
LEFT OUTER JOIN profileitemattrnameattrcontainerviewoptions pianacvo ON pianacvo.pianacid = pianc.id 
WHERE piac.attributecontainer = 'Braille' and pia.attributename in ('assignedSupport') and selectedValue = 'true';


 UPDATE groups SET isdepreciated = true, modifieduser = (select Id from aartuser where username ='cetesysadmin'), 
 modifieddate= now() WHERE groupcode in ('PDAD','SCOSL');
