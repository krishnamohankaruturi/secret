package edu.ku.cete.web.authorities;

import java.util.ArrayList;
import java.util.List;

import edu.ku.cete.web.AuthoritiesDTO;

public class AuthoritiesTab {
	public static final int CONTAINER_SIZE = 2;
	private String tabName;
	private String tabCode;
	private List<List<AuthoritiesDTO>> authorityContainers = new ArrayList<List<AuthoritiesDTO>>(CONTAINER_SIZE);

	public AuthoritiesTab(String tabName) {
		this.setTabName(tabName);
		for (int i = 0; i < CONTAINER_SIZE; i++) {
			authorityContainers.add(new ArrayList<AuthoritiesDTO>());
		}
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
		setTabCode(tabName.replace(" ", ""));
	}

	public String getTabCode() {
		return this.tabCode;
	}

	public void setTabCode(String tabCode) {
		this.tabCode = tabCode;
	}

	public List<List<AuthoritiesDTO>> getAuthorityContainers() {
		return authorityContainers;
	}

	public void setAuthorityContainers(List<List<AuthoritiesDTO>> authorityContainers) {
		this.authorityContainers = authorityContainers;
	}
	
}
