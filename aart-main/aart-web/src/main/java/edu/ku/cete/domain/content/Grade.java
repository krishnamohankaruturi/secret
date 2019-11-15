/**
 * 
 */
package edu.ku.cete.domain.content;

import edu.ku.cete.domain.audit.AuditableDomain;

/**
 * @author neil.howerton
 *
 */
public class Grade extends AuditableDomain {

    private long id;
    private int grade;
    private String description;

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
     * @return the grade
     */
    public final int getGrade() {
        return grade;
    }
    /**
     * @param grade the grade to set
     */
    public final void setGrade(int grade) {
        this.grade = grade;
    }
    /**
     * @return the description
     */
    public final String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public final void setDescription(String description) {
        this.description = description;
    }
}
