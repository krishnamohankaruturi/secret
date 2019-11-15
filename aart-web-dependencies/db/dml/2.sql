INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES
    ('Organization', 'ORG_RECORD_TYPE', 'Upload type for Organization record', (select id from categorytype where typecode = 'CSV_RECORD_TYPE'))
    except (select categoryname, categorycode, categorydescription, categorytypeid from category where categorycode = 'ORG_RECORD_TYPE');

INSERT INTO fieldspecification (fieldname, fieldlength, rejectifempty, rejectifinvalid, formatregex, showerror) VALUES
    ('displayIdentifier', 10, true, true, '^[A-z0-9]++$', true)
    except (select fieldname, fieldlength, rejectifempty, rejectifinvalid, formatregex, showerror from fieldspecification where fieldname = 'displayIdentifier');
INSERT INTO fieldspecification (fieldname, fieldlength, rejectifempty, rejectifinvalid, formatregex, showerror) VALUES
    ('parentDisplayIdentifier', 40, true, true, '^[A-z0-9]++$', true)
    except (select fieldname, fieldlength, rejectifempty, rejectifinvalid, formatregex, showerror from fieldspecification where fieldname = 'parentDisplayIdentifier');
INSERT INTO fieldspecification (fieldname, fieldlength, rejectifempty, rejectifinvalid, formatregex, showerror) VALUES
    ('contractOrgDisplayId', 10, false, true, '^[A-z0-9]*+$', true)
    except (select fieldname, fieldlength, rejectifempty, rejectifinvalid, formatregex, showerror from fieldspecification where fieldname = 'contractOrgDisplayId');
INSERT INTO fieldspecification (fieldname, fieldlength, rejectifempty, rejectifinvalid, formatregex, showerror) VALUES
    ('organizationName', 40, true, true, '^[A-z0-9 ]++$', true)
    except (select fieldname, fieldlength, rejectifempty, rejectifinvalid, formatregex, showerror from fieldspecification where fieldname = 'organizationName');

INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES
    (
        (select id from fieldspecification where fieldname = 'displayIdentifier' and mappedname is null),
        (select id from category where categorycode = 'ORG_RECORD_TYPE')
    ) except (select fieldspecificationid, recordtypeid from fieldspecificationsrecordtypes);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES
    (
        (select id from fieldspecification where fieldname = 'parentDisplayIdentifier' and mappedname is null),
        (select id from category where categorycode = 'ORG_RECORD_TYPE')
    ) except (select fieldspecificationid, recordtypeid from fieldspecificationsrecordtypes);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES
    (
        (select id from fieldspecification where fieldname = 'contractOrgDisplayId' and mappedname is null),
        (select id from category where categorycode = 'ORG_RECORD_TYPE')
    ) except (select fieldspecificationid, recordtypeid from fieldspecificationsrecordtypes);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES
    (
        (select id from fieldspecification where fieldname = 'organizationName' and mappedname is null),
        (select id from category where categorycode = 'ORG_RECORD_TYPE')
    ) except (select fieldspecificationid, recordtypeid from fieldspecificationsrecordtypes);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES
    (
        (select id from fieldspecification where fieldname = 'organizationTypeCode' and mappedname is null),
        (select id from category where categorycode = 'ORG_RECORD_TYPE')
    ) except (select fieldspecificationid, recordtypeid from fieldspecificationsrecordtypes);
    
--insert for common security.

 INSERT INTO categorytype (typename, typecode, typedescription) VALUES
    ('Restricted Resource Type','RESTRICTED_RESOURCE_TYPE','This is the resource type for restricted resources.')
    except (select typename, typecode, typedescription from categorytype);

INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES
    ('Roster Resource','ROSTER_RESOURCE','This is the roster that gets created',
    (select id from categorytype where typecode = 'RESTRICTED_RESOURCE_TYPE'))
    except (select categoryname, categorycode, categorydescription, categorytypeid
    from category);

-- This is common to all organizations.IF one organization wants to be different then a seperate entry has to be made.
INSERT INTO restriction (restrictionname, restrictioncode, restrictiondescription, restrictedresourcetypeid)
VALUES ('Top Down For Rosters', 'TOP_DOWN_ROSTERS',
'This is the restriction that applies for all organizations in this system',
(select id from category where categorycode = 'ROSTER_RESOURCE') );

--'PERM_ROSTERRECORD_VIEW','PERM_ROSTERRECORD_SEARCH' to parent.
insert into restrictionsauthorities(restrictionid,authorityid,isparent,ischild,isdifferential)
(Select restriction.id,authorities.id,true as isparent,false as ischild,false as isdifferential
 from restriction,authorities where authorities.authority in ('PERM_ROSTERRECORD_VIEW','PERM_ROSTERRECORD_SEARCH'));

--'PERM_ROSTERRECORD_VIEW','PERM_ROSTERRECORD_MODIFY','PERM_ROSTERRECORD_CREATE',
--'PERM_ROSTERRECORD_DELETE','PERM_ROSTERRECORD_SEARCH','PERM_ROSTERRECORD_ARCHIVE',
--'PERM_ROSTERRECORD_UPLOAD' to current org/ownership org.

insert into restrictionsauthorities(restrictionid,authorityid,isparent,ischild,isdifferential)
(Select restriction.id,authorities.id,false as isparent,false as ischild,false as isdifferential
 from restriction,authorities where authorities.authority in ('PERM_ROSTERRECORD_VIEW',
 'PERM_ROSTERRECORD_MODIFY','PERM_ROSTERRECORD_CREATE','PERM_ROSTERRECORD_DELETE',
 'PERM_ROSTERRECORD_SEARCH','PERM_ROSTERRECORD_ARCHIVE','PERM_ROSTERRECORD_UPLOAD'));

 --none for child.

 --'PERM_ROSTERRECORD_VIEWALL' for differential permission.

insert into restrictionsauthorities(restrictionid,authorityid,isparent,ischild,isdifferential)
(Select restriction.id,authorities.id,false as isparent,false as ischild,true as isdifferential
 from restriction,authorities where authorities.authority in ('PERM_ROSTERRECORD_VIEWALL'));

-- insert this restriction to all organizations.

insert into restrictionsorganizations(restrictionid,organizationid,isenforced)
(Select restriction.id as restrictionid,organization.id as organizationid,true from organization,restriction);
 
 --now update all the rosters to the newly created restriction.
update
roster
set
restrictionid = (select id from restriction where restrictioncode = 'TOP_DOWN_ROSTERS');

--add the not null constraint.This can only be added after the data and hence it is added here.

ALTER TABLE roster
   ALTER COLUMN restrictionid SET NOT NULL;

--for organization upload.
delete from fieldspecificationsrecordtypes where fieldspecificationid in (select id from fieldspecification where fieldname = 'organizationId');

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) (select fs.id, ca.id from fieldspecification fs,
    category ca where fs.fieldname = 'displayIdentifier' and ca.categorycode = 'USER_RECORD_TYPE') except (select fieldspecificationid, recordtypeid from fieldspecificationsrecordtypes);   