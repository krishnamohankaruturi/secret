/**
 * 
 */
package edu.ku.cete.model;

import org.apache.ibatis.annotations.Param;


/**
 * @author neil.howerton
 *
 */
public interface StudentPasswordDao {

	String selectRandomWord(Integer seed);
	

	int selectWordCount();


	int selectWordCountForStudentPassword(Integer passwordLength);


	String selectRandomWordForStudentPassword(@Param("seed")Integer nextInt,@Param("passwordLength") Integer passwordLength);
}
