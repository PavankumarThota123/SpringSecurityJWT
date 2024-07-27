package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserInfo;
import com.example.demo.repositry.UserInfoRepository;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

	
      @Override
     public String addUser(UserInfo userInfo) {
	userInfo.setPwd(passwordEncoder.encode(userInfo.getPwd()));	userInfoRepository.save(userInfo);
      return "User added successfully";
	}


	@Override
	public Object findByUsername(String name) {
		return userInfoRepository.findByUname(name);
	}



    


}

