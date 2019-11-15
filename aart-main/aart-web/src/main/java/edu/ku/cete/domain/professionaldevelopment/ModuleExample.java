package edu.ku.cete.domain.professionaldevelopment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModuleExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.module
     *
     * @mbggenerated Thu Aug 29 16:10:44 CDT 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.module
     *
     * @mbggenerated Thu Aug 29 16:10:44 CDT 2013
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.module
     *
     * @mbggenerated Thu Aug 29 16:10:44 CDT 2013
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.module
     *
     * @mbggenerated Thu Aug 29 16:10:44 CDT 2013
     */
    public ModuleExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.module
     *
     * @mbggenerated Thu Aug 29 16:10:44 CDT 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.module
     *
     * @mbggenerated Thu Aug 29 16:10:44 CDT 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.module
     *
     * @mbggenerated Thu Aug 29 16:10:44 CDT 2013
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.module
     *
     * @mbggenerated Thu Aug 29 16:10:44 CDT 2013
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.module
     *
     * @mbggenerated Thu Aug 29 16:10:44 CDT 2013
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.module
     *
     * @mbggenerated Thu Aug 29 16:10:44 CDT 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.module
     *
     * @mbggenerated Thu Aug 29 16:10:44 CDT 2013
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.module
     *
     * @mbggenerated Thu Aug 29 16:10:44 CDT 2013
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
     * This method corresponds to the database table public.module
     *
     * @mbggenerated Thu Aug 29 16:10:44 CDT 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.module
     *
     * @mbggenerated Thu Aug 29 16:10:44 CDT 2013
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table public.module
     *
     * @mbggenerated Thu Aug 29 16:10:44 CDT 2013
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andAssessmentprogramidIsNull() {
            addCriterion("assessmentprogramid is null");
            return (Criteria) this;
        }

        public Criteria andAssessmentprogramidIsNotNull() {
            addCriterion("assessmentprogramid is not null");
            return (Criteria) this;
        }

        public Criteria andAssessmentprogramidEqualTo(Long value) {
            addCriterion("assessmentprogramid =", value, "assessmentprogramid");
            return (Criteria) this;
        }

        public Criteria andAssessmentprogramidNotEqualTo(Long value) {
            addCriterion("assessmentprogramid <>", value, "assessmentprogramid");
            return (Criteria) this;
        }

        public Criteria andAssessmentprogramidGreaterThan(Long value) {
            addCriterion("assessmentprogramid >", value, "assessmentprogramid");
            return (Criteria) this;
        }

        public Criteria andAssessmentprogramidGreaterThanOrEqualTo(Long value) {
            addCriterion("assessmentprogramid >=", value, "assessmentprogramid");
            return (Criteria) this;
        }

        public Criteria andAssessmentprogramidLessThan(Long value) {
            addCriterion("assessmentprogramid <", value, "assessmentprogramid");
            return (Criteria) this;
        }

        public Criteria andAssessmentprogramidLessThanOrEqualTo(Long value) {
            addCriterion("assessmentprogramid <=", value, "assessmentprogramid");
            return (Criteria) this;
        }

        public Criteria andAssessmentprogramidIn(List<Long> values) {
            addCriterion("assessmentprogramid in", values, "assessmentprogramid");
            return (Criteria) this;
        }

        public Criteria andAssessmentprogramidNotIn(List<Long> values) {
            addCriterion("assessmentprogramid not in", values, "assessmentprogramid");
            return (Criteria) this;
        }

        public Criteria andAssessmentprogramidBetween(Long value1, Long value2) {
            addCriterion("assessmentprogramid between", value1, value2, "assessmentprogramid");
            return (Criteria) this;
        }

        public Criteria andAssessmentprogramidNotBetween(Long value1, Long value2) {
            addCriterion("assessmentprogramid not between", value1, value2, "assessmentprogramid");
            return (Criteria) this;
        }

        public Criteria andStateassignedidIsNull() {
            addCriterion("stateassignedid is null");
            return (Criteria) this;
        }

        public Criteria andStateassignedidIsNotNull() {
            addCriterion("stateassignedid is not null");
            return (Criteria) this;
        }

        public Criteria andStateassignedidEqualTo(Long value) {
            addCriterion("stateassignedid =", value, "stateassignedid");
            return (Criteria) this;
        }

        public Criteria andStateassignedidNotEqualTo(Long value) {
            addCriterion("stateassignedid <>", value, "stateassignedid");
            return (Criteria) this;
        }

        public Criteria andStateassignedidGreaterThan(Long value) {
            addCriterion("stateassignedid >", value, "stateassignedid");
            return (Criteria) this;
        }

        public Criteria andStateassignedidGreaterThanOrEqualTo(Long value) {
            addCriterion("stateassignedid >=", value, "stateassignedid");
            return (Criteria) this;
        }

        public Criteria andStateassignedidLessThan(Long value) {
            addCriterion("stateassignedid <", value, "stateassignedid");
            return (Criteria) this;
        }

        public Criteria andStateassignedidLessThanOrEqualTo(Long value) {
            addCriterion("stateassignedid <=", value, "stateassignedid");
            return (Criteria) this;
        }

        public Criteria andStateassignedidIn(List<Long> values) {
            addCriterion("stateassignedid in", values, "stateassignedid");
            return (Criteria) this;
        }

        public Criteria andStateassignedidNotIn(List<Long> values) {
            addCriterion("stateassignedid not in", values, "stateassignedid");
            return (Criteria) this;
        }

        public Criteria andStateassignedidBetween(Long value1, Long value2) {
            addCriterion("stateassignedid between", value1, value2, "stateassignedid");
            return (Criteria) this;
        }

        public Criteria andStateassignedidNotBetween(Long value1, Long value2) {
            addCriterion("stateassignedid not between", value1, value2, "stateassignedid");
            return (Criteria) this;
        }

        public Criteria andSuggestedaudienceIsNull() {
            addCriterion("suggestedaudience is null");
            return (Criteria) this;
        }

        public Criteria andSuggestedaudienceIsNotNull() {
            addCriterion("suggestedaudience is not null");
            return (Criteria) this;
        }

        public Criteria andSuggestedaudienceEqualTo(String value) {
            addCriterion("suggestedaudience =", value, "suggestedaudience");
            return (Criteria) this;
        }

        public Criteria andSuggestedaudienceNotEqualTo(String value) {
            addCriterion("suggestedaudience <>", value, "suggestedaudience");
            return (Criteria) this;
        }

        public Criteria andSuggestedaudienceGreaterThan(String value) {
            addCriterion("suggestedaudience >", value, "suggestedaudience");
            return (Criteria) this;
        }

        public Criteria andSuggestedaudienceGreaterThanOrEqualTo(String value) {
            addCriterion("suggestedaudience >=", value, "suggestedaudience");
            return (Criteria) this;
        }

        public Criteria andSuggestedaudienceLessThan(String value) {
            addCriterion("suggestedaudience <", value, "suggestedaudience");
            return (Criteria) this;
        }

        public Criteria andSuggestedaudienceLessThanOrEqualTo(String value) {
            addCriterion("suggestedaudience <=", value, "suggestedaudience");
            return (Criteria) this;
        }

        public Criteria andSuggestedaudienceLike(String value) {
            addCriterion("suggestedaudience like", value, "suggestedaudience");
            return (Criteria) this;
        }

        public Criteria andSuggestedaudienceNotLike(String value) {
            addCriterion("suggestedaudience not like", value, "suggestedaudience");
            return (Criteria) this;
        }

        public Criteria andSuggestedaudienceIn(List<String> values) {
            addCriterion("suggestedaudience in", values, "suggestedaudience");
            return (Criteria) this;
        }

        public Criteria andSuggestedaudienceNotIn(List<String> values) {
            addCriterion("suggestedaudience not in", values, "suggestedaudience");
            return (Criteria) this;
        }

        public Criteria andSuggestedaudienceBetween(String value1, String value2) {
            addCriterion("suggestedaudience between", value1, value2, "suggestedaudience");
            return (Criteria) this;
        }

        public Criteria andSuggestedaudienceNotBetween(String value1, String value2) {
            addCriterion("suggestedaudience not between", value1, value2, "suggestedaudience");
            return (Criteria) this;
        }

        public Criteria andCeuIsNull() {
            addCriterion("ceu is null");
            return (Criteria) this;
        }

        public Criteria andCeuIsNotNull() {
            addCriterion("ceu is not null");
            return (Criteria) this;
        }

        public Criteria andCeuEqualTo(Integer value) {
            addCriterion("ceu =", value, "ceu");
            return (Criteria) this;
        }

        public Criteria andCeuNotEqualTo(Integer value) {
            addCriterion("ceu <>", value, "ceu");
            return (Criteria) this;
        }

        public Criteria andCeuGreaterThan(Integer value) {
            addCriterion("ceu >", value, "ceu");
            return (Criteria) this;
        }

        public Criteria andCeuGreaterThanOrEqualTo(Integer value) {
            addCriterion("ceu >=", value, "ceu");
            return (Criteria) this;
        }

        public Criteria andCeuLessThan(Integer value) {
            addCriterion("ceu <", value, "ceu");
            return (Criteria) this;
        }

        public Criteria andCeuLessThanOrEqualTo(Integer value) {
            addCriterion("ceu <=", value, "ceu");
            return (Criteria) this;
        }

        public Criteria andCeuIn(List<Integer> values) {
            addCriterion("ceu in", values, "ceu");
            return (Criteria) this;
        }

        public Criteria andCeuNotIn(List<Integer> values) {
            addCriterion("ceu not in", values, "ceu");
            return (Criteria) this;
        }

        public Criteria andCeuBetween(Integer value1, Integer value2) {
            addCriterion("ceu between", value1, value2, "ceu");
            return (Criteria) this;
        }

        public Criteria andCeuNotBetween(Integer value1, Integer value2) {
            addCriterion("ceu not between", value1, value2, "ceu");
            return (Criteria) this;
        }

        public Criteria andCompletiontimeIsNull() {
            addCriterion("completiontime is null");
            return (Criteria) this;
        }

        public Criteria andCompletiontimeIsNotNull() {
            addCriterion("completiontime is not null");
            return (Criteria) this;
        }

        public Criteria andCompletiontimeEqualTo(Integer value) {
            addCriterion("completiontime =", value, "completiontime");
            return (Criteria) this;
        }

        public Criteria andCompletiontimeNotEqualTo(Integer value) {
            addCriterion("completiontime <>", value, "completiontime");
            return (Criteria) this;
        }

        public Criteria andCompletiontimeGreaterThan(Integer value) {
            addCriterion("completiontime >", value, "completiontime");
            return (Criteria) this;
        }

        public Criteria andCompletiontimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("completiontime >=", value, "completiontime");
            return (Criteria) this;
        }

        public Criteria andCompletiontimeLessThan(Integer value) {
            addCriterion("completiontime <", value, "completiontime");
            return (Criteria) this;
        }

        public Criteria andCompletiontimeLessThanOrEqualTo(Integer value) {
            addCriterion("completiontime <=", value, "completiontime");
            return (Criteria) this;
        }

        public Criteria andCompletiontimeIn(List<Integer> values) {
            addCriterion("completiontime in", values, "completiontime");
            return (Criteria) this;
        }

        public Criteria andCompletiontimeNotIn(List<Integer> values) {
            addCriterion("completiontime not in", values, "completiontime");
            return (Criteria) this;
        }

        public Criteria andCompletiontimeBetween(Integer value1, Integer value2) {
            addCriterion("completiontime between", value1, value2, "completiontime");
            return (Criteria) this;
        }

        public Criteria andCompletiontimeNotBetween(Integer value1, Integer value2) {
            addCriterion("completiontime not between", value1, value2, "completiontime");
            return (Criteria) this;
        }

        public Criteria andStatusidIsNull() {
            addCriterion("statusid is null");
            return (Criteria) this;
        }

        public Criteria andStatusidIsNotNull() {
            addCriterion("statusid is not null");
            return (Criteria) this;
        }

        public Criteria andStatusidEqualTo(Long value) {
            addCriterion("statusid =", value, "statusid");
            return (Criteria) this;
        }

        public Criteria andStatusidNotEqualTo(Long value) {
            addCriterion("statusid <>", value, "statusid");
            return (Criteria) this;
        }

        public Criteria andStatusidGreaterThan(Long value) {
            addCriterion("statusid >", value, "statusid");
            return (Criteria) this;
        }

        public Criteria andStatusidGreaterThanOrEqualTo(Long value) {
            addCriterion("statusid >=", value, "statusid");
            return (Criteria) this;
        }

        public Criteria andStatusidLessThan(Long value) {
            addCriterion("statusid <", value, "statusid");
            return (Criteria) this;
        }

        public Criteria andStatusidLessThanOrEqualTo(Long value) {
            addCriterion("statusid <=", value, "statusid");
            return (Criteria) this;
        }

        public Criteria andStatusidIn(List<Long> values) {
            addCriterion("statusid in", values, "statusid");
            return (Criteria) this;
        }

        public Criteria andStatusidNotIn(List<Long> values) {
            addCriterion("statusid not in", values, "statusid");
            return (Criteria) this;
        }

        public Criteria andStatusidBetween(Long value1, Long value2) {
            addCriterion("statusid between", value1, value2, "statusid");
            return (Criteria) this;
        }

        public Criteria andStatusidNotBetween(Long value1, Long value2) {
            addCriterion("statusid not between", value1, value2, "statusid");
            return (Criteria) this;
        }

        public Criteria andCreateddateIsNull() {
            addCriterion("createddate is null");
            return (Criteria) this;
        }

        public Criteria andCreateddateIsNotNull() {
            addCriterion("createddate is not null");
            return (Criteria) this;
        }

        public Criteria andCreateddateEqualTo(Date value) {
            addCriterion("createddate =", value, "createddate");
            return (Criteria) this;
        }

        public Criteria andCreateddateNotEqualTo(Date value) {
            addCriterion("createddate <>", value, "createddate");
            return (Criteria) this;
        }

        public Criteria andCreateddateGreaterThan(Date value) {
            addCriterion("createddate >", value, "createddate");
            return (Criteria) this;
        }

        public Criteria andCreateddateGreaterThanOrEqualTo(Date value) {
            addCriterion("createddate >=", value, "createddate");
            return (Criteria) this;
        }

        public Criteria andCreateddateLessThan(Date value) {
            addCriterion("createddate <", value, "createddate");
            return (Criteria) this;
        }

        public Criteria andCreateddateLessThanOrEqualTo(Date value) {
            addCriterion("createddate <=", value, "createddate");
            return (Criteria) this;
        }

        public Criteria andCreateddateIn(List<Date> values) {
            addCriterion("createddate in", values, "createddate");
            return (Criteria) this;
        }

        public Criteria andCreateddateNotIn(List<Date> values) {
            addCriterion("createddate not in", values, "createddate");
            return (Criteria) this;
        }

        public Criteria andCreateddateBetween(Date value1, Date value2) {
            addCriterion("createddate between", value1, value2, "createddate");
            return (Criteria) this;
        }

        public Criteria andCreateddateNotBetween(Date value1, Date value2) {
            addCriterion("createddate not between", value1, value2, "createddate");
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

        public Criteria andCreateduserIsNull() {
            addCriterion("createduser is null");
            return (Criteria) this;
        }

        public Criteria andCreateduserIsNotNull() {
            addCriterion("createduser is not null");
            return (Criteria) this;
        }

        public Criteria andCreateduserEqualTo(Long value) {
            addCriterion("createduser =", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserNotEqualTo(Long value) {
            addCriterion("createduser <>", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserGreaterThan(Long value) {
            addCriterion("createduser >", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserGreaterThanOrEqualTo(Long value) {
            addCriterion("createduser >=", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserLessThan(Long value) {
            addCriterion("createduser <", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserLessThanOrEqualTo(Long value) {
            addCriterion("createduser <=", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserIn(List<Long> values) {
            addCriterion("createduser in", values, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserNotIn(List<Long> values) {
            addCriterion("createduser not in", values, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserBetween(Long value1, Long value2) {
            addCriterion("createduser between", value1, value2, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserNotBetween(Long value1, Long value2) {
            addCriterion("createduser not between", value1, value2, "createduser");
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

        public Criteria andModifieduserEqualTo(Long value) {
            addCriterion("modifieduser =", value, "modifieduser");
            return (Criteria) this;
        }

        public Criteria andModifieduserNotEqualTo(Long value) {
            addCriterion("modifieduser <>", value, "modifieduser");
            return (Criteria) this;
        }

        public Criteria andModifieduserGreaterThan(Long value) {
            addCriterion("modifieduser >", value, "modifieduser");
            return (Criteria) this;
        }

        public Criteria andModifieduserGreaterThanOrEqualTo(Long value) {
            addCriterion("modifieduser >=", value, "modifieduser");
            return (Criteria) this;
        }

        public Criteria andModifieduserLessThan(Long value) {
            addCriterion("modifieduser <", value, "modifieduser");
            return (Criteria) this;
        }

        public Criteria andModifieduserLessThanOrEqualTo(Long value) {
            addCriterion("modifieduser <=", value, "modifieduser");
            return (Criteria) this;
        }

        public Criteria andModifieduserIn(List<Long> values) {
            addCriterion("modifieduser in", values, "modifieduser");
            return (Criteria) this;
        }

        public Criteria andModifieduserNotIn(List<Long> values) {
            addCriterion("modifieduser not in", values, "modifieduser");
            return (Criteria) this;
        }

        public Criteria andModifieduserBetween(Long value1, Long value2) {
            addCriterion("modifieduser between", value1, value2, "modifieduser");
            return (Criteria) this;
        }

        public Criteria andModifieduserNotBetween(Long value1, Long value2) {
            addCriterion("modifieduser not between", value1, value2, "modifieduser");
            return (Criteria) this;
        }

        public Criteria andActiveflagIsNull() {
            addCriterion("activeflag is null");
            return (Criteria) this;
        }

        public Criteria andActiveflagIsNotNull() {
            addCriterion("activeflag is not null");
            return (Criteria) this;
        }

        public Criteria andActiveflagEqualTo(Boolean value) {
            addCriterion("activeflag =", value, "activeflag");
            return (Criteria) this;
        }

        public Criteria andActiveflagNotEqualTo(Boolean value) {
            addCriterion("activeflag <>", value, "activeflag");
            return (Criteria) this;
        }

        public Criteria andActiveflagGreaterThan(Boolean value) {
            addCriterion("activeflag >", value, "activeflag");
            return (Criteria) this;
        }

        public Criteria andActiveflagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("activeflag >=", value, "activeflag");
            return (Criteria) this;
        }

        public Criteria andActiveflagLessThan(Boolean value) {
            addCriterion("activeflag <", value, "activeflag");
            return (Criteria) this;
        }

        public Criteria andActiveflagLessThanOrEqualTo(Boolean value) {
            addCriterion("activeflag <=", value, "activeflag");
            return (Criteria) this;
        }

        public Criteria andActiveflagIn(List<Boolean> values) {
            addCriterion("activeflag in", values, "activeflag");
            return (Criteria) this;
        }

        public Criteria andActiveflagNotIn(List<Boolean> values) {
            addCriterion("activeflag not in", values, "activeflag");
            return (Criteria) this;
        }

        public Criteria andActiveflagBetween(Boolean value1, Boolean value2) {
            addCriterion("activeflag between", value1, value2, "activeflag");
            return (Criteria) this;
        }

        public Criteria andActiveflagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("activeflag not between", value1, value2, "activeflag");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table public.module
     *
     * @mbggenerated do_not_delete_during_merge Thu Aug 29 16:10:44 CDT 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table public.module
     *
     * @mbggenerated Thu Aug 29 16:10:44 CDT 2013
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