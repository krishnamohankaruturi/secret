--dml/776.sql
--interim teacher feedback

DO
$BODY$
DECLARE
	PLTWID bigint := (SELECT id FROM assessmentprogram WHERE abbreviatedname = 'PLTW');
	SYSUSER bigint := (SELECT id FROM aartuser WHERE username = 'cetesysadmin');
BEGIN
	IF EXISTS (SELECT 1 FROM assessmentprogram WHERE abbreviatedname = 'PLTW') THEN

		INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag,
			createduser, createddate, modifieduser, modifieddate, assessmentprogramid)
		VALUES(
			'db_end_of_time',
			'constant',
			'Common timestamp for when an enddate is not specified',
			'9999-12-31'::TIMESTAMPTZ,
			TRUE,
			SYSUSER,
			'now'::TEXT::TIMESTAMP WITH TIME ZONE,
			SYSUSER,
			'now'::TEXT::TIMESTAMP WITH TIME ZONE,
			PLTWID
		);

		DECLARE
			ENDOFTIME timestamptz := (SELECT attrvalue::text::TIMESTAMP WITH TIME ZONE FROM appconfiguration 
				WHERE attrcode = 'db_end_of_time' AND activeflag = TRUE 
				AND assessmentprogramid = PLTWID);
			
		BEGIN
	
			insert into feedbackquestions(tasktypeexternalid, questionsequence, questiontext, 
										  questionoptions, required, startdate, enddate, assessmentprogramid, 
										  contentareaid, createduser, modifieduser, createddate, modifieddate)
			values((SELECT externalid FROM tasktype WHERE code = 'passage' ORDER BY modifieddate DESC LIMIT 1), 0, 
				'How much do you agree with the following statements?', null, false, now(), 
				ENDOFTIME, PLTWID, null, SYSUSER, SYSUSER, now(), now());
		
			insert into feedbackquestions(tasktypeexternalid, questionsequence, questiontext, 
										  questionoptions, required, startdate, enddate, assessmentprogramid, 
										  contentareaid, createduser, modifieduser, createddate, modifieddate)
			values((SELECT externalid FROM tasktype WHERE code = 'MC-K' ORDER BY modifieddate DESC LIMIT 1), 1,
				'The questions on this assessment were closely related to what I taught in the course.', 
								'[{"index": 1,"label": "Strongly agree","value": "Strongly_agree"}, 
									{"index": 2,"label": "Agree","value": "Agree"},
									{"index": 3,"label": "Neither agree nor disagree","value": "Neither_agree_nor_disagree"},
									{"index": 4,"label": "Disagree","value": "Disagree"},
									{"index": 5,"label": "Strongly disagree","value": "Strongly_disagree"}]', 
				true, now(), ENDOFTIME, PLTWID, null, SYSUSER, SYSUSER, now(), now());
	
			insert into feedbackquestions(tasktypeexternalid, questionsequence, questiontext, 
										  questionoptions, required, startdate, enddate, assessmentprogramid, 
										  contentareaid, createduser, modifieduser, createddate, modifieddate)
			values((SELECT externalid FROM tasktype WHERE code = 'MC-K' ORDER BY modifieddate DESC LIMIT 1), 2, 
				'The assessment included skills that I expect my students to encounter in the real world.', 
								'[{"index": 1,"label": "Strongly agree","value": "Strongly_agree"}, 
									{"index": 2,"label": "Agree","value": "Agree"},
									{"index": 3,"label": "Neither agree nor disagree","value": "Neither_agree_nor_disagree"},
									{"index": 4,"label": "Disagree","value": "Disagree"},
									{"index": 5,"label": "Strongly disagree","value": "Strongly_disagree"}]', 
				true, now(), ENDOFTIME, PLTWID, null, SYSUSER, SYSUSER, now(), now());
									
	
			insert into feedbackquestions(tasktypeexternalid, questionsequence, questiontext, 
										  questionoptions, required, startdate, enddate, assessmentprogramid, 
										  contentareaid, createduser, modifieduser, createddate, modifieddate)
			values((SELECT externalid FROM tasktype WHERE code = 'MC-K' ORDER BY modifieddate DESC LIMIT 1), 3, 
				'The exam questions were different than other tests than I have given before (and, different in a good way).', 
								'[{"index": 1,"label": "Strongly agree","value": "Strongly_agree"}, 
									{"index": 2,"label": "Agree","value": "Agree"},
									{"index": 3,"label": "Neither agree nor disagree","value": "Neither_agree_nor_disagree"},
									{"index": 4,"label": "Disagree","value": "Disagree"},
									{"index": 5,"label": "Strongly disagree","value": "Strongly_disagree"}]', 
				true, now(), ENDOFTIME, PLTWID, null, SYSUSER, SYSUSER, now(), now());
									
									
			insert into feedbackquestions(tasktypeexternalid, questionsequence, questiontext, 
										  questionoptions, required, startdate, enddate, assessmentprogramid, 
										  contentareaid, createduser, modifieduser, createddate, modifieddate)
			values((SELECT externalid FROM tasktype WHERE code = 'MC-K' ORDER BY modifieddate DESC LIMIT 1), 4, 
				'The exam questions were interesting.', 
								'[{"index": 1,"label": "Strongly agree","value": "Strongly_agree"}, 
									{"index": 2,"label": "Agree","value": "Agree"},
									{"index": 3,"label": "Neither agree nor disagree","value": "Neither_agree_nor_disagree"},
									{"index": 4,"label": "Disagree","value": "Disagree"},
									{"index": 5,"label": "Strongly disagree","value": "Strongly_disagree"}]', 
				true, now(), ENDOFTIME, PLTWID, null, SYSUSER, SYSUSER, now(), now());
	
			insert into feedbackquestions(tasktypeexternalid, questionsequence, questiontext, 
										  questionoptions, required, startdate, enddate, assessmentprogramid, 
										  contentareaid, createduser, modifieduser, createddate, modifieddate)
			values((SELECT externalid FROM tasktype WHERE code = 'ER' ORDER BY modifieddate DESC LIMIT 1 ), 5, 
				'Do you have any other thoughts or comments on this set of items?', null, false, now(), 
				ENDOFTIME, PLTWID, null, SYSUSER, SYSUSER, now(), now());
				
		END;
	END IF;
END;
$BODY$;