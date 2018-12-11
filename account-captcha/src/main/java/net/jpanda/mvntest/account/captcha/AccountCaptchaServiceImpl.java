package net.jpanda.mvntest.account.captcha;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.InitializingBean;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

public class AccountCaptchaServiceImpl implements AccountCaptchaService, InitializingBean {
	
	private DefaultKaptcha producer;

	public void afterPropertiesSet() throws Exception {

		producer=new DefaultKaptcha();
		producer.setConfig(new Config(new Properties()));

	}

	public String generateCaptchaKey() throws AccountCaptchaException {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] generateCaptchaImage(String captchaKey) throws AccountCaptchaException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean validateCaptcha(String captchaKey, String captchaValue) throws AccountCaptchaException {
		// TODO Auto-generated method stub
		return false;
	}

	public List<String> getPreDefinedTexts() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPreDefinedTexts(List<String> preDefinedTexts) {
		// TODO Auto-generated method stub

	}

}
