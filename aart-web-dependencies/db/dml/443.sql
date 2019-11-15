--443.sql

update authorities set displayname ='Upload PNP' where authority = 'PERSONAL_NEEDS_PROFILE_UPLOAD';

-- Change from change pond
update authorities set objecttype='Student Management-Access Profile (PNP)' where objecttype='Student Management-Access Profile(PNP)';
update authorities set displayname ='Create PNP' where authority = 'CREATE_STUDENT_PNP';
update authorities set displayname ='View PNP' where authority = 'VIEW_STUDENT_PNP';
update authorities set displayname ='Edit PNP' where authority = 'EDIT_STUDENT_PNP';


-- primary role accept  alpha numeric and all upper case letters
update fieldspecification  set formatregex = '^[A-Z0-9]*$', minimum = 2, maximum=4 where fieldname = 'primaryRole' and id = (select 
fieldspecificationid from fieldspecificationsrecordtypes fs where
 recordtypeid = ( select id from category where categorycode='USER_RECORD_TYPE' )
 and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and 
fs.recordtypeid = (select id from category where categorycode='USER_RECORD_TYPE' ) and fieldname = 'primaryRole')
);


-- secondary role accept  alpha numeric and all upper case letters
update fieldspecification  set formatregex = '^[A-Z0-9]*$', minimum = 2, maximum=4,rejectifinvalid = true where fieldname = 'secondaryRole' and id = (select 
fieldspecificationid from fieldspecificationsrecordtypes fs where
 recordtypeid = ( select id from category where categorycode='USER_RECORD_TYPE' )
 and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and 
fs.recordtypeid = (select id from category where categorycode='USER_RECORD_TYPE' ) and fieldname = 'secondaryRole')
);
 