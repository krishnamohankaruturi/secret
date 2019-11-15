package edu.ku.cete.domain;

import java.util.ArrayList;
import java.util.List;

import edu.ku.cete.util.Criterion;

public class StudentsResponsesExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.studentsresponses
     *
     * @mbggenerated Mon Oct 22 15:42:42 CDT 2012
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.studentsresponses
     *
     * @mbggenerated Mon Oct 22 15:42:42 CDT 2012
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.studentsresponses
     *
     * @mbggenerated Mon Oct 22 15:42:42 CDT 2012
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.studentsresponses
     *
     * @mbggenerated Mon Oct 22 15:42:42 CDT 2012
     */
    public StudentsResponsesExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.studentsresponses
     *
     * @mbggenerated Mon Oct 22 15:42:42 CDT 2012
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.studentsresponses
     *
     * @mbggenerated Mon Oct 22 15:42:42 CDT 2012
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.studentsresponses
     *
     * @mbggenerated Mon Oct 22 15:42:42 CDT 2012
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.studentsresponses
     *
     * @mbggenerated Mon Oct 22 15:42:42 CDT 2012
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.studentsresponses
     *
     * @mbggenerated Mon Oct 22 15:42:42 CDT 2012
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.studentsresponses
     *
     * @mbggenerated Mon Oct 22 15:42:42 CDT 2012
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.studentsresponses
     *
     * @mbggenerated Mon Oct 22 15:42:42 CDT 2012
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.studentsresponses
     *
     * @mbggenerated Mon Oct 22 15:42:42 CDT 2012
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
     * This method corresponds to the database table public.studentsresponses
     *
     * @mbggenerated Mon Oct 22 15:42:42 CDT 2012
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.studentsresponses
     *
     * @mbggenerated Mon Oct 22 15:42:42 CDT 2012
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table public.studentsresponses
     *
     * @mbggenerated Mon Oct 22 15:42:42 CDT 2012
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

        public Criteria andStudentIdIsNull() {
            addCriterion("studentid is null");
            return (Criteria) this;
        }

        public Criteria andStudentIdIsNotNull() {
            addCriterion("studentid is not null");
            return (Criteria) this;
        }

        public Criteria andStudentIdEqualTo(Long value) {
            addCriterion("studentid =", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdNotEqualTo(Long value) {
            addCriterion("studentid <>", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdGreaterThan(Long value) {
            addCriterion("studentid >", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("studentid >=", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdLessThan(Long value) {
            addCriterion("studentid <", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdLessThanOrEqualTo(Long value) {
            addCriterion("studentid <=", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdIn(List<Long> values) {
            addCriterion("studentid in", values, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdNotIn(List<Long> values) {
            addCriterion("studentid not in", values, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdBetween(Long value1, Long value2) {
            addCriterion("studentid between", value1, value2, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdNotBetween(Long value1, Long value2) {
            addCriterion("studentid not between", value1, value2, "studentId");
            return (Criteria) this;
        }

        public Criteria andTestIdIsNull() {
            addCriterion("testid is null");
            return (Criteria) this;
        }

        public Criteria andTestIdIsNotNull() {
            addCriterion("testid is not null");
            return (Criteria) this;
        }

        public Criteria andTestIdEqualTo(Long value) {
            addCriterion("testid =", value, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdNotEqualTo(Long value) {
            addCriterion("testid <>", value, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdGreaterThan(Long value) {
            addCriterion("testid >", value, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdGreaterThanOrEqualTo(Long value) {
            addCriterion("testid >=", value, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdLessThan(Long value) {
            addCriterion("testid <", value, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdLessThanOrEqualTo(Long value) {
            addCriterion("testid <=", value, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdIn(List<Long> values) {
            addCriterion("testid in", values, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdNotIn(List<Long> values) {
            addCriterion("testid not in", values, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdBetween(Long value1, Long value2) {
            addCriterion("testid between", value1, value2, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdNotBetween(Long value1, Long value2) {
            addCriterion("testid not between", value1, value2, "testId");
            return (Criteria) this;
        }

        public Criteria andTaskVariantIdIsNull() {
            addCriterion("taskvariantid is null");
            return (Criteria) this;
        }

        public Criteria andTaskVariantIdIsNotNull() {
            addCriterion("taskvariantid is not null");
            return (Criteria) this;
        }

        public Criteria andTaskVariantIdEqualTo(Long value) {
            addCriterion("taskvariantid =", value, "taskVariantId");
            return (Criteria) this;
        }

        public Criteria andTaskVariantIdNotEqualTo(Long value) {
            addCriterion("taskvariantid <>", value, "taskVariantId");
            return (Criteria) this;
        }

        public Criteria andTaskVariantIdGreaterThan(Long value) {
            addCriterion("taskvariantid >", value, "taskVariantId");
            return (Criteria) this;
        }

        public Criteria andTaskVariantIdGreaterThanOrEqualTo(Long value) {
            addCriterion("taskvariantid >=", value, "taskVariantId");
            return (Criteria) this;
        }

        public Criteria andTaskVariantIdLessThan(Long value) {
            addCriterion("taskvariantid <", value, "taskVariantId");
            return (Criteria) this;
        }

        public Criteria andTaskVariantIdLessThanOrEqualTo(Long value) {
            addCriterion("taskvariantid <=", value, "taskVariantId");
            return (Criteria) this;
        }

        public Criteria andTaskVariantIdIn(List<Long> values) {
            addCriterion("taskvariantid in", values, "taskVariantId");
            return (Criteria) this;
        }

        public Criteria andTaskVariantIdNotIn(List<Long> values) {
            addCriterion("taskvariantid not in", values, "taskVariantId");
            return (Criteria) this;
        }

        public Criteria andTaskVariantIdBetween(Long value1, Long value2) {
            addCriterion("taskvariantid between", value1, value2, "taskVariantId");
            return (Criteria) this;
        }

        public Criteria andTaskVariantIdNotBetween(Long value1, Long value2) {
            addCriterion("taskvariantid not between", value1, value2, "taskVariantId");
            return (Criteria) this;
        }

        public Criteria andFoilIdIsNull() {
            addCriterion("foilid is null");
            return (Criteria) this;
        }

        public Criteria andFoilIdIsNotNull() {
            addCriterion("foilid is not null");
            return (Criteria) this;
        }

        public Criteria andFoilIdEqualTo(Long value) {
            addCriterion("foilid =", value, "foilId");
            return (Criteria) this;
        }

        public Criteria andFoilIdNotEqualTo(Long value) {
            addCriterion("foilid <>", value, "foilId");
            return (Criteria) this;
        }

        public Criteria andFoilIdGreaterThan(Long value) {
            addCriterion("foilid >", value, "foilId");
            return (Criteria) this;
        }

        public Criteria andFoilIdGreaterThanOrEqualTo(Long value) {
            addCriterion("foilid >=", value, "foilId");
            return (Criteria) this;
        }

        public Criteria andFoilIdLessThan(Long value) {
            addCriterion("foilid <", value, "foilId");
            return (Criteria) this;
        }

        public Criteria andFoilIdLessThanOrEqualTo(Long value) {
            addCriterion("foilid <=", value, "foilId");
            return (Criteria) this;
        }

        public Criteria andFoilIdIn(List<Long> values) {
            addCriterion("foilid in", values, "foilId");
            return (Criteria) this;
        }

        public Criteria andFoilIdNotIn(List<Long> values) {
            addCriterion("foilid not in", values, "foilId");
            return (Criteria) this;
        }

        public Criteria andFoilIdBetween(Long value1, Long value2) {
            addCriterion("foilid between", value1, value2, "foilId");
            return (Criteria) this;
        }

        public Criteria andFoilIdNotBetween(Long value1, Long value2) {
            addCriterion("foilid not between", value1, value2, "foilId");
            return (Criteria) this;
        }

        public Criteria andResponseIsNull() {
            addCriterion("response is null");
            return (Criteria) this;
        }

        public Criteria andResponseIsNotNull() {
            addCriterion("response is not null");
            return (Criteria) this;
        }

        public Criteria andResponseEqualTo(String value) {
            addCriterion("response =", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseNotEqualTo(String value) {
            addCriterion("response <>", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseGreaterThan(String value) {
            addCriterion("response >", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseGreaterThanOrEqualTo(String value) {
            addCriterion("response >=", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseLessThan(String value) {
            addCriterion("response <", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseLessThanOrEqualTo(String value) {
            addCriterion("response <=", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseLike(String value) {
            addCriterion("response like", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseNotLike(String value) {
            addCriterion("response not like", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseIn(List<String> values) {
            addCriterion("response in", values, "response");
            return (Criteria) this;
        }

        public Criteria andResponseNotIn(List<String> values) {
            addCriterion("response not in", values, "response");
            return (Criteria) this;
        }

        public Criteria andResponseBetween(String value1, String value2) {
            addCriterion("response between", value1, value2, "response");
            return (Criteria) this;
        }

        public Criteria andResponseNotBetween(String value1, String value2) {
            addCriterion("response not between", value1, value2, "response");
            return (Criteria) this;
        }

        public Criteria andStudentsTestsIdIsNull() {
            addCriterion("studentstestsid is null");
            return (Criteria) this;
        }

        public Criteria andStudentsTestsIdIsNotNull() {
            addCriterion("studentstestsid is not null");
            return (Criteria) this;
        }

        public Criteria andStudentsTestsIdEqualTo(Long value) {
            addCriterion("studentstestsid =", value, "studentsTestsId");
            return (Criteria) this;
        }

        public Criteria andStudentsTestsIdNotEqualTo(Long value) {
            addCriterion("studentstestsid <>", value, "studentsTestsId");
            return (Criteria) this;
        }

        public Criteria andStudentsTestsIdGreaterThan(Long value) {
            addCriterion("studentstestsid >", value, "studentsTestsId");
            return (Criteria) this;
        }

        public Criteria andStudentsTestsIdGreaterThanOrEqualTo(Long value) {
            addCriterion("studentstestsid >=", value, "studentsTestsId");
            return (Criteria) this;
        }

        public Criteria andStudentsTestsIdLessThan(Long value) {
            addCriterion("studentstestsid <", value, "studentsTestsId");
            return (Criteria) this;
        }

        public Criteria andStudentsTestsIdLessThanOrEqualTo(Long value) {
            addCriterion("studentstestsid <=", value, "studentsTestsId");
            return (Criteria) this;
        }

        public Criteria andStudentsTestsIdIn(List<Long> values) {
            addCriterion("studentstestsid in", values, "studentsTestsId");
            return (Criteria) this;
        }

        public Criteria andStudentsTestsIdNotIn(List<Long> values) {
            addCriterion("studentstestsid not in", values, "studentsTestsId");
            return (Criteria) this;
        }

        public Criteria andStudentsTestsIdBetween(Long value1, Long value2) {
            addCriterion("studentstestsid between", value1, value2, "studentsTestsId");
            return (Criteria) this;
        }

        public Criteria andStudentsTestsIdNotBetween(Long value1, Long value2) {
            addCriterion("studentstestsid not between", value1, value2, "studentsTestsId");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table public.studentsresponses
     *
     * @mbggenerated do_not_delete_during_merge Mon Oct 22 15:42:42 CDT 2012
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}