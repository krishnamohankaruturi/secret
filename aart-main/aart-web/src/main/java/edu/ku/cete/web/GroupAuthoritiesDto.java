package edu.ku.cete.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import edu.ku.cete.domain.Groups;
import edu.ku.cete.web.authorities.AuthoritiesTab;

public class GroupAuthoritiesDto {

	private Groups group;
	private Map<String, List<AuthoritiesDTO>> tabAuthoritiesMap = new LinkedHashMap<String, List<AuthoritiesDTO>>();
	private Map<String,List<AuthoritiesDTO>> groupingAuthoritiesMap = new LinkedHashMap<String, List<AuthoritiesDTO>>();
	private Boolean isUsersLimitPerRoleVisible;
	private Boolean isUsersLimitedToOnePerRole;
	private List<AuthoritiesTab> authoritiesTabs = new ArrayList<AuthoritiesTab>();

	/**
	 *
	 * @return
	 */
	public Groups getGroup() {
		return this.group;
	}

	/**
	 *
	 * @param group
	 */
	public void setGroup(Groups group) {
		this.group = group;
	}

	/**
	 *
	 * @return
	 */
	public Map<String, List<AuthoritiesDTO>> getTabAuthoritiesMap() {
		return this.tabAuthoritiesMap;
	}

	/**
	 *
	 * @param authorities
	 */
	public void setTabAuthoritiesMap(Map<String, List<AuthoritiesDTO>> authorities) {
		this.tabAuthoritiesMap = authorities;
	}

	public void addAuthorityByTabName(String tabName, AuthoritiesDTO auth) {
		Map<String, List<AuthoritiesDTO>> map = this.getTabAuthoritiesMap();
		if (map.containsKey(tabName)) {
			List<AuthoritiesDTO> list = map.get(tabName);
			if (!list.contains(auth)) {
				list.add(auth);
			}
		} else {
			List<AuthoritiesDTO> list = new ArrayList<AuthoritiesDTO>();
			list.add(auth);
			map.put(tabName, list);
		}
		if(StringUtils.isNotBlank(auth.getAuthority().getGroupingName())){
			String groupingName = auth.getAuthority().getGroupingName();
			if(this.groupingAuthoritiesMap.containsKey(groupingName)){
				this.groupingAuthoritiesMap.get(groupingName).add(auth);
			} else {
				List<AuthoritiesDTO> authorities = new ArrayList<AuthoritiesDTO>();
				authorities.add(auth);
				this.groupingAuthoritiesMap.put(groupingName, authorities);
			}
		}
	}

	public Boolean getIsUsersLimitPerRoleVisible() {
		return isUsersLimitPerRoleVisible;
	}

	public void setIsUsersLimitPerRoleVisible(Boolean isUsersLimitPerRoleVisible) {
		this.isUsersLimitPerRoleVisible = isUsersLimitPerRoleVisible;
	}

	public Boolean getIsUsersLimitedToOnePerRole() {
		return isUsersLimitedToOnePerRole;
	}

	public void setIsUsersLimitedToOnePerRole(Boolean isUsersLimitedToOnePerRole) {
		this.isUsersLimitedToOnePerRole = isUsersLimitedToOnePerRole;
	}

	public List<AuthoritiesTab> getAuthoritiesTabs() {
		return authoritiesTabs;
	}

	public void setAuthoritiesTabs(List<AuthoritiesTab> authoritiesTabs) {
		this.authoritiesTabs = authoritiesTabs;
	}

	public Map<String, List<AuthoritiesDTO>> getGroupingAuthoritiesMap() {
		return groupingAuthoritiesMap;
	}

	public void setGroupingAuthoritiesMap(Map<String, List<AuthoritiesDTO>> groupingAuthoritiesMap) {
		this.groupingAuthoritiesMap = groupingAuthoritiesMap;
	}

	public AuthoritiesTab getAuthoritiesTab(String tabName) {
		for (AuthoritiesTab authoritiesTab : authoritiesTabs) {
			if (authoritiesTab.getTabName().equals(tabName)) {
				return authoritiesTab;
			}
		}
		return null;
	}
}
