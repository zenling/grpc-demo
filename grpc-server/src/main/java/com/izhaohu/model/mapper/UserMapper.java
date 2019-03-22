package com.izhaohu.model.mapper;

import com.izhaohu.model.User;

import java.util.List;

public interface UserMapper {
    public int addUser(User user) throws Exception;

    public int updateUser(User user, int id) throws  Exception;

    public int deleteUser(int id) throws  Exception;

    public User getUserById(int id) throws  Exception;

    public List<User> getUsers() throws  Exception;
}
