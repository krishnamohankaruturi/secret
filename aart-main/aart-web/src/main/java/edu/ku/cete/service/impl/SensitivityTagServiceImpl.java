package edu.ku.cete.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.SensitivityTag;
import edu.ku.cete.model.SensitivityTagMapper;
import edu.ku.cete.service.SensitivityTagService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class SensitivityTagServiceImpl implements SensitivityTagService {
	
	@Autowired
	private SensitivityTagMapper tagMapper;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<SensitivityTag> selectAllByStudentIdAndContentAreaId(Long studentId, Long contentAreaId) {
		return tagMapper.selectAllByStudentIdAndContentAreaId(studentId, contentAreaId);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int upsertForStudent(
		Long studentId,
		Long sensitivityTagId,
		Long userId,
		Boolean active) {
		return tagMapper.upsertForStudent(studentId, sensitivityTagId, userId, active);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateStudentTags(Long studentId, Long contentAreaId, List<Long> selectedTagIds, Long userId) {
		boolean selectedIsEmpty = CollectionUtils.isEmpty(selectedTagIds);
		List<SensitivityTag> tags = selectAllByStudentIdAndContentAreaId(studentId, contentAreaId);
    	for (SensitivityTag tag : tags) {
    		upsertForStudent(studentId, tag.getId(), userId, selectedIsEmpty ? false : selectedTagIds.contains(tag.getId()));
    	}
	}
}
