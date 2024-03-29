package edu.ku.cete.domain;

import java.util.ArrayList;
import java.util.List;

public class OrganizationHierarchyExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table organizationhierarchy
     *
     * @mbggenerated Mon Nov 25 09:44:51 CST 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table organizationhierarchy
     *
     * @mbggenerated Mon Nov 25 09:44:51 CST 2013
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table organizationhierarchy
     *
     * @mbggenerated Mon Nov 25 09:44:51 CST 2013
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table organizationhierarchy
     *
     * @mbggenerated Mon Nov 25 09:44:51 CST 2013
     */
    public OrganizationHierarchyExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table organizationhierarchy
     *
     * @mbggenerated Mon Nov 25 09:44:51 CST 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table organizationhierarchy
     *
     * @mbggenerated Mon Nov 25 09:44:51 CST 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table organizationhierarchy
     *
     * @mbggenerated Mon Nov 25 09:44:51 CST 2013
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table organizationhierarchy
     *
     * @mbggenerated Mon Nov 25 09:44:51 CST 2013
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table organizationhierarchy
     *
     * @mbggenerated Mon Nov 25 09:44:51 CST 2013
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table organizationhierarchy
     *
     * @mbggenerated Mon Nov 25 09:44:51 CST 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table organizationhierarchy
     *
     * @mbggenerated Mon Nov 25 09:44:51 CST 2013
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table organizationhierarchy
     *
     * @mbggenerated Mon Nov 25 09:44:51 CST 2013
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
     * This method corresponds to the database table organizationhierarchy
     *
     * @mbggenerated Mon Nov 25 09:44:51 CST 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table organizationhierarchy
     *
     * @mbggenerated Mon Nov 25 09:44:51 CST 2013
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table organizationhierarchy
     *
     * @mbggenerated Mon Nov 25 09:44:51 CST 2013
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

        public Criteria andOrganizationIdIsNull() {
            addCriterion("organizationid is null");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdIsNotNull() {
            addCriterion("organizationid is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdEqualTo(Long value) {
            addCriterion("organizationid =", value, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdNotEqualTo(Long value) {
            addCriterion("organizationid <>", value, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdGreaterThan(Long value) {
            addCriterion("organizationid >", value, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdGreaterThanOrEqualTo(Long value) {
            addCriterion("organizationid >=", value, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdLessThan(Long value) {
            addCriterion("organizationid <", value, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdLessThanOrEqualTo(Long value) {
            addCriterion("organizationid <=", value, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdIn(List<Long> values) {
            addCriterion("organizationid in", values, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdNotIn(List<Long> values) {
            addCriterion("organizationid not in", values, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdBetween(Long value1, Long value2) {
            addCriterion("organizationid between", value1, value2, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdNotBetween(Long value1, Long value2) {
            addCriterion("organizationid not between", value1, value2, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationTypeIdIsNull() {
            addCriterion("organizationtypeid is null");
            return (Criteria) this;
        }

        public Criteria andOrganizationTypeIdIsNotNull() {
            addCriterion("organizationtypeid is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizationTypeIdEqualTo(Long value) {
            addCriterion("organizationtypeid =", value, "organizationTypeId");
            return (Criteria) this;
        }

        public Criteria andOrganizationTypeIdNotEqualTo(Long value) {
            addCriterion("organizationtypeid <>", value, "organizationTypeId");
            return (Criteria) this;
        }

        public Criteria andOrganizationTypeIdGreaterThan(Long value) {
            addCriterion("organizationtypeid >", value, "organizationTypeId");
            return (Criteria) this;
        }

        public Criteria andOrganizationTypeIdGreaterThanOrEqualTo(Long value) {
            addCriterion("organizationtypeid >=", value, "organizationTypeId");
            return (Criteria) this;
        }

        public Criteria andOrganizationTypeIdLessThan(Long value) {
            addCriterion("organizationtypeid <", value, "organizationTypeId");
            return (Criteria) this;
        }

        public Criteria andOrganizationTypeIdLessThanOrEqualTo(Long value) {
            addCriterion("organizationtypeid <=", value, "organizationTypeId");
            return (Criteria) this;
        }

        public Criteria andOrganizationTypeIdIn(List<Long> values) {
            addCriterion("organizationtypeid in", values, "organizationTypeId");
            return (Criteria) this;
        }

        public Criteria andOrganizationTypeIdNotIn(List<Long> values) {
            addCriterion("organizationtypeid not in", values, "organizationTypeId");
            return (Criteria) this;
        }

        public Criteria andOrganizationTypeIdBetween(Long value1, Long value2) {
            addCriterion("organizationtypeid between", value1, value2, "organizationTypeId");
            return (Criteria) this;
        }

        public Criteria andOrganizationTypeIdNotBetween(Long value1, Long value2) {
            addCriterion("organizationtypeid not between", value1, value2, "organizationTypeId");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table organizationhierarchy
     *
     * @mbggenerated do_not_delete_during_merge Mon Nov 25 09:44:51 CST 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table organizationhierarchy
     *
     * @mbggenerated Mon Nov 25 09:44:51 CST 2013
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