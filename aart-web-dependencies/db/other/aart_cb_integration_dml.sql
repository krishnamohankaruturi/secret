INSERT INTO categorytype(
            typename, typecode, typedescription, originationcode)
    VALUES ('Reentry Rules', 'REENTRY', 'Test Section Reentry Rules', 'CB');

INSERT INTO categorytype(
            typename, typecode, typedescription, originationcode)
    VALUES ('Task Delivery Rules', 'TASKDELIVERY', 'Task Delivery Rules', 'CB');

INSERT INTO categorytype(
            typename, typecode, typedescription, originationcode)
    VALUES ('Tools Usage Rules', 'TOOLSUSAGE', 'Tools Usage Rules', 'CB');

INSERT INTO categorytype(
            typename, typecode, typedescription, originationcode)
    VALUES ('Navigation Rules', 'NAVIGATION', 'Navigation Rules', 'CB');

INSERT INTO categorytype(
            typename, typecode, typedescription, originationcode)
    VALUES ('Item Usage', 'ITEMUSAGE', 'Item Usage', 'CB');

INSERT INTO categorytype(
            typename, typecode, typedescription, originationcode)
    VALUES ('Test Status', 'TESTSTATUS', 'Test Status', 'CB');



INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('LOGIN_ONLY_ONCE', 'LOGIN_ONLY_ONCE',
    'Student may login to each test section only once',
    (select id from categorytype where originationcode = 'CB' and typecode = 'REENTRY'), 
            1, 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('LOGIN_ANY_NUMBER', 'LOGIN_ANY_NUMBER',
    'Student may login to each section any number of times',
    (select id from categorytype where originationcode = 'CB' and typecode = 'REENTRY'), 
            2, 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('ADMIN_REACTIVATE', 'ADMIN_REACTIVATE',
    'Test administrator may reactivate',
    (select id from categorytype where originationcode = 'CB' and typecode = 'REENTRY'), 
            3, 'CB');            


INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('NO_REORDERING', 'NO_REORDERING', 'No re-ordering',
    (select id from categorytype where originationcode = 'CB'
    and typecode = 'TASKDELIVERY'), 
            1, 'CB');    

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('REVERSE_ORDER', 'REVERSE_ORDER', 'Reverse order',
    (select id from categorytype where originationcode = 'CB'
    and typecode = 'TASKDELIVERY'), 
            2, 'CB'); 

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('RANDOM_ORDER', 'RANDOM_ORDER', 'Random order',
    (select id from categorytype where originationcode = 'CB'
    and typecode = 'TASKDELIVERY'), 
            3, 'CB'); 


INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('SECTION_TOOLS', 'SECTION_TOOLS', 'Tools Defined for Section',
    (select id from categorytype where originationcode = 'CB'
    and typecode = 'TOOLSUSAGE'), 1, 'CB'); 

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('TASK_TOOLS', 'TASK_TOOLS', 'Tools Defined for Tasks',
    (select id from categorytype where originationcode = 'CB'
    and typecode = 'TOOLSUSAGE'), 2, 'CB'); 


INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('MUST_ANSWER_ALL', 'MUST_ANSWER_ALL',
    'Students must answer every question',
    (select id from categorytype where originationcode = 'CB' and typecode = 'NAVIGATION'), 1, 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('NO_REVISIT', 'NO_REVISIT',
    'Students may not revisit a question after answering that question',
    (select id from categorytype where originationcode = 'CB' and typecode = 'NAVIGATION'), 2, 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('SAME_ORDER_PRESENTED', 'SAME_ORDER_PRESENTED',
    'Students must answer items in the order presented',
    (select id from categorytype where originationcode = 'CB' and typecode = 'NAVIGATION'), 3, 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('FIELD_TEST', 'FIELD_TEST', 'Field Test',
    (select id from categorytype where originationcode = 'CB' and typecode = 'ITEMUSAGE'), 1, 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('OPERATIONAL', 'OPERATIONAL', 'Operational',
    (select id from categorytype where originationcode = 'CB' and typecode = 'ITEMUSAGE'), 2, 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('PILOT_TEST', 'PILOT_TEST', 'Pilot Test',
    (select id from categorytype where originationcode = 'CB' and typecode = 'ITEMUSAGE'), 3, 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('EXEMPLAR', 'EXEMPLAR', 'Exemplar',
    (select id from categorytype where originationcode = 'CB' and typecode = 'ITEMUSAGE'), 4, 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('PRACTICE', 'PRACTICE', 'Practice',
    (select id from categorytype where originationcode = 'CB' and typecode = 'ITEMUSAGE'), 5, 'CB');


INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('DEVELOPMENT', 'DEVELOPMENT', 'Development',
    (select id from categorytype where originationcode = 'CB' and typecode = 'ITEMUSAGE'), 6, 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, originationcode)
    VALUES ('DEPLOYED', 'DEPLOYED', 'Deployed',
    (select id from categorytype where originationcode = 'CB' and typecode = 'TESTSTATUS'), 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, originationcode)
    VALUES ('NOT_DEPLOYED', 'NOT_DEPLOYED', 'Not Deployed',
    (select id from categorytype where originationcode = 'CB' and typecode = 'TESTSTATUS'),'CB');








   
