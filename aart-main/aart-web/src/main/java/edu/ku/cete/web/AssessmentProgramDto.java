/**
 * 
 */
package edu.ku.cete.web;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import edu.ku.cete.domain.content.AssessmentProgram;

/**
 * @author neil.howerton
 *
 */
public class AssessmentProgramDto {

    private AssessmentProgram assessmentProgram;

    /**
     * @return the assessmentProram
     */
    public final AssessmentProgram getAssessmentProgram() {
        return assessmentProgram;
    }

    /**
     * @param assessmentProram the assessmentProram to set
     */
    public final void setAssessmentProgram(AssessmentProgram assessmentProgram) {
        this.assessmentProgram = assessmentProgram;
    }

    /**
     *
     *@return String - a JavaScript escaped version of the program name.
     */
    public final String getProgramName() {
        return StringEscapeUtils.escapeJavaScript(assessmentProgram.getProgramName());
    }

    /**
     *
     *@param programName {@link String}
     */
    public final void setProgramName(String programName) {
        this.assessmentProgram.setProgramName(programName);
    }

    /**
     *
     *@return long
     */
    public final long getId() {
        return this.assessmentProgram.getId();
    }

    /**
     *
     *@param id long
     */
    public final void setId(long id) {
        this.assessmentProgram.setId(id);
    }
   
   //Added during US16351-to show Abbreviatedname on dropdown boxes
    /**
    *
    *@return String
    */
   public final String getAbbreviatedname() {
       return this.assessmentProgram.getAbbreviatedname();
   }

   /**
    *
    *@param abbreviatedname String
    */
   public final void setAbbreviatedname(String abbreviatedname) {
       this.assessmentProgram.setAbbreviatedname(abbreviatedname);
   }
   @Override
   public int hashCode() {
       return new HashCodeBuilder(17, 31). 
           append(this.getAbbreviatedname()).
           toHashCode();
   }

   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof AssessmentProgramDto))
           return false;
       if (obj == this)
           return true;

       AssessmentProgramDto rhs = (AssessmentProgramDto) obj;
       return new EqualsBuilder().
           append(this.getAbbreviatedname(), rhs.getAbbreviatedname()).
           isEquals();
   }
}
