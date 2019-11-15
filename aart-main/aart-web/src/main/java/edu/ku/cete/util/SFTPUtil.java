package edu.ku.cete.util;

import java.io.Closeable;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;

public class SFTPUtil implements Closeable {
	final static Log logger = LogFactory.getLog(SFTPUtil.class);
	private String sftpHostKey;
	private String sftpServer;
	private String sftpUsername;
	private  String sftpPassword;
	private SSHClient sshClient;
	private SFTPClient sftpClient;
	
	public void init(){
		sshClient = new SSHClient();
		try {
			sshClient.addHostKeyVerifier(sftpHostKey);
			sshClient.connect(sftpServer);
			sshClient.authPassword(sftpUsername, sftpPassword);
			sftpClient = sshClient.newSFTPClient();
		} catch (IOException ioe) {
			logger.info("Error on connecting to sftp server", ioe);
		}
	}
	
	@Override
	public void close() throws IOException {
		sftpClient.close();
		sshClient.close();
	}
	
	public boolean checkConnectionAndReestablish(){
		if (!sshClient.isConnected()){
			init();
		}
		return sshClient.isConnected();
	}
	
	public String getSftpHostKey() {
		return sftpHostKey;
	}

	public void setSftpHostKey(String sftpHostKey) {
		this.sftpHostKey = sftpHostKey;
	}

	public String getSftpServer() {
		return sftpServer;
	}

	public void setSftpServer(String sftpServer) {
		this.sftpServer = sftpServer;
	}

	public String getSftpUsername() {
		return sftpUsername;
	}

	public void setSftpUsername(String sftpUsername) {
		this.sftpUsername = sftpUsername;
	}

	public String getSftpPassword() {
		return sftpPassword;
	}

	public void setSftpPassword(String sftpPassword) {
		this.sftpPassword = sftpPassword;
	}

	public SSHClient getSshClient() {
		return sshClient;
	}

	public SFTPClient getSftpClient() {
		checkConnectionAndReestablish();
		return sftpClient;
	}
}
