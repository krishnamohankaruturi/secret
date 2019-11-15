package edu.ku.cete.batch.reportprocess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.util.SystemOutLogger;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import edu.ku.cete.service.report.UploadGrfFileService;
import edu.ku.cete.util.TimerUtil;

public class BatchGRFUploadProcessPartitioner implements Partitioner{

	private final static Log logger = LogFactory .getLog(BatchGRFUploadProcessPartitioner.class);
		
	@Autowired
	UploadGrfFileService uploadGrfFileService;
			
	private StepExecution stepExecution;
	private Long batchUploadId;
		

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		logger.debug("Enter BatchGRFUploadProcessPartitioner partition size : "+gridSize);
		stepExecution.getJobExecution();		
		Map<String, ExecutionContext> partitionMap =  new HashMap<String, ExecutionContext>(gridSize);
		List<String> districtCode = uploadGrfFileService.getDistinctDistrictsByStateId(batchUploadId);
		if(districtCode.size()>0){
			for(String districtcode : districtCode){
						ExecutionContext context = new ExecutionContext();
						context.put("districtCode", districtcode);						
						partitionMap.put(districtcode, context );						
					}
				}
		logger.debug("Created "+partitionMap.size()+" partitions.");
		return partitionMap;
	}

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public Long getBatchUploadId() {
		return batchUploadId;
	}

	public void setBatchUploadId(Long batchUploadId) {
		this.batchUploadId = batchUploadId;
	}

	
}
