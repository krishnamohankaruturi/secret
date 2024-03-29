package edu.ku.cete.domain.content;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ku.cete.util.Criterion;

public class GradeCourseExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.gradecourse
     *
     * @mbggenerated Wed Sep 12 13:56:00 CDT 2012
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.gradecourse
     *
     * @mbggenerated Wed Sep 12 13:56:00 CDT 2012
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.gradecourse
     *
     * @mbggenerated Wed Sep 12 13:56:00 CDT 2012
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.gradecourse
     *
     * @mbggenerated Wed Sep 12 13:56:00 CDT 2012
     */
    public GradeCourseExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.gradecourse
     *
     * @mbggenerated Wed Sep 12 13:56:00 CDT 2012
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.gradecourse
     *
     * @mbggenerated Wed Sep 12 13:56:00 CDT 2012
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.gradecourse
     *
     * @mbggenerated Wed Sep 12 13:56:00 CDT 2012
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.gradecourse
     *
     * @mbggenerated Wed Sep 12 13:56:00 CDT 2012
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.gradecourse
     *
     * @mbggenerated Wed Sep 12 13:56:00 CDT 2012
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.gradecourse
     *
     * @mbggenerated Wed Sep 12 13:56:00 CDT 2012
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.gradecourse
     *
     * @mbggenerated Wed Sep 12 13:56:00 CDT 2012
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.gradecourse
     *
     * @mbggenerated Wed Sep 12 13:56:00 CDT 2012
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
     * This method corresponds to the database table public.gradecourse
     *
     * @mbggenerated Wed Sep 12 13:56:00 CDT 2012
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.gradecourse
     *
     * @mbggenerated Wed Sep 12 13:56:00 CDT 2012
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table public.gradecourse
     *
     * @mbggenerated Wed Sep 12 13:56:00 CDT 2012
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

        public Criteria andExternalIdIsNull() {
            addCriterion("externalid is null");
            return (Criteria) this;
        }

        public Criteria andExternalIdIsNotNull() {
            addCriterion("externalid is not null");
            return (Criteria) this;
        }

        public Criteria andExternalIdEqualTo(Long value) {
            addCriterion("externalid =", value, "externalId");
            return (Criteria) this;
        }

        public Criteria andExternalIdNotEqualTo(Long value) {
            addCriterion("externalid <>", value, "externalId");
            return (Criteria) this;
        }

        public Criteria andExternalIdGreaterThan(Long value) {
            addCriterion("externalid >", value, "externalId");
            return (Criteria) this;
        }

        public Criteria andExternalIdGreaterThanOrEqualTo(Long value) {
            addCriterion("externalid >=", value, "externalId");
            return (Criteria) this;
        }

        public Criteria andExternalIdLessThan(Long value) {
            addCriterion("externalid <", value, "externalId");
            return (Criteria) this;
        }

        public Criteria andExternalIdLessThanOrEqualTo(Long value) {
            addCriterion("externalid <=", value, "externalId");
            return (Criteria) this;
        }

        public Criteria andExternalIdIn(List<Long> values) {
            addCriterion("externalid in", values, "externalId");
            return (Criteria) this;
        }

        public Criteria andExternalIdNotIn(List<Long> values) {
            addCriterion("externalid not in", values, "externalId");
            return (Criteria) this;
        }

        public Criteria andExternalIdBetween(Long value1, Long value2) {
            addCriterion("externalid between", value1, value2, "externalId");
            return (Criteria) this;
        }

        public Criteria andExternalIdNotBetween(Long value1, Long value2) {
            addCriterion("externalid not between", value1, value2, "externalId");
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

        public Criteria andAbbreviatedNameIsNull() {
            addCriterion("abbreviatedname is null");
            return (Criteria) this;
        }

        public Criteria andAbbreviatedNameIsNotNull() {
            addCriterion("abbreviatedname is not null");
            return (Criteria) this;
        }

        public Criteria andAbbreviatedNameEqualTo(String value) {
            addCriterion("abbreviatedname =", value, "abbreviatedName");
            return (Criteria) this;
        }

        public Criteria andAbbreviatedNameNotEqualTo(String value) {
            addCriterion("abbreviatedname <>", value, "abbreviatedName");
            return (Criteria) this;
        }

        public Criteria andAbbreviatedNameGreaterThan(String value) {
            addCriterion("abbreviatedname >", value, "abbreviatedName");
            return (Criteria) this;
        }

        public Criteria andAbbreviatedNameGreaterThanOrEqualTo(String value) {
            addCriterion("abbreviatedname >=", value, "abbreviatedName");
            return (Criteria) this;
        }

        public Criteria andAbbreviatedNameLessThan(String value) {
            addCriterion("abbreviatedname <", value, "abbreviatedName");
            return (Criteria) this;
        }

        public Criteria andAbbreviatedNameLessThanOrEqualTo(String value) {
            addCriterion("abbreviatedname <=", value, "abbreviatedName");
            return (Criteria) this;
        }

        public Criteria andAbbreviatedNameLike(String value) {
            addCriterion("abbreviatedname like", value, "abbreviatedName");
            return (Criteria) this;
        }

        public Criteria andAbbreviatedNameNotLike(String value) {
            addCriterion("abbreviatedname not like", value, "abbreviatedName");
            return (Criteria) this;
        }

        public Criteria andAbbreviatedNameIn(List<String> values) {
            addCriterion("abbreviatedname in", values, "abbreviatedName");
            return (Criteria) this;
        }

        public Criteria andAbbreviatedNameNotIn(List<String> values) {
            addCriterion("abbreviatedname not in", values, "abbreviatedName");
            return (Criteria) this;
        }

        public Criteria andAbbreviatedNameBetween(String value1, String value2) {
            addCriterion("abbreviatedname between", value1, value2, "abbreviatedName");
            return (Criteria) this;
        }

        public Criteria andAbbreviatedNameNotBetween(String value1, String value2) {
            addCriterion("abbreviatedname not between", value1, value2, "abbreviatedName");
            return (Criteria) this;
        }

        public Criteria andOrdinalityIsNull() {
            addCriterion("ordinality is null");
            return (Criteria) this;
        }

        public Criteria andOrdinalityIsNotNull() {
            addCriterion("ordinality is not null");
            return (Criteria) this;
        }

        public Criteria andOrdinalityEqualTo(Long value) {
            addCriterion("ordinality =", value, "ordinality");
            return (Criteria) this;
        }

        public Criteria andOrdinalityNotEqualTo(Long value) {
            addCriterion("ordinality <>", value, "ordinality");
            return (Criteria) this;
        }

        public Criteria andOrdinalityGreaterThan(Long value) {
            addCriterion("ordinality >", value, "ordinality");
            return (Criteria) this;
        }

        public Criteria andOrdinalityGreaterThanOrEqualTo(Long value) {
            addCriterion("ordinality >=", value, "ordinality");
            return (Criteria) this;
        }

        public Criteria andOrdinalityLessThan(Long value) {
            addCriterion("ordinality <", value, "ordinality");
            return (Criteria) this;
        }

        public Criteria andOrdinalityLessThanOrEqualTo(Long value) {
            addCriterion("ordinality <=", value, "ordinality");
            return (Criteria) this;
        }

        public Criteria andOrdinalityIn(List<Long> values) {
            addCriterion("ordinality in", values, "ordinality");
            return (Criteria) this;
        }

        public Criteria andOrdinalityNotIn(List<Long> values) {
            addCriterion("ordinality not in", values, "ordinality");
            return (Criteria) this;
        }

        public Criteria andOrdinalityBetween(Long value1, Long value2) {
            addCriterion("ordinality between", value1, value2, "ordinality");
            return (Criteria) this;
        }

        public Criteria andOrdinalityNotBetween(Long value1, Long value2) {
            addCriterion("ordinality not between", value1, value2, "ordinality");
            return (Criteria) this;
        }

        public Criteria andGradeLevelIsNull() {
            addCriterion("gradelevel is null");
            return (Criteria) this;
        }

        public Criteria andGradeLevelIsNotNull() {
            addCriterion("gradelevel is not null");
            return (Criteria) this;
        }

        public Criteria andGradeLevelEqualTo(Integer value) {
            addCriterion("gradelevel =", value, "gradeLevel");
            return (Criteria) this;
        }

        public Criteria andGradeLevelNotEqualTo(Integer value) {
            addCriterion("gradelevel <>", value, "gradeLevel");
            return (Criteria) this;
        }

        public Criteria andGradeLevelGreaterThan(Integer value) {
            addCriterion("gradelevel >", value, "gradeLevel");
            return (Criteria) this;
        }

        public Criteria andGradeLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("gradelevel >=", value, "gradeLevel");
            return (Criteria) this;
        }

        public Criteria andGradeLevelLessThan(Integer value) {
            addCriterion("gradelevel <", value, "gradeLevel");
            return (Criteria) this;
        }

        public Criteria andGradeLevelLessThanOrEqualTo(Integer value) {
            addCriterion("gradelevel <=", value, "gradeLevel");
            return (Criteria) this;
        }

        public Criteria andGradeLevelIn(List<Integer> values) {
            addCriterion("gradelevel in", values, "gradeLevel");
            return (Criteria) this;
        }

        public Criteria andGradeLevelNotIn(List<Integer> values) {
            addCriterion("gradelevel not in", values, "gradeLevel");
            return (Criteria) this;
        }

        public Criteria andGradeLevelBetween(Integer value1, Integer value2) {
            addCriterion("gradelevel between", value1, value2, "gradeLevel");
            return (Criteria) this;
        }

        public Criteria andGradeLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("gradelevel not between", value1, value2, "gradeLevel");
            return (Criteria) this;
        }

        public Criteria andShortDescriptionIsNull() {
            addCriterion("shortdescription is null");
            return (Criteria) this;
        }

        public Criteria andShortDescriptionIsNotNull() {
            addCriterion("shortdescription is not null");
            return (Criteria) this;
        }

        public Criteria andShortDescriptionEqualTo(String value) {
            addCriterion("shortdescription =", value, "shortDescription");
            return (Criteria) this;
        }

        public Criteria andShortDescriptionNotEqualTo(String value) {
            addCriterion("shortdescription <>", value, "shortDescription");
            return (Criteria) this;
        }

        public Criteria andShortDescriptionGreaterThan(String value) {
            addCriterion("shortdescription >", value, "shortDescription");
            return (Criteria) this;
        }

        public Criteria andShortDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("shortdescription >=", value, "shortDescription");
            return (Criteria) this;
        }

        public Criteria andShortDescriptionLessThan(String value) {
            addCriterion("shortdescription <", value, "shortDescription");
            return (Criteria) this;
        }

        public Criteria andShortDescriptionLessThanOrEqualTo(String value) {
            addCriterion("shortdescription <=", value, "shortDescription");
            return (Criteria) this;
        }

        public Criteria andShortDescriptionLike(String value) {
            addCriterion("shortdescription like", value, "shortDescription");
            return (Criteria) this;
        }

        public Criteria andShortDescriptionNotLike(String value) {
            addCriterion("shortdescription not like", value, "shortDescription");
            return (Criteria) this;
        }

        public Criteria andShortDescriptionIn(List<String> values) {
            addCriterion("shortdescription in", values, "shortDescription");
            return (Criteria) this;
        }

        public Criteria andShortDescriptionNotIn(List<String> values) {
            addCriterion("shortdescription not in", values, "shortDescription");
            return (Criteria) this;
        }

        public Criteria andShortDescriptionBetween(String value1, String value2) {
            addCriterion("shortdescription between", value1, value2, "shortDescription");
            return (Criteria) this;
        }

        public Criteria andShortDescriptionNotBetween(String value1, String value2) {
            addCriterion("shortdescription not between", value1, value2, "shortDescription");
            return (Criteria) this;
        }

        public Criteria andLongDescriptionIsNull() {
            addCriterion("longdescription is null");
            return (Criteria) this;
        }

        public Criteria andLongDescriptionIsNotNull() {
            addCriterion("longdescription is not null");
            return (Criteria) this;
        }

        public Criteria andLongDescriptionEqualTo(String value) {
            addCriterion("longdescription =", value, "longDescription");
            return (Criteria) this;
        }

        public Criteria andLongDescriptionNotEqualTo(String value) {
            addCriterion("longdescription <>", value, "longDescription");
            return (Criteria) this;
        }

        public Criteria andLongDescriptionGreaterThan(String value) {
            addCriterion("longdescription >", value, "longDescription");
            return (Criteria) this;
        }

        public Criteria andLongDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("longdescription >=", value, "longDescription");
            return (Criteria) this;
        }

        public Criteria andLongDescriptionLessThan(String value) {
            addCriterion("longdescription <", value, "longDescription");
            return (Criteria) this;
        }

        public Criteria andLongDescriptionLessThanOrEqualTo(String value) {
            addCriterion("longdescription <=", value, "longDescription");
            return (Criteria) this;
        }

        public Criteria andLongDescriptionLike(String value) {
            addCriterion("longdescription like", value, "longDescription");
            return (Criteria) this;
        }

        public Criteria andLongDescriptionNotLike(String value) {
            addCriterion("longdescription not like", value, "longDescription");
            return (Criteria) this;
        }

        public Criteria andLongDescriptionIn(List<String> values) {
            addCriterion("longdescription in", values, "longDescription");
            return (Criteria) this;
        }

        public Criteria andLongDescriptionNotIn(List<String> values) {
            addCriterion("longdescription not in", values, "longDescription");
            return (Criteria) this;
        }

        public Criteria andLongDescriptionBetween(String value1, String value2) {
            addCriterion("longdescription between", value1, value2, "longDescription");
            return (Criteria) this;
        }

        public Criteria andLongDescriptionNotBetween(String value1, String value2) {
            addCriterion("longdescription not between", value1, value2, "longDescription");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("createdate is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("createdate is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("createdate =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("createdate <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("createdate >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("createdate >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("createdate <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("createdate <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("createdate in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("createdate not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("createdate between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("createdate not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andModifiedDateIsNull() {
            addCriterion("modifieddate is null");
            return (Criteria) this;
        }

        public Criteria andModifiedDateIsNotNull() {
            addCriterion("modifieddate is not null");
            return (Criteria) this;
        }

        public Criteria andModifiedDateEqualTo(Date value) {
            addCriterion("modifieddate =", value, "modifiedDate");
            return (Criteria) this;
        }

        public Criteria andModifiedDateNotEqualTo(Date value) {
            addCriterion("modifieddate <>", value, "modifiedDate");
            return (Criteria) this;
        }

        public Criteria andModifiedDateGreaterThan(Date value) {
            addCriterion("modifieddate >", value, "modifiedDate");
            return (Criteria) this;
        }

        public Criteria andModifiedDateGreaterThanOrEqualTo(Date value) {
            addCriterion("modifieddate >=", value, "modifiedDate");
            return (Criteria) this;
        }

        public Criteria andModifiedDateLessThan(Date value) {
            addCriterion("modifieddate <", value, "modifiedDate");
            return (Criteria) this;
        }

        public Criteria andModifiedDateLessThanOrEqualTo(Date value) {
            addCriterion("modifieddate <=", value, "modifiedDate");
            return (Criteria) this;
        }

        public Criteria andModifiedDateIn(List<Date> values) {
            addCriterion("modifieddate in", values, "modifiedDate");
            return (Criteria) this;
        }

        public Criteria andModifiedDateNotIn(List<Date> values) {
            addCriterion("modifieddate not in", values, "modifiedDate");
            return (Criteria) this;
        }

        public Criteria andModifiedDateBetween(Date value1, Date value2) {
            addCriterion("modifieddate between", value1, value2, "modifiedDate");
            return (Criteria) this;
        }

        public Criteria andModifiedDateNotBetween(Date value1, Date value2) {
            addCriterion("modifieddate not between", value1, value2, "modifiedDate");
            return (Criteria) this;
        }

        public Criteria andOriginationCodeIsNull() {
            addCriterion("originationcode is null");
            return (Criteria) this;
        }

        public Criteria andOriginationCodeIsNotNull() {
            addCriterion("originationcode is not null");
            return (Criteria) this;
        }

        public Criteria andOriginationCodeEqualTo(String value) {
            addCriterion("originationcode =", value, "originationCode");
            return (Criteria) this;
        }

        public Criteria andOriginationCodeNotEqualTo(String value) {
            addCriterion("originationcode <>", value, "originationCode");
            return (Criteria) this;
        }

        public Criteria andOriginationCodeGreaterThan(String value) {
            addCriterion("originationcode >", value, "originationCode");
            return (Criteria) this;
        }

        public Criteria andOriginationCodeGreaterThanOrEqualTo(String value) {
            addCriterion("originationcode >=", value, "originationCode");
            return (Criteria) this;
        }

        public Criteria andOriginationCodeLessThan(String value) {
            addCriterion("originationcode <", value, "originationCode");
            return (Criteria) this;
        }

        public Criteria andOriginationCodeLessThanOrEqualTo(String value) {
            addCriterion("originationcode <=", value, "originationCode");
            return (Criteria) this;
        }

        public Criteria andOriginationCodeLike(String value) {
            addCriterion("originationcode like", value, "originationCode");
            return (Criteria) this;
        }

        public Criteria andOriginationCodeNotLike(String value) {
            addCriterion("originationcode not like", value, "originationCode");
            return (Criteria) this;
        }

        public Criteria andOriginationCodeIn(List<String> values) {
            addCriterion("originationcode in", values, "originationCode");
            return (Criteria) this;
        }

        public Criteria andOriginationCodeNotIn(List<String> values) {
            addCriterion("originationcode not in", values, "originationCode");
            return (Criteria) this;
        }

        public Criteria andOriginationCodeBetween(String value1, String value2) {
            addCriterion("originationcode between", value1, value2, "originationCode");
            return (Criteria) this;
        }

        public Criteria andOriginationCodeNotBetween(String value1, String value2) {
            addCriterion("originationcode not between", value1, value2, "originationCode");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table public.gradecourse
     *
     * @mbggenerated do_not_delete_during_merge Wed Sep 12 13:56:00 CDT 2012
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

}