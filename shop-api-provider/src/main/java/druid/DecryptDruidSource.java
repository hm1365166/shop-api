package druid;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DecryptDruidSource extends DruidDataSource {
	private static Log logger = LogFactory.getLog(DecryptDruidSource.class);
	private String publicKey;

	@Override
	public void setUsername(String username) {
		try {
			username = ConfigTools.decrypt(publicKey, username);
		} catch (Exception e) {
			logger.error("解密错误:" + e.getCause());
		}
		super.setUsername(username);
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public static void main(String[] args) throws Exception {
		String[] genKeyPair = ConfigTools.genKeyPair(512);
		String privateKey = genKeyPair[0];
		String publicKey = genKeyPair[1];

		privateKey = "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAzB/0R7R0l96c4TwE/llvf8QL4TRx6s7K0QkVW1Gelnexd07ROTOG0myW3Ut/qGGWloR7HsudMJbTTwprVdfOwQIDAQABAkAPKkyWyzRiWBqlrTTdxL1A68rKJ4BTJpvw3dDlYPH8UbQ3SW9ZL2Lj6/o9X4Cb8n0zzMXf4n0krqzh4XXecJ3xAiEA6UBRCPwJMguZecY7u+4U8LS4nXVEkQOYQU+nZ7BBLK0CIQDgCG0OL+CPmD93qalKuSM1gjYx21oXoO/6BPEk/Hk45QIgXm3vYJfIkT4o8CfU3wxP5fMEGprzG1I9PZOlzlCI0jECIFRHFy91F1ctgQK1EniHuRUSDO0ohXXKFzzIyyaMy9V5AiA0SOxEYuWOBcf0FCk0lKvW50y/K9K8oG39KB0f9Z8Utg==";
		publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMwf9Ee0dJfenOE8BP5Zb3/EC+E0cerOytEJFVtRnpZ3sXdO0TkzhtJslt1Lf6hhlpaEex7LnTCW008Ka1XXzsECAwEAAQ==";

		System.out.println("privateKey: " + privateKey);
		System.out.println("publicKey : " + publicKey);

		//		String username = "yjsdata";
		//		String password = "1*oracle";
		String username = "root";
		String password = "123";
		System.out.println("username: " + username);
		System.out.println("password: " + password);
		System.out.println("username encrypt with privateKey: " + ConfigTools.encrypt(privateKey, username));
		System.out.println("password encrypt with privateKey: " + ConfigTools.encrypt(privateKey, password));

	}
}
