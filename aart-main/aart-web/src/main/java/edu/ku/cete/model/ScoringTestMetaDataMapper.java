package edu.ku.cete.model;

import org.apache.ibatis.annotations.Param;

public interface ScoringTestMetaDataMapper {
	
   void insertScoringTestMetaData(@Param("testId") Long testId);
   
   Integer countNoOfItemInScoringTestMetaData(@Param("testId") Long testId);
}
