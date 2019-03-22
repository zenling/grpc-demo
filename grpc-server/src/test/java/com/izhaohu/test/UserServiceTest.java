package com.izhaohu.test;

import com.izhaohu.model.User;
import com.izhaohu.service.UserService;
import com.izhaohu.service.serviceimpl.UserServiceImpl;

public class UserServiceTest {

    public static void main(String[] args) {
        UserService service = new UserServiceImpl();
//        service = new UserServiceHbImpl();
        service.addUser(new User(0, "test", 18, "test@izhaohu.com"));
    }
}
