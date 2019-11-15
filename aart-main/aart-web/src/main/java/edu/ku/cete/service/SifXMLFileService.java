package edu.ku.cete.service;

import edu.ku.cete.domain.sif.SifXMLUpload;

public interface SifXMLFileService {

	int insert(SifXMLUpload xml);

	int updateByPrimaryKeySelective(SifXMLUpload xml);

	SifXMLUpload selectByPrimaryKey(Long id);

	String validateXML(String xml, String typeCode);

}
