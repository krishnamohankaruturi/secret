--dml/738.sql

INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser, 
            createddate, modifieduser, modifieddate)
    VALUES ('redirecturl','url','redirecturl','/studentHome.htm',12,now(),12,now());

INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser, 
            createddate, modifieduser, modifieddate)
    VALUES ('alttextimg','tdeaccessibility','alternatetext','No Description Available',12,now(),12,now());