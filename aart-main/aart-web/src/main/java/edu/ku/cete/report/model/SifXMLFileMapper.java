package edu.ku.cete.report.model;


import edu.ku.cete.domain.sif.SifXMLUpload;

public interface SifXMLFileMapper {


	int insertABC(SifXMLUpload record);

	SifXMLUpload selectByPrimaryKey(Long id);

	int updateByPrimaryKey(SifXMLUpload record);

}
