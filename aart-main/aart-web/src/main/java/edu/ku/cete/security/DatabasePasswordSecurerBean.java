package edu.ku.cete.security;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * This bean secures passwords that have been entered into the database using SQL statements. Salts and encodes passwords in the
 * database.
 * @author neil.howerton
 *
 */
public class DatabasePasswordSecurerBean extends JdbcDaoSupport {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SaltSource saltSource;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Method run on initialization of bean.
     */
    public final void secureDatabase() {
        getJdbcTemplate().query("select username, password from aartuser where ukey is null", new RowCallbackHandler(){

            @Override
            public void processRow(ResultSet paramResultSet) throws SQLException {
                String password = paramResultSet.getString("password");
                String username = paramResultSet.getString("username");

                String salt = RandomStringUtils.random(16);

                String encodedPassword = passwordEncoder.encodePassword(password, salt);

                logger.debug("Updating passwrd for username " + username + " to: " + encodedPassword);

                getJdbcTemplate().update("update aartuser set password = ?, ukey = ? where username = ?", encodedPassword, salt,
                        username);
            }
        });
    }
}
