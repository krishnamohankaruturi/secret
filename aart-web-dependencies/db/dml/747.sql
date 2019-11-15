--DE17635
update appconfiguration 
set attrname ='This student already exists, but is not active. Would you like to activate?.' 
where  attrcode='add_student_exist_not_active_find';

update appconfiguration 
set attrname ='A student with the entered State Student Identifier already exists, but is not active. Please see your district or state coordinators to activate for the current school year.' 
where  attrcode='add_student_exist_not_active_noedit_perm';

update appconfiguration 
set attrname ='A student with the entered State Student Identifier already exists and is active for this school year. To make changes, please use the upload, edit or transfer functions.' 
where  attrcode='add_student_exist_active';

--DE17632
update appconfiguration set attrcode = 'student_generation_VII', attrname = 'VIII', attrvalue = 'VIII'
where id in (select id from appconfiguration where attrcode = 'student_generation_VII' order by id desc limit 1);
