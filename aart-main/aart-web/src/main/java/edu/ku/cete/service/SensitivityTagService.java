package edu.ku.cete.service;

import edu.ku.cete.domain.SensitivityTag;

import java.util.List;

public interface SensitivityTagService {
	public List<SensitivityTag> selectAllByStudentIdAndContentAreaId(Long studentId, Long contentAreaId);

	int upsertForStudent(Long studentId, Long sensitivityTagId, Long userId, Boolean active);

	void updateStudentTags(Long studentId, Long contentAreaId, List<Long> selectedTagIds, Long userId);
}
