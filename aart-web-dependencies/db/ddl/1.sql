--
-- TOC entry 167 (class 1259 OID 29666)
-- Dependencies: 5
-- Name: aartuser; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE aartuser (
    id bigint NOT NULL,
    username character varying(50) NOT NULL,
    firstname character varying(50),
    middlename character varying(50),
    surname character varying(50),
    password character varying(128) NOT NULL,
    email character varying(50) NOT NULL,
    uniquecommonidentifier character varying(10),
    defaultusergroupsid bigint,
    ukey character varying(16)
);


ALTER TABLE public.aartuser OWNER TO aart;

--
-- TOC entry 3016 (class 0 OID 0)
-- Dependencies: 167
-- Name: COLUMN aartuser.uniquecommonidentifier; Type: COMMENT; Schema: public; Owner: aart
--

COMMENT ON COLUMN aartuser.uniquecommonidentifier IS 'Common identifier that is unique with in an organization.';


--
-- TOC entry 166 (class 1259 OID 29664)
-- Dependencies: 167 5
-- Name: aartuser_id_seq; Type: SEQUENCE; Schema: public; Owner: aart
--

CREATE SEQUENCE aartuser_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.aartuser_id_seq OWNER TO aart;

--
-- TOC entry 3018 (class 0 OID 0)
-- Dependencies: 166
-- Name: aartuser_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: aart
--

ALTER SEQUENCE aartuser_id_seq OWNED BY aartuser.id;


--
-- TOC entry 199 (class 1259 OID 30023)
-- Dependencies: 5
-- Name: assessment; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE assessment (
    id bigint NOT NULL,
    testtypeid bigint NOT NULL,
    assessmentprogramid bigint,
    assessmentname character varying(40),
    testsubjectid bigint
);


ALTER TABLE public.assessment OWNER TO aart;

--
-- TOC entry 181 (class 1259 OID 29778)
-- Dependencies: 5
-- Name: assessment_id_seq; Type: SEQUENCE; Schema: public; Owner: aart
--

CREATE SEQUENCE assessment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.assessment_id_seq OWNER TO aart;

--
-- TOC entry 198 (class 1259 OID 30021)
-- Dependencies: 199 5
-- Name: assessment_id_seq1; Type: SEQUENCE; Schema: public; Owner: aart
--

CREATE SEQUENCE assessment_id_seq1
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.assessment_id_seq1 OWNER TO aart;

--
-- TOC entry 3022 (class 0 OID 0)
-- Dependencies: 198
-- Name: assessment_id_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: aart
--

ALTER SEQUENCE assessment_id_seq1 OWNED BY assessment.id;


--
-- TOC entry 202 (class 1259 OID 30069)
-- Dependencies: 5
-- Name: assessmentprogram; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE assessmentprogram (
    id bigint NOT NULL,
    programname character varying(75) NOT NULL
);


ALTER TABLE public.assessmentprogram OWNER TO aart;

--
-- TOC entry 201 (class 1259 OID 30067)
-- Dependencies: 5 202
-- Name: assessmentprogram_id_seq; Type: SEQUENCE; Schema: public; Owner: aart
--

CREATE SEQUENCE assessmentprogram_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.assessmentprogram_id_seq OWNER TO aart;

--
-- TOC entry 3025 (class 0 OID 0)
-- Dependencies: 201
-- Name: assessmentprogram_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: aart
--

ALTER SEQUENCE assessmentprogram_id_seq OWNED BY assessmentprogram.id;


--
-- TOC entry 172 (class 1259 OID 29695)
-- Dependencies: 5
-- Name: authorities; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE authorities (
    id bigint NOT NULL,
    authority character varying(32) NOT NULL,
    displayname character varying(50),
    objecttype character varying(25)
);


ALTER TABLE public.authorities OWNER TO aart;

--
-- TOC entry 171 (class 1259 OID 29693)
-- Dependencies: 172 5
-- Name: authorities_id_seq; Type: SEQUENCE; Schema: public; Owner: aart
--

CREATE SEQUENCE authorities_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.authorities_id_seq OWNER TO aart;

--
-- TOC entry 3028 (class 0 OID 0)
-- Dependencies: 171
-- Name: authorities_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: aart
--

ALTER SEQUENCE authorities_id_seq OWNED BY authorities.id;


--
-- TOC entry 191 (class 1259 OID 29880)
-- Dependencies: 5
-- Name: category; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE category (
    id bigint NOT NULL,
    categoryname character varying(75) NOT NULL,
    categorycode character varying(75) NOT NULL,
    categorydescription character varying(75),
    categorytypeid bigint NOT NULL
);


ALTER TABLE public.category OWNER TO aart;

--
-- TOC entry 190 (class 1259 OID 29878)
-- Dependencies: 191 5
-- Name: category_id_seq; Type: SEQUENCE; Schema: public; Owner: aart
--

CREATE SEQUENCE category_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.category_id_seq OWNER TO aart;

--
-- TOC entry 3031 (class 0 OID 0)
-- Dependencies: 190
-- Name: category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: aart
--

ALTER SEQUENCE category_id_seq OWNED BY category.id;


--
-- TOC entry 189 (class 1259 OID 29870)
-- Dependencies: 5
-- Name: categorytype; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE categorytype (
    id bigint NOT NULL,
    typename character varying(75) NOT NULL,
    typecode character varying(75) NOT NULL,
    typedescription character varying(75)
);


ALTER TABLE public.categorytype OWNER TO aart;

--
-- TOC entry 188 (class 1259 OID 29868)
-- Dependencies: 5 189
-- Name: categorytype_id_seq; Type: SEQUENCE; Schema: public; Owner: aart
--

CREATE SEQUENCE categorytype_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.categorytype_id_seq OWNER TO aart;

--
-- TOC entry 3034 (class 0 OID 0)
-- Dependencies: 188
-- Name: categorytype_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: aart
--

ALTER SEQUENCE categorytype_id_seq OWNED BY categorytype.id;

--
-- TOC entry 174 (class 1259 OID 29708)
-- Dependencies: 2874 2875 2876 2877 2878 5
-- Name: enrollment; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE enrollment (
    id bigint NOT NULL,
    aypschoolidentifier character varying(60),
    residencedistrictidentifier character varying(60),
    currentgradelevel integer,
    localstudentidentifier character varying(60),
    currentschoolyear integer,
    fundingschool character varying(60),
    schoolentrydate date DEFAULT now(),
    districtentrydate date DEFAULT now(),
    stateentrydate date DEFAULT now(),
    exitwithdrawaldate date DEFAULT now(),
    exitwithdrawaltype integer,
    specialcircumstancestransferchoice character varying(60),
    giftedstudent boolean,
    specialedprogramendingdate date DEFAULT now(),
    qualifiedfor504 character varying(60),
    studentid bigint NOT NULL,
    attendanceschoolid bigint NOT NULL
);


ALTER TABLE public.enrollment OWNER TO aart;

--
-- TOC entry 3037 (class 0 OID 0)
-- Dependencies: 174
-- Name: COLUMN enrollment.aypschoolidentifier; Type: COMMENT; Schema: public; Owner: aart
--

COMMENT ON COLUMN enrollment.aypschoolidentifier IS 'expected to be the same as display identifier of the school responsible for ayp i.e. annual yearly progress.';


--
-- TOC entry 173 (class 1259 OID 29706)
-- Dependencies: 5 174
-- Name: enrollment_id_seq; Type: SEQUENCE; Schema: public; Owner: aart
--

CREATE SEQUENCE enrollment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.enrollment_id_seq OWNER TO aart;

--
-- TOC entry 3039 (class 0 OID 0)
-- Dependencies: 173
-- Name: enrollment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: aart
--

ALTER SEQUENCE enrollment_id_seq OWNED BY enrollment.id;


--
-- TOC entry 197 (class 1259 OID 29966)
-- Dependencies: 5
-- Name: enrollmentsrosters; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE enrollmentsrosters (
    enrollmentid bigint NOT NULL,
    rosterid bigint NOT NULL
);


ALTER TABLE public.enrollmentsrosters OWNER TO aart;

--
-- TOC entry 195 (class 1259 OID 29930)
-- Dependencies: 2891 2892 2893 5
-- Name: fieldspecification; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE fieldspecification (
    id bigint NOT NULL,
    fieldname character varying(75),
    allowablevalues character varying(75)[],
    minimum bigint,
    maximum bigint,
    fieldlength integer,
    rejectifempty boolean DEFAULT true NOT NULL,
    rejectifinvalid boolean DEFAULT true NOT NULL,
    formatregex character varying(150),
    mappedname character varying(75),
    showerror boolean DEFAULT true NOT NULL
);


ALTER TABLE public.fieldspecification OWNER TO aart;

--
-- TOC entry 194 (class 1259 OID 29928)
-- Dependencies: 195 5
-- Name: fieldspecification_id_seq; Type: SEQUENCE; Schema: public; Owner: aart
--

CREATE SEQUENCE fieldspecification_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.fieldspecification_id_seq OWNER TO aart;

--
-- TOC entry 3043 (class 0 OID 0)
-- Dependencies: 194
-- Name: fieldspecification_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: aart
--

ALTER SEQUENCE fieldspecification_id_seq OWNED BY fieldspecification.id;


--
-- TOC entry 196 (class 1259 OID 29941)
-- Dependencies: 5
-- Name: fieldspecificationsrecordtypes; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE fieldspecificationsrecordtypes (
    fieldspecificationid bigint NOT NULL,
    recordtypeid bigint NOT NULL
);


ALTER TABLE public.fieldspecificationsrecordtypes OWNER TO aart;

--
-- TOC entry 178 (class 1259 OID 29744)
-- Dependencies: 5
-- Name: groupauthorities; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE groupauthorities (
    id bigint NOT NULL,
    groupid bigint NOT NULL,
    authorityid bigint NOT NULL
);


ALTER TABLE public.groupauthorities OWNER TO aart;

--
-- TOC entry 177 (class 1259 OID 29742)
-- Dependencies: 178 5
-- Name: groupauthorities_id_seq; Type: SEQUENCE; Schema: public; Owner: aart
--

CREATE SEQUENCE groupauthorities_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.groupauthorities_id_seq OWNER TO aart;

--
-- TOC entry 3047 (class 0 OID 0)
-- Dependencies: 177
-- Name: groupauthorities_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: aart
--

ALTER SEQUENCE groupauthorities_id_seq OWNED BY groupauthorities.id;


--
-- TOC entry 176 (class 1259 OID 29731)
-- Dependencies: 2880 5
-- Name: groups; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE groups (
    id bigint NOT NULL,
    organizationid bigint NOT NULL,
    groupname character varying(50) NOT NULL,
    defaultrole boolean DEFAULT false
);


ALTER TABLE public.groups OWNER TO aart;

--
-- TOC entry 175 (class 1259 OID 29729)
-- Dependencies: 176 5
-- Name: groups_id_seq; Type: SEQUENCE; Schema: public; Owner: aart
--

CREATE SEQUENCE groups_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.groups_id_seq OWNER TO aart;

--
-- TOC entry 3050 (class 0 OID 0)
-- Dependencies: 175
-- Name: groups_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: aart
--

ALTER SEQUENCE groups_id_seq OWNED BY groups.id;


--
-- TOC entry 163 (class 1259 OID 29637)
-- Dependencies: 5
-- Name: organization; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE organization (
    id bigint NOT NULL,
    organizationname character varying(75),
    displayidentifier character varying(75) NOT NULL,
    organizationtypeid bigint NOT NULL,
    welcomemessage character varying(75)
);


ALTER TABLE public.organization OWNER TO aart;

--
-- TOC entry 162 (class 1259 OID 29635)
-- Dependencies: 163 5
-- Name: organization_id_seq; Type: SEQUENCE; Schema: public; Owner: aart
--

CREATE SEQUENCE organization_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.organization_id_seq OWNER TO aart;

--
-- TOC entry 3053 (class 0 OID 0)
-- Dependencies: 162
-- Name: organization_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: aart
--

ALTER SEQUENCE organization_id_seq OWNED BY organization.id;


--
-- TOC entry 185 (class 1259 OID 29836)
-- Dependencies: 5
-- Name: organizationrelation; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE organizationrelation (
    organizationid bigint NOT NULL,
    parentorganizationid bigint NOT NULL
);


ALTER TABLE public.organizationrelation OWNER TO aart;

--
-- TOC entry 187 (class 1259 OID 29841)
-- Dependencies: 5
-- Name: organizationtype; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE organizationtype (
    id bigint NOT NULL,
    typename character varying(75),
    typecode character varying(75) NOT NULL,
    typelevel integer NOT NULL
);


ALTER TABLE public.organizationtype OWNER TO aart;

--
-- TOC entry 184 (class 1259 OID 29834)
-- Dependencies: 5
-- Name: organizationtype_id_seq; Type: SEQUENCE; Schema: public; Owner: aart
--

CREATE SEQUENCE organizationtype_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.organizationtype_id_seq OWNER TO aart;

--
-- TOC entry 186 (class 1259 OID 29839)
-- Dependencies: 187 5
-- Name: organizationtype_id_seq1; Type: SEQUENCE; Schema: public; Owner: aart
--

CREATE SEQUENCE organizationtype_id_seq1
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.organizationtype_id_seq1 OWNER TO aart;

--
-- TOC entry 3058 (class 0 OID 0)
-- Dependencies: 186
-- Name: organizationtype_id_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: aart
--

ALTER SEQUENCE organizationtype_id_seq1 OWNED BY organizationtype.id;


--
-- TOC entry 204 (class 1259 OID 30079)
-- Dependencies: 5
-- Name: orgassessmentprogram; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE orgassessmentprogram (
    id bigint NOT NULL,
    organizationid bigint,
    assessmentprogramid bigint
);


ALTER TABLE public.orgassessmentprogram OWNER TO aart;

--
-- TOC entry 203 (class 1259 OID 30077)
-- Dependencies: 5 204
-- Name: orgassessmentprogram_id_seq; Type: SEQUENCE; Schema: public; Owner: aart
--

CREATE SEQUENCE orgassessmentprogram_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.orgassessmentprogram_id_seq OWNER TO aart;

--
-- TOC entry 3061 (class 0 OID 0)
-- Dependencies: 203
-- Name: orgassessmentprogram_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: aart
--

ALTER SEQUENCE orgassessmentprogram_id_seq OWNED BY orgassessmentprogram.id;


--
-- TOC entry 165 (class 1259 OID 29650)
-- Dependencies: 5
-- Name: policy; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE policy (
    id bigint NOT NULL,
    organizationid bigint,
    name character varying(75),
    data text
);


ALTER TABLE public.policy OWNER TO aart;

--
-- TOC entry 164 (class 1259 OID 29648)
-- Dependencies: 165 5
-- Name: policy_id_seq; Type: SEQUENCE; Schema: public; Owner: aart
--

CREATE SEQUENCE policy_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.policy_id_seq OWNER TO aart;

--
-- TOC entry 3064 (class 0 OID 0)
-- Dependencies: 164
-- Name: policy_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: aart
--

ALTER SEQUENCE policy_id_seq OWNED BY policy.id;


--
-- TOC entry 193 (class 1259 OID 29895)
-- Dependencies: 5
-- Name: roster; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE roster (
    id bigint NOT NULL,
    coursesectionname character varying(75) NOT NULL,
    coursesectiondescription character varying(75),
    teacherid bigint NOT NULL,
    statesubjectareaid bigint,
    courseenrollmentstatusid bigint,
    statecourseid bigint NOT NULL
);


ALTER TABLE public.roster OWNER TO aart;

--
-- TOC entry 192 (class 1259 OID 29893)
-- Dependencies: 193 5
-- Name: roster_id_seq; Type: SEQUENCE; Schema: public; Owner: aart
--

CREATE SEQUENCE roster_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.roster_id_seq OWNER TO aart;

--
-- TOC entry 3067 (class 0 OID 0)
-- Dependencies: 192
-- Name: roster_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: aart
--

ALTER SEQUENCE roster_id_seq OWNED BY roster.id;


--
-- TOC entry 182 (class 1259 OID 29780)
-- Dependencies: 5
-- Name: student_id_seq; Type: SEQUENCE; Schema: public; Owner: aart
--

CREATE SEQUENCE student_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.student_id_seq OWNER TO aart;

--
-- TOC entry 183 (class 1259 OID 29786)
-- Dependencies: 2883 2884 2885 5
-- Name: student; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE student (
    id bigint DEFAULT nextval('student_id_seq'::regclass) NOT NULL,
    statestudentidentifier bigint NOT NULL,
    legalfirstname character varying(80),
    legalmiddlename character varying(80),
    legallastname character varying(80),
    generationcode character varying(10),
    dateofbirth date,
    createddate timestamp with time zone DEFAULT now() NOT NULL,
    modifieddate timestamp with time zone DEFAULT now() NOT NULL,
    gender integer,
    firstlanguage character varying(2),
    comprehensiverace character(5),
    primarydisabilitycode character varying(60),
    username character varying(20),
    password character varying(15)
);


ALTER TABLE public.student OWNER TO aart;

--
-- TOC entry 200 (class 1259 OID 30041)
-- Dependencies: 5
-- Name: studentsassessments; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE studentsassessments (
    studentid bigint NOT NULL,
    assessmentid bigint NOT NULL
);


ALTER TABLE public.studentsassessments OWNER TO aart;

--
-- TOC entry 210 (class 1259 OID 30146)
-- Dependencies: 5
-- Name: studentstests; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE studentstests (
    id bigint NOT NULL,
    studentid bigint,
    testid bigint
);


ALTER TABLE public.studentstests OWNER TO aart;

--
-- TOC entry 209 (class 1259 OID 30144)
-- Dependencies: 210 5
-- Name: studentstests_id_seq; Type: SEQUENCE; Schema: public; Owner: aart
--

CREATE SEQUENCE studentstests_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.studentstests_id_seq OWNER TO aart;

--
-- TOC entry 3073 (class 0 OID 0)
-- Dependencies: 209
-- Name: studentstests_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: aart
--

ALTER SEQUENCE studentstests_id_seq OWNED BY studentstests.id;


--
-- TOC entry 206 (class 1259 OID 30102)
-- Dependencies: 5
-- Name: test; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE test (
    id bigint NOT NULL,
    assessmentid bigint,
    subjectid bigint,
    gradeid bigint,
    testname character varying(75),
    numitems integer
);


ALTER TABLE public.test OWNER TO aart;

--
-- TOC entry 205 (class 1259 OID 30100)
-- Dependencies: 5 206
-- Name: test_id_seq; Type: SEQUENCE; Schema: public; Owner: aart
--

CREATE SEQUENCE test_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.test_id_seq OWNER TO aart;

--
-- TOC entry 3076 (class 0 OID 0)
-- Dependencies: 205
-- Name: test_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: aart
--

ALTER SEQUENCE test_id_seq OWNED BY test.id;


--
-- TOC entry 208 (class 1259 OID 30125)
-- Dependencies: 5
-- Name: testform; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE testform (
    id bigint NOT NULL,
    testid bigint,
    formid character varying(10) NOT NULL,
    formname character varying(100) NOT NULL,
    numitems integer
);


ALTER TABLE public.testform OWNER TO aart;

--
-- TOC entry 207 (class 1259 OID 30123)
-- Dependencies: 208 5
-- Name: testform_id_seq; Type: SEQUENCE; Schema: public; Owner: aart
--

CREATE SEQUENCE testform_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.testform_id_seq OWNER TO aart;

--
-- TOC entry 3079 (class 0 OID 0)
-- Dependencies: 207
-- Name: testform_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: aart
--

ALTER SEQUENCE testform_id_seq OWNED BY testform.id;


--
-- TOC entry 170 (class 1259 OID 29681)
-- Dependencies: 5
-- Name: useraudit; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE useraudit (
    id bigint NOT NULL,
    userid bigint NOT NULL,
    useros character varying(256),
    logintime timestamp with time zone NOT NULL,
    logouttime timestamp with time zone
);


ALTER TABLE public.useraudit OWNER TO aart;

--
-- TOC entry 168 (class 1259 OID 29677)
-- Dependencies: 170 5
-- Name: useraudit_id_seq; Type: SEQUENCE; Schema: public; Owner: aart
--

CREATE SEQUENCE useraudit_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.useraudit_id_seq OWNER TO aart;

--
-- TOC entry 3082 (class 0 OID 0)
-- Dependencies: 168
-- Name: useraudit_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: aart
--

ALTER SEQUENCE useraudit_id_seq OWNED BY useraudit.id;


--
-- TOC entry 169 (class 1259 OID 29679)
-- Dependencies: 5 170
-- Name: useraudit_userid_seq; Type: SEQUENCE; Schema: public; Owner: aart
--

CREATE SEQUENCE useraudit_userid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.useraudit_userid_seq OWNER TO aart;

--
-- TOC entry 3084 (class 0 OID 0)
-- Dependencies: 169
-- Name: useraudit_userid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: aart
--

ALTER SEQUENCE useraudit_userid_seq OWNED BY useraudit.userid;


--
-- TOC entry 180 (class 1259 OID 29762)
-- Dependencies: 5
-- Name: usergroups; Type: TABLE; Schema: public; Owner: aart; Tablespace: 
--

CREATE TABLE usergroups (
    id bigint NOT NULL,
    aartuserid bigint NOT NULL,
    groupid bigint NOT NULL,
    status integer,
    activationno character varying(75),
    activationnoexpirationdate timestamp without time zone
);


ALTER TABLE public.usergroups OWNER TO aart;

--
-- TOC entry 179 (class 1259 OID 29760)
-- Dependencies: 5 180
-- Name: usergroups_id_seq; Type: SEQUENCE; Schema: public; Owner: aart
--

CREATE SEQUENCE usergroups_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usergroups_id_seq OWNER TO aart;

--
-- TOC entry 3087 (class 0 OID 0)
-- Dependencies: 179
-- Name: usergroups_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: aart
--

ALTER SEQUENCE usergroups_id_seq OWNED BY usergroups.id;


--
-- TOC entry 2869 (class 2604 OID 29669)
-- Dependencies: 167 166 167
-- Name: id; Type: DEFAULT; Schema: public; Owner: aart
--

ALTER TABLE ONLY aartuser ALTER COLUMN id SET DEFAULT nextval('aartuser_id_seq'::regclass);


--
-- TOC entry 2894 (class 2604 OID 30026)
-- Dependencies: 198 199 199
-- Name: id; Type: DEFAULT; Schema: public; Owner: aart
--

ALTER TABLE ONLY assessment ALTER COLUMN id SET DEFAULT nextval('assessment_id_seq1'::regclass);


--
-- TOC entry 2895 (class 2604 OID 30072)
-- Dependencies: 201 202 202
-- Name: id; Type: DEFAULT; Schema: public; Owner: aart
--

ALTER TABLE ONLY assessmentprogram ALTER COLUMN id SET DEFAULT nextval('assessmentprogram_id_seq'::regclass);


--
-- TOC entry 2872 (class 2604 OID 29698)
-- Dependencies: 172 171 172
-- Name: id; Type: DEFAULT; Schema: public; Owner: aart
--

ALTER TABLE ONLY authorities ALTER COLUMN id SET DEFAULT nextval('authorities_id_seq'::regclass);


--
-- TOC entry 2888 (class 2604 OID 29883)
-- Dependencies: 190 191 191
-- Name: id; Type: DEFAULT; Schema: public; Owner: aart
--

ALTER TABLE ONLY category ALTER COLUMN id SET DEFAULT nextval('category_id_seq'::regclass);


--
-- TOC entry 2887 (class 2604 OID 29873)
-- Dependencies: 188 189 189
-- Name: id; Type: DEFAULT; Schema: public; Owner: aart
--

ALTER TABLE ONLY categorytype ALTER COLUMN id SET DEFAULT nextval('categorytype_id_seq'::regclass);


--
-- TOC entry 2873 (class 2604 OID 29711)
-- Dependencies: 174 173 174
-- Name: id; Type: DEFAULT; Schema: public; Owner: aart
--

ALTER TABLE ONLY enrollment ALTER COLUMN id SET DEFAULT nextval('enrollment_id_seq'::regclass);


--
-- TOC entry 2890 (class 2604 OID 29933)
-- Dependencies: 194 195 195
-- Name: id; Type: DEFAULT; Schema: public; Owner: aart
--

ALTER TABLE ONLY fieldspecification ALTER COLUMN id SET DEFAULT nextval('fieldspecification_id_seq'::regclass);


--
-- TOC entry 2881 (class 2604 OID 29747)
-- Dependencies: 178 177 178
-- Name: id; Type: DEFAULT; Schema: public; Owner: aart
--

ALTER TABLE ONLY groupauthorities ALTER COLUMN id SET DEFAULT nextval('groupauthorities_id_seq'::regclass);


--
-- TOC entry 2879 (class 2604 OID 29734)
-- Dependencies: 175 176 176
-- Name: id; Type: DEFAULT; Schema: public; Owner: aart
--

ALTER TABLE ONLY groups ALTER COLUMN id SET DEFAULT nextval('groups_id_seq'::regclass);


--
-- TOC entry 2867 (class 2604 OID 29640)
-- Dependencies: 163 162 163
-- Name: id; Type: DEFAULT; Schema: public; Owner: aart
--

ALTER TABLE ONLY organization ALTER COLUMN id SET DEFAULT nextval('organization_id_seq'::regclass);


--
-- TOC entry 2886 (class 2604 OID 29844)
-- Dependencies: 186 187 187
-- Name: id; Type: DEFAULT; Schema: public; Owner: aart
--

ALTER TABLE ONLY organizationtype ALTER COLUMN id SET DEFAULT nextval('organizationtype_id_seq1'::regclass);


--
-- TOC entry 2896 (class 2604 OID 30082)
-- Dependencies: 203 204 204
-- Name: id; Type: DEFAULT; Schema: public; Owner: aart
--

ALTER TABLE ONLY orgassessmentprogram ALTER COLUMN id SET DEFAULT nextval('orgassessmentprogram_id_seq'::regclass);


--
-- TOC entry 2868 (class 2604 OID 29653)
-- Dependencies: 164 165 165
-- Name: id; Type: DEFAULT; Schema: public; Owner: aart
--

ALTER TABLE ONLY policy ALTER COLUMN id SET DEFAULT nextval('policy_id_seq'::regclass);


--
-- TOC entry 2889 (class 2604 OID 29898)
-- Dependencies: 193 192 193
-- Name: id; Type: DEFAULT; Schema: public; Owner: aart
--

ALTER TABLE ONLY roster ALTER COLUMN id SET DEFAULT nextval('roster_id_seq'::regclass);


--
-- TOC entry 2899 (class 2604 OID 30149)
-- Dependencies: 209 210 210
-- Name: id; Type: DEFAULT; Schema: public; Owner: aart
--

ALTER TABLE ONLY studentstests ALTER COLUMN id SET DEFAULT nextval('studentstests_id_seq'::regclass);


--
-- TOC entry 2897 (class 2604 OID 30105)
-- Dependencies: 206 205 206
-- Name: id; Type: DEFAULT; Schema: public; Owner: aart
--

ALTER TABLE ONLY test ALTER COLUMN id SET DEFAULT nextval('test_id_seq'::regclass);


--
-- TOC entry 2898 (class 2604 OID 30128)
-- Dependencies: 208 207 208
-- Name: id; Type: DEFAULT; Schema: public; Owner: aart
--

ALTER TABLE ONLY testform ALTER COLUMN id SET DEFAULT nextval('testform_id_seq'::regclass);


--
-- TOC entry 2870 (class 2604 OID 29684)
-- Dependencies: 168 170 170
-- Name: id; Type: DEFAULT; Schema: public; Owner: aart
--

ALTER TABLE ONLY useraudit ALTER COLUMN id SET DEFAULT nextval('useraudit_id_seq'::regclass);


--
-- TOC entry 2871 (class 2604 OID 29685)
-- Dependencies: 169 170 170
-- Name: userid; Type: DEFAULT; Schema: public; Owner: aart
--

ALTER TABLE ONLY useraudit ALTER COLUMN userid SET DEFAULT nextval('useraudit_userid_seq'::regclass);


--
-- TOC entry 2882 (class 2604 OID 29765)
-- Dependencies: 180 179 180
-- Name: id; Type: DEFAULT; Schema: public; Owner: aart
--

ALTER TABLE ONLY usergroups ALTER COLUMN id SET DEFAULT nextval('usergroups_id_seq'::regclass);


--
-- TOC entry 2907 (class 2606 OID 29672)
-- Dependencies: 167 167
-- Name: aartuser_pkey; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY aartuser
    ADD CONSTRAINT aartuser_pkey PRIMARY KEY (id);


--
-- TOC entry 2959 (class 2606 OID 30028)
-- Dependencies: 199 199
-- Name: assessment_pkey; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY assessment
    ADD CONSTRAINT assessment_pkey PRIMARY KEY (id);


--
-- TOC entry 2963 (class 2606 OID 30074)
-- Dependencies: 202 202
-- Name: assessmentprogram_pkey; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY assessmentprogram
    ADD CONSTRAINT assessmentprogram_pkey PRIMARY KEY (id);


--
-- TOC entry 2965 (class 2606 OID 30076)
-- Dependencies: 202 202
-- Name: assessmentprogram_programname_key; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY assessmentprogram
    ADD CONSTRAINT assessmentprogram_programname_key UNIQUE (programname);


--
-- TOC entry 2917 (class 2606 OID 29728)
-- Dependencies: 172 172
-- Name: authorities_pkey; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY authorities
    ADD CONSTRAINT authorities_pkey PRIMARY KEY (id);


--
-- TOC entry 2943 (class 2606 OID 30178)
-- Dependencies: 191 191 191
-- Name: category_code_uk; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY category
    ADD CONSTRAINT category_code_uk UNIQUE (categorycode, categorytypeid);


--
-- TOC entry 2945 (class 2606 OID 30176)
-- Dependencies: 191 191 191
-- Name: category_name_uk; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY category
    ADD CONSTRAINT category_name_uk UNIQUE (categoryname, categorytypeid);


--
-- TOC entry 2947 (class 2606 OID 29885)
-- Dependencies: 191 191
-- Name: category_pk; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY category
    ADD CONSTRAINT category_pk PRIMARY KEY (id);


--
-- TOC entry 2939 (class 2606 OID 29875)
-- Dependencies: 189 189
-- Name: category_type_pk; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY categorytype
    ADD CONSTRAINT category_type_pk PRIMARY KEY (id);


--
-- TOC entry 2919 (class 2606 OID 29722)
-- Dependencies: 174 174
-- Name: enrollment_pkey; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY enrollment
    ADD CONSTRAINT enrollment_pkey PRIMARY KEY (id);


--
-- TOC entry 2957 (class 2606 OID 29970)
-- Dependencies: 197 197 197
-- Name: enrollment_roster_pk; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY enrollmentsrosters
    ADD CONSTRAINT enrollment_roster_pk PRIMARY KEY (enrollmentid, rosterid);


--
-- TOC entry 2921 (class 2606 OID 29972)
-- Dependencies: 174 174 174 174 174
-- Name: enrollment_uk; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY enrollment
    ADD CONSTRAINT enrollment_uk UNIQUE (studentid, attendanceschoolid, aypschoolidentifier, currentschoolyear);


--
-- TOC entry 2951 (class 2606 OID 29940)
-- Dependencies: 195 195
-- Name: field_specification_pk; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY fieldspecification
    ADD CONSTRAINT field_specification_pk PRIMARY KEY (id);


--
-- TOC entry 2955 (class 2606 OID 29945)
-- Dependencies: 196 196 196
-- Name: field_specifications_record_types_pkey; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY fieldspecificationsrecordtypes
    ADD CONSTRAINT field_specifications_record_types_pkey PRIMARY KEY (fieldspecificationid, recordtypeid);


--
-- TOC entry 2925 (class 2606 OID 29749)
-- Dependencies: 178 178
-- Name: groupauthorities_pkey; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY groupauthorities
    ADD CONSTRAINT groupauthorities_pkey PRIMARY KEY (id);


--
-- TOC entry 2923 (class 2606 OID 29736)
-- Dependencies: 176 176
-- Name: groups_pkey; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (id);


--
-- TOC entry 2909 (class 2606 OID 30196)
-- Dependencies: 167 167 167 167 167
-- Name: login; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY aartuser
    ADD CONSTRAINT login UNIQUE (firstname, surname, username, password);


--
-- TOC entry 2903 (class 2606 OID 29642)
-- Dependencies: 163 163
-- Name: organization_pkey; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY organization
    ADD CONSTRAINT organization_pkey PRIMARY KEY (id);


--
-- TOC entry 2933 (class 2606 OID 29853)
-- Dependencies: 185 185 185
-- Name: organizationrelation_pk; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY organizationrelation
    ADD CONSTRAINT organizationrelation_pk PRIMARY KEY (organizationid, parentorganizationid);


--
-- TOC entry 2967 (class 2606 OID 30084)
-- Dependencies: 204 204
-- Name: orgassessmentprogram_pkey; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY orgassessmentprogram
    ADD CONSTRAINT orgassessmentprogram_pkey PRIMARY KEY (id);


--
-- TOC entry 2901 (class 2606 OID 30004)
-- Dependencies: 161 161
-- Name: pk_ddl_version; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY ddl_version
    ADD CONSTRAINT pk_ddl_version PRIMARY KEY (version);


--
-- TOC entry 2935 (class 2606 OID 29855)
-- Dependencies: 187 187
-- Name: pk_organization_type; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY organizationtype
    ADD CONSTRAINT pk_organization_type PRIMARY KEY (id);


--
-- TOC entry 2929 (class 2606 OID 29817)
-- Dependencies: 183 183
-- Name: pk_student; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY student
    ADD CONSTRAINT pk_student PRIMARY KEY (id);


--
-- TOC entry 2961 (class 2606 OID 30045)
-- Dependencies: 200 200 200
-- Name: pk_student_assessment; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY studentsassessments
    ADD CONSTRAINT pk_student_assessment PRIMARY KEY (studentid, assessmentid);


--
-- TOC entry 2905 (class 2606 OID 29658)
-- Dependencies: 165 165
-- Name: policy_pkey; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY policy
    ADD CONSTRAINT policy_pkey PRIMARY KEY (id);


--
-- TOC entry 2949 (class 2606 OID 29900)
-- Dependencies: 193 193
-- Name: roster_pk; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY roster
    ADD CONSTRAINT roster_pk PRIMARY KEY (id);


--
-- TOC entry 2973 (class 2606 OID 30151)
-- Dependencies: 210 210
-- Name: studentstests_pkey; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY studentstests
    ADD CONSTRAINT studentstests_pkey PRIMARY KEY (id);


--
-- TOC entry 2975 (class 2606 OID 30153)
-- Dependencies: 210 210 210
-- Name: studentstests_studentid_testid_key; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY studentstests
    ADD CONSTRAINT studentstests_studentid_testid_key UNIQUE (studentid, testid);


--
-- TOC entry 2969 (class 2606 OID 30107)
-- Dependencies: 206 206
-- Name: test_pkey; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY test
    ADD CONSTRAINT test_pkey PRIMARY KEY (id);


--
-- TOC entry 2971 (class 2606 OID 30130)
-- Dependencies: 208 208
-- Name: testform_pkey; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY testform
    ADD CONSTRAINT testform_pkey PRIMARY KEY (id);


--
-- TOC entry 2941 (class 2606 OID 29877)
-- Dependencies: 189 189
-- Name: uk_category_type_code; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY categorytype
    ADD CONSTRAINT uk_category_type_code UNIQUE (typecode);


--
-- TOC entry 2911 (class 2606 OID 29989)
-- Dependencies: 167 167
-- Name: uk_email; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY aartuser
    ADD CONSTRAINT uk_email UNIQUE (email);


--
-- TOC entry 2953 (class 2606 OID 30066)
-- Dependencies: 195 195 195
-- Name: uk_field_spec; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY fieldspecification
    ADD CONSTRAINT uk_field_spec UNIQUE (fieldname, mappedname);


--
-- TOC entry 2931 (class 2606 OID 30180)
-- Dependencies: 183 183
-- Name: uk_state_student_identifier; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY student
    ADD CONSTRAINT uk_state_student_identifier UNIQUE (statestudentidentifier);


--
-- TOC entry 2937 (class 2606 OID 29867)
-- Dependencies: 187 187
-- Name: uk_type_code; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY organizationtype
    ADD CONSTRAINT uk_type_code UNIQUE (typecode);


--
-- TOC entry 2915 (class 2606 OID 29687)
-- Dependencies: 170 170
-- Name: useraudit_pkey; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY useraudit
    ADD CONSTRAINT useraudit_pkey PRIMARY KEY (id);


--
-- TOC entry 2927 (class 2606 OID 29767)
-- Dependencies: 180 180
-- Name: usergroups_pkey; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY usergroups
    ADD CONSTRAINT usergroups_pkey PRIMARY KEY (id);


--
-- TOC entry 2913 (class 2606 OID 29993)
-- Dependencies: 167 167
-- Name: username; Type: CONSTRAINT; Schema: public; Owner: aart; Tablespace: 
--

ALTER TABLE ONLY aartuser
    ADD CONSTRAINT username UNIQUE (username);


--
-- TOC entry 3000 (class 2606 OID 30046)
-- Dependencies: 200 2958 199
-- Name: assessment_fk; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY studentsassessments
    ADD CONSTRAINT assessment_fk FOREIGN KEY (assessmentid) REFERENCES assessment(id);


--
-- TOC entry 2999 (class 2606 OID 30164)
-- Dependencies: 191 199 2946
-- Name: assessment_testsubjectid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY assessment
    ADD CONSTRAINT assessment_testsubjectid_fkey FOREIGN KEY (testsubjectid) REFERENCES category(id);


--
-- TOC entry 2979 (class 2606 OID 29973)
-- Dependencies: 2902 174 163
-- Name: attendance_school_fk; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY enrollment
    ADD CONSTRAINT attendance_school_fk FOREIGN KEY (attendanceschoolid) REFERENCES organization(id);


--
-- TOC entry 2988 (class 2606 OID 29888)
-- Dependencies: 191 2938 189
-- Name: category_type_fk; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY category
    ADD CONSTRAINT category_type_fk FOREIGN KEY (categorytypeid) REFERENCES categorytype(id);


--
-- TOC entry 2989 (class 2606 OID 29901)
-- Dependencies: 191 2946 193
-- Name: course_enrollment_status_fk; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY roster
    ADD CONSTRAINT course_enrollment_status_fk FOREIGN KEY (courseenrollmentstatusid) REFERENCES category(id);


--
-- TOC entry 2980 (class 2606 OID 30170)
-- Dependencies: 191 2946 174
-- Name: current_grade_level_fk; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY enrollment
    ADD CONSTRAINT current_grade_level_fk FOREIGN KEY (currentgradelevel) REFERENCES category(id);


--
-- TOC entry 2995 (class 2606 OID 29978)
-- Dependencies: 197 2918 174
-- Name: enrollment_fk; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY enrollmentsrosters
    ADD CONSTRAINT enrollment_fk FOREIGN KEY (enrollmentid) REFERENCES enrollment(id);


--
-- TOC entry 2993 (class 2606 OID 29946)
-- Dependencies: 2950 196 195
-- Name: field_specifications_record_types_field_specification_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY fieldspecificationsrecordtypes
    ADD CONSTRAINT field_specifications_record_types_field_specification_id_fkey FOREIGN KEY (fieldspecificationid) REFERENCES fieldspecification(id);


--
-- TOC entry 2994 (class 2606 OID 29951)
-- Dependencies: 191 196 2946
-- Name: field_specifications_record_types_record_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY fieldspecificationsrecordtypes
    ADD CONSTRAINT field_specifications_record_types_record_type_id_fkey FOREIGN KEY (recordtypeid) REFERENCES category(id);


--
-- TOC entry 2998 (class 2606 OID 30095)
-- Dependencies: 199 202 2962
-- Name: fk_assessment_assessment_program; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY assessment
    ADD CONSTRAINT fk_assessment_assessment_program FOREIGN KEY (assessmentprogramid) REFERENCES assessmentprogram(id);


--
-- TOC entry 2983 (class 2606 OID 29755)
-- Dependencies: 178 172 2916
-- Name: fk_group_authorities_authority; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY groupauthorities
    ADD CONSTRAINT fk_group_authorities_authority FOREIGN KEY (authorityid) REFERENCES authorities(id);


--
-- TOC entry 2982 (class 2606 OID 29750)
-- Dependencies: 2922 178 176
-- Name: fk_group_authorities_group; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY groupauthorities
    ADD CONSTRAINT fk_group_authorities_group FOREIGN KEY (groupid) REFERENCES groups(id);


--
-- TOC entry 2984 (class 2606 OID 29768)
-- Dependencies: 176 180 2922
-- Name: fk_group_members_group; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY usergroups
    ADD CONSTRAINT fk_group_members_group FOREIGN KEY (groupid) REFERENCES groups(id);


--
-- TOC entry 2985 (class 2606 OID 29773)
-- Dependencies: 2906 180 167
-- Name: fk_group_members_user; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY usergroups
    ADD CONSTRAINT fk_group_members_user FOREIGN KEY (aartuserid) REFERENCES aartuser(id);


--
-- TOC entry 2981 (class 2606 OID 29737)
-- Dependencies: 176 163 2902
-- Name: fk_groups_organization; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT fk_groups_organization FOREIGN KEY (organizationid) REFERENCES organization(id);


--
-- TOC entry 2986 (class 2606 OID 29856)
-- Dependencies: 185 163 2902
-- Name: organization_fk; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY organizationrelation
    ADD CONSTRAINT organization_fk FOREIGN KEY (organizationid) REFERENCES organization(id);


--
-- TOC entry 3003 (class 2606 OID 30090)
-- Dependencies: 2962 204 202
-- Name: orgassessmentprogram_assessmentprogramid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY orgassessmentprogram
    ADD CONSTRAINT orgassessmentprogram_assessmentprogramid_fkey FOREIGN KEY (assessmentprogramid) REFERENCES assessmentprogram(id);


--
-- TOC entry 3002 (class 2606 OID 30085)
-- Dependencies: 2902 204 163
-- Name: orgassessmentprogram_organizationid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY orgassessmentprogram
    ADD CONSTRAINT orgassessmentprogram_organizationid_fkey FOREIGN KEY (organizationid) REFERENCES organization(id);


--
-- TOC entry 2987 (class 2606 OID 29861)
-- Dependencies: 2902 163 185
-- Name: parent_organization_fk; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY organizationrelation
    ADD CONSTRAINT parent_organization_fk FOREIGN KEY (parentorganizationid) REFERENCES organization(id);


--
-- TOC entry 2976 (class 2606 OID 29659)
-- Dependencies: 163 165 2902
-- Name: policy_organizationid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY policy
    ADD CONSTRAINT policy_organizationid_fkey FOREIGN KEY (organizationid) REFERENCES organization(id);


--
-- TOC entry 2996 (class 2606 OID 29983)
-- Dependencies: 193 2948 197
-- Name: roster_fk; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY enrollmentsrosters
    ADD CONSTRAINT roster_fk FOREIGN KEY (rosterid) REFERENCES roster(id);


--
-- TOC entry 2990 (class 2606 OID 29906)
-- Dependencies: 193 191 2946
-- Name: state_course_id; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY roster
    ADD CONSTRAINT state_course_id FOREIGN KEY (statecourseid) REFERENCES category(id);


--
-- TOC entry 2991 (class 2606 OID 29911)
-- Dependencies: 193 2946 191
-- Name: state_subject_area_fk; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY roster
    ADD CONSTRAINT state_subject_area_fk FOREIGN KEY (statesubjectareaid) REFERENCES category(id);


--
-- TOC entry 2978 (class 2606 OID 29827)
-- Dependencies: 183 174 2928
-- Name: student_fk; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY enrollment
    ADD CONSTRAINT student_fk FOREIGN KEY (studentid) REFERENCES student(id);


--
-- TOC entry 3001 (class 2606 OID 30051)
-- Dependencies: 200 2928 183
-- Name: student_fk; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY studentsassessments
    ADD CONSTRAINT student_fk FOREIGN KEY (studentid) REFERENCES student(id);


--
-- TOC entry 3008 (class 2606 OID 30154)
-- Dependencies: 2928 210 183
-- Name: studentstests_studentid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY studentstests
    ADD CONSTRAINT studentstests_studentid_fkey FOREIGN KEY (studentid) REFERENCES student(id);


--
-- TOC entry 3009 (class 2606 OID 30159)
-- Dependencies: 206 2968 210
-- Name: studentstests_testid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY studentstests
    ADD CONSTRAINT studentstests_testid_fkey FOREIGN KEY (testid) REFERENCES test(id);


--
-- TOC entry 2992 (class 2606 OID 29916)
-- Dependencies: 2906 167 193
-- Name: teacher_fk; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY roster
    ADD CONSTRAINT teacher_fk FOREIGN KEY (teacherid) REFERENCES aartuser(id);


--
-- TOC entry 3004 (class 2606 OID 30108)
-- Dependencies: 199 206 2958
-- Name: test_assessmentid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY test
    ADD CONSTRAINT test_assessmentid_fkey FOREIGN KEY (assessmentid) REFERENCES assessment(id);


--
-- TOC entry 3006 (class 2606 OID 30118)
-- Dependencies: 191 2946 206
-- Name: test_gradeid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY test
    ADD CONSTRAINT test_gradeid_fkey FOREIGN KEY (gradeid) REFERENCES category(id);


--
-- TOC entry 3005 (class 2606 OID 30113)
-- Dependencies: 206 2946 191
-- Name: test_subjectid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY test
    ADD CONSTRAINT test_subjectid_fkey FOREIGN KEY (subjectid) REFERENCES category(id);


--
-- TOC entry 2997 (class 2606 OID 30036)
-- Dependencies: 2946 191 199
-- Name: test_type_fk; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY assessment
    ADD CONSTRAINT test_type_fk FOREIGN KEY (testtypeid) REFERENCES category(id);


--
-- TOC entry 3007 (class 2606 OID 30131)
-- Dependencies: 206 208 2968
-- Name: testform_testid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY testform
    ADD CONSTRAINT testform_testid_fkey FOREIGN KEY (testid) REFERENCES test(id);


--
-- TOC entry 2977 (class 2606 OID 29688)
-- Dependencies: 167 2906 170
-- Name: useraudit_userid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: aart
--

ALTER TABLE ONLY useraudit
    ADD CONSTRAINT useraudit_userid_fkey FOREIGN KEY (userid) REFERENCES aartuser(id);




--
-- TOC entry 224 (class 1255 OID 30169)
-- Dependencies: 5
-- Name: explode_array(anyarray); Type: FUNCTION; Schema: public; Owner: aart
--

CREATE FUNCTION explode_array(in_array anyarray) RETURNS SETOF anyelement
    LANGUAGE sql IMMUTABLE
    AS $_$
    select ($1)[s] from generate_series(1,array_upper($1, 1)) as s;
$_$;


ALTER FUNCTION public.explode_array(in_array anyarray) OWNER TO aart;

--
-- TOC entry 223 (class 1255 OID 29964)
-- Dependencies: 5
-- Name: organization_children(bigint); Type: FUNCTION; Schema: public; Owner: aart
--

CREATE FUNCTION organization_children(parentid bigint) RETURNS TABLE(id bigint, organizationname character varying, displayidentifier character varying, organizationtypeid bigint, welcomemessage character varying)
    LANGUAGE sql
    AS $_$
        WITH RECURSIVE organization_relation(organizationid, parentorganizationid) AS (
          SELECT organizationid, parentorganizationid FROM organizationrelation WHERE parentorganizationid = $1
          UNION all
          SELECT
            organizationrelation.organizationid, organizationrelation.parentorganizationid
          FROM organizationrelation, organization_relation as parentorganization_relation
          WHERE organizationrelation.parentorganizationid = parentorganization_relation.organizationid)
          SELECT org.* FROM organization_relation org_rel,organization org where org.id = org_rel.organizationid;
        $_$;


ALTER FUNCTION public.organization_children(parentid bigint) OWNER TO aart;

--
-- TOC entry 225 (class 1255 OID 29965)
-- Dependencies: 5
-- Name: organization_parent(bigint); Type: FUNCTION; Schema: public; Owner: aart
--

CREATE FUNCTION organization_parent(childid bigint) RETURNS TABLE(id bigint, organizationname character varying, displayidentifier character varying, organizationtypeid bigint, welcomemessage character varying)
    LANGUAGE sql
    AS $_$
        WITH RECURSIVE organization_relation(organizationid, parentorganizationid) AS (
          SELECT organizationid, parentorganizationid FROM organizationrelation WHERE organizationid = $1
          UNION ALL
          SELECT
            organizationrelation.organizationid, organizationrelation.parentorganizationid
          FROM organizationrelation, organization_relation as parentorganization_relation
          WHERE organizationrelation.organizationid = parentorganization_relation.parentorganizationid)
        SELECT org.* FROM organization org where org.id in (select parentorganizationid from organization_relation);
        $_$;


ALTER FUNCTION public.organization_parent(childid bigint) OWNER TO aart;

