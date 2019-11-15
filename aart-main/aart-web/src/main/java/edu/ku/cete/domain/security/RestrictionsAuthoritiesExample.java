package edu.ku.cete.domain.security;

import java.util.ArrayList;
import java.util.List;

import edu.ku.cete.util.Criterion;

public class RestrictionsAuthoritiesExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.restrictionsauthorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.restrictionsauthorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.restrictionsauthorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restrictionsauthorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
     */
    public RestrictionsAuthoritiesExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restrictionsauthorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restrictionsauthorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restrictionsauthorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restrictionsauthorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restrictionsauthorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restrictionsauthorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restrictionsauthorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restrictionsauthorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
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
     * This method corresponds to the database table public.restrictionsauthorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restrictionsauthorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table public.restrictionsauthorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
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

        public Criteria andRestrictionIdIsNull() {
            addCriterion("restrictionid is null");
            return (Criteria) this;
        }

        public Criteria andRestrictionIdIsNotNull() {
            addCriterion("restrictionid is not null");
            return (Criteria) this;
        }

        public Criteria andRestrictionIdEqualTo(Long value) {
            addCriterion("restrictionid =", value, "restrictionId");
            return (Criteria) this;
        }

        public Criteria andRestrictionIdNotEqualTo(Long value) {
            addCriterion("restrictionid <>", value, "restrictionId");
            return (Criteria) this;
        }

        public Criteria andRestrictionIdGreaterThan(Long value) {
            addCriterion("restrictionid >", value, "restrictionId");
            return (Criteria) this;
        }

        public Criteria andRestrictionIdGreaterThanOrEqualTo(Long value) {
            addCriterion("restrictionid >=", value, "restrictionId");
            return (Criteria) this;
        }

        public Criteria andRestrictionIdLessThan(Long value) {
            addCriterion("restrictionid <", value, "restrictionId");
            return (Criteria) this;
        }

        public Criteria andRestrictionIdLessThanOrEqualTo(Long value) {
            addCriterion("restrictionid <=", value, "restrictionId");
            return (Criteria) this;
        }

        public Criteria andRestrictionIdIn(List<Long> values) {
            addCriterion("restrictionid in", values, "restrictionId");
            return (Criteria) this;
        }

        public Criteria andRestrictionIdNotIn(List<Long> values) {
            addCriterion("restrictionid not in", values, "restrictionId");
            return (Criteria) this;
        }

        public Criteria andRestrictionIdBetween(Long value1, Long value2) {
            addCriterion("restrictionid between", value1, value2, "restrictionId");
            return (Criteria) this;
        }

        public Criteria andRestrictionIdNotBetween(Long value1, Long value2) {
            addCriterion("restrictionid not between", value1, value2, "restrictionId");
            return (Criteria) this;
        }

        public Criteria andAuthorityIdIsNull() {
            addCriterion("authorityid is null");
            return (Criteria) this;
        }

        public Criteria andAuthorityIdIsNotNull() {
            addCriterion("authorityid is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorityIdEqualTo(Long value) {
            addCriterion("authorityid =", value, "authorityId");
            return (Criteria) this;
        }

        public Criteria andAuthorityIdNotEqualTo(Long value) {
            addCriterion("authorityid <>", value, "authorityId");
            return (Criteria) this;
        }

        public Criteria andAuthorityIdGreaterThan(Long value) {
            addCriterion("authorityid >", value, "authorityId");
            return (Criteria) this;
        }

        public Criteria andAuthorityIdGreaterThanOrEqualTo(Long value) {
            addCriterion("authorityid >=", value, "authorityId");
            return (Criteria) this;
        }

        public Criteria andAuthorityIdLessThan(Long value) {
            addCriterion("authorityid <", value, "authorityId");
            return (Criteria) this;
        }

        public Criteria andAuthorityIdLessThanOrEqualTo(Long value) {
            addCriterion("authorityid <=", value, "authorityId");
            return (Criteria) this;
        }

        public Criteria andAuthorityIdIn(List<Long> values) {
            addCriterion("authorityid in", values, "authorityId");
            return (Criteria) this;
        }

        public Criteria andAuthorityIdNotIn(List<Long> values) {
            addCriterion("authorityid not in", values, "authorityId");
            return (Criteria) this;
        }

        public Criteria andAuthorityIdBetween(Long value1, Long value2) {
            addCriterion("authorityid between", value1, value2, "authorityId");
            return (Criteria) this;
        }

        public Criteria andAuthorityIdNotBetween(Long value1, Long value2) {
            addCriterion("authorityid not between", value1, value2, "authorityId");
            return (Criteria) this;
        }

        public Criteria andIsParentIsNull() {
            addCriterion("isparent is null");
            return (Criteria) this;
        }

        public Criteria andIsParentIsNotNull() {
            addCriterion("isparent is not null");
            return (Criteria) this;
        }

        public Criteria andIsParentEqualTo(Boolean value) {
            addCriterion("isparent =", value, "isParent");
            return (Criteria) this;
        }

        public Criteria andIsParentNotEqualTo(Boolean value) {
            addCriterion("isparent <>", value, "isParent");
            return (Criteria) this;
        }

        public Criteria andIsParentGreaterThan(Boolean value) {
            addCriterion("isparent >", value, "isParent");
            return (Criteria) this;
        }

        public Criteria andIsParentGreaterThanOrEqualTo(Boolean value) {
            addCriterion("isparent >=", value, "isParent");
            return (Criteria) this;
        }

        public Criteria andIsParentLessThan(Boolean value) {
            addCriterion("isparent <", value, "isParent");
            return (Criteria) this;
        }

        public Criteria andIsParentLessThanOrEqualTo(Boolean value) {
            addCriterion("isparent <=", value, "isParent");
            return (Criteria) this;
        }

        public Criteria andIsParentIn(List<Boolean> values) {
            addCriterion("isparent in", values, "isParent");
            return (Criteria) this;
        }

        public Criteria andIsParentNotIn(List<Boolean> values) {
            addCriterion("isparent not in", values, "isParent");
            return (Criteria) this;
        }

        public Criteria andIsParentBetween(Boolean value1, Boolean value2) {
            addCriterion("isparent between", value1, value2, "isParent");
            return (Criteria) this;
        }

        public Criteria andIsParentNotBetween(Boolean value1, Boolean value2) {
            addCriterion("isparent not between", value1, value2, "isParent");
            return (Criteria) this;
        }

        public Criteria andIsChildIsNull() {
            addCriterion("ischild is null");
            return (Criteria) this;
        }

        public Criteria andIsChildIsNotNull() {
            addCriterion("ischild is not null");
            return (Criteria) this;
        }

        public Criteria andIsChildEqualTo(Boolean value) {
            addCriterion("ischild =", value, "isChild");
            return (Criteria) this;
        }

        public Criteria andIsChildNotEqualTo(Boolean value) {
            addCriterion("ischild <>", value, "isChild");
            return (Criteria) this;
        }

        public Criteria andIsChildGreaterThan(Boolean value) {
            addCriterion("ischild >", value, "isChild");
            return (Criteria) this;
        }

        public Criteria andIsChildGreaterThanOrEqualTo(Boolean value) {
            addCriterion("ischild >=", value, "isChild");
            return (Criteria) this;
        }

        public Criteria andIsChildLessThan(Boolean value) {
            addCriterion("ischild <", value, "isChild");
            return (Criteria) this;
        }

        public Criteria andIsChildLessThanOrEqualTo(Boolean value) {
            addCriterion("ischild <=", value, "isChild");
            return (Criteria) this;
        }

        public Criteria andIsChildIn(List<Boolean> values) {
            addCriterion("ischild in", values, "isChild");
            return (Criteria) this;
        }

        public Criteria andIsChildNotIn(List<Boolean> values) {
            addCriterion("ischild not in", values, "isChild");
            return (Criteria) this;
        }

        public Criteria andIsChildBetween(Boolean value1, Boolean value2) {
            addCriterion("ischild between", value1, value2, "isChild");
            return (Criteria) this;
        }

        public Criteria andIsChildNotBetween(Boolean value1, Boolean value2) {
            addCriterion("ischild not between", value1, value2, "isChild");
            return (Criteria) this;
        }

        public Criteria andIsDifferentialIsNull() {
            addCriterion("isdifferential is null");
            return (Criteria) this;
        }

        public Criteria andIsDifferentialIsNotNull() {
            addCriterion("isdifferential is not null");
            return (Criteria) this;
        }

        public Criteria andIsDifferentialEqualTo(Boolean value) {
            addCriterion("isdifferential =", value, "isDifferential");
            return (Criteria) this;
        }

        public Criteria andIsDifferentialNotEqualTo(Boolean value) {
            addCriterion("isdifferential <>", value, "isDifferential");
            return (Criteria) this;
        }

        public Criteria andIsDifferentialGreaterThan(Boolean value) {
            addCriterion("isdifferential >", value, "isDifferential");
            return (Criteria) this;
        }

        public Criteria andIsDifferentialGreaterThanOrEqualTo(Boolean value) {
            addCriterion("isdifferential >=", value, "isDifferential");
            return (Criteria) this;
        }

        public Criteria andIsDifferentialLessThan(Boolean value) {
            addCriterion("isdifferential <", value, "isDifferential");
            return (Criteria) this;
        }

        public Criteria andIsDifferentialLessThanOrEqualTo(Boolean value) {
            addCriterion("isdifferential <=", value, "isDifferential");
            return (Criteria) this;
        }

        public Criteria andIsDifferentialIn(List<Boolean> values) {
            addCriterion("isdifferential in", values, "isDifferential");
            return (Criteria) this;
        }

        public Criteria andIsDifferentialNotIn(List<Boolean> values) {
            addCriterion("isdifferential not in", values, "isDifferential");
            return (Criteria) this;
        }

        public Criteria andIsDifferentialBetween(Boolean value1, Boolean value2) {
            addCriterion("isdifferential between", value1, value2, "isDifferential");
            return (Criteria) this;
        }

        public Criteria andIsDifferentialNotBetween(Boolean value1, Boolean value2) {
            addCriterion("isdifferential not between", value1, value2, "isDifferential");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table public.restrictionsauthorities
     *
     * @mbggenerated donotdelete_during_merge Tue Sep 04 22:27:28 CDT 2012
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}