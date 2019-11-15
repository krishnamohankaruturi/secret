-- 413 sql

ALTER TABLE IF EXISTS subscores_rawtoscale RENAME TO subscoresrawtoscale;
update fieldspecification set minimum = 0, fieldtype = 'Number', minimumregex = null where fieldname = 'scaleScore';
