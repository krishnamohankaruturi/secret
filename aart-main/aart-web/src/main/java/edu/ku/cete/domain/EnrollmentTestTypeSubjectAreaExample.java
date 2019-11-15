package edu.ku.cete.domain;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentTestTypeSubjectAreaExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table enrollmenttesttypesubjectarea
     *
     * @mbggenerated Fri Sep 27 17:54:18 CDT 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table enrollmenttesttypesubjectarea
     *
     * @mbggenerated Fri Sep 27 17:54:18 CDT 2013
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table enrollmenttesttypesubjectarea
     *
     * @mbggenerated Fri Sep 27 17:54:18 CDT 2013
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enrollmenttesttypesubjectarea
     *
     * @mbggenerated Fri Sep 27 17:54:18 CDT 2013
     */
    public EnrollmentTestTypeSubjectAreaExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enrollmenttesttypesubjectarea
     *
     * @mbggenerated Fri Sep 27 17:54:18 CDT 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enrollmenttesttypesubjectarea
     *
     * @mbggenerated Fri Sep 27 17:54:18 CDT 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enrollmenttesttypesubjectarea
     *
     * @mbggenerated Fri Sep 27 17:54:18 CDT 2013
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enrollmenttesttypesubjectarea
     *
     * @mbggenerated Fri Sep 27 17:54:18 CDT 2013
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enrollmenttesttypesubjectarea
     *
     * @mbggenerated Fri Sep 27 17:54:18 CDT 2013
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enrollmenttesttypesubjectarea
     *
     * @mbggenerated Fri Sep 27 17:54:18 CDT 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enrollmenttesttypesubjectarea
     *
     * @mbggenerated Fri Sep 27 17:54:18 CDT 2013
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enrollmenttesttypesubjectarea
     *
     * @mbggenerated Fri Sep 27 17:54:18 CDT 2013
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
     * This method corresponds to the database table enrollmenttesttypesubjectarea
     *
     * @mbggenerated Fri Sep 27 17:54:18 CDT 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enrollmenttesttypesubjectarea
     *
     * @mbggenerated Fri Sep 27 17:54:18 CDT 2013
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table enrollmenttesttypesubjectarea
     *
     * @mbggenerated Fri Sep 27 17:54:18 CDT 2013
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

        public Criteria andEnrollmentIdIsNull() {
            addCriterion("enrollmentid is null");
            return (Criteria) this;
        }

        public Criteria andEnrollmentIdIsNotNull() {
            addCriterion("enrollmentid is not null");
            return (Criteria) this;
        }

        public Criteria andEnrollmentIdEqualTo(Long value) {
            addCriterion("enrollmentid =", value, "enrollmentId");
            return (Criteria) this;
        }

        public Criteria andEnrollmentIdNotEqualTo(Long value) {
            addCriterion("enrollmentid <>", value, "enrollmentId");
            return (Criteria) this;
        }

        public Criteria andEnrollmentIdGreaterThan(Long value) {
            addCriterion("enrollmentid >", value, "enrollmentId");
            return (Criteria) this;
        }

        public Criteria andEnrollmentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("enrollmentid >=", value, "enrollmentId");
            return (Criteria) this;
        }

        public Criteria andEnrollmentIdLessThan(Long value) {
            addCriterion("enrollmentid <", value, "enrollmentId");
            return (Criteria) this;
        }

        public Criteria andEnrollmentIdLessThanOrEqualTo(Long value) {
            addCriterion("enrollmentid <=", value, "enrollmentId");
            return (Criteria) this;
        }

        public Criteria andEnrollmentIdIn(List<Long> values) {
            addCriterion("enrollmentid in", values, "enrollmentId");
            return (Criteria) this;
        }

        public Criteria andEnrollmentIdNotIn(List<Long> values) {
            addCriterion("enrollmentid not in", values, "enrollmentId");
            return (Criteria) this;
        }

        public Criteria andEnrollmentIdBetween(Long value1, Long value2) {
            addCriterion("enrollmentid between", value1, value2, "enrollmentId");
            return (Criteria) this;
        }

        public Criteria andEnrollmentIdNotBetween(Long value1, Long value2) {
            addCriterion("enrollmentid not between", value1, value2, "enrollmentId");
            return (Criteria) this;
        }

        public Criteria andTestTypeIdIsNull() {
            addCriterion("testtypeid is null");
            return (Criteria) this;
        }

        public Criteria andTestTypeIdIsNotNull() {
            addCriterion("testtypeid is not null");
            return (Criteria) this;
        }

        public Criteria andTestTypeIdEqualTo(Long value) {
            addCriterion("testtypeid =", value, "testTypeId");
            return (Criteria) this;
        }

        public Criteria andTestTypeIdNotEqualTo(Long value) {
            addCriterion("testtypeid <>", value, "testTypeId");
            return (Criteria) this;
        }

        public Criteria andTestTypeIdGreaterThan(Long value) {
            addCriterion("testtypeid >", value, "testTypeId");
            return (Criteria) this;
        }

        public Criteria andTestTypeIdGreaterThanOrEqualTo(Long value) {
            addCriterion("testtypeid >=", value, "testTypeId");
            return (Criteria) this;
        }

        public Criteria andTestTypeIdLessThan(Long value) {
            addCriterion("testtypeid <", value, "testTypeId");
            return (Criteria) this;
        }

        public Criteria andTestTypeIdLessThanOrEqualTo(Long value) {
            addCriterion("testtypeid <=", value, "testTypeId");
            return (Criteria) this;
        }

        public Criteria andTestTypeIdIn(List<Long> values) {
            addCriterion("testtypeid in", values, "testTypeId");
            return (Criteria) this;
        }

        public Criteria andTestTypeIdNotIn(List<Long> values) {
            addCriterion("testtypeid not in", values, "testTypeId");
            return (Criteria) this;
        }

        public Criteria andTestTypeIdBetween(Long value1, Long value2) {
            addCriterion("testtypeid between", value1, value2, "testTypeId");
            return (Criteria) this;
        }

        public Criteria andTestTypeIdNotBetween(Long value1, Long value2) {
            addCriterion("testtypeid not between", value1, value2, "testTypeId");
            return (Criteria) this;
        }

        public Criteria andSubjectareaIdIsNull() {
            addCriterion("subjectareaid is null");
            return (Criteria) this;
        }

        public Criteria andSubjectareaIdIsNotNull() {
            addCriterion("subjectareaid is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectareaIdEqualTo(Long value) {
            addCriterion("subjectareaid =", value, "subjectareaId");
            return (Criteria) this;
        }

        public Criteria andSubjectareaIdNotEqualTo(Long value) {
            addCriterion("subjectareaid <>", value, "subjectareaId");
            return (Criteria) this;
        }

        public Criteria andSubjectareaIdGreaterThan(Long value) {
            addCriterion("subjectareaid >", value, "subjectareaId");
            return (Criteria) this;
        }

        public Criteria andSubjectareaIdGreaterThanOrEqualTo(Long value) {
            addCriterion("subjectareaid >=", value, "subjectareaId");
            return (Criteria) this;
        }

        public Criteria andSubjectareaIdLessThan(Long value) {
            addCriterion("subjectareaid <", value, "subjectareaId");
            return (Criteria) this;
        }

        public Criteria andSubjectareaIdLessThanOrEqualTo(Long value) {
            addCriterion("subjectareaid <=", value, "subjectareaId");
            return (Criteria) this;
        }

        public Criteria andSubjectareaIdIn(List<Long> values) {
            addCriterion("subjectareaid in", values, "subjectareaId");
            return (Criteria) this;
        }

        public Criteria andSubjectareaIdNotIn(List<Long> values) {
            addCriterion("subjectareaid not in", values, "subjectareaId");
            return (Criteria) this;
        }

        public Criteria andSubjectareaIdBetween(Long value1, Long value2) {
            addCriterion("subjectareaid between", value1, value2, "subjectareaId");
            return (Criteria) this;
        }

        public Criteria andSubjectareaIdNotBetween(Long value1, Long value2) {
            addCriterion("subjectareaid not between", value1, value2, "subjectareaId");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table enrollmenttesttypesubjectarea
     *
     * @mbggenerated do_not_delete_during_merge Fri Sep 27 17:54:18 CDT 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table enrollmenttesttypesubjectarea
     *
     * @mbggenerated Fri Sep 27 17:54:18 CDT 2013
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