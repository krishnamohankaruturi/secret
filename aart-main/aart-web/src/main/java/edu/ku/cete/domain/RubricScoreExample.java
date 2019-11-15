package edu.ku.cete.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class RubricScoreExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rubricscore
	 * @mbggenerated  Sun Sep 22 02:53:50 CDT 2013
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rubricscore
	 * @mbggenerated  Sun Sep 22 02:53:50 CDT 2013
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rubricscore
	 * @mbggenerated  Sun Sep 22 02:53:50 CDT 2013
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rubricscore
	 * @mbggenerated  Sun Sep 22 02:53:50 CDT 2013
	 */
	public RubricScoreExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rubricscore
	 * @mbggenerated  Sun Sep 22 02:53:50 CDT 2013
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rubricscore
	 * @mbggenerated  Sun Sep 22 02:53:50 CDT 2013
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rubricscore
	 * @mbggenerated  Sun Sep 22 02:53:50 CDT 2013
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rubricscore
	 * @mbggenerated  Sun Sep 22 02:53:50 CDT 2013
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rubricscore
	 * @mbggenerated  Sun Sep 22 02:53:50 CDT 2013
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rubricscore
	 * @mbggenerated  Sun Sep 22 02:53:50 CDT 2013
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rubricscore
	 * @mbggenerated  Sun Sep 22 02:53:50 CDT 2013
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rubricscore
	 * @mbggenerated  Sun Sep 22 02:53:50 CDT 2013
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rubricscore
	 * @mbggenerated  Sun Sep 22 02:53:50 CDT 2013
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rubricscore
	 * @mbggenerated  Sun Sep 22 02:53:50 CDT 2013
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table rubricscore
	 * @mbggenerated  Sun Sep 22 02:53:50 CDT 2013
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

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
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

		public Criteria andStudentTestIdIsNull() {
			addCriterion("studenttestid is null");
			return (Criteria) this;
		}

		public Criteria andStudentTestIdIsNotNull() {
			addCriterion("studenttestid is not null");
			return (Criteria) this;
		}

		public Criteria andStudentTestIdEqualTo(Long value) {
			addCriterion("studenttestid =", value, "studentTestId");
			return (Criteria) this;
		}

		public Criteria andStudentTestIdNotEqualTo(Long value) {
			addCriterion("studenttestid <>", value, "studentTestId");
			return (Criteria) this;
		}

		public Criteria andStudentTestIdGreaterThan(Long value) {
			addCriterion("studenttestid >", value, "studentTestId");
			return (Criteria) this;
		}

		public Criteria andStudentTestIdGreaterThanOrEqualTo(Long value) {
			addCriterion("studenttestid >=", value, "studentTestId");
			return (Criteria) this;
		}

		public Criteria andStudentTestIdLessThan(Long value) {
			addCriterion("studenttestid <", value, "studentTestId");
			return (Criteria) this;
		}

		public Criteria andStudentTestIdLessThanOrEqualTo(Long value) {
			addCriterion("studenttestid <=", value, "studentTestId");
			return (Criteria) this;
		}

		public Criteria andStudentTestIdIn(List<Long> values) {
			addCriterion("studenttestid in", values, "studentTestId");
			return (Criteria) this;
		}

		public Criteria andStudentTestIdNotIn(List<Long> values) {
			addCriterion("studenttestid not in", values, "studentTestId");
			return (Criteria) this;
		}

		public Criteria andStudentTestIdBetween(Long value1, Long value2) {
			addCriterion("studenttestid between", value1, value2,
					"studentTestId");
			return (Criteria) this;
		}

		public Criteria andStudentTestIdNotBetween(Long value1, Long value2) {
			addCriterion("studenttestid not between", value1, value2,
					"studentTestId");
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
			addCriterion("taskvariantid between", value1, value2,
					"taskVariantId");
			return (Criteria) this;
		}

		public Criteria andTaskVariantIdNotBetween(Long value1, Long value2) {
			addCriterion("taskvariantid not between", value1, value2,
					"taskVariantId");
			return (Criteria) this;
		}

		public Criteria andRubricScoreIsNull() {
			addCriterion("rubricscore is null");
			return (Criteria) this;
		}

		public Criteria andRubricScoreIsNotNull() {
			addCriterion("rubricscore is not null");
			return (Criteria) this;
		}

		public Criteria andRubricScoreEqualTo(BigDecimal value) {
			addCriterion("rubricscore =", value, "rubricScore");
			return (Criteria) this;
		}

		public Criteria andRubricScoreNotEqualTo(BigDecimal value) {
			addCriterion("rubricscore <>", value, "rubricScore");
			return (Criteria) this;
		}

		public Criteria andRubricScoreGreaterThan(BigDecimal value) {
			addCriterion("rubricscore >", value, "rubricScore");
			return (Criteria) this;
		}

		public Criteria andRubricScoreGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("rubricscore >=", value, "rubricScore");
			return (Criteria) this;
		}

		public Criteria andRubricScoreLessThan(BigDecimal value) {
			addCriterion("rubricscore <", value, "rubricScore");
			return (Criteria) this;
		}

		public Criteria andRubricScoreLessThanOrEqualTo(BigDecimal value) {
			addCriterion("rubricscore <=", value, "rubricScore");
			return (Criteria) this;
		}

		public Criteria andRubricScoreIn(List<BigDecimal> values) {
			addCriterion("rubricscore in", values, "rubricScore");
			return (Criteria) this;
		}

		public Criteria andRubricScoreNotIn(List<BigDecimal> values) {
			addCriterion("rubricscore not in", values, "rubricScore");
			return (Criteria) this;
		}

		public Criteria andRubricScoreBetween(BigDecimal value1,
				BigDecimal value2) {
			addCriterion("rubricscore between", value1, value2, "rubricScore");
			return (Criteria) this;
		}

		public Criteria andRubricScoreNotBetween(BigDecimal value1,
				BigDecimal value2) {
			addCriterion("rubricscore not between", value1, value2,
					"rubricScore");
			return (Criteria) this;
		}

		public Criteria andDateIsNull() {
			addCriterion("date is null");
			return (Criteria) this;
		}

		public Criteria andDateIsNotNull() {
			addCriterion("date is not null");
			return (Criteria) this;
		}

		public Criteria andDateEqualTo(Date value) {
			addCriterion("date =", value, "date");
			return (Criteria) this;
		}

		public Criteria andDateNotEqualTo(Date value) {
			addCriterion("date <>", value, "date");
			return (Criteria) this;
		}

		public Criteria andDateGreaterThan(Date value) {
			addCriterion("date >", value, "date");
			return (Criteria) this;
		}

		public Criteria andDateGreaterThanOrEqualTo(Date value) {
			addCriterion("date >=", value, "date");
			return (Criteria) this;
		}

		public Criteria andDateLessThan(Date value) {
			addCriterion("date <", value, "date");
			return (Criteria) this;
		}

		public Criteria andDateLessThanOrEqualTo(Date value) {
			addCriterion("date <=", value, "date");
			return (Criteria) this;
		}

		public Criteria andDateIn(List<Date> values) {
			addCriterion("date in", values, "date");
			return (Criteria) this;
		}

		public Criteria andDateNotIn(List<Date> values) {
			addCriterion("date not in", values, "date");
			return (Criteria) this;
		}

		public Criteria andDateBetween(Date value1, Date value2) {
			addCriterion("date between", value1, value2, "date");
			return (Criteria) this;
		}

		public Criteria andDateNotBetween(Date value1, Date value2) {
			addCriterion("date not between", value1, value2, "date");
			return (Criteria) this;
		}

		public Criteria andRubricInfoIdsIsNull() {
			addCriterion("rubricinfoids is null");
			return (Criteria) this;
		}

		public Criteria andRubricInfoIdsIsNotNull() {
			addCriterion("rubricinfoids is not null");
			return (Criteria) this;
		}

		public Criteria andRubricInfoIdsEqualTo(String value) {
			addCriterion("rubricinfoids =", value, "rubricInfoIds");
			return (Criteria) this;
		}

		public Criteria andRubricInfoIdsNotEqualTo(String value) {
			addCriterion("rubricinfoids <>", value, "rubricInfoIds");
			return (Criteria) this;
		}

		public Criteria andRubricInfoIdsGreaterThan(String value) {
			addCriterion("rubricinfoids >", value, "rubricInfoIds");
			return (Criteria) this;
		}

		public Criteria andRubricInfoIdsGreaterThanOrEqualTo(String value) {
			addCriterion("rubricinfoids >=", value, "rubricInfoIds");
			return (Criteria) this;
		}

		public Criteria andRubricInfoIdsLessThan(String value) {
			addCriterion("rubricinfoids <", value, "rubricInfoIds");
			return (Criteria) this;
		}

		public Criteria andRubricInfoIdsLessThanOrEqualTo(String value) {
			addCriterion("rubricinfoids <=", value, "rubricInfoIds");
			return (Criteria) this;
		}

		public Criteria andRubricInfoIdsLike(String value) {
			addCriterion("rubricinfoids like", value, "rubricInfoIds");
			return (Criteria) this;
		}

		public Criteria andRubricInfoIdsNotLike(String value) {
			addCriterion("rubricinfoids not like", value, "rubricInfoIds");
			return (Criteria) this;
		}

		public Criteria andRubricInfoIdsIn(List<String> values) {
			addCriterion("rubricinfoids in", values, "rubricInfoIds");
			return (Criteria) this;
		}

		public Criteria andRubricInfoIdsNotIn(List<String> values) {
			addCriterion("rubricinfoids not in", values, "rubricInfoIds");
			return (Criteria) this;
		}

		public Criteria andRubricInfoIdsBetween(String value1, String value2) {
			addCriterion("rubricinfoids between", value1, value2,
					"rubricInfoIds");
			return (Criteria) this;
		}

		public Criteria andRubricInfoIdsNotBetween(String value1, String value2) {
			addCriterion("rubricinfoids not between", value1, value2,
					"rubricInfoIds");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table rubricscore
	 * @mbggenerated  Sun Sep 22 02:53:50 CDT 2013
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

		protected Criterion(String condition, Object value, Object secondValue,
				String typeHandler) {
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

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table rubricscore
     *
     * @mbggenerated do_not_delete_during_merge Mon Sep 16 12:11:27 CDT 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}