--ddl/754.sql : F797 OK Social Studies adaptive auto enrollment by using student tracker
-- Script to insert proportionmetric valus for Social Studies subject in gradeband 11-12

DO
$BODY$
DECLARE
	ss_contentareaid bigint;
	gradebandid_11_12 bigint;
	contentcodes TEXT[] := ARRAY['SS.EE.HS.HIST1-1',
								'SS.EE.HS.HIST1-2',
								'SS.EE.HS.HIST1-3',
								'SS.EE.HS.HIST1-4',
								'SS.EE.HS.HIST1-5',
								'SS.EE.HS.HIST1-6'];
								
	linkagelevels TEXT[] := ARRAY['DP','PP','TA'];
	
	
	
BEGIN
	RAISE NOTICE 'Started processing';
	ss_contentareaid := (SELECT id FROM contentarea WHERE  abbreviatedname = 'SS' AND activeflag is true);
	gradebandid_11_12 := (SELECT id FROM gradeband WHERE  name = 'Social Studies 11-12' AND contentareaid = ss_contentareaid AND activeflag is true);
					
	--RAISE NOTICE 'ContentAreaId for Social Studies is: %', ss_contentareaid;
	--RAISE NOTICE 'Gradeband Id for SS 11-12 is: %', gradebandid_11_12;
	FOR i IN array_lower(contentcodes, 1) .. array_upper(contentcodes, 1) LOOP
		FOR j IN array_lower(linkagelevels, 1) .. array_upper(linkagelevels, 1) LOOP
			RAISE NOTICE '%',contentcodes[i];
			RAISE NOTICE '%',linkagelevels[j];
			
			INSERT INTO proportionmetrics 
            (contentareaid, 
             gradebandid, 
             linkagelevelid, 
             essentialelementid, 
             essentialelement, 
             linkagelevelabbr, 
             proportionlow, 
             proportionhigh,
			 createduser,
			 modifieduser) 
VALUES      (ss_contentareaid, 
             gradebandid_11_12, 
             (SELECT id 
              FROM   category 
              WHERE  categorycode = linkagelevels[j] 
                     AND activeflag is true), 
             (SELECT cfd.id 
              FROM   contentframeworkdetail cfd, 
                     contentframework cf, 
                     gradeband gb, 
                     contentarea ca, 
                     assessmentprogram ap 
              WHERE  cfd.contentcode = contentcodes[i]
                     AND cfd.activeflag is true 
                     AND cfd.contentframeworkid = cf.id 
                     AND cf.gradebandid = gb.id 
                     AND gb.activeflag is true 
                     AND ca.id = ss_contentareaid
        			 AND gb.id = gradebandid_11_12
                     AND ca.activeflag is true 
                     AND cf.contentareaid = ca.id 
                     AND cf.activeflag is true 
                     AND cf.assessmentprogramid = ap.id 
                     AND ap.abbreviatedname = 'DLM' 
                     AND ap.activeflag is true 
              LIMIT  1), 
             contentcodes[i], 
             linkagelevels[j], 
             0.35, 
             0.79,
			 12,
			 12); 
			
			
		END LOOP;
	END LOOP;
	
	RAISE NOTICE 'Complted processing';
END;
$BODY$;


--Below script is requested by DBA for PLTW purposes

alter table roster add column if not exists classroomid BIGINT;
drop table if exists dashboardconfig;
CREATE TABLE if not exists public.dashboardconfig
(
  id bigserial NOT NULL ,
  dashboardentityname character varying(100) NOT NULL,
  assessmentprogramid bigint,
  operationaltestwindowid bigint,
  configcode character varying(100),
  configvalue character varying(100),
  createddate timestamp with time zone NOT NULL DEFAULT ('now'::text)::timestamp with time zone,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  CONSTRAINT dashboardconfig_pkey PRIMARY KEY (id)
);