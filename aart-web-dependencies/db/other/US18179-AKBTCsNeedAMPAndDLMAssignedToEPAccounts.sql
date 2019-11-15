-- US18179 : AK BTCs need AMP and DLM assigned to EP accounts
DO 
$BODY$
DECLARE
   amp_assessment_program_id BIGINT;
   dlm_assessment_program_id BIGINT;   
   amp_update_count INTEGER;
   amp_insert_count INTEGER;
   dlm_insert_count INTEGER;
   dlm_update_count INTEGER;
   insert_assessment_count INTEGER;
   aartUserRecord RECORD;
   btc_group_id BIGINT;

BEGIN
  SELECT id FROM assessmentprogram WHERE abbreviatedname = 'AMP' INTO amp_assessment_program_id;
  SELECT id FROM assessmentprogram WHERE abbreviatedname = 'DLM' INTO dlm_assessment_program_id;
  SELECT id FROM groups WHERE groupcode = 'BTC' INTO btc_group_id;
  amp_update_count := 0;
  amp_insert_count := 0;
  dlm_insert_count := 0;
  dlm_update_count := 0;

 FOR aartUserRecord IN (SELECT distinct au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id 
                   JOIN userorganizationsgroups usog ON usog.userorganizationid = uso.id
                   WHERE usog.groupid = btc_group_id AND uso.organizationid 
			IN (SELECT schoolid FROM organizationtreedetail WHERE statedisplayidentifier = 'AK')) LOOP                

      IF((SELECT count(*) FROM userassessmentprogram WHERE aartuserid = aartUserRecord.id AND assessmentprogramid = amp_assessment_program_id) = 0) THEN

          INSERT INTO userassessmentprogram(aartuserid, assessmentprogramid, activeflag) VALUES(aartUserRecord.id, amp_assessment_program_id, true);

          amp_insert_count := amp_insert_count + 1;

          
      ELSE 
         IF((SELECT count(*) FROM userassessmentprogram WHERE aartuserid = aartUserRecord.id 
                 AND assessmentprogramid = amp_assessment_program_id AND activeflag is false) = 1) THEN

            UPDATE userassessmentprogram SET activeflag = true WHERE aartuserid = aartUserRecord.id 
                  AND assessmentprogramid = amp_assessment_program_id AND activeflag is false;

            amp_update_count := amp_update_count + 1;
            
         END IF;
      END IF;

      IF((SELECT count(*) FROM userassessmentprogram WHERE aartuserid = aartUserRecord.id AND assessmentprogramid = dlm_assessment_program_id) = 0) THEN

          INSERT INTO userassessmentprogram(aartuserid, assessmentprogramid, activeflag) VALUES(aartUserRecord.id, dlm_assessment_program_id, true);

          dlm_insert_count := dlm_insert_count + 1;
          
      ELSE 
         IF((SELECT count(*) FROM userassessmentprogram WHERE aartuserid = aartUserRecord.id 
                 AND assessmentprogramid = dlm_assessment_program_id AND activeflag is false) = 1) THEN

            UPDATE userassessmentprogram SET activeflag = true WHERE aartuserid = aartUserRecord.id 
                  AND assessmentprogramid = amp_assessment_program_id AND activeflag is false;

            dlm_update_count := dlm_update_count + 1;
            
         END IF;
      END IF;         
 END LOOP;
 RAISE NOTICE 'For % BTCS AMP assessment is added', amp_insert_count; 
 RAISE NOTICE 'For % BTCS AMP assessment is set to true from false', amp_update_count;
 RAISE NOTICE 'For % BTCS DLM assessment is added', dlm_insert_count; 
 RAISE NOTICE 'For % BTCS DLM assessment is set to true from false', dlm_update_count;
END;
$BODY$;