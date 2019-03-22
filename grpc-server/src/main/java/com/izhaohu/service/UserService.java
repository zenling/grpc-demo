package com.izhaohu.service;

import com.izhaohu.model.User;

import java.util.List;

public interface UserService {

    public int addUser(User user);

    public int updateUser(User user, int id);

    public int deleteUser(int id);

    public User getUserById(int id);

    public List<User> getUsers();
}
