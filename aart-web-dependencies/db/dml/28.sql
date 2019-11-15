
-- R7-Rel Prep - Tag 4
--US12607 Name: Emergency change request - change magnfication attribute container
	
update profileitemattributenameattributecontainer set attributecontainerid = (select id from profileitemattributecontainer where attributecontainer = 'Magnification')
  where attributecontainerid = (select id from profileitemattributecontainer where attributecontainer = 'ScreenEnhancement') and attributenameid = (select id from profileitemattribute where attributename ='magnification');


--CB publishing changes.  
UPDATE CATEGORY set categorytypeid = (select id from categorytype where typecode='ACCOMODATION_TYPE') where categorycode in (
'KEYWORD_TRANSLATION','FLAGGING','CHUNKING','SCAFFOLDING','REDUCED_ANSWERS','BRAILLE','SIGNED_MODES','TACTILE_FORMS','AUDIO_FORMS','VIDEO_FORMS','GRAPHIC_FORMS','TEXT_FORMS','INTERACTIVE_FORMS'
);
