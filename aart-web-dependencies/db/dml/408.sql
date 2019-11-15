--DE9936: Raw score and Subscore Raw score can have the value '0'
update fieldspecification
set minimumregex = '^(?=.*[0-9])\d*(?:\.\d{0,9})?$'
where fieldname = 'rawScore' or fieldname = 'scaleScore';
