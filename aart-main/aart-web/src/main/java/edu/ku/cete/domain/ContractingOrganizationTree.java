/**
 * 
 */
package edu.ku.cete.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationContractRelation;
import edu.ku.cete.util.AARTCollectionUtil;

/**
 * @author m802r921
 * 1. Stores Users Organization Tree with the user's organization.
 * 2. Highest Contracting organizations.
 * 3. Children of each of the highest contracting organizations.
 * 4. parent organization map.
 */
public class ContractingOrganizationTree  implements Serializable{
	private Map<Long,List<OrganizationContractRelation>> childOrgMap
	= new HashMap<Long,List<OrganizationContractRelation>>();
	private Map<Long,List<OrganizationContractRelation>> parentOrgMap
	= new HashMap<Long,List<OrganizationContractRelation>>();
	private OrganizationContractRelation userOrganizationContractRelation = null;
	private List<OrganizationContractRelation>
	highestContractingOrgs = new ArrayList<OrganizationContractRelation>();
	private Map<Long, List<Organization>> contractingOrganizationChildren
	= new HashMap<Long, List<Organization>>();
	private OrganizationTree userOrganizationTree;
	/**
	 * @return the childOrgMap
	 */
	public Map<Long,List<OrganizationContractRelation>> getChildOrgMap() {
		return childOrgMap;
	}
	/**
	 * @param childOrgMap the childOrgMap to set
	 */
	public void setChildOrgMap(Map<Long,List<OrganizationContractRelation>> childOrgMap) {
		this.childOrgMap = childOrgMap;
	}
	/**
	 * @return the parentOrgMap
	 */
	public Map<Long,List<OrganizationContractRelation>> getParentOrgMap() {
		return parentOrgMap;
	}
	/**
	 * @param parentOrgMap the parentOrgMap to set
	 */
	public void setParentOrgMap(Map<Long,List<OrganizationContractRelation>> parentOrgMap) {
		this.parentOrgMap = parentOrgMap;
	}
	/**
	 * @return the userOrganizationContractRelation
	 */
	public OrganizationContractRelation getUserOrganizationContractRelation() {
		return userOrganizationContractRelation;
	}
	/**
	 * @param userOrganizationContractRelation the userOrganizationContractRelation to set
	 */
	public void setUserOrganizationContractRelation(
			OrganizationContractRelation userOrganizationContractRelation) {
		this.userOrganizationContractRelation = userOrganizationContractRelation;
	}
	/**
	 * @return the highestContractingOrgs
	 */
	public List<OrganizationContractRelation> getHighestContractingOrgs() {
		return highestContractingOrgs;
	}
	/**
	 * @param highestContractingOrgs the highestContractingOrgs to set
	 */
	public void setHighestContractingOrgs(List<OrganizationContractRelation> highestContractingOrgs) {
		if(highestContractingOrgs != null && CollectionUtils.isNotEmpty(highestContractingOrgs)) {
			this.highestContractingOrgs = highestContractingOrgs;
			for(OrganizationContractRelation highestContractRelation: highestContractingOrgs) {
				if(contractingOrganizationChildren.containsKey(highestContractRelation.getId())) {
					
				} else {
					//Add the contracting organization to the tree.
					List<Organization> organizations 
					= new ArrayList<Organization>();
					organizations.add(highestContractRelation.getOrganization());
					contractingOrganizationChildren.put(highestContractRelation.getId(),
							organizations);
				}
			}
		}
	}
	/**
	 * @return
	 */
	public Map<Long, List<Organization>> getContractingOrganizationChildren() {
		return contractingOrganizationChildren;
	}
	/**
	 * @param contractingOrganizationChildren
	 */
	public void setContractingOrganizationChildren(
			Map<Long, List<Organization>> contractingOrganizationChildren) {
		this.contractingOrganizationChildren = contractingOrganizationChildren;
	}
	/**
	 * 
	 */
	public void setUserOrganizationTree() {
        userOrganizationTree = new OrganizationTree();
        //Add the user's organization and it can never be null so no null check.
        userOrganizationTree.addOrganization(userOrganizationContractRelation.getOrganization());
        for(Long organizationId:childOrgMap.keySet()) {
        	List<OrganizationContractRelation>
        	organizationContractRelations = childOrgMap.get(organizationId);
        	//this can never be null so no null check.
        	for(OrganizationContractRelation organizationContractRelation:organizationContractRelations) {
        		userOrganizationTree.addOrganization(organizationContractRelation.getOrganization());
        	}
        }
	}
	/**
	 * The entire organization tree display ids.
	 * @return Set<String> - displayIdentifiers of both contractingOrganizantion and userOrganization
	 */
	public Set<String> getOrganizationDisplayIdentifiers() {		
		Set<String> organizationDisplayIdentifiers = new HashSet<String>();
		List<Organization> userOrganizationsList = null;
		
		
		// getting the contractingOrganization's and adding the displayIdentifier to a set
		Collection<List<Organization>> contractingOrganizationList = getContractingOrganizationChildren().values();		
		for(List<Organization> organizationList : contractingOrganizationList) {
			for(Organization organization : organizationList) {				
				organizationDisplayIdentifiers.add(organization.getDisplayIdentifier());				
			}
		}
		
		// getting the UserOrganization's and adding the displayIdentifier to a set
		userOrganizationsList = getUserOrganizationTree().getUserOrganizationList();
		for(Organization userOrganization: userOrganizationsList) {
			organizationDisplayIdentifiers.add(userOrganization.getDisplayIdentifier());
		}
		
		return organizationDisplayIdentifiers;			
	}	
	/**
	 * The entire contracting organization tree ids.
	 * @param organizationId
	 */
	public Set<Long> getContractingOrganizationTreeIds() {
		//initialized to a blank list, so no need to check for null.
		//TODO why not store the list instead of converting it every time.
		Set<Long>
		childContractingOrganizationIds = new HashSet<Long>();
		//initialized to a blank list, so no need to check for null.
		for (Long contractingOrganizationId:contractingOrganizationChildren.keySet()) {
			List<Organization>
			childContractingOrganizations = contractingOrganizationChildren.get(contractingOrganizationId);
			//childContractingOrganizations cannot be null so null check.
			childContractingOrganizationIds.addAll(AARTCollectionUtil.getIds(childContractingOrganizations));
		}
		return childContractingOrganizationIds;			
	}
	/**
	 * @param organizationId
	 */
	public List<Long> getContractingOrganizationTreeIds(Long organizationId) {
		//initialized to a blank list, so no need to check for null.
		//TODO why not store the list instead of converting it every time.
		List<Long>
		childContractingOrganizationIds = null;
		//initialized to a blank list, so no need to check for null.
		for (Long contractingOrganizationId:contractingOrganizationChildren.keySet()) {
			List<Organization>
			childContractingOrganizations = contractingOrganizationChildren.get(contractingOrganizationId);
			//childContractingOrganizations cannot be null so null check.
			childContractingOrganizationIds = AARTCollectionUtil.getIds(childContractingOrganizations);
			if(childContractingOrganizationIds.contains(organizationId)){
				break;
			}
		}
		return childContractingOrganizationIds;			
	}
	
	/**
	 * @return
	 */
	public OrganizationTree getUserOrganizationTree() {
		return userOrganizationTree;
	}
	/**
	 * @param userOrganizationTree
	 */
	public void setUserOrganizationTree(OrganizationTree userOrganizationTree) {
		this.userOrganizationTree = userOrganizationTree;
	}
	
	/**
	 * @param attendanceSchoolOrgId
	 * @return List<Long>
	 */
	public List<Long> getDiffOrgIdsBetweenContractingOrgNUserOrgHierarchy(long attendanceSchoolOrgId){
		Collection<Long> currentUserOrgHierachyIds = getUserOrganizationTree().getUserOrganizationIds(); 
		List<Long> completeContractingOrgHierarchyIds = getContractingOrganizationTreeIds(attendanceSchoolOrgId);
		List<Long> diffContractingOrgHierarchyIds = new ArrayList<Long>();
		
		if(CollectionUtils.isNotEmpty(completeContractingOrgHierarchyIds)){    		 
    		
	    	for(long contractingOrgid : completeContractingOrgHierarchyIds){
	    		//logger.debug("contractingOrgid -" + contractingOrgid);
				if(!currentUserOrgHierachyIds.contains(contractingOrgid)){
					diffContractingOrgHierarchyIds.add(contractingOrgid);
				}					
			}
    	}
		
		return diffContractingOrgHierarchyIds;
	}
	public Collection<Long> getDiffOrgIdsBetweenContractingOrgNUserOrgHierarchy() {
		Collection<Long> currentUserOrgHierachyIds = getUserOrganizationTree().getUserOrganizationIds();
		Set<Long> completeContractingOrgHierarchyIds = getContractingOrganizationTreeIds();
		Collection<Long> diffContractingOrgHierarchyIds = CollectionUtils.subtract(completeContractingOrgHierarchyIds, currentUserOrgHierachyIds);
		return diffContractingOrgHierarchyIds;
	}
	
	public Collection<Long> getDiffOrgIdsBetweenContractingOrgNUserOrgHierarchyForOrgId(List<Long> orgChildrenIds) {
		Collection<Long> currentUserOrgHierachyIds = getUserOrganizationTree().getUserOrganizationIds();
		Collection<Long> diffContractingOrgHierarchyIds = CollectionUtils.subtract(orgChildrenIds, currentUserOrgHierachyIds);
		return diffContractingOrgHierarchyIds;
	}
}
