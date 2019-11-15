
--F933 EP G-Release---

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='PLTW') THEN --only for pltw

INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue,activeflag, createduser, createddate, modifieduser, modifieddate,assessmentprogramid)VALUES('st_Hispanic_Ethnicity_Yes','st_Hispanic_Ethnicity_Assessment','Yes','true',true,(select id from aartuser where username ='cetesysadmin'),current_timestamp,(select id from aartuser where username ='cetesysadmin'),current_timestamp,(select id from assessmentprogram where abbreviatedname ='PLTW'));

INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue,activeflag, createduser, createddate, modifieduser, modifieddate,assessmentprogramid)VALUES('st_Hispanic_Ethnicity_No','st_Hispanic_Ethnicity_Assessment','No','false',true,(select id from aartuser where username ='cetesysadmin'),current_timestamp,(select id from aartuser where username ='cetesysadmin'),current_timestamp,(select id from assessmentprogram where abbreviatedname ='PLTW'));

INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue,activeflag, createduser, createddate, modifieduser, modifieddate,assessmentprogramid)VALUES('st_Hispanic_Ethnicity_Not_Selected','st_Hispanic_Ethnicity_Assessment','Not Selected','Not Selected',true,(select id from aartuser where username ='cetesysadmin'),current_timestamp,(select id from aartuser where username ='cetesysadmin'),current_timestamp,(select id from assessmentprogram where abbreviatedname ='PLTW'));

END IF;
END
$do$;
---Commenting below script, it's already available in the DB-----
--INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue,activeflag, createduser, createddate, modifieduser, modifieddate)VALUES('st_Hispanic_Ethnicity_Yes','st_Hispanic_Ethnicity_Assessment','Yes','true',true,(select id from aartuser where username ='cetesysadmin'),current_timestamp,(select id from aartuser where username ='cetesysadmin'),current_timestamp);
--INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue,activeflag, createduser, createddate, modifieduser, modifieddate)VALUES('st_Hispanic_Ethnicity_No','st_Hispanic_Ethnicity_Assessment','No','false',true,(select id from aartuser where username ='cetesysadmin'),current_timestamp,(select id from aartuser where username ='cetesysadmin'),current_timestamp);

update fieldspecification set formatregex='^$|yes|no|true|false|Not Selected' where id in
(select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid in
  (select id from category where categorycode in('PERSONAL_NEEDS_PROFILE_RECORD_TYPE'))) 
 and fieldname='hispanicEthnicity';

update fieldspecification set allowablevalues='{yes,YES,Yes,No,NO,no,Not Selected}' where id in
(select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid in
  (select id from category where categorycode in('ENRL_RECORD_TYPE'))) 
 and fieldname='hispanicEthnicity';
 
 update fieldspecification set fieldlength =12 where id in
(select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid in
  (select id from category where categorycode in('ENRL_RECORD_TYPE'))) 
 and fieldname='hispanicEthnicity';
 -----------------------------------------------------------------------------------------------------------------------------------------
 --SIF Accountability District Identifier Script G-Release---------
INSERT INTO public.fieldspecification(
	fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
	rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, 
	activeflag, modifieddate, modifieduser, iskeyvaluepairfield, fieldtype, 
	minimumregex, maximumregex, regexmodeflags)
	VALUES ('accountabilityDistrictIdentifier', '',null,null,20,false, false, '',
			'SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/AccountabilityDistrictIdentifier', 
			false, now(), 12, true, now(), 12,false, 'String','' ,'', '');
INSERT INTO public.fieldspecificationsrecordtypes(
	fieldspecificationid, recordtypeid, createddate, createduser, 
	activeflag, modifieddate, modifieduser, mappedname, required, jsondata, sortorder)
	VALUES ((select id from fieldspecification where fieldname = 'accountabilityDistrictIdentifier' and 
mappedname = 'SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/AccountabilityDistrictIdentifier'),
(select id from category where categorycode = 'ENRL_XML_RECORD_TYPE'), now(), 12, true, now(),12, 
'SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/AccountabilityDistrictIdentifier',	true, null, null); 

---------    INSERT Query to insert records in studentexitcodes table   --------------

------------------------------------------------------------------------------------------------
--------------------------------- DLM default subsets of exit codes ----------------------------
------------------------------------------------------------------------------------------------
DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 3, 'Transfer to a public school in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), true);			

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 6, 'Transfer to home schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 7, 'Matriculation to another school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 8, 'Graduated with rregular diploma', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 9, 'Completed school with other credentials (e.g district awarded GED)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 10, 'Student death', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 11, 'Student illness', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 12, 'Student expulsion (or long term suspension)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 13, 'Reached maximum age for services', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 14, 'Discontinued schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 15, 'Transfer to an accredited or non - accredited juvenile correctional facility where educational services are provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 16, 'Moved within the United States, not known to be enrolled in school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 17, 'Unknown educational services provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 18, 'Student data claimed in error /never attended', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 19, 'Transfer to an adult education facility (i.e., for GED completion)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 20, 'Transfer to juvenile or adult correctional facility-no', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 21, 'Student moved to another country, may or may not be continuing enrollment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 22, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), false);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 23, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), false);		
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 30, 'Student no longer meets eligibility criteria for alternate assessment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 98, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), false);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 99, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'DLM'), false);	

END IF;
END
$do$;	
------------------------------------------------------------------------------------------------
------------------------------------ KAP default subsets of exit codes --------------------------
------------------------------------------------------------------------------------------------

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='KAP') THEN
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 2, 'Transfer to a public school in a different district in state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 3, 'Transfer to a public school in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), true);			

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 4, 'Transfer to an accredited private school in state or in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 5, 'Transfer to a non-accredited private school in state or in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 6, 'Transfer to home schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), true);				

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 7, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), false);		
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 8, 'Graduated with rregular diploma', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), true);			

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 9, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), false);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 10, 'Student death', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 11, 'Student illness', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 12, 'Student expulsion (or long term suspension)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 13, 'Reached maximum age for services', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 14, 'Discontinued schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 15, 'Transfer to a juvenile or adult correctional facility where diploma completion services are provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 16, 'Moved within the US not known to be enrolled in school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 17, 'Unknown', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 18, 'Student data claimed in error by an ASGT record', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 19, 'Transfer to a GED completion program', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 20, 'Transferred to a juvenile or adult correctional facility where diploma completion services are not provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 21, 'Student moved to another country, may or may not be continuing enrollment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 22, 'Student with disabilities who met the district graduation requirements for regular diploma but is remaining in school to receive transitional services deemed necessary by the IEP team', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), true);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 23, 'Student with an extended absence at the beginning of the school year (through September 30), planning to return', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), true);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 98, 'Unresolved Exit', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), true);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 99, 'Undo previously submitted Exit', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KAP'), true);		

END IF;
END
$do$;		
------------------------------------------------------------------------------------------------
------------------------------------ KELPA2 default subsets of exit codes ----------------------
------------------------------------------------------------------------------------------------

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='KELPA2') THEN
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 2, 'Transfer to a public school in a different district in state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 3, 'Transfer to a public school in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), true);			

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 4, 'Transfer to an accredited private school in state or in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 5, 'Transfer to a non-accredited private school in state or in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 6, 'Transfer to home schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), true);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 7, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), false);				

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 8, 'Graduated with rregular diploma', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), true);			

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 9, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), false);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 10, 'Student death', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 11, 'Student illness', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 12, 'Student expulsion (or long term suspension)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 13, 'Reached maximum age for services', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 14, 'Discontinued schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 15, 'Transfer to a juvenile or adult correctional facility where diploma completion services are provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 16, 'Moved within the US not known to be enrolled in school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 17, 'Unknown', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 18, 'Student data claimed in error by an ASGT record', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 19, 'Transfer to a GED completion program', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 20, 'Transferred to a juvenile or adult correctional facility where diploma completion services are not provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 21, 'Student moved to another country, may or may not be continuing enrollment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 22, 'Student with disabilities who met the district graduation requirements for regular diploma but is remaining in school to receive transitional services deemed necessary by the IEP team', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), true);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 23, 'Student with an extended absence at the beginning of the school year (through September 30), planning to return', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), true);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 98, 'Unresolved Exit', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), true);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 99, 'Undo previously submitted Exit', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'KELPA2'), true);		

END IF;
END
$do$;
------------------------------------------------------------------------------------------------
------------------------------- CPASS default subsets of exit codes ----------------------------
------------------------------------------------------------------------------------------------

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='CPASS') THEN

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 2, 'Transfer to a public school in a different district in state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 3, 'Transfer to a public school in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), true);			

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 4, 'Transfer to an accredited private school in state or in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 5, 'Transfer to a non-accredited private school in state or in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 6, 'Transfer to home schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), true);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 7, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), false);				

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 8, 'Graduated with rregular diploma', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), true);			

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 9, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), false);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 10, 'Student death', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 11, 'Student illness', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 12, 'Student expulsion (or long term suspension)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 13, 'Reached maximum age for services', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 14, 'Discontinued schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 15, 'Transfer to a juvenile or adult correctional facility where diploma completion services are provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 16, 'Moved within the US not known to be enrolled in school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 17, 'Unknown', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 18, 'Student data claimed in error by an ASGT record', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 19, 'Transfer to a GED completion program', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 20, 'Transferred to a juvenile or adult correctional facility where diploma completion services are not provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 21, 'Student moved to another country, may or may not be continuing enrollment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 22, 'Student with disabilities who met the district graduation requirements for regular diploma but is remaining in school to receive transitional services deemed necessary by the IEP team', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), true);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 23, 'Student with an extended absence at the beginning of the school year (through September 30), planning to return', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), true);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 98, 'Unresolved Exit', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), true);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 99, 'Undo previously submitted Exit', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'CPASS'), true);		

END IF;
END
$do$;

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='KAP') THEN
--------- APP Configuration Entry F915 ------------

INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
VALUES ( 'KIDS_EXIT_KAP', 'KIDS_EXIT_ASS_PGM', 'KAP program exit', 'KAP', true,
(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
(select id from aartuser where username ='cetesysadmin'), current_timestamp);

END IF;
END
$do$;

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='KELPA2') THEN

INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
VALUES ( 'KIDS_EXIT_KELPA2', 'KIDS_EXIT_ASS_PGM', 'KELPA2 program exit', 'KELPA2', true,
(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
(select id from aartuser where username ='cetesysadmin'), current_timestamp);

END IF;
END
$do$;
--------------------------------------------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(Example given for Kansas(51)) ------------
--------------------------------------------------------------------------------------------------------------------------------------
--Need Exit code for state wise--
--------------------------------------------------------------------------------------------------------------------------------------
---F956 G-Release GRF Error Message for student id length-- 
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_CHARACTERS','grf_upload','characters',' characters.##',12,now(),12,now());	

UPDATE public.appconfiguration set attrvalue = 'State_Student_Identifier  ~ The entry in the State_Student_Identifier field must be less than or equal to ' where attrcode = 'GRF_CMN_VAL_STATE_STUDENT_IDENTIFIER_LENGTH';

--------------------------------------------------------------------------------------------------------------------------------------
-----------------------------------------F956 DML SCRIPTS-----------------------------------------------------------------------------------
-----------------------------------------Default state student identifier length------------------------------------------------------------

INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
VALUES ( 'STATE_STUDENT_IDENTIFIER_LENGTH_ERROR', 'State Student Identifier Length Error', 'State Student Identifier Length Error', 'State Student Identifier field is longer than the maximum length of ', true,
(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
(select id from aartuser where username ='cetesysadmin'), current_timestamp);  

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='KAP') THEN

INSERT INTO public.statestudentidentifierlength(
	state, statestudentidlength, createduser, createddate, activeflag, modifieddate, modifieduser)
	VALUES ( null, 10, (select id from aartuser where username ='cetesysadmin'), 
            current_timestamp, true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
        
INSERT INTO public.statestudentidentifierlength(
	state, statestudentidlength, createduser, createddate, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from organization where displayidentifier='KS'), 10, (select id from aartuser where username ='cetesysadmin'), 
            current_timestamp, true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));   
            
UPDATE fieldspecification SET fieldlength=null WHERE fieldname='stateStudentIdentifier';

END IF;
END
$do$;

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='PLTW') THEN --only for pltw

INSERT INTO public.statestudentidentifierlength(
	state, statestudentidlength, createduser, createddate, activeflag, modifieddate, modifieduser)
	VALUES ( null, 50, (select id from aartuser where username ='cetesysadmin'), 
            current_timestamp, true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));

UPDATE fieldspecification SET fieldlength=50 WHERE fieldname='stateStudentIdentifier';

END IF;
END
$do$;

update fieldspecification set allowablevalues = '{'',1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,30,98,99}'
where fieldname = 'exitReason';


-----F916 Insert new column for SHort Duration Testing permission in Dashboard ----

INSERT INTO public.authorities
(authority, displayname, objecttype, createddate, createduser, activeflag, modifieddate, modifieduser, tabname, groupingname, labelname, "level", sortorder)
select 'VIEW_SHORT_DURATION_TEST' authority, 'View Short Duration Tests' displayname,'Other-Dashboard' objecttype,now() createddate,12 createduser, true activeflag, 
now() modifieddate,12 modifieduser, 'Other'tabname,'Dashboard' groupingname, '' labelname, 2 "level", 17800 sortorder;


-----F915 Insert new row for Exit code Error message for GRF

INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser, createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EXIT_WITHDRAWAL_CODE_STATE_INVALID','grf_upload','exitwithdrawalcode','Exit_Withdrawl_Code ~The entry in the Exit_Withdrawal_Code field is rejected because exit code is not valid in your state.##',12,now(),12,now());

------F917 DE-19360

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN --only for DLM

DELETE from groupauthorities where authorityid in (select id from authorities where authority in ('CREATE_AGGREGATE_DISTRICT_CSV','CREATE_AGGREGATE_SCHOOL_CSV','CREATE_AGGREGATE_CLASSROOM_CSV'));
DELETE from authorities where authority in ('CREATE_AGGREGATE_DISTRICT_CSV','CREATE_AGGREGATE_SCHOOL_CSV','CREATE_AGGREGATE_CLASSROOM_CSV');

END IF;
END
$do$;


-----F915 State specific Insert Scripts


------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(Alaska) ----------------------------------
------------------------------------------------------------------------------------------------

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'AK'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'AK'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'AK'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'AK'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	
		

END IF;
END
$do$;		
		
------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(Arkansas) ----------------------------------
------------------------------------------------------------------------------------------------

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'AR'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'AR'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'AR'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'AR'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	
		

END IF;
END
$do$;

------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(Colorado) ----------------------------------
------------------------------------------------------------------------------------------------

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'CO'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'CO'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'CO'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'CO'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	
		

END IF;
END
$do$;

------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(Delaware) ----------------------------------
------------------------------------------------------------------------------------------------

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'DE'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'DE'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'DE'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'DE'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	
		

END IF;
END
$do$;	
------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(District of Columbia) ----------------------------------
------------------------------------------------------------------------------------------------

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'DC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'DC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'DC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'DC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);
		

END IF;
END
$do$;		
		
------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(KANSAS) ----------------------------------
------------------------------------------------------------------------------------------------

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
		

END IF;
END
$do$;		
------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(ILLINOIS) ----------------------------------
------------------------------------------------------------------------------------------------		

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IL'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IL'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IL'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IL'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 21, 'Student moved to another country, may or may not be continuing enrollment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IL'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 15, 'Transfer to an accredited or non - accredited juvenile correctional facility where educational services are provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IL'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 20, 'Transfer to juvenile or adult correctional facility-no', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IL'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 6, 'Transfer to home schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IL'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 8, 'Graduated with rregular diploma', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IL'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 9, 'Completed school with other credentials (e.g district awarded GED)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IL'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 10, 'Student death', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IL'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 11, 'Student illness', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IL'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 12, 'Student expulsion (or long term suspension)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IL'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 13, 'Reached maximum age for services', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IL'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 14, 'Discontinued schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IL'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 17, 'Unknown educational services provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IL'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 16, 'Moved within the United States, not known to be enrolled in school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IL'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 18, 'Student data claimed in error /never attended', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IL'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 19, 'Transfer to an adult education facility (i.e., for GED completion)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IL'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 7, 'Matriculation to another school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IL'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 30, 'Student no longer meets eligibility criteria for alternate assessment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IL'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
		

END IF;
END
$do$;
------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(IOWA) ----------------------------------
------------------------------------------------------------------------------------------------		

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'IA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
		

END IF;
END
$do$;	
------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(MARYLAND) ----------------------------------
------------------------------------------------------------------------------------------------	

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN	
	
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MD'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MD'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MD'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MD'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 21, 'Student moved to another country, may or may not be continuing enrollment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MD'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 15, 'Transfer to an accredited or non - accredited juvenile correctional facility where educational services are provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MD'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 20, 'Transfer to juvenile or adult correctional facility-no', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MD'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 6, 'Transfer to home schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MD'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 8, 'Graduated with rregular diploma', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MD'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 9, 'Completed school with other credentials (e.g district awarded GED)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MD'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 10, 'Student death', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MD'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 11, 'Student illness', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MD'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 12, 'Student expulsion (or long term suspension)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MD'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 13, 'Reached maximum age for services', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MD'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 14, 'Discontinued schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MD'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 17, 'Unknown educational services provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MD'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 16, 'Moved within the United States, not known to be enrolled in school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MD'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 18, 'Student data claimed in error /never attended', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MD'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 19, 'Transfer to an adult education facility (i.e., for GED completion)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MD'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 7, 'Matriculation to another school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MD'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 30, 'Student no longer meets eligibility criteria for alternate assessment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MD'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 98, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MD'), (select id from assessmentprogram where abbreviatedname = 'DLM'), false, 2019);	
		

END IF;
END
$do$;
		
------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(MISSOURI) ----------------------------------
------------------------------------------------------------------------------------------------		

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MO'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MO'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MO'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MO'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
		

END IF;
END
$do$;				
------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(NEW HAMPSHIRE) ----------------------------------
------------------------------------------------------------------------------------------------		

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN
				
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NH'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NH'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NH'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NH'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
								
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 21, 'Student moved to another country, may or may not be continuing enrollment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NH'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 15, 'Transfer to an accredited or non - accredited juvenile correctional facility where educational services are provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NH'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 20, 'Transfer to juvenile or adult correctional facility-no', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NH'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 6, 'Transfer to home schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NH'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 8, 'Graduated with rregular diploma', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NH'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 9, 'Completed school with other credentials (e.g district awarded GED)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NH'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 10, 'Student death', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NH'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 11, 'Student illness', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NH'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 12, 'Student expulsion (or long term suspension)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NH'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 13, 'Reached maximum age for services', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NH'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 14, 'Discontinued schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NH'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 17, 'Unknown educational services provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NH'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 16, 'Moved within the United States, not known to be enrolled in school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NH'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 18, 'Student data claimed in error /never attended', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NH'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 19, 'Transfer to an adult education facility (i.e., for GED completion)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NH'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 7, 'Matriculation to another school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NH'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 30, 'Student no longer meets eligibility criteria for alternate assessment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NH'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 98, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NH'), (select id from assessmentprogram where abbreviatedname = 'DLM'), false, 2019);	
		

END IF;
END
$do$;		
------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(NEW JERSEY) ----------------------------------
------------------------------------------------------------------------------------------------	

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NJ'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NJ'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NJ'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NJ'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
		

END IF;
END
$do$;		
------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(NEW YORK) ----------------------------------
------------------------------------------------------------------------------------------------		

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NY'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NY'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 3, 'Transfer to a public school in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NY'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);			

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NY'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NY'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

		INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 6, 'Transfer to home schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NY'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 10, 'Student death', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NY'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 13, 'Reached maximum age for services', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NY'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 14, 'Discontinued schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NY'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 17, 'Unknown educational services provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NY'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	
	
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 16, 'Moved within the United States, not known to be enrolled in school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NY'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 18, 'Student data claimed in error /never attended', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NY'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 30, 'Student no longer meets eligibility criteria for alternate assessment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NY'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 98, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NY'), (select id from assessmentprogram where abbreviatedname = 'DLM'), false, 2019);	
		

END IF;
END
$do$;	
------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(NORTH DAKOTA) ----------------------------------
------------------------------------------------------------------------------------------------		

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'ND'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'ND'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
	
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'ND'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'ND'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
		

END IF;
END
$do$;	
------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(OKLAHOMA) ----------------------------------
------------------------------------------------------------------------------------------------		

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'OK'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'OK'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
	
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'OK'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'OK'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
		

END IF;
END
$do$;
------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(RHODE ISLAND) ----------------------------------
------------------------------------------------------------------------------------------------	

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'RI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'RI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 3, 'Transfer to a public school in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'RI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);			

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'RI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'RI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 6, 'Transfer to home schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'RI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

		INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 16, 'Moved within the United States, not known to be enrolled in school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'RI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

		INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 18, 'Student data claimed in error /never attended', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'RI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

		INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 30, 'Student no longer meets eligibility criteria for alternate assessment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'RI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 98, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'RI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), false, 2019);	
		

END IF;
END
$do$;
------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(UTAH) ----------------------------------
------------------------------------------------------------------------------------------------	

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'UT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'UT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
	
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'UT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'UT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 21, 'Student moved to another country, may or may not be continuing enrollment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'UT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 15, 'Transfer to an accredited or non - accredited juvenile correctional facility where educational services are provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'UT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 20, 'Transfer to juvenile or adult correctional facility-no', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'UT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 6, 'Transfer to home schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'UT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 8, 'Graduated with rregular diploma', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'UT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 9, 'Completed school with other credentials (e.g district awarded GED)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'UT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 10, 'Student death', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'UT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 11, 'Student illness', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'UT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 12, 'Student expulsion (or long term suspension)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'UT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 13, 'Reached maximum age for services', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'UT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 14, 'Discontinued schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'UT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 17, 'Unknown educational services provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'UT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 16, 'Moved within the United States, not known to be enrolled in school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'UT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 18, 'Student data claimed in error /never attended', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'UT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 19, 'Transfer to an adult education facility (i.e., for GED completion)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'UT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 7, 'Matriculation to another school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'UT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 30, 'Student no longer meets eligibility criteria for alternate assessment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'UT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 98, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'UT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), false, 2019);	
		

END IF;
END
$do$;		
------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(VERMONT) ----------------------------------
------------------------------------------------------------------------------------------------		

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'VT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'VT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
	
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'VT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'VT'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
		

END IF;
END
$do$;		
------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(WEST VIRGINIA) ----------------------------------
------------------------------------------------------------------------------------------------	

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WV'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WV'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
	
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WV'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WV'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
		

END IF;
END
$do$;				
------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(WISCONSIN) ----------------------------------
------------------------------------------------------------------------------------------------		

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
	
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 21, 'Student moved to another country, may or may not be continuing enrollment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 15, 'Transfer to an accredited or non - accredited juvenile correctional facility where educational services are provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 20, 'Transfer to juvenile or adult correctional facility-no', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 6, 'Transfer to home schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 8, 'Graduated with rregular diploma', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 9, 'Completed school with other credentials (e.g district awarded GED)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 10, 'Student death', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 11, 'Student illness', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 12, 'Student expulsion (or long term suspension)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 13, 'Reached maximum age for services', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 14, 'Discontinued schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 17, 'Unknown educational services provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 16, 'Moved within the United States, not known to be enrolled in school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 18, 'Student data claimed in error /never attended', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 19, 'Transfer to an adult education facility (i.e., for GED completion)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 7, 'Matriculation to another school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 30, 'Student no longer meets eligibility criteria for alternate assessment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 98, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'WI'), (select id from assessmentprogram where abbreviatedname = 'DLM'), false, 2019);	
		

END IF;
END
$do$;		
------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(NY TRAINING) ----------------------------------
------------------------------------------------------------------------------------------------	

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN		
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NYTRAIN'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NYTRAIN'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 3, 'Transfer to a public school in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NYTRAIN'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);			

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NYTRAIN'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NYTRAIN'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 6, 'Transfer to home schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NYTRAIN'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 10, 'Student death', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NYTRAIN'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 13, 'Reached maximum age for services', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NYTRAIN'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 14, 'Discontinued schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NYTRAIN'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 17, 'Unknown educational services provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NYTRAIN'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 16, 'Moved within the United States, not known to be enrolled in school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NYTRAIN'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 18, 'Student data claimed in error /never attended', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NYTRAIN'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 30, 'Student no longer meets eligibility criteria for alternate assessment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NYTRAIN'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 98, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'NYTRAIN'), (select id from assessmentprogram where abbreviatedname = 'DLM'), false, 2019);	
			

END IF;
END
$do$;	
------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(DLM QC) ----------------------------------
------------------------------------------------------------------------------------------------	

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'DLMQCST'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'DLMQCST'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
	
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'DLMQCST'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'DLMQCST'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
		

END IF;
END
$do$;				
------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(DLM QC EOY) ----------------------------------
------------------------------------------------------------------------------------------------	

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN		
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'DLMQCEOYST'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'DLMQCEOYST'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
	
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'DLMQCEOYST'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'DLMQCEOYST'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
			

END IF;
END
$do$;					
------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(DLM QC IM) ----------------------------------
------------------------------------------------------------------------------------------------	

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN		
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'DLMQCIMST'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'DLMQCIMST'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
	
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'DLMQCIMST'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'DLMQCIMST'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
		

END IF;
END
$do$;
------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(DLM QC YE) ----------------------------------
------------------------------------------------------------------------------------------------	

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN		
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'DLMQCYEST'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'DLMQCYEST'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
	
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'DLMQCYEST'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'DLMQCYEST'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
		

END IF;
END
$do$;
------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(SERVICE DESK QC) ----------------------------------
------------------------------------------------------------------------------------------------		

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN	
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'SDQA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'SDQA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
	
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'SDQA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'SDQA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 21, 'Student moved to another country, may or may not be continuing enrollment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'SDQA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 15, 'Transfer to an accredited or non - accredited juvenile correctional facility where educational services are provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'SDQA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 20, 'Transfer to juvenile or adult correctional facility-no', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'SDQA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 6, 'Transfer to home schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'SDQA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 8, 'Graduated with rregular diploma', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'SDQA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 9, 'Completed school with other credentials (e.g district awarded GED)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'SDQA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 10, 'Student death', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'SDQA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 11, 'Student illness', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'SDQA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 12, 'Student expulsion (or long term suspension)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'SDQA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 13, 'Reached maximum age for services', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'SDQA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 14, 'Discontinued schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'SDQA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 17, 'Unknown educational services provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'SDQA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 16, 'Moved within the United States, not known to be enrolled in school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'SDQA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 18, 'Student data claimed in error /never attended', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'SDQA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 19, 'Transfer to an adult education facility (i.e., for GED completion)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'SDQA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 7, 'Matriculation to another school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'SDQA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 30, 'Student no longer meets eligibility criteria for alternate assessment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'SDQA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 98, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'SDQA'), (select id from assessmentprogram where abbreviatedname = 'DLM'), false, 2019);	
		

END IF;
END
$do$;		
------------------------------------------------------------------------------------------------
--------- DLM state specific exit codes in schoolYear(QA QC) ----------------------------------
------------------------------------------------------------------------------------------------		

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='DLM') THEN	
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'QAQC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'QAQC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
	
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'QAQC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'QAQC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 21, 'Student moved to another country, may or may not be continuing enrollment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'QAQC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 15, 'Transfer to an accredited or non - accredited juvenile correctional facility where educational services are provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'QAQC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 20, 'Transfer to juvenile or adult correctional facility-no', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'QAQC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 6, 'Transfer to home schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'QAQC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 8, 'Graduated with rregular diploma', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'QAQC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 9, 'Completed school with other credentials (e.g district awarded GED)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'QAQC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 10, 'Student death', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'QAQC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 11, 'Student illness', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'QAQC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 12, 'Student expulsion (or long term suspension)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'QAQC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 13, 'Reached maximum age for services', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'QAQC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 14, 'Discontinued schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'QAQC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 17, 'Unknown educational services provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'QAQC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 16, 'Moved within the United States, not known to be enrolled in school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'QAQC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 18, 'Student data claimed in error /never attended', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'QAQC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 19, 'Transfer to an adult education facility (i.e., for GED completion)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'QAQC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 7, 'Matriculation to another school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'QAQC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 30, 'Student no longer meets eligibility criteria for alternate assessment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'QAQC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 98, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'QAQC'), (select id from assessmentprogram where abbreviatedname = 'DLM'), false, 2019);	
				

END IF;
END
$do$;		



-------------------------------------------------------------------------------------------------
------------------------------------ I-SMART default subsets of exit codes --------------------------
-------------------------------------------------------------------------------------------------

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='I-SMART') THEN

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 3, 'Transfer to a public school in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), true);			

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 6, 'Transfer to home schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 7, 'Matriculation to another school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 8, 'Graduated with rregular diploma', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 9, 'Completed school with other credentials (e.g district awarded GED)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 10, 'Student death', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 11, 'Student illness', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 12, 'Student expulsion (or long term suspension)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 13, 'Reached maximum age for services', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 14, 'Discontinued schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 15, 'Transfer to an accredited or non - accredited juvenile correctional facility where educational services are provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 16, 'Moved within the United States, not known to be enrolled in school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 17, 'Unknown educational services provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 18, 'Student data claimed in error /never attended', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 19, 'Transfer to an adult education facility (i.e., for GED completion)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 20, 'Transfer to juvenile or adult correctional facility-no', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 21, 'Student moved to another country, may or may not be continuing enrollment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 22, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), false);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 23, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), false);		
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 30, 'Student no longer meets eligibility criteria for alternate assessment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 98, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), false);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 99, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART'), false);	

END IF;
END
$do$;	


-------------------------------------------------------------------------------------------------
------------------------------------ I-SMART2 default subsets of exit codes --------------------------
-------------------------------------------------------------------------------------------------

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='I-SMART2') THEN

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 3, 'Transfer to a public school in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), true);			

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 6, 'Transfer to home schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 7, 'Matriculation to another school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 8, 'Graduated with rregular diploma', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 9, 'Completed school with other credentials (e.g district awarded GED)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 10, 'Student death', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 11, 'Student illness', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 12, 'Student expulsion (or long term suspension)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 13, 'Reached maximum age for services', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 14, 'Discontinued schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 15, 'Transfer to an accredited or non - accredited juvenile correctional facility where educational services are provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 16, 'Moved within the United States, not known to be enrolled in school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 17, 'Unknown educational services provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 18, 'Student data claimed in error /never attended', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 19, 'Transfer to an adult education facility (i.e., for GED completion)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 20, 'Transfer to juvenile or adult correctional facility-no', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 21, 'Student moved to another country, may or may not be continuing enrollment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 22, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), false);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 23, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), false);		
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 30, 'Student no longer meets eligibility criteria for alternate assessment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 98, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), false);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 99, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'I-SMART2'), false);	

END IF;
END
$do$;


-------------------------------------------------------------------------------------------------
------------------------------------ MSS default subsets of exit codes --------------------------
-------------------------------------------------------------------------------------------------

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='MSS') THEN

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 3, 'Transfer to a public school in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), true);			

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 6, 'Transfer to home schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 7, 'Matriculation to another school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 8, 'Graduated with rregular diploma', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 9, 'Completed school with other credentials (e.g district awarded GED)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 10, 'Student death', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 11, 'Student illness', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 12, 'Student expulsion (or long term suspension)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 13, 'Reached maximum age for services', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 14, 'Discontinued schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 15, 'Transfer to an accredited or non - accredited juvenile correctional facility where educational services are provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 16, 'Moved within the United States, not known to be enrolled in school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 17, 'Unknown educational services provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 18, 'Student data claimed in error /never attended', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 19, 'Transfer to an adult education facility (i.e., for GED completion)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 20, 'Transfer to juvenile or adult correctional facility-no', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 21, 'Student moved to another country, may or may not be continuing enrollment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 22, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), false);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 23, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), false);		
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 30, 'Student no longer meets eligibility criteria for alternate assessment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 98, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), false);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 99, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'MSS'), false);	

END IF;
END
$do$;
	
-------------------------------------------------------------------------------------------------
------------------------------------ PLTW default subsets of exit codes --------------------------
-------------------------------------------------------------------------------------------------

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='PLTW') THEN

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 3, 'Transfer to a public school in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), true);			

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 6, 'Transfer to home schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 7, 'Matriculation to another school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 8, 'Graduated with rregular diploma', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 9, 'Completed school with other credentials (e.g district awarded GED)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 10, 'Student death', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 11, 'Student illness', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 12, 'Student expulsion (or long term suspension)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 13, 'Reached maximum age for services', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 14, 'Discontinued schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 15, 'Transfer to an accredited or non - accredited juvenile correctional facility where educational services are provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 16, 'Moved within the United States, not known to be enrolled in school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 17, 'Unknown educational services provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 18, 'Student data claimed in error /never attended', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 19, 'Transfer to an adult education facility (i.e., for GED completion)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 20, 'Transfer to juvenile or adult correctional facility-no', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), true);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 21, 'Student moved to another country, may or may not be continuing enrollment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 22, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), false);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 23, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), false);		
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 30, 'Student no longer meets eligibility criteria for alternate assessment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), true);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 98, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), false);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, assessmentprogramid, isvalidforui)
VALUES ( 99, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from assessmentprogram where abbreviatedname = 'PLTW'), false);	

END IF;
END
$do$;

------------------------------------------------------------------------------------------------
------------------------------------ KAP state specific exit codes (KANSAS)--------------------------
------------------------------------------------------------------------------------------------

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='KAP') THEN

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district in state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 3, 'Transfer to a public school in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);			

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school in state or in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school in state or in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 6, 'Transfer to home schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);				

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 8, 'Graduated with rregular diploma', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);			

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 10, 'Student death', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 11, 'Student illness', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 12, 'Student expulsion (or long term suspension)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 13, 'Reached maximum age for services', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 14, 'Discontinued schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 15, 'Transfer to a juvenile or adult correctional facility where diploma completion services are provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 16, 'Moved within the US not known to be enrolled in school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 17, 'Unknown', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 18, 'Student data claimed in error by an ASGT record', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 19, 'Transfer to a GED completion program', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 20, 'Transferred to a juvenile or adult correctional facility where diploma completion services are not provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 21, 'Student moved to another country, may or may not be continuing enrollment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 22, 'Student with disabilities who met the district graduation requirements for regular diploma but is remaining in school to receive transitional services deemed necessary by the IEP team', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 23, 'Student with an extended absence at the beginning of the school year (through September 30), planning to return', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 98, 'Unresolved Exit', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 99, 'Undo previously submitted Exit', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);		

END IF;
END
$do$;

------------------------------------------------------------------------------------------------
------------------------------------ KAP state specific exit codes (KAP QC)--------------------------
------------------------------------------------------------------------------------------------

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='KAP') THEN

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district in state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 3, 'Transfer to a public school in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);			

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school in state or in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school in state or in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 6, 'Transfer to home schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);				

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 8, 'Graduated with rregular diploma', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);			

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 10, 'Student death', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 11, 'Student illness', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 12, 'Student expulsion (or long term suspension)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 13, 'Reached maximum age for services', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 14, 'Discontinued schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 15, 'Transfer to a juvenile or adult correctional facility where diploma completion services are provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 16, 'Moved within the US not known to be enrolled in school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 17, 'Unknown', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 18, 'Student data claimed in error by an ASGT record', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 19, 'Transfer to a GED completion program', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 20, 'Transferred to a juvenile or adult correctional facility where diploma completion services are not provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 21, 'Student moved to another country, may or may not be continuing enrollment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 22, 'Student with disabilities who met the district graduation requirements for regular diploma but is remaining in school to receive transitional services deemed necessary by the IEP team', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 23, 'Student with an extended absence at the beginning of the school year (through September 30), planning to return', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 98, 'Unresolved Exit', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 99, 'Undo previously submitted Exit', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KAP'), true, 2019);		

END IF;
END
$do$;






------------------------------------------------------------------------------------------------
------------------------------------ KELPA2 State specific exit codes (KANSAS)------------------
------------------------------------------------------------------------------------------------

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='KELPA2') THEN

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district in state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 3, 'Transfer to a public school in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);			

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school in state or in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school in state or in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 6, 'Transfer to home schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);				

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 7, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), false, 2019);		
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 8, 'Graduated with rregular diploma', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);			

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 9, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), false, 2019);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 10, 'Student death', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 11, 'Student illness', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 12, 'Student expulsion (or long term suspension)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 13, 'Reached maximum age for services', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 14, 'Discontinued schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 15, 'Transfer to a juvenile or adult correctional facility where diploma completion services are provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 16, 'Moved within the US not known to be enrolled in school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 17, 'Unknown', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 18, 'Student data claimed in error by an ASGT record', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 19, 'Transfer to a GED completion program', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 20, 'Transferred to a juvenile or adult correctional facility where diploma completion services are not provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 21, 'Student moved to another country, may or may not be continuing enrollment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 22, 'Student with disabilities who met the district graduation requirements for regular diploma but is remaining in school to receive transitional services deemed necessary by the IEP team', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 23, 'Student with an extended absence at the beginning of the school year (through September 30), planning to return', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 98, 'Unresolved Exit', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 99, 'Undo previously submitted Exit', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);		

END IF;
END
$do$;

------------------------------------------------------------------------------------------------
------------------------------------ KELPA2 state specific exit codes (KAP QC)--------------------------
------------------------------------------------------------------------------------------------

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='KELPA2') THEN

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district in state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 3, 'Transfer to a public school in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);			

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school in state or in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school in state or in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 6, 'Transfer to home schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);				

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 8, 'Graduated with rregular diploma', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);			

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 10, 'Student death', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 11, 'Student illness', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 12, 'Student expulsion (or long term suspension)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 13, 'Reached maximum age for services', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 14, 'Discontinued schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 15, 'Transfer to a juvenile or adult correctional facility where diploma completion services are provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 16, 'Moved within the US not known to be enrolled in school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 17, 'Unknown', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 18, 'Student data claimed in error by an ASGT record', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 19, 'Transfer to a GED completion program', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 20, 'Transferred to a juvenile or adult correctional facility where diploma completion services are not provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 21, 'Student moved to another country, may or may not be continuing enrollment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 22, 'Student with disabilities who met the district graduation requirements for regular diploma but is remaining in school to receive transitional services deemed necessary by the IEP team', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 23, 'Student with an extended absence at the beginning of the school year (through September 30), planning to return', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 98, 'Unresolved Exit', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 99, 'Undo previously submitted Exit', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KAPQCST' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'KELPA2'), true, 2019);		

END IF;
END
$do$;

------------------------------------------------------------------------------------------------
------------------------------------ CPASS state specific exit codes (KANSAS)--------------------------
------------------------------------------------------------------------------------------------

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='CPASS') THEN

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'CPASS'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district in state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'CPASS'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 3, 'Transfer to a public school in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'CPASS'), true, 2019);			

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school in state or in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'CPASS'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school in state or in a different state', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'CPASS'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 6, 'Transfer to home schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'CPASS'), true, 2019);				

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 8, 'Graduated with rregular diploma', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'CPASS'), true, 2019);			

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 10, 'Student death', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'CPASS'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 11, 'Student illness', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'CPASS'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 12, 'Student expulsion (or long term suspension)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'CPASS'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 13, 'Reached maximum age for services', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'CPASS'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 14, 'Discontinued schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'CPASS'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 15, 'Transfer to a juvenile or adult correctional facility where diploma completion services are provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'CPASS'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 16, 'Moved within the US not known to be enrolled in school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'CPASS'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 17, 'Unknown', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'CPASS'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 18, 'Student data claimed in error by an ASGT record', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'CPASS'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 19, 'Transfer to a GED completion program', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'CPASS'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 20, 'Transferred to a juvenile or adult correctional facility where diploma completion services are not provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'CPASS'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 21, 'Student moved to another country, may or may not be continuing enrollment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'CPASS'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 22, 'Student with disabilities who met the district graduation requirements for regular diploma but is remaining in school to receive transitional services deemed necessary by the IEP team', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'CPASS'), true, 2019);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 23, 'Student with an extended absence at the beginning of the school year (through September 30), planning to return', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'CPASS'), true, 2019);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 98, 'Unresolved Exit', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'CPASS'), true, 2019);

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag,  stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 99, 'Undo previously submitted Exit', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'KS' and organizationtypeid = 2 and activeflag is true), 
		(select id from assessmentprogram where abbreviatedname = 'CPASS'), true, 2019);		

END IF;
END
$do$;

------------------------------------------------------------------------------------------------
--------- MSS state specific exit codes in schoolYear(MSS) ------------
------------------------------------------------------------------------------------------------

DO
$do$
BEGIN
IF EXISTS (select id from assessmentprogram where abbreviatedname ='MSS') THEN

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 1, 'Transfer to a public school in the same district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MSS'), 
		(select id from assessmentprogram where abbreviatedname = 'MSS'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 2, 'Transfer to a public school in a different district', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MSS'), 
		(select id from assessmentprogram where abbreviatedname = 'MSS'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 4, 'Transfer to an accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MSS'), 
		(select id from assessmentprogram where abbreviatedname = 'MSS'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 5, 'Transfer to a non-accredited private school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MSS'), 
		(select id from assessmentprogram where abbreviatedname = 'MSS'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 6, 'Transfer to home schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MSS'), 
		(select id from assessmentprogram where abbreviatedname = 'MSS'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 7, 'Matriculation to another school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MSS'), 
		(select id from assessmentprogram where abbreviatedname = 'MSS'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 8, 'Graduated with rregular diploma', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MSS'), 
		(select id from assessmentprogram where abbreviatedname = 'MSS'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 9, 'Completed school with other credentials (e.g district awarded GED)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MSS'), 
		(select id from assessmentprogram where abbreviatedname = 'MSS'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 10, 'Student death', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MSS'), 
		(select id from assessmentprogram where abbreviatedname = 'MSS'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 11, 'Student illness', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MSS'), 
		(select id from assessmentprogram where abbreviatedname = 'MSS'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 12, 'Student expulsion (or long term suspension)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MSS'), 
		(select id from assessmentprogram where abbreviatedname = 'MSS'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 13, 'Reached maximum age for services', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MSS'), 
		(select id from assessmentprogram where abbreviatedname = 'MSS'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 14, 'Discontinued schooling', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MSS'), 
		(select id from assessmentprogram where abbreviatedname = 'MSS'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 15, 'Transfer to an accredited or non - accredited juvenile correctional facility where educational services are provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MSS'), 
		(select id from assessmentprogram where abbreviatedname = 'MSS'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 16, 'Moved within the United States, not known to be enrolled in school', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MSS'), 
		(select id from assessmentprogram where abbreviatedname = 'MSS'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 17, 'Unknown educational services provided', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MSS'), 
		(select id from assessmentprogram where abbreviatedname = 'MSS'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 18, 'Student data claimed in error /never attended', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MSS'), 
		(select id from assessmentprogram where abbreviatedname = 'MSS'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 19, 'Transfer to an adult education facility (i.e., for GED completion)', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MSS'), 
		(select id from assessmentprogram where abbreviatedname = 'MSS'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 20, 'Transfer to juvenile or adult correctional facility-no', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MSS'), 
		(select id from assessmentprogram where abbreviatedname = 'MSS'), true, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 21, 'Student moved to another country, may or may not be continuing enrollment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MSS'), 
		(select id from assessmentprogram where abbreviatedname = 'MSS'), true, 2019);		

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 22, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MSS'), 
		(select id from assessmentprogram where abbreviatedname = 'MSS'), false, 2019);	

INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 23, 'N/A', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MSS'), 
		(select id from assessmentprogram where abbreviatedname = 'MSS'), false, 2019);
		
INSERT INTO public.studentexitcodes(
	code, description, createduser, modifieduser, activeflag, stateid, assessmentprogramid, isvalidforui, schoolyear)
VALUES ( 30, 'Student no longer meets eligibility criteria for alternate assessment', 
		(Select id from aartuser where username = 'cetesysadmin'), 
		(Select id from aartuser where username = 'cetesysadmin'), 
		true, (select id from organization where displayidentifier = 'MSS'), 
		(select id from assessmentprogram where abbreviatedname = 'MSS'), true, 2019);			

END IF;
END
$do$;



