package edu.ku.cete.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.model.ScoringAPIReprocessMapper;
import edu.ku.cete.service.ScoringAPIReprocessService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class ScoringAPIReprocessServiceImpl implements ScoringAPIReprocessService {
	
	@Autowired
	private ScoringAPIReprocessMapper scoringAPIReprocessMapper;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateDateToNow(List<Long> ids) {
		if (CollectionUtils.isNotEmpty(ids)) {
			return scoringAPIReprocessMapper.updateDateToNowById(ids.stream().filter(Objects::nonNull).collect(Collectors.toList()));
		}
		return 0;
	}

}
