package edu.ku.cete.domain;

public class StudentResponseScoreKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column studentresponsescore.studentstestsectionsid
	 * @mbggenerated  Thu Apr 07 14:01:42 CDT 2016
	 */
	private Long studentsTestSectionsId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column studentresponsescore.taskvariantexternalid
	 * @mbggenerated  Thu Apr 07 14:01:42 CDT 2016
	 */
	private Long taskVariantExternalId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column studentresponsescore.raterid
	 * @mbggenerated  Thu Apr 07 14:01:42 CDT 2016
	 */
	private Long raterId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column studentresponsescore.dimension
	 * @mbggenerated  Thu Apr 07 14:01:42 CDT 2016
	 */
	private String dimension;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column studentresponsescore.studentstestsectionsid
	 * @return  the value of studentresponsescore.studentstestsectionsid
	 * @mbggenerated  Thu Apr 07 14:01:42 CDT 2016
	 */
	public Long getStudentsTestSectionsId() {
		return studentsTestSectionsId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column studentresponsescore.studentstestsectionsid
	 * @param studentsTestSectionsId  the value for studentresponsescore.studentstestsectionsid
	 * @mbggenerated  Thu Apr 07 14:01:42 CDT 2016
	 */
	public void setStudentsTestSectionsId(Long studentsTestSectionsId) {
		this.studentsTestSectionsId = studentsTestSectionsId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column studentresponsescore.taskvariantexternalid
	 * @return  the value of studentresponsescore.taskvariantexternalid
	 * @mbggenerated  Thu Apr 07 14:01:42 CDT 2016
	 */
	public Long getTaskVariantExternalId() {
		return taskVariantExternalId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column studentresponsescore.taskvariantexternalid
	 * @param taskVariantExternalId  the value for studentresponsescore.taskvariantexternalid
	 * @mbggenerated  Thu Apr 07 14:01:42 CDT 2016
	 */
	public void setTaskVariantExternalId(Long taskVariantExternalId) {
		this.taskVariantExternalId = taskVariantExternalId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column studentresponsescore.raterid
	 * @return  the value of studentresponsescore.raterid
	 * @mbggenerated  Thu Apr 07 14:01:42 CDT 2016
	 */
	public Long getRaterId() {
		return raterId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column studentresponsescore.raterid
	 * @param raterId  the value for studentresponsescore.raterid
	 * @mbggenerated  Thu Apr 07 14:01:42 CDT 2016
	 */
	public void setRaterId(Long raterId) {
		this.raterId = raterId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column studentresponsescore.dimension
	 * @return  the value of studentresponsescore.dimension
	 * @mbggenerated  Thu Apr 07 14:01:42 CDT 2016
	 */
	public String getDimension() {
		return dimension;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column studentresponsescore.dimension
	 * @param dimension  the value for studentresponsescore.dimension
	 * @mbggenerated  Thu Apr 07 14:01:42 CDT 2016
	 */
	public void setDimension(String dimension) {
		this.dimension = dimension == null ? null : dimension.trim();
	}
}