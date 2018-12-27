package net.jpanda.mvntest.account.service;

public class AccountServiceException extends Exception {

	public AccountServiceException(String msg){
		super(msg);
	}
	
	public AccountServiceException(String msg, Exception e){
		super(msg,e);
	}
}
