package edu.ku.cete.domain.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StimulusVariantExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.stimulusvariant
     *
     * @mbggenerated Thu Oct 25 01:06:31 CDT 2012
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.stimulusvariant
     *
     * @mbggenerated Thu Oct 25 01:06:31 CDT 2012
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.stimulusvariant
     *
     * @mbggenerated Thu Oct 25 01:06:31 CDT 2012
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.stimulusvariant
     *
     * @mbggenerated Thu Oct 25 01:06:31 CDT 2012
     */
    public StimulusVariantExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.stimulusvariant
     *
     * @mbggenerated Thu Oct 25 01:06:31 CDT 2012
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.stimulusvariant
     *
     * @mbggenerated Thu Oct 25 01:06:31 CDT 2012
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.stimulusvariant
     *
     * @mbggenerated Thu Oct 25 01:06:31 CDT 2012
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.stimulusvariant
     *
     * @mbggenerated Thu Oct 25 01:06:31 CDT 2012
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.stimulusvariant
     *
     * @mbggenerated Thu Oct 25 01:06:31 CDT 2012
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.stimulusvariant
     *
     * @mbggenerated Thu Oct 25 01:06:31 CDT 2012
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.stimulusvariant
     *
     * @mbggenerated Thu Oct 25 01:06:31 CDT 2012
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.stimulusvariant
     *
     * @mbggenerated Thu Oct 25 01:06:31 CDT 2012
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
     * This method corresponds to the database table public.stimulusvariant
     *
     * @mbggenerated Thu Oct 25 01:06:31 CDT 2012
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.stimulusvariant
     *
     * @mbggenerated Thu Oct 25 01:06:31 CDT 2012
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table public.stimulusvariant
     *
     * @mbggenerated Thu Oct 25 01:06:31 CDT 2012
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

        public Criteria andExternalidIsNull() {
            addCriterion("externalid is null");
            return (Criteria) this;
        }

        public Criteria andExternalidIsNotNull() {
            addCriterion("externalid is not null");
            return (Criteria) this;
        }

        public Criteria andExternalidEqualTo(Long value) {
            addCriterion("externalid =", value, "externalid");
            return (Criteria) this;
        }

        public Criteria andExternalidNotEqualTo(Long value) {
            addCriterion("externalid <>", value, "externalid");
            return (Criteria) this;
        }

        public Criteria andExternalidGreaterThan(Long value) {
            addCriterion("externalid >", value, "externalid");
            return (Criteria) this;
        }

        public Criteria andExternalidGreaterThanOrEqualTo(Long value) {
            addCriterion("externalid >=", value, "externalid");
            return (Criteria) this;
        }

        public Criteria andExternalidLessThan(Long value) {
            addCriterion("externalid <", value, "externalid");
            return (Criteria) this;
        }

        public Criteria andExternalidLessThanOrEqualTo(Long value) {
            addCriterion("externalid <=", value, "externalid");
            return (Criteria) this;
        }

        public Criteria andExternalidIn(List<Long> values) {
            addCriterion("externalid in", values, "externalid");
            return (Criteria) this;
        }

        public Criteria andExternalidNotIn(List<Long> values) {
            addCriterion("externalid not in", values, "externalid");
            return (Criteria) this;
        }

        public Criteria andExternalidBetween(Long value1, Long value2) {
            addCriterion("externalid between", value1, value2, "externalid");
            return (Criteria) this;
        }

        public Criteria andExternalidNotBetween(Long value1, Long value2) {
            addCriterion("externalid not between", value1, value2, "externalid");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(Integer value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(Integer value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(Integer value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(Integer value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(Integer value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(Integer value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<Integer> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<Integer> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(Integer value1, Integer value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(Integer value1, Integer value2) {
            addCriterion("version not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andStimulusformatidIsNull() {
            addCriterion("stimulusformatid is null");
            return (Criteria) this;
        }

        public Criteria andStimulusformatidIsNotNull() {
            addCriterion("stimulusformatid is not null");
            return (Criteria) this;
        }

        public Criteria andStimulusformatidEqualTo(Long value) {
            addCriterion("stimulusformatid =", value, "stimulusformatid");
            return (Criteria) this;
        }

        public Criteria andStimulusformatidNotEqualTo(Long value) {
            addCriterion("stimulusformatid <>", value, "stimulusformatid");
            return (Criteria) this;
        }

        public Criteria andStimulusformatidGreaterThan(Long value) {
            addCriterion("stimulusformatid >", value, "stimulusformatid");
            return (Criteria) this;
        }

        public Criteria andStimulusformatidGreaterThanOrEqualTo(Long value) {
            addCriterion("stimulusformatid >=", value, "stimulusformatid");
            return (Criteria) this;
        }

        public Criteria andStimulusformatidLessThan(Long value) {
            addCriterion("stimulusformatid <", value, "stimulusformatid");
            return (Criteria) this;
        }

        public Criteria andStimulusformatidLessThanOrEqualTo(Long value) {
            addCriterion("stimulusformatid <=", value, "stimulusformatid");
            return (Criteria) this;
        }

        public Criteria andStimulusformatidIn(List<Long> values) {
            addCriterion("stimulusformatid in", values, "stimulusformatid");
            return (Criteria) this;
        }

        public Criteria andStimulusformatidNotIn(List<Long> values) {
            addCriterion("stimulusformatid not in", values, "stimulusformatid");
            return (Criteria) this;
        }

        public Criteria andStimulusformatidBetween(Long value1, Long value2) {
            addCriterion("stimulusformatid between", value1, value2, "stimulusformatid");
            return (Criteria) this;
        }

        public Criteria andStimulusformatidNotBetween(Long value1, Long value2) {
            addCriterion("stimulusformatid not between", value1, value2, "stimulusformatid");
            return (Criteria) this;
        }

        public Criteria andStimuluscontentIsNull() {
            addCriterion("stimuluscontent is null");
            return (Criteria) this;
        }

        public Criteria andStimuluscontentIsNotNull() {
            addCriterion("stimuluscontent is not null");
            return (Criteria) this;
        }

        public Criteria andStimuluscontentEqualTo(String value) {
            addCriterion("stimuluscontent =", value, "stimuluscontent");
            return (Criteria) this;
        }

        public Criteria andStimuluscontentNotEqualTo(String value) {
            addCriterion("stimuluscontent <>", value, "stimuluscontent");
            return (Criteria) this;
        }

        public Criteria andStimuluscontentGreaterThan(String value) {
            addCriterion("stimuluscontent >", value, "stimuluscontent");
            return (Criteria) this;
        }

        public Criteria andStimuluscontentGreaterThanOrEqualTo(String value) {
            addCriterion("stimuluscontent >=", value, "stimuluscontent");
            return (Criteria) this;
        }

        public Criteria andStimuluscontentLessThan(String value) {
            addCriterion("stimuluscontent <", value, "stimuluscontent");
            return (Criteria) this;
        }

        public Criteria andStimuluscontentLessThanOrEqualTo(String value) {
            addCriterion("stimuluscontent <=", value, "stimuluscontent");
            return (Criteria) this;
        }

        public Criteria andStimuluscontentLike(String value) {
            addCriterion("stimuluscontent like", value, "stimuluscontent");
            return (Criteria) this;
        }

        public Criteria andStimuluscontentNotLike(String value) {
            addCriterion("stimuluscontent not like", value, "stimuluscontent");
            return (Criteria) this;
        }

        public Criteria andStimuluscontentIn(List<String> values) {
            addCriterion("stimuluscontent in", values, "stimuluscontent");
            return (Criteria) this;
        }

        public Criteria andStimuluscontentNotIn(List<String> values) {
            addCriterion("stimuluscontent not in", values, "stimuluscontent");
            return (Criteria) this;
        }

        public Criteria andStimuluscontentBetween(String value1, String value2) {
            addCriterion("stimuluscontent between", value1, value2, "stimuluscontent");
            return (Criteria) this;
        }

        public Criteria andStimuluscontentNotBetween(String value1, String value2) {
            addCriterion("stimuluscontent not between", value1, value2, "stimuluscontent");
            return (Criteria) this;
        }

        public Criteria andStimulustitleIsNull() {
            addCriterion("stimulustitle is null");
            return (Criteria) this;
        }

        public Criteria andStimulustitleIsNotNull() {
            addCriterion("stimulustitle is not null");
            return (Criteria) this;
        }

        public Criteria andStimulustitleEqualTo(String value) {
            addCriterion("stimulustitle =", value, "stimulustitle");
            return (Criteria) this;
        }

        public Criteria andStimulustitleNotEqualTo(String value) {
            addCriterion("stimulustitle <>", value, "stimulustitle");
            return (Criteria) this;
        }

        public Criteria andStimulustitleGreaterThan(String value) {
            addCriterion("stimulustitle >", value, "stimulustitle");
            return (Criteria) this;
        }

        public Criteria andStimulustitleGreaterThanOrEqualTo(String value) {
            addCriterion("stimulustitle >=", value, "stimulustitle");
            return (Criteria) this;
        }

        public Criteria andStimulustitleLessThan(String value) {
            addCriterion("stimulustitle <", value, "stimulustitle");
            return (Criteria) this;
        }

        public Criteria andStimulustitleLessThanOrEqualTo(String value) {
            addCriterion("stimulustitle <=", value, "stimulustitle");
            return (Criteria) this;
        }

        public Criteria andStimulustitleLike(String value) {
            addCriterion("stimulustitle like", value, "stimulustitle");
            return (Criteria) this;
        }

        public Criteria andStimulustitleNotLike(String value) {
            addCriterion("stimulustitle not like", value, "stimulustitle");
            return (Criteria) this;
        }

        public Criteria andStimulustitleIn(List<String> values) {
            addCriterion("stimulustitle in", values, "stimulustitle");
            return (Criteria) this;
        }

        public Criteria andStimulustitleNotIn(List<String> values) {
            addCriterion("stimulustitle not in", values, "stimulustitle");
            return (Criteria) this;
        }

        public Criteria andStimulustitleBetween(String value1, String value2) {
            addCriterion("stimulustitle between", value1, value2, "stimulustitle");
            return (Criteria) this;
        }

        public Criteria andStimulustitleNotBetween(String value1, String value2) {
            addCriterion("stimulustitle not between", value1, value2, "stimulustitle");
            return (Criteria) this;
        }

        public Criteria andGradecourseidIsNull() {
            addCriterion("gradecourseid is null");
            return (Criteria) this;
        }

        public Criteria andGradecourseidIsNotNull() {
            addCriterion("gradecourseid is not null");
            return (Criteria) this;
        }

        public Criteria andGradecourseidEqualTo(Long value) {
            addCriterion("gradecourseid =", value, "gradecourseid");
            return (Criteria) this;
        }

        public Criteria andGradecourseidNotEqualTo(Long value) {
            addCriterion("gradecourseid <>", value, "gradecourseid");
            return (Criteria) this;
        }

        public Criteria andGradecourseidGreaterThan(Long value) {
            addCriterion("gradecourseid >", value, "gradecourseid");
            return (Criteria) this;
        }

        public Criteria andGradecourseidGreaterThanOrEqualTo(Long value) {
            addCriterion("gradecourseid >=", value, "gradecourseid");
            return (Criteria) this;
        }

        public Criteria andGradecourseidLessThan(Long value) {
            addCriterion("gradecourseid <", value, "gradecourseid");
            return (Criteria) this;
        }

        public Criteria andGradecourseidLessThanOrEqualTo(Long value) {
            addCriterion("gradecourseid <=", value, "gradecourseid");
            return (Criteria) this;
        }

        public Criteria andGradecourseidIn(List<Long> values) {
            addCriterion("gradecourseid in", values, "gradecourseid");
            return (Criteria) this;
        }

        public Criteria andGradecourseidNotIn(List<Long> values) {
            addCriterion("gradecourseid not in", values, "gradecourseid");
            return (Criteria) this;
        }

        public Criteria andGradecourseidBetween(Long value1, Long value2) {
            addCriterion("gradecourseid between", value1, value2, "gradecourseid");
            return (Criteria) this;
        }

        public Criteria andGradecourseidNotBetween(Long value1, Long value2) {
            addCriterion("gradecourseid not between", value1, value2, "gradecourseid");
            return (Criteria) this;
        }

        public Criteria andTestingprogramidIsNull() {
            addCriterion("testingprogramid is null");
            return (Criteria) this;
        }

        public Criteria andTestingprogramidIsNotNull() {
            addCriterion("testingprogramid is not null");
            return (Criteria) this;
        }

        public Criteria andTestingprogramidEqualTo(Long value) {
            addCriterion("testingprogramid =", value, "testingprogramid");
            return (Criteria) this;
        }

        public Criteria andTestingprogramidNotEqualTo(Long value) {
            addCriterion("testingprogramid <>", value, "testingprogramid");
            return (Criteria) this;
        }

        public Criteria andTestingprogramidGreaterThan(Long value) {
            addCriterion("testingprogramid >", value, "testingprogramid");
            return (Criteria) this;
        }

        public Criteria andTestingprogramidGreaterThanOrEqualTo(Long value) {
            addCriterion("testingprogramid >=", value, "testingprogramid");
            return (Criteria) this;
        }

        public Criteria andTestingprogramidLessThan(Long value) {
            addCriterion("testingprogramid <", value, "testingprogramid");
            return (Criteria) this;
        }

        public Criteria andTestingprogramidLessThanOrEqualTo(Long value) {
            addCriterion("testingprogramid <=", value, "testingprogramid");
            return (Criteria) this;
        }

        public Criteria andTestingprogramidIn(List<Long> values) {
            addCriterion("testingprogramid in", values, "testingprogramid");
            return (Criteria) this;
        }

        public Criteria andTestingprogramidNotIn(List<Long> values) {
            addCriterion("testingprogramid not in", values, "testingprogramid");
            return (Criteria) this;
        }

        public Criteria andTestingprogramidBetween(Long value1, Long value2) {
            addCriterion("testingprogramid between", value1, value2, "testingprogramid");
            return (Criteria) this;
        }

        public Criteria andTestingprogramidNotBetween(Long value1, Long value2) {
            addCriterion("testingprogramid not between", value1, value2, "testingprogramid");
            return (Criteria) this;
        }

        public Criteria andContentareaidIsNull() {
            addCriterion("contentareaid is null");
            return (Criteria) this;
        }

        public Criteria andContentareaidIsNotNull() {
            addCriterion("contentareaid is not null");
            return (Criteria) this;
        }

        public Criteria andContentareaidEqualTo(Long value) {
            addCriterion("contentareaid =", value, "contentareaid");
            return (Criteria) this;
        }

        public Criteria andContentareaidNotEqualTo(Long value) {
            addCriterion("contentareaid <>", value, "contentareaid");
            return (Criteria) this;
        }

        public Criteria andContentareaidGreaterThan(Long value) {
            addCriterion("contentareaid >", value, "contentareaid");
            return (Criteria) this;
        }

        public Criteria andContentareaidGreaterThanOrEqualTo(Long value) {
            addCriterion("contentareaid >=", value, "contentareaid");
            return (Criteria) this;
        }

        public Criteria andContentareaidLessThan(Long value) {
            addCriterion("contentareaid <", value, "contentareaid");
            return (Criteria) this;
        }

        public Criteria andContentareaidLessThanOrEqualTo(Long value) {
            addCriterion("contentareaid <=", value, "contentareaid");
            return (Criteria) this;
        }

        public Criteria andContentareaidIn(List<Long> values) {
            addCriterion("contentareaid in", values, "contentareaid");
            return (Criteria) this;
        }

        public Criteria andContentareaidNotIn(List<Long> values) {
            addCriterion("contentareaid not in", values, "contentareaid");
            return (Criteria) this;
        }

        public Criteria andContentareaidBetween(Long value1, Long value2) {
            addCriterion("contentareaid between", value1, value2, "contentareaid");
            return (Criteria) this;
        }

        public Criteria andContentareaidNotBetween(Long value1, Long value2) {
            addCriterion("contentareaid not between", value1, value2, "contentareaid");
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

        public Criteria andOriginationcodeIsNull() {
            addCriterion("originationcode is null");
            return (Criteria) this;
        }

        public Criteria andOriginationcodeIsNotNull() {
            addCriterion("originationcode is not null");
            return (Criteria) this;
        }

        public Criteria andOriginationcodeEqualTo(String value) {
            addCriterion("originationcode =", value, "originationcode");
            return (Criteria) this;
        }

        public Criteria andOriginationcodeNotEqualTo(String value) {
            addCriterion("originationcode <>", value, "originationcode");
            return (Criteria) this;
        }

        public Criteria andOriginationcodeGreaterThan(String value) {
            addCriterion("originationcode >", value, "originationcode");
            return (Criteria) this;
        }

        public Criteria andOriginationcodeGreaterThanOrEqualTo(String value) {
            addCriterion("originationcode >=", value, "originationcode");
            return (Criteria) this;
        }

        public Criteria andOriginationcodeLessThan(String value) {
            addCriterion("originationcode <", value, "originationcode");
            return (Criteria) this;
        }

        public Criteria andOriginationcodeLessThanOrEqualTo(String value) {
            addCriterion("originationcode <=", value, "originationcode");
            return (Criteria) this;
        }

        public Criteria andOriginationcodeLike(String value) {
            addCriterion("originationcode like", value, "originationcode");
            return (Criteria) this;
        }

        public Criteria andOriginationcodeNotLike(String value) {
            addCriterion("originationcode not like", value, "originationcode");
            return (Criteria) this;
        }

        public Criteria andOriginationcodeIn(List<String> values) {
            addCriterion("originationcode in", values, "originationcode");
            return (Criteria) this;
        }

        public Criteria andOriginationcodeNotIn(List<String> values) {
            addCriterion("originationcode not in", values, "originationcode");
            return (Criteria) this;
        }

        public Criteria andOriginationcodeBetween(String value1, String value2) {
            addCriterion("originationcode between", value1, value2, "originationcode");
            return (Criteria) this;
        }

        public Criteria andOriginationcodeNotBetween(String value1, String value2) {
            addCriterion("originationcode not between", value1, value2, "originationcode");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table public.stimulusvariant
     *
     * @mbggenerated do_not_delete_during_merge Thu Oct 25 01:06:31 CDT 2012
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table public.stimulusvariant
     *
     * @mbggenerated Thu Oct 25 01:06:31 CDT 2012
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