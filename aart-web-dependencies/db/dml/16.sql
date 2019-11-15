
--US11518 - Implement First Contact Data Points from Ref Spec - Attention Section

INSERT INTO CATEGORYTYPE (typename, typecode, typedescription, originationcode, createddate, createduser, modifieddate, modifieduser)
	VALUES ('Survey Section', 'SURVEY_SECTION', 'Survey Section', 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), 
	now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
	VALUES ('Student ID', 'STUDENT_ID', 'Student ID section for survey labels', (select id from categorytype where typecode='SURVEY_SECTION'), 
	'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
	VALUES ('State', 'STATE', 'State section for survey labels', (select id from categorytype where typecode='SURVEY_SECTION'), 
	'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
	VALUES ('Communication', 'COMMUNICATION', 'Communication section for survey labels', (select id from categorytype where typecode='SURVEY_SECTION'), 
	'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
	VALUES ('Augmentative or Alternate Communication', 'AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION', 'Augmentative or Alternate Communication section for survey labels', 
	(select id from categorytype where typecode='SURVEY_SECTION'), 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
	VALUES ('Academic', 'ACADEMIC', 'Academic section for survey labels', (select id from categorytype where typecode='SURVEY_SECTION'), 
	'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
	VALUES ('Attention, Understanding Instruction, Health', 'ATTENTION_UNDERSTANDING_INSTRUCTION_HEALTH', 'Attention, Understanding Instruction, Health section for survey labels', 
	(select id from categorytype where typecode='SURVEY_SECTION'), 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), 
	(select id from aartuser where username='cetesysadmin'));


INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from category where categorycode='STUDENT_ID'), 'Q3',13, 'Please type the student''s state issued unique identifier used for assessment* in the text box below...', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from category where categorycode='STATE'), 'Q13_1',29, 'Your STATE-STATE', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='COMMUNICATION'), 'Q36',99, 'Does the student use speech to meet expressive COMMUNICATION needs?', 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='COMMUNICATION'), 'Q37',100, 'Choose the highest STATEment that describes the student''s expressive COMMUNICATION with speech', 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='COMMUNICATION'), 'Q39',101, 'Does the student use sign language in addition to or in place of speech to meet expressive communica...', 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='COMMUNICATION'), 'Q40',102, 'Choose the highest STATEment that describes the student''s expressive COMMUNICATION with sign languag...',
	 now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='COMMUNICATION'), 'Q41',103, 'Select the student''s primary sign system', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION'), 'Q43',105, 'Does the student use augmentative or alternative COMMUNICATION in addition to or in place of speech...', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION'), 'Q44',106, 'Choose the highest STATEment that describes the student''s expressive COMMUNICATION with augmentative...', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION'), 'Q45_1',107, 'Augmentative or alternative COMMUNICATION: Mark all that apply-Symbols offered in groups of 1 or 2', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION'), 'Q45_2',108, 'Augmentative or alternative COMMUNICATION: Mark all that apply-Low-tech COMMUNICATION board(s) with 8 or fewer symbols', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION'), 'Q45_3',109, 'Augmentative or alternative COMMUNICATION: Mark all that apply-Low-tech COMMUNICATION board(s) with 9 or more symbols', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION'), 'Q45_4',110, 'Augmentative or alternative COMMUNICATION: Mark all that apply-Low-tech COMMUNICATION book with multiple pages each containing 8 or fewer symbols', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION'), 'Q45_5',111, 'Augmentative or alternative COMMUNICATION: Mark all that apply-Low-tech COMMUNICATION book with multiple pages each containing 9 or more symbols', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION'), 'Q45_6',112, 'Augmentative or alternative COMMUNICATION: Mark all that apply-Eye gaze board (eye gaze COMMUNICATION) with 4 or fewer symbols', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION'), 'Q45_7',113, 'Augmentative or alternative COMMUNICATION: Mark all that apply-Eye gaze board (eye gaze COMMUNICATION) with 5 or more symbols', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION'), 'Q45_8',114, 'Augmentative or alternative COMMUNICATION: Mark all that apply-Simple voice output device (e.g., BIGmack, Step by Step, Cheap Talk, Voice-in-a-Box, Talking Picture Frame) with 9 or fewer messages or multiple messages in sequence', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION'), 'Q45_9',115, 'Augmentative or alternative COMMUNICATION: Mark all that apply-Simple voice output device with 10 to 40 messages', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION'), 'Q45_10',116, 'Augmentative or alternative COMMUNICATION: Mark all that apply-Voice output device with levels (e.g., 6 level Voice-in-a-box, Macaw, Digivox, DAC)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION'), 'Q45_11',117, 'Augmentative or alternative COMMUNICATION: Mark all that apply-Voice output device or computer/tablet with dynamic display software (e.g., DynaVox, Mytobii, Proloquo2Go, Speaking Dynamically Pro, Vantage)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION'), 'Q45_12',118, 'Augmentative or alternative COMMUNICATION: Mark all that apply-Voice output device with icon sequencing (e.g., ECO, ECO2, Springboard Lite, Vanguard)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION'), 'Q139',119, 'Receptive COMMUNICATION', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION'), 'Q47',120, 'If the student does not use speech, sign language, or augmentative or alternative COMMUNICATION, whi...', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION'), 'Q49_1',121, 'Receptive COMMUNICATION: MARK EACH ONE to show the approximate percent of time that the student uses...-A) Can point to, look at, or touch things in the immediate vicinity when asked (e.g., pictures, objects, body parts)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION'), 'Q49_2',122, 'Receptive COMMUNICATION: MARK EACH ONE to show the approximate percent of time that the student uses...-B) Can perform simple actions, movements or activities when asked (e.g., comes to teacher''s location, gives an object to teacher or peer, locates or retF8000IME20', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION'), 'Q49_3',123, 'Receptive COMMUNICATION: MARK EACH ONE to show the approximate percent of time that the student uses...-C) Responds appropriately in any modality (speech, sign, gestures, facial expressions) when offered a favored item that is not present or visible (e.g.,F8000IME20', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION'), 'Q49_4',124, 'Receptive COMMUNICATION: MARK EACH ONE to show the approximate percent of time that the student uses...-D) Responds appropriately in any modality (speech, sign, gestures, facial expressions) to single words that are spoken or signed', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION'), 'Q49_5',125, 'Receptive COMMUNICATION: MARK EACH ONE to show the approximate percent of time that the student uses...-E) Responds appropriately in any modality (speech, sign, gestures, facial expressions) to phrases and sentences that are spoken or signed', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION'), 'Q49_6',126, 'Receptive COMMUNICATION: MARK EACH ONE to show the approximate percent of time that the student uses...-F) Follows 2-step directions presented verbally or through sign (e.g., gets a worksheet or journal and begins to work, distributes items needed by peersF8000IME20', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q51_1',128, 'Reading skills: MARK EACH ONE to show the approximate percent of time that the student uses each ski...-A) Recognizes single symbols presented visually or tactually (e.g., letters, numerals, environmental signs such as restroom symbols, logos, trademarks, F8000IME20', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q51_2',129, 'Reading skills: MARK EACH ONE to show the approximate percent of time that the student uses each ski...-B) Understands purpose of print or Braille but not necessarily by manipulating a book (e.g., knows correct orientation, can find beginning of text, undeF8000IME20', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q51_3',130, 'Reading skills: MARK EACH ONE to show the approximate percent of time that the student uses each ski...-C) Matches sounds to symbols or signs to symbols (e.g., matches sounds to letters presented visually or tactually, matches spoken or signed words to wriF8000IME20', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q51_4',131, 'Reading skills: MARK EACH ONE to show the approximate percent of time that the student uses each ski...-D) Reads words, phrases, or sentences in print or Braille when symbols are provided with the words', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q51_5',132, 'Reading skills: MARK EACH ONE to show the approximate percent of time that the student uses each ski...-E) Identifies individual words without symbol support (e.g., recognizes words in print or Braille; can choose correct word using eye gaze)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q51_6',133, 'Reading skills: MARK EACH ONE to show the approximate percent of time that the student uses each ski...-F) Reads text presented in print or Braille without symbol support but WITHOUT comprehension', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q51_7',134, 'Reading skills: MARK EACH ONE to show the approximate percent of time that the student uses each ski...-G) Reads text presented in print or Braille without symbol support and WITH comprehension (e.g., locates answers in text, reads and answers questions, rF8000IME20', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q51_8',135, 'Reading skills: MARK EACH ONE to show the approximate percent of time that the student uses each ski...-H) Explains or elaborates on text read in print or Braille', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q52',136, 'student''s approximate instructional reading level in print or Braille: Mark the highest one that app...', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q54_1',137, 'Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill-A) Creates or matches patterns of objects or images', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q54_2',138, 'Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill-B) Identifies simple shapes in 2 or 3 dimensions (e.g., square, circle, triangle, cube, sphere)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q54_3',139, 'Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill-C) Sorts objects by common properties (e.g., color, size, shape)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q54_4',140, 'Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill-D) Counts more than two objects', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q54_5',141, 'Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill-E) Adds or subtracts by joining or separating groups of objects', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q54_6',142, 'Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill-F) Adds and/or subtracts using numerals', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q54_7',143, 'Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill-G) Forms groups of objects for multiplication or division', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q54_8',144, 'Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill-H) Multiplies and/or divides using numerals', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q54_9',145, 'Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill-I) Uses an abacus', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q54_10',146, 'Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill-J) Uses a calculator', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q54_11',147, 'Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill-K) Tells time using an analog or digital clock', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q54_12',148, 'Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill-L) Uses common measuring tools (e.g., ruler or measuring cup)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q54_13',149, 'Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill-M) Uses a schedule, agenda, or calendar to identify or anticipate sequence of activities', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q56_1',150, 'Writing skills: MARK EACH ONE to show the approximate percent of time that the student uses each ski...-A) Makes random marks or scribbles with pencil or marker', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q56_2',151, 'Writing skills: MARK EACH ONE to show the approximate percent of time that the student uses each ski...-B) Randomly selects letters or symbols when asked to write, with or  without requiring use of pencil or marker  (e.g., writes single letters or numbers F8000IME20', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q56_3',152, 'Writing skills: MARK EACH ONE to show the approximate percent of time that the student uses each ski...-C) Copies letters and words with pencil, pen, marker, or keyboard, but cannot produce independent writing', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q56_4',153, 'Writing skills: MARK EACH ONE to show the approximate percent of time that the student uses each ski...-D) Selects symbols to express meaning when asked to write (e.g., writes letters with pencil or pen, chooses letters on keyboard, selects symbols on commF8000IME20', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q56_5',154, 'Writing skills: MARK EACH ONE to show the approximate percent of time that the student uses each ski...-E) Writes using word banks to select or copy words (e.g., copies words with pencil or pen, copies words using keyboard, selects words on COMMUNICATION bF8000', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q56_6',155, 'Writing skills: MARK EACH ONE to show the approximate percent of time that the student uses each ski...-F) Uses letters to accurately reflect sounds in words when writing (e.g., writes own name using pencil or keyboard, writes letters without copying, usesF8000IME20', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q56_7',156, 'Writing skills: MARK EACH ONE to show the approximate percent of time that the student uses each ski...-G) Uses spelling (not always correct) to write simple phrases and sentences (e.g., writes phrases and sentences independently without copying, uses keybF8000IME20', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ACADEMIC'), 'Q56_8',157, 'Writing skills: MARK EACH ONE to show the approximate percent of time that the student uses each ski...-H) Uses spelling (not always correct) to write paragraph-length text (e.g., produces text by writing or using keyboard or other technology without copyiF80', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ATTENTION_UNDERSTANDING_INSTRUCTION_HEALTH'), 'Q142',159, 'Level of attention to teacher-directed instruction', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ATTENTION_UNDERSTANDING_INSTRUCTION_HEALTH'), 'Q146',160, 'Level of attention to computer-directed instruction', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ATTENTION_UNDERSTANDING_INSTRUCTION_HEALTH'), 'Q58_1',161, 'Order all modes of instructional delivery by the student''s level of attention (i.e., move the delive...-Teacher-directed instructional activities', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ATTENTION_UNDERSTANDING_INSTRUCTION_HEALTH'), 'Q58_2',162, 'Order all modes of instructional delivery by the student''s level of attention (i.e., move the delive...-Peer-directed instructional activities', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ATTENTION_UNDERSTANDING_INSTRUCTION_HEALTH'), 'Q58_3',163, 'Order all modes of instructional delivery by the student''s level of attention (i.e., move the delive...-Self-selected instructional activities', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ATTENTION_UNDERSTANDING_INSTRUCTION_HEALTH'), 'Q58_4',164, 'Order all modes of instructional delivery by the student''s level of attention (i.e., move the delive...-Computer-directed instructional activities', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ATTENTION_UNDERSTANDING_INSTRUCTION_HEALTH'), 'Q60',165, 'General level of understanding instruction: Choose the highest one that applies', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ATTENTION_UNDERSTANDING_INSTRUCTION_HEALTH'), 'Q62',166, 'Does this student have a behavior intervention plan? If so, please briefly name the behavioral goal(...', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ATTENTION_UNDERSTANDING_INSTRUCTION_HEALTH'), 'Q62_TEXT',167, 'Does this student have a behavior intervention plan? If so, please briefly name the behavioral goal(...-TEXT', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ATTENTION_UNDERSTANDING_INSTRUCTION_HEALTH'), 'Q63',168, 'Comments on student''s behavior', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ATTENTION_UNDERSTANDING_INSTRUCTION_HEALTH'), 'Q64',169, 'Does the student have any health issues (e.g., fragile medical condition, seizures, therapy or treat...', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ATTENTION_UNDERSTANDING_INSTRUCTION_HEALTH'), 'Q65',170, 'If yes, please describe the health or personal care concerns that interfere with instruction or asse...', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveylabels (sectionid, labelnumber, position, label, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from category where categorycode='ATTENTION_UNDERSTANDING_INSTRUCTION_HEALTH'), 'Q154',171, 'OPTIONAL: Additional comments regarding this student (i.e., other AAC/AT devices, behavior influenci...', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));


INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q13_1'), 1,'Iowa', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q13_1'), 2,'Kansas', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q13_1'), 3,'Michigan', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q13_1'), 4,'Mississippi', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q13_1'), 5,'Missouri', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q13_1'), 6,'New Jersey', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q13_1'), 7,'North Carolina', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q13_1'), 8,'Oklahoma', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q13_1'), 9,'Utah', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q13_1'), 10,'Vermont', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q13_1'), 11,'Virginia', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q13_1'), 12,'Washington', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q13_1'), 13,'West Virginia', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q13_1'), 14,'Wisconsin', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q13_1'), 15,'Other', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q36'), 1,'Yes', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q36'), 2,'No', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q37'), 1,'Regularly combines 3 or more spoken words according to grammatical rules to accomplish a variety of communicative purposes (e.g., sharing complex information, asking/answering longer questions, giving directions to another person)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q37'), 2,'Usually uses 2 spoken words at a time to meet a variety of more complex communicative purposes (e.g., obtaining things including absent objects, social expressions beyond greetings, sharing information, directing another person''s attention, asking/answeri', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q37'), 3,'Usually uses only 1 spoken word at a time to meet a limited number of simple communicative purposes (e.g., refusing/rejecting things, making choices, requesting attention, greeting, and labeling)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q39'), 1,'Yes', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q39'), 2,'No', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q40'), 1,'Regularly combines 3 or more signed words according to grammatical rules to accomplish a variety of communicative purposes (e.g., sharing complex information, asking/answering longer questions, giving directions to another person)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q40'), 2,'Usually uses 2 signed words at a time to meet a variety of more complex communicative purposes (e.g., obtaining things including absent objects, social expressions beyond greetings, sharing information, directing another person''s attention, asking/answeri', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q40'), 3,'Usually uses only 1 signed word at a time to meet a limited number of simple communicative purposes (e.g., refusing/rejecting things, making choices, requesting attention, greeting, and labeling)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q41'), 1,'American Sign Language (ASL)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q41'), 2,'Signed Exact English (SEE)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q41'), 3,'Hybrid or idiosyncratic/personalized signing system', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q43'), 1,'Yes', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q43'), 2,'No', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q44'), 1,'Regularly combines 3 or more symbols according to grammatical rules to accomplish the 4 major communicative purposes (e.g., expressing needs and wants, developing social closeness, exchanging information, and fulfilling social etiquette routines)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q44'), 2,'Usually uses 2 symbols at a time to meet a variety of more complex communicative purposes (e.g., obtaining things including absent objects, social expressions beyond greetings, sharing information, directing another person''s attention, asking/answering qu', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q44'), 3,'Usually uses only 1 symbol to meet a limited number of simple communicative purposes (e.g., refusing/rejecting things, making choices, requesting attention, greeting)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q47'), 1,'Uses conventional gestures (e.g., waving, nodding and shaking head, thumbs up/down) and vocalizations to communicate intentionally but does not yet use symbols or sign language', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q47'), 2,'Uses only unconventional vocalizations (e.g., grunts), unconventional gestures (e.g., touching mouth to indicate hunger), and/or body movement to communicate intentionally', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q47'), 3,'Exhibits behaviors that may be reflexive and are not intentionally communicative but can be interpreted by others as communication (e.g., crying, laughing, reaching for an object, pushing an object away)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_1'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_1'), 2,'1% to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_1'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_1'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_1'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_2'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_2'), 2,'1% to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_2'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_2'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_2'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_3'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_3'), 2,'1% to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_3'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_3'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_3'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_4'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_4'), 2,'1% to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_4'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_4'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_4'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_5'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_5'), 2,'1% to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_5'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_5'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_5'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_6'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_6'), 2,'1% to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_6'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_6'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q49_6'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_1'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_1'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_1'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_1'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_1'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_2'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_2'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_2'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_2'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_2'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_3'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_3'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_3'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_3'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_3'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_4'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_4'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_4'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_4'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_4'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_5'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_5'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_5'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_5'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_5'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_6'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_6'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_6'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_6'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_6'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_7'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_7'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_7'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_7'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_7'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_8'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_8'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_8'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_8'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q51_8'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q52'), 1,'Above third grade level', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q52'), 2,'Above second grade level to third grade level', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q52'), 3,'Above first grade level to second grade level', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q52'), 4,'Primer to first grade level', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q52'), 5,'Reads only a few words or up to pre-primer level', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q52'), 6,'Does not read any words when presented in print or Braille (not including environmental signs or logos)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_1'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_1'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_1'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_1'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_1'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_2'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_2'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_2'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_2'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_2'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_3'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_3'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_3'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_3'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_3'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_4'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_4'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_4'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_4'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_4'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_5'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_5'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_5'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_5'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_5'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_6'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_6'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_6'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_6'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_6'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_7'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_7'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_7'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_7'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_7'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_8'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_8'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_8'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_8'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_8'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_9'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_9'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_9'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_9'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_9'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_10'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_10'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_10'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_10'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_10'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_11'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_11'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_11'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_11'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_11'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_12'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_12'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_12'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_12'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_12'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_13'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_13'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_13'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_13'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q54_13'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_1'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_1'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_1'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_1'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_1'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_2'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_2'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_2'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_2'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_2'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_3'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_3'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_3'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_3'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_3'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_4'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_4'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_4'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_4'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_4'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_5'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_5'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_5'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_5'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_5'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_6'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_6'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_6'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_6'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_6'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_7'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_7'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_7'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_7'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_7'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_8'), 1,'0% (student does not exhibit this skill)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_8'), 2,'None to 20% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_8'), 3,'21% to 50% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_8'), 4,'51% to 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q56_8'), 5,'More than 80% of the time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q142'), 1,'Generally sustains attention to teacher-directed instruction', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q142'), 2,'Demonstrates fleeting attention to teacher-directed instructional activities and requires repeated bids or prompts for attention', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q142'), 3,'Demonstrates little or no attention to teacher-directed instructional activities', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q146'), 1,'Generally sustains attention to computer-directed instruction', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q146'), 2,'Demonstrates fleeting attention to computer-directed instructional activities and requires repeated bids or prompts for attention', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q146'), 3,'Demonstrates little or no attention to computer-directed instructional activities', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q60'), 1,'Applies understanding of skills and concepts to novel instructional activities (e.g., generalizes learning to new settings, uses previously learned skills in unfamiliar problems or situations with no more than minimal prompting and support)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q60'), 2,'Demonstrates understanding of previously instructed skills and concepts in similar situations without prompting and support (e.g., uses previously learned skills in familiar problems or situations without prompting or support)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q60'), 3,'Demonstrates understanding of previously instructed skills and concepts with prompting and support (e.g., uses previously learned skills only with prompting and support)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q60'), 4,'Participates in instructional activities with prompting and support (e.g., participates but does not apply previously learned skills to familiar situations even with prompting and support)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q60'), 5,'Does not participate in instructional activities even with prompting and support', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q62'), 9,'Yes', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q62'), 10,'No', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q64'), 1,'No', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q64'), 2,'Health issues', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
 	 VALUES ((select id from surveylabels where labelnumber='Q64'), 3,'Personal care issues', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	 VALUES ((select id from surveylabels where labelnumber='Q64'), 4,'Both health and personal care issues', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'));

--Below values are required if the student doesn't select any reponse i.e if the response is empty.

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q142'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q146'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q36'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q37'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q39'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q40'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q41'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q43'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q44'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q47'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q49_1'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q49_2'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q49_3'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q49_4'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q49_5'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q49_6'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q51_1'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q51_2'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q51_3'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q51_4'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q51_5'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q51_6'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q51_7'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q51_8'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q52'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q54_1'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q54_10'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q54_11'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q54_12'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q54_13'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q54_2'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q54_3'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q54_4'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q54_5'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q54_6'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q54_7'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q54_8'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q54_9'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q56_1'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q56_2'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q56_3'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q56_4'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q56_5'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q56_6'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q56_7'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q56_8'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q60'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q62'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q64'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO surveyresponses (labelid, responsesequence, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from surveylabels where labelnumber='Q13_1'), -1, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'));

 	
-- DE3445 - No error when AYP School Identifier field is not populated

 UPDATE fieldspecificationsrecordtypes SET fieldspecificationid = (SELECT id FROM fieldspecification WHERE (fieldname,mappedname) IN (('aypSchoolIdentifier' ,'AYP_QPA_Bldg_No')))
	WHERE fieldspecificationid = ( SELECT id FROM fieldspecification WHERE (fieldname,mappedname) IN (('rosterAYPSchoolIdentifier','AYP_QPA_Bldg_No')))
		AND recordtypeid = (SELECT id FROM category WHERE categorycode='TEST_RECORD_TYPE');


--DE3455 - Webservice rejects KIDS and STCO records that have an accent symbol
	 			
 UPDATE fieldspecification SET formatregex = null WHERE fieldname = 'legalLastName' and mappedname ='Legal_Last_Name';
 UPDATE fieldspecification SET formatregex = null WHERE fieldname = 'legalFirstName' and mappedname ='Legal_First_Name';

 UPDATE fieldspecification SET formatregex = null WHERE fieldname = 'legalLastName' and mappedname ='Student_Legal_Last_Name';
 UPDATE fieldspecification SET formatregex = null WHERE fieldname = 'legalFirstName' and mappedname ='Student_Legal_First_Name';			

 
--DE3545 : Immeadiate Upload pulls for same start and end times for roster and enrollment when start or end time is changed
--DE3547 : Scheduled Upload pulls for same start and end times for roster and enrollment when start or end time is changed

 
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
	VALUES ('01/01/2013 01:00:00 AM', 'ROSTER_SCHEDULED_WEB_SERVICE_START_TIME', 'This is the start time from which the records are pulled', (select id from categorytype where typecode='WEB_SERVICE_RECORD_TYPE'), 
	'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
	VALUES ('01/01/2013 01:02:00 AM', 'ROSTER_SCHEDULED_WEB_SERVICE_END_TIME', 'This is the end time from which the records are pulled', (select id from categorytype where typecode='WEB_SERVICE_RECORD_TYPE'), 
	'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
	VALUES ('01/01/2013 01:00:01 AM', 'ROSTER_IMMEDIATE_WEB_SERVICE_START_TIME','This is the start time from which the records are pulled', (select id from categorytype where typecode='WEB_SERVICE_RECORD_TYPE'), 
	'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
	VALUES ('01/01/2013 01:05:00 AM', 'ROSTER_IMMEDIATE_WEB_SERVICE_END_TIME', 'This is the end time untill which the records are pulled', (select id from categorytype where typecode='WEB_SERVICE_RECORD_TYPE'), 
	'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

	
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('rosterFromDate', NULL, NULL, NULL, NULL, true, true, NULL, 'ROSTER_SCHEDULED_WEB_SERVICE_START_TIME', false, now(), 
	(Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('rosterToDate', NULL, NULL, NULL, NULL, true, true, NULL, 'ROSTER_SCHEDULED_WEB_SERVICE_END_TIME', false, now(), 
	(Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('rosterFromDate', NULL, NULL, NULL, NULL, true, true, NULL, 'ROSTER_IMMEDIATE_WEB_SERVICE_START_TIME', false, now(), 
	(Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('rosterToDate', NULL, NULL, NULL, NULL, true, true, NULL, 'ROSTER_IMMEDIATE_WEB_SERVICE_END_TIME', false, now(), 
	(Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));
	

UPDATE fieldspecificationsrecordtypes 
	SET fieldspecificationid = (SELECT id FROM fieldspecification WHERE (fieldname,mappedname) IN (('rosterFromDate' ,'ROSTER_SCHEDULED_WEB_SERVICE_START_TIME')))
	WHERE fieldspecificationid = ( SELECT id FROM fieldspecification WHERE (fieldname,mappedname) IN (('strFromDate','KANSAS_SCHEDULED_WEB_SERVICE_START_TIME')))
		AND recordtypeid = (SELECT id FROM category WHERE categorycode='ROSTER_BY_DATE_INPUT_PARAMETER_SCHEDULED_UPLOAD');

UPDATE fieldspecificationsrecordtypes 
	SET fieldspecificationid = (SELECT id FROM fieldspecification WHERE (fieldname,mappedname) IN (('rosterToDate' ,'ROSTER_SCHEDULED_WEB_SERVICE_END_TIME')))
	WHERE fieldspecificationid = ( SELECT id FROM fieldspecification WHERE (fieldname,mappedname) IN (('strToDate','KANSAS_SCHEDULED_WEB_SERVICE_END_TIME')))
		AND recordtypeid = (SELECT id FROM category WHERE categorycode='ROSTER_BY_DATE_INPUT_PARAMETER_SCHEDULED_UPLOAD');

UPDATE fieldspecificationsrecordtypes 
	SET fieldspecificationid = (SELECT id FROM fieldspecification WHERE (fieldname,mappedname) IN (('rosterFromDate' ,'ROSTER_IMMEDIATE_WEB_SERVICE_START_TIME')))
	WHERE fieldspecificationid = ( SELECT id FROM fieldspecification WHERE (fieldname,mappedname) IN (('strFromDate','KANSAS_IMMEDIATE_WEB_SERVICE_START_TIME')))
		AND recordtypeid = (SELECT id FROM category WHERE categorycode='ROSTER_BY_DATE_INPUT_PARAMETER_IMMEDIATE_UPLOAD');

UPDATE fieldspecificationsrecordtypes 
	SET fieldspecificationid = (SELECT id FROM fieldspecification WHERE (fieldname,mappedname) IN (('rosterToDate' ,'ROSTER_IMMEDIATE_WEB_SERVICE_END_TIME')))
	WHERE fieldspecificationid = ( SELECT id FROM fieldspecification WHERE (fieldname,mappedname) IN (('strToDate','KANSAS_IMMEDIATE_WEB_SERVICE_END_TIME')))
		AND recordtypeid = (SELECT id FROM category WHERE categorycode='ROSTER_BY_DATE_INPUT_PARAMETER_IMMEDIATE_UPLOAD');


 