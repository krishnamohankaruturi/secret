--591.sql
DELETE FROM fieldspecificationsrecordtypes WHERE fieldspecificationid = (SELECT id FROM fieldspecification  WHERE (fieldname,mappedname) = ('secondaryExceptionalityCode','Secondary_Exceptionality_Code'));
DELETE FROM fieldspecification  WHERE (fieldname,mappedname) = ('secondaryExceptionalityCode','Secondary_Exceptionality_Code');



-- Category type for PNP options categories 
INSERT INTO CATEGORYTYPE (typename, typecode, typedescription, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('PNP Options Category', 'PNP_OPTIONS_SETTINGS', 'PNP Options', 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), 
now(), (select id from aartuser where username='cetesysadmin'));

-- Categories for PNP options groups
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Display Enhancements', 'DISPLAY_ENHANCEMENTS', 'Display Enhancements Options', (select id from categorytype where typecode='PNP_OPTIONS_SETTINGS'), 
'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Language and Braille', 'LANGUAGE_AND_BRAILLE', 'Language and Braille Options', (select id from categorytype where typecode='PNP_OPTIONS_SETTINGS'), 
'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Audio and Environment Support', 'AUDIO_AND_ENVIRONMENT_SUPPORT', 'Audio and Environment Support Options', (select id from categorytype where typecode='PNP_OPTIONS_SETTINGS'), 
'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Other Supports', 'OTHER_SUPPORTS', 'Other Supports Options', (select id from categorytype where typecode='PNP_OPTIONS_SETTINGS'), 
'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

--pnp accomodations inserts

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Maginification',(select id from category where categorycode = 'DISPLAY_ENHANCEMENTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Magnification' and pia.attributename='assignedSupport'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Masking',(select id from category where categorycode = 'DISPLAY_ENHANCEMENTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Masking' and pia.attributename='assignedSupport'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Overlay Color',(select id from category where categorycode = 'DISPLAY_ENHANCEMENTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='ColourOverlay' and pia.attributename='assignedSupport'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Contrast Color',(select id from category where categorycode = 'DISPLAY_ENHANCEMENTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='BackgroundColour' and pia.attributename='assignedSupport'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Invert Color Choice',(select id from category where categorycode = 'DISPLAY_ENHANCEMENTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='InvertColourChoice' and pia.attributename='assignedSupport'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Item Translation Display',(select id from category where categorycode = 'LANGUAGE_AND_BRAILLE'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='itemTranslationDisplay' and pia.attributename='assignedSupport'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Keyword Translation Display',(select id from category where categorycode = 'LANGUAGE_AND_BRAILLE'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='keywordTranslationDisplay' and pia.attributename='assignedSupport'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Signing Type',(select id from category where categorycode = 'LANGUAGE_AND_BRAILLE'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Signing' and pia.attributename='assignedSupport'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Tactile',(select id from category where categorycode = 'LANGUAGE_AND_BRAILLE'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Tactile' and pia.attributename='assignedSupport'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Braille',(select id from category where categorycode = 'LANGUAGE_AND_BRAILLE'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='assignedSupport'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Auditory Background',(select id from category where categorycode = 'AUDIO_AND_ENVIRONMENT_SUPPORT'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='AuditoryBackground' and pia.attributename='assignedSupport'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Breaks',(select id from category where categorycode = 'AUDIO_AND_ENVIRONMENT_SUPPORT'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='breaks' and pia.attributename='assignedSupport'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Additional Testing Time',(select id from category where categorycode = 'AUDIO_AND_ENVIRONMENT_SUPPORT'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='AdditionalTestingTime' and pia.attributename='assignedSupport'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Spoken Audio',(select id from category where categorycode = 'AUDIO_AND_ENVIRONMENT_SUPPORT'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Spoken' and pia.attributename='assignedSupport'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Single Switches',(select id from category where categorycode = 'AUDIO_AND_ENVIRONMENT_SUPPORT'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='onscreenKeyboard' and pia.attributename='assignedSupport'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

-- Others 
INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Separate, quiet, or individual setting',(select id from category where categorycode = 'OTHER_SUPPORTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='setting' and pia.attributename='separateQuiteSetting'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Some other accommodation was used',(select id from category where categorycode = 'OTHER_SUPPORTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='presentation' and pia.attributename='someotheraccommodation'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Student reads the assessment aloud to self',(select id from category where categorycode = 'OTHER_SUPPORTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='presentation' and pia.attributename='readsAssessmentOutLoud'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Student used a translation dictionary',(select id from category where categorycode = 'OTHER_SUPPORTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='presentation' and pia.attributename='useTranslationsDictionary'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Student dictated his/her answers to a scribe',(select id from category where categorycode = 'OTHER_SUPPORTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='response' and pia.attributename='dictated'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Student used a communication device',(select id from category where categorycode = 'OTHER_SUPPORTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='response' and pia.attributename='usedCommunicationDevice'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Student signed responses',(select id from category where categorycode = 'OTHER_SUPPORTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='response' and pia.attributename='signedResponses'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Alternate Form - Visual Impairment',(select id from category where categorycode = 'OTHER_SUPPORTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedByAlternateForm' and pia.attributename='visualImpairment'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Alternate Form - Large Print Booklet',(select id from category where categorycode = 'OTHER_SUPPORTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedByAlternateForm' and pia.attributename='largePrintBooklet'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Alternate Form - Paper and Pencil',(select id from category where categorycode = 'OTHER_SUPPORTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedByAlternateForm' and pia.attributename='paperAndPencil'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Two switch system',(select id from category where categorycode = 'OTHER_SUPPORTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsRequiringAdditionalTools' and pia.attributename='supportsTwoSwitch'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Administration via iPad',(select id from category where categorycode = 'OTHER_SUPPORTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsRequiringAdditionalTools' and pia.attributename='supportsAdminIpad'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Adaptive equipment',(select id from category where categorycode = 'OTHER_SUPPORTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsRequiringAdditionalTools' and pia.attributename='supportsAdaptiveEquip'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Individualized manipulatives',(select id from category where categorycode = 'OTHER_SUPPORTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsRequiringAdditionalTools' and pia.attributename='supportsIndividualizedManipulatives'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Calculator',(select id from category where categorycode = 'OTHER_SUPPORTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsRequiringAdditionalTools' and pia.attributename='supportsCalculator'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));




INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Human read aloud',(select id from category where categorycode = 'OTHER_SUPPORTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedOutsideSystem' and pia.attributename='supportsHumanReadAloud'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Sign interpretation',(select id from category where categorycode = 'OTHER_SUPPORTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedOutsideSystem' and pia.attributename='supportsSignInterpretation'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Language translation',(select id from category where categorycode = 'OTHER_SUPPORTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedOutsideSystem' and pia.attributename='supportsLanguageTranslation'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Test admin enters responses for student',(select id from category where categorycode = 'OTHER_SUPPORTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedOutsideSystem' and pia.attributename='supportsTestAdminEnteredResponses'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Partner assisted scanning',(select id from category where categorycode = 'OTHER_SUPPORTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedOutsideSystem' and pia.attributename='supportsPartnerAssistedScanning'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser) 
values ('Student provided non-embedded accommodations as noted in IEP',(select id from category where categorycode = 'OTHER_SUPPORTS'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedOutsideSystem' and pia.attributename='supportsStudentProvidedAccommodations'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'));
