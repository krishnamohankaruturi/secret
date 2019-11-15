-- Create table to find the count difference after delete
Create table if not exists archivetablecount (table_name character varying NOT NULL,school_year integer NOT NULL,
before_count integer NOT NULL,after_count integer NOT NULL,
createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
,modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
,difference integer)
WITH (
  OIDS=FALSE
);
ALTER TABLE archivetablecount
  OWNER TO aart;
GRANT ALL ON TABLE archivetablecount TO aart;
GRANT SELECT ON TABLE archivetablecount TO aart_reader;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE archivetablecount TO aart_user;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE archivetablecount TO etl_user;

-- Create function to find the count before\after delete
CREATE OR REPLACE FUNCTION public.archivetablecount(var_action character varying,var_schoolyear integer)
  RETURNS character varying AS
$BODY$
	DECLARE 
		
		val_srccnt bigint;
		val_tgtcnt bigint;
		val_tabcnt bigint;
		val_aartdwfulltable bigint;
		tables_list record;
		sqltgtcnt text;
		sqlsrccnt text;
		sqlsrctab text;
		now_date timestamp with time zone;
	BEGIN
	sqltgtcnt:='';
	sqlsrccnt:='';
	sqlsrctab:='';
	val_tgtcnt:=0;
	val_srccnt:=0;
        val_tabcnt:=0;
        val_aartdwfulltable:=0;
        now_date:=clock_timestamp();
        for tables_list in (select table_name from information_schema.tables
                            where table_type='BASE TABLE' and table_schema='public' and table_name not in ('ddl_version') order by table_name)
        loop 
	
		sqltgtcnt:='SELECT COALESCE(count(1),0) FROM ##$var_tab_name$##;';        
		select replace(sqltgtcnt,'##$var_tab_name$##',tables_list.table_name) into sqltgtcnt;
		execute sqltgtcnt INTO val_tgtcnt;
	        RAISE INFO 'Number Of rows counting on TABLE NAME,ROWS:%,%',tables_list.table_name,val_tgtcnt;
	        if (var_action='before_count') then
	        insert into  archivetablecount(table_name,school_year,before_count,after_count,createddate,modifieddate)
	        select  tables_list.table_name  table_name,
	                var_schoolyear          school_year,  
	                val_tgtcnt              before_count,
	                0                       after_count,
	                now_date                createddate,
	                now_date                modifieddate;
		END IF; 
		if (var_action='after_count') then
	        update archivetablecount
	        set after_count=val_tgtcnt,
	            modifieddate=clock_timestamp()
	        where  table_name=tables_list.table_name and  school_year=var_schoolyear;
	        END IF; 
	        update  archivetablecount
	        set difference= before_count-after_count;
        end loop;		                                                          
        return 'SUCCESS'; END;
	$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.archivetablecount(character varying,integer)
  OWNER TO aart;
GRANT EXECUTE ON FUNCTION public.archivetablecount(character varying,integer) TO public;
GRANT EXECUTE ON FUNCTION public.archivetablecount(character varying,integer) TO aart;

-- select archivetablecount('before_count',2016);
-- select archivetablecount('after_count',2016);
-- select * from archivetablecount order by difference desc;
-- select * from archivetablecount where difference>0;