package edu.ku.cete.tde.webservice.domain;

import org.apache.commons.lang.StringUtils;

/**
 * @author m802r921
 * TODO ignore the unknown property and delete this class.
 */
public class TDEStudent {

    private long id;
    private String firstName;
    private String middleName;
    private String surname;
    private String stateId;
    private String username;
    private String password;
    private String userType = "1";
    private int enabled = 1;

    /**
     * @return the id
     */
    public final long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public final void setId(long id) {
        this.id = id;
    }
    /**
     * @return the firstName
     */
    public final String getFirstName() {
        return firstName;
    }
    /**
     * @param firstName the firstName to set
     */
    public final void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * @return the middleName
     */
    public final String getMiddleName() {
        if (middleName == null) {
            return StringUtils.EMPTY;
        }
        return middleName;
    }
    /**
     * @param middleName the middleName to set
     */
    public final void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    /**
     * @return the surName
     */
    public final String getSurname() {
        return surname;
    }
    /**
     * @param surName the surName to set
     */
    public final void setSurname(String surname) {
        this.surname = surname;
    }
    /**
     * @return the stateId
     */
    public final String getStateId() {
        return stateId;
    }
    /**
     * @param stateId the stateId to set
     */
    public final void setStateId(String stateId) {
        this.stateId = stateId;
    }
    /**
     * @return the username
     */
    public final String getUsername() {
        return username;
    }
    /**
     * @param username the username to set
     */
    public final void setUsername(String username) {
        this.username = username;
    }
    /**
     * @return the password
     */
    public final String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public final void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return the userType
     */
    public final String getUserType() {
        return userType;
    }
    /**
     * @param userType the userType to set
     */
    public final void setUserType(String userType) {
        this.userType = userType;
    }
    /**
     * @return the enabled
     */
    public final int getEnabled() {
        return enabled;
    }
    /**
     * @param enabled the enabled to set
     */
    public final void setEnabled(int enabled) {
        this.enabled = enabled;
    }
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TDEStudent [id=");
		builder.append(id);
		builder.append(", ");
		if (firstName != null) {
			builder.append("firstName=");
			builder.append(firstName);
			builder.append(", ");
		}
		if (middleName != null) {
			builder.append("middleName=");
			builder.append(middleName);
			builder.append(", ");
		}
		if (surname != null) {
			builder.append("surname=");
			builder.append(surname);
			builder.append(", ");
		}
		if (stateId != null) {
			builder.append("stateId=");
			builder.append(stateId);
			builder.append(", ");
		}
		if (username != null) {
			builder.append("username=");
			builder.append(username);
			builder.append(", ");
		}
		if (password != null) {
			builder.append("password=");
			builder.append(password);
			builder.append(", ");
		}
		if (userType != null) {
			builder.append("userType=");
			builder.append(userType);
			builder.append(", ");
		}
		builder.append("enabled=");
		builder.append(enabled);
		builder.append("]");
		return builder.toString();
	}

}
