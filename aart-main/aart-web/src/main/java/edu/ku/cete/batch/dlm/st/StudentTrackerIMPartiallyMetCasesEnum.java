package edu.ku.cete.batch.dlm.st;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import edu.ku.cete.dlm.iti.BPCriteriaAndGroups;
import edu.ku.cete.domain.test.ContentFrameworkDetail;

public enum StudentTrackerIMPartiallyMetCasesEnum {
	EES_FROM_CRITERIA_WITH_ZERO_ITI_COVERAGE(1) {
		public ContentFrameworkDetail getEligibleEE(Map<Long, BPCriteriaAndGroups> bpCriteriasMapForITIAndST, 
				Long maxCriteria) {
			Random randomGenerator = new Random();
			for(Long criteria = FIRST_CRITERIA; criteria <= maxCriteria; criteria++) {
				BPCriteriaAndGroups bpCriteriaAndGroups = bpCriteriasMapForITIAndST.get(criteria);
				if(CollectionUtils.isEmpty(bpCriteriaAndGroups.getItiTestedEEs()) 
						&& CollectionUtils.isEmpty(bpCriteriaAndGroups.getStudentTrackerTestedEEs())) {
					List<ContentFrameworkDetail> eesList = bpCriteriaAndGroups.getListOfEEs();
					if(CollectionUtils.isNotEmpty(eesList)) {
						return eesList.get(randomGenerator.nextInt(eesList.size()));
					}
				}
			}
			return null;
		}
	},
	EES_FROM_CRITEIRA_WITH_ZERO_ST_COVERAGE(2) {
		public ContentFrameworkDetail getEligibleEE(Map<Long, BPCriteriaAndGroups> bpCriteriasMapForITIAndST, 
				Long maxCriteria) {
			Random randomGenerator = new Random();
			for(Long criteria = FIRST_CRITERIA; criteria <= maxCriteria; criteria++) {
				BPCriteriaAndGroups bpCriteriaAndGroups = bpCriteriasMapForITIAndST.get(criteria);
				List<ContentFrameworkDetail> listOfEEsToAssign = new ArrayList<>();
				Set<String> studentTrackerTestedEEs = bpCriteriaAndGroups.getStudentTrackerTestedEEs();
				if(CollectionUtils.isEmpty(studentTrackerTestedEEs)) {
					if(bpCriteriaAndGroups.isCriteriaRequirmentMetForITI()) {
						listOfEEsToAssign.addAll(bpCriteriaAndGroups.getItiTestedEEs());
					} else {
						listOfEEsToAssign = getEEsFromGroupsWithZeroITI(bpCriteriaAndGroups);
						if(CollectionUtils.isEmpty(listOfEEsToAssign)) {
							listOfEEsToAssign = getEEsFromGroupsNotMetInITI(bpCriteriaAndGroups);
						}
					}
				}
				if(CollectionUtils.isNotEmpty(listOfEEsToAssign)) {
					return listOfEEsToAssign.get(randomGenerator.nextInt(listOfEEsToAssign.size()));
				}
			}
			return null;
		}
	},
	EES_FROM_ALL_GROUPS_WITH_ZERO_ST_COVERAGE(3) {
		public ContentFrameworkDetail getEligibleEE(Map<Long, BPCriteriaAndGroups> bpCriteriasMapForITIAndST, 
				Long maxCriteria) {
			Random randomGenerator = new Random();
			List<ContentFrameworkDetail> itiTestedEEs = new ArrayList<ContentFrameworkDetail>();
			List<ContentFrameworkDetail> bluePrintEEsList = new ArrayList<ContentFrameworkDetail>();
			List<ContentFrameworkDetail> eesNotTakenInITI = new ArrayList<ContentFrameworkDetail>();
			for(Long criteria = FIRST_CRITERIA; criteria <= maxCriteria; criteria++) {
				BPCriteriaAndGroups bpCriteriaAndGroups = bpCriteriasMapForITIAndST.get(criteria);
				List<Long> groupsWithZeroSTCoverage = bpCriteriaAndGroups.getListOfGroupsWithZeroSTCoverage();
				List<Long> groupsNotMetInITI = bpCriteriaAndGroups.getListOfGroupsNotMetInITI();
				if(CollectionUtils.isNotEmpty(groupsWithZeroSTCoverage)) {
					List<ContentFrameworkDetail> eesToProcess = getEEsFromGroupsWithZeroITI(bpCriteriaAndGroups);
					if(CollectionUtils.isNotEmpty(eesToProcess)) {
						bluePrintEEsList.addAll(eesToProcess);
					} else {
						eesToProcess = getEEsFromGroupsNotMetInITI(bpCriteriaAndGroups);
						if(CollectionUtils.isNotEmpty(eesToProcess)) {
							eesNotTakenInITI.addAll(eesToProcess);
						} else {
							for(Long groupNumber : groupsWithZeroSTCoverage) {
								if(!groupsNotMetInITI.contains(groupNumber)) {
									itiTestedEEs.addAll(bpCriteriaAndGroups.getItiTestedEEsByGroup().get(groupNumber));
								}
							}
						}
					}
				}
			}
			if(CollectionUtils.isNotEmpty(bluePrintEEsList)) {
				return bluePrintEEsList.get(randomGenerator.nextInt(bluePrintEEsList.size()));
			} else if(CollectionUtils.isNotEmpty(eesNotTakenInITI)) {
				return eesNotTakenInITI.get(randomGenerator.nextInt(eesNotTakenInITI.size()));
			} else if (CollectionUtils.isNotEmpty(itiTestedEEs)) {
				return itiTestedEEs.get(randomGenerator.nextInt(itiTestedEEs.size()));
			}
			return null;
		}
	},
	EES_FROM_ALL_GROUPS_NOT_MET_ST_COVERAGE(4) {
		public ContentFrameworkDetail getEligibleEE(Map<Long, BPCriteriaAndGroups> bpCriteriasMapForITIAndST, 
				Long maxCriteria) {
			Random randomGenerator = new Random();
			List<ContentFrameworkDetail> eesToAssign = new ArrayList<ContentFrameworkDetail>();
			for(Long criteria = FIRST_CRITERIA; criteria <= maxCriteria; criteria++) {
				BPCriteriaAndGroups bpCriteriaAndGroups = bpCriteriasMapForITIAndST.get(criteria);
				Set<String> stTestedEEs = bpCriteriaAndGroups.getStudentTrackerTestedEEs();
				List<Long> groupsNotMetSTRequirements = bpCriteriaAndGroups.getListOfGroupsNotMetInST();
				for(Long groupNum : groupsNotMetSTRequirements) {
					List<ContentFrameworkDetail> listOfITITestedEEs = bpCriteriaAndGroups.getItiTestedEEsByGroup().get(groupNum);
					if(CollectionUtils.isNotEmpty(listOfITITestedEEs)) {
						for(ContentFrameworkDetail itiEE: listOfITITestedEEs) {
							if(!stTestedEEs.contains(itiEE.getName())) {
								eesToAssign.add(itiEE);
							}
						}
					}
				}
			}
			if(CollectionUtils.isEmpty(eesToAssign)) {
				for(Long criteria = FIRST_CRITERIA; criteria <= maxCriteria; criteria++) {
					BPCriteriaAndGroups bpCriteriaAndGroups = bpCriteriasMapForITIAndST.get(criteria);
					Set<String> stTestedEEs = bpCriteriaAndGroups.getStudentTrackerTestedEEs();
					List<Long> groupsNotMetSTRequirements = bpCriteriaAndGroups.getListOfGroupsNotMetInST();
					for(Long groupNum : groupsNotMetSTRequirements) {
						for(ContentFrameworkDetail bluePrintEE: bpCriteriaAndGroups.getEesByGroup().get(groupNum)) {
							if(!stTestedEEs.contains(bluePrintEE.getName())) {
								eesToAssign.add(bluePrintEE);
							}
						}
					}
				}
			}
			if(CollectionUtils.isNotEmpty(eesToAssign)) {
				return eesToAssign.get(randomGenerator.nextInt(eesToAssign.size()));
			}
			return null;
		}
	};
	
	public abstract ContentFrameworkDetail getEligibleEE(Map<Long, BPCriteriaAndGroups> bpCriteriasMapForITIAndST, Long maxCriteria);
	
	private  static List<ContentFrameworkDetail> getEEsFromGroupsWithZeroITI(BPCriteriaAndGroups bpCriteriaAndGroups) {
		List<ContentFrameworkDetail> listOfBluePrintEEs = new ArrayList<>();
		List<Long> listOfGroupsWithZeroITICoverage = bpCriteriaAndGroups.getListOfGroupsWithZeroITICoverage();
		List<Long> groupsWithZeroSTCoverage = bpCriteriaAndGroups.getListOfGroupsWithZeroSTCoverage();
		for(Long grouNumber:listOfGroupsWithZeroITICoverage) {
			if(groupsWithZeroSTCoverage.contains(grouNumber)) {
				listOfBluePrintEEs.addAll(bpCriteriaAndGroups.getEesByGroup().get(grouNumber));
			}
		}
		return listOfBluePrintEEs;
	}
	
	private static List<ContentFrameworkDetail> getEEsFromGroupsNotMetInITI(BPCriteriaAndGroups bpCriteriaAndGroups) {
		List<ContentFrameworkDetail> listOfEEsNotTakenInITI = new ArrayList<>();
		List<Long> listOfGroupsPartiallyMetInITI = bpCriteriaAndGroups.getListOfGroupsNotMetInITI();
		List<String> eesTakenInITI = new ArrayList<>();
		List<Long> groupsWithZeroSTCoverage = bpCriteriaAndGroups.getListOfGroupsWithZeroSTCoverage();
		for(ContentFrameworkDetail itiEE: bpCriteriaAndGroups.getItiTestedEEs()) {
			eesTakenInITI.add(itiEE.getName());
		}
		for(Long groupNumber: listOfGroupsPartiallyMetInITI) {
			if(groupsWithZeroSTCoverage.contains(groupNumber)) {
				for(ContentFrameworkDetail cfd:bpCriteriaAndGroups.getEesByGroup().get(groupNumber)) {
					if(!eesTakenInITI.contains(cfd.getName())) {
						listOfEEsNotTakenInITI.add(cfd);
					}
				}
			}
		}
		return listOfEEsNotTakenInITI;
	}
	
	private static final long FIRST_CRITERIA = 1l;	
	private int priority;
	
	StudentTrackerIMPartiallyMetCasesEnum(int priority) {
		this.priority = priority;
	}
	
	int getPriority() {
		return this.priority;
	}
}
