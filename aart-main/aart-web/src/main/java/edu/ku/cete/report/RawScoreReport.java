package edu.ku.cete.report;

import java.util.List;

import edu.ku.cete.score.StudentRawScore;

/**
 * This object is used to describe a Raw Score report instance.
 * @author neil.howerton
 *
 */
public class RawScoreReport extends Report {

    /**
     * The students and their scores on the test the report is generated for.
     */
    private List<StudentRawScore> studentScores;

    /**
     * The name of the roster the student took the test for.
     */
    private String rosterName;

    /**
     * The subject of the test.
     */
    private String subject;

    /**
     * The grade for the test.
     */
    private String grade;
    
    /**
     * The grade for the test.
     */
    private String graphImage;

    /**
     * studentProblemReports
     */
    private List<StudentProblemReport> studentProblemReports;
    
    /**
     * studentProblemReports
     */
    private List<GraphScoreReport> graphScoreReport;
    
    /**
     * studentResponseReports
     */
    private List<StudentResponseReport> studentResponseReports;
    
    /**
     * @return the studentScores
     */
    public final List<StudentRawScore> getStudentScores() {
        return studentScores;
    }

    /**
     * @param studentScores the studentScores to set
     */
    public final void setStudentScores(List<StudentRawScore> studentScores) {
        this.studentScores = studentScores;
    }

    /**
     * @return the rosterName
     */
    public final String getRosterName() {
        return rosterName;
    }

    /**
     * @param rosterName the rosterName to set
     */
    public final void setRosterName(String rosterName) {
        this.rosterName = rosterName;
    }

    /**
     * @return the subject
     */
    public final String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public final void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the grade
     */
    public final String getGrade() {
        return grade;
    }

    /**
     * @param grade the grade to set
     */
    public final void setGrade(String grade) {
        this.grade = grade;
    }

	/**
	 * @return the studentProblemReports
	 */
	public List<StudentProblemReport> getStudentProblemReports() {
		return studentProblemReports;
	}

	/**
	 * @param studentProblemReports the studentProblemReports to set
	 */
	public void setStudentProblemReports(List<StudentProblemReport> studentProblemReports) {
		this.studentProblemReports = studentProblemReports;
	}

	public List<GraphScoreReport> getGraphScoreReport() {
		return graphScoreReport;
	}

	public void setGraphScoreReport(List<GraphScoreReport> graphScoreReport) {
		this.graphScoreReport = graphScoreReport;
	}

	public String getGraphImage() {
		return graphImage;
	}

	public void setGraphImage(String graphImage) {
		this.graphImage = graphImage;
	}

	public List<StudentResponseReport> getStudentResponseReports() {
		return studentResponseReports;
	}

	public void setStudentResponseReports(List<StudentResponseReport> studentResponseReports) {
		this.studentResponseReports = studentResponseReports;
	}
}
