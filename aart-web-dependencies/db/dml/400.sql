-- 400.sql

update fieldspecification set minimum = null, fieldtype = 'Decimal', minimumregex = '^(?=.*[1-9])\d*(?:\.\d{1,9})?$' where fieldname = 'rawScore';
update fieldspecification set minimum = null, fieldtype = 'Decimal', minimumregex = '^(?=.*[1-9])\d*(?:\.\d{1,9})?$' where fieldname = 'scaleScore';
update fieldspecification set minimum = null, fieldtype = 'Decimal', minimumregex = '^(?=.*[1-9])\d*(?:\.\d{1,9})?$' where fieldname = 'standardError';