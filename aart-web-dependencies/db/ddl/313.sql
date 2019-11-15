--313.sql --indexes for pd reports

DROP INDEX IF EXISTS idx_userorganizationsgroups_groupid;
CREATE INDEX idx_userorganizationsgroups_groupid ON userorganizationsgroups USING btree (groupid); 

DROP INDEX IF EXISTS idx_userorganizationsgroups_userorganizationid;
CREATE INDEX idx_userorganizationsgroups_userorganizationid ON userorganizationsgroups USING btree (userorganizationid); 

DROP INDEX IF EXISTS idx_usersorganizations_aartuserid;
CREATE INDEX idx_usersorganizations_aartuserid ON usersorganizations USING btree (aartuserid); 

DROP INDEX IF EXISTS idx_usersorganizations_organizationid;
CREATE INDEX idx_usersorganizations_organizationid ON usersorganizations USING btree (organizationid); 

DROP INDEX IF EXISTS idx_groupauthorities_groupid;
CREATE INDEX idx_groupauthorities_groupid ON groupauthorities USING btree (groupid); 

DROP INDEX IF EXISTS idx_groupauthorities_authorityid;
CREATE INDEX idx_groupauthorities_authorityid ON groupauthorities USING btree (authorityid);

DROP INDEX IF EXISTS idx_usermodule_moduleid;
CREATE INDEX idx_usermodule_moduleid ON usermodule USING btree (moduleid);

DROP INDEX IF EXISTS idx_usermodule_userid;
CREATE INDEX idx_usermodule_userid ON usermodule USING btree (userid); 

DROP INDEX IF EXISTS idx_usermodule_enrollmentstatusid;
CREATE INDEX idx_usermodule_enrollmentstatusid ON usermodule USING btree (enrollmentstatusid); 

DROP INDEX IF EXISTS idx_usermodule_stateid;
CREATE INDEX idx_usermodule_stateid ON usermodule USING btree (stateid); 

DROP INDEX IF EXISTS idx_modulestate_statusid;
CREATE INDEX idx_modulestate_statusid ON modulestate USING btree (statusid);  

DROP INDEX IF EXISTS idx_modulestate_stateid;
CREATE INDEX idx_modulestate_stateid ON modulestate USING btree (stateid);

DROP INDEX IF EXISTS idx_assessmentprogram_abbreviatedname;
CREATE INDEX idx_assessmentprogram_abbreviatedname ON assessmentprogram USING btree (abbreviatedname);

DROP INDEX IF EXISTS idx_student_comprehensiverace;
CREATE INDEX idx_student_comprehensiverace ON student USING btree (comprehensiverace);

CREATE OR REPLACE FUNCTION pdenrolltomodule(in_moduleid bigint, in_userid bigint, in_enrolledstatusid bigint, in_stateid bigint, in_createduserid bigint)
  RETURNS integer AS
$BODY$
  BEGIN
	update usermodule set modifieduser = in_createduserid, modifieddate = now()
	    where userid = in_userId and moduleid = in_moduleid and activeflag is true and stateid = in_stateid
	    and in_enrolledstatusid in (
		    SELECT c.id FROM category c, categorytype ct WHERE c.categorytypeid = ct.id 
		    AND c.categorycode IN ('ENROLLED','INPROGRESS','COMPLETED') AND ct.typecode='USER_MODULE_STATUS'
	    );
      
	IF NOT FOUND THEN
		insert into usermodule (id, userid, moduleid, 
		      enrollmentstatusid, createddate, modifieddate, createduser, modifieduser, activeflag, stateid
		      )
		    values (nextval('usermodule_id_seq'), in_userid, in_moduleid,
		      in_enrolledstatusid, now(), now(), in_createduserid, in_createduserid, true, in_stateid);
	END IF;	
	RETURN 1;
  EXCEPTION WHEN OTHERS THEN
    RETURN 0;    
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;