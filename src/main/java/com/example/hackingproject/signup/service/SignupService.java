package com.example.hackingproject.signup.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hackingproject.dao.SignupDAO;
import com.example.hackingproject.signup.userentity.UserEntity;

@Service
public class SignupService {
	private SignupDAO signupDAO;
	
    @Autowired
    public SignupService(SignupDAO signupDAO) {
        this.signupDAO = signupDAO;
    }
    
    
	
	private static final Logger logger = LoggerFactory.getLogger(SignupService.class);
    public void registerUser(UserEntity userEntity) {
        // UserEntity 객체의 데이터를 로그로 출력
        logger.info("Registering userId: {}, UserPw: {}, UserTelno: {}, UserName: {}, UserBirth: {}, UserBank: {}, AccountNumber: {}, UserAgency: {}, AccessLevel: {}, AccountBalance: {}",
                userEntity.getUserId(), userEntity.getUserPw(), userEntity.getUserTelno(), userEntity.getUserName(), userEntity.getUserBirth(),
                userEntity.getUserBank(), userEntity.getAccountNumber(), userEntity.getUserAgency(), userEntity.getAccessLevel(), userEntity.getAccountBalance());
    
        signupDAO.insertUser(userEntity);
    }

}



