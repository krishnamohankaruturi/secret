package edu.ku.cete.service;

import java.util.List;

public interface BatchUploadWriterService {
	void writerProcess(List<? extends Object> objects);
}
