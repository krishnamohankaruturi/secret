INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid,  originationcode, createduser, modifieduser)
VALUES ('Text - Other', 'TEXTOTHER', 'Text - Other', (select id from categorytype where typecode='STIMULUSFORMAT'), 'CB',
                (SELECT id FROM aartuser where username ='cetesysadmin'), (SELECT id FROM aartuser where username ='cetesysadmin'));