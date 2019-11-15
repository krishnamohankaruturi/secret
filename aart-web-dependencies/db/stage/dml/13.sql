--dml/13.sql empty

 /*
  * INSERT INTO ksdexmlaudit(type, xml, createdate, processedcode, fromdate, todate) VALUES 
 ('TASC', (select  tasc_xml_creation(ksdexmlauditid := 415963, subjectarea := '80')), now(), 'NOTPROCESSED', 
 (select fromdate from ksdexmlaudit where type='TASC' order by id desc limit 1), 
 (select todate from ksdexmlaudit where type='TASC' order by id desc limit 1));
 */
