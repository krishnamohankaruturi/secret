package edu.ku.cete.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.ku.cete.web.KSDEStudentTestDetailsDTO;

@Service
public interface KSDEReturnFileService {
	public List<KSDEStudentTestDetailsDTO> getReturnFileDetailsExtract(
			List<String> subjects, Long currentSchoolYear,Long schoolId);
}
