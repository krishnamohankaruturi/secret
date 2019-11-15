/**
 * 
 */
package edu.ku.cete.model;

import java.util.List;

import edu.ku.cete.domain.content.Grade;

/**
 * @author neil.howerton
 *
 */
public interface GradeDao {

    Grade findByGrade(int grade);
    
    List<Grade> getAll();
}
