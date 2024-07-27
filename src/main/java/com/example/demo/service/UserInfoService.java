package com.example.demo.service;

import com.example.demo.entity.UserInfo;

public interface UserInfoService {
    String addUser(UserInfo userInfo);

	Object findByUsername(String name);

}
