--dml/676.sql 
-- Updating the reporlt label as is provided in excel sheet with modified dates now
update surveylabel set reportlabel='Special Education_Special Education Services_Primary Disability' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin')  where labelnumber = 'Q16';
update surveylabel set reportlabel='Special Education_Special Education Services_Educational Placement' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q17';
update surveylabel set reportlabel='Sensory Capabilities_Hearing' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q19';
update surveylabel set reportlabel='Sensory Capabilities_Hearing_Classification of Hearing Impairment' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q330';
update surveylabel set reportlabel='Sensory Capabilities_Hearing_Uses personal or classroom amplification' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q20_1';
update surveylabel set reportlabel='Sensory Capabilities_Hearing_Uses unilateral hearing aid' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q20_2';
update surveylabel set reportlabel='Sensory Capabilities_Hearing_Uses bilateral hearing aid' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q20_3';
update surveylabel set reportlabel='Sensory Capabilities_Hearing_Has cochlear implant' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q20_4';
update surveylabel set reportlabel='Sensory Capabilities_Hearing_Uses oral language' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q20_5';
update surveylabel set reportlabel='Sensory Capabilities_Hearing_Uses sign language' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q20_6';
update surveylabel set reportlabel='Sensory Capabilities_Vision' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q22';
update surveylabel set reportlabel='Sensory Capabilities_Vision_Classification_Low Vision' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q429_1';
update surveylabel set reportlabel='Sensory Capabilities_Vision_Classification_Legally Blind' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q429_2';
update surveylabel set reportlabel='Sensory Capabilities_Vision_Classification_Light Perception Only' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q429_3';
update surveylabel set reportlabel='Sensory Capabilities_Vision_Classification_Totally Blind' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q429_4';
update surveylabel set reportlabel='Sensory Capabilities_Vision_Classification_Cortical Visual Impairment' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q429_5';
update surveylabel set reportlabel='Sensory Capabilities_Vision_Requires enlarged print' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q23_1';
update surveylabel set reportlabel='Sensory Capabilities_Vision_Requires tactical media' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q23_2';
update surveylabel set reportlabel='Sensory Capabilities_Vision_Requires or uses Braille' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q23_3';
update surveylabel set reportlabel='Sensory Capabilities_Vision_Requires or uses Braille_Uncontracted Braille' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q24_1';
update surveylabel set reportlabel='Sensory Capabilities_Vision_Requires or uses Braille_Contracted Braille' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q24_2';
update surveylabel set reportlabel='Sensory Capabilities_Vision_Requires or uses Braille_UEB' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q24_3';
update surveylabel set reportlabel='Sensory Capabilities_Vision_Technological Visual Aids_Screen magnification device or software' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q25_1';
update surveylabel set reportlabel='Sensory Capabilities_Vision_Technological Visual Aids_CCTV' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q25_2';
update surveylabel set reportlabel='Sensory Capabilities_Vision_Technological Visual Aids_Screen reader and/or talking word processor' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q25_3';
update surveylabel set reportlabel='Sensory Capabilities_Vision_Technological Visual Aids_Manual or Electronic Braille writing device' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q25_4';
update surveylabel set reportlabel='Sensory Capabilities_Vision_Technological Visual Aids_Device with refreshable Braille display' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q25_5';
update surveylabel set reportlabel='Motor Capabilities_Arm/Hand Control and Health_Uses two hands together to perform tasks' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q29_1';
update surveylabel set reportlabel='Motor Capabilities_Arm/Hand Control and Health_Uses only one hand to perform tasks' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q29_2';
update surveylabel set reportlabel='Motor Capabilities_Arm/Hand Control and Health_Requires physical assistance to perform tasks with hands' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q29_3';
update surveylabel set reportlabel='Motor Capabilities_Arm/Hand Control and Health_Cannot use hands to complete tasks even with assistance' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q29_4';
update surveylabel set reportlabel='Motor Capabilities_Arm/Hand Control and Health_Health issues that interfere with instruction or assessment?' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q200';
update surveylabel set reportlabel='Computer Instruction_Computer Access and Attention During Instruction_Computer Use' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q143';
update surveylabel set reportlabel='Computer Instruction_Computer Access and Attention During Instruction_Why has this student not had the opportunity to access a computer during instruction?' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q147';
update surveylabel set reportlabel='Computer Instruction_Computer Access and Attention During Instruction_Computer access during instruction_Standard computer keyboard' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q33_1';
update surveylabel set reportlabel='Computer Instruction_Computer Access and Attention During Instruction_Computer access during instruction_Keyboard with large keys or alternative keyboard ' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q33_3';
update surveylabel set reportlabel='Computer Instruction_Computer Access and Attention During Instruction_Computer access during instruction_Touch screen' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q33_5';
update surveylabel set reportlabel='Computer Instruction_Computer Access and Attention During Instruction_Computer access during instruction_Standard mouse or head mouse' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q33_6';
update surveylabel set reportlabel='Computer Instruction_Computer Access and Attention During Instruction_Computer access during instruction_Eye gaze technology' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q33_8';
update surveylabel set reportlabel='Computer Instruction_Computer Access and Attention During Instruction_Computer access during instruction_Scanning with switches' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q33_11';
update surveylabel set reportlabel='Computer Instruction_Computer Access and Attention During Instruction_Level of attention to computer-directed instruction' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q201';
update surveylabel set reportlabel='Computer Instruction_Computer Access and Attention During Instruction_Level of attention to teacher-directed instruction' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q202';
update surveylabel set reportlabel='Communication_Expressive Communication_Does the student use speech to meet expressive communication needs?' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q36';
update surveylabel set reportlabel='Communication_Expressive Communication_Highest statement that describes the student''s expressive communication with speech' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q37';
update surveylabel set reportlabel='Communication_Expressive Communication_Does the student use sign language in addition to or in place of speech to meet expressive communication needs?' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q39';
update surveylabel set reportlabel='Communication_Expressive Communication_Choose the highest statement that describes the student''s expressive communication with sign language' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q40';
update surveylabel set reportlabel='Communication_Expressive Communication_Student''s primary sign system' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q41';
update surveylabel set reportlabel='Communication_Expressive Communication_Does the student use augmentative or alternative communication in addition to or in place of speech or sign language to meet expressive communication needs?' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q43';
update surveylabel set reportlabel='Communication_Expressive Communication_Highest statement that describes the student''s expressive communication with augmentative or alternative communication' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q44';
update surveylabel set reportlabel='Communication_Expressive Communication_How many symbols does the student choose from when communicating?' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q210';
update surveylabel set reportlabel='Communication_Expressive Communication_What types of symbols does the student use?_Real objects' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q211_1';
update surveylabel set reportlabel='Communication_Expressive Communication_What types of symbols does the student use?_Tactual symbols' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q211_2';
update surveylabel set reportlabel='Communication_Expressive Communication_What types of symbols does the student use?_Photos' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q211_3';
update surveylabel set reportlabel='Communication_Expressive Communication_What types of symbols does the student use?_Line drawing symbol sets' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q211_4';
update surveylabel set reportlabel='Communication_Expressive Communication_What types of symbols does the student use?_Text Only' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q211_5';
update surveylabel set reportlabel='Communication_Expressive Communication_What voice output technology does the student use?_ Single message devices' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q300_1';
update surveylabel set reportlabel='Communication_Expressive Communication_What voice output technology does the student use?_Simple devices ' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q300_2';
update surveylabel set reportlabel='Communication_Expressive Communication_What voice output technology does the student use?_Speech generating device' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q300_3';
update surveylabel set reportlabel='Communication_Expressive Communication_What voice output technology does the student use?_None' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q300_4';
update surveylabel set reportlabel='Communication_Expressive Communication_If the student does not use speech, sign language, or augmentative or alternative communication, which of the following statements best describes the students expressive communication?' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q47';
update surveylabel set reportlabel='Communication_Receptive Communication_Can point to, look at, or touch things in the immediate vicinity when asked' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q491';
update surveylabel set reportlabel='Communication_Receptive Communication_Can perform simple actions, movements or activities when asked' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q492';
update surveylabel set reportlabel='Communication_Receptive Communication_Responds appropriately in any modality when offered a favored item that is not present or visible ' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q493';
update surveylabel set reportlabel='Communication_Receptive Communication_Responds appropriately in any modality to single words that are spoken or signed' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q494';
update surveylabel set reportlabel='Communication_Receptive Communication_Responds appropriately in any modality to phrases and sentences that are spoken or signed' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q495';
update surveylabel set reportlabel='Communication_Receptive Communication_Follows 2-step directions presented verbally or through sign' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q496';
update surveylabel set reportlabel='Language_Primary Language_Is English the student''s primary language?' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q501';
update surveylabel set reportlabel='Language_Primary Language_Is English the primary language spoken in the student''s home?' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q502';
update surveylabel set reportlabel='Language_Primary Language_Is English the primary language used for the student''s instruction?' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q503';
update surveylabel set reportlabel='Academic_Reading Skills_Recognizes single symbols presented visually or tactually' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q511';
update surveylabel set reportlabel='Academic_Reading Skills_Understands purpose of print or Braille but not necessarily by manipulating a book' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q512';
update surveylabel set reportlabel='Academic_Reading Skills_Matches sounds to symbols or signs to symbols' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q513';
update surveylabel set reportlabel='Academic_Reading Skills_Reads words, phrases, or sentences in print or Braille when symbols are provided with the words' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q514';
update surveylabel set reportlabel='Academic_Reading Skills_Identifies individual words without symbol support' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q515';
update surveylabel set reportlabel='Academic_Reading Skills_Reads text presented in print or Braille without symbol support but WITHOUT comprehension' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q516';
update surveylabel set reportlabel='Academic_Reading Skills_Reads text presented in print or Braille without symbol support and WITH comprehension' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q517';
update surveylabel set reportlabel='Academic_Reading Skills_Explains or elaborates on text read in print or Braille' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q518';
update surveylabel set reportlabel='Academic_Reading Skills_Student''s approximate instructional level of reading text with comprehension' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q52';
update surveylabel set reportlabel='Academic_Math Skills_Creates or matches patterns of objects or images' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q541';
update surveylabel set reportlabel='Academic_Math Skills_Identifies simple shapes in 2 or 3 dimensions' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q542';
update surveylabel set reportlabel='Academic_Math Skills_Sorts objects by common properties' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q543';
update surveylabel set reportlabel='Academic_Math Skills_Counts more than two objects' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q544';
update surveylabel set reportlabel='Academic_Math Skills_Adds or subtracts by joining or separating groups of objects' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q545';
update surveylabel set reportlabel='Academic_Math Skills_Adds and/or subtracts using numerals' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q546';
update surveylabel set reportlabel='Academic_Math Skills_Forms groups of objects for multiplication or division' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q547';
update surveylabel set reportlabel='Academic_Math Skills_Multiplies and/or divides using numerals' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q548';
update surveylabel set reportlabel='Academic_Math Skills_Uses an abacus' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q549';
update surveylabel set reportlabel='Academic_Math Skills_Uses a calculator' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q5410';
update surveylabel set reportlabel='Academic_Math Skills_Tells time using an analog or digital clock' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q5411';
update surveylabel set reportlabel='Academic_Math Skills_Uses common measuring tools ' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q5412';
update surveylabel set reportlabel='Academic_Math Skills_Uses a schedule, agenda, or calendar to identify or anticipate sequence of activities' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q5413';
update surveylabel set reportlabel='Academic_Writing Skills_Highest level that describes the student''s writing skills' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q500';
update surveylabel set reportlabel='Academic_Science Skills_Sorts objects or materials by common properties' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q212_1';
update surveylabel set reportlabel='Academic_Science Skills_Identifies similarities and differences' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q212_2';
update surveylabel set reportlabel='Academic_Science Skills_Recognizes patterns' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q212_3';
update surveylabel set reportlabel='Academic_Science Skills_Compares initial and final conditions to determine if something changed.' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q212_4';
update surveylabel set reportlabel='Academic_Science Skills_Uses data to answer questions.' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q212_5';
update surveylabel set reportlabel='Academic_Science Skills_Identifies evidence that supports a claim.' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q212_6';
update surveylabel set reportlabel='Academic_Science Skills_Identifies cause and effect relationships.' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q212_7';
update surveylabel set reportlabel='Academic_Science Skills_Uses diagrams to explain phenomena.' ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q212_8';

-- This section need to be active to get proper results in extract.
update surveysection  set activeflag = true,
modifieddate= now(),
modifieduser = (select id from aartuser where username='cetesysadmin') 
where surveysectioncode='ACCESS_AND_SWITCHES';

-- These updates are required to bring consistency on response values across all two dimensional questions.
update surveyresponse set responsevalue = 'Almost never (0% - 20% of the time)',
modifieddate= now(),
modifieduser = (select id from aartuser where username='cetesysadmin')  
where id in (select id from surveyresponse  where activeflag is true 
and labelid in (select id from surveylabel 
		where labeltype = (select id from category where categorycode = 'twodimentional' 
		and categorytypeid = (select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES') ) 
		and activeflag is true and labelnumber not in ('Q212_1','Q212_2','Q212_3','Q212_4','Q212_5','Q212_6','Q212_7','Q212_8')) and responseorder = 2
order by labelid,responseorder);

update surveyresponse set responsevalue = 'Occasionally (21% - 50% of the time)',
modifieddate= now(),
modifieduser = (select id from aartuser where username='cetesysadmin')  
where id in (select id from surveyresponse  where activeflag is true 
and labelid in (select id from surveylabel 
		where labeltype = (select id from category where categorycode = 'twodimentional' 
		and categorytypeid = (select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES') ) 
		and activeflag is true and labelnumber not in ('Q212_1','Q212_2','Q212_3','Q212_4','Q212_5','Q212_6','Q212_7','Q212_8')) and responseorder = 3
order by labelid,responseorder);


update surveyresponse set responsevalue = 'Frequently (51% - 80% of the time)' 
where id in (select id from surveyresponse  where activeflag is true 
and labelid in (select id from surveylabel 
		where labeltype = (select id from category where categorycode = 'twodimentional' 
		and categorytypeid = (select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES') ) 
		and activeflag is true and labelnumber not in ('Q212_1','Q212_2','Q212_3','Q212_4','Q212_5','Q212_6','Q212_7','Q212_8')) and responseorder = 4
order by labelid,responseorder);

update surveyresponse set responsevalue = 'Consistently (More than 80% of the time)',
modifieddate= now(),
modifieduser = (select id from aartuser where username='cetesysadmin')  
where id in (select id from surveyresponse  where activeflag is true 
and labelid in (select id from surveylabel 
		where labeltype = (select id from category where categorycode = 'twodimentional' 
		and categorytypeid = (select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES') ) 
		and activeflag is true and labelnumber not in ('Q212_1','Q212_2','Q212_3','Q212_4','Q212_5','Q212_6','Q212_7','Q212_8')) and responseorder = 5
order by labelid,responseorder);

update surveylabel set reportlabel='Sensory Capabilities_Vision_Uncontracted braille',
modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q24_1';

update surveylabel set reportlabel='Sensory Capabilities_Vision_contracted braille',
modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q24_2';

update surveylabel set reportlabel='Sensory Capabilities_Vision_Uses Braille_UEB',
modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber = 'Q24_3';

-- update modifeddate to sync with datawarehouse.

update surveyresponse set responsevalue = 'Frequently (51% - 80% of the time)',
modifieddate= now(),
modifieduser = (select id from aartuser where username='cetesysadmin')   
where id in (select id from surveyresponse  where activeflag is true 
and labelid in (select id from surveylabel 
		where labeltype = (select id from category where categorycode = 'twodimentional' 
		and categorytypeid = (select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES') ) 
		and activeflag is true and labelnumber not in ('Q212_1','Q212_2','Q212_3','Q212_4','Q212_5','Q212_6','Q212_7','Q212_8')) and responseorder = 4
order by labelid,responseorder);

-- script updated from scriptbees team
update surveysection  set activeflag = false,
modifieddate= now(),
modifieduser = (select id from aartuser where username='cetesysadmin') 
where surveysectioncode='ACCESS_AND_SWITCHES';

-- Updated capitalization of B & C
update surveylabel set reportlabel='Sensory Capabilities_Vision_Uncontracted Braille',
modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
where labelnumber = 'Q24_1';
update surveylabel set reportlabel='Sensory Capabilities_Vision_Contracted Braille',
modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
where labelnumber = 'Q24_2';
update surveylabel set reportlabel='Sensory Capabilities_Vision_Uses Braille_UEB',
modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
where labelnumber = 'Q24_3';
