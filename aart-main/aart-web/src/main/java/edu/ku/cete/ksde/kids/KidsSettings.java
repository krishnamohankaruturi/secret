package edu.ku.cete.ksde.kids;

public class KidsSettings {

    private String encryptedPassword;
    private String username;

    /**
     * @param encryptedPassword
     */
    protected final void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    /**
     *
     * @return the encrypted password
     */
    public final String getEncryptedPassword() {
        return encryptedPassword;
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
}
