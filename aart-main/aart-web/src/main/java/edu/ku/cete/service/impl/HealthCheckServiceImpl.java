package edu.ku.cete.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import edu.ku.cete.service.HealthCheckService;

@Service
public class HealthCheckServiceImpl implements HealthCheckService {
	
	private static final Logger logger = LoggerFactory.getLogger(HealthCheckServiceImpl.class); 
	
	@Value("${jdbc.driverClassName}")
	private String jdbcDriver;
	@Value("${jdbc.url}")
	private String jdbcConnectionUrl;
	@Value("${jdbc.username}")
	private String jdbcConnectionUsername;
	@Value("${jdbc.password}")
	private String jdbcConnectionPassword;
	
	@Value("${jdbc.report.driverClassName}")
	private String audit_jdbcDriver;
	@Value("${jdbc.report.url}")
	private String audit_jdbcConnectionUrl;
	@Value("${jdbc.report.username}")
	private String audit_jdbcConnectionUsername;
	@Value("${jdbc.report.password}")
	private String audit_jdbcConnectionPassword;
	
	@Value("${jdbc.datawarehouse.driverClassName}")
	private String warehouse_jdbcDriver;
	@Value("${jdbc.datawarehouse.url}")
	private String warehouse_jdbcConnectionUrl;
	@Value("${jdbc.datawarehouse.username}")
	private String warehouse_jdbcConnectionUsername;
	@Value("${jdbc.datawarehouse.password}")
	private String warehouse_jdbcConnectionPassword;
	
	@Value("${jdbc.readreplica.driverClassName}")
	private String replica_jdbcDriver;
	@Value("${jdbc.readreplica.url}")
	private String replica_jdbcConnectionUrl;
	@Value("${jdbc.readreplica.username}")
	private String replica_jdbcConnectionUsername;
	@Value("${jdbc.readreplica.password}")
	private String replica_jdbcConnectionPassword;
	
	@Override
	public List<String> doHealthCheck() {
		List<String> errors = new ArrayList<String>();
		Map<String, List<String>> config = new HashMap<String, List<String>>();
		
		// for simplification using the loop
		config.put("main", Arrays.asList(jdbcDriver, jdbcConnectionUrl, jdbcConnectionUsername, jdbcConnectionPassword));
		config.put("replica", Arrays.asList(replica_jdbcDriver, replica_jdbcConnectionUrl, replica_jdbcConnectionUsername, replica_jdbcConnectionPassword));
		config.put("audit", Arrays.asList(audit_jdbcDriver, audit_jdbcConnectionUrl, audit_jdbcConnectionUsername, audit_jdbcConnectionPassword));
		config.put("warehouse", Arrays.asList(warehouse_jdbcDriver, warehouse_jdbcConnectionUrl, warehouse_jdbcConnectionUsername, warehouse_jdbcConnectionPassword));
		
		for (String key : config.keySet()) {
			logger.debug(new StringBuilder()
					.append("Starting health check for ")
					.append(key)
					.append(" database...").toString());
			
			String driver = config.get(key).get(0);
			String url = config.get(key).get(1);
			String username = config.get(key).get(2);
			String password = config.get(key).get(3);
			
			Connection connection = null;
			Statement statement = null;
			ResultSet resultSet = null;
			
			try {
				logger.debug(new StringBuilder().append("loading driver ").append(driver).toString());
				Class.forName(driver);
				
				logger.debug(new StringBuilder().append("initiating db connection").toString());
				connection = DriverManager.getConnection(url, username, password);
				
				statement = connection.createStatement();
				resultSet = statement.executeQuery("SELECT 1 AS query_result");
				while (resultSet.next()) {
					String result = resultSet.getString("query_result");
					if (!StringUtils.equals(result, "1")) {
						String error = new StringBuilder()
								.append("Test query output did not match expected in ")
								.append(key)
								.append(" database").toString();
						logger.error(error);
						errors.add(error);
					}
				}
			} catch (Exception e) {
				String error = new StringBuilder()
						.append("Error performing health check in ")
						.append(key)
						.append(" database").toString();
				logger.error(error, e);
				errors.add(error);
			} finally {
				try {
					if (resultSet != null && !resultSet.isClosed()) {
						resultSet.close();
					}
				} catch (SQLException sqle) {/* swallow */}
				
				try {
					if (statement != null && !statement.isClosed()) {
						statement.close();
					}
				} catch (SQLException sqle) {/* swallow */}
				
				try {
					if (connection != null && !connection.isClosed()) {
						connection.close();
					}
				} catch (SQLException sqle) {/* swallow */}
			}
		}
		
		return errors;
	}
}
