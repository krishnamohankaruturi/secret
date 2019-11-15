
-- First Contact Survey upload defects - DE5067, DE5069, DE5070, DE5071, DE5072, DE5073

UPDATE surveyresponse SET responselabel = 1 WHERE labelid  in (SELECT id FROM surveylabel where labelnumber in ('Q151_1','Q151_2','Q151_3'));

UPDATE surveyresponse SET responselabel = 1 WHERE labelid  in (SELECT id FROM surveylabel where labelnumber in  ('Q25_1','Q25_2','Q25_3','Q25_4','Q25_5','Q25_6','Q25_7','Q25_8','Q25_9','Q25_10'));

UPDATE surveyresponse SET responselabel = 1 WHERE labelid  in (SELECT id FROM surveylabel where labelnumber in  ('Q33_1','Q33_2','Q33_3','Q33_4','Q33_5','Q33_6','Q33_7','Q33_8','Q33_9','Q33_10','Q33_11'));

UPDATE surveyresponse SET responselabel = 1 WHERE labelid  in (SELECT id FROM surveylabel where labelnumber in  ('Q132_1','Q132_2','Q132_3','Q132_4'));

UPDATE surveyresponse SET responselabel = 1 WHERE labelid  in (SELECT id FROM surveylabel where labelnumber in  ('Q20_1','Q20_2','Q20_3','Q20_4'));

UPDATE surveyresponse SET responselabel = 1 WHERE labelid  in (SELECT id FROM surveylabel where labelnumber in  ('Q23_1','Q23_2','Q23_3'));

