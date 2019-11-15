 -- 457.sql
DROP INDEX IF EXISTS idx_studentreport_attendanceschoolid;
CREATE INDEX idx_studentreport_attendanceschoolid ON studentreport USING btree (attendanceschoolid);

DROP INDEX IF EXISTS idx_studentreport_districtid;
CREATE INDEX idx_studentreport_districtid ON studentreport USING btree (districtid);

DROP INDEX IF EXISTS idx_studentreport_stateid;
CREATE INDEX idx_studentreport_stateid ON studentreport USING btree (stateid);

DROP INDEX IF EXISTS idx_studentreport_assessmentprogramid;
CREATE INDEX idx_studentreport_assessmentprogramid ON studentreport USING btree (assessmentprogramid);

DROP INDEX IF EXISTS idx_studentreport_schoolyear;
CREATE INDEX idx_studentreport_schoolyear ON studentreport USING btree (schoolyear);

DROP INDEX IF EXISTS idx_studentreport_filepath;
CREATE INDEX idx_studentreport_filepath ON studentreport USING btree (filepath);

DROP INDEX IF EXISTS idx_studentreport_aggregates;
CREATE INDEX idx_studentreport_aggregates ON studentreport USING btree (aggregates);

DROP INDEX IF EXISTS idx_studentreport_incompletestatus;
CREATE INDEX idx_studentreport_incompletestatus ON studentreport USING btree (incompletestatus);

DROP INDEX IF EXISTS idx_organizationreportdetails_assessmentprogramid;
CREATE INDEX idx_organizationreportdetails_assessmentprogramid ON organizationreportdetails USING btree (assessmentprogramid);  

DROP INDEX IF EXISTS idx_organizationreportdetails_contentareaid;
CREATE INDEX idx_organizationreportdetails_contentareaid ON organizationreportdetails USING btree (contentareaid);

DROP INDEX IF EXISTS idx_organizationreportdetails_gradeid;
CREATE INDEX idx_organizationreportdetails_gradeid ON organizationreportdetails USING btree (gradeid);

DROP INDEX IF EXISTS idx_organizationreportdetails_organizationid;
CREATE INDEX idx_organizationreportdetails_organizationid ON organizationreportdetails USING btree (organizationid);

DROP INDEX IF EXISTS idx_organizationreportdetails_schoolyear;
CREATE INDEX idx_organizationreportdetails_schoolyear ON organizationreportdetails USING btree (schoolyear);

DROP INDEX IF EXISTS idx_organizationreportdetails_detailedreportpath;
CREATE INDEX idx_organizationreportdetails_detailedreportpath ON organizationreportdetails USING btree (detailedreportpath);

DROP INDEX IF EXISTS idx_organizationreportdetails_schoolreportpdfpath;
CREATE INDEX idx_organizationreportdetails_schoolreportpdfpath ON organizationreportdetails USING btree (schoolreportpdfpath);

DROP INDEX IF EXISTS idx_organizationreportdetails_gradecourseabbrname;
CREATE INDEX idx_organizationreportdetails_gradecourseabbrname ON organizationreportdetails USING btree (gradecourseabbrname);

DROP INDEX IF EXISTS idx_reportsmedianscore_assessmentprogramid;
CREATE INDEX idx_reportsmedianscore_assessmentprogramid ON reportsmedianscore USING btree (assessmentprogramid);

DROP INDEX IF EXISTS idx_reportsmedianscore_contentareaid;
CREATE INDEX idx_reportsmedianscore_contentareaid ON reportsmedianscore USING btree (contentareaid);
  
DROP INDEX IF EXISTS idx_reportsmedianscore_gradeid;
CREATE INDEX idx_reportsmedianscore_gradeid ON reportsmedianscore USING btree (gradeid);

DROP INDEX IF EXISTS idx_reportsmedianscore_organizationid;
CREATE INDEX idx_reportsmedianscore_organizationid ON reportsmedianscore USING btree (organizationid);

DROP INDEX IF EXISTS idx_reportsmedianscore_organizationtypeid;
CREATE INDEX idx_reportsmedianscore_organizationtypeid ON reportsmedianscore USING btree (organizationtypeid);

DROP INDEX IF EXISTS idx_reportsmedianscore_subscoredefinitionname;
CREATE INDEX idx_reportsmedianscore_subscoredefinitionname ON reportsmedianscore USING btree (subscoredefinitionname);

DROP INDEX IF EXISTS idx_reportspercentbylevel_organizationid;
CREATE INDEX idx_reportspercentbylevel_organizationid ON reportspercentbylevel USING btree (organizationid);

DROP INDEX IF EXISTS idx_reportspercentbylevel_assessmentprogramid;
CREATE INDEX idx_reportspercentbylevel_assessmentprogramid ON reportspercentbylevel USING btree (assessmentprogramid);

DROP INDEX IF EXISTS idx_reportspercentbylevel_gradeid;
CREATE INDEX idx_reportspercentbylevel_gradeid ON reportspercentbylevel USING btree (gradeid);

DROP INDEX IF EXISTS idx_reportspercentbylevel_contentareaid;
CREATE INDEX idx_reportspercentbylevel_contentareaid ON reportspercentbylevel USING btree (contentareaid);

DROP INDEX IF EXISTS idx_reportspercentbylevel_level;
CREATE INDEX idx_reportspercentbylevel_level ON reportspercentbylevel USING btree (level);

DROP INDEX IF EXISTS idx_reportspercentbylevel_percent;
CREATE INDEX idx_reportspercentbylevel_percent ON reportspercentbylevel USING btree (percent);

DROP INDEX IF EXISTS idx_reportspercentbylevel_schoolyear;
CREATE INDEX idx_reportspercentbylevel_schoolyear ON reportspercentbylevel USING btree (schoolyear);

DROP INDEX IF EXISTS idx_reportsubscores_studentid;
CREATE INDEX idx_reportsubscores_studentid ON reportsubscores USING btree (studentid);

DROP INDEX IF EXISTS idx_reportsubscores_subscoredefinitionname;
CREATE INDEX idx_reportsubscores_subscoredefinitionname ON reportsubscores USING btree (subscoredefinitionname);

DROP INDEX IF EXISTS idx_reportsubscores_subscorerawscore;
CREATE INDEX idx_reportsubscores_subscorerawscore ON reportsubscores USING btree (subscorerawscore);

DROP INDEX IF EXISTS idx_reportsubscores_subscorescalescore;
CREATE INDEX idx_reportsubscores_subscorescalescore ON reportsubscores USING btree (subscorescalescore);

DROP INDEX IF EXISTS idx_reportprocess_assessmentprogramid;
CREATE INDEX idx_reportprocess_assessmentprogramid ON reportprocess USING btree (assessmentprogramid);

DROP INDEX IF EXISTS idx_reportprocess_subjectid;
CREATE INDEX idx_reportprocess_subjectid ON reportprocess USING btree (subjectid);

DROP INDEX IF EXISTS idx_reportprocess_gradeid;
CREATE INDEX idx_reportprocess_gradeid ON reportprocess USING btree (gradeid);

DROP INDEX IF EXISTS idx_reportprocess_status;
CREATE INDEX idx_reportprocess_status ON reportprocess USING btree (status);

DROP INDEX IF EXISTS idx_reportprocessreason_studentid;
CREATE INDEX idx_reportprocessreason_studentid ON reportprocessreason USING btree (studentid);

DROP INDEX IF EXISTS idx_enrollment_exitwithdrawaltype;
CREATE INDEX idx_enrollment_exitwithdrawaltype ON enrollment USING btree (exitwithdrawaltype);

DROP INDEX IF EXISTS idx_studentreport_levelid;
CREATE INDEX idx_studentreport_levelid ON studentreport USING btree (levelid);

DROP INDEX IF EXISTS idx_reportsubscores_studentreportid;
CREATE INDEX idx_reportsubscores_studentreportid ON reportsubscores USING btree (studentreportid);

DROP INDEX IF EXISTS idx_testsession_roster;
