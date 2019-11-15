package edu.ku.cete.dlm.iti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.ku.cete.domain.test.ContentFrameworkDetail;

public class BPCriteriaAndGroups {
	private Long criteriaNum;	
	private List<BPGroupsInfo> grouspInfos = new ArrayList<>();
	private Boolean writingCriteria;
	private Long contentAreaId;
	private Long gradeCourseId;
	private Long gradeBandId;
	private Long maxCriteriaNum;
	private List<ContentFrameworkDetail> listOfEEs = new ArrayList<>();
	private List<ContentFrameworkDetail> itiTestedEEs = new ArrayList<>();
	private Set<String> studentTrackerTestedEEs = new HashSet<>();
	private Map<Long, List<ContentFrameworkDetail>> itiTestedEEsByGroup = new HashMap<>();
	private Map<Long, List<ContentFrameworkDetail>> eesByGroup = new HashMap<>();
	
	public Long getCriteriaNum() {
		return criteriaNum;
	}
	public void setCriteriaNum(Long criteriaNum) {
		this.criteriaNum = criteriaNum;
	}
	
	public Boolean isWritingCriteria() {
		return writingCriteria;
	}
	public void setWritingCriteria(Boolean writingCriteria) {
		this.writingCriteria = writingCriteria;
	}
	public boolean isCriteriaRequirmentMetForITI() {
		for(BPGroupsInfo bpg:getGrouspInfos()) {
			if(!bpg.isITIGroupRequiremtMet()) {
				return false;
			}
		}
		return true;		
	}
	
	public boolean isCriteriaRequirmentMetForST() {
		for(BPGroupsInfo bpg:getGrouspInfos()) {
			if(!bpg.isSTGroupRequiremtMet()) {
				return false;
			}
		}
		return true;		
	}
	
	public List<Long> getListOfGroupsNotMetInITI() {
		List<Long> listOfGroupsNotMetInITI = new ArrayList<Long>();
		for(BPGroupsInfo bpg:getGrouspInfos()) {
			if(!bpg.isITIGroupRequiremtMet()) {
				listOfGroupsNotMetInITI.add(bpg.getGroupNumber());
			}
		}
		return listOfGroupsNotMetInITI;
	}
	public List<Long> getListOfGroupsNotMetInST() {
		List<Long> listOfGroupsNotMetInST = new ArrayList<Long>();
		for(BPGroupsInfo bpg:getGrouspInfos()) {
			if(!bpg.isSTGroupRequiremtMet()) {
				listOfGroupsNotMetInST.add(bpg.getGroupNumber());
			}
		}
		return listOfGroupsNotMetInST;
	}
	
	public List<Long> getListOfGroupsWithZeroITICoverage() {
		List<Long> listOfGroupsWithZeroITI = new ArrayList<Long>();
		for(BPGroupsInfo bpg:getGrouspInfos()) {
			if(bpg.getNumOfITIEEsCompleted() == 0) {
				listOfGroupsWithZeroITI.add(bpg.getGroupNumber());
			}
		}
		return listOfGroupsWithZeroITI;
	}
	
	public List<Long> getListOfGroupsWithZeroSTCoverage() {
		List<Long> listOfGroupsWithZeroST = new ArrayList<Long>();
		for(BPGroupsInfo bpg:getGrouspInfos()) {
			if(bpg.getNumOfSTEEsCompleted() == 0) {
				listOfGroupsWithZeroST.add(bpg.getGroupNumber());
			}
		}
		return listOfGroupsWithZeroST;
	}
	
	public List<BPGroupsInfo> getGrouspInfos() {
		return grouspInfos;
	}
	public void setGrouspInfos(List<BPGroupsInfo> grouspInfo) {
		this.grouspInfos = grouspInfo;
	}
	public Long getContentAreaId() {
		return contentAreaId;
	}
	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}
	public Long getGradeCourseId() {
		return gradeCourseId;
	}
	public void setGradeCourseId(Long gradeCourseId) {
		this.gradeCourseId = gradeCourseId;
	}
	public Long getGradeBandId() {
		return gradeBandId;
	}
	public void setGradeBandId(Long gradeBandId) {
		this.gradeBandId = gradeBandId;
	}
	public Long getMaxCriteriaNum() {
		return maxCriteriaNum;
	}
	public void setMaxCriteriaNum(Long maxCriteriaNum) {
		this.maxCriteriaNum = maxCriteriaNum;
	}
	public List<ContentFrameworkDetail> getItiTestedEEs() {
		return itiTestedEEs;
	}
	public void setItiTestedEEs(List<ContentFrameworkDetail> itiTestedEEs) {
		this.itiTestedEEs = itiTestedEEs;
	}
	public Set<String> getStudentTrackerTestedEEs() {
		return studentTrackerTestedEEs;
	}
	public void setStudentTrackerTestedEEs(Set<String> studentTrackerTestedEEs) {
		this.studentTrackerTestedEEs = studentTrackerTestedEEs;
	}
	public Map<Long, List<ContentFrameworkDetail>> getItiTestedEEsByGroup() {
		return itiTestedEEsByGroup;
	}
	public void setItiTestedEEsByGroup(Map<Long, List<ContentFrameworkDetail>> itiTestedEEsByGroup) {
		this.itiTestedEEsByGroup = itiTestedEEsByGroup;
	}
	public List<ContentFrameworkDetail> getListOfEEs() {
		return listOfEEs;
	}
	public void setListOfEEs(List<ContentFrameworkDetail> listOfEEs) {
		this.listOfEEs = listOfEEs;
	}
	public Map<Long, List<ContentFrameworkDetail>> getEesByGroup() {
		return eesByGroup;
	}
	public void setEesByGroup(Map<Long, List<ContentFrameworkDetail>> eesByGroup) {
		this.eesByGroup = eesByGroup;
	}
}
