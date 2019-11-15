package edu.ku.cete.batch.ksde.writer;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;

/**
 * Writes products to a database
 * 
 * @param <T>
 */
public class BatchKSDEDataProcessWriter implements ItemWriter<Boolean> {

	final static Log logger = LogFactory.getLog(BatchKSDEDataProcessWriter.class);

	@Override
	public void write(List<? extends Boolean> objects) throws Exception {
		// logger.debug("Started Write process for KSDE");
		if (!objects.isEmpty()) {

		}
		// logger.debug("Completed Write process for KSDE");
	}

}
