--dml/787.sql

DELETE FROM linkagelevelsortorder;
SELECT setval('linkagelevelsortorder_id_seq'::regclass, 1, FALSE);

DO
$BODY$
DECLARE
    sysadmin BIGINT := (SELECT id FROM aartuser WHERE username = 'cetesysadmin' LIMIT 1);
    ca_abbr TEXT := 'ELA';
    ll_abbrs TEXT[] := ARRAY['IP', 'DP', 'PP', 'TA', 'S'];
    ord INTEGER;
BEGIN
    IF EXISTS (SELECT id FROM assessmentprogram WHERE abbreviatedname = 'DLM') THEN
        FOR ord IN array_lower(ll_abbrs, 1) .. array_upper(ll_abbrs, 1) LOOP
            INSERT INTO linkagelevelsortorder (contentareaid, displayname, sortorder, createduser, modifieduser)
                VALUES (
                    (SELECT id FROM contentarea WHERE abbreviatedname = ca_abbr),
                    (
                        SELECT ll.categoryname
                        FROM category ll
                        JOIN categorytype ct ON ll.categorytypeid = ct.id
                        WHERE ct.typecode = 'ESSENTIAL_ELEMENT_LINKAGE'
                        AND ll.categorycode = ll_abbrs[ord]
                    ),
                    ord,
                    sysadmin,
                    sysadmin
                );
        END LOOP;
    END IF;
END;
$BODY$;


DO
$BODY$
DECLARE
    sysadmin BIGINT := (SELECT id FROM aartuser WHERE username = 'cetesysadmin' LIMIT 1);
    ca_abbr TEXT := 'M';
    ll_abbrs TEXT[] := ARRAY['IP', 'DP', 'PP', 'TA', 'S'];
    ord INTEGER;
BEGIN
    IF EXISTS (SELECT id FROM assessmentprogram WHERE abbreviatedname = 'DLM') THEN
        FOR ord IN array_lower(ll_abbrs, 1) .. array_upper(ll_abbrs, 1) LOOP
            INSERT INTO linkagelevelsortorder (contentareaid, displayname, sortorder, createduser, modifieduser)
                VALUES (
                    (SELECT id FROM contentarea WHERE abbreviatedname = ca_abbr),
                    (
                        SELECT ll.categoryname
                        FROM category ll
                        JOIN categorytype ct ON ll.categorytypeid = ct.id
                        WHERE ct.typecode = 'ESSENTIAL_ELEMENT_LINKAGE'
                        AND ll.categorycode = ll_abbrs[ord]
                    ),
                    ord,
                    sysadmin,
                    sysadmin
                );
        END LOOP;
    END IF;
END;
$BODY$;

DO
$BODY$
DECLARE
    sysadmin BIGINT := (SELECT id FROM aartuser WHERE username = 'cetesysadmin' LIMIT 1);
    ca_abbr TEXT := 'Sci';
    lls TEXT[] := ARRAY['Initial Precursor', 'Proximal Precursor', 'Target'];
    ord INTEGER;
BEGIN
    IF EXISTS (SELECT id FROM assessmentprogram WHERE abbreviatedname = 'DLM') THEN
        FOR ord IN array_lower(lls, 1) .. array_upper(lls, 1) LOOP
            INSERT INTO linkagelevelsortorder (contentareaid, displayname, sortorder, createduser, modifieduser)
                VALUES (
                    (SELECT id FROM contentarea WHERE abbreviatedname = ca_abbr),
                    lls[ord],
                    ord,
                    sysadmin,
                    sysadmin
                );
        END LOOP;
    END IF;
END;
$BODY$;

DO
$BODY$
BEGIN
	IF ((SELECT count(*) FROM appconfiguration WHERE attrcode like 'IPA_Search_Pagination') = 0) THEN
		INSERT INTO public.appconfiguration (attrcode,attrtype,attrname,attrvalue,activeflag,createduser,createddate,modifieduser,modifieddate)
		VALUES ('IPA_Search_Pagination','ipa_pagination','pagination_val','5',true,12,now(),12,now());
	ELSE
		RAISE NOTICE '%', 'Skipping';
	END IF;
END;
$BODY$;

DO
$BODY$
DECLARE
    sysadmin BIGINT := (SELECT id FROM aartuser WHERE username = 'cetesysadmin' LIMIT 1);
BEGIN
    IF NOT EXISTS (SELECT id FROM categorytype WHERE typecode = 'IAP_STATUS') THEN
        INSERT INTO categorytype (typename, typecode, typedescription, originationcode, createduser, modifieduser)
            VALUES (
                'Instruction and Assessment Planner Status',
                'IAP_STATUS',
                'Status codes for entries made by the Instruction and Assessment Planner',
                'EP',
                sysadmin,
                sysadmin
            );
        
        INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createduser, modifieduser)
            VALUES (
                'Instruction Started',
                'STARTED',
                'A plan has been created',
                (SELECT id FROM categorytype WHERE typecode = 'IAP_STATUS'),
                'EP',
                sysadmin,
                sysadmin
            );
        INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createduser, modifieduser)
            VALUES (
                'Instruction Completed - No Testlet Assigned',
                'COMPLETED-WITH-NO-TESTLET',
                'Instruction complete, but system was not to assign a testlet',
                (SELECT id FROM categorytype WHERE typecode = 'IAP_STATUS'),
                'EP',
                sysadmin,
                sysadmin
            );
        INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createduser, modifieduser)
            VALUES (
                'Instruction Completed - Testlet Assigned',
                'COMPLETED-WITH-TESTLET',
                'Instruction complete and a testlet should be assigned',
                (SELECT id FROM categorytype WHERE typecode = 'IAP_STATUS'),
                'EP',
                sysadmin,
                sysadmin
            );
        INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createduser, modifieduser)
            VALUES (
                'Instruction Completed - Testlet Canceled',
                'CANCELED',
                'Instruction complete, but the testlet was canceled.',
                (SELECT id FROM categorytype WHERE typecode = 'IAP_STATUS'),
                'EP',
                sysadmin,
                sysadmin
            );
        
    END IF;
END;
$BODY$;

DO
$BODY$
DECLARE
    sysadmin BIGINT := (SELECT id FROM aartuser WHERE username = 'cetesysadmin' LIMIT 1);
BEGIN
    UPDATE authorities
    SET displayname = 'IAP - Cancel Testlet',
    modifieduser = sysadmin,
    modifieddate = now()
    WHERE authority = 'CANCEL_ITI_ASSIGNMENT';
    
    UPDATE authorities
    SET activeflag = FALSE,
    modifieduser = sysadmin,
    modifieddate = now()
    WHERE authority = 'ITI_OVERRIDE_SYS_REC_LEVEL';
END;
$BODY$;

