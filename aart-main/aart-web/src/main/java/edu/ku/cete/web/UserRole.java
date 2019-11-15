package edu.ku.cete.web;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.user.User;

public class UserRole {
    
    private User user;
    private Organization organization;
    
    public UserRole(User usr, Organization org) {
        user = usr;
        organization = org;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
    
    public String getFirstName() {
        return this.user.getFirstName();
    }
    
    public String getLastName() {
        return this.user.getSurName();
    }
    
    public String getUserName() {
        return this.user.getUserName();
    }
    
    public long getUserId() {
        return this.user.getId();
    }
    
    public long getOrganizationId() {
        return this.organization.getId();
    }
    
    public String getOrganizationTypeName() {
        //TODO change this when method for getting org type from organization is available.
        return null;
    }
}
