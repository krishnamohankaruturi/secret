package edu.ku.cete.domain.common;

import java.util.ArrayList;
import java.util.List;

import edu.ku.cete.util.Criterion;

public class CategoryTypeExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.categorytype
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.categorytype
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.categorytype
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.categorytype
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    public CategoryTypeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.categorytype
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.categorytype
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.categorytype
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.categorytype
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.categorytype
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.categorytype
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.categorytype
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.categorytype
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
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
     * This method corresponds to the database table public.categorytype
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.categorytype
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table public.categorytype
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
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

        public Criteria andTypeNameIsNull() {
            addCriterion("typename is null");
            return (Criteria) this;
        }

        public Criteria andTypeNameIsNotNull() {
            addCriterion("typename is not null");
            return (Criteria) this;
        }

        public Criteria andTypeNameEqualTo(String value) {
            addCriterion("typename =", value, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameNotEqualTo(String value) {
            addCriterion("typename <>", value, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameGreaterThan(String value) {
            addCriterion("typename >", value, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameGreaterThanOrEqualTo(String value) {
            addCriterion("typename >=", value, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameLessThan(String value) {
            addCriterion("typename <", value, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameLessThanOrEqualTo(String value) {
            addCriterion("typename <=", value, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameLike(String value) {
            addCriterion("typename like", value, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameNotLike(String value) {
            addCriterion("typename not like", value, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameIn(List<String> values) {
            addCriterion("typename in", values, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameNotIn(List<String> values) {
            addCriterion("typename not in", values, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameBetween(String value1, String value2) {
            addCriterion("typename between", value1, value2, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameNotBetween(String value1, String value2) {
            addCriterion("typename not between", value1, value2, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeCodeIsNull() {
            addCriterion("typecode is null");
            return (Criteria) this;
        }

        public Criteria andTypeCodeIsNotNull() {
            addCriterion("typecode is not null");
            return (Criteria) this;
        }

        public Criteria andTypeCodeEqualTo(String value) {
            addCriterion("typecode =", value, "typeCode");
            return (Criteria) this;
        }

        public Criteria andTypeCodeNotEqualTo(String value) {
            addCriterion("typecode <>", value, "typeCode");
            return (Criteria) this;
        }

        public Criteria andTypeCodeGreaterThan(String value) {
            addCriterion("typecode >", value, "typeCode");
            return (Criteria) this;
        }

        public Criteria andTypeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("typecode >=", value, "typeCode");
            return (Criteria) this;
        }

        public Criteria andTypeCodeLessThan(String value) {
            addCriterion("typecode <", value, "typeCode");
            return (Criteria) this;
        }

        public Criteria andTypeCodeLessThanOrEqualTo(String value) {
            addCriterion("typecode <=", value, "typeCode");
            return (Criteria) this;
        }

        public Criteria andTypeCodeLike(String value) {
            addCriterion("typecode like", value, "typeCode");
            return (Criteria) this;
        }

        public Criteria andTypeCodeNotLike(String value) {
            addCriterion("typecode not like", value, "typeCode");
            return (Criteria) this;
        }

        public Criteria andTypeCodeIn(List<String> values) {
            addCriterion("typecode in", values, "typeCode");
            return (Criteria) this;
        }

        public Criteria andTypeCodeNotIn(List<String> values) {
            addCriterion("typecode not in", values, "typeCode");
            return (Criteria) this;
        }

        public Criteria andTypeCodeBetween(String value1, String value2) {
            addCriterion("typecode between", value1, value2, "typeCode");
            return (Criteria) this;
        }

        public Criteria andTypeCodeNotBetween(String value1, String value2) {
            addCriterion("typecode not between", value1, value2, "typeCode");
            return (Criteria) this;
        }

        public Criteria andTypeDescriptionIsNull() {
            addCriterion("typedescription is null");
            return (Criteria) this;
        }

        public Criteria andTypeDescriptionIsNotNull() {
            addCriterion("typedescription is not null");
            return (Criteria) this;
        }

        public Criteria andTypeDescriptionEqualTo(String value) {
            addCriterion("typedescription =", value, "typeDescription");
            return (Criteria) this;
        }

        public Criteria andTypeDescriptionNotEqualTo(String value) {
            addCriterion("typedescription <>", value, "typeDescription");
            return (Criteria) this;
        }

        public Criteria andTypeDescriptionGreaterThan(String value) {
            addCriterion("typedescription >", value, "typeDescription");
            return (Criteria) this;
        }

        public Criteria andTypeDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("typedescription >=", value, "typeDescription");
            return (Criteria) this;
        }

        public Criteria andTypeDescriptionLessThan(String value) {
            addCriterion("typedescription <", value, "typeDescription");
            return (Criteria) this;
        }

        public Criteria andTypeDescriptionLessThanOrEqualTo(String value) {
            addCriterion("typedescription <=", value, "typeDescription");
            return (Criteria) this;
        }

        public Criteria andTypeDescriptionLike(String value) {
            addCriterion("typedescription like", value, "typeDescription");
            return (Criteria) this;
        }

        public Criteria andTypeDescriptionNotLike(String value) {
            addCriterion("typedescription not like", value, "typeDescription");
            return (Criteria) this;
        }

        public Criteria andTypeDescriptionIn(List<String> values) {
            addCriterion("typedescription in", values, "typeDescription");
            return (Criteria) this;
        }

        public Criteria andTypeDescriptionNotIn(List<String> values) {
            addCriterion("typedescription not in", values, "typeDescription");
            return (Criteria) this;
        }

        public Criteria andTypeDescriptionBetween(String value1, String value2) {
            addCriterion("typedescription between", value1, value2, "typeDescription");
            return (Criteria) this;
        }

        public Criteria andTypeDescriptionNotBetween(String value1, String value2) {
            addCriterion("typedescription not between", value1, value2, "typeDescription");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table public.categorytype
     *
     * @mbggenerated do_not_delete_during_merge Fri May 04 12:06:31 CDT 2012
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

}