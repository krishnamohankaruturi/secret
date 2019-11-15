
--F854 script to insert security agreement messages
DO
$BODY$
declare var_pltw_id bigint;
BEGIN
select id from assessmentprogram where abbreviatedname='PLTW' into var_pltw_id;

	IF (var_pltw_id is not null) then
	--PLTW--
     IF NOT EXISTS (
    	SELECT 1
    	FROM appconfiguration conf
    	JOIN assessmentprogram ap ON conf.assessmentprogramid = ap.id
		WHERE conf.activeflag IS TRUE
        AND conf.attrtype = 'addsecuritytext'
        AND ap.abbreviatedname = 'PLTW'
    ) THEN
    	RAISE NOTICE 'Did not find security agreement text for PLTW, inserting...';
    	
		INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser, 
            createddate, modifieduser, modifieddate, assessmentprogramid)
			VALUES ('label.myprofile.generaltxtforpltw_1',
					'addsecuritytext',
					'label.myprofile.generaltxtforpltw_1',
					'The Kite suite provides opportunities for flexible assessment administration. However, all assessments - including instructionally embedded assessments chosen by the teacher and delivered during the year 2019 are secure tests.'
					,12,now(),12,now(),var_pltw_id), 
					
					('label.myprofile.generaltxtforpltw_2',
					'addsecuritytext',
					'label.myprofile.generaltxtforpltw_2',
					'Test administrators and other educational staff who support implementation are responsible for following the Kite test security standards:'
					,12,now(),12,now(),var_pltw_id),
					
					('label.myprofile.generaltxtforpltw_3',
					'addsecuritytext',
					'label.myprofile.generaltxtforpltw_3',
					'1. Assessments (testlets) are not to be stored or saved on computers or personal storage devices; shared via email or other file sharing systems; or reproduced by any means.'
					,12,now(),12,now(),var_pltw_id),
					
					('label.myprofile.generaltxtforpltw_4',
					'addsecuritytext',
					'label.myprofile.generaltxtforpltw_4',
					'2. Except where explicitly allowed as described in the Test Administration Manual, electronic materials used during assessment administration may not be printed.'
					,12,now(),12,now(),var_pltw_id),
					
					('label.myprofile.generaltxtforpltw_5',
					'addsecuritytext',
					'label.myprofile.generaltxtforpltw_5',
					'3. Those who violate the Kite test security standards may be subject to their state''s regulations or state education agency policy governing test security.'
					,12,now(),12,now(),var_pltw_id),
					
					('label.myprofile.generaltxtforpltw_6',
					'addsecuritytext',
					'label.myprofile.generaltxtforpltw_6',
					'4. Educators are encouraged to use resources provided by Kite suite to prepare themselves and their students for the assessments.'
					,12,now(),12,now(),var_pltw_id),
					
				    ('label.myprofile.generaltxtforpltw_7',
					'addsecuritytext',
					'label.myprofile.generaltxtforpltw_7',
					'5. Users will not give out, loan or share their password with anyone. Allowing others access to an Educator Portal account may cause unauthorized access to private information. Access to educational records is governed by federal and state law.'
					,12,now(),12,now(),var_pltw_id),
					
			    	('label.myprofile.generaltxtforpltw_8',
					'addsecuritytext',
					'label.myprofile.generaltxtforpltw_8',
					'6. The PLTW EoC Assessment Administration Manual contains important information that is required to be followed by both teachers and students.
						By clicking below, you verify that you have read the PLTW EoC Assessment Administration Manual and confirm that you agree to follow policies contained within the document.'
					,12,now(),12,now(),var_pltw_id),
					
				    ('label.myprofile.generaltxtforpltw_9',
					'addsecuritytext',
					'label.myprofile.generaltxtforpltw_9',
					'Questions about security expectations should be directed to the local assessment coordinator.'
					,12,now(),12,now(),var_pltw_id);
		
    ELSE
        RAISE NOTICE 'Found security agreement text for PLTW, skipping insert';
    END IF;
         
		 else
		--KAP and DLM --	
		 IF NOT EXISTS (
    	SELECT 1
    	FROM appconfiguration conf
		WHERE conf.activeflag IS TRUE
        AND conf.attrtype = 'addsecuritytext' and coalesce (assessmentprogramid, 0) = 0
    ) THEN
    	RAISE NOTICE 'Did not find security agreement text for general assessment programs, inserting...';
    	
		INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser, 
            createddate, modifieduser, modifieddate, assessmentprogramid)
			VALUES ('label.myprofile.generaltxt_1',
					'addsecuritytext',
					'label.myprofile.generaltxt_1',
					'The Kite suite provides opportunities for flexible assessment administration. However, all assessments - including instructionally embedded assessments chosen by the teacher and delivered during the year 2019 are secure tests.'
					,12,now(),12,now(),var_pltw_id), 
					
					('label.myprofile.generaltxt_2',
					'addsecuritytext',
					'label.myprofile.generaltxt_2',
					'Test administrators and other educational staff who support implementation are responsible for following the Kite test security standards:'
					,12,now(),12,now(),var_pltw_id),
					
					('label.myprofile.generaltxt_3',
					'addsecuritytext',
					'label.myprofile.generaltxt_3',
					'1. Assessments (testlets) are not to be stored or saved on computers or personal storage devices; shared via email or other file sharing systems; or reproduced by any means. '
					,12,now(),12,now(),var_pltw_id),
					
					('label.myprofile.generaltxt_4',
					'addsecuritytext',
					'label.myprofile.generaltxt_4',
					'2. Except where explicitly allowed as described in the Test Administration Manual, electronic materials used during assessment administration may not be printed.'
					,12,now(),12,now(),var_pltw_id),
					
					('label.myprofile.generaltxt_5',
					'addsecuritytext',
					'label.myprofile.generaltxt_5',
					'3. Those who violate the Kite test security standards may be subject to their state''s regulations or state education agency policy governing test security.'
					,12,now(),12,now(),var_pltw_id),
					
					('label.myprofile.generaltxt_6',
					'addsecuritytext',
					'label.myprofile.generaltxt_6',
					'4. Educators are encouraged to use resources provided by Kite suite, including practice activities and released testlets, to prepare themselves and their students for the assessments.'
					,12,now(),12,now(),var_pltw_id),
					
					('label.myprofile.generaltxt_7',
					'addsecuritytext',
					'label.myprofile.generaltxt_7',
					'5. Users will not give out, loan or share their password with anyone. Allowing others access to an Educator Portal account may cause unauthorized access to private information. Access to educational records is governed by federal and state law.'
					,12,now(),12,now(),var_pltw_id),
									
				    ('label.myprofile.generaltxt_8',
					'addsecuritytext',
					'label.myprofile.generaltxt_8',
					'Questions about security expectations should be directed to the local assessment coordinator.'
					,12,now(),12,now(),var_pltw_id);
		
    ELSE
        RAISE NOTICE 'Found security agreement text for general assessment programs , skipping insert';
    END IF;
	END IF;	
    END;
	
$BODY$;