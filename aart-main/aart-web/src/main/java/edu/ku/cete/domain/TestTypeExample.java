package edu.ku.cete.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestTypeExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table testtype
     *
     * @mbggenerated Fri Sep 27 18:08:59 CDT 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table testtype
     *
     * @mbggenerated Fri Sep 27 18:08:59 CDT 2013
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table testtype
     *
     * @mbggenerated Fri Sep 27 18:08:59 CDT 2013
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table testtype
     *
     * @mbggenerated Fri Sep 27 18:08:59 CDT 2013
     */
    public TestTypeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table testtype
     *
     * @mbggenerated Fri Sep 27 18:08:59 CDT 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table testtype
     *
     * @mbggenerated Fri Sep 27 18:08:59 CDT 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table testtype
     *
     * @mbggenerated Fri Sep 27 18:08:59 CDT 2013
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table testtype
     *
     * @mbggenerated Fri Sep 27 18:08:59 CDT 2013
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table testtype
     *
     * @mbggenerated Fri Sep 27 18:08:59 CDT 2013
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table testtype
     *
     * @mbggenerated Fri Sep 27 18:08:59 CDT 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table testtype
     *
     * @mbggenerated Fri Sep 27 18:08:59 CDT 2013
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table testtype
     *
     * @mbggenerated Fri Sep 27 18:08:59 CDT 2013
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table testtype
     *
     * @mbggenerated Fri Sep 27 18:08:59 CDT 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table testtype
     *
     * @mbggenerated Fri Sep 27 18:08:59 CDT 2013
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table testtype
     *
     * @mbggenerated Fri Sep 27 18:08:59 CDT 2013
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTestTypeCodeIsNull() {
            addCriterion("testtypecode is null");
            return (Criteria) this;
        }

        public Criteria andTestTypeCodeIsNotNull() {
            addCriterion("testtypecode is not null");
            return (Criteria) this;
        }

        public Criteria andTestTypeCodeEqualTo(String value) {
            addCriterion("testtypecode =", value, "testTypeCode");
            return (Criteria) this;
        }

        public Criteria andTestTypeCodeNotEqualTo(String value) {
            addCriterion("testtypecode <>", value, "testTypeCode");
            return (Criteria) this;
        }

        public Criteria andTestTypeCodeGreaterThan(String value) {
            addCriterion("testtypecode >", value, "testTypeCode");
            return (Criteria) this;
        }

        public Criteria andTestTypeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("testtypecode >=", value, "testTypeCode");
            return (Criteria) this;
        }

        public Criteria andTestTypeCodeLessThan(String value) {
            addCriterion("testtypecode <", value, "testTypeCode");
            return (Criteria) this;
        }

        public Criteria andTestTypeCodeLessThanOrEqualTo(String value) {
            addCriterion("testtypecode <=", value, "testTypeCode");
            return (Criteria) this;
        }

        public Criteria andTestTypeCodeLike(String value) {
            addCriterion("testtypecode like", value, "testTypeCode");
            return (Criteria) this;
        }

        public Criteria andTestTypeCodeNotLike(String value) {
            addCriterion("testtypecode not like", value, "testTypeCode");
            return (Criteria) this;
        }

        public Criteria andTestTypeCodeIn(List<String> values) {
            addCriterion("testtypecode in", values, "testTypeCode");
            return (Criteria) this;
        }

        public Criteria andTestTypeCodeNotIn(List<String> values) {
            addCriterion("testtypecode not in", values, "testTypeCode");
            return (Criteria) this;
        }

        public Criteria andTestTypeCodeBetween(String value1, String value2) {
            addCriterion("testtypecode between", value1, value2, "testTypeCode");
            return (Criteria) this;
        }

        public Criteria andTestTypeCodeNotBetween(String value1, String value2) {
            addCriterion("testtypecode not between", value1, value2, "testTypeCode");
            return (Criteria) this;
        }

        public Criteria andTestTypeNameIsNull() {
            addCriterion("testtypename is null");
            return (Criteria) this;
        }

        public Criteria andTestTypeNameIsNotNull() {
            addCriterion("testtypename is not null");
            return (Criteria) this;
        }

        public Criteria andTestTypeNameEqualTo(String value) {
            addCriterion("testtypename =", value, "testTypeName");
            return (Criteria) this;
        }

        public Criteria andTestTypeNameNotEqualTo(String value) {
            addCriterion("testtypename <>", value, "testTypeName");
            return (Criteria) this;
        }

        public Criteria andTestTypeNameGreaterThan(String value) {
            addCriterion("testtypename >", value, "testTypeName");
            return (Criteria) this;
        }

        public Criteria andTestTypeNameGreaterThanOrEqualTo(String value) {
            addCriterion("testtypename >=", value, "testTypeName");
            return (Criteria) this;
        }

        public Criteria andTestTypeNameLessThan(String value) {
            addCriterion("testtypename <", value, "testTypeName");
            return (Criteria) this;
        }

        public Criteria andTestTypeNameLessThanOrEqualTo(String value) {
            addCriterion("testtypename <=", value, "testTypeName");
            return (Criteria) this;
        }

        public Criteria andTestTypeNameLike(String value) {
            addCriterion("testtypename like", value, "testTypeName");
            return (Criteria) this;
        }

        public Criteria andTestTypeNameNotLike(String value) {
            addCriterion("testtypename not like", value, "testTypeName");
            return (Criteria) this;
        }

        public Criteria andTestTypeNameIn(List<String> values) {
            addCriterion("testtypename in", values, "testTypeName");
            return (Criteria) this;
        }

        public Criteria andTestTypeNameNotIn(List<String> values) {
            addCriterion("testtypename not in", values, "testTypeName");
            return (Criteria) this;
        }

        public Criteria andTestTypeNameBetween(String value1, String value2) {
            addCriterion("testtypename between", value1, value2, "testTypeName");
            return (Criteria) this;
        }

        public Criteria andTestTypeNameNotBetween(String value1, String value2) {
            addCriterion("testtypename not between", value1, value2, "testTypeName");
            return (Criteria) this;
        }

        public Criteria andAssessmentIdIsNull() {
            addCriterion("assessmentid is null");
            return (Criteria) this;
        }

        public Criteria andAssessmentIdIsNotNull() {
            addCriterion("assessmentid is not null");
            return (Criteria) this;
        }

        public Criteria andAssessmentIdEqualTo(Long value) {
            addCriterion("assessmentid =", value, "assessmentId");
            return (Criteria) this;
        }

        public Criteria andAssessmentIdNotEqualTo(Long value) {
            addCriterion("assessmentid <>", value, "assessmentId");
            return (Criteria) this;
        }

        public Criteria andAssessmentIdGreaterThan(Long value) {
            addCriterion("assessmentid >", value, "assessmentId");
            return (Criteria) this;
        }

        public Criteria andAssessmentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("assessmentid >=", value, "assessmentId");
            return (Criteria) this;
        }

        public Criteria andAssessmentIdLessThan(Long value) {
            addCriterion("assessmentid <", value, "assessmentId");
            return (Criteria) this;
        }

        public Criteria andAssessmentIdLessThanOrEqualTo(Long value) {
            addCriterion("assessmentid <=", value, "assessmentId");
            return (Criteria) this;
        }

        public Criteria andAssessmentIdIn(List<Long> values) {
            addCriterion("assessmentid in", values, "assessmentId");
            return (Criteria) this;
        }

        public Criteria andAssessmentIdNotIn(List<Long> values) {
            addCriterion("assessmentid not in", values, "assessmentId");
            return (Criteria) this;
        }

        public Criteria andAssessmentIdBetween(Long value1, Long value2) {
            addCriterion("assessmentid between", value1, value2, "assessmentId");
            return (Criteria) this;
        }

        public Criteria andAssessmentIdNotBetween(Long value1, Long value2) {
            addCriterion("assessmentid not between", value1, value2, "assessmentId");
            return (Criteria) this;
        }

        public Criteria andCreateduserIsNull() {
            addCriterion("createduser is null");
            return (Criteria) this;
        }

        public Criteria andCreateduserIsNotNull() {
            addCriterion("createduser is not null");
            return (Criteria) this;
        }

        public Criteria andCreateduserEqualTo(Integer value) {
            addCriterion("createduser =", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserNotEqualTo(Integer value) {
            addCriterion("createduser <>", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserGreaterThan(Integer value) {
            addCriterion("createduser >", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserGreaterThanOrEqualTo(Integer value) {
            addCriterion("createduser >=", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserLessThan(Integer value) {
            addCriterion("createduser <", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserLessThanOrEqualTo(Integer value) {
            addCriterion("createduser <=", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserIn(List<Integer> values) {
            addCriterion("createduser in", values, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserNotIn(List<Integer> values) {
            addCriterion("createduser not in", values, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserBetween(Integer value1, Integer value2) {
            addCriterion("createduser between", value1, value2, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserNotBetween(Integer value1, Integer value2) {
            addCriterion("createduser not between", value1, value2, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreatedateIsNull() {
            addCriterion("createdate is null");
            return (Criteria) this;
        }

        public Criteria andCreatedateIsNotNull() {
            addCriterion("createdate is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedateEqualTo(Date value) {
            addCriterion("createdate =", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotEqualTo(Date value) {
            addCriterion("createdate <>", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateGreaterThan(Date value) {
            addCriterion("createdate >", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateGreaterThanOrEqualTo(Date value) {
            addCriterion("createdate >=", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLessThan(Date value) {
            addCriterion("createdate <", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLessThanOrEqualTo(Date value) {
            addCriterion("createdate <=", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateIn(List<Date> values) {
            addCriterion("createdate in", values, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotIn(List<Date> values) {
            addCriterion("createdate not in", values, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateBetween(Date value1, Date value2) {
            addCriterion("createdate between", value1, value2, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotBetween(Date value1, Date value2) {
            addCriterion("createdate not between", value1, value2, "createdate");
            return (Criteria) this;
        }

        public Criteria andModifieddateIsNull() {
            addCriterion("modifieddate is null");
            return (Criteria) this;
        }

        public Criteria andModifieddateIsNotNull() {
            addCriterion("modifieddate is not null");
            return (Criteria) this;
        }

        public Criteria andModifieddateEqualTo(Date value) {
            addCriterion("modifieddate =", value, "modifieddate");
            return (Criteria) this;
        }

        public Criteria andModifieddateNotEqualTo(Date value) {
            addCriterion("modifieddate <>", value, "modifieddate");
            return (Criteria) this;
        }

        public Criteria andModifieddateGreaterThan(Date value) {
            addCriterion("modifieddate >", value, "modifieddate");
            return (Criteria) this;
        }

        public Criteria andModifieddateGreaterThanOrEqualTo(Date value) {
            addCriterion("modifieddate >=", value, "modifieddate");
            return (Criteria) this;
        }

        public Criteria andModifieddateLessThan(Date value) {
            addCriterion("modifieddate <", value, "modifieddate");
            return (Criteria) this;
        }

        public Criteria andModifieddateLessThanOrEqualTo(Date value) {
            addCriterion("modifieddate <=", value, "modifieddate");
            return (Criteria) this;
        }

        public Criteria andModifieddateIn(List<Date> values) {
            addCriterion("modifieddate in", values, "modifieddate");
            return (Criteria) this;
        }

        public Criteria andModifieddateNotIn(List<Date> values) {
            addCriterion("modifieddate not in", values, "modifieddate");
            return (Criteria) this;
        }

        public Criteria andModifieddateBetween(Date value1, Date value2) {
            addCriterion("modifieddate between", value1, value2, "modifieddate");
            return (Criteria) this;
        }

        public Criteria andModifieddateNotBetween(Date value1, Date value2) {
            addCriterion("modifieddate not between", value1, value2, "modifieddate");
            return (Criteria) this;
        }

        public Criteria andModifieduserIsNull() {
            addCriterion("modifieduser is null");
            return (Criteria) this;
        }

        public Criteria andModifieduserIsNotNull() {
            addCriterion("modifieduser is not null");
            return (Criteria) this;
        }

        public Criteria andModifieduserEqualTo(Integer value) {
            addCriterion("modifieduser =", value, "modifieduser");
            return (Criteria) this;
        }

        public Criteria andModifieduserNotEqualTo(Integer value) {
            addCriterion("modifieduser <>", value, "modifieduser");
            return (Criteria) this;
        }

        public Criteria andModifieduserGreaterThan(Integer value) {
            addCriterion("modifieduser >", value, "modifieduser");
            return (Criteria) this;
        }

        public Criteria andModifieduserGreaterThanOrEqualTo(Integer value) {
            addCriterion("modifieduser >=", value, "modifieduser");
            return (Criteria) this;
        }

        public Criteria andModifieduserLessThan(Integer value) {
            addCriterion("modifieduser <", value, "modifieduser");
            return (Criteria) this;
        }

        public Criteria andModifieduserLessThanOrEqualTo(Integer value) {
            addCriterion("modifieduser <=", value, "modifieduser");
            return (Criteria) this;
        }

        public Criteria andModifieduserIn(List<Integer> values) {
            addCriterion("modifieduser in", values, "modifieduser");
            return (Criteria) this;
        }

        public Criteria andModifieduserNotIn(List<Integer> values) {
            addCriterion("modifieduser not in", values, "modifieduser");
            return (Criteria) this;
        }

        public Criteria andModifieduserBetween(Integer value1, Integer value2) {
            addCriterion("modifieduser between", value1, value2, "modifieduser");
            return (Criteria) this;
        }

        public Criteria andModifieduserNotBetween(Integer value1, Integer value2) {
            addCriterion("modifieduser not between", value1, value2, "modifieduser");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table testtype
     *
     * @mbggenerated do_not_delete_during_merge Fri Sep 27 18:08:59 CDT 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table testtype
     *
     * @mbggenerated Fri Sep 27 18:08:59 CDT 2013
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}