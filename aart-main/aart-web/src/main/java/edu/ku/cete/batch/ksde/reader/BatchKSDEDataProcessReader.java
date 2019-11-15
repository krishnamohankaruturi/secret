package edu.ku.cete.batch.ksde.reader;

import java.util.List;

import org.springframework.batch.item.support.ListItemReader;

import edu.ku.cete.ksde.kids.result.KidRecord;

public class BatchKSDEDataProcessReader extends ListItemReader<KidRecord> {

	public BatchKSDEDataProcessReader(List<KidRecord> ksdeRecords) {
		super(ksdeRecords);
	}
	
	public KidRecord read(){
		return super.read();
	}

}