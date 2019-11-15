/**
 * 
 */
package edu.ku.cete.service;

import java.util.List;

import edu.ku.cete.domain.content.Grade;

/**
 * @author neil.howerton
 *
 */
public interface GradeService {

    Grade findByGrade(int grade);

    List<Grade> getAll();
}
