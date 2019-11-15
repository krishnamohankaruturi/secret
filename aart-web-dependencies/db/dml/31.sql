
--R8 - Iter 1 - - file 3

--US12596 Name: First Contact Survey - Motor Capabilities- Walking 
 
--Updates to prerequisitecondition

UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q37') and 
     surveyresponseid = (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q36' AND sr.responsevalue='Yes') ;
     
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q40') and
    surveyresponseid = (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q39' AND sr.responsevalue='Yes');
    
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid =(Select id from surveylabel where labelnumber='Q41') and
    surveyresponseid = (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q39' AND sr.responsevalue='Yes');
    
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid =(Select id from surveylabel where labelnumber='Q44')  and
    surveyresponseid = (Select sr.id from  surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='Yes');
    
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid =(Select id from surveylabel where labelnumber='Q45_1')  and
    surveyresponseid = (Select sr.id from  surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='Yes');
    
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid =(Select id from surveylabel where labelnumber='Q45_2')  and
    surveyresponseid = (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='Yes');
    
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid =(Select id from surveylabel where labelnumber='Q45_3')  and
    surveyresponseid = (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='Yes');
    
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid =(Select id from surveylabel where labelnumber='Q45_4')  and
    surveyresponseid = (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='Yes');

UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid =(Select id from surveylabel where labelnumber='Q45_5')  and
    surveyresponseid = (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='Yes');
    
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid =(Select id from surveylabel where labelnumber='Q45_6') and
    surveyresponseid = (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='Yes');

UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid =(Select id from surveylabel where labelnumber='Q45_7')  and
	surveyresponseid =     (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='Yes');

UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid =(Select id from surveylabel where labelnumber='Q45_8')  and
    surveyresponseid = (Select sr.id from  surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='Yes');
    
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid =(Select id from surveylabel where labelnumber='Q45_9') and
    surveyresponseid = (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='Yes');
    
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid =(Select id from surveylabel where labelnumber='Q45_10')  and
    surveyresponseid = (Select sr.id from  surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='Yes');
    
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid =(Select id from surveylabel where labelnumber='Q45_11')  and
    surveyresponseid = (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='Yes');
    
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid =(Select id from surveylabel where labelnumber='Q45_12')  and
    surveyresponseid = (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='Yes');
    
UPDATE surveylabelprerequisite set prerequisitecondition = 'and' where surveylabelid =(Select id from surveylabel where labelnumber='Q47')  and
    surveyresponseid = (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q36' AND sr.responsevalue='No');

UPDATE surveylabelprerequisite set prerequisitecondition = 'and' where surveylabelid =(Select id from surveylabel where labelnumber='Q47')  and
    surveyresponseid = (Select sr.id from  surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q39'AND sr.responsevalue='No');
    
UPDATE surveylabelprerequisite set prerequisitecondition = 'and' where surveylabelid =(Select id from surveylabel where labelnumber='Q47')  and
    surveyresponseid = (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='No');

UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q27_1') and
	surveyresponseid = (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q135' AND sr.responseorder=2);
	
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q27_1') and
	surveyresponseid =  (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q135' AND sr.responseorder=3);
	
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q27_2') and
	surveyresponseid =  (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q135' AND sr.responseorder=2);
	
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q27_2') and
	surveyresponseid = (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q135' AND sr.responseorder=3);
	
UPDATE surveylabelprerequisite set prerequisitecondition = 'or'  where surveylabelid = (Select id from surveylabel where labelnumber='Q27_3') and
	surveyresponseid = (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q135' AND sr.responseorder=2);
	
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q27_3') and 
	surveyresponseid = (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q135' AND sr.responseorder=3);
	
UPDATE surveylabelprerequisite set prerequisitecondition = 'or'  where surveylabelid = (Select id from surveylabel where labelnumber='Q27_4') and
	surveyresponseid = (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q135' AND sr.responseorder=2);
	
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q27_4') and
	surveyresponseid = (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q135' AND sr.responseorder=3);

UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q147') and 
	surveyresponseid =  (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder=3);

UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q34_1') and
	surveyresponseid = (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q33_11' AND sr.responseorder=11);	

UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q34_2') and 
	surveyresponseid = (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q33_11' AND sr.responseorder=11);
	
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q34_3') and
	surveyresponseid =  (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q33_11' AND sr.responseorder=11);
		
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q132_1') and
	surveyresponseid =   (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responseorder=2);
	
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q132_2') and
	surveyresponseid =   (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responseorder=2);

UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q132_3') and
	surveyresponseid =   (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responseorder=2);
	
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q132_4') and
	surveyresponseid =   (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responseorder=2);
	
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q20_1') and
	surveyresponseid =   (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responseorder=2);
	
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q20_2') and
	surveyresponseid =   (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responseorder=2);
	
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q20_3') and
	surveyresponseid =   (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responseorder=2);
	
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q20_4')and
	surveyresponseid =   (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responseorder=2);
	
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q23_1') and
	surveyresponseid =   (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q22' AND sr.responseorder=3);
	
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q23_2') and
	surveyresponseid =   (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q22' AND sr.responseorder=3);
	
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q23_3') and
	surveyresponseid =   (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q22' AND sr.responseorder=3);
	
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q133') and
	surveyresponseid =   (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q22' AND sr.responseorder=3);	

UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q151_1') and
	surveyresponseid =   (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q23_3' AND sr.responseorder=3);
	
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q151_2') and
	surveyresponseid =   (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q23_3' AND sr.responseorder=3);
	
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q151_3') and
	surveyresponseid =   (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q23_3' AND sr.responseorder=3);
	
UPDATE surveylabelprerequisite set prerequisitecondition = 'or' where surveylabelid = (Select id from surveylabel where labelnumber='Q24') and
	surveyresponseid =   (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q23_3' AND sr.responseorder=3);

-- Don't remove this, even though its DDL statement, it has to be here only	
ALTER TABLE surveylabelprerequisite ALTER COLUMN prerequisitecondition SET NOT NULL;



UPDATE surveylabel SET surveysectionid = (Select id from surveysection where surveysectioncode='ACCESS_AND_SWITCHES') 
	WHERE labelnumber IN ('Q33_1','Q33_2','Q33_3','Q33_4','Q33_5','Q33_6','Q33_7','Q33_8','Q33_9','Q33_10','Q33_11','Q34_1','Q34_2','Q34_3');
	
	
UPDATE surveylabel SET optional = false 
	WHERE labelnumber IN ('Q19', 'Q22', 'Q133', 'Q151','Q24','Q25_1','Q25_2','Q25_3','Q25_4','Q25_5','Q25_6','Q25_7','Q25_8','Q25_9','Q25_10');


