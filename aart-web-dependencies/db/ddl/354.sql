--US15752: Technical: Add indexes to all FK's

DROP INDEX IF EXISTS idx_batchregisteredtestsessions_batchregistrationid;
CREATE INDEX idx_batchregisteredtestsessions_batchregistrationid
  ON batchregisteredtestsessions USING btree (batchregistrationid);

DROP INDEX IF EXISTS idx_studentsassessments_studentid;
CREATE INDEX idx_studentsassessments_studentid
  ON studentsassessments USING btree (studentid);
  
DROP INDEX IF EXISTS idx_studentsassessments_assessmentid;  
CREATE INDEX idx_studentsassessments_assessmentid
  ON studentsassessments USING btree (assessmentid);

DROP INDEX IF EXISTS idx_studentsassessments_contentareaid;  
CREATE INDEX idx_studentsassessments_contentareaid
  ON studentsassessments USING btree (contentareaid);
  
DROP INDEX IF EXISTS idx_surveypagestatus_surveyid;  
CREATE INDEX idx_surveypagestatus_surveyid
  ON surveypagestatus USING btree (surveyid);  

DROP INDEX IF EXISTS idx_surveypagestatus_surveyid_globalpagenum;  
CREATE INDEX idx_surveypagestatus_surveyid_globalpagenum
  ON surveypagestatus USING btree (surveyid,globalpagenum);  
  
DROP INDEX IF EXISTS idx_ititestsessionhistory_rosterid;  
CREATE INDEX idx_ititestsessionhistory_rosterid
  ON ititestsessionhistory USING btree (rosterid);
  
DROP INDEX IF EXISTS idx_ititestsessionhistory_status;  
CREATE INDEX idx_ititestsessionhistory_status
  ON ititestsessionhistory USING btree (status);
    
DROP INDEX IF EXISTS idx_ititestsessionhistory_studentid;  
CREATE INDEX idx_ititestsessionhistory_studentid
  ON ititestsessionhistory USING btree (studentid);
  
DROP INDEX IF EXISTS idx_ititestsessionhistory_testcollectionid;  
CREATE INDEX idx_ititestsessionhistory_testcollectionid
  ON ititestsessionhistory USING btree (testcollectionid);
  
DROP INDEX IF EXISTS idx_ititestsessionhistory_studentid_rosterid_status_testcollectionid;  
CREATE INDEX idx_ititestsessionhistory_studentid_rosterid_status_testcollectionid
  ON ititestsessionhistory USING btree (studentid,rosterid,status,testcollectionid);

DROP INDEX IF EXISTS idx_ititestsessionhistory_studentid_rosterid;  
CREATE INDEX idx_ititestsessionhistory_studentid_rosterid
  ON ititestsessionhistory USING btree (studentid,rosterid);

DROP INDEX IF EXISTS idx_ititestsessionhistory_testsessionid;  
CREATE INDEX idx_ititestsessionhistory_testsessionid
  ON ititestsessionhistory USING btree (testsessionid);
  
DROP INDEX IF EXISTS idx_ititestsessionhistory_modifieddate;  
CREATE INDEX idx_ititestsessionhistory_modifieddate
  ON ititestsessionhistory USING btree (modifieddate);
  
DROP INDEX IF EXISTS idx_ititestsessionhistory_testid;  
CREATE INDEX idx_ititestsessionhistory_testid
  ON ititestsessionhistory USING btree (testid);  
  
DROP INDEX IF EXISTS idx_ksdexmlaudit_type;  
CREATE INDEX idx_ksdexmlaudit_type
  ON ksdexmlaudit USING btree (type);

DROP INDEX IF EXISTS idx_batchregistrationreason_brid;  
DROP INDEX IF EXISTS idx_batchregistrationreason_batchregistrationid;
CREATE INDEX idx_batchregistrationreason_batchregistrationid
  ON batchregistrationreason USING btree (batchregistrationid);
  
DROP INDEX IF EXISTS idx_taskvariantlearningmapnode_foilid;  
CREATE INDEX idx_taskvariantlearningmapnode_foilid
  ON taskvariantlearningmapnode USING btree (foilid);
  
DROP INDEX IF EXISTS idx_taskvariantlearningmapnode_nodetypecodeid;  
CREATE INDEX idx_taskvariantlearningmapnode_nodetypecodeid
  ON taskvariantlearningmapnode USING btree (nodetypecodeid);
    
DROP INDEX IF EXISTS idx_taskvariantlearningmapnode_nodeweightid;  
CREATE INDEX idx_taskvariantlearningmapnode_nodeweightid
  ON taskvariantlearningmapnode USING btree (nodeweightid);
  
DROP INDEX IF EXISTS idx_taskvariantlearningmapnode_taskvariantid;  
CREATE INDEX idx_taskvariantlearningmapnode_taskvariantid
  ON taskvariantlearningmapnode USING btree (taskvariantid);

DROP INDEX IF EXISTS idx_usersecurityagreement_aartuserid;  
CREATE INDEX idx_usersecurityagreement_aartuserid
  ON usersecurityagreement USING btree (aartuserid);  
  
DROP INDEX IF EXISTS idx_usersecurityagreement_assessmentprogramid;  
CREATE INDEX idx_usersecurityagreement_assessmentprogramid
  ON usersecurityagreement USING btree (assessmentprogramid);  
  
DROP INDEX IF EXISTS idx_usersecurityagreement_aartuserid_assessmentprogramid;  
CREATE INDEX idx_usersecurityagreement_aartuserid_assessmentprogramid
  ON usersecurityagreement USING btree (aartuserid,assessmentprogramid);  

DROP INDEX IF EXISTS idx_userpasswordreset_aart_user_id;  
CREATE INDEX idx_userpasswordreset_aart_user_id
  ON userpasswordreset USING btree (aart_user_id);
  
DROP INDEX IF EXISTS idx_userpasswordreset_aart_user_id_auth_token;  
CREATE INDEX idx_userpasswordreset_aart_user_id_auth_token
  ON userpasswordreset USING btree (aart_user_id,auth_token);
  
DROP INDEX IF EXISTS idx_ititestsessionsensitivitytags_ititestsessionhistoryid;  
CREATE INDEX idx_ititestsessionsensitivitytags_ititestsessionhistoryid
  ON ititestsessionsensitivitytags USING btree (ititestsessionhistoryid);
  
DROP INDEX IF EXISTS idx_ititestsessionsensitivitytags_sensitivitytag;  
CREATE INDEX idx_ititestsessionsensitivitytags_sensitivitytag
  ON ititestsessionsensitivitytags USING btree (sensitivitytag);
  
DROP INDEX IF EXISTS idx_micromap_contentframeworkdetailid;  
CREATE INDEX idx_micromap_contentframeworkdetailid
  ON micromap USING btree (contentframeworkdetailid);
  
DROP INDEX IF EXISTS idx_rubricinfo_rubriccategoryid;  
CREATE INDEX idx_rubricinfo_rubriccategoryid
  ON rubricinfo USING btree (rubriccategoryid);  
  
DROP INDEX IF EXISTS idx_modulereport_groupid;  
CREATE INDEX idx_modulereport_groupid
  ON modulereport USING btree (groupid);  
  
DROP INDEX IF EXISTS idx_modulereport_stateid;  
CREATE INDEX idx_modulereport_stateid
  ON modulereport USING btree (stateid);  
  
DROP INDEX IF EXISTS idx_modulereport_statusid;  
CREATE INDEX idx_modulereport_statusid
  ON modulereport USING btree (statusid); 
  
DROP INDEX IF EXISTS idx_modulereport_createduser_reporttypeid;  
CREATE INDEX idx_modulereport_createduser_reporttypeid
  ON modulereport USING btree (createduser,reporttypeid); 
  
DROP INDEX IF EXISTS idx_testselectionstatistic_testsectioncontainerthetanodeid;  
CREATE INDEX idx_testselectionstatistic_testsectioncontainerthetanodeid
  ON testselectionstatistic USING btree (testsectioncontainerthetanodeid);  
  
DROP INDEX IF EXISTS idx_testselectionstatistic_testsectionid;  
CREATE INDEX idx_testselectionstatistic_testsectionid
  ON testselectionstatistic USING btree (testsectionid); 
  
DROP INDEX IF EXISTS idx_complexitybandrules_complexitybandid;  
CREATE INDEX idx_complexitybandrules_complexitybandid
  ON complexitybandrules USING btree (complexitybandid); 
  
DROP INDEX IF EXISTS idx_complexitybandrules_complexitybandtypeid;  
CREATE INDEX idx_complexitybandrules_complexitybandtypeid
  ON complexitybandrules USING btree (complexitybandtypeid);  
  
DROP INDEX IF EXISTS idx_testpriorparameter_testid;  
CREATE INDEX idx_testpriorparameter_testid
  ON testpriorparameter USING btree (testid);
  
DROP INDEX IF EXISTS idx_testpriorparameter_xtestconstructid;  
CREATE INDEX idx_testpriorparameter_xtestconstructid
  ON testpriorparameter USING btree (xtestconstructid);
  
DROP INDEX IF EXISTS idx_testpriorparameter_ytestconstructid;  
CREATE INDEX idx_testpriorparameter_ytestconstructid
  ON testpriorparameter USING btree (ytestconstructid);  

DROP INDEX IF EXISTS idx_studentadaptivetestfinaltheta_studentstestid;  
CREATE INDEX idx_studentadaptivetestfinaltheta_studentstestid
  ON studentadaptivetestfinaltheta USING btree (studentstestid); 
  
DROP INDEX IF EXISTS idx_studentadaptivetestfinaltheta_testconstructid;  
CREATE INDEX idx_studentadaptivetestfinaltheta_testconstructid
  ON studentadaptivetestfinaltheta USING btree (testconstructid); 
  
DROP INDEX IF EXISTS idx_surveylabel_sectionid;  
CREATE INDEX idx_surveylabel_sectionid
  ON surveylabel USING btree (sectionid); 
  
DROP INDEX IF EXISTS idx_surveylabel_labelnumber;  
CREATE INDEX idx_surveylabel_labelnumber
  ON surveylabel USING btree (labelnumber);   

DROP INDEX IF EXISTS idx_surveylabel_globalpagenum;  
CREATE INDEX idx_surveylabel_globalpagenum
  ON surveylabel USING btree (globalpagenum);   

DROP INDEX IF EXISTS idx_fieldspecificationsrecordtypes_fieldspecificationid;  
CREATE INDEX idx_fieldspecificationsrecordtypes_fieldspecificationid
  ON fieldspecificationsrecordtypes USING btree (fieldspecificationid);
  
DROP INDEX IF EXISTS idx_fieldspecificationsrecordtypes_recordtypeid;  
CREATE INDEX idx_fieldspecificationsrecordtypes_recordtypeid
  ON fieldspecificationsrecordtypes USING btree (recordtypeid);
  
DROP INDEX IF EXISTS idx_authorities_authority;  
CREATE INDEX idx_authorities_authority
  ON authorities USING btree (authority);  

DROP INDEX IF EXISTS idx_authorities_objecttype;  
CREATE INDEX idx_authorities_objecttype
  ON authorities USING btree (objecttype);
  
DROP INDEX IF EXISTS idx_lmassessmentmodelrule_contentframeworkdetailid;  
CREATE INDEX idx_lmassessmentmodelrule_contentframeworkdetailid
  ON lmassessmentmodelrule USING btree (contentframeworkdetailid);  

DROP INDEX IF EXISTS idx_lmassessmentmodelrule_testspecificationid_ranking;  
CREATE INDEX idx_lmassessmentmodelrule_testspecificationid_ranking
  ON lmassessmentmodelrule USING btree (testspecificationid,ranking);
    
DROP INDEX IF EXISTS idx_tasksubtype_tasktypeid;  
CREATE INDEX idx_tasksubtype_tasktypeid
  ON tasksubtype USING btree (tasktypeid);
  
DROP INDEX IF EXISTS idx_testfeedbackrules_testid;  
CREATE INDEX idx_testfeedbackrules_testid
  ON testfeedbackrules USING btree (testid);  
  
DROP INDEX IF EXISTS idx_modulegroup_moduleid;  
CREATE INDEX idx_modulegroup_moduleid
  ON modulegroup USING btree (moduleid);
  
DROP INDEX IF EXISTS idx_modulegroup_groupid;  
CREATE INDEX idx_modulegroup_groupid
  ON modulegroup USING btree (groupid);
  
DROP INDEX IF EXISTS idx_profileitemattrnameattrcontainerviewoptions_assessmentprogramid;  
CREATE INDEX idx_profileitemattrnameattrcontainerviewoptions_assessmentprogramid
  ON profileitemattrnameattrcontainerviewoptions USING btree (assessmentprogramid);
  
DROP INDEX IF EXISTS idx_profileitemattrnameattrcontainerviewoptions_pianacid;  
CREATE INDEX idx_profileitemattrnameattrcontainerviewoptions_pianacid
  ON profileitemattrnameattrcontainerviewoptions USING btree (pianacid);
  
DROP INDEX IF EXISTS idx_categorytype_typecode;  
CREATE INDEX idx_categorytype_typecode
  ON categorytype USING btree (typecode);  
  
  
    
    
    
    
       
    
  
         
  
  
  
  
  
    
  
    
  
  
  
  
  