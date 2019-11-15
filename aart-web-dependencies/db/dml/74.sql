
-- Changes to PNP category values as required for TDE

update category set categorycode = 'textonly' where categorycode = 'TEXT_ONLY';

update category set categorycode = 'textandgraphics' where categorycode = 'TEXT_GRAPHIC';

update category set categorycode = 'graphicsonly' where categorycode = 'GRAPHIC_ONLY';

update category set categorycode = 'nonvisual' where categorycode = 'NON_VISUAL';



-- Reverting the PD sql changes.

delete from groupauthorities where groupid in( (select id from groups where groupname='PD Admin'and organizationid = (select id from organization where displayidentifier = 'CETE')),
	(select id from groups where groupname='PD User'and organizationid = (select id from organization where displayidentifier = 'CETE')), 
	(select id from groups where groupname='PD State Admin'and organizationid = (select id from organization where displayidentifier = 'CETE')));


delete from authorities where authority in ('VIEW_PROFESSIONAL_DEVELOPMENT','VIEW_MODULES','EDIT_MODULES','DELETE_MODULES');

delete from userorganizationsgroups where groupid in (select id from groups where groupname in ('PD Admin','PD User', 'PD State Admin'));

delete from groups where groupname in ('PD Admin','PD User', 'PD State Admin');
