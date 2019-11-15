--DROP FUNCTION check_student_pnp_and_stage4_test_availability(studentidentifier text);
CREATE OR REPLACE FUNCTION check_student_pnp_and_stage4_test_availability(studentidentifier text)
  RETURNS void AS
$BODY$
DECLARE       
       signedselected text;
       aslselectedvalue text;
       temprecord record;
       studenttestrecord record;
       brailleselected text;
BEGIN	
      
              
                
		--RAISE NOTICE 'statestudentIdentifier: %', studentidentifier;
		
		select st.id, st.status,st.createddate 
			from studentstests st
			join student s ON st.studentid = s.id and s.statestudentidentifier in(studentidentifier) and s.stateid = 51
			join testsession ts ON ts.id = st.testsessionid and ts.stageid = 5 
			join testcollection tc ON tc.id = ts.testcollectionid and tc.contentareaid = 3 where st.activeflag is true order by st.studentid, ts.stageid
			limit 1 
			INTO studenttestrecord;

		IF studenttestrecord IS NOT NULL THEN
				RAISE INFO 'stage_4 test was assigned to student with status ''%'', studenttestid ''%'' createddate ''%'' ',studenttestrecord.status, 
				studenttestrecord.id, studenttestrecord.createddate;
			ELSE
				select spav.selectedvalue INTO signedselected
				from profileitemattributenameattributecontainer pianac
				join profileitemattribute pia on pianac.attributenameid = pia.id and pia.attributename = 'assignedSupport'
				join profileitemattributecontainer piac on pianac.attributecontainerid = piac.id  
				left join studentprofileitemattributevalue spav on pianac.id = spav.profileitemattributenameattributecontainerid 
				and spav.studentid in(select id from student where statestudentidentifier = studentidentifier and stateid=51)
				where piac.attributecontainer in('Signing');

				select spav.selectedvalue INTO brailleselected
				from profileitemattributenameattributecontainer pianac
				join profileitemattribute pia on pianac.attributenameid = pia.id and pia.attributename = 'assignedSupport'
				join profileitemattributecontainer piac on pianac.attributecontainerid = piac.id  
				left join studentprofileitemattributevalue spav on pianac.id = spav.profileitemattributenameattributecontainerid 
				and spav.studentid in(select id from student where statestudentidentifier = studentidentifier and stateid=51)
				where piac.attributecontainer in('braille');
				
				select selectedvalue INTO aslselectedvalue
					from profileitemattributenameattributecontainer pianac
					join profileitemattribute pia  on pianac.attributenameid = pia.id 
					left join studentprofileitemattributevalue spav on pianac.id = spav.profileitemattributenameattributecontainerid 
					and spav.studentid in(select id from student where 
					statestudentidentifier = studentidentifier and stateid = 51)	where  pia.attributename in ('SigningType');

		
				IF(signedselected IS NOT NULL AND signedselected <> '' AND signedselected = 'true' AND aslselectedvalue IS NOT NULL AND aslselectedvalue <> '' AND aslselectedvalue = 'asl') THEN
			
					RAISE INFO 'student has ASL PNP setting ''%''', studentidentifier;

				ELSIF brailleselected IS NOT NULL THEN

					RAISE INFO 'student has Braille PNP setting ''%''', studentidentifier;
				ELSE			
					RAISE INFO 'stage_4 test was not assigned to student ''%'' please verify student record',studentidentifier;
				END IF;
		END IF;
	 
             
       END;
       $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
