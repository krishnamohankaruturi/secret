package edu.ku.cete.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.batch.upload.xml.parser.XmlXpathParser;
import edu.ku.cete.controller.sif.SifUploadType;
import edu.ku.cete.domain.sif.SifXMLUpload;
import edu.ku.cete.service.SifXMLFileService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class SifXMLFileServiceImpl implements SifXMLFileService {

	private static final Log LOGGER = LogFactory.getLog(SifXMLFileServiceImpl.class);

	@Autowired
	private edu.ku.cete.report.model.SifXMLFileMapper xmlDao;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insert(SifXMLUpload xml) {
		LOGGER.debug("Entering SifXMLFileService insert");
		int count = xmlDao.insertABC(xml);
		LOGGER.debug("Exiting SifXMLFileService insert");
		return count;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateByPrimaryKeySelective(SifXMLUpload xml) {
		LOGGER.debug("Entering SifXMLFileService updateByPrimaryKeySelective");
		int count = xmlDao.updateByPrimaryKey(xml);
		LOGGER.debug("Exiting SifXMLFileService updateByPrimaryKeySelective");
		return count;
	}

	@Override
	public SifXMLUpload selectByPrimaryKey(Long id) {
		return xmlDao.selectByPrimaryKey(id);
	}

	@Override
	public String validateXML(String xml, String typeCode) {
		try {
			String expression = SifUploadType.valueOf(typeCode).getXpathRoot();
			XmlXpathParser xmlXpathParser = new XmlXpathParser(expression);
			xmlXpathParser.parse(xml);
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"xmlParsingError\":\"InvalidXMLSchema\", \"errorMessage\":\"" + e.getMessage() + "\"}";
		}
		return null;

	}

}
