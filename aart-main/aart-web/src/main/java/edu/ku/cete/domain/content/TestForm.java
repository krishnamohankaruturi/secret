/**
 * 
 */
package edu.ku.cete.domain.content;

import edu.ku.cete.domain.audit.AuditableDomain;


/**
 * @author neil.howerton
 *
 */
public class TestForm extends AuditableDomain {

    private long id;
    private long testId;
    private String formId;
    private String formName;
    private int numItems;

    private Test test;

    /**
     * @return the id
     */
    public final long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public final void setId(long id) {
        this.id = id;
    }
    /**
     * @return the formId
     */
    public final String getFormId() {
        return formId;
    }
    /**
     * @param formId the formId to set
     */
    public final void setFormId(String formId) {
        this.formId = formId;
    }
    /**
     * @return the formName
     */
    public final String getFormName() {
        return formName;
    }
    /**
     * @param forname the formName to set
     */
    public final void setFormName(String formName) {
        this.formName = formName;
    }

    /**
     * @return the numItems
     */
    public final int getNumItems() {
        return numItems;
    }
    /**
     * @param numItems the numItems to set
     */
    public final void setNumItems(int numItems) {
        this.numItems = numItems;
    }
    /**
     * @return the assessmentId
     */
    public long getTestId() {
        return testId;
    }
    /**
     * @param assessmentId the assessmentId to set
     */
    public void setTestId(long testId) {
        this.testId = testId;
    }
    /**
     * @return the assessment
     */
    public Test getTest() {
        return test;
    }
    /**
     * @param assessment the assessment to set
     */
    public void setTest(Test test) {
        this.test = test;
    }
}
