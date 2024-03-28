package com.example.hackingproject.signup;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.hackingproject.signup.dto.User;
import com.example.hackingproject.signup.service.SignupService;
import com.example.hackingproject.signup.userentity.UserEntity;


@Controller

public class signupController {
	private static final Logger logger = LoggerFactory.getLogger(signupController.class);
	
	private SignupService signupService;
	
    @Autowired
    public void SignupController(SignupService signupService) {
        this.signupService = signupService;
    }
	@RequestMapping(path ="/signup_confirm", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> signupConfirm(@RequestBody UserEntity userEntity) {

        signupService.registerUser(userEntity);
        
        
        return ResponseEntity.ok("{\"message\":\"User registered successfully!\"}");
	}
}
