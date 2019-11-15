-- US16701: KIDS 2016 - STCO record changes DML
delete from fieldspecificationsrecordtypes where fieldspecificationid = (select id from fieldspecification where mappedname = 'SCRS_state_subject_area_code')
    and recordtypeid=(select id from category where categorycode='ROSTER_RECORD_TYPE' 
		and categorytypeid = (select id from categorytype where typecode = 'WEB_SERVICE_RECORD_TYPE'));



delete from fieldspecificationsrecordtypes where fieldspecificationid = (select id from fieldspecification where mappedname = 'SCRS_state_course_identifier')
    and recordtypeid=(select id from category where categorycode='ROSTER_RECORD_TYPE' 
		and categorytypeid = (select id from categorytype where typecode = 'WEB_SERVICE_RECORD_TYPE'));




delete from fieldspecificationsrecordtypes where fieldspecificationid = (select id from fieldspecification where mappedname = 'Local_Course_ID')
    and recordtypeid=(select id from category where categorycode='ROSTER_RECORD_TYPE' 
		and categorytypeid = (select id from categorytype where typecode = 'WEB_SERVICE_RECORD_TYPE'));	


delete from fieldspecificationsrecordtypes where fieldspecificationid = (select id from fieldspecification where mappedname = 'Comprehensive_Race')
    and recordtypeid=(select id from category where categorycode='ROSTER_RECORD_TYPE' 
		and categorytypeid = (select id from categorytype where typecode = 'WEB_SERVICE_RECORD_TYPE'));


delete from fieldspecificationsrecordtypes where fieldspecificationid in (select id from fieldspecification where mappedname = 'Local_Student_Identifier')
    and recordtypeid=(select id from category where categorycode='ROSTER_RECORD_TYPE' 
		and categorytypeid = (select id from categorytype where typecode = 'WEB_SERVICE_RECORD_TYPE'));


delete from fieldspecificationsrecordtypes where fieldspecificationid in (select id from fieldspecification where mappedname = 'Student_Generation_Code')
    and recordtypeid=(select id from category where categorycode='ROSTER_RECORD_TYPE' 
		and categorytypeid = (select id from categorytype where typecode = 'WEB_SERVICE_RECORD_TYPE'));	


delete from fieldspecificationsrecordtypes where fieldspecificationid in (select id from fieldspecification where mappedname = 'Student_Birth_Date')
    and recordtypeid=(select id from category where categorycode='ROSTER_RECORD_TYPE' 
		and categorytypeid = (select id from categorytype where typecode = 'WEB_SERVICE_RECORD_TYPE'));


delete from fieldspecificationsrecordtypes where fieldspecificationid in (select id from fieldspecification where mappedname = 'Accountability_School_Identifier')
    and recordtypeid=(select id from category where categorycode='ROSTER_RECORD_TYPE' 
		and categorytypeid = (select id from categorytype where typecode = 'WEB_SERVICE_RECORD_TYPE'));

		

delete from fieldspecificationsrecordtypes where fieldspecificationid in (select id from fieldspecification where mappedname = 'Hispanic_Ethnicity')
    and recordtypeid=(select id from category where categorycode='ROSTER_RECORD_TYPE' 
		and categorytypeid = (select id from categorytype where typecode = 'WEB_SERVICE_RECORD_TYPE'));


delete from fieldspecificationsrecordtypes where fieldspecificationid in (select id from fieldspecification where mappedname = 'section')
    and recordtypeid=(select id from category where categorycode='ROSTER_RECORD_TYPE' 
		and categorytypeid = (select id from categorytype where typecode = 'WEB_SERVICE_RECORD_TYPE'));
		
		
-- Modifying the allowable values
update fieldspecification set allowablevalues='{01,02}' where mappedname = 'Course_Status' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where fieldspecificationid in (select id from fieldspecification where mappedname = 'Course_Status')
    and recordtypeid in(select id from category where categorycode='ROSTER_RECORD_TYPE' 
		and categorytypeid in (select id from categorytype where typecode = 'WEB_SERVICE_RECORD_TYPE')));

update fieldspecification set rejectifempty = true where mappedname = 'Course_Status' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where fieldspecificationid in (select id from fieldspecification where mappedname = 'Course_Status')
    and recordtypeid in(select id from category where categorycode='ROSTER_RECORD_TYPE' 
		and categorytypeid in (select id from categorytype where typecode = 'WEB_SERVICE_RECORD_TYPE')));

update fieldspecification set rejectifempty=true where id in (select fieldspecificationid from fieldspecificationsrecordtypes where fieldspecificationid in (select id from fieldspecification where mappedname = 'Current_Grade_Level')
    and recordtypeid in(select id from category where categorycode='ROSTER_RECORD_TYPE' 
		and categorytypeid in (select id from categorytype where typecode = 'WEB_SERVICE_RECORD_TYPE')));
		