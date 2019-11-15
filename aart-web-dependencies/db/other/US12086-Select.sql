SELECT DISTINCT enrlRoster.id                          ,
                enrl.id AS enrollmentId                ,
                enrl.aypschoolidentifier               ,
                enrl.residencedistrictidentifier       ,
                enrl.localstudentidentifier            ,
                enrl. currentgradelevel                ,
                enrl.currentschoolyear                 ,
                enrl.fundingschool                     ,
                enrl.schoolentrydate                   ,
                enrl.districtentrydate                 ,
                enrl.stateentrydate                    ,
                enrl.exitwithdrawaldate                ,
                enrl.exitwithdrawaltype                ,
                enrl.specialcircumstancestransferchoice,
                enrl.giftedstudent                     ,
                enrl.specialedprogramendingdate        ,
                enrl.qualifiedfor504                   ,
                enrl.studentid                         ,
                enrl.restrictionid                     ,
                gc.id              AS gradeCourseId                 ,
                gc.name            AS gradeCourseName               ,
                gc.abbreviatedname AS gradeCourseAbbreviatedName    ,
                gc.gradeLevel                                       ,
                gc.originationCode AS gradeCourseOriginationCode    ,
                r.id               AS rosterId                      ,
                r.coursesectionname                                 ,
                r.teacherid                                         ,
                r.statesubjectareaid                                ,
                courseenrollment.id           AS courseenrollmentstatusid     ,
                courseenrollment.categoryname AS courseenrollmentstatus       ,
                r.statecourseid                                               ,
                r.statesubjectcourseidentifier                                ,
                r.localcourseid                                               ,
                r.educatorschooldisplayidentifier                             ,
                au.username                        AS educatorUserName                               ,
                au.firstname                       AS educatorFirstName                              ,
                au.middlename                      AS educatorMiddleName                             ,
                au.surname                         AS educatorLastName                               ,
                au.email                           AS educatorEmail                                  ,
                au.uniquecommonidentifier          AS educatorIdentifier                             ,
                au.defaultusergroupsid             AS educatorDefaultUserGroupsId                    ,
                statecourse.name                   AS stateCourseName                                ,
                statecourse.externalId             AS stateCourseExternalId                          ,
                stateCourse.sortOrder              AS stateCourseSortOrder                           ,
                statecourse.originationCode        AS stateCourseOriginationCode                     ,
                stateCourse.abbreviatedName        AS stateCourseAbbreviatedName                     ,
                stateSubjectArea.name              AS stateSubjectAreaName                           ,
                stateSubjectArea.externalId        AS stateSubjectAreaExternalId                     ,
                stateSubjectArea.sortOrder         AS stateSubjectAreaSortOrder                      ,
                stateSubjectArea.originationCode   AS stateSubjectAreaOriginationCode                ,
                stateSubjectArea.abbreviatedName   AS stateSubjectAreaAbbreviatedName                ,
                ts.id                              AS testSessionid                                  ,
                ts.name                            AS testSessionName                                ,
                ts.status                          AS testSessionStatus                              ,
                ts.testid                          AS testSessionTestId                              ,
                ts.rosterId                        AS testSessionRosterId                            ,
                attendanceSchool.id                AS attendanceSchoolId                             ,
                attendanceSchool.displayidentifier AS attendanceSchoolIdentifier                     ,
                attendanceSchool.organizationname  AS attendanceSchoolName                           ,
                attendanceSchool.welcomemessage                                                      ,
                ot.id        AS "attendanceSchool.organizationType.organizationTypeId"                      ,
                ot.typename  AS "attendanceSchool.organizationType.typeName"                                ,
                ot.typecode  AS "attendanceSchool.organizationType.typeCode"                                ,
                ot.typelevel AS "attendanceSchool.organizationType.typeLevel"
FROM            testsession ts               ,
                Organization attendanceSchool,
                OrganizationType ot          ,
                enrollment enrl
                LEFT JOIN GradeCourse gc
                ON              (
                                                enrl.currentgradelevel = gc.id
                                )
                                ,
                                enrollmentsRosters enrlRoster,
                                aartuser au                  ,
                                roster r
                LEFT JOIN ContentArea stateCourse
                ON              (
                                                r.statecourseid = stateCourse.id
                                )
                LEFT JOIN ContentArea stateSubjectArea
                ON              (
                                                r.stateSubjectAreaId = stateSubjectArea.id
                                )
                LEFT JOIN Category courseenrollment
                ON              (
                                                r.courseenrollmentstatusid = courseenrollment.id
                                )
WHERE           attendanceSchool.id                 = enrl.attendanceschoolid
AND             attendanceSchool.organizationtypeid = ot.id
AND             enrlroster.enrollmentid             = enrl.id
AND             ts.rosterid                         = r.id
AND             r.id                                = enrlroster.rosterid
AND             r.teacherId                         = au.id
AND             r.activeflag IS true
AND             enrlRoster.activeflag IS true
AND             enrl.activeflag IS true