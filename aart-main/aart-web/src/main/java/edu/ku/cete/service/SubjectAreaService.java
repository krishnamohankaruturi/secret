package edu.ku.cete.service;

import java.util.List;

import edu.ku.cete.domain.enrollment.SubjectArea;

public interface SubjectAreaService {

	public List<SubjectArea> getSubjectAreaByTestTypeId(Long testTypeId);
	
	public List<SubjectArea> getSubjectAreasForAutoRegistration(Long testTypeId, Long assessmentId);
	
	public SubjectArea getById(Long id);

	List<SubjectArea> getByName(String name);
}
