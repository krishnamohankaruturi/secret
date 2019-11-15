--add constraints 
--SELECT 'ALTER TABLE "'||nspname||'"."'||relname||'" ADD CONSTRAINT "'||conname||'" '||
--   pg_get_constraintdef(pg_constraint.oid)||';'
-- FROM pg_constraint
-- INNER JOIN pg_class ON conrelid=pg_class.oid
-- INNER JOIN pg_namespace ON pg_namespace.oid=pg_class.relnamespace
-- where relname in ('studentsresponses','studentstestsectionstasksfoils','studentstestsectionstasks',
-- 'studentsresponseparameters','exitwithoutsavetest','studentstestsections','studentadaptivetestthetastatus',
-- 'studentadaptivetestfinaltheta','studentsadaptivetestsections','studentspecialcircumstance',
-- 'studentstests','testsession','enrollmenttesttypesubjectarea','enrollmentsrosters', 'enrollment', 'roster')
-- ORDER BY CASE WHEN contype='f' THEN 0 ELSE 1 END DESC,contype DESC,nspname DESC,relname DESC,conname DESC;
 
 --ALTER TABLE "public"."studentsresponseparameters" ADD CONSTRAINT "studentsresponses_ukey" UNIQUE (studentstestsectionsid, taskvariantid);
 ALTER TABLE "public"."enrollmentsrosters" ADD CONSTRAINT "enrollment_roster_uk" UNIQUE (enrollmentid, rosterid);
 ALTER TABLE "public"."enrollment" ADD CONSTRAINT "enrollment_uk" UNIQUE (studentid, attendanceschoolid, currentschoolyear, aypschoolid);
 --ALTER TABLE "public"."testsession" ADD CONSTRAINT "testsession_pkey" PRIMARY KEY (id);
 --ALTER TABLE "public"."studentstestsectionstasksfoils" ADD CONSTRAINT "studentstestsectionstasksfoils_pkey" PRIMARY KEY (studentstestsectionsid, taskid, foilid);
 --ALTER TABLE "public"."studentstestsectionstasks" ADD CONSTRAINT "studentstestsectionstasks_pkey" PRIMARY KEY (studentstestsectionsid, taskid);
 --ALTER TABLE "public"."studentstestsections" ADD CONSTRAINT "studentstestsections_pkey" PRIMARY KEY (id);
 --ALTER TABLE "public"."studentstests" ADD CONSTRAINT "studentstests_pkey" PRIMARY KEY (id);
 --ALTER TABLE "public"."studentsresponses" ADD CONSTRAINT "studentsresponses_pkey" PRIMARY KEY (studentstestsectionsid, taskvariantid);
 --ALTER TABLE "public"."studentsresponseparameters" ADD CONSTRAINT "studentsresponseparameters_pkey" PRIMARY KEY (id);
 ALTER TABLE "public"."studentsadaptivetestsections" ADD CONSTRAINT "uk_studentsadaptivetestsections" PRIMARY KEY (studentstestid, testpartid, testsectionid);
 ALTER TABLE "public"."studentadaptivetestthetastatus" ADD CONSTRAINT "uk_studentadaptivetestthetastatus" PRIMARY KEY (studentstestid, testpartnumber, testsectioncontainernumber);
 ALTER TABLE "public"."studentadaptivetestfinaltheta" ADD CONSTRAINT "uk_studentadaptivetestfinaltheta" PRIMARY KEY (studentstestid, testconstructid);
 --ALTER TABLE "public"."roster" ADD CONSTRAINT "roster_pk" PRIMARY KEY (id);
 --ALTER TABLE "public"."enrollmenttesttypesubjectarea" ADD CONSTRAINT "pk_enrollmenttesttypesubjectarea_id" PRIMARY KEY (id);
 --ALTER TABLE "public"."enrollmentsrosters" ADD CONSTRAINT "enrollment_roster_pk" PRIMARY KEY (id);
 --ALTER TABLE "public"."enrollment" ADD CONSTRAINT "enrollment_pkey" PRIMARY KEY (id);
 ALTER TABLE "public"."testsession" ADD CONSTRAINT "testsession_testtypeid_fk" FOREIGN KEY (testtypeid) REFERENCES testtype(id);
 ALTER TABLE "public"."testsession" ADD CONSTRAINT "testsession_status_fkey" FOREIGN KEY (status) REFERENCES category(id);
 ALTER TABLE "public"."testsession" ADD CONSTRAINT "testsession_stageid_fk" FOREIGN KEY (stageid) REFERENCES stage(id);
 ALTER TABLE "public"."testsession" ADD CONSTRAINT "testsession_rosterid_fkey" FOREIGN KEY (rosterid) REFERENCES roster(id);
 ALTER TABLE "public"."testsession" ADD CONSTRAINT "testsession_operationaltestwindowid_fk" FOREIGN KEY (operationaltestwindowid) REFERENCES operationaltestwindow(id);
 ALTER TABLE "public"."testsession" ADD CONSTRAINT "testsession_gradecourseid_fk" FOREIGN KEY (gradecourseid) REFERENCES gradecourse(id);
 ALTER TABLE "public"."testsession" ADD CONSTRAINT "testsession_attendanceschoolid_fk" FOREIGN KEY (attendanceschoolid) REFERENCES organization(id);
 ALTER TABLE "public"."testsession" ADD CONSTRAINT "test_session_test_coll_fk" FOREIGN KEY (testcollectionid) REFERENCES testcollection(id);
 ALTER TABLE "public"."testsession" ADD CONSTRAINT "fk_ts_updated_user" FOREIGN KEY (modifieduser) REFERENCES aartuser(id);
 ALTER TABLE "public"."testsession" ADD CONSTRAINT "fk_ts_created_user" FOREIGN KEY (createduser) REFERENCES aartuser(id);
 ALTER TABLE "public"."studentstestsectionstasksfoils" ADD CONSTRAINT "studentstestsectionstasksfoils_taskid_fkey" FOREIGN KEY (taskid) REFERENCES taskvariant(id);
 ALTER TABLE "public"."studentstestsectionstasksfoils" ADD CONSTRAINT "studentstestsectionstasksfoils_studentstestsectionstasks_fkey" FOREIGN KEY (studentstestsectionsid) REFERENCES studentstestsections(id);
 ALTER TABLE "public"."studentstestsectionstasksfoils" ADD CONSTRAINT "studentstestsectionstasksfoils_foilid_fkey" FOREIGN KEY (foilid) REFERENCES foil(id);
 ALTER TABLE "public"."studentstestsectionstasks" ADD CONSTRAINT "studentstestsectionstasks_taskid_fkey" FOREIGN KEY (taskid) REFERENCES taskvariant(id);
 ALTER TABLE "public"."studentstestsectionstasks" ADD CONSTRAINT "studentstestsectionstasks_studentstestsectionsid_fkey" FOREIGN KEY (studentstestsectionsid) REFERENCES studentstestsections(id);
 ALTER TABLE "public"."studentstestsections" ADD CONSTRAINT "fk_studentstestsections_updusr" FOREIGN KEY (modifieduser) REFERENCES aartuser(id);
 ALTER TABLE "public"."studentstestsections" ADD CONSTRAINT "fk_studentstestsections_testsectionid" FOREIGN KEY (testsectionid) REFERENCES testsection(id);
 ALTER TABLE "public"."studentstestsections" ADD CONSTRAINT "fk_studentstestsections_testpartid" FOREIGN KEY (testpartid) REFERENCES testpart(id);
 ALTER TABLE "public"."studentstestsections" ADD CONSTRAINT "fk_studentstestsections_studentstestid" FOREIGN KEY (studentstestid) REFERENCES studentstests(id);
 ALTER TABLE "public"."studentstestsections" ADD CONSTRAINT "fk_studentstestsections_status" FOREIGN KEY (statusid) REFERENCES category(id);
 ALTER TABLE "public"."studentstestsections" ADD CONSTRAINT "fk_studentstestsections_crdusr" FOREIGN KEY (createduser) REFERENCES aartuser(id);
 ALTER TABLE "public"."studentstestsections" ADD CONSTRAINT "fk_previousstatus" FOREIGN KEY (previousstatusid) REFERENCES category(id);
 ALTER TABLE "public"."studentstests" ADD CONSTRAINT "studentstests_testsessionid_fkey" FOREIGN KEY (testsessionid) REFERENCES testsession(id);
 ALTER TABLE "public"."studentstests" ADD CONSTRAINT "studentstests_testid_fkey" FOREIGN KEY (testid) REFERENCES test(id);
 ALTER TABLE "public"."studentstests" ADD CONSTRAINT "studentstests_testcollectionid_fkey" FOREIGN KEY (testcollectionid) REFERENCES testcollection(id);
 ALTER TABLE "public"."studentstests" ADD CONSTRAINT "studentstests_studentid_fkey" FOREIGN KEY (studentid) REFERENCES student(id);
 ALTER TABLE "public"."studentstests" ADD CONSTRAINT "studentstests_status_fk" FOREIGN KEY (status) REFERENCES category(id);
 ALTER TABLE "public"."studentstests" ADD CONSTRAINT "fk_students_tests_status" FOREIGN KEY (status) REFERENCES category(id);
 ALTER TABLE "public"."studentstests" ADD CONSTRAINT "fk_st_updated_user" FOREIGN KEY (modifieduser) REFERENCES aartuser(id);
 ALTER TABLE "public"."studentstests" ADD CONSTRAINT "fk_st_created_user" FOREIGN KEY (createduser) REFERENCES aartuser(id);
 ALTER TABLE "public"."studentstests" ADD CONSTRAINT "fk_enrollmentid" FOREIGN KEY (enrollmentid) REFERENCES enrollment(id);
 alter TABLE "public"."studentadaptivetest" add CONSTRAINT "fk_studentadaptivetest_assignedstudentstestsid" FOREIGN KEY (nextstudentstestsid) REFERENCES studentstests(id);
 alter TABLE "public"."studentadaptivetest" add CONSTRAINT "fk_studentadaptivetest_studentstestsid" FOREIGN KEY (studentstestsid) REFERENCES studentstests(id);
 alter TABLE "public"."scoringassignmentstudent" add CONSTRAINT "studentstestsid_fk" FOREIGN KEY (studentstestsid) REFERENCES studentstests(id);
 alter TABLE "public"."studenttrackerband" add CONSTRAINT "fk_studenttrackerband_testsession" FOREIGN KEY (testsessionid) REFERENCES testsession(id);
 alter TABLE "scoringassignment" add CONSTRAINT "testsessionid_fk" FOREIGN KEY (testsessionid) REFERENCES testsession(id); 
 ALTER TABLE "public"."studentsresponseparameters" ADD CONSTRAINT "fk_studentsresponseparameters_testid" FOREIGN KEY (testid) REFERENCES test(id);
 ALTER TABLE "public"."studentsresponseparameters" ADD CONSTRAINT "fk_studentsresponseparameters_taskvariantid" FOREIGN KEY (taskvariantid) REFERENCES taskvariant(id);
 ALTER TABLE "public"."studentsresponseparameters" ADD CONSTRAINT "fk_studentsresponseparameters_studentstestsid" FOREIGN KEY (studentstestsid) REFERENCES studentstests(id);
 ALTER TABLE "public"."studentsresponseparameters" ADD CONSTRAINT "fk_studentsresponseparameters_studentstestsectionsid" FOREIGN KEY (studentstestsectionsid) REFERENCES studentstestsections(id);
 ALTER TABLE "public"."studentspecialcircumstance" ADD CONSTRAINT "studentspecialcircumstance_status_fkey" FOREIGN KEY (status) REFERENCES category(id);
 ALTER TABLE "public"."studentspecialcircumstance" ADD CONSTRAINT "studentspecialcircumstance_approvedby_fkey" FOREIGN KEY (approvedby) REFERENCES aartuser(id);
 ALTER TABLE "public"."studentspecialcircumstance" ADD CONSTRAINT "fk_studentspecialcircumstance_studenttestid" FOREIGN KEY (studenttestid) REFERENCES studentstests(id);
 ALTER TABLE "public"."studentspecialcircumstance" ADD CONSTRAINT "fk_studentspecialcircumstance_specialcircumstanceid" FOREIGN KEY (specialcircumstanceid) REFERENCES specialcircumstance(id);
 ALTER TABLE "public"."studentsadaptivetestsections" ADD CONSTRAINT "fk_studentsadaptivetestsections_testsectionid" FOREIGN KEY (testsectionid) REFERENCES testsection(id);
 ALTER TABLE "public"."studentsadaptivetestsections" ADD CONSTRAINT "fk_studentsadaptivetestsections_testsectioncontainerthetanodeid" FOREIGN KEY (testsectioncontainerthetanodeid) REFERENCES testsectioncontainerthetanode(id);
 ALTER TABLE "public"."studentsadaptivetestsections" ADD CONSTRAINT "fk_studentsadaptivetestsections_testsectioncontainerid" FOREIGN KEY (testsectioncontainerid) REFERENCES testsectioncontainer(id);
 ALTER TABLE "public"."studentsadaptivetestsections" ADD CONSTRAINT "fk_studentsadaptivetestsections_taskvariantid" FOREIGN KEY (taskvariantid) REFERENCES taskvariant(id);
 ALTER TABLE "public"."studentsadaptivetestsections" ADD CONSTRAINT "fk_studentsadaptivetestsections_studentstestid" FOREIGN KEY (studentstestid) REFERENCES studentstests(id);
 ALTER TABLE "public"."studentsadaptivetestsections" ADD CONSTRAINT "fk_studentsadaptivetestsections_partid" FOREIGN KEY (testpartid) REFERENCES testpart(id);
 ALTER TABLE "public"."studentadaptivetestthetastatus" ADD CONSTRAINT "fk_studentadaptivetestthetastatus_testsectioncontainerid" FOREIGN KEY (testsectioncontainerid) REFERENCES testsectioncontainer(id);
 ALTER TABLE "public"."studentadaptivetestthetastatus" ADD CONSTRAINT "fk_studentadaptivetestthetastatus_testpartid" FOREIGN KEY (testpartid) REFERENCES testpart(id);
 ALTER TABLE "public"."studentadaptivetestthetastatus" ADD CONSTRAINT "fk_studentadaptivetestthetastatus_studentstestid" FOREIGN KEY (studentstestid) REFERENCES studentstests(id);
 ALTER TABLE "public"."studentadaptivetestfinaltheta" ADD CONSTRAINT "fk_studentadaptivetestfinaltheta_testsectioncontainerid" FOREIGN KEY (testconstructid) REFERENCES testconstruct(id);
 ALTER TABLE "public"."studentadaptivetestfinaltheta" ADD CONSTRAINT "fk_studentadaptivetestfinaltheta_studentstestid" FOREIGN KEY (studentstestid) REFERENCES studentstests(id);
 ALTER TABLE "public"."studentstestshistory" ADD CONSTRAINT "fk_studentstestshistory_studentstestsid" FOREIGN KEY (studentstestsid) REFERENCES studentstests(id) MATCH FULL;
 ALTER TABLE "public"."studentstestshistory" ADD CONSTRAINT "fk_studentstestshistory_acteduser" FOREIGN KEY (acteduser) REFERENCES aartuser(id) MATCH FULL;
 ALTER TABLE "public"."roster" ADD CONSTRAINT "teacher_fk" FOREIGN KEY (teacherid) REFERENCES aartuser(id);
 ALTER TABLE "public"."roster" ADD CONSTRAINT "state_subject_area_fk" FOREIGN KEY (statesubjectareaid) REFERENCES contentarea(id);
 ALTER TABLE "public"."roster" ADD CONSTRAINT "source_student_fk" FOREIGN KEY (source) REFERENCES category(id);
 ALTER TABLE "public"."roster" ADD CONSTRAINT "roster_attendance_school_fk" FOREIGN KEY (attendanceschoolid) REFERENCES organization(id);
 ALTER TABLE "public"."roster" ADD CONSTRAINT "restriction_fk" FOREIGN KEY (restrictionid) REFERENCES restriction(id);
 ALTER TABLE "public"."roster" ADD CONSTRAINT "fk_roster_updusr" FOREIGN KEY (modifieduser) REFERENCES aartuser(id);
 ALTER TABLE "public"."roster" ADD CONSTRAINT "fk_roster_crdusr" FOREIGN KEY (createduser) REFERENCES aartuser(id);
 ALTER TABLE "public"."roster" ADD CONSTRAINT "course_gradecourse_fk" FOREIGN KEY (statecoursesid) REFERENCES gradecourse(id);
 ALTER TABLE "public"."roster" ADD CONSTRAINT "course_enrollment_status_fk" FOREIGN KEY (courseenrollmentstatusid) REFERENCES category(id);
 ALTER TABLE "public"."exitwithoutsavetest" ADD CONSTRAINT "fk_studentstestsectionid_testsectioid" FOREIGN KEY (studenttestsectionid) REFERENCES studentstestsections(id);
 ALTER TABLE "public"."enrollmenttesttypesubjectarea" ADD CONSTRAINT "fk_enrollmenttesttypesubjectarea_testtypeid_fk" FOREIGN KEY (testtypeid) REFERENCES testtype(id) MATCH FULL;
 ALTER TABLE "public"."enrollmenttesttypesubjectarea" ADD CONSTRAINT "fk_enrollmenttesttypesubjectarea_subjectareaid_fk" FOREIGN KEY (subjectareaid) REFERENCES subjectarea(id) MATCH FULL;
 ALTER TABLE "public"."enrollmenttesttypesubjectarea" ADD CONSTRAINT "fk_enrollmenttesttypesubjectarea_enrollmentid_fk" FOREIGN KEY (enrollmentid) REFERENCES enrollment(id) MATCH FULL;
 ALTER TABLE "public"."enrollmentsrosters" ADD CONSTRAINT "source_student_fk" FOREIGN KEY (source) REFERENCES category(id);
 ALTER TABLE "public"."enrollmentsrosters" ADD CONSTRAINT "roster_fk" FOREIGN KEY (rosterid) REFERENCES roster(id);
 ALTER TABLE "public"."enrollmentsrosters" ADD CONSTRAINT "fk_enrollmentsrosters_updusr" FOREIGN KEY (modifieduser) REFERENCES aartuser(id);
 ALTER TABLE "public"."enrollmentsrosters" ADD CONSTRAINT "fk_enrollmentsrosters_crdusr" FOREIGN KEY (createduser) REFERENCES aartuser(id);
 ALTER TABLE "public"."enrollmentsrosters" ADD CONSTRAINT "enrollmentsrosters_course_status_fk" FOREIGN KEY (courseenrollmentstatusid) REFERENCES category(id);
 ALTER TABLE "public"."enrollmentsrosters" ADD CONSTRAINT "enrollment_fk" FOREIGN KEY (enrollmentid) REFERENCES enrollment(id);
 ALTER TABLE "public"."enrollment" ADD CONSTRAINT "student_fk" FOREIGN KEY (studentid) REFERENCES student(id);
 ALTER TABLE "public"."enrollment" ADD CONSTRAINT "source_student_fk" FOREIGN KEY (source) REFERENCES category(id);
 ALTER TABLE "public"."enrollment" ADD CONSTRAINT "restriction_fk" FOREIGN KEY (restrictionid) REFERENCES restriction(id);
 ALTER TABLE "public"."enrollment" ADD CONSTRAINT "fk_enrollment_updusr" FOREIGN KEY (modifieduser) REFERENCES aartuser(id);
 ALTER TABLE "public"."enrollment" ADD CONSTRAINT "fk_enrollment_crdusr" FOREIGN KEY (createduser) REFERENCES aartuser(id);
 ALTER TABLE "public"."enrollment" ADD CONSTRAINT "current_grade_level_fk" FOREIGN KEY (currentgradelevel) REFERENCES gradecourse(id);
 ALTER TABLE "public"."enrollment" ADD CONSTRAINT "aypschoolid_fk" FOREIGN KEY (aypschoolid) REFERENCES organization(id);
 ALTER TABLE "public"."enrollment" ADD CONSTRAINT "attendance_school_fk" FOREIGN KEY (attendanceschoolid) REFERENCES organization(id); 
 ALTER TABLE "public"."ititestsessionsensitivitytags" ADD CONSTRAINT "fk_itisensitivity_historyid" FOREIGN KEY (ititestsessionhistoryid) REFERENCES ititestsessionhistory(id); 