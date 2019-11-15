package edu.ku.cete.domain.professionaldevelopment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ku.cete.util.Criterion;

public class ActivityExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
     */
    public ActivityExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
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
     * This method corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
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

        public Criteria andUseridIsNull() {
            addCriterion("userid is null");
            return (Criteria) this;
        }

        public Criteria andUseridIsNotNull() {
            addCriterion("userid is not null");
            return (Criteria) this;
        }

        public Criteria andUseridEqualTo(Long value) {
            addCriterion("userid =", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotEqualTo(Long value) {
            addCriterion("userid <>", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThan(Long value) {
            addCriterion("userid >", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThanOrEqualTo(Long value) {
            addCriterion("userid >=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThan(Long value) {
            addCriterion("userid <", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThanOrEqualTo(Long value) {
            addCriterion("userid <=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridIn(List<Long> values) {
            addCriterion("userid in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotIn(List<Long> values) {
            addCriterion("userid not in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridBetween(Long value1, Long value2) {
            addCriterion("userid between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotBetween(Long value1, Long value2) {
            addCriterion("userid not between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andModuleidIsNull() {
            addCriterion("moduleid is null");
            return (Criteria) this;
        }

        public Criteria andModuleidIsNotNull() {
            addCriterion("moduleid is not null");
            return (Criteria) this;
        }

        public Criteria andModuleidEqualTo(Long value) {
            addCriterion("moduleid =", value, "moduleid");
            return (Criteria) this;
        }

        public Criteria andModuleidNotEqualTo(Long value) {
            addCriterion("moduleid <>", value, "moduleid");
            return (Criteria) this;
        }

        public Criteria andModuleidGreaterThan(Long value) {
            addCriterion("moduleid >", value, "moduleid");
            return (Criteria) this;
        }

        public Criteria andModuleidGreaterThanOrEqualTo(Long value) {
            addCriterion("moduleid >=", value, "moduleid");
            return (Criteria) this;
        }

        public Criteria andModuleidLessThan(Long value) {
            addCriterion("moduleid <", value, "moduleid");
            return (Criteria) this;
        }

        public Criteria andModuleidLessThanOrEqualTo(Long value) {
            addCriterion("moduleid <=", value, "moduleid");
            return (Criteria) this;
        }

        public Criteria andModuleidIn(List<Long> values) {
            addCriterion("moduleid in", values, "moduleid");
            return (Criteria) this;
        }

        public Criteria andModuleidNotIn(List<Long> values) {
            addCriterion("moduleid not in", values, "moduleid");
            return (Criteria) this;
        }

        public Criteria andModuleidBetween(Long value1, Long value2) {
            addCriterion("moduleid between", value1, value2, "moduleid");
            return (Criteria) this;
        }

        public Criteria andModuleidNotBetween(Long value1, Long value2) {
            addCriterion("moduleid not between", value1, value2, "moduleid");
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
     * This class corresponds to the database table public.activity
     *
     * @mbggenerated do_not_delete_during_merge Tue Aug 27 14:31:24 CDT 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}