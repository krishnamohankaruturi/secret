-- DML for F417, F458 and F459
INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('DATA_EXTRACTS_DLM_Gen_Research', 'Create DLM General Research File Data Extract', 
    'Reports-Data Extracts',now(),(select id from aartuser where email='cete@ku.edu'), true, 
            now(),(select id from aartuser where email='cete@ku.edu'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('DATA_EXTRACTS_DLM_SPEC_CIRCUM', 'Create DLM Special Circumstances File Data Extract', 
    'Reports-Data Extracts',now(),(select id from aartuser where email='cete@ku.edu'), true, 
            now(),(select id from aartuser where email='cete@ku.edu'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('DATA_EXTRACTS_DLM_INCIDENT', 'Create DLM Incident File Data Extract', 
    'Reports-Data Extracts',now(),(select id from aartuser where email='cete@ku.edu'), true, 
            now(),(select id from aartuser where email='cete@ku.edu'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser, 
			activeflag, modifieddate, modifieduser)
	VALUES ('VIEW_UPLOAD_RESULTS', 'Upload Results Access',
	'Reports-Performance Reports',now(),(select id from aartuser where email='cete@ku.edu'), true, 
		now(),(select id from aartuser where email='cete@ku.edu'));

INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'UploadCommonGrffile', 'UPLOAD_GRF_FILE_TYPE', 'To insert general reaSearch file data into uploadgrffile table',
    (select id from  categorytype where typecode ='CSV_RECORD_TYPE' ),null, null, now(), (select id from aartuser where username = 'cetesysadmin'), true,now(), (select id from aartuser where username = 'cetesysadmin'));

INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'UploadNewYorkGrffile', 'UPLOAD_NY_GRF_FILE_TYPE', 'To insert NewYork general reaSearch file data into uploadgrffile table',(select id from  categorytype where typecode ='CSV_RECORD_TYPE' ),null, null, now(), (select id from aartuser where username = 'cetesysadmin'), true,now(), (select id from aartuser where username = 'cetesysadmin'));

INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'UploadIowaGrffile', 'UPLOAD_IA_GRF_FILE_TYPE', 'To insert Iowa general reaSearch file data into uploadgrffile table',(select id from  categorytype where typecode ='CSV_RECORD_TYPE' ),null, null, now(), (select id from aartuser where username = 'cetesysadmin'), true,now(), (select id from aartuser where username = 'cetesysadmin'));

INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'UploadKansasSCcodefile', 'UPLOAD_KS_SC_CODE_FILE_TYPE', 'To insert Kansas SC Code file data into uploadsccodefile table', (select id from  categorytype where typecode ='CSV_RECORD_TYPE' ), 
            null, null, now(), (select id from aartuser where username = 'cetesysadmin'), true, 
            now(), (select id from aartuser where username = 'cetesysadmin'));

INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'UploadCommonSCcodefile', 'UPLOAD_SC_CODE_FILE_TYPE', 'To insert Common SC Code file data into uploadsccodefile table', (select id from  categorytype where typecode ='CSV_RECORD_TYPE' ),null, null, now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'));

 INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'UploadIncidentFile', 'UPLOAD_INCIDENT_FILE_TYPE', 'To insert incident file data into uploadincidentfile table ', (select id from categorytype where typecode  ='CSV_RECORD_TYPE'), 
            null, null, now(), (select id from aartuser where username = 'cetesysadmin'), true, 
            now(), (select id from aartuser where username = 'cetesysadmin'));
            

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee26', '{0,1,2,3,4,5,9}', null, null, 1, 
            false, true, null, 'EE_26', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee25', '{0,1,2,3,4,5,9}', null, null, 1, 
            false,true, null, 'EE_25', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee24', '{0,1,2,3,4,5,9}', null, null, 1, 
            false, true, null, 'EE_24', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee23', '{0,1,2,3,4,5,9}', null, null, 1, 
            false, true, null, 'EE_23', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee22', '{0,1,2,3,4,5,9}', null, null, 1, 
            false, true, null, 'EE_22', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee21', '{0,1,2,3,4,5,9}', null, null, 1, 
            false, true, null, 'EE_21', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee20', '{0,1,2,3,4,5,9}', null, null, 1, 
            false, true, null, 'EE_20', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee19', '{0,1,2,3,4,5,9}', null, null, 1, 
            false, true, null, 'EE_19', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee18', '{0,1,2,3,4,5,9}', null, null, 1, 
            false, true, null, 'EE_18', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee17', '{0,1,2,3,4,5,9}', null, null, 1, 
            false, true, null, 'EE_17', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee16', '{0,1,2,3,4,5,9}', null, null, 1, 
            false, true, null, 'EE_16', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee15', '{0,1,2,3,4,5,9}', null, null, 1, 
            false, true, null, 'EE_15', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee14', '{0,1,2,3,4,5,9}', null, null, 1, 
            false, true, null, 'EE_14', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee13', '{0,1,2,3,4,5,9}', null, null, 1, 
            false, true, null, 'EE_13', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee12', '{0,1,2,3,4,5,9}', null, null, 1, 
            false, true, null, 'EE_12', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee11', '{0,1,2,3,4,5,9}', null, null, 1, 
            false, true, null, 'EE_11', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee10', '{0,1,2,3,4,5,9}', null, null, 1, 
            false, true, null, 'EE_10', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee9', '{0,1,2,3,4,5,9}', null, null, 1, 
            false, true, null, 'EE_9', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee8', '{0,1,2,3,4,5,9}', null, null, 1, 
            false, true, null, 'EE_8', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee7', '{0,1,2,3,4,5,9}', null, null, 1, 
            false, true, null, 'EE_7', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee6', '{0,1,2,3,4,5,9}', null, null, 1, 
            false, true, null, 'EE_6', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee5', '{0,1,2,3,4,5,9}', null, null, 1, 
            false, true, null, 'EE_5', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee4', '{0,1,2,3,4,5,9}', null, null, 1, 
            false, true, null, 'EE_4', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee3', '{0,1,2,3,4,5,9}', null, null, 1, 
            false, true, null, 'EE_3', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee2', '{0,1,2,3,4,5,9}', null, null, 1, 
            false, true, null, 'EE_2', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ee1', '{0,1,2,3,4,5,9}', null, null, null, 
            false, true, null, 'EE_1', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);
            
INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'iowaLinkageLevelsMastered', null, null, null, null, 
            false, false, null, 'Iowa_Linkage_Levels_Mastered', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'),false, null, null, null);


INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'totalLinkageLevelsMastered', null, null, null, null, 
            false, false, null, 'Total_Linkage_Levels_Mastered', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'),false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'nyPerformanceLevel', '{1,2,3,4,9}', null, null, 1, 
            true, true, null, 'Ny_Performance_Level', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'),false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'performanceLevel', '{1,2,3,4,9}', null, null, 1, 
            true, true, null, 'Performance_Level', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'sgp', null, null, null, 3, 
            true, true, null, 'SGP', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);
            
INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'finalBand', '{Foundational,Band 1,Band 2,Band 3}', null, null, 150, 
            true, true, null, 'Final_Band', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);


INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'subject', '{ELA,Math,M,SCI,SS}', null, null, 4, 
            true, true, null, 'Subject', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'kiteEducatorIdentifier', null, null, null, null, 
            true, true, null, 'Kite_Educator_Identifier', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);


INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'educatorUserName', null, null, null, 254, 
            true, true, null, 'Educator_User_Name', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'schoolName', null, null, null, 100, 
            true, true, null, 'School_Name', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'districtName', null, null, null, 100, 
            true, true, null, 'District_Name', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);


INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'gender', '{0,1}', null, null, 100, 
            false, true, null, 'Gender_With_Blank', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'userName', null, null, null, 100, 
            true, true, null, 'User_Name', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);
            
INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'studentId', null, null, null, 10, 
            true, true, null, 'Student_Id', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'invalidationCode', '{0,1}', null, null, 1, 
            true, true, null, 'Invalidation_Code', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'issueCode', null, null, null, 2, 
            true, true, null, 'Issue_Code', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);
		
INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'essentialElement', null, null, null, 10, 
            true, true, null, 'Essential_Element', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

--Field Specification

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'specialCircumstanceCode', null, null, null, 5, 
            true, true, null, 'Special_Circumstance_Code', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'ksdeScCode', null, null, null, 10, 
            true, true, null, 'Ksde_Sc_Code', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'specialCircumstanceLabel', null, null, null, 1000, 
            true, true, null, 'Special_Circumstance_Label', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'assessment', null, null, null, 5, 
            true, true, null, 'Assessment', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);


 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='studentId' and mappedname='Student_Id' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Studentid');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='stateStudentIdentifier' and mappedname is null and activeflag=true and rejectifempty=true ), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'State_Student_Identifier');
         

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='aypSchoolIdentifier' and mappedname='AYP_QPA_Bldg_No' and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Ayp_School_Identifier');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='currentGradeLevel' and mappedname='enrollment/gradeLevel' and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Current_Grade_Level');
          
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='legalFirstName' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Student_Legal_First_Name');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='legalMiddleName' and mappedname is null and activeflag= true and rejectifempty=false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Student_Legal_Middle_Name');
            

            
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='legalLastName' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Student_Legal_Last_Name');


 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='generationCode' and mappedname is null and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Generation_Code');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='userName' and mappedname='User_Name' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'User_Name');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='firstLanguage' and mappedname='First_Language' and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'First_Language');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='dateOfBirth' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Date_Of_Birth');
 
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='gender' and mappedname= 'Gender_With_Blank' and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Gender');
            
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='comprehensiveRace' and mappedname='demographics/races/race/race' and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Comprehensive_Race');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='hispanicEthnicity' and mappedname='Hispanic_Ethnicity' and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Hispanic_Ethnicity');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='primaryDisabilityCode' and mappedname='Primary_Disability_Code' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Primary_Disability_Code');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='esolParticipationCode' and mappedname='ESOL_Participation_Code' and activeflag=true and rejectifempty= true), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Esol_Participation_Code');
 
  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='schoolEntryDate' and mappedname is null and activeflag=true  and rejectifempty= true), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'School_Entry_Date');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES (( select id  from fieldspecification where fieldname='districtEntryDate' and mappedname is null and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'District_Entry_Date');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='stateEntryDate' and mappedname is null and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'State_Entry_Date');

 
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='attendanceSchoolProgramIdentifier' and mappedname='enrollment/schoolRefId' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Attendance_School_Program_Identifier');
  

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='state' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'State');
           

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='residenceDistrictIdentifier' and mappedname is null and activeflag=true and rejectifempty= true), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'District_Code');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='districtName' and mappedname='District_Name' and activeflag=true and rejectifempty= true ), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'District');
           

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='schoolIdentifier' and mappedname is null and activeflag=true and rejectifempty= true ), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'School_Code');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='schoolName' and mappedname='School_Name' and activeflag=true and rejectifempty= true), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'School');
 
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='educatorFirstName' and mappedname is null and activeflag=true and rejectifempty=true ), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Educator_First_Name');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='educatorLastName' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Educator_Last_Name');
         

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='educatorUserName' and mappedname='Educator_User_Name' and activeflag=true and rejectifempty= true ), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Educator_User_Name');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='educatorIdentifier' and mappedname is null and activeflag=true and rejectifempty= true ), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Educator_Identifier');

          
            

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='kiteEducatorIdentifier' and mappedname='Kite_Educator_Identifier' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Kite_Educator_Identifier');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='subject' and mappedname='Subject' and activeflag=true and rejectifempty= true), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Subject');

           
            

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='finalBand' and mappedname='Final_Band' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Final_Band');
   

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='sgp' and mappedname='SGP' and activeflag=true 
            and rejectifempty=  true), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'SGP');
         

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='performanceLevel' and mappedname='Performance_Level' and activeflag=true and rejectifempty= true), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Performance_Level');
            

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='invalidationCode' and mappedname='Invalidation_Code' and activeflag=true and rejectifempty= true), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Invalidation_Code');


  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee1' and mappedname='EE_1' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_1');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee2' and mappedname='EE_2' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_2');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee3' and mappedname='EE_3' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_3');


  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee4' and mappedname='EE_4' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_4');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee5' and mappedname='EE_5' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_5');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee6' and mappedname='EE_6' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_6');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee7' and mappedname='EE_7' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_7');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee8' and mappedname='EE_8' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_8');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee9' and mappedname='EE_9' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_9');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee10' and mappedname='EE_10' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_10');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee11' and mappedname='EE_11' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_11');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee12' and mappedname='EE_12' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_12');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee13' and mappedname='EE_13' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_13');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee14' and mappedname='EE_14' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_14');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee15' and mappedname='EE_15' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_15');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee16' and mappedname='EE_16' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_16');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee17' and mappedname='EE_17' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_17');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee18' and mappedname='EE_18' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_18');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee19' and mappedname='EE_19' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_19');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee20' and mappedname='EE_20' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_20');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee21' and mappedname='EE_21' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_21');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee22' and mappedname='EE_22' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_22');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee23' and mappedname='EE_23' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_23');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee24' and mappedname='EE_24' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_24');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee25' and mappedname='EE_25' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_25');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee26' and mappedname='EE_26' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_26');

            
----GRF IA FieldSpecificreocrdtypeI
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='studentId' and mappedname='Student_Id' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Studentid');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='stateStudentIdentifier' and mappedname is null and activeflag=true and rejectifempty=true ), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'State_Student_Identifier');
         

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='aypSchoolIdentifier' and mappedname='AYP_QPA_Bldg_No' and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Ayp_School_Identifier');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='currentGradeLevel' and mappedname='enrollment/gradeLevel' and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Current_Grade_Level');
          
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='legalFirstName' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Student_Legal_First_Name');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='legalMiddleName' and mappedname is null and activeflag= true and rejectifempty=false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Student_Legal_Middle_Name');
            

            
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='legalLastName' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Student_Legal_Last_Name');


 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='generationCode' and mappedname is null and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Generation_Code');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='userName' and mappedname='User_Name' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'User_Name');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='firstLanguage' and mappedname='First_Language' and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'First_Language');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='dateOfBirth' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Date_Of_Birth');
 
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='gender' and mappedname= 'Gender_With_Blank' and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Gender');
            
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='comprehensiveRace' and mappedname='demographics/races/race/race' and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Comprehensive_Race');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='hispanicEthnicity' and mappedname='Hispanic_Ethnicity' and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Hispanic_Ethnicity');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='primaryDisabilityCode' and mappedname='Primary_Disability_Code' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Primary_Disability_Code');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='esolParticipationCode' and mappedname='ESOL_Participation_Code' and activeflag=true and rejectifempty= true), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Esol_Participation_Code');
 
  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='schoolEntryDate' and mappedname is null and activeflag=true  and rejectifempty= true), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'School_Entry_Date');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES (( select id  from fieldspecification where fieldname='districtEntryDate' and mappedname is null and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'District_Entry_Date');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='stateEntryDate' and mappedname is null and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'State_Entry_Date');

 
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='attendanceSchoolProgramIdentifier' and mappedname='enrollment/schoolRefId' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Attendance_School_Program_Identifier');
  

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='state' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'State');
           

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='residenceDistrictIdentifier' and mappedname is null and activeflag=true and rejectifempty= true), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'District_Code');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='districtName' and mappedname='District_Name' and activeflag=true and rejectifempty= true ), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'District');
           

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='schoolIdentifier' and mappedname is null and activeflag=true and rejectifempty= true ), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'School_Code');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='schoolName' and mappedname='School_Name' and activeflag=true and rejectifempty= true), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'School');
 
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='educatorFirstName' and mappedname is null and activeflag=true and rejectifempty=true ), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Educator_First_Name');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='educatorLastName' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Educator_Last_Name');
         

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='educatorUserName' and mappedname='Educator_User_Name' and activeflag=true and rejectifempty= true ), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Educator_User_Name');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='educatorIdentifier' and mappedname is null and activeflag=true and rejectifempty= true ), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Educator_Identifier');
         

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='kiteEducatorIdentifier' and mappedname='Kite_Educator_Identifier' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Kite_Educator_Identifier');
            
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='exitWithdrawalDate' and mappedname is null and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Exit_Withdrawal_Date');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='exitWithdrawalType' and mappedname='Exit_Withdrawal_Type' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Exit_Withdrawal_Code');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='subject' and mappedname='Subject' and activeflag=true and rejectifempty= true), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Subject');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='finalBand' and mappedname='Final_Band' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Final_Band');
   

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='sgp' and mappedname='SGP' and activeflag=true 
            and rejectifempty=  true), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'SGP');
         

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='performanceLevel' and mappedname='Performance_Level' and activeflag=true and rejectifempty= true), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Performance_Level');
            

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='invalidationCode' and mappedname='Invalidation_Code' and activeflag=true and rejectifempty= true), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Invalidation_Code');
            
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='totalLinkageLevelsMastered' and mappedname='Total_Linkage_Levels_Mastered' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Total_Linkage_Levels_Mastered');
 
  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='iowaLinkageLevelsMastered' and mappedname='Iowa_Linkage_Levels_Mastered' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Iowa_Linkage_Levels_Mastered');
          
   INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee1' and mappedname='EE_1' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_1');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee2' and mappedname='EE_2' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_2');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee3' and mappedname='EE_3' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_3');


  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee4' and mappedname='EE_4' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_4');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee5' and mappedname='EE_5' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_5');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee6' and mappedname='EE_6' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_6');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee7' and mappedname='EE_7' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_7');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee8' and mappedname='EE_8' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_8');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee9' and mappedname='EE_9' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_9');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee10' and mappedname='EE_10' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_10');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee11' and mappedname='EE_11' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_11');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee12' and mappedname='EE_12' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_12');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee13' and mappedname='EE_13' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_13');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee14' and mappedname='EE_14' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_14');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee15' and mappedname='EE_15' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_15');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee16' and mappedname='EE_16' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_16');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee17' and mappedname='EE_17' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_17');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee18' and mappedname='EE_18' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_18');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee19' and mappedname='EE_19' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_19');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee20' and mappedname='EE_20' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_20');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee21' and mappedname='EE_21' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_21');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee22' and mappedname='EE_22' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_22');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee23' and mappedname='EE_23' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_23');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee24' and mappedname='EE_24' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_24');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee25' and mappedname='EE_25' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_25');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee26' and mappedname='EE_26' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_26');

-------GRF NY FieldSpecificreocrdtype


 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='studentId' and mappedname='Student_Id' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Studentid');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='stateStudentIdentifier' and mappedname is null and activeflag=true and rejectifempty=true ), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'State_Student_Identifier');
         
INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='localStudentIdentifier' and mappedname ='Local_Student_Identifier' and activeflag=true and rejectifempty=false ), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Local_Student_Identifier');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='aypSchoolIdentifier' and mappedname='AYP_QPA_Bldg_No' and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Ayp_School_Identifier');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='currentGradeLevel' and mappedname='enrollment/gradeLevel' and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Current_Grade_Level');
          
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='legalFirstName' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Student_Legal_First_Name');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='legalMiddleName' and mappedname is null and activeflag= true and rejectifempty=false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Student_Legal_Middle_Name');
            

            
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='legalLastName' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Student_Legal_Last_Name');


 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='generationCode' and mappedname is null and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Generation_Code');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='userName' and mappedname='User_Name' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'User_Name');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='firstLanguage' and mappedname='First_Language' and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'First_Language');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='dateOfBirth' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Date_Of_Birth');
 
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='gender' and mappedname= 'Gender_With_Blank' and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Gender');
            
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='comprehensiveRace' and mappedname='demographics/races/race/race' and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Comprehensive_Race');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='hispanicEthnicity' and mappedname='Hispanic_Ethnicity' and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Hispanic_Ethnicity');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='primaryDisabilityCode' and mappedname='Primary_Disability_Code' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Primary_Disability_Code');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='esolParticipationCode' and mappedname='ESOL_Participation_Code' and activeflag=true and rejectifempty= true), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Esol_Participation_Code');
 
  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='schoolEntryDate' and mappedname is null and activeflag=true  and rejectifempty= true), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'School_Entry_Date');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES (( select id  from fieldspecification where fieldname='districtEntryDate' and mappedname is null and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'District_Entry_Date');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='stateEntryDate' and mappedname is null and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'State_Entry_Date');

 
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='attendanceSchoolProgramIdentifier' and mappedname='enrollment/schoolRefId' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Attendance_School_Program_Identifier');
  

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='state' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'State');
           

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='residenceDistrictIdentifier' and mappedname is null and activeflag=true and rejectifempty= true), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'District_Code');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='districtName' and mappedname='District_Name' and activeflag=true and rejectifempty= true ), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'District');
           

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='schoolIdentifier' and mappedname is null and activeflag=true and rejectifempty= true ), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'School_Code');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='schoolName' and mappedname='School_Name' and activeflag=true and rejectifempty= true), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'School');
 
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='educatorFirstName' and mappedname is null and activeflag=true and rejectifempty=true ), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Educator_First_Name');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='educatorLastName' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Educator_Last_Name');
         

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='educatorUserName' and mappedname='Educator_User_Name' and activeflag=true and rejectifempty= true ), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Educator_User_Name');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='educatorIdentifier' and mappedname is null and activeflag=true and rejectifempty= true ), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Educator_Identifier');
         

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='kiteEducatorIdentifier' and mappedname='Kite_Educator_Identifier' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Kite_Educator_Identifier');
            
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='subject' and mappedname='Subject' and activeflag=true and rejectifempty= true), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Subject');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='finalBand' and mappedname='Final_Band' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Final_Band');
   

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='sgp' and mappedname='SGP' and activeflag=true 
            and rejectifempty=  true), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'SGP');
         

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='performanceLevel' and mappedname='Performance_Level' and activeflag=true and rejectifempty= true), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Performance_Level');

   INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='nyPerformanceLevel' and mappedname='Ny_Performance_Level' and activeflag=true and rejectifempty= true), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Ny_Performance_Level');           

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='invalidationCode' and mappedname='Invalidation_Code' and activeflag=true and rejectifempty= true), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Invalidation_Code');
            
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='totalLinkageLevelsMastered' and mappedname='Total_Linkage_Levels_Mastered' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Total_Linkage_Levels_Mastered');
 
         
   INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee1' and mappedname='EE_1' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_1');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee2' and mappedname='EE_2' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_2');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee3' and mappedname='EE_3' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_3');


  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee4' and mappedname='EE_4' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_4');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee5' and mappedname='EE_5' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_5');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee6' and mappedname='EE_6' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_6');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee7' and mappedname='EE_7' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_7');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee8' and mappedname='EE_8' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_8');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee9' and mappedname='EE_9' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_9');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee10' and mappedname='EE_10' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_10');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee11' and mappedname='EE_11' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_11');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee12' and mappedname='EE_12' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_12');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee13' and mappedname='EE_13' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_13');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee14' and mappedname='EE_14' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_14');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee15' and mappedname='EE_15' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_15');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee16' and mappedname='EE_16' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_16');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee17' and mappedname='EE_17' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_17');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee18' and mappedname='EE_18' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_18');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee19' and mappedname='EE_19' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_19');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee20' and mappedname='EE_20' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_20');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee21' and mappedname='EE_21' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_21');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee22' and mappedname='EE_22' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_22');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee23' and mappedname='EE_23' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_23');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee24' and mappedname='EE_24' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_24');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee25' and mappedname='EE_25' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_25');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ee26' and mappedname='EE_26' and activeflag=true and rejectifempty= false), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'EE_26');


---Dlm incident file fieldspecificationsrecordtypes enrty 
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='studentId' and mappedname='Student_Id' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadIncidentFile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Studentid');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='state' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadIncidentFile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'State');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='stateStudentIdentifier' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadIncidentFile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'State_Student_Identifier');
			
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='legalFirstName' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadIncidentFile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Student_Legal_First_Name');
            
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='legalMiddleName' and mappedname is null and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadIncidentFile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Student_Legal_Middle_Name');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='legalLastName' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadIncidentFile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Student_Legal_Last_Name');
            
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='generationCode' and mappedname is null and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadIncidentFile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Generation_Code');
            
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='dateOfBirth' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadIncidentFile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Date_of_Birth');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='essentialElement' and mappedname='Essential_Element' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadIncidentFile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Essential_Element');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='issueCode' and mappedname='Issue_Code' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadIncidentFile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Issue_Code');

--Field Specification Record Types For UploadCommonSCcodefile
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='studentId' and mappedname='Student_Id' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadCommonSCcodefile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Studentid');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='state' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadCommonSCcodefile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'State');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='stateStudentIdentifier' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadCommonSCcodefile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'State_Student_Identifier');
   

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='legalFirstName' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadCommonSCcodefile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Student_Legal_First_Name');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='legalMiddleName' and mappedname is null and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadCommonSCcodefile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Student_Legal_Middle_Name');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
 VALUES ((select id  from fieldspecification where fieldname='legalLastName' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadCommonSCcodefile'), now(), (select id from aartuser where username = 'cetesysadmin'),true, now(), (select id from aartuser where username = 'cetesysadmin'),'Student_Legal_Last_Name');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='generationCode' and mappedname is null and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadCommonSCcodefile'), now(), (select id from aartuser where username = 'cetesysadmin'),true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Generation_Code');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='dateOfBirth' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadCommonSCcodefile'), now(), (select id from aartuser where username = 'cetesysadmin'),true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Date_of_Birth');
   

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='specialCircumstanceCode' and mappedname='Special_Circumstance_Code' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadCommonSCcodefile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Special_Circumstance_Code');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='specialCircumstanceLabel' and mappedname='Special_Circumstance_Label' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadCommonSCcodefile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Special_Circumstance_Label');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='essentialElement' and mappedname='Essential_Element' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadCommonSCcodefile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Essential_Element');



----Field Specification Record Types For UploadKansasSCcodefile

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='studentId' and mappedname='Student_Id' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadKansasSCcodefile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Studentid');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='state' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadKansasSCcodefile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'State');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='stateStudentIdentifier' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadKansasSCcodefile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'State_Student_Identifier');
   

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='legalFirstName' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadKansasSCcodefile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Student_Legal_First_Name');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='legalMiddleName' and mappedname is null and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadKansasSCcodefile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Student_Legal_Middle_Name');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='legalLastName' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadKansasSCcodefile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Student_Legal_Last_Name');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='generationCode' and mappedname is null and activeflag=true and rejectifempty=false), (select id from category where categoryname ='UploadKansasSCcodefile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Generation_Code');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='dateOfBirth' and mappedname is null and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadKansasSCcodefile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Date_of_Birth');
            
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='specialCircumstanceCode' and mappedname='Special_Circumstance_Code' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadKansasSCcodefile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Special_Circumstance_Code');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='specialCircumstanceLabel' and mappedname='Special_Circumstance_Label' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadKansasSCcodefile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Special_Circumstance_Label');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='ksdeScCode' and mappedname='Ksde_Sc_Code' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadKansasSCcodefile'), now(), (select id from aartuser where username = 'cetesysadmin'),true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Ksde_Sc_Code');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='assessment' and mappedname='Assessment' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadKansasSCcodefile'), now(), (select id from aartuser where username = 'cetesysadmin'),true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Assessment');
