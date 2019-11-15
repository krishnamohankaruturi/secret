/**
 * 
 */
package edu.ku.cete.service.impl.report;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.domain.CombinedLevelMap;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.report.CombinedLevelMapService;

/**
 * @author ktaduru_sta
 *
 */
@Service
public class CombinedLevelMapUploadWriterProcessServiceImpl implements BatchUploadWriterService {
	final static Log logger = LogFactory.getLog(CombinedLevelMapUploadWriterProcessServiceImpl.class);

	@Autowired
	private CombinedLevelMapService combinedLevelMapService;
	
	@Override
	public void writerProcess(List<? extends Object> objects) {
		logger.debug("Started writing for batchupload - Combined Level Map");
		for( Object object : objects){
			CombinedLevelMap combinedLevelMap = (CombinedLevelMap) object;
			combinedLevelMapService.insertCombinedLevelMap(combinedLevelMap);
		} 
	}

}
