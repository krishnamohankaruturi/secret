--dml/753.sql

-- TDE UA Error
DO $BODY$ 
begin
		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='uaerrorpage_error_message') THEN
			INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
				VALUES ('uaerrorpage_error_message','message','uaerror_message','Please download the latest Kite Student Portal to continue.',12,now(),12,now());
		END IF;
end; $BODY$;

update surveylabel set label='Select the student''s Primary Disability. -Primary Disability'
where labelnumber='Q16';

update surveylabel set label='Classification of Hearing Impairment.'
where labelnumber='Q330';

update surveylabel set label='Hearing.'
where labelnumber='Q19';

update surveylabel set label='Hearing: Mark all that apply. -Uses sign language'
where labelnumber='Q20_6';

update surveylabel set label='Hearing: Mark all that apply. -Uses oral language'
where labelnumber='Q20_5';

update surveylabel set label='Hearing: Mark all that apply. -Has cochlear implant'
where labelnumber='Q20_4';

update surveylabel set label='Hearing: Mark all that apply. -Uses bilateral hearing aid'
where labelnumber='Q20_3';

update surveylabel set label='Hearing: Mark all that apply. -Uses unilateral hearing aid'
where labelnumber='Q20_2';

update surveylabel set label='Hearing: Mark all that apply. -Uses personal or classroom amplification (e.g., personal FM device)'
where labelnumber='Q20_1';

update surveylabel set label='Vision: Mark all that apply. -Requires tactical media (objects, tactile graphics and tactile symbols)'
where labelnumber='Q23_2';

update surveylabel set label='Vision: Mark all that apply. -Requires or uses Braille'
where labelnumber='Q23_3';

update surveylabel set label='Classification of Visual Impairment:Mark all that apply. -Cortical Visual Impairment'
where labelnumber='Q429_5';

update surveylabel set label='Classification of Visual Impairment:Mark all that apply. -Totally Blind'
where labelnumber='Q429_4';

update surveylabel set label='Classification of Visual Impairment:Mark all that apply. -Light Perception Only'
where labelnumber='Q429_3';

update surveylabel set label='Classification of Visual Impairment:Mark all that apply. -Legally Blind (acuity of 20/200 or less or field loss to 20 degrees or less in the better eye with correction)'
where labelnumber='Q429_2';

update surveylabel set label='Classification of Visual Impairment: Mark all that apply. -Low Vision (acuity of 20/70 to 20/200 in the better eye with correction)'
where labelnumber='Q429_1';

update surveylabel set label='Vision.'
where labelnumber='Q22';

update surveylabel set label='Vision: Mark all that apply. -Requires enlarged print'
where labelnumber='Q23_1';

update surveylabel set label='If the student reads Braille, select all types the student uses. -Uncontracted Braille'
where labelnumber='Q24_1';

update surveylabel set label='If the student reads Braille, select all types the student uses. -Unified English Braille (UEB)'
where labelnumber='Q24_3';

update surveylabel set label='If the student reads Braille, select all types the student uses. -Contracted Braille'
where labelnumber='Q24_2';

update surveylabel set label='Technological Visual Aids: Mark all that apply. -Device with refreshable Braille display'
where labelnumber='Q25_5';

update surveylabel set label='Technological Visual Aids: Mark all that apply. -Manual (e.g., Perkins Brailler) or Electronic (e.g., Mountbatten Brailler) Braille writing device'
where labelnumber='Q25_4';

update surveylabel set label='Technological Visual Aids: Mark all that apply. -Screen reader and/or talking word processor'
where labelnumber='Q25_3';

update surveylabel set label='Technological Visual Aids: Mark all that apply. -Screen magnification device (fits over standard monitor) or software (e.g., Closeview for Mac, ZoomText)'
where labelnumber='Q25_1';

update surveylabel set label='Technological Visual Aids: Mark all that apply. -CCTV'
where labelnumber='Q25_2';

update surveylabel set label='Arm and hand control: Mark all that apply. -Uses two hands together to perform tasks'
where labelnumber='Q29_1';

update surveylabel set label='Arm and hand control: Mark all that apply. -Cannot use hands to complete tasks even with assistance'
where labelnumber='Q29_4';

update surveylabel set label='Arm and hand control: Mark all that apply. -Requires physical assistance to perform tasks with hands'
where labelnumber='Q29_3';

update surveylabel set label='Arm and hand control: Mark all that apply. -Uses only one hand to perform tasks'
where labelnumber='Q29_2';

update surveylabel set label='Level of attention to computer-directed instruction.'
where labelnumber='Q201';

update surveylabel set label='Level of attention to teacher-directed instruction.'
where labelnumber='Q202';

update surveylabel set label='Computer Use: Select the student''s primary use of a computer during instruction.'
where labelnumber='Q143';

update surveylabel set label='Computer access: Mark all that apply. -Touch screen (e.g., touch screen computer, tablet, iPad, iPod touch)'
where labelnumber='Q33_5';

update surveylabel set label='Computer access during instruction: Mark all that apply. -Scanning with switches (one or two-switch scanning)'
where labelnumber='Q33_11';

update surveylabel set label='Computer access during instruction: Mark all that apply. -Eye gaze technology (e.g., Tobii, EyeGaze Edge)'
where labelnumber='Q33_8';

update surveylabel set label='Computer access during instruction: Mark all that apply. -Standard mouse or head mouse'
where labelnumber='Q33_6';

update surveylabel set label='Computer access during instruction: Mark all that apply. -Standard computer keyboard'
where labelnumber='Q33_1';

update surveylabel set label='Computer access during instruction: Mark all that apply. -Keyboard with large keys or alternative keyboard (e.g., Intellikeys)'
where labelnumber='Q33_3';

update surveylabel set label='Choose the highest statement that describes the student''s expressive communication with speech.'
where labelnumber='Q37';

update surveylabel set label='Select the student''s primary sign system.'
where labelnumber='Q41';

update surveylabel set label='Choose the highest statement that describes the student''s expressive communication with sign language.'
where labelnumber='Q40';

update surveylabel set label='Choose the highest statement that describes the student''s expressive communication with augmentative or alternative communication.'
where labelnumber='Q44';

update surveylabel set label='If the student does not use speech, sign language, or augmentative or alternative communication, which of the following statements best describes the students expressive communication? Choose the highest statement that applies.'
where labelnumber='Q47';

update surveylabel set label='Reading skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill. -H) Explains or elaborates on text read in print or Braille'
where labelnumber='Q518';

update surveylabel set label='Reading skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill. -C) Matches sounds to symbols or signs to symbols (e.g., matches sounds to letters presented visually or tactually, matches spoken or signed words to to written words)'
where labelnumber='Q513';

update surveylabel set label='Reading skills: MARK EACH ONE to show how consistently the student uses each skill.<br/> If the student previously demonstrated and no longer receives instruction, mark "More than 80%". -A) Recognizes single symbols presented visually or tactually (e.g., letters, numerals, environmental signs such as restroom symbols, logos, trademarks, or business signs such as fast food restaurants)'
where labelnumber='Q511';

update surveylabel set label='Reading skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill. -G) Reads text presented in print or Braille without symbol support and WITH comprehension (e.g., locates answers in text, reads and answers questions, retells after reading, completes maze task)'
where labelnumber='Q517';

update surveylabel set label='Reading skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill. -F) Reads text presented in print or Braille without symbol support but WITHOUT comprehension'
where labelnumber='Q516';

update surveylabel set label='Reading skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill. -B) Understands purpose of print or Braille but not necessarily by manipulating a book (e.g., knows correct orientation, can find beginning of text, understands purpose of text in print or Braille, enjoys being read to)'
where labelnumber='Q512';

update surveylabel set label='Reading skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill. -E) Identifies individual words without symbol support (e.g., recognizes words in print or Braille; can choose correct word using eye gaze)'
where labelnumber='Q515';

update surveylabel set label='Reading skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill. -D) Reads words, phrases, or sentences in print or Braille when symbols are provided with the words'
where labelnumber='Q514';

update surveylabel set label='Student''s approximate instructional level of reading text with comprehension (print or Braille): Mark the highest one that applies.'
where labelnumber='Q52';

update surveylabel set label='Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill. <br/>-G) Forms groups of objects for multiplication or division'
where labelnumber='Q547';

update surveylabel set label='Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill. <br/>-H) Multiplies and/or divides using numerals'
where labelnumber='Q548';

update surveylabel set label='Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill. <br/>-J) Uses a calculator'
where labelnumber='Q5410';

update surveylabel set label='Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill. <br/>-L) Uses common measuring tools (e.g., ruler or measuring cup)'
where labelnumber='Q5412';

update surveylabel set label='Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill. <br/>-K) Tells time using an analog or digital clock'
where labelnumber='Q5411';

update surveylabel set label='Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill. <br/>-I) Uses an abacus'
where labelnumber='Q549';

update surveylabel set label='Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill. <br/>-M) Uses a schedule, agenda, or calendar to identify or anticipate sequence of activities'
where labelnumber='Q5413';

update surveylabel set label='Math skills: MARK EACH ONE A) Creates or matches patterns of objects or imagesto show how consistently the student uses each skill. <br/>If the student previously demonstrated and no longer receives instruction, mark "More than 80%".-A) Creates or matches patterns of objects or images'
where labelnumber='Q541';

update surveylabel set label='Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill.<br/>-B) Identifies simple shapes in 2 or 3 dimensions (e.g., square, circle, triangle, cube, sphere)'
where labelnumber='Q542';

update surveylabel set label='Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill. <br/>-C) Sorts objects by common properties (e.g., color, size, shape)'
where labelnumber='Q543';

update surveylabel set label='Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill. <br/>-D) Counts more than two objects'
where labelnumber='Q544';

update surveylabel set label='Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill. <br/>-E) Adds or subtracts by joining or separating groups of objects'
where labelnumber='Q545';

update surveylabel set label='Math skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill. <br/>-F) Adds and/or subtracts using numerals'
where labelnumber='Q546';

update surveylabel set label='Writing skills: Indicate the highest level that describes the student''s writing skills. Choose the highest level that the student has demonstrated even once during instruction, not the highest level demonstrated consistently. Writing includes any method the student uses to write using any writing tool that includes access to all 26 letters of the alphabet. Examples of these tools include paper and pencil, traditional keyboards, alternate keyboards and eye-gaze displays of letters.'
where labelnumber='Q500';
