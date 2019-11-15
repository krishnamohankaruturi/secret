package edu.ku.cete.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentsTestSectionsCriteria {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.studentstestsections
     *
     * @mbggenerated Wed Dec 12 15:45:42 CST 2012
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.studentstestsections
     *
     * @mbggenerated Wed Dec 12 15:45:42 CST 2012
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.studentstestsections
     *
     * @mbggenerated Wed Dec 12 15:45:42 CST 2012
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.studentstestsections
     *
     * @mbggenerated Wed Dec 12 15:45:42 CST 2012
     */
    public StudentsTestSectionsCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.studentstestsections
     *
     * @mbggenerated Wed Dec 12 15:45:42 CST 2012
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.studentstestsections
     *
     * @mbggenerated Wed Dec 12 15:45:42 CST 2012
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.studentstestsections
     *
     * @mbggenerated Wed Dec 12 15:45:42 CST 2012
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.studentstestsections
     *
     * @mbggenerated Wed Dec 12 15:45:42 CST 2012
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.studentstestsections
     *
     * @mbggenerated Wed Dec 12 15:45:42 CST 2012
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.studentstestsections
     *
     * @mbggenerated Wed Dec 12 15:45:42 CST 2012
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.studentstestsections
     *
     * @mbggenerated Wed Dec 12 15:45:42 CST 2012
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.studentstestsections
     *
     * @mbggenerated Wed Dec 12 15:45:42 CST 2012
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
     * This method corresponds to the database table public.studentstestsections
     *
     * @mbggenerated Wed Dec 12 15:45:42 CST 2012
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.studentstestsections
     *
     * @mbggenerated Wed Dec 12 15:45:42 CST 2012
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table public.studentstestsections
     *
     * @mbggenerated Wed Dec 12 15:45:42 CST 2012
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

        public Criteria andStudentsTestIdIsNull() {
            addCriterion("studentstestid is null");
            return (Criteria) this;
        }

        public Criteria andStudentsTestIdIsNotNull() {
            addCriterion("studentstestid is not null");
            return (Criteria) this;
        }

        public Criteria andStudentsTestIdEqualTo(Long value) {
            addCriterion("studentstestid =", value, "studentsTestId");
            return (Criteria) this;
        }

        public Criteria andStudentsTestIdNotEqualTo(Long value) {
            addCriterion("studentstestid <>", value, "studentsTestId");
            return (Criteria) this;
        }

        public Criteria andStudentsTestIdGreaterThan(Long value) {
            addCriterion("studentstestid >", value, "studentsTestId");
            return (Criteria) this;
        }

        public Criteria andStudentsTestIdGreaterThanOrEqualTo(Long value) {
            addCriterion("studentstestid >=", value, "studentsTestId");
            return (Criteria) this;
        }

        public Criteria andStudentsTestIdLessThan(Long value) {
            addCriterion("studentstestid <", value, "studentsTestId");
            return (Criteria) this;
        }

        public Criteria andStudentsTestIdLessThanOrEqualTo(Long value) {
            addCriterion("studentstestid <=", value, "studentsTestId");
            return (Criteria) this;
        }

        public Criteria andStudentsTestIdIn(List<Long> values) {
            addCriterion("studentstestid in", values, "studentsTestId");
            return (Criteria) this;
        }

        public Criteria andStudentsTestIdNotIn(List<Long> values) {
            addCriterion("studentstestid not in", values, "studentsTestId");
            return (Criteria) this;
        }

        public Criteria andStudentsTestIdBetween(Long value1, Long value2) {
            addCriterion("studentstestid between", value1, value2, "studentsTestId");
            return (Criteria) this;
        }

        public Criteria andStudentsTestIdNotBetween(Long value1, Long value2) {
            addCriterion("studentstestid not between", value1, value2, "studentsTestId");
            return (Criteria) this;
        }

        public Criteria andTestSectionIdIsNull() {
            addCriterion("testsectionid is null");
            return (Criteria) this;
        }

        public Criteria andTestSectionIdIsNotNull() {
            addCriterion("testsectionid is not null");
            return (Criteria) this;
        }

        public Criteria andTestSectionIdEqualTo(Long value) {
            addCriterion("testsectionid =", value, "testSectionId");
            return (Criteria) this;
        }

        public Criteria andTestSectionIdNotEqualTo(Long value) {
            addCriterion("testsectionid <>", value, "testSectionId");
            return (Criteria) this;
        }

        public Criteria andTestSectionIdGreaterThan(Long value) {
            addCriterion("testsectionid >", value, "testSectionId");
            return (Criteria) this;
        }

        public Criteria andTestSectionIdGreaterThanOrEqualTo(Long value) {
            addCriterion("testsectionid >=", value, "testSectionId");
            return (Criteria) this;
        }

        public Criteria andTestSectionIdLessThan(Long value) {
            addCriterion("testsectionid <", value, "testSectionId");
            return (Criteria) this;
        }

        public Criteria andTestSectionIdLessThanOrEqualTo(Long value) {
            addCriterion("testsectionid <=", value, "testSectionId");
            return (Criteria) this;
        }

        public Criteria andTestSectionIdIn(List<Long> values) {
            addCriterion("testsectionid in", values, "testSectionId");
            return (Criteria) this;
        }

        public Criteria andTestSectionIdNotIn(List<Long> values) {
            addCriterion("testsectionid not in", values, "testSectionId");
            return (Criteria) this;
        }

        public Criteria andTestSectionIdBetween(Long value1, Long value2) {
            addCriterion("testsectionid between", value1, value2, "testSectionId");
            return (Criteria) this;
        }

        public Criteria andTestSectionIdNotBetween(Long value1, Long value2) {
            addCriterion("testsectionid not between", value1, value2, "testSectionId");
            return (Criteria) this;
        }

        public Criteria andTicketNoIsNull() {
            addCriterion("ticketno is null");
            return (Criteria) this;
        }

        public Criteria andTicketNoIsNotNull() {
            addCriterion("ticketno is not null");
            return (Criteria) this;
        }

        public Criteria andTicketNoEqualTo(String value) {
            addCriterion("ticketno =", value, "ticketNo");
            return (Criteria) this;
        }

        public Criteria andTicketNoNotEqualTo(String value) {
            addCriterion("ticketno <>", value, "ticketNo");
            return (Criteria) this;
        }

        public Criteria andTicketNoGreaterThan(String value) {
            addCriterion("ticketno >", value, "ticketNo");
            return (Criteria) this;
        }

        public Criteria andTicketNoGreaterThanOrEqualTo(String value) {
            addCriterion("ticketno >=", value, "ticketNo");
            return (Criteria) this;
        }

        public Criteria andTicketNoLessThan(String value) {
            addCriterion("ticketno <", value, "ticketNo");
            return (Criteria) this;
        }

        public Criteria andTicketNoLessThanOrEqualTo(String value) {
            addCriterion("ticketno <=", value, "ticketNo");
            return (Criteria) this;
        }

        public Criteria andTicketNoLike(String value) {
            addCriterion("ticketno like", value, "ticketNo");
            return (Criteria) this;
        }

        public Criteria andTicketNoNotLike(String value) {
            addCriterion("ticketno not like", value, "ticketNo");
            return (Criteria) this;
        }

        public Criteria andTicketNoIn(List<String> values) {
            addCriterion("ticketno in", values, "ticketNo");
            return (Criteria) this;
        }

        public Criteria andTicketNoNotIn(List<String> values) {
            addCriterion("ticketno not in", values, "ticketNo");
            return (Criteria) this;
        }

        public Criteria andTicketNoBetween(String value1, String value2) {
            addCriterion("ticketno between", value1, value2, "ticketNo");
            return (Criteria) this;
        }

        public Criteria andTicketNoNotBetween(String value1, String value2) {
            addCriterion("ticketno not between", value1, value2, "ticketNo");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIsNull() {
            addCriterion("createddate is null");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIsNotNull() {
            addCriterion("createddate is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedDateEqualTo(Date value) {
            addCriterion("createddate =", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotEqualTo(Date value) {
            addCriterion("createddate <>", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateGreaterThan(Date value) {
            addCriterion("createddate >", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateGreaterThanOrEqualTo(Date value) {
            addCriterion("createddate >=", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateLessThan(Date value) {
            addCriterion("createddate <", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateLessThanOrEqualTo(Date value) {
            addCriterion("createddate <=", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIn(List<Date> values) {
            addCriterion("createddate in", values, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotIn(List<Date> values) {
            addCriterion("createddate not in", values, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateBetween(Date value1, Date value2) {
            addCriterion("createddate between", value1, value2, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotBetween(Date value1, Date value2) {
            addCriterion("createddate not between", value1, value2, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedUserIsNull() {
            addCriterion("createduser is null");
            return (Criteria) this;
        }

        public Criteria andCreatedUserIsNotNull() {
            addCriterion("createduser is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedUserEqualTo(Integer value) {
            addCriterion("createduser =", value, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserNotEqualTo(Integer value) {
            addCriterion("createduser <>", value, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserGreaterThan(Integer value) {
            addCriterion("createduser >", value, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("createduser >=", value, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserLessThan(Integer value) {
            addCriterion("createduser <", value, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserLessThanOrEqualTo(Integer value) {
            addCriterion("createduser <=", value, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserIn(List<Integer> values) {
            addCriterion("createduser in", values, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserNotIn(List<Integer> values) {
            addCriterion("createduser not in", values, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserBetween(Integer value1, Integer value2) {
            addCriterion("createduser between", value1, value2, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserNotBetween(Integer value1, Integer value2) {
            addCriterion("createduser not between", value1, value2, "createdUser");
            return (Criteria) this;
        }

        public Criteria andActiveFlagIsNull() {
            addCriterion("activeflag is null");
            return (Criteria) this;
        }

        public Criteria andActiveFlagIsNotNull() {
            addCriterion("activeflag is not null");
            return (Criteria) this;
        }

        public Criteria andActiveFlagEqualTo(Boolean value) {
            addCriterion("activeflag =", value, "activeFlag");
            return (Criteria) this;
        }

        public Criteria andActiveFlagNotEqualTo(Boolean value) {
            addCriterion("activeflag <>", value, "activeFlag");
            return (Criteria) this;
        }

        public Criteria andActiveFlagGreaterThan(Boolean value) {
            addCriterion("activeflag >", value, "activeFlag");
            return (Criteria) this;
        }

        public Criteria andActiveFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("activeflag >=", value, "activeFlag");
            return (Criteria) this;
        }

        public Criteria andActiveFlagLessThan(Boolean value) {
            addCriterion("activeflag <", value, "activeFlag");
            return (Criteria) this;
        }

        public Criteria andActiveFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("activeflag <=", value, "activeFlag");
            return (Criteria) this;
        }

        public Criteria andActiveFlagIn(List<Boolean> values) {
            addCriterion("activeflag in", values, "activeFlag");
            return (Criteria) this;
        }

        public Criteria andActiveFlagNotIn(List<Boolean> values) {
            addCriterion("activeflag not in", values, "activeFlag");
            return (Criteria) this;
        }

        public Criteria andActiveFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("activeflag between", value1, value2, "activeFlag");
            return (Criteria) this;
        }

        public Criteria andActiveFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("activeflag not between", value1, value2, "activeFlag");
            return (Criteria) this;
        }

        public Criteria andModifiedUserIsNull() {
            addCriterion("modifieduser is null");
            return (Criteria) this;
        }

        public Criteria andModifiedUserIsNotNull() {
            addCriterion("modifieduser is not null");
            return (Criteria) this;
        }

        public Criteria andModifiedUserEqualTo(Integer value) {
            addCriterion("modifieduser =", value, "modifiedUser");
            return (Criteria) this;
        }

        public Criteria andModifiedUserNotEqualTo(Integer value) {
            addCriterion("modifieduser <>", value, "modifiedUser");
            return (Criteria) this;
        }

        public Criteria andModifiedUserGreaterThan(Integer value) {
            addCriterion("modifieduser >", value, "modifiedUser");
            return (Criteria) this;
        }

        public Criteria andModifiedUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("modifieduser >=", value, "modifiedUser");
            return (Criteria) this;
        }

        public Criteria andModifiedUserLessThan(Integer value) {
            addCriterion("modifieduser <", value, "modifiedUser");
            return (Criteria) this;
        }

        public Criteria andModifiedUserLessThanOrEqualTo(Integer value) {
            addCriterion("modifieduser <=", value, "modifiedUser");
            return (Criteria) this;
        }

        public Criteria andModifiedUserIn(List<Integer> values) {
            addCriterion("modifieduser in", values, "modifiedUser");
            return (Criteria) this;
        }

        public Criteria andModifiedUserNotIn(List<Integer> values) {
            addCriterion("modifieduser not in", values, "modifiedUser");
            return (Criteria) this;
        }

        public Criteria andModifiedUserBetween(Integer value1, Integer value2) {
            addCriterion("modifieduser between", value1, value2, "modifiedUser");
            return (Criteria) this;
        }

        public Criteria andModifiedUserNotBetween(Integer value1, Integer value2) {
            addCriterion("modifieduser not between", value1, value2, "modifiedUser");
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
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table public.studentstestsections
     *
     * @mbggenerated do_not_delete_during_merge Wed Dec 12 15:45:42 CST 2012
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }

        public Criteria andTicketNoLikeInsensitive(String value) {
            addCriterion("upper(ticketno) like", value.toUpperCase(), "ticketNo");
            return this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table public.studentstestsections
     *
     * @mbggenerated Wed Dec 12 15:45:42 CST 2012
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