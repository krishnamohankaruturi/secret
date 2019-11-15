package edu.ku.cete.batch.questar;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.service.QuestarService;

public class QuestarRegistrationReader<T> extends AbstractPagingItemReader<T> {
	
	@Autowired
	private QuestarService questarService;
	
	@Override
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		} else {
			results.clear();
		}
		results.addAll(getQuestarStagingRecords(getPage() * getPageSize(), getPageSize()));
	}
	
	@Override
	protected void doJumpToPage(int arg0) {
		// nothing
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> getQuestarStagingRecords(Integer offset, Integer pageSize) {
		return (List<T>) questarService.getNonProcessedStagingRecords(offset, pageSize);
	}
}