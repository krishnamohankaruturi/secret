-- California Organization
INSERT INTO organization (displayidentifier, organizationname, organizationtypeid) values ('CAL', 'California', (select id from organizationtype where typecode = 'ST'));

INSERT INTO orgassessmentprogram (organizationid, assessmentprogramid) select (select id from organization where displayidentifier = 'CAL'), id from assessmentprogram;

INSERT INTO groups (organizationid, groupname, defaultrole) values ((select id from organization where displayidentifier = 'CAL'), 'DEFAULT', true),
	((select id from organization where displayidentifier = 'CAL'), 'System Administrator', false);

INSERT INTO groupauthorities(groupid, authorityid) (select gr.id, au.id from groups gr, authorities au where gr.organizationid = (select id from organization where displayidentifier = 'CAL') and gr.defaultrole = false);

INSERT INTO aartuser (username, firstname, middlename, surname, password, email, uniquecommonidentifier, defaultusergroupsid, ukey) VALUES ('CaliforniaSysAdmin', 'California', NULL, 'SysAdmin', '8665aff96848a59befda17d9e2e96f3cf5faf6e8343d03075f805784123a34042af4f23b493af1481bf5ecb842a10b195751eb77d3e6cd9e780476a199845569', 'CaliforniaSysAdmin', '103001', NULL, '횺ڎ傘쐜灠혆퉗方ߍ΅饤礧髮왾');

INSERT INTO usergroups(aartuserid, groupid, status) values ((select au.id from aartuser au where au.username = 'CaliforniaSysAdmin'), (select gr.id from groups gr where gr.id = currval('groups_id_seq')), 2);


-- Nevada Organization
INSERT INTO organization (displayidentifier, organizationname, organizationtypeid) values ('NV', 'Nevada', (select id from organizationtype where typecode = 'ST'));

INSERT INTO orgassessmentprogram (organizationid, assessmentprogramid) select (select id from organization where displayidentifier = 'NV'), id from assessmentprogram;

INSERT INTO groups (organizationid, groupname, defaultrole) values ((select id from organization where displayidentifier = 'NV'), 'DEFAULT', true),
	((select id from organization where displayidentifier = 'NV'), 'System Administrator', false);

INSERT INTO groupauthorities(groupid, authorityid) (select gr.id, au.id from groups gr, authorities au where gr.organizationid = (select id from organization where displayidentifier = 'NV') and gr.defaultrole = false);

INSERT INTO aartuser (username, firstname, middlename, surname, password, email, uniquecommonidentifier, defaultusergroupsid, ukey) VALUES ('NevadaSysAdmin', 'Nevada', NULL, 'SysAdmin', '45f5b20300b490c54ddcd5209ba143e215f70de757981f4d6cfb2227a76d6cbf973d9489c806138a081677a47daef8114d468aa2ba1999f04d0699550a1eba19', 'NevadaSysAdmin', '103001', NULL, '럾췦稌᫑澱雩錠霸䌝嚯ᆞ簯뇜硺');
INSERT INTO usergroups(aartuserid, groupid, status) values ((select au.id from aartuser au where au.username = 'NevadaSysAdmin'), (select gr.id from groups gr where gr.id = currval('groups_id_seq')), 2);


-- Oregon Organization
INSERT INTO organization (displayidentifier, organizationname, organizationtypeid) values ('OR', 'Oregon', (select id from organizationtype where typecode = 'ST'));

INSERT INTO orgassessmentprogram (organizationid, assessmentprogramid) select (select id from organization where displayidentifier = 'OR'), id from assessmentprogram;

INSERT INTO groups (organizationid, groupname, defaultrole) values ((select id from organization where displayidentifier = 'OR'), 'DEFAULT', true),
	((select id from organization where displayidentifier = 'OR'), 'System Administrator', false);

INSERT INTO groupauthorities(groupid, authorityid) (select gr.id, au.id from groups gr, authorities au where gr.organizationid = (select id from organization where displayidentifier = 'OR') and gr.defaultrole = false);

INSERT INTO aartuser (username, firstname, middlename, surname, password, email, uniquecommonidentifier, defaultusergroupsid, ukey) VALUES ('OregonSysAdmin', 'Oregon', NULL, 'SysAdmin', '85bff5437ef7730d3d68530aab5271ad622f2cbf387283f299ca3b71f939676524f7464fc43d7702ae23544f01c5ba9f058ec082cd855922bdc7d2ad2b37891c', 'OregonSysAdmin', '103001', NULL, '馴Җա蝏箐＋򒁉⽗㴤ᷥ羒睻ڮ峴Ю');
INSERT INTO usergroups(aartuserid, groupid, status) values ((select au.id from aartuser au where au.username = 'OregonSysAdmin'), (select gr.id from groups gr where gr.id = currval('groups_id_seq')), 2);


-- Private School  Organization
INSERT INTO organization (displayidentifier, organizationname, organizationtypeid) values ('PS', 'Private School', (select id from organizationtype where typecode = 'ST'));

INSERT INTO orgassessmentprogram (organizationid, assessmentprogramid) select (select id from organization where displayidentifier = 'PS'), id from assessmentprogram;

INSERT INTO groups (organizationid, groupname, defaultrole) values ((select id from organization where displayidentifier = 'PS'), 'DEFAULT', true),
	((select id from organization where displayidentifier = 'PS'), 'System Administrator', false);

INSERT INTO groupauthorities(groupid, authorityid) (select gr.id, au.id from groups gr, authorities au where gr.organizationid = (select id from organization where displayidentifier = 'PS') and gr.defaultrole = false);

INSERT INTO aartuser (username, firstname, middlename, surname, password, email, uniquecommonidentifier, defaultusergroupsid, ukey) VALUES ('PrivateSchoolSysAdmin', 'PrivateSchool', NULL, 'SysAdmin', '14094ffad2f295c30d28a7fdfbe41168d0b9e01d5d0e70557a91614407118916a050f980d1aaed4d854dce3cc95f574fcfb98dcfa56547d7c0d58d750f0c6dad', 'PrivateSchoolSysAdmin', '103001', NULL, '羴跐䤘ዺ믁䬎鏦䀸櫫栭뛩婿於怂⡔');
INSERT INTO usergroups(aartuserid, groupid, status) values ((select au.id from aartuser au where au.username = 'PrivateSchoolSysAdmin'), (select gr.id from groups gr where gr.id = currval('groups_id_seq')), 2);
