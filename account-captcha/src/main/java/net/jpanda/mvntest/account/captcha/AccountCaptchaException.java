package net.jpanda.mvntest.account.captcha;

public class AccountCaptchaException extends Exception {

	public AccountCaptchaException(String msg){
		super(msg);
	}
	
	public AccountCaptchaException(String msg,Exception e){
		super(msg,e);
	}
}
