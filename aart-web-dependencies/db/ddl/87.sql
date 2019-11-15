

--US13144 Name: Technical: Performance Improvements of SQL queries -- index creation 


create index idx_student_id on student(id);

create index idx_student_stateStudentIdentifier on student(stateStudentIdentifier);

create index idx_student_legalfirstname on student(legalfirstname);

create index idx_student_legallastname on student(legallastname);

create index idx_student_dateofbirth on student(dateofbirth);

create index idx_student_firstlanguage on student(firstlanguage);

create index idx_student_gender on student(gender);

create index idx_student_comprehensiveRace on student(comprehensiveRace);

create index idx_student_primaryDisabilityCode on student(primaryDisabilityCode);

create index idx_student_activeflag on student(activeflag) where activeflag IS true;


create index idx_gradecourse_id on gradecourse(id);

create index idx_gradecourse_name on gradecourse(name);

create index idx_gradecourse_gradeLevel on gradecourse(gradeLevel);

create index idx_gradecourse_abbreviatedname on gradecourse(abbreviatedname);


create index idx_contentarea_id on contentarea(id);

create index idx_contentarea_name on contentarea(name);

create index idx_contentarea_abbreviatedName on contentarea(abbreviatedName);


create index idx_enrollment_id on enrollment(id);

create index idx_enrollment_currentgradelevel on enrollment(currentgradelevel);

create index idx_enrollment_currentschoolyear on enrollment(currentschoolyear);

create index idx_enrollment_localStudentIdentifier on enrollment(localStudentIdentifier);

create index idx_enrollment_attendanceschoolid on enrollment(attendanceschoolid);

create index idx_enrollment_Studentid on enrollment(Studentid);

create index idx_enrollment_residenceDistrictIdentifier on enrollment(residenceDistrictIdentifier);

create index idx_enrollment_activeflag on enrollment(activeflag) where activeflag IS true;


create index idx_roster_id on roster(id);

create index idx_roster_stateSubjectAreaId on roster(stateSubjectAreaId);

create index idx_roster_statecourseid on roster(statecourseid);

create index idx_roster_courseSectionName on roster(courseSectionName);

create index idx_roster_courseenrollmentstatusid on roster(courseenrollmentstatusid);

create index idx_roster_teacherid on roster(teacherid);

create index idx_roster_activeflag on roster(activeflag) where activeflag IS true;


create index idx_Category_id on Category(id);

create index idx_Category_categoryname on Category(categoryname);


create index idx_aartuser_firstname on aartuser(firstname);

create index idx_aartuser_surname on aartuser(surname);

create index idx_aartuser_uniqueCommonIdentifier on aartuser(uniqueCommonIdentifier);


create index idx_Organization_id on Organization(id);

create index idx_Organization_organizationname on Organization(organizationname);

create index idx_Organization_displayidentifier on Organization(displayidentifier);

create index idx_Organization_organizationtypeid on Organization(organizationtypeid);


create index idx_OrganizationType_id on OrganizationType(id);


create index idx_enrollmentsRosters_enrollmentid on enrollmentsRosters(enrollmentid);

create index idx_enrollmentsRosters_rosterid on enrollmentsRosters(rosterid);

create index idx_enrollmentsRosters_activeflag on enrollmentsRosters(activeflag) where activeflag IS true;

