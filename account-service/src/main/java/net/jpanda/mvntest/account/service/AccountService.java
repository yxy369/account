package net.jpanda.mvntest.account.service;

public interface AccountService {

	String generateCaptchaKey() throws AccountServiceException;
	
	byte[] generateCaptchaImage(String captchaKey) throws AccountServiceException;
	
	void signUp(SignUpRequest signUpRequest) throws AccountServiceException;
	
	void Activate(String activationNumber) throws AccountServiceException;
	
	void login(String id, String password) throws AccountServiceException;
}
