package edu.ku.cete.domain.report;

public class NodeReportKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.node_report.student_id
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    private Long studentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.node_report.node_id
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    private String nodeKey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.node_report.students_tests_id
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    private Long studentsTestsId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.node_report.test_id
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    private Long testId;

    /**
     * studentNodeReportKey.
     */
    private StudentNodeReportKey studentNodeReportKey;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.node_report.student_id
     *
     * @return the value of public.node_report.student_id
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public Long getStudentId() {
        return studentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.node_report.student_id
     *
     * @param studentId the value for public.node_report.student_id
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.node_report.node_id
     *
     * @return the value of public.node_report.node_id
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public String getNodeKey() {
        return nodeKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.node_report.node_id
     *
     * @param nodeKey the value for public.node_report.node_id
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.node_report.students_tests_id
     *
     * @return the value of public.node_report.students_tests_id
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public Long getStudentsTestsId() {
        return studentsTestsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.node_report.students_tests_id
     *
     * @param studentsTestsId the value for public.node_report.students_tests_id
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public void setStudentsTestsId(Long studentsTestsId) {
        this.studentsTestsId = studentsTestsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.node_report.test_id
     *
     * @return the value of public.node_report.test_id
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public Long getTestId() {
        return testId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.node_report.test_id
     *
     * @param testId the value for public.node_report.test_id
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public void setTestId(Long testId) {
        this.testId = testId;
    }

    /**
     * @return
     */
    public StudentNodeReportKey getStudentNodeReportKey() {
    	if(studentNodeReportKey == null) {
    		setStudentNodeReportKey();
    	}
		return studentNodeReportKey;    	
    }
    /**
     * @param studentNodeReportKey
     * @return
     */
    public void setStudentNodeReportKey(
    		StudentNodeReportKey studentNodeReportKey) {
    	this.studentNodeReportKey = studentNodeReportKey;
    }
    /**
     * @param studentNodeReportKey
     * @return
     */
    public void setStudentNodeReportKey() {
    	this.studentNodeReportKey = new StudentNodeReportKey();
    	studentNodeReportKey.setNodeKey(getNodeKey());
    	studentNodeReportKey.setStudentId(getStudentId());
    	studentNodeReportKey.setStudentsTestsId(getStudentsTestsId());
    	studentNodeReportKey.setTestId(getTestId());
    }
}