--First Contact Survey - Sensory Capabilities- Hearing,VISION Sub Section
	
INSERT INTO surveysection(surveysectionname,surveysectioncode,surveysectiondescription,createduser,modifieduser)
	values('Sensory Capabilities','SENSORY_CAPABILITIES','Sensory Capabilities Section',
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));
	
INSERT INTO surveysection(surveysectionname,surveysectioncode,surveysectiondescription,parentsurveySectionid,createduser,modifieduser)
	values('Hearing','HEARING','Hearing Subsection',
	(Select id from surveysection where surveysectioncode='SENSORY_CAPABILITIES'),
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveysection(surveysectionname,surveysectioncode,surveysectiondescription,parentsurveySectionid,createduser,modifieduser)
	values('Vision','VISION','Vision Subsection',
	(Select id from surveysection where surveysectioncode='SENSORY_CAPABILITIES'),
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));
	
INSERT INTO surveysection(surveysectionname,surveysectioncode,surveysectiondescription,parentsurveySectionid,createduser,modifieduser)
	values('Braille & Visual Aid','BRAILLE_AND_VISUAL_AID','Braille & Visual Aid Subsection',
	(Select id from surveysection where surveysectioncode='SENSORY_CAPABILITIES'),
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));
	
	--Q19
	INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q19',97, 'Hearing', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 2,(Select id from surveysection where surveysectioncode='HEARING'));


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q19'), 1,'No known hearing loss', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q19'), 2,'Deaf or hard of hearing', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);
 	
 	--Q132

 	INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q132_1',98, 'Hearing:Select the types of corrective aid/assitance Supports: Mark all that apply-No Hearing aid,cochlear implant,or other hearing assistance', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 2,(Select id from surveysection where surveysectioncode='HEARING'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q132_2',99, 'Hearing:Select the types of corrective aid/assitance Supports: Mark all that apply-Uses unilateral hearing aid', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 2,(Select id from surveysection where surveysectioncode='HEARING'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q132_3',100, 'Hearing:Select the types of corrective aid/assitance Supports: Mark all that apply-Uses bilateral hearing aid', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 2,(Select id from surveysection where surveysectioncode='HEARING'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q132_4',101, 'Hearing:Select the types of corrective aid/assitance Supports: Mark all that apply-Has cochlear implant', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 2,(Select id from surveysection where surveysectioncode='HEARING'));


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q132_1'), 1,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q132_2'), 2,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q132_3'), 3,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q132_4'), 4,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 4);

--Q20
INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q20_1',102, 'Hearing: Mark all that apply-Uses personal or classroom amplification (e.g., personal FM device) ', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 2,(Select id from surveysection where surveysectioncode='HEARING'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q20_2',103, 'Hearing: Mark all that apply-Uses animated signing software (e.g., Sign for Me)', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 2,(Select id from surveysection where surveysectioncode='HEARING'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q20_3',104, 'Hearing: Mark all that apply-Uses oral language', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 2,(Select id from surveysection where surveysectioncode='HEARING'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q20_4',105, 'Hearing: Mark all that apply-Uses sign language', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 2,(Select id from surveysection where surveysectioncode='HEARING'));


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q20_1'), 1,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q20_2'), 2,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q20_3'), 3,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q20_4'), 4,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 4);
	
--Q22
	INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q22',106, 'Vision', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 3,(Select id from surveysection where surveysectioncode='VISION'));


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q22'), 1,'No known vision loss', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q22'), 2,'Normal vision with glasses or contact lenses', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q22'), 3,'Blind or low vision, including vision that is not completely corrected with glasses or contact lenses', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);
	
--Q133
INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q133',107, 'Vision: Select the type of corrective aid/assistance ', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 3,(Select id from surveysection where surveysectioncode='VISION'));


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q133'), 1,'Does NOT wear glasses or contact lenses ', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q133'), 2,'Wears glasses or contact lenses ', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

--Q23
INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q23_1',108, 'Vision: Mark all that apply-Requires enlarged print', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 3,(Select id from surveysection where surveysectioncode='VISION'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q23_2',109, 'Vision: Mark all that apply-Requires tactile graphics and symbols', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 3,(Select id from surveysection where surveysectioncode='VISION'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q23_3',110, 'Vision: Mark all that apply-Requires or uses Braille', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 3,(Select id from surveysection where surveysectioncode='VISION'));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q23_1'), 1,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q23_2'), 2,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q23_3'), 3,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);

--Q151

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q151_1',111, 'If the student reads Braille, select all options used for assessment purposes-Uncontracted Braille', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 4,(Select id from surveysection where surveysectioncode='BRAILLE_AND_VISUAL_AID'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q151_2',112, 'If the student reads Braille, select all options used for assessment purposes-Contracted Braille', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 4,(Select id from surveysection where surveysectioncode='BRAILLE_AND_VISUAL_AID'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q151_3',113, 'If the student reads Braille, select all options used for assessment purposes-Nemeth Code for mathematics or science ', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 4,(Select id from surveysection where surveysectioncode='BRAILLE_AND_VISUAL_AID'));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q151_1'), 1,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q151_2'), 2,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q151_3'), 3,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);
	
--Q24

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q24',114, 'If the student reads Braille, select the primary type of Braille used for assessment purposes', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 4,(Select id from surveysection where surveysectioncode='BRAILLE_AND_VISUAL_AID'));


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q24'), 1,'Uncontracted Braille', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q24'), 2,'Contracted Braille', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

--Q25
INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q25_1',115, 'Technological Visual Aids: Mark all that apply-Magnifier', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 4,(Select id from surveysection where surveysectioncode='BRAILLE_AND_VISUAL_AID'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q25_2',115, 'Technological Visual Aids: Mark all that apply-Computer screen magnifier (fits over standard monitor)', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 4,(Select id from surveysection where surveysectioncode='BRAILLE_AND_VISUAL_AID'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q25_3',114, 'Technological Visual Aids: Mark all that apply-Screen magnification software (e.g., Closeview for Mac, ZoomText)', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 4,(Select id from surveysection where surveysectioncode='BRAILLE_AND_VISUAL_AID'));

	INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q25_4',115, 'Technological Visual Aids: Mark all that apply-CCTV', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 4,(Select id from surveysection where surveysectioncode='BRAILLE_AND_VISUAL_AID'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q25_5',116, 'Technological Visual Aids: Mark all that apply-Screen reader', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 4,(Select id from surveysection where surveysectioncode='BRAILLE_AND_VISUAL_AID'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q25_6',117, 'Technological Visual Aids: Mark all that apply-Scanner with talking word processor', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 4,(Select id from surveysection where surveysectioncode='BRAILLE_AND_VISUAL_AID'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q25_7',118, 'Technological Visual Aids: Mark all that apply-Manual Braille writing device (e.g., Perkins Brailler)', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 4,(Select id from surveysection where surveysectioncode='BRAILLE_AND_VISUAL_AID'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q25_8',119, 'Technological Visual Aids: Mark all that apply-Electronic Braille writing device (e.g., Mountbatten Brailler)', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 4,(Select id from surveysection where surveysectioncode='BRAILLE_AND_VISUAL_AID'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q25_9',120, 'Technological Visual Aids: Mark all that apply-Device with refreshable Braille display', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 4,(Select id from surveysection where surveysectioncode='BRAILLE_AND_VISUAL_AID'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q25_10',121, 'Technological Visual Aids: Mark all that apply-Light box', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 4,(Select id from surveysection where surveysectioncode='BRAILLE_AND_VISUAL_AID'));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q25_1'), 1,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q25_2'), 2,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q25_3'), 3,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);
INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q25_4'), 4,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 4);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q25_5'), 5,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 5);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q25_6'), 6,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 6);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q25_7'), 7,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 7);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q25_8'), 8,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 8);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q25_9'), 9,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 9);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q25_10'), 10,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 10);

--Skip logic for sensory capabilities-Hearing
INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q132_1'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responseorder=2),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q132_2'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responseorder=2),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q132_3'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responseorder=2),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q132_4'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responseorder=2),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q20_1'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responseorder=2),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q20_2'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responseorder=2),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q20_3'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responseorder=2),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q20_4'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responseorder=2),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

--Skip Logic sensory capabilities: Vision

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q23_1'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q22' AND sr.responseorder=3),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q23_2'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q22' AND sr.responseorder=3),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q23_3'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q22' AND sr.responseorder=3),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q133'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q22' AND sr.responseorder=3),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

--Skip Logic for sensory capabilities: Braille

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q151_1'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q23_3' AND sr.responseorder=3),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q151_2'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q23_3' AND sr.responseorder=3),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q151_3'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q23_3' AND sr.responseorder=3),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q24'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q23_3' AND sr.responseorder=3),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));