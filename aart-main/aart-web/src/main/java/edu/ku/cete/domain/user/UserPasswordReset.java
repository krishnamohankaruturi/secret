package edu.ku.cete.domain.user;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class UserPasswordReset implements Serializable {
	
	/**
	 * Added for US-14985
	 * These variables added for extra columns added in to table
	 *  
	 */
	private String password;
	private String requestType;
	
	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.userpasswordreset.aart_user_id
     *
     * @mbggenerated Tue Nov 27 11:52:27 CST 2012
     */
    private Long aartUserId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.userpasswordreset.password_expiration_date
     *
     * @mbggenerated Tue Nov 27 11:52:27 CST 2012
     */
    private Date passwordExpirationDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.userpasswordreset.active_flag
     *
     * @mbggenerated Tue Nov 27 11:52:27 CST 2012
     */
    private Boolean activeFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.userpasswordreset.auth_token
     *
     * @mbggenerated Tue Nov 27 11:52:27 CST 2012
     */
    private String authToken;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.userpasswordreset
     *
     * @mbggenerated Tue Nov 27 11:52:27 CST 2012
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.userpasswordreset.aart_user_id
     *
     * @return the value of public.userpasswordreset.aart_user_id
     *
     * @mbggenerated Tue Nov 27 11:52:27 CST 2012
     */
    public Long getAartUserId() {
        return aartUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.userpasswordreset.aart_user_id
     *
     * @param aartUserId the value for public.userpasswordreset.aart_user_id
     *
     * @mbggenerated Tue Nov 27 11:52:27 CST 2012
     */
    public void setAartUserId(Long aartUserId) {
        this.aartUserId = aartUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.userpasswordreset.password_expiration_date
     *
     * @return the value of public.userpasswordreset.password_expiration_date
     *
     * @mbggenerated Tue Nov 27 11:52:27 CST 2012
     */
    public Date getPasswordExpirationDate() {
        return passwordExpirationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.userpasswordreset.password_expiration_date
     *
     * @param passwordExpirationDate the value for public.userpasswordreset.password_expiration_date
     *
     * @mbggenerated Tue Nov 27 11:52:27 CST 2012
     */
    public void setPasswordExpirationDate(Date passwordExpirationDate) {
        this.passwordExpirationDate = passwordExpirationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.userpasswordreset.active_flag
     *
     * @return the value of public.userpasswordreset.active_flag
     *
     * @mbggenerated Tue Nov 27 11:52:27 CST 2012
     */
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.userpasswordreset.active_flag
     *
     * @param activeFlag the value for public.userpasswordreset.active_flag
     *
     * @mbggenerated Tue Nov 27 11:52:27 CST 2012
     */
    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.userpasswordreset.auth_token
     *
     * @return the value of public.userpasswordreset.auth_token
     *
     * @mbggenerated Tue Nov 27 11:52:27 CST 2012
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.userpasswordreset.auth_token
     *
     * @param authToken the value for public.userpasswordreset.auth_token
     *
     * @mbggenerated Tue Nov 27 11:52:27 CST 2012
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken == null ? null : authToken.trim();
    }
    
    
    /**
     * Added for US-14985
     * This method was generated for checking user password history
     * columns added in userpassword reset table.
     * @return
     */
    
    public String getPassword() {
		return password;
	}

	public void setPassword(String string) {
		this.password = string;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.userpasswordreset
     *
     * @mbggenerated Tue Nov 27 11:52:27 CST 2012
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        UserPasswordReset other = (UserPasswordReset) that;
        return (this.getAartUserId() == null ? other.getAartUserId() == null : this.getAartUserId().equals(other.getAartUserId()))
            && (this.getPasswordExpirationDate() == null ? other.getPasswordExpirationDate() == null : this.getPasswordExpirationDate().equals(other.getPasswordExpirationDate()))
            && (this.getActiveFlag() == null ? other.getActiveFlag() == null : this.getActiveFlag().equals(other.getActiveFlag()))
            && (this.getAuthToken() == null ? other.getAuthToken() == null : this.getAuthToken().equals(other.getAuthToken()))
            && (this.getRequestType() == null ? other.getRequestType() == null : this.getRequestType().equals(other.getRequestType()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.userpasswordreset
     *
     * @mbggenerated Tue Nov 27 11:52:27 CST 2012
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAartUserId() == null) ? 0 : getAartUserId().hashCode());
        result = prime * result + ((getPasswordExpirationDate() == null) ? 0 : getPasswordExpirationDate().hashCode());
        result = prime * result + ((getActiveFlag() == null) ? 0 : getActiveFlag().hashCode());
        result = prime * result + ((getAuthToken() == null) ? 0 : getAuthToken().hashCode());
        result = prime * result + ((getRequestType() == null) ? 0 : getRequestType().hashCode());
        return result;
    }
}