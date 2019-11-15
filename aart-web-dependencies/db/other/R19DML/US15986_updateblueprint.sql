Alter table blueprint add column writingtestlet boolean DEFAULT false;

update blueprint set writingtestlet=true where contentareaid=(select id from contentarea where abbreviatedname='ELA') 
and gradecourseid=(select id from gradecourse where abbreviatedname='3' and contentareaid=(select id from contentarea where abbreviatedname='ELA'))
and criteria=4;

update blueprint set writingtestlet=true where contentareaid=(select id from contentarea where abbreviatedname='ELA') 
and gradecourseid=(select id from gradecourse where abbreviatedname='4' and contentareaid=(select id from contentarea where abbreviatedname='ELA'))
and criteria=4;

update blueprint set writingtestlet=true where contentareaid=(select id from contentarea where abbreviatedname='ELA') 
and gradecourseid=(select id from gradecourse where abbreviatedname='5' and contentareaid=(select id from contentarea where abbreviatedname='ELA'))
and criteria=4;

update blueprint set writingtestlet=true where contentareaid=(select id from contentarea where abbreviatedname='ELA') 
and gradecourseid=(select id from gradecourse where abbreviatedname='6' and contentareaid=(select id from contentarea where abbreviatedname='ELA'))
and criteria=4;

update blueprint set writingtestlet=true where contentareaid=(select id from contentarea where abbreviatedname='ELA') 
and gradecourseid=(select id from gradecourse where abbreviatedname='7' and contentareaid=(select id from contentarea where abbreviatedname='ELA'))
and criteria=4;

update blueprint set writingtestlet=true where contentareaid=(select id from contentarea where abbreviatedname='ELA') 
and gradecourseid=(select id from gradecourse where abbreviatedname='8' and contentareaid=(select id from contentarea where abbreviatedname='ELA'))
and criteria=4;

update blueprint set writingtestlet=true where contentareaid=(select id from contentarea where abbreviatedname='ELA') 
and gradecourseid=(select id from gradecourse where abbreviatedname='9' and contentareaid=(select id from contentarea where abbreviatedname='ELA'))
and criteria=3;

update blueprint set writingtestlet=true where contentareaid=(select id from contentarea where abbreviatedname='ELA') 
and gradecourseid=(select id from gradecourse where abbreviatedname='10' and contentareaid=(select id from contentarea where abbreviatedname='ELA'))
and criteria=3;


update blueprint set writingtestlet=true where contentareaid=(select id from contentarea where abbreviatedname='ELA') 
and gradecourseid=(select id from gradecourse where abbreviatedname='11' and contentareaid=(select id from contentarea where abbreviatedname='ELA'))
and criteria=3;

update blueprint set writingtestlet=true where contentareaid=(select id from contentarea where abbreviatedname='ELA') 
and gradecourseid=(select id from gradecourse where abbreviatedname='12' and contentareaid=(select id from contentarea where abbreviatedname='ELA'))
and criteria=3;
