/**
 * 
 */
package edu.ku.cete.report;

/**
 * This class is used to describe a base report. An instance of this report should never be used to generate an actual report,
 * but a subclass should be used.
 * @author neil.howerton
 *
 */
public abstract class Report {

    /**
     * Name for the report to be generated.
     */
    private String reportName;

    /**
     * Date the report was generated.
     */
    private String date;

    /**
     * Name of the Test the report was generated for.
     */
    private String testName;

    /**
     * @return the reportName
     */
    public final String getReportName() {
        return reportName;
    }

    /**
     * @param reportName the reportName to set
     */
    public final void setReportName(String reportName) {
        this.reportName = reportName;
    }

    /**
     * @return the date
     */
    public final String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public final void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the testName
     */
    public final String getTestName() {
        return testName;
    }

    /**
     * @param testName the testName to set
     */
    public final void setTestName(String testName) {
        this.testName = testName;
    }
}
