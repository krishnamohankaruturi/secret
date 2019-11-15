--update to 2000 characters and update regex to include blank.
update fieldspecification set fieldlength = 2000,formatregex='^.{0,2000}$'
where (mappedname like '%_TEXT' or mappedname in ('Q63','Q65','Q154'));

--change the labels to _TEXT
update fieldspecification
set
mappedname = mappedname||'_TEXT',
formatregex='^.{0,2000}$'
where (mappedname in ('Q63','Q65','Q154'));

--insert text responses for the same.
insert into surveyresponse(labelid,responseorder,responsevalue,
responselabel,createduser,modifieduser)
(
Select sl.id as labelid,1 as responseorder,
'Entered Text' as responsevalue,
 responselabel,sr.createduser,sr.modifieduser
 from
 surveyresponse sr,surveylabel sl
 where labelid = (Select id from surveylabel sl1
 where sl1.labelnumber='Q62_TEXT')
 and sl.labelnumber in ('Q63_TEXT','Q65_TEXT','Q154_TEXT')
 );

--contracting org alias should also be saved as response to a question.
update fieldspecification
set allowablevalues= '{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}'
where mappedname ='Q13_1';


--Q36 remove duplicate

--Select * from fieldspecification where mappedname='Q36' and array_length(allowablevalues,1) =2;

delete from fieldspecificationsrecordtypes where fieldspecificationid in 
(
Select id from fieldspecification where mappedname='Q36' and array_length(allowablevalues,1) =2
);

delete from fieldspecification where mappedname='Q36' and array_length(allowablevalues,1) =2;

--Q13_1 remove duplicate

--Select * from fieldspecification where mappedname='Q13_1' 
--and fieldname='contractingOrganizationDisplayIdentifier';

delete from fieldspecificationsrecordtypes where fieldspecificationid in 
(
Select id from fieldspecification where  mappedname='Q13_1'
and fieldname='contractingOrganizationDisplayIdentifier'
);

delete from fieldspecification where mappedname='Q13_1'
and fieldname='contractingOrganizationDisplayIdentifier';


 --DE3538 : Attribute_Value field generates a warning if the value entered is not valid and the Attribute_Container field is left blank 
 
UPDATE fieldspecification SET showerror = false 
WHERE fieldname in ('stateStudentIdentifier','AssessmentNeedBackgroundColor','AssessmentNeedSpokenSourcePreferenceType','AssessmentNeedNumberOfBrailleDotsType','AssessmentNeedReadAtStartPreference','AssessmentNeedBrailleGradeType','AssessmentNeedNumberOfBrailleCells','AssessmentNeedBrailleMarkType','AssessmentNeedBrailleDotPressure','AssessmentNeedBrailleStatusCellType','usage','responsibleSchoolIdentifier','brailleUsage','AssessmentNeedMaskingType','AssessmentNeedMagnification','AssessmentNeedUserSpokenPreferenceType','AssessmentNeedTimeMultiplier','TactileApplication','attributeName','attributeValue','attributeContainer','AssessmentNeedAssignedSupport','AssessmentNeedActivateByDefault','AssessmentNeedSigningType','AssessmentNeedOverlayColor','AssessmentNeedForegroundColor','SpokenassignedSupport','SpokenActivateByDefault','SpokenSourcePreference','ReadAtStartPreference','UserSpokenPreference','SigningAssignedSupport','SigningActivatebydefault','SigningType','BrailleAssignedSupport','BrailleActivatebydefault','brailleGrade','numberOfBrailleDots','numberOfBrailleCells','brailleMark','brailleDotPressure','brailleStatusCell','MaskingAssignedSupport','MaskingActivatebydefault','MaskingType','MagnificationAssignedSupport','MagnificationActivatebydefault','magnificationAmount','tactileFile','AdditionalTestingTimeAssignedSupport','TimeMultiplier','BackgroundColourAssignedSupport','BackgroundColourActivateByDefault','Colour','ColourOverlayAssignedSupport','ColourOverlayactivateByDefault','colourTint','ForegroundColourAssignedSupport','ForegroundColourActivateByDefault','AssessmentNeedUsage','AdditionalTestingTimeActivatebydefault','AssignedSupport','ActivatebyDefault','Band')
AND mappedname in ('State_Student_Identifier','AssessmentNeedBackgroundColor','AssessmentNeedSpokenSourcePreferenceType','AssessmentNeedNumberOfBrailleDotsType','AssessmentNeedReadAtStartPreference','AssessmentNeedBrailleGradeType','AssessmentNeedNumberOfBrailleCells','AssessmentNeedBrailleMarkType','AssessmentNeedBrailleDotPressure','AssessmentNeedBrailleStatusCellType','usage','Responsible_School_Identifier','Braille_Usage','AssessmentNeedMaskingType','AssessmentNeedMagnification','AssessmentNeedUserSpokenPreferenceType','AssessmentNeedTimeMultiplier','TactileApplication','Attribute_Name','Attribute_Value','Attribute_Container','AssessmentNeedAssignedSupport','AssessmentNeedActivateByDefault','AssessmentNeedSigningType','AssessmentNeedOverlayColor','AssessmentNeedForegroundColor','SpokenassignedSupport','SpokenActivateByDefault','SpokenSourcePreference','ReadAtStartPreference','UserSpokenPreference','SigningAssignedSupport','SigningActivatebydefault','SigningType','BrailleAssignedSupport','BrailleActivatebydefault','brailleGrade','numberOfBrailleDots','numberOfBrailleCells','brailleMark','brailleDotPressure','brailleStatusCell','MaskingAssignedSupport','MaskingActivatebydefault','MaskingType','MagnificationAssignedSupport','MagnificationActivatebydefault','magnificationAmount','tactileFile','AdditionalTestingTimeAssignedSupport','TimeMultiplier','BackgroundColourAssignedSupport','BackgroundColourActivateByDefault','Colour','ColourOverlayAssignedSupport','ColourOverlayactivateByDefault','colourTint','ForegroundColourAssignedSupport','ForegroundColourActivateByDefault','AssessmentNeedUsage','AdditionalTestingTimeActivatebydefault','AssignedSupport','ActivatebyDefault','Band');


--DE3787 : Generic error displays when uploading a csv Test file for a particular group of students 

update organization set displayidentifier = UPPER(displayidentifier);


