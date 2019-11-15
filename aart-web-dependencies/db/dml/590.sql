--dml/590.sql ==> For ddl/590.sql

--US19073: [Continued] Emails Related to Processing KIDS for 2017
INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
    VALUES ('tascEducatorNotActivated', 'TASC_EducatorNotActivated',(select id from categorytype where typecode= 'KIDS_EMAIL_TEMPLATES'),
    'TASC: The educator was added to Educator Portal, but must be activated by using the Settings menu.');

    