package edu.ku.cete.domain.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ku.cete.util.Criterion;

public class ContentFrameworkDetailExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
     */
    public ContentFrameworkDetailExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
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
     * This method corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
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

        public Criteria andSortorderIsNull() {
            addCriterion("sortorder is null");
            return (Criteria) this;
        }

        public Criteria andSortorderIsNotNull() {
            addCriterion("sortorder is not null");
            return (Criteria) this;
        }

        public Criteria andSortorderEqualTo(Long value) {
            addCriterion("sortorder =", value, "sortorder");
            return (Criteria) this;
        }

        public Criteria andSortorderNotEqualTo(Long value) {
            addCriterion("sortorder <>", value, "sortorder");
            return (Criteria) this;
        }

        public Criteria andSortorderGreaterThan(Long value) {
            addCriterion("sortorder >", value, "sortorder");
            return (Criteria) this;
        }

        public Criteria andSortorderGreaterThanOrEqualTo(Long value) {
            addCriterion("sortorder >=", value, "sortorder");
            return (Criteria) this;
        }

        public Criteria andSortorderLessThan(Long value) {
            addCriterion("sortorder <", value, "sortorder");
            return (Criteria) this;
        }

        public Criteria andSortorderLessThanOrEqualTo(Long value) {
            addCriterion("sortorder <=", value, "sortorder");
            return (Criteria) this;
        }

        public Criteria andSortorderIn(List<Long> values) {
            addCriterion("sortorder in", values, "sortorder");
            return (Criteria) this;
        }

        public Criteria andSortorderNotIn(List<Long> values) {
            addCriterion("sortorder not in", values, "sortorder");
            return (Criteria) this;
        }

        public Criteria andSortorderBetween(Long value1, Long value2) {
            addCriterion("sortorder between", value1, value2, "sortorder");
            return (Criteria) this;
        }

        public Criteria andSortorderNotBetween(Long value1, Long value2) {
            addCriterion("sortorder not between", value1, value2, "sortorder");
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

        public Criteria andContentcodeIsNull() {
            addCriterion("contentcode is null");
            return (Criteria) this;
        }

        public Criteria andContentcodeIsNotNull() {
            addCriterion("contentcode is not null");
            return (Criteria) this;
        }

        public Criteria andContentcodeEqualTo(String value) {
            addCriterion("contentcode =", value, "contentcode");
            return (Criteria) this;
        }

        public Criteria andContentcodeNotEqualTo(String value) {
            addCriterion("contentcode <>", value, "contentcode");
            return (Criteria) this;
        }

        public Criteria andContentcodeGreaterThan(String value) {
            addCriterion("contentcode >", value, "contentcode");
            return (Criteria) this;
        }

        public Criteria andContentcodeGreaterThanOrEqualTo(String value) {
            addCriterion("contentcode >=", value, "contentcode");
            return (Criteria) this;
        }

        public Criteria andContentcodeLessThan(String value) {
            addCriterion("contentcode <", value, "contentcode");
            return (Criteria) this;
        }

        public Criteria andContentcodeLessThanOrEqualTo(String value) {
            addCriterion("contentcode <=", value, "contentcode");
            return (Criteria) this;
        }

        public Criteria andContentcodeLike(String value) {
            addCriterion("contentcode like", value, "contentcode");
            return (Criteria) this;
        }

        public Criteria andContentcodeNotLike(String value) {
            addCriterion("contentcode not like", value, "contentcode");
            return (Criteria) this;
        }

        public Criteria andContentcodeIn(List<String> values) {
            addCriterion("contentcode in", values, "contentcode");
            return (Criteria) this;
        }

        public Criteria andContentcodeNotIn(List<String> values) {
            addCriterion("contentcode not in", values, "contentcode");
            return (Criteria) this;
        }

        public Criteria andContentcodeBetween(String value1, String value2) {
            addCriterion("contentcode between", value1, value2, "contentcode");
            return (Criteria) this;
        }

        public Criteria andContentcodeNotBetween(String value1, String value2) {
            addCriterion("contentcode not between", value1, value2, "contentcode");
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

        public Criteria andCommentsIsNull() {
            addCriterion("comments is null");
            return (Criteria) this;
        }

        public Criteria andCommentsIsNotNull() {
            addCriterion("comments is not null");
            return (Criteria) this;
        }

        public Criteria andCommentsEqualTo(String value) {
            addCriterion("comments =", value, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsNotEqualTo(String value) {
            addCriterion("comments <>", value, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsGreaterThan(String value) {
            addCriterion("comments >", value, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsGreaterThanOrEqualTo(String value) {
            addCriterion("comments >=", value, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsLessThan(String value) {
            addCriterion("comments <", value, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsLessThanOrEqualTo(String value) {
            addCriterion("comments <=", value, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsLike(String value) {
            addCriterion("comments like", value, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsNotLike(String value) {
            addCriterion("comments not like", value, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsIn(List<String> values) {
            addCriterion("comments in", values, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsNotIn(List<String> values) {
            addCriterion("comments not in", values, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsBetween(String value1, String value2) {
            addCriterion("comments between", value1, value2, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsNotBetween(String value1, String value2) {
            addCriterion("comments not between", value1, value2, "comments");
            return (Criteria) this;
        }

        public Criteria andFrameworklevelidIsNull() {
            addCriterion("frameworklevelid is null");
            return (Criteria) this;
        }

        public Criteria andFrameworklevelidIsNotNull() {
            addCriterion("frameworklevelid is not null");
            return (Criteria) this;
        }

        public Criteria andFrameworklevelidEqualTo(Long value) {
            addCriterion("frameworklevelid =", value, "frameworklevelid");
            return (Criteria) this;
        }

        public Criteria andFrameworklevelidNotEqualTo(Long value) {
            addCriterion("frameworklevelid <>", value, "frameworklevelid");
            return (Criteria) this;
        }

        public Criteria andFrameworklevelidGreaterThan(Long value) {
            addCriterion("frameworklevelid >", value, "frameworklevelid");
            return (Criteria) this;
        }

        public Criteria andFrameworklevelidGreaterThanOrEqualTo(Long value) {
            addCriterion("frameworklevelid >=", value, "frameworklevelid");
            return (Criteria) this;
        }

        public Criteria andFrameworklevelidLessThan(Long value) {
            addCriterion("frameworklevelid <", value, "frameworklevelid");
            return (Criteria) this;
        }

        public Criteria andFrameworklevelidLessThanOrEqualTo(Long value) {
            addCriterion("frameworklevelid <=", value, "frameworklevelid");
            return (Criteria) this;
        }

        public Criteria andFrameworklevelidIn(List<Long> values) {
            addCriterion("frameworklevelid in", values, "frameworklevelid");
            return (Criteria) this;
        }

        public Criteria andFrameworklevelidNotIn(List<Long> values) {
            addCriterion("frameworklevelid not in", values, "frameworklevelid");
            return (Criteria) this;
        }

        public Criteria andFrameworklevelidBetween(Long value1, Long value2) {
            addCriterion("frameworklevelid between", value1, value2, "frameworklevelid");
            return (Criteria) this;
        }

        public Criteria andFrameworklevelidNotBetween(Long value1, Long value2) {
            addCriterion("frameworklevelid not between", value1, value2, "frameworklevelid");
            return (Criteria) this;
        }

        public Criteria andContentframeworkidIsNull() {
            addCriterion("contentframeworkid is null");
            return (Criteria) this;
        }

        public Criteria andContentframeworkidIsNotNull() {
            addCriterion("contentframeworkid is not null");
            return (Criteria) this;
        }

        public Criteria andContentframeworkidEqualTo(Long value) {
            addCriterion("contentframeworkid =", value, "contentframeworkid");
            return (Criteria) this;
        }

        public Criteria andContentframeworkidNotEqualTo(Long value) {
            addCriterion("contentframeworkid <>", value, "contentframeworkid");
            return (Criteria) this;
        }

        public Criteria andContentframeworkidGreaterThan(Long value) {
            addCriterion("contentframeworkid >", value, "contentframeworkid");
            return (Criteria) this;
        }

        public Criteria andContentframeworkidGreaterThanOrEqualTo(Long value) {
            addCriterion("contentframeworkid >=", value, "contentframeworkid");
            return (Criteria) this;
        }

        public Criteria andContentframeworkidLessThan(Long value) {
            addCriterion("contentframeworkid <", value, "contentframeworkid");
            return (Criteria) this;
        }

        public Criteria andContentframeworkidLessThanOrEqualTo(Long value) {
            addCriterion("contentframeworkid <=", value, "contentframeworkid");
            return (Criteria) this;
        }

        public Criteria andContentframeworkidIn(List<Long> values) {
            addCriterion("contentframeworkid in", values, "contentframeworkid");
            return (Criteria) this;
        }

        public Criteria andContentframeworkidNotIn(List<Long> values) {
            addCriterion("contentframeworkid not in", values, "contentframeworkid");
            return (Criteria) this;
        }

        public Criteria andContentframeworkidBetween(Long value1, Long value2) {
            addCriterion("contentframeworkid between", value1, value2, "contentframeworkid");
            return (Criteria) this;
        }

        public Criteria andContentframeworkidNotBetween(Long value1, Long value2) {
            addCriterion("contentframeworkid not between", value1, value2, "contentframeworkid");
            return (Criteria) this;
        }

        public Criteria andParentcontentframeworkdetailidIsNull() {
            addCriterion("parentcontentframeworkdetailid is null");
            return (Criteria) this;
        }

        public Criteria andParentcontentframeworkdetailidIsNotNull() {
            addCriterion("parentcontentframeworkdetailid is not null");
            return (Criteria) this;
        }

        public Criteria andParentcontentframeworkdetailidEqualTo(Long value) {
            addCriterion("parentcontentframeworkdetailid =", value, "parentcontentframeworkdetailid");
            return (Criteria) this;
        }

        public Criteria andParentcontentframeworkdetailidNotEqualTo(Long value) {
            addCriterion("parentcontentframeworkdetailid <>", value, "parentcontentframeworkdetailid");
            return (Criteria) this;
        }

        public Criteria andParentcontentframeworkdetailidGreaterThan(Long value) {
            addCriterion("parentcontentframeworkdetailid >", value, "parentcontentframeworkdetailid");
            return (Criteria) this;
        }

        public Criteria andParentcontentframeworkdetailidGreaterThanOrEqualTo(Long value) {
            addCriterion("parentcontentframeworkdetailid >=", value, "parentcontentframeworkdetailid");
            return (Criteria) this;
        }

        public Criteria andParentcontentframeworkdetailidLessThan(Long value) {
            addCriterion("parentcontentframeworkdetailid <", value, "parentcontentframeworkdetailid");
            return (Criteria) this;
        }

        public Criteria andParentcontentframeworkdetailidLessThanOrEqualTo(Long value) {
            addCriterion("parentcontentframeworkdetailid <=", value, "parentcontentframeworkdetailid");
            return (Criteria) this;
        }

        public Criteria andParentcontentframeworkdetailidIn(List<Long> values) {
            addCriterion("parentcontentframeworkdetailid in", values, "parentcontentframeworkdetailid");
            return (Criteria) this;
        }

        public Criteria andParentcontentframeworkdetailidNotIn(List<Long> values) {
            addCriterion("parentcontentframeworkdetailid not in", values, "parentcontentframeworkdetailid");
            return (Criteria) this;
        }

        public Criteria andParentcontentframeworkdetailidBetween(Long value1, Long value2) {
            addCriterion("parentcontentframeworkdetailid between", value1, value2, "parentcontentframeworkdetailid");
            return (Criteria) this;
        }

        public Criteria andParentcontentframeworkdetailidNotBetween(Long value1, Long value2) {
            addCriterion("parentcontentframeworkdetailid not between", value1, value2, "parentcontentframeworkdetailid");
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
     * This class corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated do_not_delete_during_merge Thu Oct 25 18:12:59 CDT 2012
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}