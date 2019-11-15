create table taskvariantrescore (
taskvariantid bigint,
cbtaskvariantid bigint,
maxscore bigint,
scoringmethod character varying(75),
scoringdata text,
newmaxscore bigint,
newscoringmethod character varying(75),
newscoringdata text,
reason character varying(250),
createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
processeddate timestamp,
processerror text
);