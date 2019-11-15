--
-- TOC entry 172 (class 1259 OID 121529)
-- Name: dataarchivehistory; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE dataarchivehistory (
    archid bigint NOT NULL,
    schoolyear integer,
    jobrundate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
);


--ALTER TABLE dataarchivehistory OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 121533)
-- Name: dataarchivehistory_archid; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE dataarchivehistory_archid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE dataarchivehistory_archid OWNER TO postgres;

--
-- TOC entry 174 (class 1259 OID 121535)
-- Name: dataarchivestatus; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE dataarchivestatus (
    schoolyear integer,
    insertstatus boolean DEFAULT false,
    deletestatus boolean DEFAULT false,
    createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    createduser integer
);


--ALTER TABLE dataarchivestatus OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 121541)
-- Name: enrollmentsrosters; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE enrollmentsrosters (
    archid bigint NOT NULL,
    roster_archid bigint NOT NULL,
    studentenrollment_archid bigint NOT NULL,
    enrollmentid bigint NOT NULL,
    rosterid bigint NOT NULL,
    id bigint NOT NULL,
    courseenrollmentstatusid bigint,
    source bigint,
    createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    createduser integer NOT NULL,
    activeflag boolean DEFAULT true,
    modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    modifieduser integer NOT NULL,
    arch_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
);


--ALTER TABLE enrollmentsrosters OWNER TO postgres;

--
-- TOC entry 176 (class 1259 OID 121549)
-- Name: enrollmentsrosters_archid; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE enrollmentsrosters_archid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE enrollmentsrosters_archid OWNER TO postgres;

--
-- TOC entry 177 (class 1259 OID 121551)
-- Name: enrollmenttesttypesubjectarea; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE enrollmenttesttypesubjectarea (
    archid bigint NOT NULL,
    id bigint NOT NULL,
    enrollmentid bigint,
    studentenrollment_archid bigint NOT NULL,
    groupingindicator1 character varying(60),
    groupingindicator2 character varying(60),
    testtypeid bigint NOT NULL,
    testtypecode character varying(50),
    subjectareaid bigint NOT NULL,
    testtypename character varying(100),
    subjectareacode character varying(50),
    subjectareaname character varying(100),
    enrollmenttesttypesubjectarea_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    enrollmenttesttypesubjectarea_createduser integer NOT NULL,
    enrollmenttesttypesubjectarea_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    enrollmenttesttypesubjectarea_modifieduser integer NOT NULL,
    enrollmenttesttypesubjectarea_activeflag boolean DEFAULT true,
    arch_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
);


--ALTER TABLE enrollmenttesttypesubjectarea OWNER TO postgres;

--
-- TOC entry 178 (class 1259 OID 121559)
-- Name: enrollmenttesttypesubjectarea_archid; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE enrollmenttesttypesubjectarea_archid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE enrollmenttesttypesubjectarea_archid OWNER TO postgres;

--
-- TOC entry 179 (class 1259 OID 121561)
-- Name: etl_steplog; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE etl_steplog (
    id_batch integer,
    channel_id character varying(255),
    transname character varying(255),
    stepname character varying(255),
    step_copy smallint,
    lines_read bigint,
    lines_written bigint,
    lines_updated bigint,
    lines_input bigint,
    lines_output bigint,
    lines_rejected bigint,
    errors bigint,
    log_date timestamp without time zone
);


--ALTER TABLE etl_steplog OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 121567)
-- Name: exitwithoutsavetest; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE exitwithoutsavetest (
    archid bigint NOT NULL,
    studentstestsections_archid bigint NOT NULL,
    studenttestsectionid bigint,
    createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_createddtae timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_modifieddtae timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
);


--ALTER TABLE exitwithoutsavetest OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 121573)
-- Name: exitwithoutsavetest_archid; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE exitwithoutsavetest_archid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE exitwithoutsavetest_archid OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 121575)
-- Name: firstcontactsurvey; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE firstcontactsurvey (
    archid bigint NOT NULL,
    id bigint,
    surveyid bigint,
    studentid bigint,
    surveyname character varying(100),
    status bigint,
    survey_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone NOT NULL,
    survey_createduser integer,
    survey_activeflag boolean,
    survey_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone NOT NULL,
    survey_modifieduser integer,
    surveyresponseid bigint,
    responselabel character varying(10),
    labelid bigint,
    label character varying(2000),
    responsetext text,
    surveyresponse_modifieduser integer,
    surveyresponse_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone NOT NULL,
    surveyresponse_activeflag boolean,
    surveyresponse_createduser integer,
    surveyresponse_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone NOT NULL,
    arch_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
);


--ALTER TABLE firstcontactsurvey OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 121587)
-- Name: fristcontactsurvey_archid; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE fristcontactsurvey_archid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE fristcontactsurvey_archid OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 121589)
-- Name: log_etl; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE log_etl (
    id_batch integer,
    channel_id character varying(255),
    transname character varying(255),
    status character varying(15),
    lines_read bigint,
    lines_written bigint,
    lines_updated bigint,
    lines_input bigint,
    lines_output bigint,
    lines_rejected bigint,
    errors bigint,
    startdate timestamp without time zone,
    enddate timestamp without time zone,
    logdate timestamp without time zone,
    depdate timestamp without time zone,
    replaydate timestamp without time zone,
    log_field text
);


--ALTER TABLE log_etl OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 121595)
-- Name: log_performance; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE log_performance (
    id_batch integer,
    seq_nr integer,
    logdate timestamp without time zone,
    transname character varying(255),
    stepname character varying(255),
    step_copy integer,
    lines_read bigint,
    lines_written bigint,
    lines_updated bigint,
    lines_input bigint,
    lines_output bigint,
    lines_rejected bigint,
    errors bigint,
    input_buffer_rows bigint,
    output_buffer_rows bigint
);


--ALTER TABLE log_performance OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 121601)
-- Name: personalneedprofile; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE personalneedprofile (
    archid bigint NOT NULL,
    id bigint NOT NULL,
    selectedvalue text,
    studentid bigint NOT NULL,
    profileitemattributenameattributecontainerid bigint NOT NULL,
    attributenameid bigint,
    attributename character varying(100),
    attributecontainerid bigint,
    attributecontainer character varying(100),
    parentcontainerleveloneid bigint,
    parentcontainerleveltwoid bigint,
    parentcontainerlevelthreeid bigint,
    pianac_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    pianac_createduser integer NOT NULL,
    pianac_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    pianac_modifieduser integer NOT NULL,
    pianac_activeflag boolean DEFAULT true,
    spiav_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    spiav_createduser integer NOT NULL,
    spiav_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    spiav_modifieduser integer NOT NULL,
    spiav_activeflag boolean DEFAULT true,
    arch_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
);


--ALTER TABLE personalneedprofile OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 121615)
-- Name: personalneedprofile_archid; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE personalneedprofile_archid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE personalneedprofile_archid OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 121617)
-- Name: roster; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE roster (
    archid bigint NOT NULL,
    rosterid bigint NOT NULL,
    coursesectionname character varying(75) NOT NULL,
    coursesectiondescription character varying(75),
    teacherid bigint NOT NULL,
    teacherfirstname character varying(80),
    teachermiddlename character varying(80),
    teachersurname character varying(80),
    teacherunicommonidentifier character varying(254),
    teacheremail character varying(254),
    statesubjectareaid bigint,
    statesubjectareaname character varying(100),
    statesubjectabbreviatedname character varying(75),
    courseenrollmentstatusid bigint,
    statecourseid bigint,
    restrictionid bigint NOT NULL,
    statesubjectcourseidentifier character varying(75),
    localcourseid character varying(50),
    educatorschooldisplayidentifier character varying(100),
    attendanceschoolid bigint NOT NULL,
    attendanceschooldisplayidentifier character varying(100),
    attendanceschoolorganizationname character varying(100),
    prevstatesubjectareaid bigint,
    statecoursecode character varying(100),
    source bigint,
    sourcetype character varying(20),
    statecoursesid bigint,
    statescoursesname character varying(150),
    statescoursesabbreviatedname character varying(10),
    currentschoolyear integer,
    aypschoolid bigint,
    aypschooldisplayidentifier character varying(100),
    aypschoolorganizationname character varying(100),
    roster_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    roster_createduser integer NOT NULL,
    roster_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    roster_modifieduser integer NOT NULL,
    roster_activeflag boolean DEFAULT true,
    arch_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
);


--ALTER TABLE roster OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 121628)
-- Name: roster_archid; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE roster_archid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE roster_archid OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 121630)
-- Name: studentadaptivetest; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE studentadaptivetest (
    archid bigint NOT NULL,
    studentstests_archid bigint NOT NULL,
    id bigint NOT NULL,
    studentstestsid bigint NOT NULL,
    nextexternaltestid bigint,
    nextstudentstestsid bigint,
    processedstatus text,
    message text,
    createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    createduser integer,
    modifieduser integer,
    activeflag boolean DEFAULT true,
    arch_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
);


--ALTER TABLE studentadaptivetest OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 121641)
-- Name: studentadaptivetest_archid; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE studentadaptivetest_archid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE studentadaptivetest_archid OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 121643)
-- Name: studentadaptivetestfinaltheta; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE studentadaptivetestfinaltheta (
    archid bigint NOT NULL,
    studentstests_archid bigint NOT NULL,
    studentstestid bigint NOT NULL,
    testconstructid bigint NOT NULL,
    testconstructnumber integer,
    thetavalue double precision,
    iterationcount integer,
    activeflag boolean DEFAULT true,
    createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    createusername character varying(256) DEFAULT 'TDE Student'::character varying,
    modifiedusername character varying(256) DEFAULT 'TDE Student'::character varying,
    modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
);


--ALTER TABLE studentadaptivetestfinaltheta OWNER TO postgres;

--
-- TOC entry 193 (class 1259 OID 121656)
-- Name: studentadaptivetestfinaltheta_archid; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE studentadaptivetestfinaltheta_archid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE studentadaptivetestfinaltheta_archid OWNER TO postgres;

--
-- TOC entry 194 (class 1259 OID 121658)
-- Name: studentadaptivetestthetastatus; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE studentadaptivetestthetastatus (
    archid bigint NOT NULL,
    studentstests_archid bigint NOT NULL,
    studentstestid bigint NOT NULL,
    testpartid bigint,
    testpartnumber integer NOT NULL,
    testsectioncontainerid bigint,
    testsectioncontainernumber integer NOT NULL,
    initialtheta integer,
    interimtheta integer,
    testpartcomplete boolean DEFAULT false,
    activeflag boolean DEFAULT true,
    createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    createusername character varying(256) DEFAULT 'TDE Student'::character varying,
    modifiedusername character varying(256) DEFAULT 'TDE Student'::character varying,
    modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
);


--ALTER TABLE studentadaptivetestthetastatus OWNER TO postgres;

--
-- TOC entry 195 (class 1259 OID 121672)
-- Name: studentadaptivetestthetastatus_archid; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE studentadaptivetestthetastatus_archid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE studentadaptivetestthetastatus_archid OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 121674)
-- Name: studentenrollment; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE studentenrollment (
    archid bigint NOT NULL,
    enrollmentid bigint,
    residencedistrictidentifier character varying(60),
    currentgradelevel integer,
    localstudentidentifier character varying(60),
    currentschoolyear integer,
    schoolentrydate timestamp without time zone,
    districtentrydate timestamp without time zone,
    stateentrydate timestamp without time zone,
    exitwithdrawaldate timestamp without time zone,
    exitwithdrawaltype integer,
    specialcircumstancestransferchoice character varying(60),
    giftedstudent boolean,
    specialedprogramendingdate timestamp without time zone,
    qualifiedfor504 character varying(60),
    attendanceschoolid bigint,
    attendanceschoolorganizationname character varying(100),
    attendanceschoolorganizationdisplayidentifier character varying(100),
    restrictionid bigint,
    source_enrollment bigint,
    aypschoolid bigint,
    aypschoolorganizationname character varying(100),
    ayporganizationdisplayidentifier character varying(60),
    fundingschool character varying(60),
    sourcetype character varying(20),
    studentid bigint,
    statestudentidentifier character varying(50),
    legalfirstname character varying(80),
    legalmiddlename character varying(80),
    legallastname character varying(80),
    generationcode character varying(10),
    dateofbirth date,
    gender integer,
    firstlanguage character varying(2),
    comprehensiverace character varying(5),
    primarydisabilitycode character varying(60),
    username character varying(100),
    password character varying(15),
    synced boolean,
    hispanicethnicity boolean,
    commbandid bigint,
    elabandid bigint,
    finalelabandid bigint,
    mathbandid bigint,
    finalmathbandid bigint,
    source_student character varying(20),
    usaentrydate timestamp without time zone,
    esolparticipationcode character varying(1),
    esolprogramendingdate timestamp without time zone,
    esolprogramentrydate timestamp without time zone,
    profilestatus character varying(100),
    stateid bigint,
    stateorganizationname character varying(100),
    stateorganizationdisplayidentifier character varying(100),
    districtid bigint,
    districtorganizationname character varying(100),
    districtorganizationdisplayidentifier character varying(100),
    assessmentprogramcode text,
    student_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    student_createduser integer,
    student_activeflag boolean DEFAULT true,
    student_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    student_modifieduser integer,
    enrollment_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    enrollment_createduser integer NOT NULL,
    enrollment_activeflag boolean DEFAULT true,
    enrollment_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    enrollment_modifieduser integer NOT NULL,
    arch_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
);


--ALTER TABLE studentenrollment OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 121688)
-- Name: studentenrollment_archid; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE studentenrollment_archid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE studentenrollment_archid OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 121690)
-- Name: studentresponsescore; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE studentresponsescore (
    archid bigint NOT NULL,
    studentstestsections_archid bigint NOT NULL,
    studentstestsectionsid bigint NOT NULL,
    taskvariantid bigint NOT NULL,
    score numeric(6,3),
    dimension text NOT NULL,
    diagnosticstatement text,
    raterid bigint NOT NULL,
    ratername text,
    raterorder smallint,
    raterexposure integer,
    createdate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    activeflag boolean DEFAULT true NOT NULL,
    arch_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
);


--ALTER TABLE studentresponsescore OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 121701)
-- Name: studentresponsescore_archid; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE studentresponsescore_archid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE studentresponsescore_archid OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 121703)
-- Name: studentsadaptivetestsections; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE studentsadaptivetestsections (
    archid bigint NOT NULL,
    studentstest_archid bigint NOT NULL,
    studentstestid bigint NOT NULL,
    testpartid bigint NOT NULL,
    testsectioncontainerid bigint,
    testsectioncontainerthetanodeid bigint,
    testsectionid bigint NOT NULL,
    taskvariantid bigint,
    activeflag boolean DEFAULT true,
    createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    createusername character varying(256) DEFAULT 'TDE Student'::character varying,
    modifiedusername character varying(256) DEFAULT 'TDE Student'::character varying,
    modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
);


--ALTER TABLE studentsadaptivetestsections OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 121716)
-- Name: studentsadaptivetestsections_archid; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE studentsadaptivetestsections_archid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE studentsadaptivetestsections_archid OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 121718)
-- Name: studentsresponseparameters; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE studentsresponseparameters (
    archid bigint NOT NULL,
    studentstests_archid bigint NOT NULL,
    studentstestsectionsid_archid bigint NOT NULL,
    id bigint NOT NULL,
    studentstestsid bigint NOT NULL,
    studentstestsectionsid bigint NOT NULL,
    testid bigint NOT NULL,
    taskvariantid bigint NOT NULL,
    score numeric(6,3),
    avalue double precision,
    bvalue double precision,
    createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    createduser integer,
    modifieduser integer,
    activeflag boolean DEFAULT true,
    b2value double precision,
    formulacode integer,
    arch_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
);


--ALTER TABLE studentsresponseparameters OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 121726)
-- Name: studentsresponseparameters_archid; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE studentsresponseparameters_archid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE studentsresponseparameters_archid OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 121728)
-- Name: studentsresponses; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE studentsresponses (
    archid bigint NOT NULL,
    studentstestsections_archid bigint NOT NULL,
    studentid bigint,
    testid bigint,
    testsectionid bigint,
    studentstestsid bigint,
    studentstestsectionsid bigint NOT NULL,
    taskvariantid bigint NOT NULL,
    foilid bigint,
    response text,
    score numeric(6,3),
    questarrequestid bigint,
    originalscore numeric(6,3),
    questarresponsetext text,
    studentsresponses_createddate timestamp without time zone DEFAULT now(),
    studentsresponses_modifieddate timestamp without time zone DEFAULT now(),
    studentsresponses_createduser integer,
    studentsresponses_modifieduser integer,
    studentsresponses_activeflag boolean DEFAULT true,
    arch_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
);


--ALTER TABLE studentsresponses OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 121739)
-- Name: studentsresponses_archid; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE studentsresponses_archid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE studentsresponses_archid OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 121741)
-- Name: studentstests; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE studentstests (
    archid bigint NOT NULL,
    studentenrollment_archid bigint,
    roster_archid bigint,
    id bigint,
    studentid bigint,
    specialcircumstanceid bigint,
    tagdata text,
    highlighterdata text,
    testid bigint,
    testname character varying(75),
    numitems integer,
    test_externalid bigint,
    originationcode character varying(20),
    directions text,
    uitypecode character varying(20),
    reviewtext text,
    begininstructions text,
    endinstructions text,
    test_status bigint,
    test_gradecourseid bigint,
    test_contentareaid bigint,
    contentarea_name character varying(100) NOT NULL,
    contentarea_abbreviatedname character varying(75),
    testformatcode character varying(30),
    adaptivetypecode character varying(25),
    testtimeformattypecode character varying(25),
    numberofparts integer,
    ndcst integer,
    ndit integer,
    interimthetaestmodeltypecode character varying(25),
    unidimnssbpsnasbpsmodeltypecode character varying(25),
    testinternalname character varying(100),
    varianttypeid bigint,
    qccomplete boolean,
    accessibleform boolean,
    avglinkagelevel real,
    convergencecriterionvalue numeric,
    maxiterationsvalue smallint,
    maxthetavalue numeric,
    maxthetachangevalue numeric,
    minthetavalue numeric,
    minthetachangevalue numeric,
    gradetype boolean,
    gradebandid bigint,
    unpublishreasonid bigint,
    tutorialflag boolean,
    maxattempts smallint,
    maxtimesaday smallint,
    feedbackneeded boolean,
    outcometypecode character varying(75),
    nbrofoutcomes smallint,
    maxscore integer,
    testspecificationid bigint,
    test_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    test_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    test_createduser integer,
    test_activeflag boolean,
    test_modifieduser integer,
    studentstests_testcollectionid bigint,
    studentstests_status bigint,
    testsessionid bigint,
    rosterid bigint,
    name character varying(200),
    testsession_status bigint,
    testsession_testcollectionid bigint,
    testsession_source character varying(20),
    attendanceschoolid bigint,
    operationaltestwindowid bigint,
    testtypeid bigint,
    testsession_gradecourseid bigint,
    stageid bigint,
    windowexpirydate timestamp without time zone,
    testsession_schoolyear bigint,
    testpanelid bigint,
    testsession_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    testsession_createduser integer,
    testsession_activeflag boolean,
    testsession_modifieduser integer,
    testsession_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    ticketno character varying(75),
    startdatetime timestamp with time zone,
    enddatetime timestamp with time zone,
    scores text,
    studentstests_enrollmentid bigint,
    studentstests_finalbandid bigint,
    enhancednotes text,
    interimtheta double precision,
    previousstudentstestid bigint,
    studentstests_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    studentstests_modifieduser integer,
    studentstests_activeflag boolean,
    studentstests_createduser integer,
    studentstests_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
);


--ALTER TABLE studentstests OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 121755)
-- Name: studentstests_archid; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE studentstests_archid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE studentstests_archid OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 121757)
-- Name: studentstestsections; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE studentstestsections (
    archid bigint NOT NULL,
    studentstests_archid bigint NOT NULL,
    id bigint NOT NULL,
    studentstestid bigint,
    testsectionid bigint,
    statusid bigint NOT NULL,
    lastnavqnum integer DEFAULT 0,
    startdatetime timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    enddatetime timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    scores text,
    testpartid bigint,
    ticketno character varying(75) DEFAULT NULL::character varying,
    studentstestsections_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    studentstestsections_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    studentstestsections_createduser integer,
    studentstestsections_modifieduser integer,
    studentstestsections_activeflag boolean DEFAULT true,
    arch_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
);


--ALTER TABLE studentstestsections OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 121772)
-- Name: studentstestsections_archid; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE studentstestsections_archid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE studentstestsections_archid OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 121774)
-- Name: studentstestsectionstasks; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE studentstestsectionstasks (
    archid bigint NOT NULL,
    studentstestsections_archid bigint NOT NULL,
    taskid bigint NOT NULL,
    sortorder integer NOT NULL,
    studentstestsectionsid bigint NOT NULL,
    studentstestsectionstasks_createdate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    studentstestsectionstasks_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    studentstestsectionstasks_createusername character varying(256) DEFAULT 'TDE Student'::character varying,
    studentstestsectionstasks_modifiedusername character varying(256) DEFAULT 'TDE Student'::character varying,
    arch_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
);


--ALTER TABLE studentstestsectionstasks OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 121786)
-- Name: studentstestsectionstasks_archid; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE studentstestsectionstasks_archid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE studentstestsectionstasks_archid OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 121788)
-- Name: studentstestsectionstasksfoils; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE studentstestsectionstasksfoils (
    archid bigint NOT NULL,
    studentstestsections_archid bigint NOT NULL,
    taskid bigint NOT NULL,
    foilid bigint NOT NULL,
    sortorder integer NOT NULL,
    studentstestsectionsid bigint NOT NULL,
    studentstestsectionstasksfoils_createdate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    studentstestsectionstasksfoils_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    studentstestsectionstasksfoils_createusername character varying(256) DEFAULT 'TDE Student'::character varying,
    studentstestsectionstasksfoils_modifiedusername character varying(256) DEFAULT 'TDE Student'::character varying,
    arch_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
);


--ALTER TABLE studentstestsectionstasksfoils OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 121800)
-- Name: studentstestsectionstasksfoils_archid; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE studentstestsectionstasksfoils_archid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE studentstestsectionstasksfoils_archid OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 121802)
-- Name: studentstestshisotry_archid; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE studentstestshisotry_archid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE studentstestshisotry_archid OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 121804)
-- Name: studentstestshistory; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE studentstestshistory (
    archid bigint NOT NULL,
    studentstests_archid bigint NOT NULL,
    id bigint NOT NULL,
    studentstestsid bigint NOT NULL,
    studentstestsstatusid bigint NOT NULL,
    action character varying(20),
    acteduser integer NOT NULL,
    acteddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone NOT NULL,
    arch_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
);


--ALTER TABLE studentstestshistory OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 121810)
-- Name: studenttracker; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE studenttracker (
    archid bigint NOT NULL,
    studentstests_archid bigint NOT NULL,
    id bigint NOT NULL,
    studentid bigint,
    contentareaid bigint,
    contentareaname character varying(100),
    contentareaabbreviatedname character varying(75),
    schoolyear bigint,
    courseid bigint,
    status character varying(20),
    studenttrackerband_id bigint,
    complexitybandid bigint,
    testsessionid bigint,
    source character varying(40),
    operationalwindowid bigint,
    essentialelementid bigint,
    studenttracker_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone NOT NULL,
    studenttracker_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone NOT NULL,
    studenttracker_createduser integer,
    studenttracker_modifieduser integer,
    studenttracker_activeflag boolean DEFAULT true,
    studenttrackerband_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone NOT NULL,
    studenttrackerband_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone NOT NULL,
    studenttrackerband_createduser integer,
    studenttrackerband_modifieduser integer,
    studenttrackerband_activeflag boolean DEFAULT true,
    arch_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    arch_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
);


--ALTER TABLE studenttracker OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 121821)
-- Name: studenttrackerband_archid; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE studenttrackerband_archid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE studenttrackerband_archid OWNER TO postgres;

--
-- TOC entry 2161 (class 2606 OID 121824)
-- Name: enrollmentsrosters_archid_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY enrollmentsrosters
    ADD CONSTRAINT enrollmentsrosters_archid_pkey PRIMARY KEY (archid);


--
-- TOC entry 2163 (class 2606 OID 121826)
-- Name: enrollmenttesttypesubjectarea_archid_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY enrollmenttesttypesubjectarea
    ADD CONSTRAINT enrollmenttesttypesubjectarea_archid_pkey PRIMARY KEY (archid);


--
-- TOC entry 2169 (class 2606 OID 121828)
-- Name: profile_archid_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY personalneedprofile
    ADD CONSTRAINT profile_archid_pkey PRIMARY KEY (archid);


--
-- TOC entry 2171 (class 2606 OID 121830)
-- Name: roster_archid_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY roster
    ADD CONSTRAINT roster_archid_pkey PRIMARY KEY (archid);


--
-- TOC entry 2173 (class 2606 OID 121832)
-- Name: studentadaptivetest_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY studentadaptivetest
    ADD CONSTRAINT studentadaptivetest_pkey PRIMARY KEY (id);


--
-- TOC entry 2177 (class 2606 OID 121834)
-- Name: studentenrollment_archid_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY studentenrollment
    ADD CONSTRAINT studentenrollment_archid_pkey PRIMARY KEY (archid);


--
-- TOC entry 2179 (class 2606 OID 121836)
-- Name: studentresponsescore_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY studentresponsescore
    ADD CONSTRAINT studentresponsescore_pkey PRIMARY KEY (studentstestsectionsid, taskvariantid, raterid, dimension);


--
-- TOC entry 2183 (class 2606 OID 121838)
-- Name: studentsresponseparameters_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY studentsresponseparameters
    ADD CONSTRAINT studentsresponseparameters_pkey PRIMARY KEY (id);


--
-- TOC entry 2187 (class 2606 OID 121840)
-- Name: studentsresponses_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY studentsresponses
    ADD CONSTRAINT studentsresponses_pkey PRIMARY KEY (archid);


--
-- TOC entry 2185 (class 2606 OID 121842)
-- Name: studentsresponses_ukey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY studentsresponseparameters
    ADD CONSTRAINT studentsresponses_ukey UNIQUE (studentstestsectionsid, taskvariantid);


--
-- TOC entry 2189 (class 2606 OID 121844)
-- Name: studentstests_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY studentstests
    ADD CONSTRAINT studentstests_pkey PRIMARY KEY (archid);


--
-- TOC entry 2191 (class 2606 OID 121846)
-- Name: studentstestsections_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY studentstestsections
    ADD CONSTRAINT studentstestsections_pkey PRIMARY KEY (archid);


--
-- TOC entry 2193 (class 2606 OID 121848)
-- Name: studentstestsectionstasks_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY studentstestsectionstasks
    ADD CONSTRAINT studentstestsectionstasks_pkey PRIMARY KEY (archid);


--
-- TOC entry 2195 (class 2606 OID 121850)
-- Name: studentstestsectionstasksfoils_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY studentstestsectionstasksfoils
    ADD CONSTRAINT studentstestsectionstasksfoils_pkey PRIMARY KEY (archid);


--
-- TOC entry 2197 (class 2606 OID 121852)
-- Name: studentstestshistory_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY studentstestshistory
    ADD CONSTRAINT studentstestshistory_pkey PRIMARY KEY (archid);


--
-- TOC entry 2165 (class 2606 OID 121854)
-- Name: studentsurvey_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY firstcontactsurvey
    ADD CONSTRAINT studentsurvey_pkey PRIMARY KEY (archid);


--
-- TOC entry 2199 (class 2606 OID 121856)
-- Name: studenttracker_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY studenttracker
    ADD CONSTRAINT studenttracker_pkey PRIMARY KEY (archid);


--
-- TOC entry 2175 (class 2606 OID 121858)
-- Name: uk_studentadaptivetestthetastatus; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY studentadaptivetestthetastatus
    ADD CONSTRAINT uk_studentadaptivetestthetastatus PRIMARY KEY (studentstestid, testpartnumber, testsectioncontainernumber);


--
-- TOC entry 2181 (class 2606 OID 121860)
-- Name: uk_studentsadaptivetestsections; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY studentsadaptivetestsections
    ADD CONSTRAINT uk_studentsadaptivetestsections PRIMARY KEY (studentstestid, testpartid, testsectionid);


--
-- TOC entry 2166 (class 1259 OID 121861)
-- Name: idx_log_etl_1; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX idx_log_etl_1 ON log_etl USING btree (id_batch);


--
-- TOC entry 2167 (class 1259 OID 121862)
-- Name: idx_log_etl_2; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX idx_log_etl_2 ON log_etl USING btree (errors, status, transname);


--
-- TOC entry 2202 (class 2606 OID 121863)
-- Name: enrollmentidfk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY enrollmenttesttypesubjectarea
    ADD CONSTRAINT enrollmentidfk FOREIGN KEY (studentenrollment_archid) REFERENCES studentenrollment(archid);


--
-- TOC entry 2200 (class 2606 OID 121868)
-- Name: enrollmentsrosters_roster_archidfk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY enrollmentsrosters
    ADD CONSTRAINT enrollmentsrosters_roster_archidfk FOREIGN KEY (studentenrollment_archid) REFERENCES studentenrollment(archid);


--
-- TOC entry 2201 (class 2606 OID 121873)
-- Name: enrollmentsrosters_studentenrollment_archidfk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY enrollmentsrosters
    ADD CONSTRAINT enrollmentsrosters_studentenrollment_archidfk FOREIGN KEY (studentenrollment_archid) REFERENCES studentenrollment(archid);


--
-- TOC entry 2203 (class 2606 OID 121878)
-- Name: fk_exitwithoutsavetest_studentstestsectionid_testsectionid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY exitwithoutsavetest
    ADD CONSTRAINT fk_exitwithoutsavetest_studentstestsectionid_testsectionid FOREIGN KEY (studentstestsections_archid) REFERENCES studentstestsections(archid);


--
-- TOC entry 2204 (class 2606 OID 121883)
-- Name: fk_studentadaptivetest_studentstestsid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentadaptivetest
    ADD CONSTRAINT fk_studentadaptivetest_studentstestsid FOREIGN KEY (studentstests_archid) REFERENCES studentstests(archid);


--
-- TOC entry 2205 (class 2606 OID 121888)
-- Name: fk_studentadaptivetestfinaltheta_studentstestid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentadaptivetestfinaltheta
    ADD CONSTRAINT fk_studentadaptivetestfinaltheta_studentstestid FOREIGN KEY (studentstests_archid) REFERENCES studentstests(archid);


--
-- TOC entry 2206 (class 2606 OID 121893)
-- Name: fk_studentadaptivetestthetastatus_studentstestid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentadaptivetestthetastatus
    ADD CONSTRAINT fk_studentadaptivetestthetastatus_studentstestid FOREIGN KEY (studentstests_archid) REFERENCES studentstests(archid);


--
-- TOC entry 2207 (class 2606 OID 121898)
-- Name: fk_studentresponsescore_studentstestsection_archid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentresponsescore
    ADD CONSTRAINT fk_studentresponsescore_studentstestsection_archid FOREIGN KEY (studentstestsections_archid) REFERENCES studentstestsections(archid);


--
-- TOC entry 2208 (class 2606 OID 121903)
-- Name: fk_studentsadaptivetestsections_studentstestid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentsadaptivetestsections
    ADD CONSTRAINT fk_studentsadaptivetestsections_studentstestid FOREIGN KEY (studentstest_archid) REFERENCES studentstests(archid);


--
-- TOC entry 2209 (class 2606 OID 121908)
-- Name: fk_studentsresponseparameters_studentstestsectionsid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentsresponseparameters
    ADD CONSTRAINT fk_studentsresponseparameters_studentstestsectionsid FOREIGN KEY (studentstestsectionsid_archid) REFERENCES studentstestsections(archid);


--
-- TOC entry 2210 (class 2606 OID 121913)
-- Name: fk_studentsresponseparameters_studentstestsid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentsresponseparameters
    ADD CONSTRAINT fk_studentsresponseparameters_studentstestsid FOREIGN KEY (studentstests_archid) REFERENCES studentstests(archid);


--
-- TOC entry 2211 (class 2606 OID 121918)
-- Name: fk_studentsresponses_studentstestsection_archid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentsresponses
    ADD CONSTRAINT fk_studentsresponses_studentstestsection_archid FOREIGN KEY (studentstestsections_archid) REFERENCES studentstestsections(archid);


--
-- TOC entry 2217 (class 2606 OID 121923)
-- Name: fk_studentstests_archid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentstestshistory
    ADD CONSTRAINT fk_studentstests_archid FOREIGN KEY (studentstests_archid) REFERENCES studentstests(archid);


--
-- TOC entry 2215 (class 2606 OID 121928)
-- Name: fk_studentstestsection_archid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentstestsectionstasks
    ADD CONSTRAINT fk_studentstestsection_archid FOREIGN KEY (studentstestsections_archid) REFERENCES studentstestsections(archid);


--
-- TOC entry 2214 (class 2606 OID 121933)
-- Name: fk_studentstestsections_studentstest_archid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentstestsections
    ADD CONSTRAINT fk_studentstestsections_studentstest_archid FOREIGN KEY (studentstests_archid) REFERENCES studentstests(archid);


--
-- TOC entry 2216 (class 2606 OID 121938)
-- Name: fk_studentstestsectionstasksfoils_studentstestsection_archid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentstestsectionstasksfoils
    ADD CONSTRAINT fk_studentstestsectionstasksfoils_studentstestsection_archid FOREIGN KEY (studentstestsections_archid) REFERENCES studentstestsections(archid);


--
-- TOC entry 2218 (class 2606 OID 121943)
-- Name: fk_studenttracker_studentstests_archid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studenttracker
    ADD CONSTRAINT fk_studenttracker_studentstests_archid FOREIGN KEY (studentstests_archid) REFERENCES studentstests(archid);


--
-- TOC entry 2212 (class 2606 OID 121948)
-- Name: rosterid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentstests
    ADD CONSTRAINT rosterid_fk FOREIGN KEY (roster_archid) REFERENCES roster(archid);


--
-- TOC entry 2213 (class 2606 OID 121953)
-- Name: studentenrollmentid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentstests
    ADD CONSTRAINT studentenrollmentid_fk FOREIGN KEY (studentenrollment_archid) REFERENCES studentenrollment(archid);


