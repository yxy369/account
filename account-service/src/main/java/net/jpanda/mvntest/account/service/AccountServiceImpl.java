package net.jpanda.mvntest.account.service;

import java.util.HashMap;
import java.util.Map;

import net.jpanda.mvntest.account.captcha.AccountCaptchaException;
import net.jpanda.mvntest.account.captcha.AccountCaptchaService;
import net.jpanda.mvntest.account.captcha.RandomGenerator;
import net.jpanda.mvntest.account.email.AccountEmailException;
import net.jpanda.mvntest.account.email.AccountEmailService;
import net.jpanda.mvntest.account.persist.Account;
import net.jpanda.mvntest.account.persist.AccountPersistException;
import net.jpanda.mvntest.account.persist.AccountPersistService;

public class AccountServiceImpl implements AccountService {
	
	private AccountPersistService accountPersistService;
	
	private AccountEmailService accountEmailService;
	
	private AccountCaptchaService accountCaptchaService;
	
	private Map<String, String> activationMap=new HashMap<String, String>();

	public AccountPersistService getAccountPersistService() {
		return accountPersistService;
	}

	public void setAccountPersistService(AccountPersistService accountPersistService) {
		this.accountPersistService = accountPersistService;
	}

	public AccountEmailService getAccountEmailService() {
		return accountEmailService;
	}

	public void setAccountEmailService(AccountEmailService accountEmailService) {
		this.accountEmailService = accountEmailService;
	}

	public AccountCaptchaService getAccountCaptchaService() {
		return accountCaptchaService;
	}

	public void setAccountCaptchaService(AccountCaptchaService accountCaptchaService) {
		this.accountCaptchaService = accountCaptchaService;
	}

	public String generateCaptchaKey() throws AccountServiceException {
		try{
			return accountCaptchaService.generateCaptchaKey();
		}catch(AccountCaptchaException e){
			throw new AccountServiceException("Unable to generate Captcha key.", e);
		}
	}

	public byte[] generateCaptchaImage(String captchaKey) throws AccountServiceException {
		try{
			return accountCaptchaService.generateCaptchaImage(captchaKey);
		}catch(AccountCaptchaException e){
			throw new AccountServiceException("Unable to generate Captcha Image.",e);
		}
	}
	
	public void signUp(SignUpRequest signUpRequest) throws AccountServiceException{
		try{
			if(!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword()))
				throw new AccountServiceException("2 passwords do not match.");
			if(!accountCaptchaService.validateCaptcha(signUpRequest.getCaptchaKey(), signUpRequest.getCaptchaValue()))
				throw new AccountServiceException("Incorrect Captcha.");
			Account account=new Account();
			account.setId(signUpRequest.getId());
			account.setEmail(signUpRequest.getEmail());
			account.setName(signUpRequest.getName());
			account.setPassword(signUpRequest.getPassword());
			account.setActivated(false);
			
			accountPersistService.createAccount(account);
			String activationId=RandomGenerator.getRandomString();
			activationMap.put(activationId, account.getId());
			String link=signUpRequest.getActivateServiceUrl().endsWith("/")?signUpRequest.getActivateServiceUrl()
					+activationId:signUpRequest.getActivateServiceUrl()+"?key="+activationId;
			accountEmailService.sendMail(account.getEmail(), "Please Activate Your Account", link);
		}catch(AccountCaptchaException e){
			throw new AccountServiceException("Unable to validate captcha.", e);
		}catch(AccountPersistException e){
			throw new AccountServiceException("Unable to create account.", e);
		}catch(AccountEmailException e){
			throw new AccountServiceException("Unable to send activation mail.", e);
		}
	}

	public void Activate(String activationId) throws AccountServiceException {
		String accountId=activationMap.get(activationId);
		if(accountId==null)
			throw new AccountServiceException("Invalid account activation ID.");
		try{
			Account account=accountPersistService.readAccount(accountId);
			account.setActivated(true);
			accountPersistService.updateAccount(account);
		}catch(AccountPersistException e){
			throw new AccountServiceException("Unable to activate account.");
		}

	}

	public void login(String id, String password) throws AccountServiceException {
		
		try{
			Account account=accountPersistService.readAccount(id);
			if(account==null)
				throw new AccountServiceException("Account does not exist.");
			if(!account.isActivated())
				throw new AccountServiceException("Account is disabled.");
			if(!account.getPassword().equals(password))
				throw new AccountServiceException("Incorrect password.");
		}catch(AccountPersistException e){
			throw new AccountServiceException("Unable to log in.", e);
		}

	}

}
